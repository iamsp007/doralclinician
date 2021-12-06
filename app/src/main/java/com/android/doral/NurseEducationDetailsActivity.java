package com.android.doral;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.doral.adapter.CompanyDetailAdapter;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityCompanyDetailsBinding;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.model.CompanyHistoryModel;

import java.util.ArrayList;

public class NurseEducationDetailsActivity extends BaseActivity implements BaseViewInterface {
    ActivityCompanyDetailsBinding binding;
    BasePresenterInterface presenterInterface;
    ArrayList<CompanyHistoryModel> mCompanyHistoryModel=new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCompanyDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setStatusBarColor(R.color.colorPrimary);
        presenterInterface = new Presenter(this);
        setCompanyHistoryAdapter();
    }

    @Override
    public void retry(int pos) {

    }

    @Override
    public void onError(String errorMsg, int requestCode, int resultCode) {

    }

    @Override
    public void onSuccess(Object success, int requestCode, int resultCode) {

    }

    private  void setCompanyHistoryAdapter(){

        mCompanyHistoryModel.add(new CompanyHistoryModel("","","","","","","",""));

        binding.rvEmployer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        binding.rvEmployer.setAdapter(new CompanyDetailAdapter(this,mCompanyHistoryModel));
    }
}
