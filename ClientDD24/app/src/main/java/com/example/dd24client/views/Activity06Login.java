package com.example.dd24client.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.dd24client.network.ApiService;
import com.example.dd24client.network.RetrofitClient;
import com.example.dd24client.presenter.Presenter06Login;
import com.example.dd24client.R;
import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.utils.UtilsFunction;
import com.google.firebase.analytics.FirebaseAnalytics;

public class Activity06Login extends AppCompatActivity implements Activity06LoginView {
    private FirebaseAnalytics mFirebaseAnalytics;
    private UtilsFunction function;
    private Animation shakeAnimation;
    private Presenter06Login presenter;
    private EditText emailEditText, passwordEditText;
    private TextView loginButton, infoTextView, forgotPasswordTextView;
    private boolean isPasswordVisible = false;
    private ImageButton passwordToggle;
    private ImageView indietroImageView;
    private Switch switchRicordami;
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_06_login);
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity06Login", null);

        setComponents();
        setListener();
        setExtraParameters();
    }

    private void setComponents(){
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        function = new UtilsFunction();

        presenter = new Presenter06Login(this, apiService);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonAccedi);
        infoTextView = findViewById(R.id.textViewInfo);
        forgotPasswordTextView = findViewById(R.id.textViewForgotPassword);
        passwordToggle = findViewById(R.id.passwordToggle);
        switchRicordami = findViewById(R.id.checkBoxRicordami);
        indietroImageView = findViewById(R.id.indietroImageView);
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake_error);

    }

    private void setListener() {
        function.setTouchListenerForAnimation(loginButton, this);
        function.setTouchListenerForAnimation(passwordToggle, this);
        function.setTouchListenerForAnimation(forgotPasswordTextView, this);
        function.setTouchListenerForAnimation(passwordToggle, this);

        loginButton.setOnClickListener(v -> onLoginClicked());

        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Questo metodo viene invocato prima che il testo venga cambiato.
                emailEditText.setTextColor(Color.parseColor("#000000"));

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Questo metodo viene invocato mentre il testo sta cambiando.
                emailEditText.setTextColor(Color.parseColor("#000000"));

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Questo metodo viene invocato subito dopo che il testo è stato cambiato.
                emailEditText.setTextColor(Color.parseColor("#000000"));

            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Questo metodo viene invocato prima che il testo venga cambiato.
                passwordEditText.setTextColor(Color.parseColor("#000000"));

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Questo metodo viene invocato mentre il testo sta cambiando.
                passwordEditText.setTextColor(Color.parseColor("#000000"));

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Questo metodo viene invocato subito dopo che il testo è stato cambiato.
                passwordEditText.setTextColor(Color.parseColor("#000000"));

            }
        });

        indietroImageView.setOnClickListener(v -> {
            String messageString = "Cliccato 'indietro' in 06login";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(this, Activity05Welcome.class);
            intent.putExtra("nome","");
            intent.putExtra("cognome","");
            intent.putExtra("nazionalita","");
            intent.putExtra("citta","");
            intent.putExtra("biografia","");
            intent.putExtra("linkWeb","");
            intent.putExtra("socialLinks", "");
            intent.putExtra("email", "");
            intent.putExtra("password", "");
            intent.putExtra("confermaPassword", "");
            intent.putExtra("terms", "");
            intent.putExtra("image", "");

            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

        forgotPasswordTextView.setOnClickListener(v -> {
            String messageString = "Cliccato 'password dimenticata' in 06login";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(Activity06Login.this, Activity11Forgot.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

    }

    private void setExtraParameters(){
        Intent intentRicevuto = getIntent();
        if(intentRicevuto != null){
            if(intentRicevuto.hasExtra("email") && intentRicevuto.hasExtra("password")){
                String email = intentRicevuto.getStringExtra("email");
                String password = intentRicevuto.getStringExtra("password");
                emailEditText.setText(email);
                passwordEditText.setText(password);
            }
        }
    }

    public void onLoginClicked() {
        String messageString = "Cliccato 'accedi' in 06login";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if(email.isEmpty() && password.isEmpty()) {
            emailEditText.startAnimation(shakeAnimation);
            passwordEditText.startAnimation(shakeAnimation);
        } else if(email.isEmpty()) {
            emailEditText.startAnimation(shakeAnimation);
        }else if(password.isEmpty()){
            passwordEditText.startAnimation(shakeAnimation);
        }else{
            presenter.loginUser(email, password);
        }

    }

    @Override
    public void onLoginSuccess(MessageResponse userResponse) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "06");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Login");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        boolean isRicordamiChecked = switchRicordami.isChecked();
        savePreferences(emailEditText.getText().toString(),
                passwordEditText.getText().toString(),
                isRicordamiChecked);

        Intent intent = new Intent(this, Activity15HomeAcquirente.class);
        intent.putExtra("email",emailEditText.getText().toString());
        intent.putExtra("password",passwordEditText.getText().toString());
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    public void onLoginFailure(MessageResponse message) {
        String infoMessage = "Inserisci Email e Password";
        String errorLogin = "Credenziali non valide";

        emailEditText.startAnimation(shakeAnimation);
        passwordEditText.startAnimation(shakeAnimation);

        infoTextView.setText(errorLogin);
        infoTextView.setTextColor(ContextCompat.getColor(this, R.color.red));
        new Handler().postDelayed(() -> {
            infoTextView.setText(infoMessage);
            infoTextView.setTextColor(ContextCompat.getColor(this, R.color.grey));

        }, 5000);
    }

    public void togglePasswordVisibility(View view) {
        if (isPasswordVisible) {
            passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
            passwordToggle.setImageResource(R.drawable.icon_hide_password);
        } else {
            passwordEditText.setTransformationMethod(null);
            passwordToggle.setImageResource(R.drawable.icon_show_password);
        }
        isPasswordVisible = !isPasswordVisible;
        passwordEditText.setSelection(passwordEditText.getText().length());
    }

    public void savePreferences(String email, String password, boolean isRicordamiChecked) {
        System.out.println(email + " " + password);
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            return;
        }

        if (isRicordamiChecked) {
            editor.putString("email", email);
            editor.putString("Password", password);
            editor.putBoolean("Ricordami", true);
        } else {
            editor.remove("email");
            editor.remove("Password");
            editor.putBoolean("Ricordami", false);
        }

        editor.apply();
    }
}
