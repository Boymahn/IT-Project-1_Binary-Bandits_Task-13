package com.example.taskoptimizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class FocusTimerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private static final int REQUEST_FOCUS_TIME = 1;

// ...

    public void onClickFocusButton() {
        Intent intent = new Intent(this, FocusTimerActivity.class);
        startActivityForResult(intent, REQUEST_FOCUS_TIME);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_FOCUS_TIME && resultCode == RESULT_OK) {
            long focusTimeInMillis = data.getLongExtra("focusTimeInMillis", 0);
            if (focusTimeInMillis > 0) {
                FocusTimer focusTimer = new FocusTimer(getApplicationContext());
                //focusTimer.startfocustimer(focusTimeInMillis);
            }
        }
    }
    public void onClickStartButton() {
        // Retrieve the selected focus time (e.g., from a spinner or button click)
        long focusTimeInMillis = 30 * 60 * 1000; // Example: 30 minutes

        Intent resultIntent = new Intent();
        resultIntent.putExtra("focusTimeInMillis", focusTimeInMillis);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}

