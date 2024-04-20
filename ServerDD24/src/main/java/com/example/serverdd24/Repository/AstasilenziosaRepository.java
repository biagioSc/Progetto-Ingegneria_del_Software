package com.example.serverdd24.Repository;

import com.example.serverdd24.Model.AstaribassoModel;
import com.example.serverdd24.Model.AstasilenziosaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AstasilenziosaRepository extends JpaRepository<AstasilenziosaModel, String> {
    List<AstasilenziosaModel> findByVenditoreIgnoreCase(String venditore);

    @Query(value = "SELECT asta FROM AstasilenziosaModel asta WHERE LOWER(asta.venditore) <> LOWER(:email) AND asta.stato = true")
    List<AstasilenziosaModel> findByStatoTrue(String email);

    @Query(value = "SELECT * FROM astasilenziosa asta WHERE to_tsvector('italian', titolo || ' ' || descrizioneprodotto || ' ' || paroleChiave) @@ plainto_tsquery('italian', :query) " +
            "AND LOWER(asta.venditore) = LOWER(:email)" +
            "ORDER BY " +
            "CASE WHEN :ordinaper = 'Nome A-Z' THEN asta.titolo END ASC, " +
            "CASE WHEN :ordinaper = 'Nome Z-A' THEN asta.titolo END DESC", nativeQuery = true)
    List<AstasilenziosaModel> searchByKeywordVenditore(
            @Param("query") String query,
            @Param("ordinaper") String ordinaper,
            @Param("email") String email);

    @Query(value = "SELECT * FROM astasilenziosa asta WHERE to_tsvector('italian', titolo || ' ' || descrizioneprodotto || ' ' || paroleChiave) @@ plainto_tsquery('italian', :query) " +
            "AND asta.stato = true " +
            "AND LOWER(asta.venditore) <> LOWER(:email)" +
            "ORDER BY " +
            "CASE WHEN :ordinaper = 'Nome A-Z' THEN asta.titolo END ASC, " +
            "CASE WHEN :ordinaper = 'Nome Z-A' THEN asta.titolo END DESC", nativeQuery = true)
    List<AstasilenziosaModel> searchByKeywordAcquirente(
            @Param("query") String query,
            @Param("ordinaper") String ordinaper,
            @Param("email") String email);
}

