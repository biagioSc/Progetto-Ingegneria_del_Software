package com.example.dd24client.views;

import com.example.dd24client.utils.messageResponse;
import com.example.dd24client.utils.messageResponseUtente;

public interface activity19DettaglioAstaRibassoView {
    void onCreateSuccess(messageResponse body);
    void onCreateFailure(messageResponse errore_di_registrazione);
    void utenteNonTrovato(messageResponse errore_password);
    void utenteTrovato(messageResponseUtente message);
    void onCreateSuccessSegui(messageResponse body);
    void onCreateFailureSegui(messageResponse errore_di_registrazione);
    void onSuccessSegui(messageResponse body);
    void onFailureSegui(messageResponse errore_di_registrazione);
}
