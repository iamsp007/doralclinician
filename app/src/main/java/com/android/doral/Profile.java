package com.android.doral;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.CovidFormActivity;
import com.android.Vendor.Activity.VendorDashboardActivity;
import com.android.doral.Utils.CustomProgressBar;
import com.android.doral.Utils.MyPref;
import com.android.doral.Utils.StringUtils;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityProfileBinding;
import com.android.doral.dialog.CancelRequestDialog;
import com.android.doral.dialog.CustomAlertDialog;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.BaseModel;
import com.android.doral.retrofit.model.RequestModel;
import com.android.doral.retrofit.model.RoadRequestModel;
import com.android.doral.retrofit.model.UserModel;

import java.util.HashMap;

public class Profile extends BaseActivity implements View.OnClickListener, BaseViewInterface {

    ActivityProfileBinding binding;
    String parent_id;
    BasePresenterInterface presenterInterface;
    RoadRequestModel roadRequestModel;
    CancelRequestDialog1 cancelRequestDialog;
   // RequestModel requestModel;
    String notes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (new MyPref(context).getUserData().getRoles().get(new MyPref(context).getUserData().getRoles().size() - 1).getName().equalsIgnoreCase("clinician")) {

            binding.tvRoadRequest.setVisibility(View.VISIBLE);
            binding.btnCovid.setVisibility(View.VISIBLE);

        } else {
            binding.tvRoadRequest.setVisibility(View.GONE);
            binding.btnCovid.setVisibility(View.GONE);
            binding.notes.setVisibility(View.GONE);
            binding.emrButton.setVisibility(View.GONE);
        }

        presenterInterface = new Presenter(this);
        if (getIntent().hasExtra("parent_id")) {
            parent_id = (String) getIntent().getStringExtra("parent_id");
            HashMap<String, String> map = new HashMap<>();
            map.put("parent_id", parent_id);
            presenterInterface.sendRequest(Profile.this, "", map, ApiCallInterface.ROADL_PROCESS_REQUEST_NEW, false);

        }
        if (getIntent().hasExtra("notes")) {
            notes = (String) getIntent().getStringExtra("notes");


        }else {
            notes = "";
        }

        setClick();




    }

    private void setClick() {
        binding.imgMenu.setOnClickListener(this::onClick);
        //   binding.btnCovid.setOnClickListener(this::onClick);
        binding.tvRoadRequest.setOnClickListener(this::onClick);
        binding.completeButton.setOnClickListener(this::onClick);
        binding.notes.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == binding.imgMenu.getId()) {
            onBackPressed();
        }
        if (view.getId() == binding.tvRoadRequest.getId()) {
            startActivity(new Intent(Profile.this, RoadlRequestActivity.class)
                    .putExtra("patient_id", roadRequestModel.getData().getPatient().getId())
            .putExtra("parent_id", parent_id));
        }
        if (view.getId() == binding.btnCovid.getId()) {
            startActivity(new Intent(Profile.this, CovidFormActivity.class));
        }
        if (view.getId() == binding.btnCovid.getId()) {
            UserModel sendEmitUser = getLoginUser(new MyPref(Profile.this).getUserData().getRoles().get(new MyPref(Profile.this).getUserData().getRoles().size() - 1).getName());
            HashMap<String, String> map = new HashMap<>();
            map.put("patient_requests_id", sendEmitUser.getId());
            map.put("client_id", roadRequestModel.getData().getPatient().getId());
            map.put("latitude", "" + new MyPref(Profile.this).getData(MyPref.Keys.LAT));
            map.put("longitude", "" + new MyPref(Profile.this).getData(MyPref.Keys.LAG));
            map.put("status", "4");
            map.put("notes", notes);
            presenterInterface.sendRequest(Profile.this, "", map, ApiCallInterface.UPDATE_LOCATION, false);

        }


        if (view.getId() == binding.completeButton.getId()) {
            UserModel sendEmitUser = getLoginUser(new MyPref(Profile.this).getUserData().getRoles().get(new MyPref(Profile.this).getUserData().getRoles().size() - 1).getName());
            HashMap<String, String> map = new HashMap<>();
            map.put("patient_request_id", sendEmitUser.getId());
            map.put("parent_id", parent_id);
            map.put("latitude", "" + new MyPref(Profile.this).getData(MyPref.Keys.LAT));
            map.put("longitude", "" + new MyPref(Profile.this).getData(MyPref.Keys.LAG));
            map.put("status", "4");
            map.put("notes",notes );
            final CustomProgressBar pd = new CustomProgressBar(Profile.this);
            pd.show();
            presenterInterface.sendRequest(Profile.this, "", map, ApiCallInterface.UPDATE_LOCATION, false);


        }

        if (view.getId()==binding.notes.getId()){
            if (cancelRequestDialog != null) {
                cancelRequestDialog.dismissAllowingStateLoss();
            }
            cancelRequestDialog = new CancelRequestDialog1(context,parent_id,notes);
            cancelRequestDialog.show(getSupportFragmentManager(), "");
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
                binding.tvName.setText(roadRequestModel.getData().getPatient().getFullName());

            }
        }
        if (requestCode == ApiCallInterface.UPDATE_LOCATION) {
            final CustomProgressBar pd = new CustomProgressBar(Profile.this);
            BaseModel baseModel = (BaseModel) success;
            if (baseModel.isStatus().equals("true")) {
             //   getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                new CustomAlertDialog(context, baseModel.getMessage(), "Alert", "OK", "", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (new MyPref(context).getUserData().getRoles().get(new MyPref(context).getUserData().getRoles().size() - 1).getName().equalsIgnoreCase("clinician")) {

                            startActivity(new Intent(Profile.this, NewDashboardActivity.class));
                        } else {
                            startActivity(new Intent(Profile.this, VendorDashboardActivity.class));
                        }
                        finishAffinity();

                    }
                }).show();

                pd.dismiss();
            }

        }

        if (requestCode == ApiCallInterface.Complete_Request) {
            BaseModel baseModel = (BaseModel) success;
            if (baseModel.isStatus().equals("true")) {
                if (new MyPref(context).getUserData().getRoles().get(new MyPref(context).getUserData().getRoles().size() - 1).getName().equalsIgnoreCase("clinician")) {

                    startActivity(new Intent(Profile.this, NewDashboardActivity.class));
                } else {
                    startActivity(new Intent(Profile.this, VendorDashboardActivity.class));
                }
                //  startActivity(new Intent(Profile.this, NewDashboardActivity.class));
                finish();
            }

        }
    }

    private UserModel getLoginUser(String userid) {
        for (int i = 0; i < roadRequestModel.getData().getClinicians().size(); i++) {
            if (StringUtils.isNotEmpty(roadRequestModel.getData().getClinicians().get(i).getReferral_type()) && roadRequestModel.getData().getClinicians().get(i).getReferral_type().equalsIgnoreCase(userid)) {
                return roadRequestModel.getData().getClinicians().get(i);
            }
        }
        return null;
    }
}