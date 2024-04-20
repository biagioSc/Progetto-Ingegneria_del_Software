package com.example.serverdd24.Repository;

import com.example.serverdd24.Model.AstaribassoModel;
import com.example.serverdd24.Model.AstaribassoseguiteModel;
import com.example.serverdd24.Model.AstasilenziosaModel;
import com.example.serverdd24.Model.AstasilenziosaseguiteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AstasilenziosaseguiteRepository extends JpaRepository<AstasilenziosaseguiteModel, Integer> {

    @Query("SELECT COUNT(a) FROM AstasilenziosaseguiteModel a WHERE a.idastasilenziosa = :astaId")
    int countByAstaId(@Param("astaId") Integer astaId);

    @Query("SELECT a FROM AstasilenziosaModel a JOIN AstasilenziosaseguiteModel ac ON a.id = ac.idastasilenziosa WHERE LOWER(ac.emailutente) = LOWER(:acquirente)")
    List<AstasilenziosaModel> findByEmailutenteIgnoreCase(String acquirente);

    @Query("SELECT asta FROM AstasilenziosaseguiteModel asta WHERE LOWER(asta.emailutente) = LOWER(:acquirente) AND asta.idastasilenziosa = :astaId")
    Optional<AstasilenziosaseguiteModel> findByEmailutenteAndId(String acquirente, Integer astaId);

    @Query("SELECT statoaccettazione FROM OffertaastasilenziosaModel WHERE LOWER(emailutente) = LOWER(:acquirente) and idastasilenziosa = :asta")
    String findStatoaccettazione(int asta, String acquirente);

    @Query("SELECT prezzoofferto FROM OffertaastasilenziosaModel WHERE LOWER(emailutente) = LOWER(:acquirente) and idastasilenziosa = :asta")
    double findPrezzoofferto(int asta, String acquirente);

    boolean existsByEmailutenteIgnoreCaseAndIdastasilenziosa(String emailutente, Integer idastasilenziosa);
}