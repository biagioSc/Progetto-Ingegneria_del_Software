package com.example.dd24client.views;

import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.utils.MessageResponseSilenziosa;

public interface Activity36AsteSilenziosaVenditoreView {

    void mostraErrore(MessageResponse error); // Gestisce il fallimento del login
    void scaricaAstaSilenziosa(MessageResponseSilenziosa body);
}
