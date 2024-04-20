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
import com.example.dd24client.model.astaribassoModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.network.RetrofitClient;
import com.example.dd24client.presenter.presenter35AsteRibassoVenditorePresenter;
import com.example.dd24client.R;
import com.example.dd24client.utils.messageResponse;
import com.example.dd24client.utils.messageResponseRibasso;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class activity35AsteRibassoVenditoreActivity extends AppCompatActivity implements activity35AsteRibassoVenditoreView {
    private Animation buttonAnimation;
    private TextView buttonSilenziosa, buttonAlRibasso;
    private EditText searchEditText;
    private SearchView searchView;
    private BottomNavigationView navigation;
    private String email="";
    private String password="";
    private final String activity="venditore";
    private final List<astaribassoModel> asteRibasso = new ArrayList<>();
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private presenter35AsteRibassoVenditorePresenter presenter;
    private NestedScrollView nestedScrollView;
    private ConstraintLayout constraintLayout;
    private boolean activityStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_35_aste_venditore); // Sostituisci con il tuo file layout

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
            asteRibasso.clear();

            removeAllCardViews(constraintLayout);
            presenter.astaRibasso(email);
        }
        activityStarted = false; // Reimposta il flag
    }

    private void setComponents() {
        presenter = new presenter35AsteRibassoVenditorePresenter(this, apiService);

        buttonSilenziosa = findViewById(R.id.buttonSilenziosa);
        buttonAlRibasso = findViewById(R.id.buttonAlRibasso);
        searchView = findViewById(R.id.searchView);
        searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        navigation = findViewById(R.id.bottom_navigation);
        buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.button_animation);
        nestedScrollView = findViewById(R.id.nestedScrollView);
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

        buttonSilenziosa.setOnClickListener(v->{
            Intent intent = new Intent(activity35AsteRibassoVenditoreActivity.this, activity36AsteSilenziosaVenditoreActivity.class);
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            startActivity(intent);
            finish();
            overridePendingTransition(0, 0);
        });

        navigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.navigation_home) {
                Intent intent = new Intent(activity35AsteRibassoVenditoreActivity.this, activity34HomeVenditoreActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.navigation_cerca) {
                Intent intent = new Intent(activity35AsteRibassoVenditoreActivity.this, activity44SearchActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("user", activity);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.navigation_vendi) {
                Intent intent = new Intent(activity35AsteRibassoVenditoreActivity.this, activity46VendiActivity.class);
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
                Intent intent = new Intent(activity35AsteRibassoVenditoreActivity.this, activity27ProfiloActivity.class);
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
        presenter.astaRibasso(email);
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

    public void aggiornaAste(List<astaribassoModel> aste) {
        constraintLayout.removeAllViews(); // Rimuovi tutte le view esistenti

        if (aste.isEmpty()) {
            updateNestedScrollView(nestedScrollView, false);
            return;
        }

        int idPrec=0, idPrec2=0;

        for (int i = 0; i < aste.size(); i += 2) {
            boolean isLastIteration = (i >= aste.size() - 2);

            astaribassoModel asta1 = aste.get(i);
            astaribassoModel asta2 = (i + 1 < aste.size()) ? aste.get(i + 1) : null;

            CardView cardView1;

            if(asta1.isStato()) {
                cardView1 = cardApertaRibassoVenditore(asta1, this);
                constraintLayout.addView(cardView1);
            }else{
                cardView1 = cardChiusaRibassoVenditore(asta1, this);
                constraintLayout.addView(cardView1);
            }

            CardView cardView2;
            if (asta2 != null) {
                if(asta2.isStato()) {
                    cardView2 = cardApertaRibassoVenditore(asta2, this);
                    constraintLayout.addView(cardView2);
                }else{
                    cardView2 = cardChiusaRibassoVenditore(asta2, this);
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
                Intent intent = new Intent(activity35AsteRibassoVenditoreActivity.this, activity40DettaglioARApertaActivity.class);
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

    private void setupCardListenerRibassoChiusaVenditore(final CardView cardView, astaribassoModel astaSelezionata) {
        // Assumendo che cardView sia l'istanza della CardView che hai creato dinamicamente
        setTouchListenerForAnimation(cardView);
        cardView.setOnClickListener(view -> {
            if (astaSelezionata != null) {
                // Avvia l'attività e passa l'asta come extra
                Intent intent = new Intent(activity35AsteRibassoVenditoreActivity.this, activity42DettaglioARChiusaActivity.class);
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

    private void openCerca() {
        Intent intent = new Intent(activity35AsteRibassoVenditoreActivity.this, activity44SearchActivity.class);
        intent.putExtra("caller", "35");
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        intent.putExtra("query", String.valueOf(searchEditText.getText()));

        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
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

    @Override
    public void scaricaAstaRibasso(messageResponseRibasso userResponse) {
        asteRibasso.addAll(userResponse.getAste());
        aggiornaAste(asteRibasso);
    }

    @Override
    public void mostraErrore(messageResponse message) {
        updateNestedScrollView(nestedScrollView, false);
    }

}

