package com.android.doral;

import android.os.Bundle;
import android.view.View;

import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityFamilyDetailsBinding;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;

import java.util.HashMap;

public class FamilyDetailsActivity extends BaseActivity implements BaseViewInterface {


    ActivityFamilyDetailsBinding binding;
    BasePresenterInterface presenterInterface;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFamilyDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setStatusBarColor(R.color.colorPrimary);
        presenterInterface = new Presenter(this);

        binding.toolbar.tvTitle.setText("Family Details");
        binding.toolbar.imgBack.setVisibility(View.VISIBLE);
        binding.toolbar.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validation();
            }
        });
    }
    private  void validation(){
        if (binding.edtSpouseName.getText().toString().trim().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "Please enter spouse name");
        }
        else if (binding.edtMotherName.getText().toString().trim().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "Please enter mother name");
        }
        else if (binding.edtFatherName.getText().toString().trim().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "Please enter father name");
        }
        else if (binding.edtMotherLawName.getText().toString().trim().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "Please enter mother-in-law name");
        }
        else if (binding.edtFatherLawName.getText().toString().trim().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "Please enter father-in-low name");
        }
        else if (binding.edtSiblings.getText().toString().trim().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "Please enter siblings name");
        }
        else if (binding.edtGrandparents.getText().toString().trim().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "Please enter grand parents name");
        }
        else if (binding.edtChildren.getText().toString().trim().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "Please enter children ");
        }
        else{
            HashMap<String,String> param=new HashMap<>();
            HashMap<String,Object> param_detail=new HashMap<>();
            param_detail.put("key","family_detail");
            param.put("spouse_name",binding.edtSpouseName.getText().toString());
            param.put("mother_name",binding.edtMotherName.getText().toString());
            param.put("father_name",binding.edtFatherName.getText().toString());
            param.put("mother_in_low_name",binding.edtMotherLawName.getText().toString());
            param.put("father_in_low_name",binding.edtFatherLawName.getText().toString());
            param.put("siblings_name",binding.edtSiblings.getText().toString());
            param.put("parents_name",binding.edtGrandparents.getText().toString());
            param.put("children_name",binding.edtChildren.getText().toString());
            param_detail.put("family_detail",param);
            presenterInterface.callAPI(this,null,param_detail, ApiCallInterface.STORE_APPLICANT_DETAIL);
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

        if(requestCode== ApiCallInterface.STORE_APPLICANT_DETAIL){
            finish();
        }

    }



}
