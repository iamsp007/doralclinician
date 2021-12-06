package com.android.doral;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.adapter.FormsAdapter;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityFormsBinding;
import com.android.doral.dialog.SignatureDialog;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.FormModel;
import com.android.doral.retrofit.model.RequestModel;
import com.google.gson.Gson;

import java.util.HashMap;

public class FormsActivity extends BaseActivity implements BaseViewInterface {
    private BasePresenterInterface presenterInterface;
    private ActivityFormsBinding binding;
    private FormsAdapter formsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setStatusBarColor(R.color.colorPrimary);
        presenterInterface = new Presenter(this);
        presenterInterface.sendRequest(FormsActivity.this, "", null, ApiCallInterface.GET_COVID_FORMS);
        formsAdapter = new FormsAdapter(this);
        binding.rvData.setLayoutManager(new LinearLayoutManager(this));
        binding.rvData.setAdapter(formsAdapter);
        formsAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, int which, Object object) {
                FormModel formModel = (FormModel) object;
                new SignatureDialog(context, new SignatureDialog.OnsaveClickListener() {
                    @Override
                    public void onItemClick(String filepath) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("id", formModel.getId());
                        presenterInterface.sendRequest(context, "", map, ApiCallInterface.STORE_FORM_SIGNATURE_1, filepath);
                    }
                }).show();

            }
        });

        binding.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FormsActivity.this, NewDashboardActivity.class));
            }
        });
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
        if (ApiCallInterface.GET_COVID_FORMS == requestCode) {
            FormModel baseModel = (FormModel) success;
            if (baseModel.isStatus().equals("true")) {
                if (baseModel.getData() != null && baseModel.getData().size() > 0) {
                    binding.rvData.setVisibility(View.VISIBLE);
                    binding.tvNoData.setVisibility(View.GONE);
                    formsAdapter.setData(baseModel.getData());
                } else {
                    binding.rvData.setVisibility(View.GONE);
                    binding.tvNoData.setVisibility(View.VISIBLE);
                }

            } else {
                binding.rvData.setVisibility(View.GONE);
                binding.tvNoData.setVisibility(View.VISIBLE);
            }
        }

        if (ApiCallInterface.STORE_FORM_SIGNATURE_1 == requestCode) {

            presenterInterface.sendRequest(FormsActivity.this, "", null, ApiCallInterface.GET_COVID_FORMS);
        }
    }
}