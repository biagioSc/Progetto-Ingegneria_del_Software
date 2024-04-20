package com.example.serverdd24.Controller;

import com.example.serverdd24.DTO.AstasilenziosaseguiteDTO;
import com.example.serverdd24.Model.AstasilenziosaModel;
import com.example.serverdd24.Service.AstasilenziosaseguiteService;
import com.example.serverdd24.Utils.MessageResponse;
import com.example.serverdd24.Utils.MessageResponseSilenziosa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/astesilenziosaseguite")
public class AstasilenziosaseguiteController {

    @Autowired
    private AstasilenziosaseguiteService service;

    @GetMapping("/richiedi")
    public ResponseEntity<Object> richiediAstaSilenziosa(@RequestParam("parametro1") String acquirente) {
        try {
            List<AstasilenziosaModel> aste = service.getAsteByEmailutente(acquirente);

            String baseUrl = "http://51.21.10.214:8080/api/astasilenziosa/immagini/";
            String urlToRemove = "https://dd24-s3.s3.eu-north-1.amazonaws.com/";

            for (AstasilenziosaModel asta : aste) {
                if (asta.getImmagineprodotto() != null) {
                    String fileName = asta.getImmagineprodotto().replace(urlToRemove, "");
                    String immagineUrl = baseUrl + fileName;
                    asta.setImmagineprodotto(immagineUrl);
                }
            }

            for (AstasilenziosaModel asta : aste) {
                String statoaccettazione = service.getStatoaccettazione(asta.getId(), acquirente);
                asta.setstatoaccettazione(statoaccettazione);
                double prezzoofferto = service.getPrezzoofferto(asta.getId(), acquirente);
                asta.setPrezzoofferto(prezzoofferto);
            }

            if (aste.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(new MessageResponseSilenziosa(aste));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new MessageResponse(e.getReason()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Errore del server"));
        }
    }

    @PostMapping("/segui")
    public ResponseEntity<Object> creaOfferta(@RequestBody AstasilenziosaseguiteDTO segui) {
        try {
            service.segui(segui);
            return ResponseEntity.ok().body(new MessageResponse("Offerta creata con successo"));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new MessageResponse(e.getReason()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Errore durante la creazione dell'offerta."));
        }
    }

    @GetMapping("/seguitabool")
    public ResponseEntity<Object> seguitaBool(@RequestParam("parametro1") Integer id, @RequestParam("parametro2") String acquirente){
        try {
            boolean value = service.seguitaBool(acquirente, id);
            return ResponseEntity.ok().body(new MessageResponse(String.valueOf(value)));
        } catch (ResponseStatusException e) {

            // Qui catturiamo l'eccezione specifica per utente non trovato
            return ResponseEntity.status(e.getStatusCode()).body(new MessageResponse(e.getReason()));
        } catch (Exception e) {
            // Cattura altre possibili eccezioni non previste
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Errore durante la creazione dell'offerta."));
        }
    }
}