

package com.example.taskoptimizer;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;

import java.io.Serializable;

public class FocusTimer extends NotificationListenerService {
    private Context context;

    private BroadcastReceiver notificationPolicyAccessReceiver;

    public FocusTimer(Context context) {
        this.context = context;
        setupNotificationPolicyAccessReceiver();
    }

    private void setupNotificationPolicyAccessReceiver() {
        notificationPolicyAccessReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (isNotificationPolicyAccessGranted() && areNotificationsEnabled()) {
                    // Notification policy access granted, start the focus timer


                }
            }
        };

        IntentFilter filter = new IntentFilter(NotificationManager.ACTION_NOTIFICATION_POLICY_ACCESS_GRANTED_CHANGED);
        context.registerReceiver(notificationPolicyAccessReceiver, filter);

    }

    private void cleanupNotificationPolicyAccessReceiver() {
        if (notificationPolicyAccessReceiver != null) {
            context.unregisterReceiver(notificationPolicyAccessReceiver);
            notificationPolicyAccessReceiver = null;
        }
    }

    public boolean isNotificationPolicyAccessGranted() {
        //NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);

        return notificationManager.isNotificationPolicyAccessGranted();
    }


    public void requestNotificationPolicyAccess() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Enable Notifications");
        builder.setMessage("Please enable notifications for this app to receive alerts even when 'Do Not Disturb' is enabled.");
        builder.setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openAppSettings();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    public void openAppSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
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

    public void cleanup() {
        cleanupNotificationPolicyAccessReceiver();
    }

    public boolean hasDndAccess() {
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        return notificationManager.isNotificationPolicyAccessGranted();
    }

    public void requestDndAccess() {
        Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS); // Requesting access to settings
        context.startActivity(intent);
    }

}
