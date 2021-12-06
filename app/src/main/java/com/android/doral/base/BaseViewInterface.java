package com.android.doral.base;

import android.view.View;

public interface BaseViewInterface {

     void retry(int pos);

    void onError(String errorMsg, int requestCode, int resultCode);

    void onSuccess(Object success, int requestCode, int resultCode);

}
