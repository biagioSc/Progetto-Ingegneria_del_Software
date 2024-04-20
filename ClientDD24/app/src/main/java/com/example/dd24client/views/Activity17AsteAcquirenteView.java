package com.example.dd24client.views;

import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.utils.MessageResponseRibasso;

public interface Activity17AsteAcquirenteView {
    void scaricaAstaRibasso(MessageResponseRibasso message); // Gestisce il successo del login
    void mostraErroreRibasso(MessageResponse error); // Gestisce il fallimento del login
}
