package com.example.dd24client.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class Activity40DettaglioARAperta extends AppCompatActivity {
    private ImageView indietroImageView, image_product;
    private TextView nomeProdottoTextView, categoriaTextView, sottocategoriaTextView, testoDescrizioneTextView, followerTextView, hours, minute, second, primo, ultimo, min;
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
    private static final String ID_KEY = "id";
    private static final String STATO_KEY = "stato";
    private static final String TITOLO_KEY = "titolo";
    private static final String PAROLE_CHIAVE_KEY = "parolechiave";
    private static final String CATEGORIA_KEY = "categoria";
    private static final String SOTTOCATEGORIA_KEY = "sottocategoria";
    private static final String DESCRIZIONE_PRODOTTO_KEY = "descrizioneprodotto";
    private static final String VENDITORE_KEY = "venditore";
    private static final String IMMAGINE_PRODOTTO_KEY = "immagineprodotto";
    private static final String IMPORTO_DECREMENTO_KEY = "importodecremento";
    private static final String PREZZO_MINIMO_SEGRETO_KEY = "prezzominimosegreto";
    private static final String PREZZO_INIZIALE_KEY = "prezzoiniziale";
    private static final String TIMER_DECREMENTO_KEY = "timerdecremento";
    private static final String ULTIMO_PREZZO_KEY = "ultimoprezzo";
    private static final String CONTEGGIO_UTENTI_KEY = "conteggioUtenti";
    private static final String CREATED_AT_KEY = "created_at";
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_KEY = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_40_dettaglio_vendi_ar);
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity40DettaglioARAperta", null);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.darkOrange));

        setComponents();
        setListener();
        setExtraParameters();
        startCountdown();

    }

    private void setComponents() {
        indietroImageView = findViewById(R.id.indietroImageView);

        image_product = findViewById(R.id.image_product);
        nomeProdottoTextView = findViewById(R.id.nomeProdottoTextView);
        categoriaTextView = findViewById(R.id.categoriaTextView);
        sottocategoriaTextView = findViewById(R.id.sottocategoriaTextView);
        testoDescrizioneTextView = findViewById(R.id.testoDescrizioneTextView);
        followerTextView = findViewById(R.id.followerTextView);
        hours = findViewById(R.id.hours);
        minute = findViewById(R.id.minute);
        second = findViewById(R.id.second);
        primo = findViewById(R.id.primo);
        ultimo = findViewById(R.id.ultimo);
        min = findViewById(R.id.min);

    }

    private void setListener() {

        indietroImageView.setOnClickListener(v -> {
            String messageString = "Cliccato 'indietro' in 40dettaglio";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            overridePendingTransition(0, 0);
            finish();
        });

    }

    private void setExtraParameters() {
        Intent intent = getIntent();
        id = intent.getIntExtra(ID_KEY, 0);
        titolo = intent.getStringExtra(TITOLO_KEY);
        parolechiave = intent.getStringExtra(PAROLE_CHIAVE_KEY);
        categoria = intent.getStringExtra(CATEGORIA_KEY);
        sottocategoria = intent.getStringExtra(SOTTOCATEGORIA_KEY);
        descrizioneprodotto = intent.getStringExtra(DESCRIZIONE_PRODOTTO_KEY);
        venditore = intent.getStringExtra(VENDITORE_KEY);
        immagineprodotto = intent.getStringExtra(IMMAGINE_PRODOTTO_KEY);
        importodecremento = intent.getDoubleExtra(IMPORTO_DECREMENTO_KEY, 0);
        prezzominimosegreto = intent.getDoubleExtra(PREZZO_MINIMO_SEGRETO_KEY, 0);
        prezzoiniziale = intent.getDoubleExtra(PREZZO_INIZIALE_KEY, 0);
        timerdecremento = intent.getIntExtra(TIMER_DECREMENTO_KEY, 0);
        ultimoprezzo = intent.getDoubleExtra(ULTIMO_PREZZO_KEY, 0);
        conteggioUtenti = intent.getIntExtra(CONTEGGIO_UTENTI_KEY, 0);
        created_at = intent.getStringExtra(CREATED_AT_KEY);

        nomeProdottoTextView.setText(titolo);
        categoriaTextView.setText(categoria);
        sottocategoriaTextView.setText(sottocategoria);
        testoDescrizioneTextView.setText(descrizioneprodotto);
        primo.setText(CreateCardFunction.formatDouble(prezzoiniziale)+ " €");
        ultimo.setText(CreateCardFunction.formatDouble(ultimoprezzo)+ " €");
        min.setText(CreateCardFunction.formatDouble(prezzominimosegreto)+ " €");
        followerTextView.setText("Asta seguita da " + conteggioUtenti + " utenti");
        Glide.with(this )
                .load(immagineprodotto)
                .into(image_product);
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

        handler.post(new Runnable() {
            @Override
            public void run() {

                // Calcolo di quanto manca al prossimo decremento
                //long millisRimanenti = calcolaMillisecondiMancanti(timestamp, timerdecremento);ù

                long millisRimanenti = fineGiornataMillis - System.currentTimeMillis();

                if (millisRimanenti > 0) {

                    long oreRimanenti = millisRimanenti / (60 * 60 * 1000);
                    long minutiRimanenti = (millisRimanenti % (60 * 60 * 1000)) / (60 * 1000);
                    long secondiRimanenti = ((millisRimanenti % (60 * 60 * 1000)) % (60 * 1000)) / 1000;

                    hours.setText(String.valueOf(oreRimanenti));
                    minute.setText(String.valueOf(minutiRimanenti));
                    second.setText(String.valueOf(secondiRimanenti));

                    // Aggiorna ogni secondo
                    handler.postDelayed(this, 1000);
                } else if(ultimoprezzo>=prezzominimosegreto){
                    // Countdown terminato
                    ultimoprezzo = ultimoprezzo - importodecremento;
                    ultimo.setText(String.valueOf(ultimoprezzo));
                    startCountdown();
                }else{
                    hours.setText("0");
                    minute.setText("0");
                    second.setText("0");

                    Intent intent = getIntent1();

                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();

                }
            }
        });
    }

    private Intent getIntent1() {
        Intent intent = new Intent(Activity40DettaglioARAperta.this, Activity42DettaglioARChiusa.class);
        intent.putExtra(ID_KEY, id);
        intent.putExtra(STATO_KEY, false);
        intent.putExtra(TITOLO_KEY, titolo);
        intent.putExtra(PAROLE_CHIAVE_KEY, parolechiave);
        intent.putExtra(CATEGORIA_KEY, categoria);
        intent.putExtra(SOTTOCATEGORIA_KEY, sottocategoria);
        intent.putExtra(DESCRIZIONE_PRODOTTO_KEY, descrizioneprodotto);
        intent.putExtra(VENDITORE_KEY, venditore);
        intent.putExtra(IMMAGINE_PRODOTTO_KEY, immagineprodotto);
        intent.putExtra(IMPORTO_DECREMENTO_KEY, importodecremento);
        intent.putExtra(PREZZO_MINIMO_SEGRETO_KEY, prezzominimosegreto);
        intent.putExtra(PREZZO_INIZIALE_KEY, prezzoiniziale);
        intent.putExtra(TIMER_DECREMENTO_KEY, timerdecremento);
        intent.putExtra(ULTIMO_PREZZO_KEY, ultimoprezzo);
        intent.putExtra(CONTEGGIO_UTENTI_KEY, conteggioUtenti);
        intent.putExtra(EMAIL_KEY, email);
        intent.putExtra(PASSWORD_KEY, password);
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

}
