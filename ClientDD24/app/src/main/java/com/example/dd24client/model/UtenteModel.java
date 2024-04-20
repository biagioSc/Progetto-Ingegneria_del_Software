package com.example.dd24client.model;

import androidx.annotation.NonNull;

import java.sql.Timestamp;


public class UtenteModel {
    private String email;
    private String password;
    private String nome;
    private String cognome;
    private String citta;
    private String nazionalita;
    private Integer pin;
    private String biografia;
    private String linkweb;
    private String linksocial;
    private Timestamp tempopin;
    private String immagine;
    private String token;

    // Costruttore vuoto
    public UtenteModel() {
    }

    // Costruttore con tutti i campi
    public UtenteModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UtenteModel(String email, Integer pin) {
        this.email = email;
        this.pin = pin;
    }

    public UtenteModel(String email ) {
        this.email = email;
    }

    public UtenteModel(String email, String password, String nome, String cognome, String immagine,
                     String nazionalita, String citta, String biografia, String linkweb, String linksocial, String token) {
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.immagine = immagine;
        this.nazionalita = nazionalita;
        this.citta = citta;
        this.biografia = biografia;
        this.linkweb = linkweb;
        this.linksocial = linksocial;
        this.token = token;
    }

    public UtenteModel(String email, String password, String nome, String cognome, String immagine,
                       String nazionalita, String citta, String biografia, String linkweb, String linksocial) {
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.immagine = immagine;
        this.nazionalita = nazionalita;
        this.citta = citta;
        this.biografia = biografia;
        this.linkweb = linkweb;
        this.linksocial = linksocial;
    }

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

    // Metodo toString() opzionale per la rappresentazione sotto forma di stringa dell'oggetto
    @NonNull
    @Override
    public String toString() {
        return "Utente{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String toStringUtente() {
        return "UtenteModel{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", nazionalita='" + nazionalita + '\'' +
                ", citta='" + citta + '\'' +
                ", biografia='" + biografia + '\'' +
                ", linkSitoWeb='" + linkweb + '\'' +
                ", socialLinks='" + linksocial + '\'' +
                ", immagine='" + immagine + '\'' +
                '}';
    }

}
