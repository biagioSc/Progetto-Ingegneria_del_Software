package com.example.serverdd24.Service;

import com.example.serverdd24.DTO.AstasilenziosaseguiteDTO;
import com.example.serverdd24.Model.AstasilenziosaModel;
import com.example.serverdd24.Model.AstasilenziosaseguiteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AstasilenziosaseguiteService {

    @Autowired
    private com.example.serverdd24.Repository.AstasilenziosaseguiteRepository repository;

    public int countByAstaId(Integer astaId) {
        return repository.countByAstaId(astaId);
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

    public void segui(AstasilenziosaseguiteDTO segui) {
        try {
            // Controlla se l'oggetto è già presente nel repository
            Optional<AstasilenziosaseguiteModel> optionalSeguiAsta = repository.findByEmailutenteAndId(segui.getEmailutente(), segui.getidastasilenziosa());

            if (optionalSeguiAsta.isPresent()) {
                // Se l'oggetto è già presente, eliminilo
                repository.delete(optionalSeguiAsta.get());
            } else {
                // Se l'oggetto non è presente, creane uno nuovo
                AstasilenziosaseguiteModel seguiAsta = new AstasilenziosaseguiteModel();
                // Imposta i campi da utenteDTO
                seguiAsta.setidastasilenziosa(segui.getidastasilenziosa());
                seguiAsta.setEmailutente(segui.getEmailutente());

                repository.save(seguiAsta);
            }
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

    public boolean seguitaBool(String acquirente, Integer id) {
        try {
            if (repository.existsByEmailutenteIgnoreCaseAndIdastasilenziosa(acquirente, id)){
                return true;
            }
            return false;
        } catch (Exception e) {
            // Restituisci una lista vuota o gestisci l'eccezione come preferisci
            return false;
            // Oppure, se preferisci propagare l'errore:
            // throw new CustomException("Messaggio di errore", e);
        }
    }
}
