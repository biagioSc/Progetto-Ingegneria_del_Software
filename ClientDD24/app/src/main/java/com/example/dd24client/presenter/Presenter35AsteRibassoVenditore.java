package com.example.dd24client.presenter;

import androidx.annotation.NonNull;

import com.example.dd24client.network.ApiService;
import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.utils.MessageResponseRibasso;
import com.example.dd24client.views.Activity35AsteRibassoVenditoreView;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Presenter35AsteRibassoVenditore {
    private Activity35AsteRibassoVenditoreView view;
    private ApiService apiService;

    public Presenter35AsteRibassoVenditore(Activity35AsteRibassoVenditoreView view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    public void astaRibasso(String email) {
        Call<MessageResponseRibasso> call = apiService.richiediAstaRibasso(email);
        call.enqueue(new Callback<MessageResponseRibasso>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponseRibasso> call, @NonNull Response<MessageResponseRibasso> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.scaricaAstaRibasso(response.body());
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
                        view.mostraErrore(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view.mostraErrore(new MessageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponseRibasso> call, @NonNull Throwable t) {
                view.mostraErrore(new MessageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }
}
