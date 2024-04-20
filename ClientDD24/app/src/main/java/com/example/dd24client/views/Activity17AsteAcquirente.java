package com.example.dd24client.views;

import static com.example.dd24client.R.id.navigation_aste;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import com.example.dd24client.model.AstaribassoModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.network.RetrofitClient;
import com.example.dd24client.presenter.Presenter17AsteAcquirente;
import com.example.dd24client.R;
import com.example.dd24client.utils.CreateCardFunction;
import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.utils.MessageResponseRibasso;
import com.example.dd24client.utils.UtilsFunction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

public class Activity17AsteAcquirente extends AppCompatActivity implements Activity17AsteAcquirenteView {
    private UtilsFunction function;
    private CreateCardFunction createCardFunction;
    private TextView buttonSilenziosa, buttonAlRibasso;
    private TextView textCat1, textCat2, textCat3, textCat4, textCat5, textCat6, textCat7, textCat8, textCat9, textCat10, textCat11, textCat12, textCat13, textCat14;
    private EditText searchEditText;
    private SearchView searchView;
    private BottomNavigationView navigation;
    private String email="";
    private String password="";
    private String query="";
    private String queryHome="";
    private final String activity="acquirente";
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private LinearLayout linearLayoutRibasso;
    private HorizontalScrollView scrollView;
    private int categoriaSelezionata=13;
    private final List<AstaribassoModel> asteAlRibasso = new ArrayList<>();
    private Presenter17AsteAcquirente presenter;
    private boolean activityStarted = false;
    private final String emailStringa = "email";
    private final String passwordStringa = "password";
    private final String categoriaSelezionataStringa = "categoriaSelezionata";
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

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_17_aste_acquirente);
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity17AsteAcquirente", null);

        setComponents();
        setListener();
        setExtraParameters();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (activityStarted) {
            FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity17AsteAcquirente", null);

            asteAlRibasso.clear();

            function.removeAllCardViews(linearLayoutRibasso);
            presenter.astaRibasso(email);
        }
        activityStarted = false; // Reimposta il flag
    }

    private void setComponents(){
        function = new UtilsFunction();
        createCardFunction = new CreateCardFunction();

        presenter = new Presenter17AsteAcquirente(this,apiService);

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
        linearLayoutRibasso = findViewById(R.id.linearCardRibasso);
        scrollView = findViewById(R.id.scrollView);

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
        TextView[] views = {buttonSilenziosa, buttonAlRibasso, textCat1, textCat2, textCat3, textCat4, textCat5, textCat6, textCat7, textCat8, textCat9, textCat10, textCat11, textCat12, textCat13, textCat14};

        for (TextView view : views) {
            function.setTouchListenerForAnimation(view, this);
        }

        textCat1.setSelected(true);
        textCat1.setTextColor(Color.parseColor("#FFFFFF"));

        TextView[] ribassoTextViews = {textCat1, textCat2, textCat3, textCat4, textCat5, textCat6, textCat7, textCat8, textCat9, textCat10, textCat11, textCat12, textCat13, textCat14};

        int i = -1;
        for (TextView textView : ribassoTextViews) {
            int finalI1 = i;
            textView.setOnClickListener(v -> onTextViewClickRibasso(textView, finalI1));
            i++;
        }

        buttonSilenziosa.setOnClickListener(v->{
            String messageString = "Cliccato 'aste silenziosa' in 17aste";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(Activity17AsteAcquirente.this, Activity18AsteAcquirente.class);
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
                String messageString = "Cliccato 'home' in bottom navigation in 17aste";
                String logging = "[USABILITA' UTENTE]";
                Log.d(logging, messageString);

                Intent intent = new Intent(Activity17AsteAcquirente.this, Activity15HomeAcquirente.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.navigation_cerca) {
                String messageString = "Cliccato 'cerca' in bottom navigation in 17aste";
                String logging = "[USABILITA' UTENTE]";
                Log.d(logging, messageString);

                Intent intent = new Intent(Activity17AsteAcquirente.this, Activity44Search.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("user", activity);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.navigation_vendi) {
                String messageString = "Cliccato 'vendi' in bottom navigation in 17aste";
                String logging = "[USABILITA' UTENTE]";
                Log.d(logging, messageString);

                Intent intent = new Intent(Activity17AsteAcquirente.this, Activity46Vendi.class);
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
                String messageString = "Cliccato 'profilo' in bottom navigation in 17aste";
                String logging = "[USABILITA' UTENTE]";
                Log.d(logging, messageString);

                Intent intent = new Intent(Activity17AsteAcquirente.this, Activity27ProfiloActivity.class);
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
                function.openCerca(Activity17AsteAcquirente.this, email, password, String.valueOf(searchEditText.getText()), activity);
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

        email = intentRicevuto.getStringExtra(emailStringa);
        password = intentRicevuto.getStringExtra(passwordStringa);
        categoriaSelezionata = intentRicevuto.getIntExtra(categoriaSelezionataStringa, 13);

        queryHome = intentRicevuto.getStringExtra("query");

        if (queryHome!=null) {
            if (!queryHome.isEmpty()) {
                query = queryHome;
                searchEditText.setText(query);

            }
        }

        asteAlRibasso.clear();

        function.removeAllCardViews(linearLayoutRibasso);
        presenter.astaRibasso(email);

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
                textCat1.performClick();
                scrollView.post(() -> scrollView.smoothScrollTo(textCat1.getLeft(), 0));
                break;
            // Aggiungi altri casi se necessario
            default:
                // Gestisci il caso in cui categoriaSelezionata non corrisponda a nessun caso sopra
        }
    }

    private void onTextViewClickRibasso(TextView clickedTextView, int i) {
        String messageString = "Cliccato '" + clickedTextView.getText() + "' in 17aste";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        // Deselezionare tutti i TextView
        TextView[] textViewArray = {textCat1, textCat2, textCat3, textCat4, textCat5, textCat6, textCat7, textCat8, textCat9, textCat10, textCat11, textCat12, textCat13, textCat14};

        for (TextView textView : textViewArray) {
            textView.setSelected(false);
            textView.setTextColor(Color.parseColor("#000000"));
        }

        // Selezionare il TextView cliccato
        clickedTextView.setSelected(true);
        clickedTextView.setTextColor(Color.parseColor("#FFFFFF"));

        categoriaSelezionata = i;

        List<AstaribassoModel> asteAlRibassoCat = new ArrayList<>();

        function.removeAllCardViews(linearLayoutRibasso);

        if(clickedTextView.getText().equals("Tutti")) {

            asteAlRibassoCat.addAll(asteAlRibasso);

        }else{

            for (AstaribassoModel asta : asteAlRibasso) {
                if(asta.getCategoria().contentEquals(clickedTextView.getText())) {
                    asteAlRibassoCat.add(asta);
                }
            }

        }
        if(!asteAlRibassoCat.isEmpty()) {
            aggiornaAsteAcquirenteRibasso(asteAlRibassoCat);
        }
        function.updateNestedScrollView(linearLayoutRibasso, false, this);
    }

    public void aggiornaAsteAcquirenteRibasso(List<AstaribassoModel> asteRibasso) {
        function.removeAllCardViews(linearLayoutRibasso);

        if (asteRibasso.isEmpty()) {
            function.updateNestedScrollView(linearLayoutRibasso, false, this);
            return;
        }

        for (int i = 0; i < asteRibasso.size(); i += 2) {

            AstaribassoModel asta1 = asteRibasso.get(i);
            AstaribassoModel asta2 = (i + 1 < asteRibasso.size()) ? asteRibasso.get(i + 1) : null;

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
        String messageString = "Cliccato 'CardRibassoApertaAcquirente' in 17aste";
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

    @Override
    public void scaricaAstaRibasso(MessageResponseRibasso userResponse) {
        asteAlRibasso.addAll(userResponse.getAste());
        aggiornaAsteAcquirenteRibasso(asteAlRibasso);
        selezionaCategoria(categoriaSelezionata);
    }

    @Override
    public void mostraErroreRibasso(MessageResponse message) {
        function.updateNestedScrollView(linearLayoutRibasso, false, this);
        selezionaCategoria(categoriaSelezionata);

    }

}
