package com.example.serverdd24.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "acquistiastaribasso")
public class AcquistaastaribassoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idfake")
    private Long idfake;

    @Column(name = "idastaribasso", nullable = false)
    private Integer idastaribasso;

    @Column(name = "prezzoacquisto", nullable = false)
    private Double prezzoacquisto;

    @Column(name = "emailutente", nullable = false)
    private String emailutente;


    // Getter and Setter methods

    public Integer getId() {
        return idastaribasso;
    }

    public void setId(Integer idastaribasso) {
        this.idastaribasso = idastaribasso;
    }
    public Double getprezzoacquisto() {
        return prezzoacquisto;
    }

    public void setprezzoacquisto(Double prezzoacquisto) {
        this.prezzoacquisto = prezzoacquisto;
    }

    public String getemailutente() {
        return emailutente;
    }

    public void setemailutente(String emailutente) {
        this.emailutente = emailutente;
    }

}

