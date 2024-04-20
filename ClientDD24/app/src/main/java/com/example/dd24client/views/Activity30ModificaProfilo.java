package com.example.dd24client.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import com.example.dd24client.model.UtenteModel;
import com.example.dd24client.network.ApiService;
import com.example.dd24client.network.RetrofitClient;
import com.example.dd24client.presenter.Presenter29ModificaProfilo;
import com.example.dd24client.R;
import com.example.dd24client.utils.MessageResponse;
import com.example.dd24client.utils.UtilsFunction;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

public class Activity30ModificaProfilo extends AppCompatActivity implements Activity30ModificaProfiloView {
    private FirebaseAnalytics mFirebaseAnalytics;
    private UtilsFunction function;
    private static int socialCount = 2;
    private String nome="", cognome="", nazionalita="", citta="", biografia="", socialLinks="", linkWeb="", email="", password="", foto="", imageConvertToSave="", url="";
    private TextView salvaModificheButton;
    private ImageView indietroImageView;
    private TextInputEditText linkWebEditText, linkSocial1EditText, linkSocial2EditText;
    private TextView aggiungiSocialTextView;
    private int lastElementId, lastElementIdEditText;
    private ImageButton emailToggle, emailToggle1, emailToggle2;
    private ConstraintLayout container;
    private Presenter29ModificaProfilo utentePresenter;
    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private Boolean continua=true;
    private static final String urlRegex = "^((https?:\\/\\/)?(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*))";
    private static final String socialRegex = "^(https?:\\/\\/)?(www.)?(facebook|twitter|instagram|linkedin|github|google).com\\/[a-zA-Z0-9_-]+\\/?$";
    private android.app.AlertDialog dialogoSalvataggio;
    private final List<TextInputEditText> editTextList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_30_modifica_profilo2);
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "Activity30ModificaProfilo", null);

        setComponents();
        setListener();
        setExtraParameters();
    }

    private void setComponents(){
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        function = new UtilsFunction();

        salvaModificheButton = findViewById(R.id.salvaModificheButton);
        indietroImageView = findViewById(R.id.indietroImageView);
        linkWebEditText = findViewById(R.id.editTextEmail);
        linkSocial1EditText = findViewById(R.id.editTextSocial1);
        linkSocial2EditText = findViewById(R.id.editTextSocial2);
        aggiungiSocialTextView = findViewById(R.id.aggiungiSocialTextView);
        container = findViewById(R.id.scrollViewContainer);
        emailToggle = findViewById(R.id.emailToggle);
        emailToggle1 = findViewById(R.id.emailToggle1);
        emailToggle2 = findViewById(R.id.emailToggle2);
        lastElementId = emailToggle2.getId();
        lastElementIdEditText = linkSocial2EditText.getId();
        editTextList.add(linkSocial1EditText);
        editTextList.add(linkSocial2EditText);
        utentePresenter = new Presenter29ModificaProfilo(this, apiService);

    }

    private void setListener() {
        function.setTouchListenerForAnimation(aggiungiSocialTextView, this);
        function.setTouchListenerForAnimation(salvaModificheButton, this);
        function.setTouchListenerForAnimation(indietroImageView, this);

        setupImageButtonListener(emailToggle1, linkSocial1EditText);
        setupImageButtonListener(emailToggle2, linkSocial2EditText);

        salvaModificheButton.setOnClickListener(v -> continuaFunction());

        indietroImageView.setOnClickListener(v -> {
            String messageString = "Cliccato 'indietro' in 30modifica";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            Intent intent = new Intent(Activity30ModificaProfilo.this, Activity29ModificaProfilo.class);
            raccogliExtras(intent);
        });

        aggiungiSocialTextView.setOnClickListener(v -> {
            String messageString = "Cliccato 'aggiungi social' in 30modifica";
            String logging = "[USABILITA' UTENTE]";
            Log.d(logging, messageString);

            if (socialCount < 4) {
                addSocialLinkBlock(container, this, lastElementId);
                if (socialCount == 4) {
                    aggiungiSocialTextView.setVisibility(View.GONE);
                    aggiungiSocialTextView.setVisibility(View.GONE);
                }
            }
        });

        linkWebEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String testoInserito = s.toString();
                linkWebEditText.setTextColor(Color.parseColor("#000000"));

                // Questo metodo viene invocato prima che il testo venga cambiato.
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    emailToggle.setImageResource(R.drawable.icon_basiccheck);
                    continua=false;
                } else if(testoInserito.matches(urlRegex)){
                    emailToggle.setImageResource(R.drawable.icon_okcheck);
                    continua=true;

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    emailToggle.setImageResource(R.drawable.icon_nocheck);
                    continua=false;

                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String testoInserito = s.toString();
                linkWebEditText.setTextColor(Color.parseColor("#000000"));

                // Questo metodo viene invocato mentre il testo sta cambiando.
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    emailToggle.setImageResource(R.drawable.icon_basiccheck);
                    continua=false;

                } else if(testoInserito.matches(urlRegex)){
                    emailToggle.setImageResource(R.drawable.icon_okcheck);
                    continua=true;

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    emailToggle.setImageResource(R.drawable.icon_nocheck);
                    continua=false;

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Questo metodo viene invocato subito dopo che il testo è stato cambiato.

                String testoInserito = s.toString();
                linkWebEditText.setTextColor(Color.parseColor("#000000"));

                // Qui puoi inserire il tuo controllo per verificare se il testo inserito è giusto.
                // Ad esempio, supponiamo che il testo sia "giusto" se non è vuoto e se contiene la parola "esempio".
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    emailToggle.setImageResource(R.drawable.icon_basiccheck);
                    continua=false;

                } else if(testoInserito.matches(urlRegex)){
                    emailToggle.setImageResource(R.drawable.icon_okcheck);
                    continua=true;

                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    emailToggle.setImageResource(R.drawable.icon_nocheck);
                    continua=false;

                }
            }
        });
    }

    private void setExtraParameters(){
        Intent intentRicevuto = getIntent();

        nome = intentRicevuto.getStringExtra("nome");
        cognome = intentRicevuto.getStringExtra("cognome");
        nazionalita = intentRicevuto.getStringExtra("nazionalita");
        citta = intentRicevuto.getStringExtra("citta");
        biografia = intentRicevuto.getStringExtra("biografia");
        linkWeb = intentRicevuto.getStringExtra("linkWeb");
        socialLinks = intentRicevuto.getStringExtra("socialLinks");
        email = intentRicevuto.getStringExtra("email");
        password = intentRicevuto.getStringExtra("password");
        foto = intentRicevuto.getStringExtra("image");
        imageConvertToSave = intentRicevuto.getStringExtra("imageToSave");
        url = intentRicevuto.getStringExtra("url");

        linkWebEditText.setText(linkWeb);
        setupSocialLinks(socialLinks, container, this);

        if (socialCount == 4) {
            aggiungiSocialTextView.setVisibility(View.GONE);
            aggiungiSocialTextView.setVisibility(View.GONE);
        }


    }

    public void setupSocialLinks(String socialLinks, ConstraintLayout layout, Context context) {
        // Dividi la stringa socialLinks in base agli spazi
        String[] links = socialLinks.split("\\s+");

        // Controlla quanti link sono presenti
        if (links.length > 0) {
            linkSocial1EditText.setText(links[0]);
            if (links.length > 1) {
                linkSocial2EditText.setText(links[1]);
            }

            // Per ogni link oltre i primi due, aggiungi un nuovo blocco di EditText e imposta il testo
            for (int i = 2; i < links.length; i++) {
                // Aggiungi dinamicamente il blocco EditText
                addSocialLinkBlock(layout, context, lastElementId);
                // Il nuovo EditText sarà l'ultimo aggiunto, quindi otteniamo il suo riferimento
                // Nota: Questo passo richiede che tu mantenga un riferimento all'ultimo EditText aggiunto
                // Potresti voler modificare addSocialLinkBlock per restituire l'ID del nuovo EditText
                TextInputEditText newEditText = findViewById(lastElementIdEditText); // Questo è un esempio, necessita di logica per ottenere l'effettivo nuovo EditText
                newEditText.setText(links[i]);
                // Aggiorna l'ID dell'ultimo elemento con quello del nuovo EditText aggiunto
                lastElementIdEditText = newEditText.getId();
            }
        }
    }

    private void addSocialLinkBlock(ConstraintLayout layout, Context context, int previousElementId) {
        socialCount++;
        int idTextView = View.generateViewId();
        int idEditText = View.generateViewId();
        int idImageButton = View.generateViewId();
        int idDividerView = View.generateViewId();

        // TextView: Label del link social
        TextView textView = new TextView(context);
        textView.setId(idTextView);
        textView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        textView.setText(String.format("Link social %d", socialCount));
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        textView.setTypeface(null, Typeface.BOLD);
        ConstraintLayout.LayoutParams lpTextView = (ConstraintLayout.LayoutParams) textView.getLayoutParams();
        lpTextView.setMargins(0, convertDpToPx(20, context), 0, 0);
        lpTextView.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        lpTextView.topToBottom = previousElementId;
        layout.addView(textView, lpTextView);

        // TextInputEditText
        TextInputEditText editText = new TextInputEditText(context);
        editText.setId(idEditText);
        ConstraintLayout.LayoutParams lpEditText = new ConstraintLayout.LayoutParams(0, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        editText.setLayoutParams(lpEditText);
        editText.setHint("www.example.com/tuo_username");
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        editText.setBackground(null);
        editText.setPadding(editText.getPaddingLeft(), editText.getPaddingTop(), editText.getPaddingRight(), convertDpToPx(8, context));
        lpEditText.topToBottom = idTextView;
        lpEditText.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        lpEditText.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        layout.addView(editText, lpEditText);

        // ImageButton
        ImageButton imageButton = new ImageButton(context);
        imageButton.setId(idImageButton);
        ConstraintLayout.LayoutParams lpImageButton = new ConstraintLayout.LayoutParams(
                convertDpToPx(50, context), // Larghezza 50dp convertiti in pixel
                convertDpToPx(50, context)  // Altezza 50dp convertiti in pixel
        );
        imageButton.setLayoutParams(lpImageButton);
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
        imageButton.setBackground(ContextCompat.getDrawable(context, typedValue.resourceId));
        imageButton.setImageResource(R.drawable.icon_basiccheck);

        imageButton.setLayoutParams(lpImageButton);
        imageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageButton.setAdjustViewBounds(true);
        int paddingInPx = convertDpToPx(15, context);
        imageButton.setPadding(paddingInPx, paddingInPx, paddingInPx, paddingInPx);
        lpImageButton.topToTop = idEditText;
        lpImageButton.bottomToBottom = idEditText;
        lpImageButton.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        layout.addView(imageButton, lpImageButton);

        // View: Divider
        View dividerView = new View(context);
        dividerView.setId(idDividerView);
        dividerView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, convertDpToPx(2, context)));
        dividerView.setBackgroundColor(Color.parseColor("#CCCCCC"));
        ConstraintLayout.LayoutParams lpDividerView = (ConstraintLayout.LayoutParams) dividerView.getLayoutParams();
        lpDividerView.topToBottom = idImageButton;
        lpDividerView.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        lpDividerView.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        layout.addView(dividerView, lpDividerView);

        // Aggiornare le constraint (es. per editText collegato a imageButton)
        lpEditText.endToStart = idImageButton;
        editText.setLayoutParams(lpEditText);
        lastElementId = idImageButton;
        lastElementIdEditText = idEditText;
        // Subito dopo aver aggiunto l'ImageButton e il TextInputEditText alla vista/layout
        setupImageButtonListener(imageButton, editText);

        // Da qualche parte nella tua classe, dichiara una lista per tenere traccia dei TextInputEditText

// Quando crei un nuovo TextInputEditText, aggiungilo alla lista
        editTextList.add(editText);

        // Aggiungere altre regole di constraint se necessario
    }

    private int convertDpToPx(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    private void setupImageButtonListener(final ImageButton imageButton, final TextInputEditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String testoInserito = s.toString();
                editText.setTextColor(Color.parseColor("#000000"));

                // Questo metodo viene invocato prima che il testo venga cambiato.
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    imageButton.setImageResource(R.drawable.icon_basiccheck);
                    continua=false;
                } else if(testoInserito.matches(socialRegex)){
                    imageButton.setImageResource(R.drawable.icon_okcheck);
                    continua=true;
                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    imageButton.setImageResource(R.drawable.icon_nocheck);
                    continua=false;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String testoInserito = s.toString();
                editText.setTextColor(Color.parseColor("#000000"));

                // Questo metodo viene invocato mentre il testo sta cambiando.
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    imageButton.setImageResource(R.drawable.icon_basiccheck);
                    continua=false;
                } else if(testoInserito.matches(socialRegex)){
                    imageButton.setImageResource(R.drawable.icon_okcheck);
                    continua=true;
                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    imageButton.setImageResource(R.drawable.icon_nocheck);
                    continua=false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Questo metodo viene invocato subito dopo che il testo è stato cambiato.

                String testoInserito = s.toString();
                editText.setTextColor(Color.parseColor("#000000"));

                // Qui puoi inserire il tuo controllo per verificare se il testo inserito è giusto.
                // Ad esempio, supponiamo che il testo sia "giusto" se non è vuoto e se contiene la parola "esempio".
                if (testoInserito.isEmpty()) {
                    // Il testo inserito è giusto secondo i criteri definiti.
                    // Puoi fare qualcosa qui, ad esempio mostrare un messaggio o abilitare un bottone.
                    imageButton.setImageResource(R.drawable.icon_basiccheck);
                    continua=false;
                } else if(testoInserito.matches(socialRegex)){
                    imageButton.setImageResource(R.drawable.icon_okcheck);
                    continua=true;
                }else {
                    // Il testo inserito non è giusto.
                    // Puoi gestire questo caso come preferisci, ad esempio mostrando un messaggio di errore.
                    imageButton.setImageResource(R.drawable.icon_nocheck);
                    continua=false;
                }
            }
        });
    }

    private void raccogliExtras(Intent intent) {
        socialLinks = "";
        String linkWeb = Objects.requireNonNull(linkWebEditText.getText()).toString();
        for (TextInputEditText editText : editTextList) {
            String testo = Objects.requireNonNull(editText.getText()).toString();
            if (!testo.isEmpty()) {
                socialLinks = testo + " " + socialLinks;
            }
        }
        socialCount = 2;

        // Passo i dati ricevuti da SignupActivity1
        intent.putExtra("nome", nome);
        intent.putExtra("cognome", cognome);
        intent.putExtra("nazionalita", nazionalita);
        intent.putExtra("citta", citta);
        intent.putExtra("biografia", biografia);
        // Passo i nuovi dati raccolti in SignupActivity2
        intent.putExtra("linkWeb", linkWeb);
        intent.putExtra("socialLinks", socialLinks);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        intent.putExtra("image", foto);
        intent.putExtra("imageToSave", imageConvertToSave);
        intent.putExtra("url", url);

        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    private void continuaFunction() {
        String messageString = "Cliccato 'continua' in 30modifica";
        String logging = "[USABILITA' UTENTE]";
        Log.d(logging, messageString);

        String linkWeb="";
        socialLinks = "";

        if(continua) {
            linkWeb = Objects.requireNonNull(linkWebEditText.getText()).toString();
            for (TextInputEditText editText : editTextList) {
                String testo = Objects.requireNonNull(editText.getText()).toString();
                if (!testo.isEmpty()) {
                    socialLinks = testo + " " + socialLinks;
                }
            }
            socialCount = 2;
        }

        String immagine;
        try {
            File imageFile = new File(imageConvertToSave);
            Bitmap originalBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());

            // Ridimensiona l'immagine
            int newWidth = 480; // Definisci la nuova larghezza
            int newHeight = (originalBitmap.getHeight() * newWidth) / originalBitmap.getWidth(); // Calcola la nuova altezza mantenendo il rapporto
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);

            // Comprimi l'immagine
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 75, outputStream); // Comprimi con qualità al 75%

            // Converti in array di byte e codifica in base64
            byte[] imageBytes = outputStream.toByteArray();
            immagine = Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            immagine = null;
        }

        UtenteModel utente = new UtenteModel(email, password, nome, cognome, immagine, nazionalita, citta, biografia, linkWeb, socialLinks);
        showPopupMessageSalvataggio(); // Mostra il popup prima di iniziare l'operazione di salvataggio
        utentePresenter.resetUtente(utente);

    }

    @Override
    public void onResetUtenteFailure(MessageResponse errore_password) {
        chiudiPopupMessageSalvataggio(); // Chiudi il popup in caso di successo
        Toast.makeText(Activity30ModificaProfilo.this, errore_password.getMessage(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResetUtenteSuccess(MessageResponse message) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "30");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "modifica  profilo");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        chiudiPopupMessageSalvataggio(); // Chiudi il popup in caso di successo
        Toast.makeText(Activity30ModificaProfilo.this, message.getMessage(),
                Toast.LENGTH_SHORT).show();
        overridePendingTransition(0, 0);
        finish();
    }

    public void showPopupMessageSalvataggio() {
        runOnUiThread(() -> {
            if (dialogoSalvataggio != null && dialogoSalvataggio.isShowing()) {
                return; // Il dialogo è già mostrato, non fare nulla
            }

            LayoutInflater inflater = LayoutInflater.from(Activity30ModificaProfilo.this);
            View customView = inflater.inflate(R.layout.activity_50_salvataggio, null);

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity30ModificaProfilo.this);
            builder.setCustomTitle(customView)
                    .setCancelable(false);

            dialogoSalvataggio = builder.create();
            if (!isFinishing() && dialogoSalvataggio != null && !dialogoSalvataggio.isShowing()) {
                dialogoSalvataggio.show();
            }
        });
    }

    public void chiudiPopupMessageSalvataggio() {
        runOnUiThread(() -> {
            if (dialogoSalvataggio != null && dialogoSalvataggio.isShowing()) {
                dialogoSalvataggio.dismiss();
            }
        });
    }
}
