package com.android.doral;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.android.doral.Utils.MyPref;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityChangePasswordBinding;
import com.android.doral.dialog.CustomAlertDialog;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.BaseModel;

import java.util.HashMap;

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener, BaseViewInterface {
    ActivityChangePasswordBinding binding;
    BasePresenterInterface presenterInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setStatusBarColor(R.color.colorPrimary);
        presenterInterface = new Presenter(this);

        binding.btnSubmit.setOnClickListener(this::onClick);
        binding.imgMenu.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_submit) {
            if (isValidate()) {
                HashMap<String, String> map = new HashMap<>();
                map.put("email", new MyPref(ChangePasswordActivity.this).getUserData().getEmail());
                map.put("old_password", binding.edtOldPassword.getText().toString().trim());
                map.put("new_password", binding.edtNewPassword.getText().toString().trim());
                map.put("confirm_password", binding.edtConfirmPassword.getText().toString().trim());
                presenterInterface.sendRequest(ChangePasswordActivity.this, "", map, ApiCallInterface.CHANGE_PASSWORD);

            }
        }
        if (view.getId() == R.id.img_menu) {
            onBackPressed();
        }

    }

    private boolean isValidate() {

        if (binding.edtOldPassword.getText().toString().trim().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "Please enter old password");
            return false;
        } else if (binding.edtNewPassword.getText().toString().trim().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "Please enter new password");
            return false;
        } else if (binding.edtConfirmPassword.getText().toString().trim().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "Please enter confirm password");
            return false;
        } else if (!binding.edtNewPassword.getText().toString().trim().equals(binding.edtConfirmPassword.getText().toString().trim())) {
            errorMessage(binding.getRoot(), "new password and confirm passwrod does not match");
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
        if (requestCode == ApiCallInterface.CHANGE_PASSWORD) {
            BaseModel baseModel = (BaseModel) success;
            if (baseModel.isStatus().equals("true")) {
                new CustomAlertDialog(context, baseModel.getMessage(), baseModel.getMessage(), "OK", "", new View.OnClickListener() {
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
}