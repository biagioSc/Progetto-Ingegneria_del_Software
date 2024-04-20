package com.example.dd24client.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.dd24client.R;
import com.example.dd24client.utils.UtilsFunction;
import com.google.firebase.analytics.FirebaseAnalytics;

public class Activity25ProfiloVenditoreInformazioni extends AppCompatActivity {
    private UtilsFunction function;
    private ImageView indietroImageView, imageUser;
    private String nomeVenditore = "", venditore = "", cittaNazione = "", social1Text = "", social2Text = "", social3Text = "", social4Text = "", social5Text = "", descrizioneText = "", url = "";
    private TextView negozioTextView, nomeVenditoreTextView, emailVenditoreTextView, cittaNazioneTextView, social1, social2, social3, social4, social5, descrizione;
    private String email="", password="";
    private static final String NOME_VENDITORE_KEY = "nomevenditore";
    private static final String VENDITORE_KEY = "venditore";
    private static final String CITTA_NAZIONE_KEY = "cittanazione";
    private static final String SOCIAL1_KEY = "social1";
    private static final String SOCIAL2_KEY = "social2";
    private static final String SOCIAL3_KEY = "social3";
    private static final String SOCIAL4_KEY = "social4";
    private static final String SOCIAL5_KEY = "social5";
    private static final String DESCRIZIONE_KEY = "descrizione";
    private static final String URL_KEY = "url";
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_KEY = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_25_profilo_venditore_informazioni);
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity25ProfiloVenditoreInfo", null);

        setComponents();
        setListener();
        setExtraParameters();

    }

    private void setComponents(){
        function = new UtilsFunction();

        indietroImageView = findViewById(R.id.indietroImageView);
        negozioTextView = findViewById(R.id.negozioTextView);
        imageUser = findViewById(R.id.immagineVenditoreImageView);

        nomeVenditoreTextView = findViewById(R.id.nomeVenditoreTextView);
        emailVenditoreTextView = findViewById(R.id.emailVenditoreTextView);
        cittaNazioneTextView = findViewById(R.id.cittaNazioneTextView);
        social1 = findViewById(R.id.facebookTextView);
        social2 = findViewById(R.id.instagramTextView);
        social3 = findViewById(R.id.googleTextView);
        social4 = findViewById(R.id.githubTextView);
        social5 = findViewById(R.id.linkedinTextView);
        descrizione = findViewById(R.id.layoutDescrizione);

    }

    private void setListener(){
        function.setTouchListenerForAnimation(indietroImageView, this);
        function.setTouchListenerForAnimation(negozioTextView, this);

        indietroImageView.setOnClickListener(v -> {
            String messageString = "Cliccato 'indietro' in 25profilovenditore";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            overridePendingTransition(0, 0);
            finish();
        });

        negozioTextView.setOnClickListener(v->{
            String messageString = "Cliccato 'negozio' in 25profilovenditore";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(Activity25ProfiloVenditoreInformazioni.this, Activity26ProfiloVenditoreNegozio.class);
            intent.putExtra(NOME_VENDITORE_KEY, nomeVenditore);
            intent.putExtra(VENDITORE_KEY, venditore);
            intent.putExtra(CITTA_NAZIONE_KEY, cittaNazione);
            intent.putExtra(SOCIAL1_KEY, social1Text);
            intent.putExtra(SOCIAL2_KEY, social2Text);
            intent.putExtra(SOCIAL3_KEY, social3Text);
            intent.putExtra(SOCIAL4_KEY, social4Text);
            intent.putExtra(SOCIAL5_KEY, social5Text);
            intent.putExtra(DESCRIZIONE_KEY, descrizioneText);
            intent.putExtra(URL_KEY, url);
            intent.putExtra(EMAIL_KEY, email);
            intent.putExtra(PASSWORD_KEY, password);

            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });
    }

    private void setExtraParameters() {
        Intent intentRicevuto = getIntent();

        nomeVenditore = intentRicevuto.getStringExtra(NOME_VENDITORE_KEY);
        venditore = intentRicevuto.getStringExtra(VENDITORE_KEY);
        cittaNazione = intentRicevuto.getStringExtra(CITTA_NAZIONE_KEY);
        social1Text = intentRicevuto.getStringExtra(SOCIAL1_KEY);
        social2Text = intentRicevuto.getStringExtra(SOCIAL2_KEY);
        social3Text = intentRicevuto.getStringExtra(SOCIAL3_KEY);
        social4Text = intentRicevuto.getStringExtra(SOCIAL4_KEY);
        social5Text = intentRicevuto.getStringExtra(SOCIAL5_KEY);
        descrizioneText = intentRicevuto.getStringExtra(DESCRIZIONE_KEY);
        url = intentRicevuto.getStringExtra(URL_KEY);
        email = intentRicevuto.getStringExtra(EMAIL_KEY);
        password = intentRicevuto.getStringExtra(PASSWORD_KEY);

        nomeVenditoreTextView.setText(nomeVenditore);
        emailVenditoreTextView.setText(venditore);
        cittaNazioneTextView.setText(cittaNazione);
        social1.setText(social1Text);
        social2.setText(social2Text);
        social3.setText(social3Text);
        social4.setText(social4Text);
        social5.setText(social5Text);
        descrizione.setText(descrizioneText);

        Glide.with(this )
                .load(url)
                .into(imageUser);
    }

}
