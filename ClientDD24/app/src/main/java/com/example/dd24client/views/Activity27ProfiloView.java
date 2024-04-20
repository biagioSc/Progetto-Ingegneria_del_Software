package com.example.dd24client.views;

import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.utils.MessageResponseUtente;

public interface Activity27ProfiloView {
    void utenteNonTrovato(MessageResponse errore_password);
    void utenteTrovato(MessageResponseUtente message);
}
