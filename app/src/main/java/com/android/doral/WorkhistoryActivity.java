package com.android.doral;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.adapter.EmergencyDetailAdapter;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityWorkhistoryBinding;
import com.android.doral.dialog.CustomAlertDialog;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.BaseModel;
import com.android.doral.retrofit.model.CityModel;
import com.android.doral.retrofit.model.CompanyModel;
import com.android.doral.retrofit.model.CountryModel;
import com.android.doral.retrofit.model.DesignationModel;
import com.android.doral.retrofit.model.EmergencyDataModel;
import com.android.doral.retrofit.model.StateLicense;
import com.android.doral.retrofit.model.StateModel;
import com.android.doral.retrofit.model.UserApplicationDetails.UserApplicationDetailsModel;
import com.android.doral.retrofit.model.WorkHistroyModel;
import com.android.doral.retrofit.model.applicationdetails.ApplicationDetailsModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorkhistoryActivity extends BaseActivity implements View.OnClickListener, BaseViewInterface {
    ActivityWorkhistoryBinding binding;
    BasePresenterInterface presenterInterface;
    List<DesignationModel> designationModels;
    List<StateModel> stateModelList;
    List<CityModel> cityList;
    CountryModel countryModels;
    ArrayList<CompanyModel> mEmergencyDataModel = new ArrayList<>();
    private int selectPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWorkhistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.tvTitle.setText("Work History");
        binding.toolbar.imgBack.setVisibility(View.VISIBLE);
        binding.toolbar.imgBack.setOnClickListener(this);
        presenterInterface = new Presenter(this);
        presenterInterface.sendRequest(context, null, null, ApiCallInterface.DESIGNATION);
        presenterInterface.sendRequest(context, null, null, ApiCallInterface.STATES);
        presenterInterface.sendRequest(context, null, null, ApiCallInterface.CITY);
        presenterInterface.sendRequest(context, null, null, ApiCallInterface.GET_COUNTRY);

        setCompanyHistoryAdapter();
        getIntentParamsData();

        binding.tvAddCompany.setOnClickListener(this);
        binding.tvSubmit.setOnClickListener(this);

    }

    private void setCompanyHistoryAdapter() {

//        mEmergencyDataModel.add(new EmergencyDataModel("","","","",""));
        WorkDetailAdapter nurseReferenceAdapter = new WorkDetailAdapter(this, mEmergencyDataModel);
       binding.rvCompany.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvCompany.setAdapter(nurseReferenceAdapter);

        nurseReferenceAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, int which, Object object) {
                selectPos = position;
                CompanyModel emergencyDataModel = (CompanyModel) object;
                startActivityForResult(new Intent(WorkhistoryActivity.this, AddCompanyActivity.class)
                        .putExtra("name", emergencyDataModel.getCompany_name())
                        .putExtra("zipcode", emergencyDataModel.getZipcode())
                        .putExtra("position",emergencyDataModel.getPosition())
                                .putExtra("Address1",emergencyDataModel.getAddress_line_1())
                                .putExtra("Address2",emergencyDataModel.getAddress_line_2())
                                .putExtra("apt",emergencyDataModel.getBuilding())
                                .putExtra("state",emergencyDataModel.getState())
                                .putExtra("city",emergencyDataModel.getCity())
                                .putExtra("Sdate",emergencyDataModel.getStart_date())
                                .putExtra("Edate",emergencyDataModel.getEnd_date())
                                .putExtra("reson",emergencyDataModel.getReason())
                        , 503);
            }
        });
    }
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.tv_add_company) {

            startActivityForResult(new Intent(WorkhistoryActivity.this,AddCompanyActivity.class),101);


        }
        if (v.getId() == R.id.img_back) {

            onBackPressed();

        }
        if (v.getId() == R.id.tvSubmit) {

            if (mEmergencyDataModel.isEmpty()) {

                Toast.makeText(WorkhistoryActivity.this, "Please add Emergency Details", Toast.LENGTH_SHORT).show();

            } else {

                HashMap<String, Object> mainParam = new HashMap<>();
                HashMap<String, Object> param = new HashMap<>();
                HashMap<String, Object> param_detail = new HashMap<>();
                //HashMap<String, Object> list = new HashMap<>();
                //param_detail.put("key", "workHistory_detail");
                param_detail.put("gapReason", "");
                param_detail.put("list", mEmergencyDataModel);
                //param_detail.put("workHistory_detail", list);
                mainParam.put("key","workHistory_detail");
                mainParam.put("workHistory_detail",param_detail);
                Log.e("details", String.valueOf(mainParam));
                presenterInterface.callAPI(WorkhistoryActivity.this, null, mainParam, ApiCallInterface.STORE_APPLICANT_DETAIL);

            }

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
        if (requestCode == ApiCallInterface.DESIGNATION) {
            DesignationModel baseModel = (DesignationModel) success;
            if (baseModel.isStatus().equals("true")) {
                designationModels = baseModel.getData().getDesignation();
            } else {
                errorMessage(binding.getRoot(), baseModel.getMessage());
            }
        }
        if (requestCode == ApiCallInterface.STATES) {
            stateModelList = (List<StateModel>) success;

        }
        if (requestCode == ApiCallInterface.CITY) {
            cityList = (List<CityModel>) success;

        }
        if (requestCode == ApiCallInterface.GET_COUNTRY) {

            if (success != null) {

                countryModels = (CountryModel) success;

            }

        }
        if (requestCode == ApiCallInterface.STORE_APPLICANT_DETAIL) {
//            BaseModel baseModel = (BaseModel) success;
//            designationModels = null;
//            stateModelList = null;
//            if (baseModel.isStatus().equals("true")) {
//                new CustomAlertDialog(context, baseModel.getMessage(), "Work History Saved!", "OK", "", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        finish();
//                    }
//                }).show();
//
//
//            } else {
//                errorMessage(binding.getRoot(), baseModel.getMessage());
//            }
            finish();
        }

     //   AddCompany();

    }

//    private void AddCompany() {
//        if (designationModels != null && stateModelList != null && cityList != null && countryModels != null) {
//            CompanyModel model = new Gson().fromJson(data.getStringExtra("data"), CompanyModel.class);
//            mEmergencyDataModel.add(model);
//            binding.rvCompany.getAdapter().notifyDataSetChanged();
//        }
//    }
@Override
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
        if (requestCode == 101) {
            CompanyModel model = new Gson().fromJson(data.getStringExtra("data"), CompanyModel.class);
            mEmergencyDataModel.add(model);
            binding.rvCompany.getAdapter().notifyDataSetChanged();
        }
        if (requestCode == 503) {
            CompanyModel model = new Gson().fromJson(data.getStringExtra("data"), CompanyModel.class);
            mEmergencyDataModel.set(selectPos,model);
            binding.rvCompany.getAdapter().notifyDataSetChanged();
        }

    }
}
    public void getIntentParamsData() {
//        Log.v("getIntentParamsData","called");
        if (getIntent().getExtras() != null) {
//            Log.v("I am ","here");
            UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);
            if (model != null) {

                if (model.getData().getWorkHistoryDetail() != null) {

                    for (int i = 0; i <model.getData().getWorkHistoryDetail().getList().size() ; i++) {

                        CompanyModel emergencyDataModel=new CompanyModel();
                        emergencyDataModel.setCompany_name(model.getData().getWorkHistoryDetail().getList().get(i).getCompanyName());
                        emergencyDataModel.setPosition(model.getData().getWorkHistoryDetail().getList().get(i).getPosition());
                        emergencyDataModel.setStart_date(model.getData().getWorkHistoryDetail().getList().get(i).getStartDate());
                        emergencyDataModel.setEnd_date(model.getData().getWorkHistoryDetail().getList().get(i).getEndDate());
                        emergencyDataModel.setReason(model.getData().getWorkHistoryDetail().getList().get(i).getReason());
                        emergencyDataModel.setAddress_line_1(model.getData().getWorkHistoryDetail().getList().get(i).getAddressLine1());
                        emergencyDataModel.setAddress_line_2(model.getData().getWorkHistoryDetail().getList().get(i).getAddressLine2());
                        emergencyDataModel.setBuilding(model.getData().getWorkHistoryDetail().getList().get(i).getBuilding());
                        emergencyDataModel.setState(model.getData().getWorkHistoryDetail().getList().get(i).getState());
                        emergencyDataModel.setCity(model.getData().getWorkHistoryDetail().getList().get(i).getCity());
                        emergencyDataModel.setZipcode(model.getData().getWorkHistoryDetail().getList().get(i).getZipcode());
                        mEmergencyDataModel.add(emergencyDataModel);

                    }
//
                }

            }


        }
    }

}