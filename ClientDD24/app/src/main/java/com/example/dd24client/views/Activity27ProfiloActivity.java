package com.example.dd24client.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.dd24client.model.UtenteModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.network.RetrofitClient;
import com.example.dd24client.presenter.Presenter27Profilo;
import com.example.dd24client.R;
import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.utils.MessageResponseUtente;
import com.example.dd24client.utils.UtilsFunction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;

public class Activity27ProfiloActivity extends AppCompatActivity implements Activity27ProfiloView {
    private UtilsFunction function;
    private BottomNavigationView navigation;
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private LinearLayout profiloLayout, acquistiLayout, negozioLayout, seguiteLayout, passwordLayout, termsLayout, logoutLayout;
    private String email="", password="", activity="";
    private ImageView profiloImage;
    private TextView emailText, utenteText;
    private Presenter27Profilo presenter;
    private String nome="";
    private String cognome="";
    private String nazionalita="";
    private String citta="";
    private String biografia="";
    private String socialLinks="";
    private String linkWeb="";
    private final String foto="";
    private final String imageConvertToSave="";
    private String url="";
    private boolean activityStarted = false;
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_KEY = "password";
    private static final String NOME_KEY = "nome";
    private static final String COGNOME_KEY = "cognome";
    private static final String NAZIONALITA_KEY = "nazionalita";
    private static final String CITTA_KEY = "citta";
    private static final String BIOGRAFIA_KEY = "biografia";
    private static final String LINK_WEB_KEY = "linkWeb";
    private static final String SOCIAL_LINKS_KEY = "socialLinks";
    private static final String IMAGE_KEY = "image";
    private static final String IMAGE_TO_SAVE_KEY = "imageToSave";
    private static final String URL_KEY = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_27_profilo); // Sostituisci con il tuo file layout
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity27ProfiloActivity", null);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);  // Imposta la barra delle notifiche trasparente

        setComponents();
        setListener();
        setExtraParameters();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (activityStarted) {
            FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity27ProfiloActivity", null);

            presenter.datiUtente(email);
        }
        activityStarted = false; // Reimposta il flag
    }

    private void setComponents() {
        function = new UtilsFunction();

        navigation = findViewById(R.id.bottom_navigation);
        profiloLayout = findViewById(R.id.layoutProfilo);
        acquistiLayout = findViewById(R.id.layoutAcquisti);
        negozioLayout = findViewById(R.id.layoutNegozio);
        seguiteLayout = findViewById(R.id.layoutSeguite);
        passwordLayout = findViewById(R.id.layoutPassword);
        termsLayout = findViewById(R.id.layoutTerms);
        logoutLayout = findViewById(R.id.layoutLogout);
        profiloImage = findViewById(R.id.fotoImageView);
        emailText = findViewById(R.id.emailTextView);
        utenteText = findViewById(R.id.nomeTextView);

        int selectedItem = R.id.navigation_profilo;
        navigation.setSelectedItemId(selectedItem);

        presenter = new Presenter27Profilo(this, apiService);

    }

    private void setListener() {
        navigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.navigation_home) {
                if(activity.equals("venditore")){
                    String messageString = "Cliccato 'home' in bottom navigation in 27profilo";
                    String logging = "[USABILITA' UTENTE]";
                    Log.d(logging, messageString);

                    Intent intent = new Intent(Activity27ProfiloActivity.this, Activity34HomeVenditore.class);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    intent.putExtra("user", activity);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                }else if(activity.equals("acquirente")){
                    String messageString = "Cliccato 'home' in bottom navigation in 27profilo";
                    String logging = "[USABILITA' UTENTE]";
                    Log.d(logging, messageString);

                    Intent intent = new Intent(Activity27ProfiloActivity.this, Activity15HomeAcquirente.class);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    intent.putExtra("user", activity);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                }
            } else if (id == R.id.navigation_cerca) {
                String messageString = "Cliccato 'cerca' in bottom navigation in 27profilo";
                String logging = "[USABILITA' UTENTE]";
                Log.d(logging, messageString);

                Intent intent = new Intent(Activity27ProfiloActivity.this, Activity44Search.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("user", activity);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.navigation_vendi) {
                String messageString = "Cliccato 'vendi' in bottom navigation in 27profilo";
                String logging = "[USABILITA' UTENTE]";
                Log.d(logging, messageString);

                Intent intent = new Intent(Activity27ProfiloActivity.this, Activity46Vendi.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("user", activity);
                activityStarted=true;
                startActivity(intent);
                overridePendingTransition(0, 0);
                int selectedItem = R.id.navigation_profilo;
                navigation.setSelectedItemId(selectedItem);
                return false;
            } else if (id == R.id.navigation_aste) {
                if(activity.equals("venditore")){
                    String messageString = "Cliccato 'aste' in bottom navigation in 27profilo";
                    String logging = "[USABILITA' UTENTE]";
                    Log.d(logging, messageString);

                    Intent intent = new Intent(Activity27ProfiloActivity.this, Activity35AsteRibassoVenditore.class);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    intent.putExtra("user", activity);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                }else if(activity.equals("acquirente")){
                    String messageString = "Cliccato 'aste' in bottom navigation in 27profilo";
                    String logging = "[USABILITA' UTENTE]";
                    Log.d(logging, messageString);

                    Intent intent = new Intent(Activity27ProfiloActivity.this, Activity17AsteAcquirente.class);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    intent.putExtra("user", activity);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                }
            } else return id == R.id.navigation_profilo;
            return false;
        });

        passwordLayout.setOnClickListener(v->{
            String messageString = "Cliccato 'password' in 27profilo";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(Activity27ProfiloActivity.this, Activity28ModificaPassword.class);
            intent.putExtra(EMAIL_KEY, email);
            intent.putExtra(PASSWORD_KEY, password);
            activityStarted=true;
            startActivity(intent);
            overridePendingTransition(0, 0);

        });

        profiloLayout.setOnClickListener(v->{
            String messageString = "Cliccato 'profilo' in 27profilo";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(Activity27ProfiloActivity.this, Activity29ModificaProfilo.class);
            intent.putExtra(EMAIL_KEY, email);
            intent.putExtra(PASSWORD_KEY, password);
            intent.putExtra(NOME_KEY, nome);
            intent.putExtra(COGNOME_KEY, cognome);
            intent.putExtra(NAZIONALITA_KEY, nazionalita);
            intent.putExtra(CITTA_KEY, citta);
            intent.putExtra(BIOGRAFIA_KEY, biografia);
            intent.putExtra(LINK_WEB_KEY, linkWeb);
            intent.putExtra(SOCIAL_LINKS_KEY, socialLinks);
            intent.putExtra(IMAGE_KEY, foto);
            intent.putExtra(IMAGE_TO_SAVE_KEY, imageConvertToSave);
            intent.putExtra(URL_KEY, url);

            activityStarted=true;
            startActivity(intent);
            overridePendingTransition(0, 0);

        });

        acquistiLayout.setOnClickListener(v->{
            String messageString = "Cliccato 'acquisti' in 27profilo";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(Activity27ProfiloActivity.this, Activity31Acquisti.class);
            intent.putExtra(EMAIL_KEY, email);
            intent.putExtra(PASSWORD_KEY, password);
            activityStarted=true;
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

        negozioLayout.setOnClickListener(v->{
            String messageString = "Cliccato 'negozio' in 27profilo";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(Activity27ProfiloActivity.this, Activity32Negozio.class);
            intent.putExtra(EMAIL_KEY, email);
            intent.putExtra(PASSWORD_KEY, password);
            activityStarted=true;
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

        seguiteLayout.setOnClickListener(v->{
            String messageString = "Cliccato 'seguite' in 27profilo";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(Activity27ProfiloActivity.this, Activity33Seguite.class);
            intent.putExtra(EMAIL_KEY, email);
            intent.putExtra(PASSWORD_KEY, password);
            activityStarted=true;
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

        termsLayout.setOnClickListener(v->{
            String messageString = "Cliccato 'terms' in 27profilo";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(Activity27ProfiloActivity.this, Activity14Terms.class);
            activityStarted=true;
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

        logoutLayout.setOnClickListener(v->{
            String messageString = "Cliccato 'logout' in 27profilo";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            clearPreferences();
            Intent intent = new Intent(Activity27ProfiloActivity.this, Activity01Main.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finishAffinity();
        });

        View[] views = {logoutLayout, acquistiLayout, negozioLayout, profiloLayout, passwordLayout, seguiteLayout, termsLayout};

        for (View view : views) {
            function.setTouchListenerForAnimation(view,this);
        }

    }

    private void setExtraParameters() {
        Intent intentRicevuto = getIntent();
        activity =  intentRicevuto.getStringExtra("user");
        email = intentRicevuto.getStringExtra("email");
        password = intentRicevuto.getStringExtra("password");
        emailText.setText(email);

        presenter.datiUtente(email);
    }

    private void clearPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);

        // Controlla se l'utente ha salvato le credenziali
        boolean isRicordamiChecked = sharedPreferences.getBoolean("Ricordami", false);

        if (isRicordamiChecked) {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            // Rimuovi i dettagli dell'utente e il flag 'Ricordami'
            editor.remove("email");
            editor.remove("Password");
            editor.remove("Ricordami");

            editor.apply();
        }
    }

    @Override
    public void utenteNonTrovato(MessageResponse errore_password) {
    }

    @Override
    public void utenteTrovato(MessageResponseUtente message) {
        UtenteModel utente = message.getUtente();
        nome = utente.getNome();
        cognome = utente.getCognome();
        biografia = utente.getBiografia();
        nazionalita = utente.getNazionalita();
        citta = utente.getCitta();
        linkWeb = utente.getLinkweb();
        socialLinks = utente.getLinksocial();
        url = utente.getImmagine();
        password = utente.getPassword();

        utenteText.setText(nome + " " + cognome);
        Glide.with(this )
                .load(url)
                .into(profiloImage);
    }
}

