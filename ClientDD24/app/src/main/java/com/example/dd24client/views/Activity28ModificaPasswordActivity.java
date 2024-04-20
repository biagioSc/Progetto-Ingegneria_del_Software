package com.example.dd24client.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
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
import com.example.dd24client.presenter.presenter28ModificaPasswordPresenter;
import com.example.dd24client.R;
import com.example.dd24client.utils.messageResponse;

public class activity28ModificaPasswordActivity extends AppCompatActivity implements activity28ModificaPasswordView {
    private ImageView indietroImageView;
    private EditText passwordAttualeEditText, nuovaPasswordEditText, ripetiPasswordEditText;
    private ImageView check1, check2, check3, passwordToggle1, passwordToggle2, passwordToggle3;
    private TextView salvaModificheButton;
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private presenter28ModificaPasswordPresenter modifyPasswordPresenter;
    private Animation buttonAnimation, shakeAnimation;
    private boolean isPasswordVisible = false;
    private String email="", password="";
    private static String passwordRegex = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_28_modifica_password);

        setComponents();
        setListener();
        setExtraParameters();
    }


    // Funzione per il settaggio dei componenti grafici
    private void setComponents(){
        indietroImageView = findViewById(R.id.indietroImageView);
        passwordAttualeEditText = findViewById(R.id.passwordAttualeEditText);
        nuovaPasswordEditText = findViewById(R.id.nuovaPasswordEditText);
        ripetiPasswordEditText = findViewById(R.id.ripetiPasswordEditText);
        salvaModificheButton = findViewById(R.id.salvaModificheButton);
        check1 = findViewById(R.id.passwordToggle);
        check2 = findViewById(R.id.passwordToggle2);
        check3 = findViewById(R.id.passwordToggle3);
        passwordToggle1 = findViewById(R.id.passwordToggleView);
        passwordToggle2 = findViewById(R.id.passwordToggleView2);
        passwordToggle3 = findViewById(R.id.passwordToggleView3);

        buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.button_animation);
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake_error);

        modifyPasswordPresenter = new presenter28ModificaPasswordPresenter(this,apiService);

    }

    private void setListener() {
        setTouchListenerForAnimation(salvaModificheButton);
        setTouchListenerForAnimation(indietroImageView);

        salvaModificheButton.setOnClickListener(v -> onModifyClicked());
        indietroImageView.setOnClickListener(v -> {
            String nuova = String.valueOf(nuovaPasswordEditText.getText());
            String ripeti = String.valueOf(ripetiPasswordEditText.getText());

            if(nuova.isEmpty() && ripeti.isEmpty()){
                finish();
                overridePendingTransition(0, 0);
            }else {
                showPopupMessageIndietro();
            }
        });

        passwordAttualeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String testoInserito = s.toString();
                passwordAttualeEditText.setTextColor(Color.parseColor("#000000"));

                // Questo metodo viene invocato prima che il testo venga cambiato.
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check1.setImageResource(R.drawable.icon_basiccheck);


                } else if(isValidPassword(testoInserito, password)){
                    check1.setImageResource(R.drawable.icon_okcheck);

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check1.setImageResource(R.drawable.icon_nocheck);


                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String testoInserito = s.toString();
                passwordAttualeEditText.setTextColor(Color.parseColor("#000000"));

                // Questo metodo viene invocato prima che il testo venga cambiato.
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check1.setImageResource(R.drawable.icon_basiccheck);


                } else if(isValidPassword(testoInserito, password)){
                    check1.setImageResource(R.drawable.icon_okcheck);

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check1.setImageResource(R.drawable.icon_nocheck);


                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Questo metodo viene invocato subito dopo che il testo è stato cambiato.

                String testoInserito = s.toString();
                passwordAttualeEditText.setTextColor(Color.parseColor("#000000"));

                // Questo metodo viene invocato prima che il testo venga cambiato.
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check1.setImageResource(R.drawable.icon_basiccheck);


                } else if(isValidPassword(testoInserito, password)){
                    check1.setImageResource(R.drawable.icon_okcheck);

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check1.setImageResource(R.drawable.icon_nocheck);


                }
            }
        });

        nuovaPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String testoInserito = s.toString();
                nuovaPasswordEditText.setTextColor(Color.parseColor("#000000"));

                // Questo metodo viene invocato prima che il testo venga cambiato.
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check2.setImageResource(R.drawable.icon_basiccheck);


                } else if(testoInserito.matches(passwordRegex)){
                    check2.setImageResource(R.drawable.icon_okcheck);

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check2.setImageResource(R.drawable.icon_nocheck);


                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String testoInserito = s.toString();
                nuovaPasswordEditText.setTextColor(Color.parseColor("#000000"));

                // Questo metodo viene invocato prima che il testo venga cambiato.
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check2.setImageResource(R.drawable.icon_basiccheck);


                } else if(testoInserito.matches(passwordRegex)){
                    check2.setImageResource(R.drawable.icon_okcheck);

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check2.setImageResource(R.drawable.icon_nocheck);


                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Questo metodo viene invocato subito dopo che il testo è stato cambiato.

                String testoInserito = s.toString();
                nuovaPasswordEditText.setTextColor(Color.parseColor("#000000"));

                // Questo metodo viene invocato prima che il testo venga cambiato.
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check2.setImageResource(R.drawable.icon_basiccheck);


                } else if(testoInserito.matches(passwordRegex)){
                    check2.setImageResource(R.drawable.icon_okcheck);

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check2.setImageResource(R.drawable.icon_nocheck);


                }
            }
        });

        ripetiPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String pass1 = String.valueOf(nuovaPasswordEditText.getText());
                String pass2 = String.valueOf(ripetiPasswordEditText.getText());
                ripetiPasswordEditText.setTextColor(Color.parseColor("#000000"));

                // Questo metodo viene invocato prima che il testo venga cambiato.
                if (pass1.equals(pass2) && pass1.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check3.setImageResource(R.drawable.icon_basiccheck);

                } else if(pass1.equals(pass2)){
                    check3.setImageResource(R.drawable.icon_okcheck);

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check3.setImageResource(R.drawable.icon_nocheck);

                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pass1 = String.valueOf(nuovaPasswordEditText.getText());
                String pass2 = String.valueOf(ripetiPasswordEditText.getText());
                ripetiPasswordEditText.setTextColor(Color.parseColor("#000000"));

                // Questo metodo viene invocato prima che il testo venga cambiato.
                if (pass1.equals(pass2) && pass1.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check3.setImageResource(R.drawable.icon_basiccheck);

                } else if(pass1.equals(pass2)){
                    check3.setImageResource(R.drawable.icon_okcheck);

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check3.setImageResource(R.drawable.icon_nocheck);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String pass1 = String.valueOf(nuovaPasswordEditText.getText());
                String pass2 = String.valueOf(ripetiPasswordEditText.getText());
                ripetiPasswordEditText.setTextColor(Color.parseColor("#000000"));

                // Questo metodo viene invocato prima che il testo venga cambiato.
                if (pass1.equals(pass2) && pass1.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    check3.setImageResource(R.drawable.icon_basiccheck);

                } else if(pass1.equals(pass2)){
                    check3.setImageResource(R.drawable.icon_okcheck);

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    check3.setImageResource(R.drawable.icon_nocheck);

                }
            }
        });
    }

    private void setExtraParameters(){
        Intent intentRicevuto = getIntent();
        email = intentRicevuto.getStringExtra("email");
        password = intentRicevuto.getStringExtra("password");

    }

    private boolean isValidPassword(String password, String confermaPassword){
        return password.equals(confermaPassword) && !password.isEmpty();
    }

    public void onModifyClicked() {
        String passwordAttuale = passwordAttualeEditText.getText().toString();
        String nuovaPassword = nuovaPasswordEditText.getText().toString();
        String confermaPassword = ripetiPasswordEditText.getText().toString();

        if(isValidPassword(passwordAttuale, password)) {
            if (isValidPassword(nuovaPassword, confermaPassword)) {
                utenteModel utente = new utenteModel(email,nuovaPassword);
                modifyPasswordPresenter.resetPassword(utente);
            } else {
                nuovaPasswordEditText.startAnimation(shakeAnimation);
                ripetiPasswordEditText.startAnimation(shakeAnimation);

                Toast.makeText(activity28ModificaPasswordActivity.this, "Password non valida!",
                        Toast.LENGTH_SHORT).show();
            }
        }else {
            passwordAttualeEditText.startAnimation(shakeAnimation);

            Toast.makeText(activity28ModificaPasswordActivity.this, "Password non valida!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Funzione di risposta in caso di successo
    @Override
    public void onResetPasswordSuccess(messageResponse body) {
        Toast.makeText(activity28ModificaPasswordActivity.this, body.getMessage(), Toast.LENGTH_SHORT).show();
        overridePendingTransition(0, 0);
        finish();
    }

    // Funzione di risposta in caso di fallimento
    @Override
    public void onResetPasswordFailure(messageResponse errore_di_registrazione) {
        Toast.makeText(activity28ModificaPasswordActivity.this, errore_di_registrazione.getMessage(),
                Toast.LENGTH_SHORT).show();
        passwordAttualeEditText.startAnimation(shakeAnimation);
        nuovaPasswordEditText.startAnimation(shakeAnimation);
        ripetiPasswordEditText.startAnimation(shakeAnimation);

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

    public void togglePasswordVisibility1(View view) {
        if (isPasswordVisible) {
            passwordAttualeEditText.setTransformationMethod(new PasswordTransformationMethod());
            passwordToggle1.setImageResource(R.drawable.icon_hide_password);
        } else {
            passwordAttualeEditText.setTransformationMethod(null);
            passwordToggle1.setImageResource(R.drawable.icon_show_password);
        }
        isPasswordVisible = !isPasswordVisible;
        passwordAttualeEditText.setSelection(passwordAttualeEditText.getText().length());
    }

    public void togglePasswordVisibility2(View view) {
        if (isPasswordVisible) {
            nuovaPasswordEditText.setTransformationMethod(new PasswordTransformationMethod());
            passwordToggle2.setImageResource(R.drawable.icon_hide_password);
        } else {
            nuovaPasswordEditText.setTransformationMethod(null);
            passwordToggle2.setImageResource(R.drawable.icon_show_password);
        }
        isPasswordVisible = !isPasswordVisible;
        nuovaPasswordEditText.setSelection(nuovaPasswordEditText.getText().length());
    }

    public void togglePasswordVisibility3(View view) {
        if (isPasswordVisible) {
            ripetiPasswordEditText.setTransformationMethod(new PasswordTransformationMethod());
            passwordToggle3.setImageResource(R.drawable.icon_hide_password);
        } else {
            ripetiPasswordEditText.setTransformationMethod(null);
            passwordToggle3.setImageResource(R.drawable.icon_show_password);
        }
        isPasswordVisible = !isPasswordVisible;
        ripetiPasswordEditText.setSelection(ripetiPasswordEditText.getText().length());
    }

    public void showPopupMessageIndietro() {
        runOnUiThread(() -> {
            LayoutInflater inflater = LayoutInflater.from(activity28ModificaPasswordActivity.this);
            View customView = inflater.inflate(R.layout.activity_49_popup5, null);

            TextView btnConferma = customView.findViewById(R.id.buttonAcquista);
            TextView btnAnnulla = customView.findViewById(R.id.buttonVendi);

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity28ModificaPasswordActivity.this);
            builder.setCustomTitle(customView)
                    .setCancelable(false);

            android.app.AlertDialog dialog = builder.create();
            if (!isFinishing() && dialog != null && !dialog.isShowing()) {
                dialog.show();

                // Gestione del clic sul bottone "Conferma"
                btnConferma.setOnClickListener(v -> {
                    // Chiudi il popup e l'activity corrente
                    dialog.dismiss();
                    activity28ModificaPasswordActivity.this.finish();
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
