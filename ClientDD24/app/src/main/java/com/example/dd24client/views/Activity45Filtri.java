package com.example.dd24client.views;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.dd24client.R;
import com.google.android.material.slider.RangeSlider;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Activity45Filtri extends AppCompatActivity {
    private ImageView indietroImageView;
    private RangeSlider rangeSliderPrezzo;
    private TextView buttonFiltri, leftValue, rightValue;
    private EditText editTextData;
    private CheckBox checkboxAstaSilenziosa, checkboxAstaAlRibasso,checkbox1, checkbox2, checkbox3, checkbox4, checkbox5,
            checkbox6, checkbox7, checkbox8, checkbox9, checkbox10,
            checkbox11, checkbox12, checkbox13, checkbox14;
    private Spinner spinner;
    private String email="", password="", query="", ordinaper="", activity="", data="";
    private Boolean astaSilenziosa=false, astaRibasso=false;
    private float endValue = 0;
    private float startValue=1000;
    private static final String USER_KEY = "user";
    private static final String ASTA_SILENZIOSA_KEY = "astaSilenziosa";
    private static final String ASTA_RIBASSO_KEY = "astaRibasso";
    private static final String DATA_KEY = "data";
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_KEY = "password";
    private static final String QUERY_KEY = "query";
    private static final String ORDINA_PER_KEY = "ordinaper";
    private static final String START_VALUE_KEY = "startValue";
    private static final String END_VALUE_KEY = "endValue";
    private static final String[] CATEGORIA_KEYS = {
            "categoria1", "categoria2", "categoria3", "categoria4", "categoria5",
            "categoria6", "categoria7", "categoria8", "categoria9", "categoria10",
            "categoria11", "categoria12", "categoria13", "categoria14"
    };
    private boolean[] categoria = new boolean[14];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_45_filtri);
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity45Filtri", null);

        setComponents();
        setListener();

        setExtraParameters();

    }

    private void setComponents() {
        buttonFiltri = findViewById(R.id.buttonFiltri);
        indietroImageView = findViewById(R.id.indietroImageView);
        rangeSliderPrezzo = findViewById(R.id.rangeSliderPrezzo);
        leftValue = findViewById(R.id.leftValue);
        rightValue = findViewById(R.id.rightValue);
        checkboxAstaSilenziosa= findViewById(R.id.checkboxAstaSilenziosa);
        checkboxAstaAlRibasso = findViewById(R.id.checkboxAstaAlRibasso);
        editTextData = findViewById(R.id.editTextData);
        checkbox1 = findViewById(R.id.checkboxCat1);
        checkbox2 = findViewById(R.id.checkboxCat2);
        checkbox3 = findViewById(R.id.checkboxCat3);
        checkbox4 = findViewById(R.id.checkboxCat4);
        checkbox5 = findViewById(R.id.checkboxCat5);
        checkbox6 = findViewById(R.id.checkboxCat6);
        checkbox7 = findViewById(R.id.checkboxCat7);
        checkbox8 = findViewById(R.id.checkboxCat8);
        checkbox9 = findViewById(R.id.checkboxCat9);
        checkbox10 = findViewById(R.id.checkboxCat10);
        checkbox11 = findViewById(R.id.checkboxCat11);
        checkbox12 = findViewById(R.id.checkboxCat12);
        checkbox13 = findViewById(R.id.checkboxCat13);
        checkbox14 = findViewById(R.id.checkboxCat14);
        spinner = findViewById(R.id.nazionalitaSpinner);


    }

    private void setListener() {

        buttonFiltri.setOnClickListener(view -> saveFilters());

        indietroImageView.setOnClickListener(view -> indietroFunction());

        rangeSliderPrezzo.addOnChangeListener((slider, value, fromUser) -> {
            List<Float> values = slider.getValues();
            leftValue.setText(String.format(Locale.getDefault(), "%.0f€", values.get(0)));
            rightValue.setText(String.format(Locale.getDefault(), "%.0f€", values.get(1)));
        });

        checkbox14.setOnCheckedChangeListener((buttonView, isChecked) -> setAllCheckboxes(isChecked));

        editTextData.setOnClickListener(view -> {
            // Ottieni la data corrente e aggiungi un giorno per inizializzare il picker con la data di domani
            final Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, 1); // Aggiunge un giorno alla data corrente
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Crea il DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view1, year1, monthOfYear, dayOfMonth) -> {
                        // Imposta l'EditText con la data formattata
                        String formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year1);
                        editTextData.setTextColor(Color.parseColor("#000000"));
                        editTextData.setText(formattedDate);

                    }, year, month, day);

            // Imposta la data minima selezionabile a domani per impedire la selezione della data corrente e di quelle passate
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());

            // Mostra il DatePickerDialog
            datePickerDialog.show();
        });


    }

    private void setExtraParameters() {
        Intent intentRicevuto = getIntent();
        activity = intentRicevuto.getStringExtra("user");

        if (activity != null && activity.equals("venditore")) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.darkOrange));
        }

        email = intentRicevuto.getStringExtra(EMAIL_KEY);
        password = intentRicevuto.getStringExtra(PASSWORD_KEY);
        query = intentRicevuto.getStringExtra(QUERY_KEY);
        astaSilenziosa = intentRicevuto.getBooleanExtra(ASTA_SILENZIOSA_KEY, false);
        astaRibasso = intentRicevuto.getBooleanExtra(ASTA_RIBASSO_KEY, false);
        data = intentRicevuto.getStringExtra(DATA_KEY);
        ordinaper = intentRicevuto.getStringExtra(ORDINA_PER_KEY);

        for (int i = 0; i < CATEGORIA_KEYS.length; i++) {
            categoria[i] = intentRicevuto.getBooleanExtra(CATEGORIA_KEYS[i], false);
        }

        startValue = intentRicevuto.getFloatExtra(START_VALUE_KEY, 0);
        endValue = intentRicevuto.getFloatExtra(END_VALUE_KEY, 1000);

        checkboxAstaSilenziosa.setChecked(astaSilenziosa);
        checkboxAstaAlRibasso.setChecked(astaRibasso);

        checkbox14.setChecked(categoria[13]);
        checkbox1.setChecked(categoria[0]);
        checkbox2.setChecked(categoria[1]);
        checkbox3.setChecked(categoria[2]);
        checkbox4.setChecked(categoria[3]);
        checkbox5.setChecked(categoria[4]);
        checkbox6.setChecked(categoria[5]);
        checkbox7.setChecked(categoria[6]);
        checkbox8.setChecked(categoria[7]);
        checkbox9.setChecked(categoria[8]);
        checkbox10.setChecked(categoria[9]);
        checkbox11.setChecked(categoria[10]);
        checkbox12.setChecked(categoria[11]);
        checkbox13.setChecked(categoria[12]);

        editTextData.setTextColor(Color.parseColor("#000000"));
        editTextData.setText(data);

// Supponendo che tu abbia delle TextView o altri elementi per visualizzare ordinaper, startValue e endValue
        Adapter adapter = spinner.getAdapter();
        int count = adapter.getCount();

        int indicePerSelezione = -1;
        for (int i = 0; i < count; i++) {
            if (ordinaper.equals(adapter.getItem(i).toString())) {
                indicePerSelezione = i;
                break;
            }
        }

        if (indicePerSelezione != -1) {
            spinner.setSelection(indicePerSelezione);
        }

        List<Float> sliderValues = new ArrayList<>();
        sliderValues.add(Math.max(startValue, rangeSliderPrezzo.getValueFrom()));  // Assicurati che sia all'interno dell'intervallo
        sliderValues.add(Math.min(endValue, rangeSliderPrezzo.getValueTo()));      // Assicurati che sia all'interno dell'intervallo
        rangeSliderPrezzo.setValues(sliderValues);
    }

    private void saveFilters() {
        String messageString = "Cliccato 'salva filtri' in 45filtri";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        astaSilenziosa = checkboxAstaSilenziosa.isChecked();
        astaRibasso = checkboxAstaAlRibasso.isChecked();
        categoria[0] = checkbox1.isChecked();
        categoria[1] = checkbox2.isChecked();
        categoria[2] = checkbox3.isChecked();
        categoria[3] = checkbox4.isChecked();
        categoria[4] = checkbox5.isChecked();
        categoria[5] = checkbox6.isChecked();
        categoria[6] = checkbox7.isChecked();
        categoria[7] = checkbox8.isChecked();
        categoria[8] = checkbox9.isChecked();
        categoria[9] = checkbox10.isChecked();
        categoria[10] = checkbox11.isChecked();
        categoria[11] = checkbox12.isChecked();
        categoria[12] = checkbox13.isChecked();
        categoria[13] = checkbox14.isChecked();

        ordinaper = spinner.getSelectedItem().toString();
        data = editTextData.getText().toString();

        List<Float> rangeValues = rangeSliderPrezzo.getValues();
        startValue = rangeValues.get(0);
        endValue = rangeValues.get(1);

        Intent intent = getIntent1();

        startActivity(intent);
        overridePendingTransition(0, 0); // Opzionale: transizione senza animazione
        finish();

    }

    private Intent getIntent1() {
        Intent intent = new Intent(Activity45Filtri.this, Activity44Search.class);

        intent.putExtra(USER_KEY, activity);
        intent.putExtra(ASTA_SILENZIOSA_KEY, astaSilenziosa);
        intent.putExtra(ASTA_RIBASSO_KEY, astaRibasso);
        intent.putExtra(DATA_KEY, data);
        intent.putExtra(EMAIL_KEY, email);
        intent.putExtra(PASSWORD_KEY, password);
        intent.putExtra(QUERY_KEY, query);
        intent.putExtra(ORDINA_PER_KEY, ordinaper);
        intent.putExtra(START_VALUE_KEY, startValue);
        intent.putExtra(END_VALUE_KEY, endValue);

        for (int i = 0; i < CATEGORIA_KEYS.length; i++) {
            intent.putExtra(CATEGORIA_KEYS[i], categoria[i]);
        }
        return intent;
    }

    private void indietroFunction(){
        String messageString = "Cliccato 'indietro' in 45filtri";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        Intent intent = new Intent(Activity45Filtri.this, Activity44Search.class);
        intent.putExtra(USER_KEY, activity);
        intent.putExtra(ASTA_SILENZIOSA_KEY, astaSilenziosa);
        intent.putExtra(ASTA_RIBASSO_KEY, astaRibasso);
        intent.putExtra(DATA_KEY, data);
        intent.putExtra(EMAIL_KEY, email);
        intent.putExtra(PASSWORD_KEY, password);
        intent.putExtra(QUERY_KEY, query);
        intent.putExtra(ORDINA_PER_KEY, ordinaper);
        intent.putExtra(START_VALUE_KEY, startValue);
        intent.putExtra(END_VALUE_KEY, endValue);

        for (int i = 0; i < CATEGORIA_KEYS.length; i++) {
            intent.putExtra(CATEGORIA_KEYS[i], categoria[i]);
        }

        startActivity(intent);
        overridePendingTransition(0, 0); // Opzionale: transizione senza animazione
        finish();
    }

    private void setAllCheckboxes(boolean isChecked) {
        checkbox1.setChecked(isChecked);
        checkbox2.setChecked(isChecked);
        checkbox3.setChecked(isChecked);
        checkbox4.setChecked(isChecked);
        checkbox5.setChecked(isChecked);
        checkbox6.setChecked(isChecked);
        checkbox7.setChecked(isChecked);
        checkbox8.setChecked(isChecked);
        checkbox9.setChecked(isChecked);
        checkbox10.setChecked(isChecked);
        checkbox11.setChecked(isChecked);
        checkbox12.setChecked(isChecked);
        checkbox13.setChecked(isChecked);
        checkbox14.setChecked(isChecked);
        // Aggiungi qui gli altri CheckBox se ce ne sono di più
    }
}
