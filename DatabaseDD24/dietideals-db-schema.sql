
CREATE SCHEMA dietideals24 AUTHORIZATION postgres;


--Domain
CREATE DOMAIN STATO_ACCETTAZIONE_DOMINIO AS VARCHAR(60)
	CHECK ( VALUE LIKE 'ACCETTATA' OR VALUE LIKE 'RIFIUTATA' OR VALUE LIKE 'IN VALUTAZIONE' );

CREATE DOMAIN PASSWORD_DOMINIO AS VARCHAR(40)
    CHECK (VALUE ~ '^.*(?=.*[0-9])(?=.*[a-zA-Z]).*$' AND VALUE LIKE '________%');

CREATE DOMAIN NOME_DOMINIO AS VARCHAR(255)
    CHECK (VALUE ~ '^[A-Za-z]+( [A-Za-z]+)*$');

CREATE DOMAIN COGNOME_DOMINIO AS VARCHAR(255)
    CHECK (VALUE ~ '^[A-Za-z]+( [A-Za-z]+)*$');

CREATE DOMAIN EMAIL_DOMINIO AS VARCHAR(255)
    CHECK (VALUE ~ '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$');

CREATE DOMAIN URLWEB_DOMINIO AS TEXT
    CHECK (VALUE ~ '^((https?:\/\/)?(www\.)?[-a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_\+.~#?&//=]*))');

CREATE DOMAIN URLSOCIAL_DOMINIO AS TEXT
    CHECK (VALUE ~ '([a-zA-Z0-9_.-]+\.)+[a-zA-Z]{2,6}\/[a-zA-Z0-9_.-]+\/?');



-- Table for 'Utente'
CREATE TABLE Utente (
    Nome VARCHAR(255) NOT NULL,
    Cognome VARCHAR(255) NOT NULL,
    Citta VARCHAR(255) NOT NULL,
    Nazionalita VARCHAR(255) NOT NULL,
    Email VARCHAR(255) PRIMARY KEY,
    Password PASSWORD_DOMINIO NOT NULL,
    Pin INTEGER NOT NULL UNIQUE CHECK (length(Pin::text) = 5),
	Tempopin TIMESTAMP NOT NULL,
	Immagine TEXT,
    LinkWeb VARCHAR(255),
	LinkSocial VARCHAR(255),
    Biografia TEXT,
	Token VARCHAR(255)
);

-- Table for 'Asta'
CREATE TABLE AstaSilenziosa (
    ID SERIAL PRIMARY KEY,
    Stato BOOLEAN NOT NULL DEFAULT TRUE,
    Titolo VARCHAR(255) NOT NULL,
    Categoria VARCHAR(255) NOT NULL,
    Sottocategoria VARCHAR(255) NOT NULL,
    Descrizioneprodotto TEXT NOT NULL,
    Venditore EMAIL_DOMINIO NOT NULL,
    Immagineprodotto TEXT,
	Parolechiave VARCHAR(255) NOT NULL,
    Datafineasta VARCHAR(255) NOT NULL,
    Prezzovendita DOUBLE PRECISION DEFAULT 0,

    FOREIGN KEY (Venditore) REFERENCES Utente(Email)
);
CREATE SEQUENCE AstaSilenziosaID
START 1
INCREMENT 1
MINVALUE 1
MAXVALUE 99999
OWNED BY AstaSilenziosa.ID;

-- Table for 'Asta'
CREATE TABLE AstaRibasso (
    ID SERIAL PRIMARY KEY,
    Stato BOOLEAN NOT NULL DEFAULT TRUE,
    Titolo VARCHAR(255) NOT NULL,
	Parolechiave VARCHAR(255) NOT NULL,
    Categoria VARCHAR(255) NOT NULL,
    Sottocategoria VARCHAR(255) NOT NULL,
    Descrizioneprodotto TEXT NOT NULL,
    Venditore EMAIL_DOMINIO NOT NULL,
    Immagineprodotto TEXT,
    Importodecremento DOUBLE PRECISION NOT NULL,
    Prezzominimosegreto DOUBLE PRECISION NOT NULL, 
    Prezzoiniziale DOUBLE PRECISION NOT NULL CHECK (Prezzoiniziale > Prezzominimosegreto),
    Timerdecremento INTEGER NOT NULL DEFAULT 60,
    Ultimoprezzo DOUBLE PRECISION NOT NULL CHECK (Ultimoprezzo >= Prezzominimosegreto),
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Prezzovendita DOUBLE PRECISION DEFAULT 0,

    FOREIGN KEY (Venditore) REFERENCES Utente(Email),
	CHECK (Prezzominimosegreto < Prezzoiniziale AND Ultimoprezzo >= Prezzominimosegreto)

);
CREATE SEQUENCE AstaRibassoID
START 1
INCREMENT 1
MINVALUE 1
MAXVALUE 99999
OWNED BY AstaRibasso.ID;


CREATE TABLE AsteSilenzioseSeguite(
    EmailUtente EMAIL_DOMINIO NOT NULL, 
    IDAstaSilenziosa INTEGER NOT NULL,

    FOREIGN KEY (EmailUtente) REFERENCES Utente(Email),
    FOREIGN KEY (IDAstaSilenziosa) REFERENCES AstaSilenziosa(ID),

	UNIQUE (EmailUtente, IDAstaSilenziosa)
);

CREATE TABLE AsteRibassoSeguite(
    EmailUtente EMAIL_DOMINIO NOT NULL, 
    IDAstaRibasso INTEGER NOT NULL,

    FOREIGN KEY (EmailUtente) REFERENCES Utente(Email),
    FOREIGN KEY (IDAstaRibasso) REFERENCES AstaRibasso(ID),
	
	UNIQUE (EmailUtente, IDAstaRibasso)
);

CREATE TABLE OfferteAstaSilenziosa(
    EmailUtente EMAIL_DOMINIO NOT NULL, 
    IDAstaSilenziosa INTEGER NOT NULL,
    PrezzoOfferto DOUBLE PRECISION NOT NULL,
    StatoAccettazione STATO_ACCETTAZIONE_DOMINIO NOT NULL DEFAULT 'IN VALUTAZIONE',

    FOREIGN KEY (EmailUtente) REFERENCES Utente(Email),
    FOREIGN KEY (IDAstaSilenziosa) REFERENCES AstaSilenziosa(ID),
	
	UNIQUE (EmailUtente, IDAstaSilenziosa, StatoAccettazione)
);

CREATE TABLE AcquistiAstaRibasso(
    EmailUtente EMAIL_DOMINIO NOT NULL, 
    IDAstaRibasso INTEGER NOT NULL,
    PrezzoAcquisto DOUBLE PRECISION NOT NULL,

    FOREIGN KEY (EmailUtente) REFERENCES Utente(Email),
    FOREIGN KEY (IDAstaRibasso) REFERENCES AstaRibasso(ID),
	
	UNIQUE (EmailUtente, IDAstaRibasso)
);



--Funzioni

--aggiorna lo stato dell'asta a ribasso a FALSE quando il prezzo raggiunge o scende
--sotto il prezzo minimo segreto o sotto zero.
CREATE OR REPLACE FUNCTION verifica_stato_asta()
RETURNS TRIGGER AS $$
BEGIN
    -- Verifica se l'ultimo prezzo è maggiore di 0 e maggiore o uguale al prezzo minimo segreto
    IF NEW.Ultimoprezzo <= 0 OR NEW.Ultimoprezzo < NEW.Prezzominimosegreto THEN
        NEW.Stato := FALSE;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


--cambia lo stato dell'asta silenziosa a FALSE quando lo stato di accettazione in OfferteAstaSilenziosa
--cambia in ACCETTATA.
CREATE OR REPLACE FUNCTION aggiorna_stato_astaSilenziosa()
RETURNS TRIGGER AS $$
BEGIN
    -- Controlla se lo stato di accettazione è cambiato in 'ACCETTATA'
    IF NEW.StatoAccettazione = 'ACCETTATA' THEN
        -- Aggiorna lo stato dell'asta corrispondente in AstaSilenziosa
        UPDATE AstaSilenziosa
        SET Stato = FALSE
        WHERE ID = NEW.IDAstaSilenziosa;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

--cambia lo stato dell'asta ribasso a FALSE quando si effettua l'acquistp
CREATE OR REPLACE FUNCTION aggiorna_stato_asta_ribasso()
RETURNS TRIGGER AS $$
BEGIN
    -- Imposta lo stato dell'asta a ribasso su falso quando viene effettuato un inserimento
    UPDATE AstaRibasso
    SET Stato = FALSE
    WHERE ID = NEW.IDAstaRibasso;

	UPDATE AstaRibasso
	SET Prezzovendita = NEW.PrezzoAcquisto
	WHERE ID = NEW.IDAstaRibasso;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


--cambia lo stato delle offerte "IN VALUTAZIONE" di un'asta silenziosa a "RIFIUTATA" quando lo stato di accettazione di un'OfferteAstaSilenziosa
--cambia in ACCETTATA.
CREATE OR REPLACE FUNCTION update_offerte_status()
RETURNS TRIGGER AS $$
BEGIN
    -- Controlla se l'offerta è stata accettata
    IF NEW.StatoAccettazione = 'ACCETTATA' THEN
        -- Aggiorna lo stato di tutte le altre offerte "IN VALUTAZIONE" per la stessa asta
        UPDATE OfferteAstaSilenziosa
        SET StatoAccettazione = 'RIFIUTATA'
        WHERE IDAstaSilenziosa = NEW.IDAstaSilenziosa
          AND EmailUtente != NEW.EmailUtente
          AND StatoAccettazione = 'IN VALUTAZIONE';
		  
		UPDATE AstaSilenziosa
        SET Prezzovendita = NEW.PrezzoOfferto
        WHERE ID = NEW.IDAstaSilenziosa;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


--controlla che ogni asta silenziosa abbia una sola offerta "ACCETTATA"
CREATE OR REPLACE FUNCTION check_unica_offerta_accettata()
RETURNS TRIGGER AS $$
BEGIN
    -- Controlla per l'inserimento di una nuova offerta
    IF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') AND NEW.StatoAccettazione = 'ACCETTATA' THEN
        IF EXISTS (
            SELECT 1
            FROM OfferteAstaSilenziosa
            WHERE IDAstaSilenziosa = NEW.IDAstaSilenziosa
            AND StatoAccettazione = 'ACCETTATA'
            AND EmailUtente != NEW.EmailUtente -- ignora la stessa offerta se è un aggiornamento
        ) THEN
            RAISE EXCEPTION 'Esiste già un''offerta accettata per questa asta.';
        END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


--controllo che l'utente non faccia un'altra offerta per un'asta se la sua è ancora "in valutazione"
CREATE OR REPLACE FUNCTION verifica_unica_offerta_in_valutazione()
RETURNS TRIGGER AS $$
BEGIN
    -- Se l'offerta è in inserimento o aggiornamento con stato "IN VALUTAZIONE"
    IF (TG_OP = 'INSERT' OR (TG_OP = 'UPDATE' AND NEW.StatoAccettazione = 'IN VALUTAZIONE')) THEN
        -- Controlla se esiste già un'offerta "IN VALUTAZIONE" per lo stesso utente e asta
        IF (SELECT COUNT(*) FROM OfferteAstaSilenziosa 
            WHERE EmailUtente = NEW.EmailUtente 
            AND IDAstaSilenziosa = NEW.IDAstaSilenziosa 
            AND StatoAccettazione = 'IN VALUTAZIONE'
            AND (TG_OP = 'UPDATE' AND NEW.IDAstaSilenziosa <> OLD.IDAstaSilenziosa OR TG_OP = 'INSERT')) > 0 THEN
            RAISE EXCEPTION 'L''utente % non può avere più di un''offerta "IN VALUTAZIONE" per l''asta %', NEW.EmailUtente, NEW.IDAstaSilenziosa;
        END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION trg_set_default_value_ribasso()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.Prezzovendita IS NULL THEN
        NEW.Prezzovendita = 0;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION trg_set_default_value_silenziosa()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.Prezzovendita IS NULL THEN
        NEW.Prezzovendita = 0;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

--Triggers
CREATE TRIGGER verifica_stato_asta_trigger
BEFORE UPDATE ON AstaRibasso
FOR EACH ROW
EXECUTE FUNCTION verifica_stato_asta();


CREATE TRIGGER aggiorna_stato_asta_trigger
AFTER UPDATE ON OfferteAstaSilenziosa
FOR EACH ROW
WHEN (OLD.StatoAccettazione IS DISTINCT FROM NEW.StatoAccettazione)
EXECUTE FUNCTION aggiorna_stato_asta();


CREATE TRIGGER trg_update_offerte_status
AFTER UPDATE ON OfferteAstaSilenziosa
FOR EACH ROW
WHEN (OLD.StatoAccettazione IS DISTINCT FROM NEW.StatoAccettazione)
EXECUTE FUNCTION update_offerte_status();


CREATE TRIGGER trg_check_unica_offerta_accettata
BEFORE INSERT OR UPDATE ON OfferteAstaSilenziosa
FOR EACH ROW
EXECUTE FUNCTION check_unica_offerta_accettata();


CREATE TRIGGER verifica_unica_offerta_in_valutazione_trg
BEFORE INSERT OR UPDATE ON OfferteAstaSilenziosa
FOR EACH ROW
EXECUTE FUNCTION verifica_unica_offerta_in_valutazione();

CREATE TRIGGER trigger_aggiorna_stato_asta_ribasso
AFTER INSERT ON AcquistiAstaRibasso
FOR EACH ROW
EXECUTE FUNCTION aggiorna_stato_asta_ribasso();

CREATE TRIGGER set_default_value_ribasso
BEFORE INSERT OR UPDATE ON AstaRibasso
FOR EACH ROW
EXECUTE FUNCTION trg_set_default_value_ribasso();

CREATE TRIGGER set_default_value_silenziosa
BEFORE INSERT OR UPDATE ON AstaSilenziosa
FOR EACH ROW
EXECUTE FUNCTION trg_set_default_value_silenziosa();

CREATE INDEX idx_fts_astesilenziosa ON astasilenziosa USING gin(to_tsvector('italian', titolo || ' ' || descrizioneprodotto || ' ' || parolechiave));
CREATE INDEX idx_fts_asteribasso ON astaribasso USING gin(to_tsvector('italian', titolo || ' ' || descrizioneprodotto || ' ' || parolechiave));

