package com.example.dd24client;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {

    public static void savePreferences(Context context, String email, String password, boolean isRicordamiChecked) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            // Se email o password sono null o vuote, non salviamo le preferenze
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (isRicordamiChecked) {
            editor.putString("email", email);
            editor.putString("Password", password);
            editor.putBoolean("Ricordami", true);
        } else {
            editor.remove("email");
            editor.remove("Password");
            editor.putBoolean("Ricordami", false);
        }

        editor.apply();
    }
}
