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

import androidx.annotation.NonNull;

public class activity07SignupActivity1 extends AppCompatActivity {
    private Animation buttonAnimation, shakeAnimation;
    private EditText nomeEditText, cognomeEditText, biografiaEditText;
    private Spinner nazionalitaSpinner, cittaSpinner;
    private TextView charCountTextView;
    private JSONObject datiNazionalitaCitta;
    private ImageView fotoImageView, inserisciFoto, indietroImageView, check1, check2, check3;
    private TextView continuaButton, textViewError;
    private String citta="";
    private String socialLinks="";
    private String linkWeb="";
    private String email="";
    private String password="";
    private String confermaPassword="";
    private String imageConvertToSave="";
    private boolean terms=false;
    private Boolean statoNome = false, statoCognome = false, statoNazionalita = false, statoCitta = false, statoBio = false, statoFoto=false;
    private Uri uriImage;
    private static final String nomeRegex = "^[A-Za-z]+$";
    private static final String cognomeRegex = "^[A-Za-z]+$";
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_07_signup1);

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
        buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.button_animation);
        continuaButton = findViewById(R.id.continuaButton);
        indietroImageView = findViewById(R.id.indietroImageView);
        check1 = findViewById(R.id.passwordToggle);
        check2 = findViewById(R.id.passwordToggle2);
        check3 = findViewById(R.id.biografiaToggle);
        textViewError= findViewById(R.id.textInfoError);
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake_error);
        charCountTextView = findViewById(R.id.charCountTextView);
        inserisciFoto = findViewById(R.id.inserisciFoto);
    }

    private void setListener() {
        setTouchListenerForAnimation(continuaButton);
        setTouchListenerForAnimation(indietroImageView);
        fotoImageView.setOnClickListener(v -> selectImageFromGallery());
        inserisciFoto.setOnClickListener(v -> selectImageFromGallery());

        continuaButton.setOnClickListener(v -> continuaFunction());

        indietroImageView.setOnClickListener(v -> {
                Intent intent = new Intent(this, activity05WelcomeActivity.class);
                intent.putExtra("nome", nomeEditText.getText().toString());
                intent.putExtra("cognome", cognomeEditText.getText().toString());
                intent.putExtra("nazionalita", nazionalitaSpinner.getSelectedItem().toString());
                intent.putExtra("citta", cittaSpinner.getSelectedItem().toString());
                intent.putExtra("biografia", biografiaEditText.getText().toString());
                intent.putExtra("linkWeb", linkWeb);
                intent.putExtra("socialLinks", socialLinks);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("confermaPassword", confermaPassword);
                intent.putExtra("terms", terms);
                if (uriImage != null) {
                    intent.putExtra("image", uriImage.toString());
                    // Fai qualcosa con la stringa dell'URI
                } else {
                    intent.putExtra("image", "");
                }
            intent.putExtra("imageToSave", imageConvertToSave);

            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();

        });

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
                String message = testoInserito.length()+"/255";
                charCountTextView.setText(message);

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
                String message = testoInserito.length()+"/255";
                charCountTextView.setText(message);

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
                String message = testoInserito.length()+"/255";
                charCountTextView.setText(message);

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

    private void setExtraParameters(){
        Intent intentRicevuto = getIntent();

        String nome = intentRicevuto.getStringExtra("nome");
        String cognome = intentRicevuto.getStringExtra("cognome");
        String nazionalita = intentRicevuto.getStringExtra("nazionalita");
        citta = intentRicevuto.getStringExtra("citta");
        String biografia = intentRicevuto.getStringExtra("biografia");
        linkWeb = intentRicevuto.getStringExtra("linkWeb");
        socialLinks = intentRicevuto.getStringExtra("socialLinks");
        email = intentRicevuto.getStringExtra("email");
        password = intentRicevuto.getStringExtra("password");
        confermaPassword = intentRicevuto.getStringExtra("confermaPassword");
        terms = intentRicevuto.getBooleanExtra("terms", false);
        String uriImageString = intentRicevuto.getStringExtra("image");
        imageConvertToSave = intentRicevuto.getStringExtra("imageToSave");

        nomeEditText.setText(nome);
        cognomeEditText.setText(cognome);
        biografiaEditText.setText(biografia);

        if (uriImageString != null && !uriImageString.isEmpty()) {
            uriImage = Uri.parse(uriImageString);
            fotoImageView.setImageURI(uriImage);
            statoFoto=true;

        } else {
            // Gestisci la situazione in cui la foto è nulla, ad esempio impostando un'immagine predefinita
            fotoImageView.setImageResource(R.drawable.image_setprofilo);
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

    private void caricaDatiDaJson(){
        try (InputStream is = getAssets().open("nazioni_e_citta.json")) {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.close();
            String jsonStr = new String(buffer, StandardCharsets.UTF_8);
            datiNazionalitaCitta = new JSONObject(jsonStr);
            Log.d("SignupActivity1", "Dati caricati correttamente.");
        } catch (IOException e) {
            Log.e("SignupActivity1", "Errore nella lettura del file", e);
            Toast.makeText(this, "Errore nel caricamento dei dati", Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            Log.e("SignupActivity1", "Errore nel parsing del JSON", e);
            Toast.makeText(this, "Errore nel parsing dei dati", Toast.LENGTH_LONG).show();
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
            Intent intent = new Intent(activity07SignupActivity1.this, activity08SignupActivity2.class);
            intent.putExtra("nome", nomeEditText.getText().toString());
            intent.putExtra("cognome", cognomeEditText.getText().toString());
            intent.putExtra("nazionalita", nazionalitaSpinner.getSelectedItem().toString());
            intent.putExtra("citta", cittaSpinner.getSelectedItem().toString());
            intent.putExtra("biografia", biografiaEditText.getText().toString());
            intent.putExtra("linkWeb", linkWeb);
            intent.putExtra("socialLinks", socialLinks);
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            intent.putExtra("confermaPassword", confermaPassword);
            intent.putExtra("terms", terms);
            if (uriImage != null) {
                intent.putExtra("image", uriImage.toString());
                // Fai qualcosa con la stringa dell'URI
            } else {
                intent.putExtra("image", "");
            }
            intent.putExtra("imageToSave", imageConvertToSave);

            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }else {
            String message = "Compila tutti i campi per proseguire";
            textViewError.setText(message);

            // Crea un Handler e posticipa l'esecuzione di un Runnable che resetta il testo.
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
}
