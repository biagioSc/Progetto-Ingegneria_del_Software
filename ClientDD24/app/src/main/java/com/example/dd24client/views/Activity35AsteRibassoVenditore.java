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
import com.example.dd24client.model.AstaribassoModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.network.RetrofitClient;
import com.example.dd24client.presenter.Presenter35AsteRibassoVenditore;
import com.example.dd24client.R;
import com.example.dd24client.utils.CreateCardFunction;
import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.utils.MessageResponseRibasso;
import com.example.dd24client.utils.UtilsFunction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

public class Activity35AsteRibassoVenditore extends AppCompatActivity implements Activity35AsteRibassoVenditoreView {
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
    private final List<AstaribassoModel> asteRibasso = new ArrayList<>();
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private Presenter35AsteRibassoVenditore presenter;
    private LinearLayout linearLayoutRibasso;
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
        setContentView(R.layout.activity_35_aste_venditore); // Sostituisci con il tuo file layout
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity35AsteRibassoVenditore", null);

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
            FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity35AsteRibassoVenditore", null);

            asteRibasso.clear();

            function.removeAllCardViews(linearLayoutRibasso);
            presenter.astaRibasso(email);
        }
        activityStarted = false; // Reimposta il flag
    }

    private void setComponents() {
        function = new UtilsFunction();
        createCardFunction = new CreateCardFunction();

        presenter = new Presenter35AsteRibassoVenditore(this, apiService);

        buttonSilenziosa = findViewById(R.id.buttonSilenziosa);
        buttonAlRibasso = findViewById(R.id.buttonAlRibasso);
        searchView = findViewById(R.id.searchView);
        searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        navigation = findViewById(R.id.bottom_navigation);
        linearLayoutRibasso = findViewById(R.id.linearCardRibasso);

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

        buttonSilenziosa.setOnClickListener(v->{
            String messageString = "Cliccato 'aste silenziosa' in 35aste";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(Activity35AsteRibassoVenditore.this, Activity36AsteSilenziosaVenditore.class);
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            startActivity(intent);
            finish();
            overridePendingTransition(0, 0);
        });

        navigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.navigation_home) {
                String messageString = "Cliccato 'home' in bottom navigation in 35aste";
                String logging = "[USABILITA' UTENTE]";
                Log.d(logging, messageString);

                Intent intent = new Intent(Activity35AsteRibassoVenditore.this, Activity34HomeVenditore.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.navigation_cerca) {
                String messageString = "Cliccato 'cerca' in bottom navigation in 35aste";
                String logging = "[USABILITA' UTENTE]";
                Log.d(logging, messageString);

                Intent intent = new Intent(Activity35AsteRibassoVenditore.this, Activity44Search.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("user", activity);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.navigation_vendi) {
                String messageString = "Cliccato 'vendi' in bottom navigation in 35aste";
                String logging = "[USABILITA' UTENTE]";
                Log.d(logging, messageString);

                Intent intent = new Intent(Activity35AsteRibassoVenditore.this, Activity46Vendi.class);
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
                String messageString = "Cliccato 'profilo' in bottom navigation in 35aste";
                String logging = "[USABILITA' UTENTE]";
                Log.d(logging, messageString);

                Intent intent = new Intent(Activity35AsteRibassoVenditore.this, Activity27ProfiloActivity.class);
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
                function.openCerca(Activity35AsteRibassoVenditore.this, email, password, String.valueOf(searchEditText.getText()), activity);
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

        function.removeAllCardViews(linearLayoutRibasso);
        presenter.astaRibasso(email);
    }

    public void aggiornaAsteRibasso(List<AstaribassoModel> asteRibasso) {
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

    private void setupCardListenerRibassoApertaVenditore(Context activityContext, final CardView cardView, AstaribassoModel astaSelezionata, TextView timer, TextView ultimo, String email, String password) {
        String messageString = "Cliccato 'setupCardListenerRibassoApertaVenditore' in bottom navigation in 35aste";
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

    private void setupCardListenerRibassoChiusaVenditore(Context activityContext, final CardView cardView, AstaribassoModel astaSelezionata, String email, String password) {
        String messageString = "Cliccato 'setupCardListenerRibassoChiusaVenditore' in bottom navigation in 35aste";
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

    @Override
    public void scaricaAstaRibasso(MessageResponseRibasso userResponse) {
        asteRibasso.addAll(userResponse.getAste());
        aggiornaAsteRibasso(asteRibasso);
    }

    @Override
    public void mostraErrore(MessageResponse message) {
        function.updateNestedScrollView(linearLayoutRibasso, false, this);
    }

}

