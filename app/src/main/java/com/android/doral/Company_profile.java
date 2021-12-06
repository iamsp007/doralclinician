package com.android.doral;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.android.doral.base.BaseViewInterface;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;

public class Company_profile extends AppCompatActivity {
    BasePresenterInterface presenterInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);


    }
}