package com.example.dd24client.utils;

import com.example.dd24client.model.UtenteModel;

public class MessageResponseUtente {
    private UtenteModel utente;

    public MessageResponseUtente(UtenteModel utente) {
        this.utente = utente;
    }

    public UtenteModel getUtente() {
        return utente;
    }

    public void setUtente(UtenteModel utente) {
        this.utente = utente;
    }
}
