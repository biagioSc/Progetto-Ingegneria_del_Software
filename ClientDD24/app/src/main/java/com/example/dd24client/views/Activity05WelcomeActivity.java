package com.example.dd24client.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dd24client.R;

public class activity05WelcomeActivity extends AppCompatActivity {
    private Animation buttonAnimation;
    private TextView buttonAccedi,buttonRegistrati;
    private ImageButton imageButtonGoogle, imageButtonFacebook, imageButtonGithub, imageButtonApple;
    private String nome="", cognome="", nazionalita="", citta="", biografia="", socialLinks="", linkWeb="", email="", password="", confermaPassword="", foto="", imageConvertToSave="";
    boolean terms=false;

    @Override
    protected void onCreate(Bundle savedIstanceState){
        super.onCreate(savedIstanceState);
        setContentView(R.layout.activity_05_welcome);

        setComponents();
        setExtraParameters();
        setListener();

    }

    private void setComponents(){
        buttonAccedi = findViewById(R.id.buttonAccedi);
        buttonRegistrati = findViewById(R.id.buttonRegistrati);
        imageButtonGoogle = findViewById(R.id.buttonGoogle);
        imageButtonFacebook = findViewById(R.id.buttonFacebook);
        imageButtonGithub = findViewById(R.id.buttonGithub);
        imageButtonApple = findViewById(R.id.buttonApple);
        buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.button_animation);
    }

    private void setListener() {
        setTouchListenerForAnimation(buttonAccedi);
        setTouchListenerForAnimation(buttonRegistrati);
        setTouchListenerForAnimation(imageButtonGoogle);
        setTouchListenerForAnimation(imageButtonFacebook);
        setTouchListenerForAnimation(imageButtonGithub);
        setTouchListenerForAnimation(imageButtonApple);

        buttonAccedi.setOnClickListener(v -> {
            Intent intent = new Intent(activity05WelcomeActivity.this, activity06LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

        buttonRegistrati.setOnClickListener(v -> {
            Intent intent = new Intent(activity05WelcomeActivity.this, activity07SignupActivity1.class);
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
            intent.putExtra("image", foto);
            intent.putExtra("imageToSave", imageConvertToSave);

            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

     }

    private void setExtraParameters() {
        Intent intentRicevuto = getIntent();

        nome = intentRicevuto.getStringExtra("nome");
        cognome = intentRicevuto.getStringExtra("cognome");
        nazionalita = intentRicevuto.getStringExtra("nazionalita");
        citta = intentRicevuto.getStringExtra("citta");
        biografia = intentRicevuto.getStringExtra("biografia");
        linkWeb = intentRicevuto.getStringExtra("linkWeb");
        socialLinks = intentRicevuto.getStringExtra("socialLinks");
        email = intentRicevuto.getStringExtra("email");
        password = intentRicevuto.getStringExtra("password");
        confermaPassword = intentRicevuto.getStringExtra("confermaPassword");
        terms = intentRicevuto.getBooleanExtra("terms", false);
        foto = intentRicevuto.getStringExtra("image");
        imageConvertToSave = intentRicevuto.getStringExtra("imageToSave");

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
