package com.android.doral;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import com.android.doral.Utils.MyPref;
import com.android.doral.Utils.StringUtils;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivitySecurityDetailsBinding;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.EmergencyDataModel;
import com.android.doral.retrofit.model.UserApplicationDetails.UserApplicationDetailsModel;
import com.android.doral.retrofit.model.applicationdetails.ApplicationDetailsModel;
import com.android.doral.retrofit.model.applicationdetails.SecurityDetail;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class SecurityDetailsActivity extends BaseActivity implements BaseViewInterface {


    ActivitySecurityDetailsBinding binding;
    BasePresenterInterface presenterInterface;
    Boolean isBond=true;
    Boolean isConvictFalcony=true;

    public void getIntentParamsData() {
//        Log.v("getIntentParamsData","called");
        if (getIntent().getExtras() != null) {
//            Log.v("I am ","here");
            ApplicationDetailsModel model=new Gson().fromJson(getIntent().getStringExtra("model"),ApplicationDetailsModel.class);

           if(model!=null){

               if(model.getData().getSecurityDetail()!=null){
                   SecurityDetail mSecurityDetail=model.getData().getSecurityDetail();
//                   Log.v("mSecurityDetail:::",""+mSecurityDetail.getBond_explain());
                   if(mSecurityDetail.getBond()) {
                       binding.rbBondedYes.setChecked(true);
                       binding.rbBondedNo.setChecked(false);
                       binding.linBondInput.setVisibility(View.VISIBLE);
                       binding.edtBond.setText(mSecurityDetail.getBond_explain());
                   }
                   else {
                       binding.rbBondedYes.setChecked(false);
                       binding.rbBondedNo.setChecked(true);
                       binding.linBondInput.setVisibility(View.GONE);
                   }

                   if(mSecurityDetail.getConvict()) {
                       binding.rbConvictYes.setChecked(true);
                       binding.rbConvictNo.setChecked(false);
                       binding.linFalconyInput.setVisibility(View.VISIBLE);
                       binding.edtFelony.setText(mSecurityDetail.getConvictExplain());
                   }
                   else {
                       binding.rbConvictYes.setChecked(false);
                       binding.rbConvictNo.setChecked(true);
                       binding.linFalconyInput.setVisibility(View.GONE);
                   }
               }

           }

        }
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySecurityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setStatusBarColor(R.color.colorPrimary);
        presenterInterface = new Presenter(this);

        binding.toolbar.tvTitle.setText("Security Details");
        binding.toolbar.imgBack.setVisibility(View.VISIBLE);
        if (!new MyPref(context).getUserData().getDesignation_id().equals("2")) {
            //getIntentParamsData1();
        }else{
            getIntentParamsData();
        }
        binding.toolbar.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.rbBondedYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    isBond = true;
                    binding.linBondInput.setVisibility(View.VISIBLE);
                }

            }
        });
        binding.rbBondedNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    isBond = false;
                    binding.linBondInput.setVisibility(View.GONE);
                }
            }
        });
        binding.rbConvictNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    isConvictFalcony = false;
                    binding.linFalconyInput.setVisibility(View.GONE);
                }
            }
        });
        binding.rbConvictYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    isConvictFalcony = true;
                    binding.linFalconyInput.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.rbBondedYes.isChecked() && !StringUtils.isNotEmpty(binding.edtBond.getText().toString())){
                    errorMessage(binding.getRoot(), "Please Explain Detail");

                }
                else if (binding.rbConvictYes.isChecked() && !StringUtils.isNotEmpty(binding.edtFelony.getText().toString())){
                    errorMessage(binding.getRoot(), "Please Explain Felony Detail");
                }
                else{
                    HashMap<String,Object> param=new HashMap<>();
                    HashMap<String,Object> param_detail=new HashMap<>();
                    param_detail.put("key","security_detail");
                    param.put("bond",isBond);
                    param.put("convict",isConvictFalcony);
                    if (binding.rbBondedYes.isChecked()){
                        param.put("bond_explain",  binding.edtBond.getText().toString());
                    }else{
                        param.put("bond_explain",binding.edtBond.getText().toString());
                    }

                    if (binding.rbConvictYes.isChecked()){
                        param.put("convict_explain",binding.edtFelony.getText().toString());
                    }else{
                        param.put("convict_explain",binding.edtFelony.getText().toString());
                    }


                    param_detail.put("security_detail",param);
                    Log.e("gfvfyfgy", String.valueOf(param));
                    presenterInterface.callAPI(SecurityDetailsActivity.this,null,param_detail, ApiCallInterface.STORE_APPLICANT_DETAIL);
                }





            }


        });
    }

    /*private void getIntentParamsData1() {
        if (getIntent().getExtras() != null) {
//            Log.v("I am ","here");
            UserApplicationDetailsModel model=new Gson().fromJson(getIntent().getStringExtra("model"),UserApplicationDetailsModel.class);

            if(model!=null){

                if(model.getData().getSecurityDetail()!=null){
                    SecurityDetail mSecurityDetail=model.getData().getSecurityDetail();
//                   Log.v("mSecurityDetail:::",""+mSecurityDetail.getBond_explain());
                    if(mSecurityDetail.getBond()) {
                        binding.rbBondedYes.setChecked(true);
                        binding.rbBondedNo.setChecked(false);
                        binding.linBondInput.setVisibility(View.VISIBLE);
                        binding.edtBond.setText(mSecurityDetail.getBond_explain());
                    }
                    else {
                        binding.rbBondedYes.setChecked(false);
                        binding.rbBondedNo.setChecked(true);
                        binding.linBondInput.setVisibility(View.GONE);
                    }

                    if(mSecurityDetail.getConvict()) {
                        binding.rbConvictYes.setChecked(true);
                        binding.rbConvictNo.setChecked(false);
                        binding.linFalconyInput.setVisibility(View.VISIBLE);
                        binding.edtFelony.setText(mSecurityDetail.getConvictExplain());
                    }
                    else {
                        binding.rbConvictYes.setChecked(false);
                        binding.rbConvictNo.setChecked(true);
                        binding.linFalconyInput.setVisibility(View.GONE);
                    }
                }

            }

        }
    }*/


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



}
