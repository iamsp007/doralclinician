package com.android.doral;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.doral.Utils.ImagePickerUtils;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityUploadDocumentBinding;
import com.android.doral.dialog.CustomAlertDialog;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.BaseModel;

import java.io.File;

public class UploadDocumentActivity extends BaseActivity implements View.OnClickListener, BaseViewInterface {
    ActivityUploadDocumentBinding binding;
    BasePresenterInterface presenterInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadDocumentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.tvTitle.setText("Upload Documents");
        binding.toolbar.imgBack.setVisibility(View.VISIBLE);
        setClick();
    }

    private void setClick() {
        presenterInterface = new Presenter(this);
        binding.rlIdProof.setOnClickListener(this);
        binding.rlDegreeProof.setOnClickListener(this);
        binding.rlMedicalProof.setOnClickListener(this);
        binding.rlInsuranceProof.setOnClickListener(this);
        binding.toolbar.imgBack.setOnClickListener(this);
        binding.tvNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_back) {
            onBackPressed();
        }
        if (view.getId() == R.id.rl_id_proof) {
            opneCameraGallery(1001);
        }
        if (view.getId() == R.id.rl_degree_proof) {
            opneCameraGallery(1002);
        }
        if (view.getId() == R.id.rl_medical_proof) {
            opneCameraGallery(1003);
        }
        if (view.getId() == R.id.rl_insurance_proof) {
            opneCameraGallery(1004);
        }
        if (view.getId() == R.id.tv_next) {
            if (validate()) {
                uploadDocument();
            }
        }

    }


    private void opneCameraGallery(int requestCode) {
        new ImagePickerUtils(context, false, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }, requestCode);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {
                    binding.tvIdFileName.setVisibility(View.VISIBLE);
                    binding.tvIdFileName.setText(new File(uri.getPath()).getName());
                    binding.tvIdFileName.setTag(uri.getPath());
                }
            }
        }

        if (requestCode == 1002 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {
                    binding.tvDegreeFileName.setVisibility(View.VISIBLE);
                    binding.tvDegreeFileName.setText(new File(uri.getPath()).getName());
                    binding.tvDegreeFileName.setTag(uri.getPath());
                }
            }


        }
        if (requestCode == 1003 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {
                    binding.tvMedicalFileName.setVisibility(View.VISIBLE);
                    binding.tvMedicalFileName.setText(new File(uri.getPath()).getName());
                    binding.tvMedicalFileName.setTag(uri.getPath());
                }
            }
        }
        if (requestCode == 1004 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {
                    binding.tvInsouranceFileName.setVisibility(View.VISIBLE);
                    binding.tvInsouranceFileName.setText(new File(uri.getPath()).getName());
                    binding.tvInsouranceFileName.setTag(uri.getPath());
                }
            }
        }
    }

    private boolean validate() {
        if (binding.tvIdFileName.getTag() == null) {
            errorMessage(binding.getRoot(), "please select id proof");
            return false;
        }
        if (binding.tvDegreeFileName.getTag() == null) {
            errorMessage(binding.getRoot(), "please select degree proof");
            return false;
        }
        if (binding.tvMedicalFileName.getTag() == null) {
            errorMessage(binding.getRoot(), "please select medical proof");
            return false;
        }
        if (binding.tvInsouranceFileName.getTag() == null) {
            errorMessage(binding.getRoot(), "please select insurance proof");
            return false;
        }

        return true;
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
        if (requestCode == ApiCallInterface.UPLOAD_DOCUMENT_NURSE) {
            BaseModel baseModel = (BaseModel) success;
            if (baseModel.isStatus().equals("true")) {
                new CustomAlertDialog(context, baseModel.getMessage(), "Confirm your email address", "OK", "", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        finish();
                    }
                }).show();


            } else {
                errorMessage(binding.getRoot(), baseModel.getMessage());
            }
        }
    }

    private void uploadDocument() {
        presenterInterface.sendRequest(UploadDocumentActivity.this, "", null, ApiCallInterface.UPLOAD_DOCUMENT, binding.tvIdFileName.getTag().toString(), binding.tvDegreeFileName.getTag().toString(), binding.tvMedicalFileName.getTag().toString(), binding.tvInsouranceFileName.getTag().toString());

    }
}