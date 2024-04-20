package com.example.dd24client.presenter;

import androidx.annotation.NonNull;

import com.example.dd24client.network.ApiService;
import com.example.dd24client.utils.messageResponse;
import com.example.dd24client.utils.messageResponseRibasso;
import com.example.dd24client.views.activity17AsteAcquirenteView;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class presenter17AsteAcquirentePresenter {
    private final activity17AsteAcquirenteView view;
    private final ApiService apiService;

    public presenter17AsteAcquirentePresenter(activity17AsteAcquirenteView view, ApiService apiService){
        this.view = view;
        this.apiService = apiService;
    }

    public void astaRibasso() {
        Call<messageResponseRibasso> call = apiService.richiediAstaRibasso();
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
                        view.mostraErroreRibasso(errorResponse);
                    } else {
                        String message = "Errore sconosciuto";
                        view.mostraErroreRibasso(new messageResponse(message));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<messageResponseRibasso> call, @NonNull Throwable t) {
                String message = "Errore di connessione: " + t.getMessage();
                view.mostraErroreRibasso(new messageResponse(message));
            }
        });
    }

}
