package com.example.dd24client.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import com.example.dd24client.model.UtenteModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.network.RetrofitClient;
import com.example.dd24client.presenter.Presenter01Main;
import com.example.dd24client.R;
import com.example.dd24client.utils.MessageResponse;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;

public class Activity01Main extends AppCompatActivity implements Activity01MainView {
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private Presenter01Main presenter;
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

    // Array di permessi richiesti
    private final String[] permissions = {
            Manifest.permission.INTERNET,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NOTIFICATION_POLICY,
    };

    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_01_main);

        FirebaseApp.initializeApp(this);
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity01Main", null);

        if (!checkPermissions()) {
            // Richiedi i permessi se non sono già stati concessi
            requestPermissions();
        } else {
            presenter = new Presenter01Main(this, apiService);
            presenter.verifyConnection();
        }


    }

    // Controlla se tutti i permessi richiesti sono stati concessi
    private boolean checkPermissions() {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    // Richiede i permessi all'utente
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
    }

    // Gestisce la risposta dell'utente alla richiesta di permessi
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            // Controlla se tutti i permessi sono stati concessi
            boolean allPermissionsGranted = true;
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }
            if (allPermissionsGranted) {
                presenter = new Presenter01Main(this, apiService);
                presenter.verifyConnection();
            } else {
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(Activity01Main.this, permissions[i] + ": Permesso negato!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    @Override
    public void onPingSuccess(MessageResponse message) {
        SharedPreferences prefs = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
        boolean isFirstStart = prefs.getBoolean("firstStart", true);

        if (isFirstStart) {
            showSplashScreens();
        }else {
            UtenteModel utente = loadPreferences();
            if(utente != null){
                new Handler().postDelayed(() -> {
                    Intent intent = new Intent(this, Activity15HomeAcquirente.class);
                    intent.putExtra("email",utente.getEmail());
                    intent.putExtra("password",utente.getPassword());
                    String messageString = "Accesso eseguito come: " + utente.getEmail();
                    String logging = "[USABILITA' UTENTE]";
                    Log.d(logging, messageString);
                    Toast.makeText(Activity01Main.this, messageString, Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                }, 3000);
            }else{
                new Handler().postDelayed(() -> {
                    Intent intent = new Intent(this, Activity05Welcome.class);
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
                }, 3000);
            }
        }
    }

    @Override
    public void onPingFailure(MessageResponse message) {
        String messageString = "Connessione non riuscita!";

        Toast.makeText(Activity01Main.this, messageString, Toast.LENGTH_SHORT).show();
        presenter.verifyConnection();
    }

    private void showSplashScreens() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(this, Activity02.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }, 3000);
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