package com.android.doral;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.android.doral.Utils.StringUtils;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityForgotPasswordBinding;
import com.android.doral.databinding.ActivityResetPasswordBinding;
import com.android.doral.dialog.CustomAlertDialog;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.BaseModel;
import com.android.doral.retrofit.model.LoginModel;
import com.google.gson.Gson;

import java.util.HashMap;

public class ResetPasswordActivity extends BaseActivity implements BaseViewInterface {
    ActivityResetPasswordBinding binding;
    BasePresenterInterface presenterInterface;
    private int passwordNotVisible=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setStatusBarColor(R.color.colorPrimary);
        presenterInterface = new Presenter(this);
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    HashMap<String,Object>params=new HashMap<>();
                    params.put("email",binding.edtEmail.getText().toString());
                    params.put("new_password",binding.edtNewPW.getText().toString());
                    params.put("confirm_password",binding.edtConfirmPW.getText().toString());

                    presenterInterface.callAPI(context, null,params,  ApiCallInterface.PASSWORD_RESET,"");
                }
            }
        });
        binding.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ImageView imagepass = (ImageView) findViewById(R.id.imagepassword);
        imagepass.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if (passwordNotVisible == 1) {
                    binding.edtNewPW.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                    imagepass.setImageResource(R.drawable.ic_baseline_visibility_24);
                    passwordNotVisible = 0;
                } else {

                    binding.edtNewPW.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    imagepass.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                    passwordNotVisible = 1;
                }

                binding.edtNewPW.setSelection(binding.edtNewPW.length());


            }

        });


        ImageView imagepass1 = (ImageView) findViewById(R.id.imagepassword1);
        imagepass1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if (passwordNotVisible == 1) {
                    binding.edtConfirmPW.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                    imagepass1.setImageResource(R.drawable.ic_baseline_visibility_24);
                    passwordNotVisible = 0;
                } else {

                    binding.edtConfirmPW.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    imagepass1.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                    passwordNotVisible = 1;
                }

                binding.edtConfirmPW.setSelection(binding.edtConfirmPW.length());


            }

        });


    }

    private boolean validate() {

        if (binding.edtEmail.getText().toString().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "please enter Email");
            return false;
        }
        if (TextUtils.isEmpty(binding.edtNewPW.getText().toString().trim()) || binding.edtNewPW.getText().toString().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "please enter New Password");
            return false;
        }
        if (TextUtils.isEmpty(binding.edtConfirmPW.getText().toString().trim()) || binding.edtConfirmPW.getText().toString().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "please enter Confirm Password");
            return false;
        }
        if (!binding.edtConfirmPW.getText().toString().equals(binding.edtNewPW.getText().toString())) {
            errorMessage(binding.getRoot(), "Confirm password should be same as New Password");
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
            new CustomAlertDialog(context, baseModel.getMessage(), "Password Password", "OK", "", new View.OnClickListener() {
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