package com.example.dd24client.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dd24client.R;
import com.google.firebase.analytics.FirebaseAnalytics;

public class Activity10Signup extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedIstanceState){
        super.onCreate(savedIstanceState);
        setContentView(R.layout.activity_10_signup4);
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity10Signup", null);

        Intent intentRicevuto = getIntent();
        String email = intentRicevuto.getStringExtra("email");
        String password = intentRicevuto.getStringExtra("password");

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(Activity10Signup.this, Activity06Login.class);
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }, 3000);

    }
}
