package com.android.doral.register;

import android.content.Context;
import android.view.View;

import java.util.HashMap;


public interface BasePresenterInterface {

    void sendRequest(Context context, String body, HashMap<String, String> param, int reqCode, String... document);

    void callAPI(Context context, String body, HashMap<String, Object> param, int reqCode, String... document);

    void sendRequest(Context context, String body, HashMap<String, String> param, int reqCode, boolean isProgress, String... document);
}
