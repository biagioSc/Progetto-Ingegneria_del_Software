package com.example.dd24client.presenter;


import androidx.annotation.NonNull;

import com.example.dd24client.model.UtenteModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.views.Activity11ForgotView;
import com.example.dd24client.views.Activity12ForgotView;
import com.example.dd24client.views.Activity13ForgotView;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Presenter11Forgot {
    private Activity11ForgotView view;
    private Activity12ForgotView view2;
    private Activity13ForgotView view3;
    private final ApiService apiService;

    public Presenter11Forgot(Activity11ForgotView view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    public Presenter11Forgot(Activity12ForgotView view, ApiService apiService){
        this.view2 = view;
        this.apiService = apiService;
    }

    public Presenter11Forgot(Activity13ForgotView view, ApiService apiService){
        this.view3 = view;
        this.apiService = apiService;
    }


    public void resendRecoveryEmail(UtenteModel utente){
        Call<MessageResponse> call = apiService.recuperoPin(utente);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    view2.onSendRecoveryEmailSuccess(response.body());
                }else{
                    MessageResponse errorResponse = null;
                    if(response.errorBody() != null){
                        try {
                            // Converti errorBody in stringa, poi deserializza nel tuo tipo di errore atteso
                            String errorBodyStr = response.errorBody().string();
                            // Utilizza Gson (o un'altra libreria di deserializzazione) per convertire la stringa in MessageResponse
                            errorResponse = new Gson().fromJson(errorBodyStr, MessageResponse.class);
                        } catch (IOException e) {
                                                        String errorBodyStr = "Errore sconosciuto";
                            // Utilizza Gson (o un'altra libreria di deserializzazione) per convertire la stringa in MessageResponse
                            errorResponse = new Gson().fromJson(errorBodyStr, MessageResponse.class);
                        }
                    }

                    if (errorResponse != null) {
                        view2.onSendRecoveryEmailFailure(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view2.onSendRecoveryEmailFailure(new MessageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                view2.onSendRecoveryEmailFailure(new MessageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }

    public void sendRecoveryEmail(UtenteModel utente){
        Call<MessageResponse> call = apiService.recuperoPin(utente);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                        view.onSendRecoveryEmailSuccess(response.body());
                }else{
                    MessageResponse errorResponse = null;
                    if(response.errorBody() != null){
                        try {
                            // Converti errorBody in stringa, poi deserializza nel tuo tipo di errore atteso
                            String errorBodyStr = response.errorBody().string();
                            // Utilizza Gson (o un'altra libreria di deserializzazione) per convertire la stringa in MessageResponse
                            errorResponse = new Gson().fromJson(errorBodyStr, MessageResponse.class);
                        } catch (IOException e) {
                                                        String errorBodyStr = "Errore sconosciuto";
                            // Utilizza Gson (o un'altra libreria di deserializzazione) per convertire la stringa in MessageResponse
                            errorResponse = new Gson().fromJson(errorBodyStr, MessageResponse.class);
                        }
                    }

                    if (errorResponse != null) {
                        view.onSendRecoveryEmailFailure(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view.onSendRecoveryEmailFailure(new MessageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                view.onSendRecoveryEmailFailure(new MessageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }
    public void sendRecoveryCode(UtenteModel utente){
        Call<MessageResponse> call = apiService.validatePin(utente);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    view2.onSendRecoveryCodeSuccess(response.body());
                }else{
                    MessageResponse errorResponse = null;
                    if(response.errorBody() != null){
                        try{
                            String errorBodyStr = response.errorBody().string();
                            errorResponse = new Gson().fromJson(errorBodyStr,MessageResponse.class);
                        }catch (IOException e){
                                                        String errorBodyStr = "Errore sconosciuto";
                            // Utilizza Gson (o un'altra libreria di deserializzazione) per convertire la stringa in MessageResponse
                            errorResponse = new Gson().fromJson(errorBodyStr, MessageResponse.class);
                        }
                    }
                    if(errorResponse != null){
                        view2.onSendRecoveryCodeFailure(errorResponse);
                    }else{
                        view2.onSendRecoveryCodeFailure(new MessageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                view2.onSendRecoveryCodeFailure(new MessageResponse("Errore di connessione: "+t.getMessage()));
            }
        });
    }
    public void resetPassword(UtenteModel utente){
        Call<MessageResponse> call = apiService.updatePassword(utente);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    view3.onResetPasswordSuccess(response.body());
                }else{
                    MessageResponse errorResponse = null;
                    if(response.errorBody() != null){
                        try{
                            String errorBodyStr = response.errorBody().string();
                            errorResponse = new Gson().fromJson(errorBodyStr,MessageResponse.class);
                        }catch (IOException e){
                                                        String errorBodyStr = "Errore sconosciuto";
                            // Utilizza Gson (o un'altra libreria di deserializzazione) per convertire la stringa in MessageResponse
                            errorResponse = new Gson().fromJson(errorBodyStr, MessageResponse.class);
                        }
                    }
                    if(errorResponse != null){
                        view3.onResetPasswordFailure(errorResponse);
                    }else{
                        view3.onResetPasswordFailure(new MessageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                view3.onResetPasswordFailure(new MessageResponse("Errore di connessione: "+t.getMessage()));
            }
        });
    }




}
