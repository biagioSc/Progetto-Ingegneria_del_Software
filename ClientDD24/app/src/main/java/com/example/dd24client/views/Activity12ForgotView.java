package com.example.dd24client.views;

import com.example.dd24client.utils.MessageResponse;

public interface Activity12ForgotView {
    void onSendRecoveryCodeFailure(MessageResponse errore_codice);
    void onSendRecoveryCodeSuccess(MessageResponse message);
    void onSendRecoveryEmailFailure(MessageResponse errore_email);
    void onSendRecoveryEmailSuccess(MessageResponse message);
}
