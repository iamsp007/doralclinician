package com.android.doral;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.DatePicker;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.doral.Utils.MyPref;
import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.Utils.StringUtils;
import com.android.doral.adapter.CompanyDetailAdapter;
import com.android.doral.adapter.EmergencyDetailAdapter;
import com.android.doral.adapter.NurseLanguageDetailAdapter;
import com.android.doral.adapter.NurseSchoolDetailAdapter;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityCompanyDetailsBinding;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.CompanyHistoryModel;
import com.android.doral.retrofit.model.EmergencyDataModel;
import com.android.doral.retrofit.model.NurseEducationHistoryModel;
import com.android.doral.retrofit.model.NurseLanguageModel;
import com.android.doral.retrofit.model.UserModel;
import com.android.doral.retrofit.model.applicationdetails.ApplicationDetailsModel;
import com.android.doral.retrofit.model.applicationdetails.EmployerDetail;
import com.android.doral.retrofit.model.applicationdetails.Position;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CompanyDetailsActivity extends BaseActivity implements BaseViewInterface {


    ActivityCompanyDetailsBinding binding;
    BasePresenterInterface presenterInterface;
    ArrayList<CompanyHistoryModel> mCompanyHistoryModel = new ArrayList<>();
    ArrayList<NurseEducationHistoryModel> mNurseEducationHistoryModel = new ArrayList<>();
    ArrayList<NurseLanguageModel> mNurseLanguageModel = new ArrayList<>();
    String date = "";
    String month = "", day = "";
    int step = 1;
    Boolean isAllowContactToEmployer = false;
    Boolean isCurrentEmployee = true;
    DatePickerDialog datePickerDialog;
    Calendar calendar = Calendar.getInstance();
    int startMonth=0;
    int startDate=0;
    int startYear=0;
    private int selectPos = 1;
    public void getIntentParamsData() {
        if (getIntent().getExtras() != null) {
            ApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), ApplicationDetailsModel.class);

            if (model != null) {
                if (model.getData().getEmployerDetail() != null) {
                    EmployerDetail mEmployerDetail=model.getData().getEmployerDetail();
                    if(mEmployerDetail.getEmployer()!=null && mEmployerDetail.getEmployer().size()>0){
                        mCompanyHistoryModel.addAll(mEmployerDetail.getEmployer());
                        binding.rvEmployer.getAdapter().notifyDataSetChanged();
                        binding.linHistory.setVisibility(View.VISIBLE);
                    }

                    if (mEmployerDetail.getPosition() != null) {
                        Position mEmployerDetailPosition=mEmployerDetail.getPosition();

                        if(mEmployerDetailPosition.getDate()!=null && mEmployerDetailPosition.getDate()!=""){
                            binding.tvDate.setText(mEmployerDetailPosition.getDate());
                             String[] startDateData = mEmployerDetailPosition.getDate().split("/");
                             startMonth = Integer.parseInt(startDateData[0]);
                             startDate = Integer.parseInt(startDateData[1]);
                             startYear = Integer.parseInt(startDateData[2]);
                        }
                        isAllowContactToEmployer=mEmployerDetailPosition.getAllowContactToEmployer();
                        isCurrentEmployee=mEmployerDetailPosition.getCurrentEmployee();
                        binding.rbYes.setChecked(mEmployerDetailPosition.getCurrentEmployee() ? true :false);
                        binding.rbNo.setChecked(mEmployerDetailPosition.getCurrentEmployee() ? false :true);
                        binding.rbYesEmployer.setChecked(mEmployerDetailPosition.getAllowContactToEmployer() ? true :false);
                        binding.rbNoEmployer.setChecked(mEmployerDetailPosition.getAllowContactToEmployer() ? false :true);
                    }
                }
                if(model.getData().getEducationDetail()!=null ){

                    mNurseEducationHistoryModel.addAll(model.getData().getEducationDetail());
                    binding.rvEducationDetail.getAdapter().notifyDataSetChanged();

                }
                if(model.getData().getLanguageDetail()!=null ){
                    mNurseLanguageModel.addAll(model.getData().getLanguageDetail().getLanguage());
                    binding.rvLanguage.getAdapter().notifyDataSetChanged();
                  //  binding.edtSkill.setText(model.getData().getLanguageDetail().getSkill());
                    binding.edtSkill.getSelectedItem().toString();
                }
            }

        }
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCompanyDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setStatusBarColor(R.color.colorPrimary);
        presenterInterface = new Presenter(this);
        setCompanyHistoryAdapter();
        setSchoolHistoryAdapter();
        setLanguageyAdapter();
        setClickListener();
        getIntentParamsData();
        binding.tvNext.setText("Next");
        binding.toolbar.tvTitle.setText("Employer Details");
        binding.toolbar.imgBack.setVisibility(View.VISIBLE);
        UserModel model = new MyPref(CompanyDetailsActivity.this).getUserData();
        binding.edtPosition.setText(model.getDesignation_id().equals("1") ? "Nurse" : "RN");
        binding.edtPosition.setEnabled(false);
        binding.rbNo.setChecked(false);
        binding.rbYes.setChecked(false);
        binding.toolbar.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkStep();

            }
        });
        binding.tvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (step == 3) {
                    step = 1;
                    setPreviousStep();
                } else if (step == 2) {
                    step = 0;
                    setPreviousStep();
                }
            }
        });
        binding.rbYesEmployer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isAllowContactToEmployer = true;
                    binding.addEmployer.setVisibility(View.VISIBLE);
                    binding.addEmployer.setText("Add Employer");
                }
            }
        });
        binding.rbNoEmployer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isAllowContactToEmployer = false;
                    binding.addEmployer.setVisibility(View.VISIBLE);
                    binding.addEmployer.setText("Add Previous Employer");

                }

            }
        });

        binding.rbYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                    isCurrentEmployee = false;
                   // binding.employee.setVisibility(View.GONE);


                binding.addEmployer.setVisibility(View.VISIBLE);
                binding.addEmployer.setText("Add Previous Employer");

            }
        });
        binding.rbNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                    isCurrentEmployee = false;
                binding.addEmployer.setVisibility(View.VISIBLE);
                binding.addEmployer.setText("Add Employer");


            }
        });

//        binding.rbYes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                binding.employee.setVisibility(View.VISIBLE);
//            }
//        });
//
//        binding.rbNo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                binding.employee.setVisibility(View.GONE);
//                binding.addEmployer.setVisibility(View.GONE);
//            }
//        });

        binding.edtSkill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (binding.edtSkill.getSelectedItem().toString().equalsIgnoreCase("Other")) {
                    binding.llOtherSkill.setVisibility(View.VISIBLE);
                } else {
                    binding.llOtherSkill.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    public  void checkStep(){
        if(step==1){


            HashMap<String, Object> param = new HashMap<>();
            HashMap<String, Object> param_detail = new HashMap<>();
            HashMap<String, Object> employer_detail = new HashMap<>();
            HashMap<String, Object> position = new HashMap<>();
            position.put("position", binding.edtPosition.getText().toString());
            position.put("date", binding.tvDate.getText().toString());
            HashMap<String, Object> employer = new HashMap<>();
            position.put("isAllowContactToEmployer", isAllowContactToEmployer);
            position.put("isCurrentEmployee", isCurrentEmployee);
            employer_detail.put("employer", mCompanyHistoryModel);
            employer_detail.put("position", position);
            param_detail.put("key", "employer_detail");
            param_detail.put("employer_detail", employer_detail);

            presenterInterface.callAPI(CompanyDetailsActivity.this, null, param_detail, ApiCallInterface.STORE_APPLICANT_DETAIL);

        }
        else if(step==2){
//            step=3;
//            binding.tvPrevious.setVisibility(View.VISIBLE);
//            binding.tvNext.setText("Submit");
//            binding.addEducation.setVisibility(View.GONE);
//            binding.addLanguage.setVisibility(View.VISIBLE);
//            binding.linEducation.setVisibility(View.GONE);
//            binding.linLanguage.setVisibility(View.VISIBLE);
//            binding.linSkill.setVisibility(View.VISIBLE);
//            binding.linPosition.setVisibility(View.GONE);
            HashMap<String,Object> param_detail=new HashMap<>();
            param_detail.put("key","education_detail");
            param_detail.put("education_detail",mNurseEducationHistoryModel);
            presenterInterface.callAPI(CompanyDetailsActivity.this,null,param_detail, ApiCallInterface.STORE_APPLICANT_DETAIL);
        }
        else if(step==3) {

            if (binding.edtSkill.getSelectedItem().toString().equalsIgnoreCase("other") && !StringUtils.isNotEmpty(binding.edtOtherSkill.getText().toString())) {
                errorMessage(binding.getRoot(), "Please enter other skill");
                return;
            }

            HashMap<String, Object> param_detail = new HashMap<>();
            param_detail.put("key", "language_detail");
            HashMap<String, Object> language_detail = new HashMap<>();
            language_detail.put("language", mNurseLanguageModel);
            language_detail.put("skill",binding.edtSkill.getSelectedItem().toString().equalsIgnoreCase("other") ? binding.edtOtherSkill.getText().toString() : binding.edtSkill.getSelectedItem().toString());
            param_detail.put("language_detail", language_detail);

            presenterInterface.callAPI(CompanyDetailsActivity.this, null, param_detail, ApiCallInterface.STORE_APPLICANT_DETAIL);

        } else {
            step = 1;
            binding.tvNext.setText("Next");
            binding.tvPrevious.setVisibility(View.GONE);
            binding.addEducation.setVisibility(View.GONE);
            binding.addLanguage.setVisibility(View.GONE);
            binding.linEducation.setVisibility(View.GONE);
            binding.linLanguage.setVisibility(View.GONE);
            binding.linPosition.setVisibility(View.VISIBLE);
        }

    }

    private boolean isValidate() {



        return false;
    }

    public  void setPreviousStep(){
        if(step==1){
            step=2;
            binding.tvNext.setText("Submit");
            binding.tvPrevious.setVisibility(View.VISIBLE);
            binding.addEducation.setVisibility(View.VISIBLE);
            binding.addLanguage.setVisibility(View.GONE);
            binding.linEducation.setVisibility(View.VISIBLE);
            binding.linLanguage.setVisibility(View.GONE);
            binding.linPosition.setVisibility(View.GONE);
            binding.linSkill.setVisibility(View.GONE);
            binding.toolbar.tvTitle.setText("Education Details");



        }
        else if(step==2){
           // step=3;
            binding.tvPrevious.setVisibility(View.GONE);
            binding.tvNext.setText("Submit");
            binding.addEducation.setVisibility(View.GONE);
            binding.addLanguage.setVisibility(View.GONE);
            binding.linEducation.setVisibility(View.GONE);
//            binding.linLanguage.setVisibility(View.VISIBLE);
//            binding.linSkill.setVisibility(View.VISIBLE);
            binding.linPosition.setVisibility(View.GONE);
        }

        else{
            step=1;
            binding.tvNext.setText("Next");
            binding.tvPrevious.setVisibility(View.GONE);
            binding.addEducation.setVisibility(View.GONE);
            binding.addLanguage.setVisibility(View.GONE);
            binding.linEducation.setVisibility(View.GONE);
            binding.linLanguage.setVisibility(View.GONE);
            binding.linPosition.setVisibility(View.VISIBLE);
        }

    }

    private void setClickListener() {

        binding.addEmployer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(CompanyDetailsActivity.this, EmploymentRecordActivity.class), 101);
            }
        });
        binding.addEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(CompanyDetailsActivity.this, EmploymentSchoolRecordActivity.class), 102);
            }
        });

        binding.addLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(CompanyDetailsActivity.this, NurseLanguageActivity.class), 103);
            }
        });
        binding.tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.YEAR, -18);
                if(startDate!=0){
                    calendar.set(Calendar.YEAR, startYear);
                    calendar.set(Calendar.MONTH, startMonth);
                    calendar.set(Calendar.DAY_OF_MONTH, startDate);
                }
                datePickerDialog = new DatePickerDialog(CompanyDetailsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                        // date = i + "/" + (i1 + 1) + "/" + i2;
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        startDate=dayOfMonth;
                        startMonth=monthOfYear;
                        startYear=year;
                        if (monthOfYear < 10) {

                            month = "0" + (monthOfYear + 1);

                        } else {

                            month = "" + (monthOfYear + 1);

                        }
                        if (dayOfMonth < 10) {

                            day = "0" + dayOfMonth;

                        } else {

                            day = "" + dayOfMonth;

                        }
                        date = month + "/" + day + "/" + year;
                        binding.tvDate.setText(date);

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

    }

    @Override
    public void retry(int pos) {

    }

    @Override
    public void onError(String errorMsg, int requestCode, int resultCode) {

    }

    @Override
    public void onSuccess(Object success, int requestCode, int resultCode) {

        if(step==1){
            step=2;
            binding.tvNext.setText("Submit");
            binding.tvPrevious.setVisibility(View.VISIBLE);
            binding.addEducation.setVisibility(View.VISIBLE);
            binding.addLanguage.setVisibility(View.GONE);
            binding.linEducation.setVisibility(View.VISIBLE);
            binding.linLanguage.setVisibility(View.GONE);
            binding.linPosition.setVisibility(View.GONE);
            binding.linSkill.setVisibility(View.GONE);
            binding.toolbar.tvTitle.setText("Education Details");


        }
        else if(step==2){
//          //  step=3;
//            binding.tvPrevious.setVisibility(View.GONE);
//            binding.tvNext.setText("Submit");
//            binding.addEducation.setVisibility(View.GONE);
//            binding.addLanguage.setVisibility(View.GONE);
//            binding.linEducation.setVisibility(View.GONE);
////            binding.linLanguage.setVisibility(View.VISIBLE);
////            binding.linSkill.setVisibility(View.VISIBLE);
//            binding.linPosition.setVisibility(View.GONE);
//            if(mNurseLanguageModel.size()>0){
//                binding.linLanguage.setVisibility(View.GONE);
//            }
            finish();
        }
        else{
           finish();
        }
    }

    private void setCompanyHistoryAdapter() {
        CompanyDetailAdapter nurseReferenceAdapter = new CompanyDetailAdapter(this, mCompanyHistoryModel);
        binding.rvEmployer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvEmployer.setAdapter(nurseReferenceAdapter);

        nurseReferenceAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, int which, Object object) {
                selectPos = position;
                CompanyHistoryModel emergencyDataModel = (CompanyHistoryModel) object;
                startActivityForResult(new Intent(CompanyDetailsActivity.this, EmploymentRecordActivity.class)
                        .putExtra("name", emergencyDataModel.getCompanyName())
                        .putExtra("zipcode", emergencyDataModel.getBuilding())
                        .putExtra("phone", emergencyDataModel.getAddress())
                        .putExtra("address", emergencyDataModel.getPhoneNo())
                        .putExtra("apt", emergencyDataModel.getBuilding())
                        .putExtra("state", emergencyDataModel.getCity_id())
                        .putExtra("city", emergencyDataModel.getZipcode())
                        .putExtra("address2",emergencyDataModel.getAddress_line_2()), 503);
            }
        });
    }

    private void setSchoolHistoryAdapter() {
        NurseSchoolDetailAdapter nurseReferenceAdapter = new NurseSchoolDetailAdapter(this, mNurseEducationHistoryModel);
        binding.rvEducationDetail.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvEducationDetail.setAdapter(nurseReferenceAdapter);

        nurseReferenceAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, int which, Object object) {
                selectPos = position;
                NurseEducationHistoryModel emergencyDataModel = (NurseEducationHistoryModel) object;
                String no = String.valueOf(emergencyDataModel.getIsGraduate().equalsIgnoreCase("No"));
                startActivityForResult(new Intent(CompanyDetailsActivity.this, EmploymentSchoolRecordActivity.class)
                        .putExtra("name", emergencyDataModel.getName())
                        .putExtra("address", emergencyDataModel.getAddress_line_2())
                        .putExtra("state", emergencyDataModel.getCity_id())
                        .putExtra("city", emergencyDataModel.getZipcode())
                        .putExtra("zipcode", emergencyDataModel.getBuilding())
                                .putExtra("web",emergencyDataModel.getWebsite())
                        .putExtra("dishank",emergencyDataModel.getIsGraduate().equalsIgnoreCase("Yes"))
                                .putExtra("123",no)
                                .putExtra("year",emergencyDataModel.getYear())
                        .putExtra("degree",emergencyDataModel.getDegree())
                        ,504);
            }
        });
    }

    private void setLanguageyAdapter() {
        binding.rvLanguage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvLanguage.setAdapter(new NurseLanguageDetailAdapter(this, mNurseLanguageModel));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 101) {
                CompanyHistoryModel model = new Gson().fromJson(data.getStringExtra("data"), CompanyHistoryModel.class);
                mCompanyHistoryModel.add(model);
                binding.rvEmployer.getAdapter().notifyDataSetChanged();
                binding.linHistory.setVisibility(View.VISIBLE);
            }
            if (requestCode == 102) {
                NurseEducationHistoryModel model = new Gson().fromJson(data.getStringExtra("data"), NurseEducationHistoryModel.class);
                mNurseEducationHistoryModel.add(model);
                binding.rvEducationDetail.getAdapter().notifyDataSetChanged();
                binding.linEducation.setVisibility(View.VISIBLE);
            }
            if (requestCode == 103) {
                NurseLanguageModel model = new Gson().fromJson(data.getStringExtra("data"), NurseLanguageModel.class);
                mNurseLanguageModel.add(model);
                binding.rvLanguage.getAdapter().notifyDataSetChanged();
                binding.rvLanguage.setVisibility(View.VISIBLE);
            }
            if (requestCode == 503) {
                CompanyHistoryModel model = new Gson().fromJson(data.getStringExtra("data"), CompanyHistoryModel.class);
                mCompanyHistoryModel.set(selectPos, model);
                binding.rvEmployer.getAdapter().notifyDataSetChanged();
            }

            if (requestCode == 504) {
                NurseEducationHistoryModel model = new Gson().fromJson(data.getStringExtra("data"), NurseEducationHistoryModel.class);
                mNurseEducationHistoryModel.set(selectPos, model);
                binding.rvEducationDetail.getAdapter().notifyDataSetChanged();
            }

        }
    }
}
