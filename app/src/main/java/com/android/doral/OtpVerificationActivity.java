package com.android.doral;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityOtpVerificationBinding;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.OtpResponseModel;
import com.android.doral.retrofit.model.UserModel;

import java.util.HashMap;

public class OtpVerificationActivity extends BaseActivity implements BaseViewInterface {
    ActivityOtpVerificationBinding binding;
    BasePresenterInterface presenterInterface;
    private String mobile, email;
    private OtpResponseModel otpResponseModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        presenterInterface = new Presenter(this);
        mobile = getIntent().getStringExtra("mobile");
        email = getIntent().getStringExtra("email");
        otpResponseModel = (OtpResponseModel) getIntent().getSerializableExtra("data");
        //sendOtp();
        //   binding.tvMobile.setText("(" + mobile + ")");

        binding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                   /* SendOtpModel sendOtpModel = new SendOtpModel();
                    sendOtpModel.setMobile(mobile);
                    sendOtpModel.setCode(binding.edtOtp.getText().toString().trim());
                    sendOtpModel.setRequest_id(otpResponseModel.getData());*/


                    HashMap<String, String> map = new HashMap<>();
                    map.put("email", email);
                    map.put("phone", mobile);
                    map.put("code", binding.edtOtp.getText().toString().trim());
                    map.put("request_id", otpResponseModel.getData());
                    presenterInterface.sendRequest(context, "", map, ApiCallInterface.VERIFY_OTP);

                }
            }
        });
        binding.tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendOtp();
            }
        });
        binding.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void sendOtp() {
       /* SendOtpModel sendOptModel = new SendOtpModel();
        sendOptModel.setMobile(mobile);*/


        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("phone", mobile);
        presenterInterface.sendRequest(context, "", map, ApiCallInterface.SEND_OTP);

    }

    private boolean validate() {

        if (binding.edtOtp.getText().toString().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "please enter OTP");
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

        if (requestCode == ApiCallInterface.SEND_OTP) {
            otpResponseModel = (OtpResponseModel) success;

        }

        if (requestCode == ApiCallInterface.VERIFY_OTP) {
            UserModel baseModel = (UserModel) success;
            if (baseModel.isStatus().equals("true")) {
             /*   setResult(RESULT_OK);
                finish();*/

              /*  new MyPref(context).setUserData(baseModel.getData());
                new MyPref(context).setData(MyPref.Keys.token, baseModel.getData().getAccess_token());*/
                Intent intent = new Intent(OtpVerificationActivity.this, LoginActivity.class);
                startActivity(intent);
                finishAffinity();

            } else {
                errorMessage(binding.getRoot(), baseModel.getMessage());
            }

        }

    }
}