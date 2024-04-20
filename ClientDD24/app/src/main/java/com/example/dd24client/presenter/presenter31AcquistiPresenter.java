package com.example.dd24client.presenter;

import androidx.annotation.NonNull;

import com.example.dd24client.network.ApiService;
import com.example.dd24client.utils.messageResponse;
import com.example.dd24client.utils.messageResponseRibasso;
import com.example.dd24client.utils.messageResponseSilenziosa;
import com.example.dd24client.views.activity31AcquistiView;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class presenter31AcquistiPresenter {
    private final activity31AcquistiView view;
    private final ApiService apiService;

    public presenter31AcquistiPresenter(activity31AcquistiView view, ApiService apiService){
        this.view = view;
        this.apiService = apiService;
    }

    public void astaRibasso(String email) {
        Call<messageResponseRibasso> call = apiService.acquistiAstaRibasso(email);
        call.enqueue(new Callback<messageResponseRibasso>() {
            @Override
            public void onResponse(@NonNull Call<messageResponseRibasso> call, @NonNull Response<messageResponseRibasso> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.scaricaAstaRibasso(response.body());
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
                        view.mostraErroreRibasso(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view.mostraErroreRibasso(new messageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<messageResponseRibasso> call, @NonNull Throwable t) {
                view.mostraErroreRibasso(new messageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }

    public void astaSilenziosa(String email) {
        Call<messageResponseSilenziosa> call = apiService.acquistiAstaSilenziosa(email);
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
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view.mostraErroreSilenziosa(new messageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<messageResponseSilenziosa> call, @NonNull Throwable t) {
                view.mostraErroreSilenziosa(new messageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }


}

