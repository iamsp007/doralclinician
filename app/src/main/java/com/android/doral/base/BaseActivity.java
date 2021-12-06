package com.android.doral.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.doral.R;
import com.google.android.material.snackbar.Snackbar;


public class BaseActivity extends AppCompatActivity {

//    MyPref myPref;
    public Context context;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = BaseActivity.this;
      //  LocaleHelper.setLocale(this, Prefs.getInstance().getString(Prefs.ACTIVELANGUAGE, Prefs.ARABIC));
    }

   /* public MyPref getMyPref() {
        if (myPref == null) {
            myPref = new MyPref(this);
        }
        return myPref;
    }*/

    public void setStatusBarColor(int color) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (color != R.color.colorPrimaryDark)
                getWindow().getDecorView().setSystemUiVisibility(getWindow().getDecorView().SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(ContextCompat.getColor(BaseActivity.this, color));
        }
    }

    public void errorMessage(View view, String error) {
        Snackbar snackbar = Snackbar
                .make(view, error, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.show();
    }

    public void showAnimationWhileChangeScreen() {
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }

   /* public void errorMessage(View view, String error, BaseViewInterface viewInterface, int reqCode) {
        Snackbar snackbar = Snackbar
                .make(view, error, Snackbar.LENGTH_LONG)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewInterface.retry(reqCode);
                    }
                });
        snackbar.setActionTextColor(Color.RED);
        snackbar.setTextColor(Color.WHITE);
        snackbar.show();
    }*/

    public void showDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
