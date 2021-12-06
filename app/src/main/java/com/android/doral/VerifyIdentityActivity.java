package com.android.doral;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.doral.base.BaseActivity;
import com.android.doral.databinding.ActivityVerifyIdentityBinding;

public class VerifyIdentityActivity extends BaseActivity {
    ActivityVerifyIdentityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyIdentityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VerifyIdentityActivity.this, CreateProfileActivity.class));
            }
        });

    }
}