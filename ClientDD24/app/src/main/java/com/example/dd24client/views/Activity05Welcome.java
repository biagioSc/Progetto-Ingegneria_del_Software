package com.example.dd24client.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.dd24client.R;
import com.example.dd24client.utils.UtilsFunction;
import com.google.firebase.analytics.FirebaseAnalytics;

public class Activity05Welcome extends AppCompatActivity {
    private UtilsFunction function;
    private TextView buttonAccedi,buttonRegistrati;
    private ImageButton imageButtonGoogle, imageButtonFacebook, imageButtonGithub, imageButtonApple;
    private String nome="", cognome="", nazionalita="", citta="", biografia="", socialLinks="", linkWeb="", email="", password="", confermaPassword="", foto="", imageConvertToSave="";
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
    private final String fotoStringa = "image";
    private final String imageConvertToSaveStringa = "imageToSave";

    @Override
    protected void onCreate(Bundle savedIstanceState){
        super.onCreate(savedIstanceState);
        setContentView(R.layout.activity_05_welcome);
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity05Welcome", null);

        setComponents();
        setListener();
        setExtraParameters();

    }

    private void setComponents(){
        function = new UtilsFunction();

        buttonAccedi = findViewById(R.id.buttonAccedi);
        buttonRegistrati = findViewById(R.id.buttonRegistrati);
        imageButtonGoogle = findViewById(R.id.buttonGoogle);
        imageButtonFacebook = findViewById(R.id.buttonFacebook);
        imageButtonGithub = findViewById(R.id.buttonGithub);
        imageButtonApple = findViewById(R.id.buttonApple);
    }

    private void setListener() {
        function.setTouchListenerForAnimation(buttonAccedi, this);
        function.setTouchListenerForAnimation(buttonRegistrati, this);
        function.setTouchListenerForAnimation(imageButtonGoogle, this);
        function.setTouchListenerForAnimation(imageButtonFacebook, this);
        function.setTouchListenerForAnimation(imageButtonGithub, this);
        function.setTouchListenerForAnimation(imageButtonApple, this);

        setSignupSocial(imageButtonGoogle, "google");
        setSignupSocial(imageButtonFacebook, "facebook");
        setSignupSocial(imageButtonGithub, "github");
        setSignupSocial(imageButtonApple, "apple");

        buttonAccedi.setOnClickListener(v -> {
            String messageString = "Cliccato 'accedi' in 05welcome";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(Activity05Welcome.this, Activity06Login.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

        buttonRegistrati.setOnClickListener(v -> {
            String messageString = "Cliccato 'registrati' in 05welcome";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(Activity05Welcome.this, Activity07Signup.class);
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
            intent.putExtra(fotoStringa, foto);
            intent.putExtra(imageConvertToSaveStringa, imageConvertToSave);

            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

    }

    private void setSignupSocial(ImageButton button, String text){
        button.setOnClickListener(v -> Toast.makeText(this, "Registrazione con " + text + " non disponibile", Toast.LENGTH_SHORT).show());
    }

    private void setExtraParameters() {
        Intent intentRicevuto = getIntent();

        nome = intentRicevuto.getStringExtra(nomeStringa);
        cognome = intentRicevuto.getStringExtra(cognomeStringa);
        nazionalita = intentRicevuto.getStringExtra(nazionalitaStringa);
        citta = intentRicevuto.getStringExtra(cittaStringa);
        biografia = intentRicevuto.getStringExtra(biografiaStringa);
        linkWeb = intentRicevuto.getStringExtra(linkWebStringa);
        socialLinks = intentRicevuto.getStringExtra(socialLinksStringa);
        email = intentRicevuto.getStringExtra(emailStringa);
        password = intentRicevuto.getStringExtra(passwordStringa);
        confermaPassword = intentRicevuto.getStringExtra(confermaPasswordStringa);
        terms = intentRicevuto.getBooleanExtra(termsStringa, false);
        foto = intentRicevuto.getStringExtra(fotoStringa);
        imageConvertToSave = intentRicevuto.getStringExtra(imageConvertToSaveStringa);


    }

}
