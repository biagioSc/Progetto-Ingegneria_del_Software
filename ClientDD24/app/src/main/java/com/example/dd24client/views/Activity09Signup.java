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
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dd24client.model.UtenteModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.network.RetrofitClient;
import com.example.dd24client.presenter.Presenter07Signup;
import com.example.dd24client.R;
import com.example.dd24client.utils.MessageResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;
import com.example.dd24client.utils.UtilsFunction;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;

public class Activity09Signup extends AppCompatActivity implements Activity09SignupView {
    private FirebaseAnalytics mFirebaseAnalytics;
    private UtilsFunction function;
    private ImageView indietroImageView;
    private EditText emailEditText, passwordEditText, confermaPasswordEditText;
    private ImageView terminiCondizioniCheckBox, check1, check2, check3, passwordToggle1, passwordToggle2;
    private boolean isPasswordVisible = false;
    private TextView registratiButton;
    private TextView terminiCondizioniTextView, textViewError;
    private String nome="", cognome="", nazionalita="", citta="", biografia="", socialLinks="", linkWeb="", email="", password="", confermaPassword="", foto="", imageConvertToSave="";
    boolean terms=false;
    private boolean statoEmail=true, statoPass=true, statoPassRip=true;
    private Presenter07Signup signupPresenter;
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private boolean terminiCondizioniisChecked = false;
    private Animation shakeAnimation;
    private static final String passRegex = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$";
    private static final String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private String tokenGeneral;
    private final String nomeStringa = "nome";
    private final String cognomeStringa = "cognome";
    private final String nazionalitaStringa = "nazionalita";
    private final String cittaStringa = "citta";
    private final String biografiaStringa = "biografia";
    private final String linkWebStringa = "linkWeb";
    private final String socialLinksStringa = "socialLinks";
    private final String emailStringa = "email";
    private final String passwordStringa = "password";
    private final String confermaPasswordStringa = "confermaPassword";
    private final String termsStringa = "terms";
    private final String fotoStringa = "image";
    private final String imageConvertToSaveStringa = "imageToSave";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_09_signup3);
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity09Signup", null);

        setComponents();
        setListener();
        setExtraParameters();

        getFirebaseMessagingToken();

    }

    private void getFirebaseMessagingToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        tokenGeneral = task.getResult();
                    } else {
                        tokenGeneral = "";
                    }
                });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("email", emailEditText.getText().toString());
        outState.putString("password", passwordEditText.getText().toString());
        outState.putString("confermaPassword",confermaPasswordEditText.getText().toString());
        outState.putBoolean("terminiCondizioniCheckBox",terminiCondizioniisChecked);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        emailEditText.setText(savedInstanceState.getString("email"));
        passwordEditText.setText(savedInstanceState.getString("password"));
        confermaPasswordEditText.setText(savedInstanceState.getString("confermaPassword"));
        terminiCondizioniCheckBox.setActivated(savedInstanceState.getBoolean
                ("terminiCondizioniCheckBox"));
    }

    // Funzione per il settaggio dei componenti grafici
    private void setComponents(){
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        function = new UtilsFunction();

        terminiCondizioniTextView = findViewById(R.id.visualizzaTermini);
        indietroImageView = findViewById(R.id.indietroImageView);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        confermaPasswordEditText = findViewById(R.id.editTextPassword2);
        terminiCondizioniCheckBox = findViewById(R.id.checkTermini);
        registratiButton = findViewById(R.id.registratiButton);
        check1 = findViewById(R.id.emailToggle);
        check2 = findViewById(R.id.passwordToggle);
        check3 = findViewById(R.id.passwordToggle3);
        passwordToggle1 = findViewById(R.id.passwordToggle1);
        passwordToggle2 = findViewById(R.id.passwordToggle2);
        textViewError = findViewById(R.id.textInfoError);
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake_error);

        signupPresenter = new Presenter07Signup(this, apiService);

    }

    private void setListener() {

        function.setTouchListenerForAnimation(registratiButton, this);
        function.setTouchListenerForAnimation(terminiCondizioniCheckBox, this);
        function.setTouchListenerForAnimation(terminiCondizioniTextView, this);
        function.setTouchListenerForAnimation(indietroImageView, this);

        terminiCondizioniTextView.setOnClickListener(v -> {
            String messageString = "Cliccato 'terms' in 09signup";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(Activity09Signup.this, Activity14Terms.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

        terminiCondizioniCheckBox.setOnClickListener(v -> {
            String messageString = "Cliccato 'accetta terms' in 09signup";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            if(terminiCondizioniisChecked){
                terminiCondizioniisChecked = false;
                terminiCondizioniCheckBox.setImageResource(R.drawable.icon_basiccheck);
            }else {
                terminiCondizioniisChecked = true;
                terminiCondizioniCheckBox.setImageResource(R.drawable.icon_okcheck);
            }
        });

        registratiButton.setOnClickListener(v -> onSignupClicked());
        indietroImageView.setOnClickListener(v -> {
            String messageString = "Cliccato 'indietro' in 09signup";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(this, Activity08Signup.class);

            email = emailEditText.getText().toString();
            password = passwordEditText.getText().toString();
            confermaPassword = confermaPasswordEditText.getText().toString();
            terms = terminiCondizioniisChecked;

            intent.putExtra(nomeStringa, nome);
            intent.putExtra(cognomeStringa, cognome);
            intent.putExtra(nazionalitaStringa, nazionalita);
            intent.putExtra(cittaStringa, citta);
            intent.putExtra(biografiaStringa, biografia);
            intent.putExtra(linkWebStringa, linkWeb);
            intent.putExtra(socialLinksStringa, socialLinks);
            intent.putExtra(emailStringa, email);
            intent.putExtra(passwordStringa, password);
            intent.putExtra(confermaPasswordStringa, confermaPassword);
            intent.putExtra(termsStringa, terms);
            intent.putExtra(fotoStringa, foto);
            intent.putExtra(imageConvertToSaveStringa, imageConvertToSave);

            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String testoInserito = s.toString();
                emailEditText.setTextColor(Color.parseColor("#000000"));

                // Questo metodo viene invocato prima che il testo venga cambiato.
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check1.setImageResource(R.drawable.icon_basiccheck);
                    statoEmail=false;
                } else if(testoInserito.matches(emailRegex)){
                    check1.setImageResource(R.drawable.icon_okcheck);
                    statoEmail=true;

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check1.setImageResource(R.drawable.icon_nocheck);
                    statoEmail=false;

                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String testoInserito = s.toString();
                emailEditText.setTextColor(Color.parseColor("#000000"));

                // Questo metodo viene invocato mentre il testo sta cambiando.
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check1.setImageResource(R.drawable.icon_basiccheck);
                    statoEmail=false;

                } else if(testoInserito.matches(emailRegex)){
                    check1.setImageResource(R.drawable.icon_okcheck);
                    statoEmail=true;

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check1.setImageResource(R.drawable.icon_nocheck);
                    statoEmail=false;

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Questo metodo viene invocato subito dopo che il testo è stato cambiato.

                String testoInserito = s.toString();
                emailEditText.setTextColor(Color.parseColor("#000000"));

                // Qui puoi inserire il tuo controllo per verificare se il testo inserito è giusto.
                // Ad esempio, supponiamo che il testo sia "giusto" se non è vuoto e se contiene la parola "esempio".
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check1.setImageResource(R.drawable.icon_basiccheck);
                    statoEmail=false;

                } else if(testoInserito.matches(emailRegex)){
                    check1.setImageResource(R.drawable.icon_okcheck);
                    statoEmail=true;

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check1.setImageResource(R.drawable.icon_nocheck);
                    statoEmail=false;

                }
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String testoInserito = s.toString();
                passwordEditText.setTextColor(Color.parseColor("#000000"));

                // Questo metodo viene invocato prima che il testo venga cambiato.
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check2.setImageResource(R.drawable.icon_basiccheck);
                    statoPass=false;

                } else if(testoInserito.matches(passRegex)){
                    check2.setImageResource(R.drawable.icon_okcheck);
                    statoPass=true;

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check2.setImageResource(R.drawable.icon_nocheck);
                    statoPass=false;

                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String testoInserito = s.toString();
                passwordEditText.setTextColor(Color.parseColor("#000000"));

                // Questo metodo viene invocato prima che il testo venga cambiato.
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check2.setImageResource(R.drawable.icon_basiccheck);
                    statoPass=false;

                } else if(testoInserito.matches(passRegex)){
                    check2.setImageResource(R.drawable.icon_okcheck);
                    statoPass=true;

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check2.setImageResource(R.drawable.icon_nocheck);
                    statoPass=false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Questo metodo viene invocato subito dopo che il testo è stato cambiato.

                String testoInserito = s.toString();
                passwordEditText.setTextColor(Color.parseColor("#000000"));

                // Questo metodo viene invocato prima che il testo venga cambiato.
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check2.setImageResource(R.drawable.icon_basiccheck);
                    statoPass=false;

                } else if(testoInserito.matches(passRegex)){
                    check2.setImageResource(R.drawable.icon_okcheck);
                    statoPass=true;

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check2.setImageResource(R.drawable.icon_nocheck);
                    statoPass=false;

                }
            }
        });

        confermaPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String pass1 = String.valueOf(passwordEditText.getText());
                String pass2 = String.valueOf(confermaPasswordEditText.getText());
                confermaPasswordEditText.setTextColor(Color.parseColor("#000000"));

                // Questo metodo viene invocato prima che il testo venga cambiato.
                if (pass1.equals(pass2) && pass1.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check3.setImageResource(R.drawable.icon_basiccheck);
                    statoPassRip=false;

                } else if(pass1.equals(pass2)){
                    check3.setImageResource(R.drawable.icon_okcheck);
                    statoPassRip=true;

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check3.setImageResource(R.drawable.icon_nocheck);
                    statoPassRip=false;

                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pass1 = String.valueOf(passwordEditText.getText());
                String pass2 = String.valueOf(confermaPasswordEditText.getText());
                confermaPasswordEditText.setTextColor(Color.parseColor("#000000"));

                // Questo metodo viene invocato prima che il testo venga cambiato.
                if (pass1.equals(pass2) && pass1.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check3.setImageResource(R.drawable.icon_basiccheck);
                    statoPassRip=false;

                } else if(pass1.equals(pass2)){
                    check3.setImageResource(R.drawable.icon_okcheck);
                    statoPassRip=true;

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check3.setImageResource(R.drawable.icon_nocheck);
                    statoPassRip=false;

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String pass1 = String.valueOf(passwordEditText.getText());
                String pass2 = String.valueOf(confermaPasswordEditText.getText());
                confermaPasswordEditText.setTextColor(Color.parseColor("#000000"));

                // Questo metodo viene invocato prima che il testo venga cambiato.
                if (pass1.equals(pass2) && pass1.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check3.setImageResource(R.drawable.icon_basiccheck);
                    statoPassRip=false;

                } else if(pass1.equals(pass2)){
                    check3.setImageResource(R.drawable.icon_okcheck);
                    statoPassRip=true;

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check3.setImageResource(R.drawable.icon_nocheck);
                    statoPassRip=false;

                }
            }
        });
    }

    // Funzione per il settaggio dei parametri extra delle intent di signup
    private void setExtraParameters(){
        Intent intentRicevuto = getIntent();

        nome = intentRicevuto.getStringExtra(nomeStringa);
        cognome = intentRicevuto.getStringExtra(cognomeStringa);
        nazionalita = intentRicevuto.getStringExtra(nazionalitaStringa);
        citta = intentRicevuto.getStringExtra(cittaStringa);
        biografia = intentRicevuto.getStringExtra(biografiaStringa);
        linkWeb = intentRicevuto.getStringExtra(linkWebStringa);
        socialLinks = intentRicevuto.getStringExtra(socialLinksStringa);
        email = intentRicevuto.getStringExtra(emailStringa);
        password = intentRicevuto.getStringExtra(passwordStringa);
        confermaPassword = intentRicevuto.getStringExtra(confermaPasswordStringa);
        terminiCondizioniisChecked = intentRicevuto.getBooleanExtra(termsStringa, false);
        foto = intentRicevuto.getStringExtra(fotoStringa);
        imageConvertToSave = intentRicevuto.getStringExtra(imageConvertToSaveStringa);

        emailEditText.setText(email);
        passwordEditText.setText(password);
        confermaPasswordEditText.setText(confermaPassword);

        if(!terminiCondizioniisChecked){
            terminiCondizioniCheckBox.setImageResource(R.drawable.icon_basiccheck);
        }else {
            terminiCondizioniCheckBox.setImageResource(R.drawable.icon_okcheck);
        }
    }

    // Funzione di controllo di uguaglianza tra password e confermaPassword + password non vuota
    private boolean checkPassword(String password, String confermaPassword){
        return password.equals(confermaPassword) && !password.isEmpty();
    }


    // Funzione di invio dati di registrazione
    @Override
    public void onSignupClicked() {
        String messageString = "Cliccato 'registra' in 09signup";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        boolean continua=true;
        if(!statoEmail){
            continua=false;
            emailEditText.startAnimation(shakeAnimation);
        }
        if(!statoPass){
            continua=false;
            passwordEditText.startAnimation(shakeAnimation);
        }
        if(!statoPassRip){
            continua=false;
            confermaPasswordEditText.startAnimation(shakeAnimation);
        }
        if(continua){
            String confermaPassword;
            email = emailEditText.getText().toString();
            password = passwordEditText.getText().toString();
            confermaPassword = confermaPasswordEditText.getText().toString();
            if (checkPassword(password, confermaPassword)) {
                if (terminiCondizioniisChecked) {
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

                    registratiButton.setClickable(false);
                    UtenteModel utente = new UtenteModel(email, password, nome, cognome, immagine, nazionalita, citta, biografia, linkWeb, socialLinks, tokenGeneral);
                    signupPresenter.signupUser(utente);
                    registratiButton.setClickable(false);

                } else {
                    Toast.makeText(getApplicationContext(), "Accettare i termini e condizioni!",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Password non corrispondente o non valida!",
                        Toast.LENGTH_SHORT).show();
            }
        }else {
            String messageString2 = "L'utente non ha compilato tutti i campi in 07signup";
            Log.d(logging, messageString2);

            String message = "Compila tutti i campi per proseguire";
            textViewError.setText(message);

            // Crea un Handler e posticipa l'esecuzione di un Runnable che resetta il testo.
            new Handler(Looper.getMainLooper()).postDelayed(() -> textViewError.setText(""), 8000);  // 8000 millisecondi = 8 secondi
        }
    }

    // Funzione di risposta in caso di successo
    @Override
    public void onSignupSuccess(MessageResponse body) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "07");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Signup");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        Toast.makeText(Activity09Signup.this, body.getMessage(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Activity09Signup.this, Activity10Signup.class);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    public void onSignupFailure(MessageResponse errore_di_registrazione) {
        Toast.makeText(Activity09Signup.this, errore_di_registrazione.getMessage(),
                Toast.LENGTH_SHORT).show();
        emailEditText.startAnimation(shakeAnimation);
        passwordEditText.startAnimation(shakeAnimation);
        confermaPasswordEditText.startAnimation(shakeAnimation);
        registratiButton.setClickable(true);

    }

    public void togglePasswordVisibility(View view) {
        if (isPasswordVisible) {
            passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
            passwordToggle1.setImageResource(R.drawable.icon_hide_password);
        } else {
            passwordEditText.setTransformationMethod(null);
            passwordToggle1.setImageResource(R.drawable.icon_show_password);
        }
        isPasswordVisible = !isPasswordVisible;
        passwordEditText.setSelection(passwordEditText.getText().length());
    }

    public void togglePasswordVisibility2(View view) {
        if (isPasswordVisible) {
            confermaPasswordEditText.setTransformationMethod(new PasswordTransformationMethod());
            passwordToggle2.setImageResource(R.drawable.icon_hide_password);
        } else {
            confermaPasswordEditText.setTransformationMethod(null);
            passwordToggle2.setImageResource(R.drawable.icon_show_password);
        }
        isPasswordVisible = !isPasswordVisible;
        confermaPasswordEditText.setSelection(confermaPasswordEditText.getText().length());
    }

}

