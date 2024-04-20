package com.example.dd24client.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.dd24client.model.astaribassoModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.network.RetrofitClient;
import com.example.dd24client.presenter.presenter38CreaAstaRibassoPresenter;
import com.example.dd24client.R;
import com.example.dd24client.utils.messageResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;


public class activity38CreaARActivity extends AppCompatActivity implements activity38CreaARView {
    private Animation buttonAnimation, shakeAnimation;
    private TextView continuaButton, textViewError;
    private ImageButton check1, check2, check3, check4;
    private EditText editTextImporto, editTextPrezzoMin, editTextTimer, editTextPrezzo;
    private ImageView indietroImageView;
    private Boolean decrStato=false, minStato=false, timerStato=false, prezzoStato=false;
    private String email="", password="", titolo="", descrizione="", categoria="", sottocategoria="", imageConvertToSave="", foto="", importo="", prezzomin="", timer="", prezzo="";
    private presenter38CreaAstaRibassoPresenter createPresenter;
    int prezzoiniziale=-1, decremento=-1, minimo=-1;
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_38_creaar); // Sostituisci con il tuo file layout

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.darkOrange));

        setComponents();
        setListener();

        setExtraParameters();

    }

    private void setComponents() {
        continuaButton = findViewById(R.id.continuaButton);
        editTextImporto = findViewById(R.id.editTextImporto);
        editTextPrezzoMin = findViewById(R.id.editTextPrezzoMin);
        editTextTimer = findViewById(R.id.editTextTimer);
        editTextPrezzo = findViewById(R.id.editTextPrezzo);
        check1 = findViewById(R.id.passwordToggle1);
        check2 = findViewById(R.id.passwordToggle2);
        check3 = findViewById(R.id.passwordToggle3);
        check4 = findViewById(R.id.passwordToggle4);
        indietroImageView = findViewById(R.id.indietroImageView);
        textViewError = findViewById(R.id.textInfoError);

        buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.button_animation);
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake_error);

        createPresenter = new presenter38CreaAstaRibassoPresenter(this,apiService);

    }

    private void setListener() {
        setTouchListenerForAnimation(indietroImageView);
        setTouchListenerForAnimation(continuaButton);

        continuaButton.setOnClickListener(v -> continuaFunction());
        indietroImageView.setOnClickListener(v -> indietroFunction());

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
                        String numericPart = text.replaceAll("\\D", "");
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
                        if(prezzoiniziale>0) {
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

    }

    private void setExtraParameters() {
        Intent intentRicevuto = getIntent();

        email = intentRicevuto.getStringExtra("email");
        password = intentRicevuto.getStringExtra("password");

        titolo = intentRicevuto.getStringExtra("titolo");
        descrizione = intentRicevuto.getStringExtra("descrizione");
        categoria = intentRicevuto.getStringExtra("categoria");
        sottocategoria = intentRicevuto.getStringExtra("sottocategoria");
        foto = intentRicevuto.getStringExtra("image");
        imageConvertToSave = intentRicevuto.getStringExtra("imageToSave");
        importo = intentRicevuto.getStringExtra("importo");
        prezzomin = intentRicevuto.getStringExtra("prezzomin");
        timer = intentRicevuto.getStringExtra("timer");
        prezzo = intentRicevuto.getStringExtra("prezzo");

        importo = intentRicevuto.getStringExtra("importo");
        prezzomin = intentRicevuto.getStringExtra("prezzomin");
        timer = intentRicevuto.getStringExtra("timer");
        prezzo = intentRicevuto.getStringExtra("prezzo");

        if (importo != null && !importo.isEmpty()) {
            editTextImporto.setText(importo);
        }
        if (prezzomin != null && !prezzomin.isEmpty()) {
            editTextPrezzoMin.setText(prezzomin);
        }
        if (timer != null && !timer.isEmpty()) {
            editTextTimer.setText(timer);
        }
        if (prezzo != null && !prezzo.isEmpty()) {
            editTextPrezzo.setText(prezzo);
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

    private void continuaFunction(){
        boolean continua = true;

        int importoInt = 0;
        int prezzominInt = 0;
        int prezzoInt = 0;

        importo = editTextImporto.getText().toString().replaceAll("\\D", "");
        prezzomin = editTextPrezzoMin.getText().toString().replaceAll("\\D", "");
        timer = editTextTimer.getText().toString().replaceAll("\\D", "");
        prezzo = editTextPrezzo.getText().toString().replaceAll("\\D", "");

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
        }

        if (continua) {
            showPopupMessage();
        } else {
            String message = "Compila tutti i campi per proseguire";
            textViewError.setText(message);

            // Crea un Handler e posticipa l'esecuzione di un Runnable che resetta il testo.
            new Handler(Looper.getMainLooper()).postDelayed(() -> textViewError.setText(""), 8000);  // 8000 millisecondi = 8 secondi
        }

    }

    public void showPopupMessage() {
        runOnUiThread(() -> {
            LayoutInflater inflater = LayoutInflater.from(activity38CreaARActivity.this);
            View customView = inflater.inflate(R.layout.activity_49_popup4, null);

            TextView btnConferma = customView.findViewById(R.id.buttonAcquista);
            TextView btnAnnulla = customView.findViewById(R.id.buttonVendi);

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity38CreaARActivity.this);
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

                    importo = editTextImporto.getText().toString().replaceAll("\\D", "");
                    prezzomin = editTextPrezzoMin.getText().toString().replaceAll("\\D", "");
                    timer = editTextTimer.getText().toString().replaceAll("\\D", "");
                    prezzo = editTextPrezzo.getText().toString().replaceAll("\\D", "");

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

                    astaribassoModel asta = new astaribassoModel(true, titolo, categoria, sottocategoria, descrizione, email, immagine, importoInt, prezzominInt , prezzoInt , timerInt , prezzoInt);
                    createPresenter.creaAstaribasso1(asta);
                });

                // Gestione del clic sul bottone "Annulla"
                btnAnnulla.setOnClickListener(v -> {
                    // Chiudi solo il popup
                    dialog.dismiss();
                });
            }
        });
    }

    public void indietroFunction(){
        importo = String.valueOf(editTextImporto.getText());
        prezzomin = String.valueOf(editTextPrezzoMin.getText());
        timer = String.valueOf(editTextTimer.getText());
        prezzo = String.valueOf(editTextPrezzo.getText());

        if(!decrStato){
            importo="";
        }
        if(!minStato){
            prezzomin="";
        }
        if(!timerStato){
            timer="";
        }
        if(!prezzoStato){
            prezzo="";
        }

        Intent intent = getIntent1();

        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();

    }

    private Intent getIntent1() {
        Intent intent = new Intent(activity38CreaARActivity.this, activity37CreaARActivity.class);
        intent.putExtra("titolo", titolo);
        intent.putExtra("descrizione", descrizione);
        intent.putExtra("sottocategoria", sottocategoria);
        intent.putExtra("categoria", categoria);
        intent.putExtra("image", foto);
        intent.putExtra("imageToSave", imageConvertToSave);
        intent.putExtra("importo", importo);
        intent.putExtra("prezzomin", prezzomin);
        intent.putExtra("timer", timer);
        intent.putExtra("prezzo", prezzo);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        intent.putExtra("caller", "38");
        return intent;
    }

    @Override
    public void onCreateSuccess(messageResponse body) {
        Toast.makeText(activity38CreaARActivity.this, body.getMessage(), Toast.LENGTH_SHORT).show();
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    public void onCreateFailure(messageResponse errore_di_registrazione) {
        Toast.makeText(activity38CreaARActivity.this, errore_di_registrazione.getMessage(),
                Toast.LENGTH_SHORT).show();
    }
}

