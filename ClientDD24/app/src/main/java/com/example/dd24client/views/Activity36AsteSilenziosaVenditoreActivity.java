package com.example.dd24client.views;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dd24client.model.astasilenziosaModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.network.RetrofitClient;
import com.example.dd24client.presenter.presenter36AsteSilenziosaVenditorePresenter;
import com.example.dd24client.R;
import com.example.dd24client.utils.messageResponse;
import com.example.dd24client.utils.messageResponseSilenziosa;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class activity36AsteSilenziosaVenditoreActivity extends AppCompatActivity implements activity36AsteSilenziosaVenditoreView {
    private Animation buttonAnimation;
    private TextView buttonSilenziosa, buttonAlRibasso;
    private EditText searchEditText;
    private SearchView searchView;
    private BottomNavigationView navigation;
    private String email="";
    private String password="";
    private final String activity="venditore";
    private final List<astasilenziosaModel> asteSilenziosa = new ArrayList<>();
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private presenter36AsteSilenziosaVenditorePresenter presenter;
    private NestedScrollView nestedScrollView;
    private ConstraintLayout constraintLayout;
    private boolean activityStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_36_aste_venditore2); // Sostituisci con il tuo file layout

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
            asteSilenziosa.clear();

            removeAllCardViews(constraintLayout);
            presenter.astaSilenziosa(email);
        }
        activityStarted = false; // Reimposta il flag
    }

    private void setComponents() {
        presenter = new presenter36AsteSilenziosaVenditorePresenter(this, apiService);

        buttonSilenziosa = findViewById(R.id.buttonSilenziosa);
        buttonAlRibasso = findViewById(R.id.buttonAlRibasso);
        searchView = findViewById(R.id.searchView);
        searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        navigation = findViewById(R.id.bottom_navigation);
        buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.button_animation);
        nestedScrollView = findViewById(R.id.nestedScroll);
        constraintLayout = findViewById(R.id.constraintCard);

        int selectedItem = R.id.navigation_aste;
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
        setTouchListenerForAnimation(buttonSilenziosa);
        setTouchListenerForAnimation(buttonAlRibasso);

        buttonAlRibasso.setOnClickListener(v->{
            Intent intent = new Intent(activity36AsteSilenziosaVenditoreActivity.this, activity35AsteRibassoVenditoreActivity.class);
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            startActivity(intent);
            finish();
            overridePendingTransition(0, 0);
        });

        navigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.navigation_home) {
                Intent intent = new Intent(activity36AsteSilenziosaVenditoreActivity.this, activity34HomeVenditoreActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.navigation_cerca) {
                Intent intent = new Intent(activity36AsteSilenziosaVenditoreActivity.this, activity44SearchActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("user", activity);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.navigation_vendi) {
                Intent intent = new Intent(activity36AsteSilenziosaVenditoreActivity.this, activity46VendiActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                activityStarted = true;
                startActivity(intent);
                overridePendingTransition(0, 0);
                int selectedItem = R.id.navigation_aste;
                navigation.setSelectedItemId(selectedItem);
                return false;
            } else if (id == R.id.navigation_aste) {
                return true;
            } else if (id == R.id.navigation_profilo) {
                Intent intent = new Intent(activity36AsteSilenziosaVenditoreActivity.this, activity27ProfiloActivity.class);
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


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                openCerca();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Qui puoi gestire la modifica del testo di ricerca in tempo reale se necessario
                return false;
            }
        });

    }

    private void setExtraParameters() {
        Intent intentRicevuto = getIntent();
        email = intentRicevuto.getStringExtra("email");
        password = intentRicevuto.getStringExtra("password");

        String queryHome = intentRicevuto.getStringExtra("query");

        if (queryHome !=null) {
            if (!queryHome.isEmpty()) {
                searchEditText.setText(queryHome);

            }
        }

        removeAllCardViews(constraintLayout);
        presenter.astaSilenziosa(email);
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

    public void aggiornaAste(List<astasilenziosaModel> aste) {
        constraintLayout.removeAllViews(); // Rimuovi tutte le view esistenti

        if (aste.isEmpty()) {
            updateNestedScrollView(nestedScrollView);
            return;
        }

        int idPrec=0, idPrec2=0;

        for (int i = 0; i < aste.size(); i += 2) {
            boolean isLastIteration = (i >= aste.size() - 2);

            astasilenziosaModel asta1 = aste.get(i);
            astasilenziosaModel asta2 = (i + 1 < aste.size()) ? aste.get(i + 1) : null;

            CardView cardView1;

            if(asta1.isStato()) {
                cardView1 = cardApertaSilenziosaVenditore(asta1, this);
                constraintLayout.addView(cardView1);
            }else{
                cardView1 = cardChiusaSilenziosaVenditore(asta1, this);
                constraintLayout.addView(cardView1);
            }

            CardView cardView2;
            if (asta2 != null) {
                if(asta2.isStato()) {
                    cardView2 = cardApertaSilenziosaVenditore(asta2, this);
                    constraintLayout.addView(cardView2);
                }else{
                    cardView2 = cardChiusaSilenziosaVenditore(asta2, this);
                    constraintLayout.addView(cardView2);
                }
            }else{
                cardView2 = null;
            }

            ConstraintSet set = new ConstraintSet();
            set.clone(constraintLayout);

            if(idPrec==0){
                set.connect(cardView1.getId(), ConstraintSet.TOP,  ConstraintSet.PARENT_ID, ConstraintSet.TOP);
            }else {
                set.connect(cardView1.getId(), ConstraintSet.TOP, idPrec, ConstraintSet.BOTTOM);
            }
            if(cardView2 != null) {
                if(idPrec2==0){
                    set.connect(cardView2.getId(), ConstraintSet.TOP,  ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                }else {
                    set.connect(cardView2.getId(), ConstraintSet.TOP, idPrec2, ConstraintSet.BOTTOM);
                }

                set.connect(cardView1.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
                set.connect(cardView1.getId(), ConstraintSet.END, cardView2.getId(), ConstraintSet.START);

                set.connect(cardView2.getId(), ConstraintSet.START, cardView1.getId(), ConstraintSet.END);
                set.connect(cardView2.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);

                if (isLastIteration) {
                    set.connect(cardView1.getId(), ConstraintSet.BOTTOM,  ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
                    set.connect(cardView2.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
                }

                idPrec = cardView1.getId();
                idPrec2 = cardView2.getId();

            }else {

                set.connect(cardView1.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
                set.connect(cardView1.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);

                if (isLastIteration) {
                    set.connect(cardView1.getId(), ConstraintSet.BOTTOM,  ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
                }

                idPrec = cardView1.getId();

            }

            set.applyTo(constraintLayout);

        }
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
            Intent intent = new Intent(activity36AsteSilenziosaVenditoreActivity.this, activity41DettaglioASApertaActivity.class);
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

    private void setupCardListenerSilenziosaChiusaVenditore(final CardView cardView, astasilenziosaModel astaSelezionata) {
        // Assumendo che cardView sia l'istanza della CardView che hai creato dinamicamente
        setTouchListenerForAnimation(cardView);
        cardView.setOnClickListener(view -> {
            if (astaSelezionata != null) {
                // Avvia l'attività e passa l'asta come extra
                Intent intent = new Intent(activity36AsteSilenziosaVenditoreActivity.this, activity43DettaglioASChiusaActivity.class);
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
        if (value == (long) value) {
            return String.format("%d", (long) value);
        } else {
            return String.format("%s", value);
        }
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

    public static LocalDateTime calcolaOrarioProssimoDecrementoRibasso(LocalDateTime timestamp, int intervalloMinuti) {
        long millisecondiPassati = ChronoUnit.MILLIS.between(timestamp, LocalDateTime.now());
        long millisecondiIntervallo = (long) intervalloMinuti * 60 * 1000;
        long millisecondiMancanti = millisecondiIntervallo - (millisecondiPassati % millisecondiIntervallo);
        return LocalDateTime.now().plus(Duration.ofMillis(millisecondiMancanti));
    }

    private void updateNestedScrollView(NestedScrollView nestedScrollView) {
        // Ottieni il LinearLayout interno dal HorizontalScrollView
        ConstraintLayout constraintLayout = (ConstraintLayout) nestedScrollView.getChildAt(0);

        // Controlla se ci sono già elementi nel LinearLayout
        if (constraintLayout.getChildCount() == 0) {
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
            constraintLayout.addView(textView);
        }
    }

    private void removeAllCardViews(ConstraintLayout constraintLayout) {
        for (int i = 0; i < constraintLayout.getChildCount(); i++) {
            View child = constraintLayout.getChildAt(i);
            if (child instanceof CardView || child instanceof TextView) {
                constraintLayout.removeViewAt(i);
                i--; // Decrementa l'indice dopo la rimozione
            }
        }
    }

    private void openCerca() {
        Intent intent = new Intent(activity36AsteSilenziosaVenditoreActivity.this, activity44SearchActivity.class);
        intent.putExtra("user", activity);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        intent.putExtra("query", String.valueOf(searchEditText.getText()));

        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    public void scaricaAstaSilenziosa(messageResponseSilenziosa userResponse) {
        asteSilenziosa.addAll(userResponse.getAste());
        aggiornaAste(asteSilenziosa);

    }

    @Override
    public void mostraErrore(messageResponse message) {
        updateNestedScrollView(nestedScrollView);
    }

}

