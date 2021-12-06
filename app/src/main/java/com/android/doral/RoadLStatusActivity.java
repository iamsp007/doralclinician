package com.android.doral;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import com.android.doral.Utils.MyPref;
import com.android.doral.adapter.RoadLStatusAdapter;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityRoadLStatusBinding;
import com.android.doral.databinding.ActivitySendRequestBinding;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.AppontmentModel;
import com.android.doral.retrofit.model.RequestModel;
import com.android.doral.retrofit.model.RoadRequestModel;
import com.android.doral.retrofit.model.UserModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoadLStatusActivity extends BaseActivity implements BaseViewInterface, View.OnClickListener {
    ActivityRoadLStatusBinding binding;
    BasePresenterInterface presenterInterface;
    RoadLStatusAdapter roadLStatusAdapter;
    List<UserModel> userModelList = new ArrayList<>();
    String parent_id;
    MyPref myPref;
    RoadRequestModel roadRequestModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRoadLStatusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setStatusBarColor(R.color.colorPrimary);
        myPref = new MyPref(context);
        presenterInterface = new Presenter(this);
        binding.rvData.setLayoutManager(new LinearLayoutManager(this));
        roadLStatusAdapter = new RoadLStatusAdapter(context, userModelList);
        binding.rvData.setAdapter(roadLStatusAdapter);
        setClick();
        if (getIntent().hasExtra("parent_id")) {

            parent_id = (String) getIntent().getStringExtra("parent_id");
            HashMap<String, String> map = new HashMap<>();
            map.put("parent_id", parent_id);
            if (!new MyPref(context).getUserData().getRoles().get(new MyPref(context).getUserData().getRoles().size() - 1).getName().equalsIgnoreCase("clinician")) {
                binding.imgAdd.setVisibility(View.GONE);

                if (getIntent().hasExtra("type_id")) {
                    map.put("type_id", getIntent().getStringExtra("type_id"));
                }
            }
            presenterInterface.sendRequest(RoadLStatusActivity.this, "", map, ApiCallInterface.ROADL_PROCESS_REQUEST_NEW, true);
        }
    }

    private void setClick() {

        binding.imgMenu.setOnClickListener(this::onClick);
        binding.btnShowMap.setOnClickListener(this::onClick);
        binding.imgAdd.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_menu) {
            onBackPressed();
        }
        if (view.getId() == R.id.img_add) {
             startActivity(new Intent(RoadLStatusActivity.this, RoadlRequestActivity.class).putExtra("patient_id", roadRequestModel.getData().getPatient().getId()).putExtra("parent_id",parent_id));
        }
        if (view.getId() == R.id.btn_show_map) {

           /* if (!myPref.getUserData().getRoles().get(myPref.getUserData().getRoles().size() - 1).getName().equalsIgnoreCase("clinician")) {
                Intent intent = new Intent(RoadLStatusActivity.this, MainActivity.class);
                intent.putExtra("data", requestModel);
                startActivity(intent);
            } else {
                Intent intent = new Intent(RoadLStatusActivity.this, ClinicianTrackActivity.class);
                intent.putExtra("data", requestModel);
                startActivity(intent);
            }*/

            Intent intent = new Intent(RoadLStatusActivity.this, ClinicianTrackActivity.class);
            intent.putExtra("parent_id", parent_id).putExtra("type_id", getIntent().getStringExtra("type_id"));
            startActivity(intent);
        }
    }

    @Override
    public void retry(int pos) {

    }

    @Override
    public void onError(String errorMsg, int requestCode, int resultCode) {

    }

    @Override
    public void onSuccess(Object success, int requestCode, int resultCode) {
        if (requestCode == ApiCallInterface.ROADL_PROCESS_REQUEST_NEW) {
            roadRequestModel = (RoadRequestModel) success;
            if (roadRequestModel.isStatus().equals("true")) {
                userModelList.addAll(roadRequestModel.getData().getClinicians());
                roadLStatusAdapter.notifyDataSetChanged();
            }
        }

    }
}