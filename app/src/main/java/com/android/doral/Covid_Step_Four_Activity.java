package com.android.doral;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.android.CovidFormActivity;
import com.android.doral.Utils.DateUtils;
import com.android.doral.Utils.PhoneTextFormatter;
import com.android.doral.Utils.StringUtils;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseFragment;
import com.android.doral.databinding.ActivityCovidStepFourBinding;
import com.android.doral.retrofit.model.CovidFormModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class Covid_Step_Four_Activity extends BaseFragment {
    ActivityCovidStepFourBinding binding;
    DatePickerDialog datePickerDialog;
    private String birth_date;
    private String birth_date_second;
    private final SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());



    ViewPager viewPager;


    public Covid_Step_Four_Activity(ViewPager viewPager) {
        // Required empty public constructor
        this.viewPager = viewPager;
    }

    // TODO: Rename and change types and number of parameters
    public static Covid_Step_Four_Activity newInstance(ViewPager viewPager) {
        Covid_Step_Four_Activity fragment = new Covid_Step_Four_Activity(viewPager);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = ActivityCovidStepFourBinding.inflate(getLayoutInflater());

        binding.rlBottom.imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* if (isValidate() && binding.llSecondary.getVisibility() == View.GONE) {
                    startActivity(new Intent(Covid_Step_Four_Activity.this, Covid_Step_Six_Activity.class));
                } else if (isValidatesecondary() && binding.llSecondary.getVisibility() == View.VISIBLE && isValidate()) {
                    startActivity(new Intent(Covid_Step_Four_Activity.this, Covid_Step_Six_Activity.class));
                }*/

                ((CovidFormActivity) getActivity()).formModel.setPrimary_insurance_name(binding.elInsuranceName.getText().toString().trim());
                ((CovidFormActivity) getActivity()).formModel.setPrimary_insurance_id(binding.etPrimaryInsuranceId.getText().toString().trim());
                ((CovidFormActivity) getActivity()).formModel.setPrimary_subscriber_dob(birth_date);
                ((CovidFormActivity) getActivity()).formModel.setPrimary_relation_patient(binding.etSubscriberRelPatient.getSelectedItemPosition() == 0 ? "" : binding.etSubscriberRelPatient.getSelectedItem().toString().toString().trim());
                ((CovidFormActivity) getActivity()).formModel.setPrimary_insurance_address(binding.etClPrimaryInsuranceAddress.getText().toString().trim());
                ((CovidFormActivity) getActivity()).formModel.setPrimary_insurance_group(binding.etPrimaryInsuranceGroup.getText().toString().trim());
                ((CovidFormActivity) getActivity()).formModel.setPrimary_insurance_phone(binding.etPriamryInsurancePhone.getText().toString().trim());


                ((CovidFormActivity) getActivity()).formModel.setSecondary_insurance_name(binding.elInsuranceNameSecond.getText().toString().trim());
                ((CovidFormActivity) getActivity()).formModel.setSecondary_insurance_id(binding.etPrimaryInsuranceIdSeoond.getText().toString().trim());
                ((CovidFormActivity) getActivity()).formModel.setSecondary_subscriber_dob(birth_date_second);
                ((CovidFormActivity) getActivity()).formModel.setSecondary_relation_patient(binding.etSubscriberRelPatientSecond.getSelectedItemPosition() == 0 ? "" : binding.etSubscriberRelPatientSecond.getSelectedItem().toString().toString().trim());
                ((CovidFormActivity) getActivity()).formModel.setSecondary_insurance_address(binding.etClPrimaryInsuranceAddressSeocnd.getText().toString().trim());
                ((CovidFormActivity) getActivity()).formModel.setSecondary_insurance_group(binding.etPrimaryInsuranceGroupSecond.getText().toString().trim());
                ((CovidFormActivity) getActivity()).formModel.setSecondary_insurance_phone(binding.etPriamryInsurancePhoneSecond.getText().toString().trim());
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);

            }
        });
        binding.rlBottom.imgPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });
       /* binding.btnAddSecondary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidate()) {
                    binding.btnAddSecondary.setVisibility(View.GONE);
                    binding.llSecondary.setVisibility(View.VISIBLE);
                }
            }
        });*/
        binding.tvPrimaryInsurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.llPrimary.getVisibility() == View.VISIBLE) {
                    binding.llPrimary.setVisibility(View.GONE);
                } else {
                    binding.llPrimary.setVisibility(View.VISIBLE);
                }


            }
        });
        binding.tvSecondaryInsurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.llSecondary.getVisibility() == View.VISIBLE) {
                    binding.llSecondary.setVisibility(View.GONE);
                } else {
                    binding.llSecondary.setVisibility(View.VISIBLE);
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
        binding.tvDobSecond.setOnClickListener(new View.OnClickListener() {
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
                        birth_date_second = dateformat.format(calendar.getTime());
                        binding.tvDobSecond.setText(DateUtils.changeDateFormat(DateUtils.DATE_FORMATE_8, DateUtils.DATE_FORMATE_1, dateformat.format(calendar.getTime()), false));

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                Calendar calendar1 = Calendar.getInstance();
                calendar1.add(Calendar.YEAR, -18);
                datePickerDialog.getDatePicker().setMaxDate(calendar1.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        binding.etPriamryInsurancePhone.addTextChangedListener(new PhoneTextFormatter(binding.etPriamryInsurancePhone, "+1 (###) ###-####"));
        binding.etPriamryInsurancePhoneSecond.addTextChangedListener(new PhoneTextFormatter(binding.etPriamryInsurancePhoneSecond, "+1 (###) ###-####"));
        return binding.getRoot();
    }

    private boolean isValidate() {

        if (!StringUtils.isNotEmpty(binding.elInsuranceName.getText().toString().trim())) {
            errorMessage(binding.getRoot(), "Please enter primary insurance name");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.etPrimaryInsuranceId.getText().toString().trim())) {
            errorMessage(binding.getRoot(), "Please enter primary insurance ID");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.etPriamryInsurancePhone.getText().toString().trim())) {
            errorMessage(binding.getRoot(), "Please enter primary insurance phone");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.etSubscriber.getText().toString().trim())) {
            errorMessage(binding.getRoot(), "Please enter subscriber name");
            return false;
        } else if (!StringUtils.isNotEmpty(birth_date)) {
            errorMessage(binding.getRoot(), "Please select subscriber birthdate");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.etSubscriberRelPatient.getSelectedItem().toString().toString().trim())) {
            errorMessage(binding.getRoot(), "Please enter Subscriber Rel.to Patient");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.etPrimaryInsuranceGroup.getText().toString().trim())) {
            errorMessage(binding.getRoot(), "Please enter Primary Insurance Group");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.etClPrimaryInsuranceAddress.getText().toString().trim())) {
            errorMessage(binding.getRoot(), "Please enter Primary Insurance Address");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.etSubscriber.getText().toString().trim())) {
            errorMessage(binding.getRoot(), "Please enter subscriber name");
            return false;
        }
        return true;
    }

    private boolean isValidatesecondary() {

        if (!StringUtils.isNotEmpty(binding.elInsuranceNameSecond.getText().toString().trim())) {
            errorMessage(binding.getRoot(), "Please enter secondary insurance name");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.etPrimaryInsuranceIdSeoond.getText().toString().trim())) {
            errorMessage(binding.getRoot(), "Please enter secondary insurance ID");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.etPriamryInsurancePhoneSecond.getText().toString().trim())) {
            errorMessage(binding.getRoot(), "Please enter secondary insurance phone");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.etSubscriberSecond.getText().toString().trim())) {
            errorMessage(binding.getRoot(), "Please enter subscriber name");
            return false;
        } else if (!StringUtils.isNotEmpty(birth_date)) {
            errorMessage(binding.getRoot(), "Please select subscriber birthdate");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.etSubscriberRelPatientSecond.getSelectedItem().toString().trim())) {
            errorMessage(binding.getRoot(), "Please enter Subscriber Rel.to Patient");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.etPrimaryInsuranceGroupSecond.getText().toString().trim())) {
            errorMessage(binding.getRoot(), "Please enter secondary Insurance Group");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.etClPrimaryInsuranceAddressSeocnd.getText().toString().trim())) {
            errorMessage(binding.getRoot(), "Please enter secondary Insurance Address");
            return false;
        }
        return true;
    }
}
