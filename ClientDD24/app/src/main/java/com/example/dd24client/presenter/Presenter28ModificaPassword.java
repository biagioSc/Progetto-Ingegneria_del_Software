package com.example.dd24client.presenter;

import androidx.annotation.NonNull;

import com.example.dd24client.model.UtenteModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.views.Activity28ModificaPassword;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Presenter28ModificaPassword {
    private final Activity28ModificaPassword view;
    private final ApiService apiService;

    public Presenter28ModificaPassword(Activity28ModificaPassword view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    public void resetPassword(UtenteModel utente){
        Call<MessageResponse> call = apiService.updatePassword(utente);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    view.onResetPasswordSuccess(response.body());
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
                        view.onResetPasswordFailure(errorResponse);
                    }else{
                        view.onResetPasswordFailure(new MessageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                view.onResetPasswordFailure(new MessageResponse("Errore di connessione: "+t.getMessage()));
            }
        });
    }
}
