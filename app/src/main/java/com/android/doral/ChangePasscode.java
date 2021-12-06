package com.android.doral;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityChangePasscodeBinding;
import com.android.doral.databinding.ActivityPasscodeBinding;
import com.android.doral.retrofit.ApiCallInterface;

import java.util.HashMap;

public class ChangePasscode extends BaseActivity implements BaseViewInterface, View.OnClickListener {
    ActivityChangePasscodeBinding binding;
    private int passwordNotVisible=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasscodeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setStatusBarColor(R.color.colorPrimary);
        binding.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
//                    HashMap<String,Object> params=new HashMap<>();
//                    params.put("email",binding.edtEmail.getText().toString());
//                    params.put("new_password",binding.edtNewPW.getText().toString());
//                    params.put("confirm_password",binding.edtConfirmPW.getText().toString());

                   // presenterInterface.callAPI(context, null,params,  ApiCallInterface.PASSWORD_RESET,"");
                }
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
                    binding.edtNewPW1.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                    imagepass1.setImageResource(R.drawable.ic_baseline_visibility_24);
                    passwordNotVisible = 0;
                } else {

                    binding.edtNewPW1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    imagepass1.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                    passwordNotVisible = 1;
                }

                binding.edtNewPW1.setSelection(binding.edtNewPW1.length());


            }

        });

        ImageView imagepass2 = (ImageView) findViewById(R.id.imagepassword2);
        imagepass2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if (passwordNotVisible == 1) {
                    binding.edtNewPW2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                    imagepass1.setImageResource(R.drawable.ic_baseline_visibility_24);
                    passwordNotVisible = 0;
                } else {

                    binding.edtNewPW2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    imagepass1.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                    passwordNotVisible = 1;
                }

                binding.edtNewPW2.setSelection(binding.edtNewPW2.length());


            }

        });
    }

    private boolean validate() {

        if (binding.edtNewPW.getText().toString().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "please enter Old Passcode");
            return false;
        }
        if (TextUtils.isEmpty(binding.edtNewPW1.getText().toString().trim()) || binding.edtNewPW1.getText().toString().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "please enter New Passcode");
            return false;
        }
        if (TextUtils.isEmpty(binding.edtNewPW2.getText().toString().trim()) || binding.edtNewPW2.getText().toString().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "please enter Confirm Passcode");
            return false;
        }
        if (!binding.edtNewPW2.getText().toString().equals(binding.edtNewPW1.getText().toString())) {
            errorMessage(binding.getRoot(), "Confirm Passcode should be same as New Passcode");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void retry(int pos) {

    }

    @Override
    public void onError(String errorMsg, int requestCode, int resultCode) {

    }

    @Override
    public void onSuccess(Object success, int requestCode, int resultCode) {

    }
}