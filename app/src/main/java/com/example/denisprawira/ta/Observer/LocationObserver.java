package com.example.denisprawira.ta.Observer;

import java.util.Observable;

public class LocationObserver extends Observable {
    private static final LocationObserver instance = new LocationObserver();

    public LocationObserver getInstance() {
        return instance;
    }

    public LocationObserver() {
    }

    public void updateValue(Object data) {
        synchronized (this) {
            setChanged();
            notifyObservers(data);
        }
    }
}
