package com.example.serverdd24.Controller;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.example.serverdd24.DTO.AstasilenziosaDTO;
import com.example.serverdd24.Model.AstasilenziosaModel;
import com.example.serverdd24.Service.*;
import com.example.serverdd24.Utils.MessageResponse;
import com.example.serverdd24.Utils.MessageResponseSilenziosa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/astasilenziosa")
public class AstasilenziosaController {

    @Autowired
    private AstasilenziosaService astasilenziosaService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private AstasilenziosaseguiteService astasilenziosaseguiteService;

    @Autowired
    private OffertaastasilenziosaService offertaastasilenziosaService;


    @PostMapping("/crea")
    public ResponseEntity<Object> creaAstasilenziosa(@RequestBody AstasilenziosaDTO astaDTO) {
        try {
            astasilenziosaService.creaAsta(astaDTO);
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
    public ResponseEntity<Object> richiediAstasilenziosa(@RequestParam("parametro1") String venditore) {
        try {
            astasilenziosaService.aggiornaStato();

            List<AstasilenziosaModel> aste = astasilenziosaService.getAsteByVenditore(venditore);

            String baseUrl = "http://51.21.10.214:8080/api/astasilenziosa/immagini/";
            String urlToRemove = "https://dd24-s3.s3.eu-north-1.amazonaws.com/";

            for (AstasilenziosaModel asta : aste) {
                if (asta.getImmagineprodotto() != null) {
                    String fileName = asta.getImmagineprodotto().replace(urlToRemove, "");
                    String immagineUrl = baseUrl + fileName;
                    asta.setImmagineprodotto(immagineUrl);
                }

                // Ottieni e imposta il conteggio degli utenti che seguono l'asta
                int conteggioUtenti = astasilenziosaseguiteService.countByAstaId(asta.getId());
                asta.setConteggioUtenti(conteggioUtenti);

                int conteggioOfferte = offertaastasilenziosaService.countByAstaId(asta.getId(), "IN VALUTAZIONE");
                asta.setConteggioOfferte(conteggioOfferte);
            }

            if (aste.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(new MessageResponseSilenziosa(aste));
        } catch (ResponseStatusException e) {
            // Usa getStatusCode() per ottenere lo status HTTP dall'eccezione ResponseStatusException.
            return ResponseEntity.status(e.getStatusCode()).body(new MessageResponse(e.getReason()));
        } catch (Exception e) {
            System.out.println(e);
            // Per qualsiasi altra eccezione non prevista, ritorna un errore generico del server.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Errore del server"));
        }
    }

    @GetMapping("/richieditutte")
    public ResponseEntity<Object> richiediAstasilenziosaAcquirente(@RequestParam("parametro1") String email) {
        try {
            astasilenziosaService.aggiornaStato();

            List<AstasilenziosaModel> aste = astasilenziosaService.getAsteAcquirente(email);

            String baseUrl = "http://51.21.10.214:8080/api/astasilenziosa/immagini/";
            String urlToRemove = "https://dd24-s3.s3.eu-north-1.amazonaws.com/";

            for (AstasilenziosaModel asta : aste) {
                if (asta.getImmagineprodotto() != null) {
                    String fileName = asta.getImmagineprodotto().replace(urlToRemove, "");
                    String immagineUrl = baseUrl + fileName;
                    asta.setImmagineprodotto(immagineUrl);
                }

            }

            if (aste.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(new MessageResponseSilenziosa(aste));
        } catch (ResponseStatusException e) {
            // Usa getStatusCode() per ottenere lo status HTTP dall'eccezione ResponseStatusException.
            return ResponseEntity.status(e.getStatusCode()).body(new MessageResponse(e.getReason()));
        } catch (Exception e) {
            System.out.println(e);
            // Per qualsiasi altra eccezione non prevista, ritorna un errore generico del server.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Errore del server"));
        }
    }

    @GetMapping("/searchAcquirente")
    public ResponseEntity<Object> searchAsteAcquirente(@RequestParam(required = false) String query,
                                             @RequestParam(required = false) String data,
                                             @RequestParam(required = false) List<String> categoria,
                                             @RequestParam(required = false) String ordinaper,
                                             @RequestParam(required = false) String email){
        try {
               String baseUrlSilenziosa = "http://51.21.10.214:8080/api/astasilenziosa/immagini/";
            String urlToRemove = "https://dd24-s3.s3.eu-north-1.amazonaws.com/";

            List<AstasilenziosaModel> asteSilenziosa = astasilenziosaService.searchByKeywordAcquirente(query, ordinaper, email);
            List<AstasilenziosaModel> asteSilenziosaToSend = new ArrayList<>();

            for (AstasilenziosaModel asta : asteSilenziosa) {
                if(categoria == null || categoria.contains(asta.getCategoria())) {
                    if(data!=null && !data.isEmpty()){
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                        LocalDate date1 = LocalDate.parse(data, formatter);
                        LocalDate date2 = LocalDate.parse(asta.getDatafineasta(), formatter);

                        if (date1.isBefore(date2)) {
                            // date1 è precedente a date2
                        } else if (date1.isAfter(date2)) {
                            asteSilenziosaToSend.add(asta);
                        } else {
                            asteSilenziosaToSend.add(asta);
                        }
                    }else{
                        asteSilenziosaToSend.add(asta);
                    }
                }
            }

            for (AstasilenziosaModel asta : asteSilenziosaToSend) {
                if (asta.getImmagineprodotto() != null) {
                    String fileName = asta.getImmagineprodotto().replace(urlToRemove, "");
                    String immagineUrl = baseUrlSilenziosa + fileName;
                    asta.setImmagineprodotto(immagineUrl);
                }

                // Ottieni e imposta il conteggio degli utenti che seguono l'asta
                int conteggioUtenti = astasilenziosaseguiteService.countByAstaId(asta.getId());
                asta.setConteggioUtenti(conteggioUtenti);

                int conteggioOfferte = offertaastasilenziosaService.countByAstaId(asta.getId(), "IN VALUTAZIONE");
                asta.setConteggioOfferte(conteggioOfferte);
            }

            if (asteSilenziosaToSend.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(new MessageResponseSilenziosa(asteSilenziosaToSend));
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
                                             @RequestParam(required = false) String data,
                                             @RequestParam(required = false) List<String> categoria,
                                             @RequestParam(required = false) String ordinaper,
                                             @RequestParam(required = false) String email){
        try {
            String baseUrlSilenziosa = "http://51.21.10.214:8080/api/astasilenziosa/immagini/";
            String urlToRemove = "https://dd24-s3.s3.eu-north-1.amazonaws.com/";

            List<AstasilenziosaModel> asteSilenziosa = astasilenziosaService.searchByKeywordVenditore(query, ordinaper, email);
            List<AstasilenziosaModel> asteSilenziosaToSend = new ArrayList<>();

            for (AstasilenziosaModel asta : asteSilenziosa) {
                if(categoria == null || categoria.contains(asta.getCategoria())) {
                    if(data!=null && !data.isEmpty()){
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                        LocalDate date1 = LocalDate.parse(data, formatter);
                        LocalDate date2 = LocalDate.parse(asta.getDatafineasta(), formatter);

                        if (date1.isBefore(date2)) {
                            // date1 è precedente a date2
                        } else if (date1.isAfter(date2)) {
                            asteSilenziosaToSend.add(asta);
                        } else {
                            asteSilenziosaToSend.add(asta);
                        }
                    }else{
                        asteSilenziosaToSend.add(asta);
                    }
                }
            }

            for (AstasilenziosaModel asta : asteSilenziosaToSend) {
                if (asta.getImmagineprodotto() != null) {
                    String fileName = asta.getImmagineprodotto().replace(urlToRemove, "");
                    String immagineUrl = baseUrlSilenziosa + fileName;
                    asta.setImmagineprodotto(immagineUrl);
                }

                // Ottieni e imposta il conteggio degli utenti che seguono l'asta
                int conteggioUtenti = astasilenziosaseguiteService.countByAstaId(asta.getId());
                asta.setConteggioUtenti(conteggioUtenti);

                int conteggioOfferte = offertaastasilenziosaService.countByAstaId(asta.getId(), "IN VALUTAZIONE");
                asta.setConteggioOfferte(conteggioOfferte);
            }

            if (asteSilenziosaToSend.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(new MessageResponseSilenziosa(asteSilenziosa));
        } catch (ResponseStatusException e) {
            // Usa getStatusCode() per ottenere lo status HTTP dall'eccezione ResponseStatusException.
            return ResponseEntity.status(e.getStatusCode()).body(new MessageResponse(e.getReason()));
        } catch (Exception e) {
            // Per qualsiasi altra eccezione non prevista, ritorna un errore generico del server.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Errore del server"));
        }
    }

}
