package com.android.doral;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.doral.Utils.StringUtils;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityDipositBinding;
import com.android.doral.dialog.CustomAlertDialog;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.BaseModel;
import com.android.doral.retrofit.model.CityModel;
import com.android.doral.retrofit.model.StateModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DipositActivity extends BaseActivity implements BaseViewInterface, View.OnClickListener {
    ActivityDipositBinding binding;
    BasePresenterInterface presenterInterface;
    private String state_code;
    private String city_code;
    private List<CityModel> cityList;
    private String send_document = "";
    private String legal_entity = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDipositBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();

    }

    private void init() {
        binding.toolbar.tvTitle.setText("GET PAID");
        binding.toolbar.imgBack.setVisibility(View.VISIBLE);
        binding.toolbar.imgBack.setOnClickListener(this);
        presenterInterface = new Presenter(this);
        presenterInterface.sendRequest(context, null, null, ApiCallInterface.STATES, false);
        presenterInterface.sendRequest(context, null, null, ApiCallInterface.CITY, false);
        binding.tvState.setOnClickListener(this);
        binding.tvCity.setOnClickListener(this);
        binding.tvSendDocument.setOnClickListener(this);
        binding.tvLegalEntity.setOnClickListener(this);
        binding.tvNext.setOnClickListener(this);
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
        binding.spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        });

        binding.spSendTaxDocument.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                send_document = ((String) binding.spSendTaxDocument.getSelectedItem());
                binding.tvSendDocument.setText(send_document);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.spEntity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                legal_entity = ((String) binding.spEntity.getSelectedItem());
                binding.tvLegalEntity.setText(legal_entity);


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

        if (requestCode == ApiCallInterface.ADD_DIPOSIT_DETAILS) {
            BaseModel baseModel = (BaseModel) success;
            if (baseModel.isStatus().equals("true")) {
                new CustomAlertDialog(context, baseModel.getMessage(), "Confirm your email address", "OK", "", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        finish();
                    }
                }).show();


            } else {
                errorMessage(binding.getRoot(), baseModel.getMessage());
            }
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

        ArrayAdapter<CityModel> arrayAdapter = new ArrayAdapter<CityModel>(context, R.layout.spinner_row, list) {
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

        ArrayAdapter<StateModel> arrayAdapter = new ArrayAdapter<StateModel>(context, R.layout.spinner_row, list) {
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
            binding.spCity.performClick();
        }
        if (view.getId() == R.id.tv_send_document) {
            binding.spSendTaxDocument.performClick();
        }
        if (view.getId() == R.id.tv_legal_entity) {
            binding.spEntity.performClick();
        }
        if (view.getId()==R.id.img_back){

            onBackPressed();

        }
        if (view.getId() == R.id.tv_next) {
            if (validate()) {
                HashMap<String, String> map = new HashMap<>();
                map.put("account_name", binding.edtName.getText().toString());
                map.put("account_type", binding.edtAccountType.getText().toString());
                map.put("routing_number", binding.edtRoutingNumber.getText().toString());
                map.put("account_number", binding.edtAccountNumber.getText().toString());
                map.put("address_line_1", binding.edtAddress1.getText().toString());
                map.put("address_line_2", binding.edtAddress2.getText().toString());
                map.put("building", binding.edtBuildingApt.getText().toString());
                map.put("state", state_code);
                map.put("city", city_code);
                map.put("zip", binding.edtZipcode.getText().toString());
                map.put("send_tax_documents_to", send_document);
                map.put("legal_entity", legal_entity);
                map.put("tax_payer_id_number", binding.edtTaxNumber.getText().toString());
                presenterInterface.sendRequest(context, "", map, ApiCallInterface.ADD_DIPOSIT_DETAILS, true);
            }
        }

    }


    public boolean validate() {
        if (!StringUtils.isNotEmpty(binding.edtName.getText().toString())) {
            errorMessage(binding.getRoot(), "please enter name");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.edtAccountType.getText().toString())) {
            errorMessage(binding.getRoot(), "please enter account type");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.edtRoutingNumber.getText().toString())) {
            errorMessage(binding.getRoot(), "please enter routing number");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.edtAddress1.getText().toString())) {
            errorMessage(binding.getRoot(), "please enter address1");
            return false;
        }
//        else if (!StringUtils.isNotEmpty(binding.edtAddress2.getText().toString())) {
//            errorMessage(binding.getRoot(), "please enter address2");
//            return false;

        else if (!StringUtils.isNotEmpty(binding.edtBuildingApt.getText().toString())) {
            errorMessage(binding.getRoot(), "please enter aptarment");
            return false;

        } else if (!StringUtils.isNotEmpty(state_code)) {
            errorMessage(binding.getRoot(), "please select state");
            return false;
        } else if (!StringUtils.isNotEmpty(city_code)) {
            errorMessage(binding.getRoot(), "please select city");
            return false;
        } else if (!StringUtils.isNotEmpty(send_document)) {
            errorMessage(binding.getRoot(), "please select send document");
            return false;
        } else if (!StringUtils.isNotEmpty(legal_entity)) {
            errorMessage(binding.getRoot(), "please select legal entity");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.edtTaxNumber.getText().toString())) {
            errorMessage(binding.getRoot(), "please enter identification Number");
            return false;
        }
        return true;
    }
}