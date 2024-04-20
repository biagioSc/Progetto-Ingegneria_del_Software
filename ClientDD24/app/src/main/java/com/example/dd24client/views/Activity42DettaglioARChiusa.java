package com.example.dd24client.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.example.dd24client.R;
import com.example.dd24client.utils.CreateCardFunction;
import com.google.firebase.analytics.FirebaseAnalytics;

public class Activity42DettaglioARChiusa extends AppCompatActivity {

    private ImageView indietroImageView, image_product;
    private TextView infoprezzo, nomeProdottoTextView, categoriaTextView, sottocategoriaTextView, testoDescrizioneTextView, infoprezzo2;
    private static final String TITOLO_KEY = "titolo";
    private static final String CATEGORIA_KEY = "categoria";
    private static final String SOTTOCATEGORIA_KEY = "sottocategoria";
    private static final String DESCRIZIONE_PRODOTTO_KEY = "descrizioneprodotto";
    private static final String IMMAGINE_PRODOTTO_KEY = "immagineprodotto";
    private static final String PREZZO_INIZIALE_KEY = "prezzoiniziale";
    private static final String PREZZO_VENDITA_KEY = "prezzovendita";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_42_dettaglio_chiusa_ar);
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity42DettaglioARChiusa", null);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.darkOrange));

        setComponents();
        setListener();
        setExtraParameters();

    }

    private void setComponents() {
        indietroImageView = findViewById(R.id.indietroImageView);

        image_product = findViewById(R.id.image_product);
        nomeProdottoTextView = findViewById(R.id.nomeProdottoTextView);
        categoriaTextView = findViewById(R.id.categoriaTextView);
        sottocategoriaTextView = findViewById(R.id.sottocategoriaTextView);
        testoDescrizioneTextView = findViewById(R.id.testoDescrizioneTextView);
        infoprezzo = findViewById(R.id.infoprezzo);
        infoprezzo2 = findViewById(R.id.infoprezzo2);

    }

    private void setListener() {

        indietroImageView.setOnClickListener(v -> {
            String messageString = "Cliccato 'indietro' in 42dettaglio";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            overridePendingTransition(0, 0);
            finish();
        });

    }

    private void setExtraParameters() {
        Intent intent = getIntent();
        String titolo = intent.getStringExtra(TITOLO_KEY);
        String categoria = intent.getStringExtra(CATEGORIA_KEY);
        String sottocategoria = intent.getStringExtra(SOTTOCATEGORIA_KEY);
        String descrizioneprodotto = intent.getStringExtra(DESCRIZIONE_PRODOTTO_KEY);
        String immagineprodotto = intent.getStringExtra(IMMAGINE_PRODOTTO_KEY);
        double prezzoiniziale = intent.getDoubleExtra(PREZZO_INIZIALE_KEY, 0);
        double prezzovendita = intent.getDoubleExtra(PREZZO_VENDITA_KEY, 0); // 0 è un valore di default

        nomeProdottoTextView.setText(titolo);
        categoriaTextView.setText(categoria);
        sottocategoriaTextView.setText(sottocategoria);
        testoDescrizioneTextView.setText(descrizioneprodotto);
        infoprezzo2.setText("Prezzo iniziale " + prezzoiniziale + "€");
        Glide.with(this )
                .load(immagineprodotto)
                .into(image_product);
        if(prezzovendita<1){
            infoprezzo.setText("Questa asta è andata fallita!");
        }else {
            infoprezzo.setText("Venduto a " + CreateCardFunction.formatDouble(prezzovendita) + " €");
        }
    }

}
