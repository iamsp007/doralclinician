package com.android.doral.fragement;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.android.doral.ApplicationActivity;
import com.android.doral.ChangePasswordActivity;
import com.android.doral.R;
import com.android.doral.Utils.MyPref;
import com.android.doral.Utils.PhoneTextFormatter;
import com.android.doral.Utils.StringUtils;
import com.android.doral.base.BaseFragment;
import com.android.doral.databinding.FragmentApplicationFirstBinding;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApplicationFirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApplicationFirstFragment extends BaseFragment implements View.OnClickListener {

    FragmentApplicationFirstBinding binding;
    ViewPager viewPager;
    String date = "";
    DatePickerDialog datePickerDialog;
    Calendar calendar = Calendar.getInstance();
    String isCitizen = "";
    String month="",day="";
    String number;

    public ApplicationFirstFragment(ViewPager viewPager) {
        this.viewPager = viewPager;
        // Required empty public constructor
    }


    public static ApplicationFirstFragment newInstance(ViewPager viewPager) {
        ApplicationFirstFragment fragment = new ApplicationFirstFragment(viewPager);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentApplicationFirstBinding.inflate(inflater);
        // Inflate the layout for this fragment
        setClick();
        binding.rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (binding.rbGroup.getCheckedRadioButtonId() == R.id.rb_yes) {
                    isCitizen = "1";
                    binding.llCard.setVisibility(View.GONE);
                    binding.edtCardNumber.setText("");
                } else if (binding.rbGroup.getCheckedRadioButtonId() == R.id.rb_no) {
                    binding.llCard.setVisibility(View.VISIBLE);
                    binding.edtCardNumber.setText("");
                    isCitizen = "0";
                }
            }
        });

        return binding.getRoot();
    }

    private void setClick() {
        binding.tvNext.setOnClickListener(this);
        binding.edtDate.setOnClickListener(this);
        number =  new MyPref(getActivity()).getUserData().getPhone();
     //   binding.edtPhoneNumber.addTextChangedListener(new PhoneTextFormatter(binding.edtPhoneNumber, "+1 (###) ###-####"));
        binding.edtHomePhone.addTextChangedListener(new PhoneTextFormatter(binding.edtHomePhone, "+1 (###) ###-####"));
        binding.edtEmergencyPhone.addTextChangedListener(new PhoneTextFormatter(binding.edtEmergencyPhone, "+1 (###) ###-####"));
        binding.edtSsnNumber.addTextChangedListener(new PhoneTextFormatter(binding.edtSsnNumber, "###-##-####"));
        binding.edtName.setText(new MyPref(getActivity()).getUserData().getFirst_name());
        binding.edtPhoneNumber.setText("+1" + "\t" + PhoneNumberUtils.formatNumber( number, "US"));
    }

    public boolean validate() {

        if (!StringUtils.isNotEmpty(binding.edtName.getText().toString())) {
            errorMessage(binding.getRoot(), "please enter application name");
            return false;
        }
//        else if (!StringUtils.isNotEmpty(binding.edtOtherName.getText().toString())) {
//            errorMessage(binding.getRoot(), "please enter other name");
//            return false;
//        }
        else if (!StringUtils.isNotEmpty(binding.edtSsnNumber.getText().toString())) {
            errorMessage(binding.getRoot(), "please enter SSN number");
            return false;
        } else if (binding.edtSsnNumber.getText().toString().length()<11) {
            errorMessage(binding.getRoot(), "please enter valid SSN number");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.edtPhoneNumber.getText().toString())) {
            errorMessage(binding.getRoot(), "please enter Cell phone number");
            return false;
        } else if (binding.edtPhoneNumber.getText().length()<17) {
            errorMessage(binding.getRoot(), "please enter valid Cell phone number");
            return false;
        } else if (!StringUtils.isNotEmpty(binding.edtHomePhone.getText().toString())) {
            errorMessage(binding.getRoot(), "please enter home phone number");
            return false;
        } else if (binding.edtHomePhone.getText().length()<17) {
            errorMessage(binding.getRoot(), "please enter valid home Phone number");
            return false;
        }else if (!StringUtils.isNotEmpty(date)) {
            errorMessage(binding.getRoot(), "please select date");
            return false;
        } else if (!StringUtils.isNotEmpty(isCitizen)) {
            errorMessage(binding.getRoot(), "please select us citizen");
            return false;
        } else if (StringUtils.isNotEmpty(isCitizen) && isCitizen.equalsIgnoreCase("0") && !StringUtils.isNotEmpty(binding.edtCardNumber.getText().toString())) {
            errorMessage(binding.getRoot(), "please enter immigration id");
            return false;
        }


        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_next) {
            if (validate()) {

                ((ApplicationActivity) getActivity()).map.put("applicant_name", binding.edtName.getText().toString());
             //   ((ApplicationActivity) getActivity()).map.put("other_name", binding.edtOtherName.getText().toString());
                ((ApplicationActivity) getActivity()).map.put("ssn", getOriginalSsnNumber(binding.edtSsnNumber));
                ((ApplicationActivity) getActivity()).map.put("phone",(binding.edtPhoneNumber.getText().toString()));
                ((ApplicationActivity) getActivity()).map.put("home_phone", getOriginalPhoneNumber(binding.edtHomePhone));
                ((ApplicationActivity) getActivity()).map.put("date", date);
                ((ApplicationActivity) getActivity()).map.put("us_citizen", isCitizen);
                ((ApplicationActivity) getActivity()).map.put("immigration_id", binding.edtName.getText().toString());
                 viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        }
        if (view.getId() == R.id.edt_date) {


            datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                   // date = i + "/" + (i1 + 1) + "/" + i2;
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    if(monthOfYear < 10){

                        month = "0" + (monthOfYear+1);

                    }else {

                        month = "" + (monthOfYear+1);

                    }
                    if(dayOfMonth < 10){

                        day  = "0" + dayOfMonth ;

                    }else {

                        day  = "" + dayOfMonth ;

                    }
                    date=month + "/" + day + "/" + year;
                    binding.edtDate.setText(date);

                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();

        }
    }

    private String getOriginalPhoneNumber(EditText editText){
        String removeSpecial="";
        String[] splitPhoneNumber=editText.getText().toString().split(" ");
        removeSpecial= splitPhoneNumber[1]+""+splitPhoneNumber[2];
        return  removeSpecial.replaceAll("[^\\d]", "");
    }

    private String getOriginalSsnNumber(EditText editText){
        String removeSpecial = editText.getText().toString().replaceAll("[^\\d]", "");
        return  removeSpecial;
    }
}