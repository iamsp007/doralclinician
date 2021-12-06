package com.android.doral;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.android.CovidFormActivity;
import com.android.Vendor.Activity.VendorDashboardActivity;
import com.android.doral.Utils.DateUtils;
import com.android.doral.Utils.ImageUtils;
import com.android.doral.Utils.MyPref;
import com.android.doral.Utils.StringUtils;
import com.android.doral.Utils.Utility;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseFragment;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityCovid20Binding;
import com.android.doral.dialog.SignatureDialog;
import com.android.doral.dialog.TimerDialogDialog;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.CovidFormModel;
import com.android.doral.retrofit.model.MainCovidFormModel;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Covid20 extends BaseFragment implements BaseViewInterface {
    ActivityCovid20Binding binding;

    private String administatite_site = "";
    BasePresenterInterface presenterInterface;
    ViewPager viewPager;


    public Covid20(ViewPager viewPager) {
        // Required empty public constructor
        this.viewPager = viewPager;
    }

    // TODO: Rename and change types and number of parameters
    public static Covid20 newInstance(ViewPager viewPager) {
        Covid20 fragment = new Covid20(viewPager);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = ActivityCovid20Binding.inflate(getLayoutInflater());
        presenterInterface = new Presenter(this);
        binding.rlBottom.imgNext.setVisibility(View.GONE);
        binding.rlBottom.imgPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });
        binding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isValidate()) {

                    ((CovidFormActivity) getActivity()).formModel.setAdministration_site(administatite_site);

                    RadioButton radioButton = getActivity().findViewById(binding.rbGroup.getCheckedRadioButtonId());
                    ((CovidFormActivity) getActivity()).formModel.setDosage(radioButton.getText().toString());
                    ((CovidFormActivity) getActivity()).formModel.setIs_accept_term(true);

                    MainCovidFormModel mainCovidFormModel = new MainCovidFormModel();

                    mainCovidFormModel.setTime("" + ((CovidFormActivity) getActivity()).filling_date_time);
                    mainCovidFormModel.setUser_id(new MyPref(getActivity()).getUserData().getId());
                    mainCovidFormModel.setData(((CovidFormActivity) getActivity()).formModel);
                    mainCovidFormModel.setStatus("1");
                    mainCovidFormModel.setDose(((CovidFormActivity) getActivity()).formModel.getVaccine_type());
                    mainCovidFormModel.setPatient_name(((CovidFormActivity) getActivity()).formModel.getRecipient_name());
                    mainCovidFormModel.setPhone(((CovidFormActivity) getActivity()).formModel.getPhone());
                    mainCovidFormModel.setVaccine_name(((CovidFormActivity) getActivity()).formModel.getVaccine_name());


                    Log.e("Data-->", new Gson().toJson(mainCovidFormModel, MainCovidFormModel.class));

                    presenterInterface.sendRequest(getActivity(), new Gson().toJson(mainCovidFormModel, MainCovidFormModel.class), null, ApiCallInterface.SUBMIT_COVID_FORM);


                }

            }
        });

        binding.rlSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new SignatureDialog(getActivity(), new SignatureDialog.OnsaveClickListener() {
                    @Override
                    public void onItemClick(String filepath) {
                        binding.imgSignature.setVisibility(View.VISIBLE);
                        ImageUtils.loadImage(getActivity(), filepath, R.drawable.white_logo, binding.imgSignature);
                    }
                }).show();
            }
        });
        binding.rbLeftD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChecked(binding.rbLeftD);
            }
        });

        binding.rbRightD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChecked(binding.rbRightD);
            }
        });
        binding.rbLeftT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChecked(binding.rbLeftT);
            }
        });
        binding.rbRightT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChecked(binding.rbRightT);
            }
        });
        return binding.getRoot();
    }

    private void setChecked(RadioButton button) {
        binding.rbLeftD.setChecked(false);
        binding.rbLeftT.setChecked(false);
        binding.rbRightD.setChecked(false);
        binding.rbRightT.setChecked(false);
        button.setChecked(true);
        administatite_site = button.getText().toString();

    }


    private boolean isValidate() {

        if (!StringUtils.isNotEmpty(administatite_site)) {
            errorMessage(binding.getRoot(), "Please select administration site");
            return false;
        } else if (binding.rbGroup.getCheckedRadioButtonId() == -1) {
            errorMessage(binding.getRoot(), "Please select dosage");
            return false;
        } else if (!binding.termCondition.isChecked()) {
            errorMessage(binding.getRoot(), "Please select term & condition");
            return false;
        }

        return true;
    }

    @Override
    public void retry(int pos) {

    }

    @Override
    public void onError(String errorMsg, int requestCode, int resultCode) {

    }

    @Override
    public void onSuccess(Object success, int requestCode, int resultCode) {


        if (requestCode == ApiCallInterface.SUBMIT_COVID_FORM) {

            MainCovidFormModel mainCovidFormModel = (MainCovidFormModel) success;

            if (mainCovidFormModel.isStatus().equalsIgnoreCase("true")) {


                new MyPref(getActivity()).setData(MyPref.Keys.FORM_ID, "" + mainCovidFormModel.getData().getId());
                HashMap<String, String> map = new HashMap<>();
                map.put("id", mainCovidFormModel.getData().getId());

                if (StringUtils.isNotEmpty(Utility.recipient_sign) || StringUtils.isNotEmpty(Utility.interpreter_sign)) {
                    presenterInterface.sendRequest(getActivity(), "", map, ApiCallInterface.STORE_FORM_SIGNATURE, Utility.recipient_sign, Utility.interpreter_sign, Utility.vaccination_sign);
                } else {

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.DATE_FORMATE_4);
                    try {
                        Date date = simpleDateFormat.parse(((CovidFormActivity) getActivity()).filling_date_time);

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        calendar.add(Calendar.MINUTE, 15);
                        new MyPref(getActivity()).setData(MyPref.Keys.COVID_TIME, "" + calendar.getTimeInMillis());
                        new TimerDialogDialog(getActivity(), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (view.getId() == R.id.tv_ok) {
                                    getActivity().finish();
                                    startActivity(new Intent(getActivity(), CovidFormActivity.class));

                                }


                   /* new SignatureDialog(getActivity(), new SignatureDialog.OnsaveClickListener() {
                        @Override
                        public void onItemClick(String filepath) {
                            HashMap<String, String> map = new HashMap<>();
                            map.put("id", new MyPref(getActivity()).getData(MyPref.Keys.FORM_ID));
                            presenterInterface.sendRequest(getActivity(), "", map, ApiCallInterface.STORE_FORM_SIGNATURE_1, filepath);
                        }
                    }).show();*/


                            }
                        }).show();

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }


            }
        }
        if (requestCode == ApiCallInterface.STORE_FORM_SIGNATURE) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, 15);

            new MyPref(getActivity()).setData(MyPref.Keys.COVID_TIME, "" + calendar.getTimeInMillis());
            new TimerDialogDialog(getActivity(), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view.getId() == R.id.tv_ok) {
                        getActivity().finish();
                        startActivity(new Intent(getActivity(), CovidFormActivity.class));

                    }


                   /* new SignatureDialog(getActivity(), new SignatureDialog.OnsaveClickListener() {
                        @Override
                        public void onItemClick(String filepath) {
                            HashMap<String, String> map = new HashMap<>();
                            map.put("id", new MyPref(getActivity()).getData(MyPref.Keys.FORM_ID));
                            presenterInterface.sendRequest(getActivity(), "", map, ApiCallInterface.STORE_FORM_SIGNATURE_1, filepath);
                        }
                    }).show();*/


                }
            }).show();
        }

        if (requestCode == ApiCallInterface.STORE_FORM_SIGNATURE_1) {
            getActivity().finish();
        }
    }
}