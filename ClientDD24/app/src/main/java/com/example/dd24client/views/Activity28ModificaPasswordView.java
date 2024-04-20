package com.example.dd24client.views;

import com.example.dd24client.utils.MessageResponse;

public interface Activity28ModificaPasswordView {
    void onResetPasswordFailure(MessageResponse errore_password);
    void onResetPasswordSuccess(MessageResponse message);
}
