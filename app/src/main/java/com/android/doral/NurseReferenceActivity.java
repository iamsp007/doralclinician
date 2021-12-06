package com.android.doral;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;

import com.android.doral.Utils.PhoneTextFormatter;
import com.android.doral.Utils.StringUtils;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.RowEmergencyContactBinding;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.CityModel;
import com.android.doral.retrofit.model.EmergencyDataModel;
import com.android.doral.retrofit.model.StateModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class NurseReferenceActivity extends BaseActivity implements BaseViewInterface, View.OnClickListener {
    RowEmergencyContactBinding binding;
    BasePresenterInterface presenterInterface;
    private String state_code;
    private String city_code;
    private List<CityModel> cityList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RowEmergencyContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setStatusBarColor(R.color.colorPrimary);
        presenterInterface = new Presenter(this);
        presenterInterface.sendRequest(this, null, null, ApiCallInterface.STATES);
        presenterInterface.sendRequest(this, null, null, ApiCallInterface.CITY);
        binding.tvState.setOnClickListener(this);
        binding.toolbar.tvTitle.setText("Add Reference Detail");
        binding.toolbar.imgBack.setVisibility(View.VISIBLE);
        binding.toolbar.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.edtPhoneNumber.addTextChangedListener(new PhoneTextFormatter(binding.edtPhoneNumber, "+1 (###) ###-####"));
        setSpinner(binding.spRelation);
        binding.edtRelation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.spRelation.performClick();
            }
        });
        binding.edtRelationType.setVisibility(View.GONE);
        binding.tvRelationLabel.setVisibility(View.GONE);
        binding.spRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                binding.edtRelation.setText(parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

        binding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!StringUtils.isNotEmpty(binding.edtName.getText().toString())) {
                    errorMessage(binding.getRoot(), "Please enter Name");
                } else if (!StringUtils.isNotEmpty(binding.edtPhoneNumber.getText().toString())) {
                    errorMessage(binding.getRoot(), "Please enter Phone number");
                } else if (binding.edtPhoneNumber.getText().length() < 17) {
                    errorMessage(binding.getRoot(), "Please enter valid Phone number");
                } else if (!StringUtils.isNotEmpty(binding.edtBuildingApt.getText().toString())) {
                    errorMessage(binding.getRoot(), "Please enter Building/Apt");
                } else if (!StringUtils.isNotEmpty(binding.edtAddress.getText().toString())) {
                    errorMessage(binding.getRoot(), "Please enter Address Line 1");
                }
//                else if (!StringUtils.isNotEmpty(binding.edtAddress1.getText().toString())) {
//                    errorMessage(binding.getRoot(), "Please enter Address Line 2");
//                }
                else if (!StringUtils.isNotEmpty(state_code)) {
                    errorMessage(binding.getRoot(), "Please select state");
                } else if (!StringUtils.isNotEmpty(city_code)) {
                    errorMessage(binding.getRoot(), "Please select city");
                } else if (binding.edtRelation.getText().toString() == "Select") {
                    errorMessage(binding.getRoot(), "Please select Relationship");
                } else if (!StringUtils.isNotEmpty(binding.edtRelationType.getText().toString())) {
                    errorMessage(binding.getRoot(), "Please enter Person Relation to you");
                } else {
                    EmergencyDataModel model = new EmergencyDataModel(binding.edtName.getText().toString(), binding.edtPhoneNumber.getText().toString(), binding.edtBuildingApt.getText().toString(),
                            binding.edtAddress.getText().toString(),
                            binding.edtAddress1.getText().toString(),
                           // binding.tvState.getText().toString(),
                            state_code,
                          //  binding.tvCity.getText().toString(),
                            city_code,
                            binding.edtZipCode.getText().toString(),
                            binding.edtRelation.getText().toString()
                    );

                    Intent intent = new Intent();
                    intent.putExtra("data", new Gson().toJson(model));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private void setSpinner(Spinner spinner) {
        List<String> list = new ArrayList<>();
        list.add("Select");
        list.add("Professional");
        list.add("Personal");
//        list.add("Grand Father");
//        list.add("Grand Mother");
//        list.add("Father");
//        list.add("Mother");
//        list.add("Brother");
//        list.add("Sister");
//        list.add("Husband");
//        list.add("Wife");
//        list.add("Son");
//        list.add("Daughter");


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_row, list) {
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
    public void retry(int pos) {

    }

    @Override
    public void onError(String errorMsg, int requestCode, int resultCode) {

    }

    @Override
    public void onSuccess(Object success, int requestCode, int resultCode) {
        if (requestCode == ApiCallInterface.STATES) {
            List<StateModel> stateModelList = (List<StateModel>) success;
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
}
