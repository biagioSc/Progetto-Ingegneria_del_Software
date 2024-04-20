package com.example.serverdd24.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "offerteastasilenziosa")
public class OffertaastasilenziosaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idfake")
    private Long idfake;

    @Column(name = "idastasilenziosa", nullable = false)
    private Integer idastasilenziosa;

    @Column(name = "prezzoofferto", nullable = false)
    private Double prezzoofferto;

    @Column(name = "emailutente", nullable = false)
    private String emailutente;

    @Column(name = "statoaccettazione", nullable = false)
    private String statoaccettazione;


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

