package com.example.dd24client.presenter;

import androidx.annotation.NonNull;

import com.example.dd24client.network.ApiService;
import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.utils.MessageResponseUtente;
import com.example.dd24client.views.Activity27ProfiloView;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Presenter27Profilo {
    private Activity27ProfiloView view;
    private ApiService apiService;

    public Presenter27Profilo(Activity27ProfiloView view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    public void datiUtente(String email) {
        Call<MessageResponseUtente> call = apiService.datiUtente(email);
        call.enqueue(new Callback<MessageResponseUtente>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponseUtente> call, @NonNull Response<MessageResponseUtente> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.utenteTrovato(response.body());
                } else {
                    MessageResponse errorResponse = null;
                    if (response.errorBody() != null) {
                        try {
                            // Converti errorBody in stringa, poi deserializza nel tuo tipo di errore atteso
                            String errorBodyStr = response.errorBody().string();
                            // Utilizza Gson (o un'altra libreria di deserializzazione) per convertire la stringa in MessageResponse
                            errorResponse = new Gson().fromJson(errorBodyStr, MessageResponse.class);
                        } catch (IOException e) {
                            String errorBodyStr = "Errore sconosciuto";
                            // Utilizza Gson (o un'altra libreria di deserializzazione) per convertire la stringa in MessageResponse
                            errorResponse = new Gson().fromJson(errorBodyStr, MessageResponse.class);
                        }
                    }
                    if (errorResponse != null) {
                        view.utenteNonTrovato(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view.utenteNonTrovato(new MessageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponseUtente> call, @NonNull Throwable t) {
                view.utenteNonTrovato(new MessageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }

}
