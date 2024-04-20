package com.example.dd24client.model;

public class OffertaastasilenziosaModel {
    private Integer idastasilenziosa;
    private Double prezzoofferto;
    private String emailutente;
    private String statoaccettazione;

    // Costruttore senza argomenti
    public OffertaastasilenziosaModel() {
    }

    // Costruttore con tutti i campi
    public OffertaastasilenziosaModel(Integer idastasilenziosa, Double prezzoofferto, String emailutente, String statoaccettazione) {
        this.idastasilenziosa = idastasilenziosa;
        this.prezzoofferto = prezzoofferto;
        this.emailutente = emailutente;
        this.statoaccettazione = statoaccettazione;
    }

    // Getter and Setter methods

    public Integer getIdastasilenziosa() {
        return idastasilenziosa;
    }

    public void setIdastasilenziosa(Integer id) {
        this.idastasilenziosa = id;
    }
    public Double getprezzoofferto() {
        return prezzoofferto;
    }

    public void setprezzoofferto(Double prezzoofferto) {
        this.prezzoofferto = prezzoofferto;
    }

    public String getemailutente() {
        return emailutente;
    }

    public void setemailutente(String emailutente) {
        this.emailutente = emailutente;
    }

    public String getstatoaccettazione() {
        return statoaccettazione;
    }

    public void setstatoaccettazione(String statoaccettazione) {
        this.statoaccettazione = statoaccettazione;
    }
}

