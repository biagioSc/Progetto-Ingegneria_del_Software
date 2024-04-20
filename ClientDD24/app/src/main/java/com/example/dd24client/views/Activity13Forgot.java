package com.example.dd24client.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import androidx.appcompat.app.AppCompatActivity;
import com.example.dd24client.model.UtenteModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.network.RetrofitClient;
import com.example.dd24client.presenter.Presenter11Forgot;
import com.example.dd24client.R;
import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.utils.UtilsFunction;
import com.google.firebase.analytics.FirebaseAnalytics;

public class Activity13Forgot extends AppCompatActivity implements Activity13ForgotView {
    private UtilsFunction function;
    private Animation shakeAnimation;
    private EditText passwordEditText, confermaPasswordEditText;
    private TextView reimpostaPasswordButton;
    private Presenter11Forgot forgotPasswordPresenter;
    private String email,password;
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private boolean isPasswordVisible = false;
    private ImageView passwordToggle1, passwordToggle2, check1, check2, indietroImageView;
    private static final String passRegex = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$";

    @Override
    protected void onCreate(Bundle savedIstanceState){
        super.onCreate(savedIstanceState);
        setContentView(R.layout.activity_13_recpassword3);
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity13Forgot", null);

        setComponents();
        setListener();
        setExtraParameters();
    }

    private void setComponents(){
        function = new UtilsFunction();

        passwordEditText = findViewById(R.id.editTextPassword);
        confermaPasswordEditText = findViewById(R.id.editTextPassword2);
        reimpostaPasswordButton = findViewById(R.id.reimpostaPasswordButton);
        passwordToggle1 = findViewById(R.id.passwordToggle1);
        passwordToggle2 = findViewById(R.id.passwordToggle2);
        indietroImageView = findViewById(R.id.indietroImageView);
        check1 = findViewById(R.id.passwordToggle);
        check2 = findViewById(R.id.passwordToggle3);
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.button_animation);

    }

    private void setListener() {
        function.setTouchListenerForAnimation(reimpostaPasswordButton, this);

        reimpostaPasswordButton.setOnClickListener(v -> onResetPasswordClicked());

        indietroImageView.setOnClickListener(v -> {
            String messageString = "Cliccato 'indietro' in 13forgot";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(this, Activity06Login.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String testoInserito = s.toString();

                // Questo metodo viene invocato prima che il testo venga cambiato.
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check1.setImageResource(R.drawable.icon_basiccheck);
                    passwordEditText.setTextColor(Color.parseColor("#000000"));

                } else if(testoInserito.matches(passRegex)){
                    check1.setImageResource(R.drawable.icon_okcheck);
                    passwordEditText.setTextColor(Color.parseColor("#000000"));

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check1.setImageResource(R.drawable.icon_nocheck);
                    passwordEditText.setTextColor(Color.parseColor("#000000"));
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String testoInserito = s.toString();

                // Questo metodo viene invocato prima che il testo venga cambiato.
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check1.setImageResource(R.drawable.icon_basiccheck);
                    passwordEditText.setTextColor(Color.parseColor("#000000"));

                } else if(testoInserito.matches(passRegex)){
                    check1.setImageResource(R.drawable.icon_okcheck);
                    passwordEditText.setTextColor(Color.parseColor("#000000"));

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check1.setImageResource(R.drawable.icon_nocheck);
                    passwordEditText.setTextColor(Color.parseColor("#000000"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Questo metodo viene invocato subito dopo che il testo è stato cambiato.

                String testoInserito = s.toString();

                // Questo metodo viene invocato prima che il testo venga cambiato.
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check1.setImageResource(R.drawable.icon_basiccheck);
                    passwordEditText.setTextColor(Color.parseColor("#000000"));

                } else if(testoInserito.matches(passRegex)){
                    check1.setImageResource(R.drawable.icon_okcheck);
                    passwordEditText.setTextColor(Color.parseColor("#000000"));

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check1.setImageResource(R.drawable.icon_nocheck);
                    passwordEditText.setTextColor(Color.parseColor("#000000"));
                }
            }
        });

        confermaPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String pass1 = String.valueOf(passwordEditText.getText());
                String pass2 = String.valueOf(confermaPasswordEditText.getText());

                // Questo metodo viene invocato prima che il testo venga cambiato.
                if (pass1.equals(pass2) && pass1.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check2.setImageResource(R.drawable.icon_basiccheck);
                    confermaPasswordEditText.setTextColor(Color.parseColor("#000000"));

                } else if(pass1.equals(pass2)){
                    check2.setImageResource(R.drawable.icon_okcheck);
                    confermaPasswordEditText.setTextColor(Color.parseColor("#000000"));

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check2.setImageResource(R.drawable.icon_nocheck);
                    confermaPasswordEditText.setTextColor(Color.parseColor("#000000"));

                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pass1 = String.valueOf(passwordEditText.getText());
                String pass2 = String.valueOf(confermaPasswordEditText.getText());

                // Questo metodo viene invocato mentre il testo sta cambiando.
                if (pass1.equals(pass2) && pass1.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check2.setImageResource(R.drawable.icon_basiccheck);
                    confermaPasswordEditText.setTextColor(Color.parseColor("#000000"));

                } else if(pass1.equals(pass2)){
                    check2.setImageResource(R.drawable.icon_okcheck);
                    confermaPasswordEditText.setTextColor(Color.parseColor("#000000"));

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check2.setImageResource(R.drawable.icon_nocheck);
                    confermaPasswordEditText.setTextColor(Color.parseColor("#000000"));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String pass1 = String.valueOf(passwordEditText.getText());
                String pass2 = String.valueOf(confermaPasswordEditText.getText());

                // Questo metodo viene invocato subito dopo che il testo è stato cambiato.
                if (pass1.equals(pass2) && pass1.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check2.setImageResource(R.drawable.icon_basiccheck);
                    confermaPasswordEditText.setTextColor(Color.parseColor("#000000"));

                } else if(pass1.equals(pass2)){
                    check2.setImageResource(R.drawable.icon_okcheck);
                    confermaPasswordEditText.setTextColor(Color.parseColor("#000000"));

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check2.setImageResource(R.drawable.icon_nocheck);
                    confermaPasswordEditText.setTextColor(Color.parseColor("#000000"));

                }
            }
        });
    }

    private void setExtraParameters() {
        forgotPasswordPresenter = new Presenter11Forgot(this,apiService);
        Intent intentRicevuto = getIntent();
        email = intentRicevuto.getStringExtra("email");
    }

    private boolean isValidPassword(String password, String confermaPassword){
        return password.equals(confermaPassword) && !password.isEmpty();
    }

    private void onResetPasswordClicked() {
        String messageString = "Cliccato 'reimposta password' in 13forgot";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        reimpostaPasswordButton.setClickable(false);
        password = passwordEditText.getText().toString();
        String confermaPassword = confermaPasswordEditText.getText().toString();
        if(isValidPassword(password,confermaPassword)){
            UtenteModel utente = new UtenteModel(email,password);
            forgotPasswordPresenter.resetPassword(utente);
            reimpostaPasswordButton.setClickable(false);

        }else{
            confermaPasswordEditText.startAnimation(shakeAnimation);
            reimpostaPasswordButton.setClickable(true);
            Toast.makeText(Activity13Forgot.this, "Password non corrispondente!",
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onResetPasswordFailure(MessageResponse errore_password) {
        Toast.makeText(Activity13Forgot.this, errore_password.getMessage(),
                Toast.LENGTH_SHORT).show();
        reimpostaPasswordButton.setClickable(true);
        passwordEditText.startAnimation(shakeAnimation);
        confermaPasswordEditText.startAnimation(shakeAnimation);

        reimpostaPasswordButton.setClickable(false);

    }

    @Override
    public void onResetPasswordSuccess(MessageResponse message) {
        Toast.makeText(Activity13Forgot.this, message.getMessage(),
                Toast.LENGTH_SHORT).show();

        // Inserire delay per mostrare messaggio

        Intent intent = new Intent(Activity13Forgot.this, Activity06Login.class);
        intent.putExtra("email",email);
        intent.putExtra("password",password);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
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
