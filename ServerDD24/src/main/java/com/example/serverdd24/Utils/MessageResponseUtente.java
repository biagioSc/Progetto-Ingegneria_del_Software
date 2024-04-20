package com.example.serverdd24.Utils;

import com.example.serverdd24.Model.UtenteModel;

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