package com.android.doral;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.android.CovidFormActivity;
import com.android.doral.Utils.DateUtils;
import com.android.doral.Utils.StringUtils;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseFragment;
import com.android.doral.databinding.ActivityCovidStepOneBinding;
import com.android.doral.fragement.AddressDetailsFragment;
import com.android.doral.retrofit.model.CovidFormModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class Covid_Step_One_Activity extends BaseFragment {
    ActivityCovidStepOneBinding binding;
    DatePickerDialog datePickerDialog;
    private String birth_date;
    private final SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    HashMap<String, String> map = new HashMap();
    private String marital_status = "";
    private String gender = "";
    private String sex = "";
    private String ethncity = "";

    ViewPager viewPager;


    public Covid_Step_One_Activity(ViewPager viewPager) {
        // Required empty public constructor
        this.viewPager = viewPager;
    }

    // TODO: Rename and change types and number of parameters
    public static Covid_Step_One_Activity newInstance(ViewPager viewPager) {
        Covid_Step_One_Activity fragment = new Covid_Step_One_Activity(viewPager);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ActivityCovidStepOneBinding.inflate(getLayoutInflater());

        binding.bottom.imgPrevious.setVisibility(View.GONE);
        binding.bottom.imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidate()) {
                    ((CovidFormActivity) getActivity()).formModel.setRecipient_name(binding.edtRecipentName.getText().toString().trim());

                    ((CovidFormActivity) getActivity()).formModel.setPreferred_name(binding.edtRecipentName.getText().toString().trim());
                    ((CovidFormActivity) getActivity()).formModel.setDOB(birth_date);
                    ((CovidFormActivity) getActivity()).formModel.setMarital_staus(marital_status);
                    ((CovidFormActivity) getActivity()).formModel.setEthnicity(ethncity);
                    ((CovidFormActivity) getActivity()).formModel.setGender_id(gender);
                    ((CovidFormActivity) getActivity()).formModel.setSex_at_birth(sex);

                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);

                }
            }
        });

        binding.tvDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        /*birth_date = i + "/" + (i1 + 1) + "/" + i2;
                        binding.activitySignUpTvDOB.setText(birth_date);*/

                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        birth_date = dateformat.format(calendar.getTime());
                        binding.tvDob.setText(DateUtils.changeDateFormat(DateUtils.DATE_FORMATE_8, DateUtils.DATE_FORMATE_1, dateformat.format(calendar.getTime()), false));

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                Calendar calendar1 = Calendar.getInstance();
                calendar1.add(Calendar.YEAR, -18);
                datePickerDialog.getDatePicker().setMaxDate(calendar1.getTimeInMillis());
                datePickerDialog.show();
            }
        });
        binding.spMaritalStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0) {
                    marital_status = getResources().getStringArray(R.array.marital_status_value)[i];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0) {
                    gender = getResources().getStringArray(R.array.gender_sp_value)[i];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.spEthinicity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0) {
                    ethncity = getResources().getStringArray(R.array.ethencity_value)[i];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.spSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0) {
                    sex = getResources().getStringArray(R.array.sex_sp_value)[i];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return binding.getRoot();
    }


    private boolean isValidate() {
        if (!StringUtils.isNotEmpty(binding.edtRecipentName.getText().toString().trim())) {
            errorMessage(binding.getRoot(), "Please enter recipient name");
            return false;
//        }
//        else if (!StringUtils.isNotEmpty(binding.edtPreferedName.getText().toString().trim())) {
//            errorMessage(binding.getRoot(), "Please enter preferred name");
//            return false;

        } else if (!StringUtils.isNotEmpty(birth_date)) {
            errorMessage(binding.getRoot(), "Please select birthdate");
            return false;
        } else if (binding.spMaritalStatus.getSelectedItemPosition() == 0) {
            errorMessage(binding.getRoot(), "Please select marital status");
            return false;
        } else if (binding.spGender.getSelectedItemPosition() == 0) {
            errorMessage(binding.getRoot(), "Please select gender");
            return false;
        } else if (binding.spEthinicity.getSelectedItemPosition() == 0) {
            errorMessage(binding.getRoot(), "Please select ethinicity");
            return false;
        } else if (binding.spSex.getSelectedItemPosition() == 0) {
            errorMessage(binding.getRoot(), "Please select sexuality");
            return false;
        }


        return true;
    }
}
