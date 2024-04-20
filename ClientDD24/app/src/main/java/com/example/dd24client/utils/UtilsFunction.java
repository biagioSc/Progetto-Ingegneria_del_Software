package com.example.dd24client.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.example.dd24client.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import com.example.dd24client.views.Activity44Search;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class UtilsFunction {
    private Animation buttonAnimation;

    public void setTouchListenerForAnimation(View view, Context context) {
        buttonAnimation = AnimationUtils.loadAnimation(context, R.anim.button_animation);

        view.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.startAnimation(buttonAnimation);
                new Handler().postDelayed(v::clearAnimation, 100);
            }
            return false;
        });
    }

    public JSONObject caricaDatiDaJson(Context context, String filename) {
        if (context != null && filename != null) {
            AssetManager assetManager = context.getAssets();
            try (InputStream is = assetManager.open(filename)) {
                int size = is.available();
                byte[] buffer = new byte[size];
                int bytesRead = is.read(buffer);
                if (bytesRead != -1) {
                    String jsonStr = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8);
                    return new JSONObject(jsonStr);
                } else {
                    Log.e("SignupActivity1", "Nessun dato letto dal file");
                    Toast.makeText(context, "Errore nel caricamento dei dati", Toast.LENGTH_LONG).show();
                }
            } catch (IOException e) {
                Log.e("SignupActivity1", "Errore nella lettura del file", e);
                Toast.makeText(context, "Errore nel caricamento dei dati", Toast.LENGTH_LONG).show();
            } catch (JsonSyntaxException | JsonIOException | JSONException e) {
                Log.e("SignupActivity1", "Errore nel parsing del JSON", e);
                Toast.makeText(context, "Errore nel parsing dei dati", Toast.LENGTH_LONG).show();
            }

        }
        return null;
    }

    public void openCerca(Context activityContext, String email, String password, String query, String activity) {
        String messageString = "Cliccato per fare una ricerca in " + activityContext;
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        Intent intent = new Intent(activityContext, Activity44Search.class);
        intent.putExtra("user", activity);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        intent.putExtra("query", query);

        activityContext.startActivity(intent);
        ((Activity) activityContext).overridePendingTransition(0, 0);
        ((Activity) activityContext).finish();
    }

    public void updateHorizontalScrollView(HorizontalScrollView horizontalScrollView, boolean removeText, Context context) {
        // Ottieni il LinearLayout interno dal HorizontalScrollView
        LinearLayout linearLayout = (LinearLayout) horizontalScrollView.getChildAt(0);

        if (removeText) {
            // Rimuovi il TextView se presente
            if (linearLayout.getChildCount() > 0 && linearLayout.getChildAt(0) instanceof TextView) {
                linearLayout.removeViewAt(0);
            }
        } else {
            // Controlla se ci sono già elementi nel LinearLayout
            if (linearLayout.getChildCount() == 0) {
                // Non ci sono elementi, quindi aggiungi un TextView
                TextView textView = new TextView(context);
                textView.setText("Non sono presenti aste attive");
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18); // Imposta la dimensione del testo a 18sp
                textView.setTypeface(null, Typeface.BOLD); // Imposta il testo in grassetto

                // Imposta la larghezza e l'altezza per coprire l'intero HorizontalScrollView
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                textView.setLayoutParams(params);
                linearLayout.addView(textView);
            }
        }
    }

    public void updateNestedScrollView(LinearLayout linearLayout, boolean removeText, Context context) {

        if (removeText) {
            // Rimuovi il TextView se presente
            if (linearLayout.getChildCount() > 0 && linearLayout.getChildAt(0) instanceof TextView) {
                linearLayout.removeViewAt(0);
            }
        } else {
            // Controlla se ci sono già elementi nel LinearLayout
            if (linearLayout.getChildCount() == 0) {
                // Non ci sono elementi, quindi aggiungi un TextView
                TextView textView = new TextView(context);
                textView.setText("Non sono presenti aste");
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18); // Imposta la dimensione del testo a 18sp
                textView.setTypeface(null, Typeface.BOLD); // Imposta il testo in grassetto

                // Imposta la larghezza e l'altezza per coprire l'intero HorizontalScrollView
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                textView.setLayoutParams(params);
                linearLayout.addView(textView);
            }
        }
    }

    public void updateNestedScrollViewOfferte(LinearLayout linearLayout, boolean removeText, Context context) {

        if (removeText) {
            // Rimuovi il TextView se presente
            if (linearLayout.getChildCount() > 0 && linearLayout.getChildAt(0) instanceof TextView) {
                linearLayout.removeViewAt(0);
            }
        } else {
            // Controlla se ci sono già elementi nel LinearLayout
            if (linearLayout.getChildCount() == 0) {
                // Non ci sono elementi, quindi aggiungi un TextView
                TextView textView = new TextView(context);
                textView.setText("Non sono presenti offerte");
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18); // Imposta la dimensione del testo a 18sp
                textView.setTypeface(null, Typeface.BOLD); // Imposta il testo in grassetto

                // Imposta la larghezza e l'altezza per coprire l'intero HorizontalScrollView
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                textView.setLayoutParams(params);
                linearLayout.addView(textView);
            }
        }
    }


    public void removeAllCardViews(LinearLayout linearLayout) {
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View child = linearLayout.getChildAt(i);
            if (child instanceof CardView || child instanceof TextView || child instanceof LinearLayout) {
                linearLayout.removeViewAt(i);
                i--; // Decrementa l'indice dopo la rimozione
            }
        }
    }



}
