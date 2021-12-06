package com.android.doral;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.doral.Utils.ImageUtils;
import com.android.doral.Utils.MyPref;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityNurseProfileBinding;
import com.android.doral.dialog.SignatureDialog;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.UserApplicationDetails.UserApplicationDetailsModel;
import com.android.doral.retrofit.model.UserModel;
import com.android.doral.retrofit.model.applicationdetails.ApplicationDetailsModel;
import com.android.doral.retrofit.model.applicationdetails.SecurityDetail;
import com.google.gson.Gson;

public class NurseProfileActivity extends BaseActivity implements View.OnClickListener, BaseViewInterface {
    ActivityNurseProfileBinding binding;
    BasePresenterInterface presenterInterface;
    ApplicationDetailsModel basemodel;
    UserApplicationDetailsModel basemodelUser;
    Boolean isCompleted1 = false;
    Boolean isCompleted2 = false;
    Boolean isCompleted3 = false;
    Boolean isCompleted4 = false;
    Boolean isCompleted5 = false;
    Boolean isCompleted6 = false;
    Boolean isCompleted7 = false;
    Boolean isCompleted8 = false;
    Boolean isCompleted9 = false;
    Boolean isCompleted10 = false;
    Boolean isCompleted11 = false;
    String signatuePath="";
    private boolean isProfileUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNurseProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.tvTitle.setText("Create Profile");
        binding.toolbar.imgBack.setVisibility(View.VISIBLE);
        binding.toolbar.imgBack.setOnClickListener(this);

        presenterInterface = new Presenter(this);
        //  presenterInterface.sendRequest(context, null, null, ApiCallInterface.GET_APPLICATION_DETAIL);
//        presenterInterface.sendRequest(context, null, null, ApiCallInterface.USER_DETAILS);

        if (isProfileUpdate){

            binding.linProfileDetails.setVisibility(View.VISIBLE);
            binding.linSignature.setVisibility(View.GONE);
            binding.tvApproval.setVisibility(View.GONE);

        }

        if (!new MyPref(context).getUserData().getDesignation_id().equals("2")) {
            binding.linSecurityDetails.setVisibility(View.GONE);
            binding.linMilitaryDetails.setVisibility(View.GONE);
            binding.linEmergencyDetails.setVisibility(View.GONE);
            binding.linProfessionalContact.setVisibility(View.VISIBLE);
            binding.linCompanyDetails.setVisibility(View.GONE);
            binding.linBackgroundDetails.setVisibility(View.VISIBLE);
        }

        binding.llApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!new MyPref(context).getUserData().getDesignation_id().equals("2")) {

                    startActivity(new Intent(NurseProfileActivity.this, NurseApplicationDetailActivity.class).putExtra("model", new Gson().toJson(basemodelUser)));

                }else {

                    startActivity(new Intent(NurseProfileActivity.this, NurseApplicationDetailActivity.class).putExtra("model", new Gson().toJson(basemodel)));

                }


            }
        });

        binding.linProfessionalContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!new MyPref(context).getUserData().getDesignation_id().equals("2")) {

                    startActivity(new Intent(NurseProfileActivity.this, ProfessionalActivity.class).putExtra("model", new Gson().toJson(basemodelUser)));

                }
            }
        });

        binding.linBackgroundDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!new MyPref(context).getUserData().getDesignation_id().equals("2")) {

                    startActivity(new Intent(NurseProfileActivity.this, WorkhistoryActivity.class).putExtra("model", new Gson().toJson(basemodelUser)));

                }
            }
        });

        binding.llUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!new MyPref(context).getUserData().getDesignation_id().equals("2")) {

                    startActivity(new Intent(NurseProfileActivity.this, NurseUploadDocumentActivity.class).putExtra("model", new Gson().toJson(basemodelUser)));

                }else {

                    startActivity(new Intent(NurseProfileActivity.this, NurseUploadDocumentActivity.class).putExtra("model", new Gson().toJson(basemodel)));

                }
             //   startActivity(new Intent(NurseProfileActivity.this, NurseUploadDocumentActivity.class).putExtra("model", new Gson().toJson(basemodel)));
            }
        });

        if (!new MyPref(context).getUserData().getDesignation_id().equals("2")) {

            binding.llEducationDetails.setVisibility(View.VISIBLE);
            binding.llEducationDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(NurseProfileActivity.this, EducationActivity.class).putExtra("model", new Gson().toJson(basemodelUser)));
                }
            });

        }else {

            binding.llEducationDetails.setVisibility(View.GONE);

        }


        binding.tvReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(CreateProfileActivity.this, DashboardActivity.class));
            }
        });

        binding.linCompanyDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startActivity(new Intent(NurseProfileActivity.this, CompanyDetailsActivity.class)
                            .putExtra("model", new Gson().toJson(basemodel)));
            }
        });
        binding.linEmergencyDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(NurseProfileActivity.this, EmergencyDetailsActivity.class).putExtra("model", new Gson().toJson(basemodel)));
                if (!new MyPref(context).getUserData().getDesignation_id().equals("2")) {

                    startActivity(new Intent(NurseProfileActivity.this, EmergencyDetailsActivity.class).putExtra("model", new Gson().toJson(basemodelUser)));

                }else {

                    startActivity(new Intent(NurseProfileActivity.this, EmergencyDetailsActivity.class).putExtra("model", new Gson().toJson(basemodel)));

                }
            }
        });

        binding.linFamilyDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startActivity(new Intent(NurseProfileActivity.this, FamilyDetailsActivity.class));
            }
        });
        binding.linSecurityDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                startActivity(new Intent(NurseProfileActivity.this, SecurityDetailsActivity.class).
//                        putExtra("model", new Gson().toJson(basemodel)));
                if (!new MyPref(context).getUserData().getDesignation_id().equals("2")) {

                    startActivity(new Intent(NurseProfileActivity.this, SecurityDetailsActivity.class).putExtra("model", new Gson().toJson(basemodelUser)));

                }else {

                    startActivity(new Intent(NurseProfileActivity.this, SecurityDetailsActivity.class).putExtra("model", new Gson().toJson(basemodel)));

                }
            }
        });


        binding.linMilitaryDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (!isCompleted6)
//                startActivity(new Intent(NurseProfileActivity.this, MilitaryDetailsActivity.class).
//                        putExtra("model", new Gson().toJson(basemodel)));
                if (!new MyPref(context).getUserData().getDesignation_id().equals("2")) {

                    startActivity(new Intent(NurseProfileActivity.this, MilitaryDetailsActivity.class).putExtra("model", new Gson().toJson(basemodelUser)));

                }else {

                    startActivity(new Intent(NurseProfileActivity.this, MilitaryDetailsActivity.class).putExtra("model", new Gson().toJson(basemodel)));

                }
            }
        });

        binding.linParollDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    startActivity(new Intent(NurseProfileActivity.this, PayrollActivity.class)
//                            .putExtra("model", new Gson().toJson(basemodel)));
                if (!new MyPref(context).getUserData().getDesignation_id().equals("2")) {

                    startActivity(new Intent(NurseProfileActivity.this, PayrollActivity.class).putExtra("model", new Gson().toJson(basemodelUser)));

                }else {

                    startActivity(new Intent(NurseProfileActivity.this, PayrollActivity.class).putExtra("model", new Gson().toJson(basemodel)));

                }
            }
        });
        binding.tvAddSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SignatureDialog(NurseProfileActivity.this, new SignatureDialog.OnsaveClickListener() {
                    @Override
                    public void onItemClick(String filepath) {
                        binding.imgSignature.setVisibility(View.VISIBLE);
                        signatuePath=filepath;
                        ImageUtils.loadImage(NurseProfileActivity.this, filepath, R.drawable.white_logo, binding.imgSignature);
                    }
                }).show();
            }
        });
        binding.tvSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(signatuePath.trim().length()!=0)
                    presenterInterface.sendRequest(NurseProfileActivity.this, "",null , ApiCallInterface.NURSE_SIGNATURE,signatuePath);
                else
                    Toast.makeText(NurseProfileActivity.this, "Please add signature", Toast.LENGTH_SHORT).show();

            }
        });

        binding.llUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if (!isCompleted1)
                startActivityForResult(new Intent(NurseProfileActivity.this, EditUserProfileActivity.class),101);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
//        presenterInterface.sendRequest(context, null, null, ApiCallInterface.USER_DETAILS);

        if (new MyPref(context).getUserData().getDesignation_id().equals("2")) {
            presenterInterface.sendRequest(context, null, null, ApiCallInterface.USER_DETAILS);
            presenterInterface.sendRequest(context, null, null, ApiCallInterface.GET_APPLICATION_DETAIL);
        } else {
            presenterInterface.sendRequest(context, null, null, ApiCallInterface.USER_DETAILS);
            presenterInterface.sendRequest(context, null, null, ApiCallInterface.GET_USER_APPLICATION_DETAIL);
        }

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
            UserModel basemodel = (UserModel) success;
            if (basemodel.isStatus().equals("true")) {
                if (basemodel.getData() != null) {
                    new MyPref(context).setUserData(basemodel.getData());
                }


            }
        }
        if (requestCode == ApiCallInterface.NURSE_SIGNATURE) {

            binding.linProfileDetails.setVisibility(View.GONE);
            binding.linSignature.setVisibility(View.GONE);
            binding.tvApproval.setVisibility(View.VISIBLE);

        }
        if (requestCode == ApiCallInterface.GET_APPLICATION_DETAIL) {

            basemodel = (ApplicationDetailsModel) success;

            if (basemodel.isStatus().equals("true")) {

                if (basemodel.getData() != null) {

                    if(basemodel.getData().getIs_signature_added()){

//                        binding.linProfileDetails.setVisibility(View.GONE);
//                        binding.linSignature.setVisibility(View.GONE);
//                        binding.tvApproval.setVisibility(View.VISIBLE);

                        if (basemodel.isStatus().equals("0")){

                            binding.linProfileDetails.setVisibility(View.GONE);
                            binding.linSignature.setVisibility(View.GONE);
                            binding.tvApproval.setVisibility(View.VISIBLE);

                        }else{
                            binding.linProfileDetails.setVisibility(View.VISIBLE);
                            binding.linSignature.setVisibility(View.GONE);
                            binding.tvApproval.setVisibility(View.GONE);
                            update();

                        }


                    } else{

                        if (basemodel.getData().getAddressDetail() != null && basemodel.getData().getAddressDetail().getAddress() != null) {

                            binding.linApplicationDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
                            binding.tvApplicationDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                            binding.tvApplicationDetailsStatus.setTextColor(getResources().getColor(R.color.black));
                            binding.tvApplicationDetailsStatus.setText("Status: Complete");
                            isCompleted1 = true;

                        } else {

                            binding.linApplicationDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
                            binding.tvApplicationDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
                            binding.tvApplicationDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
                            binding.tvApplicationDetailsStatus.setText("Status: Pending");
                            isCompleted1 = false;
                        }


                        if (basemodel.getData().getEmergencyDetail() != null) {

                            isCompleted2 = true;
                            binding.linEmergencyDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
                            binding.tvEmergencyDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                            binding.tvEmergencyDetailsStatus.setTextColor(getResources().getColor(R.color.black));
                            binding.tvEmergencyDetailsStatus.setText("Status: Complete");

                        } else {
                            isCompleted2 = false;
                            binding.linEmergencyDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
                            binding.tvEmergencyDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
                            binding.tvEmergencyDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
                            binding.tvEmergencyDetailsStatus.setText("Status: Pending");
                        }
                        if (basemodel.getData().getFamilyDetail() != null) {
                            isCompleted3 = true;
                            binding.linFamilyDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
                            binding.tvFamilyDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                            binding.tvFamilyDetailsStatus.setTextColor(getResources().getColor(R.color.black));
                            binding.tvFamilyDetailsStatus.setText("Status: Complete");
                        } else {
                            isCompleted3 = false;
                            binding.linFamilyDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
                            binding.tvFamilyDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
                            binding.tvFamilyDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
                            binding.tvFamilyDetailsStatus.setText("Status: Pending");
                        }
                        if (basemodel.getData().getEmployerDetail() != null) {
                            isCompleted4 = true;
                            binding.linCompanyDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
                            binding.tvCompanyDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                            binding.tvCompanyDetailsStatus.setTextColor(getResources().getColor(R.color.black));
                            binding.tvCompanyDetailsStatus.setText("Status: Complete");
                        } else {
                            isCompleted4 = false;
                            binding.linCompanyDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
                            binding.tvCompanyDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
                            binding.tvCompanyDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
                            binding.tvCompanyDetailsStatus.setText("Status: Pending");
                        }
                        if (basemodel.getData().getSecurityDetail() != null) {
                            isCompleted5 = true;
                            binding.linSecurityDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
                            binding.tvSecurityDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                            binding.tvSecurityDetailsStatus.setTextColor(getResources().getColor(R.color.black));
                            binding.tvSecurityDetailsStatus.setText("Status: Complete");
                        } else {
                            isCompleted5 = false;
                            binding.linSecurityDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
                            binding.tvSecurityDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
                            binding.tvSecurityDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
                            binding.tvSecurityDetailsStatus.setText("Status: Pending");
                        }
                        if (basemodel.getData().getMilitaryDetail() != null) {
                            isCompleted6 = true;
                            binding.linMilitaryDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
                            binding.tvMilitaryDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                            binding.tvMilitaryDetailsStatus.setTextColor(getResources().getColor(R.color.black));
                            binding.tvMilitaryDetailsStatus.setText("Status: Complete");
                        } else {
                            isCompleted6 = false;
                            binding.linMilitaryDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
                            binding.tvMilitaryDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
                            binding.tvMilitaryDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
                            binding.tvMilitaryDetailsStatus.setText("Status: Pending");
                        }
                        if (basemodel.getData().getDocuments() != null && basemodel.getData().getDocuments().size() > 1) {
                            isCompleted7 = true;
                            binding.linDocumentVerification.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
                            binding.tvDocumentVerification.setTextColor(getResources().getColor(R.color.colorPrimary));
                            binding.tvDocumentStatus.setTextColor(getResources().getColor(R.color.black));
                            binding.tvDocumentStatus.setText("Status: Complete");
                        } else {
                            isCompleted7 = false;
                            binding.linDocumentVerification.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
                            binding.tvDocumentVerification.setTextColor(getResources().getColor(R.color.color_74ABB0));
                            binding.tvDocumentStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
                            binding.tvDocumentStatus.setText("Status: Pending");
                        }

                        if (basemodel.getData().getPayrollDetail() != null) {
                            isCompleted8 = true;
                            binding.linParollDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
                            binding.tvParollDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                            binding.tvParollDetailsStatus.setTextColor(getResources().getColor(R.color.black));
                            binding.tvParollDetailsStatus.setText("Status: Complete");
                        } else {
                            isCompleted8 = false;
                            binding.linParollDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
                            binding.tvParollDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
                            binding.tvParollDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
                            binding.tvParollDetailsStatus.setText("Status: Pending");
                        }
                        if (basemodel.getData().getEducationDetail() != null) {

                            binding.linEducationDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
                            binding.tvEducationDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                            binding.tvEducationDetailsStatus.setTextColor(getResources().getColor(R.color.black));
                            binding.tvEducationDetailsStatus.setText("Status: Complete");
                            isCompleted9 = true;

                        } else {

                            binding.linEducationDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
                            binding.tvEducationDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
                            binding.tvEducationDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
                            binding.tvEducationDetailsStatus.setText("Status: Pending");
                            isCompleted9 = false;

                        }

                        if (isCompleted1 && isCompleted2 &&isCompleted4 && isCompleted5 && isCompleted6 && isCompleted7 && isCompleted8){

                            binding.linProfileDetails.setVisibility(View.GONE);
                            binding.linSignature.setVisibility(View.VISIBLE);
                            binding.tvApproval.setVisibility(View.GONE);
                            new SignatureDialog(this, new SignatureDialog.OnsaveClickListener() {
                                @Override
                                public void onItemClick(String filepath) {
                                    binding.imgSignature.setVisibility(View.VISIBLE);
                                    signatuePath=filepath;
                                    ImageUtils.loadImage(NurseProfileActivity.this, filepath, R.drawable.white_logo, binding.imgSignature);
                                }
                            }).show();

                        }
                    }



                }
            } else {
                errorMessage(binding.getRoot(), basemodel.getMessage());

            }
        }

        if (requestCode == ApiCallInterface.GET_USER_APPLICATION_DETAIL) {

            basemodelUser = (UserApplicationDetailsModel) success;

            if (basemodelUser.isStatus()) {

                if (basemodelUser.getData() != null) {

                    if(basemodelUser.getData().isIsSignatureAdded()){

//                        binding.linProfileDetails.setVisibility(View.GONE);
//                        binding.linSignature.setVisibility(View.GONE);
//                        binding.tvApproval.setVisibility(View.VISIBLE);

                        if (basemodelUser.isStatus()){

                            binding.linProfileDetails.setVisibility(View.GONE);
                            binding.linSignature.setVisibility(View.GONE);
                            binding.tvApproval.setVisibility(View.VISIBLE);

                        }else{
                            binding.linProfileDetails.setVisibility(View.VISIBLE);
                            binding.linSignature.setVisibility(View.GONE);
                            binding.tvApproval.setVisibility(View.GONE);
                            update1();
                        }

                    } else{

                        if (basemodelUser.getData().getAddressDetail() != null && basemodelUser.getData().getAddressDetail() != null) {

                            binding.linApplicationDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
                            binding.tvApplicationDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                            binding.tvApplicationDetailsStatus.setTextColor(getResources().getColor(R.color.black));
                            binding.tvApplicationDetailsStatus.setText("Status: Complete");
                            isCompleted1 = true;

                        } else {

                            binding.linApplicationDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
                            binding.tvApplicationDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
                            binding.tvApplicationDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
                            binding.tvApplicationDetailsStatus.setText("Status: Pending");
                            isCompleted1 = false;
                        }


                        if (basemodelUser.getData().getEmergencyDetail() != null) {

                            isCompleted2 = true;
                            binding.linEmergencyDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
                            binding.tvEmergencyDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                            binding.tvEmergencyDetailsStatus.setTextColor(getResources().getColor(R.color.black));
                            binding.tvEmergencyDetailsStatus.setText("Status: Complete");

                        } else {
                            isCompleted2 = false;
                            binding.linEmergencyDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
                            binding.tvEmergencyDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
                            binding.tvEmergencyDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
                            binding.tvEmergencyDetailsStatus.setText("Status: Pending");
                        }
                        if (basemodelUser.getData().getFamilyDetail() != null) {
                            isCompleted3 = true;
                            binding.linFamilyDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
                            binding.tvFamilyDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                            binding.tvFamilyDetailsStatus.setTextColor(getResources().getColor(R.color.black));
                            binding.tvFamilyDetailsStatus.setText("Status: Complete");
                        } else {
                            isCompleted3 = false;
                            binding.linFamilyDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
                            binding.tvFamilyDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
                            binding.tvFamilyDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
                            binding.tvFamilyDetailsStatus.setText("Status: Pending");
                        }
                        if (basemodelUser.getData().getEmployerDetail() != null) {
                            isCompleted4 = true;
                            binding.linCompanyDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
                            binding.tvCompanyDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                            binding.tvCompanyDetailsStatus.setTextColor(getResources().getColor(R.color.black));
                            binding.tvCompanyDetailsStatus.setText("Status: Complete");
                        } else {
                            isCompleted4 = false;
                            binding.linCompanyDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
                            binding.tvCompanyDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
                            binding.tvCompanyDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
                            binding.tvCompanyDetailsStatus.setText("Status: Pending");
                        }
                        if (basemodelUser.getData().getSecurityDetail() != null) {
                            isCompleted5 = true;
                            binding.linSecurityDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
                            binding.tvSecurityDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                            binding.tvSecurityDetailsStatus.setTextColor(getResources().getColor(R.color.black));
                            binding.tvSecurityDetailsStatus.setText("Status: Complete");
                        } else {
                            isCompleted5 = false;
                            binding.linSecurityDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
                            binding.tvSecurityDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
                            binding.tvSecurityDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
                            binding.tvSecurityDetailsStatus.setText("Status: Pending");
                        }
                        if (basemodelUser.getData().getMilitaryDetail() != null) {
                            isCompleted6 = true;
                            binding.linMilitaryDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
                            binding.tvMilitaryDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                            binding.tvMilitaryDetailsStatus.setTextColor(getResources().getColor(R.color.black));
                            binding.tvMilitaryDetailsStatus.setText("Status: Complete");
                        } else {
                            isCompleted6 = false;
                            binding.linMilitaryDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
                            binding.tvMilitaryDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
                            binding.tvMilitaryDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
                            binding.tvMilitaryDetailsStatus.setText("Status: Pending");
                        }
                        if (basemodelUser.getData().getDocuments() != null && basemodelUser.getData().getDocuments().size() > 1) {
                            isCompleted7 = true;
                            binding.linDocumentVerification.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
                            binding.tvDocumentVerification.setTextColor(getResources().getColor(R.color.colorPrimary));
                            binding.tvDocumentStatus.setTextColor(getResources().getColor(R.color.black));
                            binding.tvDocumentStatus.setText("Status: Complete");
                        } else {
                            isCompleted7 = false;
                            binding.linDocumentVerification.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
                            binding.tvDocumentVerification.setTextColor(getResources().getColor(R.color.color_74ABB0));
                            binding.tvDocumentStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
                            binding.tvDocumentStatus.setText("Status: Pending");
                        }

                        if (basemodelUser.getData().getPayrollDetails() != null) {
                            isCompleted8 = true;
                            binding.linParollDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
                            binding.tvParollDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                            binding.tvParollDetailsStatus.setTextColor(getResources().getColor(R.color.black));
                            binding.tvParollDetailsStatus.setText("Status: Complete");
                        } else {
                            isCompleted8 = false;
                            binding.linParollDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
                            binding.tvParollDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
                            binding.tvParollDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
                            binding.tvParollDetailsStatus.setText("Status: Pending");
                        }
                        if (basemodelUser.getData().getEducationDetail() != null) {

                            binding.linEducationDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
                            binding.tvEducationDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                            binding.tvEducationDetailsStatus.setTextColor(getResources().getColor(R.color.black));
                            binding.tvEducationDetailsStatus.setText("Status: Complete");
                            isCompleted9 = true;

                        } else {

                            binding.linEducationDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
                            binding.tvEducationDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
                            binding.tvEducationDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
                            binding.tvEducationDetailsStatus.setText("Status: Pending");
                            isCompleted9 = false;

                        }
                        if (basemodelUser.getData().getWorkHistoryDetail() != null) {
                            isCompleted10 = true;
                            binding.linBackgroundDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
                            binding.tvBackgroundDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                            binding.tvBackgroundDetailStatus.setTextColor(getResources().getColor(R.color.black));
                            binding.tvBackgroundDetailStatus.setText("Status: Complete");


                        } else {
                            isCompleted10 = false;
                            binding.linBackgroundDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
                            binding.tvBackgroundDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
                            binding.tvBackgroundDetailStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
                            binding.tvBackgroundDetailStatus.setText("Status: Pending");


                        }

                        if (basemodelUser.getData().getProfessionalDetail() != null) {
                            isCompleted11 = true;
                            binding.linProfessionalContact.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
                            binding.tvProfessionalContact.setTextColor(getResources().getColor(R.color.colorPrimary));
                            binding.tvProfessionalContactStatus.setTextColor(getResources().getColor(R.color.black));
                            binding.tvProfessionalContactStatus.setText("Status: Complete");


                        } else {
                            isCompleted11 = false;
                            binding.linProfessionalContact.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
                            binding.tvProfessionalContact.setTextColor(getResources().getColor(R.color.color_74ABB0));
                            binding.tvProfessionalContactStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
                            binding.tvProfessionalContactStatus.setText("Status: Pending");


                        }
                        if (isCompleted1 &&isCompleted7&& isCompleted8&&isCompleted9 &&isCompleted10&&isCompleted11){
                            binding.linProfileDetails.setVisibility(View.GONE);
                            binding.linSignature.setVisibility(View.VISIBLE);
                            binding.tvApproval.setVisibility(View.GONE);
                            new SignatureDialog(this, new SignatureDialog.OnsaveClickListener() {
                                @Override
                                public void onItemClick(String filepath) {
                                    binding.imgSignature.setVisibility(View.VISIBLE);
                                    signatuePath=filepath;
                                    ImageUtils.loadImage(NurseProfileActivity.this, filepath, R.drawable.white_logo, binding.imgSignature);
                                }
                            }).show();
                        }
                    }



                }
            } else {
                errorMessage(binding.getRoot(), basemodelUser.getMessage());

            }
        }


        Log.v("Data is:::", "" + success);
    }

    private void update1() {
        if (basemodelUser.getData().getAddressDetail() != null && basemodelUser.getData().getAddressDetail() != null) {

            binding.linApplicationDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
            binding.tvApplicationDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
            binding.tvApplicationDetailsStatus.setTextColor(getResources().getColor(R.color.black));
            binding.tvApplicationDetailsStatus.setText("Status: Complete");
            isCompleted1 = true;

        } else {

            binding.linApplicationDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
            binding.tvApplicationDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
            binding.tvApplicationDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
            binding.tvApplicationDetailsStatus.setText("Status: Pending");
            isCompleted1 = false;
        }


        if (basemodelUser.getData().getEmergencyDetail() != null) {
            isProfileUpdate=true;
            isCompleted2 = true;
            binding.linEmergencyDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
            binding.tvEmergencyDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
            binding.tvEmergencyDetailsStatus.setTextColor(getResources().getColor(R.color.black));
            binding.tvEmergencyDetailsStatus.setText("Status: Complete");

        } else {
            isCompleted2 = false;
            isProfileUpdate=false;
            binding.linEmergencyDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
            binding.tvEmergencyDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
            binding.tvEmergencyDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
            binding.tvEmergencyDetailsStatus.setText("Status: Pending");
        }
        if (basemodelUser.getData().getFamilyDetail() != null) {
            isCompleted3 = true;
            binding.linFamilyDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
            binding.tvFamilyDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
            binding.tvFamilyDetailsStatus.setTextColor(getResources().getColor(R.color.black));
            binding.tvFamilyDetailsStatus.setText("Status: Complete");
        } else {
            isCompleted3 = false;
            binding.linFamilyDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
            binding.tvFamilyDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
            binding.tvFamilyDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
            binding.tvFamilyDetailsStatus.setText("Status: Pending");
        }
        if (basemodelUser.getData().getEmployerDetail() != null) {
            isCompleted4 = true;
            binding.linCompanyDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
            binding.tvCompanyDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
            binding.tvCompanyDetailsStatus.setTextColor(getResources().getColor(R.color.black));
            binding.tvCompanyDetailsStatus.setText("Status: Complete");
        } else {
            isCompleted4 = false;
            binding.linCompanyDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
            binding.tvCompanyDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
            binding.tvCompanyDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
            binding.tvCompanyDetailsStatus.setText("Status: Pending");
        }
        if (basemodelUser.getData().getSecurityDetail() != null) {
            isCompleted5 = true;
            binding.linSecurityDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
            binding.tvSecurityDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
            binding.tvSecurityDetailsStatus.setTextColor(getResources().getColor(R.color.black));
            binding.tvSecurityDetailsStatus.setText("Status: Complete");
        } else {
            isCompleted5 = false;
            binding.linSecurityDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
            binding.tvSecurityDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
            binding.tvSecurityDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
            binding.tvSecurityDetailsStatus.setText("Status: Pending");
        }
        if (basemodelUser.getData().getMilitaryDetail() != null) {
            isCompleted6 = true;
            binding.linMilitaryDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
            binding.tvMilitaryDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
            binding.tvMilitaryDetailsStatus.setTextColor(getResources().getColor(R.color.black));
            binding.tvMilitaryDetailsStatus.setText("Status: Complete");
        } else {
            isCompleted6 = false;
            binding.linMilitaryDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
            binding.tvMilitaryDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
            binding.tvMilitaryDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
            binding.tvMilitaryDetailsStatus.setText("Status: Pending");
        }
        if (basemodelUser.getData().getDocuments() != null && basemodelUser.getData().getDocuments().size() > 1) {
            isCompleted7 = true;
            binding.linDocumentVerification.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
            binding.tvDocumentVerification.setTextColor(getResources().getColor(R.color.colorPrimary));
            binding.tvDocumentStatus.setTextColor(getResources().getColor(R.color.black));
            binding.tvDocumentStatus.setText("Status: Complete");
        } else {
            isCompleted7 = false;
            binding.linDocumentVerification.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
            binding.tvDocumentVerification.setTextColor(getResources().getColor(R.color.color_74ABB0));
            binding.tvDocumentStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
            binding.tvDocumentStatus.setText("Status: Pending");
        }

        if (basemodelUser.getData().getPayrollDetails() != null) {
            isCompleted8 = true;
            binding.linParollDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
            binding.tvParollDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
            binding.tvParollDetailsStatus.setTextColor(getResources().getColor(R.color.black));
            binding.tvParollDetailsStatus.setText("Status: Complete");
        } else {
            isCompleted8 = false;
            binding.linParollDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
            binding.tvParollDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
            binding.tvParollDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
            binding.tvParollDetailsStatus.setText("Status: Pending");
        }
        if (basemodelUser.getData().getEducationDetail() != null) {

            binding.linEducationDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
            binding.tvEducationDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
            binding.tvEducationDetailsStatus.setTextColor(getResources().getColor(R.color.black));
            binding.tvEducationDetailsStatus.setText("Status: Complete");
            isCompleted9 = true;

        } else {

            binding.linEducationDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
            binding.tvEducationDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
            binding.tvEducationDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
            binding.tvEducationDetailsStatus.setText("Status: Pending");
            isCompleted9 = false;

        }
        if (basemodelUser.getData().getWorkHistoryDetail() != null) {
            isCompleted10 = true;
            binding.linBackgroundDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
            binding.tvBackgroundDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
            binding.tvBackgroundDetailStatus.setTextColor(getResources().getColor(R.color.black));
            binding.tvBackgroundDetailStatus.setText("Status: Complete");


        } else {
            isCompleted10 = false;
            binding.linBackgroundDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
            binding.tvBackgroundDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
            binding.tvBackgroundDetailStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
            binding.tvBackgroundDetailStatus.setText("Status: Pending");


        }

        if (basemodelUser.getData().getProfessionalDetail() != null) {
            isCompleted11 = true;
            binding.linProfessionalContact.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
            binding.tvProfessionalContact.setTextColor(getResources().getColor(R.color.colorPrimary));
            binding.tvProfessionalContactStatus.setTextColor(getResources().getColor(R.color.black));
            binding.tvProfessionalContactStatus.setText("Status: Complete");


        } else {
            isCompleted11 = false;
            binding.linProfessionalContact.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
            binding.tvProfessionalContact.setTextColor(getResources().getColor(R.color.color_74ABB0));
            binding.tvProfessionalContactStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
            binding.tvProfessionalContactStatus.setText("Status: Pending");


        }
//        if (isCompleted1 &&isCompleted7&& isCompleted8&&isCompleted9 &&isCompleted10&&isCompleted11){
//            binding.linProfileDetails.setVisibility(View.GONE);
//            binding.linSignature.setVisibility(View.VISIBLE);
//            binding.tvApproval.setVisibility(View.GONE);
//            new SignatureDialog(this, new SignatureDialog.OnsaveClickListener() {
//                @Override
//                public void onItemClick(String filepath) {
//                    binding.imgSignature.setVisibility(View.VISIBLE);
//                    signatuePath=filepath;
//                    ImageUtils.loadImage(NurseProfileActivity.this, filepath, R.drawable.white_logo, binding.imgSignature);
//                }
//            }).show();
//        }
    }

    private void update() {
        if (basemodel.getData().getAddressDetail() != null && basemodel.getData().getAddressDetail().getAddress() != null) {

            binding.linApplicationDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
            binding.tvApplicationDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
            binding.tvApplicationDetailsStatus.setTextColor(getResources().getColor(R.color.black));
            binding.tvApplicationDetailsStatus.setText("Status: Complete");
            isCompleted1 = true;

        } else {

            binding.linApplicationDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
            binding.tvApplicationDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
            binding.tvApplicationDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
            binding.tvApplicationDetailsStatus.setText("Status: Pending");
            isCompleted1 = false;
        }


        if (basemodel.getData().getEmergencyDetail() != null) {

            isCompleted2 = true;
            binding.linEmergencyDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
            binding.tvEmergencyDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
            binding.tvEmergencyDetailsStatus.setTextColor(getResources().getColor(R.color.black));
            binding.tvEmergencyDetailsStatus.setText("Status: Complete");

        } else {
            isCompleted2 = false;
            binding.linEmergencyDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
            binding.tvEmergencyDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
            binding.tvEmergencyDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
            binding.tvEmergencyDetailsStatus.setText("Status: Pending");
        }
        if (basemodel.getData().getFamilyDetail() != null) {
            isCompleted3 = true;
            binding.linFamilyDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
            binding.tvFamilyDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
            binding.tvFamilyDetailsStatus.setTextColor(getResources().getColor(R.color.black));
            binding.tvFamilyDetailsStatus.setText("Status: Complete");
        } else {
            isCompleted3 = false;
            binding.linFamilyDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
            binding.tvFamilyDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
            binding.tvFamilyDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
            binding.tvFamilyDetailsStatus.setText("Status: Pending");
        }
        if (basemodel.getData().getEmployerDetail() != null) {
            isCompleted4 = true;
            binding.linCompanyDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
            binding.tvCompanyDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
            binding.tvCompanyDetailsStatus.setTextColor(getResources().getColor(R.color.black));
            binding.tvCompanyDetailsStatus.setText("Status: Complete");
        } else {
            isCompleted4 = false;
            binding.linCompanyDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
            binding.tvCompanyDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
            binding.tvCompanyDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
            binding.tvCompanyDetailsStatus.setText("Status: Pending");
        }
        if (basemodel.getData().getSecurityDetail() != null) {
            isCompleted5 = true;
            isProfileUpdate=false;
            binding.linSecurityDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
            binding.tvSecurityDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
            binding.tvSecurityDetailsStatus.setTextColor(getResources().getColor(R.color.black));
            binding.tvSecurityDetailsStatus.setText("Status: Complete");
        } else {
            isCompleted5 = false;
            isProfileUpdate=true;
            binding.linSecurityDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
            binding.tvSecurityDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
            binding.tvSecurityDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
            binding.tvSecurityDetailsStatus.setText("Status: Pending");
        }
        if (basemodel.getData().getMilitaryDetail() != null) {
            isCompleted6 = true;
            binding.linMilitaryDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
            binding.tvMilitaryDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
            binding.tvMilitaryDetailsStatus.setTextColor(getResources().getColor(R.color.black));
            binding.tvMilitaryDetailsStatus.setText("Status: Complete");
        } else {
            isCompleted6 = false;
            binding.linMilitaryDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
            binding.tvMilitaryDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
            binding.tvMilitaryDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
            binding.tvMilitaryDetailsStatus.setText("Status: Pending");
        }
        if (basemodel.getData().getDocuments() != null && basemodel.getData().getDocuments().size() > 1) {
            isCompleted7 = true;
            binding.linDocumentVerification.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
            binding.tvDocumentVerification.setTextColor(getResources().getColor(R.color.colorPrimary));
            binding.tvDocumentStatus.setTextColor(getResources().getColor(R.color.black));
            binding.tvDocumentStatus.setText("Status: Complete");
        } else {
            isCompleted7 = false;
            binding.linDocumentVerification.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
            binding.tvDocumentVerification.setTextColor(getResources().getColor(R.color.color_74ABB0));
            binding.tvDocumentStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
            binding.tvDocumentStatus.setText("Status: Pending");
        }

        if (basemodel.getData().getPayrollDetail() != null) {
            isCompleted8 = true;
            binding.linParollDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
            binding.tvParollDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
            binding.tvParollDetailsStatus.setTextColor(getResources().getColor(R.color.black));
            binding.tvParollDetailsStatus.setText("Status: Complete");
        } else {
            isCompleted8 = false;
            binding.linParollDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
            binding.tvParollDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
            binding.tvParollDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
            binding.tvParollDetailsStatus.setText("Status: Pending");
        }
        if (basemodel.getData().getEducationDetail() != null) {

            binding.linEducationDetails.setBackground(getResources().getDrawable(R.drawable.create_profile_back_green));
            binding.tvEducationDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
            binding.tvEducationDetailsStatus.setTextColor(getResources().getColor(R.color.black));
            binding.tvEducationDetailsStatus.setText("Status: Complete");
            isCompleted9 = true;

        } else {

            binding.linEducationDetails.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.create_profile_back));
            binding.tvEducationDetails.setTextColor(getResources().getColor(R.color.color_74ABB0));
            binding.tvEducationDetailsStatus.setTextColor(getResources().getColor(R.color.color_AEAEAE));
            binding.tvEducationDetailsStatus.setText("Status: Pending");
            isCompleted9 = false;

        }

//        if (isCompleted1 && isCompleted2 &&isCompleted4 && isCompleted5 && isCompleted6 && isCompleted7 && isCompleted8){
//
//            binding.linProfileDetails.setVisibility(View.GONE);
//            binding.linSignature.setVisibility(View.VISIBLE);
//            binding.tvApproval.setVisibility(View.GONE);
//            new SignatureDialog(this, new SignatureDialog.OnsaveClickListener() {
//                @Override
//                public void onItemClick(String filepath) {
//                    binding.imgSignature.setVisibility(View.VISIBLE);
//                    signatuePath=filepath;
//                    ImageUtils.loadImage(NurseProfileActivity.this, filepath, R.drawable.white_logo, binding.imgSignature);
//                }
//            }).show();
//
//        }

    }

    @Override
    public void onBackPressed() {
        if(isProfileUpdate){
            setResult(RESULT_OK,new Intent());
            finish();
        }
        else
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==101 && resultCode==RESULT_OK){
            isProfileUpdate=true;
        }
    }
}