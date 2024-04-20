package com.example.serverdd24.Service;

import com.example.serverdd24.Model.AcquistaastaribassoModel;
import com.example.serverdd24.Model.AstaribassoModel;
import com.example.serverdd24.Repository.AcquistaastaribassoRepository;
import com.example.serverdd24.Repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@Service
public class AcquistaastaribassoService {

    @Autowired
    private AcquistaastaribassoRepository repository;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UtenteRepository utenteRepository;

    public int countByAstaId(Integer astaId) {
        return repository.countByAstaId(astaId);
    }

    public List<AcquistaastaribassoModel> getAcquistoById(Integer id) {
        try {
            return repository.findByIdastaribasso(id);
        } catch (Exception e) {
            // Restituisci una lista vuota o gestisci l'eccezione come preferisci
            return Collections.emptyList();
            // Oppure, se preferisci propagare l'errore:
            // throw new CustomException("Messaggio di errore", e);
        }
    }

    public List<AstaribassoModel> getAsteByEmailutente(String acquirente) {
        try {
            return repository.findByEmailutenteIgnoreCase(acquirente);
        } catch (Exception e) {
            // Restituisci una lista vuota o gestisci l'eccezione come preferisci
            return Collections.emptyList();
            // Oppure, se preferisci propagare l'errore:
            // throw new CustomException("Messaggio di errore", e);
        }
    }

    public void acquisto(AcquistaastaribassoModel acquistoDTO) {
        try{
            AcquistaastaribassoModel acquisto = new AcquistaastaribassoModel();

            // Imposta i campi dell'oggetto asta utilizzando i valori da astaDTO
            acquisto.setId(acquistoDTO.getId());
            acquisto.setemailutente(acquistoDTO.getemailutente());
            acquisto.setprezzoacquisto(acquistoDTO.getprezzoacquisto());

            // Salva l'oggetto asta nel database
            repository.save(acquisto);

            String token = utenteRepository.findTokenByAstaribassoId(acquisto.getId());
            notificationService.sendNotificationToSeller(token, "Asta conclusa", "La tua asta è stata aggiudicata!", 34);

        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Errore di integrità dei dati", e);
        } catch (Exception e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Errore durante la creazione dell'offerta", e);
        }
    }

}