package com.example.serverdd24.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "astesilenzioseseguite")
public class AstasilenziosaseguiteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idfake")
    private Long idfake;

    @Column(name = "idastasilenziosa", nullable = false)
    private Integer idastasilenziosa;

    @Column(name = "emailutente", nullable = false)
    private String emailutente;


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

