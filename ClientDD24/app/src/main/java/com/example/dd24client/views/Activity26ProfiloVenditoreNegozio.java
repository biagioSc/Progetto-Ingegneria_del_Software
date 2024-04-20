package com.example.dd24client.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.dd24client.model.AstaribassoModel;
import com.example.dd24client.model.AstasilenziosaModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.network.RetrofitClient;
import com.example.dd24client.presenter.Presenter26ProfiloVenditoreNegozio;
import com.example.dd24client.R;
import com.example.dd24client.utils.CreateCardFunction;
import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.utils.MessageResponseRibasso;
import com.example.dd24client.utils.MessageResponseSilenziosa;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.example.dd24client.utils.UtilsFunction;
import com.google.firebase.analytics.FirebaseAnalytics;

public class Activity26ProfiloVenditoreNegozio extends AppCompatActivity implements Activity26ProfiloVenditoreNegozioView {
    private UtilsFunction function;
    private CreateCardFunction createCardFunction;
    private ImageView indietroImageView, imageUser;
    private TextView infoTextView, nomeVenditoreTextView, emailVenditoreTextView, cittaNazioneTextView;
    private String nomeVenditore = "", venditore = "", cittaNazione = "", social1Text = "", social2Text = "", social3Text = "", social4Text = "", social5Text = "", descrizioneText = "", url = "";
    private LinearLayout linearLayoutRibasso, linearLayoutSilenziosa;
    private final List<AstaribassoModel> asteAlRibasso = new ArrayList<>();
    private final List<AstasilenziosaModel> asteSilenziosa = new ArrayList<>();
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private Presenter26ProfiloVenditoreNegozio presenter;
    private String email="", password="";
    private boolean activityStarted = false;
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
    private static final String ID_KEY = "id";
    private static final String STATO_KEY = "stato";
    private static final String TITOLO_KEY = "titolo";
    private static final String PAROLE_CHIAVE_KEY = "parolechiave";
    private static final String CATEGORIA_KEY = "categoria";
    private static final String SOTTOCATEGORIA_KEY = "sottocategoria";
    private static final String DESCRIZIONE_PRODOTTO_KEY = "descrizioneprodotto";
    private static final String IMMAGINE_PRODOTTO_KEY = "immagineprodotto";
    private static final String IMPORTO_DECREMENTO_KEY = "importodecremento";
    private static final String PREZZO_MINIMO_SEGRETO_KEY = "prezzominimosegreto";
    private static final String PREZZO_INIZIALE_KEY = "prezzoiniziale";
    private static final String TIMER_DECREMENTO_KEY = "timerdecremento";
    private static final String ULTIMO_PREZZO_KEY = "ultimoprezzo";
    private static final String CONTEGGIO_UTENTI_KEY = "conteggioUtenti";
    private static final String CREATED_AT_KEY = "created_at";
    private static final String PREZZO_VENDITA_KEY = "prezzovendita";
    private static final String DATA_FINE_ASTA_KEY = "datafineasta";
    private static final String importoDecrementoStringa = "importodecremento";
    private static final String prezzoMinimoSegretoStringa = "prezzominimosegreto";
    private static final String prezzoInizialeStringa = "prezzoiniziale";
    private static final String timerDecrementoStringa = "timerdecremento";
    private static final String ultimoPrezzoStringa = "ultimoprezzo";
    private static final String createdAtStringa = "created_at";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_26_profilo_venditore_negozio);
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity26ProfiloVenditoreNegozio", null);

        setComponents();
        setListener();
        setExtraParameters();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (activityStarted) {
            FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity26ProfiloVenditoreNegozio", null);

            asteAlRibasso.clear();
            asteSilenziosa.clear();

            function.removeAllCardViews(linearLayoutRibasso);
            function.removeAllCardViews(linearLayoutSilenziosa);

            presenter.astaSilenziosa(venditore);
            presenter.astaRibasso(venditore);

        }
        activityStarted = false; // Reimposta il flag
    }

    private void setComponents(){
        function = new UtilsFunction();
        createCardFunction = new CreateCardFunction();

        indietroImageView = findViewById(R.id.indietroImageView);
        infoTextView = findViewById(R.id.informazioniTextView);
        imageUser = findViewById(R.id.immagineVenditoreImageView);

        nomeVenditoreTextView = findViewById(R.id.nomeVenditoreTextView);
        emailVenditoreTextView = findViewById(R.id.emailVenditoreTextView);
        cittaNazioneTextView = findViewById(R.id.cittaNazioneTextView);
        presenter = new Presenter26ProfiloVenditoreNegozio(this, apiService);

        linearLayoutRibasso = findViewById(R.id.linearCardRibasso);
        linearLayoutSilenziosa = findViewById(R.id.linearCardSilenziosa);

    }

    private void setListener(){
        function.setTouchListenerForAnimation(indietroImageView, this);
        function.setTouchListenerForAnimation(infoTextView, this);

        indietroImageView.setOnClickListener(v -> {
            String messageString = "Cliccato 'indietro' in 25negoziovenditore";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            overridePendingTransition(0, 0);
            finish();
        });

        infoTextView.setOnClickListener(v->{
            String messageString = "Cliccato 'informazioni' in 25negoziovenditore";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(Activity26ProfiloVenditoreNegozio.this, Activity25ProfiloVenditoreInformazioni.class);
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

        Glide.with(this )
                .load(url)
                .into(imageUser);

        function.removeAllCardViews(linearLayoutRibasso);
        function.removeAllCardViews(linearLayoutSilenziosa);

        presenter.astaSilenziosa(venditore);
        presenter.astaRibasso(venditore);
    }

    public void aggiornaAsteSilenziosa(List<AstasilenziosaModel> aste) {
        for (int i = 0; i < aste.size(); i += 2) {

            AstasilenziosaModel asta1 = aste.get(i);
            AstasilenziosaModel asta2 = (i + 1 < aste.size()) ? aste.get(i + 1) : null;

            LinearLayout horizontalLayout = new LinearLayout(this);
            horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            horizontalLayout.setGravity(Gravity.CENTER);
            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);

            CardView cardView1;
            if(asta1.isStato()){
                Object[] result1 = createCardFunction.cardApertaSilenziosaAcquirente(this, asta1);
                cardView1 = (CardView) result1[0];
                TextView textTimer1 = (TextView) result1[1];
                setupCardListenerSilenziosaApertaAcquirente(Activity21DettaglioAstaSilenziosa.class, this, cardView1, asta1, textTimer1, email, password);

            }else {
                cardView1 = createCardFunction.cardChiusaSilenziosaAcquirente(this, asta1);
                setupCardListenerSilenziosaChiusaAcquirente(this, cardView1, asta1, email, password);

            }
            horizontalLayout.addView(cardView1);

            if (asta2 != null) {
                CardView cardView2;
                if(asta2.isStato()) {
                    Object[] result2 = createCardFunction.cardApertaSilenziosaAcquirente(this, asta1);
                    cardView2 = (CardView) result2[0];
                    TextView textTimer2 = (TextView) result2[1];
                    setupCardListenerSilenziosaApertaAcquirente(Activity21DettaglioAstaSilenziosa.class, this, cardView2, asta2, textTimer2, email, password);

                }else{
                    cardView2 = createCardFunction.cardChiusaSilenziosaAcquirente(this, asta1);
                    setupCardListenerSilenziosaChiusaAcquirente(this, cardView2, asta2, email, password);

                }
                horizontalLayout.addView(cardView2);

            }
            linearLayoutSilenziosa.addView(horizontalLayout);

        }
    }

    public void aggiornaAsteRibasso(List<AstaribassoModel> aste) {
        for (int i = 0; i < aste.size(); i += 2) {

            AstaribassoModel asta1 = aste.get(i);
            AstaribassoModel asta2 = (i + 1 < aste.size()) ? aste.get(i + 1) : null;

            LinearLayout horizontalLayout = new LinearLayout(this);
            horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            horizontalLayout.setGravity(Gravity.CENTER);
            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);

            CardView cardView1;
            if (asta1.isStato()){
                Object[] result1 = createCardFunction.cardApertaRibassoAcquirente(this, asta1);
                cardView1 = (CardView) result1[0];
                TextView textTimer1 = (TextView) result1[1];
                TextView textPrice1 = (TextView) result1[2];
                setupCardListenerRibassoApertaAcquirente(Activity19DettaglioAstaRibassoAperta.class, this, cardView1, asta1, textTimer1, textPrice1, email, password);

            }else{
                cardView1 = createCardFunction.cardChiusaRibassoAcquirente(this, asta1);
                setupCardListenerRibassoChiusaAcquirente(this, cardView1, asta1, email, password);

            }
            horizontalLayout.addView(cardView1);

            if (asta2 != null) {
                CardView cardView2;

                if(asta2.isStato()) {
                    Object[] result2 = createCardFunction.cardApertaRibassoAcquirente(this, asta2);
                    cardView2 = (CardView) result2[0];
                    TextView textTimer2 = (TextView) result2[1];
                    TextView textPrice2 = (TextView) result2[2];
                    setupCardListenerRibassoApertaAcquirente(Activity19DettaglioAstaRibassoAperta.class, this, cardView2, asta2, textTimer2, textPrice2, email, password);
                }else {
                    cardView2 = createCardFunction.cardChiusaRibassoAcquirente(this, asta2);
                    setupCardListenerRibassoChiusaAcquirente(this, cardView2, asta2, email, password);
                }
                horizontalLayout.addView(cardView2);
            }

            linearLayoutRibasso.addView(horizontalLayout);

        }
    }

    public void setupCardListenerRibassoApertaAcquirente(Class<?> destinationActivity, Context activityContext, final CardView cardView, AstaribassoModel astaSelezionata, TextView timer, TextView ultimo, String email, String password) {
        String messageString = "Cliccato 'CardRibassoApertaAcquirente' in 25negoziovenditore";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        function.setTouchListenerForAnimation(cardView, activityContext);
        createCardFunction.startCountdownRibasso(cardView, timer, ultimo, astaSelezionata);

        cardView.setOnClickListener(view -> {
            // Avvia l'attività e passa l'asta come extra
            Intent intent = new Intent(activityContext, destinationActivity);
            intent.putExtra(ID_KEY, astaSelezionata.getId());
            intent.putExtra(STATO_KEY, astaSelezionata.isStato());
            intent.putExtra(TITOLO_KEY, astaSelezionata.getTitolo());
            intent.putExtra(PAROLE_CHIAVE_KEY, astaSelezionata.getParolechiave());
            intent.putExtra(CATEGORIA_KEY, astaSelezionata.getCategoria());
            intent.putExtra(SOTTOCATEGORIA_KEY, astaSelezionata.getSottocategoria());
            intent.putExtra(DESCRIZIONE_KEY, astaSelezionata.getDescrizioneprodotto());
            intent.putExtra(VENDITORE_KEY, astaSelezionata.getVenditore());
            intent.putExtra(IMMAGINE_PRODOTTO_KEY, astaSelezionata.getImmagineprodotto());
            intent.putExtra(importoDecrementoStringa, astaSelezionata.getImportodecremento());
            intent.putExtra(prezzoMinimoSegretoStringa, astaSelezionata.getPrezzominimosegreto());
            intent.putExtra(prezzoInizialeStringa, astaSelezionata.getPrezzoiniziale());
            intent.putExtra(timerDecrementoStringa, astaSelezionata.getTimerdecremento());
            intent.putExtra(ultimoPrezzoStringa, astaSelezionata.getUltimoprezzo());
            intent.putExtra(CONTEGGIO_UTENTI_KEY, astaSelezionata.getConteggioUtenti());
            intent.putExtra(createdAtStringa, astaSelezionata.getCreated_at().toString());
            intent.putExtra(PREZZO_VENDITA_KEY, astaSelezionata.getPrezzovendita());
            intent.putExtra(EMAIL_KEY, email);
            intent.putExtra(PASSWORD_KEY, password);

            activityStarted = true;
            activityContext.startActivity(intent);
            ((Activity) activityContext).overridePendingTransition(0, 0);
        });
    }

    public void setupCardListenerSilenziosaApertaAcquirente(Class<?> destinationActivity, Context activityContext, final CardView cardView, AstasilenziosaModel astaSelezionata, TextView timer, String email, String password) {
        String messageString = "Cliccato 'CardSilenziosaApertaAcquirente' in 25negoziovenditore";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        function.setTouchListenerForAnimation(cardView, activityContext);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate dataFineAsta = LocalDate.parse(astaSelezionata.getDatafineasta(), formatter);
        LocalDate dataCorrente = LocalDate.now();

        if(dataFineAsta.equals(dataCorrente)) {
            createCardFunction.startCountdownSilenziosa(cardView, timer, astaSelezionata);
        }
        else{
            timer.setText(astaSelezionata.getDatafineasta());
        }

        cardView.setOnClickListener(view -> {
            // Avvia l'attività e passa l'asta come extra
            Intent intent = new Intent(activityContext, destinationActivity);
            intent.putExtra(ID_KEY, astaSelezionata.getId());
            intent.putExtra(STATO_KEY, astaSelezionata.isStato());
            intent.putExtra(TITOLO_KEY, astaSelezionata.getTitolo());
            intent.putExtra(PAROLE_CHIAVE_KEY, astaSelezionata.getParolechiave());
            intent.putExtra(CATEGORIA_KEY, astaSelezionata.getCategoria());
            intent.putExtra(SOTTOCATEGORIA_KEY, astaSelezionata.getSottocategoria());
            intent.putExtra(DESCRIZIONE_KEY, astaSelezionata.getDescrizioneprodotto());
            intent.putExtra(VENDITORE_KEY, astaSelezionata.getVenditore());
            intent.putExtra(IMMAGINE_PRODOTTO_KEY, astaSelezionata.getImmagineprodotto());
            intent.putExtra(DATA_FINE_ASTA_KEY, astaSelezionata.getDatafineasta());
            intent.putExtra(CONTEGGIO_UTENTI_KEY, astaSelezionata.getConteggioUtenti());
            intent.putExtra(PREZZO_VENDITA_KEY, astaSelezionata.getPrezzovendita());
            intent.putExtra(EMAIL_KEY, email);
            intent.putExtra(PASSWORD_KEY, password);

            activityStarted=true;
            activityContext.startActivity(intent);
            ((Activity) activityContext).overridePendingTransition(0, 0);
        });
    }

    private void setupCardListenerRibassoChiusaAcquirente(Context activityContext, final CardView cardView, AstaribassoModel astaSelezionata, String email, String password) {
        // Assumendo che cardView sia l'istanza della CardView che hai creato dinamicamente
        function.setTouchListenerForAnimation(cardView, activityContext);
        cardView.setOnClickListener(view -> {
            if (astaSelezionata != null) {
                // Avvia l'attività e passa l'asta come extra
                Intent intent = new Intent(activityContext, Activity24DettaglioAstaRibassoChiusa.class);
                intent.putExtra(ID_KEY, astaSelezionata.getId());
                intent.putExtra(STATO_KEY, astaSelezionata.isStato());
                intent.putExtra(TITOLO_KEY, astaSelezionata.getTitolo());
                intent.putExtra(PAROLE_CHIAVE_KEY, astaSelezionata.getParolechiave());
                intent.putExtra(CATEGORIA_KEY, astaSelezionata.getCategoria());
                intent.putExtra(SOTTOCATEGORIA_KEY, astaSelezionata.getSottocategoria());
                intent.putExtra(DESCRIZIONE_PRODOTTO_KEY, astaSelezionata.getDescrizioneprodotto());
                intent.putExtra(VENDITORE_KEY, astaSelezionata.getVenditore());
                intent.putExtra(IMMAGINE_PRODOTTO_KEY, astaSelezionata.getImmagineprodotto());
                intent.putExtra(IMPORTO_DECREMENTO_KEY, astaSelezionata.getImportodecremento());
                intent.putExtra(PREZZO_MINIMO_SEGRETO_KEY, astaSelezionata.getPrezzominimosegreto());
                intent.putExtra(PREZZO_INIZIALE_KEY, astaSelezionata.getPrezzoiniziale());
                intent.putExtra(TIMER_DECREMENTO_KEY, astaSelezionata.getTimerdecremento());
                intent.putExtra(ULTIMO_PREZZO_KEY, astaSelezionata.getUltimoprezzo());
                intent.putExtra(CONTEGGIO_UTENTI_KEY, astaSelezionata.getConteggioUtenti());
                intent.putExtra(CREATED_AT_KEY, astaSelezionata.getCreated_at().toString());
                intent.putExtra(PREZZO_VENDITA_KEY, astaSelezionata.getPrezzovendita());
                intent.putExtra(EMAIL_KEY, email);
                intent.putExtra(PASSWORD_KEY, password);
                activityStarted=true;
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }

    private void setupCardListenerSilenziosaChiusaAcquirente(Context activityContext, final CardView cardView, AstasilenziosaModel astaSelezionata, String email, String password) {
        // Assumendo che cardView sia l'istanza della CardView che hai creato dinamicamente
        function.setTouchListenerForAnimation(cardView, activityContext);
        cardView.setOnClickListener(view -> {
            if (astaSelezionata != null) {
                // Avvia l'attività e passa l'asta come extra
                Intent intent = new Intent(activityContext, Activity23DettaglioAstaSilenziosaChiusa.class);
                intent.putExtra(ID_KEY, astaSelezionata.getId());
                intent.putExtra(STATO_KEY, astaSelezionata.isStato());
                intent.putExtra(TITOLO_KEY, astaSelezionata.getTitolo());
                intent.putExtra(PAROLE_CHIAVE_KEY, astaSelezionata.getParolechiave());
                intent.putExtra(CATEGORIA_KEY, astaSelezionata.getCategoria());
                intent.putExtra(SOTTOCATEGORIA_KEY, astaSelezionata.getSottocategoria());
                intent.putExtra(DESCRIZIONE_PRODOTTO_KEY, astaSelezionata.getDescrizioneprodotto());
                intent.putExtra(VENDITORE_KEY, astaSelezionata.getVenditore());
                intent.putExtra(IMMAGINE_PRODOTTO_KEY, astaSelezionata.getImmagineprodotto());
                intent.putExtra(DATA_FINE_ASTA_KEY, astaSelezionata.getDatafineasta());
                intent.putExtra(CONTEGGIO_UTENTI_KEY, astaSelezionata.getConteggioUtenti());
                intent.putExtra(PREZZO_VENDITA_KEY, astaSelezionata.getPrezzovendita());
                intent.putExtra(EMAIL_KEY, email);
                intent.putExtra(PASSWORD_KEY, password);
                activityStarted=true;
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    public void scaricaAstaRibasso(MessageResponseRibasso userResponse) {
        asteAlRibasso.addAll(userResponse.getAste());
        aggiornaAsteRibasso(asteAlRibasso);
    }

    @Override
    public void scaricaAstaSilenziosa(MessageResponseSilenziosa userResponse) {
        asteSilenziosa.addAll(userResponse.getAste());
        aggiornaAsteSilenziosa(asteSilenziosa);
    }

    @Override
    public void mostraErroreRibasso(MessageResponse message) {
        function.updateNestedScrollView(linearLayoutRibasso, false, this);
    }

    @Override
    public void mostraErroreSilenziosa(MessageResponse message) {
        function.updateNestedScrollView(linearLayoutSilenziosa, false, this);
    }

}

