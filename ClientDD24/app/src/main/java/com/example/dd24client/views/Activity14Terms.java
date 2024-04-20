package com.example.dd24client.views;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dd24client.R;
import com.example.dd24client.utils.UtilsFunction;
import com.google.firebase.analytics.FirebaseAnalytics;

public class Activity14Terms extends AppCompatActivity {
    private UtilsFunction function;
    private ImageView indietroImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_14_terms);
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity14Forgot", null);

        setComponents();
        setListener();
    }

    private void setComponents() {
        function = new UtilsFunction();
        indietroImageView = findViewById(R.id.imageButtonIndietro);
    }

    private void setListener() {
        function.setTouchListenerForAnimation(indietroImageView,this);
        indietroImageView.setOnClickListener(v -> {
            String messageString = "Cliccato 'indietro' in 14Terms";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            overridePendingTransition(0, 0);
            finish();
        });
    }

}
