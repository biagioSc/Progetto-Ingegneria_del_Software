package com.example.dd24client.presenter;



import androidx.annotation.NonNull;

import com.example.dd24client.model.UtenteModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.views.Activity09SignupView;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Presenter07Signup {
    private final Activity09SignupView view;
    private final ApiService apiService;

    public Presenter07Signup(Activity09SignupView view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    public void signupUser(UtenteModel utente) {
        Call<MessageResponse> call = apiService.signupUser(utente);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onSignupSuccess(response.body());
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
                        view.onSignupFailure(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view.onSignupFailure(new MessageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                view.onSignupFailure(new MessageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }

}
