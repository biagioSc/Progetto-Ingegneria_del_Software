package com.example.dd24client.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import androidx.appcompat.app.AppCompatActivity;

import com.example.dd24client.R;

public class activity10SignupActivity4 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedIstanceState){
        super.onCreate(savedIstanceState);
        setContentView(R.layout.activity_10_signup4);

        Intent intentRicevuto = getIntent();
        String email = intentRicevuto.getStringExtra("email");
        String password = intentRicevuto.getStringExtra("password");

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(activity10SignupActivity4.this, activity06LoginActivity.class);
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }, 3000);

    }
}
