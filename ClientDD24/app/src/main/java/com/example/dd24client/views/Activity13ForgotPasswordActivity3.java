package com.example.dd24client.views;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dd24client.model.utenteModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.network.RetrofitClient;
import com.example.dd24client.presenter.presenter11ForgotPasswordPresenter;
import com.example.dd24client.R;
import com.example.dd24client.utils.messageResponse;

public class activity13ForgotPasswordActivity3 extends AppCompatActivity implements activity13ForgotPasswordView3 {
    private Animation buttonAnimation, shakeAnimation;
    private EditText passwordEditText, confermaPasswordEditText;
    private TextView reimpostaPasswordButton;
    private presenter11ForgotPasswordPresenter forgotPasswordPresenter;
    private String email,password;
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private boolean isPasswordVisible = false;
    private ImageView passwordToggle1, passwordToggle2, check1, check2, indietroImageView;
    private static final String passwordRegex = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$";

    @Override
    protected void onCreate(Bundle savedIstanceState){
        super.onCreate(savedIstanceState);
        setContentView(R.layout.activity_13_recpassword3);

        setComponents();
        setListener();
        setExtraParameters();
    }

    private void setComponents(){
        buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.button_animation);
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
        setTouchListenerForAnimation(reimpostaPasswordButton);

        reimpostaPasswordButton.setOnClickListener(v -> onResetPasswordClicked());

        indietroImageView.setOnClickListener(v -> {
            Intent intent = new Intent(this, activity06LoginActivity.class);
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

                } else if(testoInserito.matches(passwordRegex)){
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

                } else if(testoInserito.matches(passwordRegex)){
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

                } else if(testoInserito.matches(passwordRegex)){
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
        forgotPasswordPresenter = new presenter11ForgotPasswordPresenter(this,apiService);
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

    private boolean isValidPassword(String password, String confermaPassword){
        return password.equals(confermaPassword) && !password.isEmpty();
    }
    @Override
    public void onResetPasswordClicked() {
        reimpostaPasswordButton.setClickable(false);
        password = passwordEditText.getText().toString();
        String confermaPassword = confermaPasswordEditText.getText().toString();
        if(isValidPassword(password,confermaPassword)){
            utenteModel utente = new utenteModel(email,password);
            forgotPasswordPresenter.resetPassword(utente);
        }else{
            passwordEditText.startAnimation(shakeAnimation);
            confermaPasswordEditText.startAnimation(shakeAnimation);
            reimpostaPasswordButton.setClickable(true);
            Toast.makeText(activity13ForgotPasswordActivity3.this, "Password non valida!",
                    Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onResetPasswordFailure(messageResponse errore_password) {
        Toast.makeText(activity13ForgotPasswordActivity3.this, errore_password.getMessage(),
                Toast.LENGTH_SHORT).show();
        reimpostaPasswordButton.setClickable(true);
        passwordEditText.startAnimation(shakeAnimation);
        confermaPasswordEditText.startAnimation(shakeAnimation);
    }

    @Override
    public void onResetPasswordSuccess(messageResponse message) {
        Toast.makeText(activity13ForgotPasswordActivity3.this, message.getMessage(),
                Toast.LENGTH_SHORT).show();

        // Inserire delay per mostrare messaggio

        Intent intent = new Intent(activity13ForgotPasswordActivity3.this, activity06LoginActivity.class);
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
