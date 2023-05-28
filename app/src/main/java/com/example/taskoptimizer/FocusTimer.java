package com.example.taskoptimizer;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

public class FocusTimer {
    private Context context;
    private static final int REQUEST_CODE_NOTIFICATION_POLICY_ACCESS = 1001;

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

    public void enableDoNotDisturb() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!isDoNotDisturbEnabled()) {
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
    }

    public void disableDoNotDisturb() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isDoNotDisturbEnabled()) {
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                if (notificationManager != null) {
                    notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL);
                }
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