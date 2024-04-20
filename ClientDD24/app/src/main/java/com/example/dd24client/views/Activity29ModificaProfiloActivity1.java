package com.example.dd24client.views;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.dd24client.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

public class activity29ModificaProfiloActivity1 extends AppCompatActivity{
    private Animation buttonAnimation, shakeAnimation;
    private EditText nomeEditText, cognomeEditText, biografiaEditText;
    private Spinner nazionalitaSpinner, cittaSpinner;
    private TextView charCountTextView;
    private JSONObject datiNazionalitaCitta;
    private ImageView fotoImageView, indietroImageView, check1, check2, check3;
    private TextView continuaButton, textViewError;
    private String citta="";
    private String socialLinks="";
    private String linkWeb="";
    private String email="";
    private String password="";
    private String imageConvertToSave="";
    private String url="";
    private Uri uriImage;
    private static final String nomeRegex = "^[A-Za-z]+( [A-Za-z]+)*$";
    private static final String cognomeRegex = "^[A-Za-z]+( [A-Za-z]+)*$";
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private Boolean statoNome = false, statoCognome = false, statoNazionalita = false, statoCitta = false, statoBio = false, statoFoto=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_29_modifica_profilo1);

        setComponents();
        setListener();

        caricaDatiDaJson();
        setupNazionalitaSpinner();

        setExtraParameters();

    }

    private void setComponents() {
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
        textViewError= findViewById(R.id.textInfoError);

        buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.button_animation);
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake_error);
        charCountTextView = findViewById(R.id.charCountTextView);
    }

    private void setListener() {
        setTouchListenerForAnimation(continuaButton);
        setTouchListenerForAnimation(indietroImageView);
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

        cittaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                // Imposta sottocategoriaStato a true se l'elemento selezionato NON è "Sottocategoria", altrimenti false
                statoCitta = !selectedItem.equals("Citta");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Non è necessario fare nulla qui
            }
        });

    }

    private void setExtraParameters() {
        Intent intentRicevuto = getIntent();

        email = intentRicevuto.getStringExtra("email");
        password = intentRicevuto.getStringExtra("password");
        String nome = intentRicevuto.getStringExtra("nome");
        String cognome = intentRicevuto.getStringExtra("cognome");
        String nazionalita = intentRicevuto.getStringExtra("nazionalita");
        citta = intentRicevuto.getStringExtra("citta");
        String biografia = intentRicevuto.getStringExtra("biografia");
        linkWeb = intentRicevuto.getStringExtra("linkWeb");
        socialLinks = intentRicevuto.getStringExtra("socialLinks");
        String uriImageString = intentRicevuto.getStringExtra("image");
        imageConvertToSave = intentRicevuto.getStringExtra("imageToSave");
        url = intentRicevuto.getStringExtra("url");

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

        // Ottieni l'Adapter dello Spinner
        Adapter adapter = nazionalitaSpinner.getAdapter();
        int count = adapter.getCount();

        int indicePerSelezione = -1;
        for (int i = 0; i < count; i++) {
            if (nazionalita != null && nazionalita.equals(adapter.getItem(i).toString())) {
                indicePerSelezione = i;
                break;
            }
        }

        if (indicePerSelezione != -1) {
            nazionalitaSpinner.setSelection(indicePerSelezione);
            // Aggiorna le città basandoti sulla nazionalità selezionata.

            // Utilizza un Handler per ritardare l'impostazione della selezione della città.
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                Adapter adapter2 = cittaSpinner.getAdapter();
                int count2 = adapter2.getCount();
                int indicePerSelezione2 = -1;
                for (int i = 0; i < count2; i++) {
                    if (citta.equals(adapter2.getItem(i).toString())) {
                        indicePerSelezione2 = i;
                        break;
                    }
                }
                if (indicePerSelezione2 != -1) {
                    cittaSpinner.setSelection(indicePerSelezione2);
                }
            }, 100); // Ritardo di 100ms (aggiusta il tempo secondo necessità)
        }


    }

    private void caricaDatiDaJson() {
        InputStream is = null;
        try {
            is = getAssets().open("nazioni_e_citta.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            int bytesRead = is.read(buffer); // Lettura del flusso di input
            if (bytesRead != -1) {
                String jsonStr = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8);
                datiNazionalitaCitta = new JSONObject(jsonStr);
                Log.d("SignupActivity1", "Dati caricati correttamente.");
            } else {
                Log.e("SignupActivity1", "Nessun byte letto dal flusso di input");
                Toast.makeText(this, "Errore nel caricamento dei dati", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            Log.e("SignupActivity1", "Errore nella lettura del file", e);
            Toast.makeText(this, "Errore nel caricamento dei dati", Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            Log.e("SignupActivity1", "Errore nel parsing del JSON", e);
            Toast.makeText(this, "Errore nel parsing dei dati", Toast.LENGTH_LONG).show();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.e("SignupActivity1", "Errore nella chiusura del flusso di input", e);
                }
            }
        }
    }

    private void setupNazionalitaSpinner() {
        ArrayList<String> nazionalita = new ArrayList<>();
        Iterator<String> keys = datiNazionalitaCitta.keys();
        while (keys.hasNext()) {
            nazionalita.add(keys.next());
        }
        ArrayAdapter<String> adapterNazionalita = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nazionalita);
        adapterNazionalita.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nazionalitaSpinner.setAdapter(adapterNazionalita);

        nazionalitaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedNazionalita = (String) parent.getItemAtPosition(position);
                updateCittaSpinner(selectedNazionalita);
                String selectedItem = parent.getItemAtPosition(position).toString();
                statoNazionalita = !selectedItem.equals("Nazione");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void updateCittaSpinner(String nazionalita) {
        ArrayList<String> citta = new ArrayList<>();
        try {
            JSONArray arr = datiNazionalitaCitta.getJSONArray(nazionalita);
            for (int i = 0; i < arr.length(); i++) {
                citta.add(arr.getString(i));
            }
        } catch (JSONException e) {
            citta.add("Non disponibile");
        }
        ArrayAdapter<String> adapterCitta = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, citta);
        adapterCitta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

            Log.d("FOTO3", imageConvertToSave + " - " + uriImage + " - " + url);
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

    private void continuaFunction(){
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
            Intent intent = new Intent(activity29ModificaProfiloActivity1.this, activity30ModificaProfiloActivity2.class);
            intent.putExtra("nome", nomeEditText.getText().toString());
            intent.putExtra("cognome", cognomeEditText.getText().toString());
            intent.putExtra("nazionalita", nazionalitaSpinner.getSelectedItem().toString());
            intent.putExtra("citta", cittaSpinner.getSelectedItem().toString());
            intent.putExtra("biografia", biografiaEditText.getText().toString());
            intent.putExtra("linkWeb", linkWeb);
            intent.putExtra("socialLinks", socialLinks);
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            if (uriImage != null) {
                intent.putExtra("image", uriImage.toString());
                // Fai qualcosa con la stringa dell'URI
            } else {
                intent.putExtra("image", "");
            }
            intent.putExtra("imageToSave", imageConvertToSave);
            intent.putExtra("url", url);

            Log.d("FOTO1", imageConvertToSave + " - " + uriImage + " - " + url);

            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }else {
            textViewError.setText("Compila tutti i campi per proseguire");

            new Handler(Looper.getMainLooper()).postDelayed(() -> textViewError.setText(""), 8000);  // 8000 millisecondi = 8 secondi
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

    public void showPopupMessageIndietro() {
        runOnUiThread(() -> {
            LayoutInflater inflater = LayoutInflater.from(activity29ModificaProfiloActivity1.this);
            View customView = inflater.inflate(R.layout.activity_49_popup5, null);

            TextView btnConferma = customView.findViewById(R.id.buttonAcquista);
            TextView btnAnnulla = customView.findViewById(R.id.buttonVendi);

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity29ModificaProfiloActivity1.this);
            builder.setCustomTitle(customView)
                    .setCancelable(false);

            android.app.AlertDialog dialog = builder.create();
            if (!isFinishing() && dialog != null && !dialog.isShowing()) {
                dialog.show();

                // Gestione del clic sul bottone "Conferma"
                btnConferma.setOnClickListener(v -> {
                    // Chiudi il popup e l'activity corrente
                    dialog.dismiss();
                    activity29ModificaProfiloActivity1.this.finish();
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
