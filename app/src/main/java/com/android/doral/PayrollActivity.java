package com.android.doral;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import com.android.doral.Utils.ImagePickerUtils;
import com.android.doral.Utils.ImageUtils;
import com.android.doral.Utils.MyPref;
import com.android.doral.Utils.StringUtils;
import com.android.doral.Utils.Utility;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityPayrollBinding;
import com.android.doral.dialog.CustomAlertDialog;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.BaseModel;
import com.android.doral.retrofit.model.CityModel;
import com.android.doral.retrofit.model.SelectionListModel.AddressType;
import com.android.doral.retrofit.model.SelectionListModel.LegalEntity;
import com.android.doral.retrofit.model.SelectionListModel.ReasonForLeaving;
import com.android.doral.retrofit.model.SelectionListModel.SelectionListModel;
import com.android.doral.retrofit.model.StateModel;
import com.android.doral.retrofit.model.UserApplicationDetails.PayrollDetails;
import com.android.doral.retrofit.model.UserApplicationDetails.UserApplicationDetailsModel;
import com.android.doral.retrofit.model.UserModel;
import com.android.doral.retrofit.model.applicationdetails.ApplicationDetailsModel;
import com.android.doral.retrofit.model.applicationdetails.PayrollDetail;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PayrollActivity extends BaseActivity implements BaseViewInterface, View.OnClickListener {
    ActivityPayrollBinding binding;
    private String state_code, city_code;
    BasePresenterInterface presenterInterface;
    private List<CityModel> cityList;
    List<AddressType> addressTypeList;
    List<LegalEntity> legalEntityList;
    private String prior_state_code;
    private int step = 1,address_type_Code,legal_entity;
    private String value1,value2;
    List<StateModel> stateModelList;
    private static final int PLACE_REQUEST = 997;
    int a,b,sum;

    public void getIntentParamsData() {
//        Log.v("getIntentParamsData","called");
        if (getIntent().getExtras() != null) {
//            Log.v("I am ","here");
            ApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), ApplicationDetailsModel.class);

            if (model != null) {

                if (model.getData().getPayrollDetail() != null) {

                    PayrollDetail mPayrollDetail = model.getData().getPayrollDetail();
                    binding.bankname.setText(mPayrollDetail.getNameOfAccount());
                    //binding.edtAccountType.setText(mPayrollDetail.getTypeOfAccount());
                    binding.edtRoutingNumber.setText(mPayrollDetail.getRoutingNumber());
                    binding.edtAccountNumber.setText(mPayrollDetail.getAccountNumber());
                    binding.edtConfirmAccountNumber.setText(mPayrollDetail.getConfirmAcountNumber());
                    binding.tvState.setText(mPayrollDetail.getState_id());
                    binding.tvCity.setText(mPayrollDetail.getCity_id());
                    binding.edtZipCode.setText(mPayrollDetail.getZip_code());
                    binding.otherDependents.setText(mPayrollDetail.getOtherdependents());
                    binding.edtChildren.setText(mPayrollDetail.getChildrendependents());
                    binding.address.setText(mPayrollDetail.getAddress_line_1());
                    binding.address2.setText(mPayrollDetail.getAddress_line_2());
                    binding.apt.setText(mPayrollDetail.getApt());
                    //binding.accountname.setText(mPayrollDetail.getNameofaccount());
                    binding.totalDependents.setText(mPayrollDetail.getTotaldependents());


                    if (model.getData().getDocuments() != null) {

                        for (int i = 0; i < model.getData().getDocuments().size(); i++) {
                            if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("19")) {
                            binding.imgDoc.setVisibility(View.VISIBLE);
                            ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.imgDoc);
                        }
                        }
//                        binding.imgDoc.setVisibility(View.VISIBLE);
//                        int i=0;
//                        ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.imgDoc);

//
                    }


//
                if (mPayrollDetail.getTypeOfAccount().equalsIgnoreCase("Checking")){
                    binding.rbCheck.setChecked(true);
                }else{
                    binding.rbSave.setChecked(true);
                }


                if (mPayrollDetail.getFilesyourtax().equalsIgnoreCase("Single or Married filling separately")){
                    binding.rbYes.setChecked(true);
                }else if (mPayrollDetail.getFilesyourtax().equalsIgnoreCase("Married filling jointly or Qualifying widow(er)")){
                    binding.rbNo.setChecked(true);
                }else {
                    binding.rbUnknown.setChecked(true);
                }

                    Log.e("hgughs",mPayrollDetail.getFilesyourtax());
                    binding.edtDependents.setText(mPayrollDetail.getDependents());
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payroll);

        binding = ActivityPayrollBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenterInterface = new Presenter(this);
        if (!Places.isInitialized()) {
            Places.initialize(PayrollActivity.this, getString(R.string.google_api_key));
        }
        presenterInterface.sendRequest(this, null, null, ApiCallInterface.STATES);
        presenterInterface.sendRequest(this, null, null, ApiCallInterface.CITY);
        presenterInterface.sendRequest(this, null, null, ApiCallInterface.SELECTION_LIST);
        binding.tvState.setOnClickListener(this);
        binding.tvAddressType.setOnClickListener(this);
        binding.tvLegalEntity.setOnClickListener(this);
//        presenterInterface.sendRequest(this, null, null, ApiCallInterface.STATES);
//        presenterInterface.sendRequest(this, null, null, ApiCallInterface.CITY);

        binding.toolbar.tvTitle.setText("Payroll Details");
        binding.rlPriorAddress.setOnClickListener(this);
        binding.rlPriorAddress1.setOnClickListener(this);
        binding.tvpriorState.setOnClickListener(this);
        binding.tvPriorCity.setOnClickListener(this);
        binding.tvNext.setOnClickListener(this);
        binding.toolbar.imgBack.setVisibility(View.VISIBLE);
        binding.toolbar.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        UserModel model1 = new MyPref(this).getUserData();
        String Fname = capitizeString(model1.getFirst_name());
        String Lname = capitizeString(model1.getLast_name());
        binding.accountname.setText(Fname+"\t"+Lname);
        if (!new MyPref(context).getUserData().getDesignation_id().equals("2")) {
            getIntentParamsData1();
        }else{
            getIntentParamsData();
        }


        if (!new MyPref(context).getUserData().getDesignation_id().equals("2")) {

            binding.rlPriorAddress1.setVisibility(View.VISIBLE);
            binding.tax.setVisibility(View.VISIBLE);

        }else{
            binding.rlPriorAddress1.setVisibility(View.GONE);
            binding.tax.setVisibility(View.GONE);
        }

        binding.spPriorstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    prior_state_code = ((StateModel) binding.spPriorstate.getSelectedItem()).getId();
                    binding.tvpriorState.setText(((StateModel) binding.spPriorstate.getSelectedItem()).getState());
                    setCitySpinner(binding.spPriorcity, getCityList(((StateModel) binding.spPriorstate.getSelectedItem()).getState_code()));

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.rlSocialSecurity.setOnClickListener(this);


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

        binding.spAddressType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    address_type_Code = ((AddressType) binding.spAddressType.getSelectedItem()).getId();
                    binding.tvAddressType.setText(((AddressType) binding.spAddressType.getSelectedItem()).getValue());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spLegalEntity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    legal_entity = ((LegalEntity) binding.spLegalEntity.getSelectedItem()).getId();
                    binding.tvLegalEntity.setText(((LegalEntity) binding.spLegalEntity.getSelectedItem()).getValue());
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


        binding.rlUploadDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opneCameraGallery(1008);
            }
        });

        binding.edtConfirmAccountNumber.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v)
            {
                return true;
            }
        });

        binding.address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)/*.setCountry("IN")*/
                        .build(PayrollActivity.this);
                startActivityForResult(intent, PLACE_REQUEST);
            }
        });

        binding.edtChildren.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!binding.edtChildren.getText().toString().equalsIgnoreCase("")&& !binding.otherDependents.getText().toString().equalsIgnoreCase("")){

                    value1 = binding.edtChildren.getText().toString();
                    value2 = binding.otherDependents.getText().toString();

                    a = Integer.parseInt(value1);
                    b = Integer.parseInt(value2);
                    String sum1 = String.valueOf(a+b);
                    binding.totalDependents.setText(sum1);
                }
                // TODO Auto-generated method stub

            }
        });

        binding.otherDependents.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!binding.edtChildren.getText().toString().equalsIgnoreCase("")&& !binding.otherDependents.getText().toString().equalsIgnoreCase("")){

                    value1 = binding.edtChildren.getText().toString();
                    value2 = binding.otherDependents.getText().toString();

                    a = Integer.parseInt(value1);
                    b = Integer.parseInt(value2);
                    String sum1 = String.valueOf(a+b);
                    binding.totalDependents.setText(sum1);
                }
            }
        });

        init();
    }

    private void getIntentParamsData1() {
        if (getIntent().getExtras() != null) {
//            Log.v("I am ","here");
            UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);

            if (model != null) {

                if (model.getData().getPayrollDetails() != null) {

                    PayrollDetails mPayrollDetail = model.getData().getPayrollDetails();
                    binding.bankname.setText(mPayrollDetail.getNameOfAccount());
                    //   binding.edtAccountType.setText(mPayrollDetail.getTypeOfAccount());
                    binding.edtRoutingNumber.setText(mPayrollDetail.getRoutingNumber());
                    binding.edtAccountNumber.setText(mPayrollDetail.getAccountNumber());
                    binding.edtConfirmAccountNumber.setText(mPayrollDetail.getConfirmAcountNumber());
                    binding.tvState.setText(mPayrollDetail.getStateId());
                    binding.tvCity.setText(mPayrollDetail.getCityId());
                    binding.edtZipCode.setText(mPayrollDetail.getZipCode());
                    binding.otherDependents.setText(mPayrollDetail.getOtherdependents());
                    binding.edtChildren.setText(mPayrollDetail.getChildrendependents());
                    binding.address.setText(mPayrollDetail.getAddressLine1());
                    binding.address2.setText(mPayrollDetail.getAddressLine2());
                    binding.apt.setText(mPayrollDetail.getBuilding());
                    //  binding.accountname.setText(mPayrollDetail.getNameofaccount());


                    binding.totalDependents.setText(mPayrollDetail.getTotaldependents());
                    binding.tvLegalEntity.setText(mPayrollDetail.getLegalEntity());
                    binding.tvAddressType.setText(mPayrollDetail.getSendtaxdocument());
                    binding.taxnumber.setText(mPayrollDetail.getTaxpayerIdNumber());

                    if (model.getData().getDocuments() != null) {

                        for (int i = 0; i < model.getData().getDocuments().size(); i++) {
                            if (model.getData().getDocuments().get(i).getType().equalsIgnoreCase("19")) {
                                binding.imgDoc.setVisibility(View.VISIBLE);
                                ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFileUrl(), R.drawable.ic_loading, binding.imgDoc);
                            }
                        }
//                        binding.imgDoc.setVisibility(View.VISIBLE);
//                        int i=0;
//                        ImageUtils.loadImage(context, model.getData().getDocuments().get(i).getFile_url(), R.drawable.ic_loading, binding.imgDoc);

//
                    }


//
                    if (mPayrollDetail.getTypeOfAccount().equalsIgnoreCase("Checking")){
                        binding.rbCheck.setChecked(true);
                    }else{
                        binding.rbSave.setChecked(true);
                    }


                    if (mPayrollDetail.getFilesyourtax().equalsIgnoreCase("Single or Married filling separately")){
                        binding.rbYes.setChecked(true);
                    }else if (mPayrollDetail.getFilesyourtax().equalsIgnoreCase("Married filling jointly or Qualifying widow(er)")){
                        binding.rbNo.setChecked(true);
                    }else {
                        binding.rbUnknown.setChecked(true);
                    }

                    Log.e("hgughs",mPayrollDetail.getFilesyourtax());
                    binding.edtDependents.setText(mPayrollDetail.getDependents());
                }
            }
        }
    }

    private void init() {

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

    private void setAddressTypeSpinner(AppCompatSpinner spinner, List<AddressType> list) {
        AddressType spinnerModel = new AddressType("select");
        list.add(0, spinnerModel);
        ArrayAdapter<AddressType> arrayAdapter = new ArrayAdapter<AddressType>(this, R.layout.spinner_row, list) {
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

    private void setLegalEntitySpinner(AppCompatSpinner spinner, List<LegalEntity> list) {
        LegalEntity spinnerModel = new LegalEntity("select");
        list.add(0, spinnerModel);
        ArrayAdapter<LegalEntity> arrayAdapter = new ArrayAdapter<LegalEntity>(this, R.layout.spinner_row, list) {
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

        if (view.getId() == R.id.rlPriorAddress) {
            if (binding.llPriorAddress.getVisibility() == View.VISIBLE)
                binding.llPriorAddress.setVisibility(View.GONE);
            else {
                binding.llPriorAddress.setVisibility(View.VISIBLE);
            }
        }

        if (view.getId() == R.id.rlPriorAddress1) {
            if (binding.tax.getVisibility() == View.VISIBLE)
                binding.tax.setVisibility(View.GONE);
            else {
                binding.tax.setVisibility(View.VISIBLE);
            }
        }
        if (view.getId() == R.id.tvState) {
            binding.spState.performClick();
        }
        if (view.getId() == R.id.tvAddressType) {
            binding.spAddressType.performClick();
        }
        if (view.getId() == R.id.tvLegalEntity) {
            binding.spLegalEntity.performClick();
        }

        if (view.getId() == R.id.tvPrevious) {
            binding.tvNext.setText("Next");

        }

        if (view.getId() == R.id.rl_social_security) {
            opneCameraGallery(1005);
        }
        if (view.getId() == R.id.tv_next) {

            if (!StringUtils.isNotEmpty(binding.tvState.getText().toString())) {
                errorMessage(binding.getRoot(), "Please select state");


            } else if (!StringUtils.isNotEmpty(binding.tvCity.getText().toString())) {
                errorMessage(binding.getRoot(), "Please select city");

            } else if (!StringUtils.isNotEmpty(binding.edtZipCode.getText().toString())) {
                errorMessage(binding.getRoot(), "Please enter zip code");

            }else if (!StringUtils.isNotEmpty(binding.bankname.getText().toString())){
                errorMessage(binding.getRoot(), "Please enter bank name");
            }
            else if (!StringUtils.isNotEmpty(binding.accountname.getText().toString())){
                errorMessage(binding.getRoot(), "Please enter account holder name");
            }
            else if (!StringUtils.isNotEmpty(binding.edtRoutingNumber.getText().toString())){
                errorMessage(binding.getRoot(), "Please enter bank routing number");
            }
            else if (!StringUtils.isNotEmpty(binding.edtAccountNumber.getText().toString())){
                errorMessage(binding.getRoot(), "Please enter account number");
            }else if (!StringUtils.isNotEmpty(binding.edtConfirmAccountNumber.getText().toString())){
                errorMessage(binding.getRoot(), "Please enter confirm account number");
            }else if (!binding.edtAccountNumber.getText().toString().equals(binding.edtConfirmAccountNumber.getText().toString())){
                errorMessage(binding.getRoot(), "Account Number doesn’t match");
            }
//            else if (!StringUtils.isNotEmpty(binding.tvState1.getText().toString())){
//                errorMessage(binding.getRoot(), "Please select tax document");
//            }else if (!StringUtils.isNotEmpty(binding.tvState2.getText().toString())){
//                errorMessage(binding.getRoot(), "Please select legal entity");
//            }else if (!StringUtils.isNotEmpty(binding.taxnumber.getText().toString())){
//                errorMessage(binding.getRoot(), "Please enter tax number");
//            }
            else{

                if (binding.imgDoc.getTag() != null){
                    presenterInterface.sendRequest(PayrollActivity.this, "", null, ApiCallInterface.upload,binding.imgDoc.getTag() != null ? binding.imgDoc.getTag().toString() : "" );
                }else{
                    payroll();
                }
//
               // payroll();




           // presenterInterface.callAPI(PayrollActivity.this, null, param_detail, ApiCallInterface.STORE_APPLICANT_DETAIL, binding.imgDoc.getTag() != null ? binding.imgDoc.getTag().toString() : "");


            }
        }


    }

    private void opneCameraGallery(int requestCode) {
        new ImagePickerUtils(context, false, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }, requestCode);
    }

    @Override
    public void retry(int pos) {

    }

    @Override
    public void onError(String errorMsg, int requestCode, int resultCode) {

    }

    @Override
    public void onSuccess(Object success, int requestCode, int resultCode) {

        Log.e("response", success.toString());
        if (requestCode == ApiCallInterface.STATES) {
             stateModelList = (List<StateModel>) success;
            if (stateModelList != null) {

                setStatteSpinner(binding.spState, stateModelList);

            }
        }
        if (requestCode == ApiCallInterface.CITY) {
            cityList = (List<CityModel>) success;

        }
        if (requestCode == ApiCallInterface.STORE_APPLICANT_DETAIL) {
//            if (step == 1) {
//                step = 2;
//                binding.tvNext.setText("Submit");
//
//            } else {
//                finish();
//            }

            if (requestCode == ApiCallInterface.STORE_APPLICANT_DETAIL) {
                finish();
            }
        }
        if (requestCode == ApiCallInterface.SELECTION_LIST) {

            SelectionListModel selectionLaseModel = (SelectionListModel) success;
            if (selectionLaseModel.isStatus()){

                addressTypeList=selectionLaseModel.getData().getAddressType();
                legalEntityList=selectionLaseModel.getData().getLegalEntity();
                if (addressTypeList!=null){
                    setAddressTypeSpinner(binding.spAddressType,addressTypeList);
                }
                if (legalEntityList!=null){
                    setLegalEntitySpinner(binding.spLegalEntity,legalEntityList);
                }

            }

        }

        if (requestCode == ApiCallInterface.upload) {
            BaseModel baseModel = (BaseModel) success;
            if (baseModel.isStatus().equals("true")) {
                new CustomAlertDialog(context, baseModel.getMessage(), "Confirm your email address", "OK", "", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                      //  finish();
                       payroll();
                    }
                }).show();


            } else {
                errorMessage(binding.getRoot(), baseModel.getMessage());
            }
        }


    }

    private void payroll() {

        value1 = binding.edtChildren.getText().toString();
        value2 = binding.otherDependents.getText().toString();

        //        int b = Integer.parseInt(value2);


        if (binding.edtChildren.getText().toString().length()==0){
            a =0;
        }else{
            a = Integer.parseInt(value1);
        }
        if (binding.otherDependents.getText().toString().length()==0){
            b =0;
        }else{
            b = Integer.parseInt(value2);
        }

        sum = a+b;
        Log.e("sum", String.valueOf(sum));

//
        //  binding.totalDependents.setText("");

//            if (step == 1) {
        HashMap<String, Object> param = new HashMap<>();
        HashMap<String, Object> param_detail = new HashMap<>();
        param_detail.put("key", "payroll_details");
        param_detail.put("payroll_details", param);
        //  param.put("filesyourtax",  binding.rbYes.isChecked() ? binding.rbYes.getText().toString() : binding.rbNo.getText().toString());

        //param.put("totaldependents", sum);
        param.put("filesTax", sum);

        param.put("accountNumber", binding.edtAccountNumber.getText().toString());
        param.put("nameOfAccount", binding.accountname.getText().toString());
        param.put("legal_entity", binding.tvLegalEntity.getText().toString());
        param.put("routingNumber", binding.edtRoutingNumber.getText().toString());
        param.put("address_line_1", binding.address.getText().toString());
        param.put("building", binding.apt.getText().toString());
        param.put("otherdependents", binding.otherDependents.getText().toString());
        if (binding.rbYes.isChecked()){
            param.put("filesyourtax",  binding.rbYes.isChecked() ? binding.rbYes.getText().toString() : binding.rbNo.getText().toString());
        }else if (binding.rbNo.isChecked()){
            param.put("filesyourtax",  binding.rbYes.isChecked() ? binding.rbYes.getText().toString() : binding.rbNo.getText().toString());
        }else{
            param.put("filesyourtax",  binding.rbUnknown.getText().toString());
        }
        param.put("state_id", binding.tvState.getText().toString());
        param.put("address_line_2", binding.address2.getText().toString());
        param.put("confirmAcountNumber", binding.edtConfirmAccountNumber.getText().toString());
        param.put("nameOfBank", binding.bankname.getText().toString());
        param.put("zip_code", binding.edtZipCode.getText().toString());
        param.put("dependents", binding.edtDependents.getText().toString());
        param.put("childrendependents", binding.edtChildren.getText().toString());
        param.put("city_id", binding.tvCity.getText().toString());
        param.put("send_tax_doc", address_type_Code);
        param.put("totaldependents", binding.totalDependents.getText().toString());
        param.put("sendtaxdocument", binding.tvAddressType.getText().toString());
        param.put("typeOfAccount", binding.rbCheck.isChecked() ? binding.rbCheck.getText().toString() : binding.rbSave.getText().toString());
        param.put("taxpayer_id_number", binding.taxnumber.getText().toString());
        Log.e("param_detail",param_detail.toString());
        presenterInterface.callAPI(PayrollActivity.this, null, param_detail, ApiCallInterface.STORE_APPLICANT_DETAIL);
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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1008 && resultCode == Activity.RESULT_OK) {
//            assert data != null;
//            if (data.hasExtra("path")) {
//                Uri uri = data.getParcelableExtra("path");
//                if (uri != null) {
//
//                    binding.imgDoc.setVisibility(View.VISIBLE);
//                    binding.imgDoc.setTag(uri.getPath());
//                    binding.imgDoc.setImageURI(uri);
//
//                }
//            }
//
//
//
//        }
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == PLACE_REQUEST) {
                if (resultCode == RESULT_OK) {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    binding.address.setText(place.getAddress());
                    binding.tvCity.setText(Utility.getCity(PayrollActivity.this, place.getLatLng()));
                    binding.tvState.setText(Utility.getState(PayrollActivity.this, place.getLatLng()));
                    binding.edtZipCode.setText(Utility.getPostalCode(PayrollActivity.this, place.getLatLng()));

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


            if (requestCode == 1008 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                if (uri != null) {

                    binding.imgDoc.setVisibility(View.VISIBLE);
                    binding.imgDoc.setTag(uri.getPath());
                    binding.imgDoc.setImageURI(uri);

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

    private String capitizeString(String name){
        String captilizedString="";
        if(!name.trim().equals("")){
            captilizedString = name.substring(0,1).toUpperCase() + name.substring(1);
        }
        return captilizedString;
    }
}