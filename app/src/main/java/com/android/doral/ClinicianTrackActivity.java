package com.android.doral;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.Vendor.Activity.VendorDashboardActivity;
import com.android.doral.Utils.AppClass;
import com.android.doral.Utils.BottomSheetAskPermission;
import com.android.doral.Utils.GpsUtils;
import com.android.doral.Utils.Logger;
import com.android.doral.Utils.MyPref;
import com.android.doral.Utils.SingleShotLocationProvider;
import com.android.doral.Utils.StringUtils;
import com.android.doral.adapter.CustomInfoWindowAdapter;
import com.android.doral.adapter.TransitHorizontalAdapter;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityMainBinding;
import com.android.doral.dialog.CustomAlertDialog;
import com.android.doral.dialog.CustomDrivingModeDialog;
import com.android.doral.dialog.CustomTransitModeDialog;
import com.android.doral.dialog.TransitModeDialog;
import com.android.doral.dialog.TransitModeRecommendedDialog;
import com.android.doral.directions.CarMoveAnim;
import com.android.doral.directions.GetDirectionsResponse;
import com.android.doral.directions.GoogleMapUtils;
import com.android.doral.directions.OnGoogleMapEventListener;
import com.android.doral.directions.PolyUtil;
import com.android.doral.model.StepsModel;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.BaseModel;
import com.android.doral.retrofit.model.RoadRequestModel;
import com.android.doral.retrofit.model.UserModel;
import com.android.doral.service.GeoService;
import com.android.doral.socket.SocketEmitType;
import com.android.doral.socket.SocketIOConnectionHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static com.android.doral.Utils.Utility.Bicycling;
import static com.android.doral.Utils.Utility.Driving;
import static com.android.doral.Utils.Utility.TRAVEL_MODES;
import static com.android.doral.Utils.Utility.Transit;
import static com.android.doral.Utils.Utility.Walking;

public class ClinicianTrackActivity extends AppCompatActivity implements OnMapReadyCallback, BaseViewInterface, SocketIOConnectionHelper.OnSocketResponseListerner {
    private GoogleMap gMap;
    private Marker marker;
    private ArrayList<Marker> dropmarkerPlaces;
    String lat = "", lng = "";
    String parent_id;
    BasePresenterInterface presenterInterface;
    ActivityMainBinding binding;
    private final List<UserModel> roadLUsers = new ArrayList<>();
    RoadRequestModel roadRequestModel;
    MyPref myPref;
    UserModel sendEmitUser;
    private String selectedReferel = "";
    List<UserModel> clinicianList = new ArrayList<>();
    List<StepsModel> listSteps = new ArrayList<>();
    PolylineOptions polyLineOptions;
    String arrivalTime="",startAddress="";
    TransitModeRecommendedDialog transitModeRecommendedDialog;
    private TextToSpeech textToSpeechSystem;
    boolean isMute=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        myPref = new MyPref(ClinicianTrackActivity.this);

//        if (myPref.getUserData().getRoles().get(myPref.getUserData().getRoles().size() - 1).getName().equalsIgnoreCase("clinician")) {
//
//            binding.btnComplete.setVisibility(View.VISIBLE);
//        } else {
//            binding.btnComplete.setVisibility(View.GONE);
//        }

        init();

    }

    private void init() {

        dropmarkerPlaces = new ArrayList<>();
        presenterInterface = new Presenter(this);


        if (getIntent().hasExtra("parent_id")) {

            parent_id = (String) getIntent().getStringExtra("parent_id");
            new MyPref(getApplicationContext()).setData(MyPref.Keys.PARENT_ID, parent_id);
           /* lat = requestModel.getLatitude();
            lng = requestModel.getLongitude();*/
            setUpGoogleMap();

        }


//        if (isMute){
//
//            binding.imgMute.setImageDrawable(getResources().getDrawable(R.drawable.ic_mute));
//
//        }else {
//
//            binding.imgMute.setImageDrawable(getResources().getDrawable(R.drawable.ic_unmute));
//
//        }

        binding.imgMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isMute){

                    binding.imgMute.setImageDrawable(getResources().getDrawable(R.drawable.ic_mute));
                    isMute=false;

                }else {

                    binding.imgMute.setImageDrawable(getResources().getDrawable(R.drawable.ic_unmute));
                    isMute=true;

                }

            }
        });


        binding.btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, String> map = new HashMap<>();
                map.put("patient_requests_id", sendEmitUser.getId());
                map.put("client_id", roadRequestModel.getData().getPatient().getId());
                map.put("latitude", "" + new MyPref(ClinicianTrackActivity.this).getData(MyPref.Keys.LAT));
                map.put("longitude", "" + new MyPref(ClinicianTrackActivity.this).getData(MyPref.Keys.LAG));
                map.put("status", "3");
                presenterInterface.sendRequest(ClinicianTrackActivity.this, "", map, ApiCallInterface.UPDATE_LOCATION, true);

            }
        });

        if (TRAVEL_MODES.equals(Driving)) {
            binding.imgTravelMode.setImageDrawable(getResources().getDrawable(R.drawable.ic_driving));
            binding.tvTravelMode.setText("Driving");
        } else if (TRAVEL_MODES.equals(Walking)) {
            binding.imgTravelMode.setImageDrawable(getResources().getDrawable(R.drawable.ic_walking));
            binding.tvTravelMode.setText("Walking");
        } else if (TRAVEL_MODES.equals(Transit)) {
            binding.imgTravelMode.setImageDrawable(getResources().getDrawable(R.drawable.ic_transit));
            binding.tvTravelMode.setText("Transit");
        } else if (TRAVEL_MODES.equals(Bicycling)) {
            binding.imgTravelMode.setImageDrawable(getResources().getDrawable(R.drawable.ic_bicycle));
            binding.tvTravelMode.setText("Bicycle");
        } else {
            TRAVEL_MODES=Driving;
            binding.imgTravelMode.setImageDrawable(getResources().getDrawable(R.drawable.ic_driving));
            binding.tvTravelMode.setText("Driving");
        }

        binding.linTravelMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new CustomDrivingModeDialog(ClinicianTrackActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (TRAVEL_MODES.equals(Driving)) {
                            binding.imgTravelMode.setImageDrawable(getResources().getDrawable(R.drawable.ic_driving));
                            binding.tvTravelMode.setText("Driving");
                            selectDrivingMode();
                        } else if (TRAVEL_MODES.equals(Walking)) {
                            binding.imgTravelMode.setImageDrawable(getResources().getDrawable(R.drawable.ic_walking));
                            binding.tvTravelMode.setText("Walking");
                            selectDrivingMode();
                        } else if (TRAVEL_MODES.equals(Transit)) {

                            binding.imgTravelMode.setImageDrawable(getResources().getDrawable(R.drawable.ic_transit));
                            binding.tvTravelMode.setText("Transit");
                            selectDrivingMode();

                        } else if (TRAVEL_MODES.equals(Bicycling)) {
                            binding.imgTravelMode.setImageDrawable(getResources().getDrawable(R.drawable.ic_bicycle));
                            binding.tvTravelMode.setText("Bicycle");
                            selectDrivingMode();
                        } else {
                            binding.imgTravelMode.setImageDrawable(getResources().getDrawable(R.drawable.ic_driving));
                            binding.tvTravelMode.setText("Driving");
                            selectDrivingMode();
                        }


                    }
                }).show();

            }
        });

        binding.tvType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.spType.performClick();
            }
        });

        binding.spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                UserModel userModel = (UserModel) binding.spType.getSelectedItem();
                userModel.setLastLat(0);
                userModel.setLastLng(0);
                binding.tvType.setText(userModel.getReferral_type());
                selectedReferel = userModel.getReferral_type();
                if (userModel.getReferral_type().equalsIgnoreCase("all")) {

                    binding.bottom.setVisibility(View.GONE);
                    if (roadLUsers != null && gMap != null) {

                        for (int i = 0; i < roadLUsers.size(); i++) {

                            roadLUsers.get(i).setLastLat(0);
                            roadLUsers.get(i).setLastLng(0);
                            if (StringUtils.isNotEmpty(roadLUsers.get(i).getLatitude()) && StringUtils.isNotEmpty(roadLUsers.get(i).getLongitude())) {

                                addVenderMarker(roadLUsers.get(i), i);

                            }

                        }
                    }

                    if (roadRequestModel != null) {

                        if (roadRequestModel.getData().getClinicians() != null) {

                            clinicianList.clear();
                            for (int i = 0; i < roadRequestModel.getData().getClinicians().size(); i++) {

                                if (roadRequestModel.getData().getClinicians().get(i).isStatus().equals("2")) {

                                    clinicianList.add(roadRequestModel.getData().getClinicians().get(i));

                                }

                            }


                        }

                        Collections.sort(clinicianList, new Comparator<UserModel>() {

                            @Override
                            public int compare(UserModel t, UserModel t1) {
                                int getTime = 0;
                                int getTime1 = 0;
                                if (t.getTravel_time() != null) {
                                    getTime = Integer.parseInt(t.getTravel_time());
                                }
                                if (t1.getTravel_time() != null) {
                                    getTime1 = Integer.parseInt(t1.getTravel_time());
                                }
                                return getTime - getTime1;
                            }
                        });

                        for (int i = 0; i < clinicianList.size(); i++) {

                            Log.e("clinicianList", "" + clinicianList.get(i).getReferral_type());
                            if (clinicianList.get(i).getReferral_type().equals("Clinician")) {

                                binding.bottom.setVisibility(View.VISIBLE);
                                binding.linClinician.setVisibility(View.VISIBLE);

                            }

                            if (clinicianList.size() >= 3) {

                                if (!clinicianList.get(i).getReferral_type().equals("Clinician")) {

                                    if (clinicianList.get(i).isStatus().equals("2")) {


                                        if (clinicianList.get(i).getTravel_time() != null) {

                                            binding.linOne.setVisibility(View.VISIBLE);
                                            binding.tvUserName1.setText(clinicianList.get(i).getReferral_type());

                                            try {

                                                if (clinicianList.get(i).getAccepted_time() != null) {
                                                    String totalMinutes = "";
                                                    try {

                                                        if (clinicianList.get(i).getPreparation_time() != null) {

                                                            String prepareTime = clinicianList.get(i).getPreparation_time();
                                                            String acceptedTime = clinicianList.get(i).getAccepted_time();

                                                            String preparationTimeDiff = getDateTimeDifference(acceptedTime);
                                                            Log.e("preparationTimeDiff", preparationTimeDiff);

                                                            String preparation = getPreparationTime(prepareTime, preparationTimeDiff);
                                                            Log.e("preparation", preparation);

                                                            String preparationTime = getHH_MM_SS_to_minute(preparation);
                                                            Log.e("preparationTime", preparationTime);

                                                            if (clinicianList.get(i).getTravel_time() != null) {

                                                                String travelTime = clinicianList.get(i).getTravel_time();
                                                                binding.tvTime1.setText(getDisplayTime(travelTime, preparationTime));

                                                            }

                                                        }

                                                    } catch (ParseException e) {
                                                        e.printStackTrace();

                                                    }

                                                }

                                            } catch (NumberFormatException e) {
                                            }


                                        } else {

                                            binding.linOne.setVisibility(View.VISIBLE);
                                            binding.tvUserName1.setText(clinicianList.get(i).getReferral_type());
                                            binding.tvTime1.setText("00 Min");

                                        }

                                        continue;

                                    }

                                    if (clinicianList.get(i).isStatus().equals("2")) {

                                        if (clinicianList.get(i).getTravel_time() != null) {

                                            binding.linTwo.setVisibility(View.VISIBLE);
                                            binding.tvUserName2.setText(clinicianList.get(i).getReferral_type());
                                            try {

                                                if (clinicianList.get(i).getAccepted_time() != null) {
                                                    String totalMinutes = "";
                                                    try {

                                                        if (clinicianList.get(i).getPreparation_time() != null) {

                                                            String prepareTime = clinicianList.get(i).getPreparation_time();
                                                            String acceptedTime = clinicianList.get(i).getAccepted_time();

                                                            String preparationTimeDiff = getDateTimeDifference(acceptedTime);
                                                            Log.e("preparationTimeDiff", preparationTimeDiff);

                                                            String preparation = getPreparationTime(prepareTime, preparationTimeDiff);
                                                            Log.e("preparation", preparation);

                                                            String preparationTime = getHH_MM_SS_to_minute(preparation);
                                                            Log.e("preparationTime", preparationTime);

                                                            if (clinicianList.get(i).getTravel_time() != null) {

                                                                String travelTime = clinicianList.get(i).getTravel_time();
                                                                binding.tvTime2.setText(getDisplayTime(travelTime, preparationTime));

                                                            }

                                                        }

                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }

                                                } else {


                                                }

                                            } catch (NumberFormatException e) {
                                            }

                                        } else {

                                            binding.linTwo.setVisibility(View.VISIBLE);
                                            binding.tvUserName2.setText(clinicianList.get(i).getReferral_type());
                                            binding.tvTime2.setText("00 Min");

                                        }

                                    }


                                }

                            }

                            if (clinicianList.size() <= 2) {

                                if (!clinicianList.get(i).getReferral_type().equals("Clinician")) {

                                    if (i == 0) {

                                        if (clinicianList.get(i).getTravel_time() != null) {

                                            binding.linOne.setVisibility(View.VISIBLE);
                                            binding.tvUserName1.setText(clinicianList.get(i).getReferral_type());

                                            try {

                                                if (clinicianList.get(i).getAccepted_time() != null) {
                                                    String totalMinutes = "";
                                                    try {

                                                        if (clinicianList.get(i).getPreparation_time() != null) {

                                                            String prepareTime = clinicianList.get(i).getPreparation_time();
                                                            String acceptedTime = clinicianList.get(i).getAccepted_time();

                                                            String preparationTimeDiff = getDateTimeDifference(acceptedTime);
                                                            Log.e("preparationTimeDiff", preparationTimeDiff);

                                                            String preparation = getPreparationTime(prepareTime, preparationTimeDiff);
                                                            Log.e("preparation", preparation);

                                                            String preparationTime = getHH_MM_SS_to_minute(preparation);
                                                            Log.e("preparationTime", preparationTime);

                                                            if (clinicianList.get(i).getTravel_time() != null) {

                                                                String travelTime = clinicianList.get(i).getTravel_time();
                                                                binding.tvTime1.setText(getDisplayTime(travelTime, preparationTime));

                                                            }

                                                        }

                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }

                                                }

                                            } catch (NumberFormatException e) {
                                            }


                                        } else {

                                            binding.linOne.setVisibility(View.VISIBLE);
                                            binding.tvUserName1.setText(clinicianList.get(i).getReferral_type());
                                            binding.tvTime1.setText("00 Min");

                                        }

                                    }

                                }

                            }

                        }
                    }


                } else {

                    binding.bottom.setVisibility(View.VISIBLE);
                    binding.linOne.setVisibility(View.GONE);
                    binding.linTwo.setVisibility(View.GONE);

                    if (roadLUsers != null && gMap != null) {

                        for (int i = 0; i < roadLUsers.size(); i++) {

                            if (StringUtils.isNotEmpty(roadLUsers.get(i).getId()) && !roadLUsers.get(i).getId().equalsIgnoreCase(userModel.getId())) {

                                if (roadLUsers.get(i).getMarker() != null) {

                                    roadLUsers.get(i).getMarker().remove();
                                    roadLUsers.get(i).setMarker(null);

                                }
                                if (roadLUsers.get(i).getLastPolyline() != null) {

                                    roadLUsers.get(i).getLastPolyline().remove();
                                    roadLUsers.get(i).setLastPolyline(null);

                                }

                            }

                        }

                    }

                    addVenderMarker(userModel, getUserPos(userModel.getId()));

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ((AppClass) getApplication()).createSocketConnection();
        ((AppClass) getApplication()).setAppListerner(parent_id);
        ((AppClass) getApplication()).setOnSocketResponseListerner(this);

    }

    public void selectDrivingMode() {

        UserModel userModel = (UserModel) binding.spType.getSelectedItem();
        userModel.setLastLat(0);
        userModel.setLastLng(0);
        binding.tvType.setText(userModel.getReferral_type());
        selectedReferel = userModel.getReferral_type();

        if (userModel.getReferral_type().equalsIgnoreCase("all")) {

            binding.bottom.setVisibility(View.GONE);
            if (roadLUsers != null && gMap != null) {

                for (int i = 0; i < roadLUsers.size(); i++) {

                    roadLUsers.get(i).setLastLat(0);
                    roadLUsers.get(i).setLastLng(0);
                    if (StringUtils.isNotEmpty(roadLUsers.get(i).getLatitude()) && StringUtils.isNotEmpty(roadLUsers.get(i).getLongitude())) {

                        addVenderMarker(roadLUsers.get(i), i);

                    }

                }
            }

            if (roadRequestModel != null) {

                if (roadRequestModel.getData().getClinicians() != null) {

                    clinicianList.clear();
                    for (int i = 0; i < roadRequestModel.getData().getClinicians().size(); i++) {

                        if (roadRequestModel.getData().getClinicians().get(i).isStatus().equals("2")) {

                            clinicianList.add(roadRequestModel.getData().getClinicians().get(i));

                        }

                    }


                }

                Collections.sort(clinicianList, new Comparator<UserModel>() {

                    @Override
                    public int compare(UserModel t, UserModel t1) {
                        int getTime = 0;
                        int getTime1 = 0;
                        if (t.getTravel_time() != null) {
                            getTime = Integer.parseInt(t.getTravel_time());
                        }
                        if (t1.getTravel_time() != null) {
                            getTime1 = Integer.parseInt(t1.getTravel_time());
                        }
                        return getTime - getTime1;
                    }
                });

                for (int i = 0; i < clinicianList.size(); i++) {

                    Log.e("clinicianList", "" + clinicianList.get(i).getReferral_type());
                    if (clinicianList.get(i).getReferral_type().equals("Clinician")) {

                        binding.bottom.setVisibility(View.VISIBLE);
                        binding.linClinician.setVisibility(View.VISIBLE);

                    }

                    if (clinicianList.size() >= 3) {

                        if (!clinicianList.get(i).getReferral_type().equals("Clinician")) {

                            if (clinicianList.get(i).isStatus().equals("2")) {


                                if (clinicianList.get(i).getTravel_time() != null) {

                                    binding.linOne.setVisibility(View.VISIBLE);
                                    binding.tvUserName1.setText(clinicianList.get(i).getReferral_type());

                                    try {

                                        if (clinicianList.get(i).getAccepted_time() != null) {
                                            String totalMinutes = "";
                                            try {

                                                if (clinicianList.get(i).getPreparation_time() != null) {

                                                    String prepareTime = clinicianList.get(i).getPreparation_time();
                                                    String acceptedTime = clinicianList.get(i).getAccepted_time();

                                                    String preparationTimeDiff = getDateTimeDifference(acceptedTime);
                                                    Log.e("preparationTimeDiff", preparationTimeDiff);

                                                    String preparation = getPreparationTime(prepareTime, preparationTimeDiff);
                                                    Log.e("preparation", preparation);

                                                    String preparationTime = getHH_MM_SS_to_minute(preparation);
                                                    Log.e("preparationTime", preparationTime);

                                                    if (clinicianList.get(i).getTravel_time() != null) {

                                                        String travelTime = clinicianList.get(i).getTravel_time();
                                                        binding.tvTime1.setText(getDisplayTime(travelTime, preparationTime));

                                                    }

                                                }

                                            } catch (ParseException e) {
                                                e.printStackTrace();

                                            }

                                        }

                                    } catch (NumberFormatException e) {
                                    }


                                } else {

                                    binding.linOne.setVisibility(View.VISIBLE);
                                    binding.tvUserName1.setText(clinicianList.get(i).getReferral_type());
                                    binding.tvTime1.setText("00 Min");


                                }

                                continue;

                            }

                            if (clinicianList.get(i).isStatus().equals("2")) {

                                if (clinicianList.get(i).getTravel_time() != null) {

                                    binding.linTwo.setVisibility(View.VISIBLE);
                                    binding.tvUserName2.setText(clinicianList.get(i).getReferral_type());
                                    try {

                                        if (clinicianList.get(i).getAccepted_time() != null) {
                                            String totalMinutes = "";
                                            try {

                                                if (clinicianList.get(i).getPreparation_time() != null) {

                                                    String prepareTime = clinicianList.get(i).getPreparation_time();
                                                    String acceptedTime = clinicianList.get(i).getAccepted_time();

                                                    String preparationTimeDiff = getDateTimeDifference(acceptedTime);
                                                    Log.e("preparationTimeDiff", preparationTimeDiff);

                                                    String preparation = getPreparationTime(prepareTime, preparationTimeDiff);
                                                    Log.e("preparation", preparation);

                                                    String preparationTime = getHH_MM_SS_to_minute(preparation);
                                                    Log.e("preparationTime", preparationTime);

                                                    if (clinicianList.get(i).getTravel_time() != null) {

                                                        String travelTime = clinicianList.get(i).getTravel_time();
                                                        binding.tvTime2.setText(getDisplayTime(travelTime, preparationTime));

                                                    }

                                                }

                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }

                                        } else {


                                        }

                                    } catch (NumberFormatException e) {
                                    }

                                } else {

                                    binding.linTwo.setVisibility(View.VISIBLE);
                                    binding.tvUserName2.setText(clinicianList.get(i).getReferral_type());
                                    binding.tvTime2.setText("00 Min");

                                }

                            }


                        }

                    }

                    if (clinicianList.size() <= 2) {

                        if (!clinicianList.get(i).getReferral_type().equals("Clinician")) {

                            if (i == 0) {

                                if (clinicianList.get(i).getTravel_time() != null) {

                                    binding.linOne.setVisibility(View.VISIBLE);
                                    binding.tvUserName1.setText(clinicianList.get(i).getReferral_type());

                                    try {

                                        if (clinicianList.get(i).getAccepted_time() != null) {
                                            String totalMinutes = "";
                                            try {

                                                if (clinicianList.get(i).getPreparation_time() != null) {

                                                    String prepareTime = clinicianList.get(i).getPreparation_time();
                                                    String acceptedTime = clinicianList.get(i).getAccepted_time();

                                                    String preparationTimeDiff = getDateTimeDifference(acceptedTime);
                                                    Log.e("preparationTimeDiff", preparationTimeDiff);

                                                    String preparation = getPreparationTime(prepareTime, preparationTimeDiff);
                                                    Log.e("preparation", preparation);

                                                    String preparationTime = getHH_MM_SS_to_minute(preparation);
                                                    Log.e("preparationTime", preparationTime);

                                                    if (clinicianList.get(i).getTravel_time() != null) {

                                                        String travelTime = clinicianList.get(i).getTravel_time();
                                                        binding.tvTime1.setText(getDisplayTime(travelTime, preparationTime));

                                                    }

                                                }

                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }

                                        }

                                    } catch (NumberFormatException e) {
                                    }


                                } else {

                                    binding.linOne.setVisibility(View.VISIBLE);
                                    binding.tvUserName1.setText(clinicianList.get(i).getReferral_type());
                                    binding.tvTime1.setText("00 Min");

                                }

                            }

                        }

                    }

                }
            }


        } else {

            binding.bottom.setVisibility(View.VISIBLE);
            binding.linOne.setVisibility(View.GONE);
            binding.linTwo.setVisibility(View.GONE);

            if (roadLUsers != null && gMap != null) {

                for (int i = 0; i < roadLUsers.size(); i++) {

                    if (StringUtils.isNotEmpty(roadLUsers.get(i).getId()) && !roadLUsers.get(i).getId().equalsIgnoreCase(userModel.getId())) {

                        if (roadLUsers.get(i).getMarker() != null) {

                            roadLUsers.get(i).getMarker().remove();
                            roadLUsers.get(i).setMarker(null);

                        }
                        if (roadLUsers.get(i).getLastPolyline() != null) {

                            roadLUsers.get(i).getLastPolyline().remove();
                            roadLUsers.get(i).setLastPolyline(null);

                        }

                    }
                }
            }

            addVenderMarker(userModel, getUserPos(userModel.getId()));

        }

    }

    private void animateCar(GoogleMap googleMap, LatLng start, LatLng end) {

        if (marker == null) {
            marker = googleMap.addMarker(getMarker(start, R.drawable.ic_clinician_map));
        }
        CarMoveAnim.startcarAnimation(marker, googleMap, start, end, 1000, null);
    }

    private MarkerOptions getMarker(LatLng latLng, int drawable) {
        BitmapDescriptor icon;
        icon = BitmapDescriptorFactory.fromResource(drawable);
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).icon(icon);
        return markerOptions;
    }

    private void setUpGoogleMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.home_map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

//        if (!Places.isInitialized())
//            Places.initialize(getApplicationContext(), getString(R.string.map_key));
    }

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

                        startForegroundService(new Intent(ClinicianTrackActivity.this, GeoService.class));

                    } else {

                        startService(new Intent(ClinicianTrackActivity.this, GeoService.class));

                    }
                    SingleShotLocationProvider.requestSingleUpdate(ClinicianTrackActivity.this, new SingleShotLocationProvider.LocationCallback() {
                        @Override
                        public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {


                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    //addMarkerWithNavigate(location.latitude, location.longitude);
                                }
                            });


                        }
                    });

                }

            }
        });
    }

    private void AddDropMarker() {
        if (gMap != null) {
            CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(ClinicianTrackActivity.this);
            gMap.setInfoWindowAdapter(adapter);
            Marker dropLocation = gMap.addMarker(getMarker(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)), R.drawable.patient_icon));
            dropLocation.setTitle(getIntent().hasExtra("name") ? getIntent().getStringExtra("name") : "Patient");
            dropLocation.setZIndex(10);
            dropLocation.setSnippet(new Gson().toJson(roadRequestModel.getData().getPatient()));
            dropLocation.showInfoWindow();
            dropmarkerPlaces.add(dropLocation);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case GpsUtils.GPS_REQUEST:

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                        startForegroundService(new Intent(ClinicianTrackActivity.this, GeoService.class));

                    } else {

                        startService(new Intent(ClinicianTrackActivity.this, GeoService.class));

                    }

                    SingleShotLocationProvider.requestSingleUpdate(ClinicianTrackActivity.this, new SingleShotLocationProvider.LocationCallback() {
                        @Override
                        public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {


                            // getKm("" + location.latitude, "" + location.longitude);


                            //                     addMarkerWithNavigate(location.latitude, location.longitude);


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
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (googleMap != null) {

            gMap = googleMap;
            gMap.setTrafficEnabled(true);
            HashMap<String, String> map = new HashMap<>();
            map.put("parent_id", parent_id);

            if (!new MyPref(ClinicianTrackActivity.this).getUserData().getRoles().get(new MyPref(ClinicianTrackActivity.this).getUserData().getRoles().size() - 1).getName().equalsIgnoreCase("clinician")) {
                if (getIntent().hasExtra("type_id")) {
                    map.put("type_id", getIntent().getStringExtra("type_id"));
                }
            }

            presenterInterface.sendRequest(ClinicianTrackActivity.this, "", map, ApiCallInterface.ROADL_PROCESS_REQUEST_NEW, false);
           /* if (roadLUsers != null) {
                for (int i = 0; i < roadLUsers.size(); i++) {
                    if (StringUtils.isNotEmpty(roadLUsers.get(i).getLatitude()) && StringUtils.isNotEmpty(roadLUsers.get(i).getLongitude())) {
                        addVenderMarker(roadLUsers.get(i), i);
                    }
                }
            }*/


        }

    }

    private void disableGoogleMap() {
        if (gMap != null) {
            gMap.getUiSettings().setCompassEnabled(false);
            gMap.getUiSettings().setMapToolbarEnabled(false);
            gMap.getUiSettings().setZoomControlsEnabled(false);
            gMap.getUiSettings().setZoomGesturesEnabled(false);
            gMap.getUiSettings().setAllGesturesEnabled(false);
            gMap.getUiSettings().setRotateGesturesEnabled(false);
            gMap.getUiSettings().setScrollGesturesEnabled(false);
            gMap.getUiSettings().setTiltGesturesEnabled(false);

        }
    }

    public void zoomRoute(List<LatLng> lstLatLngRoute) {
        try {
            if (gMap == null || lstLatLngRoute == null || lstLatLngRoute.isEmpty()) return;
            LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
            for (LatLng latLngPoint : lstLatLngRoute)
                boundsBuilder.include(latLngPoint);
            int routePadding = 100;
            LatLngBounds latLngBounds = boundsBuilder.build();
            gMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, routePadding));
            //  loadLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*private void loadLocation() {
        try {
            new Handler().postDelayed(() -> {
                String lat = new MyPref(ClinicianTrackActivity.this).getData(MyPref.Keys.LAT);
                String lng = new MyPref(ClinicianTrackActivity.this).getData(MyPref.Keys.LAG);
                Location location = new Location("");
                location.setLatitude(Double.parseDouble(lat));
                location.setLongitude(Double.parseDouble(lng));
                if (lastPolyline != null) {
                    location = GoogleMapUtils.getLatLngOnPathIfNearByLocation(location, lastPolyline, 24, true);
                    addMarkerWithNavigate(location.getLatitude(), location.getLongitude());
                } else {
                    addMarkerWithNavigate(location.getLatitude(), location.getLongitude());
                }
            }, 2000);
        } catch (Exception ae) {
            ae.printStackTrace();
        }
    }*/


    private Bitmap bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return bitmap;
    }

    public Bitmap getResizedBitmap(Bitmap bm, double percentage) {
        try {
            int width = bm.getWidth();
            int height = bm.getHeight();
            int newWidth = (int) (width * percentage);
            int newHeight = (int) (height * percentage);
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            Bitmap resizedBitmap = Bitmap.createBitmap(
                    bm, 0, 0, width, height, matrix, false);
            bm.recycle();
            return resizedBitmap;
        } catch (Exception ae) {
            ae.printStackTrace();
            return bm;
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (intent.hasExtra(GeoService.EXTRA_LOCATION)) {

                        Location location = intent.getParcelableExtra(GeoService.EXTRA_LOCATION);

                        if (location != null && sendEmitUser != null) {

                            //addMarkerWithNavigate(location.getLatitude(), location.getLongitude());
                            //Log.e("Location", Double.toString(location.getLatitude()));
                            JSONObject object = new JSONObject();
                            try {
                                object.put("referral_type", myPref.getUserData().getRoles().get(myPref.getUserData().getRoles().size() - 1).getName());
                                object.put("latitude", location.getLatitude());
                                object.put("longitude", location.getLongitude());
                                object.put("first_name", myPref.getUserData().getFirst_name());
                                object.put("last_name", myPref.getUserData().getLast_name());
                                object.put("status", "accept");
                                object.put("id", sendEmitUser.getId());
                                object.put("parent_id", parent_id);
                                object.put("type_id", sendEmitUser.getType_id());
                                object.put("user_id", myPref.getUserData().getId());
                                object.put("color", myPref.getUserData().getColor());
                                object.put("icon", myPref.getUserData().getIcon());

                                new MyPref(context).setData(MyPref.Keys.ACCEPT_SOCKET, "accept");
                                new MyPref(context).setData(MyPref.Keys.ID_SOCKET, sendEmitUser.getId());
                                new MyPref(context).setData(MyPref.Keys.TYPE_ID_SOCKET, sendEmitUser.getType_id());
                                new MyPref(context).setData(MyPref.Keys.START_SOCKET_BACKGROUND, true);

                                if (!isMyServiceRunning(GeoService.class)) {

                                    Intent myService = new Intent(ClinicianTrackActivity.this, GeoService.class);
                                    startService(myService);

                                }
                                //Logger.e("SEND-->" + object.toString());
                                ((AppClass) getApplication()).setEmitData(SocketEmitType.send_location, object.toString(), new SocketIOConnectionHelper.OnSocketAckListerner() {
                                    @Override
                                    public void onSocketAck(final SocketEmitType type, Object object) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {


                                            }
                                        });
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            // presenterInterface.sendRequest(MainActivity.this, "", map, ApiCallInterface.UPDATE_LOCATION, false);

                        }
                    }
                }
            });

        }
    };

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        ((AppClass) getApplication()).removeSocketConnection();
    }

    private void updateMapPath(String lattitude, String longitude, int pos) {
        try {
            binding.progress.setVisibility(View.VISIBLE);
            new GetDirectionsResponse(this, new LatLng(Double.parseDouble(lattitude), Double.parseDouble(longitude)), new LatLng(Double.parseDouble(roadRequestModel.getData().getPatient().getLatitude()), Double.parseDouble(roadRequestModel.getData().getPatient().getLongitude())), new OnGoogleMapEventListener() {
                @Override
                public void latlngList(PolylineOptions lineOptions, final List<LatLng> latLngList) {
                    polyLineOptions = lineOptions;
                    if (lineOptions != null && latLngList != null && latLngList.size() > 0) {
                        if (roadLUsers.get(pos).getLastPolyline() == null)
                            roadLUsers.get(pos).setLastPolyline(gMap.addPolyline(lineOptions));
                        try {
                            new Handler().postDelayed(() -> roadLUsers.get(pos).getLastPolyline().setPoints(PolyUtil.simplify(latLngList, 1)), 1000);
                        } catch (Exception ae) {
                            ae.printStackTrace();
                            new Handler().postDelayed(() -> roadLUsers.get(pos).getLastPolyline().setPoints(latLngList), 1000);
                        }
                        if (StringUtils.isNotEmpty(roadLUsers.get(pos).getColor()) && roadLUsers.get(pos).getColor().startsWith("#")) {
                            roadLUsers.get(pos).getLastPolyline().setColor(Color.parseColor(roadLUsers.get(pos).getColor()));
                        }
                    }
                }

                @Override
                public void getDuration(JSONObject durations, JSONObject distance, JSONArray rootsData) throws JSONException {
                    binding.progress.setVisibility(View.GONE);
                    if (durations != null && distance != null) {

                        binding.tvNoRootAvailable.setVisibility(View.GONE);
                        binding.relHomeMap.setVisibility(View.VISIBLE);

                        try {
                            JSONArray jLegs = ((JSONObject) rootsData.get(0)).getJSONArray("legs");

                            for (int j = 0; j < jLegs.length(); j++) {

                                JSONArray stepsJsonArray=((JSONObject) jLegs.get(j)).getJSONArray("steps");

                                for (int k = 0; k <stepsJsonArray.length() ; k++) {

                                    Log.e("stepsJsonArray",stepsJsonArray.toString());
                                    //JSONArray stepsArray = ((JSONObject) stepsJsonArray.get(k)).getJSONArray("steps");
                                    JSONObject distanceSteps = ((JSONObject) stepsJsonArray.get(0)).getJSONObject("distance");
                                    String distanceText=distanceSteps.getString("text");
                                    String htmlInstructions = ((JSONObject) stepsJsonArray.get(0)).getString("html_instructions");
                                    String maneuver = ((JSONObject) stepsJsonArray.get(0)).optString("maneuver");
                                    Log.e("maneuver",maneuver);

                                    if (maneuver!=null && maneuver!=""){

                                        binding.imgDistanceKm.setVisibility(View.VISIBLE);
                                        if (maneuver.equals("ramp-left")){

                                            binding.imgDistanceKm.setImageDrawable(getResources().getDrawable(R.drawable.ramp_left));

                                        }else if (maneuver.equals("ramp-right")){

                                            binding.imgDistanceKm.setImageDrawable(getResources().getDrawable(R.drawable.ramp_right));

                                        }else if (maneuver.equals("straight")){

                                            binding.imgDistanceKm.setImageDrawable(getResources().getDrawable(R.drawable.straight));

                                        }else if (maneuver.equals("uturn-right")){

                                            binding.imgDistanceKm.setImageDrawable(getResources().getDrawable(R.drawable.uturn_right));

                                        }else if (maneuver.equals("uturn-left")){

                                            binding.imgDistanceKm.setImageDrawable(getResources().getDrawable(R.drawable.uturn_left));

                                        }else if (maneuver.equals("turn-slight-right")){

                                            binding.imgDistanceKm.setImageDrawable(getResources().getDrawable(R.drawable.turn_slight_right));

                                        }else if (maneuver.equals("turn-slight-left")){

                                            binding.imgDistanceKm.setImageDrawable(getResources().getDrawable(R.drawable.turn_slight_left));

                                        }else if (maneuver.equals("turn-sharp-right")){

                                            binding.imgDistanceKm.setImageDrawable(getResources().getDrawable(R.drawable.turn_sharp_right));

                                        }else if (maneuver.equals("turn-sharp-left")){

                                            binding.imgDistanceKm.setImageDrawable(getResources().getDrawable(R.drawable.turn_sharp_left));

                                        }else if (maneuver.equals("turn-right")){

                                            binding.imgDistanceKm.setImageDrawable(getResources().getDrawable(R.drawable.turn_right));

                                        }else if (maneuver.equals("turn-left")){

                                            binding.imgDistanceKm.setImageDrawable(getResources().getDrawable(R.drawable.turn_left));

                                        }else if (maneuver.equals("roundabout-right")){

                                            binding.imgDistanceKm.setImageDrawable(getResources().getDrawable(R.drawable.roundabout_right));

                                        }else if (maneuver.equals("roundabout-left")){

                                            binding.imgDistanceKm.setImageDrawable(getResources().getDrawable(R.drawable.roundabout_left));

                                        }else if (maneuver.equals("ramp-right")){

                                            binding.imgDistanceKm.setImageDrawable(getResources().getDrawable(R.drawable.ramp_right));

                                        }else if (maneuver.equals("merge")){

                                            binding.imgDistanceKm.setImageDrawable(getResources().getDrawable(R.drawable.merge));

                                        }else if (maneuver.equals("fork-left")){

                                            binding.imgDistanceKm.setImageDrawable(getResources().getDrawable(R.drawable.fork_left));

                                        }else if (maneuver.equals("fork-right")){

                                            binding.imgDistanceKm.setImageDrawable(getResources().getDrawable(R.drawable.fork_right));

                                        }else if (maneuver.equals("ferry-train")){

                                            binding.imgDistanceKm.setImageDrawable(getResources().getDrawable(R.drawable.ferry_train));

                                        }else if (maneuver.equals("ferry")){

                                            binding.imgDistanceKm.setImageDrawable(getResources().getDrawable(R.drawable.ferry));

                                        }else {

                                            binding.imgDistanceKm.setImageDrawable(getResources().getDrawable(R.drawable.straight));

                                        }


                                    }else {

                                      //  binding.imgDistanceKm.setVisibility(View.GONE);
                                        binding.imgDistanceKm.setImageDrawable(getResources().getDrawable(R.drawable.straight));

                                    }

                                    if (!isMute){

                                        binding.tvTextLocation.setText(Html.fromHtml(htmlInstructions));
                                        binding.tvDistanceKm.setText(distanceText);

                                        textToSpeechSystem = new TextToSpeech(ClinicianTrackActivity.this, new TextToSpeech.OnInitListener() {
                                            @Override
                                            public void onInit(int status) {
                                                if (status == TextToSpeech.SUCCESS) {
                                                    textToSpeechSystem.speak(binding.tvTextLocation.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                                                }
                                            }
                                        });

                                    }

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (TRAVEL_MODES.equals(Transit)){

                            if (rootsData!=null){

                                /*for (int i = 0; i <legsData.length() ; i++) {

                                    JSONObject legsObject=legsData.getJSONObject(i);
                                    JSONObject jsonObjectArrival=legsObject.getJSONObject("arrival_time");
                                    arrivalTime=jsonObjectArrival.getString("text");
                                    startAddress=legsObject.getString("start_address");
                                    JSONArray stepsJsonArray=legsObject.getJSONArray("steps");
                                    Log.e("arrivalTime",arrivalTime);

                                    for (int j = 0; j <stepsJsonArray.length() ; j++) {

                                        JSONObject stepsObject=stepsJsonArray.getJSONObject(j);
                                        JSONObject distanceObject=stepsObject.getJSONObject("distance");
                                        JSONObject durationObject=stepsObject.getJSONObject("duration");

                                        listSteps.add(new StepsModel(stepsObject.getString("html_instructions"),distanceObject.getString("text"),durationObject.getString("text")));
                                    }


                                }*/

                                transitModeRecommendedDialog= new TransitModeRecommendedDialog(rootsData,ClinicianTrackActivity.this, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {



                                    }
                                });
                                transitModeRecommendedDialog.show();
                            }

                        }

                        try {
                            Double km = Double.parseDouble(distance.getString("value")) / 1609;
                            // binding.tvKm.setText(distance.getString("text"));
                            binding.tvMiles.setText(String.format("%.2f Miles", km));
                            binding.tvMinute.setText(durations.getString("text"));
                            roadLUsers.get(pos).setKilometer(distance.getString("text"));
                            roadLUsers.get(pos).setDuration(durations.getString("text"));
                        /*if (lastPolyline != null) {
                            List<LatLng> latLngs = new ArrayList<>();
                            latLngs.add(new LatLng(lastDirectionLat, lastDirectionLng));
                            latLngs.add(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));
                            latLngs.addAll(lastPolyline.getPoints());
                            zoomRoute(latLngs);
                        } else {
                            List<LatLng> latLngs = new ArrayList<>();
                            latLngs.add(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));
                            //   latLngs.add(new LatLng(lastDirectionLat, lastDirectionLng));
                            zoomRoute(latLngs);
                        }*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {

                        binding.tvMiles.setText("00.00");
                        binding.tvMinute.setText("00:00");
                        /*roadLUsers.get(pos).setKilometer("0");
                        roadLUsers.get(pos).setDuration("00:00:00");*/
                        binding.tvNoRootAvailable.setVisibility(View.VISIBLE);
                        binding.relHomeMap.setVisibility(View.GONE);
                      //  Toast.makeText(getApplicationContext(), "No root available", Toast.LENGTH_SHORT).show();

                    }

                }
            });
        } catch (Exception ae) {
            ae.printStackTrace();
        }
    }

    @Override
    public void retry(int pos) {

    }

    @Override
    public void onError(String errorMsg, int requestCode, int resultCode) {
        binding.progress.setVisibility(View.GONE);
    }

    @Override
    public void onSuccess(Object success, int requestCode, int resultCode) {
        binding.progress.setVisibility(View.GONE);
        if (requestCode == ApiCallInterface.UPDATE_LOCATION) {
            BaseModel baseModel = (BaseModel) success;
            if (baseModel.isStatus().equals("true")) {

                new MyPref(ClinicianTrackActivity.this).setData(MyPref.Keys.START_SOCKET_BACKGROUND, false);
                Intent myService = new Intent(ClinicianTrackActivity.this, GeoService.class);
                stopService(myService);
                startActivity(new Intent(ClinicianTrackActivity.this, Profile.class).putExtra("parent_id", parent_id));
                finish();

            }

        }

        if (requestCode == ApiCallInterface.ROADL_PROCESS_REQUEST_NEW) {

            roadRequestModel = (RoadRequestModel) success;
            if (roadRequestModel.isStatus().equals("true")) {

                lat = roadRequestModel.getData().getPatient().getLatitude();
                lng = roadRequestModel.getData().getPatient().getLongitude();

                AddDropMarker();
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)), 16f));
                for (int i = 0; i < roadRequestModel.getData().getClinicians().size(); i++) {

                    if (StringUtils.isNotEmpty(roadRequestModel.getData().getClinicians().get(i).getLatitude()) && StringUtils.isNotEmpty(roadRequestModel.getData().getClinicians().get(i).getLongitude())) {

                        roadLUsers.add(roadRequestModel.getData().getClinicians().get(i));

                    }

                }

                if (myPref.getUserData().getRoles().get(myPref.getUserData().getRoles().size() - 1).getName().equalsIgnoreCase("clinician")) {

                    binding.llType.setVisibility(View.VISIBLE);
                    binding.bottom.setVisibility(View.VISIBLE);
                    setSpinner(binding.spType, roadLUsers);

                } else {

                    binding.llType.setVisibility(View.GONE);
                    binding.bottom.setVisibility(View.VISIBLE);

                    if (roadLUsers != null && gMap != null) {

                        for (int i = 0; i < roadLUsers.size(); i++) {

                            if (StringUtils.isNotEmpty(roadLUsers.get(i).getLatitude()) && StringUtils.isNotEmpty(roadLUsers.get(i).getLongitude())) {

                                selectedReferel = roadLUsers.get(i).getReferral_type();
                                addVenderMarker(roadLUsers.get(i), i);

                            }

                        }

                    }
                }

                LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("location_update"));
                //askPermission();
                sendEmitUser = getLoginUser(myPref.getUserData().getRoles().get(myPref.getUserData().getRoles().size() - 1).getName());

                if (sendEmitUser != null) {
                    binding.btnComplete.setVisibility(View.VISIBLE);
                } else {
                    binding.btnComplete.setVisibility(View.GONE);
                }

            }

        }

    }

    @Override
    public void onSocketResponse(String type, Object object) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Logger.e("Response" + object.toString());
                UserModel userModel = new Gson().fromJson(object.toString(), UserModel.class);
                if (userModel != null) {
                    if (getUserPos(userModel.getId()) != -1) {
                        roadLUsers.get(getUserPos(userModel.getId())).setLatitude(userModel.getLatitude());
                        roadLUsers.get(getUserPos(userModel.getId())).setLongitude(userModel.getLongitude());
                    } else {

                        roadLUsers.add(userModel);
                    }


                    if (selectedReferel.equalsIgnoreCase("all")) {
                        for (int i = 0; i < roadLUsers.size(); i++) {
                            if (StringUtils.isNotEmpty(roadLUsers.get(i).getLatitude()) && StringUtils.isNotEmpty(roadLUsers.get(i).getLongitude())) {
                                addVenderMarker(roadLUsers.get(i), i);
                            }
                        }
                    } else {
                        if (getUserPosbyReferral(selectedReferel) != -1) {
                            addVenderMarker(roadLUsers.get(getUserPosbyReferral(selectedReferel)), getUserPosbyReferral(selectedReferel));
                        }
                    }
                }
            }
        });

    }


    private void addVenderMarker(UserModel userModel, int pos) {

        if (gMap != null) {

            if (Double.parseDouble(userModel.getLatitude()) != userModel.getLastLat() || Double.parseDouble(userModel.getLongitude()) != userModel.getLastLng()) {

                if (userModel.getMarker() == null) {

                    CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(ClinicianTrackActivity.this);
                    gMap.setInfoWindowAdapter(adapter);

                    if (StringUtils.isNotEmpty(userModel.getIcon())) {

                        if (userModel.getIconBitmap() == null) {

                            try {

                                Glide.with(this).asBitmap().apply(new RequestOptions().override(85, 85)).load(userModel.getIcon()).into(new CustomTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                        try {
                                            if (resource != null) {
                                                //Bitmap myLogo = getResizedBitmap(resource, 0.85);
                                                Bitmap myLogo = resource;
                                                Marker marker = gMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(userModel.getLatitude()), Double.parseDouble(userModel.getLongitude()))).flat(true).icon(BitmapDescriptorFactory.fromBitmap(myLogo)));
                                                marker.setTitle(userModel.getFullName());
                                                marker.setSnippet(new Gson().toJson(userModel));
                                                userModel.setMarker(marker);
                                                roadLUsers.get(pos).setIconBitmap(myLogo);
                                            }
                                        } catch (Exception e) {
                                            /*Bitmap myLogo = getResizedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_car), 0.85);
                                            if (myLogo != null) {
                                                Marker marker = gMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(userModel.getLatitude()), Double.parseDouble(userModel.getLongitude()))).flat(true).icon(BitmapDescriptorFactory.fromBitmap(myLogo)));
                                                marker.setTitle(userModel.getFullName());
                                                marker.setSnippet("User");
                                                userModel.setMarker(marker);
                                            }*/
                                        }

                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {

                                    }
                                });
                            } catch (Exception e) {
                               /* Bitmap myLogo = getResizedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_car), 0.85);
                                if (myLogo != null) {
                                    Marker marker = gMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(userModel.getLatitude()), Double.parseDouble(userModel.getLongitude()))).flat(true).icon(BitmapDescriptorFactory.fromBitmap(myLogo)));
                                    marker.setTitle(userModel.getFullName());
                                    marker.setSnippet(new Gson().toJson(userModel));
                                    userModel.setMarker(marker);
                                }*/
                            }

                        } else {

                            Marker marker = gMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(userModel.getLatitude()), Double.parseDouble(userModel.getLongitude()))).flat(true).icon(BitmapDescriptorFactory.fromBitmap(userModel.getIconBitmap())));
                            marker.setTitle(userModel.getFullName());
                            marker.setSnippet(new Gson().toJson(userModel));
                            userModel.setMarker(marker);

                        }

                    } else {

                        Bitmap myLogo = getResizedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_clinician_request), 0.85);
                        if (myLogo != null) {

                            Marker marker = gMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(userModel.getLatitude()), Double.parseDouble(userModel.getLongitude()))).flat(true).icon(BitmapDescriptorFactory.fromBitmap(myLogo)));
                            marker.setTitle(userModel.getFullName());
                            marker.setSnippet(new Gson().toJson(userModel));
                            userModel.setMarker(marker);

                        }

                    }
                    // Bitmap myLogo = getResizedBitmap(bitmapDescriptorFromVector(MainActivity.this, R.drawable.ic_clinician_request), 0.95);

                } else {

                    CarMoveAnim.startcarAnimation(userModel.getMarker(), gMap, new LatLng(userModel.getLastLat(), userModel.getLastLng()), new LatLng(Double.parseDouble(userModel.getLatitude()), Double.parseDouble(userModel.getLongitude())), 2000, null);

                }
                updateMapPath(userModel.getLatitude(), userModel.getLongitude(), pos);
                roadLUsers.get(pos).setLastLat(Double.parseDouble(userModel.getLatitude()));
                roadLUsers.get(pos).setLastLng(Double.parseDouble(userModel.getLongitude()));

            }
        }
    }

    private int getUserPos(String userid) {
        for (int i = 0; i < roadLUsers.size(); i++) {
            if (StringUtils.isNotEmpty(roadLUsers.get(i).getId()) && roadLUsers.get(i).getId().equalsIgnoreCase(userid)) {
                return i;
            }
        }
        return -1;
    }

    private int getUserPosbyReferral(String userid) {
        for (int i = 0; i < roadLUsers.size(); i++) {
            if (StringUtils.isNotEmpty(roadLUsers.get(i).getReferral_type()) && roadLUsers.get(i).getReferral_type().equalsIgnoreCase(userid)) {
                return i;
            }
        }
        return -1;
    }

    private UserModel getLoginUser(String userid) {
        for (int i = 0; i < roadLUsers.size(); i++) {
            if (StringUtils.isNotEmpty(roadLUsers.get(i).getReferral_type()) && roadLUsers.get(i).getReferral_type().equalsIgnoreCase(userid)) {
                return roadLUsers.get(i);
            }
        }
        return null;
    }

    private void setSpinner(Spinner spinner, List<UserModel> list) {
        UserModel userModel = new UserModel();
        userModel.setReferral_type("All");
        list.add(0, userModel);

        ArrayAdapter<UserModel> arrayAdapter = new ArrayAdapter<UserModel>(ClinicianTrackActivity.this, R.layout.spinner_row, list) {
            @Override
            public boolean isEnabled(int position) {
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View spinnerView = super.getDropDownView(position, convertView, parent);
                TextView spinnerTextV = (TextView) spinnerView;
                spinnerTextV.setText(list.get(position).getReferral_type());
               /* if (position == 0) {
                    spinnerTextV.setTextColor(Color.parseColor("#a7a7a6"));
                } else {
                    spinnerTextV.setTextColor(Color.parseColor("#b2000000"));
                }*/
                return spinnerView;
            }
        };


        arrayAdapter.setDropDownViewResource(R.layout.spinner_drop_down);

        spinner.setAdapter(arrayAdapter);

    }

    String getDisplayTime(String travelTimeInSec, String preparationTime) throws ParseException {
        String totalMinutes = "";
        long travelTimes = Long.parseLong(travelTimeInSec);
        String minutesTravel = getSecondsToMinute(travelTimes);

        long totalMin = Long.parseLong(getSecondsToOnlyMinute(travelTimeInSec)) + Long.parseLong(preparationTime);

        if (totalMin > 60) {

            SimpleDateFormat sdf = new SimpleDateFormat("mm");
            TimeZone tz = TimeZone.getTimeZone("EST");
            sdf.setTimeZone(tz);
            Date dt = sdf.parse("" + totalMin);
            sdf = new SimpleDateFormat("HH:mm");
            sdf.setTimeZone(tz);
            totalMinutes = sdf.format(dt) + " Hours";

        } else {

            totalMinutes = totalMin + " Min";

        }
        return "Travel Time: " + minutesTravel + " + " + getMinutesToHH_MMAndMM(Long.parseLong(preparationTime)) + " = " + totalMinutes;
    }

    public String getMinutesToHH_MMAndMM(long minutes) throws ParseException {
        String properTime = "";
        if (minutes > 60) {

            SimpleDateFormat sdf = new SimpleDateFormat("mm");
            TimeZone tz = TimeZone.getTimeZone("EST");
            sdf.setTimeZone(tz);
            Date dt = sdf.parse("" + minutes);
            sdf = new SimpleDateFormat("HH:mm");
            sdf.setTimeZone(tz);
            properTime = sdf.format(dt) + " Hours";

        } else {

            properTime = minutes + " Min";

        }
        return properTime;
    }

    public String getHH_MM_SS_to_minute(String time) {
        String[] timeSplit = time.split(":");
        String hours = timeSplit[0];
        String min = timeSplit[1];
        String sec = timeSplit[2];
        long minutes = Long.parseLong(hours) * 60;
        long minutes1 = Long.parseLong(min);
        long minutes2 = TimeUnit.SECONDS
                .toMinutes(Long.parseLong(sec));
        long totalMinutes = minutes + minutes1 + minutes2;
        return "" + totalMinutes;
    }

    public String getSecondsToMinute(long seconds) throws ParseException {
        String totalMinutes = "";
        if (seconds > 3600) {

            SimpleDateFormat sdf = new SimpleDateFormat("ss");
            TimeZone tz = TimeZone.getTimeZone("EST");
            sdf.setTimeZone(tz);
            Date dt = sdf.parse("" + seconds);
            sdf = new SimpleDateFormat("HH:mm");
            sdf.setTimeZone(tz);
            totalMinutes = sdf.format(dt) + " Hours";

        } else {

            SimpleDateFormat sdf = new SimpleDateFormat("ss");
            TimeZone tz = TimeZone.getTimeZone("EST");
            sdf.setTimeZone(tz);
            Date dt = sdf.parse("" + seconds);
            sdf = new SimpleDateFormat("mm");
            sdf.setTimeZone(tz);
            totalMinutes = sdf.format(dt) + " Min";

        }
        return totalMinutes;
    }

    public String getSecondsToOnlyMinute(String seconds) throws ParseException {
        long minutes = TimeUnit.SECONDS
                .toMinutes(Long.parseLong(seconds));
        return "" + minutes;
    }

    public String getCurrentTime() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeZone tz = TimeZone.getTimeZone("EST");
        formatter.setTimeZone(tz);
        return formatter.format(new Date(System.currentTimeMillis()));
    }

    public String getDateTimeDifference(String acceptedTime) throws ParseException {
        TimeZone tz = TimeZone.getTimeZone("EST");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(tz);
        Date startDate = simpleDateFormat.parse(acceptedTime);
        Date endDate = simpleDateFormat.parse(getCurrentTime());
        //Log.e("getCurrentTime",getCurrentTime().toString());
        //Date endDate = simpleDateFormat.parse("2021-07-07 12:40:00");
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        long totalHours = (elapsedDays * 24) + elapsedHours;

        return (totalHours < 10 ? "0" + totalHours : totalHours) + ":" + (elapsedMinutes < 10 ? "0" + elapsedMinutes : elapsedMinutes) + ":" + (elapsedSeconds < 10 ? "0" + elapsedSeconds : elapsedSeconds) + "";
    }

    public String getPreparationTime(String endTime, String startTime) {
        String diffTime = "";
        try {
            java.text.DateFormat df = new java.text.SimpleDateFormat("HH:mm:ss");
            TimeZone tz = TimeZone.getTimeZone("EST");
            df.setTimeZone(tz);
            Date date1 = df.parse(startTime);
            java.util.Date date2 = df.parse(endTime);
            long diff = date2.getTime() - date1.getTime();
            long timeInSeconds = diff / 1000;
            long hours, minutes, seconds;
            hours = timeInSeconds / 3600;
            timeInSeconds = timeInSeconds - (hours * 3600);
            minutes = timeInSeconds / 60;
            timeInSeconds = timeInSeconds - (minutes * 60);
            seconds = timeInSeconds;
            diffTime = (hours < 10 ? "0" + hours : hours) + ":" + (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds) + "";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return diffTime;
    }

    public void getRoadLPrecess() {

        HashMap<String, String> map = new HashMap<>();
        map.put("parent_id", parent_id);

        if (!new MyPref(ClinicianTrackActivity.this).getUserData().getRoles().get(new MyPref(ClinicianTrackActivity.this).getUserData().getRoles().size() - 1).getName().equalsIgnoreCase("clinician")) {
            if (getIntent().hasExtra("type_id")) {
                map.put("type_id", getIntent().getStringExtra("type_id"));
            }
        }

        presenterInterface.sendRequest(ClinicianTrackActivity.this, "", map, ApiCallInterface.ROADL_PROCESS_REQUEST_NEW, false);

    }

}