package com.example.dd24client.views;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class activity45FiltriActivity extends AppCompatActivity {

    private ImageView indietroImageView;
    private RangeSlider rangeSliderPrezzo;
    private TextView buttonFiltri, leftValue, rightValue;
    private EditText editTextData;
    private CheckBox checkboxAstaSilenziosa, checkboxAstaAlRibasso,checkbox1, checkbox2, checkbox3, checkbox4, checkbox5,
            checkbox6, checkbox7, checkbox8, checkbox9, checkbox10,
            checkbox11, checkbox12, checkbox13, checkbox14;
    private Spinner spinner;
    private String email="", password="", query="", ordinaper="", activity="", data="";
    private Boolean astaSilenziosa=false, astaRibasso=false, categoria1=false,categoria2=false,categoria3=false,categoria4=false,categoria5=false,categoria6=false,categoria7=false,categoria8=false,categoria9=false,categoria10=false,categoria11=false,categoria12=false,categoria13=false,categoria14=false;
    private float endValue = 0;
    private float startValue=1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_45_filtri);

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

        email = intentRicevuto.getStringExtra("email");
        password = intentRicevuto.getStringExtra("password");
        query = intentRicevuto.getStringExtra("query");
        astaSilenziosa = intentRicevuto.getBooleanExtra("astaSilenziosa", false);
        astaRibasso = intentRicevuto.getBooleanExtra("astaRibasso", false);
        data = intentRicevuto.getStringExtra("data");
        categoria1 = intentRicevuto.getBooleanExtra("categoria1", false);
        categoria2 = intentRicevuto.getBooleanExtra("categoria2", false);
        categoria3 = intentRicevuto.getBooleanExtra("categoria3", false);
        categoria4 = intentRicevuto.getBooleanExtra("categoria4", false);
        categoria5 = intentRicevuto.getBooleanExtra("categoria5", false);
        categoria6 = intentRicevuto.getBooleanExtra("categoria6", false);
        categoria7 = intentRicevuto.getBooleanExtra("categoria7", false);
        categoria8 = intentRicevuto.getBooleanExtra("categoria8", false);
        categoria9 = intentRicevuto.getBooleanExtra("categoria9", false);
        categoria10 = intentRicevuto.getBooleanExtra("categoria10", false);
        categoria11 = intentRicevuto.getBooleanExtra("categoria11", false);
        categoria12 = intentRicevuto.getBooleanExtra("categoria12", false);
        categoria13 = intentRicevuto.getBooleanExtra("categoria13", false);
        categoria14 = intentRicevuto.getBooleanExtra("categoria14", false);

        ordinaper = intentRicevuto.getStringExtra("ordinaper");

        startValue = intentRicevuto.getFloatExtra("startValue", 0);
        endValue = intentRicevuto.getFloatExtra("endValue", 1000);

        checkboxAstaSilenziosa.setChecked(astaSilenziosa);
        checkboxAstaAlRibasso.setChecked(astaRibasso);
        checkbox14.setChecked(categoria14);
        checkbox1.setChecked(categoria1);
        checkbox2.setChecked(categoria2);
        checkbox3.setChecked(categoria3);
        checkbox4.setChecked(categoria4);
        checkbox5.setChecked(categoria5);
        checkbox6.setChecked(categoria6);
        checkbox7.setChecked(categoria7);
        checkbox8.setChecked(categoria8);
        checkbox9.setChecked(categoria9);
        checkbox10.setChecked(categoria10);
        checkbox11.setChecked(categoria11);
        checkbox12.setChecked(categoria12);
        checkbox13.setChecked(categoria13);
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
        astaSilenziosa = checkboxAstaSilenziosa.isChecked();
        astaRibasso = checkboxAstaAlRibasso.isChecked();
        categoria1 = checkbox1.isChecked();
        categoria2 = checkbox2.isChecked();
        categoria3 = checkbox3.isChecked();
        categoria4 = checkbox4.isChecked();
        categoria5 = checkbox5.isChecked();
        categoria6 = checkbox6.isChecked();
        categoria7 = checkbox7.isChecked();
        categoria8 = checkbox8.isChecked();
        categoria9 = checkbox9.isChecked();
        categoria10 = checkbox10.isChecked();
        categoria11 = checkbox11.isChecked();
        categoria12 = checkbox12.isChecked();
        categoria13 = checkbox13.isChecked();
        categoria14 = checkbox14.isChecked();

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
        Intent intent = new Intent(activity45FiltriActivity.this, activity44SearchActivity.class);

        intent.putExtra("user", activity);
        intent.putExtra("astaSilenziosa", astaSilenziosa);
        intent.putExtra("astaRibasso", astaRibasso);
        intent.putExtra("data", data);
        intent.putExtra("categoria1", categoria1);
        intent.putExtra("categoria2", categoria2);
        intent.putExtra("categoria3", categoria3);
        intent.putExtra("categoria4", categoria4);
        intent.putExtra("categoria5", categoria5);
        intent.putExtra("categoria6", categoria6);
        intent.putExtra("categoria7", categoria7);
        intent.putExtra("categoria8", categoria8);
        intent.putExtra("categoria9", categoria9);
        intent.putExtra("categoria10", categoria10);
        intent.putExtra("categoria11", categoria11);
        intent.putExtra("categoria12", categoria12);
        intent.putExtra("categoria13", categoria13);
        intent.putExtra("categoria14", categoria14);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        intent.putExtra("query", query);

        intent.putExtra("ordinaper", ordinaper);
        intent.putExtra("startValue", startValue);
        intent.putExtra("endValue", endValue);
        return intent;
    }

    private void indietroFunction(){
        Intent intent = new Intent(activity45FiltriActivity.this, activity44SearchActivity.class);
        intent.putExtra("user", activity);
        intent.putExtra("astaSilenziosa", astaSilenziosa);
        intent.putExtra("astaRibasso", astaRibasso);
        intent.putExtra("data", data);
        intent.putExtra("categoria1", categoria1);
        intent.putExtra("categoria2", categoria2);
        intent.putExtra("categoria3", categoria3);
        intent.putExtra("categoria4", categoria4);
        intent.putExtra("categoria5", categoria5);
        intent.putExtra("categoria6", categoria6);
        intent.putExtra("categoria7", categoria7);
        intent.putExtra("categoria8", categoria8);
        intent.putExtra("categoria9", categoria9);
        intent.putExtra("categoria10", categoria10);
        intent.putExtra("categoria11", categoria11);
        intent.putExtra("categoria12", categoria12);
        intent.putExtra("categoria13", categoria13);
        intent.putExtra("categoria14", categoria14);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        intent.putExtra("query", query);

        intent.putExtra("ordinaper", ordinaper);
        intent.putExtra("startValue", startValue);
        intent.putExtra("endValue", endValue);

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
