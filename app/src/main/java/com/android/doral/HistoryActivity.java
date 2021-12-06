package com.android.doral;

import android.os.Bundle;
import android.view.View;

import com.android.doral.adapter.ViewPagerAdapter;
import com.android.doral.base.BaseActivity;
import com.android.doral.databinding.ActivityAppointmentBinding;
import com.android.doral.databinding.ActivityHistoryBinding;
import com.android.doral.fragement.AppointmentFragment;
import com.android.doral.fragement.HistoryFragment;

public class HistoryActivity extends BaseActivity implements View.OnClickListener {
    ViewPagerAdapter viewPagerAdapter;
    ActivityHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setStatusBarColor(R.color.colorPrimary);

        binding.imgMenu.setOnClickListener(this);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(HistoryFragment.newInstance("4"), "Completed");
        viewPagerAdapter.addFragment(HistoryFragment.newInstance("5"), "Cancelled");
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