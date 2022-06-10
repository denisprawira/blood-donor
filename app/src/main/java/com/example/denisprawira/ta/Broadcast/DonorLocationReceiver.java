package com.example.denisprawira.ta.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.denisprawira.ta.Observer.DonorLocationObserver;

public class DonorLocationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DonorLocationObserver observableObject = new DonorLocationObserver();
        observableObject.getInstance().updateValue(intent);

    }
}