package com.example.serverdd24.Repository;

import com.example.serverdd24.Model.UtenteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<UtenteModel, String> {
    Optional<UtenteModel> findByEmailIgnoreCase(String email);
    boolean existsBypin(Integer pin);

    @Query("SELECT v.token FROM UtenteModel v JOIN AstaribassoModel a ON LOWER(v.email) = LOWER(a.venditore) WHERE a.id = :idAsta")
    String findTokenByAstaribassoId(@Param("idAsta") int idAsta);

    @Query("SELECT v.token FROM UtenteModel v JOIN AstasilenziosaModel a ON LOWER(v.email) = LOWER(a.venditore) WHERE a.id = :idAsta")
    String findTokenByAstasilenziosaId(@Param("idAsta") int idAsta);
}
