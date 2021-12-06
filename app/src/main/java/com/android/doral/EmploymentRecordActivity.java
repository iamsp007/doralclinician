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
import androidx.appcompat.widget.AppCompatSpinner;

import com.android.doral.Utils.PhoneTextFormatter;
import com.android.doral.Utils.StringUtils;
import com.android.doral.Utils.Utility;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.RowEmploymentHistoryBinding;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.CityModel;
import com.android.doral.retrofit.model.CompanyHistoryModel;
import com.android.doral.retrofit.model.StateModel;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmploymentRecordActivity extends BaseActivity implements BaseViewInterface , View.OnClickListener{


    RowEmploymentHistoryBinding binding;
    private String state_code;
    private String city_code;
    private List<CityModel> cityList;
    BasePresenterInterface presenterInterface;
    List<StateModel> stateModelList;
    private static final int PLACE_REQUEST = 997;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= RowEmploymentHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setStatusBarColor(R.color.colorPrimary);
        presenterInterface = new Presenter(this);
        if (!Places.isInitialized()) {
            Places.initialize(EmploymentRecordActivity.this, getString(R.string.google_api_key));
        }
        presenterInterface.sendRequest(this, null, null, ApiCallInterface.STATES);
        presenterInterface.sendRequest(this, null, null, ApiCallInterface.CITY);
        binding.tvState.setOnClickListener(this);
        binding.toolbar.tvTitle.setText("Add Empoyment Detail");
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
        binding.edtPhoneNumber.addTextChangedListener(new PhoneTextFormatter(binding.edtPhoneNumber, "+1 (###) ###-####"));

        binding.edtAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)/*.setCountry("IN")*/
                        .build(EmploymentRecordActivity.this);
                startActivityForResult(intent, PLACE_REQUEST);
            }
        });
        binding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 if (!StringUtils.isNotEmpty(binding.edtCompany.getText().toString())) {
                    errorMessage(binding.getRoot(), "Please enter Company");
                 }
                 else  if (!StringUtils.isNotEmpty(binding.edtAddress.getText().toString())) {
                    errorMessage(binding.getRoot(), "Please enter Address line 1");
                }
//                 else if (!StringUtils.isNotEmpty(binding.edtAddress1.getText().toString())){
//                     errorMessage(binding.getRoot(), "Please enter Address");
//                 }
//                 else if (!StringUtils.isNotEmpty(binding.edtBuildingApt.getText().toString())){
//                     errorMessage(binding.getRoot(), "Please enter Apt# *");
//                 }
                 else if (!StringUtils.isNotEmpty(binding.edtPhoneNumber.getText().toString()) ) {
                     errorMessage(binding.getRoot(), "Please enter Phone number");
                 } else if (binding.edtPhoneNumber.getText().length()<17 ) {
                     errorMessage(binding.getRoot(), "Please enter valid Phone number");
                 }
                 else if (!StringUtils.isNotEmpty(binding.tvState.getText().toString())) {
                    errorMessage(binding.getRoot(), "Please select state");
                } else if (!StringUtils.isNotEmpty(binding.tvCity.getText().toString())) {
                    errorMessage(binding.getRoot(), "Please select city");}

                 else{
                     CompanyHistoryModel model=new CompanyHistoryModel(binding.edtCompany.getText().toString(),
                             binding.edtPhoneNumber.getText().toString(),
                             binding.edtAddress.getText().toString(),
                             binding.edtAddress1.getText().toString(),
                             binding.apt.getText().toString(),
                             binding.tvState.getText().toString(),
                             binding.tvCity.getText().toString(),
                             binding.edtZipCode.getText().toString()
                             );

                    // Log.e("apt", String.valueOf(model));
                     Intent intent=new Intent();
                     intent.putExtra("data",new Gson().toJson(model));
                     setResult(RESULT_OK,intent);
                     finish();
                 }
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tvState) {
            binding.spState.performClick();
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

    private void edit() {
        if (getIntent().hasExtra("name")) {
            String parent_id = (String) getIntent().getStringExtra("name");
            binding.edtCompany.setText(parent_id);
        }

        if (getIntent().hasExtra("zipcode")) {
            String parent_id = (String) getIntent().getStringExtra("zipcode");
            binding.edtZipCode.setText(parent_id);
        }

        if (getIntent().hasExtra("phone")) {
            String parent_id = (String) getIntent().getStringExtra("phone");
            binding.edtPhoneNumber.setText(parent_id);
        }

        if (getIntent().hasExtra("address")) {
            String parent_id = (String) getIntent().getStringExtra("address");
            binding.edtAddress.setText(parent_id);
        }

        if (getIntent().hasExtra("apt")) {
            String parent_id = (String) getIntent().getStringExtra("apt");
            binding.apt.setText(parent_id);
        }

        if (getIntent().hasExtra("state")) {
            String parent_id = (String) getIntent().getStringExtra("state");
            binding.tvState.setText(parent_id);
        }

        if (getIntent().hasExtra("city")) {
            String parent_id = (String) getIntent().getStringExtra("city");
            binding.tvCity.setText(parent_id);
        }

        if (getIntent().hasExtra("address2")) {
            String parent_id = (String) getIntent().getStringExtra("address2");
            binding.edtAddress1.setText(parent_id);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == PLACE_REQUEST) {
                if (resultCode == RESULT_OK) {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    binding.edtAddress.setText(place.getAddress());
                    binding.tvCity.setText(Utility.getCity(EmploymentRecordActivity.this, place.getLatLng()));
                    binding.tvState.setText(Utility.getState(EmploymentRecordActivity.this, place.getLatLng()));
                    binding.edtZipCode.setText(Utility.getPostalCode(EmploymentRecordActivity.this, place.getLatLng()));

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
