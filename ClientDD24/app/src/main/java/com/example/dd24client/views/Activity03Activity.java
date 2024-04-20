package com.example.dd24client.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dd24client.R;

public class activity03Activity extends AppCompatActivity {
    private Animation buttonAnimation;
    private TextView textViewSalta;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_03_splashscreen2);

        setComponents();
        setListener();
    }

    private void setComponents() {
        buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.button_animation);
        textViewSalta = findViewById(R.id.textViewSalta);
        imageButtonContinua = findViewById(R.id.buttonContinua);
        imageViewIndietro = findViewById(R.id.imageButtonIndietro);
    }

    private void setListener() {
        setTouchListenerForAnimation(imageButtonContinua);
        setTouchListenerForAnimation(textViewSalta);
        setTouchListenerForAnimation(imageViewIndietro);

        textViewSalta.setOnClickListener(v -> {
            setSplashPreferences();

            Intent intent = new Intent(activity03Activity.this, activity05WelcomeActivity.class);
            intent.putExtra("nome", nome);
            intent.putExtra("cognome", cognome);
            intent.putExtra("nazionalita", nazionalita);
            intent.putExtra("citta", citta);
            intent.putExtra("biografia", biografia);
            intent.putExtra("linkWeb", linkWeb);
            intent.putExtra("socialLinks", socialLinks);
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            intent.putExtra("confermaPassword", confermaPassword);
            intent.putExtra("terms", terms);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

        imageButtonContinua.setOnClickListener(v -> {
            Intent intent = new Intent(activity03Activity.this, activity04Activity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

        imageViewIndietro.setOnClickListener(v -> {
            Intent intent = new Intent(activity03Activity.this, activity02Activity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });
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

    private void setSplashPreferences() {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }
}
