package com.android.doral;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.android.doral.Utils.ImagePickerUtils;
import com.android.doral.Utils.ImageUtils;
import com.android.doral.Utils.MyPref;
import com.android.doral.Utils.StringUtils;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityNurseUploadDocumentBinding;
import com.android.doral.dialog.CustomAlertDialog;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.BaseModel;
import com.android.doral.retrofit.model.UserApplicationDetails.UserApplicationDetailsModel;
import com.android.doral.retrofit.model.applicationdetails.AddressDetail;
import com.android.doral.retrofit.model.applicationdetails.ApplicationDetailsModel;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NurseUploadDocumentActivity extends BaseActivity implements View.OnClickListener, BaseViewInterface {
    ActivityNurseUploadDocumentBinding binding;
    BasePresenterInterface presenterInterface;

    private List<String> social = new ArrayList<>();
    private List<String> professional_ref = new ArrayList<>();
    private List<String> nyc_nurse = new ArrayList<>();
    private List<String> mainpractice_insurance = new ArrayList<>();
    private List<String> CPR = new ArrayList<>();
    private List<String> physical = new ArrayList<>();
    private List<String> forensic_drug = new ArrayList<>();
    private List<String> rubella_immunization = new ArrayList<>();
    private List<String> rubella_Measles_immunization = new ArrayList<>();
    private List<String> annual_PPD = new ArrayList<>();
    private List<String> flu = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNurseUploadDocumentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.tvTitle.setText("Upload Documents");
        binding.toolbar.imgBack.setVisibility(View.VISIBLE);
        setClick();
        if (!new MyPref(context).getUserData().getDesignation_id().equals("2")) {
            getIntentParamsData1();
        }else{
            getIntentParamsData();
        }


        if (!new MyPref(context).getUserData().getDesignation_id().equals("2")) {
            binding.rlAnnualScreening.setVisibility(View.GONE);
            binding.rlXRayChest.setVisibility(View.GONE);
            binding.rlNycNurse.setVisibility(View.GONE);
            binding.rlMainpracticeInsurance.setVisibility(View.GONE);
            binding.rlCPR.setVisibility(View.GONE);
            binding.rlPhysical.setVisibility(View.GONE);
            binding.rlForensicDrug.setVisibility(View.GONE);
            binding.rlRubellaImmunization.setVisibility(View.GONE);
            binding.rlRubellaMeaslesImmunization.setVisibility(View.GONE);
            binding.rlAnnualPPD.setVisibility(View.GONE);
            binding.rlFlu.setVisibility(View.GONE);
            binding.rlProfessionalRef.setVisibility(View.GONE);
        }else{
            binding.rlPictIdentification.setVisibility(View.GONE);
            binding.rlCurrentCv.setVisibility(View.GONE);
            binding.rlProfessional.setVisibility(View.GONE);
            binding.rlStateRegCert.setVisibility(View.GONE);
            binding.rlDea.setVisibility(View.GONE);
            binding.rlControll.setVisibility(View.GONE);
            binding.rlMalprctice.setVisibility(View.GONE);
            binding.rlAllMalprctice.setVisibility(View.GONE);
            binding.rlSchoolDiploma.setVisibility(View.GONE);
            binding.rlRecCertificate.setVisibility(View.GONE);
            binding.rlFellCertificate.setVisibility(View.GONE);
            binding.rlIntenCertificate.setVisibility(View.GONE);
            binding.rlECFMGCertificate.setVisibility(View.GONE);
            binding.rlBoardCertificate.setVisibility(View.GONE);
            binding.rlHospitalAffiliationLetter.setVisibility(View.GONE);
            binding.rlSanctionsQueries.setVisibility(View.GONE);
            binding.rlMedicalWelcomeLetter.setVisibility(View.GONE);
            binding.rlSignedW9.setVisibility(View.GONE);
            binding.rlSignedESignatureForm.setVisibility(View.GONE);
        }

    }

    private void getIntentParamsData1() {
        if (getIntent().getExtras() != null) {

            UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);

            if (model != null) {

                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("1")) {
                            binding.imgIdProof.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.imgIdProof);
                        }

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("5")) {
                            binding.imgSecurity.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.imgSecurity);
                        }
                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("6")) {
                            binding.imgProffestional.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.imgProffestional);
                        }
                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("7")) {
                            binding.imgPractice.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.imgPractice);
                        }
                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("8")) {
                            binding.imgNyc.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.imgNyc);
                        }
                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("9")) {
                            binding.imgCpr.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.imgCpr);
                        }
                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("10")) {
                            binding.imgPhysical.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.imgPhysical);
                        }
                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("11")) {
                            binding.imgForensic.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.imgForensic);
                        }
                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("12")) {
                            binding.imgRubella.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.imgRubella);
                        }
                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("13")) {
                            binding.imgRubelaMeassels.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.imgRubelaMeassels);
                        }
                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("15")) {
                            binding.imgFlue.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.imgFlue);
                        }
                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("16")) {
                            binding.imgAnnualPpd.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.imgAnnualPpd);
                        }
                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("17")) {
                            binding.imgXraychest.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.imgXraychest);
                        }
                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("18")) {
                            binding.imgAnnualScreening.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.imgAnnualScreening);
                        }

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("20")) {
                            binding.imgProof.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.imgProof);
                        }

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("21")) {
                            binding.imgSocialproof.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.imgSocialproof);
                        }

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("2")) {
                            binding.tvDegreeFileName.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.tvDegreeFileName);
                        }

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("3")) {
                            binding.tvMedicalFileName.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.tvMedicalFileName);
                        }

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("4")) {
                            binding.tvInsouranceFileName.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.tvInsouranceFileName);
                        }

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("22")) {
                            binding.imgIdProof1.setVisibility(View.VISIBLE);
                            // binding.imgIdProof1.setText(model.getData().getDocuments().get(i).getFileUrl());
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.imgIdProof1);
                        }

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("25")) {
                            binding.tvPictIdentification.setVisibility(View.VISIBLE);
                            // binding.imgIdProof1.setText(model.getData().getDocuments().get(i).getFileUrl());
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.tvPictIdentification);
                        }

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("26")) {
                            binding.tvCurrentCv.setVisibility(View.VISIBLE);
                            // binding.imgIdProof1.setText(model.getData().getDocuments().get(i).getFileUrl());
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.tvCurrentCv);
                        }


                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("27")) {
                            binding.tvProfessional.setVisibility(View.VISIBLE);
                            // binding.imgIdProof1.setText(model.getData().getDocuments().get(i).getFileUrl());
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.tvProfessional);
                        }


                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("28")) {
                            binding.tvStateRegCert.setVisibility(View.VISIBLE);
                            // binding.imgIdProof1.setText(model.getData().getDocuments().get(i).getFileUrl());
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.tvStateRegCert);
                        }


                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("29")) {
                            binding.tvDea.setVisibility(View.VISIBLE);
                            // binding.imgIdProof1.setText(model.getData().getDocuments().get(i).getFileUrl());
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.tvDea);
                        }


                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("30")) {
                            binding.tvControll.setVisibility(View.VISIBLE);
                            // binding.imgIdProof1.setText(model.getData().getDocuments().get(i).getFileUrl());
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.tvControll);
                        }

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("31")) {
                            binding.tvMalprctice.setVisibility(View.VISIBLE);
                            // binding.imgIdProof1.setText(model.getData().getDocuments().get(i).getFileUrl());
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.tvMalprctice);
                        }

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("32")) {
                            binding.tvAllMalpractice.setVisibility(View.VISIBLE);
                            // binding.imgIdProof1.setText(model.getData().getDocuments().get(i).getFileUrl());
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.tvAllMalpractice);
                        }

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("33")) {
                            binding.tvMedicalSchoolDiploma.setVisibility(View.VISIBLE);
                            // binding.imgIdProof1.setText(model.getData().getDocuments().get(i).getFileUrl());
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.tvMedicalSchoolDiploma);
                        }

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("34")) {
                            binding.tvRecCertificate.setVisibility(View.VISIBLE);
                            // binding.imgIdProof1.setText(model.getData().getDocuments().get(i).getFileUrl());
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.tvRecCertificate);
                        }

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("35")) {
                            binding.tvFellCertificate.setVisibility(View.VISIBLE);
                            // binding.imgIdProof1.setText(model.getData().getDocuments().get(i).getFileUrl());
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.tvFellCertificate);
                        }

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("36")) {
                            binding.tvIntenCertificate.setVisibility(View.VISIBLE);
                            // binding.imgIdProof1.setText(model.getData().getDocuments().get(i).getFileUrl());
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.tvIntenCertificate);
                        }


                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("37")) {
                            binding.tvECFMGCertificate.setVisibility(View.VISIBLE);
                            // binding.imgIdProof1.setText(model.getData().getDocuments().get(i).getFileUrl());
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.tvECFMGCertificate);
                        }


                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("38")) {
                            binding.tvBoardCertificate.setVisibility(View.VISIBLE);
                            // binding.imgIdProof1.setText(model.getData().getDocuments().get(i).getFileUrl());
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.tvBoardCertificate);
                        }


                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("39")) {
                            binding.tvHospitalAffiliationLetter.setVisibility(View.VISIBLE);
                            // binding.imgIdProof1.setText(model.getData().getDocuments().get(i).getFileUrl());
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.tvHospitalAffiliationLetter);
                        }


                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("40")) {
                            binding.tvSanctionsQueries.setVisibility(View.VISIBLE);
                            // binding.imgIdProof1.setText(model.getData().getDocuments().get(i).getFileUrl());
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.tvSanctionsQueries);
                        }

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("41")) {
                            binding.tvMedicalWelcomeLetter.setVisibility(View.VISIBLE);
                            // binding.imgIdProof1.setText(model.getData().getDocuments().get(i).getFileUrl());
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.tvMedicalWelcomeLetter);
                        }

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("42")) {
                            binding.tvSignedW9.setVisibility(View.VISIBLE);
                            // binding.imgIdProof1.setText(model.getData().getDocuments().get(i).getFileUrl());
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.tvSignedW9);
                        }

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("43")) {
                            binding.tvSignedESignatureForm.setVisibility(View.VISIBLE);
                            // binding.imgIdProof1.setText(model.getData().getDocuments().get(i).getFileUrl());
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.tvSignedESignatureForm);
                        }





                    }

                }

            }

        }
    }

    private void setClick() {
        presenterInterface = new Presenter(this);
        binding.rlIdProof.setOnClickListener(this);
        binding.idproof.setOnClickListener(this);
        binding.rlIdSocialproof.setOnClickListener(this);
        binding.idsocialproof.setOnClickListener(this);
        binding.rlAnnualScreening.setOnClickListener(this);
        binding.rlXRayChest.setOnClickListener(this);
        binding.rlSocialSecurity.setOnClickListener(this);
        binding.rlProfessionalRef.setOnClickListener(this);
        binding.rlNycNurse.setOnClickListener(this);
        binding.rlMainpracticeInsurance.setOnClickListener(this);
        binding.rlCPR.setOnClickListener(this);
        binding.rlPhysical.setOnClickListener(this);
        binding.rlForensicDrug.setOnClickListener(this);
        binding.rlRubellaImmunization.setOnClickListener(this);
        binding.rlRubellaMeaslesImmunization.setOnClickListener(this);
        binding.rlAnnualPPD.setOnClickListener(this);
        binding.rlFlu.setOnClickListener(this);
        binding.rlDegreeProof.setOnClickListener(this);
        binding.rlInsuranceProof.setOnClickListener(this);
        binding.rlMedicalProof.setOnClickListener(this);
        binding.rlPictIdentification.setOnClickListener(this);
        binding.rlCurrentCv.setOnClickListener(this);
        binding.rlProfessional.setOnClickListener(this);
        binding.rlStateRegCert.setOnClickListener(this);
        binding.rlDea.setOnClickListener(this);
        binding.rlControll.setOnClickListener(this);
        binding.rlMalprctice.setOnClickListener(this);
        binding.rlAllMalprctice.setOnClickListener(this);
        binding.rlSchoolDiploma.setOnClickListener(this);
        binding.rlRecCertificate.setOnClickListener(this);
        binding.rlFellCertificate.setOnClickListener(this);
        binding.rlIntenCertificate.setOnClickListener(this);
        binding.rlECFMGCertificate.setOnClickListener(this);
        binding.rlBoardCertificate.setOnClickListener(this);
        binding.rlHospitalAffiliationLetter.setOnClickListener(this);
        binding.rlSanctionsQueries.setOnClickListener(this);
        binding.rlMedicalWelcomeLetter.setOnClickListener(this);
        binding.rlSignedW9.setOnClickListener(this);
        binding.rlSignedESignatureForm.setOnClickListener(this);

        binding.imgIdProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!new MyPref(context).getUserData().getDesignation_id().equals("2")) {
                    UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);
                    if (model.getData().getDocuments() != null) {
                        for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                            if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("1")) {
                                binding.image.setVisibility(View.VISIBLE);
                                binding.tvNext.setVisibility(View.GONE);
                                ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.image);
                            }
                        }
                    }
                }else{
                    ApplicationDetailsModel   model = new Gson().fromJson(getIntent().getStringExtra("model"), ApplicationDetailsModel.class);
                    if (model.getData().getDocuments() != null) {
                        for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                            if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("1")) {
                                binding.image.setVisibility(View.VISIBLE);
                                binding.tvNext.setVisibility(View.GONE);
                                ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.image);
                            }
                        }
                    }
                }

            }
        });

        binding.imgProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!new MyPref(context).getUserData().getDesignation_id().equals("2")) {
                    UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);
                    if (model.getData().getDocuments() != null) {
                        for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                            if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("20")) {
                                binding.image.setVisibility(View.VISIBLE);
                                binding.tvNext.setVisibility(View.GONE);
                                ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.image);
                            }
                        }
                    }
                }else{
                    ApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), ApplicationDetailsModel.class);
                    if (model.getData().getDocuments() != null) {
                        for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                            if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("20")) {
                                binding.image.setVisibility(View.VISIBLE);
                                binding.tvNext.setVisibility(View.GONE);
                                ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.image);
                            }
                        }
                    }
                }

            }
        });

        binding.imgSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!new MyPref(context).getUserData().getDesignation_id().equals("2")) {
                    UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);
                    if (model.getData().getDocuments() != null) {
                        for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                            if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("5")) {
                                binding.image.setVisibility(View.VISIBLE);
                                binding.tvNext.setVisibility(View.GONE);
                                ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.image);
                            }
                        }
                    }
                }else{
                    ApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), ApplicationDetailsModel.class);
                    if (model.getData().getDocuments() != null) {
                        for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                            if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("5")) {
                                binding.image.setVisibility(View.VISIBLE);
                                binding.tvNext.setVisibility(View.GONE);
                                ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.image);
                            }
                        }
                    }
                }

            }
        });

        binding.imgSocialproof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!new MyPref(context).getUserData().getDesignation_id().equals("2")) {
                    UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);
                    if (model.getData().getDocuments() != null) {
                        for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                            if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("21")) {
                                binding.image.setVisibility(View.VISIBLE);
                                binding.tvNext.setVisibility(View.GONE);
                                ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.image);
                            }
                        }
                    }
                }else{
                    ApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), ApplicationDetailsModel.class);
                    if (model.getData().getDocuments() != null) {
                        for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                            if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("21")) {
                                binding.image.setVisibility(View.VISIBLE);
                                binding.tvNext.setVisibility(View.GONE);
                                ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.image);
                            }
                        }
                    }
                }

            }
        });

        binding.tvDegreeFileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!new MyPref(context).getUserData().getDesignation_id().equals("2")) {
                    UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);
                    if (model.getData().getDocuments() != null) {
                        for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                            if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("2")) {
                                binding.image.setVisibility(View.VISIBLE);
                                binding.tvNext.setVisibility(View.GONE);
                                ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.image);
                            }
                        }
                    }
                }else{
                    ApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), ApplicationDetailsModel.class);
                    if (model.getData().getDocuments() != null) {
                        for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                            if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("2")) {
                                binding.image.setVisibility(View.VISIBLE);
                                binding.tvNext.setVisibility(View.GONE);
                                ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.image);
                            }
                        }
                    }
                }

            }
        });

        binding.tvInsouranceFileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!new MyPref(context).getUserData().getDesignation_id().equals("2")) {
                    UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);
                    if (model.getData().getDocuments() != null) {
                        for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                            if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("4")) {
                                binding.image.setVisibility(View.VISIBLE);
                                binding.tvNext.setVisibility(View.GONE);
                                ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.image);
                            }
                        }
                    }
                }else{
                    ApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), ApplicationDetailsModel.class);
                    if (model.getData().getDocuments() != null) {
                        for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                            if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("4")) {
                                binding.image.setVisibility(View.VISIBLE);
                                binding.tvNext.setVisibility(View.GONE);
                                ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.image);
                            }
                        }
                    }
                }

            }
        });

        binding.tvMedicalFileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!new MyPref(context).getUserData().getDesignation_id().equals("2")) {
                    UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);
                    if (model.getData().getDocuments() != null) {
                        for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                            if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("3")) {
                                binding.image.setVisibility(View.VISIBLE);
                                binding.tvNext.setVisibility(View.GONE);
                                ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.image);
                            }
                        }
                    }
                }else{
                    ApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), ApplicationDetailsModel.class);
                    if (model.getData().getDocuments() != null) {
                        for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                            if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("3")) {
                                binding.image.setVisibility(View.VISIBLE);
                                binding.tvNext.setVisibility(View.GONE);
                                ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.image);
                            }
                        }
                    }
                }

            }
        });

        binding.tvPictIdentification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);
                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("25")) {
                            binding.image.setVisibility(View.VISIBLE);
                            binding.tvNext.setVisibility(View.GONE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.image);
                        }
                    }
                }
            }
        });

        binding.tvCurrentCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);
                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("26")) {
                            binding.image.setVisibility(View.VISIBLE);
                            binding.tvNext.setVisibility(View.GONE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.image);
                        }
                    }
                }
            }
        });

        binding.tvProfessional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);
                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("27")) {
                            binding.image.setVisibility(View.VISIBLE);
                            binding.tvNext.setVisibility(View.GONE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.image);
                        }
                    }
                }
            }
        });

        binding.tvStateRegCert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);
                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("28")) {
                            binding.image.setVisibility(View.VISIBLE);
                            binding.tvNext.setVisibility(View.GONE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.image);
                        }
                    }
                }
            }
        });

        binding.tvDea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);
                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("29")) {
                            binding.image.setVisibility(View.VISIBLE);
                            binding.tvNext.setVisibility(View.GONE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.image);
                        }
                    }
                }
            }
        });

        binding.tvControll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);
                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("30")) {
                            binding.image.setVisibility(View.VISIBLE);
                            binding.tvNext.setVisibility(View.GONE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.image);
                        }
                    }
                }
            }
        });

        binding.tvMalprctice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);
                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("31")) {
                            binding.image.setVisibility(View.VISIBLE);
                            binding.tvNext.setVisibility(View.GONE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.image);
                        }
                    }
                }
            }
        });
        binding.tvAllMalpractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);
                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("32")) {
                            binding.image.setVisibility(View.VISIBLE);
                            binding.tvNext.setVisibility(View.GONE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.image);
                        }
                    }
                }
            }
        });
        binding.tvMedicalSchoolDiploma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);
                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("33")) {
                            binding.image.setVisibility(View.VISIBLE);
                            binding.tvNext.setVisibility(View.GONE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.image);
                        }
                    }
                }
            }
        });
        binding.tvRecCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);
                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("34")) {
                            binding.image.setVisibility(View.VISIBLE);
                            binding.tvNext.setVisibility(View.GONE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.image);
                        }
                    }
                }
            }
        });
        binding.tvFellCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);
                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("35")) {
                            binding.image.setVisibility(View.VISIBLE);
                            binding.tvNext.setVisibility(View.GONE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.image);
                        }
                    }
                }
            }
        });
        binding.tvIntenCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);
                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("36")) {
                            binding.image.setVisibility(View.VISIBLE);
                            binding.tvNext.setVisibility(View.GONE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.image);
                        }
                    }
                }
            }
        });
        binding.tvECFMGCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);
                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("37")) {
                            binding.image.setVisibility(View.VISIBLE);
                            binding.tvNext.setVisibility(View.GONE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.image);
                        }
                    }
                }
            }
        });
        binding.tvBoardCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);
                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("38")) {
                            binding.image.setVisibility(View.VISIBLE);
                            binding.tvNext.setVisibility(View.GONE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.image);
                        }
                    }
                }
            }
        });
        binding.tvHospitalAffiliationLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);
                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("39")) {
                            binding.image.setVisibility(View.VISIBLE);
                            binding.tvNext.setVisibility(View.GONE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.image);
                        }
                    }
                }
            }
        });
        binding.tvSanctionsQueries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);
                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("40")) {
                            binding.image.setVisibility(View.VISIBLE);
                            binding.tvNext.setVisibility(View.GONE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.image);
                        }
                    }
                }
            }
        });
        binding.tvMedicalWelcomeLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);
                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("41")) {
                            binding.image.setVisibility(View.VISIBLE);
                            binding.tvNext.setVisibility(View.GONE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.image);
                        }
                    }
                }
            }
        });
        binding.tvSignedW9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);
                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("42")) {
                            binding.image.setVisibility(View.VISIBLE);
                            binding.tvNext.setVisibility(View.GONE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.image);
                        }
                    }
                }
            }
        });
        binding.tvSignedESignatureForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);
                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("43")) {
                            binding.image.setVisibility(View.VISIBLE);
                            binding.tvNext.setVisibility(View.GONE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.image);
                        }
                    }
                }
            }
        });

        binding.imgProffestional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), ApplicationDetailsModel.class);
                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("6")) {
                            binding.image.setVisibility(View.VISIBLE);
                            binding.tvNext.setVisibility(View.GONE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.image);
                        }
                    }
                }
            }
        });

        binding.imgNyc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), ApplicationDetailsModel.class);
                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("8")) {
                            binding.image.setVisibility(View.VISIBLE);
                            binding.tvNext.setVisibility(View.GONE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.image);
                        }
                    }
                }
            }
        });

        binding.imgPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), ApplicationDetailsModel.class);
                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("7")) {
                            binding.image.setVisibility(View.VISIBLE);
                            binding.tvNext.setVisibility(View.GONE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.image);
                        }
                    }
                }
            }
        });

        binding.imgCpr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), ApplicationDetailsModel.class);
                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("9")) {
                            binding.image.setVisibility(View.VISIBLE);
                            binding.tvNext.setVisibility(View.GONE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.image);
                        }
                    }
                }
            }
        });

        binding.imgPhysical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), ApplicationDetailsModel.class);
                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("10")) {
                            binding.image.setVisibility(View.VISIBLE);
                            binding.tvNext.setVisibility(View.GONE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.image);
                        }
                    }
                }
            }
        });

        binding.imgForensic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), ApplicationDetailsModel.class);
                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("11")) {
                            binding.image.setVisibility(View.VISIBLE);
                            binding.tvNext.setVisibility(View.GONE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.image);
                        }
                    }
                }
            }
        });

        binding.imgRubella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), ApplicationDetailsModel.class);
                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("12")) {
                            binding.image.setVisibility(View.VISIBLE);
                            binding.tvNext.setVisibility(View.GONE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.image);
                        }
                    }
                }
            }
        });

        binding.imgRubelaMeassels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), ApplicationDetailsModel.class);
                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("13")) {
                            binding.image.setVisibility(View.VISIBLE);
                            binding.tvNext.setVisibility(View.GONE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.image);
                        }
                    }
                }
            }
        });

        binding.imgFlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), ApplicationDetailsModel.class);
                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("15")) {
                            binding.image.setVisibility(View.VISIBLE);
                            binding.tvNext.setVisibility(View.GONE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.image);
                        }
                    }
                }
            }
        });

        binding.imgAnnualPpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), ApplicationDetailsModel.class);
                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("16")) {
                            binding.image.setVisibility(View.VISIBLE);
                            binding.tvNext.setVisibility(View.GONE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.image);
                        }
                    }
                }
            }
        });

        binding.imgXraychest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), ApplicationDetailsModel.class);
                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("17")) {
                            binding.image.setVisibility(View.VISIBLE);
                            binding.tvNext.setVisibility(View.GONE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.image);
                        }
                    }
                }
            }
        });

        binding.imgAnnualScreening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), ApplicationDetailsModel.class);
                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("18")) {
                            binding.image.setVisibility(View.VISIBLE);
                            binding.tvNext.setVisibility(View.GONE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.image);
                        }
                    }
                }
            }
        });
        binding.toolbar.imgBack.setOnClickListener(this);
        binding.tvNext.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_back) {
            onBackPressed();
        }


        if (view.getId() == R.id.rl_id_proof) {
            opneCameraGallery(1028);
        }
        if (view.getId() == R.id.idproof) {
            opneCameraGallery(1031);
        }

        if (view.getId() == R.id.rl_id_socialproof) {
            opneCameraGallery(1032);
        }

        if (view.getId() == R.id.idsocialproof) {
            opneCameraGallery(1033);
        }

        if (view.getId() == R.id.rl_x_ray_chest) {
            opneCameraGallery(1029);
        }
        if (view.getId() == R.id.rl_annual_screening) {
            opneCameraGallery(1030);
        }
        if (view.getId() == R.id.rl_social_security) {
            opneCameraGallery(1005);
        }
        if (view.getId() == R.id.rl_professional_ref) {
            opneCameraGallery(1006);
        }
        if (view.getId() == R.id.rl_nyc_nurse) {
            opneCameraGallery(1007);
        }
        if (view.getId() == R.id.rl_mainpractice_insurance) {
            opneCameraGallery(1008);
        }
        if (view.getId() == R.id.rl_CPR) {
            opneCameraGallery(1009);
        }
        if (view.getId() == R.id.rl_physical) {
            opneCameraGallery(1010);
        }
        if (view.getId() == R.id.rl_forensic_drug) {
            opneCameraGallery(1011);
        }
        if (view.getId() == R.id.rl_rubella_immunization) {
            opneCameraGallery(1012);
        }
        if (view.getId() == R.id.rl_rubella_Measles_immunization) {
            opneCameraGallery(1013);
        }
        if (view.getId() == R.id.rl_annual_PPD) {
            opneCameraGallery(1014);
        }
        if (view.getId() == R.id.rl_flu) {
            opneCameraGallery(1015);
        }

        if (view.getId() == R.id.rl_degree_proof) {
            opneCameraGallery(1016);
        }
        if (view.getId() == R.id.rl_insurance_proof) {
            opneCameraGallery(1017);
        }

        if (view.getId() == R.id.rl_medical_proof) {
            opneCameraGallery(1018);
        }



        if (view.getId() == R.id.rl_professional) {
            opneCameraGallery(1019);
        }

        if (view.getId() == R.id.rl_state_reg_cert) {
            opneCameraGallery(1020);
        }

        if (view.getId() == R.id.rl_dea) {
            opneCameraGallery(1021);
        }

        if (view.getId() == R.id.rl_pict_identification) {
            opneCameraGallery(1022);
        }

        if (view.getId() == R.id.rl_current_cv) {
            opneCameraGallery(1023);
        }

        if (view.getId() == R.id.rl_controll) {
            opneCameraGallery(1024);
        }

        if (view.getId() == R.id.rl_malprctice) {
            opneCameraGallery(1025);

        }

        if (view.getId() == R.id.rl_All_malprctice) {
            opneCameraGallery(1026);
        }

        if (view.getId() == R.id.rl_school_diploma) {
            opneCameraGallery(1027);
        }

        if (view.getId() == R.id.rl_rec_certificate) {
            opneCameraGallery(1035);
        }

        if (view.getId() == R.id.rl_fell_certificate) {
            opneCameraGallery(1036);
        }

        if (view.getId() == R.id.rl_inten_certificate) {
            opneCameraGallery(1037);
        }

        if (view.getId() == R.id.rl_ECFMGCertificate) {
            opneCameraGallery(1038);
        }

        if (view.getId() == R.id.rl_BoardCertificate) {
            opneCameraGallery(1039);
        }

        if (view.getId() == R.id.rl_HospitalAffiliationLetter) {
            opneCameraGallery(1040);
        }
        if (view.getId() == R.id.rl_SanctionsQueries) {
            opneCameraGallery(1041);
        }

        if (view.getId() == R.id.rl_MedicalWelcomeLetter) {
            opneCameraGallery(1042);
        }

        if (view.getId() == R.id.rl_SignedW9) {
            opneCameraGallery(1043);
        }

        if (view.getId() == R.id.rl_SignedESignatureForm) {
            opneCameraGallery(1044);
        }
        if (view.getId() == R.id.tv_next) {
//            if (validate()) {
//           uploadDocument();
//            }

            if (binding.imgIdProof.getTag()==null){
                errorMessage(binding.getRoot(), "please select Front  ID  proof");
            }else if (binding.imgProof.getTag()==null){
                errorMessage(binding.getRoot(), "please select Back ID  proof");
            }else if (binding.imgSecurity.getTag()==null){
                errorMessage(binding.getRoot(), "please select Front Social Security proof");
            }else if (binding.imgSocialproof.getTag()==null){
                errorMessage(binding.getRoot(), "please select Back Social Security proof");
            }else {
                uploadDocument();
            }
//            uploadDocument();

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

        if (requestCode == 1028 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {

                    binding.imgIdProof.setVisibility(View.VISIBLE);
                    binding.imgIdProof.setTag(uri.getPath());
                    binding.imgIdProof.setImageURI(uri);
//                    binding.imgIdProof1.setVisibility(View.VISIBLE);
//                    binding.imgIdProof1.setTag(uri.getPath());
//                    binding.imgIdProof1.setImageURI(uri);

                }
            }
        }

        if (requestCode == 1031 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {

                    binding.imgProof.setVisibility(View.VISIBLE);
                    binding.imgProof.setTag(uri.getPath());
                    binding.imgProof.setImageURI(uri);

                }
            }
        }

        if (requestCode == 1032 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {

                    binding.imgSecurity.setVisibility(View.VISIBLE);
                    binding.imgSecurity.setTag(uri.getPath());
                    binding.imgSecurity.setImageURI(uri);

                }
            }
        }

        if (requestCode == 1033 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {

                    binding.imgSocialproof.setVisibility(View.VISIBLE);
                    binding.imgSocialproof.setTag(uri.getPath());
                    binding.imgSocialproof.setImageURI(uri);

                }
            }
        }
        if (requestCode == 1029 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {

                    binding.imgXraychest.setVisibility(View.VISIBLE);
                    binding.imgXraychest.setTag(uri.getPath());
                    binding.imgXraychest.setImageURI(uri);

                }
            }
        }
        if (requestCode == 1030 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {

                    binding.imgAnnualScreening.setVisibility(View.VISIBLE);
                    binding.imgAnnualScreening.setTag(uri.getPath());
                    binding.imgAnnualScreening.setImageURI(uri);

                }
            }
        }
        if (requestCode == 1005 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {
                    binding.imgSecurity.setVisibility(View.VISIBLE);
                    binding.imgSecurity.setTag(uri.getPath());
                    binding.imgSecurity.setImageURI(uri);
                    social.add(uri.getPath());
                }
            }
        }
        if (requestCode == 1006 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {
                    binding.imgProffestional.setVisibility(View.VISIBLE);
                    binding.imgProffestional.setImageURI(uri);
                    binding.imgProffestional.setTag(uri.getPath());
                    professional_ref.add(uri.getPath());

                }
            }
        }
        if (requestCode == 1007 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {
                    binding.imgNyc.setVisibility(View.VISIBLE);
                    binding.imgNyc.setImageURI(uri);
                    binding.imgNyc.setTag(uri.getPath());
                    nyc_nurse.add(uri.getPath());
                }
            }
        }
        if (requestCode == 1008 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {
                    binding.imgPractice.setVisibility(View.VISIBLE);
                    binding.imgPractice.setImageURI(uri);
                    binding.imgPractice.setTag(uri.getPath());
                    mainpractice_insurance.add(uri.getPath());
                }
            }
        }
        if (requestCode == 1009 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {


                    binding.imgCpr.setVisibility(View.VISIBLE);
                    binding.imgCpr.setImageURI(uri);
                    binding.imgCpr.setTag(uri.getPath());

                    CPR.add(uri.getPath());
                }
            }
        }
        if (requestCode == 1010 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {


                    binding.imgPhysical.setVisibility(View.VISIBLE);
                    binding.imgPhysical.setImageURI(uri);
                    binding.imgPhysical.setTag(uri.getPath());

                    physical.add(uri.getPath());
                }
            }
        }
        if (requestCode == 1011 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {
                    binding.imgForensic.setVisibility(View.VISIBLE);
                    binding.imgForensic.setImageURI(uri);
                    binding.imgForensic.setTag(uri.getPath());

                    forensic_drug.add(uri.getPath());
                }
            }
        }
        if (requestCode == 1012 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {

                    binding.imgRubella.setVisibility(View.VISIBLE);
                    binding.imgRubella.setImageURI(uri);
                    binding.imgRubella.setTag(uri.getPath());

                    rubella_immunization.add(uri.getPath());
                }
            }
        }
        if (requestCode == 1013 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {
                    binding.imgRubelaMeassels.setVisibility(View.VISIBLE);
                    binding.imgRubelaMeassels.setImageURI(uri);
                    binding.imgRubelaMeassels.setTag(uri.getPath());

                    rubella_Measles_immunization.add(uri.getPath());
                }
            }
        }
        if (requestCode == 1014 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {

                    binding.imgAnnualPpd.setVisibility(View.VISIBLE);
                    binding.imgAnnualPpd.setImageURI(uri);
                    binding.imgAnnualPpd.setTag(uri.getPath());

                    annual_PPD.add(uri.getPath());
                }
            }
        }
        if (requestCode == 1015 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {

                    binding.imgFlue.setVisibility(View.VISIBLE);
                    binding.imgFlue.setImageURI(uri);
                    binding.imgFlue.setTag(uri.getPath());
                    flu.add(uri.getPath());
                }
            }
        }


        if (requestCode == 1016 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {
                    binding.tvDegreeFileName.setVisibility(View.VISIBLE);
                    binding.tvDegreeFileName.setImageURI(uri);
                    binding.tvDegreeFileName.setTag(uri.getPath());
                }
            }


        }

        if (requestCode == 1017 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {
                    binding.tvInsouranceFileName.setVisibility(View.VISIBLE);
                    binding.tvInsouranceFileName.setImageURI(uri);
                    binding.tvInsouranceFileName.setTag(uri.getPath());
                }
            }
        }

        if (requestCode == 1018 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {
                    binding.tvMedicalFileName.setVisibility(View.VISIBLE);
                    binding.tvMedicalFileName.setImageURI(uri);
                    binding.tvMedicalFileName.setTag(uri.getPath());
                }
            }
        }

        if (requestCode == 1019 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {
                    binding.tvProfessional.setVisibility(View.VISIBLE);
                    binding.tvProfessional.setImageURI(uri);
                    binding.tvProfessional.setTag(uri.getPath());
                }
            }
        }

        if (requestCode == 1020 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {
                    binding.tvStateRegCert.setVisibility(View.VISIBLE);
                    binding.tvStateRegCert.setImageURI(uri);
                    binding.tvStateRegCert.setTag(uri.getPath());
                }
            }
        }

        if (requestCode == 1021 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {
                    binding.tvDea.setVisibility(View.VISIBLE);
                    binding.tvDea.setImageURI(uri);
                    binding.tvDea.setTag(uri.getPath());
                }
            }
        }

        if (requestCode == 1022 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {
                    binding.tvPictIdentification.setVisibility(View.VISIBLE);
                    binding.tvPictIdentification.setImageURI(uri);
                    binding.tvPictIdentification.setTag(uri.getPath());
                }
            }
        }

        if (requestCode == 1023 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {
                    binding.tvCurrentCv.setVisibility(View.VISIBLE);
                    binding.tvCurrentCv.setImageURI(uri);
                    binding.tvCurrentCv.setTag(uri.getPath());
                }
            }
        }

        if (requestCode == 1024 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {
                    binding.tvControll.setVisibility(View.VISIBLE);
                    binding.tvControll.setImageURI(uri);
                    binding.tvControll.setTag(uri.getPath());
                }
            }
        }

        if (requestCode == 1025 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {
                    binding.tvMalprctice.setVisibility(View.VISIBLE);
                    binding.tvMalprctice.setImageURI(uri);
                    binding.tvMalprctice.setTag(uri.getPath());
                }
            }
        }

        if (requestCode == 1026 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {
                    binding.tvAllMalpractice.setVisibility(View.VISIBLE);
                    binding.tvAllMalpractice.setImageURI(uri);
                    binding.tvAllMalpractice.setTag(uri.getPath());
                }
            }
        }

        if (requestCode == 1027 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {
                    binding.tvMedicalSchoolDiploma.setVisibility(View.VISIBLE);
                    binding.tvMedicalSchoolDiploma.setImageURI(uri);
                    binding.tvMedicalSchoolDiploma.setTag(uri.getPath());
                }
            }
        }

        if (requestCode == 1035 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {
                    binding.tvRecCertificate.setVisibility(View.VISIBLE);
                    binding.tvRecCertificate.setImageURI(uri);
                    binding.tvRecCertificate.setTag(uri.getPath());
                }
            }
        }

        if (requestCode == 1036 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {
                    binding.tvFellCertificate.setVisibility(View.VISIBLE);
                    binding.tvFellCertificate.setImageURI(uri);
                    binding.tvFellCertificate.setTag(uri.getPath());
                }
            }
        }

        if (requestCode == 1037 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {
                    binding.tvIntenCertificate.setVisibility(View.VISIBLE);
                    binding.tvIntenCertificate.setImageURI(uri);
                    binding.tvIntenCertificate.setTag(uri.getPath());
                }
            }
        }

        if (requestCode == 1038 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {
                    binding.tvECFMGCertificate.setVisibility(View.VISIBLE);
                    binding.tvECFMGCertificate.setImageURI(uri);
                    binding.tvECFMGCertificate.setTag(uri.getPath());
                }
            }
        }

        if (requestCode == 1039 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {
                    binding.tvBoardCertificate.setVisibility(View.VISIBLE);
                    binding.tvBoardCertificate.setImageURI(uri);
                    binding.tvBoardCertificate.setTag(uri.getPath());
                }
            }
        }

        if (requestCode == 1040 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {
                    binding.tvHospitalAffiliationLetter.setVisibility(View.VISIBLE);
                    binding.tvHospitalAffiliationLetter.setImageURI(uri);
                    binding.tvHospitalAffiliationLetter.setTag(uri.getPath());
                }
            }
        }

        if (requestCode == 1041 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {
                    binding.tvSanctionsQueries.setVisibility(View.VISIBLE);
                    binding.tvSanctionsQueries.setImageURI(uri);
                    binding.tvSanctionsQueries.setTag(uri.getPath());
                }
            }
        }

        if (requestCode == 1042 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {
                    binding.tvMedicalWelcomeLetter.setVisibility(View.VISIBLE);
                    binding.tvMedicalWelcomeLetter.setImageURI(uri);
                    binding.tvMedicalWelcomeLetter.setTag(uri.getPath());
                }
            }
        }

        if (requestCode == 1043 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {
                    binding.tvSignedW9.setVisibility(View.VISIBLE);
                    binding.tvSignedW9.setImageURI(uri);
                    binding.tvSignedW9.setTag(uri.getPath());
                }
            }
        }

        if (requestCode == 1044 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {
                    binding.tvSignedESignatureForm.setVisibility(View.VISIBLE);
                    binding.tvSignedESignatureForm.setImageURI(uri);
                    binding.tvSignedESignatureForm.setTag(uri.getPath());
                }
            }
        }


    }

    private boolean validate() {


        if (binding.imgSecurity.getDrawable()==null) {
            errorMessage(binding.getRoot(), "please select Social Security proof");
            return false;
        }

//        if (binding.tvProfessionalRef.getTag() == null) {
//            errorMessage(binding.getRoot(), "please select Professional Reference proof");
//            return false;
//        }
//        if (binding.tvNYC.getTag() == null) {
//            errorMessage(binding.getRoot(), "please select NYC Nurse Certificate proof");
//            return false;
//        }
//        if (binding.tvPractise.getTag() == null) {
//            errorMessage(binding.getRoot(), "please select MainPractice proof");
//            return false;
//        }
//        if (binding.tvCPR.getTag() == null) {
//            errorMessage(binding.getRoot(), "please select CPR proof");
//            return false;
//        }
//        if (binding.tvPhysical.getTag() == null) {
//            errorMessage(binding.getRoot(), "please select Physical proof");
//            return false;
//        }
//        if (binding.tvForensicDrug.getTag() == null) {
//            errorMessage(binding.getRoot(), "please select Forensic Drug Screen proof");
//            return false;
//        }
//        if (binding.tvRubellaImmunization.getTag() == null) {
//            errorMessage(binding.getRoot(), "please select Rubella Immunization proof");
//            return false;
//        }
//        if (binding.tvRubellaMeasles.getTag() == null) {
//            errorMessage(binding.getRoot(), "please select Rubella/Measles  proof");
//            return false;
//        }
//        if (binding.tvAnnual.getTag() == null) {
//            errorMessage(binding.getRoot(), "please select Annual PPD proof");
//            return false;
//        }
//        if (binding.tvFlu.getTag() == null) {
//            errorMessage(binding.getRoot(), "please select Flu Vaccination proof");
//            return false;
//        }

        return true;
    }

    @Override
    public void retry(int pos) {

    }

    @Override
    public void onError(String errorMsg, int requestCode, int resultCode) {
        errorMessage(binding.getRoot(), "Please try again later");
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
        presenterInterface.sendRequest(NurseUploadDocumentActivity.this,
                "", null, ApiCallInterface.UPLOAD_DOCUMENT_NURSE,
                binding.imgSecurity.getTag() != null ? binding.imgSecurity.getTag().toString() : "",
                binding.imgProffestional.getTag() != null ? binding.imgProffestional.getTag().toString() : "",
                binding.imgPractice.getTag() != null ? binding.imgPractice.getTag().toString() : "",
                binding.imgNyc.getTag() != null ? binding.imgNyc.getTag().toString() : "",
                binding.imgCpr.getTag() != null ? binding.imgCpr.getTag().toString() : "",
                binding.imgPhysical.getTag() != null ? binding.imgPhysical.getTag().toString() : "",
                binding.imgForensic.getTag() != null ? binding.imgForensic.getTag().toString() : "",
                binding.imgRubella.getTag() != null ? binding.imgRubella.getTag().toString() : "",
                binding.imgRubelaMeassels.getTag() != null ? binding.imgRubelaMeassels.getTag().toString() : "",
                binding.imgAnnualPpd.getTag() != null ? binding.imgAnnualPpd.getTag().toString() : "",
                binding.imgFlue.getTag() != null ? binding.imgFlue.getTag().toString() : "",
                binding.imgIdProof.getTag() != null ? binding.imgIdProof.getTag().toString() : "",
                binding.imgXraychest.getTag() != null ? binding.imgXraychest.getTag().toString() : "",
                binding.imgAnnualScreening.getTag() != null ? binding.imgAnnualScreening.getTag().toString() : "",
                binding.imgProof.getTag() != null ? binding.imgProof.getTag().toString() : "" ,
                binding.imgSocialproof.getTag() != null ? binding.imgSocialproof.getTag().toString() : "",
                binding.tvDegreeFileName.getTag() != null ? binding.tvDegreeFileName.getTag().toString() : "",
                binding.tvInsouranceFileName.getTag() != null ? binding.tvInsouranceFileName.getTag().toString() : "",
                binding.tvMedicalFileName.getTag() != null ? binding.tvMedicalFileName.getTag().toString() : "",
                binding.imgIdProof1.getTag() != null ? binding.imgIdProof1.getTag().toString() : "",

                binding.tvPictIdentification.getTag() != null ? binding.tvPictIdentification.getTag().toString() : "",
                binding.tvCurrentCv.getTag() != null ? binding.tvCurrentCv.getTag().toString() : "",
                binding.tvProfessional.getTag() != null ? binding.tvProfessional.getTag().toString() : "",
                binding.tvStateRegCert.getTag() != null ? binding.tvStateRegCert.getTag().toString() : "",
                binding.tvDea.getTag() != null ? binding.tvDea.getTag().toString() : "",
                binding.tvControll.getTag() != null ? binding.tvControll.getTag().toString() : "",
                binding.tvMalprctice.getTag() != null ? binding.tvMalprctice.getTag().toString() : "",
                binding.tvAllMalpractice.getTag() != null ? binding.tvAllMalpractice.getTag().toString() : "",
                binding.tvMedicalSchoolDiploma.getTag() != null ? binding.tvMedicalSchoolDiploma.getTag().toString() : "",
                binding.tvRecCertificate.getTag() != null ? binding.tvRecCertificate.getTag().toString() : "",
                binding.tvFellCertificate.getTag() != null ? binding.tvFellCertificate.getTag().toString() : "",
                binding.tvIntenCertificate.getTag() != null ? binding.tvIntenCertificate.getTag().toString() : "",
                binding.tvECFMGCertificate.getTag() != null ? binding.tvECFMGCertificate.getTag().toString() : "",
                binding.tvBoardCertificate.getTag() != null ? binding.tvBoardCertificate.getTag().toString() : "",
                binding.tvHospitalAffiliationLetter.getTag() != null ? binding.tvHospitalAffiliationLetter.getTag().toString() : "",
                binding.tvSanctionsQueries.getTag() != null ? binding.tvSanctionsQueries.getTag().toString() : "",
                binding.tvMedicalWelcomeLetter.getTag() != null ? binding.tvMedicalWelcomeLetter.getTag().toString() : "",
                binding.tvSignedW9.getTag() != null ? binding.tvSignedW9.getTag().toString() : "",
                binding.tvSignedESignatureForm.getTag() != null ? binding.tvSignedESignatureForm.getTag().toString() : ""

        );
    }


    public void getIntentParamsData() {
        if (getIntent().getExtras() != null) {

            ApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), ApplicationDetailsModel.class);

            if (model != null) {

                if (model.getData().getDocuments() != null) {
                    for (int i = 0; i < model.getData().getDocuments().size(); i++) {

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("1")) {
                            binding.imgIdProof.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.imgIdProof);
                        }

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("5")) {
                            binding.imgSecurity.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.imgSecurity);
                        }
                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("6")) {
                            binding.imgProffestional.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.imgProffestional);
                        }
                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("7")) {
                            binding.imgPractice.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.imgPractice);
                        }
                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("8")) {
                            binding.imgNyc.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.imgNyc);
                        }
                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("9")) {
                            binding.imgCpr.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.imgCpr);
                        }
                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("10")) {
                            binding.imgPhysical.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.imgPhysical);
                        }
                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("11")) {
                            binding.imgForensic.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.imgForensic);
                        }
                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("12")) {
                            binding.imgRubella.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.imgRubella);
                        }
                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("13")) {
                            binding.imgRubelaMeassels.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.imgRubelaMeassels);
                        }
                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("15")) {
                            binding.imgFlue.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.imgFlue);
                        }
                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("16")) {
                            binding.imgAnnualPpd.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.imgAnnualPpd);
                        }
                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("17")) {
                            binding.imgXraychest.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.imgXraychest);
                        }
                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("18")) {
                            binding.imgAnnualScreening.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.imgAnnualScreening);
                        }

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("20")) {
                            binding.imgProof.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.imgProof);
                        }

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("21")) {
                            binding.imgSocialproof.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.imgSocialproof);
                        }

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("2")) {
                            binding.tvDegreeFileName.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.tvDegreeFileName);
                        }

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("3")) {
                            binding.tvMedicalFileName.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.tvMedicalFileName);
                        }

                        if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("4")) {
                            binding.tvInsouranceFileName.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.tvInsouranceFileName);
                        }

                    }

                }

            }

        }
    }
}