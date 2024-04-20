package com.example.serverdd24.Controller;

import com.example.serverdd24.DTO.AstaribassoseguiteDTO;
import com.example.serverdd24.Model.AstaribassoModel;
import com.example.serverdd24.Service.AstaribassoseguiteService;
import com.example.serverdd24.Utils.MessageResponse;
import com.example.serverdd24.Utils.MessageResponseRibasso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/asteribassoseguite")
public class AstaribassoseguiteController {

    @Autowired
    private AstaribassoseguiteService service;

    @GetMapping("/richiedi")
    public ResponseEntity<Object> richiediAstaRibasso(@RequestParam("parametro1") String acquirente) {
        try {
            List<AstaribassoModel> aste = service.getAsteByEmailutente(acquirente);

            String baseUrl = "http://51.21.10.214:8080/api/astaribasso/immagini/";
            String urlToRemove = "https://dd24-s3.s3.eu-north-1.amazonaws.com/";

            for (AstaribassoModel asta : aste) {
                if (asta.getImmagineprodotto() != null) {
                    String fileName = asta.getImmagineprodotto().replace(urlToRemove, "");
                    String immagineUrl = baseUrl + fileName;
                    asta.setImmagineprodotto(immagineUrl);
                }
            }

            if (aste.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(new MessageResponseRibasso(aste));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new MessageResponse(e.getReason()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Errore del server"));
        }
    }

    @PostMapping("/segui")
    public ResponseEntity<Object> segui(@RequestBody AstaribassoseguiteDTO segui) {
        try {
            System.out.println(segui.getEmailutente());
            System.out.println(segui.getidastaribasso());

            service.segui(segui);
            return ResponseEntity.ok().body(new MessageResponse("Offerta creata con successo"));
        } catch (ResponseStatusException e) {

            // Qui catturiamo l'eccezione specifica per utente non trovato
            return ResponseEntity.status(e.getStatusCode()).body(new MessageResponse(e.getReason()));
        } catch (Exception e) {
            // Cattura altre possibili eccezioni non previste
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
