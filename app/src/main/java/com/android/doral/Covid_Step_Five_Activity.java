package com.android.doral;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.android.doral.Utils.StringUtils;
import com.android.doral.base.BaseActivity;
import com.android.doral.databinding.ActivityCovidStepFiveBinding;

public class Covid_Step_Five_Activity extends BaseActivity {
    ActivityCovidStepFiveBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCovidStepFiveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.rlBottom.imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidate()) {
                    startActivity(new Intent(Covid_Step_Five_Activity.this, Covid_Step_Six_Activity.class));
                }
            }
        });

        binding.rlBottom.imgPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    private boolean isValidate() {

        if (!StringUtils.isNotEmpty(binding.etSubscriberRelPatient.getText().toString().trim())) {
            errorMessage(binding.getRoot(), "Please enter Subscriber Rel.to Patient");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.etPrimaryInsuranceGroup.getText().toString().trim())) {
            errorMessage(binding.getRoot(), "Please enter Primary Insurance Group");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.etClPrimaryInsuranceAddress.getText().toString().trim())) {
            errorMessage(binding.getRoot(), "Please enter Primary Insurance Address");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.etSubscriber.getText().toString().trim())) {
            errorMessage(binding.getRoot(), "Please enter subscriber name");
            return false;
        }
        return true;
    }
}
