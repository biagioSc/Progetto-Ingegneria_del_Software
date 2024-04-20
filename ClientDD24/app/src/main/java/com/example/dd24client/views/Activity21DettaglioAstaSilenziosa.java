package com.example.dd24client.views;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.dd24client.model.OffertaastasilenziosaModel;
import com.example.dd24client.model.UtenteModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.network.RetrofitClient;
import com.example.dd24client.presenter.Presenter19Dettaglio;
import com.example.dd24client.R;
import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.utils.MessageResponseUtente;
import com.example.dd24client.utils.UtilsFunction;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Calendar;
import java.util.Objects;

public class Activity21DettaglioAstaSilenziosa extends AppCompatActivity implements Activity21DettaglioAstaSilenziosaView {
    private FirebaseAnalytics mFirebaseAnalytics;
    private UtilsFunction function;
    private ImageView indietroImageView,prodottoImageView;
    private TextView nomeVenditoreTextView, nomeProdottoTextView, categoriaTextView, sottocategoriaTextView,
            descrizioneTextView, oreTextView, textViewDay,
            minutiTextView, secondiTextView, offriButton, meno, piu, sugg1, sugg2, sugg3;
    private EditText offertaAttualeTextView;
    private ToggleButton seguiAstaToggleButton;
    private String emailvenditore;
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private Presenter19Dettaglio presenter;
    private String nome="", cognome="", nazionalita="", citta="", biografia="", socialLinks="", linkWeb="", url="";
    private int id;
    private String titolo, descrizioneprodotto, parolechiave, immagineprodotto, categoria, sottocategoria, datafineasta;
    private String email="", password="";
    private Dialog dialog;
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
    private final String paroleChiaveStringa = "parolechiave";
    private final String descrizioneProdottoStringa = "descrizioneprodotto";
    private final String immagineProdottoStringa = "immagineprodotto";
    private final String dataFineAstaStringa = "datafineasta";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_21_dettaglio_asta_silenziosa);
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity21DettaglioAstaSilenziosa", null);

        setComponents();
        setListener();
        setExtraParameters();

        startCountdown();

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (activityStarted) {
            FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity21DettaglioAstaSilenziosa", null);

            presenter.datiUtente21(emailvenditore);
            presenter.seguitaboolAsta21(id, email);
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
        oreTextView = findViewById(R.id.hours);
        minutiTextView = findViewById(R.id.minute);
        secondiTextView = findViewById(R.id.second);
        seguiAstaToggleButton = findViewById(R.id.toggleButton);
        offriButton = findViewById(R.id.offriButton);
        piu = findViewById(R.id.piu);
        meno = findViewById(R.id.meno);
        offertaAttualeTextView = findViewById(R.id.offertaAttualeTextView);
        sugg1 = findViewById(R.id.sugg1);
        sugg2 = findViewById(R.id.sugg2);
        sugg3 = findViewById(R.id.sugg3);
        textViewDay = findViewById(R.id.textView1);

        presenter = new Presenter19Dettaglio(this, apiService);

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
        datafineasta = intent.getStringExtra(dataFineAstaStringa);
        email = intent.getStringExtra(emailStringa);
        password = intent.getStringExtra(passwordStringa);

        nomeProdottoTextView.setText(titolo);
        categoriaTextView.setText(categoria);
        sottocategoriaTextView.setText(sottocategoria);
        descrizioneTextView.setText(descrizioneprodotto);

        Glide.with(this )
                .load(immagineprodotto)
                .into(prodottoImageView);

        presenter.datiUtente21(emailvenditore);
        presenter.seguitaboolAsta21(id, email);

    }

    private void setListener(){
        View[] viewsToAnimate = {indietroImageView, offriButton, piu, meno, sugg1, sugg2, sugg3, nomeVenditoreTextView};

// Itera su ciascuna view nell'array e applica l'animazione al tocco
        for (View view : viewsToAnimate) {
            function.setTouchListenerForAnimation(view, this);
        }

        indietroImageView.setOnClickListener(v -> {
            String messageString = "Cliccato 'indietro' in 21dettaglio";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            overridePendingTransition(0, 0);
            finish();
        });

        offertaAttualeTextView.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int cursorPosition = offertaAttualeTextView.getSelectionStart();

                if (!s.toString().equals(current)) {
                    offertaAttualeTextView.removeTextChangedListener(this);

                    String stringValue = offertaAttualeTextView.getText().toString();
                    // Rimuovi tutti i caratteri non numerici tranne il punto decimale e il simbolo dell'euro
                    stringValue = stringValue.replaceAll("[^\\d.,€]", "");
                    // Sostituisci la virgola con il punto per consentire il parsing corretto
                    stringValue = stringValue.replace(',', '.');
                    // Rimuovi eventuali occorrenze multiple del simbolo dell'euro
                    stringValue = stringValue.replace("€", "");
                    // Effettua il parsing del valore
                    double value = Double.parseDouble(stringValue);

                    offertaAttualeTextView.setText(String.format(Locale.ITALY, "%.2f €", value));

                    offertaAttualeTextView.addTextChangedListener(this);

                }

                offertaAttualeTextView.setSelection(cursorPosition);

            }
        });

        piu.setOnClickListener(v -> gestisciOfferta(1));
        meno.setOnClickListener(v -> gestisciOfferta(0));
        sugg1.setOnClickListener(v -> gestisciAggiuntarapida(1));
        sugg2.setOnClickListener(v -> gestisciAggiuntarapida(2));
        sugg3.setOnClickListener(v -> gestisciAggiuntarapida(3));

        nomeVenditoreTextView.setOnClickListener(v->{
            String messageString = "Cliccato 'nome venditore' in 21dettaglio";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(Activity21DettaglioAstaSilenziosa.this, Activity25ProfiloVenditoreInformazioni.class);
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

        offriButton.setOnClickListener(v -> showPopup());
        seguiAstaToggleButton.setOnClickListener(v -> seguiFunction(id, email));
    }

    private void seguiFunction(int id, String email) {
        String messageString = "Cliccato 'segui/seguita' in 21dettaglio";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        seguiAstaToggleButton.setClickable(false);
        presenter.seguiAsta21(id, email);
    }

    private void gestisciOfferta(int indicatore){
        String messageString = "Cliccato 'piu/meno' in 21dettaglio";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        try {
            String stringValue = offertaAttualeTextView.getText().toString();
            // Rimuovi tutti i caratteri non numerici tranne il punto decimale e il simbolo dell'euro
            stringValue = stringValue.replaceAll("[^\\d.,€]", "");
            // Sostituisci la virgola con il punto per consentire il parsing corretto
            stringValue = stringValue.replace(',', '.');
            // Rimuovi eventuali occorrenze multiple del simbolo dell'euro
            stringValue = stringValue.replace("€", "");
            // Effettua il parsing del valore
            double value = Double.parseDouble(stringValue);

            if(indicatore == 1)
                value += 0.50;
            else
                value -= 0.50;
            offertaAttualeTextView.setText(String.format(Locale.ITALY, "%.2f€", value));
        } catch (NumberFormatException e) {
            offertaAttualeTextView.setText(String.format(Locale.ITALY, "%.2f€", 00.00));
        }
    }

    private void gestisciAggiuntarapida(int indicatore) {
        String messageString = "Cliccato 'aggiunta rapida' in 21dettaglio";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        try {
            String stringValue = offertaAttualeTextView.getText().toString();
            // Rimuovi tutti i caratteri non numerici tranne il punto decimale e il simbolo dell'euro
            stringValue = stringValue.replaceAll("[^\\d.,€]", "");
            // Sostituisci la virgola con il punto per consentire il parsing corretto
            stringValue = stringValue.replace(',', '.');
            // Rimuovi eventuali occorrenze multiple del simbolo dell'euro
            stringValue = stringValue.replace("€", "");
            // Effettua il parsing del valore
            double value = Double.parseDouble(stringValue);

            // Aggiungi l'importo in base all'indicatore
            if (indicatore == 1)
                value += 10;
            else if (indicatore == 2)
                value += 25;
            else if (indicatore == 3)
                value += 50;

            // Aggiorna il TextView con il nuovo valore formattato
            offertaAttualeTextView.setText(String.format(Locale.ITALY, "%.2f€", value));
        } catch (NumberFormatException e) {
            offertaAttualeTextView.setText(String.format(Locale.ITALY, "%.2f€", 00.00));
        }
    }

    private void showPopup(){
        String messageString = "Cliccato 'offri' in 21dettaglio";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        // Crea il Dialog
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_49_popup2);
        TextView offriTextView, annullaTextView;
        offriTextView = dialog.findViewById(R.id.buttonAcquista);
        annullaTextView = dialog.findViewById(R.id.buttonVendi);

        function.setTouchListenerForAnimation(offriTextView,this);
        function.setTouchListenerForAnimation(annullaTextView, this);

        // Opzionale: Imposta le dimensioni del Dialog
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Opzionale: Imposta altre caratteristiche del Dialog

        // Mostra il Dialog
        dialog.show();

        offriTextView.setOnClickListener(v -> {
            String stringValue = offertaAttualeTextView.getText().toString();
            // Rimuovi tutti i caratteri non numerici tranne il punto decimale e il simbolo dell'euro
            stringValue = stringValue.replaceAll("[^\\d.,€]", "");
            // Sostituisci la virgola con il punto per consentire il parsing corretto
            stringValue = stringValue.replace(',', '.');
            // Rimuovi eventuali occorrenze multiple del simbolo dell'euro
            stringValue = stringValue.replace("€", "");
            // Effettua il parsing del valore
            double value = Double.parseDouble(stringValue);

            if(value>0) {
                OffertaastasilenziosaModel asta = new OffertaastasilenziosaModel(id, value, email, "IN VALUTAZIONE");
                presenter.creaOfferta21(asta);
                offriButton.setClickable(false);
            }else{
                Toast.makeText(Activity21DettaglioAstaSilenziosa.this, "Offerta non valida", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        annullaTextView.setOnClickListener(v -> dialog.dismiss());
    }

    private void startCountdown() {
        Handler handler = new Handler();

        Calendar cal = Calendar.getInstance();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Parsa la stringa di data usando il formato definito
        LocalDate data = LocalDate.parse(datafineasta, formatter);

        // Ottieni giorno, mese e anno dalla data
        int giorno = data.getDayOfMonth();
        int mese = data.getMonthValue();
        int anno = data.getYear();

        final long millisFineAsta = getFineAstaMillis(cal, giorno, mese, anno);

        try {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    long millisRimanenti = millisFineAsta - System.currentTimeMillis();

                    if (millisRimanenti > 0) {
                        long oreRimanenti = millisRimanenti / (60 * 60 * 1000);
                        long minutiRimanenti = (millisRimanenti % (60 * 60 * 1000)) / (60 * 1000);
                        long secondiRimanenti = ((millisRimanenti % (60 * 60 * 1000)) % (60 * 1000)) / 1000;

                        if (oreRimanenti >= 24) {
                            long giorniRimanenti = oreRimanenti / 24;

                            oreTextView.setText(String.valueOf(giorniRimanenti));
                            textViewDay.setText("GIORNI");

                        } else {
                            oreTextView.setText(String.valueOf(oreRimanenti));
                            textViewDay.setText("ORE");

                        }

                        //oreTextView.setText(String.valueOf(oreRimanenti));
                        minutiTextView.setText(String.valueOf(minutiRimanenti));
                        secondiTextView.setText(String.valueOf(secondiRimanenti));

                        // Aggiorna ogni secondo
                        handler.postDelayed(this, 1000);
                    } else {
                        // Countdown terminato
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
        Intent intent = new Intent(Activity21DettaglioAstaSilenziosa.this, Activity43DettaglioASChiusa.class);
        intent.putExtra(idStringa, id);
        intent.putExtra(titoloStringa, titolo);
        intent.putExtra(paroleChiaveStringa, parolechiave);
        intent.putExtra(categoriaStringa, categoria);
        intent.putExtra(sottocategoriaStringa, sottocategoria);
        intent.putExtra(descrizioneProdottoStringa, descrizioneprodotto);
        intent.putExtra(venditoreStringa, emailvenditore);
        intent.putExtra(immagineProdottoStringa, immagineprodotto);
        intent.putExtra(dataFineAstaStringa, datafineasta);
        intent.putExtra(emailStringa, email);
        intent.putExtra(passwordStringa, password);
        return intent;
    }

    private long getFineAstaMillis(Calendar cal, int giorno, int mese, int anno) {
        cal.clear(); // Pulisce il calendario
        cal.set(Calendar.DAY_OF_MONTH, giorno);
        cal.set(Calendar.MONTH, mese - 1); // I mesi iniziano da 0 (0 = gennaio, 1 = febbraio, ecc.)
        cal.set(Calendar.YEAR, anno);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTimeInMillis();
    }

    @Override
    public void utenteNonTrovato(MessageResponse errore_password) {
        nomeVenditoreTextView.setClickable(false);
        Toast.makeText(Activity21DettaglioAstaSilenziosa.this, "Informazioni venditore non disponibili", Toast.LENGTH_SHORT).show();
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
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "21");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "crea offerta asta silenziosa");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        Toast.makeText(Activity21DettaglioAstaSilenziosa.this, body.getMessage(), Toast.LENGTH_SHORT).show();
        overridePendingTransition(0, 0);
        dialog.dismiss();
    }

    @Override
    public void onCreateFailure(MessageResponse errore_di_registrazione) {
        Toast.makeText(Activity21DettaglioAstaSilenziosa.this, "Offerta già presentata",
                Toast.LENGTH_SHORT).show();
        dialog.dismiss();
        offriButton.setClickable(true);

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
