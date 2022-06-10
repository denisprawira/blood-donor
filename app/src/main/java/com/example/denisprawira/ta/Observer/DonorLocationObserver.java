package com.example.denisprawira.ta.Observer;

import java.util.Observable;

public class DonorLocationObserver extends Observable {
    private static final DonorLocationObserver instance = new DonorLocationObserver();

    public DonorLocationObserver getInstance() {
        return instance;
    }

    public DonorLocationObserver() {
    }

    public void updateValue(Object data) {
        synchronized (this) {
            setChanged();
            notifyObservers(data);
        }
    }
}
