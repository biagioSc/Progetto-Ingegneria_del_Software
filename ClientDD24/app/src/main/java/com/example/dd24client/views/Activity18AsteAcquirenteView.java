package com.example.dd24client.views;

import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.utils.MessageResponseSilenziosa;

public interface Activity18AsteAcquirenteView {
    void scaricaAstaSilenziosa(MessageResponseSilenziosa body);
    void mostraErroreSilenziosa(MessageResponse error); // Gestisce il fallimento del login
}
