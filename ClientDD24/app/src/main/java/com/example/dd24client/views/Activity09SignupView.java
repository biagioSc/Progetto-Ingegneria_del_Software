package com.example.dd24client.views;

import com.example.dd24client.utils.MessageResponse;

public interface Activity09SignupView {
    void onSignupSuccess(MessageResponse body);
    void onSignupFailure(MessageResponse errore_di_registrazione);
    void onSignupClicked();

}
