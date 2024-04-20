package com.example.serverdd24.Service;

import com.example.serverdd24.DTO.OffertaastasilenziosaDTO;
import com.example.serverdd24.DTO.UtenteDTO;
import com.example.serverdd24.Model.AstasilenziosaModel;
import com.example.serverdd24.Model.OffertaastasilenziosaModel;
import com.example.serverdd24.Model.UtenteModel;
import com.example.serverdd24.Repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@Service
public class OffertaastasilenziosaService {

    @Autowired
    private com.example.serverdd24.Repository.OffertaastasilenziosaRepository repository;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UtenteRepository utenteRepository;

    public int countByAstaId(Integer astaId, String stato) {
        return repository.countByAstaId(astaId, stato);
    }

    public List<OffertaastasilenziosaModel> getOfferteById(Integer id) {
        try {
            return repository.findByIdastasilenziosaAndStatoaccettazione(id, "IN VALUTAZIONE");
        } catch (Exception e) {
            // Restituisci una lista vuota o gestisci l'eccezione come preferisci
            return Collections.emptyList();
            // Oppure, se preferisci propagare l'errore:
            // throw new CustomException("Messaggio di errore", e);
        }
    }

    public boolean updateOfferta(OffertaastasilenziosaDTO offertaDTO) {
        OffertaastasilenziosaModel offerta = repository.findByEmailutenteIgnoreCaseAndIdastasilenziosa(offertaDTO.getemailutente(), offertaDTO.getIdastasilenziosa())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato con l'email specificata"));

        // A questo punto, l'utente è stato trovato, quindi possiamo procedere con l'aggiornamento della password
        offerta.setstatoaccettazione(offertaDTO.getstatoaccettazione());
        repository.save(offerta);
        return true; // La password è stata aggiornata con successo
    }

    public List<AstasilenziosaModel> getAsteByEmailutente(String acquirente) {
        try {
            return repository.findByEmailutenteIgnoreCase(acquirente);
        } catch (Exception e) {
            // Restituisci una lista vuota o gestisci l'eccezione come preferisci
            return Collections.emptyList();
            // Oppure, se preferisci propagare l'errore:
            // throw new CustomException("Messaggio di errore", e);
        }
    }

    public String getStatoaccettazione(int idasta, String acquirente) {
        try {
            return repository.findStatoaccettazione(idasta, acquirente);
        } catch (Exception e) {
            // Restituisci una lista vuota o gestisci l'eccezione come preferisci
            return null;
            // Oppure, se preferisci propagare l'errore:
            // throw new CustomException("Messaggio di errore", e);
        }
    }

    public double getPrezzoofferto(int idasta, String acquirente) {
        try {
            return repository.findPrezzoofferto(idasta, acquirente);
        } catch (Exception e) {
            // Restituisci una lista vuota o gestisci l'eccezione come preferisci
            return -1;
            // Oppure, se preferisci propagare l'errore:
            // throw new CustomException("Messaggio di errore", e);
        }
    }

    public void creaOfferta(OffertaastasilenziosaDTO offertaDTO) {
        try{
            OffertaastasilenziosaModel offerta = new OffertaastasilenziosaModel();

            // Imposta i campi dell'oggetto asta utilizzando i valori da astaDTO
            offerta.setIdastasilenziosa(offertaDTO.getIdastasilenziosa());
            offerta.setemailutente(offertaDTO.getemailutente());
            offerta.setprezzoofferto(offertaDTO.getprezzoofferto());
            offerta.setstatoaccettazione(offertaDTO.getstatoaccettazione());

            // Salva l'oggetto asta nel database
            repository.save(offerta);

            String token = utenteRepository.findTokenByAstasilenziosaId(offerta.getIdastasilenziosa());
            notificationService.sendNotificationToSeller(token, "Nuova offerta", "Hai una nuova offerta per la tua asta!", 34);

        } catch (
            DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Errore di integrità dei dati", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Errore durante la creazione dell'offerta", e);
        }
    }
}

