package com.example.dd24client.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.example.dd24client.model.OffertaastasilenziosaModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.network.RetrofitClient;
import com.example.dd24client.presenter.Presenter41DettaglioASAperta;
import com.example.dd24client.R;
import com.example.dd24client.utils.CreateCardFunction;
import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.utils.MessageResponseOfferte;
import com.example.dd24client.utils.UtilsFunction;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.time.LocalDate;

public class Activity41DettaglioASAperta extends AppCompatActivity implements Activity41DettaglioASApertaView {
    private FirebaseAnalytics mFirebaseAnalytics;
    private UtilsFunction function;
    private CreateCardFunction createCardFunction;
    private ImageView indietroImageView, image_product;
    private TextView textViewDay, nomeProdottoTextView, categoriaTextView, sottocategoriaTextView, testoDescrizioneTextView, followerTextView, hours, minute, second;
    private final List<OffertaastasilenziosaModel> offerteList = new ArrayList<>();
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private Presenter41DettaglioASAperta presenter;
    private LinearLayout linearOfferte;
    private Integer id, conteggioUtenti;
    private Boolean stato;
    private String titolo, descrizioneprodotto, venditore, parolechiave, immagineprodotto, categoria, sottocategoria, datafineasta, email, password;
    private static final String ID_KEY = "id";
    private static final String STATO_KEY = "stato";
    private static final String TITOLO_KEY = "titolo";
    private static final String PAROLE_CHIAVE_KEY = "parolechiave";
    private static final String CATEGORIA_KEY = "categoria";
    private static final String SOTTOCATEGORIA_KEY = "sottocategoria";
    private static final String DESCRIZIONE_PRODOTTO_KEY = "descrizioneprodotto";
    private static final String VENDITORE_KEY = "venditore";
    private static final String IMMAGINE_PRODOTTO_KEY = "immagineprodotto";
    private static final String DATA_FINE_ASTA_KEY = "datafineasta";
    private static final String CONTEGGIO_UTENTI_KEY = "conteggioUtenti";
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_KEY = "password";
    private static final String PREZZO_VENDITA_KEY = "prezzovendita";

    private Double prezzovenduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_41_dettaglio_vendi_as);
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity41DettaglioASAperta", null);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.darkOrange));

        setComponents();
        setListener();
        setExtraParameters();
        startCountdown();
    }

    private void setComponents() {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        function = new UtilsFunction();
        createCardFunction = new CreateCardFunction();

        presenter = new Presenter41DettaglioASAperta(this, apiService);

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
        linearOfferte = findViewById(R.id.linearOfferte);
        textViewDay = findViewById(R.id.textView1);


    }

    private void setListener() {

        indietroImageView.setOnClickListener(v -> {
            String messageString = "Cliccato 'indietro' in 41dettaglio";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            overridePendingTransition(0, 0);
            finish();
        });

    }

    private void setExtraParameters() {
        Intent intent = getIntent();
        id = intent.getIntExtra(ID_KEY, 0);
        stato = intent.getBooleanExtra(STATO_KEY, false);
        titolo = intent.getStringExtra(TITOLO_KEY);
        parolechiave = intent.getStringExtra(PAROLE_CHIAVE_KEY);
        categoria = intent.getStringExtra(CATEGORIA_KEY);
        sottocategoria = intent.getStringExtra(SOTTOCATEGORIA_KEY);
        descrizioneprodotto = intent.getStringExtra(DESCRIZIONE_PRODOTTO_KEY);
        venditore = intent.getStringExtra(VENDITORE_KEY);
        immagineprodotto = intent.getStringExtra(IMMAGINE_PRODOTTO_KEY);
        datafineasta = intent.getStringExtra(DATA_FINE_ASTA_KEY);
        conteggioUtenti = intent.getIntExtra(CONTEGGIO_UTENTI_KEY, 0);
        email = intent.getStringExtra(EMAIL_KEY);
        password = intent.getStringExtra(PASSWORD_KEY);

        linearOfferte.removeAllViews();
        presenter.richiediOfferteAstaSilenziosa(id);

        nomeProdottoTextView.setText(titolo);
        categoriaTextView.setText(categoria);
        sottocategoriaTextView.setText(sottocategoria);
        testoDescrizioneTextView.setText(descrizioneprodotto);
        followerTextView.setText("Asta seguita da " + conteggioUtenti + " utenti");
        Glide.with(this )
                .load(immagineprodotto)
                .into(image_product);
    }

    public void aggiornaOfferte(List<OffertaastasilenziosaModel> aste) {
        linearOfferte.removeAllViews(); // Rimuovi tutte le view esistenti

        if (aste.isEmpty()) {
            function.updateNestedScrollViewOfferte(linearOfferte, false, this);
            return;
        }

        for (int i = 0; i < aste.size(); i ++) {
            OffertaastasilenziosaModel offerta = aste.get(i);

            CardView cardView1;

            cardView1 = creaCardViewOfferta(offerta, this);
            linearOfferte.addView(cardView1);

        }

    }

    private CardView creaCardViewOfferta(OffertaastasilenziosaModel offerta, Context context) {
        // Crea la CardView e imposta le dimensioni e gli altri attributi
        CardView cardView = new CardView(context);
        cardView.setId(CardView.generateViewId());
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );

        params.topMargin = createCardFunction.convertDpToPx(10, context);
        cardView.setPadding(createCardFunction.convertDpToPx(16, context), createCardFunction.convertDpToPx(5, context), createCardFunction.convertDpToPx(16, context), createCardFunction.convertDpToPx(5, context));
        cardView.setLayoutParams(params);
        cardView.setRadius(createCardFunction.convertDpToPx(20, context));
        cardView.setCardElevation(createCardFunction.convertDpToPx(8, context));
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
        cardView.setContentPadding(createCardFunction.convertDpToPx(8, context), createCardFunction.convertDpToPx(8, context), createCardFunction.convertDpToPx(8, context), createCardFunction.convertDpToPx(8, context));

        // Crea il ConstraintLayout che conterrà gli elementi della CardView
        ConstraintLayout constraintLayout = new ConstraintLayout(context);
        constraintLayout.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        ));

        // Crea e configura il TextView per il nome del prodotto
        TextView textViewOfferente = new TextView(context);
        createCardFunction.setTextViewAttributes(textViewOfferente, offerta.getemailutente(), 18, R.color.black, context);
        textViewOfferente.setId(View.generateViewId());
        textViewOfferente.setTypeface(null, Typeface.BOLD);
        ConstraintLayout.LayoutParams lpProductName = new ConstraintLayout.LayoutParams(
                createCardFunction.convertDpToPx(180, context),
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        lpProductName.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        lpProductName.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        lpProductName.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        textViewOfferente.setLayoutParams(lpProductName);
        constraintLayout.addView(textViewOfferente);

        // Crea e configura il TextView per il numero di persone interessate
        TextView textPeopleInterested = new TextView(context);
        createCardFunction.setTextViewAttributes(textPeopleInterested, CreateCardFunction.formatDouble(offerta.getprezzoofferto())+ " €", 18, R.color.darkOrange, context);
        textPeopleInterested.setId(View.generateViewId());
        textPeopleInterested.setTypeface(null, Typeface.BOLD);
        ConstraintLayout.LayoutParams lpPeopleInterested = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        lpPeopleInterested.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        lpPeopleInterested.startToEnd = textViewOfferente.getId();
        lpPeopleInterested.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        lpPeopleInterested.setMargins(createCardFunction.convertDpToPx(8, context), 0, 0, 0);
        textPeopleInterested.setLayoutParams(lpPeopleInterested);
        constraintLayout.addView(textPeopleInterested);

        ImageView imageViewRifiuta = new ImageView(context);
        ConstraintLayout.LayoutParams imageParamsRifiuta = new ConstraintLayout.LayoutParams(
                createCardFunction.convertDpToPx(40, context),
                createCardFunction.convertDpToPx(40, context)
        );
        imageParamsRifiuta.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        imageParamsRifiuta.startToEnd = textPeopleInterested.getId();
        imageParamsRifiuta.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        imageParamsRifiuta.setMargins(createCardFunction.convertDpToPx(15, context), 0, 0, 0);
        imageViewRifiuta.setLayoutParams(imageParamsRifiuta);
        imageViewRifiuta.setId(ImageView.generateViewId());
        imageViewRifiuta.setImageResource(R.drawable.icon_rifiuta);
        imageViewRifiuta.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageViewRifiuta.setAdjustViewBounds(true);
        constraintLayout.addView(imageViewRifiuta); // Aggiungi l'ImageView al ConstraintLayout

        ImageView imageViewAccetta = new ImageView(context);
        ConstraintLayout.LayoutParams imageParamsAccetta = new ConstraintLayout.LayoutParams(
                createCardFunction.convertDpToPx(40, context),
                createCardFunction.convertDpToPx(40, context)
        );
        imageParamsAccetta.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        imageParamsAccetta.startToEnd = imageViewRifiuta.getId();
        imageParamsAccetta.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        imageParamsAccetta.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        imageParamsAccetta.setMargins(createCardFunction.convertDpToPx(15, context), 0, createCardFunction.convertDpToPx(10, context), 0);
        imageViewAccetta.setLayoutParams(imageParamsAccetta);
        imageViewAccetta.setId(ImageView.generateViewId());
        imageViewAccetta.setImageResource(R.drawable.icon_accetta);
        imageViewAccetta.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageViewAccetta.setAdjustViewBounds(true);
        constraintLayout.addView(imageViewAccetta); // Aggiungi l'ImageView al ConstraintLayout

        setupCardListenerOfferta(cardView, offerta, imageViewRifiuta, imageViewAccetta);
        cardView.addView(constraintLayout);

        return cardView;
    }

    private void setupCardListenerOfferta(CardView cardView, OffertaastasilenziosaModel offerta, ImageView rifiuta, ImageView accetta) {
        String messageString = "Cliccato 'accetta/rifiuta' offerta in 40dettaglio";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        // Assumendo che cardView sia l'istanza della CardView che hai creato dinamicamente
        function.setTouchListenerForAnimation(rifiuta,this);
        function.setTouchListenerForAnimation(accetta,this);

        rifiuta.setOnClickListener(view -> {
            OffertaastasilenziosaModel offertaUpdate = new OffertaastasilenziosaModel(id, offerta.getprezzoofferto(), offerta.getemailutente(), "RIFIUTATA");
            presenter.updateStatoOffertaRifiutata(offertaUpdate, cardView);
        });
        accetta.setOnClickListener(view -> {
            OffertaastasilenziosaModel offertaUpdate = new OffertaastasilenziosaModel(id, offerta.getprezzoofferto(), offerta.getemailutente(), "ACCETTATA");
            prezzovenduto = offerta.getprezzoofferto();
            presenter.updateStatoOffertaAccettata(offertaUpdate);

        });
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

                        hours.setText(String.valueOf(giorniRimanenti));
                        textViewDay.setText("GIORNI");

                    } else {
                        hours.setText(String.valueOf(oreRimanenti));
                        textViewDay.setText("ORE");

                    }

                    minute.setText(String.valueOf(minutiRimanenti));
                    second.setText(String.valueOf(secondiRimanenti));

                    // Aggiorna ogni secondo
                    handler.postDelayed(this, 1000);
                } else {
                    // Countdown terminato
                    hours.setText("0");
                    minute.setText("0");
                    second.setText("0");

                    Intent intent = new Intent(Activity41DettaglioASAperta.this, Activity43DettaglioASChiusa.class);
                    intent.putExtra(ID_KEY, id);
                    intent.putExtra(STATO_KEY, false);
                    intent.putExtra(TITOLO_KEY, titolo);
                    intent.putExtra(PAROLE_CHIAVE_KEY, parolechiave);
                    intent.putExtra(CATEGORIA_KEY, categoria);
                    intent.putExtra(SOTTOCATEGORIA_KEY, sottocategoria);
                    intent.putExtra(DESCRIZIONE_PRODOTTO_KEY, descrizioneprodotto);
                    intent.putExtra(DATA_FINE_ASTA_KEY, datafineasta);
                    intent.putExtra(IMMAGINE_PRODOTTO_KEY, immagineprodotto);
                    intent.putExtra(CONTEGGIO_UTENTI_KEY, conteggioUtenti);
                    intent.putExtra(EMAIL_KEY, email);
                    intent.putExtra(PASSWORD_KEY, password);


                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                }
            }
        });
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
    public void onUpdateSuccessAccettata(MessageResponse body) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "41");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "offerta accettata");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        Intent intent = new Intent(Activity41DettaglioASAperta.this, Activity43DettaglioASChiusa.class);
        intent.putExtra(ID_KEY, id);
        intent.putExtra(STATO_KEY, stato);
        intent.putExtra(TITOLO_KEY, titolo);
        intent.putExtra(PAROLE_CHIAVE_KEY, parolechiave);
        intent.putExtra(CATEGORIA_KEY, categoria);
        intent.putExtra(SOTTOCATEGORIA_KEY, sottocategoria);
        intent.putExtra(DESCRIZIONE_PRODOTTO_KEY, descrizioneprodotto);
        intent.putExtra(VENDITORE_KEY, venditore);
        intent.putExtra(IMMAGINE_PRODOTTO_KEY, immagineprodotto);
        intent.putExtra(DATA_FINE_ASTA_KEY, datafineasta);
        intent.putExtra(CONTEGGIO_UTENTI_KEY, conteggioUtenti);
        intent.putExtra(PREZZO_VENDITA_KEY, prezzovenduto);

        finish();
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onUpdateSuccessRifiutata(MessageResponse body, CardView card) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "41");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "offerta rifiutata");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        card.setVisibility(View.GONE);
    }

    @Override
    public void onUpdateFailure(MessageResponse body) {
        Toast.makeText(Activity41DettaglioASAperta.this, body.getMessage(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void scaricaOfferta(MessageResponseOfferte userResponse) {
        offerteList.addAll(userResponse.getAste());
        aggiornaOfferte(offerteList);
    }

    @Override
    public void mostraErrore(MessageResponse message) {
        function.updateNestedScrollViewOfferte(linearOfferte, false, this);
    }
}
