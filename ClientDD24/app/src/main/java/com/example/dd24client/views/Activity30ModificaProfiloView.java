package com.example.dd24client.views;

import com.example.dd24client.utils.MessageResponse;

public interface Activity30ModificaProfiloView {

    void onResetUtenteFailure(MessageResponse errore_password);
    void onResetUtenteSuccess(MessageResponse message);
}
