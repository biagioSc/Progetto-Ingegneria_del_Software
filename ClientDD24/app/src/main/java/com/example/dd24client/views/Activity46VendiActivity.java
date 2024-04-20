package com.example.dd24client.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.core.content.ContextCompat;

import com.example.dd24client.R;

public class activity46VendiActivity extends Activity {
    private Animation buttonAnimation;
    private ImageView indietroImageView;
    private Spinner tipologia;
    private String email="", password="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_46_vendi1); // Sostituisci con il tuo file layout

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.darkOrange));

        setComponents();
        setListener();

        setExtraParameters();

    }

    private void setComponents() {
        indietroImageView = findViewById(R.id.indietroImageView);
        tipologia = findViewById(R.id.tipologiaSpinner);

        buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.button_animation);

    }

    private void setListener() {
        setTouchListenerForAnimation(indietroImageView);

        indietroImageView.setOnClickListener(v -> {
            overridePendingTransition(0, 0);
            finish();
        });

        tipologia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = tipologia.getItemAtPosition(position).toString();

                if (selectedItem.equals("Al Ribasso")) {
                    // Apre l'activity Home
                    Intent intent = new Intent(activity46VendiActivity.this, activity47VendiARActivity.class);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                } else if (selectedItem.equals("Silenziosa")) {
                    // Apre l'activity Dashboard
                    Intent intent = new Intent(activity46VendiActivity.this, activity48VendiASActivity.class);
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

    private void setTouchListenerForAnimation(View view) {
        view.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.startAnimation(buttonAnimation);
                new Handler().postDelayed(v::clearAnimation, 100);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                v.performClick(); // Chiamata a performClick() quando viene rilevato un clic
            }
            return false;
        });
    }

}

