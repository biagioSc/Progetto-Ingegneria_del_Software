package com.example.serverdd24.Repository;

import com.example.serverdd24.Model.AstaribassoModel;
import com.example.serverdd24.Model.AstasilenziosaModel;
import com.example.serverdd24.Model.UtenteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AstaribassoRepository extends JpaRepository<AstaribassoModel, String> {
    List<AstaribassoModel> findByVenditoreIgnoreCase(String venditore);

    @Query(value = "SELECT asta FROM AstaribassoModel asta WHERE LOWER(asta.venditore) <> LOWER(:email) AND asta.stato = true")
    List<AstaribassoModel> findByStatoTrue(String email);

    @Query(value = "SELECT * FROM astaribasso asta WHERE to_tsvector('italian', titolo || ' ' || descrizioneprodotto || ' ' || paroleChiave) @@ plainto_tsquery('italian', :query) " +
            "AND LOWER(asta.venditore) = LOWER(:email)" +
            "ORDER BY " +
            "CASE WHEN :ordinaper = 'Prezzo dal più caro' THEN asta.ultimoprezzo END DESC, " +
            "CASE WHEN :ordinaper = 'Prezzo dal meno caro' THEN asta.ultimoprezzo END ASC, " +
            "CASE WHEN :ordinaper = 'Nome A-Z' THEN asta.titolo END ASC, " +
            "CASE WHEN :ordinaper = 'Nome Z-A' THEN asta.titolo END DESC", nativeQuery = true)
    List<AstaribassoModel> searchByKeywordVenditore(
            @Param("query") String query,
            @Param("ordinaper") String ordinaper,
            @Param("email") String email);

    @Query(value = "SELECT * FROM astaribasso asta WHERE to_tsvector('italian', titolo || ' ' || descrizioneprodotto || ' ' || paroleChiave) @@ plainto_tsquery('italian', :query) " +
            "AND asta.stato = true " +
            "AND LOWER(asta.venditore) <> LOWER(:email)" +
            "ORDER BY " +
            "CASE WHEN :ordinaper = 'Prezzo dal più caro' THEN asta.ultimoprezzo END DESC, " +
            "CASE WHEN :ordinaper = 'Prezzo dal meno caro' THEN asta.ultimoprezzo END ASC, " +
            "CASE WHEN :ordinaper = 'Nome A-Z' THEN asta.titolo END ASC, " +
            "CASE WHEN :ordinaper = 'Nome Z-A' THEN asta.titolo END DESC", nativeQuery = true)
    List<AstaribassoModel> searchByKeywordAcquirente(
            @Param("query") String query,
            @Param("ordinaper") String ordinaper,
            @Param("email") String email);

}

