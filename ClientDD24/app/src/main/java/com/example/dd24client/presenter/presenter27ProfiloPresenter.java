package com.example.dd24client.presenter;

import androidx.annotation.NonNull;

import com.example.dd24client.network.ApiService;
import com.example.dd24client.utils.messageResponse;
import com.example.dd24client.utils.messageResponseUtente;
import com.example.dd24client.views.activity27ProfiloView;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class presenter27ProfiloPresenter {

    private final activity27ProfiloView view;
    private final ApiService apiService;

    public presenter27ProfiloPresenter(activity27ProfiloView view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    public void datiUtente(String email) {
        Call<messageResponseUtente> call = apiService.datiUtente(email);
        call.enqueue(new Callback<messageResponseUtente>() {
            @Override
            public void onResponse(@NonNull Call<messageResponseUtente> call, @NonNull Response<messageResponseUtente> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.utenteTrovato(response.body());
                } else {
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
                        view.utenteNonTrovato(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view.utenteNonTrovato(new messageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<messageResponseUtente> call, @NonNull Throwable t) {
                view.utenteNonTrovato(new messageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }

}
