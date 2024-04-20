package com.example.serverdd24.DTO;

public class AcquistaastaribassoDTO {
    private Integer id;
    private Double prezzoacquisto;
    private String emailutente;

    // Costruttore senza argomenti
    public AcquistaastaribassoDTO() {
    }

    // Costruttore con tutti i campi
    public AcquistaastaribassoDTO(Integer id, Double prezzoacquisto, String emailutente) {
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
