package com.example.dd24client.views;

import android.Manifest;
import android.app.Activity;
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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.dd24client.R;
import com.example.dd24client.utils.UtilsFunction;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Activity37CreaAR extends Activity {
    private UtilsFunction function;
    private TextView continuaButton, charCountTextView, charCountTitoloTextView, button_upload, textViewError;
    private ImageButton titoloCheck, descrCheck;
    private EditText editTextTitolo, editTextDescrizione;
    private Spinner categoriaSpinner, sottocategoriaSpinner;
    private ImageView indietroImageView, fotoImageView;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private Uri uriImage;
    private JSONObject datiCategorie;
    private Boolean titoloStato=false, descrStato=false, categoriaStato=false, sottocategoriaStato=false, fotoStato=false;
    private String email="", password="", titolo="", descrizione="", categoria="", sottocategoria="", imageConvertToSave="", importo="", prezzomin="", timer="", prezzo="";
    private Animation shakeAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_37_creaar); // Sostituisci con il tuo file layout

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.darkOrange));

        setComponents();
        setListener();
        setExtraParameters();

    }

    private void setComponents() {
        function = new UtilsFunction();

        continuaButton = findViewById(R.id.continuaButton);
        titoloCheck = findViewById(R.id.passwordToggle);
        descrCheck = findViewById(R.id.biografiaToggle);
        editTextTitolo = findViewById(R.id.editTextTitolo);
        editTextDescrizione = findViewById(R.id.editTextDescrizione);
        categoriaSpinner = findViewById(R.id.categoriaSpinner);
        sottocategoriaSpinner = findViewById(R.id.sottocategoriaSpinner);
        indietroImageView = findViewById(R.id.indietroImageView);
        charCountTextView = findViewById(R.id.charCountTextView);
        charCountTitoloTextView = findViewById(R.id.charCountTitoloTextView);
        fotoImageView = findViewById(R.id.fotoImageView);
        button_upload = findViewById(R.id.button_upload);
        textViewError = findViewById(R.id.textInfoError);
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

    }

    private void setExtraParameters() {
        Intent intentRicevuto = getIntent();

        String caller = intentRicevuto.getStringExtra("caller");

        datiCategorie = function.caricaDatiDaJson(this, "categorie_e_sottocategorie.json");

        setupcategoriaSpinner();

        if (caller != null) {
            if (caller.equals("34")) {
                email = intentRicevuto.getStringExtra("email");
                password = intentRicevuto.getStringExtra("password");

            } else if (caller.equals("38")) {
                email = intentRicevuto.getStringExtra("email");
                password = intentRicevuto.getStringExtra("password");

                titolo = intentRicevuto.getStringExtra("titolo");
                descrizione = intentRicevuto.getStringExtra("descrizione");
                categoria = intentRicevuto.getStringExtra("categoria");
                sottocategoria = intentRicevuto.getStringExtra("sottocategoria");
                String uriImageString = intentRicevuto.getStringExtra("image");
                imageConvertToSave = intentRicevuto.getStringExtra("imageToSave");
                importo = intentRicevuto.getStringExtra("importo");
                prezzomin = intentRicevuto.getStringExtra("prezzomin");
                timer = intentRicevuto.getStringExtra("timer");
                prezzo = intentRicevuto.getStringExtra("prezzo");

                editTextTitolo.setText(titolo);
                editTextDescrizione.setText(descrizione);

                if (uriImageString != null && !uriImageString.isEmpty()) {
                    uriImage = Uri.parse(uriImageString);
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

                    fotoStato = true;
                } else {
                    // Gestisci la situazione in cui la foto è nulla, ad esempio impostando un'immagine predefinita
                    fotoImageView.setImageResource(R.drawable.button_upload);
                    fotoImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

                }

                // Ottieni l'Adapter dello Spinner
                Adapter adapter = categoriaSpinner.getAdapter();
                int count = adapter.getCount();

                int indicePerSelezione = -1;
                for (int i = 0; i < count; i++) {
                    if (categoria.equals(adapter.getItem(i).toString())) {
                        indicePerSelezione = i;
                        break;
                    }
                }

                if (indicePerSelezione != -1) {
                    categoriaSpinner.setSelection(indicePerSelezione);
                    // Aggiorna le città basandoti sulla nazionalità selezionata.

                    // Utilizza un Handler per ritardare l'impostazione della selezione della città.
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        Adapter adapter2 = sottocategoriaSpinner.getAdapter();
                        int count2 = adapter2.getCount();
                        int indicePerSelezione2 = -1;
                        for (int i = 0; i < count2; i++) {
                            if (sottocategoria.equals(adapter2.getItem(i).toString())) {
                                indicePerSelezione2 = i;
                                break;
                            }
                        }
                        if (indicePerSelezione2 != -1) {
                            sottocategoriaSpinner.setSelection(indicePerSelezione2);
                        }
                    }, 100); // Ritardo di 100ms (aggiusta il tempo secondo necessità)
                }
            }
        }


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

    private void continuaFunction(){
        String messageString = "Cliccato 'continua' in 37crea";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

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
        if(!fotoStato){
            continua = false;
        }

        if(continua) {
            Intent intent = new Intent(Activity37CreaAR.this, Activity38CreaAR.class);
            intent.putExtra("titolo", editTextTitolo.getText().toString());
            intent.putExtra("descrizione", editTextDescrizione.getText().toString());
            intent.putExtra("sottocategoria", sottocategoriaSpinner.getSelectedItem().toString());
            intent.putExtra("categoria", categoriaSpinner.getSelectedItem().toString());
            if (uriImage != null) {
                intent.putExtra("image", uriImage.toString());
                // Fai qualcosa con la stringa dell'URI
            } else {
                intent.putExtra("image", "");
            }
            intent.putExtra("imageToSave", imageConvertToSave);
            intent.putExtra("importo", importo);
            intent.putExtra("prezzomin", prezzomin);
            intent.putExtra("timer", timer);
            intent.putExtra("prezzo", prezzo);
            intent.putExtra("email", email);
            intent.putExtra("password", password);

            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }else {
            textViewError.setText("Compila tutti i campi per proseguire");

            // Crea un Handler e posticipa l'esecuzione di un Runnable che resetta il testo.
            new Handler(Looper.getMainLooper()).postDelayed(() -> textViewError.setText(""), 8000);  // 8000 millisecondi = 8 secondi
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
        } catch (JSONException e) {
            sottocategoria.add("-");
        }
        ArrayAdapter<String> adaptersottocategoria = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sottocategoria);
        adaptersottocategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sottocategoriaSpinner.setAdapter(adaptersottocategoria);
    }

    public void showPopupMessageIndietro() {
        String messageString = "Cliccato 'indietro' in 37crea";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        runOnUiThread(() -> {
            LayoutInflater inflater = LayoutInflater.from(Activity37CreaAR.this);
            View customView = inflater.inflate(R.layout.activity_49_popup5, null);

            TextView btnConferma = customView.findViewById(R.id.buttonAcquista);
            TextView btnAnnulla = customView.findViewById(R.id.buttonVendi);

            function.setTouchListenerForAnimation(btnAnnulla, this);
            function.setTouchListenerForAnimation(btnConferma, this);

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity37CreaAR.this);
            builder.setCustomTitle(customView)
                    .setCancelable(false);

            android.app.AlertDialog dialog = builder.create();
            if (!isFinishing() && dialog != null && !dialog.isShowing()) {
                dialog.show();

                // Gestione del clic sul bottone "Conferma"
                btnConferma.setOnClickListener(v -> {
                    // Chiudi il popup e l'activity corrente
                    dialog.dismiss();
                    Activity37CreaAR.this.finish();
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

