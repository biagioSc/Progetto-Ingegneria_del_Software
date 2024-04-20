package com.example.dd24client.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dd24client.model.AstaribassoModel;
import com.example.dd24client.model.AstasilenziosaModel;
import com.example.dd24client.R;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Locale;

public class CreateCardFunction {


    public Object[] cardApertaRibassoAcquirente(Context activityContext, AstaribassoModel astaSelezionata) {
        CardView cardView = new CardView(activityContext);
        ConstraintLayout constraintLayoutCard = new ConstraintLayout(activityContext);
        ImageView imageProduct = new ImageView(activityContext);
        TextView textProductName = new TextView(activityContext);
        ConstraintLayout constraintLayoutInterno = new ConstraintLayout(activityContext);
        TextView textPrice = new TextView(activityContext);
        ImageView iconClock = new ImageView(activityContext);
        TextView textTimer = new TextView(activityContext);
        TextView textStateAuction = new TextView(activityContext);

        //Setting id
        cardView.setId(astaSelezionata.getId());
        constraintLayoutCard.setId(View.generateViewId());
        imageProduct.setId(View.generateViewId());
        textProductName.setId(View.generateViewId());
        constraintLayoutInterno.setId(View.generateViewId());
        textPrice.setId(View.generateViewId());
        iconClock.setId(View.generateViewId());
        textTimer.setId(View.generateViewId());
        textStateAuction.setId(View.generateViewId());

        //Setting cardView
        ConstraintLayout.LayoutParams paramsCardView = new ConstraintLayout.LayoutParams(
                convertDpToPx(175, activityContext), // width
                convertDpToPx(240, activityContext)  // height
        );

        paramsCardView.setMargins(convertDpToPx(8, activityContext), convertDpToPx(8, activityContext), convertDpToPx(8, activityContext), convertDpToPx(8, activityContext));
        cardView.setLayoutParams(paramsCardView);
        cardView.setRadius(convertDpToPx(20, activityContext));
        cardView.setCardElevation(convertDpToPx(8, activityContext));
        cardView.setCardBackgroundColor(ContextCompat.getColor(activityContext, R.color.white));

        //Setting constraintLayoutCard
        ConstraintLayout.LayoutParams paramsConstraintLayoutCard = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );
        constraintLayoutCard.setLayoutParams(paramsConstraintLayoutCard);

        //Setting imageProduct
        ConstraintLayout.LayoutParams paramsImageProduct = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                convertDpToPx(120, activityContext)
        );
        paramsImageProduct.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.setMargins(0, convertDpToPx(0, activityContext), 0, convertDpToPx(2, activityContext));
        imageProduct.setLayoutParams(paramsImageProduct);
        try {
            RequestOptions requestOptions = new RequestOptions()
                    .centerCrop();

            Glide.with(activityContext)
                    .load(astaSelezionata.getImmagineprodotto())
                    .apply(requestOptions)
                    .into(imageProduct);
        }catch (Exception e){
            imageProduct.setImageResource(R.drawable.image_notfound);
        }

        imageProduct.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageProduct.setAdjustViewBounds(true);
        constraintLayoutCard.addView(imageProduct);

        //Setting textProductName
        ConstraintLayout.LayoutParams paramsTextProductName = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextProductName.bottomToTop = constraintLayoutInterno.getId();
        paramsTextProductName.topToBottom = imageProduct.getId();
        paramsTextProductName.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.setMargins(0, convertDpToPx(2, activityContext), 0, convertDpToPx(2, activityContext));
        textProductName.setLayoutParams(paramsTextProductName);
        setTextViewAttributes(textProductName, astaSelezionata.getTitolo(), 16, R.color.black, activityContext);
        textProductName.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textProductName);

        //Setting constraintLayoutInterno
        ConstraintLayout.LayoutParams paramsConstraintLayoutInterno = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsConstraintLayoutInterno.setMargins(0, convertDpToPx(2, activityContext), 0, convertDpToPx(2, activityContext));
        paramsConstraintLayoutInterno.bottomToTop = textStateAuction.getId();
        paramsConstraintLayoutInterno.topToBottom = textProductName.getId();
        paramsConstraintLayoutInterno.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsConstraintLayoutInterno.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        constraintLayoutInterno.setLayoutParams(paramsConstraintLayoutInterno);
        constraintLayoutCard.addView(constraintLayoutInterno);

        //Setting textPrice
        ConstraintLayout.LayoutParams paramsTextPrice = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextPrice.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextPrice.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextPrice.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextPrice.endToStart = iconClock.getId();
        textPrice.setLayoutParams(paramsTextPrice);
        setTextViewAttributes(textPrice, formatDouble(astaSelezionata.getUltimoprezzo())+" €", 16, R.color.white, activityContext);
        textPrice.setTypeface(null, Typeface.BOLD);
        textPrice.setPadding(convertDpToPx(8, activityContext),convertDpToPx(3, activityContext),convertDpToPx(8, activityContext),convertDpToPx(3, activityContext));
        textPrice.setBackgroundResource(R.drawable.text_info_celeste);
        constraintLayoutInterno.addView(textPrice);

        //Setting iconClock
        ConstraintLayout.LayoutParams paramsIconClock = new ConstraintLayout.LayoutParams(
                convertDpToPx(20, activityContext),
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );
        paramsIconClock.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsIconClock.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsIconClock.startToEnd = textPrice.getId();
        paramsIconClock.endToStart = textTimer.getId();
        paramsIconClock.setMargins(convertDpToPx(10, activityContext), 0, 0, 0);
        iconClock.setLayoutParams(paramsIconClock);
        iconClock.setImageResource(R.drawable.icon_orologio);
        iconClock.setScaleType(ImageView.ScaleType.FIT_CENTER);
        iconClock.setAdjustViewBounds(true);
        constraintLayoutInterno.addView(iconClock); // Aggiungi l'ImageView al ConstraintLayout

        //Setting textTimer
        ConstraintLayout.LayoutParams paramsTextTimer = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextTimer.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextTimer.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextTimer.startToEnd = iconClock.getId();
        paramsTextTimer.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextTimer.setMargins(convertDpToPx(5, activityContext), 0, 0, 0);
        textTimer.setLayoutParams(paramsTextTimer);
        setTextViewAttributes(textTimer, "-", 16, R.color.lightOrange, activityContext);
        textTimer.setTypeface(null, Typeface.BOLD);
        constraintLayoutInterno.addView(textTimer);

        //Setting textStateAuction
        ConstraintLayout.LayoutParams paramsTextStateAuction = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextStateAuction.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.topToBottom = constraintLayoutInterno.getId();
        paramsTextStateAuction.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.setMargins(0, convertDpToPx(2, activityContext), 0, convertDpToPx(7, activityContext));
        textStateAuction.setLayoutParams(paramsTextStateAuction);
        textStateAuction.setPadding(convertDpToPx(16, activityContext),convertDpToPx(5, activityContext),convertDpToPx(16, activityContext),convertDpToPx(5, activityContext));
        setTextViewAttributes(textStateAuction, "Asta al Ribasso", 14, R.color.customBlue, activityContext);
        textStateAuction.setBackgroundResource(R.drawable.text_info_basic);
        textStateAuction.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textStateAuction);

        cardView.addView(constraintLayoutCard);

        Object[] result = new Object[3];
        result[0] = cardView;
        result[1] = textTimer;
        result[2] = textPrice;

        return result;
    } //ok

    public Object[] cardApertaSilenziosaAcquirente(Context activityContext, AstasilenziosaModel astaSelezionata) {
        CardView cardView = new CardView(activityContext);
        ConstraintLayout constraintLayoutCard = new ConstraintLayout(activityContext);
        ImageView imageProduct = new ImageView(activityContext);
        TextView textProductName = new TextView(activityContext);
        TextView textDate = new TextView(activityContext);
        TextView textStateAuction = new TextView(activityContext);

        //Setting id
        cardView.setId(astaSelezionata.getId());
        constraintLayoutCard.setId(View.generateViewId());
        imageProduct.setId(View.generateViewId());
        textProductName.setId(View.generateViewId());
        textDate.setId(View.generateViewId());
        textStateAuction.setId(View.generateViewId());

        //Setting cardView
        ConstraintLayout.LayoutParams paramsCardView = new ConstraintLayout.LayoutParams(
                convertDpToPx(175, activityContext), // width
                convertDpToPx(240, activityContext)  // height
        );
        paramsCardView.setMargins(convertDpToPx(8, activityContext), convertDpToPx(8, activityContext), convertDpToPx(8, activityContext), convertDpToPx(8, activityContext));
        cardView.setLayoutParams(paramsCardView);
        cardView.setRadius(convertDpToPx(20, activityContext));
        cardView.setCardElevation(convertDpToPx(8, activityContext));
        cardView.setCardBackgroundColor(ContextCompat.getColor(activityContext, R.color.white));

        //Setting constraintLayoutCard
        ConstraintLayout.LayoutParams paramsConstraintLayoutCard = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );
        constraintLayoutCard.setLayoutParams(paramsConstraintLayoutCard);

        //Setting imageProduct
        ConstraintLayout.LayoutParams paramsImageProduct = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                convertDpToPx(120, activityContext)
        );
        paramsImageProduct.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.setMargins(0, convertDpToPx(0, activityContext), 0, convertDpToPx(2, activityContext));
        imageProduct.setLayoutParams(paramsImageProduct);
        try {
            RequestOptions requestOptions = new RequestOptions()
                    .centerCrop();

            Glide.with(activityContext)
                    .load(astaSelezionata.getImmagineprodotto())
                    .apply(requestOptions)
                    .into(imageProduct);
        }catch (Exception e){
            imageProduct.setImageResource(R.drawable.image_notfound);
        }
        imageProduct.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageProduct.setAdjustViewBounds(true);
        constraintLayoutCard.addView(imageProduct);

        //Setting textProductName
        ConstraintLayout.LayoutParams paramsTextProductName = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextProductName.bottomToTop = textDate.getId();
        paramsTextProductName.topToBottom = imageProduct.getId();
        paramsTextProductName.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.setMargins(0, convertDpToPx(2, activityContext), 0, convertDpToPx(2, activityContext));
        textProductName.setLayoutParams(paramsTextProductName);
        setTextViewAttributes(textProductName, astaSelezionata.getTitolo(), 16, R.color.black, activityContext);
        textProductName.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textProductName);

        //Setting textDate
        ConstraintLayout.LayoutParams paramsTextDate = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextDate.bottomToTop = textStateAuction.getId();
        paramsTextDate.topToBottom = textProductName.getId();
        paramsTextDate.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextDate.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextDate.setMargins(0, convertDpToPx(2, activityContext), 0, convertDpToPx(2, activityContext));
        textDate.setLayoutParams(paramsTextDate);
        setTextViewAttributes(textDate, String.valueOf(astaSelezionata.getDatafineasta()), 16, R.color.lightOrange, activityContext);
        textDate.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textDate);

        //Setting textStateAuction
        ConstraintLayout.LayoutParams paramsTextStateAuction = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextStateAuction.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.topToBottom = textDate.getId();
        paramsTextStateAuction.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.setMargins(0, convertDpToPx(2, activityContext), 0, convertDpToPx(7, activityContext));
        textStateAuction.setLayoutParams(paramsTextStateAuction);
        textStateAuction.setPadding(convertDpToPx(16, activityContext),convertDpToPx(5, activityContext),convertDpToPx(16, activityContext),convertDpToPx(5, activityContext));
        setTextViewAttributes(textStateAuction, "Asta Silenziosa", 14, R.color.customBlue, activityContext);
        textStateAuction.setBackgroundResource(R.drawable.text_info_basic);
        textStateAuction.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textStateAuction);

        cardView.addView(constraintLayoutCard);

        Object[] result = new Object[2];
        result[0] = cardView;
        result[1] = textDate;

        return result;
    } //ok

    public CardView cardChiusaRibassoAcquirente(Context activityContext, AstaribassoModel astaSelezionata) {
        CardView cardView = new CardView(activityContext);
        ConstraintLayout constraintLayoutCard = new ConstraintLayout(activityContext);
        ImageView imageProduct = new ImageView(activityContext);
        TextView textProductName = new TextView(activityContext);
        TextView textStato = new TextView(activityContext);
        TextView textTypeAuction = new TextView(activityContext);

        //Setting id
        cardView.setId(astaSelezionata.getId());
        constraintLayoutCard.setId(View.generateViewId());
        imageProduct.setId(View.generateViewId());
        textProductName.setId(View.generateViewId());
        textStato.setId(View.generateViewId());
        textTypeAuction.setId(View.generateViewId());

        //Setting cardView
        ConstraintLayout.LayoutParams paramsCardView = new ConstraintLayout.LayoutParams(
                convertDpToPx(175, activityContext), // width
                convertDpToPx(240, activityContext)  // height
        );

        paramsCardView.setMargins(convertDpToPx(8, activityContext), convertDpToPx(8, activityContext), convertDpToPx(8, activityContext), convertDpToPx(8, activityContext));
        cardView.setLayoutParams(paramsCardView);
        cardView.setRadius(convertDpToPx(20, activityContext));
        cardView.setCardElevation(convertDpToPx(8, activityContext));
        cardView.setCardBackgroundColor(ContextCompat.getColor(activityContext, R.color.white));

        //Setting constraintLayoutCard
        ConstraintLayout.LayoutParams paramsConstraintLayoutCard = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );
        constraintLayoutCard.setLayoutParams(paramsConstraintLayoutCard);

        //Setting imageProduct
        ConstraintLayout.LayoutParams paramsImageProduct = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                convertDpToPx(120, activityContext)
        );
        paramsImageProduct.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.setMargins(0, convertDpToPx(0, activityContext), 0, convertDpToPx(2, activityContext));
        imageProduct.setLayoutParams(paramsImageProduct);

        try {
            RequestOptions requestOptions = new RequestOptions()
                    .centerCrop();

            Glide.with(activityContext)
                    .load(astaSelezionata.getImmagineprodotto())
                    .apply(requestOptions)
                    .into(imageProduct);
        }catch (Exception e){
            imageProduct.setImageResource(R.drawable.image_notfound);
        }

        imageProduct.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageProduct.setAdjustViewBounds(true);
        constraintLayoutCard.addView(imageProduct);

        //Setting textProductName
        ConstraintLayout.LayoutParams paramsTextProductName = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextProductName.bottomToTop = textStato.getId();
        paramsTextProductName.topToBottom = imageProduct.getId();
        paramsTextProductName.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.setMargins(0, convertDpToPx(2, activityContext), 0, convertDpToPx(2, activityContext));
        textProductName.setLayoutParams(paramsTextProductName);
        setTextViewAttributes(textProductName, astaSelezionata.getTitolo(), 16, R.color.black, activityContext);
        textProductName.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textProductName);

        //Setting textStato
        ConstraintLayout.LayoutParams paramsTextStato = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextStato.bottomToTop = textTypeAuction.getId();
        paramsTextStato.topToBottom = textProductName.getId();
        paramsTextStato.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStato.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.setMargins(0, convertDpToPx(2, activityContext), 0, convertDpToPx(7, activityContext));
        textStato.setLayoutParams(paramsTextStato);
        textStato.setPadding(convertDpToPx(16, activityContext),convertDpToPx(5, activityContext),convertDpToPx(16, activityContext),convertDpToPx(5, activityContext));
        setTextViewAttributes(textStato, "CHIUSA", 14, R.color.white, activityContext);
        textStato.setBackgroundResource(R.drawable.text_info_rosso);
        textStato.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textStato);

        //Setting textTypeAuction
        ConstraintLayout.LayoutParams paramsTextTypeAuction = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextTypeAuction.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextTypeAuction.topToBottom = textStato.getId();
        paramsTextTypeAuction.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextTypeAuction.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.setMargins(0, convertDpToPx(2, activityContext), 0, convertDpToPx(7, activityContext));
        textTypeAuction.setLayoutParams(paramsTextTypeAuction);
        textTypeAuction.setPadding(convertDpToPx(16, activityContext),convertDpToPx(5, activityContext),convertDpToPx(16, activityContext),convertDpToPx(5, activityContext));
        setTextViewAttributes(textTypeAuction, "Asta al Ribasso", 14, R.color.customBlue, activityContext);
        textTypeAuction.setBackgroundResource(R.drawable.text_info_basic);
        textTypeAuction.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textTypeAuction);

        cardView.addView(constraintLayoutCard);

        return cardView;
    } //ok

    public CardView cardChiusaSilenziosaAcquirente(Context activityContext, AstasilenziosaModel astaSelezionata) {
        CardView cardView = new CardView(activityContext);
        ConstraintLayout constraintLayoutCard = new ConstraintLayout(activityContext);
        ImageView imageProduct = new ImageView(activityContext);
        TextView textProductName = new TextView(activityContext);
        TextView textStato = new TextView(activityContext);
        TextView textTypeAuction = new TextView(activityContext);

        //Setting id
        cardView.setId(astaSelezionata.getId());
        constraintLayoutCard.setId(View.generateViewId());
        imageProduct.setId(View.generateViewId());
        textProductName.setId(View.generateViewId());
        textStato.setId(View.generateViewId());
        textTypeAuction.setId(View.generateViewId());

        //Setting cardView
        ConstraintLayout.LayoutParams paramsCardView = new ConstraintLayout.LayoutParams(
                convertDpToPx(175, activityContext), // width
                convertDpToPx(240, activityContext)  // height
        );

        paramsCardView.setMargins(convertDpToPx(8, activityContext), convertDpToPx(8, activityContext), convertDpToPx(8, activityContext), convertDpToPx(8, activityContext));
        cardView.setLayoutParams(paramsCardView);
        cardView.setRadius(convertDpToPx(20, activityContext));
        cardView.setCardElevation(convertDpToPx(8, activityContext));
        cardView.setCardBackgroundColor(ContextCompat.getColor(activityContext, R.color.white));

        //Setting constraintLayoutCard
        ConstraintLayout.LayoutParams paramsConstraintLayoutCard = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );
        constraintLayoutCard.setLayoutParams(paramsConstraintLayoutCard);

        //Setting imageProduct
        ConstraintLayout.LayoutParams paramsImageProduct = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                convertDpToPx(120, activityContext)
        );
        paramsImageProduct.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.setMargins(0, convertDpToPx(0, activityContext), 0, convertDpToPx(2, activityContext));
        imageProduct.setLayoutParams(paramsImageProduct);
        try {
            RequestOptions requestOptions = new RequestOptions()
                    .centerCrop();

            Glide.with(activityContext)
                    .load(astaSelezionata.getImmagineprodotto())
                    .apply(requestOptions)
                    .into(imageProduct);
        }catch (Exception e){
            imageProduct.setImageResource(R.drawable.image_notfound);
        }

        imageProduct.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageProduct.setAdjustViewBounds(true);
        constraintLayoutCard.addView(imageProduct);

        //Setting textProductName
        ConstraintLayout.LayoutParams paramsTextProductName = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextProductName.bottomToTop = textStato.getId();
        paramsTextProductName.topToBottom = imageProduct.getId();
        paramsTextProductName.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.setMargins(0, convertDpToPx(2, activityContext), 0, convertDpToPx(2, activityContext));
        textProductName.setLayoutParams(paramsTextProductName);
        setTextViewAttributes(textProductName, astaSelezionata.getTitolo(), 16, R.color.black, activityContext);
        textProductName.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textProductName);

        //Setting textStato
        ConstraintLayout.LayoutParams paramsTextStato = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextStato.bottomToTop = textTypeAuction.getId();
        paramsTextStato.topToBottom = textProductName.getId();
        paramsTextStato.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStato.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.setMargins(0, convertDpToPx(2, activityContext), 0, convertDpToPx(7, activityContext));
        textStato.setLayoutParams(paramsTextStato);
        textStato.setPadding(convertDpToPx(16, activityContext),convertDpToPx(5, activityContext),convertDpToPx(16, activityContext),convertDpToPx(5, activityContext));
        setTextViewAttributes(textStato, "CHIUSA", 14, R.color.white, activityContext);
        textStato.setBackgroundResource(R.drawable.text_info_rosso);
        textStato.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textStato);

        //Setting textTypeAuction
        ConstraintLayout.LayoutParams paramsTextTypeAuction = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextTypeAuction.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextTypeAuction.topToBottom = textStato.getId();
        paramsTextTypeAuction.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextTypeAuction.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.setMargins(0, convertDpToPx(2, activityContext), 0, convertDpToPx(7, activityContext));
        textTypeAuction.setLayoutParams(paramsTextTypeAuction);
        textTypeAuction.setPadding(convertDpToPx(16, activityContext),convertDpToPx(5, activityContext),convertDpToPx(16, activityContext),convertDpToPx(5, activityContext));
        setTextViewAttributes(textTypeAuction, "Asta Silenziosa", 14, R.color.customBlue, activityContext);
        textTypeAuction.setBackgroundResource(R.drawable.text_info_basic);
        textTypeAuction.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textTypeAuction);

        cardView.addView(constraintLayoutCard);

        return cardView;
    } //ok

    public CardView cardRifiutataSilenziosaAcquirente(AstasilenziosaModel asta, Context context) {
        CardView cardView = new CardView(context);
        ConstraintLayout constraintLayoutCard = new ConstraintLayout(context);
        ImageView imageProduct = new ImageView(context);
        TextView textProductName = new TextView(context);
        TextView textOfferta = new TextView(context);
        TextView textStateAuction = new TextView(context);

        //Setting id
        cardView.setId(asta.getId());
        constraintLayoutCard.setId(View.generateViewId());
        imageProduct.setId(View.generateViewId());
        textProductName.setId(View.generateViewId());
        textOfferta.setId(View.generateViewId());
        textStateAuction.setId(View.generateViewId());

        //Setting cardView
        ConstraintLayout.LayoutParams paramsCardView = new ConstraintLayout.LayoutParams(
                convertDpToPx(175, context), // width
                convertDpToPx(240, context)  // height
        );

        paramsCardView.setMargins(convertDpToPx(8, context), convertDpToPx(8, context), convertDpToPx(8, context), convertDpToPx(8, context));
        cardView.setLayoutParams(paramsCardView);
        cardView.setRadius(convertDpToPx(20, context));
        cardView.setCardElevation(convertDpToPx(8, context));
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));

        //Setting constraintLayoutCard
        ConstraintLayout.LayoutParams paramsConstraintLayoutCard = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );
        constraintLayoutCard.setLayoutParams(paramsConstraintLayoutCard);

        //Setting imageProduct
        ConstraintLayout.LayoutParams paramsImageProduct = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                convertDpToPx(120, context)
        );
        paramsImageProduct.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.setMargins(0, convertDpToPx(0, context), 0, convertDpToPx(2, context));
        imageProduct.setLayoutParams(paramsImageProduct);
        try {
            RequestOptions requestOptions = new RequestOptions()
                    .centerCrop();

            Glide.with(context)
                    .load(asta.getImmagineprodotto())
                    .apply(requestOptions)
                    .into(imageProduct);
        }catch (Exception e){
            imageProduct.setImageResource(R.drawable.image_notfound);
        }

        imageProduct.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageProduct.setAdjustViewBounds(true);
        constraintLayoutCard.addView(imageProduct);

        //Setting textProductName
        ConstraintLayout.LayoutParams paramsTextProductName = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextProductName.bottomToTop = textOfferta.getId();
        paramsTextProductName.topToBottom = imageProduct.getId();
        paramsTextProductName.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(2, context));
        textProductName.setLayoutParams(paramsTextProductName);
        setTextViewAttributes(textProductName, asta.getTitolo(), 16, R.color.black, context);
        textProductName.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textProductName);

        //Setting textOfferta
        ConstraintLayout.LayoutParams paramstextOfferta = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramstextOfferta.bottomToTop = textStateAuction.getId();
        paramstextOfferta.topToBottom = textProductName.getId();
        paramstextOfferta.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramstextOfferta.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramstextOfferta.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(2, context));
        textOfferta.setLayoutParams(paramstextOfferta);
        textOfferta.setText("Offerta: " + formatDouble(asta.getPrezzoofferto())+ " €");
        textOfferta.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textOfferta.setTextColor(ContextCompat.getColor(context, R.color.black));
        textOfferta.setGravity(Gravity.CENTER_VERTICAL);
        textOfferta.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textOfferta.setBackgroundResource(R.drawable.text_info_basic);
        textOfferta.setCompoundDrawablePadding(convertDpToPx(4, context));
        textOfferta.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textOfferta);

        //Setting textStateAuction
        ConstraintLayout.LayoutParams paramsTextStateAuction = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextStateAuction.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.topToBottom = textOfferta.getId();
        paramsTextStateAuction.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(10, context));
        textStateAuction.setLayoutParams(paramsTextStateAuction);
        textStateAuction.setPadding(convertDpToPx(16, context),convertDpToPx(5, context),convertDpToPx(16, context),convertDpToPx(5, context));
        setTextViewAttributes(textStateAuction, "Rifiutata", 14, R.color.white, context);
        textStateAuction.setBackgroundResource(R.drawable.text_info_rosso);
        textStateAuction.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textStateAuction);

        cardView.addView(constraintLayoutCard);

        return cardView;
    } //ok

    public CardView cardInValSilenziosaAcquirente(AstasilenziosaModel asta, Context context) {
        CardView cardView = new CardView(context);
        ConstraintLayout constraintLayoutCard = new ConstraintLayout(context);
        ImageView imageProduct = new ImageView(context);
        TextView textProductName = new TextView(context);
        TextView textOfferta = new TextView(context);
        TextView textStateAuction = new TextView(context);

        //Setting id
        cardView.setId(asta.getId());
        constraintLayoutCard.setId(View.generateViewId());
        imageProduct.setId(View.generateViewId());
        textProductName.setId(View.generateViewId());
        textOfferta.setId(View.generateViewId());
        textStateAuction.setId(View.generateViewId());

        //Setting cardView
        ConstraintLayout.LayoutParams paramsCardView = new ConstraintLayout.LayoutParams(
                convertDpToPx(175, context), // width
                convertDpToPx(220, context)  // height
        );

        paramsCardView.setMargins(convertDpToPx(8, context), convertDpToPx(8, context), convertDpToPx(8, context), convertDpToPx(8, context));
        cardView.setLayoutParams(paramsCardView);
        cardView.setRadius(convertDpToPx(20, context));
        cardView.setCardElevation(convertDpToPx(8, context));
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));

        //Setting constraintLayoutCard
        ConstraintLayout.LayoutParams paramsConstraintLayoutCard = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );
        constraintLayoutCard.setLayoutParams(paramsConstraintLayoutCard);

        //Setting imageProduct
        ConstraintLayout.LayoutParams paramsImageProduct = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                convertDpToPx(120, context)
        );
        paramsImageProduct.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.setMargins(0, convertDpToPx(0, context), 0, convertDpToPx(2, context));
        imageProduct.setLayoutParams(paramsImageProduct);
        try {
            RequestOptions requestOptions = new RequestOptions()
                    .centerCrop();

            Glide.with(context)
                    .load(asta.getImmagineprodotto())
                    .apply(requestOptions)
                    .into(imageProduct);
        }catch (Exception e){
            imageProduct.setImageResource(R.drawable.image_notfound);
        }

        imageProduct.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageProduct.setAdjustViewBounds(true);
        constraintLayoutCard.addView(imageProduct);

        //Setting textProductName
        ConstraintLayout.LayoutParams paramsTextProductName = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextProductName.bottomToTop = textOfferta.getId();
        paramsTextProductName.topToBottom = imageProduct.getId();
        paramsTextProductName.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(2, context));
        textProductName.setLayoutParams(paramsTextProductName);
        setTextViewAttributes(textProductName, asta.getTitolo(), 16, R.color.black, context);
        textProductName.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textProductName);

        //Setting textOfferta
        ConstraintLayout.LayoutParams paramstextOfferta = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramstextOfferta.bottomToTop = textStateAuction.getId();
        paramstextOfferta.topToBottom = textProductName.getId();
        paramstextOfferta.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramstextOfferta.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramstextOfferta.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(2, context));
        textOfferta.setLayoutParams(paramstextOfferta);
        textOfferta.setText("Offerta: " + formatDouble(asta.getPrezzoofferto())+ " €");
        textOfferta.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textOfferta.setTextColor(ContextCompat.getColor(context, R.color.black));
        textOfferta.setGravity(Gravity.CENTER_VERTICAL);
        textOfferta.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textOfferta.setBackgroundResource(R.drawable.text_info_basic);
        textOfferta.setCompoundDrawablePadding(convertDpToPx(4, context));
        textOfferta.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textOfferta);

        //Setting textStateAuction
        ConstraintLayout.LayoutParams paramsTextStateAuction = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextStateAuction.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.topToBottom = textOfferta.getId();
        paramsTextStateAuction.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(10, context));
        textStateAuction.setLayoutParams(paramsTextStateAuction);
        textStateAuction.setPadding(convertDpToPx(16, context),convertDpToPx(5, context),convertDpToPx(16, context),convertDpToPx(5, context));
        setTextViewAttributes(textStateAuction, "In Valutazione", 14, R.color.white, context);
        textStateAuction.setBackgroundResource(R.drawable.text_info_grigio);
        textStateAuction.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textStateAuction);

        cardView.addView(constraintLayoutCard);

        return cardView;
    } //ok

    public CardView cardVintaSilenziosaAcquirente(AstasilenziosaModel asta, Context context) {
        CardView cardView = new CardView(context);
        ConstraintLayout constraintLayoutCard = new ConstraintLayout(context);
        ImageView imageProduct = new ImageView(context);
        TextView textProductName = new TextView(context);
        TextView textOfferta = new TextView(context);
        TextView textStateAuction = new TextView(context);

        //Setting id
        cardView.setId(asta.getId());
        constraintLayoutCard.setId(View.generateViewId());
        imageProduct.setId(View.generateViewId());
        textProductName.setId(View.generateViewId());
        textOfferta.setId(View.generateViewId());
        textStateAuction.setId(View.generateViewId());

        //Setting cardView
        ConstraintLayout.LayoutParams paramsCardView = new ConstraintLayout.LayoutParams(
                convertDpToPx(175, context), // width
                convertDpToPx(220, context)  // height
        );

        paramsCardView.setMargins(convertDpToPx(8, context), convertDpToPx(8, context), convertDpToPx(8, context), convertDpToPx(8, context));
        cardView.setLayoutParams(paramsCardView);
        cardView.setRadius(convertDpToPx(20, context));
        cardView.setCardElevation(convertDpToPx(8, context));
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));

        //Setting constraintLayoutCard
        ConstraintLayout.LayoutParams paramsConstraintLayoutCard = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );
        constraintLayoutCard.setLayoutParams(paramsConstraintLayoutCard);

        //Setting imageProduct
        ConstraintLayout.LayoutParams paramsImageProduct = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                convertDpToPx(120, context)
        );
        paramsImageProduct.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.setMargins(0, convertDpToPx(0, context), 0, convertDpToPx(2, context));
        imageProduct.setLayoutParams(paramsImageProduct);
        try {
            RequestOptions requestOptions = new RequestOptions()
                    .centerCrop();

            Glide.with(context)
                    .load(asta.getImmagineprodotto())
                    .apply(requestOptions)
                    .into(imageProduct);
        }catch (Exception e){
            imageProduct.setImageResource(R.drawable.image_notfound);
        }

        imageProduct.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageProduct.setAdjustViewBounds(true);
        constraintLayoutCard.addView(imageProduct);

        //Setting textProductName
        ConstraintLayout.LayoutParams paramsTextProductName = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextProductName.bottomToTop = textOfferta.getId();
        paramsTextProductName.topToBottom = imageProduct.getId();
        paramsTextProductName.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(2, context));
        textProductName.setLayoutParams(paramsTextProductName);
        setTextViewAttributes(textProductName, asta.getTitolo(), 16, R.color.black, context);
        textProductName.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textProductName);

        //Setting textOfferta
        ConstraintLayout.LayoutParams paramstextOfferta = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramstextOfferta.bottomToTop = textStateAuction.getId();
        paramstextOfferta.topToBottom = textProductName.getId();
        paramstextOfferta.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramstextOfferta.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramstextOfferta.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(2, context));
        textOfferta.setLayoutParams(paramstextOfferta);
        setTextViewAttributes(textOfferta, formatDouble(asta.getPrezzoofferto())+ " €", 16, R.color.black, context);
        textOfferta.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textOfferta);

        //Setting textStateAuction
        ConstraintLayout.LayoutParams paramsTextStateAuction = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextStateAuction.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.topToBottom = textOfferta.getId();
        paramsTextStateAuction.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(10, context));
        textStateAuction.setLayoutParams(paramsTextStateAuction);
        textStateAuction.setPadding(convertDpToPx(16, context),convertDpToPx(5, context),convertDpToPx(16, context),convertDpToPx(5, context));
        setTextViewAttributes(textStateAuction, "Vinta", 14, R.color.white, context);
        textStateAuction.setBackgroundResource(R.drawable.text_info_verde);
        textStateAuction.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textStateAuction);

        cardView.addView(constraintLayoutCard);

        return cardView;
    } //ok

    public CardView cardVintaRibassoAcquirente(AstaribassoModel asta, Context context) {
        CardView cardView = new CardView(context);
        ConstraintLayout constraintLayoutCard = new ConstraintLayout(context);
        ImageView imageProduct = new ImageView(context);
        TextView textProductName = new TextView(context);
        TextView textOfferta = new TextView(context);
        TextView textStateAuction = new TextView(context);

        //Setting id
        cardView.setId(asta.getId());
        constraintLayoutCard.setId(View.generateViewId());
        imageProduct.setId(View.generateViewId());
        textProductName.setId(View.generateViewId());
        textOfferta.setId(View.generateViewId());
        textStateAuction.setId(View.generateViewId());

        //Setting cardView
        ConstraintLayout.LayoutParams paramsCardView = new ConstraintLayout.LayoutParams(
                convertDpToPx(175, context), // width
                convertDpToPx(220, context)  // height
        );

        paramsCardView.setMargins(convertDpToPx(8, context), convertDpToPx(8, context), convertDpToPx(8, context), convertDpToPx(8, context));
        cardView.setLayoutParams(paramsCardView);
        cardView.setRadius(convertDpToPx(20, context));
        cardView.setCardElevation(convertDpToPx(8, context));
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));

        //Setting constraintLayoutCard
        ConstraintLayout.LayoutParams paramsConstraintLayoutCard = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );
        constraintLayoutCard.setLayoutParams(paramsConstraintLayoutCard);

        //Setting imageProduct
        ConstraintLayout.LayoutParams paramsImageProduct = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                convertDpToPx(120, context)
        );
        paramsImageProduct.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.setMargins(0, convertDpToPx(0, context), 0, convertDpToPx(2, context));
        imageProduct.setLayoutParams(paramsImageProduct);
        try {
            RequestOptions requestOptions = new RequestOptions()
                    .centerCrop();

            Glide.with(context)
                    .load(asta.getImmagineprodotto())
                    .apply(requestOptions)
                    .into(imageProduct);
        }catch (Exception e){
            imageProduct.setImageResource(R.drawable.image_notfound);
        }

        imageProduct.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageProduct.setAdjustViewBounds(true);
        constraintLayoutCard.addView(imageProduct);

        //Setting textProductName
        ConstraintLayout.LayoutParams paramsTextProductName = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextProductName.bottomToTop = textOfferta.getId();
        paramsTextProductName.topToBottom = imageProduct.getId();
        paramsTextProductName.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(2, context));
        textProductName.setLayoutParams(paramsTextProductName);
        setTextViewAttributes(textProductName, asta.getTitolo(), 16, R.color.black, context);
        textProductName.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textProductName);

        //Setting textOfferta
        ConstraintLayout.LayoutParams paramstextOfferta = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramstextOfferta.bottomToTop = textStateAuction.getId();
        paramstextOfferta.topToBottom = textProductName.getId();
        paramstextOfferta.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramstextOfferta.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramstextOfferta.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(2, context));
        textOfferta.setLayoutParams(paramstextOfferta);
        setTextViewAttributes(textOfferta, formatDouble(asta.getUltimoprezzo())+ " €", 16, R.color.black, context);
        textOfferta.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textOfferta);

        //Setting textStateAuction
        ConstraintLayout.LayoutParams paramsTextStateAuction = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextStateAuction.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.topToBottom = textOfferta.getId();
        paramsTextStateAuction.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(10, context));
        textStateAuction.setLayoutParams(paramsTextStateAuction);
        textStateAuction.setPadding(convertDpToPx(16, context),convertDpToPx(5, context),convertDpToPx(16, context),convertDpToPx(5, context));
        setTextViewAttributes(textStateAuction, "Vinta", 14, R.color.white, context);
        textStateAuction.setBackgroundResource(R.drawable.text_info_verde);
        textStateAuction.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textStateAuction);

        cardView.addView(constraintLayoutCard);

        return cardView;
    } //ok

    public Object[] cardApertaRibassoVenditore(AstaribassoModel asta, Context context) {
        CardView cardView = new CardView(context);
        ConstraintLayout constraintLayoutCard = new ConstraintLayout(context);
        ImageView imageProduct = new ImageView(context);
        TextView textPeopleInterested = new TextView(context);
        TextView textProductName = new TextView(context);
        ConstraintLayout constraintLayoutInterno = new ConstraintLayout(context);
        TextView textPrice = new TextView(context);
        ImageView iconClock = new ImageView(context);
        TextView textTimer = new TextView(context);
        TextView textStateAuction = new TextView(context);

        //Setting id
        cardView.setId(asta.getId());
        constraintLayoutCard.setId(View.generateViewId());
        imageProduct.setId(View.generateViewId());
        textPeopleInterested.setId(View.generateViewId());
        textProductName.setId(View.generateViewId());
        constraintLayoutInterno.setId(View.generateViewId());
        textPrice.setId(View.generateViewId());
        iconClock.setId(View.generateViewId());
        textTimer.setId(View.generateViewId());
        textStateAuction.setId(View.generateViewId());

        //Setting cardView
        ConstraintLayout.LayoutParams paramsCardView = new ConstraintLayout.LayoutParams(
                convertDpToPx(175, context), // width
                convertDpToPx(270, context)  // height
        );

        paramsCardView.setMargins(convertDpToPx(8, context), convertDpToPx(8, context), convertDpToPx(8, context), convertDpToPx(8, context));
        cardView.setLayoutParams(paramsCardView);
        cardView.setRadius(convertDpToPx(20, context));
        cardView.setCardElevation(convertDpToPx(8, context));
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));

        //Setting constraintLayoutCard
        ConstraintLayout.LayoutParams paramsConstraintLayoutCard = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );
        constraintLayoutCard.setLayoutParams(paramsConstraintLayoutCard);

        //Setting imageProduct
        ConstraintLayout.LayoutParams paramsImageProduct = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                convertDpToPx(120, context)
        );
        paramsImageProduct.bottomToTop = textPeopleInterested.getId();
        paramsImageProduct.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.setMargins(0, convertDpToPx(0, context), 0, convertDpToPx(2, context));
        imageProduct.setLayoutParams(paramsImageProduct);
        try {
            RequestOptions requestOptions = new RequestOptions()
                    .centerCrop();

            Glide.with(context)
                    .load(asta.getImmagineprodotto())
                    .apply(requestOptions)
                    .into(imageProduct);
        }catch (Exception e){
            imageProduct.setImageResource(R.drawable.image_notfound);
        }

        imageProduct.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageProduct.setAdjustViewBounds(true);
        constraintLayoutCard.addView(imageProduct);

        //Setting textPeopleInterested
        ConstraintLayout.LayoutParams paramsTextPeopleInterested = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextPeopleInterested.bottomToTop = textProductName.getId();
        paramsTextPeopleInterested.topToBottom = imageProduct.getId();
        paramsTextPeopleInterested.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextPeopleInterested.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextPeopleInterested.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(2, context));
        textPeopleInterested.setLayoutParams(paramsTextPeopleInterested);
        setTextViewAttributes(textPeopleInterested, asta.getConteggioUtenti() +" interessati", 16, R.color.grey, context);
        textProductName.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textPeopleInterested);

        //Setting textProductName
        ConstraintLayout.LayoutParams paramsTextProductName = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextProductName.bottomToTop = constraintLayoutInterno.getId();
        paramsTextProductName.topToBottom = textPeopleInterested.getId();
        paramsTextProductName.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(2, context));
        textProductName.setLayoutParams(paramsTextProductName);
        setTextViewAttributes(textProductName, asta.getTitolo(), 16, R.color.black, context);
        textProductName.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textProductName);

        //Setting constraintLayoutInterno
        ConstraintLayout.LayoutParams paramsConstraintLayoutInterno = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsConstraintLayoutInterno.setMargins(convertDpToPx(12, context), 0, convertDpToPx(12, context), 0);
        paramsConstraintLayoutInterno.bottomToTop = textStateAuction.getId();
        paramsConstraintLayoutInterno.topToBottom = textProductName.getId();
        paramsConstraintLayoutInterno.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsConstraintLayoutInterno.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        constraintLayoutInterno.setLayoutParams(paramsConstraintLayoutInterno);
        constraintLayoutCard.addView(constraintLayoutInterno);

        //Setting textPrice
        ConstraintLayout.LayoutParams paramsTextPrice = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextPrice.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextPrice.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextPrice.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextPrice.endToStart = iconClock.getId();
        textPrice.setLayoutParams(paramsTextPrice);
        setTextViewAttributes(textPrice, formatDouble(asta.getUltimoprezzo())+" €", 16, R.color.black, context);
        textPrice.setTypeface(null, Typeface.BOLD);
        constraintLayoutInterno.addView(textPrice);

        //Setting iconClock
        ConstraintLayout.LayoutParams paramsIconClock = new ConstraintLayout.LayoutParams(
                convertDpToPx(20, context),
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );
        paramsIconClock.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsIconClock.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsIconClock.startToEnd = textPrice.getId();
        paramsIconClock.endToStart = textTimer.getId();
        paramsIconClock.setMargins(convertDpToPx(10, context), 0, 0, 0);
        iconClock.setLayoutParams(paramsIconClock);
        iconClock.setImageResource(R.drawable.icon_orologio);
        iconClock.setScaleType(ImageView.ScaleType.FIT_CENTER);
        iconClock.setAdjustViewBounds(true);
        constraintLayoutInterno.addView(iconClock); // Aggiungi l'ImageView al ConstraintLayout

        //Setting textTimer
        ConstraintLayout.LayoutParams paramsTextTimer = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextTimer.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextTimer.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextTimer.startToEnd = iconClock.getId();
        paramsTextTimer.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextTimer.setMargins(convertDpToPx(5, context), 0, 0, 0);
        textTimer.setLayoutParams(paramsTextTimer);
        setTextViewAttributes(textTimer, "-", 16, R.color.lightOrange, context);
        textTimer.setTypeface(null, Typeface.BOLD);
        constraintLayoutInterno.addView(textTimer);

        //Setting textStateAuction
        ConstraintLayout.LayoutParams paramsTextStateAuction = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextStateAuction.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.topToBottom = constraintLayoutInterno.getId();
        paramsTextStateAuction.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(7, context));
        textStateAuction.setLayoutParams(paramsTextStateAuction);
        textStateAuction.setPadding(convertDpToPx(16, context),convertDpToPx(5, context),convertDpToPx(16, context),convertDpToPx(5, context));
        setTextViewAttributes(textStateAuction, "APERTA", 16, R.color.white, context);
        textStateAuction.setBackgroundResource(R.drawable.text_info_arancio);
        textStateAuction.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textStateAuction);

        cardView.addView(constraintLayoutCard);

        Object[] result = new Object[3];
        result[0] = cardView;
        result[1] = textTimer;
        result[2] = textPrice;

        return result;

    } //ok

    public Object[] cardApertaSilenziosaVenditore(AstasilenziosaModel asta, Context context) {
        CardView cardView = new CardView(context);
        ConstraintLayout constraintLayoutCard = new ConstraintLayout(context);
        ImageView imageProduct = new ImageView(context);
        TextView textOfferte = new TextView(context);
        TextView textProductName = new TextView(context);
        TextView textDate = new TextView(context);
        TextView textStateAuction = new TextView(context);

        //Setting id
        cardView.setId(asta.getId());
        constraintLayoutCard.setId(View.generateViewId());
        imageProduct.setId(View.generateViewId());
        textOfferte.setId(View.generateViewId());
        textProductName.setId(View.generateViewId());
        textDate.setId(View.generateViewId());
        textStateAuction.setId(View.generateViewId());

        //Setting cardView
        ConstraintLayout.LayoutParams paramsCardView = new ConstraintLayout.LayoutParams(
                convertDpToPx(175, context), // width
                convertDpToPx(270, context)  // height
        );

        paramsCardView.setMargins(convertDpToPx(8, context), convertDpToPx(8, context), convertDpToPx(8, context), convertDpToPx(8, context));
        cardView.setLayoutParams(paramsCardView);
        cardView.setRadius(convertDpToPx(20, context));
        cardView.setCardElevation(convertDpToPx(8, context));
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));

        //Setting constraintLayoutCard
        ConstraintLayout.LayoutParams paramsConstraintLayoutCard = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );
        constraintLayoutCard.setLayoutParams(paramsConstraintLayoutCard);

        //Setting imageProduct
        ConstraintLayout.LayoutParams paramsImageProduct = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                convertDpToPx(120, context)
        );
        paramsImageProduct.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.setMargins(0, convertDpToPx(0, context), 0, convertDpToPx(2, context));
        imageProduct.setLayoutParams(paramsImageProduct);
        try {
            RequestOptions requestOptions = new RequestOptions()
                    .centerCrop();

            Glide.with(context)
                    .load(asta.getImmagineprodotto())
                    .apply(requestOptions)
                    .into(imageProduct);
        }catch (Exception e){
            imageProduct.setImageResource(R.drawable.image_notfound);
        }

        imageProduct.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageProduct.setAdjustViewBounds(true);
        constraintLayoutCard.addView(imageProduct);

        //Setting textOfferte
        ConstraintLayout.LayoutParams paramsTextOfferte = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextOfferte.bottomToTop = textProductName.getId();
        paramsTextOfferte.topToBottom = imageProduct.getId();
        paramsTextOfferte.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextOfferte.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextOfferte.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(2, context));
        textOfferte.setLayoutParams(paramsTextOfferte);
        textOfferte.setPadding(convertDpToPx(16, context),convertDpToPx(5, context),convertDpToPx(16, context),convertDpToPx(5, context));
        setTextViewAttributes(textOfferte, asta.getConteggioOfferte() +" offerte", 14, R.color.white, context);
        textOfferte.setBackgroundResource(R.drawable.text_info_verde);
        textOfferte.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textOfferte);

        //Setting textProductName
        ConstraintLayout.LayoutParams paramsTextProductName = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextProductName.bottomToTop = textDate.getId();
        paramsTextProductName.topToBottom = textOfferte.getId();
        paramsTextProductName.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(2, context));
        textProductName.setLayoutParams(paramsTextProductName);
        setTextViewAttributes(textProductName, asta.getTitolo(), 16, R.color.black, context);
        textProductName.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textProductName);

        //Setting textDate
        ConstraintLayout.LayoutParams paramsTextDate = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextDate.bottomToTop = textStateAuction.getId();
        paramsTextDate.topToBottom = textProductName.getId();
        paramsTextDate.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextDate.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextDate.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(2, context));
        textDate.setLayoutParams(paramsTextDate);
        setTextViewAttributes(textDate, String.valueOf(asta.getDatafineasta()), 16, R.color.lightOrange, context);
        textDate.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textDate);

        //Setting textStateAuction
        ConstraintLayout.LayoutParams paramsTextStateAuction = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextStateAuction.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.topToBottom = textDate.getId();
        paramsTextStateAuction.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(10, context));
        textStateAuction.setLayoutParams(paramsTextStateAuction);
        textStateAuction.setPadding(convertDpToPx(16, context),convertDpToPx(5, context),convertDpToPx(16, context),convertDpToPx(5, context));
        setTextViewAttributes(textStateAuction, "APERTA", 16, R.color.white, context);
        textStateAuction.setBackgroundResource(R.drawable.text_info_arancio);
        textStateAuction.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textStateAuction);

        cardView.addView(constraintLayoutCard);

        Object[] result = new Object[2];
        result[0] = cardView;
        result[1] = textDate;

        return result;

    } //ok

    public CardView cardChiusaSilenziosaVenditore(AstasilenziosaModel asta, Context context) {
        CardView cardView = new CardView(context);
        ConstraintLayout constraintLayoutCard = new ConstraintLayout(context);
        ImageView imageProduct = new ImageView(context);
        TextView textPrezzo = new TextView(context);
        TextView textProductName = new TextView(context);
        TextView textDate = new TextView(context);
        TextView textStateAuction = new TextView(context);

        //Setting id
        cardView.setId(asta.getId());
        constraintLayoutCard.setId(View.generateViewId());
        imageProduct.setId(View.generateViewId());
        textPrezzo.setId(View.generateViewId());
        textProductName.setId(View.generateViewId());
        textDate.setId(View.generateViewId());
        textStateAuction.setId(View.generateViewId());

        //Setting cardView
        ConstraintLayout.LayoutParams paramsCardView = new ConstraintLayout.LayoutParams(
                convertDpToPx(175, context), // width
                convertDpToPx(270, context)  // height
        );

        paramsCardView.setMargins(convertDpToPx(8, context), convertDpToPx(8, context), convertDpToPx(8, context), convertDpToPx(8, context));
        cardView.setLayoutParams(paramsCardView);
        cardView.setRadius(convertDpToPx(20, context));
        cardView.setCardElevation(convertDpToPx(8, context));
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));

        //Setting constraintLayoutCard
        ConstraintLayout.LayoutParams paramsConstraintLayoutCard = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );
        constraintLayoutCard.setLayoutParams(paramsConstraintLayoutCard);

        //Setting imageProduct
        ConstraintLayout.LayoutParams paramsImageProduct = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                convertDpToPx(120, context)
        );
        paramsImageProduct.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.setMargins(0, convertDpToPx(0, context), 0, convertDpToPx(2, context));
        imageProduct.setLayoutParams(paramsImageProduct);
        try {
            RequestOptions requestOptions = new RequestOptions()
                    .centerCrop();

            Glide.with(context)
                    .load(asta.getImmagineprodotto())
                    .apply(requestOptions)
                    .into(imageProduct);
        }catch (Exception e){
            imageProduct.setImageResource(R.drawable.image_notfound);
        }

        imageProduct.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageProduct.setAdjustViewBounds(true);
        constraintLayoutCard.addView(imageProduct);

        //Setting textProductName
        ConstraintLayout.LayoutParams paramsTextProductName = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextProductName.bottomToTop = textPrezzo.getId();
        paramsTextProductName.topToBottom = imageProduct.getId();
        paramsTextProductName.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(2, context));
        textProductName.setLayoutParams(paramsTextProductName);
        setTextViewAttributes(textProductName, asta.getTitolo(), 16, R.color.black, context);
        textProductName.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textProductName);

        //Setting textOfferte
        ConstraintLayout.LayoutParams paramsTextPrezzo = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextPrezzo.bottomToTop = textDate.getId();
        paramsTextPrezzo.topToBottom = textProductName.getId();
        paramsTextPrezzo.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextPrezzo.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextPrezzo.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(2, context));
        textPrezzo.setLayoutParams(paramsTextPrezzo);
        if(asta.getPrezzovendita()<1){
            setTextViewAttributes(textPrezzo, "Non aggiudicata", 14, R.color.darkOrange, context);
        }else {
            setTextViewAttributes(textPrezzo, "Venduto a " + formatDouble(asta.getPrezzovendita())+ " €", 14, R.color.darkOrange, context);
        }
        textPrezzo.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textPrezzo);

        //Setting textDate
        ConstraintLayout.LayoutParams paramsTextDate = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextDate.bottomToTop = textStateAuction.getId();
        paramsTextDate.topToBottom = textPrezzo.getId();
        paramsTextDate.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextDate.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextDate.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(2, context));
        textDate.setLayoutParams(paramsTextDate);
        setTextViewAttributes(textDate, String.valueOf(asta.getDatafineasta()), 14, R.color.black, context);
        textDate.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textDate);

        //Setting textStateAuction
        ConstraintLayout.LayoutParams paramsTextStateAuction = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextStateAuction.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.topToBottom = textDate.getId();
        paramsTextStateAuction.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(10, context));
        textStateAuction.setLayoutParams(paramsTextStateAuction);
        textStateAuction.setPadding(convertDpToPx(16, context),convertDpToPx(5, context),convertDpToPx(16, context),convertDpToPx(5, context));
        setTextViewAttributes(textStateAuction, "CONCLUSA", 16, R.color.white, context);
        textStateAuction.setBackgroundResource(R.drawable.text_info_verde);
        textStateAuction.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textStateAuction);

        cardView.addView(constraintLayoutCard);

        return cardView;
    }

    public CardView cardChiusaRibassoVenditore(AstaribassoModel asta, Context context) {
        CardView cardView = new CardView(context);
        ConstraintLayout constraintLayoutCard = new ConstraintLayout(context);
        ImageView imageProduct = new ImageView(context);
        TextView textPrezzo = new TextView(context);
        TextView textProductName = new TextView(context);
        TextView textInitprice = new TextView(context);
        TextView textStateAuction = new TextView(context);

        //Setting id
        cardView.setId(asta.getId());
        constraintLayoutCard.setId(View.generateViewId());
        imageProduct.setId(View.generateViewId());
        textPrezzo.setId(View.generateViewId());
        textProductName.setId(View.generateViewId());
        textInitprice.setId(View.generateViewId());
        textStateAuction.setId(View.generateViewId());

        //Setting cardView
        ConstraintLayout.LayoutParams paramsCardView = new ConstraintLayout.LayoutParams(
                convertDpToPx(175, context), // width
                convertDpToPx(270, context)  // height
        );

        paramsCardView.setMargins(convertDpToPx(8, context), convertDpToPx(8, context), convertDpToPx(8, context), convertDpToPx(8, context));
        cardView.setLayoutParams(paramsCardView);
        cardView.setRadius(convertDpToPx(20, context));
        cardView.setCardElevation(convertDpToPx(8, context));
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));

        //Setting constraintLayoutCard
        ConstraintLayout.LayoutParams paramsConstraintLayoutCard = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );
        constraintLayoutCard.setLayoutParams(paramsConstraintLayoutCard);

        //Setting imageProduct
        ConstraintLayout.LayoutParams paramsImageProduct = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                convertDpToPx(120, context)
        );
        paramsImageProduct.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsImageProduct.setMargins(0, convertDpToPx(0, context), 0, convertDpToPx(2, context));
        imageProduct.setLayoutParams(paramsImageProduct);
        try {
            RequestOptions requestOptions = new RequestOptions()
                    .centerCrop();

            Glide.with(context)
                    .load(asta.getImmagineprodotto())
                    .apply(requestOptions)
                    .into(imageProduct);
        }catch (Exception e){
            imageProduct.setImageResource(R.drawable.image_notfound);
        }
        imageProduct.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageProduct.setAdjustViewBounds(true);
        constraintLayoutCard.addView(imageProduct);

        //Setting textProductName
        ConstraintLayout.LayoutParams paramsTextProductName = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextProductName.bottomToTop = textPrezzo.getId();
        paramsTextProductName.topToBottom = imageProduct.getId();
        paramsTextProductName.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextProductName.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(2, context));
        textProductName.setLayoutParams(paramsTextProductName);
        setTextViewAttributes(textProductName, asta.getTitolo(), 16, R.color.black, context);
        textProductName.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textProductName);

        //Setting textPrezzo
        ConstraintLayout.LayoutParams paramsTextPrezzo = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextPrezzo.bottomToTop = textInitprice.getId();
        paramsTextPrezzo.topToBottom = textProductName.getId();
        paramsTextPrezzo.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextPrezzo.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextPrezzo.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(2, context));
        textPrezzo.setLayoutParams(paramsTextPrezzo);
        if(asta.getPrezzovendita()<1){
            setTextViewAttributes(textPrezzo, "Non aggiudicata", 14, R.color.darkOrange, context);
        }else {
            setTextViewAttributes(textPrezzo, "Venduto a " + formatDouble(asta.getUltimoprezzo())+ " €", 14, R.color.darkOrange, context);
        }
        textPrezzo.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textPrezzo);

        //Setting textInitprice
        ConstraintLayout.LayoutParams paramsTextInitprice = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextInitprice.bottomToTop = textStateAuction.getId();
        paramsTextInitprice.topToBottom = textPrezzo.getId();
        paramsTextInitprice.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextInitprice.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextInitprice.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(2, context));
        textInitprice.setLayoutParams(paramsTextInitprice);
        textInitprice.setText("Prezzo iniziale " + formatDouble(asta.getPrezzoiniziale())+ " €");
        textInitprice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textInitprice.setTextColor(ContextCompat.getColor(context, R.color.black));
        textInitprice.setGravity(Gravity.CENTER_VERTICAL);
        textInitprice.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textInitprice.setBackgroundResource(R.drawable.text_info_basic);
        textInitprice.setCompoundDrawablePadding(convertDpToPx(4, context));
        textInitprice.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textInitprice);

        //Setting textStateAuction
        ConstraintLayout.LayoutParams paramsTextStateAuction = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextStateAuction.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.topToBottom = textInitprice.getId();
        paramsTextStateAuction.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsTextStateAuction.setMargins(0, convertDpToPx(2, context), 0, convertDpToPx(10, context));
        textStateAuction.setLayoutParams(paramsTextStateAuction);
        textStateAuction.setPadding(convertDpToPx(16, context),convertDpToPx(5, context),convertDpToPx(16, context),convertDpToPx(5, context));
        setTextViewAttributes(textStateAuction, "CONCLUSA", 16, R.color.white, context);
        textStateAuction.setBackgroundResource(R.drawable.text_info_verde);
        textStateAuction.setTypeface(null, Typeface.BOLD);
        constraintLayoutCard.addView(textStateAuction);

        cardView.addView(constraintLayoutCard);

        return cardView;
    } //ok

    public void setTextViewAttributes(TextView textView, String text, int textSize, int textColor, Context activityContext) {
        try {
            if (textView == null || text == null || activityContext == null) {
                throw new NullPointerException("Uno o più parametri sono null.");
            }
            if (text.length() > 15) {
                String truncatedText = text.substring(0, 15) + "...";
                textView.setText(truncatedText);
            } else {
                textView.setText(text);
            }
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            textView.setTextColor(ContextCompat.getColor(activityContext, textColor));
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView.setBackgroundResource(R.drawable.text_info_basic);
            textView.setCompoundDrawablePadding(convertDpToPx(4, activityContext));
        } catch (NullPointerException e) {
            Toast.makeText(activityContext, "Errore nel settaggio degli attributi del TextView: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    public int convertDpToPx(int dp, Context activityContext) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                activityContext.getResources().getDisplayMetrics()
        );
    }

    public static String formatDouble(double value) {
        if (value == (long) value) {
            return String.format(Locale.getDefault(), "%d", (long) value);
        } else {
            return String.format(Locale.getDefault(), "%s", value);
        }
    }

    public void startCountdownSilenziosa(CardView view, TextView timer, AstasilenziosaModel asta) {
        Handler handler = new Handler();

        Calendar cal = Calendar.getInstance();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Parsa la stringa di data usando il formato definito
        LocalDate data = LocalDate.parse(asta.getDatafineasta(), formatter);

        // Ottieni giorno, mese e anno dalla data
        int giorno = data.getDayOfMonth();
        int mese = data.getMonthValue();
        int anno = data.getYear();

        final long millisFineAsta = getFineAstaMillis(cal, giorno, mese, anno);

        try{
            handler.post(new Runnable() {
                @Override
                public void run() {
                    long millisRimanenti = millisFineAsta - System.currentTimeMillis();

                    if (millisRimanenti > 0) {
                        long oreRimanenti = millisRimanenti / (60 * 60 * 1000);
                        long minutiRimanenti = (millisRimanenti % (60 * 60 * 1000)) / (60 * 1000);
                        long secondiRimanenti = ((millisRimanenti % (60 * 60 * 1000)) % (60 * 1000)) / 1000;

                        String oreFormat = String.format(Locale.getDefault(),"%02d", oreRimanenti);
                        String minutiFormat = String.format(Locale.getDefault(),"%02d", minutiRimanenti);
                        String secondiFormat = String.format(Locale.getDefault(),"%02d", secondiRimanenti);

                        String orario = oreFormat+":"+minutiFormat+":"+secondiFormat;
                        timer.setText(orario);

                        // Aggiorna ogni secondo
                        handler.postDelayed(this, 1000);
                    } else {
                        // Countdown terminato
                        view.setVisibility(View.GONE);
                    }
                }
            });
        }catch (Exception e){
            view.setVisibility(View.GONE);
        }
    }

    public long getFineAstaMillis(Calendar cal, int giorno, int mese, int anno) {
        cal.clear(); // Pulisce il calendario
        cal.set(Calendar.DAY_OF_MONTH, giorno);
        cal.set(Calendar.MONTH, mese - 1); // I mesi iniziano da 0 (0 = gennaio, 1 = febbraio, ecc.)
        cal.set(Calendar.YEAR, anno);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTimeInMillis();
    }

    public void startCountdownRibasso(CardView view, TextView timer, TextView ultimo ,AstaribassoModel asta) {
        Handler handler = new Handler();
        // Calcola il tempo corrente
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime timestamp = LocalDateTime.parse(String.valueOf(asta.getCreated_at()), formatter);
        LocalDateTime orarioProssimoDecremento = calcolaOrarioProssimoDecrementoRibasso(timestamp, asta.getTimerdecremento());

        int ora = orarioProssimoDecremento.getHour();
        int minuto = orarioProssimoDecremento.getMinute();
        int secondo = orarioProssimoDecremento.getSecond();
        int millisecondo = orarioProssimoDecremento.getNano() / 1000000; // Conversione da nanosecondi a millisecondi

        Calendar cal = Calendar.getInstance();
        final long fineGiornataMillis = getFineGiornataMillisRibasso(cal, ora, minuto, secondo, millisecondo);
        try{
            handler.post(new Runnable() {
                @Override
                public void run() {

                    // Calcolo di quanto manca al prossimo decremento
                    //long millisRimanenti = calcolaMillisecondiMancanti(timestamp, timerdecremento);
                    long millisRimanenti = fineGiornataMillis - System.currentTimeMillis();

                    if (millisRimanenti > 0) {

                        long oreRimanenti = millisRimanenti / (60 * 60 * 1000);
                        long minutiRimanenti = (millisRimanenti % (60 * 60 * 1000)) / (60 * 1000);
                        long secondiRimanenti = ((millisRimanenti % (60 * 60 * 1000)) % (60 * 1000)) / 1000;

                        String oreFormat = String.format(Locale.getDefault(),"%02d", oreRimanenti);
                        String minutiFormat = String.format(Locale.getDefault(),"%02d", minutiRimanenti);
                        String secondiFormat = String.format(Locale.getDefault(),"%02d", secondiRimanenti);

                        String orario = oreFormat+":"+minutiFormat+":"+secondiFormat;
                        timer.setText(orario);

                        // Aggiorna ogni secondo
                        handler.postDelayed(this, 1000);
                    } else if(asta.getUltimoprezzo()>=asta.getPrezzominimosegreto()){
                        // Countdown terminato
                        asta.setUltimoprezzo(asta.getUltimoprezzo() - asta.getImportodecremento());
                        ultimo.setText(formatDouble(asta.getUltimoprezzo())+" €");
                        startCountdownRibasso(view, timer, ultimo, asta);
                    }else{
                        view.setVisibility(View.GONE);
                    }
                }
            });
        }catch (Exception e){
            view.setVisibility(View.GONE);
        }
    }

    public static LocalDateTime calcolaOrarioProssimoDecrementoRibasso(LocalDateTime timestamp, int intervalloMinuti) {
        long millisecondiPassati = ChronoUnit.MILLIS.between(timestamp, LocalDateTime.now());
        long millisecondiIntervallo = (long) intervalloMinuti * 60 * 1000;
        long millisecondiMancanti = millisecondiIntervallo - (millisecondiPassati % millisecondiIntervallo);
        return LocalDateTime.now().plus(Duration.ofMillis(millisecondiMancanti));
    }

    public long getFineGiornataMillisRibasso(Calendar cal, int ore, int minuti, int secondi, int mills) {
        cal.set(Calendar.HOUR_OF_DAY, ore);
        cal.set(Calendar.MINUTE, minuti);
        cal.set(Calendar.SECOND, secondi);
        cal.set(Calendar.MILLISECOND, mills);
        return cal.getTimeInMillis();
    }

}