package com.example.dd24client.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dd24client.model.UtenteModel;
import com.example.dd24client.R;
import com.example.dd24client.utils.UtilsFunction;
import com.google.firebase.analytics.FirebaseAnalytics;

public class Activity04 extends AppCompatActivity {
    private UtilsFunction function;
    private ImageButton imageButtonContinua;
    private ImageView imageViewIndietro;
    private final String nome="";
    private final String cognome="";
    private final String nazionalita="";
    private final String citta="";
    private final String biografia="";
    private final String socialLinks="";
    private final String linkWeb="";
    private final String email="";
    private final String password="";
    private final String confermaPassword="";
    boolean terms=false;
    private final String nomeStringa = "nome";
    private final String cognomeStringa = "cognome";
    private final String nazionalitaStringa = "nazionalita";
    private final String cittaStringa = "citta";
    private final String biografiaStringa = "biografia";
    private final String linkWebStringa = "linkWeb";
    private final String socialLinksStringa = "socialLinks";
    private final String emailStringa = "email";
    private final String passwordStringa = "password";
    private final String confermaPasswordStringa = "confermaPassword";
    private final String termsStringa = "terms";

    @Override
    protected void onCreate(Bundle savedIstanceState){
        super.onCreate(savedIstanceState);
        setContentView(R.layout.activity_04_splashscreen3);
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity04", null);

        setComponents();
        setListener();

        setSplashPreferences();
    }

    private void setComponents() {
        function = new UtilsFunction();

        imageButtonContinua = findViewById(R.id.buttonContinua);
        imageViewIndietro = findViewById(R.id.imageButtonIndietro);
    }

    private void setListener() {
        function.setTouchListenerForAnimation(imageButtonContinua, this);
        function.setTouchListenerForAnimation(imageViewIndietro, this);

        imageButtonContinua.setOnClickListener(v -> {
            String messageString = "Cliccato 'continua' in splashscreen3";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            UtenteModel utente = loadPreferences();

            Intent intent;
            if(utente != null){
                intent = new Intent(Activity04.this, Activity15HomeAcquirente.class);
                intent.putExtra("email",utente.getEmail());
                intent.putExtra("password",utente.getPassword());
            }else{
                intent = new Intent(Activity04.this, Activity05Welcome.class);
                intent.putExtra(nomeStringa, nome);
                intent.putExtra(cognomeStringa, cognome);
                intent.putExtra(nazionalitaStringa, nazionalita);
                intent.putExtra(cittaStringa, citta);
                intent.putExtra(biografiaStringa, biografia);
                intent.putExtra(linkWebStringa, linkWeb);
                intent.putExtra(socialLinksStringa, socialLinks);
                intent.putExtra(emailStringa, email);
                intent.putExtra(passwordStringa, password);
                intent.putExtra(confermaPasswordStringa, confermaPassword);
                intent.putExtra(termsStringa, terms);
            }
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

        imageViewIndietro.setOnClickListener(v -> {
            String messageString = "Cliccato 'indietro' in splashscreen3";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(Activity04.this, Activity03.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });
    }

    private UtenteModel loadPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
        boolean isRicordami = sharedPreferences.getBoolean("Ricordami", false);

        if (isRicordami) {
            String email = sharedPreferences.getString("Username", "");
            String password = sharedPreferences.getString("Password", "");

            return new UtenteModel(email, password);
        }
        return null;
    }

    private void setSplashPreferences() {
        SharedPreferences prefs = getSharedPreferences("MyAppPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }
}
