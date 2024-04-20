package com.example.dd24client.views;

import static android.view.ViewGroup.*;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.dd24client.model.AcquistaastaribassoModel;
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

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Activity19DettaglioAstaRibassoAperta extends AppCompatActivity implements Activity19DettaglioAstaRibassoApertaView {
    private FirebaseAnalytics mFirebaseAnalytics;
    private UtilsFunction function;
    private ImageView indietroImageView,prodottoImageView;
    private TextView nomeVenditoreTextView, nomeProdottoTextView, categoriaTextView, sottocategoriaTextView,
    descrizioneTextView, prezzoInizialeTextView, ultimoPrezzoTextView, oreTextView,
    minutiTextView, secondiTextView, acquistaButton;
    private ToggleButton seguiAstaToggleButton;
    private String emailvenditore;
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private Presenter19Dettaglio presenter;
    private String nome="", cognome="", nazionalita="", citta="", biografia="", socialLinks="", linkWeb="", url="";
    private String email="", password="";
    private int id;
    private String titolo;
    private String parolechiave;
    private String categoria;
    private String sottocategoria;
    private String descrizioneprodotto;
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
    private final String parolechiaveStringa = "parolechiave";
    private final String categoriaStringa = "categoria";
    private final String sottocategoriaStringa = "sottocategoria";
    private final String descrizioneprodottoStringa = "descrizioneprodotto";
    private final String venditoreStringa = "venditore";
    private final String immagineprodottoStringa = "immagineprodotto";
    private final String importodecrementoStringa = "importodecremento";
    private final String prezzominimosegretoStringa = "prezzominimosegreto";
    private final String prezzoinizialeStringa = "prezzoiniziale";
    private final String timerdecrementoStringa = "timerdecremento";
    private final String ultimoprezzoStringa = "ultimoprezzo";
    private final String conteggioUtentiStringa = "conteggioUtenti";
    private final String paroleChiaveStringa = "parolechiave";
    private final String descrizioneProdottoStringa = "descrizioneprodotto";
    private final String immagineProdottoStringa = "immagineprodotto";
    private final String importoDecrementoStringa = "importodecremento";
    private final String prezzoMinimoSegretoStringa = "prezzominimosegreto";
    private final String prezzoInizialeStringa = "prezzoiniziale";
    private final String timerDecrementoStringa = "timerdecremento";
    private final String ultimoPrezzoStringa = "ultimoprezzo";
    private final String createdAtStringa = "created_at";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_19_dettaglio_asta_ribasso);
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity19DettaglioAstaRibassoAperta", null);

        setComponents();
        setListener();
        setExtraParameters();

        startCountdown();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (activityStarted) {
            FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity19DettaglioAstaRibassoAperta", null);

            presenter.datiUtente19(emailvenditore);
            presenter.seguitaboolAsta19(id, email);
        }
        activityStarted = false; // Reimposta il flag
    }

    private void setComponents(){
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        function = new UtilsFunction();

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

        presenter = new Presenter19Dettaglio(this, apiService);

    }

    private void setListener(){
        function.setTouchListenerForAnimation(indietroImageView,this);
        function.setTouchListenerForAnimation(acquistaButton, this);
        function.setTouchListenerForAnimation(nomeVenditoreTextView, this);

        indietroImageView.setOnClickListener(v -> {
            String messageString = "Cliccato 'indietro' in 19dettaglio";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            overridePendingTransition(0, 0);
            finish();
        });

        nomeVenditoreTextView.setOnClickListener(v->{
            String messageString = "Cliccato 'nome venditore' in 19dettaglio";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(Activity19DettaglioAstaRibassoAperta.this, Activity25ProfiloVenditoreInformazioni.class);
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
            activityStarted = true;

            startActivity(intent);
            overridePendingTransition(0, 0);
        });
        acquistaButton.setOnClickListener(v -> showPopup() );

        seguiAstaToggleButton.setOnClickListener(v -> seguiFunction(id, email));
    }

    private void seguiFunction(int id, String email) {
        String messageString = "Cliccato 'segui/seguita' in 19dettaglio";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        seguiAstaToggleButton.setClickable(false);
        presenter.seguiAsta19(id, email);
    }

    private void setExtraParameters() {
        Intent intent = getIntent();
        id = intent.getIntExtra(idStringa, 0); // Default è 0 per Integer
        titolo = intent.getStringExtra(titoloStringa);
        parolechiave = intent.getStringExtra(paroleChiaveStringa);
        categoria = intent.getStringExtra(categoriaStringa);
        sottocategoria = intent.getStringExtra(sottocategoriaStringa);
        descrizioneprodotto = intent.getStringExtra(descrizioneProdottoStringa);
        emailvenditore = intent.getStringExtra(venditoreStringa);
        immagineprodotto = intent.getStringExtra(immagineProdottoStringa);
        importodecremento = intent.getDoubleExtra(importoDecrementoStringa, 0);
        prezzominimosegreto = intent.getDoubleExtra(prezzoMinimoSegretoStringa, 0);
        prezzoiniziale = intent.getDoubleExtra(prezzoInizialeStringa, 0);
        timerdecremento = intent.getIntExtra(timerDecrementoStringa, 0); // Default è 0 per Integer
        ultimoprezzo = intent.getDoubleExtra(ultimoPrezzoStringa, 0);
        conteggioUtenti = intent.getIntExtra(conteggioUtentiStringa, 0);
        created_at = intent.getStringExtra(createdAtStringa);
        email = intent.getStringExtra(emailStringa);
        password = intent.getStringExtra(passwordStringa);

        nomeProdottoTextView.setText(titolo);
        categoriaTextView.setText(categoria);
        sottocategoriaTextView.setText(sottocategoria);
        descrizioneTextView.setText(descrizioneprodotto);
        prezzoInizialeTextView.setText(CreateCardFunction.formatDouble(prezzoiniziale)+ " €");
        ultimoPrezzoTextView.setText(CreateCardFunction.formatDouble(ultimoprezzo)+ " €");
        Glide.with(this )
                .load(immagineprodotto)
                .into(prodottoImageView);


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
                        ultimoPrezzoTextView.setText(CreateCardFunction.formatDouble(ultimoprezzo)+ " €");
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
        Intent intent = new Intent(Activity19DettaglioAstaRibassoAperta.this, Activity24DettaglioAstaRibassoChiusa.class);
        intent.putExtra(idStringa, id);
        intent.putExtra(titoloStringa, titolo);
        intent.putExtra(parolechiaveStringa, parolechiave);
        intent.putExtra(categoriaStringa, categoria);
        intent.putExtra(sottocategoriaStringa, sottocategoria);
        intent.putExtra(descrizioneprodottoStringa, descrizioneprodotto);
        intent.putExtra(venditoreStringa, emailvenditore);
        intent.putExtra(immagineprodottoStringa, immagineprodotto);
        intent.putExtra(importodecrementoStringa, importodecremento);
        intent.putExtra(prezzominimosegretoStringa, prezzominimosegreto);
        intent.putExtra(prezzoinizialeStringa, prezzoiniziale);
        intent.putExtra(timerdecrementoStringa, timerdecremento);
        intent.putExtra(ultimoprezzoStringa, ultimoprezzo);
        intent.putExtra(conteggioUtentiStringa, conteggioUtenti);
        intent.putExtra(emailStringa, email);
        intent.putExtra(passwordStringa, password);
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
        String messageString = "Cliccato 'acquista' in 19dettaglio";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        // Crea il Dialog
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_49_popup1);
        TextView acquistaTextView, annullaTextView;
        acquistaTextView = dialog.findViewById(R.id.buttonAcquista);
        annullaTextView = dialog.findViewById(R.id.buttonVendi);

        function.setTouchListenerForAnimation(acquistaTextView,this);
        function.setTouchListenerForAnimation(annullaTextView, this);

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

            AcquistaastaribassoModel asta = new AcquistaastaribassoModel(id, value, email);
            presenter.acquisto19(asta);
            acquistaButton.setClickable(false);
        });
        annullaTextView.setOnClickListener(v -> dialog.dismiss());
    }

    @Override
    public void utenteNonTrovato(MessageResponse errore_password) {
        nomeVenditoreTextView.setClickable(false);
        Toast.makeText(Activity19DettaglioAstaRibassoAperta.this, "Informazioni venditore non disponibili", Toast.LENGTH_SHORT).show();
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
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "19");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "acquisto asta ribasso");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        Toast.makeText(Activity19DettaglioAstaRibassoAperta.this, body.getMessage(), Toast.LENGTH_SHORT).show();
        dialog.dismiss();
        Intent intent = getIntent1();
        intent.putExtra("prezzovendita", ultimoprezzo);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    public void onCreateFailure(MessageResponse errore_di_registrazione) {
        Toast.makeText(Activity19DettaglioAstaRibassoAperta.this, errore_di_registrazione.getMessage(),
                Toast.LENGTH_SHORT).show();
        dialog.dismiss();
        acquistaButton.setClickable(true);

    }

    @Override
    public void onCreateSuccessSegui(MessageResponse body) {
        seguiAstaToggleButton.setClickable(true);
    }

    @Override
    public void onCreateFailureSegui(MessageResponse errore_di_registrazione) {
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
