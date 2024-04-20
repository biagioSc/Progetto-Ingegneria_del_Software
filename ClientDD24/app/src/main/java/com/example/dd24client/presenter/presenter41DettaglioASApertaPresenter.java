package com.example.dd24client.presenter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.dd24client.model.offertaastasilenziosaModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.utils.messageResponse;
import com.example.dd24client.utils.messageResponseOfferte;
import com.example.dd24client.views.activity41DettaglioASApertaView;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class presenter41DettaglioASApertaPresenter {

    private final activity41DettaglioASApertaView view;

    private final ApiService apiService;

    public presenter41DettaglioASApertaPresenter(activity41DettaglioASApertaView view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    public void richiediOfferteAstaSilenziosa(Integer id) {
        Call<messageResponseOfferte> call = apiService.richiediOfferteAstaSilenziosa(id);
        call.enqueue(new Callback<messageResponseOfferte>() {
            @Override
            public void onResponse(@NonNull Call<messageResponseOfferte> call, @NonNull Response<messageResponseOfferte> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.scaricaOfferta(response.body());
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
                        view.mostraErrore(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view.mostraErrore(new messageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<messageResponseOfferte> call, @NonNull Throwable t) {
                view.mostraErrore(new messageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }

    public void updateStatoOffertaAccettata(offertaastasilenziosaModel offerta){
        Log.d("DATI ASTA4",offerta.getIdastasilenziosa()+ " " + offerta.getprezzoofferto()+ " " + offerta.getemailutente()+ " " +offerta.getstatoaccettazione() );

        Call<messageResponse> call = apiService.updateStatoOfferta(offerta);
        call.enqueue(new Callback<messageResponse>() {
            @Override
            public void onResponse(@NonNull Call<messageResponse> call, @NonNull Response<messageResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    view.onUpdateSuccessAccettata(response.body());
                }else{
                    messageResponse errorResponse = null;
                    if(response.errorBody() != null){
                        try{
                            String errorBodyStr = response.errorBody().string();
                            errorResponse = new Gson().fromJson(errorBodyStr, messageResponse.class);
                        }catch (IOException ignored) {
                        }
                    }
                    if(errorResponse != null){
                        view.onUpdateFailure(errorResponse);
                    }else{
                        view.onUpdateFailure(new messageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<messageResponse> call, @NonNull Throwable t) {
                view.onUpdateFailure(new messageResponse("Errore di connessione: "+t.getMessage()));
            }
        });
    }

    public void updateStatoOffertaRifiutata(offertaastasilenziosaModel offerta, CardView card){
        Log.d("DATI ASTA3",offerta.getIdastasilenziosa()+ " " + offerta.getprezzoofferto()+ " " + offerta.getemailutente()+ " " +offerta.getstatoaccettazione() );

        Call<messageResponse> call = apiService.updateStatoOfferta(offerta);
        call.enqueue(new Callback<messageResponse>() {
            @Override
            public void onResponse(@NonNull Call<messageResponse> call, @NonNull Response<messageResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    view.onUpdateSuccessRifiutata(response.body(), card);
                }else{
                    messageResponse errorResponse = null;
                    if(response.errorBody() != null){
                        try{
                            String errorBodyStr = response.errorBody().string();
                            errorResponse = new Gson().fromJson(errorBodyStr, messageResponse.class);
                        }catch (IOException ignored) {
                        }
                    }
                    if(errorResponse != null){
                        view.onUpdateFailure(errorResponse);
                    }else{
                        view.onUpdateFailure(new messageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<messageResponse> call, @NonNull Throwable t) {
                view.onUpdateFailure(new messageResponse("Errore di connessione: "+t.getMessage()));
            }
        });
    }

}
