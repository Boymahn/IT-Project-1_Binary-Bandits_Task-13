package com.example.taskoptimizer;

import android.app.NotificationManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.DatabaseUtils;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.view.View;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

    public class FocusTimer extends NotificationListenerService {

        public static boolean BLOCK_ALL = false;

        private Timer timer;
        private int timeRemaining;

        public FocusTimer(int timeInSeconds) {
            timer = new Timer();
            timeRemaining = timeInSeconds;
        }

        public FocusTimer() {
            super();
        }


      /**  public void onNotificationPosted(StatusBarNotification sbn) {
            super.onNotificationPosted(sbn);
            if(BLOCK_ALL)cancelAllNotifications();
            List<String> appNameList;

            appNameList = appsDB.dbDAO().getAllAppNames();
            if(appNameList.contains(getApplicationName(sbn.getPackageName()))){  // if the blocked list has the app name of the notification
                cancelNotification(sbn.getKey());
            }
        }
       ***/
        /***
         * getApplicationName: get the application name of the notification belongs to
         * @param pack: the package name; a StatusBarNotification.getPackageName()
         * @return
         */
        private String getApplicationName(String pack){
            final PackageManager pm = getApplication().getPackageManager();
            ApplicationInfo appInfo;
            try{
                appInfo = pm.getApplicationInfo(pack, 0);  // getting the application info
            }catch(PackageManager.NameNotFoundException e){
                appInfo = null;
            }
            final String appName = (String) (appInfo != null? pm.getApplicationLabel(appInfo) : "(unknown)");  // if the appInfo is not null get the label of the Application
            return appName;
        }

        public void start() {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    timeRemaining--;
                    if (timeRemaining == 0) {
                        stop();
                    }
                }
            }, 2000, 2000);
        }
        public void stop() {
            timer.cancel();
        }

        public int getTimeRemaining() {
            return timeRemaining;
        }
    }

