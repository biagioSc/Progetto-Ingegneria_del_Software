package com.example.dd24client.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
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
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dd24client.model.astaribassoModel;
import com.example.dd24client.model.astasilenziosaModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.network.RetrofitClient;
import com.example.dd24client.presenter.presenter34HomeVenditorePresenter;
import com.example.dd24client.R;
import com.example.dd24client.utils.messageResponse;
import com.example.dd24client.utils.messageResponseRibasso;
import com.example.dd24client.utils.messageResponseSilenziosa;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class activity34HomeVenditoreActivity extends AppCompatActivity implements activity34HomeVenditoreView {
    private Animation buttonAnimation;
    private TextView buttonHomeAcquista, buttonHomeVendi, buttonViewAll;
    private TextView textCat1, textCat2, textCat3, textCat4, textCat5, textCat6, textCat7, textCat8, textCat9, textCat10, textCat11, textCat12, textCat13, textCat14;
    private CardView creaar, creaas;
    private EditText searchEditText;
    private SearchView searchView;
    private String email="";
    private String password="";
    private final String activity="venditore";
    private BottomNavigationView navigation;
    private HorizontalScrollView horizontalCardsScrollView;
    private LinearLayout linearLayout;
    private final List<astaribassoModel> asteAlRibasso = new ArrayList<>();
    private final List<astasilenziosaModel> asteSilenziosa = new ArrayList<>();
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private presenter34HomeVenditorePresenter presenter;
    private static int vuoto=0;
    private boolean activityStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_34_home_venditore); // Sostituisci con il tuo file layout

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.darkOrange));

        setComponents();
        setListener();

        setExtraParameters();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (activityStarted) {
            asteAlRibasso.clear();
            asteSilenziosa.clear();

            removeAllCardViews(linearLayout);

            vuoto = 0;
            presenter.astaSilenziosa(email);
            presenter.astaRibasso(email);
        }
        activityStarted = false; // Reimposta il flag
    }

    private void setComponents() {
        presenter = new presenter34HomeVenditorePresenter(this, apiService);

        buttonHomeAcquista = findViewById(R.id.buttonHomeAcquista);
        buttonHomeVendi = findViewById(R.id.buttonHomeVendi);
        buttonViewAll = findViewById(R.id.buttonViewAll);
        searchView = findViewById(R.id.searchView);
        searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        creaar = findViewById(R.id.CardAR);
        creaas = findViewById(R.id.CardAS);
        textCat1 = findViewById(R.id.textViewCat1);
        textCat2 = findViewById(R.id.textViewCat2);
        textCat3 = findViewById(R.id.textViewCat3);
        textCat4 = findViewById(R.id.textViewCat4);
        textCat5 = findViewById(R.id.textViewCat5);
        textCat6 = findViewById(R.id.textViewCat6);
        textCat7 = findViewById(R.id.textViewCat7);
        textCat8 = findViewById(R.id.textViewCat8);
        textCat9 = findViewById(R.id.textViewCat9);
        textCat10 = findViewById(R.id.textViewCat10);
        textCat11 = findViewById(R.id.textViewCat11);
        textCat12 = findViewById(R.id.textViewCat12);
        textCat13 = findViewById(R.id.textViewCat13);
        textCat14 = findViewById(R.id.textViewCat14);
        navigation = findViewById(R.id.bottom_navigation);
        horizontalCardsScrollView = findViewById(R.id.horizontalCardsScrollView);
        linearLayout = findViewById(R.id.LinearLayoutCard);

        buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.button_animation);

        int selectedItem = R.id.navigation_home;
        navigation.setSelectedItemId(selectedItem);

        searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(ContextCompat.getColor(this, R.color.black));
        searchEditText.setHintTextColor(ContextCompat.getColor(this, R.color.grey));
        ImageView searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_mag_icon);
        searchIcon.setColorFilter(ContextCompat.getColor(this, R.color.grey));
        ImageView closeButton = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        closeButton.setColorFilter(ContextCompat.getColor(this, R.color.grey), PorterDuff.Mode.SRC_ATOP);

    }

    private void setListener() {
        setTouchListenerForAnimation(buttonHomeAcquista);
        setTouchListenerForAnimation(buttonHomeVendi);
        setTouchListenerForAnimation(buttonViewAll);
        setTouchListenerForAnimation(creaar);
        setTouchListenerForAnimation(creaas);
        setTouchListenerForAnimation(textCat1);
        setTouchListenerForAnimation(textCat2);
        setTouchListenerForAnimation(textCat3);
        setTouchListenerForAnimation(textCat4);
        setTouchListenerForAnimation(textCat5);
        setTouchListenerForAnimation(textCat6);
        setTouchListenerForAnimation(textCat7);
        setTouchListenerForAnimation(textCat8);
        setTouchListenerForAnimation(textCat9);
        setTouchListenerForAnimation(textCat10);
        setTouchListenerForAnimation(textCat11);
        setTouchListenerForAnimation(textCat12);
        setTouchListenerForAnimation(textCat13);
        setTouchListenerForAnimation(textCat14);

        textCat1.setSelected(true);
        textCat1.setTextColor(Color.parseColor("#FFFFFF"));

        textCat1.setOnClickListener(v -> onTextViewClick(textCat1));
        textCat2.setOnClickListener(v -> onTextViewClick(textCat2));
        textCat3.setOnClickListener(v -> onTextViewClick(textCat3));
        textCat4.setOnClickListener(v -> onTextViewClick(textCat4));
        textCat5.setOnClickListener(v -> onTextViewClick(textCat5));
        textCat6.setOnClickListener(v -> onTextViewClick(textCat6));
        textCat7.setOnClickListener(v -> onTextViewClick(textCat7));
        textCat8.setOnClickListener(v -> onTextViewClick(textCat8));
        textCat9.setOnClickListener(v -> onTextViewClick(textCat9));
        textCat10.setOnClickListener(v -> onTextViewClick(textCat10));
        textCat11.setOnClickListener(v -> onTextViewClick(textCat11));
        textCat12.setOnClickListener(v -> onTextViewClick(textCat12));
        textCat13.setOnClickListener(v -> onTextViewClick(textCat13));
        textCat14.setOnClickListener(v -> onTextViewClick(textCat14));

        buttonHomeAcquista.setOnClickListener(v->{
            Intent intent = new Intent(activity34HomeVenditoreActivity.this, activity15HomeAcquirente.class);
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

        navigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.navigation_home) {
                return true;
            } else if (id == R.id.navigation_cerca) {
                openCerca();
                return true;
            } else if (id == R.id.navigation_vendi) {
                Intent intent = new Intent(activity34HomeVenditoreActivity.this, activity46VendiActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("user", activity);
                activityStarted = true;
                startActivity(intent);
                overridePendingTransition(0, 0);
                int selectedItem = R.id.navigation_home;
                navigation.setSelectedItemId(selectedItem);
                return false;
            } else if (id == R.id.navigation_aste) {
                // Aggiungi qui il codice per gestire il clic su "Aste"
                Intent intent = new Intent(activity34HomeVenditoreActivity.this, activity35AsteRibassoVenditoreActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("user", activity);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.navigation_profilo) {
                Intent intent = new Intent(activity34HomeVenditoreActivity.this, activity27ProfiloActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("user", activity);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return false;
        });

        buttonViewAll.setOnClickListener(v->{
            Intent intent = new Intent(activity34HomeVenditoreActivity.this, activity35AsteRibassoVenditoreActivity.class);
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            intent.putExtra("user", activity);
            startActivity(intent);

            overridePendingTransition(0, 0);
            finish();
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //query al database
                openCerca();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Qui puoi gestire la modifica del testo di ricerca in tempo reale se necessario
                return false;
            }
        });

        creaar.setOnClickListener(v->{
            Intent intent = new Intent(activity34HomeVenditoreActivity.this, activity37CreaARActivity.class);
            intent.putExtra("caller", "34");
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            activityStarted = true;
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

        creaas.setOnClickListener(v->{
            Intent intent = new Intent(activity34HomeVenditoreActivity.this, activity39CreaASActivity.class);
            intent.putExtra("caller", "34");
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            activityStarted = true;
            startActivity(intent);
            overridePendingTransition(0, 0);
        });
    }

    private void setExtraParameters() {
        Intent intentRicevuto = getIntent();
        email = intentRicevuto.getStringExtra("email");
        password = intentRicevuto.getStringExtra("password");

        removeAllCardViews(linearLayout);

        vuoto = 0;
        presenter.astaSilenziosa(email);
        presenter.astaRibasso(email);

    }

    private void onTextViewClick(TextView clickedTextView) {
        // Deselezionare tutti i TextView
        textCat1.setSelected(false);
        textCat1.setTextColor(Color.parseColor("#000000"));
        textCat2.setSelected(false);
        textCat2.setTextColor(Color.parseColor("#000000"));
        textCat3.setSelected(false);
        textCat3.setTextColor(Color.parseColor("#000000"));
        textCat4.setSelected(false);
        textCat4.setTextColor(Color.parseColor("#000000"));
        textCat5.setSelected(false);
        textCat5.setTextColor(Color.parseColor("#000000"));
        textCat6.setSelected(false);
        textCat6.setTextColor(Color.parseColor("#000000"));
        textCat7.setSelected(false);
        textCat7.setTextColor(Color.parseColor("#000000"));
        textCat8.setSelected(false);
        textCat8.setTextColor(Color.parseColor("#000000"));
        textCat9.setSelected(false);
        textCat9.setTextColor(Color.parseColor("#000000"));
        textCat10.setSelected(false);
        textCat10.setTextColor(Color.parseColor("#000000"));
        textCat11.setSelected(false);
        textCat11.setTextColor(Color.parseColor("#000000"));
        textCat12.setSelected(false);
        textCat12.setTextColor(Color.parseColor("#000000"));
        textCat13.setSelected(false);
        textCat13.setTextColor(Color.parseColor("#000000"));
        textCat14.setSelected(false);
        textCat14.setTextColor(Color.parseColor("#000000"));

        // Selezionare il TextView cliccato
        clickedTextView.setSelected(true);
        clickedTextView.setTextColor(Color.parseColor("#FFFFFF"));

        List<astaribassoModel> asteAlRibassoCat = new ArrayList<>();
        List<astasilenziosaModel> asteSilenziosaCat = new ArrayList<>();

        removeAllCardViews(linearLayout);
        if(clickedTextView.getText().equals("Tutti")) {
            int update=0;

            asteAlRibassoCat.addAll(asteAlRibasso);
            if(!asteAlRibassoCat.isEmpty()) {
                aggiornaAsteRibasso(asteAlRibassoCat);
            }else{
                update++;
            }

            asteSilenziosaCat.addAll(asteSilenziosa);
            if(!asteSilenziosaCat.isEmpty()) {
                aggiornaAsteSilenziosa(asteSilenziosaCat);
            }else{
                update++;
            }

            updateHorizontalScrollView(horizontalCardsScrollView, update != 2);
        }else{
            int update=0;

            for (astaribassoModel asta : asteAlRibasso) {
                if(asta.getCategoria().contentEquals(clickedTextView.getText())) {
                    asteAlRibassoCat.add(asta);
                }
            }
            if(!asteAlRibassoCat.isEmpty()) {
                aggiornaAsteRibasso(asteAlRibassoCat);
            }else{
                update++;
            }

            for (astasilenziosaModel asta : asteSilenziosa) {
                if(asta.getCategoria().contentEquals(clickedTextView.getText())) {
                    asteSilenziosaCat.add(asta);
                }
            }
            if(!asteSilenziosaCat.isEmpty()) {
                aggiornaAsteSilenziosa(asteSilenziosaCat);
            }else{
                update++;
            }

            updateHorizontalScrollView(horizontalCardsScrollView, update != 2);
        }
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

    private void openCerca() {
        Intent intent = new Intent(activity34HomeVenditoreActivity.this, activity44SearchActivity.class);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        intent.putExtra("query", String.valueOf(searchEditText.getText()));
        intent.putExtra("user", activity);

        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void aggiornaAsteSilenziosa(List<astasilenziosaModel> aste) {
        for (int i = 0; i < aste.size(); i += 2) {

            astasilenziosaModel asta1 = aste.get(i);
            astasilenziosaModel asta2 = (i + 1 < aste.size()) ? aste.get(i + 1) : null;

            CardView cardView1;

            if(asta1.isStato()) {
                cardView1 = cardApertaSilenziosaVenditore(asta1, this);
                linearLayout.addView(cardView1);
            }else{
                cardView1 = cardChiusaSilenziosaVenditore(asta1, this);
                linearLayout.addView(cardView1);
            }

            CardView cardView2;
            if (asta2 != null) {
                if(asta2.isStato()) {
                    cardView2 = cardApertaSilenziosaVenditore(asta2, this);
                    linearLayout.addView(cardView2);
                }else{
                    cardView2 = cardChiusaSilenziosaVenditore(asta2, this);
                    linearLayout.addView(cardView2);
                }
            }

        }
    }

    public void aggiornaAsteRibasso(List<astaribassoModel> aste) {
        for (int i = 0; i < aste.size(); i += 2) {

            astaribassoModel asta1 = aste.get(i);
            astaribassoModel asta2 = (i + 1 < aste.size()) ? aste.get(i + 1) : null;

            CardView cardView1;

            if(asta1.isStato()) {
                cardView1 = cardApertaRibassoVenditore(asta1, this);
                linearLayout.addView(cardView1);
            }else{
                cardView1 = cardChiusaRibassoVenditore(asta1, this);
                linearLayout.addView(cardView1);
            }

            CardView cardView2;
            if (asta2 != null) {
                if(asta2.isStato()) {
                    cardView2 = cardApertaRibassoVenditore(asta2, this);
                    linearLayout.addView(cardView2);
                }else{
                    cardView2 = cardChiusaRibassoVenditore(asta2, this);
                    linearLayout.addView(cardView2);
                }
            }

        }
    }

    private CardView cardApertaRibassoVenditore(astaribassoModel asta, Context context) {
        CardView cardView = new CardView(context);
        ConstraintLayout constraintLayoutCard = new ConstraintLayout(context);
        ImageView imageProduct = new ImageView(context);
        TextView textPeopleInterested = new TextView(context);
        TextView textProductName = new TextView(context);
        ConstraintLayout constraintLayoutInterno = new ConstraintLayout(context);
        TextView textPrice = new TextView(context);
        ImageView iconClock = new ImageView(context);
        TextView textTimer = new TextView(context);
        TextView textStateAuction = new TextView(context);

        //Setting cardView
        cardView.setId(asta.getId());
        ConstraintLayout.LayoutParams paramsCardView = new ConstraintLayout.LayoutParams(
                convertDpToPx(160, context), // width
                convertDpToPx(270, context)  // height
        );

        paramsCardView.setMargins(convertDpToPx(8, context), convertDpToPx(8, context), convertDpToPx(8, context), convertDpToPx(8, context));
        cardView.setLayoutParams(paramsCardView);
        cardView.setRadius(convertDpToPx(20, context));
        cardView.setCardElevation(convertDpToPx(8, context));
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));

        //Setting constraintLayoutCard
        ConstraintLayout.LayoutParams paramsConstraintLayoutCard = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );
        constraintLayoutCard.setLayoutParams(paramsConstraintLayoutCard);

        //Setting imageProduct
        imageProduct.setId(ImageView.generateViewId());
        ConstraintLayout.LayoutParams paramsImageProduct = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                convertDpToPx(120, context)
        );
        paramsImageProduct.bottomToTop = textPeopleInterested.getId();
        paramsImageProduct.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.setMargins(0, convertDpToPx(0, context), 0, convertDpToPx(2, context));
        imageProduct.setLayoutParams(paramsImageProduct);
        if (!isFinishing() && !isDestroyed()) {
            try {
                RequestOptions requestOptions = new RequestOptions()
                        .centerCrop();

                Glide.with(this)
                        .load(asta.getImmagineprodotto())
                        .apply(requestOptions)
                        .into(imageProduct);
            }catch (Exception e){
                imageProduct.setImageResource(R.drawable.image_notfound);
            }
        }
        imageProduct.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageProduct.setAdjustViewBounds(true);
        constraintLayoutCard.addView(imageProduct);

        //Setting textPeopleInterested
        textPeopleInterested.setId(View.generateViewId());
        ConstraintLayout.LayoutParams paramsTextPeopleInterested = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextPeopleInterested.bottomToTop = textProductName.getId();
        paramsTextPeopleInterested.topToBottom = imageProduct.getId();
        paramsTextPeopleInterested.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextPeopleInterested.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextPeopleInterested.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(2, context));
        textPeopleInterested.setLayoutParams(paramsTextPeopleInterested);
        setTextViewAttributes(textPeopleInterested, asta.getConteggioUtenti() +" interessati", 14, R.color.black, context);
        constraintLayoutCard.addView(textPeopleInterested);

        //Setting textProductName
        textProductName.setId(View.generateViewId());
        ConstraintLayout.LayoutParams paramsTextProductName = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextProductName.bottomToTop = constraintLayoutInterno.getId();
        paramsTextProductName.topToBottom = textPeopleInterested.getId();
        paramsTextProductName.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(2, context));
        textProductName.setLayoutParams(paramsTextProductName);
        setTextViewAttributes(textProductName, asta.getTitolo(), 16, R.color.black, context);
        textProductName.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textProductName);

        //Setting constraintLayoutInterno
        ConstraintLayout.LayoutParams paramsConstraintLayoutInterno = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsConstraintLayoutInterno.setMargins(convertDpToPx(12, context), 0, convertDpToPx(12, context), 0);
        paramsConstraintLayoutInterno.bottomToTop = textStateAuction.getId();
        paramsConstraintLayoutInterno.topToBottom = textProductName.getId();
        paramsConstraintLayoutInterno.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsConstraintLayoutInterno.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsConstraintLayoutCard.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(2, context));
        constraintLayoutInterno.setLayoutParams(paramsConstraintLayoutInterno);
        constraintLayoutCard.addView(constraintLayoutInterno);

        //Setting textPrice
        textPrice.setId(View.generateViewId());
        ConstraintLayout.LayoutParams paramsTextPrice = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextPrice.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextPrice.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextPrice.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextPrice.endToStart = iconClock.getId();
        textPrice.setLayoutParams(paramsTextPrice);
        setTextViewAttributes(textPrice, formatDouble(asta.getUltimoprezzo())+" €", 16, R.color.black, context);
        textPrice.setTypeface(null, Typeface.BOLD);
        constraintLayoutInterno.addView(textPrice);

        //Setting iconClock
        iconClock.setId(ImageView.generateViewId());
        ConstraintLayout.LayoutParams paramsIconClock = new ConstraintLayout.LayoutParams(
                convertDpToPx(20, context),
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );
        paramsIconClock.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsIconClock.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsIconClock.startToEnd = textPrice.getId();
        paramsIconClock.endToStart = textTimer.getId();
        paramsIconClock.setMargins(convertDpToPx(10, context), 0, 0, 0);
        iconClock.setLayoutParams(paramsIconClock);
        iconClock.setImageResource(R.drawable.icon_orologio);
        iconClock.setScaleType(ImageView.ScaleType.FIT_CENTER);
        iconClock.setAdjustViewBounds(true);
        constraintLayoutInterno.addView(iconClock); // Aggiungi l'ImageView al ConstraintLayout

        //Setting textTimer
        textTimer.setId(View.generateViewId());
        ConstraintLayout.LayoutParams paramsTextTimer = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextTimer.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextTimer.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextTimer.startToEnd = iconClock.getId();
        paramsTextTimer.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextTimer.setMargins(convertDpToPx(5, context), 0, 0, 0);
        textTimer.setLayoutParams(paramsTextTimer);
        setTextViewAttributes(textTimer, "-", 16, R.color.lightOrange, context);
        textTimer.setTypeface(null, Typeface.BOLD);
        constraintLayoutInterno.addView(textTimer);

        //Setting textStateAuction
        textStateAuction.setId(View.generateViewId());
        ConstraintLayout.LayoutParams paramsTextStateAuction = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextStateAuction.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.topToBottom = constraintLayoutInterno.getId();
        paramsTextStateAuction.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(7, context));
        textStateAuction.setLayoutParams(paramsTextStateAuction);
        textStateAuction.setPadding(convertDpToPx(16, context),convertDpToPx(5, context),convertDpToPx(16, context),convertDpToPx(5, context));
        setTextViewAttributes(textStateAuction, "APERTA", 16, R.color.white, context);
        textStateAuction.setBackgroundResource(R.drawable.text_info_arancio);
        textStateAuction.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textStateAuction);

        cardView.addView(constraintLayoutCard);

        setupCardListenerRibassoApertaVenditore(cardView, asta, textTimer, textPrice, asta);

        return cardView;
    }

    private CardView cardApertaSilenziosaVenditore(astasilenziosaModel asta, Context context) {
        CardView cardView = new CardView(context);
        ConstraintLayout constraintLayoutCard = new ConstraintLayout(context);
        ImageView imageProduct = new ImageView(context);
        TextView textOfferte = new TextView(context);
        TextView textProductName = new TextView(context);
        TextView textDate = new TextView(context);
        TextView textStateAuction = new TextView(context);

        //Setting cardView
        cardView.setId(asta.getId());
        ConstraintLayout.LayoutParams paramsCardView = new ConstraintLayout.LayoutParams(
                convertDpToPx(160, context), // width
                convertDpToPx(270, context)  // height
        );

        paramsCardView.setMargins(convertDpToPx(8, context), convertDpToPx(8, context), convertDpToPx(8, context), convertDpToPx(8, context));
        cardView.setLayoutParams(paramsCardView);
        cardView.setRadius(convertDpToPx(20, context));
        cardView.setCardElevation(convertDpToPx(8, context));
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));

        //Setting constraintLayoutCard
        ConstraintLayout.LayoutParams paramsConstraintLayoutCard = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );
        constraintLayoutCard.setLayoutParams(paramsConstraintLayoutCard);

        //Setting imageProduct
        imageProduct.setId(ImageView.generateViewId());
        ConstraintLayout.LayoutParams paramsImageProduct = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                convertDpToPx(120, context)
        );
        paramsImageProduct.bottomToTop = textOfferte.getId();
        paramsImageProduct.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.setMargins(0, convertDpToPx(0, context), 0, convertDpToPx(2, context));
        imageProduct.setLayoutParams(paramsImageProduct);
        if (!isFinishing() && !isDestroyed()) {
            try {
                RequestOptions requestOptions = new RequestOptions()
                        .centerCrop();

                Glide.with(this)
                        .load(asta.getImmagineprodotto())
                        .apply(requestOptions)
                        .into(imageProduct);
            }catch (Exception e){
                imageProduct.setImageResource(R.drawable.image_notfound);
            }
        }
        imageProduct.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageProduct.setAdjustViewBounds(true);
        constraintLayoutCard.addView(imageProduct);

        //Setting textOfferte
        textOfferte.setId(View.generateViewId());
        ConstraintLayout.LayoutParams paramsTextOfferte = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextOfferte.bottomToTop = textProductName.getId();
        paramsTextOfferte.topToBottom = imageProduct.getId();
        paramsTextOfferte.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextOfferte.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextOfferte.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(2, context));
        textOfferte.setLayoutParams(paramsTextOfferte);
        textOfferte.setPadding(convertDpToPx(16, context),convertDpToPx(5, context),convertDpToPx(16, context),convertDpToPx(5, context));
        setTextViewAttributes(textOfferte, asta.getConteggioOfferte() +" offerte", 14, R.color.white, context);
        textOfferte.setBackgroundResource(R.drawable.text_info_verde);
        textOfferte.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textOfferte);

        //Setting textProductName
        textProductName.setId(View.generateViewId());
        ConstraintLayout.LayoutParams paramsTextProductName = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextProductName.bottomToTop = textDate.getId();
        paramsTextProductName.topToBottom = textOfferte.getId();
        paramsTextProductName.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(2, context));
        textProductName.setLayoutParams(paramsTextProductName);
        setTextViewAttributes(textProductName, asta.getTitolo(), 16, R.color.black, context);
        textProductName.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textProductName);

        //Setting textDate
        textDate.setId(View.generateViewId());
        ConstraintLayout.LayoutParams paramsTextDate = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextDate.bottomToTop = textStateAuction.getId();
        paramsTextDate.topToBottom = textProductName.getId();
        paramsTextDate.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextDate.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextDate.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(2, context));
        textDate.setLayoutParams(paramsTextDate);
        setTextViewAttributes(textDate, String.valueOf(asta.getDatafineasta()), 16, R.color.lightOrange, context);
        textDate.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textDate);

        //Setting textStateAuction
        textStateAuction.setId(View.generateViewId());
        ConstraintLayout.LayoutParams paramsTextStateAuction = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextStateAuction.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.topToBottom = textDate.getId();
        paramsTextStateAuction.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(7, context));
        textStateAuction.setLayoutParams(paramsTextStateAuction);
        textStateAuction.setPadding(convertDpToPx(16, context),convertDpToPx(5, context),convertDpToPx(16, context),convertDpToPx(5, context));
        setTextViewAttributes(textStateAuction, "APERTA", 16, R.color.white, context);
        textStateAuction.setBackgroundResource(R.drawable.text_info_arancio);
        textStateAuction.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textStateAuction);

        cardView.addView(constraintLayoutCard);

        setupCardListenerSilenziosaApertaVenditore(cardView, asta, textDate);

        return cardView;
    }

    private CardView cardChiusaSilenziosaVenditore(astasilenziosaModel asta, Context context) {
        CardView cardView = new CardView(context);
        ConstraintLayout constraintLayoutCard = new ConstraintLayout(context);
        ImageView imageProduct = new ImageView(context);
        TextView textPrezzo = new TextView(context);
        TextView textProductName = new TextView(context);
        TextView textDate = new TextView(context);
        TextView textStateAuction = new TextView(context);

        //Setting cardView
        cardView.setId(asta.getId());
        ConstraintLayout.LayoutParams paramsCardView = new ConstraintLayout.LayoutParams(
                convertDpToPx(160, context), // width
                convertDpToPx(270, context)  // height
        );

        paramsCardView.setMargins(convertDpToPx(8, context), convertDpToPx(8, context), convertDpToPx(8, context), convertDpToPx(8, context));
        cardView.setLayoutParams(paramsCardView);
        cardView.setRadius(convertDpToPx(20, context));
        cardView.setCardElevation(convertDpToPx(8, context));
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));

        //Setting constraintLayoutCard
        ConstraintLayout.LayoutParams paramsConstraintLayoutCard = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );
        constraintLayoutCard.setLayoutParams(paramsConstraintLayoutCard);

        //Setting imageProduct
        imageProduct.setId(ImageView.generateViewId());
        ConstraintLayout.LayoutParams paramsImageProduct = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                convertDpToPx(120, context)
        );
        paramsImageProduct.bottomToTop = textProductName.getId();
        paramsImageProduct.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.setMargins(0, convertDpToPx(0, context), 0, convertDpToPx(2, context));
        imageProduct.setLayoutParams(paramsImageProduct);
        if (!isFinishing() && !isDestroyed()) {
            try {
                RequestOptions requestOptions = new RequestOptions()
                        .centerCrop();

                Glide.with(this)
                        .load(asta.getImmagineprodotto())
                        .apply(requestOptions)
                        .into(imageProduct);
            }catch (Exception e){
                imageProduct.setImageResource(R.drawable.image_notfound);
            }
        }
        imageProduct.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageProduct.setAdjustViewBounds(true);
        constraintLayoutCard.addView(imageProduct);

        //Setting textProductName
        textProductName.setId(View.generateViewId());
        ConstraintLayout.LayoutParams paramsTextProductName = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextProductName.bottomToTop = textPrezzo.getId();
        paramsTextProductName.topToBottom = imageProduct.getId();
        paramsTextProductName.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(2, context));
        textProductName.setLayoutParams(paramsTextProductName);
        setTextViewAttributes(textProductName, asta.getTitolo(), 16, R.color.black, context);
        textProductName.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textProductName);

        //Setting textOfferte
        textPrezzo.setId(View.generateViewId());
        ConstraintLayout.LayoutParams paramsTextPrezzo = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextPrezzo.bottomToTop = textDate.getId();
        paramsTextPrezzo.topToBottom = textProductName.getId();
        paramsTextPrezzo.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextPrezzo.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextPrezzo.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(2, context));
        textPrezzo.setLayoutParams(paramsTextPrezzo);
        if(asta.getPrezzovendita()<1){
            setTextViewAttributes(textPrezzo, "Non aggiudicata", 14, R.color.darkOrange, context);
        }else {
            setTextViewAttributes(textPrezzo, "Venduto a " + formatDouble(asta.getPrezzovendita())+ " €", 14, R.color.darkOrange, context);
        }
        textPrezzo.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textPrezzo);

        //Setting textDate
        textDate.setId(View.generateViewId());
        ConstraintLayout.LayoutParams paramsTextDate = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextDate.bottomToTop = textStateAuction.getId();
        paramsTextDate.topToBottom = textPrezzo.getId();
        paramsTextDate.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextDate.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextDate.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(2, context));
        textDate.setLayoutParams(paramsTextDate);
        setTextViewAttributes(textDate, String.valueOf(asta.getDatafineasta()), 14, R.color.black, context);
        textDate.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textDate);

        //Setting textStateAuction
        textStateAuction.setId(View.generateViewId());
        ConstraintLayout.LayoutParams paramsTextStateAuction = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextStateAuction.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.topToBottom = textDate.getId();
        paramsTextStateAuction.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(7, context));
        textStateAuction.setLayoutParams(paramsTextStateAuction);
        textStateAuction.setPadding(convertDpToPx(16, context),convertDpToPx(5, context),convertDpToPx(16, context),convertDpToPx(5, context));
        setTextViewAttributes(textStateAuction, "CONCLUSA", 16, R.color.white, context);
        textStateAuction.setBackgroundResource(R.drawable.text_info_verde);
        textStateAuction.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textStateAuction);

        cardView.addView(constraintLayoutCard);

        setupCardListenerSilenziosaChiusaVenditore(cardView, asta);

        return cardView;
    }

    private CardView cardChiusaRibassoVenditore(astaribassoModel asta, Context context) {
        CardView cardView = new CardView(context);
        ConstraintLayout constraintLayoutCard = new ConstraintLayout(context);
        ImageView imageProduct = new ImageView(context);
        TextView textPrezzo = new TextView(context);
        TextView textProductName = new TextView(context);
        TextView textInitprice = new TextView(context);
        TextView textStateAuction = new TextView(context);

        //Setting cardView
        cardView.setId(asta.getId());
        ConstraintLayout.LayoutParams paramsCardView = new ConstraintLayout.LayoutParams(
                convertDpToPx(160, context), // width
                convertDpToPx(270, context)  // height
        );

        paramsCardView.setMargins(convertDpToPx(8, context), convertDpToPx(8, context), convertDpToPx(8, context), convertDpToPx(8, context));
        cardView.setLayoutParams(paramsCardView);
        cardView.setRadius(convertDpToPx(20, context));
        cardView.setCardElevation(convertDpToPx(8, context));
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));

        //Setting constraintLayoutCard
        ConstraintLayout.LayoutParams paramsConstraintLayoutCard = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );
        constraintLayoutCard.setLayoutParams(paramsConstraintLayoutCard);

        //Setting imageProduct
        imageProduct.setId(ImageView.generateViewId());
        ConstraintLayout.LayoutParams paramsImageProduct = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                convertDpToPx(120, context)
        );
        paramsImageProduct.bottomToTop = textProductName.getId();
        paramsImageProduct.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.setMargins(0, convertDpToPx(0, context), 0, convertDpToPx(2, context));
        imageProduct.setLayoutParams(paramsImageProduct);
        if (!isFinishing() && !isDestroyed()) {
            try {
                RequestOptions requestOptions = new RequestOptions()
                        .centerCrop();

                Glide.with(this)
                        .load(asta.getImmagineprodotto())
                        .apply(requestOptions)
                        .into(imageProduct);
            }catch (Exception e){
                imageProduct.setImageResource(R.drawable.image_notfound);
            }
        }
        imageProduct.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageProduct.setAdjustViewBounds(true);
        constraintLayoutCard.addView(imageProduct);

        //Setting textProductName
        textProductName.setId(View.generateViewId());
        ConstraintLayout.LayoutParams paramsTextProductName = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextProductName.bottomToTop = textPrezzo.getId();
        paramsTextProductName.topToBottom = imageProduct.getId();
        paramsTextProductName.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(2, context));
        textProductName.setLayoutParams(paramsTextProductName);
        setTextViewAttributes(textProductName, asta.getTitolo(), 16, R.color.black, context);
        textProductName.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textProductName);

        //Setting textPrezzo
        textPrezzo.setId(View.generateViewId());
        ConstraintLayout.LayoutParams paramsTextPrezzo = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextPrezzo.bottomToTop = textInitprice.getId();
        paramsTextPrezzo.topToBottom = textProductName.getId();
        paramsTextPrezzo.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextPrezzo.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextPrezzo.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(2, context));
        textPrezzo.setLayoutParams(paramsTextPrezzo);
        if(asta.getPrezzovendita()<1){
            setTextViewAttributes(textPrezzo, "Non aggiudicata", 14, R.color.darkOrange, context);
        }else {
            setTextViewAttributes(textPrezzo, "Venduto a " + formatDouble(asta.getUltimoprezzo())+ " €", 14, R.color.darkOrange, context);
        }
        textPrezzo.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textPrezzo);

        //Setting textInitprice
        textInitprice.setId(View.generateViewId());
        ConstraintLayout.LayoutParams paramsTextInitprice = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextInitprice.bottomToTop = textStateAuction.getId();
        paramsTextInitprice.topToBottom = textPrezzo.getId();
        paramsTextInitprice.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextInitprice.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextInitprice.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(2, context));
        textInitprice.setLayoutParams(paramsTextInitprice);
        setTextViewAttributes(textInitprice, "Prezzo iniziale " + formatDouble(asta.getPrezzoiniziale())+ " €", 14, R.color.black, context);
        textInitprice.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textInitprice);

        //Setting textStateAuction
        textStateAuction.setId(View.generateViewId());
        ConstraintLayout.LayoutParams paramsTextStateAuction = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextStateAuction.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.topToBottom = textInitprice.getId();
        paramsTextStateAuction.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(7, context));
        textStateAuction.setLayoutParams(paramsTextStateAuction);
        textStateAuction.setPadding(convertDpToPx(16, context),convertDpToPx(5, context),convertDpToPx(16, context),convertDpToPx(5, context));
        setTextViewAttributes(textStateAuction, "CONCLUSA", 16, R.color.white, context);
        textStateAuction.setBackgroundResource(R.drawable.text_info_verde);
        textStateAuction.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textStateAuction);

        cardView.addView(constraintLayoutCard);

        setupCardListenerRibassoChiusaVenditore(cardView, asta);

        return cardView;
    }

    private void setupCardListenerRibassoApertaVenditore(final CardView cardView, astaribassoModel astaSelezionata, TextView timer, TextView ultimo, astaribassoModel asta) {
        // Assumendo che cardView sia l'istanza della CardView che hai creato dinamicamente
        setTouchListenerForAnimation(cardView);
        startCountdownRibasso(cardView, timer, ultimo, asta);

        cardView.setOnClickListener(view -> {
            if (astaSelezionata != null) {
                // Avvia l'attività e passa l'asta come extra
                Intent intent = new Intent(activity34HomeVenditoreActivity.this, activity40DettaglioARApertaActivity.class);
                intent.putExtra("id", astaSelezionata.getId());
                intent.putExtra("stato", astaSelezionata.isStato());
                intent.putExtra("titolo", astaSelezionata.getTitolo());
                intent.putExtra("parolechiave", astaSelezionata.getParolechiave());
                intent.putExtra("categoria", astaSelezionata.getCategoria());
                intent.putExtra("sottocategoria", astaSelezionata.getSottocategoria());
                intent.putExtra("descrizioneprodotto", astaSelezionata.getDescrizioneprodotto());
                intent.putExtra("venditore", astaSelezionata.getVenditore());
                intent.putExtra("immagineprodotto", astaSelezionata.getImmagineprodotto());
                intent.putExtra("importodecremento", astaSelezionata.getImportodecremento());
                intent.putExtra("prezzominimosegreto", astaSelezionata.getPrezzominimosegreto());
                intent.putExtra("prezzoiniziale", astaSelezionata.getPrezzoiniziale());
                intent.putExtra("timerdecremento", astaSelezionata.getTimerdecremento());
                intent.putExtra("ultimoprezzo", astaSelezionata.getUltimoprezzo());
                intent.putExtra("conteggioUtenti", astaSelezionata.getConteggioUtenti());
                intent.putExtra("created_at", astaSelezionata.getCreated_at().toString());
                intent.putExtra("prezzovendita", astaSelezionata.getPrezzovendita());
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                activityStarted = true;

                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }

    private void setupCardListenerSilenziosaApertaVenditore(final CardView cardView, astasilenziosaModel astaSelezionata, TextView timer) {
        // Assumendo che cardView sia l'istanza della CardView che hai creato dinamicamente
        setTouchListenerForAnimation(cardView);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate dataFineAsta = LocalDate.parse(astaSelezionata.getDatafineasta(), formatter);
        LocalDate dataCorrente = LocalDate.now();

        if(dataFineAsta.equals(dataCorrente)) {
            startCountdownSilenziosa(cardView, timer, astaSelezionata);
        }
        else{
            timer.setText(astaSelezionata.getDatafineasta());
        }

        cardView.setOnClickListener(view -> {
            // Avvia l'attività e passa l'asta come extra
            Intent intent = new Intent(activity34HomeVenditoreActivity.this, activity41DettaglioASApertaActivity.class);
            intent.putExtra("id", astaSelezionata.getId());
            intent.putExtra("stato", astaSelezionata.isStato());
            intent.putExtra("titolo", astaSelezionata.getTitolo());
            intent.putExtra("parolechiave", astaSelezionata.getParolechiave());
            intent.putExtra("categoria", astaSelezionata.getCategoria());
            intent.putExtra("sottocategoria", astaSelezionata.getSottocategoria());
            intent.putExtra("descrizioneprodotto", astaSelezionata.getDescrizioneprodotto());
            intent.putExtra("venditore", astaSelezionata.getVenditore());
            intent.putExtra("immagineprodotto", astaSelezionata.getImmagineprodotto());
            intent.putExtra("datafineasta", astaSelezionata.getDatafineasta());
            intent.putExtra("conteggioUtenti", astaSelezionata.getConteggioUtenti());
            intent.putExtra("prezzovendita", astaSelezionata.getPrezzovendita());
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            activityStarted = true;

            startActivity(intent);
            overridePendingTransition(0, 0);
        });
    }

    private void setupCardListenerRibassoChiusaVenditore(final CardView cardView, astaribassoModel astaSelezionata) {
        // Assumendo che cardView sia l'istanza della CardView che hai creato dinamicamente
        setTouchListenerForAnimation(cardView);
        cardView.setOnClickListener(view -> {
            if (astaSelezionata != null) {
                // Avvia l'attività e passa l'asta come extra
                Intent intent = new Intent(activity34HomeVenditoreActivity.this, activity42DettaglioARChiusaActivity.class);
                intent.putExtra("id", astaSelezionata.getId());
                intent.putExtra("stato", astaSelezionata.isStato());
                intent.putExtra("titolo", astaSelezionata.getTitolo());
                intent.putExtra("parolechiave", astaSelezionata.getParolechiave());
                intent.putExtra("categoria", astaSelezionata.getCategoria());
                intent.putExtra("sottocategoria", astaSelezionata.getSottocategoria());
                intent.putExtra("descrizioneprodotto", astaSelezionata.getDescrizioneprodotto());
                intent.putExtra("venditore", astaSelezionata.getVenditore());
                intent.putExtra("immagineprodotto", astaSelezionata.getImmagineprodotto());
                intent.putExtra("importodecremento", astaSelezionata.getImportodecremento());
                intent.putExtra("prezzominimosegreto", astaSelezionata.getPrezzominimosegreto());
                intent.putExtra("prezzoiniziale", astaSelezionata.getPrezzoiniziale());
                intent.putExtra("timerdecremento", astaSelezionata.getTimerdecremento());
                intent.putExtra("ultimoprezzo", astaSelezionata.getUltimoprezzo());
                intent.putExtra("conteggioUtenti", astaSelezionata.getConteggioUtenti());
                intent.putExtra("created_at", astaSelezionata.getCreated_at().toString());
                intent.putExtra("prezzovendita", astaSelezionata.getPrezzovendita());
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                activityStarted = true;

                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }

    private void setupCardListenerSilenziosaChiusaVenditore(final CardView cardView, astasilenziosaModel astaSelezionata) {
        // Assumendo che cardView sia l'istanza della CardView che hai creato dinamicamente
        setTouchListenerForAnimation(cardView);
        cardView.setOnClickListener(view -> {
            if (astaSelezionata != null) {
                // Avvia l'attività e passa l'asta come extra
                Intent intent = new Intent(activity34HomeVenditoreActivity.this, activity43DettaglioASChiusaActivity.class);
                intent.putExtra("id", astaSelezionata.getId());
                intent.putExtra("stato", astaSelezionata.isStato());
                intent.putExtra("titolo", astaSelezionata.getTitolo());
                intent.putExtra("parolechiave", astaSelezionata.getParolechiave());
                intent.putExtra("categoria", astaSelezionata.getCategoria());
                intent.putExtra("sottocategoria", astaSelezionata.getSottocategoria());
                intent.putExtra("descrizioneprodotto", astaSelezionata.getDescrizioneprodotto());
                intent.putExtra("venditore", astaSelezionata.getVenditore());
                intent.putExtra("immagineprodotto", astaSelezionata.getImmagineprodotto());
                intent.putExtra("datafineasta", astaSelezionata.getDatafineasta());
                intent.putExtra("conteggioUtenti", astaSelezionata.getConteggioUtenti());
                intent.putExtra("prezzovendita", astaSelezionata.getPrezzovendita());
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                activityStarted = true;

                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
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

    public static String formatDouble(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#.###"); // Specifica il formato desiderato
        return decimalFormat.format(value);
    }

    private void startCountdownSilenziosa(CardView view, TextView timer, astasilenziosaModel asta) {
        Handler handler = new Handler();

        Calendar cal = Calendar.getInstance();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Parsa la stringa di data usando il formato definito
        LocalDate data = LocalDate.parse(asta.getDatafineasta(), formatter);

        // Ottieni giorno, mese e anno dalla data
        int giorno = data.getDayOfMonth();
        int mese = data.getMonthValue();
        int anno = data.getYear();

        final long millisFineAsta = getFineAstaMillis(cal, giorno, mese, anno);

        try{
            handler.post(new Runnable() {
                @Override
                public void run() {
                    long millisRimanenti = millisFineAsta - System.currentTimeMillis();

                    if (millisRimanenti > 0) {
                        long oreRimanenti = millisRimanenti / (60 * 60 * 1000);
                        long minutiRimanenti = (millisRimanenti % (60 * 60 * 1000)) / (60 * 1000);
                        long secondiRimanenti = ((millisRimanenti % (60 * 60 * 1000)) % (60 * 1000)) / 1000;

                        String oreFormat = String.format("%02d", oreRimanenti);
                        String minutiFormat = String.format("%02d", minutiRimanenti);
                        String secondiFormat = String.format("%02d", secondiRimanenti);

                        timer.setText(oreFormat+":"+minutiFormat+":"+secondiFormat);

                        // Aggiorna ogni secondo
                        handler.postDelayed(this, 1000);
                    } else {
                        // Countdown terminato
                        view.setVisibility(View.GONE);
                    }
                }
            });
        }catch (Exception e){
            view.setVisibility(View.GONE);

        }
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

    private void startCountdownRibasso(CardView view, TextView timer, TextView ultimo , astaribassoModel asta) {
        Handler handler = new Handler();
        // Calcola il tempo corrente
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime timestamp = LocalDateTime.parse(String.valueOf(asta.getCreated_at()), formatter);
        LocalDateTime orarioProssimoDecremento = calcolaOrarioProssimoDecrementoRibasso(timestamp, asta.getTimerdecremento());

        int ora = orarioProssimoDecremento.getHour();
        int minuto = orarioProssimoDecremento.getMinute();
        int secondo = orarioProssimoDecremento.getSecond();
        int millisecondo = orarioProssimoDecremento.getNano() / 1000000; // Conversione da nanosecondi a millisecondi

        Calendar cal = Calendar.getInstance();
        final long fineGiornataMillis = getFineGiornataMillisRibasso(cal, ora, minuto, secondo, millisecondo);
        try{
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

                        String oreFormat = String.format("%02d", oreRimanenti);
                        String minutiFormat = String.format("%02d", minutiRimanenti);
                        String secondiFormat = String.format("%02d", secondiRimanenti);

                        timer.setText(oreFormat+":"+minutiFormat+":"+secondiFormat);

                        // Aggiorna ogni secondo
                        handler.postDelayed(this, 1000);
                    } else if(asta.getUltimoprezzo()>=asta.getPrezzominimosegreto()){
                        // Countdown terminato
                        asta.setUltimoprezzo(asta.getUltimoprezzo() - asta.getImportodecremento());
                        ultimo.setText(String.valueOf(asta.getUltimoprezzo()));
                        startCountdownRibasso(view, timer, ultimo, asta);
                    }else{
                        view.setVisibility(View.GONE);
                    }
                }
            });
        }catch (Exception e){
            view.setVisibility(View.GONE);

        }
    }

    public static LocalDateTime calcolaOrarioProssimoDecrementoRibasso(LocalDateTime timestamp, int intervalloMinuti) {
        long millisecondiPassati = ChronoUnit.MILLIS.between(timestamp, LocalDateTime.now());
        long millisecondiIntervallo = (long) intervalloMinuti * 60 * 1000;
        long millisecondiMancanti = millisecondiIntervallo - (millisecondiPassati % millisecondiIntervallo);
        return LocalDateTime.now().plus(Duration.ofMillis(millisecondiMancanti));
    }

    private long getFineGiornataMillisRibasso(Calendar cal, int ore, int minuti, int secondi, int mills) {
        cal.set(Calendar.HOUR_OF_DAY, ore);
        cal.set(Calendar.MINUTE, minuti);
        cal.set(Calendar.SECOND, secondi);
        cal.set(Calendar.MILLISECOND, mills);
        return cal.getTimeInMillis();
    }

    private void updateHorizontalScrollView(HorizontalScrollView horizontalScrollView, boolean removeText) {
        // Ottieni il LinearLayout interno dal HorizontalScrollView
        LinearLayout linearLayout = (LinearLayout) horizontalScrollView.getChildAt(0);

        if (removeText) {
            // Rimuovi il TextView se presente
            if (linearLayout.getChildCount() > 0 && linearLayout.getChildAt(0) instanceof TextView) {
                linearLayout.removeViewAt(0);
            }
        } else {
            // Controlla se ci sono già elementi nel LinearLayout
            if (linearLayout.getChildCount() == 0) {
                // Non ci sono elementi, quindi aggiungi un TextView
                TextView textView = new TextView(this);
                textView.setText("Non sono presenti aste");
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18); // Imposta la dimensione del testo a 18sp
                textView.setTypeface(null, Typeface.BOLD); // Imposta il testo in grassetto

                // Imposta la larghezza e l'altezza per coprire l'intero HorizontalScrollView
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                textView.setLayoutParams(params);
                linearLayout.addView(textView);
            }
        }
    }

    private void removeAllCardViews(LinearLayout linearLayout) {
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View child = linearLayout.getChildAt(i);
            if (child instanceof CardView || child instanceof TextView) {
                linearLayout.removeViewAt(i);
                i--; // Decrementa l'indice dopo la rimozione
            }
        }
    }

    @Override
    public void scaricaAstaRibasso(messageResponseRibasso userResponse) {
        asteAlRibasso.addAll(userResponse.getAste());
        aggiornaAsteRibasso(asteAlRibasso);
    }

    @Override
    public void scaricaAstaSilenziosa(messageResponseSilenziosa userResponse) {
        asteSilenziosa.addAll(userResponse.getAste());
        aggiornaAsteSilenziosa(asteSilenziosa);
    }

    @Override
    public void mostraErrore(messageResponse message) {
        vuoto = vuoto + 1;
        updateHorizontalScrollView(horizontalCardsScrollView, vuoto != 2);
    }

}

