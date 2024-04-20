package com.example.dd24client.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
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
import com.example.dd24client.presenter.Presenter34HomeVenditore;
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
import java.util.List;

public class Activity34HomeVenditore extends AppCompatActivity implements Activity34HomeVenditoreView {
    private FirebaseAnalytics mFirebaseAnalytics;
    private UtilsFunction function;
    private CreateCardFunction createCardFunction;
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
    private final List<AstaribassoModel> asteAlRibasso = new ArrayList<>();
    private final List<AstasilenziosaModel> asteSilenziosa = new ArrayList<>();
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private Presenter34HomeVenditore presenter;
    private static int vuoto=0;
    private boolean activityStarted = false;
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
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_KEY = "password";
    private static final String IMPORTODECREMENTO_KEY = "importodecremento";
    private static final String PREZZOMINIMOSEGRETTO_KEY = "prezzominimosegreto";
    private static final String PREZZOINIZIALE_KEY = "prezzoiniziale";
    private static final String TIMERDECREMENTO_KEY = "timerdecremento";
    private static final String ULTIMOPREZZO_KEY = "ultimoprezzo";
    private static final String CREATED_AT_KEY = "created_at";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_34_home_venditore); // Sostituisci con il tuo file layout
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity34HomeVenditore", null);

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
            FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity34HomeVenditore", null);

            vuoto=0;

            asteAlRibasso.clear();
            asteSilenziosa.clear();

            function.removeAllCardViews(linearLayout);

            presenter.astaSilenziosa(email);
            presenter.astaRibasso(email);
        }
        activityStarted = false; // Reimposta il flag
    }

    private void setComponents() {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        function = new UtilsFunction();
        createCardFunction = new CreateCardFunction();

        presenter = new Presenter34HomeVenditore(this, apiService);

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
        View[] viewsToAnimate = {
                buttonHomeAcquista, buttonHomeVendi,
                textCat1, textCat2, textCat3, textCat4, textCat5, textCat6, textCat7, textCat8, textCat9, textCat10, textCat11, textCat12, textCat13, textCat14,

        };

        for (View view : viewsToAnimate) {
            function.setTouchListenerForAnimation(view, this);
        }

        TextView[] textViewCat = {textCat1, textCat2, textCat3, textCat4, textCat5, textCat6, textCat7, textCat8, textCat9, textCat10, textCat11, textCat12, textCat13, textCat14};

        int i = -1;
        for (TextView textView : textViewCat) {
            textView.setOnClickListener(v -> onTextViewClick(textView));
            i++;
        }

        textCat1.setSelected(true);
        textCat1.setTextColor(Color.parseColor("#FFFFFF"));

        buttonHomeAcquista.setOnClickListener(v->{
            String messageString = "Cliccato 'acquista' in 34home";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(Activity34HomeVenditore.this, Activity15HomeAcquirente.class);
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
                String messageString = "Cliccato 'cerca' in bottom navigation in 34home";
                String logging = "[USABILITA' UTENTE]";
                Log.d(logging, messageString);

                function.openCerca(Activity34HomeVenditore.this, email, password, String.valueOf(searchEditText.getText()), activity);
                return true;
            } else if (id == R.id.navigation_vendi) {
                String messageString = "Cliccato 'vendi' in bottom navigation in 34home";
                String logging = "[USABILITA' UTENTE]";
                Log.d(logging, messageString);

                Intent intent = new Intent(Activity34HomeVenditore.this, Activity46Vendi.class);
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
                String messageString = "Cliccato 'aste' in bottom navigation in 34home";
                String logging = "[USABILITA' UTENTE]";
                Log.d(logging, messageString);

                // Aggiungi qui il codice per gestire il clic su "Aste"
                Intent intent = new Intent(Activity34HomeVenditore.this, Activity35AsteRibassoVenditore.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("user", activity);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.navigation_profilo) {
                String messageString = "Cliccato 'profilo' in bottom navigation in 34home";
                String logging = "[USABILITA' UTENTE]";
                Log.d(logging, messageString);

                Intent intent = new Intent(Activity34HomeVenditore.this, Activity27ProfiloActivity.class);
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
            String messageString = "Cliccato 'vedi tutte' in bottom navigation in 34home";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(Activity34HomeVenditore.this, Activity35AsteRibassoVenditore.class);
            intent.putExtra("email", email);
            intent.putExtra("password", password);

            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //query al database
                function.openCerca(Activity34HomeVenditore.this, email, password, String.valueOf(searchEditText.getText()), activity);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Qui puoi gestire la modifica del testo di ricerca in tempo reale se necessario
                return false;
            }
        });

        creaar.setOnClickListener(v->{
            String messageString = "Cliccato 'creaar' in bottom navigation in 34home";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(Activity34HomeVenditore.this, Activity37CreaAR.class);
            intent.putExtra("caller", "34");
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            activityStarted = true;
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

        creaas.setOnClickListener(v->{
            String messageString = "Cliccato 'creaas' in bottom navigation in 34home";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(Activity34HomeVenditore.this, Activity39CreaAS.class);
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

        vuoto=0;

        asteAlRibasso.clear();
        asteSilenziosa.clear();

        function.removeAllCardViews(linearLayout);

        presenter.astaSilenziosa(email);
        presenter.astaRibasso(email);

    }

    private void onTextViewClick(TextView clickedTextView) {
        // Deselezionare tutti i TextView
        TextView[] textViewArray = {textCat1, textCat2, textCat3, textCat4, textCat5, textCat6, textCat7, textCat8, textCat9, textCat10, textCat11, textCat12, textCat13, textCat14};

        for (TextView textView : textViewArray) {
            textView.setSelected(false);
            textView.setTextColor(Color.parseColor("#000000"));
        }

        clickedTextView.setSelected(true);
        clickedTextView.setTextColor(Color.parseColor("#FFFFFF"));

        List<AstaribassoModel> asteAlRibassoCat = new ArrayList<>();
        List<AstasilenziosaModel> asteSilenziosaCat = new ArrayList<>();

        function.removeAllCardViews(linearLayout);
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

            function.updateHorizontalScrollView(horizontalCardsScrollView, update != 2, this);

        }else{
            int update=0;

            for (AstaribassoModel asta : asteAlRibasso) {
                if(asta.getCategoria().contentEquals(clickedTextView.getText())) {
                    asteAlRibassoCat.add(asta);
                }
            }
            if(!asteAlRibassoCat.isEmpty()) {
                aggiornaAsteRibasso(asteAlRibassoCat);
            }else{
                update++;
            }

            for (AstasilenziosaModel asta : asteSilenziosa) {
                if(asta.getCategoria().contentEquals(clickedTextView.getText())) {
                    asteSilenziosaCat.add(asta);
                }
            }
            if(!asteSilenziosaCat.isEmpty()) {
                aggiornaAsteSilenziosa(asteSilenziosaCat);
            }else{
                update++;
            }

            function.updateHorizontalScrollView(horizontalCardsScrollView, update != 2, this);
        }
    }

    public void aggiornaAsteSilenziosa(List<AstasilenziosaModel> aste) {
        for (int i = 0; i < aste.size(); i += 2) {

            AstasilenziosaModel asta1 = aste.get(i);
            AstasilenziosaModel asta2 = (i + 1 < aste.size()) ? aste.get(i + 1) : null;

            CardView cardView1;

            if(asta1.isStato()) {
                Object[] result1 = createCardFunction.cardApertaSilenziosaVenditore(asta1, this);
                cardView1 = (CardView) result1[0];
                TextView textTimer1 = (TextView) result1[1];
                setupCardListenerSilenziosaApertaVenditore(this, cardView1, asta1, textTimer1, email, password);

                linearLayout.addView(cardView1);
            }else{
                cardView1 = createCardFunction.cardChiusaSilenziosaVenditore(asta1, this);
                setupCardListenerSilenziosaChiusaVenditore(this, cardView1, asta1, email, password);

                linearLayout.addView(cardView1);
            }

            CardView cardView2;
            if (asta2 != null) {
                if(asta2.isStato()) {
                    Object[] result2 = createCardFunction.cardApertaSilenziosaVenditore(asta2, this);
                    cardView2 = (CardView) result2[0];
                    TextView textTimer2 = (TextView) result2[1];
                    setupCardListenerSilenziosaApertaVenditore(this, cardView2, asta2, textTimer2, email, password);

                    linearLayout.addView(cardView2);
                }else{
                    cardView2 = createCardFunction.cardChiusaSilenziosaVenditore(asta2, this);
                    setupCardListenerSilenziosaChiusaVenditore(this, cardView2, asta2, email, password);

                    linearLayout.addView(cardView2);
                }
            }

        }
    }

    public void aggiornaAsteRibasso(List<AstaribassoModel> aste) {
        for (int i = 0; i < aste.size(); i += 2) {

            AstaribassoModel asta1 = aste.get(i);
            AstaribassoModel asta2 = (i + 1 < aste.size()) ? aste.get(i + 1) : null;

            CardView cardView1;

            if(asta1.isStato()) {
                Object[] result1 = createCardFunction.cardApertaRibassoVenditore(asta1, this);
                cardView1 = (CardView) result1[0];
                TextView textTimer1 = (TextView) result1[1];
                TextView textPrice1 = (TextView) result1[2];
                setupCardListenerRibassoApertaVenditore(this, cardView1, asta1, textTimer1, textPrice1, email, password);

                linearLayout.addView(cardView1);
            }else{
                cardView1 = createCardFunction.cardChiusaRibassoVenditore(asta1, this);
                setupCardListenerRibassoChiusaVenditore(this, cardView1, asta1, email, password);
                linearLayout.addView(cardView1);
            }

            CardView cardView2;
            if (asta2 != null) {
                if(asta2.isStato()) {
                    Object[] result2 = createCardFunction.cardApertaRibassoVenditore(asta2, this);
                    cardView2 = (CardView) result2[0];
                    TextView textTimer2 = (TextView) result2[1];
                    TextView textPrice2 = (TextView) result2[2];
                    setupCardListenerRibassoApertaVenditore(this, cardView2, asta2, textTimer2, textPrice2, email, password);

                    linearLayout.addView(cardView2);
                }else{
                    cardView2 = createCardFunction.cardChiusaRibassoVenditore(asta2, this);
                    setupCardListenerRibassoChiusaVenditore(this, cardView2, asta2, email, password);

                    linearLayout.addView(cardView2);
                }
            }

        }
    }

    private void setupCardListenerRibassoApertaVenditore(Context activityContext, final CardView cardView, AstaribassoModel astaSelezionata, TextView timer, TextView ultimo, String email, String password) {
        String messageString = "Cliccato 'setupCardListenerRibassoApertaVenditore' in 34home";
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
        String messageString = "Cliccato 'setupCardListenerSilenziosaApertaVenditore' in 34home";
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
        String messageString = "Cliccato 'setupCardListenerRibassoChiusaVenditore' in 34home";
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
        String messageString = "Cliccato 'setupCardListenerSilenziosaChiusaVenditore' in 34home";
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
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "34");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "aste ribasso home venditore");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        asteAlRibasso.addAll(userResponse.getAste());
        aggiornaAsteRibasso(asteAlRibasso);
    }

    @Override
    public void scaricaAstaSilenziosa(MessageResponseSilenziosa userResponse) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "34");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "aste silenziose home venditore");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        asteSilenziosa.addAll(userResponse.getAste());
        aggiornaAsteSilenziosa(asteSilenziosa);
    }

    @Override
    public void mostraErrore(MessageResponse message) {
        vuoto = vuoto + 1;
        function.updateHorizontalScrollView(horizontalCardsScrollView, vuoto != 2, this);
    }

}

