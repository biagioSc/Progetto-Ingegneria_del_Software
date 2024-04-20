package com.example.dd24client.presenter;

import androidx.annotation.NonNull;

import com.example.dd24client.network.ApiService;
import com.example.dd24client.utils.messageResponse;
import com.example.dd24client.utils.messageResponseSilenziosa;
import com.example.dd24client.views.activity36AsteSilenziosaVenditoreView;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class presenter36AsteSilenziosaVenditorePresenter {
    private final activity36AsteSilenziosaVenditoreView view;
    private final ApiService apiService;

    public presenter36AsteSilenziosaVenditorePresenter(activity36AsteSilenziosaVenditoreView view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    public void astaSilenziosa(String email) {
        Call<messageResponseSilenziosa> call = apiService.richiediAstaSilenziosa(email);
        call.enqueue(new Callback<messageResponseSilenziosa>() {
            @Override
            public void onResponse(@NonNull Call<messageResponseSilenziosa> call, @NonNull Response<messageResponseSilenziosa> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.scaricaAstaSilenziosa(response.body());
                } else {
                    messageResponse errorResponse = null;
                    if (response.errorBody() != null) {
                        try {
                            // Converti errorBody in stringa, poi deserializza nel tuo tipo di errore atteso
                            String errorBodyStr = response.errorBody().string();
                            // Utilizza Gson (o un'altra libreria di deserializzazione) per convertire la stringa in MessageResponseOggetti
                            errorResponse = new Gson().fromJson(errorBodyStr, messageResponse.class);
                        } catch (IOException ignored) {
                        }
                    }
                    if (errorResponse != null) {
                        view.mostraErrore(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view.mostraErrore(new messageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<messageResponseSilenziosa> call, @NonNull Throwable t) {
                view.mostraErrore(new messageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }
}
