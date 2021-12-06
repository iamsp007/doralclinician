package com.android.doral;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.doral.Utils.MyPref;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityCreateProfileBinding;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.UserModel;

public class CreateProfileActivity extends BaseActivity implements View.OnClickListener, BaseViewInterface {
    ActivityCreateProfileBinding binding;
    BasePresenterInterface presenterInterface;
    UserModel basemodel;

    //7984196756

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.tvTitle.setText("Create Profile");
        binding.toolbar.imgBack.setVisibility(View.VISIBLE);
        binding.toolbar.imgBack.setOnClickListener(this);

        presenterInterface = new Presenter(this);
        presenterInterface.sendRequest(context, null, null, ApiCallInterface.USER_DETAILS);

        binding.llApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (basemodel!=null){

                    if (!basemodel.getData().isApplicant()){

                        startActivity(new Intent(CreateProfileActivity.this, ApplicationActivity.class));

                    }

                }

            }
        });

        binding.llUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (basemodel!=null){

                    if (!basemodel.getData().isDocuments()){

                        startActivity(new Intent(CreateProfileActivity.this, UploadDocumentActivity.class));

                    }

                }

            }
        });
        binding.llEducationDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (basemodel!=null){

                    if (!basemodel.getData().isEducation()){

                        startActivity(new Intent(CreateProfileActivity.this, EducationActivity.class));

                    }

                }

            }
        });
        binding.llBackGround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (basemodel!=null){

                    if (!basemodel.getData().isBackground()){

                        startActivity(new Intent(CreateProfileActivity.this, WorkhistoryActivity.class));

                    }

                }

            }
        });
        binding.llDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (basemodel!=null){

                    if (!basemodel.getData().isDeposit()){

                        startActivity(new Intent(CreateProfileActivity.this, DipositActivity.class));

                    }

                }
            }
        });
        binding.llProfessinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (basemodel!=null){

                    if (!basemodel.getData().isProfessional()){

                        startActivity(new Intent(CreateProfileActivity.this, ProfessionalActivity.class));

                    }

                }

            }
        });
        binding.tvReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(CreateProfileActivity.this, DashboardActivity.class));
            }
        });

     /*   binding.llVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateProfileActivity.this, VerifyIdentityActivity.class));
            }
        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenterInterface.sendRequest(context, null, null, ApiCallInterface.USER_DETAILS);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_back) {

            new MyPref(context).setData(MyPref.Keys.IsLogin, false);
            //new MyPref(context).clearPrefs();
            startActivity(new Intent(context, LoginActivity.class));
            finishAffinity();

          //  onBackPressed();
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

        if (requestCode == ApiCallInterface.USER_DETAILS) {
            basemodel = (UserModel) success;
            if (basemodel.isStatus().equals("true")) {

                if (basemodel.getData() != null) {

                    new MyPref(context).setUserData(basemodel.getData());

                    Log.e("basemodel.isApplicant()",""+basemodel.getData().isApplicant());
                    if (basemodel.getData().isApplicant()){

                        binding.linApplicationDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
                        binding.tvApplicationDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                        binding.tvApplicationDetailsStatus.setTextColor(getResources().getColor(R.color.black));
                        binding.tvApplicationDetailsStatus.setText("Status: Complete");

                    }else {

                        binding.linApplicationDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
                        binding.tvApplicationDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
                        binding.tvApplicationDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
                        binding.tvApplicationDetailsStatus.setText("Status: Pending");

                    }

                    if (basemodel.getData().isEducation()){

                        binding.linEducationDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
                        binding.tvEducationDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                        binding.tvEducationStatus.setTextColor(getResources().getColor(R.color.black));
                        binding.tvEducationStatus.setText("Status: Complete");
                    }else {

                        binding.linEducationDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
                        binding.tvEducationDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
                        binding.tvEducationStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
                        binding.tvEducationStatus.setText("Status: Pending");
                    }

                    if (basemodel.getData().isProfessional()){

                        binding.linProfessionalDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
                        binding.tvProfessionalDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                        binding.tvProfessionalDetailsStatus.setTextColor(getResources().getColor(R.color.black));
                        binding.tvProfessionalDetailsStatus.setText("Status: Complete");
                    }else {

                        binding.linProfessionalDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
                        binding.tvProfessionalDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
                        binding.tvProfessionalDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
                        binding.tvProfessionalDetailsStatus.setText("Status: Pending");
                    }

                    if (basemodel.getData().isBackground()){

                        binding.linBackgroundDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
                        binding.tvBackgroundDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                        binding.tvBackgroundStatus.setTextColor(getResources().getColor(R.color.black));
                        binding.tvBackgroundStatus.setText("Status: Complete");

                    }else {

                        binding.linBackgroundDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
                        binding.tvBackgroundDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
                        binding.tvBackgroundStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
                        binding.tvBackgroundStatus.setText("Status: Pending");
                    }

                    if (basemodel.getData().isDeposit()){

                        binding.linDepositDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
                        binding.tvDepositDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                        binding.tvDepositDetailsStatus.setTextColor(getResources().getColor(R.color.black));
                        binding.tvDepositDetailsStatus.setText("Status: Complete");

                    }else {

                        binding.linDepositDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
                        binding.tvDepositDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
                        binding.tvDepositDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
                        binding.tvDepositDetailsStatus.setText("Status: Pending");
                    }

                    if (basemodel.getData().isDocuments()){

                        binding.linDocumentVerification.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
                        binding.tvDocumentVerification.setTextColor(getResources().getColor(R.color.colorPrimary));
                        binding.tvDocumentStatus.setTextColor(getResources().getColor(R.color.black));
                        binding.tvDocumentStatus.setText("Status: Complete");
                    }else {

                        binding.linDocumentVerification.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
                        binding.tvDocumentVerification.setTextColor(getResources().getColor(R.color.color_74ABB0));
                        binding.tvDocumentStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
                        binding.tvDocumentStatus.setText("Status: Pending");
                    }

                }

            } else {

                errorMessage(binding.getRoot(), basemodel.getMessage());

            }
        }

    }
}