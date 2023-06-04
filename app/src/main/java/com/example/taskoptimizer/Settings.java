package com.example.taskoptimizer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;

import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.button.MaterialButton;

public class Settings extends AppCompatActivity implements NumberPicker.OnValueChangeListener{
    NumberPicker lowPriPicker, medPriPicker, highPriPicker, shortEstPicker, midEstPicker, longEstPicker, altVariable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout based on the theme
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        shortEstPicker = findViewById(R.id.shortEstNumberPicker);
        midEstPicker = findViewById(R.id.midEstNumberPicker);
        longEstPicker = findViewById(R.id.longEstNumberPicker);

        lowPriPicker = findViewById(R.id.lowPriorityNumberPicker);
        medPriPicker = findViewById(R.id.medPriorityNumberPicker);
        highPriPicker = findViewById(R.id.highPriorityNumberPicker);

        altVariable = findViewById(R.id.altVariablePicker);

        lowPriPicker.setMinValue(10);
        lowPriPicker.setMaxValue(30);
        medPriPicker.setMinValue(31);
        medPriPicker.setMaxValue(60);
        highPriPicker.setMinValue(61);
        highPriPicker.setMaxValue(90);

        shortEstPicker.setMinValue(10);
        shortEstPicker.setMaxValue(30);
        midEstPicker.setMinValue(31);
        midEstPicker.setMaxValue(60);
        midEstPicker.setMaxValue(61);
        longEstPicker.setMinValue(120);
        altVariable.setMinValue(5);
        altVariable.setMaxValue(30);

        lowPriPicker.setOnValueChangedListener(this);
        medPriPicker.setOnValueChangedListener(this);
        highPriPicker.setOnValueChangedListener(this);
        shortEstPicker.setOnValueChangedListener(this);
        midEstPicker.setOnValueChangedListener(this);
        longEstPicker.setOnValueChangedListener(this);
        altVariable.setOnValueChangedListener(this);

        /*



        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavPage();
            }
        });

         */
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        switch (picker.getId()){
            case R.id.lowPriorityNumberPicker:
                Toast.makeText(this,"Old Number="+oldVal+"New Num="+newVal,Toast.LENGTH_SHORT).show();
            case R.id.medPriorityNumberPicker:
                Toast.makeText(this,"Old Number="+oldVal+"New Num="+newVal,Toast.LENGTH_SHORT).show();
            case R.id.highPriorityNumberPicker:
                Toast.makeText(this,"Old Number="+oldVal+"New Num="+newVal,Toast.LENGTH_SHORT).show();
            case R.id.shortEstNumberPicker:
                Toast.makeText(this,"Old Number="+oldVal+"New Num="+newVal,Toast.LENGTH_SHORT).show();
            case R.id.midEstNumberPicker:
                Toast.makeText(this,"Old Number="+oldVal+"New Num="+newVal,Toast.LENGTH_SHORT).show();
            case R.id.longEstNumberPicker:
                Toast.makeText(this,"Old Number="+oldVal+"New Num="+newVal,Toast.LENGTH_SHORT).show();
            case R.id.altVariablePicker:
                Toast.makeText(this,"Old Number="+oldVal+"New Num="+newVal,Toast.LENGTH_SHORT).show();


        }
    }
}
