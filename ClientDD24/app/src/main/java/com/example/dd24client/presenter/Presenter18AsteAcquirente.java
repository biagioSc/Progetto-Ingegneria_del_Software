package com.example.dd24client.presenter;

import androidx.annotation.NonNull;

import com.example.dd24client.network.ApiService;
import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.utils.MessageResponseSilenziosa;
import com.example.dd24client.views.Activity18AsteAcquirenteView;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Presenter18AsteAcquirente {
    private Activity18AsteAcquirenteView view;
    private ApiService apiService;

    public Presenter18AsteAcquirente(Activity18AsteAcquirenteView view, ApiService apiService){
        this.view = view;
        this.apiService = apiService;
    }

    public void astaSilenziosa(String email) {
        Call<MessageResponseSilenziosa> call = apiService.richiediAstaSilenziosaAcquirente(email);
        call.enqueue(new Callback<MessageResponseSilenziosa>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponseSilenziosa> call, @NonNull Response<MessageResponseSilenziosa> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.scaricaAstaSilenziosa(response.body());
                } else {
                    MessageResponse errorResponse = null;
                    if (response.errorBody() != null) {
                        try {
                            // Converti errorBody in stringa, poi deserializza nel tuo tipo di errore atteso
                            String errorBodyStr = response.errorBody().string();
                            // Utilizza Gson (o un'altra libreria di deserializzazione) per convertire la stringa in MessageResponseOggetti
                            errorResponse = new Gson().fromJson(errorBodyStr, MessageResponse.class);
                        } catch (IOException e) {
                            String errorBodyStr = "Errore sconosciuto";
                            // Utilizza Gson (o un'altra libreria di deserializzazione) per convertire la stringa in MessageResponse
                            errorResponse = new Gson().fromJson(errorBodyStr, MessageResponse.class);
                        }
                    }
                    if (errorResponse != null) {
                        view.mostraErroreSilenziosa(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view.mostraErroreSilenziosa(new MessageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponseSilenziosa> call, @NonNull Throwable t) {
                view.mostraErroreSilenziosa(new MessageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }

}
