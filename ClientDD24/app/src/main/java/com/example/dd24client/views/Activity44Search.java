package com.example.dd24client.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import com.example.dd24client.model.AstaribassoModel;
import com.example.dd24client.model.AstasilenziosaModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.network.RetrofitClient;
import com.example.dd24client.presenter.Presenter44Search;
import com.example.dd24client.R;
import com.example.dd24client.utils.CreateCardFunction;
import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.utils.MessageResponseRibasso;
import com.example.dd24client.utils.MessageResponseSilenziosa;
import com.example.dd24client.utils.UtilsFunction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Activity44Search extends AppCompatActivity implements Activity44SearchView {
    private FirebaseAnalytics mFirebaseAnalytics;

    private UtilsFunction function;
    private CreateCardFunction createCardFunction;
    private ImageButton buttonFiltri;
    private EditText searchEditText;
    private SearchView searchView;
    private BottomNavigationView navigation;
    private String email="", password="", query="", queryHome="", ordinaper="", ordinaperExtra="", activity="", data="";
    private Boolean astaSilenziosa=false, astaRibasso=false, categoria1=false,categoria2=false,categoria3=false,categoria4=false,categoria5=false,categoria6=false,categoria7=false,categoria8=false,categoria9=false,categoria10=false,categoria11=false,categoria12=false,categoria13=false,categoria14=false;
    private float endValue=0, startValue=1000;
    private LinearLayout linearLayoutRibasso, linearLayoutSilenziosa;
    private final List<AstaribassoModel> asteAlRibasso = new ArrayList<>();
    private final List<AstasilenziosaModel> asteSilenziosa = new ArrayList<>();
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private Presenter44Search presenter;
    private final List<String> categorieNomi = new ArrayList<>();
    private boolean activityStarted = false;
    private static final String USER_KEY = "user";
    private static final String ASTA_SILENZIOSA_KEY = "astaSilenziosa";
    private static final String ASTA_RIBASSO_KEY = "astaRibasso";
    private static final String DATA_KEY = "data";
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_KEY = "password";
    private static final String QUERY_KEY = "query";
    private static final String ORDINA_PER_KEY = "ordinaper";
    private static final String START_VALUE_KEY = "startValue";
    private static final String END_VALUE_KEY = "endValue";
    private static final String[] CATEGORIA_KEYS = {
            "categoria1", "categoria2", "categoria3", "categoria4", "categoria5",
            "categoria6", "categoria7", "categoria8", "categoria9", "categoria10",
            "categoria11", "categoria12", "categoria13", "categoria14"
    };

    private static final String ID_KEY = "id";
    private static final String STATO_KEY = "stato";
    private static final String TITOLO_KEY = "titolo";
    private static final String PAROLECHIAVE_KEY = "parolechiave";
    private static final String CATEGORIA_KEY = "categoria";
    private static final String SOTTOCATEGORIA_KEY = "sottocategoria";
    private static final String DESCRIZIONEPRODOTTO_KEY = "descrizioneprodotto";
    private static final String VENDITORE_KEY = "venditore";
    private static final String IMMAGINEPRODOTTO_KEY = "immagineprodotto";
    private static final String DATAFINEASTA_KEY = "datafineasta";
    private static final String CONTEGGIO_UTENTI_KEY = "conteggioUtenti";
    private static final String PREZZOVENDITA_KEY = "prezzovendita";
    private static final String IMPORTODECREMENTO_KEY = "importodecremento";
    private static final String PREZZOMINIMOSEGRETTO_KEY = "prezzominimosegreto";
    private static final String PREZZOINIZIALE_KEY = "prezzoiniziale";
    private static final String TIMERDECREMENTO_KEY = "timerdecremento";
    private static final String ULTIMOPREZZO_KEY = "ultimoprezzo";
    private static final String CREATED_AT_KEY = "created_at";
    private final String emailStringa = "email";
    private final String passwordStringa = "password";
    private final String idStringa = "id";
    private final String statoStringa = "stato";
    private final String titoloStringa = "titolo";
    private final String paroleChiaveStringa = "parolechiave";
    private final String categoriaStringa = "categoria";
    private final String sottocategoriaStringa = "sottocategoria";
    private final String descrizioneProdottoStringa = "descrizioneprodotto";
    private final String venditoreStringa = "venditore";
    private final String immagineProdottoStringa = "immagineprodotto";
    private final String importoDecrementoStringa = "importodecremento";
    private final String prezzoMinimoSegretoStringa = "prezzominimosegreto";
    private final String prezzoInizialeStringa = "prezzoiniziale";
    private final String timerDecrementoStringa = "timerdecremento";
    private final String ultimoPrezzoStringa = "ultimoprezzo";
    private final String conteggioUtentiStringa = "conteggioUtenti";
    private final String createdAtStringa = "created_at";
    private final String prezzoVenditaStringa = "prezzovendita";
    private final String dataFineAstaStringa = "datafineasta";
    private static final String[] MAPPING_CATEGORIE = {
            "Elettronica",             // categoria1
            "Moda",                    // categoria2
            "Casa e Giardino",         // categoria3
            "Sport e Tempo Libero",    // categoria4
            "Bellezza e Salute",       // categoria5
            "Libri, Film e Musica",    // categoria6
            "Giochi e Giocattoli",     // categoria7
            "Arte e Antiquariato",     // categoria8
            "Auto, Moto e Altri Veicoli", // categoria9
            "Gioielli e Orologi",      // categoria10
            "Immobili",                // categoria11
            "Collezionismo",           // categoria12
            "Altro"                    // categoria13
    };
    private boolean[] categorie = new boolean[14];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_44_search); // Sostituisci con il tuo file layout
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity44Search", null);

        setComponents();
        setListener();
        setExtraParameters();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (activityStarted) {
            FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity44Search", null);

            function.removeAllCardViews(linearLayoutRibasso);
            function.removeAllCardViews(linearLayoutSilenziosa);

            asteAlRibasso.clear();
            asteSilenziosa.clear();

            if(activity.equals("venditore")) {
                if(astaRibasso.equals(astaSilenziosa)){
                    presenter.asteRibassoVenditore(searchEditText.getText().toString(), categorieNomi, ordinaper, startValue, endValue, email);
                    presenter.astaSilenziosaVenditore(searchEditText.getText().toString(), data, categorieNomi, ordinaper, email);
                }else{
                    if(astaRibasso){
                        presenter.asteRibassoVenditore(searchEditText.getText().toString(), categorieNomi, ordinaper, startValue, endValue, email);
                    }
                    if(astaSilenziosa){
                        presenter.astaSilenziosaVenditore(searchEditText.getText().toString(), data, categorieNomi, ordinaper, email);
                    }
                }
            }else {
                if(astaRibasso.equals(astaSilenziosa)){
                    presenter.asteRibassoAcquirente(searchEditText.getText().toString(), categorieNomi, ordinaper, startValue, endValue, email);
                    presenter.astaSilenziosaAcquirente(searchEditText.getText().toString(), data, categorieNomi, ordinaper, email);
                }else{
                    if (astaRibasso) {
                        presenter.asteRibassoAcquirente(searchEditText.getText().toString(), categorieNomi, ordinaper, startValue, endValue, email);
                    }
                    if (astaSilenziosa) {
                        presenter.astaSilenziosaAcquirente(searchEditText.getText().toString(), data, categorieNomi, ordinaper, email);
                    }
                }
            }
        }
        activityStarted = false; // Reimposta il flag
    }

    private void setComponents() {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        function = new UtilsFunction();
        createCardFunction = new CreateCardFunction();

        presenter = new Presenter44Search(this, apiService);

        buttonFiltri = findViewById(R.id.buttonFiltri);
        searchView = findViewById(R.id.searchView);
        searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);

        navigation = findViewById(R.id.bottom_navigation);

        linearLayoutRibasso = findViewById(R.id.linearCardRibasso);
        linearLayoutSilenziosa = findViewById(R.id.linearCardSilenziosa);

        int selectedItem = R.id.navigation_cerca;
        navigation.setSelectedItemId(selectedItem);

        searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(ContextCompat.getColor(this,R.color.black));
        searchEditText.setHintTextColor(ContextCompat.getColor(this,R.color.grey));
        ImageView searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_mag_icon);
        searchIcon.setColorFilter(ContextCompat.getColor(this,R.color.grey));
        ImageView closeButton = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        closeButton.setColorFilter(ContextCompat.getColor(this,R.color.grey), PorterDuff.Mode.SRC_ATOP);

    }

    private void setListener() {
        function.setTouchListenerForAnimation(buttonFiltri, this);

        buttonFiltri.setOnClickListener(v -> showFiltri());

        navigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.navigation_home) {
                if(activity.equals("venditore")){
                    String messageString = "Cliccato 'home' in bottom navigation in 44search";
                    String logging = "[USABILITA' UTENTE]";
                    Log.d(logging, messageString);

                    Intent intent = new Intent(Activity44Search.this, Activity34HomeVenditore.class);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    intent.putExtra("user", activity);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                }else if(activity.equals("acquirente")){
                    String messageString = "Cliccato 'home' in bottom navigation in 44search";
                    String logging = "[USABILITA' UTENTE]";
                    Log.d(logging, messageString);

                    Intent intent = new Intent(Activity44Search.this, Activity15HomeAcquirente.class);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    intent.putExtra("user", activity);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                }
            } else if (id == R.id.navigation_cerca) {
                return true;
            } else if (id == R.id.navigation_vendi) {
                String messageString = "Cliccato 'vendi' in bottom navigation in 44search";
                String logging = "[USABILITA' UTENTE]";
                Log.d(logging, messageString);

                Intent intent = new Intent(Activity44Search.this, Activity46Vendi.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("user", activity);
                activityStarted = true;
                startActivity(intent);
                overridePendingTransition(0, 0);
                int selectedItem = R.id.navigation_cerca;
                navigation.setSelectedItemId(selectedItem);
                return false;
            } else if (id == R.id.navigation_aste) {
                if(activity.equals("venditore")){
                    String messageString = "Cliccato 'aste' in bottom navigation in 44search";
                    String logging = "[USABILITA' UTENTE]";
                    Log.d(logging, messageString);

                    Intent intent = new Intent(Activity44Search.this, Activity35AsteRibassoVenditore.class);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    intent.putExtra("user", activity);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                }else if(activity.equals("acquirente")){
                    String messageString = "Cliccato 'aste' in bottom navigation in 44search";
                    String logging = "[USABILITA' UTENTE]";
                    Log.d(logging, messageString);

                    Intent intent = new Intent(Activity44Search.this, Activity17AsteAcquirente.class);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    intent.putExtra("user", activity);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                }
            } else if (id == R.id.navigation_profilo) {
                String messageString = "Cliccato 'profilo' in bottom navigation in 44search";
                String logging = "[USABILITA' UTENTE]";
                Log.d(logging, messageString);

                Intent intent = new Intent(Activity44Search.this, Activity27ProfiloActivity.class);
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
                function.removeAllCardViews(linearLayoutRibasso);
                function.removeAllCardViews(linearLayoutSilenziosa);

                asteAlRibasso.clear();
                asteSilenziosa.clear();

                if(activity.equals("venditore")) {
                    if(astaRibasso.equals(astaSilenziosa)){
                        presenter.asteRibassoVenditore(searchEditText.getText().toString(), categorieNomi, ordinaper, startValue, endValue, email);
                        presenter.astaSilenziosaVenditore(searchEditText.getText().toString(), data, categorieNomi, ordinaper, email);
                    }else{
                        if(astaRibasso){
                            presenter.asteRibassoVenditore(searchEditText.getText().toString(), categorieNomi, ordinaper, startValue, endValue, email);
                        }
                        if(astaSilenziosa){
                            presenter.astaSilenziosaVenditore(searchEditText.getText().toString(), data, categorieNomi, ordinaper, email);
                        }
                    }
                }else {
                    if(astaRibasso.equals(astaSilenziosa)){
                        presenter.asteRibassoAcquirente(searchEditText.getText().toString(), categorieNomi, ordinaper, startValue, endValue, email);
                        presenter.astaSilenziosaAcquirente(searchEditText.getText().toString(), data, categorieNomi, ordinaper, email);
                    }else{
                        if (astaRibasso) {
                            presenter.asteRibassoAcquirente(searchEditText.getText().toString(), categorieNomi, ordinaper, startValue, endValue, email);
                        }
                        if (astaSilenziosa) {
                            presenter.astaSilenziosaAcquirente(searchEditText.getText().toString(), data, categorieNomi, ordinaper, email);
                        }
                    }
                }

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
        activity = intentRicevuto.getStringExtra("user");

        if ("venditore".equals(activity)) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.darkOrange));
        }

        email = intentRicevuto.getStringExtra(EMAIL_KEY);
        password = intentRicevuto.getStringExtra(PASSWORD_KEY);
        astaSilenziosa = intentRicevuto.getBooleanExtra(ASTA_SILENZIOSA_KEY, false);
        astaRibasso = intentRicevuto.getBooleanExtra(ASTA_RIBASSO_KEY, false);
        data = intentRicevuto.getStringExtra(DATA_KEY);

        for (int i = 0; i < CATEGORIA_KEYS.length; i++) {
            categorie[i] = intentRicevuto.getBooleanExtra(CATEGORIA_KEYS[i], false);
        }

        startValue = intentRicevuto.getFloatExtra(START_VALUE_KEY, 0);
        endValue = intentRicevuto.getFloatExtra(END_VALUE_KEY, 1000);

        queryHome = intentRicevuto.getStringExtra(QUERY_KEY);

        if (queryHome!=null) {
            if (!queryHome.isEmpty()) {
                query = queryHome;
                searchEditText.setText(query);
            }
        }

        ordinaperExtra = intentRicevuto.getStringExtra(ORDINA_PER_KEY);
        if (ordinaperExtra!=null) {
            if (!ordinaperExtra.isEmpty()) {
                ordinaper = ordinaperExtra;
            }
        }

        boolean almenoUnaCategoria = false;

        for (int i = 0; i < categorie.length-1; i++) {
            if (categorie[i]) {
                categorieNomi.add(MAPPING_CATEGORIE[i]);
                almenoUnaCategoria = true;
            }
        }


        if (!almenoUnaCategoria) {
            categorieNomi.addAll(Arrays.asList(MAPPING_CATEGORIE).subList(0, categorie.length-1));
        }

        function.removeAllCardViews(linearLayoutRibasso);
        function.removeAllCardViews(linearLayoutSilenziosa);

        asteAlRibasso.clear();
        asteSilenziosa.clear();

        if(activity.equals("venditore")) {
            if(astaRibasso.equals(astaSilenziosa)){
                presenter.asteRibassoVenditore(searchEditText.getText().toString(), categorieNomi, ordinaper, startValue, endValue, email);
                presenter.astaSilenziosaVenditore(searchEditText.getText().toString(), data, categorieNomi, ordinaper, email);
            }else{
                if(astaRibasso){
                    presenter.asteRibassoVenditore(searchEditText.getText().toString(), categorieNomi, ordinaper, startValue, endValue, email);
                }
                if(astaSilenziosa){
                    presenter.astaSilenziosaVenditore(searchEditText.getText().toString(), data, categorieNomi, ordinaper, email);
                }
            }
        }else {
            if(astaRibasso.equals(astaSilenziosa)){
                presenter.asteRibassoAcquirente(searchEditText.getText().toString(), categorieNomi, ordinaper, startValue, endValue, email);
                presenter.astaSilenziosaAcquirente(searchEditText.getText().toString(), data, categorieNomi, ordinaper, email);
            }else{
                if (astaRibasso) {
                    presenter.asteRibassoAcquirente(searchEditText.getText().toString(), categorieNomi, ordinaper, startValue, endValue, email);
                }
                if (astaSilenziosa) {
                    presenter.astaSilenziosaAcquirente(searchEditText.getText().toString(), data, categorieNomi, ordinaper, email);
                }
            }
        }

    }

    public void showFiltri() {
        String messageString = "Cliccato 'filtri' in bottom navigation in 44search";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        // Intent per avviare FilterActivity
        Intent intent = new Intent(Activity44Search.this, Activity45Filtri.class);
        intent.putExtra(USER_KEY, activity);
        intent.putExtra(EMAIL_KEY, email);
        intent.putExtra(PASSWORD_KEY, password);
        intent.putExtra(QUERY_KEY, String.valueOf(searchEditText.getText()));
        intent.putExtra(ASTA_SILENZIOSA_KEY, astaSilenziosa);
        intent.putExtra(ASTA_RIBASSO_KEY, astaRibasso);
        intent.putExtra(DATA_KEY, data);

        for (int i = 0; i < CATEGORIA_KEYS.length; i++) {
            intent.putExtra(CATEGORIA_KEYS[i], categorie[i]);
        }

        intent.putExtra(ORDINA_PER_KEY, ordinaper);
        intent.putExtra(START_VALUE_KEY, startValue);
        intent.putExtra(END_VALUE_KEY, endValue);
        startActivity(intent);
        overridePendingTransition(0, 0); // Opzionale: transizione senza animazione
        finish();

    }

    public void aggiornaAsteVenditoreRibasso(List<AstaribassoModel> asteRibasso) {
        if (!asteRibasso.isEmpty()) {
            for (int i = 0; i < asteRibasso.size(); i += 2) {

                AstaribassoModel asta1 = asteRibasso.get(i);
                AstaribassoModel asta2 = (i + 1 < asteRibasso.size()) ? asteRibasso.get(i + 1) : null;

                LinearLayout horizontalLayout = new LinearLayout(this);
                horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                horizontalLayout.setGravity(Gravity.CENTER);
                horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);

                CardView cardView1;
                if (asta1.isStato()) {
                    Object[] result1 = createCardFunction.cardApertaRibassoVenditore(asta1, this);
                    cardView1 = (CardView) result1[0];
                    TextView textTimer1 = (TextView) result1[1];
                    TextView textPrice1 = (TextView) result1[2];
                    setupCardListenerRibassoApertaVenditore(this, cardView1, asta1, textTimer1, textPrice1, email, password);

                } else {
                    cardView1 = createCardFunction.cardChiusaRibassoVenditore(asta1, this);
                    setupCardListenerRibassoChiusaVenditore(this, cardView1, asta1, email, password);

                }
                horizontalLayout.addView(cardView1);

                CardView cardView2;
                if (asta2 != null) {
                    if (asta2.isStato()) {
                        Object[] result2 = createCardFunction.cardApertaRibassoVenditore(asta2, this);
                        cardView2 = (CardView) result2[0];
                        TextView textTimer2 = (TextView) result2[1];
                        TextView textPrice2 = (TextView) result2[2];
                        setupCardListenerRibassoApertaVenditore(this, cardView2, asta2, textTimer2, textPrice2, email, password);

                    } else {
                        cardView2 = createCardFunction.cardChiusaRibassoVenditore(asta2, this);
                        setupCardListenerRibassoChiusaVenditore(this, cardView2, asta2, email, password);

                    }
                    horizontalLayout.addView(cardView2);
                }

                linearLayoutRibasso.addView(horizontalLayout);

            }

        }
    }

    public void aggiornaAsteVenditoreSilenziosa(List<AstasilenziosaModel> asteSilenziosa) {
        if (!asteSilenziosa.isEmpty()) {
            for (int i = 0; i < asteSilenziosa.size(); i += 2) {

                AstasilenziosaModel asta1 = asteSilenziosa.get(i);
                AstasilenziosaModel asta2 = (i + 1 < asteSilenziosa.size()) ? asteSilenziosa.get(i + 1) : null;

                LinearLayout horizontalLayout = new LinearLayout(this);
                horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                horizontalLayout.setGravity(Gravity.CENTER);
                horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);

                CardView cardView1;

                if (asta1.isStato()) {
                    Object[] result1 = createCardFunction.cardApertaSilenziosaVenditore(asta1, this);
                    cardView1 = (CardView) result1[0];
                    TextView textTimer1 = (TextView) result1[1];
                    setupCardListenerSilenziosaApertaVenditore(this, cardView1, asta1, textTimer1, email, password);

                } else {
                    cardView1 = createCardFunction.cardChiusaSilenziosaVenditore(asta1, this);
                    setupCardListenerSilenziosaChiusaVenditore(this, cardView1, asta1, email, password);

                }
                horizontalLayout.addView(cardView1);

                CardView cardView2;
                if (asta2 != null) {
                    if (asta2.isStato()) {
                        Object[] result2 = createCardFunction.cardApertaSilenziosaVenditore(asta2, this);
                        cardView2 = (CardView) result2[0];
                        TextView textTimer2 = (TextView) result2[1];
                        setupCardListenerSilenziosaApertaVenditore(this, cardView2, asta2, textTimer2, email, password);

                    } else {
                        cardView2 = createCardFunction.cardChiusaSilenziosaVenditore(asta2, this);
                        setupCardListenerSilenziosaChiusaVenditore(this, cardView2, asta2, email, password);

                    }
                    horizontalLayout.addView(cardView2);
                }

                linearLayoutSilenziosa.addView(horizontalLayout);

            }

        }
    }

    public void aggiornaAsteAcquirenteSilenziosa(List<AstasilenziosaModel> aste) {
        for (int i = 0; i < aste.size(); i += 2) {

            AstasilenziosaModel asta1 = aste.get(i);
            AstasilenziosaModel asta2 = (i + 1 < aste.size()) ? aste.get(i + 1) : null;

            LinearLayout horizontalLayout = new LinearLayout(this);
            horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            horizontalLayout.setGravity(Gravity.CENTER);
            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);

            Object[] result1 = createCardFunction.cardApertaSilenziosaAcquirente(this, asta1);
            CardView cardView1 = (CardView) result1[0];
            TextView textTimer1 = (TextView) result1[1];
            setupCardListenerSilenziosaApertaAcquirente(Activity21DettaglioAstaSilenziosa.class, this, cardView1, asta1, textTimer1, email, password);

            horizontalLayout.addView(cardView1);

            if (asta2 != null) {
                Object[] result2 = createCardFunction.cardApertaSilenziosaAcquirente(this, asta2);
                CardView cardView2 = (CardView) result2[0];
                TextView textTimer2 = (TextView) result2[1];
                setupCardListenerSilenziosaApertaAcquirente(Activity21DettaglioAstaSilenziosa.class, this, cardView2, asta2, textTimer2, email, password);

                horizontalLayout.addView(cardView2);
            }

            linearLayoutSilenziosa.addView(horizontalLayout);
        }
    }

    public void aggiornaAsteAcquirenteRibasso(List<AstaribassoModel> aste) {
        for (int i = 0; i < aste.size(); i += 2) {

            AstaribassoModel asta1 = aste.get(i);
            AstaribassoModel asta2 = (i + 1 < aste.size()) ? aste.get(i + 1) : null;

            LinearLayout horizontalLayout = new LinearLayout(this);
            horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            horizontalLayout.setGravity(Gravity.CENTER);
            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);

            Object[] result1 = createCardFunction.cardApertaRibassoAcquirente(this, asta1);
            CardView cardView1 = (CardView) result1[0];
            TextView textTimer1 = (TextView) result1[1];
            TextView textPrice1 = (TextView) result1[2];
            setupCardListenerRibassoApertaAcquirente(Activity19DettaglioAstaRibassoAperta.class, this, cardView1, asta1, textTimer1, textPrice1, email, password);

            horizontalLayout.addView(cardView1);

            if (asta2 != null) {
                Object[] result2 = createCardFunction.cardApertaRibassoAcquirente(this, asta2);
                CardView cardView2 = (CardView) result2[0];
                TextView textTimer2 = (TextView) result2[1];
                TextView textPrice2 = (TextView) result2[2];
                setupCardListenerRibassoApertaAcquirente(Activity19DettaglioAstaRibassoAperta.class, this, cardView2, asta2, textTimer2, textPrice2, email, password);

                horizontalLayout.addView(cardView2);

            }
            linearLayoutRibasso.addView(horizontalLayout);

        }
    }

    public void setupCardListenerRibassoApertaAcquirente(Class<?> destinationActivity, Context activityContext, final CardView cardView, AstaribassoModel astaSelezionata, TextView timer, TextView ultimo, String email, String password) {
        String messageString = "Cliccato 'CardListenerRibassoApertaAcquirente' in 44search";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        function.setTouchListenerForAnimation(cardView, activityContext);
        createCardFunction.startCountdownRibasso(cardView, timer, ultimo, astaSelezionata);

        cardView.setOnClickListener(view -> {
            // Avvia l'attività e passa l'asta come extra
            Intent intent = new Intent(activityContext, destinationActivity);
            intent.putExtra(idStringa, astaSelezionata.getId());
            intent.putExtra(statoStringa, astaSelezionata.isStato());
            intent.putExtra(titoloStringa, astaSelezionata.getTitolo());
            intent.putExtra(paroleChiaveStringa, astaSelezionata.getParolechiave());
            intent.putExtra(categoriaStringa, astaSelezionata.getCategoria());
            intent.putExtra(sottocategoriaStringa, astaSelezionata.getSottocategoria());
            intent.putExtra(descrizioneProdottoStringa, astaSelezionata.getDescrizioneprodotto());
            intent.putExtra(venditoreStringa, astaSelezionata.getVenditore());
            intent.putExtra(immagineProdottoStringa, astaSelezionata.getImmagineprodotto());
            intent.putExtra(importoDecrementoStringa, astaSelezionata.getImportodecremento());
            intent.putExtra(prezzoMinimoSegretoStringa, astaSelezionata.getPrezzominimosegreto());
            intent.putExtra(prezzoInizialeStringa, astaSelezionata.getPrezzoiniziale());
            intent.putExtra(timerDecrementoStringa, astaSelezionata.getTimerdecremento());
            intent.putExtra(ultimoPrezzoStringa, astaSelezionata.getUltimoprezzo());
            intent.putExtra(conteggioUtentiStringa, astaSelezionata.getConteggioUtenti());
            intent.putExtra(createdAtStringa, astaSelezionata.getCreated_at().toString());
            intent.putExtra(prezzoVenditaStringa, astaSelezionata.getPrezzovendita());
            intent.putExtra(emailStringa, email);
            intent.putExtra(passwordStringa, password);

            activityStarted = true;
            activityContext.startActivity(intent);
            ((Activity) activityContext).overridePendingTransition(0, 0);
        });
    }

    public void setupCardListenerSilenziosaApertaAcquirente(Class<?> destinationActivity, Context activityContext, final CardView cardView, AstasilenziosaModel astaSelezionata, TextView timer, String email, String password) {
        String messageString = "Cliccato 'setupCardListenerSilenziosaApertaAcquirente' in 44search";
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
            intent.putExtra(idStringa, astaSelezionata.getId());
            intent.putExtra(statoStringa, astaSelezionata.isStato());
            intent.putExtra(titoloStringa, astaSelezionata.getTitolo());
            intent.putExtra(paroleChiaveStringa, astaSelezionata.getParolechiave());
            intent.putExtra(categoriaStringa, astaSelezionata.getCategoria());
            intent.putExtra(sottocategoriaStringa, astaSelezionata.getSottocategoria());
            intent.putExtra(descrizioneProdottoStringa, astaSelezionata.getDescrizioneprodotto());
            intent.putExtra(venditoreStringa, astaSelezionata.getVenditore());
            intent.putExtra(immagineProdottoStringa, astaSelezionata.getImmagineprodotto());
            intent.putExtra(dataFineAstaStringa, astaSelezionata.getDatafineasta());
            intent.putExtra(conteggioUtentiStringa, astaSelezionata.getConteggioUtenti());
            intent.putExtra(prezzoVenditaStringa, astaSelezionata.getPrezzovendita());
            intent.putExtra(emailStringa, email);
            intent.putExtra(passwordStringa, password);

            activityStarted=true;
            activityContext.startActivity(intent);
            ((Activity) activityContext).overridePendingTransition(0, 0);
        });
    }

    private void setupCardListenerRibassoApertaVenditore(Context activityContext, final CardView cardView, AstaribassoModel astaSelezionata, TextView timer, TextView ultimo, String email, String password) {
        String messageString = "Cliccato 'setupCardListenerRibassoApertaVenditore' in 44search";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        function.setTouchListenerForAnimation(cardView, this);
        createCardFunction.startCountdownRibasso(cardView, timer, ultimo, astaSelezionata);

        cardView.setOnClickListener(view -> {
            // Avvia l'attività e passa l'asta come extra
            Intent intent = new Intent(activityContext, Activity40DettaglioARAperta.class);
            intent.putExtra(ID_KEY, astaSelezionata.getId());
            intent.putExtra(STATO_KEY, astaSelezionata.isStato());
            intent.putExtra(TITOLO_KEY, astaSelezionata.getTitolo());
            intent.putExtra(PAROLECHIAVE_KEY, astaSelezionata.getParolechiave());
            intent.putExtra(CATEGORIA_KEY, astaSelezionata.getCategoria());
            intent.putExtra(SOTTOCATEGORIA_KEY, astaSelezionata.getSottocategoria());
            intent.putExtra(DESCRIZIONEPRODOTTO_KEY, astaSelezionata.getDescrizioneprodotto());
            intent.putExtra(VENDITORE_KEY, astaSelezionata.getVenditore());
            intent.putExtra(IMMAGINEPRODOTTO_KEY, astaSelezionata.getImmagineprodotto());
            intent.putExtra(IMPORTODECREMENTO_KEY, astaSelezionata.getImportodecremento());
            intent.putExtra(PREZZOMINIMOSEGRETTO_KEY, astaSelezionata.getPrezzominimosegreto());
            intent.putExtra(PREZZOINIZIALE_KEY, astaSelezionata.getPrezzoiniziale());
            intent.putExtra(TIMERDECREMENTO_KEY, astaSelezionata.getTimerdecremento());
            intent.putExtra(ULTIMOPREZZO_KEY, astaSelezionata.getUltimoprezzo());
            intent.putExtra(CONTEGGIO_UTENTI_KEY, astaSelezionata.getConteggioUtenti());
            intent.putExtra(CREATED_AT_KEY, astaSelezionata.getCreated_at().toString());
            intent.putExtra(PREZZOVENDITA_KEY, astaSelezionata.getPrezzovendita());
            intent.putExtra(EMAIL_KEY, email);
            intent.putExtra(PASSWORD_KEY, password);
            activityStarted=true;
            startActivity(intent);
            overridePendingTransition(0, 0);
        });
    }

    private void setupCardListenerSilenziosaApertaVenditore(Context activityContext, final CardView cardView, AstasilenziosaModel astaSelezionata, TextView timer, String email, String password) {
        String messageString = "Cliccato 'setupCardListenerSilenziosaApertaVenditore' in 44search";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        function.setTouchListenerForAnimation(cardView, this);

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
            Intent intent = new Intent(activityContext, Activity41DettaglioASAperta.class);
            intent.putExtra(ID_KEY, astaSelezionata.getId());
            intent.putExtra(STATO_KEY, astaSelezionata.isStato());
            intent.putExtra(TITOLO_KEY, astaSelezionata.getTitolo());
            intent.putExtra(PAROLECHIAVE_KEY, astaSelezionata.getParolechiave());
            intent.putExtra(CATEGORIA_KEY, astaSelezionata.getCategoria());
            intent.putExtra(SOTTOCATEGORIA_KEY, astaSelezionata.getSottocategoria());
            intent.putExtra(DESCRIZIONEPRODOTTO_KEY, astaSelezionata.getDescrizioneprodotto());
            intent.putExtra(VENDITORE_KEY, astaSelezionata.getVenditore());
            intent.putExtra(IMMAGINEPRODOTTO_KEY, astaSelezionata.getImmagineprodotto());
            intent.putExtra(DATAFINEASTA_KEY, astaSelezionata.getDatafineasta());
            intent.putExtra(CONTEGGIO_UTENTI_KEY, astaSelezionata.getConteggioUtenti());
            intent.putExtra(PREZZOVENDITA_KEY, astaSelezionata.getPrezzovendita());
            intent.putExtra(EMAIL_KEY, email);
            intent.putExtra(PASSWORD_KEY, password);
            activityStarted=true;
            startActivity(intent);
            overridePendingTransition(0, 0);
        });
    }

    private void setupCardListenerRibassoChiusaVenditore(Context activityContext, final CardView cardView, AstaribassoModel astaSelezionata, String email, String password) {
        String messageString = "Cliccato 'setupCardListenerRibassoChiusaVenditore' in 44search";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        function.setTouchListenerForAnimation(cardView, this);
        cardView.setOnClickListener(view -> {
            if (astaSelezionata != null) {
                // Avvia l'attività e passa l'asta come extra
                Intent intent = new Intent(activityContext, Activity42DettaglioARChiusa.class);
                intent.putExtra(ID_KEY, astaSelezionata.getId());
                intent.putExtra(STATO_KEY, astaSelezionata.isStato());
                intent.putExtra(TITOLO_KEY, astaSelezionata.getTitolo());
                intent.putExtra(PAROLECHIAVE_KEY, astaSelezionata.getParolechiave());
                intent.putExtra(CATEGORIA_KEY, astaSelezionata.getCategoria());
                intent.putExtra(SOTTOCATEGORIA_KEY, astaSelezionata.getSottocategoria());
                intent.putExtra(DESCRIZIONEPRODOTTO_KEY, astaSelezionata.getDescrizioneprodotto());
                intent.putExtra(VENDITORE_KEY, astaSelezionata.getVenditore());
                intent.putExtra(IMMAGINEPRODOTTO_KEY, astaSelezionata.getImmagineprodotto());
                intent.putExtra(IMPORTODECREMENTO_KEY, astaSelezionata.getImportodecremento());
                intent.putExtra(PREZZOMINIMOSEGRETTO_KEY, astaSelezionata.getPrezzominimosegreto());
                intent.putExtra(PREZZOINIZIALE_KEY, astaSelezionata.getPrezzoiniziale());
                intent.putExtra(TIMERDECREMENTO_KEY, astaSelezionata.getTimerdecremento());
                intent.putExtra(ULTIMOPREZZO_KEY, astaSelezionata.getUltimoprezzo());
                intent.putExtra(CONTEGGIO_UTENTI_KEY, astaSelezionata.getConteggioUtenti());
                intent.putExtra(CREATED_AT_KEY, astaSelezionata.getCreated_at().toString());
                intent.putExtra(PREZZOVENDITA_KEY, astaSelezionata.getPrezzovendita());
                intent.putExtra(EMAIL_KEY, email);
                intent.putExtra(PASSWORD_KEY, password);
                activityStarted=true;
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }

    private void setupCardListenerSilenziosaChiusaVenditore(Context activityContext, final CardView cardView, AstasilenziosaModel astaSelezionata, String email, String password) {
        String messageString = "Cliccato 'setupCardListenerSilenziosaChiusaVenditore' in 44search";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        function.setTouchListenerForAnimation(cardView, this);
        cardView.setOnClickListener(view -> {
            if (astaSelezionata != null) {
                // Avvia l'attività e passa l'asta come extra
                Intent intent = new Intent(activityContext, Activity43DettaglioASChiusa.class);
                intent.putExtra(ID_KEY, astaSelezionata.getId());
                intent.putExtra(STATO_KEY, astaSelezionata.isStato());
                intent.putExtra(TITOLO_KEY, astaSelezionata.getTitolo());
                intent.putExtra(PAROLECHIAVE_KEY, astaSelezionata.getParolechiave());
                intent.putExtra(CATEGORIA_KEY, astaSelezionata.getCategoria());
                intent.putExtra(SOTTOCATEGORIA_KEY, astaSelezionata.getSottocategoria());
                intent.putExtra(DESCRIZIONEPRODOTTO_KEY, astaSelezionata.getDescrizioneprodotto());
                intent.putExtra(VENDITORE_KEY, astaSelezionata.getVenditore());
                intent.putExtra(IMMAGINEPRODOTTO_KEY, astaSelezionata.getImmagineprodotto());
                intent.putExtra(DATAFINEASTA_KEY, astaSelezionata.getDatafineasta());
                intent.putExtra(CONTEGGIO_UTENTI_KEY, astaSelezionata.getConteggioUtenti());
                intent.putExtra(PREZZOVENDITA_KEY, astaSelezionata.getPrezzovendita());
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
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "44");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "search asta ribasso");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        asteAlRibasso.addAll(userResponse.getAste());
        if(activity.equals("venditore")){
            aggiornaAsteVenditoreRibasso(asteAlRibasso);
        }else{
            aggiornaAsteAcquirenteRibasso(asteAlRibasso);
        }
    }

    @Override
    public void scaricaAstaSilenziosa(MessageResponseSilenziosa userResponse) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "44");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "search asta silenziosa");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        asteSilenziosa.addAll(userResponse.getAste());
        if(activity.equals("venditore")){
            aggiornaAsteVenditoreSilenziosa(asteSilenziosa);
        }else{
            aggiornaAsteAcquirenteSilenziosa(asteSilenziosa);
        }
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

