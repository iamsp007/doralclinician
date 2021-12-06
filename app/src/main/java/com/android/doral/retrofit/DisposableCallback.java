package com.android.doral.retrofit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.doral.LoginActivity;
import com.android.doral.Utils.Logger;
import com.android.doral.Utils.MyPref;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.retrofit.model.BaseModel;
import com.google.gson.Gson;


import java.io.IOException;
import java.net.ConnectException;

import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

public class DisposableCallback<T> extends DisposableObserver<T> {
    private Context context;
    private int reqCode;
    private BaseViewInterface onApiCallListerner;
    private boolean isProgress;
    private ProgressDialog progressDialog;


    public DisposableCallback(Context context, int reqCode, BaseViewInterface onApiCallListerner, boolean isProgress) {
        this.context = context;
        this.reqCode = reqCode;
        this.onApiCallListerner = onApiCallListerner;
        this.isProgress = isProgress;
        if (this.isProgress)
            showProgrssDialog();


    }

    public DisposableCallback(Context context, int reqCode, BaseViewInterface onApiCallListerner) {
        this(context, reqCode, onApiCallListerner, false);


    }

    @Override
    public void onNext(Object t) {
        dismissDialog();
        if (((AppCompatActivity) context).isFinishing()) {
            return;
        }

        onApiCallListerner.onSuccess(t, reqCode, 200);
    }

    @Override
    public void onError(Throwable e) {
        dismissDialog();
        if (((AppCompatActivity) context).isFinishing()) {
            return;
        }
        Logger.e(e.getMessage());
        //hideProgress();
        if (e instanceof UndeliverableException) {
            onApiCallListerner.onError(e.getMessage(), reqCode, e.hashCode());
        } else if (e instanceof ConnectException) {
            //  AppClass.networkConnectivity.errorMessage(context, onApiCallListerner, reqCode);
        } else if (e instanceof HttpException) {

            if (((HttpException) e).code() == 401) {

                context.startActivity(new Intent(context, LoginActivity.class));
                new MyPref(context).clearPrefs();
                ((AppCompatActivity) context).finishAffinity();

                return;
            } else {
                HttpException httpException = (HttpException) e;
                try {
                    String errorBody = httpException.response().errorBody().string();
                    BaseModel baseModel = new Gson().fromJson(errorBody, BaseModel.class);
                    onApiCallListerner.onError(baseModel.getMessage(), reqCode, ((HttpException) e).code());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    @Override
    public void onComplete() {
        dismissDialog();
    }

    private void showProgrssDialog() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading");
        progressDialog.show();
    }

    private void dismissDialog() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }
}
