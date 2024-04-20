package com.example.dd24client.views;

import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.utils.MessageResponseRibasso;
import com.example.dd24client.utils.MessageResponseSilenziosa;

public interface Activity15HomeAcquirenteView {
    void scaricaAstaRibasso(MessageResponseRibasso message); // Gestisce il successo del login
    void mostraErroreRibasso(MessageResponse error); // Gestisce il fallimento del login
    void scaricaAstaSilenziosa(MessageResponseSilenziosa body);
    void mostraErroreSilenziosa(MessageResponse error); // Gestisce il fallimento del login

}
