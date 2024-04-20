package com.example.dd24client.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import com.example.dd24client.model.AstasilenziosaModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.network.RetrofitClient;
import com.example.dd24client.presenter.Presenter36AsteSilenziosaVenditore;
import com.example.dd24client.R;
import com.example.dd24client.utils.CreateCardFunction;
import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.utils.MessageResponseSilenziosa;
import com.example.dd24client.utils.UtilsFunction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Activity36AsteSilenziosaVenditore extends AppCompatActivity implements Activity36AsteSilenziosaVenditoreView {
    private UtilsFunction function;
    private CreateCardFunction createCardFunction;
    private TextView buttonSilenziosa, buttonAlRibasso;
    private EditText searchEditText;
    private SearchView searchView;
    private BottomNavigationView navigation;
    private String email="";
    private String password="";
    private String query="";
    private String queryHome="";
    private final String activity="venditore";
    private final List<AstasilenziosaModel> asteSilenziosa = new ArrayList<>();
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private Presenter36AsteSilenziosaVenditore presenter;
    private boolean activityStarted = false;
    private LinearLayout linearLayoutSilenziosa;
    private static final String ID_KEY = "id";
    private static final String STATO_KEY = "stato";
    private static final String TITOLO_KEY = "titolo";
    private static final String PAROLECHIAVE_KEY = "parolechiave";
    private static final String CATEGORIA_KEY = "categoria";
    private static final String SOTTOCATEGORIA_KEY = "sottocategoria";
    private static final String DESCRIZIONEPRODOTTO_KEY = "descrizioneprodotto";
    private static final String VENDITORE_KEY = "venditore";
    private static final String IMMAGINEPRODOTTO_KEY = "immagineprodotto";
    private static final String CONTEGGIO_UTENTI_KEY = "conteggioUtenti";
    private static final String PREZZOVENDITA_KEY = "prezzovendita";
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_KEY = "password";
    private static final String DATAFINEASTA_KEY = "datafineasta";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_36_aste_venditore2); // Sostituisci con il tuo file layout
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity36AsteSilenziosaVenditore", null);

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
            FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity36AsteSilenziosaVenditore", null);

            asteSilenziosa.clear();

            function.removeAllCardViews(linearLayoutSilenziosa);
            presenter.astaSilenziosa(email);
        }
        activityStarted = false; // Reimposta il flag
    }

    private void setComponents() {
        function = new UtilsFunction();
        createCardFunction = new CreateCardFunction();

        presenter = new Presenter36AsteSilenziosaVenditore(this, apiService);

        buttonSilenziosa = findViewById(R.id.buttonSilenziosa);
        buttonAlRibasso = findViewById(R.id.buttonAlRibasso);
        searchView = findViewById(R.id.searchView);
        searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        navigation = findViewById(R.id.bottom_navigation);
        linearLayoutSilenziosa = findViewById(R.id.linearCardSilenziosa);

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
        function.setTouchListenerForAnimation(buttonSilenziosa, this);
        function.setTouchListenerForAnimation(buttonAlRibasso, this);

        buttonAlRibasso.setOnClickListener(v->{
            String messageString = "Cliccato 'aste ribasso' in 36aste";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(Activity36AsteSilenziosaVenditore.this, Activity35AsteRibassoVenditore.class);
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            startActivity(intent);
            finish();
            overridePendingTransition(0, 0);
        });

        navigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.navigation_home) {
                String messageString = "Cliccato 'home' in bottom navigation in 36aste";
                String logging = "[USABILITA' UTENTE]";
                Log.d(logging, messageString);

                Intent intent = new Intent(Activity36AsteSilenziosaVenditore.this, Activity34HomeVenditore.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.navigation_cerca) {
                String messageString = "Cliccato 'cerca' in bottom navigation in 36aste";
                String logging = "[USABILITA' UTENTE]";
                Log.d(logging, messageString);

                Intent intent = new Intent(Activity36AsteSilenziosaVenditore.this, Activity44Search.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("user", activity);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.navigation_vendi) {
                String messageString = "Cliccato 'vendi' in bottom navigation in 36aste";
                String logging = "[USABILITA' UTENTE]";
                Log.d(logging, messageString);

                Intent intent = new Intent(Activity36AsteSilenziosaVenditore.this, Activity46Vendi.class);
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
                String messageString = "Cliccato 'profilo' in bottom navigation in 36aste";
                String logging = "[USABILITA' UTENTE]";
                Log.d(logging, messageString);

                Intent intent = new Intent(Activity36AsteSilenziosaVenditore.this, Activity27ProfiloActivity.class);
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
                function.openCerca(Activity36AsteSilenziosaVenditore.this, email, password, String.valueOf(searchEditText.getText()), activity);
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
        queryHome = intentRicevuto.getStringExtra("query");

        if (queryHome!=null) {
            if (!queryHome.isEmpty()) {
                query = queryHome;
                searchEditText.setText(query);

            }
        }

        function.removeAllCardViews(linearLayoutSilenziosa);
        presenter.astaSilenziosa(email);
    }

    public void aggiornaAsteSilenziosa(List<AstasilenziosaModel> asteSilenziosa) {
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

    private void setupCardListenerSilenziosaApertaVenditore(Context activityContext, final CardView cardView, AstasilenziosaModel astaSelezionata, TextView timer, String email, String password) {
        String messageString = "Cliccato 'setupCardListenerSilenziosaApertaVenditore' in bottom navigation in 36aste";
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

    private void setupCardListenerSilenziosaChiusaVenditore(Context activityContext, final CardView cardView, AstasilenziosaModel astaSelezionata, String email, String password) {
        String messageString = "Cliccato 'setupCardListenerSilenziosaChiusaVenditore' in bottom navigation in 36aste";
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
    public void scaricaAstaSilenziosa(MessageResponseSilenziosa userResponse) {
        asteSilenziosa.addAll(userResponse.getAste());
        aggiornaAsteSilenziosa(asteSilenziosa);

    }

    @Override
    public void mostraErrore(MessageResponse message) {
        function.updateNestedScrollView(linearLayoutSilenziosa, false, this);
    }

}

