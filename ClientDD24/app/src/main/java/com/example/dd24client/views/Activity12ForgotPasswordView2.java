package com.example.dd24client.views;

import com.example.dd24client.utils.messageResponse;

public interface activity12ForgotPasswordView2 {
    void onSendRecoveryCodeClicked();
    void onSendRecoveryCodeFailure(messageResponse errore_codice);
    void onSendRecoveryCodeSuccess(messageResponse message);


    void onSendRecoveryEmailFailure(messageResponse errore_email);
    void onSendRecoveryEmailSuccess(messageResponse message);
    void onSendRecoveryEmailClicked();
}
