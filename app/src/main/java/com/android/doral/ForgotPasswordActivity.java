package com.android.doral;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityForgotPasswordBinding;
import com.android.doral.dialog.CustomAlertDialog;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.BaseModel;
import com.android.doral.retrofit.model.LoginModel;
import com.google.gson.Gson;

public class ForgotPasswordActivity extends BaseActivity implements BaseViewInterface {
    ActivityForgotPasswordBinding binding;
    BasePresenterInterface presenterInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setStatusBarColor(R.color.colorPrimary);
        presenterInterface = new Presenter(this);
        binding.activityForgotPasswordBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    LoginModel userModel = new LoginModel();
                    userModel.setEmail(binding.activityForgotPasswordEtEmail.getText().toString());

                    presenterInterface.sendRequest(context, new Gson().toJson(userModel), null, ApiCallInterface.FORGOT_PASSWORD);
                }
            }
        });


    }

    private boolean validate() {

        if (binding.activityForgotPasswordEtEmail.getText().toString().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "please enter email or phone number");
            return false;
        }
        return true;
    }

    @Override
    public void retry(int pos) {

    }

    @Override
    public void onError(String errorMsg, int requestCode, int resultCode) {
        errorMessage(binding.getRoot(), errorMsg);
    }

    @Override
    public void onSuccess(Object success, int requestCode, int resultCode) {
        BaseModel baseModel = (BaseModel) success;
        if (baseModel.isStatus().equals("true")) {
            new CustomAlertDialog(context, baseModel.getMessage(), "Forgot Password", "OK", "", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    finish();
                }
            }).show();
        } else {
            errorMessage(binding.getRoot(), baseModel.getMessage());
        }
    }
}