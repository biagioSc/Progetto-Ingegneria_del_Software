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
import com.example.dd24client.model.AstaribassoModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.network.RetrofitClient;
import com.example.dd24client.presenter.Presenter38CreaAstaRibasso;
import com.example.dd24client.R;
import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.utils.UtilsFunction;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;


public class Activity47VendiAR extends AppCompatActivity implements Activity47VendiARView {
    private FirebaseAnalytics mFirebaseAnalytics;
    private UtilsFunction function;
    private Animation shakeAnimation;
    private TextView textViewError, continuaButton, charCountTextView, charCountTitoloTextView, button_upload;
    private ImageButton titoloCheck, descrCheck;
    private EditText editTextTitolo, editTextDescrizione;
    private Spinner categoriaSpinner, sottocategoriaSpinner, tipologiaSpinner;
    private ImageView indietroImageView, fotoImageView;
    private ImageButton check1, check2, check3, check4;
    private EditText editTextImporto, editTextPrezzoMin, editTextTimer, editTextPrezzo;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private JSONObject datiCategorie;
    private boolean titoloStato=false, descrStato=false, categoriaStato=false, sottocategoriaStato=false, fotoStato=false, decrStato=false, minStato=false, timerStato=false, prezzoStato=false;
    private String email="", password="", imageConvertToSave="";
    private Uri uriImage;
    private Presenter38CreaAstaRibasso createPresenter;
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    int prezzoiniziale=-1, decremento=-1, minimo=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_47_vendi2); // Sostituisci con il tuo file layout

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.darkOrange));

        setComponents();
        setListener();
        setExtraParameters();

    }

    private void setComponents() {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        function = new UtilsFunction();

        continuaButton = findViewById(R.id.continuaButton);
        titoloCheck = findViewById(R.id.titoloCheck);
        descrCheck = findViewById(R.id.descrCheck);
        editTextTitolo = findViewById(R.id.editTextTitolo);
        editTextDescrizione = findViewById(R.id.editTextDescrizione);
        categoriaSpinner = findViewById(R.id.categoriaSpinner);
        sottocategoriaSpinner = findViewById(R.id.sottocategoriaSpinner);
        tipologiaSpinner = findViewById(R.id.spinnertipo);
        indietroImageView = findViewById(R.id.indietroImageView);
        charCountTextView = findViewById(R.id.charCountTextView);
        charCountTitoloTextView = findViewById(R.id.charCountTitoloTextView);
        fotoImageView = findViewById(R.id.fotoImageView);
        button_upload = findViewById(R.id.button_upload);
        editTextImporto = findViewById(R.id.editTextImporto);
        editTextPrezzoMin = findViewById(R.id.editTextPrezzoMin);
        editTextTimer = findViewById(R.id.editTextTimer);
        editTextPrezzo = findViewById(R.id.editTextPrezzo);
        check1 = findViewById(R.id.passwordToggle1);
        check2 = findViewById(R.id.passwordToggle2);
        check3 = findViewById(R.id.passwordToggle3);
        check4 = findViewById(R.id.passwordToggle4);
        textViewError = findViewById(R.id.textInfoError);

        createPresenter = new Presenter38CreaAstaRibasso(this, apiService);

        String[] tipoAstaArray = getResources().getStringArray(R.array.tipoasta);
        int indexAlRibasso = Arrays.asList(tipoAstaArray).indexOf("Al Ribasso");

        if (indexAlRibasso >= 0) {
            tipologiaSpinner.setSelection(indexAlRibasso);
        }

        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake_error);

    }

    private void setListener() {

        function.setTouchListenerForAnimation(indietroImageView, this);
        function.setTouchListenerForAnimation(button_upload, this);
        function.setTouchListenerForAnimation(continuaButton, this);

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

        button_upload.setOnClickListener(v -> selectImageFromGallery());
        continuaButton.setOnClickListener(v -> continuaFunction());
        indietroImageView.setOnClickListener(v -> showPopupMessageIndietro());

        editTextImporto.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                editTextImporto.setTextColor(Color.parseColor("#000000"));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editTextImporto.setTextColor(Color.parseColor("#000000"));
            }

            @Override
            public void afterTextChanged(Editable s) {
                editTextImporto.setTextColor(Color.parseColor("#000000"));

                if (!s.toString().equals(current)) {
                    editTextImporto.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[€\\s,]", "");

                    if (cleanString.matches("\\d+(\\.\\d{1,2})?")) {
                        decremento = Integer.parseInt(cleanString);
                        if(decremento<prezzoiniziale && decremento>=0) {
                            check1.setImageResource(R.drawable.icon_okcheck);
                            String formattedString = cleanString + " €";
                            current = formattedString;
                            editTextImporto.setText(formattedString);
                            editTextImporto.setSelection(formattedString.length() - 2);
                            decrStato = true;
                        }else {
                            check1.setImageResource(R.drawable.icon_nocheck);
                            decrStato = false;
                            decremento = -1;
                        }
                    } else {
                        check1.setImageResource(R.drawable.icon_nocheck);
                        decrStato = false;
                        decremento = -1;
                    }

                    editTextImporto.addTextChangedListener(this);
                }
            }
        });

        editTextPrezzoMin.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                editTextPrezzoMin.setTextColor(Color.parseColor("#000000"));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editTextPrezzoMin.setTextColor(Color.parseColor("#000000"));
            }

            @Override
            public void afterTextChanged(Editable s) {
                editTextPrezzoMin.setTextColor(Color.parseColor("#000000"));
                if (!s.toString().equals(current)) {
                    editTextPrezzoMin.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[€\\s,]", "");

                    if (cleanString.matches("\\d+(\\.\\d{1,2})?")) {
                        minimo = Integer.parseInt(cleanString);
                        if(minimo<prezzoiniziale && minimo>=0) {
                            check2.setImageResource(R.drawable.icon_okcheck);
                            String formattedString = cleanString + " €";
                            current = formattedString;
                            editTextPrezzoMin.setText(formattedString);
                            editTextPrezzoMin.setSelection(formattedString.length() - 2);
                            minStato = true;
                        }else {
                            check2.setImageResource(R.drawable.icon_nocheck);
                            minStato = false;
                            minimo=-1;
                        }
                    } else {
                        check2.setImageResource(R.drawable.icon_nocheck);
                        minStato = false;
                        minimo=-1;
                    }

                    editTextPrezzoMin.addTextChangedListener(this);
                }
            }
        });

        editTextTimer.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                editTextTimer.setTextColor(Color.parseColor("#000000"));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editTextTimer.setTextColor(Color.parseColor("#000000"));
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                editTextTimer.setTextColor(Color.parseColor("#000000"));
                if (!text.isEmpty()) {
                    try {
                        // Estraiamo solo la parte numerica dalla stringa.
                        String numericPart = text.replaceAll("[^\\d]", "");
                        int minutes = Integer.parseInt(numericPart);

                        if (minutes > 0) {
                            // Rimuoviamo il listener per evitare loop infiniti durante l'aggiornamento del testo.
                            editTextTimer.removeTextChangedListener(this);

                            // Aggiorniamo il campo di testo solo se il nuovo valore è diverso da quello corrente.
                            String formattedString = numericPart + " minuti";
                            if (!formattedString.equals(current)) {
                                current = formattedString;
                                editTextTimer.setText(formattedString);
                                editTextTimer.setSelection(formattedString.length() - " minuti".length());
                                check3.setImageResource(R.drawable.icon_okcheck);
                                timerStato=true;
                            }

                            // Riaggiungiamo il listener dopo aver aggiornato il testo.
                            editTextTimer.addTextChangedListener(this);
                            check3.setImageResource(R.drawable.icon_okcheck);
                            timerStato = true;
                        } else {
                            // Il numero non è valido (minore o uguale a 0)
                            check3.setImageResource(R.drawable.icon_nocheck);
                            timerStato = false;
                        }
                    } catch (NumberFormatException e) {
                        // Il testo inserito non è un numero.
                        check3.setImageResource(R.drawable.icon_nocheck);
                        timerStato=false;
                    }
                } else {
                    // Testo vuoto.
                    check3.setImageResource(R.drawable.icon_nocheck);
                    timerStato=false;
                }

            }
        });

        editTextPrezzo.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                editTextPrezzo.setTextColor(Color.parseColor("#000000"));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editTextPrezzo.setTextColor(Color.parseColor("#000000"));
            }

            @Override
            public void afterTextChanged(Editable s) {
                editTextPrezzo.setTextColor(Color.parseColor("#000000"));
                if (!s.toString().equals(current)) {
                    editTextPrezzo.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[€\\s,]", "");

                    if (cleanString.matches("\\d+(\\.\\d{1,2})?")) {
                        prezzoiniziale = Integer.parseInt(cleanString);
                        if(prezzoiniziale>0 && prezzoiniziale<=1000){
                            check4.setImageResource(R.drawable.icon_okcheck);
                            String formattedString = cleanString + " €";
                            current = formattedString;
                            editTextPrezzo.setText(formattedString);
                            editTextPrezzo.setSelection(formattedString.length() - 2);
                            prezzoStato = true;
                        }else {
                            check4.setImageResource(R.drawable.icon_nocheck);
                            prezzoStato=false;
                            prezzoiniziale=-1;
                        }
                    } else {
                        check4.setImageResource(R.drawable.icon_nocheck);
                        prezzoStato=false;
                        prezzoiniziale=-1;
                    }

                    editTextPrezzo.addTextChangedListener(this);
                }
            }
        });

        tipologiaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = tipologiaSpinner.getItemAtPosition(position).toString();

                if (selectedItem.equals("Silenziosa")) {
                    // Apre l'activity Home
                    Intent homeIntent = new Intent(Activity47VendiAR.this, Activity48VendiAS.class);
                    startActivity(homeIntent);
                    overridePendingTransition(0, 0);
                    finish();
                } else if (selectedItem.equals("Tipologia")) {
                    // Apre l'activity Dashboard
                    Intent dashboardIntent = new Intent(Activity47VendiAR.this, Activity46Vendi.class);
                    startActivity(dashboardIntent);
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

        datiCategorie = function.caricaDatiDaJson(this, "categorie_e_sottocategorie.json");

        setupcategoriaSpinner();

        email = intentRicevuto.getStringExtra("email");
        password = intentRicevuto.getStringExtra("password");

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
        } catch (JSONException e) {
            sottocategoria.add("-");
        }
        ArrayAdapter<String> adaptersottocategoria = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sottocategoria);
        adaptersottocategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sottocategoriaSpinner.setAdapter(adaptersottocategoria);
    }

    private void continuaFunction(){
        String messageString = "Cliccato 'continua' in 47vendi";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        boolean continua = true;

        int importoInt = 0;
        int prezzominInt = 0;
        int prezzoInt = 0;

        String importo = editTextImporto.getText().toString().replaceAll("[^\\d]", "");
        String prezzomin = editTextPrezzoMin.getText().toString().replaceAll("[^\\d]", "");
        String prezzo = editTextPrezzo.getText().toString().replaceAll("[^\\d]", "");

        try {
            importoInt = Integer.parseInt(importo);
        } catch (NumberFormatException e) {
            // Gestisci l'eccezione se la stringa non è un numero valido
        }

        try {
            prezzominInt = Integer.parseInt(prezzomin);
        } catch (NumberFormatException e) {
            // Gestisci l'eccezione se la stringa non è un numero valido
        }

        try {
            prezzoInt = Integer.parseInt(prezzo);
        } catch (NumberFormatException e) {
            // Gestisci l'eccezione se la stringa non è un numero valido
        }

        if(importoInt>prezzoInt){
            decrStato=false;
            check1.setImageResource(R.drawable.icon_nocheck);
        }
        if(prezzoInt-importoInt<prezzominInt){
            minStato=false;
            check2.setImageResource(R.drawable.icon_nocheck);
        }
        if(prezzominInt>prezzoInt){
            minStato=false;
            check2.setImageResource(R.drawable.icon_nocheck);
        }

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
        if(!fotoStato){
            continua = false;
        }
        if(!decrStato){
            continua = false;
            editTextImporto.startAnimation(shakeAnimation);
        }
        if(!minStato){
            continua = false;
            editTextPrezzoMin.startAnimation(shakeAnimation);
        }
        if(!timerStato){
            continua = false;
            editTextTimer.startAnimation(shakeAnimation);
        }
        if(!prezzoStato){
            continua = false;
            editTextPrezzo.startAnimation(shakeAnimation);
            String message = "Prezzo massimo consentito: 1000 €";
            Toast.makeText(Activity47VendiAR.this, message, Toast.LENGTH_SHORT).show();
        }

        if (continua) {
            showPopupMessage();
        } else {
            textViewError.setText("Compila tutti i campi per proseguire");

            // Crea un Handler e posticipa l'esecuzione di un Runnable che resetta il testo.
            new Handler(Looper.getMainLooper()).postDelayed(() -> textViewError.setText(""), 8000);  // 8000 millisecondi = 8 secondi
        }

    }

    public void showPopupMessage() {
        String messageString = "Cliccato 'continua' in 47vendi";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        runOnUiThread(() -> {
            LayoutInflater inflater = LayoutInflater.from(Activity47VendiAR.this);
            View customView = inflater.inflate(R.layout.activity_49_popup4, null);

            TextView btnConferma = customView.findViewById(R.id.buttonAcquista);
            TextView btnAnnulla = customView.findViewById(R.id.buttonVendi);

            function.setTouchListenerForAnimation(btnAnnulla, this);
            function.setTouchListenerForAnimation(btnConferma, this);

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity47VendiAR.this);
            builder.setCustomTitle(customView)
                    .setCancelable(false);

            android.app.AlertDialog dialog = builder.create();
            if (!isFinishing() && dialog != null && !dialog.isShowing()) {
                dialog.show();

                // Gestione del clic sul bottone "Conferma"
                btnConferma.setOnClickListener(v -> {
                    // Chiudi il popup e l'activity corrente
                    dialog.dismiss();

                    int importoInt = 0;
                    int prezzominInt = 0;
                    int timerInt = 0;
                    int prezzoInt = 0;

                    String importo = editTextImporto.getText().toString().replaceAll("[^\\d]", "");
                    String prezzomin = editTextPrezzoMin.getText().toString().replaceAll("[^\\d]", "");
                    String timer = editTextTimer.getText().toString().replaceAll("[^\\d]", "");
                    String prezzo = editTextPrezzo.getText().toString().replaceAll("[^\\d]", "");

                    try {
                        importoInt = Integer.parseInt(importo);
                    } catch (NumberFormatException e) {
                        // Gestisci l'eccezione se la stringa non è un numero valido
                    }

                    try {
                        prezzominInt = Integer.parseInt(prezzomin);
                    } catch (NumberFormatException e) {
                        // Gestisci l'eccezione se la stringa non è un numero valido
                    }

                    try {
                        timerInt = Integer.parseInt(timer);
                    } catch (NumberFormatException e) {
                        // Gestisci l'eccezione se la stringa non è un numero valido
                    }

                    try {
                        prezzoInt = Integer.parseInt(prezzo);
                    } catch (NumberFormatException e) {
                        // Gestisci l'eccezione se la stringa non è un numero valido
                    }

                    String immagine;
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
                    } catch (Exception e) {
                       immagine = null;
                    }

                    String titolo = editTextTitolo.getText().toString();
                    String descrizione = editTextDescrizione.getText().toString();
                    String sottocategoria = sottocategoriaSpinner.getSelectedItem().toString();
                    String categoria = categoriaSpinner.getSelectedItem().toString();

                    AstaribassoModel asta = new AstaribassoModel(true, titolo, categoria, sottocategoria, descrizione, email, immagine, importoInt, prezzominInt , prezzoInt , timerInt , prezzoInt);
                    createPresenter.creaAstaribasso2(asta);
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
        String messageString = "Cliccato 'indietro' in 47vendi";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        runOnUiThread(() -> {
            LayoutInflater inflater = LayoutInflater.from(Activity47VendiAR.this);
            View customView = inflater.inflate(R.layout.activity_49_popup5, null);

            TextView btnConferma = customView.findViewById(R.id.buttonAcquista);
            TextView btnAnnulla = customView.findViewById(R.id.buttonVendi);

            function.setTouchListenerForAnimation(btnAnnulla, this);
            function.setTouchListenerForAnimation(btnConferma, this);

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity47VendiAR.this);
            builder.setCustomTitle(customView)
                    .setCancelable(false);

            android.app.AlertDialog dialog = builder.create();
            if (!isFinishing() && dialog != null && !dialog.isShowing()) {
                dialog.show();

                // Gestione del clic sul bottone "Conferma"
                btnConferma.setOnClickListener(v -> {
                    // Chiudi il popup e l'activity corrente
                    dialog.dismiss();
                    Activity47VendiAR.this.finish();
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

    @Override
    public void onCreateSuccess(MessageResponse body) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "47");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "crea asta ribasso");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        Toast.makeText(Activity47VendiAR.this, body.getMessage(), Toast.LENGTH_SHORT).show();
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    public void onCreateFailure(MessageResponse errore_di_registrazione) {
        Toast.makeText(Activity47VendiAR.this, errore_di_registrazione.getMessage(),
                Toast.LENGTH_SHORT).show();
    }
}

