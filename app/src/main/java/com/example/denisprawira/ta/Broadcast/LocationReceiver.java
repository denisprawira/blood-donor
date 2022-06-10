package com.example.denisprawira.ta.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.denisprawira.ta.Observer.LocationObserver;

public class LocationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        LocationObserver observableObject = new LocationObserver();
        observableObject.getInstance().updateValue(intent);

    }
}