package com.example.dd24client.presenter;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.dd24client.model.AcquistaastaribassoModel;
import com.example.dd24client.model.AstaribassoseguiteModel;
import com.example.dd24client.model.AstasilenziosaseguiteModel;
import com.example.dd24client.model.OffertaastasilenziosaModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.utils.MessageResponseUtente;
import com.example.dd24client.views.Activity19DettaglioAstaRibassoApertaView;
import com.example.dd24client.views.Activity21DettaglioAstaSilenziosaView;
import com.example.dd24client.views.Activity23DettaglioAstaSilenziosaChiusaView;
import com.example.dd24client.views.Activity24DettaglioAstaRibassoChiusaView;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Presenter19Dettaglio {
    private Activity19DettaglioAstaRibassoApertaView view;
    private Activity21DettaglioAstaSilenziosaView view2;
    private Activity23DettaglioAstaSilenziosaChiusaView view3;
    private Activity24DettaglioAstaRibassoChiusaView view4;
    private ApiService apiService;

    public Presenter19Dettaglio(Activity19DettaglioAstaRibassoApertaView view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    public Presenter19Dettaglio(Activity21DettaglioAstaSilenziosaView view, ApiService apiService) {
        this.view2 = view;
        this.apiService = apiService;
    }

    public Presenter19Dettaglio(Activity23DettaglioAstaSilenziosaChiusaView view, ApiService apiService) {
        this.view3 = view;
        this.apiService = apiService;
    }

    public Presenter19Dettaglio(Activity24DettaglioAstaRibassoChiusaView view, ApiService apiService) {
        this.view4 = view;
        this.apiService = apiService;
    }

    public void datiUtente19(String email) {
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
    public void datiUtente21(String email) {
        Call<MessageResponseUtente> call = apiService.datiUtente(email);
        call.enqueue(new Callback<MessageResponseUtente>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponseUtente> call, @NonNull Response<MessageResponseUtente> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view2.utenteTrovato(response.body());
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
                        view2.utenteNonTrovato(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view2.utenteNonTrovato(new MessageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponseUtente> call, @NonNull Throwable t) {
                view2.utenteNonTrovato(new MessageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }
    public void datiUtente23(String email) {
        Call<MessageResponseUtente> call = apiService.datiUtente(email);
        call.enqueue(new Callback<MessageResponseUtente>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponseUtente> call, @NonNull Response<MessageResponseUtente> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view3.utenteTrovato(response.body());
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
                        view3.utenteNonTrovato(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view3.utenteNonTrovato(new MessageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponseUtente> call, @NonNull Throwable t) {
                view3.utenteNonTrovato(new MessageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }
    public void datiUtente24(String email) {
        Call<MessageResponseUtente> call = apiService.datiUtente(email);
        call.enqueue(new Callback<MessageResponseUtente>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponseUtente> call, @NonNull Response<MessageResponseUtente> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view4.utenteTrovato(response.body());
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
                        view4.utenteNonTrovato(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view4.utenteNonTrovato(new MessageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponseUtente> call, @NonNull Throwable t) {
                view4.utenteNonTrovato(new MessageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }

    public void creaOfferta21(OffertaastasilenziosaModel asta) {
        Call<MessageResponse> call = apiService.creaOfferta(asta);
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

    public void acquisto19(AcquistaastaribassoModel asta) {
        Call<MessageResponse> call = apiService.acquisto(asta);
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

    public void seguiAsta19(int id, String email) {
        Log.d("SEGUITAPROVA19", String.valueOf(id));
        AstaribassoseguiteModel segui = new AstaribassoseguiteModel(id, email);
        Call<MessageResponse> call = apiService.seguiAstaRibasso(segui);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onCreateSuccessSegui(response.body());
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
                        view.onCreateFailureSegui(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view.onCreateFailureSegui(new MessageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                view.onCreateFailureSegui(new MessageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }

    public void seguiAsta21(int idastasilenziosa, String emailutente) {
        Log.d("SEGUITAPROVA21", String.valueOf(idastasilenziosa));
        AstasilenziosaseguiteModel segui = new AstasilenziosaseguiteModel(idastasilenziosa, emailutente);
        Call<MessageResponse> call = apiService.seguiAstaSilenziosa(segui);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view2.onCreateSuccessSegui(response.body());
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
                        view2.onCreateFailureSegui(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view2.onCreateFailureSegui(new MessageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                view2.onCreateFailureSegui(new MessageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }

    public void seguiAsta23(int id, String email) {
        AstasilenziosaseguiteModel segui = new AstasilenziosaseguiteModel(id, email);
        Call<MessageResponse> call = apiService.seguiAstaSilenziosa(segui);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view3.onCreateSuccess(response.body());
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
                        view3.onCreateFailure(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view3.onCreateFailure(new MessageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                view3.onCreateFailure(new MessageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }

    public void seguiAsta24(int id, String email) {
        AstaribassoseguiteModel segui = new AstaribassoseguiteModel(id, email);
        Call<MessageResponse> call = apiService.seguiAstaRibasso(segui);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view4.onCreateSuccess(response.body());
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
                        view4.onCreateFailure(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view4.onCreateFailure(new MessageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                view4.onCreateFailure(new MessageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }

    public void seguitaboolAsta19(int id, String email) {
        Call<MessageResponse> call = apiService.seguitaboolAstaRibasso(id, email);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onSuccessSegui(response.body());
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
                        view.onFailureSegui(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view.onFailureSegui(new MessageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                view.onFailureSegui(new MessageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }

    public void seguitaboolAsta21(int id, String email) {
        Call<MessageResponse> call = apiService.seguitaboolAstaSilenziosa(id, email);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view2.onSuccessSegui(response.body());
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
                        view2.onFailureSegui(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view2.onFailureSegui(new MessageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                view2.onFailureSegui(new MessageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }

    public void seguitaboolAsta23(int id, String email) {
        Call<MessageResponse> call = apiService.seguitaboolAstaSilenziosa(id, email);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view3.onSuccessSegui(response.body());
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
                        view3.onFailureSegui(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view3.onFailureSegui(new MessageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                view3.onFailureSegui(new MessageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }

    public void seguitaboolAsta24(int id, String email) {
        Call<MessageResponse> call = apiService.seguitaboolAstaRibasso(id, email);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view4.onSuccessSegui(response.body());
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
                        view4.onFailureSegui(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view4.onFailureSegui(new MessageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                view4.onFailureSegui(new MessageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }

}
