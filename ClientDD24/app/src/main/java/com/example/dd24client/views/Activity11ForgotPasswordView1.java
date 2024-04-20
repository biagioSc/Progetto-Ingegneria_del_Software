package com.example.dd24client.views;

import com.example.dd24client.utils.messageResponse;

public interface activity11ForgotPasswordView1 {

    void onSendRecoveryEmailFailure(messageResponse errore_email);
    void onSendRecoveryEmailSuccess(messageResponse message);
    void onSendRecoveryEmailClicked();


}
