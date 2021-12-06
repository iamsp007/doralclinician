package com.android.doral;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.doral.databinding.ActivitySignatureBinding;

public class SignatureActivity extends AppCompatActivity {
    ActivitySignatureBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignatureBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.tvTitle.setText("Signature");
    }
}