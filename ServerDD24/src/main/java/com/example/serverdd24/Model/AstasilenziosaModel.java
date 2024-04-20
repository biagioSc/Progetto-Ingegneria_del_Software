package com.example.serverdd24.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

@Entity
@Table(name = "astasilenziosa")
public class AstasilenziosaModel {
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

    @Column(name = "datafineasta", nullable = false)
    private String datafineasta;

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

    @Transient
    private int conteggioOfferte;

    // Getter e setter per conteggioUtenti

    public int getConteggioOfferte() {
        return conteggioOfferte;
    }

    public void setConteggioOfferte(int conteggioOfferte) {
        this.conteggioOfferte = conteggioOfferte;
    }


    @Transient
    private String statoaccettazione;

    // Getter e setter per conteggioUtenti

    public String getstatoaccettazione() {
        return statoaccettazione;
    }

    public void setstatoaccettazione(String statoaccettazione) {
        this.statoaccettazione = statoaccettazione;
    }

    @Transient
    private double prezzoofferto;

    // Getter e setter per conteggioUtenti

    public double getPrezzoofferto() {
        return prezzoofferto;
    }

    public void setPrezzoofferto(double prezzoofferto) {
        this.prezzoofferto = prezzoofferto;
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

    public String getDatafineasta() {
        return datafineasta;
    }

    public void setDatafineasta(String datafineasta) {
        this.datafineasta = datafineasta;
    }

    public Double getPrezzovendita() {
        return prezzovendita;
    }

    public void setPrezzovendita(Double prezzovendita) {
        this.prezzovendita = prezzovendita;
    }

}

