package com.android.doral;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.android.CovidFormActivity;
import com.android.doral.Utils.PhoneTextFormatter;
import com.android.doral.Utils.StringUtils;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseFragment;
import com.android.doral.databinding.ActivityCovidStepSixBinding;
import com.android.doral.retrofit.model.CovidFormModel;

import java.util.HashMap;

public class Covid_Step_Six_Activity extends BaseFragment {
    ActivityCovidStepSixBinding binding;


    ViewPager viewPager;


    public Covid_Step_Six_Activity(ViewPager viewPager) {
        // Required empty public constructor
        this.viewPager = viewPager;
    }

    // TODO: Rename and change types and number of parameters
    public static Covid_Step_Six_Activity newInstance(ViewPager viewPager) {
        Covid_Step_Six_Activity fragment = new Covid_Step_Six_Activity(viewPager);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ActivityCovidStepSixBinding.inflate(getLayoutInflater());

        binding.rlBottom.imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidate()) {

                    ((CovidFormActivity) getActivity()).formModel.setClinicOffice_site(binding.etClinicOfficeSite.getText().toString().trim());
                    ((CovidFormActivity) getActivity()).formModel.setPrimary_care(binding.etPrimaryCare.getText().toString().trim());
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);

                }
            }
        });

        binding.rlBottom.imgPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });

        return binding.getRoot();
        //  binding.etPrimaryCare.addTextChangedListener(new PhoneTextFormatter(binding.etPrimaryCare, "+1 (###) ###-####"));
    }

    private boolean isValidate() {

        if (!StringUtils.isNotEmpty(binding.etClinicOfficeSite.getText().toString().trim())) {
            errorMessage(binding.getRoot(), "Please enter Clinic Office Site where  vaccine is Administered");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.etPrimaryCare.getText().toString().trim())) {
            errorMessage(binding.getRoot(), "Please enter Primary care Physician  Address/Phone Number");
            return false;
        }
        return true;
    }
}
