package com.android.doral;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.android.CovidFormActivity;
import com.android.doral.Utils.PhoneTextFormatter;
import com.android.doral.Utils.StringUtils;
import com.android.doral.Utils.Utility;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseFragment;
import com.android.doral.databinding.ActivityCovidStepThreeBinding;
import com.android.doral.retrofit.model.CovidFormModel;

import java.util.HashMap;

public class Covid_Step_Three_Activity extends BaseFragment {
    ActivityCovidStepThreeBinding binding;

    private String race;


    ViewPager viewPager;


    public Covid_Step_Three_Activity(ViewPager viewPager) {
        // Required empty public constructor
        this.viewPager = viewPager;
    }

    // TODO: Rename and change types and number of parameters
    public static Covid_Step_Three_Activity newInstance(ViewPager viewPager) {
        Covid_Step_Three_Activity fragment = new Covid_Step_Three_Activity(viewPager);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ActivityCovidStepThreeBinding.inflate(getLayoutInflater());

        binding.rlBottom.imgPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });
        binding.rlBottom.imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidate()) {

                    ((CovidFormActivity) getActivity()).formModel.setEmail(binding.edtEmail.getText().toString().trim());
                    ((CovidFormActivity) getActivity()).formModel.setPhone(binding.edtPhone.getText().toString().trim());
                    ((CovidFormActivity) getActivity()).formModel.setParent_name(binding.ettParent.getText().toString().trim());
                    ((CovidFormActivity) getActivity()).formModel.setRace(race);
                    viewPager.setCurrentItem(viewPager.getCurrentItem() +1);

                }
            }
        });

        binding.edtPhone.addTextChangedListener(new PhoneTextFormatter(binding.edtPhone, "+1 (###) ###-####"));

        binding.spRace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                race = getResources().getStringArray(R.array.race_sp_value)[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return binding.getRoot();
    }

    private boolean isValidate() {

        if (!StringUtils.isNotEmpty(binding.edtEmail.getText().toString().trim())) {
            errorMessage(binding.getRoot(), "Please enter email");
            return false;
        } else if (!Utility.isValidEmail(binding.edtEmail.getText().toString())) {
            errorMessage(binding.getRoot(), "Please enter valid email address");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.edtPhone.getText().toString().trim())) {
            errorMessage(binding.getRoot(), "Please enter phone");
            return false;
        } else if (binding.spRace.getSelectedItemPosition() == 0) {
            errorMessage(binding.getRoot(), "Please select race");
            return false;
        }

        return true;
    }
}
