package com.example.dd24client.model;


public class AstasilenziosaseguiteModel {
    private Integer idastasilenziosa;
    private String emailutente;

    // Costruttore senza argomenti
    public AstasilenziosaseguiteModel() {
    }

    // Costruttore con tutti i campi
    public AstasilenziosaseguiteModel(Integer idastasilenziosa, String emailutente) {
        this.idastasilenziosa = idastasilenziosa;
        this.emailutente = emailutente;
    }

    // Getter and Setter methods

    public Integer getidastasilenziosa() {
        return idastasilenziosa;
    }

    public void setidastasilenziosa(Integer idastasilenziosa) {
        this.idastasilenziosa = idastasilenziosa;
    }

    public String getEmailutente() {
        return emailutente;
    }

    public void setEmailutente(String emailutente) {
        this.emailutente = emailutente;
    }
}
