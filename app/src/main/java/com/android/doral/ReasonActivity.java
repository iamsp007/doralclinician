package com.android.doral;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.Utils.StringUtils;
import com.android.doral.adapter.ReasonAdapter;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityReasonsBinding;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.ReasonModel;

public class ReasonActivity extends BaseActivity implements BaseViewInterface, View.OnClickListener {
    ActivityReasonsBinding binding;
    BasePresenterInterface presenterInterface;
    ReasonAdapter adapter;
    private String reason;
    private String reason_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReasonsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setData();

    }

    private void setData() {
        presenterInterface = new Presenter(this);
        binding.back.setOnClickListener(this);
        binding.activityReasonsBtnSubmit.setOnClickListener(this);
        adapter = new ReasonAdapter(context);
        binding.activityReasonsRvReasons.setLayoutManager(new LinearLayoutManager(context));
        binding.activityReasonsRvReasons.setAdapter(adapter);
        adapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, int which, Object object) {
                ReasonModel reasonModel = (ReasonModel) object;
                reason = reasonModel.getReason();
                reason_id = reasonModel.getId();
                if (reasonModel.getReason().equalsIgnoreCase("other")) {
                    binding.activityReasonsEtReason.setVisibility(View.VISIBLE);
                } else {
                    binding.activityReasonsEtReason.setVisibility(View.GONE);
                }

            }
        });
        presenterInterface.sendRequest(context, "", null, ApiCallInterface.CANCEL_REASON);
    }

    @Override
    public void retry(int pos) {

    }

    @Override
    public void onError(String errorMsg, int requestCode, int resultCode) {


    }

    @Override
    public void onSuccess(Object success, int requestCode, int resultCode) {
        if (requestCode == ApiCallInterface.CANCEL_REASON) {
            ReasonModel reasonModel = (ReasonModel) success;

            if (reasonModel.isStatus().equals("true")) {

                adapter.setData(reasonModel.getData().getReasons());
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back) {
            onBackPressed();
        }
        if (view.getId() == R.id.activity_reasons_btn_submit) {
            Intent intent = new Intent();
            if (!StringUtils.isNotEmpty(reason)) {
                errorMessage(binding.getRoot(), "please select reason");
                return;
            }
            if (reason.equalsIgnoreCase("other")) {

                if (!StringUtils.isNotEmpty(binding.activityReasonsEtReason.getText().toString())) {
                    errorMessage(binding.getRoot(), "please enter reason");
                    return;
                }
                intent.putExtra("reason", binding.activityReasonsEtReason.getText().toString());
            } else {
                intent.putExtra("reason", reason);
            }
            intent.putExtra("reason_id", reason_id);
            setResult(RESULT_OK, intent);
            finish();
        }

    }
}