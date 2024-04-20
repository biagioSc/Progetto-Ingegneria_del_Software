package com.example.dd24client.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;
import com.example.dd24client.model.offertaastasilenziosaModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.network.RetrofitClient;
import com.example.dd24client.presenter.presenter41DettaglioASApertaPresenter;
import com.example.dd24client.R;
import com.example.dd24client.utils.messageResponse;
import com.example.dd24client.utils.messageResponseOfferte;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.time.LocalDate;

public class activity41DettaglioASApertaActivity extends AppCompatActivity implements activity41DettaglioASApertaView {
    private Animation buttonAnimation;
    private ImageView indietroImageView, image_product;
    private TextView nomeProdottoTextView, categoriaTextView, sottocategoriaTextView, testoDescrizioneTextView, followerTextView, hours, minute, second, primo, ultimo, min;
    private List<offertaastasilenziosaModel> offerteList = new ArrayList<>();
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private presenter41DettaglioASApertaPresenter presenter;
    private NestedScrollView nestedScrollView;
    private LinearLayout linearOfferte;
    private Integer id, conteggioUtenti;
    private Boolean stato;
    private String titolo, descrizioneprodotto, venditore, parolechiave, immagineprodotto, categoria, sottocategoria, datafineasta, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_41_dettaglio_vendi_as);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.darkOrange));
        }

        setComponents();
        setListener();

        setExtraParameters();
        startCountdown();
    }

    private void setComponents() {
        presenter = new presenter41DettaglioASApertaPresenter(this, apiService);

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
        nestedScrollView = findViewById(R.id.nestedScroll2);
        linearOfferte = findViewById(R.id.linearOfferte);
        buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.button_animation);


    }

    private void setListener() {

        indietroImageView.setOnClickListener(v -> {
            overridePendingTransition(0, 0);
            finish();
        });

    }

    private void setExtraParameters() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0); // 0 è un valore di default
        stato = intent.getBooleanExtra("stato", false); // false è un valore di default
        titolo = intent.getStringExtra("titolo");
        parolechiave = intent.getStringExtra("parolechiave");
        categoria = intent.getStringExtra("categoria");
        sottocategoria = intent.getStringExtra("sottocategoria");
        descrizioneprodotto = intent.getStringExtra("descrizioneprodotto");
        venditore = intent.getStringExtra("venditore");
        immagineprodotto = intent.getStringExtra("immagineprodotto");
        datafineasta = intent.getStringExtra("datafineasta");
        conteggioUtenti = intent.getIntExtra("conteggioUtenti", 0); // 0 è un valore di default
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");

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

    private void setTouchListenerForAnimation(View view) {
        view.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.startAnimation(buttonAnimation);
                new Handler().postDelayed(v::clearAnimation, 100);
            }
            return false;
        });
    }

    public void aggiornaOfferte(List<offertaastasilenziosaModel> aste) {
        linearOfferte.removeAllViews(); // Rimuovi tutte le view esistenti

        if (aste.isEmpty()) {
            updateNestedScrollView(nestedScrollView, false);
            return;
        }

        for (int i = 0; i < aste.size(); i ++) {
            offertaastasilenziosaModel offerta = aste.get(i);

            CardView cardView1;

            cardView1 = creaCardViewOfferta(offerta, this);
            linearOfferte.addView(cardView1);

        }

    }

    private CardView creaCardViewOfferta(offertaastasilenziosaModel offerta, Context context) {
        // Crea la CardView e imposta le dimensioni e gli altri attributi
        CardView cardView = new CardView(context);
        cardView.setId(CardView.generateViewId());
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );

        params.topMargin = convertDpToPx(10, context);
        cardView.setPadding(convertDpToPx(16, context), convertDpToPx(5, context), convertDpToPx(16, context), convertDpToPx(5, context));
        cardView.setLayoutParams(params);
        cardView.setRadius(convertDpToPx(20, context));
        cardView.setCardElevation(convertDpToPx(8, context));
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
        cardView.setContentPadding(convertDpToPx(8, context), convertDpToPx(8, context), convertDpToPx(8, context), convertDpToPx(8, context));

        // Crea il ConstraintLayout che conterrà gli elementi della CardView
        ConstraintLayout constraintLayout = new ConstraintLayout(context);
        constraintLayout.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        ));

        // Crea e configura il TextView per il nome del prodotto
        TextView textViewOfferente = new TextView(context);
        setTextViewAttributes(textViewOfferente, offerta.getemailutente(), 18, R.color.black, context);
        textViewOfferente.setId(View.generateViewId());
        textViewOfferente.setTypeface(null, Typeface.BOLD);
        ConstraintLayout.LayoutParams lpProductName = new ConstraintLayout.LayoutParams(
                convertDpToPx(180, context),
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        lpProductName.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        lpProductName.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        lpProductName.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        textViewOfferente.setLayoutParams(lpProductName);
        constraintLayout.addView(textViewOfferente);

        // Crea e configura il TextView per il numero di persone interessate
        TextView textPeopleInterested = new TextView(context);
        setTextViewAttributes(textPeopleInterested, String.valueOf(offerta.getprezzoofferto())+"€", 18, R.color.darkOrange, context);
        textPeopleInterested.setId(View.generateViewId());
        textPeopleInterested.setTypeface(null, Typeface.BOLD);
        ConstraintLayout.LayoutParams lpPeopleInterested = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        lpPeopleInterested.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        lpPeopleInterested.startToEnd = textViewOfferente.getId();
        lpPeopleInterested.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        lpPeopleInterested.setMargins(convertDpToPx(8, context), 0, 0, 0);
        textPeopleInterested.setLayoutParams(lpPeopleInterested);
        constraintLayout.addView(textPeopleInterested);

        ImageView imageViewRifiuta = new ImageView(context);
        ConstraintLayout.LayoutParams imageParamsRifiuta = new ConstraintLayout.LayoutParams(
                convertDpToPx(40, context),
                convertDpToPx(40, context)
        );
        imageParamsRifiuta.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        imageParamsRifiuta.startToEnd = textPeopleInterested.getId();
        imageParamsRifiuta.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        imageParamsRifiuta.setMargins(convertDpToPx(15, context), 0, 0, 0);
        imageViewRifiuta.setLayoutParams(imageParamsRifiuta);
        imageViewRifiuta.setId(ImageView.generateViewId());
        imageViewRifiuta.setImageResource(R.drawable.icon_rifiuta);
        imageViewRifiuta.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageViewRifiuta.setAdjustViewBounds(true);
        constraintLayout.addView(imageViewRifiuta); // Aggiungi l'ImageView al ConstraintLayout

        ImageView imageViewAccetta = new ImageView(context);
        ConstraintLayout.LayoutParams imageParamsAccetta = new ConstraintLayout.LayoutParams(
                convertDpToPx(40, context),
                convertDpToPx(40, context)
        );
        imageParamsAccetta.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        imageParamsAccetta.startToEnd = imageViewRifiuta.getId();
        imageParamsAccetta.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        imageParamsAccetta.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        imageParamsAccetta.setMargins(convertDpToPx(15, context), 0, convertDpToPx(10, context), 0);
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

    private void setTextViewAttributes(TextView textView, String text, int textSize, int textColor, Context context) {
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        textView.setTextColor(ContextCompat.getColor(context, textColor));
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setCompoundDrawablePadding(convertDpToPx(4, context));
    }

    private int convertDpToPx(int dp, Context context) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                context.getResources().getDisplayMetrics()
        );
    }

    private void setupCardListenerOfferta(CardView cardView, offertaastasilenziosaModel offerta, ImageView rifiuta, ImageView accetta) {
        // Assumendo che cardView sia l'istanza della CardView che hai creato dinamicamente
        setTouchListenerForAnimation(rifiuta);
        setTouchListenerForAnimation(accetta);

        rifiuta.setOnClickListener(view -> {
            offertaastasilenziosaModel offertaUpdate = new offertaastasilenziosaModel(id, offerta.getprezzoofferto(), offerta.getemailutente(), "RIFIUTATA");
            presenter.updateStatoOffertaRifiutata(offertaUpdate, cardView);
        });
        accetta.setOnClickListener(view -> {
            offertaastasilenziosaModel offertaUpdate = new offertaastasilenziosaModel(id, offerta.getprezzoofferto(), offerta.getemailutente(), "ACCETTATA");
            presenter.updateStatoOffertaAccettata(offertaUpdate);

        });
    }

    private void updateNestedScrollView(NestedScrollView nestedScrollView2, boolean removeText) {
        // Ottieni il LinearLayout interno dal HorizontalScrollView
        ConstraintLayout constraintLayout = (ConstraintLayout) nestedScrollView2.getChildAt(0);

        if (removeText) {
            // Rimuovi il TextView se presente
            if (constraintLayout.getChildCount() > 0 && constraintLayout.getChildAt(0) instanceof TextView) {
                constraintLayout.removeViewAt(0);
            }
        } else {
            // Controlla se ci sono già elementi nel LinearLayout
            if (constraintLayout.getChildCount() == 0) {
                // Non ci sono elementi, quindi aggiungi un TextView
                TextView textView = new TextView(this);
                textView.setText("Non sono presenti offerte");
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18); // Imposta la dimensione del testo a 18sp
                textView.setTypeface(null, Typeface.BOLD); // Imposta il testo in grassetto

                // Imposta la larghezza e l'altezza per coprire l'intero HorizontalScrollView
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                textView.setLayoutParams(params);
                constraintLayout.addView(textView);
            }
        }
    }

    private void removeAllCardViews(ConstraintLayout constraintLayout) {
        for (int i = 0; i < constraintLayout.getChildCount(); i++) {
            View child = constraintLayout.getChildAt(i);
            if (child instanceof CardView) {
                constraintLayout.removeViewAt(i);
                i--; // Decrementa l'indice dopo la rimozione
            }
        }
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

        final long fineGiornataMillis = getFineGiornataMillis(cal);
        final long millisFineAsta = getFineAstaMillis(cal, giorno, mese, anno);

        handler.post(new Runnable() {
            @Override
            public void run() {
                long millisRimanenti = millisFineAsta - System.currentTimeMillis();

                if (millisRimanenti > 0) {
                    long oreRimanenti = millisRimanenti / (60 * 60 * 1000);
                    long minutiRimanenti = (millisRimanenti % (60 * 60 * 1000)) / (60 * 1000);
                    long secondiRimanenti = ((millisRimanenti % (60 * 60 * 1000)) % (60 * 1000)) / 1000;

                    hours.setText(String.valueOf(oreRimanenti));
                    minute.setText(String.valueOf(minutiRimanenti));
                    second.setText(String.valueOf(secondiRimanenti));

                    // Aggiorna ogni secondo
                    handler.postDelayed(this, 1000);
                } else {
                    // Countdown terminato
                    hours.setText("0");
                    minute.setText("0");
                    second.setText("0");

                    Intent intent = new Intent(activity41DettaglioASApertaActivity.this, activity43DettaglioASChiusaActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("stato", false);
                    intent.putExtra("titolo", titolo);
                    intent.putExtra("parolechiave", parolechiave);
                    intent.putExtra("categoria", categoria);
                    intent.putExtra("sottocategoria", sottocategoria);
                    intent.putExtra("descrizioneprodotto", descrizioneprodotto);
                    intent.putExtra("datafineasta", datafineasta);
                    intent.putExtra("immagineprodotto", immagineprodotto);
                    intent.putExtra("conteggioUtenti", conteggioUtenti);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);

                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                }
            }
        });
    }

    private long getFineGiornataMillis(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTimeInMillis();
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
    public void onUpdateSuccessAccettata(messageResponse body) {
        Intent intent = new Intent(activity41DettaglioASApertaActivity.this, activity43DettaglioASChiusaActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("stato",stato);
        intent.putExtra("titolo", titolo);
        intent.putExtra("parolechiave", parolechiave);
        intent.putExtra("categoria", categoria);
        intent.putExtra("sottocategoria", sottocategoria);
        intent.putExtra("descrizioneprodotto", descrizioneprodotto);
        intent.putExtra("venditore", venditore);
        intent.putExtra("immagineprodotto", immagineprodotto);
        intent.putExtra("datafineasta", datafineasta);
        intent.putExtra("conteggioUtenti", conteggioUtenti);

        finish();
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onUpdateSuccessRifiutata(messageResponse body, CardView card) {
        card.setVisibility(View.GONE);
    }

    @Override
    public void onUpdateFailure(messageResponse body) {
        Toast.makeText(activity41DettaglioASApertaActivity.this, body.getMessage(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void scaricaOfferta(messageResponseOfferte userResponse) {
        for (offertaastasilenziosaModel asta : userResponse.getAste()) {
            offerteList.add(asta);
        }
        aggiornaOfferte(offerteList);
    }

    @Override
    public void mostraErrore(messageResponse message) {
        updateNestedScrollView(nestedScrollView, false);
    }
}
