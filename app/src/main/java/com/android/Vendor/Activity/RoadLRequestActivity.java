package com.android.Vendor.Activity;

import android.os.Bundle;
import android.view.View;

import com.android.Vendor.fragment.RoadLReqFragment;
import com.android.doral.R;
import com.android.doral.adapter.ViewPagerAdapter;
import com.android.doral.base.BaseActivity;
import com.android.doral.databinding.ActivityAppointmentBinding;
import com.android.doral.fragement.AppointmentFragment;

public class RoadLRequestActivity extends BaseActivity implements View.OnClickListener {
    ViewPagerAdapter viewPagerAdapter;
    ActivityAppointmentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAppointmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setStatusBarColor(R.color.colorPrimary);

        binding.imgMenu.setOnClickListener(this);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(RoadLReqFragment.newInstance("1"), "New Request");
        viewPagerAdapter.addFragment(RoadLReqFragment.newInstance("2"), "On Track");
        binding.viewpager.setOffscreenPageLimit(2);
        binding.viewpager.setAdapter(viewPagerAdapter);
        binding.tab.setupWithViewPager(binding.viewpager);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_menu) {
            onBackPressed();
        }

    }
}