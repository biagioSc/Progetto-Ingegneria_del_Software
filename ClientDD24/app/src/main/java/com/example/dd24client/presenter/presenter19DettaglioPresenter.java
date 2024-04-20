package com.example.dd24client.presenter;

import androidx.annotation.NonNull;

import com.example.dd24client.model.acquistaastaribassoModel;
import com.example.dd24client.model.astaribassoseguiteModel;
import com.example.dd24client.model.astasilenziosaseguiteModel;
import com.example.dd24client.model.offertaastasilenziosaModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.utils.messageResponse;
import com.example.dd24client.utils.messageResponseUtente;
import com.example.dd24client.views.activity19DettaglioAstaRibassoView;
import com.example.dd24client.views.activity21DettaglioAstaSilenziosaView;
import com.example.dd24client.views.activity23DettaglioAstaSilenziosaChiusaView;
import com.example.dd24client.views.activity24DettaglioAstaRibassoChiusaView;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class presenter19DettaglioPresenter {
    private activity19DettaglioAstaRibassoView view;
    private activity21DettaglioAstaSilenziosaView view2;
    private activity23DettaglioAstaSilenziosaChiusaView view3;
    private activity24DettaglioAstaRibassoChiusaView view4;

    private final ApiService apiService;

    public presenter19DettaglioPresenter(activity19DettaglioAstaRibassoView view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    public presenter19DettaglioPresenter(activity21DettaglioAstaSilenziosaView view, ApiService apiService) {
        this.view2 = view;
        this.apiService = apiService;
    }

    public presenter19DettaglioPresenter(activity23DettaglioAstaSilenziosaChiusaView view, ApiService apiService) {
        this.view3 = view;
        this.apiService = apiService;
    }

    public presenter19DettaglioPresenter(activity24DettaglioAstaRibassoChiusaView view, ApiService apiService) {
        this.view4 = view;
        this.apiService = apiService;
    }

    public void datiUtente19(String email) {
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
    public void datiUtente21(String email) {
        Call<messageResponseUtente> call = apiService.datiUtente(email);
        call.enqueue(new Callback<messageResponseUtente>() {
            @Override
            public void onResponse(@NonNull Call<messageResponseUtente> call, @NonNull Response<messageResponseUtente> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view2.utenteTrovato(response.body());
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
                        view2.utenteNonTrovato(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view2.utenteNonTrovato(new messageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<messageResponseUtente> call, @NonNull Throwable t) {
                view2.utenteNonTrovato(new messageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }
    public void datiUtente23(String email) {
        Call<messageResponseUtente> call = apiService.datiUtente(email);
        call.enqueue(new Callback<messageResponseUtente>() {
            @Override
            public void onResponse(@NonNull Call<messageResponseUtente> call, @NonNull Response<messageResponseUtente> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view3.utenteTrovato(response.body());
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
                        view3.utenteNonTrovato(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view3.utenteNonTrovato(new messageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<messageResponseUtente> call, @NonNull Throwable t) {
                view3.utenteNonTrovato(new messageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }
    public void datiUtente24(String email) {
        Call<messageResponseUtente> call = apiService.datiUtente(email);
        call.enqueue(new Callback<messageResponseUtente>() {
            @Override
            public void onResponse(@NonNull Call<messageResponseUtente> call, @NonNull Response<messageResponseUtente> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view4.utenteTrovato(response.body());
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
                        view4.utenteNonTrovato(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view4.utenteNonTrovato(new messageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<messageResponseUtente> call, @NonNull Throwable t) {
                view4.utenteNonTrovato(new messageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }
    public void creaOfferta21(offertaastasilenziosaModel asta) {
        Call<messageResponse> call = apiService.creaOfferta(asta);
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

    public void acquisto19(acquistaastaribassoModel asta) {
        Call<messageResponse> call = apiService.acquisto(asta);
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

    public void seguiAsta19(int id, String email) {
        astaribassoseguiteModel segui = new astaribassoseguiteModel(id, email);
        Call<messageResponse> call = apiService.seguiAstaRibasso(segui);
        call.enqueue(new Callback<messageResponse>() {
            @Override
            public void onResponse(@NonNull Call<messageResponse> call, @NonNull Response<messageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onCreateSuccessSegui(response.body());
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
                        view.onCreateFailureSegui(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view.onCreateFailureSegui(new messageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<messageResponse> call, @NonNull Throwable t) {
                view.onCreateFailureSegui(new messageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }

    public void seguiAsta21(int idastasilenziosa, String emailutente) {
        astasilenziosaseguiteModel segui = new astasilenziosaseguiteModel(idastasilenziosa, emailutente);
        Call<messageResponse> call = apiService.seguiAstaSilenziosa(segui);
        call.enqueue(new Callback<messageResponse>() {
            @Override
            public void onResponse(@NonNull Call<messageResponse> call, @NonNull Response<messageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view2.onCreateSuccessSegui(response.body());
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
                        view2.onCreateFailureSegui(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view2.onCreateFailureSegui(new messageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<messageResponse> call, @NonNull Throwable t) {
                view2.onCreateFailureSegui(new messageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }

    public void seguiAsta23(int id, String email) {
        astasilenziosaseguiteModel segui = new astasilenziosaseguiteModel(id, email);
        Call<messageResponse> call = apiService.seguiAstaSilenziosa(segui);
        call.enqueue(new Callback<messageResponse>() {
            @Override
            public void onResponse(@NonNull Call<messageResponse> call, @NonNull Response<messageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view3.onCreateSuccess(response.body());
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
                        view3.onCreateFailure(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view3.onCreateFailure(new messageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<messageResponse> call, @NonNull Throwable t) {
                view3.onCreateFailure(new messageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }

    public void seguiAsta24(int id, String email) {
        astaribassoseguiteModel segui = new astaribassoseguiteModel(id, email);
        Call<messageResponse> call = apiService.seguiAstaRibasso(segui);
        call.enqueue(new Callback<messageResponse>() {
            @Override
            public void onResponse(@NonNull Call<messageResponse> call, @NonNull Response<messageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view4.onCreateSuccess(response.body());
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
                        view4.onCreateFailure(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view4.onCreateFailure(new messageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<messageResponse> call, @NonNull Throwable t) {
                view4.onCreateFailure(new messageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }

    public void seguitaboolAsta19(int id, String email) {
        Call<messageResponse> call = apiService.seguitaboolAstaRibasso(id, email);
        call.enqueue(new Callback<messageResponse>() {
            @Override
            public void onResponse(@NonNull Call<messageResponse> call, @NonNull Response<messageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.onSuccessSegui(response.body());
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
                        view.onFailureSegui(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view.onFailureSegui(new messageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<messageResponse> call, @NonNull Throwable t) {
                view.onFailureSegui(new messageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }

    public void seguitaboolAsta21(int id, String email) {
        Call<messageResponse> call = apiService.seguitaboolAstaSilenziosa(id, email);
        call.enqueue(new Callback<messageResponse>() {
            @Override
            public void onResponse(@NonNull Call<messageResponse> call, @NonNull Response<messageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view2.onSuccessSegui(response.body());
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
                        view2.onFailureSegui(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view2.onFailureSegui(new messageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<messageResponse> call, @NonNull Throwable t) {
                view2.onFailureSegui(new messageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }

    public void seguitaboolAsta23(int id, String email) {
        Call<messageResponse> call = apiService.seguitaboolAstaSilenziosa(id, email);
        call.enqueue(new Callback<messageResponse>() {
            @Override
            public void onResponse(@NonNull Call<messageResponse> call, @NonNull Response<messageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view3.onSuccessSegui(response.body());
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
                        view3.onFailureSegui(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view3.onFailureSegui(new messageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<messageResponse> call, @NonNull Throwable t) {
                view3.onFailureSegui(new messageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }

    public void seguitaboolAsta24(int id, String email) {
        Call<messageResponse> call = apiService.seguitaboolAstaRibasso(id, email);
        call.enqueue(new Callback<messageResponse>() {
            @Override
            public void onResponse(@NonNull Call<messageResponse> call, @NonNull Response<messageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view4.onSuccessSegui(response.body());
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
                        view4.onFailureSegui(errorResponse);
                    } else {
                        // Fallback in caso di errori nella deserializzazione o errorBody vuoto
                        view4.onFailureSegui(new messageResponse("Errore sconosciuto"));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<messageResponse> call, @NonNull Throwable t) {
                view4.onFailureSegui(new messageResponse("Errore di connessione: " + t.getMessage()));
            }
        });
    }

}
