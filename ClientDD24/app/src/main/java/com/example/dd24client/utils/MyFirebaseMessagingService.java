package com.example.dd24client.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.dd24client.model.UtenteModel;
import com.example.dd24client.views.Activity01Main;
import com.example.dd24client.views.Activity15HomeAcquirente;
import com.example.dd24client.views.Activity34HomeVenditore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMessagingServ";

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG, "Refreshed token: " + token);

    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "Messaggio ricevuto: " + remoteMessage);

        // Verifica se il messaggio contiene dati di notifica
        if (!remoteMessage.getData().isEmpty()) {
            // Recupera il titolo e il corpo della notifica dai dati del messaggio
            String title = remoteMessage.getData().get("title");
            String body = remoteMessage.getData().get("body");
            String customNumberStr = remoteMessage.getData().get("customNumber");
            Class<?> activity = Activity01Main.class;
            UtenteModel utente = loadPreferences();

            // Costruisci e mostra la notifica di sistema
            if (customNumberStr != null) {
                try {
                    int customNumber = Integer.parseInt(customNumberStr);

                    // Controllo sul valore di customNumber
                    switch (customNumber) {
                        case 15:
                            if(utente != null) {
                                activity = Activity15HomeAcquirente.class;
                                NotificationUtils.showNotification(getApplicationContext(), title, body, activity, utente.getEmail(), utente.getPassword());
                            }else{
                                NotificationUtils.showNotification(getApplicationContext(), title, body, activity, "", "");
                            }
                            break;
                        case 34:
                            if(utente != null) {
                                activity = Activity34HomeVenditore.class;
                                NotificationUtils.showNotification(getApplicationContext(), title, body, activity, utente.getEmail(), utente.getPassword());
                            }else{
                                NotificationUtils.showNotification(getApplicationContext(), title, body, activity, "", "");
                            }
                            break;
                        default:
                            // Se il valore di customNumber non corrisponde a nessun caso previsto, mostra la notifica
                            NotificationUtils.showNotification(getApplicationContext(), title, body, activity, "", "");
                            break;
                    }
                } catch (NumberFormatException e) {
                    // Gestione dell'eccezione se customNumber non è un intero
                    Log.e(TAG, "NumberFormatException: " + e.getMessage());
                    // Se si verifica un errore, mostra la notifica
                    NotificationUtils.showNotification(getApplicationContext(), title, body, activity, "", "");
                }
            } else {
                // Il messaggio non contiene il campo customNumber
                // Mostra la notifica se il campo non è presente
                NotificationUtils.showNotification(getApplicationContext(), title, body, activity, "", "");
            }
        } else {
            // Il messaggio non contiene dati di notifica
            Log.d(TAG, "Il messaggio ricevuto non contiene dati di notifica.");
        }
    }

    private UtenteModel loadPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
        boolean isRicordami = sharedPreferences.getBoolean("Ricordami", false);

        if (isRicordami) {
            String email = sharedPreferences.getString("email", "");
            String password = sharedPreferences.getString("Password", "");

            return new UtenteModel(email, password);
        }
        return null;
    }

}
