package com.example.serverdd24.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;


public class AstasilenziosaDTO {
    private Integer id;
    private boolean stato;
    private String titolo;
    private String parolechiave;
    private String categoria;
    private String sottocategoria;
    private String descrizioneprodotto;
    private String venditore;
    private String immagineprodotto;
    private String datafineasta;
    private int conteggioUtenti;
    private Double prezzovendita;

    // Getter e setter per conteggioUtenti

    public int getConteggioUtenti() {
        return conteggioUtenti;
    }

    public void setConteggioUtenti(int conteggioUtenti) {
        this.conteggioUtenti = conteggioUtenti;
    }

    private int conteggioOfferte;

    // Getter e setter per conteggioUtenti

    public int getConteggioOfferte() {
        return conteggioOfferte;
    }

    public void setConteggioOfferte(int conteggioOfferte) {
        this.conteggioOfferte = conteggioOfferte;
    }
    // Costruttore senza argomenti
    public AstasilenziosaDTO() {
    }

    // Costruttore con tutti i campi
    public AstasilenziosaDTO(boolean stato, String titolo, String categoria, String sottocategoria, String descrizioneprodotto, String venditore, String immagineprodotto, String datafineasta) {
        this.stato = stato;
        this.titolo = titolo;
        this.categoria = categoria;
        this.sottocategoria = sottocategoria;
        this.descrizioneprodotto = descrizioneprodotto;
        this.venditore = venditore;
        this.immagineprodotto = immagineprodotto;
        this.datafineasta = datafineasta;
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
