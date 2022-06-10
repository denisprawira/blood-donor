package com.example.denisprawira.ta.Helper;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.example.denisprawira.ta.R;

import io.getstream.chat.android.client.ChatClient;
import io.getstream.chat.android.client.logger.ChatLogLevel;
import io.getstream.chat.android.livedata.ChatDomain;

public class App extends Application {
    public static final String CHANNEL_ID="Channel Services";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        //ChatClient client = new ChatClient.Builder(getString(R.string.chat_stream_token), this).logLevel(ChatLogLevel.ALL).build();
        ChatClient client = new ChatClient.Builder(getString(R.string.chat_stream_token), getApplicationContext())
                .logLevel(ChatLogLevel.ALL) // Set to NOTHING in prod
                .build();
        new ChatDomain.Builder(client, getApplicationContext()).build();
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
