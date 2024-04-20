package com.example.serverdd24.DTO;

public class AstasilenziosaseguiteDTO {
    private Integer idastasilenziosa;
    private String emailutente;

    // Costruttore senza argomenti
    public AstasilenziosaseguiteDTO() {
    }

    // Costruttore con tutti i campi
    public AstasilenziosaseguiteDTO(Integer idastasilenziosa, String emailutente) {
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
