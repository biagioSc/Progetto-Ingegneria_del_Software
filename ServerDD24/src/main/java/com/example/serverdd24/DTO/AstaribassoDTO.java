package com.example.serverdd24.DTO;

import org.antlr.v4.runtime.misc.Interval;

import java.sql.Timestamp;

public class AstaribassoDTO {
    private Integer id;
    private boolean stato;
    private String titolo;
    private String parolechiave;
    private String categoria;
    private String sottocategoria;
    private String descrizioneprodotto;
    private String venditore;
    private String immagineprodotto;
    private double importodecremento;
    private double prezzominimosegreto;
    private double prezzoiniziale;
    private Integer timerdecremento; // Assicurati che questo tipo sia adatto
    private double ultimoprezzo;
    private int conteggioUtenti;
    private Timestamp created_at;
    private Double prezzovendita;

    // Getter e setter per conteggioUtenti

    public int getConteggioUtenti() {
        return conteggioUtenti;
    }

    public void setConteggioUtenti(int conteggioUtenti) {
        this.conteggioUtenti = conteggioUtenti;
    }

    // Costruttore senza argomenti
    public AstaribassoDTO() {
    }

    // Costruttore con tutti i campi
    public AstaribassoDTO(boolean stato, String titolo, String categoria, String sottocategoria, String descrizioneprodotto, String venditore, String immagineprodotto, int importodecremento, int prezzominimosegreto, int prezzoiniziale, Integer timerdecremento, int ultimoprezzo) {
        this.stato = stato;
        this.titolo = titolo;
        this.categoria = categoria;
        this.sottocategoria = sottocategoria;
        this.descrizioneprodotto = descrizioneprodotto;
        this.venditore = venditore;
        this.immagineprodotto = immagineprodotto;
        this.importodecremento = importodecremento;
        this.prezzominimosegreto = prezzominimosegreto;
        this.prezzoiniziale = prezzoiniziale;
        this.timerdecremento = timerdecremento;
        this.ultimoprezzo = ultimoprezzo;
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
