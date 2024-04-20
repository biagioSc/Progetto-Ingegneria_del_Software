package com.example.dd24client.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.dd24client.model.UtenteModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.network.RetrofitClient;
import com.example.dd24client.presenter.Presenter11Forgot;
import com.example.dd24client.R;
import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.utils.UtilsFunction;
import com.google.firebase.analytics.FirebaseAnalytics;

public class Activity11Forgot extends AppCompatActivity implements Activity11ForgotView {
    private UtilsFunction function;
    private Animation shakeAnimation;
    private EditText recuperoEmailEditText;
    private TextView tvSubHeader;
    private TextView inviaCodiceButton;
    private ImageView indietroImageView;
    private Presenter11Forgot forgotPasswordPresenter;
    private String emailRecupero;
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedIstanceState){
        super.onCreate(savedIstanceState);
        setContentView(R.layout.activity_11_recpassword1);
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity11Forgot", null);

        setComponents();
        setListener();

    }

    private void setComponents(){
        function = new UtilsFunction();

        recuperoEmailEditText = findViewById(R.id.editTextEmail);
        inviaCodiceButton = findViewById(R.id.inviaCodiceButton);
        tvSubHeader = findViewById(R.id.tvSubHeader);
        indietroImageView = findViewById(R.id.indietroImageView);
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake_error);
        forgotPasswordPresenter = new Presenter11Forgot(this,apiService);
    }

    private void setListener() {
        function.setTouchListenerForAnimation(inviaCodiceButton, this);

        indietroImageView.setOnClickListener(v -> {
            String messageString = "Cliccato 'indietro' in 10forgot";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(this, Activity06Login.class);
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

    private void onSendRecoveryEmailClicked() {
        String messageString = "Cliccato 'invia codice' in 10forgot";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        emailRecupero = recuperoEmailEditText.getText().toString();
        if(!emailRecupero.isEmpty()){
            UtenteModel utente = new UtenteModel(emailRecupero);
            forgotPasswordPresenter.sendRecoveryEmail(utente);
            inviaCodiceButton.setClickable(false);
        }else{
            recuperoEmailEditText.startAnimation(shakeAnimation);
            String errorLogin = "Email non valida";
            Toast.makeText(this, errorLogin, Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onSendRecoveryEmailFailure(MessageResponse errore_email) {
        String infoMessage = "Email non trovata";
        String errorLogin = "Email non valida";
        recuperoEmailEditText.startAnimation(shakeAnimation);

        tvSubHeader.setText(errorLogin);
        tvSubHeader.setTextColor(Color.parseColor("#FF5959"));
        Toast.makeText(this, infoMessage, Toast.LENGTH_SHORT).show();

        inviaCodiceButton.setClickable(true);

    }

    @Override
    public void onSendRecoveryEmailSuccess(MessageResponse message) {
        String errorLogin = "Email trovata";
        recuperoEmailEditText.startAnimation(shakeAnimation);

        tvSubHeader.setText(errorLogin);
        tvSubHeader.setTextColor(ContextCompat.getColor(this, R.color.customBlue));

        Toast.makeText(Activity11Forgot.this, message.getMessage(),
                Toast.LENGTH_SHORT).show();

        // Breve pausa per mostrare il messaggio di successo

        Intent intent = new Intent(Activity11Forgot.this, Activity12Forgot.class);
        intent.putExtra("email",emailRecupero);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

}
