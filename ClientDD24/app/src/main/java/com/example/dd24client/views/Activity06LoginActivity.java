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
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dd24client.network.ApiService;
import com.example.dd24client.network.RetrofitClient;
import com.example.dd24client.presenter.presenter06LoginPresenter;
import com.example.dd24client.R;
import com.example.dd24client.utils.messageResponse;

public class activity06LoginActivity extends AppCompatActivity implements activity06LoginView {
    private Animation buttonAnimation, shakeAnimation;
    private presenter06LoginPresenter presenter;
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

        setComponents();
        setListener();

        autoFillInputs();
    }

    private void setComponents(){
        // Inizializzazione di presenter, views, etc.
        presenter = new presenter06LoginPresenter(this, apiService);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonAccedi);
        infoTextView = findViewById(R.id.textViewInfo);
        forgotPasswordTextView = findViewById(R.id.textViewForgotPassword);
        passwordToggle = findViewById(R.id.passwordToggle);
        switchRicordami = findViewById(R.id.checkBoxRicordami);
        indietroImageView = findViewById(R.id.indietroImageView);
        buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.button_animation);
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake_error);

    }


    private void setListener() {
        setTouchListenerForAnimation(loginButton);
        setTouchListenerForAnimation(passwordToggle);
        setTouchListenerForAnimation(forgotPasswordTextView);
        setTouchListenerForAnimation(passwordToggle);

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

            Intent intent = new Intent(this, activity05WelcomeActivity.class);
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
            Intent intent = new Intent(activity06LoginActivity.this, activity11ForgotPasswordActivity1.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

    }

    // Funzione di autoFill per ritorno da registrazione o reset password
    private void autoFillInputs(){
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
    public void onLoginSuccess(messageResponse userResponse) {
        boolean isRicordamiChecked = switchRicordami.isChecked();
        savePreferences(emailEditText.getText().toString(),
                passwordEditText.getText().toString(),
                isRicordamiChecked);

        Intent intent = new Intent(this, activity15HomeAcquirente.class);
        intent.putExtra("email",emailEditText.getText().toString());
        intent.putExtra("password",passwordEditText.getText().toString());
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    public void onLoginFailure(messageResponse message) {
        String infoMessage = "Inserisci Email e Password";
        String errorLogin = "Credenziali non valide";

        emailEditText.startAnimation(shakeAnimation);
        passwordEditText.startAnimation(shakeAnimation);

        infoTextView.setText(errorLogin);
        infoTextView.setTextColor(Color.parseColor("#FF5959"));
        new Handler().postDelayed(() -> {
            infoTextView.setText(infoMessage);
            infoTextView.setTextColor(Color.parseColor("#7F7F82"));

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

    private void savePreferences(String username, String password, boolean isRicordamiChecked) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (isRicordamiChecked) {
            editor.putString("Username", username);
            editor.putString("Password", password);
            editor.putBoolean("Ricordami", true);
        } else {
            editor.remove("Username");
            editor.remove("Password");
            editor.putBoolean("Ricordami", false);
        }

        editor.apply();
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
