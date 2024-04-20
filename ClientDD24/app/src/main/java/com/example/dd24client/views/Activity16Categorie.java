package com.example.dd24client.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.dd24client.R;
import com.example.dd24client.utils.UtilsFunction;
import com.google.firebase.analytics.FirebaseAnalytics;

public class Activity16Categorie extends AppCompatActivity {
    private UtilsFunction function;
    private ImageView indietroImageView;
    private String email,password;
    private CardView card1, card2, card3, card4, card5, card6, card7, card8, card9, card10, card11, card12, card13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_16_categorie);
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity16Categorie", null);

        setComponents();
        setListener();
        setExtraParameters();

    }


    private void setComponents(){
        function = new UtilsFunction();

        indietroImageView = findViewById(R.id.indietroImageView);

        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);
        card5 = findViewById(R.id.card5);
        card6 = findViewById(R.id.card6);
        card7 = findViewById(R.id.card7);
        card8 = findViewById(R.id.card8);
        card9 = findViewById(R.id.card9);
        card10 = findViewById(R.id.card10);
        card11 = findViewById(R.id.card11);
        card12 = findViewById(R.id.card12);
        card13 = findViewById(R.id.card13);

    }

    private void setListener(){
        function.setTouchListenerForAnimation(indietroImageView, this);

        indietroImageView.setOnClickListener(v -> {
            String messageString = "Cliccato 'indietro' in 16categorie";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            overridePendingTransition(0, 0);
            finish();
        });

        function.setTouchListenerForAnimation(card1, this);
        function.setTouchListenerForAnimation(card2, this);
        function.setTouchListenerForAnimation(card3, this);
        function.setTouchListenerForAnimation(card4, this);
        function.setTouchListenerForAnimation(card5, this);
        function.setTouchListenerForAnimation(card6, this);
        function.setTouchListenerForAnimation(card7, this);
        function.setTouchListenerForAnimation(card8, this);
        function.setTouchListenerForAnimation(card9, this);
        function.setTouchListenerForAnimation(card10, this);
        function.setTouchListenerForAnimation(card11, this);
        function.setTouchListenerForAnimation(card12, this);
        function.setTouchListenerForAnimation(card13, this);

        CardView[] cardViews = {card1, card2, card3, card4, card5, card6, card7, card8, card9, card10, card11, card12, card13};

        for (int k = 0; k < cardViews.length; k++) {
            String messageString = "Cliccato '" + cardViews[k] + "' in 16categorie";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            int finalI = k;
            cardViews[k].setOnClickListener(v -> cardClick(finalI));
        }


    }

    private void setExtraParameters() {
        Intent intentRicevuto = getIntent();
        email = intentRicevuto.getStringExtra("email");
        password = intentRicevuto.getStringExtra("password");
    }

    private void cardClick(int categoriaSelezionata) {
        Intent intent = new Intent(Activity16Categorie.this, Activity17AsteAcquirente.class);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        intent.putExtra("categoriaSelezionata",categoriaSelezionata);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finishAffinity();
    }
}
