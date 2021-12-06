package com.android.doral;

import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.doral.adapter.ViewPagerAdapter;
import com.android.doral.base.BaseActivity;
import com.android.doral.databinding.ActivityApplicationBinding;
import com.android.doral.fragement.AddressDetailsFragment;
import com.android.doral.fragement.ApplicationFirstFragment;
import com.android.doral.fragement.EmergencyContactFragment;
import com.android.doral.fragement.ReferenceFragment;

import java.util.HashMap;

public class ApplicationActivity extends BaseActivity {
    ActivityApplicationBinding binding;
    ViewPagerAdapter viewPagerAdapter;
    ApplicationFirstFragment firstFragment;
    AddressDetailsFragment detailsFragment;
    ReferenceFragment referenceFragment;
    EmergencyContactFragment contactFragment;
    public HashMap<String, String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityApplicationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.tvTitle.setText("Application");
        firstFragment = ApplicationFirstFragment.newInstance(binding.viewpager);
        detailsFragment = AddressDetailsFragment.newInstance(binding.viewpager);
        referenceFragment = ReferenceFragment.newInstance(binding.viewpager);
        contactFragment = EmergencyContactFragment.newInstance(binding.viewpager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(firstFragment);
        viewPagerAdapter.addFragment(detailsFragment);
        viewPagerAdapter.addFragment(referenceFragment);
        viewPagerAdapter.addFragment(contactFragment);
        binding.viewpager.setOffscreenPageLimit(4);
        binding.viewpager.setAdapter(viewPagerAdapter);


    }
}