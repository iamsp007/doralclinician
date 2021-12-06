package com.android.Vendor.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.Vendor.Adapter.UpcomingVisitAdapter;
import com.android.doral.R;
import com.android.doral.adapter.RoadLRequestAdapter;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityRoadlRequestBinding;
import com.android.doral.databinding.ActivityUpcomingVisitBinding;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.AppontmentModel;
import com.android.doral.retrofit.model.RoadLRequestModel;

import java.util.ArrayList;
import java.util.List;

public class UpcomingVisitActivity extends BaseActivity implements View.OnClickListener, BaseViewInterface {
    ActivityUpcomingVisitBinding binding;
    List<RoadLRequestModel> list = new ArrayList<>();
    private BasePresenterInterface presenterInterface;
    private UpcomingVisitAdapter upcomingVisitAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpcomingVisitBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setStatusBarColor(R.color.colorPrimary);
        binding.imgMenu.setOnClickListener(this);

        presenterInterface = new Presenter(this);

        binding.rvUpcomingVisit.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        upcomingVisitAdapter = new UpcomingVisitAdapter(this);
        binding.rvUpcomingVisit.setAdapter(upcomingVisitAdapter);

        presenterInterface.sendRequest(context, null, null, ApiCallInterface.APPOITNMENT);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_menu) {
            onBackPressed();
        }
    }

    @Override
    public void retry(int pos) {

    }

    @Override
    public void onError(String errorMsg, int requestCode, int resultCode) {
        errorMessage(binding.getRoot(), errorMsg);
    }

    @Override
    public void onSuccess(Object success, int requestCode, int resultCode) {
        if (requestCode == ApiCallInterface.APPOITNMENT) {
            AppontmentModel appontmentModel = (AppontmentModel) success;
            if (appontmentModel.getData() != null) {

                upcomingVisitAdapter.setData(appontmentModel.getData());

            }
        }
    }
}