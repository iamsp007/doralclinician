package com.android.doral;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import com.android.doral.Utils.DrawableClickListener;
import com.android.doral.Utils.MyPref;
import com.android.doral.Utils.Utility;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityEducationBinding;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.BaseModel;
import com.android.doral.retrofit.model.CityModel;
import com.android.doral.retrofit.model.EducationModel;
import com.android.doral.retrofit.model.StateModel;
import com.android.doral.retrofit.model.UserApplicationDetails.FellowshipInstitute;
import com.android.doral.retrofit.model.UserApplicationDetails.MedicalInstitute;
import com.android.doral.retrofit.model.UserApplicationDetails.ResidencyInstitute;
import com.android.doral.retrofit.model.UserApplicationDetails.UserApplicationDetailsModel;
import com.android.doral.retrofit.model.applicationdetails.ApplicationDetailsModel;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialog;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class EducationActivity extends BaseActivity implements BaseViewInterface, View.OnClickListener {
    ActivityEducationBinding binding;
    BasePresenterInterface presenterInterface;
    private String state_code, city_code;
    private String residency_state_code, residency_city_code, fellow_state_code, fellow_city_code;
    private List<CityModel> cityList;
    List<StateModel> stateModelList;
    boolean isFellowShip = false;
    int yearSelected;
    int monthSelected;
    private static final int PLACE_REQUEST_INSTITUTE = 997;
    private static final int PLACE_REQUEST_RESIDENCY = 998;
    private static final int PLACE_REQUEST_FELLOWSHIP = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEducationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (!Places.isInitialized()) {
            Places.initialize(EducationActivity.this, getString(R.string.google_api_key));
        }
        //Set default values
        Calendar calendar = Calendar.getInstance();
        yearSelected = calendar.get(Calendar.YEAR);
        monthSelected = calendar.get(Calendar.MONTH);

        binding.toolbar.tvTitle.setText(getResources().getString(R.string.education_details));
        binding.toolbar.imgBack.setVisibility(View.VISIBLE);
        binding.toolbar.imgBack.setOnClickListener(this);

        presenterInterface = new Presenter(this);

        presenterInterface.sendRequest(this, null, null, ApiCallInterface.STATES);
        presenterInterface.sendRequest(this, null, null, ApiCallInterface.CITY);

        binding.tvStateF.setOnClickListener(this);
        binding.tvResidencyState.setOnClickListener(this);
        binding.tvState.setOnClickListener(this);
        binding.tvCity.setOnClickListener(this);
        binding.tvStartedYear.setOnClickListener(this);
        binding.tvCompleteYear.setOnClickListener(this);
        binding.tvResidStartedYear.setOnClickListener(this);
        binding.tvResidCompleteYear.setOnClickListener(this);
        binding.tvCityF.setOnClickListener(this);
        binding.tvFellowStartedYear.setOnClickListener(this);
        binding.tvFellowCompletedYear.setOnClickListener(this);
        binding.tvResidencyCity.setOnClickListener(this);
        binding.tvAddFollowship.setOnClickListener(this);
        binding.tvClose.setOnClickListener(this);

        binding.relMedicalInstitute.setOnClickListener(this);
        binding.relResidencyInstitute.setOnClickListener(this);
        binding.relFellowshipInstitute.setOnClickListener(this);

        binding.linMedicalInstitute.setVisibility(View.GONE);
        binding.viewMedicalInstitute.setVisibility(View.GONE);
        binding.linResidencyInstitute.setVisibility(View.GONE);
        binding.viewResidencyInstitute.setVisibility(View.GONE);
        binding.linFellowshipInstitute.setVisibility(View.GONE);

        binding.edtInstituteAddress.setOnClickListener(this);
        binding.edtResidencyInstAddress.setOnClickListener(this);
        binding.edtFellowInstiAddress.setOnClickListener(this);

        init();

        //setYear();

    }

    private void init() {

        binding.spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0) {

                    state_code = ((StateModel) binding.spState.getSelectedItem()).getId();
                    binding.tvState.setText(((StateModel) binding.spState.getSelectedItem()).getState());
                    setCitySpinner(binding.spCity, getCityList(((StateModel) binding.spState.getSelectedItem()).getState_code()));

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spStateF.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0) {

                    fellow_state_code = ((StateModel) binding.spStateF.getSelectedItem()).getId();
                    binding.tvStateF.setText(((StateModel) binding.spStateF.getSelectedItem()).getState());
                    setCitySpinner(binding.spCityF, getCityList(((StateModel) binding.spStateF.getSelectedItem()).getState_code()));

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        binding.spResidencyState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0) {

                    residency_state_code = ((StateModel) binding.spResidencyState.getSelectedItem()).getId();
                    binding.tvResidencyState.setText(((StateModel) binding.spResidencyState.getSelectedItem()).getState());
                    setCitySpinner(binding.spResidencyCity, getCityList(((StateModel) binding.spResidencyState.getSelectedItem()).getState_code()));

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0) {

                    city_code = ((CityModel) binding.spCity.getSelectedItem()).getId();
                    binding.tvCity.setText(((CityModel) binding.spCity.getSelectedItem()).getCity());

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spCityF.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0)

                fellow_city_code = ((CityModel) binding.spCityF.getSelectedItem()).getId();
                binding.tvCityF.setText(((CityModel) binding.spCityF.getSelectedItem()).getCity());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spResidencyCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0)
                residency_city_code = ((CityModel) binding.spResidencyCity.getSelectedItem()).getId();
                binding.tvResidencyCity.setText(((CityModel) binding.spResidencyCity.getSelectedItem()).getCity());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        if (getIntent().getExtras() != null) {

            UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);
            if (model!=null){

                if (model.getData().getEducationDetail()!=null){

                    MedicalInstitute medicalInstitute=model.getData().getEducationDetail().getMedicalInstitute();
                    if (medicalInstitute!=null){
                        binding.edtMedicalInstituteName.setText(medicalInstitute.getMedicalInstituteName());
                        binding.edtInstituteAddress.setText(medicalInstitute.getMedicalAddress1());
                        binding.edtInstituteAddressLine2.setText(medicalInstitute.getMedicalAddress2());
                        binding.edtApt.setText(medicalInstitute.getMedicalBuilding());
                        binding.edtZipCode.setText(medicalInstitute.getMedicalZipcode());
                        binding.tvStartedYear.setText(medicalInstitute.getMedicalYearStarted());
                        binding.tvCompleteYear.setText(medicalInstitute.getMedicalYearEnded());
                        binding.tvState.setText(medicalInstitute.getMedicalStateId());
                        binding.tvCity.setText(medicalInstitute.getMedicalCityId());

                        if (stateModelList!=null){

                            for (int i = 0; i < stateModelList.size(); i++) {
                                if (stateModelList.get(i).getState().equalsIgnoreCase(medicalInstitute.getMedicalStateId().toString())) {
                                    state_code = stateModelList.get(i).getId();
                                    binding.tvState.setText(stateModelList.get(i).getState());
                                }
                            }

                        }

                        if (cityList!=null){

                            for (int i = 0; i < cityList.size(); i++) {
                                if (cityList.get(i).getCity().equalsIgnoreCase(medicalInstitute.getMedicalCityId().toString())) {
                                    city_code = cityList.get(i).getId();
                                    binding.tvCity.setText(cityList.get(i).getCity());
                                }
                            }

                        }


                    }

                    ResidencyInstitute residencyInstitute=model.getData().getEducationDetail().getResidencyInstitute();
                    if (residencyInstitute!=null){

                        binding.edtResidencyInstName.setText(residencyInstitute.getMedicalInstituteName());
                        binding.edtResidencyInstAddress.setText(residencyInstitute.getMedicalAddress1());
                        binding.edtResidencyInstAddressLine2.setText(residencyInstitute.getMedicalAddress2());
                        binding.edtResidencyInstApt.setText(residencyInstitute.getMedicalBuilding());
                        binding.edtResidencyIntiZipCode.setText(residencyInstitute.getMedicalZipcode());
                        binding.tvResidStartedYear.setText(residencyInstitute.getMedicalYearStarted());
                        binding.tvResidCompleteYear.setText(residencyInstitute.getMedicalYearEnded());
                        binding.tvResidencyState.setText(residencyInstitute.getMedicalStateId());
                        binding.tvResidencyCity.setText(residencyInstitute.getMedicalCityId());

                        if (stateModelList!=null){

                            for (int i = 0; i < stateModelList.size(); i++) {
                                if (stateModelList.get(i).getState().equalsIgnoreCase(residencyInstitute.getMedicalStateId().toString())) {
                                    state_code = stateModelList.get(i).getId();
                                    binding.tvResidencyState.setText(stateModelList.get(i).getState());
                                }
                            }

                        }

                        if (cityList!=null){

                            for (int i = 0; i < cityList.size(); i++) {
                                if (cityList.get(i).getCity().equalsIgnoreCase(residencyInstitute.getMedicalCityId().toString())) {
                                    city_code = cityList.get(i).getId();
                                    binding.tvResidencyCity.setText(cityList.get(i).getCity());
                                }
                            }

                        }


                    }

                    FellowshipInstitute fellowshipInstitute=model.getData().getEducationDetail().getFellowshipInstitute();
                    if (fellowshipInstitute!=null){

                        binding.edtFellowNameInstitute.setText(fellowshipInstitute.getMedicalInstituteName());
                        binding.edtFellowInstiAddress.setText(fellowshipInstitute.getMedicalAddress1());
                        binding.edtFellowshipInstAddressLine2.setText(fellowshipInstitute.getMedicalAddress2());
                        binding.edtFellowshipInstApt.setText(fellowshipInstitute.getMedicalBuilding());
                        binding.edtFellowshipZipCode.setText(fellowshipInstitute.getMedicalZipcode());
                        binding.tvFellowStartedYear.setText(fellowshipInstitute.getMedicalYearStarted());
                        binding.tvFellowCompletedYear.setText(fellowshipInstitute.getMedicalYearEnded());
                        binding.tvStateF.setText(fellowshipInstitute.getMedicalStateId());
                        binding.tvCityF.setText(fellowshipInstitute.getMedicalCityId());

                        if (stateModelList!=null){

                            for (int i = 0; i < stateModelList.size(); i++) {
                                if (stateModelList.get(i).getState().equalsIgnoreCase(fellowshipInstitute.getMedicalStateId().toString())) {
                                    state_code = stateModelList.get(i).getId();
                                    binding.tvStateF.setText(stateModelList.get(i).getState());
                                }
                            }

                        }

                        if (cityList!=null){

                            for (int i = 0; i < cityList.size(); i++) {
                                if (cityList.get(i).getCity().equalsIgnoreCase(fellowshipInstitute.getMedicalCityId().toString())) {
                                    city_code = cityList.get(i).getId();
                                    binding.tvCityF.setText(cityList.get(i).getCity());
                                }
                            }

                        }


                    }

                }
            }

        }

        binding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validate()) {

                    HashMap<String, Object> mainParam = new HashMap<>();
                    HashMap<String, Object> param = new HashMap<>();
                    HashMap<String, Object> medicalInstituteParam = new HashMap<>();
                    HashMap<String, Object> residencyParam = new HashMap<>();
                    HashMap<String, Object> fellowshipParam = new HashMap<>();
                    mainParam.put("key", "education_detail");

                    medicalInstituteParam.put("medical_instituteName", binding.edtMedicalInstituteName.getText().toString());
                    medicalInstituteParam.put("medical_address1", binding.edtInstituteAddress.getText().toString());
                    medicalInstituteParam.put("medical_address2", binding.edtInstituteAddressLine2.getText().toString());
                    medicalInstituteParam.put("medical_yearStarted", binding.tvStartedYear.getText().toString());
                    medicalInstituteParam.put("medical_yearEnded", binding.tvCompleteYear.getText().toString());
                    medicalInstituteParam.put("medical_stateId", binding.tvState.getText().toString());
                    medicalInstituteParam.put("medical_cityId", binding.tvCity.getText().toString());
                    medicalInstituteParam.put("medical_building", binding.edtApt.getText().toString());
                    medicalInstituteParam.put("medical_zipcode", binding.edtZipCode.getText().toString());

                    residencyParam.put("medical_instituteName", binding.edtResidencyInstName.getText().toString());
                    residencyParam.put("medical_address1", binding.edtResidencyInstAddress.getText().toString());
                    residencyParam.put("medical_address2", binding.edtResidencyInstAddressLine2.getText().toString());
                    residencyParam.put("medical_yearStarted", binding.tvResidStartedYear.getText().toString());
                    residencyParam.put("medical_yearEnded", binding.tvResidCompleteYear.getText().toString());
                    residencyParam.put("medical_stateId", binding.tvResidencyState.getText().toString());
                    residencyParam.put("medical_cityId", binding.tvResidencyCity.getText().toString());
                    residencyParam.put("medical_building", binding.edtResidencyInstApt.getText().toString());
                    residencyParam.put("medical_zipcode", binding.edtResidencyIntiZipCode.getText().toString());

                    fellowshipParam.put("medical_instituteName", binding.edtFellowNameInstitute.getText().toString());
                    fellowshipParam.put("medical_address1", binding.edtFellowInstiAddress.getText().toString());
                    fellowshipParam.put("medical_address2", binding.edtFellowshipInstAddressLine2.getText().toString());
                    fellowshipParam.put("medical_yearStarted", binding.tvFellowStartedYear.getText().toString());
                    fellowshipParam.put("medical_yearEnded", binding.tvFellowCompletedYear.getText().toString());
                    fellowshipParam.put("medical_stateId", binding.tvStateF.getText().toString());
                    fellowshipParam.put("medical_cityId", binding.tvCityF.getText().toString());
                    fellowshipParam.put("medical_building", binding.edtFellowshipInstApt.getText().toString());
                    fellowshipParam.put("medical_zipcode", binding.edtFellowshipZipCode.getText().toString());

                    param.put("residencyInstitute", residencyParam);
                    param.put("medicalInstitute", medicalInstituteParam);
                    param.put("fellowshipInstitute", fellowshipParam);
                    mainParam.put("education_detail",param);

                    Log.e("education Details", mainParam.toString());

                    presenterInterface.callAPI(EducationActivity.this, null, mainParam, ApiCallInterface.STORE_APPLICANT_DETAIL);

                }

            }
        });
    }


    private boolean validate() {

        if (binding.edtMedicalInstituteName.getText().toString().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "please enter medical institute name");
            return false;
        } else if (binding.edtInstituteAddress.getText().toString().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "please enter medical institute address");
            return false;
        } else if (binding.tvState.getText().toString().equalsIgnoreCase("select")) {
            errorMessage(binding.getRoot(), "please select medical institute state");
            return false;
        } else if (binding.tvCity.getText().toString().equalsIgnoreCase("select")) {
            errorMessage(binding.getRoot(), "please select medical institute city");
            return false;
        } else if (binding.edtResidencyInstName.getText().toString().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "please enter residency institute name");
            return false;
        } else if (binding.edtResidencyInstAddress.getText().toString().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "please enter residency institute address");
            return false;
        } else if (binding.tvResidencyState.getText().toString().equalsIgnoreCase("select")) {
            errorMessage(binding.getRoot(), "please select residency institute state");
            return false;
        } else if (binding.tvResidencyCity.getText().toString().equalsIgnoreCase("select")) {
            errorMessage(binding.getRoot(), "please select residency institute city");
            return false;
        }
        if (isFellowShip) {

            if (binding.edtFellowNameInstitute.getText().toString().equalsIgnoreCase("")) {
                errorMessage(binding.getRoot(), "please enter fellowship institute name");
                return false;
            } else if (binding.edtFellowInstiAddress.getText().toString().equalsIgnoreCase("")) {
                errorMessage(binding.getRoot(), "please enter fellowship institute address");
                return false;
            } else if (binding.tvStateF.getText().toString().equalsIgnoreCase("select")) {
                errorMessage(binding.getRoot(), "please select fellowship institute state");
                return false;
            } else if (binding.tvCityF.getText().toString().equalsIgnoreCase("select")) {
                errorMessage(binding.getRoot(), "please select fellowship institute city");
                return false;
            }

        }
        return true;
    }

    private void setYear() {

        ArrayList<String> yearArray = new ArrayList<>();
        yearArray.add("1995");
        yearArray.add("1996");
        yearArray.add("1997");
        yearArray.add("1998");
        yearArray.add("1999");
        yearArray.add("2000");
        yearArray.add("2001");
        yearArray.add("2002");
        yearArray.add("2003");
        yearArray.add("2004");
        yearArray.add("2005");
        yearArray.add("2006");
        yearArray.add("2007");
        yearArray.add("2008");
        yearArray.add("2009");
        yearArray.add("2010");
        yearArray.add("2011");
        yearArray.add("2012");
        yearArray.add("2013");
        yearArray.add("2014");
        yearArray.add("2015");
        yearArray.add("2016");
        yearArray.add("2017");
        yearArray.add("2018");
        yearArray.add("2019");
        yearArray.add("2020");

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, yearArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spStartedYear.setAdapter(spinnerArrayAdapter);
        binding.spCompletedYear.setAdapter(spinnerArrayAdapter);

        binding.spResidStartedYear.setAdapter(spinnerArrayAdapter);
        binding.spResideCompleteYear.setAdapter(spinnerArrayAdapter);

        binding.spFellowStartedYear.setAdapter(spinnerArrayAdapter);
        binding.spFellowCompleteYear.setAdapter(spinnerArrayAdapter);

        binding.spStartedYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                binding.tvStartedYear.setText(adapterView.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spCompletedYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                binding.tvCompleteYear.setText(adapterView.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spResidStartedYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                binding.tvResidStartedYear.setText(adapterView.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spResideCompleteYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                binding.tvResidCompleteYear.setText(adapterView.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spFellowStartedYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                binding.tvFellowStartedYear.setText(adapterView.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spFellowCompleteYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                binding.tvFellowCompletedYear.setText(adapterView.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
        Log.e("response",success.toString());
        if (requestCode == ApiCallInterface.STATES) {

             stateModelList = (List<StateModel>) success;
            if (stateModelList != null) {

                List<StateModel> stateList=new ArrayList<>();
                StateModel spinnerModel = new StateModel("select");
                stateList.add(0, spinnerModel);
                stateList.addAll(stateModelList);

                setStatteSpinner(binding.spState, stateList);
                setStatteSpinner(binding.spResidencyState, stateList);
                setStatteSpinner(binding.spStateF, stateList);

            }

        }
        if (requestCode == ApiCallInterface.CITY) {

            cityList = (List<CityModel>) success;

        }
        if (requestCode == ApiCallInterface.EDUCATION) {

            BaseModel basemodel = (BaseModel) success;

            if (basemodel.isStatus().equals("true") ) {

                Log.e("successMessage",basemodel.getMessage());
                errorMessage(binding.getRoot(), basemodel.getMessage());
                finish();

            } else {

                errorMessage(binding.getRoot(), basemodel.getMessage());
                Log.e("errorMessage",basemodel.getMessage());

            }

        }
        if (requestCode == ApiCallInterface.STORE_APPLICANT_DETAIL) {

            BaseModel basemodel = (BaseModel) success;

            if (basemodel.isStatus().equals("true") ) {

                Log.e("successMessage",basemodel.getMessage());
                errorMessage(binding.getRoot(), basemodel.getMessage());
                Toast.makeText(getApplicationContext(),basemodel.getMessage(),Toast.LENGTH_SHORT).show();
                finish();

            } else {

                errorMessage(binding.getRoot(), basemodel.getMessage());
                Log.e("errorMessage",basemodel.getMessage());

            }

        }

    }

    private List<CityModel> getCityList(String state_code) {

        List<CityModel> modelList = new ArrayList<>();
        CityModel spinnerModel = new CityModel("select");
        modelList.add(0, spinnerModel);

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
            //binding.spState.performClick();
        }
        if (view.getId() == R.id.tvStateF) {
            //binding.spStateF.performClick();
        }
        if (view.getId() == R.id.tvResidencyState) {
            //binding.spResidencyState.performClick();
        }
        if (view.getId() == R.id.tvStartedYear) {
            //binding.spStartedYear.performClick();
            MonthYearPickerDialogFragment dialogFragment = MonthYearPickerDialogFragment
                    .getInstance(monthSelected, yearSelected);
            dialogFragment.setOnDateSetListener(new MonthYearPickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(int year, int monthOfYear) {
                    // do something
                    binding.tvStartedYear.setText(""+getMonthName(monthOfYear)+" "+year);
                }
            });

            dialogFragment.show(getSupportFragmentManager(), null);
        }
        if (view.getId() == R.id.tvCompleteYear) {
            //binding.spCompletedYear.performClick();
            MonthYearPickerDialogFragment dialogFragment = MonthYearPickerDialogFragment
                    .getInstance(monthSelected, yearSelected);
            dialogFragment.setOnDateSetListener(new MonthYearPickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(int year, int monthOfYear) {
                    // do something
                    binding.tvCompleteYear.setText(""+getMonthName(monthOfYear)+" "+year);
                }
            });

            dialogFragment.show(getSupportFragmentManager(), null);
        }
        if (view.getId() == R.id.tvResidStartedYear) {
            //binding.spResidStartedYear.performClick();
            MonthYearPickerDialogFragment dialogFragment = MonthYearPickerDialogFragment
                    .getInstance(monthSelected, yearSelected);
            dialogFragment.setOnDateSetListener(new MonthYearPickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(int year, int monthOfYear) {
                    // do something
                    binding.tvResidStartedYear.setText(""+getMonthName(monthOfYear)+" "+year);
                }
            });

            dialogFragment.show(getSupportFragmentManager(), null);
        }
        if (view.getId() == R.id.tvResidCompleteYear) {
            //binding.spResideCompleteYear.performClick();
            MonthYearPickerDialogFragment dialogFragment = MonthYearPickerDialogFragment
                    .getInstance(monthSelected, yearSelected);
            dialogFragment.setOnDateSetListener(new MonthYearPickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(int year, int monthOfYear) {
                    // do something
                    binding.tvResidCompleteYear.setText(""+getMonthName(monthOfYear)+" "+year);
                }
            });

            dialogFragment.show(getSupportFragmentManager(), null);
        }
        if (view.getId() == R.id.tvFellowStartedYear) {
            //binding.spFellowStartedYear.performClick();
            MonthYearPickerDialogFragment dialogFragment = MonthYearPickerDialogFragment
                    .getInstance(monthSelected, yearSelected);
            dialogFragment.setOnDateSetListener(new MonthYearPickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(int year, int monthOfYear) {
                    // do something
                    binding.tvFellowStartedYear.setText(""+getMonthName(monthOfYear)+" "+year);
                }
            });

            dialogFragment.show(getSupportFragmentManager(), null);
        }
        if (view.getId() == R.id.tvFellowCompletedYear) {
            //binding.spFellowCompleteYear.performClick();
            MonthYearPickerDialogFragment dialogFragment = MonthYearPickerDialogFragment
                    .getInstance(monthSelected, yearSelected);
            dialogFragment.setOnDateSetListener(new MonthYearPickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(int year, int monthOfYear) {
                    // do something
                    binding.tvFellowCompletedYear.setText(""+getMonthName(monthOfYear)+" "+year);
                }
            });

            dialogFragment.show(getSupportFragmentManager(), null);
        }
        if (view.getId() == R.id.tvCity) {
            //binding.spCity.performClick();
        }
        if (view.getId() == R.id.tvCityF) {
            //binding.spCityF.performClick();
        }
        if (view.getId() == R.id.tvResidencyCity) {
            //binding.spResidencyCity.performClick();
        }
        if (view.getId() == R.id.tvAddFollowship) {
            binding.linFellowshipInstitute.setVisibility(View.VISIBLE);
            binding.tvAddFollowship.setVisibility(View.GONE);
            isFellowShip = true;
        }
        if (view.getId() == R.id.tv_close) {
            binding.linFellowshipInstitute.setVisibility(View.GONE);
            binding.tvAddFollowship.setVisibility(View.GONE);
            isFellowShip = false;
        }
        if (view.getId() == R.id.edtInstituteAddress) {

            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
            // Start the autocomplete intent.
            Intent intent = new Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.FULLSCREEN, fields)/*.setCountry("IN")*/
                    .build(EducationActivity.this);
            startActivityForResult(intent, PLACE_REQUEST_INSTITUTE);

        }
        if (view.getId() == R.id.edtResidencyInstAddress) {

            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
            // Start the autocomplete intent.
            Intent intent = new Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.FULLSCREEN, fields)/*.setCountry("IN")*/
                    .build(EducationActivity.this);
            startActivityForResult(intent, PLACE_REQUEST_RESIDENCY);

        }
        if (view.getId() == R.id.edtFellowInstiAddress) {

            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
            // Start the autocomplete intent.
            Intent intent = new Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.FULLSCREEN, fields)/*.setCountry("IN")*/
                    .build(EducationActivity.this);
            startActivityForResult(intent, PLACE_REQUEST_FELLOWSHIP);

        }

        if (view.getId() == R.id.img_back) {
          onBackPressed();
        }
        if (view.getId() == R.id.relMedicalInstitute) {

            if(binding.linMedicalInstitute.getVisibility()==View.VISIBLE){

                binding.linMedicalInstitute.setVisibility(View.GONE);
                binding.viewMedicalInstitute.setVisibility(View.GONE);

            }else {

                binding.linMedicalInstitute.setVisibility(View.VISIBLE);
                binding.viewMedicalInstitute.setVisibility(View.VISIBLE);
                binding.linResidencyInstitute.setVisibility(View.GONE);
                binding.viewResidencyInstitute.setVisibility(View.GONE);
                binding.linFellowshipInstitute.setVisibility(View.GONE);

            }


        }
        if (view.getId() == R.id.relResidencyInstitute) {

            if(binding.linResidencyInstitute.getVisibility()==View.VISIBLE){

                binding.linResidencyInstitute.setVisibility(View.GONE);
                binding.viewResidencyInstitute.setVisibility(View.GONE);

            }else {

                binding.linMedicalInstitute.setVisibility(View.GONE);
                binding.viewMedicalInstitute.setVisibility(View.GONE);
                binding.linResidencyInstitute.setVisibility(View.VISIBLE);
                binding.viewResidencyInstitute.setVisibility(View.VISIBLE);
                binding.linFellowshipInstitute.setVisibility(View.GONE);

            }


        }
        if (view.getId() == R.id.relFellowshipInstitute) {

            if(binding.linFellowshipInstitute.getVisibility()==View.VISIBLE){

                binding.linFellowshipInstitute.setVisibility(View.GONE);
                isFellowShip = false;

            }else {

                binding.linMedicalInstitute.setVisibility(View.GONE);
                binding.viewMedicalInstitute.setVisibility(View.GONE);
                binding.linResidencyInstitute.setVisibility(View.GONE);
                binding.viewResidencyInstitute.setVisibility(View.GONE);
                binding.linFellowshipInstitute.setVisibility(View.VISIBLE);
                isFellowShip = true;

            }


        }
    }

    String getMonthName(int mothNumber){
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        cal.set(Calendar.MONTH,mothNumber);
        String month_name = month_date.format(cal.getTime());
        return month_name;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == PLACE_REQUEST_INSTITUTE) {

                if (resultCode == RESULT_OK) {

                    Place place = Autocomplete.getPlaceFromIntent(data);
                    binding.edtInstituteAddress.setText(place.getAddress());
                    binding.tvCity.setText(Utility.getCity(EducationActivity.this, place.getLatLng()));
                    binding.tvState.setText(Utility.getState(EducationActivity.this, place.getLatLng()));
                    binding.edtZipCode.setText(Utility.getPostalCode(EducationActivity.this, place.getLatLng()));

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

            if (requestCode == PLACE_REQUEST_RESIDENCY) {

                if (resultCode == RESULT_OK) {

                    Place place = Autocomplete.getPlaceFromIntent(data);
                    binding.edtResidencyInstAddress.setText(place.getAddress());
                    binding.tvResidencyCity.setText(Utility.getCity(EducationActivity.this, place.getLatLng()));
                    binding.tvResidencyState.setText(Utility.getState(EducationActivity.this, place.getLatLng()));
                    binding.edtResidencyIntiZipCode.setText(Utility.getPostalCode(EducationActivity.this, place.getLatLng()));

                    for (int i = 0; i < stateModelList.size(); i++) {
                        if (stateModelList.get(i).getState().equalsIgnoreCase(binding.tvResidencyState.getText().toString())) {
                            residency_state_code = stateModelList.get(i).getId();
                        }
                    }
                    for (int i = 0; i < cityList.size(); i++) {
                        if (cityList.get(i).getCity().equalsIgnoreCase(binding.tvResidencyCity.getText().toString())) {
                            residency_city_code = cityList.get(i).getId();
                        }
                    }

                }
            }

            if (requestCode == PLACE_REQUEST_FELLOWSHIP) {

                if (resultCode == RESULT_OK) {

                    Place place = Autocomplete.getPlaceFromIntent(data);
                    binding.edtFellowInstiAddress.setText(place.getAddress());
                    binding.tvCityF.setText(Utility.getCity(EducationActivity.this, place.getLatLng()));
                    binding.tvStateF.setText(Utility.getState(EducationActivity.this, place.getLatLng()));
                    binding.edtFellowshipZipCode.setText(Utility.getPostalCode(EducationActivity.this, place.getLatLng()));

                    for (int i = 0; i < stateModelList.size(); i++) {
                        if (stateModelList.get(i).getState().equalsIgnoreCase(binding.tvStateF.getText().toString())) {
                            fellow_state_code = stateModelList.get(i).getId();
                        }
                    }
                    for (int i = 0; i < cityList.size(); i++) {
                        if (cityList.get(i).getCity().equalsIgnoreCase(binding.tvCityF.getText().toString())) {
                            fellow_city_code = cityList.get(i).getId();
                        }
                    }

                }
            }

        }
    }
}
