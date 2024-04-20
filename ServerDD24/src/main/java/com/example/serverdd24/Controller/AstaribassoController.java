package com.example.serverdd24.Controller;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.example.serverdd24.DTO.AstaribassoDTO;
import com.example.serverdd24.Model.AstaribassoModel;
import com.example.serverdd24.Service.AstaribassoService;
import com.example.serverdd24.Service.AstaribassoseguiteService;
import com.example.serverdd24.Service.StorageService;
import com.example.serverdd24.Utils.MessageResponse;
import com.example.serverdd24.Utils.MessageResponseRibasso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/astaribasso")
public class AstaribassoController {

    @Autowired
    private AstaribassoService astaribassoService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private AstaribassoseguiteService astaribassoseguiteService;

    @PostMapping("/crea")
    public ResponseEntity<Object> creaAstaribasso(@RequestBody AstaribassoDTO astaDTO) {
        try {
            astaribassoService.creaAsta(astaDTO);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new MessageResponse("Asta creata con successo"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Errore durante la creazione."));
        }

    }

    @GetMapping("/immagini/{fileName}")
    public ResponseEntity<byte[]> getImmagine(@PathVariable String fileName) {
        try {
            S3ObjectInputStream inputStream = storageService.downloadFile(fileName);
            byte[] content = IOUtils.toByteArray(inputStream);
            inputStream.close();
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(content);
        } catch (AmazonServiceException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/richiedi")
    public ResponseEntity<Object> richiediAstaRibasso(@RequestParam("parametro1") String venditore) {
        try {

            astaribassoService.aggiornaPrezziAttuali();

            List<AstaribassoModel> aste = astaribassoService.getAsteByVenditore(venditore);

            String baseUrl = "http://51.21.10.214:8080/api/astaribasso/immagini/";
            String urlToRemove = "https://dd24-s3.s3.eu-north-1.amazonaws.com/";

            for (AstaribassoModel asta : aste) {
                if (asta.getImmagineprodotto() != null) {
                    String fileName = asta.getImmagineprodotto().replace(urlToRemove, "");
                    String immagineUrl = baseUrl + fileName;
                    asta.setImmagineprodotto(immagineUrl);
                }

                // Ottieni e imposta il conteggio degli utenti che seguono l'asta
                int conteggioUtenti = astaribassoseguiteService.countByAstaId(asta.getId());
                asta.setConteggioUtenti(conteggioUtenti);
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

    @GetMapping("/richieditutte")
    public ResponseEntity<Object> richiediAstaRibassoAcquirente(@RequestParam("parametro1") String email) {
        try {
            astaribassoService.aggiornaPrezziAttuali();

            List<AstaribassoModel> aste = astaribassoService.getAsteAcquirente(email);

            System.out.println(aste.size());
            String baseUrl = "http://51.21.10.214:8080/api/astaribasso/immagini/";
            String urlToRemove = "https://dd24-s3.s3.eu-north-1.amazonaws.com/";

            for (AstaribassoModel asta : aste) {
                if (asta.getImmagineprodotto() != null) {
                    String fileName = asta.getImmagineprodotto().replace(urlToRemove, "");
                    String immagineUrl = baseUrl + fileName;
                    asta.setImmagineprodotto(immagineUrl);
                }

                // Ottieni e imposta il conteggio degli utenti che seguono l'asta
                int conteggioUtenti = astaribassoseguiteService.countByAstaId(asta.getId());
                asta.setConteggioUtenti(conteggioUtenti);
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

    @GetMapping("/searchAcquirente")
    public ResponseEntity<Object> searchAsteAcquirente(@RequestParam(required = false) String query,
                                                       @RequestParam(required = false) List<String> categoria,
                                                       @RequestParam(required = false) String ordinaper,
                                                       @RequestParam(required = false) Float startvalue,
                                                       @RequestParam(required = false) Float endvalue,
                                                       @RequestParam(required = false) String email){
        try {
            List<AstaribassoModel> asteRibasso = astaribassoService.searchByKeywordAcquirente(query, ordinaper, email);
            List<AstaribassoModel> asteRibassoToSend = new ArrayList<>();

            String baseUrl = "http://51.21.10.214:8080/api/astaribasso/immagini/";
            String urlToRemove = "https://dd24-s3.s3.eu-north-1.amazonaws.com/";

            for (AstaribassoModel asta : asteRibasso) {
                if(categoria == null || categoria.contains(asta.getCategoria())) {
                    if(asta.getUltimoprezzo()>=startvalue && asta.getUltimoprezzo()<=endvalue) {
                        asteRibassoToSend.add(asta);
                    }
                }
            }

            for (AstaribassoModel asta : asteRibassoToSend) {
                if (asta.getImmagineprodotto() != null) {
                    String fileName = asta.getImmagineprodotto().replace(urlToRemove, "");
                    String immagineUrl = baseUrl + fileName;
                    asta.setImmagineprodotto(immagineUrl);
                }

                int conteggioUtenti = astaribassoseguiteService.countByAstaId(asta.getId());
                asta.setConteggioUtenti(conteggioUtenti);
            }

            if (asteRibassoToSend.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(new MessageResponseRibasso(asteRibassoToSend));
        } catch (ResponseStatusException e) {
            // Usa getStatusCode() per ottenere lo status HTTP dall'eccezione ResponseStatusException.
            return ResponseEntity.status(e.getStatusCode()).body(new MessageResponse(e.getReason()));
        } catch (Exception e) {
            // Per qualsiasi altra eccezione non prevista, ritorna un errore generico del server.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Errore del server"));
        }
    }

    @GetMapping("/searchVenditore")
    public ResponseEntity<Object> searchAsteVenditore(@RequestParam(required = false) String query,
                                                      @RequestParam(required = false) List<String> categoria,
                                                      @RequestParam(required = false) String ordinaper,
                                                       @RequestParam(required = false) Float startvalue,
                                                       @RequestParam(required = false) Float endvalue,
                                                       @RequestParam(required = false) String email){
        try {
            List<AstaribassoModel> asteRibasso = astaribassoService.searchByKeywordVenditore(query, ordinaper, email);
            List<AstaribassoModel> asteRibassoToSend = new ArrayList<>();

            String baseUrl = "http://51.21.10.214:8080/api/astaribasso/immagini/";
            String urlToRemove = "https://dd24-s3.s3.eu-north-1.amazonaws.com/";

            for (AstaribassoModel asta : asteRibasso) {
                if(categoria == null || categoria.contains(asta.getCategoria())) {
                    if(asta.getUltimoprezzo()>=startvalue && asta.getUltimoprezzo()<=endvalue) {
                        asteRibassoToSend.add(asta);
                    }
                }
            }

            for (AstaribassoModel asta : asteRibassoToSend) {
                if (asta.getImmagineprodotto() != null) {
                    String fileName = asta.getImmagineprodotto().replace(urlToRemove, "");
                    String immagineUrl = baseUrl + fileName;
                    asta.setImmagineprodotto(immagineUrl);
                }

                // Ottieni e imposta il conteggio degli utenti che seguono l'asta
                int conteggioUtenti = astaribassoseguiteService.countByAstaId(asta.getId());
                asta.setConteggioUtenti(conteggioUtenti);
            }

            if (asteRibassoToSend.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(new MessageResponseRibasso(asteRibassoToSend));
        } catch (ResponseStatusException e) {
            // Usa getStatusCode() per ottenere lo status HTTP dall'eccezione ResponseStatusException.
            return ResponseEntity.status(e.getStatusCode()).body(new MessageResponse(e.getReason()));
        } catch (Exception e) {
            // Per qualsiasi altra eccezione non prevista, ritorna un errore generico del server.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Errore del server"));
        }
    }


}
