package com.android.doral;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.android.Vendor.Activity.VendorDashboardActivity;
import com.android.doral.Utils.MyPref;
import com.android.doral.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (new MyPref(context).getData(MyPref.Keys.IsLogin, false)) {
                    if (new MyPref(context).getUserData().getRoles().get(new MyPref(context).getUserData().getRoles().size() - 1).getName().equalsIgnoreCase("clinician")) {

                        startActivity(new Intent(SplashActivity.this, NewDashboardActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, VendorDashboardActivity.class));
                    }

                } else {

                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));

                }

                finishAffinity();

            }
        }, 3000);
    }
}