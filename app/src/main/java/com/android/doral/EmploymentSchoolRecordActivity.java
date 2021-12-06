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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;

import com.android.doral.Utils.DateUtils;
import com.android.doral.Utils.StringUtils;
import com.android.doral.Utils.Utility;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.RowSchoolHistoryBinding;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.CityModel;
import com.android.doral.retrofit.model.NurseEducationHistoryModel;
import com.android.doral.retrofit.model.StateModel;
import com.android.doral.retrofit.model.applicationdetails.ApplicationDetailsModel;
import com.android.doral.retrofit.model.applicationdetails.PayrollDetail;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class EmploymentSchoolRecordActivity extends BaseActivity implements BaseViewInterface, View.OnClickListener {


    RowSchoolHistoryBinding binding;
    BasePresenterInterface presenterInterface;
    private String state_code;
    private String city_code;
    private List<CityModel> cityList;
    String birth_date = "";
    DatePickerDialog datePickerDialog;
    List<StateModel> stateModelList;
    int genderValue = 0;
    private static final int PLACE_REQUEST = 997;
    NurseEducationHistoryModel model;

    private final SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= RowSchoolHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setStatusBarColor(R.color.colorPrimary);
        presenterInterface = new Presenter(this);
        if (!Places.isInitialized()) {
            Places.initialize(EmploymentSchoolRecordActivity.this, getString(R.string.google_api_key));
        }
        presenterInterface.sendRequest(this, null, null, ApiCallInterface.STATES);
        presenterInterface.sendRequest(this, null, null, ApiCallInterface.CITY);
        binding.toolbar.tvTitle.setText("Add Education Detail");
        binding.toolbar.imgBack.setVisibility(View.VISIBLE);
        binding.toolbar.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        edit();


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
        binding.tvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CityModel cityModel = (CityModel) adapterView.getItemAtPosition(i);
                city_code = cityModel.getId();


            }
        });

        binding.rbYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.year.setVisibility(View.VISIBLE);
            }
        });

        binding.rbNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.year.setVisibility(View.GONE);

            }
        });

        binding.tvState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.spState.performClick();
            }
        });


        binding.tvSignUpGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.edtYearCompleted.performClick();
            }
        });


        binding.edtYearCompleted.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0) {

                    genderValue = i;
                    binding.tvSignUpGender.setText(binding.edtYearCompleted.getSelectedItem().toString());

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        binding.tvSignUpGender1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.edtDegree.performClick();
            }
        });


        binding.edtDegree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0) {

                    genderValue = i;
                    binding.tvSignUpGender1.setText(binding.edtDegree.getSelectedItem().toString());

                }

                if (i == 6) {
                    binding.llOtherDegree.setVisibility(View.VISIBLE);
                } else {
                    binding.llOtherDegree.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




//        binding.edtYearCompleted.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Calendar calendar = Calendar.getInstance();
//                datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
//                        /*birth_date = i + "/" + (i1 + 1) + "/" + i2;
//                        binding.activitySignUpTvDOB.setText(birth_date);*/
//
//                        calendar.set(Calendar.YEAR, year);
//                        calendar.set(Calendar.MONTH, monthOfYear);
//                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//
//                        birth_date = dateformat.format(calendar.getTime());
//                        binding.edtYearCompleted.setText(DateUtils.changeDateFormat(DateUtils.DATE_FORMATE_8, DateUtils.DATE_FORMATE_1, dateformat.format(calendar.getTime()), false));
//
//                    }
//                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
//                Calendar calendar1 = Calendar.getInstance();
//                datePickerDialog.getDatePicker().setMaxDate(calendar1.getTimeInMillis());
//                datePickerDialog.show();
//            }
//        });



//        binding.edtDegree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (i == 6) {
//                    binding.llOtherDegree.setVisibility(View.VISIBLE);
//                } else {
//                    binding.llOtherDegree.setVisibility(View.GONE);
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        binding.edtSchooladdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)/*.setCountry("IN")*/
                        .build(EmploymentSchoolRecordActivity.this);
                startActivityForResult(intent, PLACE_REQUEST);
            }
        });

        binding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 if (!StringUtils.isNotEmpty(binding.edtNameAddress.getText().toString())) {
                    errorMessage(binding.getRoot(), "Please enter Name and Address");
                 }
                 else if (!StringUtils.isNotEmpty(binding.edtSchooladdress.getText().toString())){
                     errorMessage(binding.getRoot(), "Please enter Name and Address");
                 }
                 else  if (!StringUtils.isNotEmpty(binding.tvState.getText().toString())) {
                    errorMessage(binding.getRoot(), "Please enter State");
                }
                 else if (!StringUtils.isNotEmpty(binding.tvCity.getText().toString()) ) {
                     errorMessage(binding.getRoot(), "Please enter City");
                 }else if (!StringUtils.isNotEmpty(binding.edtZipCode.getText().toString())){
                     errorMessage(binding.getRoot(), "Please enter Zipcode");
                 }
                 else if (!StringUtils.isNotEmpty(binding.edtDegree.getSelectedItem().toString())) {
                    errorMessage(binding.getRoot(), "Please enter Degree");
                }

                 else{
                     NurseEducationHistoryModel model=new NurseEducationHistoryModel(binding.edtNameAddress.getText().toString(),
                             binding.edtYearCompleted.getSelectedItem().toString(),
                             binding.rbYes.isChecked() ? binding.rbYes.getText().toString() : binding.rbNo.getText().toString(),
                             binding.edtDegree.getSelectedItemPosition() != 6 ? binding.edtDegree.getSelectedItem().toString() : binding.edtOtherDegree.getText().toString(),
                             binding.edtSchooladdress.getText().toString(),
                             binding.edtBuildingApt.getText().toString(),
                             binding.tvState.getText().toString(),
                             binding.tvCity.getText().toString(),
                             binding.edtZipCode.getText().toString(),
                             binding.edtWebsite.getText().toString()
                     );

                     Intent intent=new Intent();
                     intent.putExtra("data",new Gson().toJson(model));
                     setResult(RESULT_OK,intent);
                     finish();
                 }


            }
        });


    }

    private void edit() {

        if (getIntent().hasExtra("name")) {
            String parent_id = (String) getIntent().getStringExtra("name");
            binding.edtNameAddress.setText(parent_id);
        }

        if (getIntent().hasExtra("zipcode")) {
            String parent_id = (String) getIntent().getStringExtra("zipcode");
            binding.edtZipCode.setText(parent_id);
        }



        if (getIntent().hasExtra("address")) {
            String parent_id = (String) getIntent().getStringExtra("address");
            binding.edtSchooladdress.setText(parent_id);
        }

//

        if (getIntent().hasExtra("state")) {
            String parent_id = (String) getIntent().getStringExtra("state");
            binding.tvState.setText(parent_id);
        }

        if (getIntent().hasExtra("city")) {
            String parent_id = (String) getIntent().getStringExtra("city");
            binding.tvCity.setText(parent_id);
        }

//

        if (getIntent().hasExtra("dishank")) {

                binding.rbYes.setChecked(true);
                binding.year.setVisibility(View.VISIBLE);

        }else{
            binding.rbNo.setChecked(true);
            binding.year.setVisibility(View.GONE);
        }

        if (getIntent().hasExtra("year")) {
            String parent_id = (String) getIntent().getStringExtra("year");

            binding.tvSignUpGender.setText(parent_id);
        }

        if (getIntent().hasExtra("degree")) {
            String parent_id = (String) getIntent().getStringExtra("degree");

            binding.tvSignUpGender1.setText(parent_id);
        }


        if (getIntent().hasExtra("web")) {
            String parent_id = (String) getIntent().getStringExtra("web");
            binding.edtWebsite.setText(parent_id);
        }


    }

    public void onClick(View view) {
        if (view.getId() == R.id.tvState) {
            binding.spState.performClick();
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
        if (requestCode == ApiCallInterface.STATES) {
            stateModelList = (List<StateModel>) success;
            if (stateModelList != null) {

                setStatteSpinner(binding.spState, stateModelList);

            }
        }
        if (requestCode == ApiCallInterface.CITY) {
            cityList = (List<CityModel>) success;

        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == PLACE_REQUEST) {
                if (resultCode == RESULT_OK) {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    binding.edtSchooladdress.setText(place.getAddress());
                    binding.tvCity.setText(Utility.getCity(EmploymentSchoolRecordActivity.this, place.getLatLng()));
                    binding.tvState.setText(Utility.getState(EmploymentSchoolRecordActivity.this, place.getLatLng()));
                    binding.edtZipCode.setText(Utility.getPostalCode(EmploymentSchoolRecordActivity.this, place.getLatLng()));

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


        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            // TODO: Handle the error.
            Status status = Autocomplete.getStatusFromIntent(data);

            Log.i("TAG", status.getStatusMessage());
        } else if (resultCode == RESULT_CANCELED) {
            // The user canceled the operation.
        }




    }
}
