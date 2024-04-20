package com.example.serverdd24.Controller;

import com.example.serverdd24.DTO.OffertaastasilenziosaDTO;
import com.example.serverdd24.Model.AstasilenziosaModel;
import com.example.serverdd24.Model.OffertaastasilenziosaModel;
import com.example.serverdd24.Service.OffertaastasilenziosaService;
import com.example.serverdd24.Utils.MessageResponse;
import com.example.serverdd24.Utils.MessageResponseOfferte;
import com.example.serverdd24.Utils.MessageResponseSilenziosa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/astesilenziosaofferta")
public class OffertaastasilenziosaController {

    @Autowired
    private OffertaastasilenziosaService offertaastasilenziosaService;

    @GetMapping("/richiediofferte")
    public ResponseEntity<Object> richiediOfferte(@RequestParam("parametro1") Integer id) {
        try {
            List<OffertaastasilenziosaModel> aste = offertaastasilenziosaService.getOfferteById(id);

            if (aste.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(new MessageResponseOfferte(aste));
        } catch (ResponseStatusException e) {
            // Usa getStatusCode() per ottenere lo status HTTP dall'eccezione ResponseStatusException.
            return ResponseEntity.status(e.getStatusCode()).body(new MessageResponse(e.getReason()));
        } catch (Exception e) {
            // Per qualsiasi altra eccezione non prevista, ritorna un errore generico del server.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Errore del server"));
        }
    }

    @PutMapping("/updateOfferta")
    public ResponseEntity<Object> updateOfferta(@RequestBody OffertaastasilenziosaDTO offertaDTO) {
        try {
            offertaastasilenziosaService.updateOfferta(offertaDTO);
            return ResponseEntity.ok().body(new MessageResponse("Stato offerta aggiornata con successo"));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new MessageResponse(e.getReason()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Errore durante l'aggiornamento dello stato."));
        }
    }

    @GetMapping("/richiedi")
    public ResponseEntity<Object> richiediAstaSilenziosa(@RequestParam("parametro1") String acquirente) {
        try {
            List<AstasilenziosaModel> aste = offertaastasilenziosaService.getAsteByEmailutente(acquirente);

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
                String statoaccettazione = offertaastasilenziosaService.getStatoaccettazione(asta.getId(), acquirente);
                asta.setstatoaccettazione(statoaccettazione);
                double prezzoofferto = offertaastasilenziosaService.getPrezzoofferto(asta.getId(), acquirente);
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

    @PostMapping("/creaOfferta")
    public ResponseEntity<Object> creaOfferta(@RequestBody OffertaastasilenziosaDTO offertaDTO) {
        try {
            offertaastasilenziosaService.creaOfferta(offertaDTO);
            return ResponseEntity.ok().body(new MessageResponse("Offerta creata con successo"));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new MessageResponse(e.getReason()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Errore durante la creazione dell'offerta."));
        }
    }

}