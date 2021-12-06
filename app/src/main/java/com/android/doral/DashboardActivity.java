package com.android.doral;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.android.doral.Utils.AppClass;
import com.android.doral.Utils.MyPref;
import com.android.doral.adapter.AppointmentAdapter;
import com.android.doral.adapter.DoctorAdapter;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityDashboardBinding;
import com.android.doral.dialog.CustomAlertDialog;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.AppontmentModel;
import com.android.doral.retrofit.model.UserModel;
import com.harrywhewell.scrolldatepicker.OnDateSelectedListener;
import com.michalsvec.singlerowcalendar.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DashboardActivity extends BaseActivity implements View.OnClickListener, BaseViewInterface {
    ActivityDashboardBinding binding;
    private Calendar calendar = Calendar.getInstance();
    private int currentMonth = 0;
    BasePresenterInterface presenterInterface;
    AppointmentAdapter appointmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setStatusBarColor(R.color.white);
        presenterInterface = new Presenter(this);
        setClick();

        currentMonth = calendar.get(Calendar.MONTH);

        binding.rvDoctor.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.rvAppointment.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.rvDoctor.setAdapter(new DoctorAdapter(this));
        appointmentAdapter = new AppointmentAdapter(this);
        binding.rvAppointment.setAdapter(appointmentAdapter);

        binding.dayDatePicker.setStartDate(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
        // binding.dayDatePicker.setEndDate(calendar.getActualMaximum(Calendar.DATE), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));

        binding.dayDatePicker.getSelectedDate(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@Nullable Date date) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(com.android.doral.Utils.DateUtils.DATE_FORMATE_8);
                appointmentAdapter.getFilter().filter(simpleDateFormat.format(date));

            }
        });


        presenterInterface.sendRequest(context, null, null, ApiCallInterface.APPOITNMENT);
        presenterInterface.sendRequest(context, null, null, ApiCallInterface.USER_DETAILS);

      /* ((AppClass) getApplication()).createSocketConnection();
        ((AppClass) getApplication()).setAppListerner();*/


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private List<Date> getNextMonthDate() {
        currentMonth++;// + because we want next month
        if (currentMonth == 12) {
            // we will switch to january of next year, when we reach last month of year
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
            currentMonth = 0;
        }


        return getDates();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //((AppClass) getApplication()).removeSocketConnection();
    }

    private List<Date> getPreviousMonthDate() {
        currentMonth--;
        if (currentMonth == -1) {
            // we will switch to december of previous year, when we reach first month of year
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
            currentMonth = 11;
        }

        return getDates();
    }

    private List<Date> getDates() {
        List<Date> dateList = new ArrayList<>();
        calendar.set(Calendar.MONTH, currentMonth);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        dateList.add(calendar.getTime());
        while (currentMonth == calendar.get(Calendar.MONTH)) {
            calendar.add(Calendar.DATE, +1);
            if (calendar.get(Calendar.MONTH) == currentMonth)
                dateList.add(calendar.getTime());
        }
        calendar.add(Calendar.DATE, -1);

        binding.tvDate.setText(DateUtils.INSTANCE.getMonthName(calendar.getTime()) + "," + DateUtils.INSTANCE.getYear(calendar.getTime()));


        return dateList;
    }

    private void setClick() {

        binding.btnOffline.setOnClickListener(this::onClick);
        binding.imgMenu.setOnClickListener(this::onClick);
        binding.imgNext.setOnClickListener(this::onClick);
        binding.imgPrevious.setOnClickListener(this::onClick);
        binding.leftMenuHome.llHome.setOnClickListener(this::onClick);
        binding.leftMenuHome.llProfile.setOnClickListener(this::onClick);
        binding.leftMenuHome.llRequest.setOnClickListener(this::onClick);
        binding.leftMenuHome.llChangePassword.setOnClickListener(this::onClick);
        binding.leftMenuHome.llSupport.setOnClickListener(this::onClick);
        binding.leftMenuHome.llLogout.setOnClickListener(this::onClick);
        binding.leftMenuHome.llAppointments.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_offline) {
            HashMap<String, String> map = new HashMap<>();
            map.put("is_available", new MyPref(context).getUserData().getIs_available().equals("1") ? "0" : "1");
            presenterInterface.sendRequest(context, null, map, ApiCallInterface.CHNAGE_AVAILABILTY);

        }
        if (view.getId() == R.id.img_next) {


        }
        if (view.getId() == R.id.img_menu) {
            binding.drawelayoutHomeactivity.openDrawer(GravityCompat.START);
        }
        if (view.getId() == R.id.ll_home) {
            binding.drawelayoutHomeactivity.closeDrawer(GravityCompat.START, true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                }
            }, 300);
        }
        if (view.getId() == R.id.ll_profile) {
            binding.drawelayoutHomeactivity.closeDrawer(GravityCompat.START, true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    startActivity(new Intent(context, CreateProfileActivity.class));
                }
            }, 300);
        }
        if (view.getId() == R.id.ll_appointments) {
            binding.drawelayoutHomeactivity.closeDrawer(GravityCompat.START, true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    startActivity(new Intent(context, AppointmentActivity.class));
                }
            }, 300);
        }
        if (view.getId() == R.id.ll_request) {
            binding.drawelayoutHomeactivity.closeDrawer(GravityCompat.START, true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(context, GetRequestActivity.class));

                }
            }, 300);
        }
        if (view.getId() == R.id.ll_change_password) {
            binding.drawelayoutHomeactivity.closeDrawer(GravityCompat.START, true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    startActivity(new Intent(context, ChangePasswordActivity.class));
                }
            }, 300);
        }
        if (view.getId() == R.id.ll_support) {
            binding.drawelayoutHomeactivity.closeDrawer(GravityCompat.START, true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                }
            }, 300);
        }
        if (view.getId() == R.id.ll_logout) {
            binding.drawelayoutHomeactivity.closeDrawer(GravityCompat.START, true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    new CustomAlertDialog(context, "Are you sure want to logout?", "Logout", "Yes", "No", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            new MyPref(context).setData(MyPref.Keys.IsLogin, false);
                            //new MyPref(context).clearPrefs();
                            startActivity(new Intent(context, LoginActivity.class));
                            finishAffinity();

                        }
                    }).show();

                }
            }, 300);
        }

    }

    @Override
    public void retry(int pos) {

    }

    @Override
    public void onError(String errorMsg, int requestCode, int resultCode) {
        errorMessage(binding.getRoot(), errorMsg);
    }

    @Override
    public void onSuccess(Object success, int requestCode, int resultCode) {

        if (requestCode == ApiCallInterface.APPOITNMENT) {
            AppontmentModel appontmentModel = (AppontmentModel) success;
            if (appontmentModel.getData() != null) {

                appointmentAdapter.setData(appontmentModel.getData());

            }
        }

        if (requestCode == ApiCallInterface.USER_DETAILS) {
            UserModel basemodel = (UserModel) success;
            if (basemodel.isStatus().equals("true")) {

                if (basemodel.getData() != null) {

                    new MyPref(context).setUserData(basemodel.getData());
                    TextView tvUserName = findViewById(R.id.tvUserName);
                    tvUserName.setText(basemodel.getData().getFirst_name() + " " + basemodel.getData().getLast_name());
                    binding.btnOffline.setText(basemodel.getData().getIs_available().equals("1") ? "Online" : "Offline");

                }

            } else {

                errorMessage(binding.getRoot(), basemodel.getMessage());

            }
        }

        if (requestCode == ApiCallInterface.CHNAGE_AVAILABILTY) {
            UserModel basemodel = (UserModel) success;
            if (basemodel.isStatus().equals("true")) {

                if (basemodel.getData() != null) {

                    UserModel userModel = new MyPref(context).getUserData();
                    userModel.setIs_available(basemodel.getData().getIs_available());
                    new MyPref(context).setUserData(userModel);

                    binding.btnOffline.setText(basemodel.getData().getIs_available().equals("1") ? "Online" : "Offline");
                }

            } else {
                errorMessage(binding.getRoot(), basemodel.getMessage());
            }
        }

    }
}