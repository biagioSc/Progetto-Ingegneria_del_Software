package com.example.dd24client.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.dd24client.model.UtenteModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.network.RetrofitClient;
import com.example.dd24client.presenter.Presenter19Dettaglio;
import com.example.dd24client.R;
import com.example.dd24client.utils.CreateCardFunction;
import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.utils.MessageResponseUtente;
import com.example.dd24client.utils.UtilsFunction;
import com.google.firebase.analytics.FirebaseAnalytics;

public class Activity23DettaglioAstaSilenziosaChiusa extends AppCompatActivity implements Activity23DettaglioAstaSilenziosaChiusaView {
    private UtilsFunction function;
    private ImageView indietroImageView, image_product;
    private TextView infoprezzo, nomeProdottoTextView, nomeVenditoreTextView, categoriaTextView, sottocategoriaTextView, testoDescrizioneTextView ;
    private String emailvenditore;
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private Presenter19Dettaglio presenter;
    private String nome="", cognome="", nazionalita="", citta="", biografia="", socialLinks="", linkWeb="", url="";
    private String email="", password="";
    private int id;
    private String titolo;
    private String categoria;
    private String sottocategoria;
    private String descrizioneprodotto;
    private String immagineprodotto;
    private double prezzovendita;
    private ToggleButton seguiAstaToggleButton;
    private boolean activityStarted = false;
    private final String social1Stringa = "social1";
    private final String social2Stringa = "social2";
    private final String social3Stringa = "social3";
    private final String social4Stringa = "social4";
    private final String social5Stringa = "social5";
    private final String descrizioneStringa = "descrizione";
    private final String urlStringa = "url";
    private final String emailStringa = "email";
    private final String passwordStringa = "password";
    private final String idStringa = "id";
    private final String titoloStringa = "titolo";
    private final String categoriaStringa = "categoria";
    private final String sottocategoriaStringa = "sottocategoria";
    private final String venditoreStringa = "venditore";
    private final String descrizioneProdottoStringa = "descrizioneprodotto";
    private final String immagineProdottoStringa = "immagineprodotto";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_23_dettaglio_asta_silenziosa_chiusa);
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity23DettaglioSilenziosaChiusa", null);

        setComponents();
        setListener();
        setExtraParameters();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (activityStarted) {
            FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity23DettaglioSilenziosaChiusa", null);

            presenter.datiUtente23(emailvenditore);
            presenter.seguitaboolAsta23(id, email);
        }
        activityStarted = false; // Reimposta il flag
    }

    private void setComponents() {
        function = new UtilsFunction();

        indietroImageView = findViewById(R.id.indietroImageView);
        image_product = findViewById(R.id.image_product);
        nomeProdottoTextView = findViewById(R.id.nomeProdottoTextView);
        categoriaTextView = findViewById(R.id.categoriaTextView);
        sottocategoriaTextView = findViewById(R.id.sottocategoriaTextView);
        testoDescrizioneTextView = findViewById(R.id.testoDescrizioneTextView);
        nomeVenditoreTextView = findViewById(R.id.followerTextView);
        presenter = new Presenter19Dettaglio(this, apiService);
        infoprezzo = findViewById(R.id.infoprezzo);
        seguiAstaToggleButton = findViewById(R.id.toggleButton);

    }

    private void setListener() {
        function.setTouchListenerForAnimation(indietroImageView, this);
        function.setTouchListenerForAnimation(nomeVenditoreTextView, this);

        indietroImageView.setOnClickListener(v -> {
            String messageString = "Cliccato 'indietro' in 23dettaglio";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            overridePendingTransition(0, 0);
            finish();
        });

        nomeVenditoreTextView.setOnClickListener(v->{
            String messageString = "Cliccato 'nome venditore' in 23dettaglio";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(Activity23DettaglioAstaSilenziosaChiusa.this, Activity25ProfiloVenditoreInformazioni.class);
            intent.putExtra("venditore", emailvenditore);
            intent.putExtra("nomevenditore", nome + " " + cognome);
            intent.putExtra("cittanazione", citta +", "+ nazionalita);

            String[] socialArray = new String[5]; // Array per memorizzare i valori dei social

            if (socialLinks != null) {
                socialArray = socialLinks.split(" "); // Dividi la stringa dei social in un array
            }

            intent.putExtra(social2Stringa, socialArray.length > 0 ? socialArray[0] : "");
            intent.putExtra(social3Stringa, socialArray.length > 1 ? socialArray[1] : "");
            intent.putExtra(social4Stringa, socialArray.length > 2 ? socialArray[2] : "");
            intent.putExtra(social5Stringa, socialArray.length > 3 ? socialArray[3] : "");
            intent.putExtra(social1Stringa, linkWeb);
            intent.putExtra(descrizioneStringa, biografia);
            intent.putExtra(urlStringa, url);
            intent.putExtra(emailStringa, email);
            intent.putExtra(passwordStringa, password);
            activityStarted=true;
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

        seguiAstaToggleButton.setOnClickListener(v -> seguiFunction(id, email));
    }

    private void seguiFunction(int id, String email) {
        String messageString = "Cliccato 'segui/seguita' in 23dettaglio";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        seguiAstaToggleButton.setClickable(false);
        presenter.seguiAsta23(id, email);
    }

    private void setExtraParameters() {
        Intent intent = getIntent();
        id = intent.getIntExtra(idStringa, 0); // Default è 0 per Integer
        titolo = intent.getStringExtra(titoloStringa);
        categoria = intent.getStringExtra(categoriaStringa);
        sottocategoria = intent.getStringExtra(sottocategoriaStringa);
        descrizioneprodotto = intent.getStringExtra(descrizioneProdottoStringa);
        emailvenditore = intent.getStringExtra(venditoreStringa);
        immagineprodotto = intent.getStringExtra(immagineProdottoStringa);
        email = intent.getStringExtra(emailStringa);
        password = intent.getStringExtra(passwordStringa);
        prezzovendita = intent.getDoubleExtra("prezzovendita", 0); // 0 è un valore di default

        nomeProdottoTextView.setText(titolo);
        categoriaTextView.setText(categoria);
        sottocategoriaTextView.setText(sottocategoria);
        testoDescrizioneTextView.setText(descrizioneprodotto);
        Glide.with(this )
                .load(immagineprodotto)
                .into(image_product);
        if(prezzovendita<1){
            infoprezzo.setText("Questa asta è andata fallita!");
        }else {
            infoprezzo.setText("Venduto a " + CreateCardFunction.formatDouble(prezzovendita) + " €");
        }

        presenter.datiUtente23(emailvenditore);
        presenter.seguitaboolAsta23(id, email);

    }

    @Override
    public void utenteNonTrovato(MessageResponse errore_password) {
        nomeVenditoreTextView.setClickable(false);
        Toast.makeText(Activity23DettaglioAstaSilenziosaChiusa.this, "Informazioni venditore non disponibili", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void utenteTrovato(MessageResponseUtente message) {
        UtenteModel utente = message.getUtente();
        nome = utente.getNome();
        cognome = utente.getCognome();
        biografia = utente.getBiografia();
        nazionalita = utente.getNazionalita();
        citta = utente.getCitta();
        linkWeb = utente.getLinkweb();
        socialLinks = utente.getLinksocial();
        url = utente.getImmagine();

        nomeVenditoreTextView.setText("Venditore: " + nome + " " + cognome);

    }

    @Override
    public void onCreateSuccess(MessageResponse body) {
        seguiAstaToggleButton.setClickable(true);
    }

    @Override
    public void onCreateFailure(MessageResponse errore_di_registrazione) {
        seguiAstaToggleButton.setClickable(true);
    }

    @Override
    public void onSuccessSegui(MessageResponse body) {
        if(body.getMessage().equals("true")){
            seguiAstaToggleButton.setClickable(false); // Disabilita il clic
            seguiAstaToggleButton.setChecked(true);
            seguiAstaToggleButton.setClickable(true); // Disabilita il clic
        }else{
            seguiAstaToggleButton.setClickable(false); // Disabilita il clic
            seguiAstaToggleButton.setChecked(false);
            seguiAstaToggleButton.setClickable(true); // Disabilita il clic
        }
    }

    @Override
    public void onFailureSegui(MessageResponse errore_di_registrazione) {
        seguiAstaToggleButton.setClickable(false); // Disabilita il clic
        seguiAstaToggleButton.setChecked(false);
        seguiAstaToggleButton.setClickable(true); // Disabilita il clic
    }
}
