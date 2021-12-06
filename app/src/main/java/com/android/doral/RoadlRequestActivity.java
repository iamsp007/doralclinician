package com.android.doral;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.doral.Utils.MyPref;
import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.Utils.StringUtils;
import com.android.doral.Utils.Utility;
import com.android.doral.adapter.RoadLRequestAdapter;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityRoadlRequestBinding;
import com.android.doral.dialog.CustomAlertDialog;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.AppontmentModel;
import com.android.doral.retrofit.model.BaseModel;
import com.android.doral.retrofit.model.ParentIdModel;
import com.android.doral.retrofit.model.RoadLRequestModel;
import com.android.doral.retrofit.model.VenderModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class RoadlRequestActivity extends BaseActivity implements View.OnClickListener, BaseViewInterface {
    ActivityRoadlRequestBinding binding;
    RoadLRequestAdapter roadLRequestAdapter;
    List<VenderModel> list = new ArrayList<>();
    BasePresenterInterface presenterInterface;
    String patient_id,parent_id,gg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRoadlRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setStatusBarColor(R.color.colorPrimary);
        binding.imgMenu.setOnClickListener(this);
        binding.btnShowStatus.setOnClickListener(this);
        presenterInterface = new Presenter(this);
        if (getIntent().hasExtra("patient_id")) {
            patient_id = (String) getIntent().getStringExtra("patient_id");
        }
        if (getIntent().hasExtra("parent_id")) {
            parent_id = (String) getIntent().getStringExtra("parent_id");
        }
        binding.rvRequest.setLayoutManager(new LinearLayoutManager(this));
        //list.add(new RoadLRequestModel(R.drawable.ic_clinician_request, "Start Clinician Request", true, true));

        roadLRequestAdapter = new RoadLRequestAdapter(context, list);
        binding.rvRequest.setAdapter(roadLRequestAdapter);
        Utility.CATEGORIES_ID="";
        Utility.SUBCATEGORIES_ID="";
        Utility.CATEGORIES_NAME="";
        Utility.SUBCATEGORIES_NAME="";
        Utility.IS_SUBCATEGORIES_Available=false;

        if (getIntent().hasExtra("gg")) {

            gg = (String) getIntent().getStringExtra("gg");
            getVendorList(true);

        }

        getVendorList(true);

    }


    public void getVendorList(boolean isProgress){
        HashMap<String, String> map = new HashMap<>();
        map.put("patient_id", patient_id);
        map.put("parent_id", parent_id);
        presenterInterface.sendRequest(RoadlRequestActivity.this, "", map, ApiCallInterface.VENDOR_LIST, isProgress);
        roadLRequestAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, int which, Object object) {
                VenderModel venderModel = (VenderModel) object;
                if (patient_id != null) {

                    HashMap<String, String> map = new HashMap<>();
                    map.put("reason", "test");
                    map.put("patient_id", patient_id);
                    map.put("dieses", "");
                    map.put("symptoms", "");
                    map.put("is_parking", "Yes");
                    //  map.put("appointment_id", appontmentModel.getId());
                    //map.put("test_name", venderModel.getTest_name());
                    map.put("type", venderModel.getName());
                    map.put("type_id", venderModel.getRole_id());
                    map.put("test_name", Utility.CATEGORIES_NAME);
                    map.put("sub_test_name", Utility.SUBCATEGORIES_NAME);
                    //Log.e("roadlRequest",map.toString());
                    presenterInterface.sendRequest(RoadlRequestActivity.this, "", map, ApiCallInterface.PATIENT_REQUEST, true);

                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_menu) {
            onBackPressed();
        }
        if (view.getId() == R.id.btn_show_status) {

            HashMap<String, String> map = new HashMap<>();
            map.put("patient_id", patient_id);
            presenterInterface.sendRequest(RoadlRequestActivity.this, "", map, ApiCallInterface.GET_PARENT_ID, true);

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
        if (requestCode == ApiCallInterface.VENDOR_LIST) {
            VenderModel venderModel = (VenderModel) success;
            if (venderModel.isStatus().equals("true")) {
                list.clear();
                list.addAll(venderModel.getData());
                roadLRequestAdapter.notifyDataSetChanged();
            }
        }
        if (requestCode == ApiCallInterface.PATIENT_REQUEST) {
            BaseModel baseModel = (BaseModel) success;

            new CustomAlertDialog(context, baseModel.getMessage(), "Logout", "OK", "", new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                }
            }).show();
        }
        if (requestCode == ApiCallInterface.GET_PARENT_ID) {

            ParentIdModel baseModel = (ParentIdModel) success;
            if (baseModel.isStatus().equalsIgnoreCase("true")) {

                if (StringUtils.isNotEmpty(baseModel.getData().getParent_id())) {

                    Intent intent = new Intent(RoadlRequestActivity.this, RoadLStatusActivity.class);
                    intent.putExtra("parent_id", baseModel.getData().getParent_id());
                    startActivity(intent);

                } else {

                    new CustomAlertDialog(context, "No Request found", "Logout", "OK", "", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                        }
                    }).show();

                }

            } else {

                new CustomAlertDialog(context, "No Request found", "Logout", "OK", "", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                    }
                }).show();
            }


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getVendorList(false);
    }
}