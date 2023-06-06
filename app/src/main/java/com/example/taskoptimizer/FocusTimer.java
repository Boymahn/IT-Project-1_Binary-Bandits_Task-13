/* package com.example.taskoptimizer;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.Settings;

public class FocusTimer {
    private Context context;
    private CountDownTimer timer;
    private long focusTimeInMillis;

    public FocusTimer(Context context) {
        this.context = context;
    }

    public boolean isNotificationPolicyAccessGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                return notificationManager.isNotificationPolicyAccessGranted();
            }
        }
        return false;
    }

    public void requestNotificationPolicyAccess() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!isNotificationPolicyAccessGranted()) {
                Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                context.startActivity(intent);
            }
        }
    }

    public void startFocusTimer(long focusTimeInMillis) {
        this.focusTimeInMillis = focusTimeInMillis;
        if (isNotificationPolicyAccessGranted()) {
            enableDoNotDisturb();
        } else {
            requestNotificationPolicyAccess();
        }

        timer = new CountDownTimer(focusTimeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Timer is ticking (1 second interval)
                long minutesLeft = millisUntilFinished / (60 * 1000);
                // Update UI or perform actions for each tick
            }

            @Override
            public void onFinish() {
                // Timer finished
                disableDoNotDisturb();
                // Perform actions after the focus time is over
            }
        }.start();
    }

    public void stopFocusTimer() {
        if (timer != null) {
            timer.cancel();
            disableDoNotDisturb();
        }
    }

    public void enableDoNotDisturb() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !isDoNotDisturbEnabled()) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALARMS);
                } else {
                    notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE);
                }
            }
        }
    }

    public void disableDoNotDisturb() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isDoNotDisturbEnabled()) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL);
            }
        }
    }

    public boolean isDoNotDisturbEnabled() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                return notificationManager.isNotificationPolicyAccessGranted() &&
                        notificationManager.getCurrentInterruptionFilter() != NotificationManager.INTERRUPTION_FILTER_ALL;
            }
        }
        return false;
    }
} */

package com.example.taskoptimizer;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.Settings;

public class FocusTimer {
    private Context context;
    private CountDownTimer timer;
    private long focusTimeInMillis;

    private String channelId = "countdown_channel";

    public FocusTimer(Context context) {
        this.context = context;
    }

    public boolean isNotificationPolicyAccessGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                return notificationManager.isNotificationPolicyAccessGranted();
            }
        }
        return false;
    }

    public void requestNotificationPolicyAccess() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!isNotificationPolicyAccessGranted()) {
                Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                context.startActivity(intent);
            } else if (!areNotificationsEnabled()) {
                Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
                context.startActivity(intent);
            }
        }
    }

    public boolean areNotificationsEnabled() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                return notificationManager.getImportance() != NotificationManager.IMPORTANCE_NONE;
            }
        }
        return true;
    }

    public void startFocusTimer(long focusTimeInMillis) {
        this.focusTimeInMillis = focusTimeInMillis;
        if (isNotificationPolicyAccessGranted()) {
            enableDoNotDisturb();
        } else {
            requestNotificationPolicyAccess();
        }

        timer = new CountDownTimer(focusTimeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Timer is ticking (1 second interval)
                long minutesLeft = millisUntilFinished / (60 * 1000);
                // Update UI or perform actions for each tick
            }

            @Override
            public void onFinish() {
                // Timer finished
                disableDoNotDisturb();
                // Perform actions after the focus time is over
            }
        }.start();
    }

    public void stopFocusTimer() {
        if (timer != null) {
            timer.cancel();
            disableDoNotDisturb();
        }
    }

    public void enableDoNotDisturb() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !isDoNotDisturbEnabled()) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALARMS);
                } else {
                    notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE);
                }
            }
        }
    }

    public void disableDoNotDisturb() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isDoNotDisturbEnabled()) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL);
            }
        }
    }

    public boolean isDoNotDisturbEnabled() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                return notificationManager.isNotificationPolicyAccessGranted() &&
                        notificationManager.getCurrentInterruptionFilter() != NotificationManager.INTERRUPTION_FILTER_ALL;
            }
        }
        return false;
    }
}