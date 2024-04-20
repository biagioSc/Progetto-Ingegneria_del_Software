package com.example.serverdd24.Service;

import com.example.serverdd24.DTO.AstaribassoseguiteDTO;
import com.example.serverdd24.Model.AstaribassoModel;
import com.example.serverdd24.Model.AstaribassoseguiteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AstaribassoseguiteService {

    @Autowired
    private com.example.serverdd24.Repository.AstaribassoseguiteRepository repository;

    public int countByAstaId(Integer astaId) {
        return repository.countByAstaId(astaId);
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

    public void segui(AstaribassoseguiteDTO segui) {
        try {
            // Controlla se l'oggetto è già presente nel repository
            Optional<AstaribassoseguiteModel> optionalSeguiAsta = repository.findByEmailutenteAndId(segui.getEmailutente(), segui.getidastaribasso());

            if (optionalSeguiAsta.isPresent()) {
                // Se l'oggetto è già presente, eliminilo
                repository.delete(optionalSeguiAsta.get());
            } else {
                // Se l'oggetto non è presente, creane uno nuovo
                AstaribassoseguiteModel seguiAsta = new AstaribassoseguiteModel();
                // Imposta i campi da utenteDTO
                seguiAsta.setidastaribasso(segui.getidastaribasso());
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

    public boolean seguitaBool(String acquirente, Integer id) {
        try {
            if (repository.existsByEmailutenteIgnoreCaseAndIdastaribasso(acquirente, id)){
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
