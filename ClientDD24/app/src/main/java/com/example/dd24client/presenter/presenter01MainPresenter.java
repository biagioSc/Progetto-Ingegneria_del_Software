package com.example.dd24client.presenter;

import androidx.annotation.NonNull;

import com.example.dd24client.network.ApiService;
import com.example.dd24client.utils.messageResponse;
import com.example.dd24client.views.activity01MainView;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class presenter01MainPresenter {

    private final activity01MainView view;
    private final ApiService apiService;

    public presenter01MainPresenter(activity01MainView view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    public void verifyConnection() {
        Call<messageResponse> call = apiService.ping();
        call.enqueue(new Callback<messageResponse>() {
            @Override
            public void onResponse(@NonNull Call<messageResponse> call, @NonNull Response<messageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onPingSuccess(response.body());
                }else {
                    messageResponse errorResponse = null;
                    if (response.errorBody() != null) {
                        try {
                            // Converti errorBody in stringa, poi deserializza nel tuo tipo di errore atteso
                            String errorBodyStr = response.errorBody().string();
                            // Utilizza Gson (o un'altra libreria di deserializzazione) per convertire la stringa in MessageResponse
                            errorResponse = new Gson().fromJson(errorBodyStr, messageResponse.class);
                        } catch (IOException ignored) {
                        }
                    }
                    if (errorResponse != null) {
                        view.onPingFailure(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        String message = "Errore sconosciuto";
                        view.onPingFailure(new messageResponse(message));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<messageResponse> call, @NonNull Throwable t) {
                String message = "Errore di connessione" + t.getMessage();
                view.onPingFailure(new messageResponse(message));
            }
        });
    }

}
