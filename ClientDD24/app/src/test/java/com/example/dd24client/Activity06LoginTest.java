package com.example.dd24client;

import static org.mockito.Mockito.*;

import android.content.Context;
import android.content.SharedPreferences;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class Activity06LoginTest {

    @Mock
    private Context context;

    @Mock
    private SharedPreferences.Editor editor;

    @Mock
    private SharedPreferences sharedPreferences;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPreferences);
        when(sharedPreferences.edit()).thenReturn(editor);

        // Configura il mock di editor per rispondere alle chiamate
        when(editor.putString(eq("email"), anyString())).thenReturn(editor);
        when(editor.putString(eq("Password"), anyString())).thenReturn(editor);
        when(editor.putBoolean(eq("Ricordami"), anyBoolean())).thenReturn(editor);
        when(editor.remove(anyString())).thenReturn(editor);
        doNothing().when(editor).apply();


    }


    @Test
    public void savePreferencesTestCase1() {
        PreferencesManager.savePreferences(context, "[CEmail1]", "[CEpassword1]", true);

        verify(editor).putString("email", "[CEmail1]");
        verify(editor).putString("Password", "[CEpassword1]");
        verify(editor).putBoolean("Ricordami", true);
        verify(editor).apply();
    }

    @Test
    public void savePreferencesTestCase2() {
        PreferencesManager.savePreferences(context, null, "[CEpassword2]", true);

        verify(editor, never()).putString(anyString(), anyString());
        verify(editor, never()).putBoolean(anyString(), anyBoolean());
        verify(editor, never()).apply();
    }

    @Test
    public void savePreferencesTestCase3() {
        PreferencesManager.savePreferences(context, "[CEmail3]", "[CEpassword3]", false);

        verify(editor).putString("email", "[CEmail3]");
        verify(editor).putString("Password", "[CEpassword3]");
        verify(editor).putBoolean("Ricordami", false);
        verify(editor).apply();
    }

    @Test
    public void savePreferencesTestCase4() {
        PreferencesManager.savePreferences(context, "[CEmail1]", "[CEpassword1]", false);

        verify(editor).putString("email", "[CEmail1]");
        verify(editor).putString("Password", "[CEpassword1]");
        verify(editor).putBoolean("Ricordami", false);
        verify(editor).apply();
    }

    @Test
    public void savePreferencesTestCase5() {
        PreferencesManager.savePreferences(context, "[CEmail1]", "", true);

        verify(editor, never()).putString(anyString(), anyString());
        verify(editor, never()).putBoolean(anyString(), anyBoolean());
        verify(editor, never()).apply();
    }

    @Test
    public void savePreferencesTestCase6() {
        PreferencesManager.savePreferences(context, "", "[CEpassword2]", true);

        verify(editor, never()).putString(anyString(), anyString());
        verify(editor, never()).putBoolean(anyString(), anyBoolean());
        verify(editor, never()).apply();
    }

    @Test
    public void savePreferencesTestCase7() {
        PreferencesManager.savePreferences(context, "", "", true);

        verify(editor, never()).putString(anyString(), anyString());
        verify(editor, never()).putBoolean(anyString(), anyBoolean());
        verify(editor, never()).apply();
    }


}


