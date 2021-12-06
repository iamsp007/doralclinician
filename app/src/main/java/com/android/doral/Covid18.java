package com.android.doral;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.CovidFormActivity;
import com.android.doral.Utils.DateUtils;
import com.android.doral.Utils.StringUtils;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseFragment;
import com.android.doral.databinding.ActivityCovid18Binding;
import com.android.doral.retrofit.model.CovidFormModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Covid18 extends BaseFragment implements View.OnClickListener {
    ActivityCovid18Binding binding;

    private String vaccine, dose_type, dose_date, fact_dose_date;

    private final SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    ViewPager viewPager;


    public Covid18(ViewPager viewPager) {
        // Required empty public constructor
        this.viewPager = viewPager;
    }

    // TODO: Rename and change types and number of parameters
    public static Covid18 newInstance(ViewPager viewPager) {
        Covid18 fragment = new Covid18(viewPager);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = ActivityCovid18Binding.inflate(getLayoutInflater());

        binding.rlBottom.imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidate()) {
                    ((CovidFormActivity) getActivity()).formModel.setVaccine_name(vaccine);
                    ((CovidFormActivity) getActivity()).formModel.setVaccine_date(dose_date);
                    RadioButton radioButton = getActivity().findViewById(binding.rbGroup.getCheckedRadioButtonId());
                    ((CovidFormActivity) getActivity()).formModel.setVaccine_type(radioButton.getText().toString().equalsIgnoreCase("First Dose") ? "first" : "second");
                    ((CovidFormActivity) getActivity()).formModel.setFact_dose_date(fact_dose_date);
                    ((CovidFormActivity) getActivity()).formModel.setManufacture_lot_number(binding.edtManufatureName.getText().toString());

                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
            }
        });
        binding.rlBottom.imgPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });
        binding.tvFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChecked(binding.tvFirst);
                binding.llFirst.setVisibility(View.VISIBLE);
                binding.rbSecondDose.setVisibility(View.VISIBLE);
                binding.tvVaccineName.setText(binding.tvFirst.getText().toString());
            }
        });
        binding.tvSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChecked(binding.tvSecond);
                binding.llFirst.setVisibility(View.VISIBLE);
                binding.rbSecondDose.setVisibility(View.VISIBLE);
                binding.tvVaccineName.setText(binding.tvSecond.getText().toString());
            }
        });
        binding.tvThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChecked(binding.tvThird);
                binding.llFirst.setVisibility(View.VISIBLE);
                binding.rbSecondDose.setVisibility(View.VISIBLE);
                binding.tvVaccineName.setText(binding.tvThird.getText().toString());
            }
        });
        binding.tvFourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChecked(binding.tvFourth);
                binding.llFirst.setVisibility(View.VISIBLE);
                binding.rbSecondDose.setVisibility(View.GONE);
                binding.tvVaccineName.setText(binding.tvFourth.getText().toString());
            }
        });
        setClick();
        return binding.getRoot();
    }

    private void setClick() {

        fact_dose_date = dateformat.format(Calendar.getInstance().getTime());
        dose_date = dateformat.format(Calendar.getInstance().getTime());
        binding.tvFirstFactDate.setText(DateUtils.changeDateFormat(DateUtils.DATE_FORMATE_8, DateUtils.DATE_FORMATE_1, dateformat.format(Calendar.getInstance().getTime()), false));
        binding.tvDoseDate.setText(DateUtils.changeDateFormat(DateUtils.DATE_FORMATE_8, DateUtils.DATE_FORMATE_1, dateformat.format(Calendar.getInstance().getTime()), false));
        binding.tvDoseDate.setOnClickListener(this);

        binding.tvFirstFactDate.setOnClickListener(this);
        binding.tvSecondFirstDoseDate.setOnClickListener(this);
        binding.tvSecondSecondDoseDate.setOnClickListener(this);
        binding.tvSecondFactDate.setOnClickListener(this);
        binding.tvThirdFirstDoseDate.setOnClickListener(this);
        binding.tvThirdSecondDoseDate.setOnClickListener(this);
        binding.tvThirdFactDate.setOnClickListener(this);

        binding.tvFourthFactDate.setOnClickListener(this);
        binding.tvFourthFirstDoseDate.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == binding.tvDoseDate.getId()) {
            firstDoseDate(binding.tvDoseDate);
        }

        if (view.getId() == binding.tvSecondFirstDoseDate.getId()) {
            firstDoseDate(binding.tvSecondFirstDoseDate);
        }
        if (view.getId() == binding.tvSecondSecondDoseDate.getId()) {
            seocndDoseDate(binding.tvSecondSecondDoseDate);
        }
        if (view.getId() == binding.tvThirdFirstDoseDate.getId()) {
            firstDoseDate(binding.tvThirdFirstDoseDate);
        }
        if (view.getId() == binding.tvThirdSecondDoseDate.getId()) {
            seocndDoseDate(binding.tvThirdSecondDoseDate);
        }
        if (view.getId() == binding.tvFourthFactDate.getId()) {
            factDoseDate(binding.tvFourthFactDate);
        }
        if (view.getId() == binding.tvFirstFactDate.getId()) {
            factDoseDate(binding.tvFirstFactDate);
        }
        if (view.getId() == binding.tvSecondFactDate.getId()) {
            factDoseDate(binding.tvSecondFactDate);
        }
        if (view.getId() == binding.tvThirdFactDate.getId()) {
            factDoseDate(binding.tvThirdFactDate);
        }
        if (view.getId() == binding.tvFourthFirstDoseDate.getId()) {
            firstDoseDate(binding.tvFourthFirstDoseDate);
        }
    }

    private void firstDoseDate(TextView textView) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        /*birth_date = i + "/" + (i1 + 1) + "/" + i2;
                        binding.activitySignUpTvDOB.setText(birth_date);*/

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dose_date = dateformat.format(calendar.getTime());
                textView.setText(DateUtils.changeDateFormat(DateUtils.DATE_FORMATE_8, DateUtils.DATE_FORMATE_1, dateformat.format(calendar.getTime()), false));

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void seocndDoseDate(TextView textView) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        /*birth_date = i + "/" + (i1 + 1) + "/" + i2;
                        binding.activitySignUpTvDOB.setText(birth_date);*/

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                //second_dose_date = dateformat.format(calendar.getTime());
                textView.setText(DateUtils.changeDateFormat(DateUtils.DATE_FORMATE_8, DateUtils.DATE_FORMATE_1, dateformat.format(calendar.getTime()), false));

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void factDoseDate(TextView textView) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        /*birth_date = i + "/" + (i1 + 1) + "/" + i2;
                        binding.activitySignUpTvDOB.setText(birth_date);*/

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                fact_dose_date = dateformat.format(calendar.getTime());
                textView.setText(DateUtils.changeDateFormat(DateUtils.DATE_FORMATE_8, DateUtils.DATE_FORMATE_1, dateformat.format(calendar.getTime()), false));

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }


    private void setChecked(RadioButton button) {
        binding.tvFirst.setChecked(false);
        binding.tvSecond.setChecked(false);
        binding.tvThird.setChecked(false);
        binding.tvFourth.setChecked(false);
        button.setChecked(true);
        vaccine = button.getText().toString();
    }

    private boolean isValidate() {

        if (!StringUtils.isNotEmpty(vaccine)) {
            errorMessage(binding.getRoot(), "Please select vaccine");
            return false;
        } else if (binding.rbGroup.getCheckedRadioButtonId() == -1) {
            errorMessage(binding.getRoot(), "Please select vaccine dose");
            return false;
        } else if (!StringUtils.isNotEmpty(dose_date)) {
            errorMessage(binding.getRoot(), "Please select dose date");
            return false;
        } else if (!StringUtils.isNotEmpty(fact_dose_date)) {
            errorMessage(binding.getRoot(), "Please select EUA Fact Sheet Date");
            return false;
        }

        return true;
    }
}