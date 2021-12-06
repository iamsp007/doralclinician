package com.android.doral;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.doral.Utils.DateUtils;
import com.android.doral.Utils.PhoneTextFormatter;
import com.android.doral.Utils.StringUtils;
import com.android.doral.Utils.Utility;
import com.android.doral.base.BaseActivity;
import com.android.doral.databinding.ActivitySignupBinding;
import com.android.doral.dialog.CustomAlertDialog;
import com.android.doral.register.Presenter;
import com.android.doral.register.BasePresenterInterface;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SignupActivity extends BaseActivity implements View.OnClickListener, RegisterViewInterface {
    ActivitySignupBinding binding;
    BasePresenterInterface presenterInterface;
    String birth_date = "",email;
    DatePickerDialog datePickerDialog;
    private int passwordNotVisible=1;

    int genderValue=0;
    private final SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private String service_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setStatusBarColor(R.color.colorPrimary);
        presenterInterface = new Presenter(this);
        presenterInterface.sendRequest(context, null, null, ApiCallInterface.DESIGNATION);

        binding.activitySignUpEtPhone.addTextChangedListener(new PhoneTextFormatter(binding.activitySignUpEtPhone, "+1 (###) ###-####"));
        binding.activitySignUpTvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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
                datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        /*birth_date = i + "/" + (i1 + 1) + "/" + i2;
                        binding.activitySignUpTvDOB.setText(birth_date);*/

                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        birth_date = dateformat.format(calendar.getTime());
                        binding.activitySignUpTvDOB.setText(DateUtils.changeDateFormat(DateUtils.DATE_FORMATE_8, DateUtils.DATE_FORMATE_1, dateformat.format(calendar.getTime()), false));

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                Calendar calendar1 = Calendar.getInstance();
                calendar1.add(Calendar.YEAR, -18);
                datePickerDialog.getDatePicker().setMaxDate(calendar1.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        ImageView imagepass = (ImageView) findViewById(R.id.imagepassword);
        imagepass.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if (passwordNotVisible == 1) {
                    binding.activitySignUpEtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                    imagepass.setImageResource(R.drawable.ic_baseline_visibility_24);
                    passwordNotVisible = 0;
                } else {

                    binding.activitySignUpEtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    imagepass.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                    passwordNotVisible = 1;
                }

                binding.activitySignUpEtPassword.setSelection(binding.activitySignUpEtPassword.length());

            }

        });


        ImageView imagepass1 = (ImageView) findViewById(R.id.imagepassword1);
        imagepass1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if (passwordNotVisible == 1) {
                    binding.activitySignUpEtConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                    imagepass1.setImageResource(R.drawable.ic_baseline_visibility_24);
                    passwordNotVisible = 0;
                } else {

                    binding.activitySignUpEtConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    imagepass1.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                    passwordNotVisible = 1;
                }

                binding.activitySignUpEtConfirmPassword.setSelection(binding.activitySignUpEtConfirmPassword.length());


            }

        });


        binding.activitySignUpBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.cbDoralConnect.isChecked()) {
                    service_id = "1";
                }
                if (binding.cbRoadl.isChecked()) {
                    service_id = "2";
                }
                if (binding.cbRoadl.isChecked() && binding.cbDoralConnect.isChecked()) {
                    service_id = "3";
                }
                if (isValidate()) {
                    //sendOtp();

                    FirebaseMessaging.getInstance().getToken()
                            .addOnCompleteListener(new OnCompleteListener<String>() {
                                @Override
                                public void onComplete(@NonNull Task<String> task) {
                                    if (!task.isSuccessful()) {
                                        Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                                        return;
                                    }
                                    // Log and toast
                                    UserModel userModel = new UserModel();
                                    email = binding.activitySignUpEtEmail.getText().toString();
                                    userModel.setFirst_name(binding.activitySignUpEtFirstName.getText().toString());
                                    userModel.setLast_name(binding.activitySignUpEtLastName.getText().toString());
                                    userModel.setEmail(binding.activitySignUpEtEmail.getText().toString());
                                    userModel.setPassword(binding.activitySignUpEtPassword.getText().toString());
                                    userModel.setPhone(getOriginalPhoneNumber(binding.activitySignUpEtPhone));
                                    userModel.setGender(""+genderValue);
                                    // userModel.setGender(binding.activitySignUpSpGender.getSelectedItem().toString().toLowerCase());
                                    userModel.setDob(birth_date);
                                    userModel.setDevice_token(task.getResult());
                                    userModel.setDevice_type("1");
                                    userModel.setDesignation_id(((DesignationModel) binding.spinnerDesignation.getSelectedItem()).getId());
                                    userModel.setType("clinician");

                                    if (binding.cbDoralConnect.isChecked()) {
                                        service_id = "1";
                                    }
                                    if (binding.cbRoadl.isChecked()) {
                                        service_id = "2";
                                    }
                                    if (binding.cbRoadl.isChecked() && binding.cbDoralConnect.isChecked()) {
                                        service_id = "3";
                                    }
                                    userModel.setService_id(service_id);


                                    presenterInterface.sendRequest(context, new Gson().toJson(userModel), null, ApiCallInterface.REGISTER);
                                }
                            });


                }
            }
        });
        binding.activitySignUpIvPrivacyPolicyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, PrivacyPolicyActivity.class));
            }
        });
        binding.activitySignUpIvTermsOfUseInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, TermsofuseActivity.class));
            }
        });

        binding.tvSignUpGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.activitySignUpSpGender.performClick();
            }
        });

        binding.tvPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.spinnerDesignation.performClick();

            }
        });

        binding.tvPosition1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.spinnerDesignation1.performClick();
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

        binding.spinnerDesignation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0) {

                    binding.tvPosition.setText(binding.spinnerDesignation.getSelectedItem().toString());

                }

                if (i==6){
                   binding.spc.setVisibility(View.VISIBLE);
                }else{
                    binding.spc.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spinnerDesignation1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0) {

                    binding.tvPosition1.setText(binding.spinnerDesignation1.getSelectedItem().toString());

                }

//                if (i==6){
//                    binding.spc.setVisibility(View.VISIBLE);
//                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    public void onClick(View view) {

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
        } else if (binding.spinnerDesignation.getSelectedItemPosition() == 0) {
            errorMessage(binding.getRoot(), "Please select designation");
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
        } else if (binding.activitySignUpEtPassword.getText().toString().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "Please enter password");
            return false;
        }else if (!StringUtils.isNotEmpty(service_id)) {
            errorMessage(binding.getRoot(), "please select service type");
            return false;
        } else if (binding.activitySignUpEtConfirmPassword.getText().toString().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "Please enter confirm password");
            return false;
        } else if (!binding.activitySignUpEtPassword.getText().toString().equals(binding.activitySignUpEtConfirmPassword.getText().toString())) {
            errorMessage(binding.getRoot(), "password and confirm password does not matched");
            return false;
        } else if (!binding.activitySignUpSwitchTermsOfUse.isChecked()) {
            errorMessage(binding.getRoot(), "please agree term and conditions");
            return false;
        } else if (!binding.activitySignUpSwitchPrivacyPolicy.isChecked()) {
            errorMessage(binding.getRoot(), "please agree privacy policy");
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
        if (requestCode == ApiCallInterface.REGISTER) {
            BaseModel baseModel = (BaseModel) success;
            if (baseModel.isStatus().equals("true")) {
                new CustomAlertDialog(context, baseModel.getMessage(), "Confirm your email address", "OK", "", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //sendOtp();
                        Intent intent = new Intent(SignupActivity.this, Passcode.class);
                      //  intent.putExtra("email", email);
                      //  Log.e("email",email);
                        startActivity(intent);
                        finish();
                    }
                }).show();


            } else {
                errorMessage(binding.getRoot(), baseModel.getMessage());
            }
        }
        if (requestCode == ApiCallInterface.DESIGNATION) {
            DesignationModel baseModel = (DesignationModel) success;
            if (baseModel.isStatus().equals("true")) {
                setSpinner(binding.spinnerDesignation, baseModel.getData().getDesignation());
            } else {
                errorMessage(binding.getRoot(), baseModel.getMessage());
            }
        }
        if (requestCode == ApiCallInterface.SEND_OTP) {
            OtpResponseModel baseModel = (OtpResponseModel) success;
            if (baseModel.isStatus().equals("true")) {
                startActivityForResult(new Intent(SignupActivity.this, OtpVerificationActivity.class).putExtra("mobile", binding.activitySignUpEtPhone.getText().toString()).putExtra("email", binding.activitySignUpEtEmail.getText().toString()).putExtra("data", baseModel), 2003);
                finish();
            } else {
                errorMessage(binding.getRoot(), baseModel.getMessage());
            }
        }
    }

    private void setSpinner(Spinner spinner, List<DesignationModel> list) {
        List<DesignationModel> listDes=new ArrayList<>();
        listDes.clear();
        DesignationModel spinnerModel = new DesignationModel("select");
        listDes.add(spinnerModel);
        for (int i = 0; i <list.size() ; i++) {

            if (list.get(i).getName().equals("Gynecologist (GYN)")){



            }else if (list.get(i).getName().equals("Gastrointestinal Specialist (GI)")){

            }else {

                listDes.add(list.get(i));

            }

        }

        ArrayAdapter<DesignationModel> arrayAdapter = new ArrayAdapter<DesignationModel>(context, R.layout.spinner_row, listDes) {
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
            userModel.setPassword(binding.activitySignUpEtPassword.getText().toString());
            userModel.setPhone(binding.activitySignUpEtPhone.getText().toString());
            userModel.setGender(""+genderValue);
           // userModel.setGender(binding.activitySignUpSpGender.getSelectedItem().toString().toLowerCase());
            userModel.setDob(birth_date);
            // userModel.setType(((DesignationModel) binding.spinnerDesignation.getSelectedItem()).getName());
            userModel.setType("clinician");
            presenterInterface.sendRequest(context, new Gson().toJson(userModel), null, ApiCallInterface.REGISTER);
        }
    }

    private void sendOtp() {
        HashMap<String, String> map = new HashMap<>();
        map.put("email", binding.activitySignUpEtEmail.getText().toString().trim());
        map.put("phone", binding.activitySignUpEtPhone.getText().toString().trim());
        presenterInterface.sendRequest(context, "", map, ApiCallInterface.SEND_OTP);

    }
}