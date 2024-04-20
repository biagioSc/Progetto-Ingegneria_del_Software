package com.example.serverdd24.Service;

import jakarta.mail.event.MailEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

import java.util.HashMap;
import java.util.Map;


@Service
public class NotificationService {

    @Autowired
    private FirebaseMessaging firebaseMessaging;

    public void sendNotificationToSeller(String sellerToken, String title, String body, int customNumber) {
        // Creazione del payload della notifica
        Map<String, String> notificationPayload = new HashMap<>();
        notificationPayload.put("title", title);
        notificationPayload.put("body", body);
        notificationPayload.put("customNumber", String.valueOf(customNumber)); // Converti il numero intero in una stringa

        // Creazione del messaggio di notifica
        Message message = Message.builder()
                .putAllData(notificationPayload)
                .setToken(sellerToken)
                .build();

        // Invio della notifica al venditore
        try {
            System.out.println(message);

            firebaseMessaging.send(message);
            System.out.println("Notifica inviata al venditore con successo.");
        } catch (FirebaseMessagingException e) {
            System.err.println("Errore durante l'invio della notifica al venditore: " + e.getMessage());
            // Gestire l'eccezione in base al caso d'uso specifico
        }
    }

}

