package com.android.doral.Utils;

import android.content.Context;

import java.util.Observable;

public class AppObserver extends Observable {

    /* nStatusType = 1 (Syncing)
       nStatusType = 2 (Synced) */

    private int nStatusType;
    Context context;

    public AppObserver(Context context) {
        this.context = context;
    }

    public void setValue(int nStatusTyp) {
        this.nStatusType = nStatusTyp;
        setChanged();
        notifyObservers();
    }

    public int getValue() {
        return nStatusType;
    }
}
