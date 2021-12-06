package com.android.doral;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.doral.Utils.AppClass;
import com.android.doral.Utils.Logger;
import com.android.doral.adapter.CustomInfoWindowAdapter;
import com.android.doral.base.BaseViewInterface;
import com.android.doral.databinding.ActivityMainBinding;
import com.android.doral.directions.CarMoveAnim;
import com.android.doral.directions.GetDirectionsResponse;
import com.android.doral.directions.GoogleMapUtils;
import com.android.doral.directions.OnGoogleMapEventListener;
import com.android.doral.directions.PolyUtil;
import com.android.doral.register.BasePresenterInterface;
import com.android.doral.register.Presenter;
import com.android.doral.retrofit.ApiCallInterface;
import com.android.doral.retrofit.model.RequestModel;
import com.android.doral.retrofit.model.RoadLUser;
import com.android.doral.retrofit.model.RoadRequestModel;
import com.android.doral.retrofit.model.UserModel;
import com.android.doral.service.GeoService;
import com.android.doral.Utils.BottomSheetAskPermission;
import com.android.doral.Utils.GpsUtils;
import com.android.doral.Utils.MyPref;
import com.android.doral.Utils.SingleShotLocationProvider;
import com.android.doral.socket.SocketEmitType;
import com.android.doral.socket.SocketEmitterType;
import com.android.doral.socket.SocketIOConnectionHelper;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.android.doral.Utils.Utility.Driving;
import static com.android.doral.Utils.Utility.TRAVEL_MODES;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, BaseViewInterface, SocketIOConnectionHelper.OnSocketResponseListerner {
    private GoogleMap gMap;
    private Marker marker;
    Location lastLocation;
    private Polyline lastPolyline;
    private ArrayList<Marker> dropmarkerPlaces;
    private double lastLng, lastLat, lastDirectionLng, lastDirectionLat;
    AppCompatTextView tv_time, tv_km;
    String total_km = "0 km";
    String lat = "", lng = "";
    RequestModel requestModel;
    BasePresenterInterface presenterInterface;
    Button btnComplete;
    String newLastLat = "", newLastLong = "";
    ActivityMainBinding binding;
    private final List<UserModel> roadLUsers = new ArrayList<>();
    RoadRequestModel roadRequestModel;
    MyPref myPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        myPref = new MyPref(MainActivity.this);
        init();

    }

    private void init() {

        dropmarkerPlaces = new ArrayList<>();
        tv_time = findViewById(R.id.tv_time);
        tv_km = findViewById(R.id.tv_km);
        btnComplete = findViewById(R.id.btnComplete);
        presenterInterface = new Presenter(this);

        if (getIntent().hasExtra("data")) {

            requestModel = (RequestModel) getIntent().getSerializableExtra("data");
            lat = requestModel.getLatitude();
            lng = requestModel.getLongitude();
            HashMap<String, String> map = new HashMap<>();
            map.put("parent_id", requestModel.getParent_id());
            map.put("type_id", requestModel.getType_id());
            presenterInterface.sendRequest(MainActivity.this, "", map, ApiCallInterface.ROADL_PROCESS_REQUEST_NEW, false);

        }


        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, String> map = new HashMap<>();
                map.put("patient_requests_id", requestModel.getId());
                map.put("client_id", requestModel.getDetail().getId());
                map.put("latitude", "" + new MyPref(MainActivity.this).getData(MyPref.Keys.LAT));
                map.put("longitude", "" + new MyPref(MainActivity.this).getData(MyPref.Keys.LAG));
                map.put("status", "complete");
                presenterInterface.sendRequest(MainActivity.this, "", map, ApiCallInterface.UPDATE_LOCATION, false);

                Toast.makeText(getApplicationContext(), "complete", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        ((AppClass) getApplication()).createSocketConnection();
        ((AppClass) getApplication()).setAppListerner(requestModel.getParent_id());
        ((AppClass) getApplication()).setOnSocketResponseListerner(this);
    }

    private void animateCar(GoogleMap googleMap, LatLng start, LatLng end) {

        if (marker == null) {
            marker = googleMap.addMarker(getMarker(start, R.drawable.clinician));
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
                        startForegroundService(new Intent(MainActivity.this, GeoService.class));
                    } else {
                        startService(new Intent(MainActivity.this, GeoService.class));
                    }
                    SingleShotLocationProvider.requestSingleUpdate(MainActivity.this, new SingleShotLocationProvider.LocationCallback() {
                        @Override
                        public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                            lastDirectionLat = location.latitude;
                            lastDirectionLng = location.longitude;

                            // getKm("" + location.latitude, "" + location.longitude);
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    addMarkerWithNavigate(location.latitude, location.longitude);
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
            CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(MainActivity.this);
            gMap.setInfoWindowAdapter(adapter);
            Marker dropLocation = gMap.addMarker(getMarker(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)), R.drawable.ic_drop));
            dropLocation.setTitle(getIntent().hasExtra("name") ? getIntent().getStringExtra("name") : "Patient");
            dropLocation.setZIndex(10);
            dropLocation.setSnippet(new Gson().toJson(requestModel));
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
                        startForegroundService(new Intent(MainActivity.this, GeoService.class));
                    } else {
                        startService(new Intent(MainActivity.this, GeoService.class));
                    }
                    SingleShotLocationProvider.requestSingleUpdate(MainActivity.this, new SingleShotLocationProvider.LocationCallback() {
                        @Override
                        public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {

                            lastDirectionLat = location.latitude;
                            lastDirectionLng = location.longitude;

                            // getKm("" + location.latitude, "" + location.longitude);


                            addMarkerWithNavigate(location.latitude, location.longitude);


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
            AddDropMarker();

            lastDirectionLat = Double.parseDouble(new MyPref(MainActivity.this).getData(MyPref.Keys.LAT));
            lastDirectionLng = Double.parseDouble(new MyPref(MainActivity.this).getData(MyPref.Keys.LAG));
            // gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastDirectionLat, lastDirectionLng), 16));
            //disableGoogleMap();

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
            loadLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadLocation() {
        try {
            new Handler().postDelayed(() -> {
                String lat = new MyPref(MainActivity.this).getData(MyPref.Keys.LAT);
                String lng = new MyPref(MainActivity.this).getData(MyPref.Keys.LAG);
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
    }

    private void addMarkerWithNavigate(double lat, double lng) {
        if (gMap != null) {
            if (lat != lastLat || lng != lastLng) {
                if (marker == null) {
                    //Bitmap myLogo = getResizedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_car), 0.85);
                    Bitmap myLogo = getResizedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_car), 0.85);
                    // Bitmap myLogo = getResizedBitmap(bitmapDescriptorFromVector(MainActivity.this, R.drawable.ic_clinician_request), 0.95);
                    marker = gMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).flat(true).icon(BitmapDescriptorFactory.fromBitmap(myLogo)));
                    marker.setTitle("Doctor");
                } else {
                    CarMoveAnim.startcarAnimation(marker, gMap, new LatLng(lastLat, lastLng), new LatLng(lat, lng), 2000, null);
                }
                if (lastLat != lat && lastLng != lng) {
                    Location start = new Location("start");
                    start.setLatitude(lat);
                    start.setLongitude(lng);
                    Location end = new Location("end");
                    end.setLatitude(lastDirectionLat);
                    end.setLongitude(lastDirectionLat);
                    if (start.distanceTo(end) > 20) {
                        updateMapPath(lat + "", lng + "");
                    }

                    if (start.distanceTo(end) < 50) {
                        binding.btnComplete.setVisibility(View.VISIBLE);
                    } else {
                        binding.btnComplete.setVisibility(View.GONE);
                    }
                }
                lastLat = lat;
                lastLng = lng;
            }
        }
    }

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


                        newLastLat = "" + location.getLatitude();
                        newLastLong = "" + location.getLongitude();

                        if (location != null) {

                            addMarkerWithNavigate(location.getLatitude(), location.getLongitude());
                            Log.e("Location", Double.toString(location.getLatitude()));
                            HashMap<String, String> map = new HashMap<>();
                            map.put("patient_requests_id", requestModel.getId());
                            map.put("client_id", requestModel.getDetail().getId());
                            map.put("latitude", "" + location.getLatitude());
                            map.put("longitude", "" + location.getLongitude());
                            map.put("status", "running");

                            JSONObject object = new JSONObject();


                            try {

                                object.put("referral_type", myPref.getUserData().getRoles().get(myPref.getUserData().getRoles().size() - 1).getName());
                                object.put("latitude", location.getLatitude());
                                object.put("longitude", location.getLongitude());
                                object.put("first_name", myPref.getUserData().getFirst_name());
                                object.put("last_name", myPref.getUserData().getLast_name());
                                object.put("status", "accept");
                                object.put("id", requestModel.getId());
                                object.put("symptoms", requestModel.getSymptoms());
                                object.put("test_name", requestModel.getTest_name());
                                object.put("reason", requestModel.getReason());
                                object.put("parent_id", requestModel.getParent_id());
                                object.put("type_id", requestModel.getType_id());
                                object.put("user_id", myPref.getUserData().getId());
                                object.put("color", myPref.getUserData().getColor());
                                object.put("icon", myPref.getUserData().getIcon());
                                Logger.e("SEND-->" + object.toString());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        ((AppClass) getApplication()).removeSocketConnection();
    }

    private void updateMapPath(String latitude, String longitude) {
        lastDirectionLat = Double.parseDouble(latitude);
        lastDirectionLng = Double.parseDouble(longitude);

        try {
            TRAVEL_MODES=Driving;
            new GetDirectionsResponse(this, new LatLng(Double.parseDouble(latitude),
                    Double.parseDouble(longitude)), new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)), new OnGoogleMapEventListener() {
                @Override
                public void latlngList(PolylineOptions lineOptions, final List<LatLng> latLngList) {
                    if (lineOptions != null && latLngList != null && latLngList.size() > 0) {
                        if (lastPolyline == null)
                            lastPolyline = gMap.addPolyline(lineOptions);
                        try {
                            new Handler().postDelayed(() -> lastPolyline.setPoints(PolyUtil.simplify(latLngList, 1)), 1000);
                        } catch (Exception ae) {
                            ae.printStackTrace();
                            new Handler().postDelayed(() -> lastPolyline.setPoints(latLngList), 1000);
                        }

                    }
                }

                @Override
                public void getDuration(JSONObject durations, JSONObject distance, JSONArray stepsData) {


                    try {


                        tv_km.setText(distance.getString("text"));
                        tv_time.setText(durations.getString("text"));


                        if (lastPolyline != null) {
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
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception ae) {
            ae.printStackTrace();
        }
    }


    private void getKm(String latitude, String longitude) {

        lastDirectionLat = Double.parseDouble(latitude);
        lastDirectionLng = Double.parseDouble(longitude);
        lastLat = Double.parseDouble(latitude);
        lastLng = Double.parseDouble(longitude);
        try {
            TRAVEL_MODES=Driving;
            new GetDirectionsResponse(this, new LatLng(Double.parseDouble(latitude),
                    Double.parseDouble(longitude)), new LatLng(Double.parseDouble("23.2156"), Double.parseDouble("72.6369")), new OnGoogleMapEventListener() {
                @Override
                public void latlngList(PolylineOptions lineOptions, final List<LatLng> latLngList) {
                    if (lineOptions != null && latLngList != null && latLngList.size() > 0) {
                        if (lastPolyline == null)
                            lastPolyline = gMap.addPolyline(lineOptions);
                        try {
                            new Handler().postDelayed(() -> lastPolyline.setPoints(PolyUtil.simplify(latLngList, 1)), 1000);
                        } catch (Exception ae) {
                            ae.printStackTrace();
                            new Handler().postDelayed(() -> lastPolyline.setPoints(latLngList), 1000);
                        }

                    }

                }

                @Override
                public void getDuration(JSONObject durations, JSONObject distance, JSONArray stepsData) {


                    try {
                        //mBinding.tvPicktime.setText(distance.getString("text") + " - " + durations.getString("text"));
                        total_km = distance.getString("text");
                        tv_km.setText(total_km + "/" + total_km);
                        tv_time.setText(durations.getString("text"));

                    } catch (JSONException e) {
                        e.printStackTrace();
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

    }

    @Override
    public void onSuccess(Object success, int requestCode, int resultCode) {


        if (requestCode == ApiCallInterface.ROADL_PROCESS_REQUEST_NEW) {
            roadRequestModel = (RoadRequestModel) success;
            if (roadRequestModel.isStatus().equals("true")) {
                roadLUsers.addAll(roadRequestModel.getData().getClinicians());
                lat = requestModel.getLatitude();
                lng = requestModel.getLongitude();
                setUpGoogleMap();
                LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("location_update"));
                askPermission();
            }
        }

    }

    @Override
    public void onSocketResponse(String type, Object object) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Logger.e("Response" + object.toString());
            }
        });

    }

}