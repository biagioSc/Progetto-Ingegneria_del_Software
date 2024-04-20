package com.example.dd24client.presenter;

import androidx.annotation.NonNull;

import com.example.dd24client.model.astaribassoModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.utils.messageResponse;
import com.example.dd24client.views.activity38CreaARView;
import com.example.dd24client.views.activity47VendiARActivity;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class presenter38CreaAstaRibassoPresenter {

    private activity38CreaARView view;

    private activity47VendiARActivity view2;

    private final ApiService apiService;

    public presenter38CreaAstaRibassoPresenter(activity38CreaARView view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    public presenter38CreaAstaRibassoPresenter(activity47VendiARActivity view, ApiService apiService) {
        this.view2 = view;
        this.apiService = apiService;
    }

    public void creaAstaribasso1(astaribassoModel asta) {
        Call<messageResponse> call = apiService.creaAstaribasso(asta);
        call.enqueue(new Callback<messageResponse>() {
            @Override
            public void onResponse(@NonNull Call<messageResponse> call, @NonNull Response<messageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onCreateSuccess(response.body());
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
                        view.onCreateFailure(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view.onCreateFailure(new messageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<messageResponse> call, @NonNull Throwable t) {
                view.onCreateFailure(new messageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }

    public void creaAstaribasso2(astaribassoModel asta) {
        Call<messageResponse> call = apiService.creaAstaribasso(asta);
        call.enqueue(new Callback<messageResponse>() {
            @Override
            public void onResponse(@NonNull Call<messageResponse> call, @NonNull Response<messageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view2.onCreateSuccess(response.body());
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
                        view2.onCreateFailure(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view2.onCreateFailure(new messageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<messageResponse> call, @NonNull Throwable t) {
                view2.onCreateFailure(new messageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }
}
