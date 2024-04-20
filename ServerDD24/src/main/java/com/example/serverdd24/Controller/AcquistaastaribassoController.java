package com.example.serverdd24.Controller;

import com.example.serverdd24.Model.AcquistaastaribassoModel;
import com.example.serverdd24.Model.AstaribassoModel;
import com.example.serverdd24.Service.AcquistaastaribassoService;
import com.example.serverdd24.Utils.MessageResponse;
import com.example.serverdd24.Utils.MessageResponseRibasso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/asteribassoacquisto")
public class AcquistaastaribassoController {

    @Autowired
    private AcquistaastaribassoService AcquistaastaribassoService;

    @GetMapping("/richiedi")
    public ResponseEntity<Object> richiediAstaRibasso(@RequestParam("parametro1") String acquirente) {
        try {
            List<AstaribassoModel> aste = AcquistaastaribassoService.getAsteByEmailutente(acquirente);

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

    @PostMapping("/acquisto")
    public ResponseEntity<Object> acquisto(@RequestBody AcquistaastaribassoModel offertaDTO) {
        try {
            AcquistaastaribassoService.acquisto(offertaDTO);
            return ResponseEntity.ok().body(new MessageResponse("Acquisto avvenuto con successo"));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new MessageResponse(e.getReason()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Errore durante l'acquisto.."));
        }
    }

}