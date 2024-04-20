package com.example.dd24client.presenter;



import androidx.annotation.NonNull;

import com.example.dd24client.model.utenteModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.utils.messageResponse;
import com.example.dd24client.views.activity09SignupView;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class presenter07SignupPresenter {
    private final activity09SignupView view;
    private final ApiService apiService;

    public presenter07SignupPresenter(activity09SignupView view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    public void signupUser(utenteModel utente) {
        Call<messageResponse> call = apiService.signupUser(utente);
        call.enqueue(new Callback<messageResponse>() {
            @Override
            public void onResponse(@NonNull Call<messageResponse> call, @NonNull Response<messageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onSignupSuccess(response.body());
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
                        view.onSignupFailure(errorResponse);
                    } else {
                        String message = "Errore sconosciuto";
                        view.onSignupFailure(new messageResponse(message));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<messageResponse> call, @NonNull Throwable t) {
                String message = "Errore di connessione" + t.getMessage();
                view.onSignupFailure(new messageResponse(message));
            }
        });
    }

}
