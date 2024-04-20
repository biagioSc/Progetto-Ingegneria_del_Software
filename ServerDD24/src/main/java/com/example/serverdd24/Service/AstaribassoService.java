package com.example.serverdd24.Service;

import com.example.serverdd24.Controller.UtenteController;
import com.example.serverdd24.DTO.AstaribassoDTO;
import com.example.serverdd24.Model.AstaribassoModel;
import com.example.serverdd24.Repository.AstaribassoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class AstaribassoService {
    @Autowired
    private AstaribassoRepository astaribassoRepository;
    @Autowired
    private StorageService storageService;
    private static final Logger logger = LoggerFactory.getLogger(UtenteController.class);
    private static final String[] MAPPING_CATEGORIE = {
            "Elettronica",             // categoria1
            "Moda",                    // categoria2
            "Casa e Giardino",         // categoria3
            "Sport e Tempo Libero",    // categoria4
            "Bellezza e Salute",       // categoria5
            "Libri, Film e Musica",    // categoria6
            "Giochi e Giocattoli",     // categoria7
            "Arte e Antiquariato",     // categoria8
            "Auto, Moto e Altri Veicoli", // categoria9
            "Gioielli e Orologi",      // categoria10
            "Immobili",                // categoria11
            "Collezionismo",           // categoria12
            "Altro"                    // categoria13
    };

    public void creaAsta(AstaribassoDTO astaDTO) {
        try{
            AstaribassoModel asta = new AstaribassoModel();

            // Imposta i campi dell'oggetto asta utilizzando i valori da astaDTO
            asta.setStato(astaDTO.isStato()); // Supponendo che stato sia un booleano
            asta.setTitolo(astaDTO.getTitolo());
            asta.setCategoria(astaDTO.getCategoria());
            asta.setSottocategoria(astaDTO.getSottocategoria());
            asta.setDescrizioneprodotto(astaDTO.getDescrizioneprodotto());
            asta.setVenditore(astaDTO.getVenditore());
            String base64Image = astaDTO.getImmagineprodotto();
            String contentType = "image/jpeg"; // Assicurati di avere un modo per ottenere il contentType corretto

            if(base64Image!=null) {
                String imageUrl = storageService.uploadBase64Image(astaDTO.getVenditore() + "_" + System.currentTimeMillis() + ".jpg", base64Image, contentType);
                asta.setImmagineprodotto(imageUrl);
            }else {
                asta.setImmagineprodotto(null);
            }

            asta.setImportodecremento(astaDTO.getImportodecremento());
            asta.setPrezzominimosegreto(astaDTO.getPrezzominimosegreto());
            asta.setPrezzoiniziale(astaDTO.getPrezzoiniziale());
            asta.setTimerdecremento(astaDTO.getTimerdecremento()); // Assicurati che i tipi siano compatibili
            asta.setUltimoprezzo(astaDTO.getUltimoprezzo());
            asta.setParolechiave(astaDTO.getTitolo().replaceAll("([a-zA-Z0-9]+)", "$1,"));
            asta.setCreated_at( new Timestamp(System.currentTimeMillis()));

            // Salva l'oggetto asta nel database
            astaribassoRepository.save(asta);
        } catch (DataIntegrityViolationException e) {
            logger.error(String.valueOf(e));
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Errore di integrità dei dati", e);
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Errore durante la creazione dell'asta", e);
        }
    }

    public List<AstaribassoModel> getAsteByVenditore(String venditore) {
        try {
            return astaribassoRepository.findByVenditoreIgnoreCase(venditore);
        } catch (Exception e) {
            // Restituisci una lista vuota o gestisci l'eccezione come preferisci
            return Collections.emptyList();
            // Oppure, se preferisci propagare l'errore:
            // throw new CustomException("Messaggio di errore", e);
        }
    }

    public List<AstaribassoModel> getAsteAcquirente(String email) {
        try {
            return astaribassoRepository.findByStatoTrue(email);
        } catch (Exception e) {
            // Restituisci una lista vuota o gestisci l'eccezione come preferisci
            return Collections.emptyList();
            // Oppure, se preferisci propagare l'errore:
            // throw new CustomException("Messaggio di errore", e);
        }
    }

    public void aggiornaPrezziAttuali() {
        // Recupera tutte le aste al ribasso dal database
        List<AstaribassoModel> aste = astaribassoRepository.findAll();

        // Itera su ciascuna asta e aggiorna il prezzo attuale
        for (AstaribassoModel asta : aste) {
            if (asta.isStato()){
                double prezzoAttuale = calcolaPrezzoAttuale(asta);
                if(prezzoAttuale>=asta.getPrezzominimosegreto()) {
                    asta.setUltimoprezzo(prezzoAttuale);
                }else{
                    asta.setStato(false);
                }
                // Aggiorna il record nel database con il nuovo prezzo attuale
                astaribassoRepository.save(asta);
            }
        }
    }

    // Metodo per calcolare il prezzo attuale di un'asta
    private double calcolaPrezzoAttuale(AstaribassoModel asta) {
        // Ottieni i dati necessari per il calcolo
        LocalDateTime createdAt = asta.getCreated_at().toLocalDateTime();
        int timerDecremento = asta.getTimerdecremento();
        double prezzoIniziale = asta.getPrezzoiniziale();
        double importoDecremento = asta.getImportodecremento();

        // Calcola il tempo trascorso dall'inizio dell'asta
        LocalDateTime now = LocalDateTime.now();
        long minutiTrascorsi = ChronoUnit.MINUTES.between(createdAt, now);

        // Calcola il numero di decrementi completati
        int decrementiCompletati = (int) (minutiTrascorsi / timerDecremento);

        // Calcola il prezzo attuale
        double prezzoAttuale = prezzoIniziale - (decrementiCompletati * importoDecremento);

        // Restituisci il prezzo attuale
        return prezzoAttuale;
    }

    public List<AstaribassoModel> searchByKeywordAcquirente(String query, String ordinaper, String email) {
        try {

            return astaribassoRepository.searchByKeywordAcquirente(query, ordinaper, email);
        } catch (Exception e) {
            // Restituisci una lista vuota o gestisci l'eccezione come preferisci
            return Collections.emptyList();
            // Oppure, se preferisci propagare l'errore:
            // throw new CustomException("Messaggio di errore", e);
        }
    }

    public List<AstaribassoModel> searchByKeywordVenditore(String query, String ordinaper, String email) {
        try {

            return astaribassoRepository.searchByKeywordVenditore(query, ordinaper, email);
        } catch (Exception e) {
            // Restituisci una lista vuota o gestisci l'eccezione come preferisci
            return Collections.emptyList();
            // Oppure, se preferisci propagare l'errore:
            // throw new CustomException("Messaggio di errore", e);
        }
    }
}
