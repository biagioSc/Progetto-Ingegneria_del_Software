package com.example.dd24client.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.example.dd24client.R;
import java.text.DecimalFormat;

public class activity42DettaglioARChiusaActivity extends AppCompatActivity {

    private ImageView indietroImageView, image_product;
    private TextView infoprezzo, nomeProdottoTextView, categoriaTextView, sottocategoriaTextView, testoDescrizioneTextView, infoprezzo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_42_dettaglio_chiusa_ar);

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
            overridePendingTransition(0, 0);
            finish();
        });

    }

    private void setExtraParameters() {
        Intent intent = getIntent();
        String titolo = intent.getStringExtra("titolo");
        String categoria = intent.getStringExtra("categoria");
        String sottocategoria = intent.getStringExtra("sottocategoria");
        String descrizioneprodotto = intent.getStringExtra("descrizioneprodotto");
        String immagineprodotto = intent.getStringExtra("immagineprodotto");
        double prezzoiniziale = intent.getDoubleExtra("prezzoiniziale", 0);
        double prezzovendita = intent.getDoubleExtra("prezzovendita", 0); // 0 è un valore di default

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
            infoprezzo.setText("Venduto a " + formatDouble(prezzovendita) + " €");
        }
    }

    public static String formatDouble(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#.###"); // Specifica il formato desiderato
        return decimalFormat.format(value);
    }

}
