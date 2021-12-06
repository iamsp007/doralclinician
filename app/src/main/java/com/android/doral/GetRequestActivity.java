package com.android.doral;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.android.doral.Utils.BottomSheetAskPermission;
import com.android.doral.Utils.DateUtils;
import com.android.doral.Utils.GpsUtils;
import com.android.doral.Utils.MyPref;
import com.android.doral.Utils.OnItemClickListener;
import com.android.doral.Utils.SingleShotLocationProvider;
import com.android.doral.adapter.RequestAdapter;
import com.android.doral.base.BaseActivity;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivitySendRequestBinding;
import com.android.doral.dialog.CancelRequestDialog;
import com.android.doral.dialog.CustomAlertDialog;
import com.android.doral.dialog.PreparationDialog;
import com.android.doral.dialog.TimerDialogDialog1;
import com.android.doral.register.Presenter;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.AcceptRequest;
import com.android.doral.retrofit.model.BaseModel;
import com.android.doral.retrofit.model.RequestModel;
import com.android.doral.service.GeoService;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class GetRequestActivity extends BaseActivity implements BaseViewInterface, View.OnClickListener {
    ActivitySendRequestBinding binding;
    BasePresenterInterface presenterInterface;
    String id, lat, lng, name, reason, email;
    double cur_lat, cur_lng;
    RequestAdapter requestAdapter;
    RequestModel requestModel;
    private boolean istrack;
    PreparationDialog preparationDialog;
    CancelRequestDialog cancelRequestDialog;
    String type = "2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySendRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setStatusBarColor(R.color.colorPrimary);
        presenterInterface = new Presenter(this);
        cur_lat = Double.parseDouble(new MyPref(GetRequestActivity.this).getData(MyPref.Keys.LAT));
        cur_lng = Double.parseDouble(new MyPref(GetRequestActivity.this).getData(MyPref.Keys.LAG));

        setClick();
        binding.rvData.setLayoutManager(new LinearLayoutManager(this));
        requestAdapter = new RequestAdapter(this);
        binding.rvData.setAdapter(requestAdapter);
        requestAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, int which, Object object) {
                requestModel = (RequestModel) object;
                id = requestModel.getId();
                lat = requestModel.getLatitude();
                lng = requestModel.getLongitude();
                //   name = requestModel.getDetail().getFullName();
                if (requestModel.getDetail() == null) {
                    name = "";
                } else {
                    name = requestModel.getDetail().getFullName();
                }
                switch (which) {
                    case 1:
                        long totalSeconds=0;
                        if (requestModel.getTravel_time()!=null) {

                            if (requestModel.getTravel_time().toLowerCase().contains("hour")&&requestModel.getTravel_time().toLowerCase().contains("min")){

                                String[] minuteTravelSplit=requestModel.getTravel_time().toLowerCase().split(" ");
                                int hoursTravel=Integer.parseInt(minuteTravelSplit[0].toString());
                                int minutesTravel=Integer.parseInt(minuteTravelSplit[2].toString());
                                Log.e("hourTravel",""+hoursTravel);
                                Log.e("minutesTravel",""+minutesTravel);
                                long secondsHours = TimeUnit.HOURS.toSeconds(hoursTravel);
                                long secondsMinute = TimeUnit.MINUTES.toSeconds(minutesTravel);
                                totalSeconds=secondsHours + secondsMinute;

                            }else if (requestModel.getTravel_time().toLowerCase().contains("min")){

                                String[] minuteTravelSplit=requestModel.getTravel_time().toLowerCase().split(" ");
                                int minuteTravel=Integer.parseInt(minuteTravelSplit[0].toString());
                                long secondsMinute = TimeUnit.MINUTES.toSeconds(minuteTravel);
                                Log.e("hourTravel",""+minuteTravel);
                                totalSeconds=secondsMinute;
                            }

                        }else {

                            totalSeconds=0;

                        }

                        Log.e("hourTravel",""+totalSeconds);

                        istrack = false;
                        AcceptRequest acceptRequest = new AcceptRequest();
                        acceptRequest.setRequest_id(id);
                        acceptRequest.setLatitude("" + cur_lat);
                        acceptRequest.setLongitude("" + cur_lng);
                        acceptRequest.setType_id("" + requestModel.getType_id());
                        acceptRequest.setParent_id("" + requestModel.getParent_id());
                        acceptRequest.setDistance("" + requestModel.getDistance());
                        //acceptRequest.setTravel_time("" + requestModel.getTravel_time());
                        acceptRequest.setTravel_time("" + totalSeconds);
                        presenterInterface.sendRequest(GetRequestActivity.this, new Gson().toJson(acceptRequest), null, ApiCallInterface.ACCPET_REQUEST);

                        break;

                    case 2:

                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.DATE_FORMATE_4);
                        try {
                            calendar.setTime(simpleDateFormat.parse(requestModel.getPreparasion_date()));

                            if (System.currentTimeMillis() > calendar.getTimeInMillis()) {

                                istrack = true;
                                // askPermission();
                                Intent intent = new Intent(GetRequestActivity.this, RoadLStatusActivity.class);
                                intent.putExtra("parent_id", requestModel.getParent_id());
                                intent.putExtra("type_id", requestModel.getType_id());
                                startActivity(intent);

                            } else {

                                long diff = calendar.getTimeInMillis() - System.currentTimeMillis();
                                long diffMinutes = TimeUnit.MILLISECONDS.toSeconds(diff);

                                int minute = (int) diffMinutes;
                                new TimerDialogDialog1(GetRequestActivity.this, minute, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        istrack = true;
                                        // askPermission();
                                        Intent intent = new Intent(GetRequestActivity.this, RoadLStatusActivity.class);
                                        intent.putExtra("parent_id", requestModel.getParent_id());
                                        intent.putExtra("type_id", requestModel.getType_id());
                                        startActivity(intent);
                                    }
                                }).show();
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        break;
                    case 3:
                        if (preparationDialog != null) {
                            preparationDialog.dismissAllowingStateLoss();
                        }
                        preparationDialog = new PreparationDialog(GetRequestActivity.this, requestModel.getId(), presenterInterface);
                        preparationDialog.show(getSupportFragmentManager(), "");
                        break;

                    case 5:
                        startActivity(new Intent(GetRequestActivity.this, Profile.class).putExtra("parent_id", requestModel.getParent_id()));
                        break;

                    case 6:
                        istrack = true;
                        // askPermission();
                        Intent intent = new Intent(GetRequestActivity.this, RoadLStatusActivity.class);
                        intent.putExtra("parent_id", requestModel.getParent_id());
                        intent.putExtra("type_id", requestModel.getType_id());
                        startActivity(intent);
                        break;
                }
            }
        });

       /* binding.tvType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.spType.performClick();
            }
        });*/

        /*binding.spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                binding.tvType.setText(binding.spType.getSelectedItem().toString());
                getrequestList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        getrequestList();

    }

    public void cancelRequestDialog(RequestModel requestModel) {

        if (cancelRequestDialog != null) {
            cancelRequestDialog.dismissAllowingStateLoss();
        }
        cancelRequestDialog = new CancelRequestDialog(GetRequestActivity.this, requestModel.getId(), presenterInterface,requestModel);
        cancelRequestDialog.show(getSupportFragmentManager(), "");

    }

    public void cancelRequest(RequestModel request,String reason){

        HashMap<String, String> map = new HashMap<>();
        map.put("patient_requests_id", request.getId());
        map.put("client_id", request.getPatient_detail().getId());
        map.put("latitude", "" + request.getLatitude());
        map.put("longitude", "" + request.getLongitude());
        map.put("status", "5");
        map.put("notes", reason);
        presenterInterface.sendRequest(context, "", map, ApiCallInterface.UPDATE_LOCATION, true);

    }

    @Override
    public void retry(int pos) {

    }

    @Override
    public void onError(String errorMsg, int requestCode, int resultCode) {

        new CustomAlertDialog(context, errorMsg, "Alert", "OK", "", new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        }).show();
    }

    @Override
    public void onSuccess(Object success, int requestCode, int resultCode) {

        if (ApiCallInterface.ACCPET_REQUEST == requestCode) {
            BaseModel baseModel = (BaseModel) success;

            if (baseModel.isStatus().equals("true")) {

                if (new MyPref(context).getUserData().getRoles().get(new MyPref(context).getUserData().getRoles().size() - 1).getName().equalsIgnoreCase("clinician")) {

                    Intent intent = new Intent(GetRequestActivity.this, RoadLStatusActivity.class);
                    intent.putExtra("parent_id", requestModel.getParent_id());
                    intent.putExtra("type_id", requestModel.getType_id());
                    startActivity(intent);

                } else {

                    if (preparationDialog != null) {
                        preparationDialog.dismissAllowingStateLoss();
                    }
                    preparationDialog = new PreparationDialog(GetRequestActivity.this, requestModel.getId(), presenterInterface);
                    preparationDialog.show(getSupportFragmentManager(), "");

                }

            } else {

                new CustomAlertDialog(context, baseModel.getMessage(), "Alert", "OK", "", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                    }
                }).show();

            }
        }

        if (ApiCallInterface.UPDATE_LOCATION==requestCode){

            BaseModel baseModel = (BaseModel) success;

            if (baseModel.isStatus().equals("true")) {

                if (cancelRequestDialog != null) {
                    cancelRequestDialog.dismissAllowingStateLoss();
                }
                getrequestList();

            }

        }

        if (ApiCallInterface.UPDATE_TIME == requestCode) {

            BaseModel baseModel = (BaseModel) success;
            if (baseModel.isStatus().equals("true")) {

                if (preparationDialog != null) {
                    preparationDialog.dismissAllowingStateLoss();
                }
                startActivity(new Intent(GetRequestActivity.this, GetRequestActivity.class));
                getrequestList();

            }
        }
        if (ApiCallInterface.REQUEST_LIST == requestCode) {

            RequestModel baseModel = (RequestModel) success;
            if (baseModel.isStatus().equals("true")) {

                if (baseModel.getData() != null && baseModel.getData().size() > 0) {

                    binding.rvData.setVisibility(View.VISIBLE);
                    binding.tvNoData.setVisibility(View.GONE);
                    requestAdapter.setData(baseModel.getData());

                } else {

                    if (type.equals("2")) {

                        type = "3";
                        getrequestList();

                    } else if (type.equals("3")) {

                        type = "1";
                        getrequestList();

                    } else {

                        binding.rvData.setVisibility(View.GONE);
                        binding.tvNoData.setVisibility(View.VISIBLE);

                    }

                }

            } else {

                if (type.equals("2")) {

                    type = "3";
                    getrequestList();

                } else if (type.equals("3")) {

                    type = "1";
                    getrequestList();

                } else {

                    binding.rvData.setVisibility(View.GONE);
                    binding.tvNoData.setVisibility(View.VISIBLE);

                }

            }

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getrequestList();
    }


    private void setClick() {

        binding.imgMenu.setOnClickListener(this::onClick);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_menu) {
            onBackPressed();
        }
    }

    private void getrequestList() {
        // String type = "";
      /*  if (binding.spType.getSelectedItem().toString().equalsIgnoreCase("active")) {
            type = "1";
        } else if (binding.spType.getSelectedItem().toString().equalsIgnoreCase("accept")) {
            type = "2";
        } else if (binding.spType.getSelectedItem().toString().equalsIgnoreCase("arrive")) {
            type = "3";
        } else if (binding.spType.getSelectedItem().toString().equalsIgnoreCase("complete")) {
            type = "4";
        } else if (binding.spType.getSelectedItem().toString().equalsIgnoreCase("cancel")) {
            type = "5";
        }*/
        //type="2";
        HashMap<String, String> map = new HashMap<>();
        map.put("type", type);
        presenterInterface.sendRequest(GetRequestActivity.this, "", map, ApiCallInterface.REQUEST_LIST);
    }
}