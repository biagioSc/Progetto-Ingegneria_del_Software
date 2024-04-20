package com.example.dd24client.views;

import com.example.dd24client.utils.MessageResponse;

public interface Activity01MainView {

    void onPingSuccess(MessageResponse message); // Gestisce il successo del login
    void onPingFailure(MessageResponse error); // Gestisce il fallimento del login
}
