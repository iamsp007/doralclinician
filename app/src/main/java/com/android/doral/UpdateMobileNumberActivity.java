package com.android.doral;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityOtpVerificationBinding;
import com.android.doral.databinding.ActivityUpdateMobileBinding;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.OtpResponseModel;

import java.util.HashMap;

public class UpdateMobileNumberActivity extends BaseActivity implements BaseViewInterface {
    ActivityUpdateMobileBinding binding;
    BasePresenterInterface presenterInterface;
    private String mobile, email;
    private OtpResponseModel otpResponseModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateMobileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        presenterInterface = new Presenter(this);
        email = getIntent().getStringExtra("email");
        otpResponseModel = (OtpResponseModel) getIntent().getSerializableExtra("data");

        binding.activityUpdateMobileNumberBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {

                    sendOtp();

                }
            }
        });


    }

    private void sendOtp() {

        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("phone", binding.activityUpdateMobileNumberEtPhone.getText().toString());
        presenterInterface.sendRequest(context, "", map, ApiCallInterface.SEND_OTP);

    }

    private boolean validate() {

        if (binding.activityUpdateMobileNumberEtPhone.getText().toString().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "please enter mobile number");
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

            OtpResponseModel baseModel = (OtpResponseModel) success;
            if (baseModel.isStatus().equals("true")) {

                startActivity(new Intent(UpdateMobileNumberActivity.this, OtpVerificationActivity.class).putExtra("mobile", binding.activityUpdateMobileNumberEtPhone.getText().toString()).putExtra("email", email).putExtra("data", baseModel));
                finish();

            }

        }

    }
}