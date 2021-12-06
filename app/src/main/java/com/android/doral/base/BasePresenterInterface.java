package com.android.doral.base;

import android.content.Context;

public interface BasePresenterInterface {
    void sendRequest(Context context,  int reqCode);
}
