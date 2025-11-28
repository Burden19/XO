package com.example.xo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class LanguageSelectionActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "XOPrefs";
    private static final String KEY_LANGUAGE = "language";
    private static final String KEY_LANGUAGE_SELECTED = "language_selected";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);

        Button btnEnglish = findViewById(R.id.btn_english);
        Button btnFrench = findViewById(R.id.btn_french);

        btnEnglish.setOnClickListener(v -> selectLanguage("en"));
        btnFrench.setOnClickListener(v -> selectLanguage("fr"));
    }

    private void selectLanguage(String languageCode) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_LANGUAGE, languageCode);
        editor.putBoolean(KEY_LANGUAGE_SELECTED, true);
        editor.apply();

        setLocale(languageCode);

        Intent intent = new Intent(LanguageSelectionActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}

