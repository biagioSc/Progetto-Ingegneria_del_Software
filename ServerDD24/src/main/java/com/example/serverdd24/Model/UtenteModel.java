package com.example.serverdd24.Model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "utente")
public class UtenteModel {
    @Id
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "cognome", nullable = false)
    private String cognome;
    @Column(name = "citta", nullable = false)
    private String citta;
    @Column(name = "nazionalita", nullable = false)
    private String nazionalita;
    @Column(name = "pin", nullable = false)
    private Integer pin;
    @Column(name = "linkweb")
    private String linkweb;
    @Column(name = "biografia")
    private String biografia;
    @Column(name = "tempopin")
    private Timestamp tempopin;
    @Column(name = "linksocial")
    private String linksocial;
    @Column(name = "immagine")
    private String immagine;
    @Column(name = "token")
    private String token;

    // Costruttore, getter e setter
    public UtenteModel() {
    }


    // Getter e setter

    // Getter e setter
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getNome() {
        return nome;
    }
    public String getCognome() {
        return cognome;
    }
    public String getCitta() {
        return citta;
    }
    public String getNazionalita() {
        return nazionalita;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public void setNazionalita(String nazionalita) {
        this.nazionalita = nazionalita;
    }

    public void setpin(Integer pin) {
        this.pin = pin;
    }

    public void setLinkweb(String linkWeb) {
        this.linkweb = linkWeb;
    }
    public void setLinksocial(String linksocial) {
        this.linksocial = linksocial;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }
    public void setTempopin(Timestamp tempopin) {
        this.tempopin = tempopin;
    }
    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public Integer getpin() {
        return pin;
    }
    public String getLinkweb() {
        return linkweb;
    }
    public String getLinksocial() {
        return linksocial;
    }
    public String getBiografia() {
        return biografia;
    }
    public Timestamp getTempopin() {
        return tempopin;
    }
    public String getImmagine() {
        return immagine;
    }
    public String getToken() {
        return token;
    }

}
