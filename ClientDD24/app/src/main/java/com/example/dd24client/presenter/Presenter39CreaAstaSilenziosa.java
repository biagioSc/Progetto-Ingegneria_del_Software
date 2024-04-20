package com.example.dd24client.presenter;

import androidx.annotation.NonNull;

import com.example.dd24client.model.AstasilenziosaModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.views.Activity39CreaASView;
import com.example.dd24client.views.Activity48VendiASView;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Presenter39CreaAstaSilenziosa {
    private Activity39CreaASView view;
    private Activity48VendiASView view2;
    private final ApiService apiService;

    public Presenter39CreaAstaSilenziosa(Activity39CreaASView view, ApiService apiService){
        this.view = view;
        this.apiService = apiService;

    }

    public Presenter39CreaAstaSilenziosa(Activity48VendiASView view, ApiService apiService){
        this.view2 = view;
        this.apiService = apiService;

    }

    public void creaAstasilenziosa1(AstasilenziosaModel asta) {
        Call<MessageResponse> call = apiService.creaAstasilenziosa(asta);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onCreateSuccess(response.body());
                }else {
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
                        view.onCreateFailure(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view.onCreateFailure(new MessageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                view.onCreateFailure(new MessageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }

    public void creaAstasilenziosa2(AstasilenziosaModel asta) {
        Call<MessageResponse> call = apiService.creaAstasilenziosa(asta);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view2.onCreateSuccess(response.body());
                }else {
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
                        view2.onCreateFailure(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view2.onCreateFailure(new MessageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                view2.onCreateFailure(new MessageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }
}
