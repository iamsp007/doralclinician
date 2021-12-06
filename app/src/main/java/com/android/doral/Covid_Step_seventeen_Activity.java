package com.android.doral;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.android.CovidFormActivity;
import com.android.doral.Utils.DateUtils;
import com.android.doral.Utils.ImageUtils;
import com.android.doral.Utils.Utility;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseFragment;
import com.android.doral.databinding.ActivityCovidStep17Binding;
import com.android.doral.dialog.SignatureDialog;
import com.android.doral.retrofit.model.CovidFormModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Covid_Step_seventeen_Activity extends BaseFragment {
    ActivityCovidStep17Binding binding;
    DatePickerDialog datePickerDialog;

    private String date1 = "";

    private final SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private String interceptor_signature = "";

    ViewPager viewPager;


    public Covid_Step_seventeen_Activity(ViewPager viewPager) {
        // Required empty public constructor
        this.viewPager = viewPager;
    }

    // TODO: Rename and change types and number of parameters
    public static Covid_Step_seventeen_Activity newInstance(ViewPager viewPager) {
        Covid_Step_seventeen_Activity fragment = new Covid_Step_seventeen_Activity(viewPager);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = ActivityCovidStep17Binding.inflate(getLayoutInflater());

        binding.rlBottom.imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((CovidFormActivity) getActivity()).formModel.setInterpreter_relation(binding.spRelation.getSelectedItemPosition() == 0 ? "" : binding.spRelation.getSelectedItem().toString());
                ((CovidFormActivity) getActivity()).formModel.setInterpreter_signature_datetime(date1);
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });
        binding.rlBottom.imgPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });
        binding.llSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new SignatureDialog(getActivity(), new SignatureDialog.OnsaveClickListener() {
                    @Override
                    public void onItemClick(String filepath) {
                        binding.imgSignature.setVisibility(View.VISIBLE);
                        interceptor_signature = filepath;
                        Utility.interpreter_sign = filepath;
                        ImageUtils.loadImage(getActivity(), filepath, R.drawable.white_logo, binding.imgSignature);
                    }
                }).show();
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

                        new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                calendar.set(Calendar.HOUR_OF_DAY, i);
                                calendar.set(Calendar.MINUTE, i1);
                                date1 = dateformat.format(calendar.getTime());
                                binding.tvDob.setText(DateUtils.changeDateFormat(DateUtils.DATE_FORMATE_4, DateUtils.DATE_FORMATE_11, dateformat.format(calendar.getTime()), false));
                            }
                        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();


                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
        return binding.getRoot();
    }
}
