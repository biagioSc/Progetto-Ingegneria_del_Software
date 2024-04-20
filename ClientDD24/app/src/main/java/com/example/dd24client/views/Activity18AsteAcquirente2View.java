package com.example.dd24client.views;

import com.example.dd24client.utils.messageResponse;
import com.example.dd24client.utils.messageResponseSilenziosa;

public interface Activity18AsteAcquirente2View {
    void scaricaAstaSilenziosa(messageResponseSilenziosa body);
    void mostraErroreSilenziosa(messageResponse error); // Gestisce il fallimento del login
}
