package com.android.doral;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.doral.Utils.PhoneTextFormatter;
import com.android.doral.Utils.StringUtils;
import com.android.doral.adapter.BoardCertificateAdapter;
import com.android.doral.adapter.CompanyAdapter;
import com.android.doral.adapter.StateLicenseAdapter;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityProfessionalBinding;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.BaseModel;
import com.android.doral.retrofit.model.BoardCertificate;
import com.android.doral.retrofit.model.CertifyingModel;
import com.android.doral.retrofit.model.EmergencyDataModel;
import com.android.doral.retrofit.model.ReferenceModel;
import com.android.doral.retrofit.model.StateLicense;
import com.android.doral.retrofit.model.StateModel;
import com.android.doral.retrofit.model.UserApplicationDetails.UserApplicationDetailsModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfessionalActivity extends BaseActivity implements BaseViewInterface, View.OnClickListener {
    ActivityProfessionalBinding binding;
    RadioButton rbMedicare, rbMedicId;
    BasePresenterInterface presenterInterface;
    boolean isCheck09, isCheckCb1013, isCheckCb1421, isCheckCb2240,isCheckCb4165, isCheckCb65Plus;
    private String state_code_medicare = "", state_code_medicaId = "", state_code_licenses = "";
    ArrayList<String> ageRange = new ArrayList<>();
    ArrayList<StateLicense> stateLicenseList = new ArrayList<>();
    ArrayList<BoardCertificate> boardCertificates = new ArrayList<>();
    StateLicenseAdapter stateLicenseAdapter;
    BoardCertificateAdapter boardCertificateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfessionalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.tvTitle.setText("Application");
        binding.toolbar.imgBack.setVisibility(View.VISIBLE);
        binding.toolbar.imgBack.setOnClickListener(this);

        binding.rbMedicareEnrolledNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.edtMedicareNumber.setText("");
                    binding.edtMedicareNumber.setEnabled(false);
                } else {
                    binding.edtMedicareNumber.setEnabled(true);
                }
            }
        });
        binding.rbMedicaidEnrolledNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.edtMedicidNumber.setText("");
                    binding.edtMedicidNumber.setEnabled(false);
                } else {
                    binding.edtMedicidNumber.setEnabled(true);
                }
            }
        });
        init();
        getIntentParamsDataOtherUser();
    }

    private void getIntentParamsDataOtherUser() {

        UserApplicationDetailsModel model = new Gson().fromJson(getIntent().getStringExtra("model"), UserApplicationDetailsModel.class);


        if (model != null) {

            if (model.getData().getProfessionalDetail() != null) {

                if (model.getData().getProfessionalDetail().getMedicareEnrolled().equals("true")){
                    binding.rbMedicareEnrolledYes.setChecked(true);
                    binding.tvStateMedicare.setText(model.getData().getProfessionalDetail().getMedicareEnrolledStateId());
                    binding.edtMedicareNumber.setText(model.getData().getProfessionalDetail().getMedicareEnrolledNumber());
                }else{
                    binding.rbMedicareEnrolledNo.setChecked(true);
                }

                if (model.getData().getProfessionalDetail().getMedicaidEnrolled().equals("true")){
                    binding.rbMedicaidEnrolledYes.setChecked(true);
                    binding.tvMedicaidState.setText(model.getData().getProfessionalDetail().getMedicaidEnrolledStateId());
                    binding.edtMedicidNumber.setText(model.getData().getProfessionalDetail().getMedicaidEnrolledNumber());


                }else{
                    binding.rbMedicaidEnrolledNo.setChecked(true);
                }


                if (model.getData().getProfessionalDetail().getAge09().equals("true")){
                    binding.cb09.setChecked(true);
                }else if (model.getData().getProfessionalDetail().getAge1013().equals("true")){
                    binding.cb1013.setChecked(true);
                }else if (model.getData().getProfessionalDetail().getAge1421().equals("true")){
                    binding.cb1421.setChecked(true);
                }else if (model.getData().getProfessionalDetail().getAge2240().equals("true")){
                    binding.cb2240.setChecked(true);
                }else if (model.getData().getProfessionalDetail().getAge4165().equals("true")){
                    binding.cb4165.setChecked(true);
                }else{
                    binding.cb65Plus.setChecked(true);
                }

                if (model.getData().getProfessionalDetail().getStateLicense()!=null){
                    for (int i = 0; i <model.getData().getProfessionalDetail().getStateLicense().size() ; i++) {

                        StateLicense emergencyDataModel=new StateLicense();
                        emergencyDataModel.setState(model.getData().getProfessionalDetail().getStateLicense().get(i).getState());
                        emergencyDataModel.setNumber(model.getData().getProfessionalDetail().getStateLicense().get(i).getNumber());
                        stateLicenseList.add(emergencyDataModel);

                    }

                    binding.rvStateLicence.getAdapter().notifyDataSetChanged();
                }

                if (model.getData().getProfessionalDetail().getBoardCertificate()!=null){
                    for (int i = 0; i <model.getData().getProfessionalDetail().getBoardCertificate().size() ; i++) {

                        BoardCertificate emergencyDataModel=new BoardCertificate();
                        emergencyDataModel.setCertificate(model.getData().getProfessionalDetail().getBoardCertificate().get(i).getCertificate());
                        emergencyDataModel.setStatus(model.getData().getProfessionalDetail().getBoardCertificate().get(i).getStatus());
                        boardCertificates.add(emergencyDataModel);

                    }

                    binding.rvBoardCertificate.getAdapter().notifyDataSetChanged();
                }





                binding.edtFederalID.setText(model.getData().getProfessionalDetail().getFederalDEAId());


//
            }

        }
    }

    private void init() {

        presenterInterface = new Presenter(this);
        presenterInterface.sendRequest(this, null, null, ApiCallInterface.STATES);
        presenterInterface.sendRequest(this, null, null, ApiCallInterface.GET_CERTIFYING_BOARD);
        presenterInterface.sendRequest(this, null, null, ApiCallInterface.GET_CERTIFYING_BOARD_STATUS);

        binding.tvStateMedicare.setOnClickListener(this);
        binding.tvMedicaidState.setOnClickListener(this);
        binding.tvStateLicense.setOnClickListener(this);
        binding.tvCertifyingBoard.setOnClickListener(this);
        binding.tvCertifyingStatus.setOnClickListener(this);
        binding.tvSubmit.setOnClickListener(this);
        binding.tvAddLicense.setOnClickListener(this);
        binding.tvAddBoardCertification.setOnClickListener(this);

        stateLicenseAdapter = new StateLicenseAdapter(ProfessionalActivity.this,stateLicenseList);
        binding.rvStateLicence.setLayoutManager(new LinearLayoutManager(context));
        binding.rvStateLicence.setAdapter(stateLicenseAdapter);

        boardCertificateAdapter = new BoardCertificateAdapter(ProfessionalActivity.this,boardCertificates);
        binding.rvBoardCertificate.setLayoutManager(new LinearLayoutManager(context));
        binding.rvBoardCertificate.setAdapter(boardCertificateAdapter);

        binding.edtMedicareNumber.addTextChangedListener(new PhoneTextFormatter(binding.edtMedicareNumber, "####-###-####"));

        binding.rgMedicare.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                rbMedicare = (RadioButton) findViewById(binding.rgMedicare.getCheckedRadioButtonId());
                if (rbMedicare.getText().toString().equals("Yes")) {

                    binding.lnMedicare.setVisibility(View.VISIBLE);

                } else {

                    binding.lnMedicare.setVisibility(View.GONE);

                }
            }
        });

        binding.rgMedicId.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                rbMedicId = (RadioButton) findViewById(binding.rgMedicId.getCheckedRadioButtonId());
                if (rbMedicId.getText().toString().equals("Yes")) {

                    binding.lnMedicId.setVisibility(View.VISIBLE);

                } else {

                    binding.lnMedicId.setVisibility(View.GONE);

                }
            }
        });

        binding.spStateMedicare.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0) {

                    state_code_medicare = ((StateModel) binding.spStateMedicare.getSelectedItem()).getId();
                    binding.tvStateMedicare.setText(((StateModel) binding.spStateMedicare.getSelectedItem()).getState());

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spMedicaidState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0) {

                    state_code_medicaId = ((StateModel) binding.spMedicaidState.getSelectedItem()).getId();
                    binding.tvMedicaidState.setText(((StateModel) binding.spMedicaidState.getSelectedItem()).getState());

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spStateLicense.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0) {

                    state_code_licenses = ((StateModel) binding.spStateLicense.getSelectedItem()).getId();
                    binding.tvStateLicense.setText(((StateModel) binding.spStateLicense.getSelectedItem()).getState());

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spCertifyingBoard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0) {

                    binding.tvCertifyingBoard.setText((binding.spCertifyingBoard.getSelectedItem().toString()));

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spCertifyingStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0) {

                    binding.tvCertifyingStatus.setText((binding.spCertifyingStatus.getSelectedItem().toString()));

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        binding.cb09.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (compoundButton.isChecked()) {

                    ageRange.add("0-9");
                    isCheck09 = true;
                    binding.cb1013.setChecked(false);
                    binding.cb1421.setChecked(false);
                    binding.cb2240.setChecked(false);
                    binding.cb4165.setChecked(false);
                    binding.cb65Plus.setChecked(false);

                } else {

                    ageRange.remove("0-9");
                    isCheck09 = false;

                }
            }
        });

        binding.cb1013.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (compoundButton.isChecked()) {

                    ageRange.add("10-13");
                    isCheckCb1013 = true;
                    binding.cb09.setChecked(false);
                    binding.cb1421.setChecked(false);
                    binding.cb2240.setChecked(false);
                    binding.cb4165.setChecked(false);
                    binding.cb65Plus.setChecked(false);

                } else {

                    ageRange.remove("10-13");
                    isCheckCb1013 = false;

                }
            }
        });

        binding.cb1421.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (compoundButton.isChecked()) {
                    ageRange.add("14-21");
                    isCheckCb1421 = true;
                    binding.cb09.setChecked(false);
                    binding.cb1013.setChecked(false);
                    binding.cb2240.setChecked(false);
                    binding.cb4165.setChecked(false);
                    binding.cb65Plus.setChecked(false);

                } else {
                    ageRange.remove("14-21");
                    isCheckCb1421 = false;
                }
            }
        });
        binding.cb2240.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (compoundButton.isChecked()) {
                    ageRange.add("22-40");
                    isCheckCb2240 = true;
                    binding.cb09.setChecked(false);
                    binding.cb1013.setChecked(false);
                    binding.cb1421.setChecked(false);
                    binding.cb4165.setChecked(false);
                    binding.cb65Plus.setChecked(false);

                } else {
                    ageRange.remove("22-40");
                    isCheckCb2240 = false;
                }
            }
        });

        binding.cb4165.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (compoundButton.isChecked()) {
                    ageRange.add("65+");
                    isCheckCb4165 = true;
                    binding.cb09.setChecked(false);
                    binding.cb1013.setChecked(false);
                    binding.cb2240.setChecked(false);
                    binding.cb1421.setChecked(false);
                    binding.cb65Plus.setChecked(false);

                } else {
                    ageRange.remove("65+");
                    isCheckCb4165 = false;

                }
            }
        });

        binding.cb65Plus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (compoundButton.isChecked()) {
                    ageRange.add("65+");
                    isCheckCb65Plus = true;
                    binding.cb09.setChecked(false);
                    binding.cb1013.setChecked(false);
                    binding.cb2240.setChecked(false);
                    binding.cb1421.setChecked(false);
                    binding.cb4165.setChecked(false);

                } else {
                    ageRange.remove("65+");
                    isCheckCb65Plus = false;

                }
            }
        });

    }

    private boolean validate() {

        rbMedicare = (RadioButton) findViewById(binding.rgMedicare.getCheckedRadioButtonId());
        rbMedicId = (RadioButton) findViewById(binding.rgMedicId.getCheckedRadioButtonId());

        if (rbMedicare.getText().toString().equals("Yes")) {


            if (binding.tvStateMedicare.getText().toString().equalsIgnoreCase("select")) {

                errorMessage(binding.getRoot(), "please select medicare enrolled state");
                return false;

            }
            if (binding.edtMedicareNumber.getText().toString().equalsIgnoreCase("")) {

                errorMessage(binding.getRoot(), "please enter medicare number");
                return false;
            }

        }

        if (rbMedicId.getText().toString().equals("Yes")) {

            if (binding.tvMedicaidState.getText().toString().equalsIgnoreCase("select")) {

                errorMessage(binding.getRoot(), "please select medicaid enrolled state");
                return false;

            }
            if (binding.edtMedicidNumber.getText().toString().equalsIgnoreCase("")) {

                errorMessage(binding.getRoot(), "please enter medicaid number");
                return false;

            }

        }

        if (ageRange.size() <= 0) {

            errorMessage(binding.getRoot(), "please select age range treated");
            return false;

        }



        if (stateLicenseList.size() <= 0) {

            errorMessage(binding.getRoot(), "please add a license");
            return false;

        }


        if (boardCertificates.size() <= 0) {

            errorMessage(binding.getRoot(), "please add a board certification");
            return false;

        }

        if (binding.edtFederalID.getText().toString().equalsIgnoreCase("")) {
            errorMessage(binding.getRoot(), "please enter federal DEA ID");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tvStateMedicare) {

            binding.spStateMedicare.performClick();

        } else if (view.getId() == R.id.tvMedicaidState) {

            binding.spMedicaidState.performClick();

        } else if (view.getId() == R.id.tvStateLicense) {

            binding.spStateLicense.performClick();

        } else if (view.getId() == R.id.tvCertifyingBoard) {

            binding.spCertifyingBoard.performClick();

        } else if (view.getId() == R.id.tvCertifyingStatus) {

            binding.spCertifyingStatus.performClick();

        } else if (view.getId() == R.id.tvSubmit) {

            if (validate()) {

                HashMap<String, Object> map = new HashMap<>();
                if (rbMedicare.getText().toString().equals("Yes")) {

                    map.put("medicareEnrolled", "true");

                } else {

                    map.put("medicareEnrolled", "false");

                }

                if (rbMedicId.getText().toString().equals("Yes")) {

                    map.put("medicaidEnrolled", "true");

                } else {

                    map.put("medicaidEnrolled", "false");

                }

                map.put("medicareEnrolled_StateId", binding.tvStateMedicare.getText().toString());
                map.put("medicareEnrolled_Number", binding.edtMedicareNumber.getText().toString());
                map.put("medicaidEnrolled_StateId", binding.tvMedicaidState.getText().toString());
                map.put("medicaidEnrolled_Number", binding.edtMedicidNumber.getText().toString());
                map.put("federal_DEA_id", binding.edtFederalID.getText().toString());
                map.put("age_0_9", ""+isCheck09);
                map.put("age_10_13", ""+isCheckCb1013);
                map.put("age_14_21", ""+isCheckCb1421);
                map.put("age_22_40", ""+isCheckCb2240);
                map.put("age_41_65", ""+isCheckCb4165);
                map.put("age_65Plus", ""+isCheckCb65Plus);

                map.put("stateLicense", stateLicenseList);
                map.put("boardCertificate", boardCertificates);

               /* HashMap<String, Object> stateLicense = new HashMap<>();
                HashMap<String, Object> stateLicenseValue = new HashMap<>();*/
               /* Gson gson = new GsonBuilder().create();
                JsonArray myCustomArray = gson.toJsonTree(stateLicenseList).getAsJsonArray();
                Log.e("myCustomArray",myCustomArray.toString());*/

                HashMap<String, Object> mainParam = new HashMap<>();
                mainParam.put("key", "professional_detail");
                mainParam.put("professional_detail",map);

                /*if (stateLicenseList.size() > 0) {

                    for (int i = 0; i < stateLicenseList.size(); i++) {
                        map.put("stateLicense[" + i + "][StateID]", stateLicenseList.get(i).getStateID());
                        map.put("stateLicense[" + i + "][State]", stateLicenseList.get(i).getState());
                        map.put("stateLicense[" + i + "][Number]", stateLicenseList.get(i).getNumber());

                    }

                }

                if (boardCertificates.size() > 0) {

                    for (int i = 0; i < boardCertificates.size(); i++) {
                        map.put("boardCertificate[" + i + "][certificate]", boardCertificates.get(i).getCertificate());
                        map.put("boardCertificate[" + i + "][status]", boardCertificates.get(i).getStatus());
                    }

                }*/

                presenterInterface.callAPI(ProfessionalActivity.this, null, mainParam, ApiCallInterface.STORE_APPLICANT_DETAIL);
                //presenterInterface.sendRequest(this, null, map, ApiCallInterface.ADD_CERTIFICATE);
                Log.e("professional data", mainParam.toString());

            }

        } else if (view.getId() == R.id.tvAddLicense) {

            if (binding.tvStateLicense.getText().toString().equalsIgnoreCase("select")) {
                errorMessage(binding.getRoot(), "please select state licenses");
            }else if (binding.edtLicenseNumber.getText().toString().equalsIgnoreCase("")) {
                errorMessage(binding.getRoot(), "please enter state licenses number");
            }else {
                if (!binding.edtLicenseNumber.getText().toString().trim().equals("")){

                    StateLicense stateLicense = new StateLicense();
                    stateLicense.setStateID(state_code_licenses);
                    stateLicense.setState(binding.tvStateLicense.getText().toString());
                    stateLicense.setNumber(binding.edtLicenseNumber.getText().toString());
                    stateLicenseList.add(stateLicense);
                    stateLicenseAdapter.notifyDataSetChanged();
                    binding.edtLicenseNumber.getText().clear();
                    binding.spStateLicense.setSelection(0);
                    binding.tvStateLicense.setText("select");
                    errorMessage(binding.getRoot(), "Add state license successfully");
                    Log.e("getDataInString", getDataInString());

                }
            }



        } else if (view.getId() == R.id.tvAddBoardCertification) {

            if (binding.tvCertifyingBoard.getText().toString().equalsIgnoreCase("select")) {
                errorMessage(binding.getRoot(), "please select certifying board");
            }else if (binding.tvCertifyingStatus.getText().toString().equalsIgnoreCase("select")) {
                errorMessage(binding.getRoot(), "please select certifying board status");
            }else {

                if (!binding.tvCertifyingStatus.getText().toString().trim().equals("select")){

                    BoardCertificate boardCertificate = new BoardCertificate();
                    boardCertificate.setCertificate(binding.tvCertifyingBoard.getText().toString());
                    boardCertificate.setStatus(binding.tvCertifyingStatus.getText().toString());
                    boardCertificates.add(boardCertificate);
                    errorMessage(binding.getRoot(), "Add board certificate successfully");

                    boardCertificateAdapter.notifyDataSetChanged();
                    binding.tvCertifyingBoard.setText("select");
                    binding.spCertifyingBoard.setSelection(0);
                    binding.tvCertifyingStatus.setText("select");
                    binding.spCertifyingStatus.setSelection(0);

                }

            }

        }
        if (view.getId() == R.id.img_back) {

            onBackPressed();

        }
    }

    public String getDataInString() {
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < ageRange.size(); i++) {
            map.put("age_ranges[" + i + "]age_range_treated]", ageRange.get(i).toString());
        }
        return map.toString();
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

                List<StateModel> stateList=new ArrayList<>();
                StateModel spinnerModel = new StateModel("select");
                stateList.add(0, spinnerModel);
                stateList.addAll(stateModelList);

                setStateSpinner(binding.spStateMedicare, stateList);
                setStateSpinner(binding.spMedicaidState, stateList);
                setStateSpinner(binding.spStateLicense, stateList);

            }

        }
        if (requestCode == ApiCallInterface.GET_CERTIFYING_BOARD) {

            CertifyingModel certifyingModel = (CertifyingModel) success;
            setCertifyingBoardSpinner(binding.spCertifyingBoard, certifyingModel.getData());

        }
        if (requestCode == ApiCallInterface.GET_CERTIFYING_BOARD_STATUS) {

            CertifyingModel certifyingModel = (CertifyingModel) success;
            setCertifyingBoardSpinner(binding.spCertifyingStatus, certifyingModel.getData());

        }
        if (requestCode == ApiCallInterface.STORE_APPLICANT_DETAIL) {

            BaseModel basemodel = (BaseModel) success;

            if (basemodel.isStatus().equals("true")) {

                errorMessage(binding.getRoot(), basemodel.getMessage());
                finish();

            } else {

                errorMessage(binding.getRoot(), basemodel.getMessage());

            }

        }

    }

    private void setCertifyingBoardSpinner(AppCompatSpinner spinner, List<String> list) {

        list.add(0, "select");

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

    private void setStateSpinner(AppCompatSpinner spinner, List<StateModel> list) {

       /* StateModel spinnerModel = new StateModel("select");
        list.add(0, spinnerModel);*/

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

    public void deleteStateLicenceItem(int position){
        stateLicenseList.remove(position);
        stateLicenseAdapter.notifyDataSetChanged();
    }

    public void deleteBoardCertificateItem(int position){
        boardCertificates.remove(position);
        boardCertificateAdapter.notifyDataSetChanged();
    }
}