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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.android.doral.R;
import com.google.android.material.snackbar.Snackbar;


public class BaseFragment extends Fragment {

//    MyPref myPref;
    public FragmentActivity context=getActivity();
    ProgressDialog progressDialog;



   /* public MyPref getMyPref() {
        if (myPref == null) {
            myPref = new MyPref(this);
        }
        return myPref;
    }*/

    public void errorMessage(View view, String error) {
        Snackbar snackbar = Snackbar
                .make(view, error, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.show();
    }



    public void showDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
