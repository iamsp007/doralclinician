package com.android.doral;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.CovidFormActivity;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseFragment;
import com.android.doral.databinding.ActivityCovid14Binding;
import com.android.doral.retrofit.model.CovidFormModel;

public class Covid14 extends BaseFragment {
    ActivityCovid14Binding binding;


    ViewPager viewPager;


    public Covid14(ViewPager viewPager) {
        // Required empty public constructor
        this.viewPager = viewPager;
    }

    // TODO: Rename and change types and number of parameters
    public static Covid14 newInstance(ViewPager viewPager) {
        Covid14 fragment = new Covid14(viewPager);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ActivityCovid14Binding.inflate(getLayoutInflater());

        binding.rlBottom.imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.cbAuth.isChecked()) {
                    ((CovidFormActivity) getActivity()).formModel.setIs_use_authorization(true);
                    viewPager.setCurrentItem(viewPager.getCurrentItem() +1);
                } else {
                    errorMessage(binding.getRoot(), "please accept Emergency Use Authorization");
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
    }
}