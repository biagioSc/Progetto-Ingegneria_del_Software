package com.example.serverdd24.Model;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.Interval;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "astaribasso")
public class AstaribassoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "stato", nullable = false)
    private boolean stato;

    @Column(name = "titolo", nullable = false)
    private String titolo;

    @Column(name = "parolechiave", nullable = false)
    private String parolechiave;

    @Column(name = "categoria", nullable = false)
    private String categoria;

    @Column(name = "sottocategoria", nullable = false)
    private String sottocategoria;

    @Column(name = "descrizioneprodotto", nullable = false)
    private String descrizioneprodotto;

    @Column(name = "venditore", nullable = false)
    private String venditore;

    @Column(name = "immagineprodotto")
    private String immagineprodotto;

    @Column(name = "importodecremento", nullable = false)
    private double importodecremento;

    @Column(name = "prezzominimosegreto", nullable = false)
    private double prezzominimosegreto;

    @Column(name = "prezzoiniziale", nullable = false)
    private double prezzoiniziale;

    @Column(name = "timerdecremento", nullable = false)
    private Integer timerdecremento;

    @Column(name = "ultimoprezzo", nullable = false)
    private double ultimoprezzo;

    @Column(name = "created_at")
    private Timestamp created_at;

    @Column(name = "Prezzovendita")
    private Double prezzovendita;

    @Transient
    private int conteggioUtenti;

    // Getter e setter per conteggioUtenti

    public int getConteggioUtenti() {
        return conteggioUtenti;
    }

    public void setConteggioUtenti(int conteggioUtenti) {
        this.conteggioUtenti = conteggioUtenti;
    }


    // Getter and Setter methods

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isStato() {
        return stato;
    }

    public void setStato(boolean stato) {
        this.stato = stato;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getParolechiave() {
        return parolechiave;
    }

    public void setParolechiave(String parolechiave) {
        this.parolechiave = parolechiave;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSottocategoria() {
        return sottocategoria;
    }

    public void setSottocategoria(String sottocategoria) {
        this.sottocategoria = sottocategoria;
    }

    public String getDescrizioneprodotto() {
        return descrizioneprodotto;
    }

    public void setDescrizioneprodotto(String descrizioneprodotto) {
        this.descrizioneprodotto = descrizioneprodotto;
    }

    public String getVenditore() {
        return venditore;
    }

    public void setVenditore(String venditore) {
        this.venditore = venditore;
    }

    public String getImmagineprodotto() {
        return immagineprodotto;
    }

    public void setImmagineprodotto(String immagineprodotto) {
        this.immagineprodotto = immagineprodotto;
    }

    public double getImportodecremento() {
        return importodecremento;
    }

    public void setImportodecremento(double importodecremento) {
        this.importodecremento = importodecremento;
    }

    public double getPrezzominimosegreto() {
        return prezzominimosegreto;
    }

    public void setPrezzominimosegreto(double prezzominimosegreto) {
        this.prezzominimosegreto = prezzominimosegreto;
    }

    public double getPrezzoiniziale() {
        return prezzoiniziale;
    }

    public void setPrezzoiniziale(double prezzoiniziale) {
        this.prezzoiniziale = prezzoiniziale;
    }

    public Integer getTimerdecremento() {
        return timerdecremento;
    }

    public void setTimerdecremento(Integer timerdecremento) {
        this.timerdecremento = timerdecremento;
    }

    public double getUltimoprezzo() {
        return ultimoprezzo;
    }

    public void setUltimoprezzo(double ultimoprezzo) {
        this.ultimoprezzo = ultimoprezzo;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Double getPrezzovendita() {
        return prezzovendita;
    }

    public void setPrezzovendita(Double prezzovendita) {
        this.prezzovendita = prezzovendita;
    }

}
