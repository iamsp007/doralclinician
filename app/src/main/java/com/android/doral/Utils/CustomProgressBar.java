package com.android.doral.Utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.android.doral.R;

public class CustomProgressBar extends Dialog {

    public Context c;

    public CustomProgressBar(Context a) {
        super(a);
        this.c = a;
    }

    public CustomProgressBar(Context a, int myTheme) {
        super(a, myTheme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(
                R.color.transparent);
        setCancelable(false);
        setContentView(R.layout.view_custom_progress_bar);

    }
}