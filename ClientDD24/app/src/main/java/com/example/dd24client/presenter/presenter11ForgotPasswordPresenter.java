package com.example.dd24client.presenter;


import androidx.annotation.NonNull;

import com.example.dd24client.model.utenteModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.utils.messageResponse;
import com.example.dd24client.views.activity11ForgotPasswordView1;
import com.example.dd24client.views.activity12ForgotPasswordView2;
import com.example.dd24client.views.activity13ForgotPasswordView3;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class presenter11ForgotPasswordPresenter {
    private activity11ForgotPasswordView1 view;
    private activity12ForgotPasswordView2 view2;
    private activity13ForgotPasswordView3 view3;
    private final ApiService apiService;

    public presenter11ForgotPasswordPresenter(activity11ForgotPasswordView1 view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    public presenter11ForgotPasswordPresenter(activity12ForgotPasswordView2 view, ApiService apiService){
        this.view2 = view;
        this.apiService = apiService;
    }

    public presenter11ForgotPasswordPresenter(activity13ForgotPasswordView3 view, ApiService apiService){
        this.view3 = view;
        this.apiService = apiService;
    }


    public void resendRecoveryEmail(utenteModel utente){
        Call<messageResponse> call = apiService.recuperoPin(utente);
        call.enqueue(new Callback<messageResponse>() {
            @Override
            public void onResponse(@NonNull Call<messageResponse> call, @NonNull Response<messageResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    view2.onSendRecoveryEmailSuccess(response.body());
                }else{
                    messageResponse errorResponse = null;
                    if(response.errorBody() != null){
                        try {
                            // Converti errorBody in stringa, poi deserializza nel tuo tipo di errore atteso
                            String errorBodyStr = response.errorBody().string();
                            // Utilizza Gson (o un'altra libreria di deserializzazione) per convertire la stringa in MessageResponse
                            errorResponse = new Gson().fromJson(errorBodyStr, messageResponse.class);
                        } catch (IOException ignored) {
                        }
                    }

                    if (errorResponse != null) {
                        view2.onSendRecoveryEmailFailure(errorResponse);
                    } else {
                        String message = "Errore sconosciuto";
                        view2.onSendRecoveryEmailFailure(new messageResponse(message));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<messageResponse> call, @NonNull Throwable t) {
                String message = "Errore di connessione: " + t.getMessage();
                view2.onSendRecoveryEmailFailure(new messageResponse(message));
            }
        });
    }

    public void sendRecoveryEmail(utenteModel utente){
        Call<messageResponse> call = apiService.recuperoPin(utente);
        call.enqueue(new Callback<messageResponse>() {
            @Override
            public void onResponse(@NonNull Call<messageResponse> call, @NonNull Response<messageResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                        view.onSendRecoveryEmailSuccess(response.body());
                }else{
                    messageResponse errorResponse = null;
                    if(response.errorBody() != null){
                        try {
                            // Converti errorBody in stringa, poi deserializza nel tuo tipo di errore atteso
                            String errorBodyStr = response.errorBody().string();
                            // Utilizza Gson (o un'altra libreria di deserializzazione) per convertire la stringa in MessageResponse
                            errorResponse = new Gson().fromJson(errorBodyStr, messageResponse.class);
                        } catch (IOException ignored) {
                        }
                    }

                    if (errorResponse != null) {
                        view.onSendRecoveryEmailFailure(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        String message = "Errore sconosciuto";
                        view.onSendRecoveryEmailFailure(new messageResponse(message));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<messageResponse> call, @NonNull Throwable t) {
                String message = "Errore di connessione: " + t.getMessage();
                view.onSendRecoveryEmailFailure(new messageResponse(message));
            }
        });
    }

    public void sendRecoveryCode(utenteModel utente){
        Call<messageResponse> call = apiService.validatePin(utente);
        call.enqueue(new Callback<messageResponse>() {
            @Override
            public void onResponse(@NonNull Call<messageResponse> call, @NonNull Response<messageResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    view2.onSendRecoveryCodeSuccess(response.body());
                }else{
                    messageResponse errorResponse = null;
                    if(response.errorBody() != null){
                        try{
                            String errorBodyStr = response.errorBody().string();
                            errorResponse = new Gson().fromJson(errorBodyStr, messageResponse.class);
                        }catch (IOException ignored) {
                        }
                    }
                    if(errorResponse != null){
                        view2.onSendRecoveryCodeFailure(errorResponse);
                    }else{
                        String message = "Errore sconosciuto";
                        view2.onSendRecoveryEmailFailure(new messageResponse(message));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<messageResponse> call, @NonNull Throwable t) {
                String message = "Errore di connessione: " + t.getMessage();
                view2.onSendRecoveryEmailFailure(new messageResponse(message));
            }
        });
    }
    public void resetPassword(utenteModel utente){
        Call<messageResponse> call = apiService.updatePassword(utente);
        call.enqueue(new Callback<messageResponse>() {
            @Override
            public void onResponse(@NonNull Call<messageResponse> call, @NonNull Response<messageResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    view3.onResetPasswordSuccess(response.body());
                }else{
                    messageResponse errorResponse = null;
                    if(response.errorBody() != null){
                        try{
                            String errorBodyStr = response.errorBody().string();
                            errorResponse = new Gson().fromJson(errorBodyStr, messageResponse.class);
                        }catch (IOException ignored) {
                        }
                    }
                    if(errorResponse != null){
                        view3.onResetPasswordFailure(errorResponse);
                    }else{
                        String message = "Errore sconosciuto";
                        view3.onResetPasswordFailure(new messageResponse(message));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<messageResponse> call, @NonNull Throwable t) {
                String message = "Errore di connessione: " + t.getMessage();
                view3.onResetPasswordFailure(new messageResponse(message));
            }
        });
    }




}
