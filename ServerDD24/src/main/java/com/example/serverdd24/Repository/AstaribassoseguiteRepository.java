package com.example.serverdd24.Repository;

import com.example.serverdd24.Model.AstaribassoModel;
import com.example.serverdd24.Model.AstaribassoseguiteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AstaribassoseguiteRepository extends JpaRepository<AstaribassoseguiteModel, Integer> {

    @Query("SELECT COUNT(a) FROM AstaribassoseguiteModel a WHERE a.idastaribasso = :astaId")
    int countByAstaId(@Param("astaId") Integer astaId);

    @Query("SELECT a FROM AstaribassoModel a JOIN AstaribassoseguiteModel ac ON a.id = ac.idastaribasso WHERE LOWER(ac.emailutente) = LOWER(:acquirente)")
    List<AstaribassoModel> findByEmailutenteIgnoreCase(String acquirente);

    @Query("SELECT asta FROM AstaribassoseguiteModel asta WHERE LOWER(asta.emailutente) = LOWER(:acquirente) AND asta.idastaribasso =:astaId")
    Optional<AstaribassoseguiteModel> findByEmailutenteAndId(String acquirente, Integer astaId);

    boolean existsByEmailutenteIgnoreCaseAndIdastaribasso(String emailutente, Integer idastaribasso);
}
