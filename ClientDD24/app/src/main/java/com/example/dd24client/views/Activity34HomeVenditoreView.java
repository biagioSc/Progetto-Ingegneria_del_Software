package com.example.dd24client.views;

import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.utils.MessageResponseRibasso;
import com.example.dd24client.utils.MessageResponseSilenziosa;

public interface Activity34HomeVenditoreView {
    void scaricaAstaRibasso(MessageResponseRibasso message); // Gestisce il successo del login
    void mostraErrore(MessageResponse error); // Gestisce il fallimento del login
    void scaricaAstaSilenziosa(MessageResponseSilenziosa body);
}
