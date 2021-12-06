package com.android.doral;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.doral.Utils.MyPref;
import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.Utils.StringUtils;
import com.android.doral.adapter.EmergencyDetailAdapter;
import com.android.doral.adapter.NurseReferenceAdapter;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityEmergencyDetailsBinding;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.EmergencyDataModel;
import com.android.doral.retrofit.model.UserApplicationDetails.UserApplicationDetailsModel;
import com.android.doral.retrofit.model.applicationdetails.AddressDetail;
import com.android.doral.retrofit.model.applicationdetails.ApplicationDetailsModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class EmergencyDetailsActivity extends BaseActivity implements BaseViewInterface {


    ActivityEmergencyDetailsBinding binding;
    BasePresenterInterface presenterInterface;
    ArrayList<EmergencyDataModel> mEmergencyDataModel = new ArrayList<>();
    private int selectPos = -1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmergencyDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setStatusBarColor(R.color.colorPrimary);
        presenterInterface = new Presenter(this);
        setCompanyHistoryAdapter();

        setClickListener();
        if (!new MyPref(context).getUserData().getDesignation_id().equals("2")) {
            //getIntentParamsData1();
        }else{
            getIntentParamsData();
        }
        binding.toolbar.tvTitle.setText("Emergency Contact");
        binding.toolbar.imgBack.setVisibility(View.VISIBLE);
      //  binding.toolbar.imgRight.setVisibility(View.VISIBLE);
        binding.toolbar.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        binding.toolbar.imgRight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivityForResult(new Intent(EmergencyDetailsActivity.this, EmergencyRecordActivity.class), 101);
//            }
//        });
        binding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mEmergencyDataModel.isEmpty()) {
                    Toast.makeText(EmergencyDetailsActivity.this, "Please add Emergency Details", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, Object> param = new HashMap<>();
                    HashMap<String, Object> param_detail = new HashMap<>();
                    param_detail.put("key", "emergency_detail");
                    param_detail.put("emergency_detail", mEmergencyDataModel);
                    Log.e("details", mEmergencyDataModel.toString());
                    presenterInterface.callAPI(EmergencyDetailsActivity.this, null, param_detail, ApiCallInterface.STORE_APPLICANT_DETAIL);
                }
            }
        });
    }

   /* private void getIntentParamsData1() {
        if (getIntent().getExtras() != null) {
//            Log.v("I am ","here");
            UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);

            if (model != null) {

                if (model.getData().getEmergencyDetail() != null) {

                    if (model.getData().getEmergencyDetail() != null) {
                        mEmergencyDataModel.addAll(model.getData().getEmergencyDetail());
                        binding.rvReference.getAdapter().notifyDataSetChanged();
                    }
//
                }

            }


        }
    }*/

    private void setClickListener() {

        binding.addReference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(EmergencyDetailsActivity.this, EmergencyRecordActivity.class), 101);
//                mEmergencyDataModel.add(new EmergencyDataModel("","","","",","));
//                binding.rvReference.getAdapter().notifyDataSetChanged();
            }
        });
    }

    @Override
    public void retry(int pos) {

    }

    @Override
    public void onError(String errorMsg, int requestCode, int resultCode) {

    }

    @Override
    public void onSuccess(Object success, int requestCode, int resultCode) {

        finish();

    }

    private void setCompanyHistoryAdapter() {

//        mEmergencyDataModel.add(new EmergencyDataModel("","","","",""));
        EmergencyDetailAdapter nurseReferenceAdapter = new EmergencyDetailAdapter(this, mEmergencyDataModel);
        binding.rvReference.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvReference.setAdapter(nurseReferenceAdapter);

        nurseReferenceAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, int which, Object object) {
                selectPos = position;
                EmergencyDataModel emergencyDataModel = (EmergencyDataModel) object;
                startActivityForResult(new Intent(EmergencyDetailsActivity.this, EmergencyRecordActivity.class)
                        .putExtra("name", emergencyDataModel.getName())
                        .putExtra("zipcode", emergencyDataModel.getZipcode())
                        .putExtra("phone", emergencyDataModel.getPhoneNo())
                        .putExtra("address", emergencyDataModel.getAddress())
                        .putExtra("apt", emergencyDataModel.getBuilding())
                        .putExtra("state", emergencyDataModel.getState_id())
                        .putExtra("city", emergencyDataModel.getCity_id())
                        .putExtra("address2",emergencyDataModel.getAddress_line_2())
                        .putExtra("relation",emergencyDataModel.getRelation()), 503);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 101) {
                EmergencyDataModel model = new Gson().fromJson(data.getStringExtra("data"), EmergencyDataModel.class);
                mEmergencyDataModel.add(model);
                binding.rvReference.getAdapter().notifyDataSetChanged();
            }

            if (requestCode == 503) {
                EmergencyDataModel model = new Gson().fromJson(data.getStringExtra("data"), EmergencyDataModel.class);
                mEmergencyDataModel.set(selectPos, model);
                binding.rvReference.getAdapter().notifyDataSetChanged();
            }
        }
    }


    public void getIntentParamsData() {
//        Log.v("getIntentParamsData","called");
        if (getIntent().getExtras() != null) {
//            Log.v("I am ","here");
            ApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), ApplicationDetailsModel.class);

            if (model != null) {

                if (model.getData().getEmergencyDetail() != null) {

                    if (model.getData().getEmergencyDetail() != null) {
                        mEmergencyDataModel.addAll(model.getData().getEmergencyDetail());
                        binding.rvReference.getAdapter().notifyDataSetChanged();
                    }
//
                }

            }


        }
    }
}
