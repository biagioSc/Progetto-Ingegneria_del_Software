package com.example.dd24client.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;

import android.widget.Spinner;

import com.example.dd24client.R;
import com.example.dd24client.utils.UtilsFunction;
import com.google.firebase.analytics.FirebaseAnalytics;

public class Activity46Vendi extends Activity {
    private UtilsFunction function;
    private ImageView indietroImageView;
    private Spinner tipologia;
    private String email="", password="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_46_vendi1); // Sostituisci con il tuo file layout
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity46Vendi", null);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.darkOrange));

        setComponents();
        setListener();
        setExtraParameters();

    }

    private void setComponents() {
        function = new UtilsFunction();

        indietroImageView = findViewById(R.id.indietroImageView);
        tipologia = findViewById(R.id.tipologiaSpinner);

    }

    private void setListener() {
        function.setTouchListenerForAnimation(indietroImageView, this);

        indietroImageView.setOnClickListener(v -> {
            String messageString = "Cliccato 'indietro' in 46vendi";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            overridePendingTransition(0, 0);
            finish();
        });

        tipologia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = tipologia.getItemAtPosition(position).toString();

                if (selectedItem.equals("Al Ribasso")) {
                    // Apre l'activity Home
                    Intent intent = new Intent(Activity46Vendi.this, Activity47VendiAR.class);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                } else if (selectedItem.equals("Silenziosa")) {
                    // Apre l'activity Dashboard
                    Intent intent = new Intent(Activity46Vendi.this, Activity48VendiAS.class);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Non fare nulla qui
            }
        });
    }

    private void setExtraParameters() {
        Intent intentRicevuto = getIntent();
        email = intentRicevuto.getStringExtra("email");
        password = intentRicevuto.getStringExtra("password");

    }

}

