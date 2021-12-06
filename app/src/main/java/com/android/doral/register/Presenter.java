package com.android.doral.register;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.android.doral.Utils.AppClass;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.retrofit.DisposableCallback;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;


public class Presenter implements BasePresenterInterface {

    BaseViewInterface mvi;
    private String TAG = "UserPresenter";

    public Presenter(BaseViewInterface mvi) {
        this.mvi = mvi;
    }


    @Override
    public void sendRequest(Context context, String body, HashMap<String, String> param, int reqCode,String... document) {
        try {
            ((AppClass) ((AppCompatActivity) context).getApplication()).getApiTask().sendRequest(body, param, reqCode, new DisposableCallback(context, reqCode, mvi, true),document);
        } catch (CertificateException | UnrecoverableKeyException | NoSuchAlgorithmException | KeyStoreException | KeyManagementException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callAPI(Context context, String body, HashMap<String, Object> param, int reqCode, String... document) {
        try {
            ((AppClass) ((AppCompatActivity) context).getApplication()).getApiTask().callAPI(body, param, reqCode, new DisposableCallback(context, reqCode, mvi,true),document);
        } catch (CertificateException | UnrecoverableKeyException | NoSuchAlgorithmException | KeyStoreException | KeyManagementException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendRequest(Context context, String body, HashMap<String, String> param, int reqCode, boolean isProgress, String... document) {
        try {
            ((AppClass) ((AppCompatActivity) context).getApplication()).getApiTask().sendRequest(body, param, reqCode, new DisposableCallback(context, reqCode, mvi, isProgress),document);
        } catch (CertificateException | UnrecoverableKeyException | NoSuchAlgorithmException | KeyStoreException | KeyManagementException | IOException e) {
            e.printStackTrace();
        }
    }
}
