package com.example.dd24client.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

import android.text.Editable;
import android.text.TextWatcher;

public class Activity12Forgot extends AppCompatActivity implements Activity12ForgotView {
    private UtilsFunction function;
    private EditText codiceRecuperoEditText;
    private TextView inviaCodiceTextView, tvSubHeader;
    private TextView continuaButton;
    private String email;
    private UtenteModel utente;
    private ImageView indietroImageView;

    private Presenter11Forgot forgotPasswordPresenter;
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedIstanceState){
        super.onCreate(savedIstanceState);
        setContentView(R.layout.activity_12_recpassword2);
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity12Forgot", null);

        setComponents();
        setListener();
        setExtraParameters();

    }

    private void setComponents(){
        function = new UtilsFunction();

        codiceRecuperoEditText = findViewById(R.id.editTextEmail);
        inviaCodiceTextView = findViewById(R.id.inviaCodiceTextView);
        continuaButton = findViewById(R.id.inviaCodiceButton);
        tvSubHeader = findViewById(R.id.tvSubHeader);
        indietroImageView = findViewById(R.id.indietroImageView);

        forgotPasswordPresenter = new Presenter11Forgot(this,apiService);

    }

    private void setListener() {
        function.setTouchListenerForAnimation(continuaButton, this);
        function.setTouchListenerForAnimation(inviaCodiceTextView, this);

        indietroImageView.setOnClickListener(v -> {
            String messageString = "Cliccato 'indietro' in 11forgot";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(this, Activity06Login.class);
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

    private void setExtraParameters() {
        Intent intentRicevuto = getIntent();
        email = intentRicevuto.getStringExtra("email");

    }

    private boolean isValidRecoveryCode(String codiceRecuperoString){
        try {
            Integer.parseInt(codiceRecuperoString);
            return true;
        }catch (NumberFormatException e){
            return false;
        }

    }

    public void onSendRecoveryCodeClicked() {
        String messageString = "Cliccato 'continua' in 12forgot";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        String codiceRecuperoString = codiceRecuperoEditText.getText().toString();

        if(isValidRecoveryCode(codiceRecuperoString)){
            int codiceRecupero = Integer.parseInt(codiceRecuperoString);
            utente = new UtenteModel(email, codiceRecupero);
            forgotPasswordPresenter.sendRecoveryCode(utente);
            continuaButton.setClickable(false);

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
    public void onSendRecoveryCodeFailure(MessageResponse errore_codice) {
        String errorLogin = "Pin non valido o scaduto";
        tvSubHeader.setText(errorLogin);
        tvSubHeader.setTextColor(Color.parseColor("#FF5959"));
        new Handler().postDelayed(() -> {
            tvSubHeader.setText(errorLogin);
            tvSubHeader.setTextColor(Color.parseColor("#7F7F82"));

        }, 5000);
    }

    @Override
    public void onSendRecoveryCodeSuccess(MessageResponse message) {
        Toast.makeText(Activity12Forgot.this, message.getMessage(),
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Activity13Forgot.class);
        intent.putExtra("email",email);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void onSendRecoveryEmailClicked() {
        String messageString = "Cliccato 'invia nuovo codice' in 12forgot";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        UtenteModel utente = new UtenteModel(email);
        forgotPasswordPresenter.resendRecoveryEmail(utente);
        inviaCodiceTextView.setClickable(false);

    }

    @Override
    public void onSendRecoveryEmailFailure(MessageResponse errore_email) {
        Toast.makeText(Activity12Forgot.this, errore_email.getMessage(),
                Toast.LENGTH_SHORT).show();
        continuaButton.setClickable(true);
        inviaCodiceTextView.setClickable(true);

    }

    @Override
    public void onSendRecoveryEmailSuccess(MessageResponse message) {
        Toast.makeText(this, message.getMessage(),
                Toast.LENGTH_SHORT).show();
    }

}
