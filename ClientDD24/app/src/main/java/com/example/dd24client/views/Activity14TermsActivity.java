package com.example.dd24client.views;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dd24client.R;

public class activity14TermsActivity extends AppCompatActivity {
    private Animation buttonAnimation;
    private ImageView indietroImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_14_terms);

        setComponents();
        setListener();
    }

    private void setComponents() {
        buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.button_animation);
        indietroImageView = findViewById(R.id.imageButtonIndietro);
    }

    private void setListener() {
        setTouchListenerForAnimation(indietroImageView);
        indietroImageView.setOnClickListener(v -> {
            overridePendingTransition(0, 0);
            finish();
        });
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
