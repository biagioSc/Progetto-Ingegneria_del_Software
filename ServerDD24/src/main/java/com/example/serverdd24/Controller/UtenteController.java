package com.example.serverdd24.Controller;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.example.serverdd24.DTO.UtenteDTO;
import com.example.serverdd24.Model.UtenteModel;
import com.example.serverdd24.Service.StorageService;
import com.example.serverdd24.Service.UtenteService;
import com.example.serverdd24.Utils.MessageResponse;
import com.example.serverdd24.Utils.MessageResponseUtente;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;


@RestController
@RequestMapping("/api/utente")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private StorageService storageService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UtenteDTO loginDto) {
        try {
            // Il metodo validateLogin ora lancia un'eccezione ResponseStatusException se qualcosa va storto.
            utenteService.validateLogin(loginDto.getEmail(), loginDto.getPassword());
            // Se nessuna eccezione è lanciata, il login è riuscito.
            return ResponseEntity.ok().body(new MessageResponse("Login riuscito"));
        } catch (ResponseStatusException e) {
            // Usa getStatusCode() per ottenere lo status HTTP dall'eccezione ResponseStatusException.
            return ResponseEntity.status(e.getStatusCode()).body(new MessageResponse(e.getReason()));
        } catch (Exception e) {
            // Per qualsiasi altra eccezione non prevista, ritorna un errore generico del server.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Errore del server"));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody UtenteDTO signupDTO) {
        try {

            // Processa la registrazione dell'utente e dei link social
            utenteService.registerUser(signupDTO);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new MessageResponse("Registrazione completata con successo"));
        } catch (ResponseStatusException e) {
            // Cattura e utilizza l'eccezione specifica per restituire il codice di stato e il messaggio corretti
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(new MessageResponse(e.getReason()));
        } catch (Exception e) {
            // Cattura qualsiasi altra eccezione non gestita specificamente e restituisce un errore generico
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Errore durante la registrazione: " + e.getMessage()));
        }
    }

    @PostMapping("/recuperopin")
    public ResponseEntity<Object> recuperopin(@RequestBody UtenteDTO emailDTO) {
        try {
            boolean success = utenteService.inviaCodiceRecupero(emailDTO.getEmail());
            // Considerando che inviaCodiceRecupero ora lancia un'eccezione se non riesce,
            // il controllo success può essere rimosso o mantenuto per coerenza logica.
            if (success) {
                return ResponseEntity.ok().body(new MessageResponse("Codice di recupero inviato a " + emailDTO.getEmail()));
            } else {
                // Se il metodo arriva qui, potrebbe significare che l'email esiste ma c'è stato un problema con l'invio del PIN.
                // Tuttavia, con la gestione delle eccezioni, questo ramo potrebbe non essere mai raggiunto.
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Impossibile inviare il codice di recupero, riprova più tardi"));
            }
        } catch (ResponseStatusException e) {
            // Usa lo status dell'eccezione per determinare la risposta
            return ResponseEntity.status(e.getStatusCode()).body(new MessageResponse(e.getReason()));
        } catch (Exception e) {
            // Per qualsiasi altra eccezione non gestita specificatamente
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Errore sconosciuto: " + e.getMessage()));
        }
    }

    @PostMapping("/validatepin")
    public ResponseEntity<Object> validatepin(@RequestBody UtenteDTO validapinDto) {
        try {
            // Il metodo validateLogin ora lancia un'eccezione ResponseStatusException se qualcosa va storto.
            utenteService.validatePin(validapinDto.getEmail(), validapinDto.getpin());
            // Se nessuna eccezione è lanciata, il login è riuscito.
            return ResponseEntity.ok().body(new MessageResponse("Pin corretto"));
        } catch (ResponseStatusException e) {
            // Usa getStatusCode() per ottenere lo status HTTP dall'eccezione ResponseStatusException.
            return ResponseEntity.status(e.getStatusCode()).body(new MessageResponse(e.getReason()));
        } catch (Exception e) {
            // Per qualsiasi altra eccezione non prevista, ritorna un errore generico del server.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Errore del server"));
        }
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<Object> updatePassword(@RequestBody UtenteDTO utenteDTO) {
        try {
            utenteService.updatePasswordByEmail(utenteDTO);
            return ResponseEntity.ok().body(new MessageResponse("Password aggiornata con successo"));
        } catch (ResponseStatusException e) {
            // Qui catturiamo l'eccezione specifica per utente non trovato
            return ResponseEntity.status(e.getStatusCode()).body(new MessageResponse(e.getReason()));
        } catch (Exception e) {
            // Cattura altre possibili eccezioni non previste
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Errore durante l'aggiornamento della password"));
        }
    }

    @PutMapping("/updateProfilo")
    public ResponseEntity<Object> updateProfilo(@RequestBody UtenteDTO utenteDTO) {
        try {
            utenteService.updateProfiloByEmail(utenteDTO);
            return ResponseEntity.ok().body(new MessageResponse("Profilo aggiornato con successo"));
        } catch (ResponseStatusException e) {
            // Qui catturiamo l'eccezione specifica per utente non trovato
            return ResponseEntity.status(e.getStatusCode()).body(new MessageResponse(e.getReason()));
        } catch (Exception e) {
            // Cattura altre possibili eccezioni non previste
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Errore durante l'aggiornamento del profilo."));
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

    @GetMapping("/datiutente")
    public ResponseEntity<Object> datiUtente(@RequestParam("parametro1") String email) {
        try {
            UtenteModel utente = utenteService.trovaUtente(email);

            String baseUrl = "http://51.21.10.214:8080/api/utente/immagini/";
            String urlToRemove = "https://dd24-s3.s3.eu-north-1.amazonaws.com/";
            String fileName = utente.getImmagine();
            String immagineUrl = baseUrl + fileName.replace(urlToRemove, "");

            utente.setImmagine(immagineUrl);

            return ResponseEntity.ok().body(new MessageResponseUtente(utente));
        } catch (ResponseStatusException e) {
            // Qui catturiamo l'eccezione specifica per utente non trovato
            return ResponseEntity.status(e.getStatusCode()).body(new MessageResponse(e.getReason()));
        } catch (Exception e) {
            // Cattura altre possibili eccezioni non previste
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Errore durante il recupero dati!"));
        }
    }

}
