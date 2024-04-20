package com.example.dd24client.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
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

public class activity11ForgotPasswordActivity1 extends AppCompatActivity implements activity11ForgotPasswordView1 {
    private Animation buttonAnimation, shakeAnimation;
    private EditText recuperoEmailEditText;
    private TextView tvSubHeader;
    private TextView inviaCodiceButton;
    private ImageView indietroImageView;
    private presenter11ForgotPasswordPresenter forgotPasswordPresenter;
    private String emailRecupero;
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedIstanceState){
        super.onCreate(savedIstanceState);
        setContentView(R.layout.activity_11_recpassword1);
        forgotPasswordPresenter = new presenter11ForgotPasswordPresenter(this,apiService);
        setComponents();
        setListener();

    }

    private void setComponents(){
        recuperoEmailEditText = findViewById(R.id.editTextEmail);
        inviaCodiceButton = findViewById(R.id.inviaCodiceButton);
        tvSubHeader = findViewById(R.id.tvSubHeader);
        indietroImageView = findViewById(R.id.indietroImageView);
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake_error);
        buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.button_animation);
    }

    private void setListener() {
        setTouchListenerForAnimation(inviaCodiceButton);
        indietroImageView.setOnClickListener(v -> {
            Intent intent = new Intent(this, activity06LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

        recuperoEmailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Questo metodo viene invocato prima che il testo venga cambiato.
                recuperoEmailEditText.setTextColor(Color.parseColor("#000000"));

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Questo metodo viene invocato mentre il testo sta cambiando.
                recuperoEmailEditText.setTextColor(Color.parseColor("#000000"));

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Questo metodo viene invocato subito dopo che il testo è stato cambiato.
                recuperoEmailEditText.setTextColor(Color.parseColor("#000000"));

            }
        });
        inviaCodiceButton.setOnClickListener(v -> onSendRecoveryEmailClicked());
    }

    @Override
    public void onSendRecoveryEmailClicked() {

        emailRecupero = recuperoEmailEditText.getText().toString();
        if(!emailRecupero.isEmpty()){
            utenteModel utente = new utenteModel(emailRecupero);
            forgotPasswordPresenter.sendRecoveryEmail(utente);
        }else{
            recuperoEmailEditText.startAnimation(shakeAnimation);
            Toast.makeText(getApplicationContext(), "Email non valida!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSendRecoveryEmailFailure(messageResponse errore_email) {
        String infoMessage = "Email non trovata";
        String errorLogin = "Email non valida";
        recuperoEmailEditText.startAnimation(shakeAnimation);

        tvSubHeader.setText(errorLogin);
        tvSubHeader.setTextColor(Color.parseColor("#FF5959"));
        new Handler().postDelayed(() -> {
            tvSubHeader.setText(infoMessage);
            tvSubHeader.setTextColor(Color.parseColor("#7F7F82"));

        }, 5000);
    }

    @Override
    public void onSendRecoveryEmailSuccess(messageResponse message) {
        Toast.makeText(activity11ForgotPasswordActivity1.this, message.getMessage(),
                Toast.LENGTH_SHORT).show();

        // Breve pausa per mostrare il messaggio di successo

        Intent intent = new Intent(activity11ForgotPasswordActivity1.this, activity12ForgotPasswordActivity2.class);
        intent.putExtra("email",emailRecupero);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
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
