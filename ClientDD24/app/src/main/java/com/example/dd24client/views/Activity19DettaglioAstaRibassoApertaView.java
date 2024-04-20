package com.example.dd24client.views;

import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.utils.MessageResponseUtente;

public interface Activity19DettaglioAstaRibassoApertaView {
    void onCreateSuccess(MessageResponse body);
    void onCreateFailure(MessageResponse errore_di_registrazione);
    void utenteNonTrovato(MessageResponse errore_password);
    void utenteTrovato(MessageResponseUtente message);
    void onCreateSuccessSegui(MessageResponse body);
    void onCreateFailureSegui(MessageResponse errore_di_registrazione);
    void onSuccessSegui(MessageResponse body);
    void onFailureSegui(MessageResponse errore_di_registrazione);
}
