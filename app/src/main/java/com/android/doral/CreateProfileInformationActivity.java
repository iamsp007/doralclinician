package com.android.doral;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.doral.databinding.ActivityCreateProfileBinding;
import com.android.doral.databinding.ActivityProfileInformationBinding;

public class CreateProfileInformationActivity extends AppCompatActivity {
    ActivityProfileInformationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}
