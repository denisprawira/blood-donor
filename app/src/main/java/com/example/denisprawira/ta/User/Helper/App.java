package com.example.denisprawira.ta.User.Helper;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    public static final String CHANNEL_ID="Channel Services";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }


    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel servicesChannel = new NotificationChannel(
                    CHANNEL_ID,"Example Services Channel",NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(servicesChannel);
        }

    }
}
