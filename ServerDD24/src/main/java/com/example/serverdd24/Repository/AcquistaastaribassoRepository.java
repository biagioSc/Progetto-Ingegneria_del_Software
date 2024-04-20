package com.example.serverdd24.Repository;

import com.example.serverdd24.Model.AcquistaastaribassoModel;
import com.example.serverdd24.Model.AstaribassoModel;
import com.example.serverdd24.Model.OffertaastasilenziosaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AcquistaastaribassoRepository extends JpaRepository<AcquistaastaribassoModel, Integer> {

    @Query("SELECT COUNT(a) FROM AcquistaastaribassoModel a WHERE a.idastaribasso = :astaId")
    int countByAstaId(@Param("astaId") Integer astaId);

    List<AcquistaastaribassoModel> findByIdastaribasso(Integer id);

    @Query("SELECT a FROM AstaribassoModel a JOIN AcquistaastaribassoModel ac ON a.id = ac.idastaribasso WHERE LOWER(ac.emailutente) = LOWER(:acquirente) AND a.stato = false")
    List<AstaribassoModel> findByEmailutenteIgnoreCase(String acquirente);

}