package com.example.dd24client.presenter;

import androidx.annotation.NonNull;

import com.example.dd24client.network.ApiService;
import com.example.dd24client.utils.messageResponse;
import com.example.dd24client.utils.messageResponseRibasso;
import com.example.dd24client.utils.messageResponseSilenziosa;
import com.example.dd24client.views.Activity18AsteAcquirente2View;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class presenter18AsteAcquirente2Presenter {
    private final Activity18AsteAcquirente2View view;
    private final ApiService apiService;

    public presenter18AsteAcquirente2Presenter(Activity18AsteAcquirente2View view, ApiService apiService){
        this.view = view;
        this.apiService = apiService;
    }

    public void astaSilenziosa() {
        Call<messageResponseSilenziosa> call = apiService.richiediAstaSilenziosa();
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
                        view.mostraErroreSilenziosa(errorResponse);
                    } else {
                        String message = "Errore sconosciuto";
                        view.mostraErroreSilenziosa(new messageResponse(message));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<messageResponseSilenziosa> call, @NonNull Throwable t) {
                String message = "Errore di connessione: " + t.getMessage();
                view.mostraErroreSilenziosa(new messageResponse(message));
            }
        });
    }

}
