package com.android.doral;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.AlarmManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.agik.swipebutton.controller.OnSwipeCompleteListener;
import com.agik.swipebutton.view.Swipe_Button_View;
import com.android.CovidFormActivity;
import com.android.doral.Utils.BottomSheetAskPermission;
import com.android.doral.Utils.GpsUtils;
import com.android.doral.Utils.ImagePickerUtils;
import com.android.doral.Utils.MyPref;
import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.Utils.SingleShotLocationProvider;
import com.android.doral.Utils.StringUtils;
import com.android.doral.adapter.AppointmentAdapter1;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityNewDashboardBinding;
import com.android.doral.dialog.CustomAlertDialog;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.AppontmentModel;
import com.android.doral.retrofit.model.BaseModel;
import com.android.doral.retrofit.model.ParentIdModel;
import com.android.doral.retrofit.model.UserModel;
import com.android.doral.retrofit.model.applicationdetails.ApplicationDetailsModel;
import com.android.doral.service.GeoService;
import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewDashboardActivity extends BaseActivity implements View.OnClickListener, BaseViewInterface {
    ActivityNewDashboardBinding binding;
    private Calendar calendar = Calendar.getInstance();
    private int currentMonth = 0;
    private BasePresenterInterface presenterInterface;
    private AppointmentAdapter1 appointmentAdapter;
    AppontmentModel appontmentModel;
    ApplicationDetailsModel basemodel;
    private String parent_id;
    private String patient_request_id;
    String str;

    private boolean started = false;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setStatusBarColor(R.color.color_2CB9C7);
        presenterInterface = new Presenter(this);
        currentMonth = calendar.get(Calendar.MONTH);
        binding.rvAppointment.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        appointmentAdapter = new AppointmentAdapter1(this);
        binding.rvAppointment.setAdapter(appointmentAdapter);
        setClick();
        presenterInterface.sendRequest(context, null, null, ApiCallInterface.APPOITNMENT);
        presenterInterface.sendRequest(context, null, null, ApiCallInterface.USER_DETAILS);


//
//            if (appontmentModel.getService_id().equals(1)){
//                binding.doral.setChecked(true);
//                binding.roadl.setChecked(false);
//            }else if (appontmentModel.getService_id().equals("2")){
//                binding.roadl.setChecked(true);
//                binding.doral.setChecked(false);
//            }else{
//                binding.doral.setChecked(true);
//                binding.roadl.setChecked(true);
//            }
//
        binding.doral.setChecked(true);
        binding.roadl.setChecked(true);


        appointmentAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, int which, Object object) {
                //  selectPos = position;
                appontmentModel = (AppontmentModel) object;
                if (which == 1) {
                    startActivityForResult(new Intent(NewDashboardActivity.this, ReasonActivity.class).putExtra("id", ((AppontmentModel) object).getId()), 2005);
                }
                if (which == 2) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("patient_id", appontmentModel.getPatient_id());
                    presenterInterface.sendRequest(NewDashboardActivity.this, "", map, ApiCallInterface.GET_PARENT_ID, true);

                }
                if (which == 3) {

                    startActivity(new Intent(NewDashboardActivity.this, RoadLStatusActivity.class).putExtra("parent_id", appontmentModel.getParent_id()).putExtra("patient_id", appontmentModel.getPatient_id()));
                }

            }
        });

        binding.btnTrackRoadL.setOnSwipeCompleteListener_forward_reverse(new OnSwipeCompleteListener() {
            @Override
            public void onSwipe_Forward(Swipe_Button_View swipe_button_view) {
                swipe_button_view.setVisibility(View.GONE);
                Intent intent = new Intent(NewDashboardActivity.this, RoadLStatusActivity.class);
                intent.putExtra("parent_id", parent_id);
                //  intent.putExtra("type_id", requestModel.getType_id());
                startActivity(intent);
            }

            @Override
            public void onSwipe_Reverse(Swipe_Button_View swipe_button_view) {

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

    @Override
    protected void onResume() {
        super.onResume();
        askPermission();
    }

    private void setClick() {
        binding.btnOffline.setOnClickListener(this::onClick);
        binding.cardAppointment.setOnClickListener(this::onClick);
        binding.cardRequest.setOnClickListener(this::onClick);
        binding.btnGenerateForm.setOnClickListener(this::onClick);
        binding.imgMenu.setOnClickListener(this::onClick);
        binding.imgNotificatin.setOnClickListener(this::onClick);
        binding.leftMenuHome.flProfile.setOnClickListener(this::onClick);
        binding.leftMenuHome.llHome.setOnClickListener(this::onClick);
        binding.leftMenuHome.llProfile.setOnClickListener(this::onClick);
        binding.leftMenuHome.llRequest.setOnClickListener(this::onClick);
        binding.leftMenuHome.llChangePassword.setOnClickListener(this::onClick);
        binding.leftMenuHome.llSupport.setOnClickListener(this::onClick);
        binding.leftMenuHome.llLogout.setOnClickListener(this::onClick);
        binding.leftMenuHome.llAppointments.setOnClickListener(this::onClick);
        binding.leftMenuHome.llCovidForms.setOnClickListener(this::onClick);
        binding.leftMenuHome.llEditProfile.setOnClickListener(this::onClick);
        binding.leftMenuHome.llRequestHistory.setOnClickListener(this::onClick);
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
        if (view.getId() == R.id.fl_profile) {
            opneCameraGallery(1028);
        }
        if (view.getId() == R.id.img_notificatin) {
            startActivity(new Intent(NewDashboardActivity.this, NotificationList.class));
        }
        if (view.getId() == R.id.card_appointment) {
            startActivity(new Intent(NewDashboardActivity.this, AppointmentActivity.class));
        }
        if (view.getId() == R.id.ll_covid_forms) {
            startActivity(new Intent(NewDashboardActivity.this, FormsActivity.class));
        }
        if (view.getId() == R.id.btn_generate_form) {
            startActivity(new Intent(NewDashboardActivity.this, CovidFormActivity.class));
        }
        if (view.getId() == R.id.llEditProfile) {
            startActivity(new Intent(NewDashboardActivity.this, EditUserProfileActivity.class));
        }
        if (view.getId() == R.id.card_request) {
            startActivity(new Intent(NewDashboardActivity.this, GetRequestActivity.class));
        }
        if (view.getId() == R.id.ll_request_history) {
            startActivity(new Intent(NewDashboardActivity.this, HistoryActivity.class));
        }
        if (view.getId() == R.id.ll_home) {
            binding.drawelayoutHomeactivity.closeDrawer(GravityCompat.START, true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() { }
            }, 300);
        }
        if (view.getId() == R.id.ll_profile) {
            binding.drawelayoutHomeactivity.closeDrawer(GravityCompat.START, true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    UserModel model = new MyPref(context).getUserData();
                    if (model.getDesignation_id().equals("1") || model.getDesignation_id().equals("2")) {
                        startActivityForResult(new Intent(NewDashboardActivity.this, NurseProfileActivity.class), 101);
                    } else {
                        startActivity(new Intent(NewDashboardActivity.this, CreateProfileActivity.class));
                    }

//                    if (!new MyPref(context).getUserData().getDesignation_id().equals("2")) {
//                        startActivity(new Intent(NewDashboardActivity.this, NurseProfileActivity.class));
//                    }else{
//                        startActivity(new Intent(NewDashboardActivity.this, NurseProfileActivity.class));
//                    }
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
                    startActivity(new Intent(context, Patient_list.class));
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

    private void opneCameraGallery(int requestCode) {
        new ImagePickerUtils(context, false, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }, requestCode);
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
        if (requestCode == ApiCallInterface.GET_PARENT_ID) {
            ParentIdModel baseModel = (ParentIdModel) success;
            if (baseModel.isStatus().equalsIgnoreCase("true")) {

                if (StringUtils.isNotEmpty(baseModel.getData().getParent_id())) {
                    Intent intent = new Intent(NewDashboardActivity.this, RoadLStatusActivity.class);
                    intent.putExtra("parent_id", baseModel.getData().getParent_id());
                    startActivity(intent);
                } else {
                    startActivity(new Intent(NewDashboardActivity.this, RoadlRequestActivity.class).putExtra("patient_id", appontmentModel.getPatient_id()));
                }

            } else {
                startActivity(new Intent(NewDashboardActivity.this, RoadlRequestActivity.class).putExtra("patient_id", appontmentModel.getPatient_id()));
            }

        }

        if (requestCode == ApiCallInterface.USER_DETAILS) {
            UserModel basemodel = (UserModel) success;
            if (basemodel.isStatus().equals("true")) {

                if (basemodel.getData() != null) {

                    new MyPref(context).setUserData(basemodel.getData());
                    TextView tvUserName = findViewById(R.id.tvUserName);
                    CircleImageView image =findViewById(R.id.fl_profile);
                    TextView tv_type = findViewById(R.id.tv_type);
                    //Log.e("Designation_id:::", basemodel.getData().getDesignation_id());
                    if (basemodel.getData().getDesignation_id().equals("1")) {
                        tv_type.setText("Nurse");
                    } else if (basemodel.getData().getDesignation_id().equals("2")) {
                        tv_type.setText("RN");
                    } else
                        tv_type.setText(basemodel.getData().getRoles().get(basemodel.getData().getRoles().size() - 1).getName());
                    tvUserName.setText(basemodel.getData().getFirst_name() + " " + basemodel.getData().getLast_name());
                    binding.btnOffline.setText(basemodel.getData().getIs_available().equals("1") ? "Online" : "Offline");
                    binding.btnOffline.setCompoundDrawablesWithIntrinsicBounds(basemodel.getData().getIs_available().equals("1") ? R.drawable.ic_online : R.drawable.ic_offline, 0, 0, 0);
//                    try{
//                        String data=(String)success;
//                        JSONObject jObject = new JSONObject(data);
//                        JSONObject userData=jObject.getJSONObject("data");
//                        int profileStatus=userData.getInt("status");
//                        Log.e("profileStatus::",""+profileStatus);
//                        if (profileStatus==0){
//                            startActivity(new Intent(NewDashboardActivity.this, NurseProfileActivity.class));
//                        }
//                    }catch (JSONException e){
//
//                    }

                    Glide.with(this)
                            .load(basemodel.getData().getAvatar_image())
                            .into(image);

                    if (StringUtils.isNotEmpty(basemodel.getData().getIsApplicantStatus()) && basemodel.getData().getIsApplicantStatus().equals("0")) {
                        startActivity(new Intent(NewDashboardActivity.this, NurseProfileActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        finish();
                    }

//                    if (basemodel.getService_id()==null){
//                        binding.doral.setChecked(true);
//                        binding.roadl.setChecked(true);
//                    }else{
//                        if (basemodel.getData().getUser().getService_id().equals("1")){
//                            binding.doral.setChecked(true);
//                            binding.roadl.setChecked(false);
//                        }else if (basemodel.getData().getService_id().equals("2")){
//                            binding.doral.setChecked(false);
//                            binding.roadl.setChecked(true);
//                        }else {
//                            binding.doral.setChecked(true);
//                            binding.roadl.setChecked(true);
//                        }
//
//                    }

//                    if (!basemodel.getData().getDesignation_id().equals("1")) {
//                        tv_type.setText("Nurse");
//                    } else if (basemodel.getData().getDesignation_id().equals("2")) {
//                        tv_type.setText("RN");
//                    } else
//                        tv_type.setText(basemodel.getData().getRoles().get(basemodel.getData().getRoles().size() - 1).getName());

                   /* if (StringUtils.isNotEmpty(basemodel.getData().getParent_id()) && StringUtils.isNotEmpty(basemodel.getData().getPatient_request_id())) {
                        binding.btnTrackRoadL.setVisibility(View.VISIBLE);
                        parent_id = basemodel.getData().getParent_id();
                        patient_request_id = basemodel.getData().getPatient_request_id();
                    } else {
                        parent_id = "162";
                        patient_request_id = "163";
                    }*/

                    binding.btnTrackRoadL.setVisibility(View.GONE);



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

        if (requestCode == ApiCallInterface.Upload_image) {
            BaseModel baseModel = (BaseModel) success;
            if (baseModel.isStatus().equals("true")) {
                new CustomAlertDialog(context, baseModel.getMessage(), "Confirm your email address", "OK", "", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        finish();
                    }
                }).show();


            } else {
                errorMessage(binding.getRoot(), baseModel.getMessage());
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            presenterInterface.sendRequest(context, null, null, ApiCallInterface.USER_DETAILS);
        }

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case GpsUtils.GPS_REQUEST:

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService(new Intent(NewDashboardActivity.this, GeoService.class));
                    } else {
                        startService(new Intent(NewDashboardActivity.this, GeoService.class));
                    }
                    SingleShotLocationProvider.requestSingleUpdate(NewDashboardActivity.this, new SingleShotLocationProvider.LocationCallback() {
                        @Override
                        public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                            new MyPref(NewDashboardActivity.this).setData(MyPref.Keys.LAT, location.latitude + "");
                            new MyPref(NewDashboardActivity.this).setData(MyPref.Keys.LAG, location.longitude + "");

                        }
                    });

                    break;

            }

        } else if (resultCode == RESULT_CANCELED) {
            switch (requestCode) {
                case GpsUtils.GPS_REQUEST:
                    onGPS();
                    break;
            }

        }

        if (requestCode == 1028 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (data.hasExtra("path")) {
                Uri uri = data.getParcelableExtra("path");
                 str = uri.getPath();
                if (uri != null) {

                    binding.leftMenuHome.flProfile.setVisibility(View.VISIBLE);
                    binding.leftMenuHome.flProfile.setTag(uri.getPath());
                    binding.leftMenuHome.flProfile.setImageURI(uri);
//                    binding.imgIdProof1.setVisibility(View.VISIBLE);
//                    binding.imgIdProof1.setTag(uri.getPath());
//                    binding.imgIdProof1.setImageURI(uri);

                    updateimage();

                }
            }
        }
    }

    private void updateimage() {
//        HashMap<String, String> map = new HashMap<>();
//        map.put("avatar",str );
//        presenterInterface.sendRequest(context, null, map, ApiCallInterface.Upload_image);

        presenterInterface.sendRequest(NewDashboardActivity.this,
                "", null, ApiCallInterface.Upload_image,
                binding.leftMenuHome.flProfile.getTag() != null ?  binding.leftMenuHome.flProfile.getTag().toString() : ""
        );}


    private void askPermission() {
        new BottomSheetAskPermission(this, new BottomSheetAskPermission.PermissionResultListener() {
            @Override
            public void onAllPermissionAllow() {

                onGPS();

            }

            @Override
            public void onPermissionDeny() {


            }
        }, BottomSheetAskPermission.ACCESS_COARSE_LOCATION, BottomSheetAskPermission.ACCESS_FINE_LOCATION
        ).show(getSupportFragmentManager(), "");
    }

    private void onGPS() {
        new GpsUtils(this).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {

                if (isGPSEnable) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService(new Intent(NewDashboardActivity.this, GeoService.class));
                    } else {
                        startService(new Intent(NewDashboardActivity.this, GeoService.class));
                    }
                    SingleShotLocationProvider.requestSingleUpdate(NewDashboardActivity.this, new SingleShotLocationProvider.LocationCallback() {
                        @Override
                        public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {

                            new MyPref(NewDashboardActivity.this).setData(MyPref.Keys.LAT, location.latitude + "");
                            new MyPref(NewDashboardActivity.this).setData(MyPref.Keys.LAG, location.longitude + "");

                        }
                    });

                }

            }
        });
    }
}