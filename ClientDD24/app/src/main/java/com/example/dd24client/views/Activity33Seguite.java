package com.example.dd24client.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.dd24client.model.AstaribassoModel;
import com.example.dd24client.model.AstasilenziosaModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.network.RetrofitClient;
import com.example.dd24client.presenter.Presenter33Seguite;
import com.example.dd24client.R;
import com.example.dd24client.utils.CreateCardFunction;
import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.utils.MessageResponseRibasso;
import com.example.dd24client.utils.MessageResponseSilenziosa;
import com.example.dd24client.utils.UtilsFunction;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Activity33Seguite extends AppCompatActivity implements Activity33SeguiteView {
    private UtilsFunction function;
    private CreateCardFunction createCardFunction;
    private ImageView indietroImageView;
    private String email="", password="";
    private LinearLayout linearLayoutRibasso, linearLayoutSilenziosa;
    private final List<AstaribassoModel> asteAlRibasso = new ArrayList<>();
    private final List<AstasilenziosaModel> asteSilenziosa = new ArrayList<>();
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private Presenter33Seguite presenter;
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
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_33_seguite);
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity33Seguite", null);

        setComponents();
        setListener();
        setExtraParameters();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (activityStarted) {
            FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity33Seguite", null);

            asteAlRibasso.clear();
            asteSilenziosa.clear();

            function.removeAllCardViews(linearLayoutRibasso);
            function.removeAllCardViews(linearLayoutSilenziosa);

            presenter.astaSilenziosa(email);
            presenter.astaRibasso(email);
        }
        activityStarted = false; // Reimposta il flag
    }

    private void setComponents() {
        function = new UtilsFunction();
        createCardFunction = new CreateCardFunction();

        presenter = new Presenter33Seguite(this, apiService);

        indietroImageView = findViewById(R.id.indietroImageView);
        linearLayoutRibasso = findViewById(R.id.linearCardRibasso);
        linearLayoutSilenziosa = findViewById(R.id.linearCardSilenziosa);

    }

    private void setListener() {
        function.setTouchListenerForAnimation(indietroImageView, this);

        indietroImageView.setOnClickListener(v -> {
            String messageString = "Cliccato 'indietro' in 33seguite";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            overridePendingTransition(0, 0);
            finish();
        });
    }

    private void setExtraParameters() {
        Intent intentRicevuto = getIntent();

        email = intentRicevuto.getStringExtra("email");
        password = intentRicevuto.getStringExtra("password");

        function.removeAllCardViews(linearLayoutRibasso);
        function.removeAllCardViews(linearLayoutSilenziosa);

        presenter.astaSilenziosa(email);
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
                    Object[] result1 = createCardFunction.cardApertaRibassoAcquirente(this, asta1);
                    cardView1 = (CardView) result1[0];
                    TextView textTimer1 = (TextView) result1[1];
                    TextView textPrice1 = (TextView) result1[2];
                    setupCardListenerRibassoApertaAcquirente(Activity19DettaglioAstaRibassoAperta.class, this, cardView1, asta1, textTimer1, textPrice1, email, password);
                }else{
                    cardView1 = createCardFunction.cardChiusaRibassoAcquirente(this, asta1);
                    setupCardListenerRibassoChiusaAcquirente(this, cardView1, asta1, email, password);
                }
                horizontalLayout.addView(cardView1);

                CardView cardView2;
                if (asta2 != null) {
                    if (asta2.isStato()) {
                        Object[] result2 = createCardFunction.cardApertaRibassoAcquirente(this, asta2);
                        cardView2 = (CardView) result2[0];
                        TextView textTimer2 = (TextView) result2[1];
                        TextView textPrice2 = (TextView) result2[2];
                        setupCardListenerRibassoApertaAcquirente(Activity19DettaglioAstaRibassoAperta.class, this, cardView2, asta2, textTimer2, textPrice2, email, password);
                    }else{
                        cardView2 = createCardFunction.cardChiusaRibassoAcquirente(this, asta2);
                        setupCardListenerRibassoChiusaAcquirente(this, cardView2, asta2, email, password);
                    }
                    horizontalLayout.addView(cardView2);

                }

                linearLayoutRibasso.addView(horizontalLayout);

            }

        }
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
                if(asta1.getstatoaccettazione()!=null) {
                    if (asta1.getstatoaccettazione().equals("IN VALUTAZIONE")) {
                        cardView1 = createCardFunction.cardInValSilenziosaAcquirente(asta1, this);
                        setupCardListenerSilenziosaApertaAcquirente2(this, cardView1, asta1, email, password);

                    } else if (asta1.getstatoaccettazione().equals("RIFIUTATA")) {
                        cardView1 = createCardFunction.cardRifiutataSilenziosaAcquirente(asta1, this);
                        setupCardListenerSilenziosaApertaAcquirente2(this, cardView1, asta1, email, password);

                    }else if (asta1.isStato()) {
                        Object[] result1 = createCardFunction.cardApertaSilenziosaAcquirente(this, asta1);
                        cardView1 = (CardView) result1[0];
                        TextView textTimer1 = (TextView) result1[1];
                        setupCardListenerSilenziosaApertaAcquirente1(Activity21DettaglioAstaSilenziosa.class, this, cardView1, asta1, textTimer1, email, password);

                    }else{
                        cardView1 = createCardFunction.cardChiusaSilenziosaAcquirente(this, asta1);
                        setupCardListenerSilenziosaChiusaAcquirente(this, cardView1, asta1, email, password);

                    }


                }else if (asta1.isStato()) {
                    Object[] result1 = createCardFunction.cardApertaSilenziosaAcquirente(this, asta1);
                    cardView1 = (CardView) result1[0];
                    TextView textTimer1 = (TextView) result1[1];
                    setupCardListenerSilenziosaApertaAcquirente1(Activity21DettaglioAstaSilenziosa.class, this, cardView1, asta1, textTimer1, email, password);

                }else{
                    cardView1 = createCardFunction.cardChiusaSilenziosaAcquirente(this, asta1);
                    setupCardListenerSilenziosaChiusaAcquirente(this, cardView1, asta1, email, password);

                }

                horizontalLayout.addView(cardView1);


                CardView cardView2;
                if (asta2 != null) {
                    if(asta2.getstatoaccettazione()!=null) {
                        if (asta2.getstatoaccettazione().equals("IN VALUTAZIONE")) {
                            cardView2 = createCardFunction.cardInValSilenziosaAcquirente(asta2, this);
                            setupCardListenerSilenziosaApertaAcquirente2(this, cardView2, asta2, email, password);

                        } else if (asta2.getstatoaccettazione().equals("RIFIUTATA")) {
                            cardView2 = createCardFunction.cardRifiutataSilenziosaAcquirente(asta2, this);
                            setupCardListenerSilenziosaApertaAcquirente2(this, cardView2, asta2, email, password);

                        }else if (asta2.isStato()) {
                            Object[] result2 = createCardFunction.cardApertaSilenziosaAcquirente(this, asta2);
                            cardView2 = (CardView) result2[0];
                            TextView textTimer2 = (TextView) result2[1];
                            setupCardListenerSilenziosaApertaAcquirente1(Activity21DettaglioAstaSilenziosa.class, this, cardView2, asta2, textTimer2, email, password);

                        }else{
                            cardView2 = createCardFunction.cardChiusaSilenziosaAcquirente(this, asta2);
                            setupCardListenerSilenziosaChiusaAcquirente(this, cardView2, asta2, email, password);

                        }


                    }else if (asta2.isStato()) {
                        Object[] result2 = createCardFunction.cardApertaSilenziosaAcquirente(this, asta2);
                        cardView2 = (CardView) result2[0];
                        TextView textTimer2 = (TextView) result2[1];
                        setupCardListenerSilenziosaApertaAcquirente1(Activity21DettaglioAstaSilenziosa.class, this, cardView2, asta2, textTimer2, email, password);

                    }else{
                        cardView2 = createCardFunction.cardChiusaSilenziosaAcquirente(this, asta2);
                        setupCardListenerSilenziosaChiusaAcquirente(this, cardView2, asta2, email, password);

                    }

                    horizontalLayout.addView(cardView2);

                }

                linearLayoutSilenziosa.addView(horizontalLayout);

            }

        }
    }

    public void setupCardListenerRibassoApertaAcquirente(Class<?> destinationActivity, Context activityContext, final CardView cardView, AstaribassoModel astaSelezionata, TextView timer, TextView ultimo, String email, String password) {
        String messageString = "Cliccato 'CardRibassoApertaAcquirente' in 33seguite";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        function.setTouchListenerForAnimation(cardView, activityContext);
        createCardFunction.startCountdownRibasso(cardView, timer, ultimo, astaSelezionata);

        cardView.setOnClickListener(view -> {
            // Avvia l'attività e passa l'asta come extra
            Intent intent = new Intent(activityContext, destinationActivity);
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

            activityStarted = true;
            activityContext.startActivity(intent);
            ((Activity) activityContext).overridePendingTransition(0, 0);
        });
    }

    public void setupCardListenerSilenziosaApertaAcquirente1(Class<?> destinationActivity, Context activityContext, final CardView cardView, AstasilenziosaModel astaSelezionata, TextView timer, String email, String password) {
        String messageString = "Cliccato 'CardSilenziosaApertaAcquirente' in 33seguite";
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
            activityContext.startActivity(intent);
            ((Activity) activityContext).overridePendingTransition(0, 0);
        });
    }

    private void setupCardListenerSilenziosaApertaAcquirente2(Context activityContext, final CardView cardView, AstasilenziosaModel astaSelezionata, String email, String password) {
        String messageString = "Cliccato 'CardSilenziosaApertaAcquirente' in 33seguite";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        function.setTouchListenerForAnimation(cardView, this);
        cardView.setOnClickListener(view -> {
            if (astaSelezionata != null) {
                // Avvia l'attività e passa l'asta come extra
                Intent intent = new Intent(activityContext, Activity21DettaglioAstaSilenziosa.class);
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

    private void setupCardListenerRibassoChiusaAcquirente(Context activityContext, final CardView cardView, AstaribassoModel astaSelezionata, String email, String password) {
        String messageString = "Cliccato 'CardRibassoChiusaAcquirente' in 33seguite";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        function.setTouchListenerForAnimation(cardView, this);
        cardView.setOnClickListener(view -> {
            if (astaSelezionata != null) {
                // Avvia l'attività e passa l'asta come extra
                Intent intent = new Intent(activityContext, Activity24DettaglioAstaRibassoChiusa.class);
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

    private void setupCardListenerSilenziosaChiusaAcquirente(Context activityContext, final CardView cardView, AstasilenziosaModel astaSelezionata, String email, String password) {
        String messageString = "Cliccato 'CardSilenziosaChiusaAcquirente' in 33seguite";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        function.setTouchListenerForAnimation(cardView, this);
        cardView.setOnClickListener(view -> {
            if (astaSelezionata != null) {
                // Avvia l'attività e passa l'asta come extra
                Intent intent = new Intent(activityContext, Activity23DettaglioAstaSilenziosaChiusa.class);
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
        asteAlRibasso.addAll(userResponse.getAste());
        aggiornaAsteRibasso(asteAlRibasso);
    }

    @Override
    public void scaricaAstaSilenziosa(MessageResponseSilenziosa userResponse) {
        asteSilenziosa.addAll(userResponse.getAste());
        aggiornaAsteSilenziosa(asteSilenziosa);
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

