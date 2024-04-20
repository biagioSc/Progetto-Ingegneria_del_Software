package com.example.serverdd24.Service;

import com.example.serverdd24.Controller.UtenteController;
import com.example.serverdd24.DTO.UtenteDTO;
import com.example.serverdd24.Model.UtenteModel;
import com.example.serverdd24.Repository.UtenteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private StorageService storageService;
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private JavaMailSender mailSender;

    public boolean validateLogin(String email, String password) {
        UtenteModel utenteModel = utenteRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato per l'email fornita."));

        if (!utenteModel.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenziali non valide.");
        }
        return true;
    }

    public void registerUser(UtenteDTO utenteDTO) {
        try {
            if (utenteRepository.findByEmailIgnoreCase(utenteDTO.getEmail()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email già in uso");
            }

            UtenteModel utente = new UtenteModel();
            // Imposta i campi da utenteDTO
            utente.setEmail(utenteDTO.getEmail());
            utente.setPassword(utenteDTO.getPassword());
            utente.setNome(utenteDTO.getNome());
            utente.setCognome(utenteDTO.getCognome());
            utente.setBiografia(utenteDTO.getBiografia());
            utente.setNazionalita(utenteDTO.getNazionalita());
            utente.setCitta(utenteDTO.getCitta());
            utente.setLinkweb(utenteDTO.getLinkweb());
            utente.setLinksocial(utenteDTO.getLinksocial());
            utente.setToken(utenteDTO.getToken());

            //funzione salvataggio s3
            String base64Image = utenteDTO.getImmagine();
            String contentType = "image/jpeg"; // Assicurati di avere un modo per ottenere il contentType corretto

            if(base64Image!=null) {
                String imageUrl = storageService.uploadBase64Image(utenteDTO.getEmail() + "_" + System.currentTimeMillis() + ".jpg", base64Image, contentType);
                utente.setImmagine(imageUrl);
            }else {
                utente.setImmagine(null);
            }

            // Genera e imposta un PIN unico
            Integer pinUnico = generaPinUnico();
            utente.setpin(pinUnico);
            Timestamp expirationTimestamp = generaTimestamp();
            utente.setTempopin(expirationTimestamp);

            utenteRepository.save(utente);

            notificationService.sendNotificationToSeller(utente.getToken(), "Registrato a DD24", "Benventuo in DD24, crea ora la tua prima asta!", 1);

            SimpleMailMessage messaggio = new SimpleMailMessage();
            messaggio.setFrom("noreply.dd24@gmail.com"); // Sostituisci con la tua email
            messaggio.setTo(utente.getEmail());
            messaggio.setSubject("Registrazione DD24");
            messaggio.setText("Registrazione completata. Il tuo account è attivo, crea le tue aste o partecipa a quelle già presenti!");

            mailSender.send(messaggio);

        } catch (ResponseStatusException e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email già in uso");
        } catch (DataIntegrityViolationException e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Errore di integrità dei dati", e);
        } catch (Exception e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Errore durante la registrazione dell'utente", e);
        }
    }

    private Timestamp generaTimestamp() {
        long expirationTimeMillis = System.currentTimeMillis() + (60 * 1000);
        Timestamp expirationTimestamp = new Timestamp(expirationTimeMillis);
        return expirationTimestamp;
    }

    public int generaPinUnico() {
        Random random = new Random();
        Integer pin;
        do {
            pin = 10000 + random.nextInt(90000); // Genera un numero tra 10000 e 99999
        } while (utenteRepository.existsBypin(pin));
        return pin;
    }

    public boolean inviaCodiceRecupero(String email) {
        Optional<UtenteModel> utenteModelOptional = utenteRepository.findByEmailIgnoreCase(email);

        if (!utenteModelOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato");
        }

        UtenteModel utente = utenteModelOptional.get(); // Estrai l'oggetto UtenteModel dall'Optional

        try {
            Integer pinUnico = generaPinUnico();
            utente.setpin(pinUnico);
            Timestamp expirationTimestamp = generaTimestamp();
            utente.setTempopin(expirationTimestamp);
            utenteRepository.save(utente);

            SimpleMailMessage messaggio = new SimpleMailMessage();
            messaggio.setFrom("noreply.dd24@gmail.com"); // Sostituisci con la tua email
            messaggio.setTo(email);
            messaggio.setSubject("Codice di Recupero");
            messaggio.setText("Il tuo codice di recupero è: " + pinUnico + ". Il codice ha una validità di 60 secondi!");

            mailSender.send(messaggio);
            return true;
        } catch (DataAccessException dae) {
            // Gestione eccezioni relative all'accesso ai dati
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Servizio di generazione codice non disponibile", dae);
        } catch (MailException me) {
            // Gestione eccezioni relative all'invio della mail
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Impossibile inviare l'email", me);
        } catch (Exception e) {
            // Gestione di qualsiasi altra eccezione non prevista
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Errore interno del server", e);
        }
    }

    public Integer inviaCodiceRecuperoTest(String email) {
        Optional<UtenteModel> utenteModelOptional = utenteRepository.findByEmailIgnoreCase(email);

        if (!utenteModelOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato");
        }

        UtenteModel utente = utenteModelOptional.get(); // Estrai l'oggetto UtenteModel dall'Optional

        try {
            Integer pinUnico = generaPinUnico();
            utente.setpin(pinUnico);
            Timestamp expirationTimestamp = generaTimestamp();
            utente.setTempopin(expirationTimestamp);
            utenteRepository.save(utente);

            System.out.println(pinUnico);
            System.out.println(expirationTimestamp);

            return pinUnico;
        } catch (DataAccessException dae) {
            // Gestione eccezioni relative all'accesso ai dati
            return 0;
        } catch (MailException me) {
            // Gestione eccezioni relative all'invio della mail
            return 0;
        } catch (Exception e) {
            // Gestione di qualsiasi altra eccezione non prevista
            return 0;
        }
    }

    public boolean updatePasswordByEmail(UtenteDTO utenteDTO) {
        UtenteModel utente = utenteRepository.findByEmailIgnoreCase(utenteDTO.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato con l'email specificata"));

        // A questo punto, l'utente è stato trovato, quindi possiamo procedere con l'aggiornamento della password
        utente.setPassword(utenteDTO.getPassword());
        utenteRepository.save(utente);
        return true; // La password è stata aggiornata con successo
    }

    public boolean updateProfiloByEmail(UtenteDTO utenteDTO) {
        UtenteModel utente = utenteRepository.findByEmailIgnoreCase(utenteDTO.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato con l'email specificata"));

        // A questo punto, l'utente è stato trovato, quindi possiamo procedere con l'aggiornamento della password

        utente.setEmail(utenteDTO.getEmail());
        utente.setPassword(utenteDTO.getPassword());
        utente.setNome(utenteDTO.getNome());
        utente.setCognome(utenteDTO.getCognome());
        utente.setBiografia(utenteDTO.getBiografia());
        utente.setNazionalita(utenteDTO.getNazionalita());
        utente.setCitta(utenteDTO.getCitta());
        utente.setLinkweb(utenteDTO.getLinkweb());
        utente.setLinksocial(utenteDTO.getLinksocial());

        //funzione salvataggio s3
        String base64Image = utenteDTO.getImmagine();
        String contentType = "image/jpeg"; // Assicurati di avere un modo per ottenere il contentType corretto
        String imageUrl;

        if(base64Image!=null) {
            if (utente.getImmagine() != null && !utente.getImmagine().isEmpty()) {
                storageService.deleteFile(utente.getImmagine());
            }
            imageUrl = storageService.uploadBase64Image(utenteDTO.getEmail() + "_" + System.currentTimeMillis() + ".jpg", base64Image, contentType);
            utente.setImmagine(imageUrl);
        }

        // Genera e imposta un PIN unico
        Integer pinUnico = generaPinUnico();
        utente.setpin(pinUnico);
        Timestamp expirationTimestamp = generaTimestamp();
        utente.setTempopin(expirationTimestamp);

        utenteRepository.save(utente);
        return true; // La password è stata aggiornata con successo
    }

    public boolean validatePin(String email, Integer pin) {

        UtenteModel utenteModel = utenteRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato per l'email fornita."));

        // Calcola il timestamp corrente
        Timestamp oraCorrente = new Timestamp(System.currentTimeMillis());

        // Calcola il timestamp 60 secondi prima dell'ora corrente
        Timestamp limiteScadenza = new Timestamp(oraCorrente.getTime() - 60000); // 60000 millisecondi = 60 secondi

        if (!utenteModel.getpin().equals(pin)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Pin non valido.");
        }else if(utenteModel.getpin().equals(pin) && utenteModel.getTempopin().before(limiteScadenza)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Pin scaduto.");
        }
        return true;

    }

    public boolean validatePinTest(String email, Integer pin, Timestamp oraCorrente) {

        UtenteModel utenteModel = utenteRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato per l'email fornita."));

        Timestamp limiteScadenza = new Timestamp(oraCorrente.getTime() - 60000); // 60000 millisecondi = 60 secondi

        if (!utenteModel.getpin().equals(pin)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Pin non valido.");
        }else if(utenteModel.getpin().equals(pin) && utenteModel.getTempopin().before(limiteScadenza)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Pin scaduto.");
        }
        return true;

    }

    public UtenteModel trovaUtente(String email) {
        Optional<UtenteModel> utenteModelOptional = utenteRepository.findByEmailIgnoreCase(email);

        if (!utenteModelOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato");
        }

        UtenteModel utente = utenteModelOptional.get(); // Estrai l'oggetto UtenteModel dall'Optional

        return utente;
    }
}