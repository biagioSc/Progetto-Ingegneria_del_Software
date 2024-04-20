package com.example.dd24client.model;

public class AcquistaastaribassoModel {
    private Integer id;
    private Double prezzoacquisto;
    private String emailutente;

    // Costruttore senza argomenti
    public AcquistaastaribassoModel() {
    }

    // Costruttore con tutti i campi
    public AcquistaastaribassoModel(Integer id, Double prezzoacquisto, String emailutente) {
        this.id = id;
        this.prezzoacquisto = prezzoacquisto;
        this.emailutente = emailutente;
    }

    // Getter and Setter methods

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrezzoAcquisto() {
        return prezzoacquisto;
    }

    public void setPrezzoAcquisto(Double prezzoacquisto) {
        this.prezzoacquisto = prezzoacquisto;
    }

    public String getEmailUtente() {
        return emailutente;
    }

    public void setEmailUtente(String emailutente) {
        this.emailutente = emailutente;
    }
}
