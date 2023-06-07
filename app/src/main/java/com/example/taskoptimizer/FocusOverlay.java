package com.example.taskoptimizer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FocusOverlay extends AppCompatActivity {
    private CountDownTimer timer;
    private long focusTimeInMillis;
    public long remainingTime;
    private String channelId = "countdown_channel";

    private CountdownNotification countdownNotification;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_overlay);
        getSupportActionBar().setTitle("FocusTimer");

        FocusTimer focusTimer = new FocusTimer(this);

        TextView timerView = findViewById(R.id.timerCountdown);
        Button stopTimer = findViewById(R.id.stopTimer);
        startFocusTimer(10000, timerView);
        stopTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopFocusTimer(timerView);
                //Intent intent = new Intent(this, NavBarControl.class);

            }
        });


    }
    private void startTimer(long focusTimeInMillis, TextView output){
         CountDownTimer timer = new CountDownTimer(focusTimeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutesLeft = millisUntilFinished / (60 * 1000);
                output.setText(String.valueOf( minutesLeft)+" Mins");

            }

            @Override
            public void onFinish() {

                // Perform actions after the focus time is over

            }
        }.start();
         disableDoNotDisturb();

    }

    public boolean isDoNotDisturbEnabled() {
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            return notificationManager.isNotificationPolicyAccessGranted() &&
                    notificationManager.getCurrentInterruptionFilter() != NotificationManager.INTERRUPTION_FILTER_ALL;
        }
        return false;
    }
    public boolean areNotificationsEnabled() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                return notificationManager.getImportance() != NotificationManager.IMPORTANCE_NONE;
            }
        }
        return false; //
    }
    public void stopFocusTimer(TextView output) {
        if (timer != null) {
            timer.cancel();
            disableDoNotDisturb();
            countdownNotification.cancelNotification();
            output.setText("Timer Stopped");
        }
    }
    public void enableDoNotDisturb() {
        if (!isDoNotDisturbEnabled()) {
            NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALARMS);
                } else {
                    notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE);
                    notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_PRIORITY);
                }
            }
        }
    }


    public void disableDoNotDisturb() {
        if (isDoNotDisturbEnabled()) {
            NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL);
            }
        }
    }
    public void startFocusTimer(long focusTimeInMill, TextView output) {
        this.focusTimeInMillis = focusTimeInMill;


        stopFocusTimer(output); // Stop the previous timer, if any

        countdownNotification = new CountdownNotification(this);
        if(!isDoNotDisturbEnabled()){
            enableDoNotDisturb();
        }

        timer = new CountDownTimer(focusTimeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime = millisUntilFinished / (60 * 1000);
                countdownNotification.showCountdownNotification(remainingTime);
                output.setText(String.valueOf( remainingTime)+" Mins");
            }

            @Override
            public void onFinish() {
                stopFocusTimer(output);
                // Perform actions after the focus time is over

            }
        }.start();


    }
}