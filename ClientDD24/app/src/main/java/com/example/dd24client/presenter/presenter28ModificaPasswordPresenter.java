package com.example.dd24client.presenter;

import androidx.annotation.NonNull;

import com.example.dd24client.model.utenteModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.utils.messageResponse;
import com.example.dd24client.views.activity28ModificaPasswordActivity;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class presenter28ModificaPasswordPresenter {
    private final activity28ModificaPasswordActivity view;
    private final ApiService apiService;

    public presenter28ModificaPasswordPresenter(activity28ModificaPasswordActivity view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    public void resetPassword(utenteModel utente){
        Call<messageResponse> call = apiService.updatePassword(utente);
        call.enqueue(new Callback<messageResponse>() {
            @Override
            public void onResponse(@NonNull Call<messageResponse> call, @NonNull Response<messageResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    view.onResetPasswordSuccess(response.body());
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
                        view.onResetPasswordFailure(errorResponse);
                    }else{
                        view.onResetPasswordFailure(new messageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<messageResponse> call, @NonNull Throwable t) {
                view.onResetPasswordFailure(new messageResponse("Errore di connessione: "+t.getMessage()));
            }
        });
    }
}
