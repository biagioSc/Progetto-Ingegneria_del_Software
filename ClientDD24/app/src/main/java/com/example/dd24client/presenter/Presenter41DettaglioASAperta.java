package com.example.dd24client.presenter;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.dd24client.model.OffertaastasilenziosaModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.utils.MessageResponseOfferte;
import com.example.dd24client.views.Activity41DettaglioASApertaView;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Presenter41DettaglioASAperta {

    private Activity41DettaglioASApertaView view;
    private final ApiService apiService;

    public Presenter41DettaglioASAperta(Activity41DettaglioASApertaView view, ApiService apiService){
        this.view = view;
        this.apiService = apiService;

    }

    public void richiediOfferteAstaSilenziosa(Integer id) {
        Call<MessageResponseOfferte> call = apiService.richiediOfferteAstaSilenziosa(id);
        call.enqueue(new Callback<MessageResponseOfferte>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponseOfferte> call, @NonNull Response<MessageResponseOfferte> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.scaricaOfferta(response.body());
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
                        view.mostraErrore(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view.mostraErrore(new MessageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponseOfferte> call, @NonNull Throwable t) {
                view.mostraErrore(new MessageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }

    public void updateStatoOffertaAccettata(OffertaastasilenziosaModel offerta){
        Call<MessageResponse> call = apiService.updateStatoOfferta(offerta);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    view.onUpdateSuccessAccettata(response.body());
                }else{
                    MessageResponse errorResponse = null;
                    if(response.errorBody() != null){
                        try{
                            String errorBodyStr = response.errorBody().string();
                            errorResponse = new Gson().fromJson(errorBodyStr,MessageResponse.class);
                        }catch (IOException e){
                                                        String errorBodyStr = "Errore sconosciuto";
                            // Utilizza Gson (o un'altra libreria di deserializzazione) per convertire la stringa in MessageResponse
                            errorResponse = new Gson().fromJson(errorBodyStr, MessageResponse.class);
                        }
                    }
                    if(errorResponse != null){
                        view.onUpdateFailure(errorResponse);
                    }else{
                        view.onUpdateFailure(new MessageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                view.onUpdateFailure(new MessageResponse("Errore di connessione: "+t.getMessage()));
            }
        });
    }

    public void updateStatoOffertaRifiutata(OffertaastasilenziosaModel offerta, CardView card){
        Call<MessageResponse> call = apiService.updateStatoOfferta(offerta);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    view.onUpdateSuccessRifiutata(response.body(), card);
                }else{
                    MessageResponse errorResponse = null;
                    if(response.errorBody() != null){
                        try{
                            String errorBodyStr = response.errorBody().string();
                            errorResponse = new Gson().fromJson(errorBodyStr,MessageResponse.class);
                        }catch (IOException e){
                                                        String errorBodyStr = "Errore sconosciuto";
                            // Utilizza Gson (o un'altra libreria di deserializzazione) per convertire la stringa in MessageResponse
                            errorResponse = new Gson().fromJson(errorBodyStr, MessageResponse.class);
                        }
                    }
                    if(errorResponse != null){
                        view.onUpdateFailure(errorResponse);
                    }else{
                        view.onUpdateFailure(new MessageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                view.onUpdateFailure(new MessageResponse("Errore di connessione: "+t.getMessage()));
            }
        });
    }

}
