package com.example.dd24client.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import com.example.dd24client.model.AstaribassoModel;
import com.example.dd24client.model.AstasilenziosaModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.network.RetrofitClient;
import com.example.dd24client.presenter.Presenter15HomeAcquirente;
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

public class Activity15HomeAcquirente extends AppCompatActivity implements Activity15HomeAcquirenteView {
    private FirebaseAnalytics mFirebaseAnalytics;
    private UtilsFunction function;
    private CreateCardFunction createCardFunction;
    private TextView buttonHomeAcquista, buttonHomeVendi, buttonViewAllRibasso, buttonViewAllSilenziose, buttonViewAllCat;
    private TextView textCat1, textCat2, textCat3, textCat4, textCat5, textCat6, textCat7, textCat8, textCat9, textCat10, textCat11, textCat12, textCat13, textCat14;
    private TextView textCat102, textCat202, textCat302, textCat402, textCat502, textCat602, textCat702, textCat802, textCat902, textCat1002, textCat1102, textCat1202, textCat1302, textCat1402;
    private CardView card1, card2, card3, card4, card5, card6, card7, card8, card9, card10, card11, card12, card13;
    private EditText searchEditText;
    private SearchView searchView;
    private String email="";
    private String password="";
    private final String activity="acquirente";
    private BottomNavigationView navigation;
    private HorizontalScrollView horizontalCardsScrollViewRibasso, horizontalCardsScrollViewSilenziosa;
    private LinearLayout linearLayoutRibasso, linearLayoutSilenziosa;
    private final List<AstaribassoModel> asteAlRibasso = new ArrayList<>();
    private final List<AstasilenziosaModel> asteSilenziosa = new ArrayList<>();
    private Presenter15HomeAcquirente presenter;
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private boolean activityStarted = false;
    int categoriaSelezionataRibasso = 13;
    int categoriaSelezionataSilenziosa = 13;
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

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_15_home_acquirente);
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity15HomeAcquirente", null);

        setComponents();
        setListener();
        setExtraParameters();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (activityStarted) {
            FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity15HomeAcquirente", null);

            asteAlRibasso.clear();
            asteSilenziosa.clear();

            function.removeAllCardViews(linearLayoutRibasso);
            function.removeAllCardViews(linearLayoutSilenziosa);

            presenter.astaSilenziosa(email);
            presenter.astaRibasso(email);
        }
        activityStarted = false; // Reimposta il flag
    }

    private void setComponents(){
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        function = new UtilsFunction();
        createCardFunction = new CreateCardFunction();

        presenter = new Presenter15HomeAcquirente(this, apiService);

        buttonHomeAcquista = findViewById(R.id.buttonHomeAcquista);
        buttonHomeVendi = findViewById(R.id.buttonHomeVendi);
        buttonViewAllRibasso = findViewById(R.id.buttonViewAll);
        buttonViewAllSilenziose = findViewById(R.id.buttonViewAll2);
        buttonViewAllCat = findViewById(R.id.buttonViewAllCat);

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

        textCat102 = findViewById(R.id.textViewCat102);
        textCat202 = findViewById(R.id.textViewCat22);
        textCat302 = findViewById(R.id.textViewCat32);
        textCat402 = findViewById(R.id.textViewCat42);
        textCat502 = findViewById(R.id.textViewCat52);
        textCat602 = findViewById(R.id.textViewCat62);
        textCat702 = findViewById(R.id.textViewCat72);
        textCat802 = findViewById(R.id.textViewCat82);
        textCat902 = findViewById(R.id.textViewCat92);
        textCat1002 = findViewById(R.id.textViewCat103);
        textCat1102 = findViewById(R.id.textViewCat112);
        textCat1202 = findViewById(R.id.textViewCat122);
        textCat1302 = findViewById(R.id.textViewCat132);
        textCat1402 = findViewById(R.id.textViewCat142);

        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);
        card5 = findViewById(R.id.card5);
        card6 = findViewById(R.id.card6);
        card7 = findViewById(R.id.card7);
        card8 = findViewById(R.id.card8);
        card9 = findViewById(R.id.card9);
        card10 = findViewById(R.id.card10);
        card11 = findViewById(R.id.card11);
        card12 = findViewById(R.id.card12);
        card13 = findViewById(R.id.card13);

        navigation = findViewById(R.id.bottom_navigation);
        horizontalCardsScrollViewRibasso = findViewById(R.id.horizontalCardsScrollView);
        linearLayoutRibasso = findViewById(R.id.LinearLayoutCard);
        horizontalCardsScrollViewSilenziosa = findViewById(R.id.horizontalCardsScrollView2);
        linearLayoutSilenziosa = findViewById(R.id.LinearLayoutCard2);

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
                buttonViewAllRibasso, buttonViewAllSilenziose, buttonViewAllCat,
                textCat1, textCat2, textCat3, textCat4, textCat5, textCat6, textCat7, textCat8, textCat9, textCat10, textCat11, textCat12, textCat13, textCat14,
                textCat102, textCat202, textCat302, textCat402, textCat502, textCat602, textCat702, textCat802, textCat902, textCat1002, textCat1102, textCat1202, textCat1302, textCat1402,
                card1, card2, card3, card4, card5, card6, card7, card8, card9, card10, card11, card12, card13
        };

        for (View view : viewsToAnimate) {
            function.setTouchListenerForAnimation(view, this);
        }

        textCat1.setSelected(true);
        textCat1.setTextColor(Color.parseColor("#FFFFFF"));

        textCat102.setSelected(true);
        textCat102.setTextColor(Color.parseColor("#FFFFFF"));

        TextView[] ribassoTextViews = {textCat1, textCat2, textCat3, textCat4, textCat5, textCat6, textCat7, textCat8, textCat9, textCat10, textCat11, textCat12, textCat13, textCat14};
        TextView[] silenziosaTextViews = {textCat102, textCat202, textCat302, textCat402, textCat502, textCat602, textCat702, textCat802, textCat902, textCat1002, textCat1102, textCat1202, textCat1302, textCat1402};

        int i = -1;
        for (TextView textView : ribassoTextViews) {
            int finalI1 = i;
            textView.setOnClickListener(v -> onTextViewClickRibasso(textView, finalI1));
            i++;
        }

        int j = -1;
        for (TextView textView : silenziosaTextViews) {
            int finalI1 = j;
            textView.setOnClickListener(v -> onTextViewClickSilenziosa(textView, finalI1));
            j++;
        }


        buttonHomeVendi.setOnClickListener(v->{
            String messageString = "Cliccato 'vendi' in 15home";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(Activity15HomeAcquirente.this, Activity34HomeVenditore.class);
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
                String messageString = "Cliccato 'cerca' in bottom navigation in 15home";
                String logging = "[USABILITA' UTENTE]";
                Log.d(logging, messageString);

                function.openCerca(Activity15HomeAcquirente.this, email, password, String.valueOf(searchEditText.getText()), activity);
                return true;
            } else if (id == R.id.navigation_vendi) {
                String messageString = "Cliccato 'vendi' in bottom navigation in 15home";
                String logging = "[USABILITA' UTENTE]";
                Log.d(logging, messageString);

                Intent intent = new Intent(Activity15HomeAcquirente.this, Activity46Vendi.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                activityStarted=true;
                startActivity(intent);
                overridePendingTransition(0, 0);
                int selectedItem = R.id.navigation_home;
                navigation.setSelectedItemId(selectedItem);
                return false;
            } else if (id == R.id.navigation_aste) {
                String messageString = "Cliccato 'aste' in bottom navigation in 15home";
                String logging = "[USABILITA' UTENTE]";
                Log.d(logging, messageString);

                // Aggiungi qui il codice per gestire il clic su "Aste"
                Intent intent = new Intent(Activity15HomeAcquirente.this, Activity17AsteAcquirente.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.navigation_profilo) {
                String messageString = "Cliccato 'profilo' in bottom navigation in 15home";
                String logging = "[USABILITA' UTENTE]";
                Log.d(logging, messageString);

                Intent intent = new Intent(Activity15HomeAcquirente.this, Activity27ProfiloActivity.class);
                intent.putExtra("user", activity);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return false;
        });

        buttonViewAllRibasso.setOnClickListener(v->{
            String messageString = "Cliccato 'vedi tutte' al ribasso in 15home";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(Activity15HomeAcquirente.this, Activity17AsteAcquirente.class);
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            intent.putExtra("categoriaSelezionata",categoriaSelezionataRibasso);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

        buttonViewAllSilenziose.setOnClickListener(v->{
            String messageString = "Cliccato 'vedi tutte' silenziose in 15home";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(Activity15HomeAcquirente.this, Activity18AsteAcquirente.class);
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            intent.putExtra("categoriaSelezionata",categoriaSelezionataSilenziosa);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

        buttonViewAllCat.setOnClickListener(v->{
            String messageString = "Cliccato 'vedi tutte' categorie in 15home";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(Activity15HomeAcquirente.this, Activity16Categorie.class);
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            activityStarted=true;
            startActivity(intent);
            overridePendingTransition(0, 0);
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //query al database
                function.openCerca(Activity15HomeAcquirente.this, email, password, String.valueOf(searchEditText.getText()), activity);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Qui puoi gestire la modifica del testo di ricerca in tempo reale se necessario
                return false;
            }
        });

        CardView[] cardViews = {card1, card2, card3, card4, card5, card6, card7, card8, card9, card10, card11, card12, card13};

        for (int k = 0; k < cardViews.length; k++) {
            int finalI = k;
            int finalK = k;
            cardViews[k].setOnClickListener(v -> cardClick(cardViews[finalK] ,finalI));
        }

    }

    private void setExtraParameters() {
        Intent intentRicevuto = getIntent();
        email = intentRicevuto.getStringExtra("email");
        password = intentRicevuto.getStringExtra("password");

        asteSilenziosa.clear();
        asteAlRibasso.clear();

        function.removeAllCardViews(linearLayoutRibasso);
        function.removeAllCardViews(linearLayoutSilenziosa);

        presenter.astaSilenziosa(email);
        presenter.astaRibasso(email);

    }

    private void cardClick(CardView cardViewClicked, int categoriaSelezionata) {
        String messageString = "Cliccato '" + cardViewClicked + "' in 15home";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        Intent intent = new Intent(Activity15HomeAcquirente.this, Activity17AsteAcquirente.class);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        intent.putExtra("categoriaSelezionata",categoriaSelezionata);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    private void onTextViewClickRibasso(TextView clickedTextView, int categoriaSelezionata) {
        String messageString = "Cliccato '" + clickedTextView.getText() + "' in 15home";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        // Deselezionare tutti i TextView
        TextView[] textViewArray = {textCat1, textCat2, textCat3, textCat4, textCat5, textCat6, textCat7, textCat8, textCat9, textCat10, textCat11, textCat12, textCat13, textCat14};

        for (TextView textView : textViewArray) {
            textView.setSelected(false);
            textView.setTextColor(Color.parseColor("#000000"));
        }

        // Selezionare il TextView cliccato e impostare il colore del testo a #FFFFFF
        categoriaSelezionataRibasso = categoriaSelezionata;

        clickedTextView.setSelected(true);
        clickedTextView.setTextColor(Color.parseColor("#FFFFFF"));

        List<AstaribassoModel> asteAlRibassoCat = new ArrayList<>();

        function.removeAllCardViews(linearLayoutRibasso);

        if(clickedTextView.getText().equals("Tutti")) {
            int update=0;

            asteAlRibassoCat.addAll(asteAlRibasso);
            if(!asteAlRibassoCat.isEmpty()) {
                aggiornaAsteRibasso(asteAlRibassoCat);
            }else{
                update++;
            }

            function.updateHorizontalScrollView(horizontalCardsScrollViewRibasso, update != 1, this);
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

            function.updateHorizontalScrollView(horizontalCardsScrollViewRibasso, update != 1, this);
        }
    }

    private void onTextViewClickSilenziosa(TextView clickedTextView, int categoriaSelezionata) {
        String messageString = "Cliccato '" + clickedTextView.getText() + "' in 15home";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        TextView[] textViewArray = {textCat102, textCat202, textCat302, textCat402, textCat502, textCat602, textCat702, textCat802, textCat902, textCat1002, textCat1102, textCat1202, textCat1302, textCat1402};

        // Deseleziona tutti i TextView e imposta il colore del testo a #000000
        for (TextView textView : textViewArray) {
            textView.setSelected(false);
            textView.setTextColor(Color.parseColor("#000000"));
        }

        categoriaSelezionataSilenziosa = categoriaSelezionata;

        // Seleziona il TextView cliccato e imposta il colore del testo a #FFFFFF
        clickedTextView.setSelected(true);
        clickedTextView.setTextColor(Color.parseColor("#FFFFFF"));

        List<AstasilenziosaModel> asteSilenziosaCat = new ArrayList<>();

        function.removeAllCardViews(linearLayoutSilenziosa);

        if(clickedTextView.getText().equals("Tutti")) {
            int update=0;

            asteSilenziosaCat.addAll(asteSilenziosa);
            if(!asteSilenziosaCat.isEmpty()) {
                aggiornaAsteSilenziosa(asteSilenziosaCat);
            }else{
                update++;
            }

            function.updateHorizontalScrollView(horizontalCardsScrollViewSilenziosa, update != 1, this);
        }else{
            int update=0;

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

            function.updateHorizontalScrollView(horizontalCardsScrollViewSilenziosa, update != 1, this);
        }
    }

    public void aggiornaAsteSilenziosa(List<AstasilenziosaModel> aste) {
        for (int i = 0; i < aste.size(); i += 2) {

            AstasilenziosaModel asta1 = aste.get(i);
            AstasilenziosaModel asta2 = (i + 1 < aste.size()) ? aste.get(i + 1) : null;

            Object[] result1 = createCardFunction.cardApertaSilenziosaAcquirente(this, asta1);
            CardView cardView1 = (CardView) result1[0];
            TextView textTimer1 = (TextView) result1[1];
            setupCardListenerSilenziosaApertaAcquirente(Activity21DettaglioAstaSilenziosa.class, this, cardView1, asta1, textTimer1, email, password);

            linearLayoutSilenziosa.addView(cardView1);

            if (asta2 != null) {
                Object[] result2 = createCardFunction.cardApertaSilenziosaAcquirente(this, asta2);
                CardView cardView2 = (CardView) result2[0];
                TextView textTimer2 = (TextView) result2[1];
                setupCardListenerSilenziosaApertaAcquirente(Activity21DettaglioAstaSilenziosa.class, this, cardView2, asta2, textTimer2, email, password);

                linearLayoutSilenziosa.addView(cardView2);
            }

        }
    }

    public void aggiornaAsteRibasso(List<AstaribassoModel> aste) {
        for (int i = 0; i < aste.size(); i += 2) {

            AstaribassoModel asta1 = aste.get(i);
            AstaribassoModel asta2 = (i + 1 < aste.size()) ? aste.get(i + 1) : null;

            Object[] result1 = createCardFunction.cardApertaRibassoAcquirente(this, asta1);
            CardView cardView1 = (CardView) result1[0];
            TextView textTimer1 = (TextView) result1[1];
            TextView textPrice1 = (TextView) result1[2];
            setupCardListenerRibassoApertaAcquirente(Activity19DettaglioAstaRibassoAperta.class, this, cardView1, asta1, textTimer1, textPrice1, email, password);

            linearLayoutRibasso.addView(cardView1);

            if (asta2 != null) {
                Object[] result2 = createCardFunction.cardApertaRibassoAcquirente(this, asta2);
                CardView cardView2 = (CardView) result2[0];
                TextView textTimer2 = (TextView) result2[1];
                TextView textPrice2 = (TextView) result2[2];
                setupCardListenerRibassoApertaAcquirente(Activity19DettaglioAstaRibassoAperta.class, this, cardView2, asta2, textTimer2, textPrice2, email, password);

                linearLayoutRibasso.addView(cardView2);

            }

        }
    }

    public void setupCardListenerRibassoApertaAcquirente(Class<?> destinationActivity, Context activityContext, final CardView cardView, AstaribassoModel astaSelezionata, TextView timer, TextView ultimo, String email, String password) {
        // Assumendo che cardView sia l'istanza della CardView che hai creato dinamicamente
        String messageString = "Cliccato 'CardRibassoApertaAcquirente' in 15home";
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
        String messageString = "Cliccato 'CardSilenziosaApertaAcquirente' in 15home";
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

    @Override
    public void scaricaAstaRibasso(MessageResponseRibasso userResponse) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "15");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "aste ribasso home acquirente");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        asteAlRibasso.addAll(userResponse.getAste());
        aggiornaAsteRibasso(asteAlRibasso);
    }

    @Override
    public void scaricaAstaSilenziosa(MessageResponseSilenziosa userResponse) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "15");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "aste silenziose home acquirente");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        asteSilenziosa.addAll(userResponse.getAste());
        aggiornaAsteSilenziosa(asteSilenziosa);
    }

    @Override
    public void mostraErroreRibasso(MessageResponse message) {
        function.updateHorizontalScrollView(horizontalCardsScrollViewRibasso, false, this);
    }

    @Override
    public void mostraErroreSilenziosa(MessageResponse message) {
        function.updateHorizontalScrollView(horizontalCardsScrollViewSilenziosa, false, this);
    }

}
