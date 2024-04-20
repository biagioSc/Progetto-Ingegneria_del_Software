package com.example.dd24client.views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.example.dd24client.R;
import com.example.dd24client.utils.UtilsFunction;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Activity29ModificaProfilo extends AppCompatActivity{
    private UtilsFunction function;
    private Animation shakeAnimation;
    private EditText nomeEditText, cognomeEditText, biografiaEditText;
    private AutoCompleteTextView cittaSpinner, nazionalitaSpinner;
    private TextView charCountTextView;
    private JSONObject datiNazionalitaCitta;
    private ImageView fotoImageView, indietroImageView, check1, check2, check3, check4, check5;
    private TextView continuaButton, textViewError;
    private String nome="", cognome="", nazionalita="", citta="", biografia="", socialLinks="", linkWeb="", email="", password="", imageConvertToSave="", url="";
    private Uri uriImage;
    private static final String nomeRegex = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z])*$";
    private static final String cognomeRegex = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z])*$";
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private boolean statoNome = false, statoCognome = false, statoNazionalita = false, statoCitta = false, statoBio = false, statoFoto=false;
    private final String nomeStringa = "nome";
    private final String cognomeStringa = "cognome";
    private final String nazionalitaStringa = "nazionalita";
    private final String cittaStringa = "citta";
    private final String biografiaStringa = "biografia";
    private final String linkWebStringa = "linkWeb";
    private final String socialLinksStringa = "socialLinks";
    private final String emailStringa = "email";
    private final String passwordStringa = "password";
    private final String fotoStringa = "image";
    private ArrayList<String> cittaList, nazionalitaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_29_modifica_profilo1);
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity29ModificaProfilo", null);

        setComponents();
        setListener();
        setExtraParameters();

    }

    private void setComponents() {
        function = new UtilsFunction();

        fotoImageView = findViewById(R.id.fotoImageView);
        nomeEditText = findViewById(R.id.editTextNome);
        cognomeEditText = findViewById(R.id.cognomeEditText);
        nazionalitaSpinner = findViewById(R.id.nazionalitaSpinner);
        cittaSpinner = findViewById(R.id.cittaSpinner);
        biografiaEditText = findViewById(R.id.biografiaEditText);
        continuaButton = findViewById(R.id.continuaButton);
        indietroImageView = findViewById(R.id.indietroImageView);
        check1 = findViewById(R.id.passwordToggle);
        check2 = findViewById(R.id.passwordToggle2);
        check3 = findViewById(R.id.biografiaToggle);
        check4 = findViewById(R.id.checkNazione);
        check5 = findViewById(R.id.checkCitta);
        textViewError= findViewById(R.id.textInfoError);

        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake_error);
        charCountTextView = findViewById(R.id.charCountTextView);
    }

    private void setListener() {
        function.setTouchListenerForAnimation(continuaButton, this);
        function.setTouchListenerForAnimation(indietroImageView, this);

        fotoImageView.setOnClickListener(v -> selectImageFromGallery());
        continuaButton.setOnClickListener(v -> continuaFunction());

        indietroImageView.setOnClickListener(v -> showPopupMessageIndietro());

        nomeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Questo metodo viene invocato prima che il testo venga cambiato.
                String testoInserito = s.toString();
                nomeEditText.setTextColor(Color.parseColor("#000000"));

                // Qui puoi inserire il tuo controllo per verificare se il testo inserito è giusto.
                // Ad esempio, supponiamo che il testo sia "giusto" se non è vuoto e se contiene la parola "esempio".
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check1.setImageResource(R.drawable.icon_basiccheck);
                    statoNome=false;

                } else if(testoInserito.matches(nomeRegex)){
                    check1.setImageResource(R.drawable.icon_okcheck);
                    statoNome=true;

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check1.setImageResource(R.drawable.icon_nocheck);
                    statoNome=false;

                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Questo metodo viene invocato mentre il testo sta cambiando.
                String testoInserito = s.toString();
                nomeEditText.setTextColor(Color.parseColor("#000000"));

                // Qui puoi inserire il tuo controllo per verificare se il testo inserito è giusto.
                // Ad esempio, supponiamo che il testo sia "giusto" se non è vuoto e se contiene la parola "esempio".
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check1.setImageResource(R.drawable.icon_basiccheck);
                    statoNome=false;

                } else if(testoInserito.matches(nomeRegex)){
                    check1.setImageResource(R.drawable.icon_okcheck);
                    statoNome=true;

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check1.setImageResource(R.drawable.icon_nocheck);
                    statoNome=false;

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Questo metodo viene invocato subito dopo che il testo è stato cambiato.

                String testoInserito = s.toString();
                nomeEditText.setTextColor(Color.parseColor("#000000"));

                // Qui puoi inserire il tuo controllo per verificare se il testo inserito è giusto.
                // Ad esempio, supponiamo che il testo sia "giusto" se non è vuoto e se contiene la parola "esempio".
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check1.setImageResource(R.drawable.icon_basiccheck);
                    statoNome=false;

                } else if(testoInserito.matches(nomeRegex)){
                    check1.setImageResource(R.drawable.icon_okcheck);
                    statoNome=true;

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check1.setImageResource(R.drawable.icon_nocheck);
                    statoNome=false;

                }
            }
        });

        cognomeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Questo metodo viene invocato prima che il testo venga cambiato.
                String testoInserito = s.toString();
                cognomeEditText.setTextColor(Color.parseColor("#000000"));

                // Qui puoi inserire il tuo controllo per verificare se il testo inserito è giusto.
                // Ad esempio, supponiamo che il testo sia "giusto" se non è vuoto e se contiene la parola "esempio".
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check2.setImageResource(R.drawable.icon_basiccheck);
                    statoCognome=false;

                } else if(testoInserito.matches(cognomeRegex)){
                    check2.setImageResource(R.drawable.icon_okcheck);
                    statoCognome=true;

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check2.setImageResource(R.drawable.icon_nocheck);
                    statoCognome=false;

                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Questo metodo viene invocato mentre il testo sta cambiando.
                String testoInserito = s.toString();
                cognomeEditText.setTextColor(Color.parseColor("#000000"));

                // Qui puoi inserire il tuo controllo per verificare se il testo inserito è giusto.
                // Ad esempio, supponiamo che il testo sia "giusto" se non è vuoto e se contiene la parola "esempio".
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check2.setImageResource(R.drawable.icon_basiccheck);
                    statoCognome=false;

                } else if(testoInserito.matches(cognomeRegex)){
                    check2.setImageResource(R.drawable.icon_okcheck);
                    statoCognome=true;

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check2.setImageResource(R.drawable.icon_nocheck);
                    statoCognome=false;

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Questo metodo viene invocato subito dopo che il testo è stato cambiato.

                String testoInserito = s.toString();
                cognomeEditText.setTextColor(Color.parseColor("#000000"));

                // Qui puoi inserire il tuo controllo per verificare se il testo inserito è giusto.
                // Ad esempio, supponiamo che il testo sia "giusto" se non è vuoto e se contiene la parola "esempio".
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check2.setImageResource(R.drawable.icon_basiccheck);
                    statoCognome=false;

                } else if(testoInserito.matches(cognomeRegex)){
                    check2.setImageResource(R.drawable.icon_okcheck);
                    statoCognome=true;

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check2.setImageResource(R.drawable.icon_nocheck);
                    statoCognome=false;

                }
            }
        });

        biografiaEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Questo metodo viene invocato prima che il testo venga cambiato.
                String testoInserito = s.toString();
                biografiaEditText.setTextColor(Color.parseColor("#000000"));
                charCountTextView.setText(testoInserito.length()+"/255");

                // Qui puoi inserire il tuo controllo per verificare se il testo inserito è giusto.
                // Ad esempio, supponiamo che il testo sia "giusto" se non è vuoto e se contiene la parola "esempio".
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check3.setImageResource(R.drawable.icon_basiccheck);
                    statoBio=false;

                } else if(testoInserito.length()<=255){
                    check3.setImageResource(R.drawable.icon_okcheck);
                    statoBio=true;

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check3.setImageResource(R.drawable.icon_nocheck);
                    statoBio=false;

                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Questo metodo viene invocato mentre il testo sta cambiando.
                String testoInserito = s.toString();
                biografiaEditText.setTextColor(Color.parseColor("#000000"));
                charCountTextView.setText(testoInserito.length()+"/255");

                // Qui puoi inserire il tuo controllo per verificare se il testo inserito è giusto.
                // Ad esempio, supponiamo che il testo sia "giusto" se non è vuoto e se contiene la parola "esempio".
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check3.setImageResource(R.drawable.icon_basiccheck);
                    statoBio=false;

                } else if(testoInserito.length()<=255){
                    check3.setImageResource(R.drawable.icon_okcheck);
                    statoBio=true;

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check3.setImageResource(R.drawable.icon_nocheck);
                    statoBio=false;

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Questo metodo viene invocato subito dopo che il testo è stato cambiato.
                String testoInserito = s.toString();
                biografiaEditText.setTextColor(Color.parseColor("#000000"));
                charCountTextView.setText(testoInserito.length()+"/255");

                // Qui puoi inserire il tuo controllo per verificare se il testo inserito è giusto.
                // Ad esempio, supponiamo che il testo sia "giusto" se non è vuoto e se contiene la parola "esempio".
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check3.setImageResource(R.drawable.icon_basiccheck);
                    statoBio=false;

                } else if(testoInserito.length()<=255){
                    check3.setImageResource(R.drawable.icon_okcheck);
                    statoBio=true;

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check3.setImageResource(R.drawable.icon_nocheck);
                    statoBio=false;

                }

            }
        });

        nazionalitaSpinner.setOnItemClickListener((parent, view, position, id) -> {
            String selectedNazionalita = nazionalitaSpinner.getText().toString();
            updateCittaSpinner(selectedNazionalita);
            statoNazionalita = true;
            cittaSpinner.setText("");
        });

        nazionalitaSpinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String testoInserito = s.toString();
                nazionalitaSpinner.setTextColor(Color.parseColor("#000000"));

                // Qui puoi inserire il tuo controllo per verificare se il testo inserito è giusto.
                // Ad esempio, supponiamo che il testo sia "giusto" se non è vuoto e se contiene la parola "esempio".
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check4.setImageResource(R.drawable.icon_basiccheck);
                    statoNazionalita=false;

                } else if(nazionalitaList.contains(testoInserito)){
                    check4.setImageResource(R.drawable.icon_okcheck);
                    statoNazionalita=true;

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check4.setImageResource(R.drawable.icon_nocheck);
                    statoNazionalita=false;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String testoInserito = s.toString();
                nazionalitaSpinner.setTextColor(Color.parseColor("#000000"));

                // Qui puoi inserire il tuo controllo per verificare se il testo inserito è giusto.
                // Ad esempio, supponiamo che il testo sia "giusto" se non è vuoto e se contiene la parola "esempio".
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check4.setImageResource(R.drawable.icon_basiccheck);
                    statoNazionalita=false;

                } else if(nazionalitaList.contains(testoInserito)){
                    check4.setImageResource(R.drawable.icon_okcheck);
                    statoNazionalita=true;

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check4.setImageResource(R.drawable.icon_nocheck);
                    statoNazionalita=false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String testoInserito = s.toString();
                nazionalitaSpinner.setTextColor(Color.parseColor("#000000"));

                // Qui puoi inserire il tuo controllo per verificare se il testo inserito è giusto.
                // Ad esempio, supponiamo che il testo sia "giusto" se non è vuoto e se contiene la parola "esempio".
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check4.setImageResource(R.drawable.icon_basiccheck);
                    statoNazionalita=false;

                } else if(nazionalitaList.contains(testoInserito)){
                    check4.setImageResource(R.drawable.icon_okcheck);
                    statoNazionalita=true;

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check4.setImageResource(R.drawable.icon_nocheck);
                    statoNazionalita=false;
                }
            }
        });

        cittaSpinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String testoInserito = s.toString();
                cittaSpinner.setTextColor(Color.parseColor("#000000"));

                // Qui puoi inserire il tuo controllo per verificare se il testo inserito è giusto.
                // Ad esempio, supponiamo che il testo sia "giusto" se non è vuoto e se contiene la parola "esempio".
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check5.setImageResource(R.drawable.icon_basiccheck);
                    statoCitta=false;

                } else if(cittaList.contains(testoInserito)){
                    check5.setImageResource(R.drawable.icon_okcheck);
                    statoCitta=true;

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check5.setImageResource(R.drawable.icon_nocheck);
                    statoCitta=false;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String testoInserito = s.toString();
                cittaSpinner.setTextColor(Color.parseColor("#000000"));

                // Qui puoi inserire il tuo controllo per verificare se il testo inserito è giusto.
                // Ad esempio, supponiamo che il testo sia "giusto" se non è vuoto e se contiene la parola "esempio".
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check5.setImageResource(R.drawable.icon_basiccheck);
                    statoCitta=false;

                } else if(cittaList.contains(testoInserito)){
                    check5.setImageResource(R.drawable.icon_okcheck);
                    statoCitta=true;

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check5.setImageResource(R.drawable.icon_nocheck);
                    statoCitta=false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String testoInserito = s.toString();
                cittaSpinner.setTextColor(Color.parseColor("#000000"));

                // Qui puoi inserire il tuo controllo per verificare se il testo inserito è giusto.
                // Ad esempio, supponiamo che il testo sia "giusto" se non è vuoto e se contiene la parola "esempio".
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check5.setImageResource(R.drawable.icon_basiccheck);
                    statoCitta=false;

                } else if(cittaList.contains(testoInserito)){
                    check5.setImageResource(R.drawable.icon_okcheck);
                    statoCitta=true;

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check5.setImageResource(R.drawable.icon_nocheck);
                    statoCitta=false;
                }
            }
        });

        cittaSpinner.setOnItemClickListener((parent, view, position, id) -> statoCitta = true);

    }

    private void setExtraParameters() {
        Intent intentRicevuto = getIntent();

        email = intentRicevuto.getStringExtra("email");
        password = intentRicevuto.getStringExtra("password");
        nome = intentRicevuto.getStringExtra("nome");
        cognome = intentRicevuto.getStringExtra("cognome");
        nazionalita = intentRicevuto.getStringExtra("nazionalita");
        citta = intentRicevuto.getStringExtra("citta");
        biografia = intentRicevuto.getStringExtra("biografia");
        linkWeb = intentRicevuto.getStringExtra("linkWeb");
        socialLinks = intentRicevuto.getStringExtra("socialLinks");
        String uriImageString = intentRicevuto.getStringExtra("image");
        imageConvertToSave = intentRicevuto.getStringExtra("imageToSave");
        url = intentRicevuto.getStringExtra("url");

        datiNazionalitaCitta = function.caricaDatiDaJson(this, "nazioni_e_citta.json");

        setupNazionalitaSpinner();
        updateCittaSpinner(nazionalita);

        nomeEditText.setText(nome);
        cognomeEditText.setText(cognome);
        biografiaEditText.setText(biografia);

        if (url.isEmpty()){
            if (uriImageString != null && !uriImageString.isEmpty()) {
                uriImage = Uri.parse(uriImageString);
                fotoImageView.setImageURI(uriImage);
                statoFoto=true;

            } else {
                // Gestisci la situazione in cui la foto è nulla, ad esempio impostando un'immagine predefinita
                fotoImageView.setImageResource(R.drawable.image_setprofilo);
            }
        }else{
            statoFoto=true;
            Glide.with(this )
                    .load(url)
                    .into(fotoImageView);
        }

        nazionalitaSpinner.setText(nazionalita);
        cittaSpinner.setText(citta);

    }

    private void setupNazionalitaSpinner() {
        nazionalitaList = new ArrayList<>();
        Iterator<String> keys = datiNazionalitaCitta.keys();
        while (keys.hasNext()) {
            nazionalitaList.add(keys.next());
        }
        ArrayAdapter<String> adapterNazionalita = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, nazionalitaList);
        nazionalitaSpinner.setAdapter(adapterNazionalita);

        String selectedNazionalita = nazionalitaSpinner.getText().toString();
        updateCittaSpinner(selectedNazionalita);
    }

    private void updateCittaSpinner(String nazionalita) {
        cittaList = new ArrayList<>();
        try {
            JSONArray arr = datiNazionalitaCitta.getJSONArray(nazionalita);
            for (int i = 0; i < arr.length(); i++) {
                cittaList.add(arr.getString(i));
            }
        } catch (JSONException e) {
            cittaList.add("-");
        }
        ArrayAdapter<String> adapterCitta = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, cittaList);
        cittaSpinner.setAdapter(adapterCitta);

    }

    public void selectImageFromGallery() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Se non abbiamo il permesso, lo richiediamo
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            // Abbiamo già il permesso, quindi possiamo procedere con la selezione dell'immagine
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permesso concesso, possiamo procedere con la selezione dell'immagine
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            } else {
                // Permesso negato, gestisci il caso
                Toast.makeText(this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                imageConvertToSave = saveDrawableImageToInternalStorage(R.drawable.image_usernoperms);
                statoFoto=true;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriImage = data.getData();
            // Utilizza Glide per caricare l'immagine
            Glide.with(this).load(uriImage).into(fotoImageView);

            // Continua con l'upload dell'immagine
            imageConvertToSave = saveImageToInternalStorage(uriImage);
            url="";
            statoFoto=true;

        }
    }

    private String saveImageToInternalStorage(Uri uri) {
        String imagePath;
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            File outputFile = new File(getCacheDir(), "tempImage.jpg");
            try (FileOutputStream out = new FileOutputStream(outputFile)) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                imagePath = outputFile.getAbsolutePath();
            }
        } catch (IOException e) {
            imagePath = null;
        }
        return imagePath;
    }

    private String saveDrawableImageToInternalStorage(int drawableResourceId) {
        String imagePath;
        try {
            // Carica l'immagine dal drawable
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawableResourceId);

            // Crea un file temporaneo
            File outputFile = new File(getCacheDir(), "tempImage.jpg");

            // Salva l'immagine sul disco
            try (FileOutputStream out = new FileOutputStream(outputFile)) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                imagePath = outputFile.getAbsolutePath();
            }
        } catch (IOException e) {
            imagePath = null;
        }
        return imagePath;
    }

    private void continuaFunction(){
        String messageString = "Cliccato 'continua' in 29modifica";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        boolean continua=true;
        if(!statoNazionalita){
            continua=false;
            nazionalitaSpinner.startAnimation(shakeAnimation);
        }
        if(!statoCitta){
            continua=false;
            cittaSpinner.startAnimation(shakeAnimation);
        }
        if(!statoNome){
            continua=false;
            nomeEditText.startAnimation(shakeAnimation);
        }
        if(!statoCognome){
            continua=false;
            cognomeEditText.startAnimation(shakeAnimation);
        }
        if(!statoBio){
            continua=false;
            biografiaEditText.startAnimation(shakeAnimation);
        }
        if(!statoFoto){
            continua=false;
        }
        if(continua){
            Intent intent = new Intent(Activity29ModificaProfilo.this, Activity30ModificaProfilo.class);
            intent.putExtra(nomeStringa, nomeEditText.getText().toString());
            intent.putExtra(cognomeStringa, cognomeEditText.getText().toString());
            intent.putExtra(nazionalitaStringa, nazionalitaSpinner.getText().toString());
            intent.putExtra(cittaStringa, cittaSpinner.getText().toString());
            intent.putExtra(biografiaStringa, biografiaEditText.getText().toString());
            intent.putExtra(linkWebStringa, linkWeb);
            intent.putExtra(socialLinksStringa, socialLinks);
            intent.putExtra(emailStringa, email);
            intent.putExtra("url", url);
            intent.putExtra(passwordStringa, password);

            if (uriImage != null) {
                intent.putExtra(fotoStringa, uriImage.toString());
                // Fai qualcosa con la stringa dell'URI
            } else {
                intent.putExtra(fotoStringa, "");
            }
            String imageToSaveStringa = "imageToSave";
            intent.putExtra(imageToSaveStringa, imageConvertToSave);


            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }else {
            String messageString2 = "L'utente non ha compilato tutti i campi in 29modifica";
            Log.d(logging, messageString2);

            String message = "Compila tutti i campi per proseguire";
            textViewError.setText(message);

            // Crea un Handler e posticipa l'esecuzione di un Runnable che resetta il testo.
            new Handler(Looper.getMainLooper()).postDelayed(() -> textViewError.setText(""), 8000);  // 8000 millisecondi = 8 secondi
        }
    }

    public void showPopupMessageIndietro() {
        String messageString = "Cliccato 'indietro' in 29modifica";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        runOnUiThread(() -> {
            LayoutInflater inflater = LayoutInflater.from(Activity29ModificaProfilo.this);
            View customView = inflater.inflate(R.layout.activity_49_popup5, null);

            TextView btnConferma = customView.findViewById(R.id.buttonAcquista);
            TextView btnAnnulla = customView.findViewById(R.id.buttonVendi);

            function.setTouchListenerForAnimation(btnAnnulla, this);
            function.setTouchListenerForAnimation(btnConferma, this);

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity29ModificaProfilo.this);
            builder.setCustomTitle(customView)
                    .setCancelable(false);

            android.app.AlertDialog dialog = builder.create();
            if (!isFinishing() && dialog != null && !dialog.isShowing()) {
                dialog.show();

                // Gestione del clic sul bottone "Conferma"
                btnConferma.setOnClickListener(v -> {
                    // Chiudi il popup e l'activity corrente
                    dialog.dismiss();
                    Activity29ModificaProfilo.this.finish();
                    overridePendingTransition(0, 0);
                });

                // Gestione del clic sul bottone "Annulla"
                btnAnnulla.setOnClickListener(v -> {
                    // Chiudi solo il popup
                    dialog.dismiss();
                });
            }
        });
    }


}
