package com.example.serverdd24.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "asteribassoseguite")
public class AstaribassoseguiteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idfake")
    private Long idfake;

    @Column(name = "idastaribasso", nullable = false)
    private Integer idastaribasso;

    @Column(name = "emailutente", nullable = false)
    private String emailutente;


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