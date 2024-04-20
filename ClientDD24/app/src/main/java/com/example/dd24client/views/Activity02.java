package com.example.dd24client.views;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.dd24client.R;
import com.example.dd24client.utils.UtilsFunction;
import com.google.firebase.analytics.FirebaseAnalytics;

public class Activity02 extends Activity {
    private UtilsFunction function;
    private TextView textViewSalta;
    private ImageButton imageButtonContinua;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_02_splashscreen1);
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity02", null);

        setComponents();
        setListener();
    }

    private void setComponents() {
        function = new UtilsFunction();

        textViewSalta = findViewById(R.id.textViewSalta);
        imageButtonContinua = findViewById(R.id.buttonContinua);
    }

    private void setListener() {

        function.setTouchListenerForAnimation(imageButtonContinua, this);
        function.setTouchListenerForAnimation(textViewSalta, this);

        textViewSalta.setOnClickListener(v -> {
            String messageString = "Cliccato 'salta' in splashscreen1";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            setSplashPreferences();

            Intent intent = new Intent(Activity02.this, Activity05Welcome.class);
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
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

        imageButtonContinua.setOnClickListener(v -> {
            String messageString = "Cliccato 'continua' in splashscreen1";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(Activity02.this, Activity03.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });
    }

    private void setSplashPreferences() {
        SharedPreferences prefs = getSharedPreferences("MyAppPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

}
