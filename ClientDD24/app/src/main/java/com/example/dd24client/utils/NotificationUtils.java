package com.example.dd24client.utils;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.example.dd24client.R;
import com.example.dd24client.views.Activity01Main;

public class NotificationUtils {

    private static final String CHANNEL_ID = "channel_id"; // Identificatore del canale di notifica

    // Metodo per mostrare una notifica
    public static void showNotification(Context context, String title, String body, Class<?> activity, String email, String password) {
        // Controlla se il dispositivo sta eseguendo Android Oreo o versioni successive
        {
            // Se il dispositivo è Android Oreo o successivo, crea un canale di notifica
            CharSequence name = "Channel Name";
            String description = "Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = null;
            channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = null;
            notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(context, activity); // Sostituisci MainActivity con il nome della tua activity principale
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if(activity != Activity01Main.class){
            intent.putExtra("email", email);
            intent.putExtra("password", password);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Costruisci la notifica utilizzando NotificationCompat.Builder
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.image_logodd24) // Icona della notifica
                .setContentTitle(title) // Titolo della notifica
                .setContentText(body) // Testo della notifica
                .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Priorità della notifica
                .setContentIntent(pendingIntent)
                .setAutoCancel(true); // Per chiudere la notifica dopo il tocco

        // Ottieni il NotificationManagerCompat per mostrare la notifica
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        int notificationId = (int) System.currentTimeMillis(); // Identificatore univoco per la notifica
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            // Il permesso è stato concesso, mostra la notifica
            notificationManager.notify(notificationId, notificationBuilder.build());
        }
    }


}
