package com.example.taskoptimizer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class CountdownNotification{
    private Context context;
    private NotificationManagerCompat notificationManager;
    private int notificationId = 1;
    private String channelId = "countdown_channel";
    private String channelName = "Countdown Channel";

    public CountdownNotification(Context context) {
        this.context = context;
        notificationManager = NotificationManagerCompat.from(context);

        // Create notification channel (required for Android 8.0 and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW);
            channel.setDescription("Countdown Notification Channel");
            channel.enableLights(true);
            channel.setLightColor(Color.GREEN);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void showCountdownNotification(int minutes) {
        String contentText = "Countdown in progress";
        String contentTitle = minutes + " minutes remaining";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true);

        Notification notification = builder.build();
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(notificationId, notification);
    }

    public void cancelNotification() {
        notificationManager.cancel(notificationId);
    }
}
