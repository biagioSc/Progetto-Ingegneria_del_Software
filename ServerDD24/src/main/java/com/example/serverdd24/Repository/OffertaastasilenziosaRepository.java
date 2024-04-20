package com.example.serverdd24.Repository;

import com.example.serverdd24.Model.AstaribassoModel;
import com.example.serverdd24.Model.AstasilenziosaModel;
import com.example.serverdd24.Model.OffertaastasilenziosaModel;
import com.example.serverdd24.Model.UtenteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OffertaastasilenziosaRepository extends JpaRepository<OffertaastasilenziosaModel, Integer> {

    @Query("SELECT COUNT(a) FROM OffertaastasilenziosaModel a WHERE a.idastasilenziosa = :astaId and a.statoaccettazione = :stato")
    int countByAstaId(@Param("astaId") Integer astaId,
                      @Param("stato") String stato);

    List<OffertaastasilenziosaModel> findByIdastasilenziosaAndStatoaccettazione(Integer id, String stato);

    Optional<OffertaastasilenziosaModel> findByEmailutenteIgnoreCaseAndIdastasilenziosa(String email, Integer id);

    @Query("SELECT a FROM AstasilenziosaModel a JOIN OffertaastasilenziosaModel ac ON a.id = ac.idastasilenziosa WHERE LOWER(ac.emailutente) = LOWER(:acquirente)")
    List<AstasilenziosaModel> findByEmailutenteIgnoreCase(String acquirente);

    @Query("SELECT statoaccettazione FROM OffertaastasilenziosaModel WHERE LOWER(emailutente) = LOWER(:acquirente) and idastasilenziosa = :asta")
    String findStatoaccettazione(int asta, String acquirente);

    @Query("SELECT prezzoofferto FROM OffertaastasilenziosaModel WHERE LOWER(emailutente) = LOWER(:acquirente) and idastasilenziosa = :asta")
    double findPrezzoofferto(int asta, String acquirente);
}