package com.example.cafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

public class Settings extends AppCompatActivity {

    private static final String THEME_PREFS = "theme_prefs";
    private static final String THEME_MODE_KEY = "theme_mode";

    private SwitchCompat themeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        applyTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        themeSwitch = findViewById(R.id.themeSwitch);
        ImageView imageView = findViewById(R.id.back);
        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Load the user's theme preference and set the switch accordingly
        int savedThemeMode = loadThemeMode();
        themeSwitch.setChecked(savedThemeMode == AppCompatDelegate.MODE_NIGHT_YES);

        // Set a listener to detect changes in the switch state
        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Save the user's theme preference
            saveThemeMode(isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

            // Apply the new theme immediately
            recreate(); // Restart the activity to apply the new theme
        });
    }

    private void applyTheme() {
        // Get the user's preferred theme setting from SharedPreferences
        int themeMode = loadThemeMode();

        // Set the app's night mode based on the user's preference
        AppCompatDelegate.setDefaultNightMode(themeMode);
    }

    private int loadThemeMode() {
        SharedPreferences prefs = getSharedPreferences(THEME_PREFS, MODE_PRIVATE);
        // Default to MODE_NIGHT_FOLLOW_SYSTEM if no value is found
        return prefs.getInt(THEME_MODE_KEY, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    private void saveThemeMode(int themeMode) {
        SharedPreferences prefs = getSharedPreferences(THEME_PREFS, MODE_PRIVATE);
        prefs.edit().putInt(THEME_MODE_KEY, themeMode).apply();
    }
}
