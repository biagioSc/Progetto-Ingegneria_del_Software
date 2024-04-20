package com.example.dd24client.views;

import static android.view.ViewGroup.*;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.dd24client.model.acquistaastaribassoModel;
import com.example.dd24client.model.utenteModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.network.RetrofitClient;
import com.example.dd24client.presenter.presenter19DettaglioPresenter;
import com.example.dd24client.R;
import com.example.dd24client.utils.messageResponse;
import com.example.dd24client.utils.messageResponseUtente;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.time.temporal.ChronoUnit;
import java.util.Objects;


public class activity19DettaglioAstaRibasso extends AppCompatActivity implements activity19DettaglioAstaRibassoView {
    private ImageView indietroImageView,prodottoImageView;
    private TextView nomeVenditoreTextView, nomeProdottoTextView, categoriaTextView, sottocategoriaTextView,
    descrizioneTextView, prezzoInizialeTextView, ultimoPrezzoTextView, oreTextView,
    minutiTextView, secondiTextView, acquistaButton;
    private ToggleButton seguiAstaToggleButton;
    private Animation buttonAnimation;
    private String emailvenditore;
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private presenter19DettaglioPresenter presenter;
    private String nome="";
    private String cognome="";
    private String nazionalita="";
    private String citta="";
    private String biografia="";
    private String socialLinks="";
    private String linkWeb="";
    private String url="";
    private String email="", password="";
    private int id;
    private String titolo;
    private String parolechiave;
    private String categoria;
    private String sottocategoria;
    private String descrizioneprodotto;
    private String venditore;
    private String immagineprodotto;
    private double importodecremento;
    private double prezzominimosegreto;
    private double prezzoiniziale;
    private int timerdecremento;
    private double ultimoprezzo;
    private int conteggioUtenti;
    private String created_at;
    private boolean activityStarted = false;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_19_dettaglio_asta_ribasso);

        setComponents();
        setupListeners();
        initAnimations();
        setExtraParameters();

        startCountdown();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (activityStarted) {
            presenter.datiUtente19(emailvenditore);
            presenter.seguitaboolAsta19(id, email);
        }
        activityStarted = false; // Reimposta il flag
    }

    private void setComponents(){
        indietroImageView = findViewById(R.id.indietroImageView);
        prodottoImageView = findViewById(R.id.image_product);
        nomeVenditoreTextView = findViewById(R.id.venditoreTextView);
        nomeProdottoTextView = findViewById(R.id.nomeProdottoTextView);
        categoriaTextView = findViewById(R.id.categoriaTextView);
        sottocategoriaTextView = findViewById(R.id.sottocategoriaTextView);
        descrizioneTextView = findViewById(R.id.testoDescrizioneTextView);
        prezzoInizialeTextView = findViewById(R.id.primo);
        ultimoPrezzoTextView = findViewById(R.id.ultimo);
        oreTextView = findViewById(R.id.hours);
        minutiTextView = findViewById(R.id.minute);
        secondiTextView = findViewById(R.id.second);
        seguiAstaToggleButton = findViewById(R.id.toggleButton);
        acquistaButton = findViewById(R.id.acquistaButton);

        buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.button_animation);

        presenter = new presenter19DettaglioPresenter(this, apiService);

    }

    private void setupListeners(){
        indietroImageView.setOnClickListener(v -> {
            overridePendingTransition(0, 0);
            finish();
        });
        nomeVenditoreTextView.setOnClickListener(v->{
            Intent intent = new Intent(activity19DettaglioAstaRibasso.this, activity25ProfiloVenditoreInformazioni.class);
            intent.putExtra("venditore", emailvenditore);
            intent.putExtra("nomevenditore", nome + " " + cognome);
            intent.putExtra("cittanazione", citta +", "+ nazionalita);

            String[] socialArray = new String[5]; // Array per memorizzare i valori dei social

            if (socialLinks != null) {
                socialArray = socialLinks.split(" "); // Dividi la stringa dei social in un array
            }

            intent.putExtra("social2", socialArray.length > 0 ? socialArray[0] : "");
            intent.putExtra("social3", socialArray.length > 1 ? socialArray[1] : "");
            intent.putExtra("social4", socialArray.length > 2 ? socialArray[2] : "");
            intent.putExtra("social5", socialArray.length > 3 ? socialArray[3] : "");
            intent.putExtra("social1", linkWeb);
            intent.putExtra("descrizione", biografia);
            intent.putExtra("url", url);
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            activityStarted = true;

            startActivity(intent);
            overridePendingTransition(0, 0);
        });
        acquistaButton.setOnClickListener(v -> showPopup() );

        seguiAstaToggleButton.setOnClickListener(v -> seguiFunction(id, email));
    }

    private void seguiFunction(int id, String email) {
        presenter.seguiAsta19(id, email);
    }

    private void setExtraParameters() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0); // Default è null per Integer
        titolo = intent.getStringExtra("titolo");
        parolechiave = intent.getStringExtra("parolechiave");
        categoria = intent.getStringExtra("categoria");
        sottocategoria = intent.getStringExtra("sottocategoria");
        descrizioneprodotto = intent.getStringExtra("descrizioneprodotto");
        venditore = intent.getStringExtra("venditore");
        immagineprodotto = intent.getStringExtra("immagineprodotto");
        importodecremento = intent.getDoubleExtra("importodecremento", 0);
        prezzominimosegreto = intent.getDoubleExtra("prezzominimosegreto", 0);
        prezzoiniziale = intent.getDoubleExtra("prezzoiniziale", 0);
        timerdecremento = intent.getIntExtra("timerdecremento", 0); // Default è null per Integer
        ultimoprezzo = intent.getDoubleExtra("ultimoprezzo", 0);
        conteggioUtenti = intent.getIntExtra("conteggioUtenti", 0);
        created_at = intent.getStringExtra("created_at");
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");

        nomeProdottoTextView.setText(titolo);
        categoriaTextView.setText(categoria);
        sottocategoriaTextView.setText(sottocategoria);
        descrizioneTextView.setText(descrizioneprodotto);
        String message1 = formatDouble(prezzoiniziale)+ " €";
        String message2 = formatDouble(ultimoprezzo)+ " €";
        prezzoInizialeTextView.setText(message1);
        ultimoPrezzoTextView.setText(message2);
        Glide.with(this )
                .load(immagineprodotto)
                .into(prodottoImageView);

        emailvenditore = intent.getStringExtra("venditore");

        presenter.datiUtente19(emailvenditore);
        presenter.seguitaboolAsta19(id, email);
    }

    private void startCountdown() {
        Handler handler = new Handler();
        // Calcola il tempo corrente
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime timestamp = LocalDateTime.parse(created_at, formatter);
        LocalDateTime orarioProssimoDecremento = calcolaOrarioProssimoDecremento(timestamp, timerdecremento);

        int ora = orarioProssimoDecremento.getHour();
        int minuto = orarioProssimoDecremento.getMinute();
        int secondo = orarioProssimoDecremento.getSecond();
        int millisecondo = orarioProssimoDecremento.getNano() / 1000000; // Conversione da nanosecondi a millisecondi

        Calendar cal = Calendar.getInstance();
        final long fineGiornataMillis = getFineGiornataMillis(cal, ora, minuto, secondo, millisecondo);

        try {
            handler.post(new Runnable() {
                @Override
                public void run() {

                    // Calcolo di quanto manca al prossimo decremento
                    //long millisRimanenti = calcolaMillisecondiMancanti(timestamp, timerdecremento);
                    long millisRimanenti = fineGiornataMillis - System.currentTimeMillis();

                    if (millisRimanenti > 0) {

                        long oreRimanenti = millisRimanenti / (60 * 60 * 1000);
                        long minutiRimanenti = (millisRimanenti % (60 * 60 * 1000)) / (60 * 1000);
                        long secondiRimanenti = ((millisRimanenti % (60 * 60 * 1000)) % (60 * 1000)) / 1000;

                        oreTextView.setText(String.valueOf(oreRimanenti));
                        minutiTextView.setText(String.valueOf(minutiRimanenti));
                        secondiTextView.setText(String.valueOf(secondiRimanenti));

                        // Aggiorna ogni secondo
                        handler.postDelayed(this, 1000);
                    } else if (ultimoprezzo >= prezzominimosegreto) {
                        // Countdown terminato
                        ultimoprezzo = ultimoprezzo - importodecremento;
                        ultimoPrezzoTextView.setText(String.valueOf(ultimoprezzo));
                        startCountdown();
                    } else {
                        oreTextView.setText("0");
                        minutiTextView.setText("0");
                        secondiTextView.setText("0");

                        Intent intent = getIntent1();

                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();

                    }
                }
            });
        }catch (Exception e){
            oreTextView.setText("0");
            minutiTextView.setText("0");
            secondiTextView.setText("0");

            Intent intent = getIntent1();

            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }
    }

    private Intent getIntent1() {
        Intent intent = new Intent(activity19DettaglioAstaRibasso.this, activity24DettaglioAstaRibassoChiusa.class);
        intent.putExtra("id", id);
        intent.putExtra("stato", false);
        intent.putExtra("titolo", titolo);
        intent.putExtra("parolechiave", parolechiave);
        intent.putExtra("categoria", categoria);
        intent.putExtra("sottocategoria", sottocategoria);
        intent.putExtra("descrizioneprodotto", descrizioneprodotto);
        intent.putExtra("venditore", venditore);
        intent.putExtra("immagineprodotto", immagineprodotto);
        intent.putExtra("importodecremento", importodecremento);
        intent.putExtra("prezzominimosegreto", prezzominimosegreto);
        intent.putExtra("prezzoiniziale", prezzoiniziale);
        intent.putExtra("timerdecremento", timerdecremento);
        intent.putExtra("ultimoprezzo", ultimoprezzo);
        intent.putExtra("conteggioUtenti", conteggioUtenti);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        return intent;
    }

    public static LocalDateTime calcolaOrarioProssimoDecremento(LocalDateTime timestamp, int intervalloMinuti) {
        long millisecondiPassati = ChronoUnit.MILLIS.between(timestamp, LocalDateTime.now());
        long millisecondiIntervallo = (long) intervalloMinuti * 60 * 1000;
        long millisecondiMancanti = millisecondiIntervallo - (millisecondiPassati % millisecondiIntervallo);
        return LocalDateTime.now().plus(Duration.ofMillis(millisecondiMancanti));
    }

    private long getFineGiornataMillis(Calendar cal, int ore, int minuti, int secondi, int mills) {
        cal.set(Calendar.HOUR_OF_DAY, ore);
        cal.set(Calendar.MINUTE, minuti);
        cal.set(Calendar.SECOND, secondi);
        cal.set(Calendar.MILLISECOND, mills);
        return cal.getTimeInMillis();
    }
    
    private void showPopup(){
        // Crea il Dialog
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_49_popup1);
        TextView acquistaTextView, annullaTextView;
        acquistaTextView = dialog.findViewById(R.id.buttonAcquista);
        annullaTextView = dialog.findViewById(R.id.buttonVendi);


        // Opzionale: Imposta le dimensioni del Dialog
        Objects.requireNonNull(dialog.getWindow()).setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        // Opzionale: Imposta altre caratteristiche del Dialog

        // Mostra il Dialog
        dialog.show();

        acquistaTextView.setOnClickListener(v -> {
            String stringValue = ultimoPrezzoTextView.getText().toString();
            // Rimuovi tutti i caratteri non numerici tranne il punto decimale e il simbolo dell'euro
            stringValue = stringValue.replaceAll("[^\\d.,€]", "");
            // Sostituisci la virgola con il punto per consentire il parsing corretto
            stringValue = stringValue.replace(',', '.');
            // Rimuovi eventuali occorrenze multiple del simbolo dell'euro
            stringValue = stringValue.replace("€", "");
            // Effettua il parsing del valore
            double value = Double.parseDouble(stringValue);

            acquistaastaribassoModel asta = new acquistaastaribassoModel(id, value, email);
            presenter.acquisto19(asta);
        });
        annullaTextView.setOnClickListener(v -> dialog.dismiss());
    }

    private void initAnimations(){
        setTouchListenerForAnimation(indietroImageView);
        setTouchListenerForAnimation(acquistaButton);
        setTouchListenerForAnimation(nomeVenditoreTextView);

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

    public static String formatDouble(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#.###"); // Specifica il formato desiderato
        return decimalFormat.format(value);
    }

    @Override
    public void utenteNonTrovato(messageResponse errore_password) {
    }

    @Override
    public void utenteTrovato(messageResponseUtente message) {
        utenteModel utente = message.getUtente();
        nome = utente.getNome();
        cognome = utente.getCognome();
        biografia = utente.getBiografia();
        nazionalita = utente.getNazionalita();
        citta = utente.getCitta();
        linkWeb = utente.getLinkweb();
        socialLinks = utente.getLinksocial();
        url = utente.getImmagine();

        String message2 = nome + " " + cognome;
        nomeVenditoreTextView.setText(message2);

    }

    @Override
    public void onCreateSuccess(messageResponse body) {
        Toast.makeText(activity19DettaglioAstaRibasso.this, body.getMessage(), Toast.LENGTH_SHORT).show();
        overridePendingTransition(0, 0);
        dialog.dismiss();
    }

    @Override
    public void onCreateFailure(messageResponse errore_di_registrazione) {
        Toast.makeText(activity19DettaglioAstaRibasso.this, errore_di_registrazione.getMessage(),
                Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    @Override
    public void onCreateSuccessSegui(messageResponse body) {
        seguiAstaToggleButton.setClickable(true);
    }

    @Override
    public void onCreateFailureSegui(messageResponse errore_di_registrazione) {
        seguiAstaToggleButton.setClickable(true);
    }

    @Override
    public void onSuccessSegui(messageResponse body) {
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
    public void onFailureSegui(messageResponse errore_di_registrazione) {
        seguiAstaToggleButton.setClickable(false); // Disabilita il clic
        seguiAstaToggleButton.setChecked(false);
        seguiAstaToggleButton.setClickable(true); // Disabilita il clic
    }
}
