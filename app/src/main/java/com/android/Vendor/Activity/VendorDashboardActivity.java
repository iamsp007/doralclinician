package com.android.Vendor.Activity;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.Vendor.Adapter.UpcomingVisitAdapter;
import com.android.doral.AppointmentActivity;
import com.android.doral.ChangePasswordActivity;
import com.android.doral.Covid_Step_One_Activity;
import com.android.doral.CreateProfileActivity;
import com.android.doral.GetRequestActivity;
import com.android.doral.LoginActivity;
import com.android.doral.R;
import com.android.doral.ReasonActivity;
import com.android.doral.RoadlRequestActivity;
import com.android.doral.Utils.MyPref;
import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.adapter.AppointmentAdapter1;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityVendorDashboardBinding;
import com.android.doral.dialog.CustomAlertDialog;
import com.android.doral.dialog.SignatureDialog;
import com.android.doral.dialog.TimerDialogDialog;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.AppontmentModel;
import com.android.doral.retrofit.model.UserModel;

import java.util.Calendar;
import java.util.HashMap;

public class VendorDashboardActivity extends BaseActivity implements View.OnClickListener, BaseViewInterface {
    ActivityVendorDashboardBinding binding;
    private Calendar calendar = Calendar.getInstance();
    private int currentMonth = 0;
    private BasePresenterInterface presenterInterface;
    private UpcomingVisitAdapter upcomingVisitAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVendorDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setStatusBarColor(R.color.color_2CB9C7);
        presenterInterface = new Presenter(this);
        currentMonth = calendar.get(Calendar.MONTH);
        binding.rvAppointment.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        upcomingVisitAdapter = new UpcomingVisitAdapter(this);
        binding.rvAppointment.setAdapter(upcomingVisitAdapter);

        binding.leftMenuHome.llProfile.setVisibility(View.GONE);
        binding.leftMenuHome.llChangePassword.setVisibility(View.GONE);
        binding.leftMenuHome.llProfile.setVisibility(View.GONE);
        binding.leftMenuHome.llSupport.setVisibility(View.GONE);
        binding.leftMenuHome.llAppointments.setVisibility(View.GONE);
        binding.leftMenuHome.llCovidForms.setVisibility(View.GONE);
        setClick();
        presenterInterface.sendRequest(context, null, null, ApiCallInterface.APPOITNMENT);
        presenterInterface.sendRequest(context, null, null, ApiCallInterface.USER_DETAILS);


        binding.tvUpcomingVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(VendorDashboardActivity.this, UpcomingVisitActivity.class));
            }
        });

        binding.cvRoadLRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(VendorDashboardActivity.this, GetRequestActivity.class));
            }
        });
       /* if (!new MyPref(context).getData(MyPref.Keys.COVID_TIME).equalsIgnoreCase("")) {
            new TimerDialogDialog(context, new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new SignatureDialog(context, new SignatureDialog.OnsaveClickListener() {
                        @Override
                        public void onItemClick(String filepath) {
                            HashMap<String, String> map = new HashMap<>();
                            map.put("id", new MyPref(context).getData(MyPref.Keys.FORM_ID));
                            presenterInterface.sendRequest(context, "", map, ApiCallInterface.STORE_FORM_SIGNATURE_1, filepath);
                        }
                    }).show();


                }
            }).show();
        }*/
    }

    private void setClick() {
        binding.btnOffline.setOnClickListener(this::onClick);
        binding.cardAppointment.setOnClickListener(this::onClick);
        binding.btnGenerateForm.setOnClickListener(this::onClick);
        binding.imgMenu.setOnClickListener(this::onClick);
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
        if (view.getId() == R.id.img_menu) {
            binding.drawelayoutHomeactivity.openDrawer(GravityCompat.START);
        }
        if (view.getId() == R.id.card_appointment) {


        }
        if (view.getId() == R.id.btn_generate_form) {

            startActivity(new Intent(context, Covid_Step_One_Activity.class));
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

                upcomingVisitAdapter.setData(appontmentModel.getData());

            }
        }

        if (requestCode == ApiCallInterface.USER_DETAILS) {
            UserModel basemodel = (UserModel) success;
            if (basemodel.isStatus().equals("true")) {

                if (basemodel.getData() != null) {

                    new MyPref(context).setUserData(basemodel.getData());
                    TextView tvUserName = findViewById(R.id.tvUserName);
                    TextView tv_type = findViewById(R.id.tv_type);
                    tv_type.setText(basemodel.getData().getRoles().get(basemodel.getData().getRoles().size() - 1).getName());
                    tvUserName.setText(basemodel.getData().getFirst_name() + " " + basemodel.getData().getLast_name());
                    binding.btnOffline.setText(basemodel.getData().getIs_available().equals("1") ? "Online" : "Offline");
                    binding.btnOffline.setCompoundDrawablesWithIntrinsicBounds(basemodel.getData().getIs_available().equals("1") ? R.drawable.ic_online : R.drawable.ic_offline, 0, 0, 0);


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
                    binding.btnOffline.setCompoundDrawablesWithIntrinsicBounds(basemodel.getData().getIs_available().equals("1") ? R.drawable.ic_online : R.drawable.ic_offline, 0, 0, 0);
                }

            } else {
                errorMessage(binding.getRoot(), basemodel.getMessage());
            }
        }

    }
}