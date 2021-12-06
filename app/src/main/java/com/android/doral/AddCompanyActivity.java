package com.android.doral;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.android.doral.Utils.StringUtils;
import com.android.doral.Utils.Utility;
import com.android.doral.adapter.ViewPagerAdapter;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityAddCompanyBinding;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.CityModel;
import com.android.doral.retrofit.model.CompanyModel;
import com.android.doral.retrofit.model.EmergencyDataModel;
import com.android.doral.retrofit.model.SelectionListModel.AddressType;
import com.android.doral.retrofit.model.SelectionListModel.ReasonForLeaving;
import com.android.doral.retrofit.model.SelectionListModel.SelectionListModel;
import com.android.doral.retrofit.model.StateModel;
import com.android.doral.retrofit.model.WorkHistroyModel;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialog;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class AddCompanyActivity extends BaseActivity implements BaseViewInterface, View.OnClickListener {
    ActivityAddCompanyBinding binding;
    ViewPagerAdapter viewPagerAdapter;
    private static final int PLACE_REQUEST = 997;
    List<StateModel> stateModelList;
    private String state_code;
    BasePresenterInterface presenterInterface;
    private String city_code;
    private List<CityModel> cityList;
    int yearSelected;
    int monthSelected;
    List<ReasonForLeaving> reasonForLeavingsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCompanyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        presenterInterface = new Presenter(this);
        if (!Places.isInitialized()) {
            Places.initialize(AddCompanyActivity.this, getString(R.string.google_api_key));
        }
        Calendar calendar = Calendar.getInstance();
        yearSelected = calendar.get(Calendar.YEAR);
        monthSelected = calendar.get(Calendar.MONTH);
        presenterInterface.sendRequest(this, null, null, ApiCallInterface.STATES);
        presenterInterface.sendRequest(this, null, null, ApiCallInterface.CITY);
        presenterInterface.sendRequest(this, null, null, ApiCallInterface.SELECTION_LIST);
        binding.toolbar.tvTitle.setText("Add Company");

        binding.tvStartedYear.setOnClickListener(this);
        binding.tvCompleteYear.setOnClickListener(this);
        binding.tvReasonForLeaving.setOnClickListener(this);

        edit();
        binding.edtInstituteAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)/*.setCountry("IN")*/
                        .build(AddCompanyActivity.this);
                startActivityForResult(intent, PLACE_REQUEST);
            }
        });

        binding.spReasonForLeaving.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    binding.tvReasonForLeaving.setText(((ReasonForLeaving) binding.spReasonForLeaving.getSelectedItem()).getValue());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!StringUtils.isNotEmpty(binding.edtCompanyName.getText().toString())) {
                    errorMessage(binding.getRoot(), "Please enter company Name");
                }
                else if (!StringUtils.isNotEmpty(binding.edtPositionTitle.getText().toString())) {
                    errorMessage(binding.getRoot(), "Please enter Position*");
                }
                else if (!StringUtils.isNotEmpty(binding.edtInstituteAddress.getText().toString())) {
                    errorMessage(binding.getRoot(), "Please enter Address Line 1");
                }
//                else if (!StringUtils.isNotEmpty(binding.edtAddress1.getText().toString())) {
//                    errorMessage(binding.getRoot(), "Please enter Address Line 2");
//
//                }
                else if (!StringUtils.isNotEmpty( binding.tvState.getText().toString())) {
                    errorMessage(binding.getRoot(), "Please select state");
                } else if (!StringUtils.isNotEmpty( binding.tvCity.getText().toString())) {
                    errorMessage(binding.getRoot(), "Please select city");
                } else if (binding.tvReasonForLeaving.getText().toString().equals("select")) {
                    errorMessage(binding.getRoot(), "Please Enter Reason");
                }
                else if (!StringUtils.isNotEmpty(binding.tvStartedYear.getText().toString())) {
                    errorMessage(binding.getRoot(), "Please Select start year");

                }else if (!StringUtils.isNotEmpty(binding.tvCompleteYear.getText().toString())) {
                    errorMessage(binding.getRoot(), "Please Select complete year");

                }

                else {

                    CompanyModel model = new CompanyModel(binding.edtCompanyName.getText().toString(),
                            binding.tvState.getText().toString(),
                            binding.tvCity.getText().toString(),
                            binding.tvStartedYear.getText().toString(),
                            binding.tvCompleteYear.getText().toString(),
                            binding.edtPositionTitle.getText().toString(),
                            binding.tvReasonForLeaving.getText().toString(),
                            binding.edtZipCode.getText().toString(),
                            binding.edtInstituteAddress.getText().toString(),
                            binding.edtInstituteAddressLine2.getText().toString(),
                            city_code,
                            state_code,
                            binding.edtApt.getText().toString()

                    );

                    Intent intent = new Intent();
                    intent.putExtra("data", new Gson().toJson(model));
                    Log.e("data",  binding.tvState.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();

                }
            }
        });

    }

    @Override
    public void onClick(View view) {
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
        if (view.getId() == R.id.tvReasonForLeaving) {
            binding.spReasonForLeaving.performClick();
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
    }

    String getMonthName(int mothNumber){
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        cal.set(Calendar.MONTH,mothNumber);
        String month_name = month_date.format(cal.getTime());
        return month_name;
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
        if (requestCode == ApiCallInterface.SELECTION_LIST) {

            SelectionListModel selectionLaseModel = (SelectionListModel) success;
            if (selectionLaseModel.isStatus()){

                reasonForLeavingsList=selectionLaseModel.getData().getReasonForLeaving();
                if (reasonForLeavingsList!=null){
                    List<ReasonForLeaving> reasonForLeavings=new ArrayList<>();
                    ReasonForLeaving spinnerModel = new ReasonForLeaving("select");
                    reasonForLeavings.add(0, spinnerModel);
                    reasonForLeavings.addAll(reasonForLeavingsList);
                    setReasonForLeavingSpinner(binding.spReasonForLeaving,reasonForLeavings);
                }

            }

        }
    }

    private void setReasonForLeavingSpinner(AppCompatSpinner spinner, List<ReasonForLeaving> list) {
      /*  AddressType spinnerModel = new AddressType("select");
        list.add(0, spinnerModel);*/
        ArrayAdapter<ReasonForLeaving> arrayAdapter = new ArrayAdapter<ReasonForLeaving>(this, R.layout.spinner_row, list) {
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == PLACE_REQUEST) {
                if (resultCode == RESULT_OK) {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    binding.edtInstituteAddress.setText(place.getAddress());
                    binding.tvCity.setText(Utility.getCity(AddCompanyActivity.this, place.getLatLng()));
                    binding.tvState.setText(Utility.getState(AddCompanyActivity.this, place.getLatLng()));
                    binding.edtZipCode.setText(Utility.getPostalCode(AddCompanyActivity.this, place.getLatLng()));

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

    private void edit() {
        if (getIntent().hasExtra("name")) {
            String parent_id = (String) getIntent().getStringExtra("name");
            binding.edtCompanyName.setText(parent_id);
        }

        if (getIntent().hasExtra("zipcode")) {
            String parent_id = (String) getIntent().getStringExtra("zipcode");
            binding.edtZipCode.setText(parent_id);
        }

        if (getIntent().hasExtra("position")) {
            String parent_id = (String) getIntent().getStringExtra("position");
            binding.edtPositionTitle.setText(parent_id);
        }

        if (getIntent().hasExtra("Address1")) {
            String parent_id = (String) getIntent().getStringExtra("Address1");
            binding.edtInstituteAddress.setText(parent_id);
        }

        if (getIntent().hasExtra("Address2")) {
            String parent_id = (String) getIntent().getStringExtra("Address2");
            binding.edtInstituteAddressLine2.setText(parent_id);
        }

        if (getIntent().hasExtra("state")) {
            String parent_id = (String) getIntent().getStringExtra("state");
            binding.tvState.setText(parent_id);
        }

        if (getIntent().hasExtra("city")) {
            String parent_id = (String) getIntent().getStringExtra("city");
            binding.tvCity.setText(parent_id);
        }

        if (getIntent().hasExtra("apt")) {
            String parent_id = (String) getIntent().getStringExtra("apt");
            binding.edtApt.setText(parent_id);
        }

        if (getIntent().hasExtra("Sdate")) {
            String parent_id = (String) getIntent().getStringExtra("Sdate");
            binding.tvStartedYear.setText(parent_id);
        }

        if (getIntent().hasExtra("Edate")) {
            String parent_id = (String) getIntent().getStringExtra("Edate");
            binding.tvCompleteYear.setText(parent_id);
        }

        if (getIntent().hasExtra("reson")) {
            String parent_id = (String) getIntent().getStringExtra("reson");
            binding.tvReasonForLeaving.setText(parent_id);
        }




    }
}