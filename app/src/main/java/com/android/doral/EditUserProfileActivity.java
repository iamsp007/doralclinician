package com.android.doral;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.doral.Utils.DateUtils;
import com.android.doral.Utils.MyPref;
import com.android.doral.Utils.PhoneTextFormatter;
import com.android.doral.Utils.Utility;
import com.android.doral.base.BaseActivity;
import com.android.doral.databinding.ActivityEditUserProfileBinding;
import com.android.doral.databinding.ActivitySignupBinding;
import com.android.doral.dialog.CustomAlertDialog;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.register.RegisterViewInterface;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.BaseModel;
import com.android.doral.retrofit.model.DesignationModel;
import com.android.doral.retrofit.model.OtpResponseModel;
import com.android.doral.retrofit.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class EditUserProfileActivity extends BaseActivity implements View.OnClickListener, RegisterViewInterface {
    ActivityEditUserProfileBinding binding;
    BasePresenterInterface presenterInterface;
    String birth_date = "";
    DatePickerDialog datePickerDialog;
    int startMonth=0;
    int startDate=0;
    int startYear=0;
    int genderValue=0;
    private final SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setStatusBarColor(R.color.colorPrimary);
        presenterInterface = new Presenter(this);
        presenterInterface.sendRequest(context, null, null, ApiCallInterface.DESIGNATION);

        binding.activitySignUpEtPhone.addTextChangedListener(new PhoneTextFormatter(binding.activitySignUpEtPhone, "+1 (###) ###-####"));

        binding.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.activitySignUpTvDOB.setOnClickListener(new View.OnClickListener() {
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
                        birth_date = dateformat.format(calendar.getTime());
                        binding.activitySignUpTvDOB.setText(DateUtils.changeDateFormat(DateUtils.DATE_FORMATE_8, DateUtils.DATE_FORMATE_1, dateformat.format(calendar.getTime()), false));

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                Calendar calendar1 = Calendar.getInstance();
//                calendar1.add(Calendar.YEAR, -18);
                datePickerDialog.getDatePicker().setMaxDate(calendar1.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        binding.activitySignUpBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidate()) {
                    //sendOtp();
                    HashMap<String,Object>params=new HashMap<>();
                    params.put("first_name",binding.activitySignUpEtFirstName.getText().toString());
                    params.put("last_name",binding.activitySignUpEtLastName.getText().toString());
                    params.put("email",binding.activitySignUpEtEmail.getText().toString());
                    params.put("phone",binding.activitySignUpEtPhone.getText().toString());
                    params.put("dob",binding.activitySignUpTvDOB.getText().toString());
                    params.put("gender",genderValue);


                    presenterInterface.callAPI(context,null, params, ApiCallInterface.Edit_USER_PROFILE,"");

                }
            }
        });

        binding.tvSignUpGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.activitySignUpSpGender.performClick();
            }
        });
        binding.activitySignUpSpGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0) {

                    genderValue=i;
                    binding.tvSignUpGender.setText(binding.activitySignUpSpGender.getSelectedItem().toString());

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        setData();
    }


    public  void setData(){

        UserModel model=new MyPref(this).getUserData();
        binding.activitySignUpEtFirstName.setText(model.getFirst_name());
        binding.activitySignUpEtLastName.setText(model.getLast_name());
        binding.activitySignUpEtEmail.setText(model.getEmail());
        binding.activitySignUpEtPhone.setText(model.getPhone());
        binding.activitySignUpTvDOB.setText(model.getDob().replace("\\", ""));
        String[] startDateData = model.getDob().replace("\\", "").split("/");
        startMonth = Integer.parseInt(startDateData[0]);
        startDate = Integer.parseInt(startDateData[1]);
        startYear = Integer.parseInt(startDateData[2]);
        birth_date=model.getDob().replace("\\", "");
        binding.activitySignUpSpGender.setSelection(Integer.parseInt(model.getGender()));
    }
    private String getOriginalPhoneNumber(EditText editText) {
        String removeSpecial = "";
        String[] splitPhoneNumber = editText.getText().toString().split(" ");
        removeSpecial = splitPhoneNumber[1] + "" + splitPhoneNumber[2];
        return removeSpecial.replaceAll("[^\\d]", "");
    }

    private boolean isValidate() {

        if (binding.activitySignUpEtFirstName.getText().toString().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "Please enter first name");
            return false;
        } else if (binding.activitySignUpEtLastName.getText().toString().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "Please enter last name");
            return false;
        } else if (binding.activitySignUpSpGender.getSelectedItemPosition() == 0) {
            errorMessage(binding.getRoot(), "Please select gender");
            return false;
        } else if (birth_date.equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "Please select birthdate");
            return false;
        } else if (binding.activitySignUpEtEmail.getText().toString().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "Please enter email address");
            return false;
        } else if (!Utility.isValidEmail(binding.activitySignUpEtEmail.getText().toString())) {
            errorMessage(binding.getRoot(), "Please enter valid email address");
            return false;
        } else if (binding.activitySignUpEtPhone.getText().toString().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "Please enter phone number");
            return false;
        } else if (binding.activitySignUpEtPhone.getText().length() < 17) {
            errorMessage(binding.getRoot(), "please enter valid Phone number");
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
        if (requestCode == ApiCallInterface.Edit_USER_PROFILE) {
            BaseModel baseModel = (BaseModel) success;
            if (baseModel.isStatus().equals("true")) {
                new CustomAlertDialog(context, baseModel.getMessage(), "Profile updated successfully", "OK", "", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        setResult(RESULT_OK,new Intent());
                        finish();
                    }
                }).show();


            } else {
                errorMessage(binding.getRoot(), baseModel.getMessage());
            }
        }

        if (requestCode == ApiCallInterface.SEND_OTP) {
            OtpResponseModel baseModel = (OtpResponseModel) success;
            if (baseModel.isStatus().equals("true")) {
                startActivityForResult(new Intent(EditUserProfileActivity.this, OtpVerificationActivity.class).putExtra("mobile", binding.activitySignUpEtPhone.getText().toString()).putExtra("email", binding.activitySignUpEtEmail.getText().toString()).putExtra("data", baseModel), 2003);
                finish();
            } else {
                errorMessage(binding.getRoot(), baseModel.getMessage());
            }
        }
    }

    private void setSpinner(Spinner spinner, List<DesignationModel> list) {

        DesignationModel spinnerModel = new DesignationModel("select");
        list.add(0, spinnerModel);

        ArrayAdapter<DesignationModel> arrayAdapter = new ArrayAdapter<DesignationModel>(context, R.layout.spinner_row, list) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View spinnerView = super.getDropDownView(position, convertView, parent);
                TextView spinnerTextV = (TextView) spinnerView;
                if (position == 0) {
                    spinnerTextV.setTextColor(Color.parseColor("#a7a7a6"));
                } else {
                    spinnerTextV.setTextColor(Color.parseColor("#b2000000"));
                }
                return spinnerView;
            }
        };


        arrayAdapter.setDropDownViewResource(R.layout.spinner_drop_down);

        spinner.setAdapter(arrayAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2003 && resultCode == RESULT_OK) {
            UserModel userModel = new UserModel();
            userModel.setFirst_name(binding.activitySignUpEtFirstName.getText().toString());
            userModel.setLast_name(binding.activitySignUpEtLastName.getText().toString());
            userModel.setEmail(binding.activitySignUpEtEmail.getText().toString());
            userModel.setPhone(binding.activitySignUpEtPhone.getText().toString());
            userModel.setGender(""+genderValue);
           // userModel.setGender(binding.activitySignUpSpGender.getSelectedItem().toString().toLowerCase());
            userModel.setDob(birth_date);
            // userModel.setType(((DesignationModel) binding.spinnerDesignation.getSelectedItem()).getName());
            userModel.setType("clinician");
            presenterInterface.sendRequest(context, new Gson().toJson(userModel), null, ApiCallInterface.Edit_USER_PROFILE);
        }
    }


    @Override
    public void onClick(View v) {

    }
}