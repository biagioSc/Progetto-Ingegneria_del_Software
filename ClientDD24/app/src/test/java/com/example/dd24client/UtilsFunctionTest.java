package com.example.dd24client;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import android.content.Context;

import com.example.dd24client.utils.UtilsFunction;

import org.json.JSONObject;
import java.io.IOException;
import org.json.JSONException;

public class UtilsFunctionTest {
    Context mockContext = Mockito.mock(Context.class);

    @Test
    public void caricaDatiDaJsonTestCase1() {
        // Context valido, File esistente, Json valido
        UtilsFunction obj = new UtilsFunction();
        JSONObject result = obj.caricaDatiDaJson(mockContext, "nazioni_e_citta.json");
        assertNotNull(result);
    }

    @Test
    public void caricaDatiDaJsonTestCase2() {
        // Context null, File esistente, Json valido
        UtilsFunction obj = new UtilsFunction();
        assertThrows(NullPointerException.class, () -> obj.caricaDatiDaJson(null, "nazioni_e_citta.json"));
    }

    @Test
    public void caricaDatiDaJsonTestCase3() {
        // Context valido, File non esistente
        UtilsFunction obj = new UtilsFunction();
        assertThrows(IOException.class, () -> obj.caricaDatiDaJson(mockContext, "nonExistingFile.json"));
    }

    @Test
    public void caricaDatiDaJsonTestCase4() {
        // Context valido, File null
        UtilsFunction obj = new UtilsFunction();
        assertThrows(NullPointerException.class, () -> obj.caricaDatiDaJson(mockContext, null));
    }

    @Test
    public void caricaDatiDaJsonTestCase5() {
        // Context valido, File esistente, Json non valido
        UtilsFunction obj = new UtilsFunction();
        assertThrows(JSONException.class, () -> obj.caricaDatiDaJson(mockContext, "invalid.json"));
    }
}

