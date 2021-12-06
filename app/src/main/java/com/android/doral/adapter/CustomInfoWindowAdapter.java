package com.android.doral.adapter;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


import com.android.doral.Utils.StringUtils;
import com.android.doral.databinding.InfoItemBinding;
import com.android.doral.retrofit.model.RequestModel;
import com.android.doral.retrofit.model.UserModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private AppCompatActivity context;

    public CustomInfoWindowAdapter(AppCompatActivity context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        InfoItemBinding binding = InfoItemBinding.inflate(context.getLayoutInflater());
        UserModel requestModel = new Gson().fromJson(marker.getSnippet(), UserModel.class);

        if (requestModel != null) {
            if (StringUtils.isNotEmpty(requestModel.getReferral_type())) {
                binding.tvTitle.setText(requestModel.getReferral_type() + " Request");
            } else {
                binding.tvTitle.setText("Patient");
            }
           /* if (StringUtils.isNotEmpty(requestModel.getKilometer())) {
                binding.tvKm.setText(requestModel.getKilometer());
            }*/
            binding.tvName.setText(requestModel.getFullName());
            binding.tvAddress.setText(requestModel.getEmail());
            //binding.tvDisease.setText(requestModel.getDieses());
            //binding.tvSymptoms.setText(requestModel.getSymptoms());
        }
        return binding.getRoot();
    }
}