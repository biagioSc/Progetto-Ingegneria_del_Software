package com.example.dd24client.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.example.dd24client.R;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;


public class activity40DettaglioARApertaActivity extends AppCompatActivity {

    private ImageView indietroImageView, image_product;
    private TextView nomeProdottoTextView, categoriaTextView, sottocategoriaTextView, testoDescrizioneTextView, followerTextView, hours, minute, second, primo, ultimo, min;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_40_dettaglio_vendi_ar);

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
            overridePendingTransition(0, 0);
            finish();
        });

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

        nomeProdottoTextView.setText(titolo);
        categoriaTextView.setText(categoria);
        sottocategoriaTextView.setText(sottocategoria);
        testoDescrizioneTextView.setText(descrizioneprodotto);
        primo.setText(formatDouble(prezzoiniziale)+ " €");
        ultimo.setText(formatDouble(ultimoprezzo)+ " €");
        min.setText(formatDouble(prezzominimosegreto)+ " €");
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
        Intent intent = new Intent(activity40DettaglioARApertaActivity.this, activity42DettaglioARChiusaActivity.class);
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
        String email = "";
        intent.putExtra("email", email);
        String password = "";
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

    public static String formatDouble(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#.###"); // Specifica il formato desiderato
        return decimalFormat.format(value);
    }

}
