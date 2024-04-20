package com.example.dd24client.views;

import androidx.cardview.widget.CardView;

import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.utils.MessageResponseOfferte;

public interface Activity41DettaglioASApertaView {
    void onUpdateFailure(MessageResponse body);
    void mostraErrore(MessageResponse error); // Gestisce il fallimento del login
    void scaricaOfferta(MessageResponseOfferte body);
    void onUpdateSuccessAccettata(MessageResponse body);
    void onUpdateSuccessRifiutata(MessageResponse body, CardView card);


}
