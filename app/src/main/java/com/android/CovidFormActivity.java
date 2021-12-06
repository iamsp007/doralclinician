package com.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.android.doral.Covid14;
import com.android.doral.Covid15;
import com.android.doral.Covid18;
import com.android.doral.Covid20;
import com.android.doral.Covid_Step_Four_Activity;
import com.android.doral.Covid_Step_One_Activity;
import com.android.doral.Covid_Step_Seven_Activity;
import com.android.doral.Covid_Step_Six_Activity;
import com.android.doral.Covid_Step_Three_Activity;
import com.android.doral.Covid_Step_Two_Activity;
import com.android.doral.Covid_Step_seventeen_Activity;
import com.android.doral.Covid_Step_sixsteen_Activity;
import com.android.doral.R;
import com.android.doral.Utils.DateUtils;
import com.android.doral.adapter.ViewPagerAdapter;
import com.android.doral.base.BaseActivity;
import com.android.doral.databinding.ActivityCovidFormBinding;
import com.android.doral.retrofit.model.CovidFormModel;
import com.android.doral.retrofit.model.FormModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CovidFormActivity extends BaseActivity {
    ActivityCovidFormBinding binding;
    ViewPagerAdapter viewPagerAdapter;

    public CovidFormModel formModel = new CovidFormModel();
    public String filling_date_time = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCovidFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.DATE_FORMATE_4);
        filling_date_time = "" + simpleDateFormat.format(Calendar.getInstance().getTime());
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(Covid_Step_One_Activity.newInstance(binding.rvForm));
        viewPagerAdapter.addFragment(Covid_Step_Two_Activity.newInstance(binding.rvForm));
        viewPagerAdapter.addFragment(Covid_Step_Three_Activity.newInstance(binding.rvForm));
        viewPagerAdapter.addFragment(Covid_Step_Four_Activity.newInstance(binding.rvForm));
        viewPagerAdapter.addFragment(Covid_Step_Six_Activity.newInstance(binding.rvForm));
        viewPagerAdapter.addFragment(Covid_Step_Seven_Activity.newInstance(binding.rvForm));
        viewPagerAdapter.addFragment(Covid14.newInstance(binding.rvForm));
        viewPagerAdapter.addFragment(Covid15.newInstance(binding.rvForm));
        viewPagerAdapter.addFragment(Covid_Step_sixsteen_Activity.newInstance(binding.rvForm));
        //viewPagerAdapter.addFragment(Covid_Step_seventeen_Activity.newInstance(binding.rvForm));
        viewPagerAdapter.addFragment(Covid18.newInstance(binding.rvForm));
        viewPagerAdapter.addFragment(Covid20.newInstance(binding.rvForm));
        binding.rvForm.setOffscreenPageLimit(11);
        binding.rvForm.setAdapter(viewPagerAdapter);


    }

    @Override
    public void onBackPressed() {
        if (binding.rvForm.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            binding.rvForm.setCurrentItem(binding.rvForm.getCurrentItem() - 1);
        }
    }


}