package com.example.dd24client.views;

import com.example.dd24client.utils.MessageResponse;

public interface Activity11ForgotView {

    void onSendRecoveryEmailFailure(MessageResponse errore_email);
    void onSendRecoveryEmailSuccess(MessageResponse message);

}
