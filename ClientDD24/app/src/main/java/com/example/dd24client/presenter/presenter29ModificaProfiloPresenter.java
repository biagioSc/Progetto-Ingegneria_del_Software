package com.example.dd24client.presenter;

import androidx.annotation.NonNull;

import com.example.dd24client.model.utenteModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.utils.messageResponse;
import com.example.dd24client.views.activity30ModificaProfiloView;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class presenter29ModificaProfiloPresenter {

    private final activity30ModificaProfiloView view;
    private final ApiService apiService;

    public presenter29ModificaProfiloPresenter(activity30ModificaProfiloView view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    public void resetUtente(utenteModel utente){
        Call<messageResponse> call = apiService.updateProfilo(utente);
        call.enqueue(new Callback<messageResponse>() {
            @Override
            public void onResponse(@NonNull Call<messageResponse> call, @NonNull Response<messageResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    view.onResetUtenteSuccess(response.body());
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
                        view.onResetUtenteFailure(errorResponse);
                    }else{
                        view.onResetUtenteFailure(new messageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<messageResponse> call, @NonNull Throwable t) {
                view.onResetUtenteFailure(new messageResponse("Errore di connessione: "+t.getMessage()));
            }
        });
    }

}
