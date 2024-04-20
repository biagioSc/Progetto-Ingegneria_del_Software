package com.example.dd24client.presenter;

import androidx.annotation.NonNull;

import com.example.dd24client.model.UtenteModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.views.Activity30ModificaProfiloView;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Presenter29ModificaProfilo {
    private Activity30ModificaProfiloView view;
    private final ApiService apiService;

    public Presenter29ModificaProfilo(Activity30ModificaProfiloView view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    public void resetUtente(UtenteModel utente){
        Call<MessageResponse> call = apiService.updateProfilo(utente);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                     view.onResetUtenteSuccess(response.body());
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
                        view.onResetUtenteFailure(errorResponse);
                    }else{
                        view.onResetUtenteFailure(new MessageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                view.onResetUtenteFailure(new MessageResponse("Errore di connessione: "+t.getMessage()));
            }
        });
    }

}
