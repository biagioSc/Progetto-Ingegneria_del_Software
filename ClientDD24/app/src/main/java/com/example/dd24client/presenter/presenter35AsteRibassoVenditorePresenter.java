package com.example.dd24client.presenter;

import androidx.annotation.NonNull;

import com.example.dd24client.network.ApiService;
import com.example.dd24client.utils.messageResponse;
import com.example.dd24client.utils.messageResponseRibasso;
import com.example.dd24client.views.activity35AsteRibassoVenditoreView;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class presenter35AsteRibassoVenditorePresenter {
    private final activity35AsteRibassoVenditoreView view;
    private final ApiService apiService;

    public presenter35AsteRibassoVenditorePresenter(activity35AsteRibassoVenditoreView view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    public void astaRibasso(String email) {
        Call<messageResponseRibasso> call = apiService.richiediAstaRibasso(email);
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
                        }catch (IOException ignored) {
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
            public void onFailure(@NonNull Call<messageResponseRibasso> call, @NonNull Throwable t) {
                view.mostraErrore(new messageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }
}
