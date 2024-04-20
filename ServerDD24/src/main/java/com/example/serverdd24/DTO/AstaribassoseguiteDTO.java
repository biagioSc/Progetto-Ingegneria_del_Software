package com.example.serverdd24.DTO;

public class AstaribassoseguiteDTO {
    private Integer idastaribasso;
    private String emailutente;

    // Costruttore senza argomenti
    public AstaribassoseguiteDTO() {
    }

    // Costruttore con tutti i campi
    public AstaribassoseguiteDTO(Integer idastaribasso, String emailutente) {
        this.idastaribasso = idastaribasso;
        this.emailutente = emailutente;
    }

    // Getter and Setter methods

    public Integer getidastaribasso() {
        return idastaribasso;
    }

    public void setidastaribasso(Integer idastaribasso) {
        this.idastaribasso = idastaribasso;
    }

    public String getEmailutente() {
        return emailutente;
    }

    public void setEmailutente(String emailutente) {
        this.emailutente = emailutente;
    }
}
