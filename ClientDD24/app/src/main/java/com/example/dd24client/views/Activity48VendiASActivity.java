package com.example.dd24client.views;

import android.Manifest;
import android.app.DatePickerDialog;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.dd24client.model.astasilenziosaModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.network.RetrofitClient;
import com.example.dd24client.presenter.presenter39CreaAstaSilenziosaPresenter;
import com.example.dd24client.R;
import com.example.dd24client.utils.messageResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

public class activity48VendiASActivity extends AppCompatActivity implements activity48VendiASView {
    private Animation buttonAnimation, shakeAnimation;
    private TextView continuaButton, charCountTextView, charCountTitoloTextView, button_upload, textViewError;
    private ImageButton titoloCheck, descrCheck, dataCheck;
    private EditText editTextTitolo, editTextDescrizione, editTextData;
    private Spinner categoriaSpinner, sottocategoriaSpinner, tipologiaSpinner;
    private ImageView indietroImageView, fotoImageView;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private JSONObject datiCategorie;
    private Boolean titoloStato=false, descrStato=false, categoriaStato=false, sottocategoriaStato=false, dataStato=false, fotoStato=false;
    private String email="";
    private String imageConvertToSave="";
    private presenter39CreaAstaSilenziosaPresenter createPresenter;
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_48_vendi3); // Sostituisci con il tuo file layout

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.darkOrange));

        setComponents();
        setListener();

        setExtraParameters();

    }

    private void setComponents() {
        continuaButton = findViewById(R.id.continuaButton);
        titoloCheck = findViewById(R.id.titoloCheck);
        descrCheck = findViewById(R.id.descrCheck);
        editTextTitolo = findViewById(R.id.editTextTitolo);
        editTextDescrizione = findViewById(R.id.editTextDescrizione);
        editTextData = findViewById(R.id.editTextData);
        dataCheck = findViewById(R.id.dataCheck);
        categoriaSpinner = findViewById(R.id.categoriaSpinner);
        sottocategoriaSpinner = findViewById(R.id.sottocategoriaSpinner);
        indietroImageView = findViewById(R.id.indietroImageView);
        charCountTextView = findViewById(R.id.charCountTextView);
        charCountTitoloTextView = findViewById(R.id.charCountTitoloTextView);
        fotoImageView = findViewById(R.id.fotoImageView);
        button_upload = findViewById(R.id.button_upload);
        tipologiaSpinner = findViewById(R.id.spinnerTipologia);
        textViewError = findViewById(R.id.textInfoError);

        createPresenter = new presenter39CreaAstaSilenziosaPresenter(this,apiService);

        String[] tipoAstaArray = getResources().getStringArray(R.array.tipoasta);
        int indexAlRibasso = Arrays.asList(tipoAstaArray).indexOf("Silenziosa");

        if (indexAlRibasso >= 0) {
            tipologiaSpinner.setSelection(indexAlRibasso);
        }

        buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.button_animation);
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake_error);

        caricaDatiDaJson();
        setupcategoriaSpinner();
    }

    private void setListener() {
        setTouchListenerForAnimation(indietroImageView);
        setTouchListenerForAnimation(button_upload);
        setTouchListenerForAnimation(continuaButton);

        editTextTitolo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Questo metodo viene invocato prima che il testo venga cambiato.
                String testoInserito = s.toString();
                editTextTitolo.setTextColor(Color.parseColor("#000000"));
                charCountTitoloTextView.setText(testoInserito.length()+"/150");

                // Qui puoi inserire il tuo controllo per verificare se il testo inserito è giusto.
                // Ad esempio, supponiamo che il testo sia "giusto" se non è vuoto e se contiene la parola "esempio".
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    titoloCheck.setImageResource(R.drawable.icon_basiccheck);
                    titoloStato = false;

                } else if(testoInserito.length()<=150){
                    titoloCheck.setImageResource(R.drawable.icon_okcheck);
                    titoloStato = true;
                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    titoloCheck.setImageResource(R.drawable.icon_nocheck);
                    titoloStato = false;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Questo metodo viene invocato mentre il testo sta cambiando.
                String testoInserito = s.toString();
                editTextTitolo.setTextColor(Color.parseColor("#000000"));
                charCountTitoloTextView.setText(testoInserito.length()+"/150");

                // Qui puoi inserire il tuo controllo per verificare se il testo inserito è giusto.
                // Ad esempio, supponiamo che il testo sia "giusto" se non è vuoto e se contiene la parola "esempio".
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    titoloCheck.setImageResource(R.drawable.icon_basiccheck);
                    titoloStato = false;
                } else if(testoInserito.length()<=150){
                    titoloCheck.setImageResource(R.drawable.icon_okcheck);
                    titoloStato = true;
                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    titoloCheck.setImageResource(R.drawable.icon_nocheck);
                    titoloStato = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Questo metodo viene invocato subito dopo che il testo è stato cambiato.
                String testoInserito = s.toString();
                editTextTitolo.setTextColor(Color.parseColor("#000000"));
                charCountTitoloTextView.setText(testoInserito.length()+"/150");

                // Qui puoi inserire il tuo controllo per verificare se il testo inserito è giusto.
                // Ad esempio, supponiamo che il testo sia "giusto" se non è vuoto e se contiene la parola "esempio".
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    titoloCheck.setImageResource(R.drawable.icon_basiccheck);
                    titoloStato = false;
                } else if(testoInserito.length()<=150){
                    titoloCheck.setImageResource(R.drawable.icon_okcheck);
                    titoloStato = true;
                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    titoloCheck.setImageResource(R.drawable.icon_nocheck);
                    titoloStato = false;
                }

            }
        });
        editTextDescrizione.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Questo metodo viene invocato prima che il testo venga cambiato.
                String testoInserito = s.toString();
                editTextDescrizione.setTextColor(Color.parseColor("#000000"));
                charCountTextView.setText(testoInserito.length()+"/500");

                // Qui puoi inserire il tuo controllo per verificare se il testo inserito è giusto.
                // Ad esempio, supponiamo che il testo sia "giusto" se non è vuoto e se contiene la parola "esempio".
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    descrCheck.setImageResource(R.drawable.icon_basiccheck);
                    descrStato = false;
                } else if(testoInserito.length()<=500){
                    descrCheck.setImageResource(R.drawable.icon_okcheck);
                    descrStato = true;

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    descrCheck.setImageResource(R.drawable.icon_nocheck);
                    descrStato = false;

                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Questo metodo viene invocato mentre il testo sta cambiando.
                String testoInserito = s.toString();
                editTextDescrizione.setTextColor(Color.parseColor("#000000"));
                charCountTextView.setText(testoInserito.length()+"/500");

                // Qui puoi inserire il tuo controllo per verificare se il testo inserito è giusto.
                // Ad esempio, supponiamo che il testo sia "giusto" se non è vuoto e se contiene la parola "esempio".
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    descrCheck.setImageResource(R.drawable.icon_basiccheck);
                    descrStato = false;

                } else if(testoInserito.length()<=500){
                    descrCheck.setImageResource(R.drawable.icon_okcheck);
                    descrStato = true;

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    descrCheck.setImageResource(R.drawable.icon_nocheck);
                    descrStato = false;

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Questo metodo viene invocato subito dopo che il testo è stato cambiato.
                String testoInserito = s.toString();
                editTextDescrizione.setTextColor(Color.parseColor("#000000"));
                charCountTextView.setText(testoInserito.length()+"/500");

                // Qui puoi inserire il tuo controllo per verificare se il testo inserito è giusto.
                // Ad esempio, supponiamo che il testo sia "giusto" se non è vuoto e se contiene la parola "esempio".
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    descrCheck.setImageResource(R.drawable.icon_basiccheck);
                    descrStato = false;

                } else if(testoInserito.length()<=500){
                    descrCheck.setImageResource(R.drawable.icon_okcheck);
                    descrStato = true;

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    descrCheck.setImageResource(R.drawable.icon_nocheck);
                    descrStato = false;

                }

            }
        });
        sottocategoriaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                // Imposta sottocategoriaStato a true se l'elemento selezionato NON è "Sottocategoria", altrimenti false
                sottocategoriaStato = !selectedItem.equals("Sottocategoria");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Non è necessario fare nulla qui
            }
        });

        editTextData.setOnClickListener(view -> {
            // Ottieni la data corrente e aggiungi un giorno per inizializzare il picker con la data di domani
            final Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, 1); // Aggiunge un giorno alla data corrente
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Crea il DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view1, year1, monthOfYear, dayOfMonth) -> {
                        // Imposta l'EditText con la data formattata
                        String formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year1);
                        editTextData.setTextColor(Color.parseColor("#000000"));
                        editTextData.setText(formattedDate);
                        dataCheck.setImageResource(R.drawable.icon_okcheck);
                        dataStato=true;
                    }, year, month, day);

            // Imposta la data minima selezionabile a domani per impedire la selezione della data corrente e di quelle passate
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());

            // Mostra il DatePickerDialog
            datePickerDialog.show();
        });
        button_upload.setOnClickListener(v -> selectImageFromGallery());
        continuaButton.setOnClickListener(v -> continuaFunction());
        indietroImageView.setOnClickListener(v -> showPopupMessageIndietro());

        // Assume che editTextData sia l'EditText per la data
        tipologiaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = tipologiaSpinner.getItemAtPosition(position).toString();

                if (selectedItem.equals("Al Ribasso")) {
                    // Apre l'activity Home
                    Intent intent = new Intent(activity48VendiASActivity.this, activity47VendiARActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                } else if (selectedItem.equals("Tipologia")) {
                    // Apre l'activity Dashboard
                    Intent intent = new Intent(activity48VendiASActivity.this, activity46VendiActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Non fare nulla qui
            }
        });

    }

    private void setExtraParameters() {
        Intent intentRicevuto = getIntent();
        email = intentRicevuto.getStringExtra("email");
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
            Uri uriImage = data.getData();
            // Utilizza Glide per caricare l'immagine
            fotoImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(this)
                    .load(uriImage)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(14))) // 14 è il raggio degli angoli arrotondati
                    .into(fotoImageView);

            ConstraintLayout.LayoutParams imageParams = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.MATCH_PARENT
            );
            fotoImageView.setLayoutParams(imageParams);

            // Continua con l'upload dell'immagine
            imageConvertToSave = saveImageToInternalStorage(uriImage);
            fotoStato = imageConvertToSave != null;
        }
    }

    private String saveImageToInternalStorage(Uri uri) {
        String imagePath = null;
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            File outputFile = new File(getCacheDir(), "tempImage.jpg");
            try (FileOutputStream out = new FileOutputStream(outputFile)) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                imagePath = outputFile.getAbsolutePath();
            }
        } catch (IOException ignored) {
        }
        return imagePath;
    }

    private void continuaFunction(){
        boolean continua = true;

        if(!titoloStato){
            continua = false;
            editTextTitolo.startAnimation(shakeAnimation);
        }
        if(!descrStato){
            continua = false;
            editTextDescrizione.startAnimation(shakeAnimation);
        }
        if(!categoriaStato){
            continua = false;
            categoriaSpinner.startAnimation(shakeAnimation);
        }
        if(!sottocategoriaStato){
            continua = false;
            sottocategoriaSpinner.startAnimation(shakeAnimation);
        }
        if(!dataStato){
            continua = false;
            editTextData.startAnimation(shakeAnimation);
        }
        if(!fotoStato){
            continua = false;
        }

        if (continua) {
            showPopupMessage();
        } else {
            textViewError.setText("Compila tutti i campi per proseguire");

            // Crea un Handler e posticipa l'esecuzione di un Runnable che resetta il testo.
            new Handler(Looper.getMainLooper()).postDelayed(() -> textViewError.setText(""), 8000);  // 8000 millisecondi = 8 secondi
        }

    }

    private void caricaDatiDaJson() {
        InputStream is = null;
        try {
            is = getAssets().open("categorie_e_sottocategorie.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            int bytesRead = is.read(buffer); // Lettura del flusso di input
            if (bytesRead != -1) {
                String jsonStr = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8);
                datiCategorie = new JSONObject(jsonStr);
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

    private void setupcategoriaSpinner() {
        ArrayList<String> categoria = new ArrayList<>();
        Iterator<String> keys = datiCategorie.keys();
        while (keys.hasNext()) {
            categoria.add(keys.next());
        }
        ArrayAdapter<String> adaptercategoria = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoria);
        adaptercategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriaSpinner.setAdapter(adaptercategoria);

        categoriaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedcategoria = (String) parent.getItemAtPosition(position);
                updatesottocategoriaSpinner(selectedcategoria);
                String selectedItem = parent.getItemAtPosition(position).toString();
                categoriaStato = !selectedItem.equals("Categoria");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void updatesottocategoriaSpinner(String categoria) {
        ArrayList<String> sottocategoria = new ArrayList<>();
        try {
            JSONArray arr = datiCategorie.getJSONArray(categoria);
            for (int i = 0; i < arr.length(); i++) {
                sottocategoria.add(arr.getString(i));
            }
        } catch (JSONException ignored) {
        }
        ArrayAdapter<String> adaptersottocategoria = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sottocategoria);
        adaptersottocategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sottocategoriaSpinner.setAdapter(adaptersottocategoria);
    }

    public void showPopupMessage() {
        runOnUiThread(() -> {
            LayoutInflater inflater = LayoutInflater.from(activity48VendiASActivity.this);
            View customView = inflater.inflate(R.layout.activity_49_popup4, null);

            TextView btnConferma = customView.findViewById(R.id.buttonAcquista);
            TextView btnAnnulla = customView.findViewById(R.id.buttonVendi);

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity48VendiASActivity.this);
            builder.setCustomTitle(customView)
                    .setCancelable(false);

            android.app.AlertDialog dialog = builder.create();
            if (!isFinishing() && dialog != null && !dialog.isShowing()) {
                dialog.show();

                // Gestione del clic sul bottone "Conferma"
                btnConferma.setOnClickListener(v -> {
                    // Chiudi il popup e l'activity corrente
                    dialog.dismiss();
                    String immagine = null;
                    try {
                        File imageFile = new File(imageConvertToSave);
                        Bitmap originalBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());

                        // Ridimensiona l'immagine
                        int newWidth = 480; // Definisci la nuova larghezza
                        int newHeight = (originalBitmap.getHeight() * newWidth) / originalBitmap.getWidth(); // Calcola la nuova altezza mantenendo il rapporto
                        Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);

                        // Comprimi l'immagine
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 75, outputStream); // Comprimi con qualità al 75%

                        // Converti in array di byte e codifica in base64
                        byte[] imageBytes = outputStream.toByteArray();
                        immagine = Base64.getEncoder().encodeToString(imageBytes);
                    } catch (Exception ignored) {
                    }

                    String titolo = editTextTitolo.getText().toString();
                    String categoria = (String) categoriaSpinner.getSelectedItem();
                    String sottocategoria = (String) sottocategoriaSpinner.getSelectedItem();
                    String descrizione = editTextDescrizione.getText().toString();
                    String data = editTextData.getText().toString();

                    astasilenziosaModel asta = new astasilenziosaModel(true, titolo, categoria, sottocategoria, descrizione, email, immagine, data);
                    createPresenter.creaAstasilenziosa2(asta);

                });

                // Gestione del clic sul bottone "Annulla"
                btnAnnulla.setOnClickListener(v -> {
                    // Chiudi solo il popup
                    dialog.dismiss();
                });
            }
        });
    }

    public void showPopupMessageIndietro() {
        runOnUiThread(() -> {
            LayoutInflater inflater = LayoutInflater.from(activity48VendiASActivity.this);
            View customView = inflater.inflate(R.layout.activity_49_popup5, null);

            TextView btnConferma = customView.findViewById(R.id.buttonAcquista);
            TextView btnAnnulla = customView.findViewById(R.id.buttonVendi);

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity48VendiASActivity.this);
            builder.setCustomTitle(customView)
                    .setCancelable(false);

            android.app.AlertDialog dialog = builder.create();
            if (!isFinishing() && dialog != null && !dialog.isShowing()) {
                dialog.show();

                // Gestione del clic sul bottone "Conferma"
                btnConferma.setOnClickListener(v -> {
                    // Chiudi il popup e l'activity corrente
                    dialog.dismiss();
                    overridePendingTransition(0, 0);
                    finish();
                });

                // Gestione del clic sul bottone "Annulla"
                btnAnnulla.setOnClickListener(v -> {
                    // Chiudi solo il popup
                    dialog.dismiss();
                });
            }
        });
    }


    @Override
    public void onCreateSuccess(messageResponse body) {
        Toast.makeText(activity48VendiASActivity.this, body.getMessage(), Toast.LENGTH_SHORT).show();
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    public void onCreateFailure(messageResponse errore_di_registrazione) {
        Toast.makeText(activity48VendiASActivity.this, errore_di_registrazione.getMessage(),
                Toast.LENGTH_SHORT).show();
    }
}

