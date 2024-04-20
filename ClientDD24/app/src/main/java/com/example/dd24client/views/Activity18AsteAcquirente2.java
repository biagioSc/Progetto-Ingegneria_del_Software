package com.example.dd24client.views;

import static com.example.dd24client.R.id.navigation_aste;

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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.SearchView;


import androidx.appcompat.app.AppCompatActivity;
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
import com.example.dd24client.presenter.presenter18AsteAcquirente2Presenter;
import com.example.dd24client.R;
import com.example.dd24client.utils.messageResponse;
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
import java.util.Locale;

public class activity18AsteAcquirente2 extends AppCompatActivity implements Activity18AsteAcquirente2View {

    private Animation buttonAnimation;
    private TextView buttonSilenziosa, buttonAlRibasso;
    private TextView textCat1, textCat2, textCat3, textCat4, textCat5, textCat6, textCat7, textCat8, textCat9, textCat10, textCat11, textCat12, textCat13, textCat14;
    private EditText searchEditText;
    private SearchView searchView;
    private BottomNavigationView navigation;
    private String email="";
    private String password="";
    private final String activity="acquirente";
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private NestedScrollView nestedScrollView;
    private ConstraintLayout constraintLayout;
    private HorizontalScrollView scrollView;
    private int categoriaSelezionata=13;
    private final List<astasilenziosaModel> asteSilenziosa = new ArrayList<>();
    private presenter18AsteAcquirente2Presenter presenter;
    private boolean activityStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_18_aste_acquirente2);

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
            presenter.astaSilenziosa();

        }
        activityStarted = false; // Reimposta il flag
    }

    private void setComponents(){
        presenter = new presenter18AsteAcquirente2Presenter(this,apiService);

        buttonSilenziosa = findViewById(R.id.buttonSilenziosa);
        buttonAlRibasso = findViewById(R.id.buttonAlRibasso);

        searchView = findViewById(R.id.searchView);
        searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);

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
        nestedScrollView = findViewById(R.id.nestedScrollView);
        constraintLayout = findViewById(R.id.constraintCard);
        scrollView = findViewById(R.id.scrollView);

        buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.button_animation);

        int selectedItem = navigation_aste;
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

        textCat1.setOnClickListener(v -> onTextViewClickSilenziosa(textCat1, 13));
        textCat2.setOnClickListener(v -> onTextViewClickSilenziosa(textCat2,0));
        textCat3.setOnClickListener(v -> onTextViewClickSilenziosa(textCat3,1));
        textCat4.setOnClickListener(v -> onTextViewClickSilenziosa(textCat4,2));
        textCat5.setOnClickListener(v -> onTextViewClickSilenziosa(textCat5,3));
        textCat6.setOnClickListener(v -> onTextViewClickSilenziosa(textCat6,4));
        textCat7.setOnClickListener(v -> onTextViewClickSilenziosa(textCat7,5));
        textCat8.setOnClickListener(v -> onTextViewClickSilenziosa(textCat8,6));
        textCat9.setOnClickListener(v -> onTextViewClickSilenziosa(textCat9,7));
        textCat10.setOnClickListener(v -> onTextViewClickSilenziosa(textCat10,8));
        textCat11.setOnClickListener(v -> onTextViewClickSilenziosa(textCat11,9));
        textCat12.setOnClickListener(v -> onTextViewClickSilenziosa(textCat12,10));
        textCat13.setOnClickListener(v -> onTextViewClickSilenziosa(textCat13,11));
        textCat14.setOnClickListener(v -> onTextViewClickSilenziosa(textCat14,12));

        buttonAlRibasso.setOnClickListener(v->{
            Intent intent = new Intent(activity18AsteAcquirente2.this, activity17AsteAcquirente.class);
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            intent.putExtra("categoriaSelezionata",categoriaSelezionata);
            startActivity(intent);
            finish();
            overridePendingTransition(0, 0);
        });

        navigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.navigation_home) {
                Intent intent = new Intent(activity18AsteAcquirente2.this, activity15HomeAcquirente.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.navigation_cerca) {
                Intent intent = new Intent(activity18AsteAcquirente2.this, activity44SearchActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("user", activity);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.navigation_vendi) {
                Intent intent = new Intent(activity18AsteAcquirente2.this, activity46VendiActivity.class);
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
                Intent intent = new Intent(activity18AsteAcquirente2.this, activity27ProfiloActivity.class);
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

    }

    private void setExtraParameters() {
        Intent intentRicevuto = getIntent();
        email = intentRicevuto.getStringExtra("email");
        password = intentRicevuto.getStringExtra("password");
        categoriaSelezionata = intentRicevuto.getIntExtra("categoriaSelezionata",13);

        String queryHome = intentRicevuto.getStringExtra("query");

        if (queryHome !=null) {
            if (!queryHome.isEmpty()) {
                searchEditText.setText(queryHome);

            }
        }

        removeAllCardViews(constraintLayout);
        presenter.astaSilenziosa();

    }

    private void selezionaCategoria(int categoriaSelezionata) {
        switch (categoriaSelezionata) {
            case 0:
                textCat2.performClick();
                scrollView.post(() -> scrollView.smoothScrollTo(textCat2.getLeft(), 0));
                break;
            case 1:
                textCat3.performClick();
                scrollView.post(() -> scrollView.smoothScrollTo(textCat3.getLeft(), 0));
                break;
            case 2:
                textCat4.performClick();
                scrollView.post(() -> scrollView.smoothScrollTo(textCat4.getLeft(), 0));
                break;
            case 3:
                textCat5.performClick();
                scrollView.post(() -> scrollView.smoothScrollTo(textCat5.getLeft(), 0));
                break;
            case 4:
                textCat6.performClick();
                scrollView.post(() -> scrollView.smoothScrollTo(textCat6.getLeft(), 0));
                break;
            case 5:
                textCat7.performClick();
                scrollView.post(() -> scrollView.smoothScrollTo(textCat7.getLeft(), 0));
                break;
            case 6:
                textCat8.performClick();
                scrollView.post(() -> scrollView.smoothScrollTo(textCat8.getLeft(), 0));
                break;
            case 7:
                textCat9.performClick();
                scrollView.post(() -> scrollView.smoothScrollTo(textCat9.getLeft(), 0));
                break;
            case 8:
                textCat10.performClick();
                scrollView.post(() -> scrollView.smoothScrollTo(textCat10.getLeft(), 0));
                break;
            case 9:
                textCat11.performClick();
                scrollView.post(() -> scrollView.smoothScrollTo(textCat11.getLeft(), 0));
                break;
            case 10:
                textCat12.performClick();
                scrollView.post(() -> scrollView.smoothScrollTo(textCat12.getLeft(), 0));
                break;
            case 11:
                textCat13.performClick();
                scrollView.post(() -> scrollView.smoothScrollTo(textCat13.getLeft(), 0));
                break;
            case 12:
                textCat14.performClick();
                scrollView.post(() -> scrollView.smoothScrollTo(textCat14.getLeft(), 0));
                break;
            case 13:
                scrollView.post(() -> scrollView.smoothScrollTo(textCat1.getLeft(), 0));
                textCat1.performClick();
                break;
            // Aggiungi altri casi se necessario
            default:
                // Gestisci il caso in cui categoriaSelezionata non corrisponda a nessun caso sopra
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

    private void onTextViewClickSilenziosa(TextView clickedTextView, int i) {
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

        categoriaSelezionata = i;

        List<astasilenziosaModel> asteAlRibassoCat = new ArrayList<>();

        removeAllCardViews(constraintLayout);

        if(clickedTextView.getText().equals("Tutti")) {

            asteAlRibassoCat.addAll(asteSilenziosa);

        }else{

            for (astasilenziosaModel asta : asteSilenziosa) {
                if(asta.getCategoria().contentEquals(clickedTextView.getText())) {
                    asteAlRibassoCat.add(asta);
                }
            }

        }
        if(!asteAlRibassoCat.isEmpty()) {
            aggiornaAste(asteAlRibassoCat);
        }
        updateNestedScrollView(nestedScrollView);
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

            CardView cardView1=null;

            if(asta1.isStato()) {
                cardView1 = cardApertaSilenziosaAcquirente(asta1, this);
                constraintLayout.addView(cardView1);
            }

            CardView cardView2=null;
            if (asta2 != null) {
                if(asta2.isStato()) {
                    cardView2 = cardApertaSilenziosaAcquirente(asta2, this);
                    constraintLayout.addView(cardView2);
                }
            }

            ConstraintSet set = new ConstraintSet();
            set.clone(constraintLayout);

            if(cardView1!=null && cardView2!=null) {
                if(idPrec==0){
                    set.connect(cardView1.getId(), ConstraintSet.TOP,  ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                }else {
                    set.connect(cardView1.getId(), ConstraintSet.TOP, idPrec, ConstraintSet.BOTTOM);
                }
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

            }else if(cardView1 != null){
                if(idPrec==0){
                    set.connect(cardView1.getId(), ConstraintSet.TOP,  ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                }else {
                    set.connect(cardView1.getId(), ConstraintSet.TOP, idPrec, ConstraintSet.BOTTOM);
                }

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

    private CardView cardApertaSilenziosaAcquirente(astasilenziosaModel asta, Context context) {
        CardView cardView = new CardView(context);
        ConstraintLayout constraintLayoutCard = new ConstraintLayout(context);
        ImageView imageProduct = new ImageView(context);
        TextView textProductName = new TextView(context);
        TextView textDate = new TextView(context);
        TextView textStateAuction = new TextView(context);

        //Setting cardView
        cardView.setId(asta.getId());
        ConstraintLayout.LayoutParams paramsCardView = new ConstraintLayout.LayoutParams(
                convertDpToPx(160, context), // width
                convertDpToPx(230, context)  // height
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
        paramsTextProductName.bottomToTop = textDate.getId();
        paramsTextProductName.topToBottom = imageProduct.getId();
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
        setTextViewAttributes(textStateAuction, "Asta Silenziosa", 14, R.color.customBlue, context);
        textStateAuction.setBackgroundResource(R.drawable.text_info_basic);
        textStateAuction.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textStateAuction);

        cardView.addView(constraintLayoutCard);

        setupCardListenerSilenziosaApertaAcquirente(cardView, asta, textDate, asta);

        return cardView;
    }

    private void setupCardListenerSilenziosaApertaAcquirente(final CardView cardView, astasilenziosaModel astaSelezionata, TextView timer, astasilenziosaModel asta) {
        // Assumendo che cardView sia l'istanza della CardView che hai creato dinamicamente
        setTouchListenerForAnimation(cardView);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate dataFineAsta = LocalDate.parse(asta.getDatafineasta(), formatter);
        LocalDate dataCorrente = LocalDate.now();

        if(dataFineAsta.equals(dataCorrente)) {
            startCountdownSilenziosa(cardView, timer, asta);
        }
        else{
            timer.setText(asta.getDatafineasta());
        }

        cardView.setOnClickListener(view -> {
            if (astaSelezionata != null) {
                // Avvia l'attività e passa l'asta come extra
                Intent intent = new Intent(activity18AsteAcquirente2.this, activity21DettaglioAstaSilenziosa.class);
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

                        String oreFormat = String.format(Locale.US,"%02d", oreRimanenti);
                        String minutiFormat = String.format(Locale.US,"%02d", minutiRimanenti);
                        String secondiFormat = String.format(Locale.US,"%02d", secondiRimanenti);

                        String message = oreFormat+":"+minutiFormat+":"+secondiFormat;
                        timer.setText(message);

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
            String message = "Non sono presenti aste attive";
            textView.setText(message);
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
        Intent intent = new Intent(activity18AsteAcquirente2.this, activity44SearchActivity.class);
        intent.putExtra("caller", "17");
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
        selezionaCategoria(categoriaSelezionata);

    }

    @Override
    public void mostraErroreSilenziosa(messageResponse message) {
        updateNestedScrollView(nestedScrollView);
        selezionaCategoria(categoriaSelezionata);

    }
}
