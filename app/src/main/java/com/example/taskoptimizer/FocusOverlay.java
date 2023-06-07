package com.example.taskoptimizer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class FocusOverlay extends AppCompatActivity {
    private CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_overlay);
        getSupportActionBar().setTitle("FocusTimer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView timerView = findViewById(R.id.timerCountdown);
        startTimer(10000,timerView);




    }
    private void startTimer(long focusTimeInMillis, TextView output){
        timer = new CountDownTimer(focusTimeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutesLeft = millisUntilFinished / (60 * 1000);
                output.setText((int) minutesLeft);

            }

            @Override
            public void onFinish() {

                // Perform actions after the focus time is over

            }
        }.start();
    }
}