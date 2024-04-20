package com.example.dd24client.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
import android.text.Editable;
import android.text.TextWatcher;
public class activity12ForgotPasswordActivity2 extends AppCompatActivity implements activity12ForgotPasswordView2 {
    private Animation buttonAnimation;
    private EditText codiceRecuperoEditText;
    private TextView inviaCodiceTextView, tvSubHeader;
    private TextView continuaButton;
    private String email;
    private ImageView indietroImageView;

    private presenter11ForgotPasswordPresenter forgotPasswordPresenter;
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedIstanceState){
        super.onCreate(savedIstanceState);
        setContentView(R.layout.activity_12_recpassword2);
        forgotPasswordPresenter = new presenter11ForgotPasswordPresenter(this,apiService);
        setComponents();
        setListener();

        Intent intentRicevuto = getIntent();
        email = intentRicevuto.getStringExtra("email");

    }

    private void setComponents(){
        codiceRecuperoEditText = findViewById(R.id.editTextEmail);
        inviaCodiceTextView = findViewById(R.id.inviaCodiceTextView);
        continuaButton = findViewById(R.id.inviaCodiceButton);
        tvSubHeader = findViewById(R.id.tvSubHeader);
        indietroImageView = findViewById(R.id.indietroImageView);
        buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.button_animation);
    }

    private void setListener() {
        setTouchListenerForAnimation(continuaButton);
        setTouchListenerForAnimation(inviaCodiceTextView);

        indietroImageView.setOnClickListener(v -> {
            Intent intent = new Intent(this, activity06LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

        codiceRecuperoEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Questo metodo viene invocato prima che il testo venga cambiato.
                codiceRecuperoEditText.setTextColor(Color.parseColor("#000000"));

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Questo metodo viene invocato mentre il testo sta cambiando.
                codiceRecuperoEditText.setTextColor(Color.parseColor("#000000"));

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Questo metodo viene invocato subito dopo che il testo è stato cambiato.
                codiceRecuperoEditText.setTextColor(Color.parseColor("#000000"));

            }
        });

        continuaButton.setOnClickListener(v -> onSendRecoveryCodeClicked());
        inviaCodiceTextView.setOnClickListener(v -> onSendRecoveryEmailClicked());

    }

    private boolean isValidRecoveryCode(String codiceRecuperoString){
        try {
            Integer.parseInt(codiceRecuperoString);
            return true;
        }catch (NumberFormatException e){
            return false;
        }

    }

    @Override
    public void onSendRecoveryCodeClicked() {
        String codiceRecuperoString = codiceRecuperoEditText.getText().toString();

        if(isValidRecoveryCode(codiceRecuperoString)){
            int codiceRecupero = Integer.parseInt(codiceRecuperoString);
            utenteModel utente = new utenteModel(email, codiceRecupero);
            forgotPasswordPresenter.sendRecoveryCode(utente);
        }else{
            String errorLogin = "Pin non valido o scaduto";

            tvSubHeader.setText(errorLogin);
            tvSubHeader.setTextColor(Color.parseColor("#FF5959"));
            new Handler().postDelayed(() -> {
                tvSubHeader.setText(errorLogin);
                tvSubHeader.setTextColor(Color.parseColor("#7F7F82"));

            }, 5000);
        }

    }

    @Override
    public void onSendRecoveryCodeFailure(messageResponse errore_codice) {
        String errorLogin = "Pin non valido o scaduto";
        tvSubHeader.setText(errorLogin);
        tvSubHeader.setTextColor(Color.parseColor("#FF5959"));
        new Handler().postDelayed(() -> {
            tvSubHeader.setText(errorLogin);
            tvSubHeader.setTextColor(Color.parseColor("#7F7F82"));

        }, 5000);
    }

    @Override
    public void onSendRecoveryCodeSuccess(messageResponse message) {
        Toast.makeText(activity12ForgotPasswordActivity2.this, message.getMessage(),
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, activity13ForgotPasswordActivity3.class);
        intent.putExtra("email",email);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    public void onSendRecoveryEmailClicked() {
        utenteModel utente = new utenteModel(email);
        forgotPasswordPresenter.resendRecoveryEmail(utente);
    }

    @Override
    public void onSendRecoveryEmailFailure(messageResponse errore_email) {
        Toast.makeText(activity12ForgotPasswordActivity2.this, errore_email.getMessage(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSendRecoveryEmailSuccess(messageResponse message) {
        Toast.makeText(this, message.getMessage(),
                Toast.LENGTH_SHORT).show();
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
