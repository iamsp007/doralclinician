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
import com.android.doral.Utils.StringUtils;
import com.android.doral.Utils.Utility;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseFragment;
import com.android.doral.databinding.ActivityCovidStep16Binding;
import com.android.doral.dialog.SignatureDialog;
import com.android.doral.retrofit.model.CovidFormModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Covid_Step_sixsteen_Activity extends BaseFragment {
    ActivityCovidStep16Binding binding;
    DatePickerDialog datePickerDialog;
    DatePickerDialog datePickerDialog1;
    DatePickerDialog datePickerDialog2;
    private String date1="";
    private String date2="";
    private String date3="";
    private final SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    private String receipent_signature = "";
    private String interpretter_signature = "";

    ViewPager viewPager;


    public Covid_Step_sixsteen_Activity(ViewPager viewPager) {
        // Required empty public constructor
        this.viewPager = viewPager;
    }

    // TODO: Rename and change types and number of parameters
    public static Covid_Step_sixsteen_Activity newInstance(ViewPager viewPager) {
        Covid_Step_sixsteen_Activity fragment = new Covid_Step_sixsteen_Activity(viewPager);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = ActivityCovidStep16Binding.inflate(getLayoutInflater());

        date1 = dateformat.format(Calendar.getInstance().getTime());
        date2 = dateformat.format(Calendar.getInstance().getTime());
        date3 = dateformat.format(Calendar.getInstance().getTime());
        binding.tvDob.setText(DateUtils.changeDateFormat(DateUtils.DATE_FORMATE_4, DateUtils.DATE_FORMATE_11, dateformat.format(Calendar.getInstance().getTime()), false));
        binding.tvDob1.setText(DateUtils.changeDateFormat(DateUtils.DATE_FORMATE_4, DateUtils.DATE_FORMATE_11, dateformat.format(Calendar.getInstance().getTime()), false));
        binding.tvDobInterpretter.setText(DateUtils.changeDateFormat(DateUtils.DATE_FORMATE_4, DateUtils.DATE_FORMATE_11, dateformat.format(Calendar.getInstance().getTime()), false));
        binding.rlBottom.imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((CovidFormActivity) getActivity()).formModel.setRecipient_signature_datetime(date1);
                ((CovidFormActivity) getActivity()).formModel.setRecipient_relation(binding.spRelation.getSelectedItemPosition() == 0 ? "" : binding.spRelation.getSelectedItem().toString());
                ((CovidFormActivity) getActivity()).formModel.setPrint_name(binding.edtPrintName.getText().toString());
                ((CovidFormActivity) getActivity()).formModel.setTelephonic_interpreter_id(binding.edtTelephonic.getText().toString());
                ((CovidFormActivity) getActivity()).formModel.setTelephonic_interpreter_datetime(date2);

                ((CovidFormActivity) getActivity()).formModel.setInterpreter_relation(binding.spRelationInterpretter.getSelectedItemPosition() == 0 ? "" : binding.spRelationInterpretter.getSelectedItem().toString());
                ((CovidFormActivity) getActivity()).formModel.setInterpreter_signature_datetime(date3);
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);

            }
        });
        binding.rlBottom.imgPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });

        binding.rlSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SignatureDialog(getActivity(), new SignatureDialog.OnsaveClickListener() {
                    @Override
                    public void onItemClick(String filepath) {
                        binding.imgSignature.setVisibility(View.VISIBLE);
                        receipent_signature = filepath;
                        Utility.recipient_sign = filepath;
                        ImageUtils.loadImage(getActivity(), filepath, R.drawable.white_logo, binding.imgSignature);
                    }
                }).show();
            }
        });
        binding.llSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SignatureDialog(getActivity(), new SignatureDialog.OnsaveClickListener() {
                    @Override
                    public void onItemClick(String filepath) {
                        binding.imgSignatureInterpretter.setVisibility(View.VISIBLE);
                        interpretter_signature = filepath;
                        Utility.interpreter_sign = filepath;
                        ImageUtils.loadImage(getActivity(), filepath, R.drawable.white_logo, binding.imgSignatureInterpretter);
                    }
                }).show();
            }
        });

        binding.tvFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llFirst.setVisibility(binding.llFirst.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            }
        });
        binding.tvSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llSeocond.setVisibility(binding.llSeocond.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            }
        });
        binding.tvThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llThird.setVisibility(binding.llThird.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
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
        binding.tvDob1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                datePickerDialog1 = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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
                                date2 = dateformat.format(calendar.getTime());
                                binding.tvDob1.setText(DateUtils.changeDateFormat(DateUtils.DATE_FORMATE_4, DateUtils.DATE_FORMATE_11, dateformat.format(calendar.getTime()), false));
                            }
                        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();


                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog1.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog1.show();
            }
        });
        binding.tvDobInterpretter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                datePickerDialog2 = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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
                                date3 = dateformat.format(calendar.getTime());
                                binding.tvDobInterpretter.setText(DateUtils.changeDateFormat(DateUtils.DATE_FORMATE_4, DateUtils.DATE_FORMATE_11, dateformat.format(calendar.getTime()), false));
                            }
                        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();


                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog2.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog2.show();
            }
        });
        return binding.getRoot();
    }

    private boolean isValidate() {

        if (!StringUtils.isNotEmpty(receipent_signature)) {
            errorMessage(binding.getRoot(), "Please enter Recipient/Surrogate/Guardian recipient  signature");
            return false;
        } else if (!StringUtils.isNotEmpty(date1)) {
            errorMessage(binding.getRoot(), "Please select date time");
            return false;
        } else if (binding.spRelation.getSelectedItemPosition() == 0) {
            errorMessage(binding.getRoot(), "Please select relation to patient");
            return false;
        }
//        else if (!StringUtils.isNotEmpty(binding.edtPrintName.getText().toString())) {
//            errorMessage(binding.getRoot(), "Please enter print name");
//            return false;
//        } else if (!StringUtils.isNotEmpty(binding.edtTelephonic.getText().toString())) {
//            errorMessage(binding.getRoot(), "Please enter Telephonic Interpreter’s ID");
//            return false;
//        } else if (!StringUtils.isNotEmpty(date2)) {
//            errorMessage(binding.getRoot(), "Please select Telephonic Interpreter’s date time");
//            return false;
//        }
        return true;
    }
}
