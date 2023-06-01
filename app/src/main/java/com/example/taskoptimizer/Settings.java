package com.example.taskoptimizer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.button.MaterialButton;

public class Settings extends AppCompatActivity {

    private MaterialButton logoutbtn;
    private MaterialButton backbtn;
    private Switch themeSwitch;
    private RelativeLayout settingspage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Apply the default theme
        setTheme(R.style.LightTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        logoutbtn = findViewById(R.id.logoutbtn);
        backbtn = findViewById(R.id.backbtn);

        themeSwitch = findViewById(R.id.themeSwitch);
        settingspage = findViewById(R.id.settingspage);

        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Apply the selected theme based on the switch state
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        // Update the theme based on the current state of the switch
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            themeSwitch.setChecked(true);
            settingspage.setBackgroundColor(getResources().getColor(R.color.dark_background_color));
        } else {
            themeSwitch.setChecked(false);
            settingspage.setBackgroundColor(getResources().getColor(R.color.light_background_color));
        }

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginPage();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavPage();
            }
        });
    }

    private void openLoginPage() {
        Intent intent = new Intent(Settings.this, MainActivity.class);
        startActivity(intent);
    }

    private void openNavPage() {
        Intent intent = new Intent(Settings.this, NavBarControl.class);
        startActivity(intent);
    }
}
