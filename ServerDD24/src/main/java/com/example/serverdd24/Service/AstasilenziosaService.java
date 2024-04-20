package com.example.serverdd24.Service;


import com.example.serverdd24.Controller.AstasilenziosaController;
import com.example.serverdd24.DTO.AstasilenziosaDTO;
import com.example.serverdd24.Model.AstaribassoModel;
import com.example.serverdd24.Model.AstasilenziosaModel;
import com.example.serverdd24.Repository.AstasilenziosaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class AstasilenziosaService {
    @Autowired
    private AstasilenziosaRepository astasilenziosaRepository;
    @Autowired
    private StorageService storageService;
    private static final Logger logger = LoggerFactory.getLogger(AstasilenziosaController.class);
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
    public void creaAsta(AstasilenziosaDTO astaDTO) {

        try {
            AstasilenziosaModel asta = new AstasilenziosaModel();

            // Imposta i campi dell'oggetto asta utilizzando i valori da astaDTO
            asta.setStato(astaDTO.isStato()); // Supponendo che stato sia un booleano
            asta.setTitolo(astaDTO.getTitolo());
            asta.setCategoria(astaDTO.getCategoria());
            asta.setSottocategoria(astaDTO.getSottocategoria());
            asta.setDescrizioneprodotto(astaDTO.getDescrizioneprodotto());
            asta.setVenditore(astaDTO.getVenditore());
            asta.setDatafineasta(astaDTO.getDatafineasta());

            String base64Image = astaDTO.getImmagineprodotto();
            String contentType = "image/jpeg"; // Assicurati di avere un modo per ottenere il contentType corretto

            if (base64Image != null) {
                String imageUrl = storageService.uploadBase64Image(astaDTO.getVenditore() + "_" + System.currentTimeMillis() + ".jpg", base64Image, contentType);
                asta.setImmagineprodotto(imageUrl);
            } else {
                asta.setImmagineprodotto(null);
            }

            asta.setParolechiave(astaDTO.getTitolo().replaceAll("([a-zA-Z0-9]+)", "$1,"));
            // Salva l'oggetto asta nel database
            astasilenziosaRepository.save(asta);
        } catch (DataIntegrityViolationException e) {
            logger.error(String.valueOf(e));
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Errore di integrità dei dati", e);
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Errore durante la creazione dell'asta", e);
        }
    }

    public List<AstasilenziosaModel> getAsteByVenditore(String venditore) {
        try {
            return astasilenziosaRepository.findByVenditoreIgnoreCase(venditore);
        } catch (Exception e) {
            // Restituisci una lista vuota o gestisci l'eccezione come preferisci
            return Collections.emptyList();
            // Oppure, se preferisci propagare l'errore:
            // throw new CustomException("Messaggio di errore", e);
        }
    }

    public List<AstasilenziosaModel> getAsteAcquirente(String email) {
        try {
            return astasilenziosaRepository.findByStatoTrue(email);
        } catch (Exception e) {
            // Restituisci una lista vuota o gestisci l'eccezione come preferisci
            return Collections.emptyList();
            // Oppure, se preferisci propagare l'errore:
            // throw new CustomException("Messaggio di errore", e);
        }
    }

    public List<AstasilenziosaModel> searchByKeywordAcquirente(String query, String ordinaper, String email) {
        try {

            return astasilenziosaRepository.searchByKeywordAcquirente(query, ordinaper, email);
        } catch (Exception e) {
            // Restituisci una lista vuota o gestisci l'eccezione come preferisci
            return Collections.emptyList();
            // Oppure, se preferisci propagare l'errore:
            // throw new CustomException("Messaggio di errore", e);
        }
    }

    public List<AstasilenziosaModel> searchByKeywordVenditore(String query, String ordinaper, String email) {
        try {

            return astasilenziosaRepository.searchByKeywordVenditore(query, ordinaper, email);
        } catch (Exception e) {
            // Restituisci una lista vuota o gestisci l'eccezione come preferisci
            return Collections.emptyList();
            // Oppure, se preferisci propagare l'errore:
            // throw new CustomException("Messaggio di errore", e);
        }
    }

    public void aggiornaStato() {
        // Recupera tutte le aste al ribasso dal database
        List<AstasilenziosaModel> aste = astasilenziosaRepository.findAll();

        // Itera su ciascuna asta e aggiorna il prezzo attuale
        for (AstasilenziosaModel asta : aste) {
            if (asta.isStato()){
                String dataFineAsta = asta.getDatafineasta(); // Assumo che getDataFineAsta() restituisca una stringa rappresentante la data in formato "yyyy-MM-dd"

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                LocalDate dataFineAstaLocalDate = LocalDate.parse(dataFineAsta, formatter);

// Ottieni la data di ieri
                LocalDate ieri = LocalDate.now().minusDays(1);

                if (dataFineAstaLocalDate.isEqual(ieri)) {
                    asta.setStato(false);
                    astasilenziosaRepository.save(asta);
                }
            }
        }
    }

}
