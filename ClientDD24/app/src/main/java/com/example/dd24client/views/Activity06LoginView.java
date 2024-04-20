package com.example.dd24client.views;

// LoginView.java

import com.example.dd24client.utils.MessageResponse;

public interface Activity06LoginView {
    void onLoginSuccess(MessageResponse message); // Gestisce il successo del login
    void onLoginFailure(MessageResponse error); // Gestisce il fallimento del login
    void onLoginClicked();

}
