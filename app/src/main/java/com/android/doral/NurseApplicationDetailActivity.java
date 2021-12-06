package com.android.doral;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.doral.Notification.Model.Datum2;
import com.android.doral.Notification.NotificationAdapter;
import com.android.doral.Utils.MyPref;
import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.Utils.PhoneTextFormatter;
import com.android.doral.Utils.StringUtils;
import com.android.doral.Utils.Utility;
import com.android.doral.adapter.NurseReferenceAdapter;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityNurseApplicantDetailsBinding;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.CityModel;
import com.android.doral.retrofit.model.EmergencyDataModel;
import com.android.doral.retrofit.model.StateModel;
import com.android.doral.retrofit.model.UserApplicationDetails.UserApplicationDetailsModel;
import com.android.doral.retrofit.model.UserModel;
import com.android.doral.retrofit.model.applicationdetails.AddressDetail;
import com.android.doral.retrofit.model.applicationdetails.ApplicationDetailsModel;
import com.android.doral.retrofit.model.applicationdetails.Prior;
import com.android.doral.retrofit.model.applicationdetails.SecurityDetail;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class NurseApplicationDetailActivity extends BaseActivity implements BaseViewInterface, View.OnClickListener {


    ActivityNurseApplicantDetailsBinding binding;
    BasePresenterInterface presenterInterface;
    private String state_code;
    private String city_code;
    private String prior_state_code;
    private String prior_city_code;
    private List<CityModel> cityList;
    List<StateModel> stateModelList;
    private int step = 1;
    List<EmergencyDataModel> mEmergencyDataModel = new ArrayList<>();
    List<EmergencyDataModel> mEmergencyDataModelUser = new ArrayList<>();
    private String yy = "";
    String tt;
    int genderValue = 0;
    private int selectPos = -1;
    NurseReferenceAdapter mAdapter;
    private static final int PLACE_REQUEST = 997;
    private static final int PLACE_REQUEST_PRIOR = 998;
    String number,num;

    String date = "";
    DatePickerDialog datePickerDialog;
    Calendar calendar = Calendar.getInstance();
    String month="",day="";
    String isCitizen = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNurseApplicantDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setStatusBarColor(R.color.colorPrimary);
        presenterInterface = new Presenter(this);
        if (!Places.isInitialized()) {
            Places.initialize(NurseApplicationDetailActivity.this, getString(R.string.google_api_key));
        }
        binding.toolbar.tvTitle.setText("Applicant Detail");
        binding.toolbar.imgBack.setVisibility(View.VISIBLE);
        binding.toolbar.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        presenterInterface = new Presenter(this);
        presenterInterface.sendRequest(this, null, null, ApiCallInterface.STATES);
        presenterInterface.sendRequest(this, null, null, ApiCallInterface.CITY);
        binding.tvState.setOnClickListener(this);
        binding.tvCity.setOnClickListener(this);
        binding.rlAddress.setOnClickListener(this);
        binding.rlPriorAddress.setOnClickListener(this);
        binding.addReference.setOnClickListener(this);
        binding.tvpriorState.setOnClickListener(this);
        binding.tvPriorCity.setOnClickListener(this);
        binding.edtAddress1.setOnClickListener(this);
        binding.edtPriorAddress1.setOnClickListener(this);
        binding.tvNext.setOnClickListener(this);
        binding.svApplicantDetails.setVisibility(View.VISIBLE);
        binding.tvPrevious.setOnClickListener(this);
        binding.tvPrevious.setVisibility(View.GONE);
        setReferenceAdapter();
        UserModel model = new MyPref(this).getUserData();
        binding.edtFirstName.setText(model.getFirst_name());
        binding.edtLastName.setText(model.getLast_name());
        number = model.getPhone();
        num = number.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
        binding.edtPhoneNumber.setText( "+1"+"\n"+num);
     //   binding.edtPhoneNumber.addTextChangedListener(new PhoneTextFormatter(binding.edtPhoneNumber, "+1 (###) ###-####"));
        binding.edtHomePhone.addTextChangedListener(new PhoneTextFormatter(binding.edtHomePhone, "+1 (###) ###-####"));



        if (!new MyPref(context).getUserData().getDesignation_id().equals("2")) {

            binding.linOtherUser.setVisibility(View.VISIBLE);
            binding.emergencynum.setVisibility(View.INVISIBLE);
            binding.linOtherQuestion.setVisibility(View.VISIBLE);
            binding.edtDate.setOnClickListener(this);
            binding.edtSsnNumber.addTextChangedListener(new PhoneTextFormatter(binding.edtSsnNumber, "###-##-####"));
            binding.edtEmergencyPhone.addTextChangedListener(new PhoneTextFormatter(binding.edtEmergencyPhone, "+1 (###) ###-####"));

            binding.rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {

                    if (binding.rbGroup.getCheckedRadioButtonId() == R.id.rb_yes) {

                        isCitizen = "1";
                        binding.llCard.setVisibility(View.GONE);
                        binding.edtCardNumber.setText("");

                    } else if (binding.rbGroup.getCheckedRadioButtonId() == R.id.rb_no) {

                        binding.llCard.setVisibility(View.VISIBLE);
                       // binding.edtCardNumber.setText("");
                        isCitizen = "0";

                    }
                }
            });

            getIntentParamsDataOtherUser();


        }else {

            getIntentParamsData();
            binding.linOtherUser.setVisibility(View.GONE);
            binding.linOtherQuestion.setVisibility(View.GONE);

        }

        binding.spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    state_code = ((StateModel) binding.spState.getSelectedItem()).getId();
                    binding.tvState.setText(((StateModel) binding.spState.getSelectedItem()).getState());
                    //setCitySpinner(binding.spCity, getCityList(((StateModel) binding.spState.getSelectedItem()).getState_code()));


                    ArrayAdapter<CityModel> adapter = new ArrayAdapter<CityModel>
                            (context, android.R.layout.select_dialog_item, getCityList(((StateModel) binding.spState.getSelectedItem()).getState_code()));
                    binding.tvCity.setThreshold(1); //will start working from first character
                    binding.tvCity.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        binding.spPriorstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    prior_state_code = ((StateModel) binding.spPriorstate.getSelectedItem()).getId();
                    binding.tvpriorState.setText(((StateModel) binding.spPriorstate.getSelectedItem()).getState());
                    //   setCitySpinner(binding.spPriorcity, getCityList(((StateModel) binding.spPriorstate.getSelectedItem()).getState_code()));
                    ArrayAdapter<CityModel> adapter = new ArrayAdapter<CityModel>
                            (context, android.R.layout.select_dialog_item, getCityList(((StateModel) binding.spPriorstate.getSelectedItem()).getState_code()));
                    binding.tvPriorCity.setThreshold(1); //will start working from first character
                    binding.tvPriorCity.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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

                    genderValue = i;
                    binding.tvSignUpGender.setText(binding.activitySignUpSpGender.getSelectedItem().toString());

                }

                if (i == 1) {
                 //   errorMessage(binding.getRoot(), "Please enter prior Address Compulsory");
                } else if (i == 1) {
                    binding.tvSignUpGender.setText(binding.activitySignUpSpGender.getSelectedItem().toString());
                }

//                if (i == 1) {
//                    errorMessage(binding.getRoot(), "Please enter prior Address Compulsory");
//                } else if (i == 1) {
//                    binding.tvSignUpGender.setText(binding.activitySignUpSpGender.getSelectedItem().toString());
//                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.tvSignUpGender1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.activitySignUpSpGender1.performClick();
            }
        });

        binding.activitySignUpSpGender1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0) {

                    genderValue = i;
                    binding.tvSignUpGender1.setText(binding.activitySignUpSpGender1.getSelectedItem().toString());

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        edit();

       /* binding.spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    binding.tvCity.setText(((CityModel) binding.spCity.getSelectedItem()).getCity());
                    city_code = ((CityModel) binding.spCity.getSelectedItem()).getId();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        binding.tvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CityModel cityModel = (CityModel) adapterView.getItemAtPosition(i);
                city_code = cityModel.getId();


            }
        });

       /* binding.spPriorcity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    binding.tvPriorCity.setText(((CityModel) binding.spPriorcity.getSelectedItem()).getCity());
                    prior_city_code = ((CityModel) binding.spPriorcity.getSelectedItem()).getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });*/


        binding.tvPriorCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CityModel cityModel = (CityModel) adapterView.getItemAtPosition(i);
                prior_city_code = cityModel.getId();


            }
        });
    }

//    private boolean edit() {
//        binding.spRace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (i == 0) {
//                    errorMessage(binding.getRoot(), "Please enter prior Address Compulsory");
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//        return true;
//
//    }

    @Override
    public void retry(int pos) {

    }

    @Override
    public void onError(String errorMsg, int requestCode, int resultCode) {

    }

    @Override
    public void onSuccess(Object success, int requestCode, int resultCode) {
        if (requestCode == ApiCallInterface.STATES) {
            stateModelList = (List<StateModel>) success;
            if (stateModelList != null) {

                setStatteSpinner(binding.spState, stateModelList);
                setStatteSpinner(binding.spPriorstate, stateModelList);
            }
        }
        if (requestCode == ApiCallInterface.CITY) {

            cityList = (List<CityModel>) success;

        }

        if (requestCode == ApiCallInterface.STORE_APPLICANT_DETAIL) {

            if (step == 1) {

                step = 2;
                binding.tvNext.setText("Submit");
                binding.tvPrevious.setVisibility(View.VISIBLE);
                binding.svApplicantDetails.setVisibility(View.GONE);
                binding.linReference.setVisibility(View.VISIBLE);

                if (mEmergencyDataModel.size()==0){
                    startActivityForResult(new Intent(this, NurseReferenceRecordActivity.class), 101);
                }
            } else {

                //ApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), ApplicationDetailsModel.class);

                if (mEmergencyDataModel.size() >= 2) {

                    finish();

                } else {

                    errorMessage(binding.getRoot(), "Please enter two references");

                }

            }
        }

        if (requestCode == ApiCallInterface.GET_APPLICATION_DETAIL) {


        }

    }

    private List<CityModel> getCityList(String state_code) {
        List<CityModel> modelList = new ArrayList<>();

        if (cityList != null) {
            for (int i = 0; i < cityList.size(); i++) {
                if (cityList.get(i).getState_code().equals(state_code)) {
                    modelList.add(cityList.get(i));
                }
            }
        }
        return modelList;
    }

    private void setCitySpinner(AppCompatSpinner spinner, List<CityModel> list) {

        CityModel spinnerModel = new CityModel("select");
        list.add(0, spinnerModel);

        ArrayAdapter<CityModel> arrayAdapter = new ArrayAdapter<CityModel>(this, R.layout.spinner_row, list) {
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

    private void setStatteSpinner(AppCompatSpinner spinner, List<StateModel> list) {

        StateModel spinnerModel = new StateModel("select");
        list.add(0, spinnerModel);
        ArrayAdapter<StateModel> arrayAdapter = new ArrayAdapter<StateModel>(this, R.layout.spinner_row, list) {
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
    public void onClick(View view) {

        if (view.getId() == R.id.tvState) {
            binding.spState.performClick();
        }
        if (view.getId() == R.id.tvCity) {
            // binding.spCity.performClick();
        }
        if (view.getId() == R.id.edtAddress1) {
            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
            // Start the autocomplete intent.
            Intent intent = new Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.FULLSCREEN, fields)/*.setCountry("IN")*/
                    .build(NurseApplicationDetailActivity.this);
            startActivityForResult(intent, PLACE_REQUEST);
        }
        if (view.getId() == R.id.edt_prior_Address1) {
            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
            // Start the autocomplete intent.
            Intent intent = new Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.FULLSCREEN, fields)/*.setCountry("IN")*/
                    .build(NurseApplicationDetailActivity.this);
            startActivityForResult(intent, PLACE_REQUEST_PRIOR);
        }
        if (view.getId() == R.id.rlAddress) {
            //binding.llPriorAddress.setVisibility(View.GONE);
            if (binding.linAddress.getVisibility() == View.VISIBLE) {
                binding.linAddress.setVisibility(View.GONE);
            }
            else {
                binding.linAddress.setVisibility(View.VISIBLE);
            }
        }
        if (view.getId() == R.id.rlPriorAddress) {
            //binding.linAddress.setVisibility(View.GONE);
            if (binding.llPriorAddress.getVisibility() == View.VISIBLE) {
                binding.llPriorAddress.setVisibility(View.GONE);
            }
            else {
                binding.llPriorAddress.setVisibility(View.VISIBLE);
            }
        }

        if (view.getId() == R.id.tvpriorState) {
            binding.spPriorstate.performClick();
        }
        if (view.getId() == R.id.add_reference) {
            startActivityForResult(new Intent(this, NurseReferenceRecordActivity.class), 101);
        }
        if (view.getId() == R.id.tvPriorCity) {
            binding.spPriorcity.performClick();
        }

        if (view.getId() == R.id.edt_date) {


            datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                    // date = i + "/" + (i1 + 1) + "/" + i2;
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    calendar.add(Calendar.YEAR, -18);
                    if(monthOfYear < 10){

                        month = "0" + (monthOfYear+1);

                    }else {

                        month = "" + (monthOfYear+1);

                    }
                    if(dayOfMonth < 10){

                        day  = "0" + dayOfMonth ;

                    }else {

                        day  = "" + dayOfMonth ;

                    }
                    date=month + "/" + day + "/" + year;
                    binding.edtDate.setText(date);

                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            calendar.add(Calendar.YEAR, -18);
            datePickerDialog.show();

        }

        if (view.getId() == R.id.tv_previous) {

            if (step == 2) {
                step = 0;
                setPreviousStep();
            } else if (step == 1) {
                step = 0;
                setPreviousStep();
            }
//            binding.tvNext.setText("Next");
//            binding.tvPrevious.setVisibility(View.GONE);
//            binding.linReference.setVisibility(View.GONE);
//            binding.svApplicantDetails.setVisibility(View.VISIBLE);
        }

        if (view.getId() == R.id.tv_next) {


            yy = binding.tvpriorState.getText().toString();

            if (step == 1) {

                if (validate()) {

                    HashMap<String, Object> param = new HashMap<>();
                    HashMap<String, Object> address =   new HashMap<>();
                    HashMap<String, Object> param_prior = new HashMap<>();
                    HashMap<String, Object> param_detail = new HashMap<>();
                    HashMap<String, Object> infoParam = new HashMap<>();

                    param_detail.put("key", "address_detail");
                    param_detail.put("home_phone", binding.edtHomePhone.getText().toString());
                    param_detail.put("phone", binding.edtPhoneNumber.getText().toString());

                    if (!new MyPref(context).getUserData().getDesignation_id().equals("2")) {

                        infoParam.put("ssn", getOriginalSsnNumber(binding.edtSsnNumber));
                        infoParam.put("us_citizen", isCitizen);
                        infoParam.put("emergency_phone", binding.edtEmergencyPhone.getText().toString());
                        infoParam.put("date_available", date);
                        infoParam.put("immigration_card", binding.edtCardNumber.getText().toString());
                        /*param_detail.put("bonded", binding.rgBonded.getCheckedRadioButtonId() == R.id.rb_bonded_yes ? "1" : "0");
                        param_detail.put("refused_bond", binding.rgRefused.getCheckedRadioButtonId() == R.id.rb_refused_yes ? "1" : "0");
                        param_detail.put("convicted_crime", binding.rgCrime.getCheckedRadioButtonId() == R.id.rb_crime_yes ? "1" : "0");*/
                    }

                    param.put("building", binding.edtBuildingApt.getText().toString());
                    param.put("address1", binding.edtAddress1.getText().toString());
                    param.put("address2", binding.edtAddress2.getText().toString());
                    param.put("state", binding.tvState.getText().toString());
                    param.put("state_id", state_code);
                    param.put("city", binding.tvCity.getText().toString());
                    param.put("city_id", city_code);
                    param.put("zipcode", binding.edtZipCode.getText().toString());
                    param.put("how_long_resident", binding.activitySignUpSpGender.getSelectedItem().toString());
                    param_prior.put("building", binding.edtBuildingApt1.getText().toString());
                    param_prior.put("address1", binding.edtPriorAddress1.getText().toString());
                    param_prior.put("address2", binding.edtPriorAddress2.getText().toString());
                    param_prior.put("state", binding.tvpriorState.getText().toString());
                    param_prior.put("state_id", prior_state_code);
                    param_prior.put("city", binding.tvPriorCity.getText().toString());
                    param_prior.put("city_id", prior_city_code);
                    param_prior.put("zipcode", binding.edtPriorZipCode.getText().toString());
                  //  param_prior.put("how_long_resident", binding.activitySignUpSpGender1.getSelectedItem().toString());
                    address.put("address", param);
                    Log.e("Dishank--------->", String.valueOf(infoParam));
                    address.put("prior", param_prior);
                    param_detail.put("address_detail", address);
                    param_detail.put("prior_detail", param);
                    address.put("info", infoParam);
                    Log.e("passsss",param_prior.toString());
                    presenterInterface.callAPI(NurseApplicationDetailActivity.this, null, param_detail, ApiCallInterface.STORE_APPLICANT_DETAIL);

                }

            } else {

                HashMap<String, Object> param = new HashMap<>();
                param.put("key", "reference_detail");
                param.put("home_phone", binding.edtHomePhone.getText().toString());
                param.put("phone", binding.edtPhoneNumber.getText().toString());
                param.put("reference_detail", mEmergencyDataModel);
                presenterInterface.callAPI(NurseApplicationDetailActivity.this, null, param, ApiCallInterface.STORE_APPLICANT_DETAIL);

            }

        }

    }

    private void setPreviousStep() {
        if (step == 1) {
            step = 2;
            binding.tvNext.setText("Submit");
            binding.tvPrevious.setVisibility(View.VISIBLE);
            binding.svApplicantDetails.setVisibility(View.GONE);
            binding.linReference.setVisibility(View.VISIBLE);
        } else {
            step = 1;
            binding.tvNext.setText("Next");
            binding.tvPrevious.setVisibility(View.GONE);
            binding.linReference.setVisibility(View.GONE);
            binding.svApplicantDetails.setVisibility(View.VISIBLE);

        }

    }

    public boolean validate() {


//        if (!StringUtils.isNotEmpty(binding.edtFirstName.getText().toString()) ){
//            errorMessage(binding.getRoot(), "Please enter first name");
//            return false;
//        }
//        else if (!StringUtils.isNotEmpty(binding.edtLastName.getText().toString())  ){
//            errorMessage(binding.getRoot(), "Please enter last name");
//            return false;
//        }
//        if (!StringUtils.isNotEmpty(binding.edtHomePhone.getText().toString())) {
//            errorMessage(binding.getRoot(), "Please enter Home Phone");
//            return false;
//        } else
//            if (binding.edtHomePhone.getText().length() < 17) {
//            errorMessage(binding.getRoot(), "Please enter valid Home Phone");
//            return false;
//        } else
//            if (!StringUtils.isNotEmpty(binding.edtPhoneNumber.getText().toString())) {
//            errorMessage(binding.getRoot(), "Please enter  Cell Phone");
//            return false;
//        } else if (binding.edtPhoneNumber.getText().length() < 17) {
//            errorMessage(binding.getRoot(), "Please enter valid Cell Phone");
//            return false;
//        } else if (!StringUtils.isNotEmpty(binding.edtBuildingApt.getText().toString())) {
//            errorMessage(binding.getRoot(), "Please enter Apt# *");
//            return false;
//        }

        if (!new MyPref(context).getUserData().getDesignation_id().equals("2")) {

             if (!StringUtils.isNotEmpty(binding.edtSsnNumber.getText().toString())) {
                errorMessage(binding.getRoot(), "please enter SSN number");
                return false;
            } else if (binding.edtSsnNumber.getText().toString().length()<11) {
                errorMessage(binding.getRoot(), "please enter valid SSN number");
                return false;
            } else if (!StringUtils.isNotEmpty(binding.edtEmergencyPhone.getText().toString())) {
                errorMessage(binding.getRoot(), "please enter emergency phone number");
                return false;
            } else if (binding.edtEmergencyPhone.getText().length()<17) {
                errorMessage(binding.getRoot(), "please enter valid emergency phone number");
                return false;
            }else if (!StringUtils.isNotEmpty(isCitizen)) {
                 errorMessage(binding.getRoot(), "please select us citizen");
                 return false;
             } else if (StringUtils.isNotEmpty(isCitizen) && isCitizen.equalsIgnoreCase("0") && !StringUtils.isNotEmpty(binding.edtCardNumber.getText().toString())) {
                 errorMessage(binding.getRoot(), "please enter immigration id");
                 return false;
             }
        }

        if (!StringUtils.isNotEmpty(binding.edtAddress1.getText().toString())) {
            errorMessage(binding.getRoot(), "Please enter address1");
            return false;
        }
//        else if (!StringUtils.isNotEmpty(binding.edtAddress2.getText().toString())) {
//            errorMessage(binding.getRoot(), "Please enter address2");
//            return false;
//        }
        else if (!StringUtils.isNotEmpty(binding.tvState.getText().toString())) {
            errorMessage(binding.getRoot(), "Please select state");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.tvCity.getText().toString())) {
            errorMessage(binding.getRoot(), "Please select city");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.edtZipCode.getText().toString())) {
            errorMessage(binding.getRoot(), "Please enter zip code");
            return false;
        } else if (binding.activitySignUpSpGender.getSelectedItemPosition() == 1 && !StringUtils.isNotEmpty(binding.edtPriorAddress1.getText().toString())) {
            errorMessage(binding.getRoot(), "Kindly Provide Prior Address to proceed");
            return false;
        }
//        else if (!StringUtils.isNotEmpty(binding.edtAddress2.getText().toString())) {
//            errorMessage(binding.getRoot(), "Please enter address2");
//            return false;
//        }
        else if (binding.activitySignUpSpGender.getSelectedItemPosition() == 1 && !StringUtils.isNotEmpty(binding.tvpriorState.getText().toString())) {
            errorMessage(binding.getRoot(), "Please select prior state");
            return false;
        } else if (binding.activitySignUpSpGender.getSelectedItemPosition() == 1 && !StringUtils.isNotEmpty(binding.tvPriorCity.getText().toString())) {
            errorMessage(binding.getRoot(), "Please select prior city");
            return false;
        } else if (binding.activitySignUpSpGender.getSelectedItemPosition() == 1 && !StringUtils.isNotEmpty(binding.edtPriorZipCode.getText().toString())) {
            errorMessage(binding.getRoot(), "Please enter prior zip code");
            return false;
        }

        return true;
    }

    private void setReferenceAdapter() {

//        mEmergencyDataModel.add(new EmergencyDataModel("","","","",""));
        NurseReferenceAdapter nurseReferenceAdapter = new NurseReferenceAdapter(this, mEmergencyDataModel);
        binding.rvReference.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvReference.setAdapter(nurseReferenceAdapter);

//        mAdapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(int position, int which, Object object) {
//
//                HashMap<String, String> map = new HashMap<>();
//                map.put("id", ((Datum2) object).getRequest().getId());
//                presenterInterface.sendRequest(context, null, map, ApiCallInterface.readNotification);
//            }
//        });

        nurseReferenceAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, int which, Object object) {
                selectPos = position;
                EmergencyDataModel emergencyDataModel = (EmergencyDataModel) object;
                startActivityForResult(new Intent(NurseApplicationDetailActivity.this, NurseReferenceRecordActivity.class)
                        .putExtra("name", emergencyDataModel.getName())
                        .putExtra("zipcode", emergencyDataModel.getZipcode())
                        .putExtra("phone", emergencyDataModel.getPhoneNo())
                        .putExtra("address", emergencyDataModel.getAddress())
                        .putExtra("apt", emergencyDataModel.getBuilding())
                        .putExtra("state", emergencyDataModel.getState())
                        .putExtra("state_id", emergencyDataModel.getState_id())
                        .putExtra("address2",emergencyDataModel.getAddress_line_2())
                        .putExtra("city", emergencyDataModel.getCity())
                        .putExtra("city_id", emergencyDataModel.getCity_id())
                        .putExtra("rel",emergencyDataModel.getRelation()), 503);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 101) {
                EmergencyDataModel model = new Gson().fromJson(data.getStringExtra("data"), EmergencyDataModel.class);
                mEmergencyDataModel.add(model);
                binding.rvReference.getAdapter().notifyDataSetChanged();
            }

            if (requestCode == 503) {
                EmergencyDataModel model = new Gson().fromJson(data.getStringExtra("data"), EmergencyDataModel.class);
                mEmergencyDataModel.set(selectPos, model);
                binding.rvReference.getAdapter().notifyDataSetChanged();
            }
            if (requestCode == PLACE_REQUEST) {

                if (resultCode == RESULT_OK) {

                    Place place = Autocomplete.getPlaceFromIntent(data);
                    binding.edtAddress1.setText(place.getAddress());
                    binding.tvCity.setText(Utility.getCity(NurseApplicationDetailActivity.this, place.getLatLng()));
                    binding.tvState.setText(Utility.getState(NurseApplicationDetailActivity.this, place.getLatLng()));
                    binding.edtZipCode.setText(Utility.getPostalCode(NurseApplicationDetailActivity.this, place.getLatLng()));

                    for (int i = 0; i < stateModelList.size(); i++) {
                        if (stateModelList.get(i).getState().equalsIgnoreCase(binding.tvState.getText().toString())) {
                            state_code = stateModelList.get(i).getId();
                        }
                    }
                    for (int i = 0; i < cityList.size(); i++) {
                        if (cityList.get(i).getCity().equalsIgnoreCase(binding.tvCity.getText().toString())) {
                            city_code = cityList.get(i).getId();
                        }
                    }

//                    Log.e("CIty ID", city_code);
//                    Log.e("STATE ID", state_code);
                    // do query with address

                }
            }
            if (requestCode == PLACE_REQUEST_PRIOR) {

                if (resultCode == RESULT_OK) {

                    Place place = Autocomplete.getPlaceFromIntent(data);
                    Log.i("TAG", "Place: " + place.getName() + ", " + place.getId() + ", " + place.getAddress());

                    binding.edtPriorAddress1.setText(place.getAddress());

                    binding.tvPriorCity.setText(Utility.getCity(NurseApplicationDetailActivity.this, place.getLatLng()));
                    binding.tvpriorState.setText(Utility.getState(NurseApplicationDetailActivity.this, place.getLatLng()));
                    binding.edtPriorZipCode.setText(Utility.getPostalCode(NurseApplicationDetailActivity.this, place.getLatLng()));

                    for (int i = 0; i < stateModelList.size(); i++) {
                        if (stateModelList.get(i).getState().equalsIgnoreCase(binding.tvState.getText().toString())) {
                            prior_state_code = stateModelList.get(i).getId();
                        }
                    }
                    for (int i = 0; i < cityList.size(); i++) {
                        if (cityList.get(i).getCity().equalsIgnoreCase(binding.tvCity.getText().toString())) {
                            prior_city_code = cityList.get(i).getId();
                        }
                    }


                }
            }
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            // TODO: Handle the error.
            Status status = Autocomplete.getStatusFromIntent(data);

            Log.i("TAG", status.getStatusMessage());
        } else if (resultCode == RESULT_CANCELED) {
            // The user canceled the operation.
        }

    }


    public void getIntentParamsData() {
//        Log.v("getIntentParamsData","called");
        if (getIntent().getExtras() != null) {
//            Log.v("I am ","here");
            ApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), ApplicationDetailsModel.class);


            if (model != null) {

                if (model.getData().getAddressDetail() != null) {

                    if (StringUtils.isNotEmpty(model.getData().getHome_phone())) {
                        binding.edtHomePhone.setText(model.getData().getHome_phone());

                    }
                    if (StringUtils.isNotEmpty(model.getData().getPhone())) {
                        binding.edtPhoneNumber.setText(model.getData().getPhone());
                    }
//

                    AddressDetail addressDetail = model.getData().getAddressDetail();
                    binding.edtAddress1.setText(addressDetail.getAddress().getAddress1());
                    binding.edtAddress2.setText(addressDetail.getAddress().getAddress2());
                    binding.edtBuildingApt.setText(addressDetail.getAddress().getBuilding());
                    binding.tvState.setText(addressDetail.getAddress().getState());
                    binding.tvCity.setText(addressDetail.getAddress().getCity());
                    binding.edtZipCode.setText(addressDetail.getAddress().getZipcode());
                    binding.tvSignUpGender.setText(addressDetail.getAddress().getHow_long_resident());

//                    if (addressDetail.getAddress().getHow_long_resident().equals("More than 2 year")){
//                        binding.edtResidentAddress.setText(addressDetail.getAddress().getHow_long_resident());
//                    }


                    if (model.getData().getAddressDetail().getPrior() != null) {

                        binding.edtPriorAddress1.setText(addressDetail.getPrior().getAddress1());
                        binding.edtPriorAddress2.setText(addressDetail.getPrior().getAddress2());
                        binding.edtBuildingApt1.setText(addressDetail.getPrior().getBuilding());
                        binding.tvpriorState.setText(addressDetail.getPrior().getState());
                        binding.tvPriorCity.setText(addressDetail.getPrior().getCity());
                        binding.edtPriorZipCode.setText(addressDetail.getPrior().getZipcode());
                        binding.tvSignUpGender1.setText(addressDetail.getPrior().getHow_long_resident());


//
                    }

                    if (model.getData().getReferenceDetail() != null) {
                        mEmergencyDataModel.addAll(model.getData().getReferenceDetail());
                        binding.rvReference.getAdapter().notifyDataSetChanged();
                    }
//
                }

            }


        }
    }

    public void getIntentParamsDataOtherUser() {

        if (getIntent().getExtras() != null) {

            UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);


            if (model != null) {

                if (model.getData().getAddressDetail() != null) {

                    if (StringUtils.isNotEmpty(model.getData().getHomePhone())) {
                        binding.edtHomePhone.setText(model.getData().getHomePhone());

                    }
                    if (StringUtils.isNotEmpty(model.getData().getPhone())) {
                        binding.edtPhoneNumber.setText(model.getData().getPhone());
                    }
                    if (StringUtils.isNotEmpty(model.getData().getSsn())) {
                        binding.edtSsnNumber.setText(model.getData().getSsn());
                    }
                    if (StringUtils.isNotEmpty(model.getData().getEmergencyPhone())) {
                        binding.edtEmergencyPhone.setText(model.getData().getEmergencyPhone());
                    }



                    com.android.doral.retrofit.model.UserApplicationDetails.AddressDetail addressDetail = model.getData().getAddressDetail();
                    binding.edtAddress1.setText(addressDetail.getAddress().getAddress1());
                    binding.edtAddress2.setText(addressDetail.getAddress().getAddress2());
                    binding.edtBuildingApt.setText(addressDetail.getAddress().getBuilding());
                    binding.tvState.setText(addressDetail.getAddress().getState());
                    binding.tvCity.setText(addressDetail.getAddress().getCity());
                    binding.edtZipCode.setText(addressDetail.getAddress().getZipcode());
                    binding.tvSignUpGender.setText(addressDetail.getAddress().getHowLongResident());






                    if (model.getData().getAddressDetail().getInfo() != null) {
                        binding.edtSsnNumber.setText(addressDetail.getInfo().getSsn());
                        binding.edtEmergencyPhone.setText(addressDetail.getInfo().getEmergencyPhone());
                        binding.edtDate.setText(addressDetail.getInfo().getDateAvailable());

                        if (addressDetail.getInfo().getUsCitizen().equals("1")){

                            isCitizen = "1";
                            binding.llCard.setVisibility(View.GONE);
                            //  binding.edtCardNumber.setText("");
                            binding.rbYes.setChecked(true);
                            binding.rbNo.setChecked(false);
                        }else {

                            binding.llCard.setVisibility(View.VISIBLE);
                            //  binding.edtCardNumber.setText(model.getData().getImmigrationId());
                            isCitizen = "0";
                            binding.edtCardNumber.setText(addressDetail.getInfo().getImmigrationCard());
                            binding.rbYes.setChecked(false);
                            binding.rbNo.setChecked(true);
                        }
                    }
                    if (model.getData().getAddressDetail().getPrior() != null) {

                        binding.edtPriorAddress1.setText(addressDetail.getPrior().getAddress1());
                        binding.edtPriorAddress2.setText(addressDetail.getPrior().getAddress2());
                        binding.edtBuildingApt1.setText(addressDetail.getPrior().getBuilding());
                        binding.tvpriorState.setText(addressDetail.getPrior().getState());
                        binding.tvPriorCity.setText(addressDetail.getPrior().getCity());
                        binding.edtPriorZipCode.setText(addressDetail.getPrior().getZipcode());
                        binding.tvSignUpGender1.setText(addressDetail.getPrior().getHowLongResident());

                    }

                    if (model.getData().getReferenceDetail() != null) {

                        for (int i = 0; i <model.getData().getReferenceDetail().size() ; i++) {

                            EmergencyDataModel emergencyDataModel=new EmergencyDataModel();
                            emergencyDataModel.setName(model.getData().getReferenceDetail().get(i).getName());
                            emergencyDataModel.setPhoneNo(model.getData().getReferenceDetail().get(i).getPhoneNo());
                            emergencyDataModel.setBuilding(model.getData().getReferenceDetail().get(i).getBuilding());
                            emergencyDataModel.setAddress_line_1(model.getData().getReferenceDetail().get(i).getAddressLine1());
                            emergencyDataModel.setAddress_line_2(model.getData().getReferenceDetail().get(i).getAddressLine2());
                            emergencyDataModel.setState_id(model.getData().getReferenceDetail().get(i).getStateId());
                            emergencyDataModel.setCity_id(model.getData().getReferenceDetail().get(i).getCityId());
                            emergencyDataModel.setState(model.getData().getReferenceDetail().get(i).getState());
                            emergencyDataModel.setCity(model.getData().getReferenceDetail().get(i).getCity());
                            emergencyDataModel.setZipcode(model.getData().getReferenceDetail().get(i).getZipcode());
                            emergencyDataModel.setRelation(model.getData().getReferenceDetail().get(i).getRelation());
                            mEmergencyDataModel.add(emergencyDataModel);

                        }

                        binding.rvReference.getAdapter().notifyDataSetChanged();
                    }
//
                }

            }


        }
    }

    private String getOriginalSsnNumber(EditText editText){
        String removeSpecial = editText.getText().toString().replaceAll("[^\\d]", "");
        return  removeSpecial;
    }

}
