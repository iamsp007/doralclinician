package com.android.doral;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;

import com.android.doral.Utils.DateUtils;
import com.android.doral.Utils.MyPref;
import com.android.doral.Utils.StringUtils;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityMilitaryDetailsBinding;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.UserApplicationDetails.UserApplicationDetailsModel;
import com.android.doral.retrofit.model.applicationdetails.ApplicationDetailsModel;
import com.android.doral.retrofit.model.applicationdetails.MilitaryDetail;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class MilitaryDetailsActivity extends BaseActivity implements BaseViewInterface {


    ActivityMilitaryDetailsBinding binding;
    BasePresenterInterface presenterInterface;
    Boolean isMilitary = true;
    Boolean isCommited = true;
    Boolean isVietnam = true;
    Boolean isDisable = true;
    Boolean isSpecial = true;
    DatePickerDialog datePickerDialog;
    DatePickerDialog ServeEnddatePickerDialog;

    int endMonth=0;
    int endDate=0;
    int endYear=0;


    int startMonth=0;
    int startDate=0;
    int startYear=0;

    private final SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public void getIntentParamsData() {
        Log.v("getIntentParamsData","called");
        if (getIntent().getExtras() != null) {
            Log.v("I am ", "here");
            ApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), ApplicationDetailsModel.class);

            if (model != null) {

                if (model.getData().getMilitaryDetail() != null) {
                    MilitaryDetail militaryDetail=model.getData().getMilitaryDetail();
                    isMilitary=militaryDetail.getIsMilitary();
                    if(militaryDetail.getIsMilitary()) {
                        binding.rbMilitaryYes.setChecked(true);
                        binding.rbMilitaryNo.setChecked(false);
                        binding.linServeDetail.setVisibility(View.VISIBLE);
                        binding.edtBranch.setText(militaryDetail.getBranch());
                        if (militaryDetail.getServe_end_date() == null) {
                           binding.tvServeEndDate.setText("");
                        }else{
                            binding.tvServeEndDate.setText(militaryDetail.getServe_end_date().replace("\\",""));
                            String[]endDateData=militaryDetail.getServe_end_date().replace("\\","").split("/");
                            endMonth= Integer.parseInt(endDateData[0]);
                            endDate= Integer.parseInt(endDateData[1]);
                            endYear= Integer.parseInt(endDateData[2]);
                        }
                        if (militaryDetail.getServe_start_date() == null) {
                            binding.tvServeStartDate.setText("");
                        }else{
                            binding.tvServeStartDate.setText(militaryDetail.getServe_start_date().replace("\\", ""));
                            String[] startDateData = militaryDetail.getServe_start_date().replace("\\", "").split("/");
                            startMonth = Integer.parseInt(startDateData[0]);
                            startDate = Integer.parseInt(startDateData[1]);
                            startYear = Integer.parseInt(startDateData[2]);
                        }
                    }
                    else {
                        binding.rbMilitaryYes.setChecked(false);
                        binding.rbMilitaryNo.setChecked(true);
                        binding.linServeDetail.setVisibility(View.GONE);
                    }
                    binding.rbMilatryCommitmentYes.setChecked(militaryDetail.getIsCommited() ? true :false);
                    binding.rbMilatryCommitmentNo.setChecked(militaryDetail.getIsCommited() ? false :true);

                    if(militaryDetail.getIsCommited()){
                        binding.edtExplain.setVisibility(View.VISIBLE);
                        binding.edtExplain.setText(militaryDetail.getIsCommitedExplain());
                    }


                    isVietnam=militaryDetail.getIsVietnam();
                    isCommited=militaryDetail.getIsCommited();
                    isDisable=militaryDetail.getIsDisableVetran();
                    isSpecial=militaryDetail.getIsSpecialDisableVereran();

                    binding.rbVeteranYes.setChecked(militaryDetail.getIsVietnam() ? true :false);
                    binding.rbVeteranNo.setChecked(militaryDetail.getIsVietnam() ? false :true);


                    binding.rbDisableveteranYes.setChecked(militaryDetail.getDisableVetran() ? true :false);
                    binding.rbDisableveteranNo.setChecked(militaryDetail.getDisableVetran() ? false :true);

                    binding.rbSpecialdisableveteranYes.setChecked(militaryDetail.getIsSpecialDisableVereran() ? true :false);
                    binding.rbSpecialdisableveteranNo.setChecked(militaryDetail.getIsSpecialDisableVereran() ? false :true);
                }
            }
        }
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMilitaryDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setStatusBarColor(R.color.colorPrimary);
        presenterInterface = new Presenter(this);

        binding.toolbar.tvTitle.setText("Military Details");
        binding.toolbar.imgBack.setVisibility(View.VISIBLE);
        binding.toolbar.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (!new MyPref(context).getUserData().getDesignation_id().equals("2")) {
            //getIntentParamsData1();
        }else{
            getIntentParamsData();
        }
        binding.rbMilitaryYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    isMilitary = true;
                    binding.linServeDetail.setVisibility(View.VISIBLE);
                }

            }
        });
        binding.rbMilitaryNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    isMilitary = false;
                    binding.linServeDetail.setVisibility(View.GONE);
                }

            }
        });

        binding.rbMilatryCommitmentYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                    isCommited = true;

            }
        });
        binding.rbMilatryCommitmentNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                    isCommited = false;

            }
        });

         binding.rbMilatryCommitmentYes.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 binding.milatryExplain.setVisibility(View.VISIBLE);
             }
         });

        binding.rbMilatryCommitmentNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.milatryExplain.setVisibility(View.GONE);
            }
        });
        binding.rbVeteranYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                    isVietnam = true;

            }
        });
        binding.rbVeteranNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                    isVietnam = false;

            }
        });
        binding.rbDisableveteranYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                    isDisable = true;

            }
        });
        binding.rbDisableveteranNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                    isDisable = false;
            }
        });
        binding.rbSpecialdisableveteranYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                    isDisable = true;

            }
        });
        binding.rbSpecialdisableveteranNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                    isDisable = false;
            }
        });

        binding.tvServeStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.YEAR, -18);
                if(startDate!=0){
                    calendar.set(Calendar.YEAR, startYear);
                    calendar.set(Calendar.MONTH, startMonth);
                    calendar.set(Calendar.DAY_OF_MONTH, startDate);
                }

                datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        /*birth_date = i + "/" + (i1 + 1) + "/" + i2;
                        binding.activitySignUpTvDOB.setText(birth_date);*/

                        startDate=dayOfMonth;
                        startMonth=monthOfYear;
                        startYear=year;
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                        birth_date = dateformat.format(calendar.getTime());
                        binding.tvServeStartDate.setText(DateUtils.changeDateFormat(DateUtils.DATE_FORMATE_8, DateUtils.DATE_FORMATE_1, dateformat.format(calendar.getTime()), false));

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                Calendar calendar1 = Calendar.getInstance();
                calendar1.add(Calendar.YEAR, -18);
                datePickerDialog.getDatePicker().setMaxDate(calendar1.getTimeInMillis());
                datePickerDialog.show();
            }
        });
        binding.tvServeEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.YEAR, -18);
                if(endDate!=0){
                    calendar.set(Calendar.YEAR, endYear);
                    calendar.set(Calendar.MONTH, endMonth);
                    calendar.set(Calendar.DAY_OF_MONTH, endDate);
                }
                ServeEnddatePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        /*birth_date = i + "/" + (i1 + 1) + "/" + i2;
                        binding.activitySignUpTvDOB.setText(birth_date);*/
                        endDate=dayOfMonth;
                        endMonth=monthOfYear;
                        endYear=year;
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        binding.tvServeEndDate.setText(DateUtils.changeDateFormat(DateUtils.DATE_FORMATE_8, DateUtils.DATE_FORMATE_1, dateformat.format(calendar.getTime()), false));

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                Calendar calendar1 = Calendar.getInstance();
                calendar1.add(Calendar.YEAR, -18);
                ServeEnddatePickerDialog.getDatePicker().setMaxDate(calendar1.getTimeInMillis());
                ServeEnddatePickerDialog.show();
            }
        });

        binding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.rbMilitaryYes.isChecked() && !StringUtils.isNotEmpty(binding.edtBranch.getText().toString())){
                    errorMessage(binding.getRoot(), "Please fill Detail");

                }
//                else if (binding.rbMilatryCommitmentNo.isChecked() && !StringUtils.isNotEmpty(binding.edtExplain.getText().toString())){
//                  //  errorMessage(binding.getRoot(), "Please Explain  Detail");
//                }
                else{
                    HashMap<String, Object> param = new HashMap<>();
                    HashMap<String, Object> param_detail = new HashMap<>();
                    param_detail.put("key", "military_detail");
                    param.put("isMilitary", isMilitary);
                    param.put("branch", binding.edtBranch.getText().toString());

                    param.put("serve_start_date", binding.tvServeStartDate.getText().toString());
                    param.put("serve_end_date", binding.tvServeEndDate.getText().toString());
                    param.put("isCommited", isCommited);
                    param.put("isDisableVetran", isDisable);
                    param.put("isVietnam", isVietnam);
                    param.put("isSpecialDisableVereran", isSpecial);
                    param.put("isCommited_explain", binding.edtExplain.getText().toString());
                    param_detail.put("military_detail", param);
                    presenterInterface.callAPI(MilitaryDetailsActivity.this, null, param_detail, ApiCallInterface.STORE_APPLICANT_DETAIL);
                }


            }
        });
    }

    /*private void getIntentParamsData1() {
        if (getIntent().getExtras() != null) {
            Log.v("I am ", "here");
            UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);

            if (model != null) {

                if (model.getData().getMilitaryDetail() != null) {
                    MilitaryDetail militaryDetail=model.getData().getMilitaryDetail();
                    isMilitary=militaryDetail.getIsMilitary();
                    if(militaryDetail.getIsMilitary()) {
                        binding.rbMilitaryYes.setChecked(true);
                        binding.rbMilitaryNo.setChecked(false);
                        binding.linServeDetail.setVisibility(View.VISIBLE);
                        binding.edtBranch.setText(militaryDetail.getBranch());
                        if (militaryDetail.getServe_end_date() == null) {
                            binding.tvServeEndDate.setText("");
                        }else{
                            binding.tvServeEndDate.setText(militaryDetail.getServe_end_date().replace("\\",""));
                            String[]endDateData=militaryDetail.getServe_end_date().replace("\\","").split("/");
                            endMonth= Integer.parseInt(endDateData[0]);
                            endDate= Integer.parseInt(endDateData[1]);
                            endYear= Integer.parseInt(endDateData[2]);
                        }
                        if (militaryDetail.getServe_start_date() == null) {
                            binding.tvServeStartDate.setText("");
                        }else{
                            binding.tvServeStartDate.setText(militaryDetail.getServe_start_date().replace("\\", ""));
                            String[] startDateData = militaryDetail.getServe_start_date().replace("\\", "").split("/");
                            startMonth = Integer.parseInt(startDateData[0]);
                            startDate = Integer.parseInt(startDateData[1]);
                            startYear = Integer.parseInt(startDateData[2]);
                        }
                    }
                    else {
                        binding.rbMilitaryYes.setChecked(false);
                        binding.rbMilitaryNo.setChecked(true);
                        binding.linServeDetail.setVisibility(View.GONE);
                        binding.milatryExplain.setVisibility(View.GONE);
                    }
                    binding.rbMilatryCommitmentYes.setChecked(militaryDetail.getIsCommited() ? true :false);
                    binding.rbMilatryCommitmentNo.setChecked(militaryDetail.getIsCommited() ? false :true);

                    if(militaryDetail.getIsCommited()){
                        binding.edtExplain.setVisibility(View.VISIBLE);
                        binding.edtExplain.setText(militaryDetail.getIsCommitedExplain());
                    }


                    isVietnam=militaryDetail.getIsVietnam();
                    isCommited=militaryDetail.getIsCommited();
                    isDisable=militaryDetail.getIsDisableVetran();
                    isSpecial=militaryDetail.getIsSpecialDisableVereran();

                    binding.rbVeteranYes.setChecked(militaryDetail.getIsVietnam() ? true :false);
                    binding.rbVeteranNo.setChecked(militaryDetail.getIsVietnam() ? false :true);


                    binding.rbDisableveteranYes.setChecked(militaryDetail.getDisableVetran() ? true :false);
                    binding.rbDisableveteranNo.setChecked(militaryDetail.getDisableVetran() ? false :true);

                    binding.rbSpecialdisableveteranYes.setChecked(militaryDetail.getIsSpecialDisableVereran() ? true :false);
                    binding.rbSpecialdisableveteranNo.setChecked(militaryDetail.getIsSpecialDisableVereran() ? false :true);
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
