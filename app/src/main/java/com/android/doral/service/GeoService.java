package com.android.doral.service;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import com.android.doral.ClinicianTrackActivity;
import com.android.doral.R;
import com.android.doral.Utils.AppClass;
import com.android.doral.Utils.Logger;
import com.android.doral.Utils.MyPref;
import com.android.doral.socket.SocketEmitType;
import com.android.doral.socket.SocketIOConnectionHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;

public class GeoService extends Service {

    protected final String TAG = "location-updates-sample";
    public final long UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
    public final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    public final long MIN_DISTANCE = 10;
    protected LocationRequest mLocationRequest;
    public Location mCurrentLocation;
    public Location lastLocation;
    public long lastTime;
    public static final String EXTRA_LOCATION = "new_location";
    public static final String ACTION_LOCATION = "location_update";
    protected Boolean mRequestingLocationUpdates;
    protected String mLastUpdateTime;
    private FusedLocationProviderClient mFusedLocationClient;
    private int NOTIFICATON_ID = 1001;
    private MyPref myPref;

    @Override
    public void onCreate() {
        onStartService();
    }

    private void onStartService() {
        mRequestingLocationUpdates = true;
        mLastUpdateTime = "";
        myPref = new MyPref(this);
        createLocationRequest();
        if (Build.VERSION.SDK_INT >= 26) {

            String NOTIFICATION_CHANNEL_ID = getString(R.string.app_name);
            String channelName = "RoadL";

            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(channelName);
            notificationChannel.setSound(null, null);

            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
            mBuilder.setSmallIcon(R.drawable.ic_launcher_background);
            mBuilder.setColor(getResources().getColor(R.color.colorPrimary));
            mBuilder.setContentTitle("Location Alert");
            mBuilder.setContentText("RoadL using your location");
            mBuilder.setPriority(NotificationManager.IMPORTANCE_MIN);
            mBuilder.setCategory(Notification.CATEGORY_SERVICE);
            startForeground(NOTIFICATON_ID, mBuilder.build());

        }
    }

    private final IBinder mBinder = new LocalBinder();

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //  mLocationRequest.setSmallestDisplacement(DISPLACEMENT_TIME);
        onConnected();
    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (mFusedLocationClient != null)
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, locationCallback, null);
    }

    private MyPref getMyPref() {
        if (myPref == null) {
            myPref = new MyPref(this);
        }
        return myPref;
    }
    @Override
    public void onDestroy() {
        stopLocationUpdates();
    }


    protected void stopLocationUpdates() {
        if (mFusedLocationClient != null)
            mFusedLocationClient.removeLocationUpdates(locationCallback);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.cancel(NOTIFICATON_ID);
        mCurrentLocation = null;
        mRequestingLocationUpdates = false;
        if (Build.VERSION.SDK_INT >= 26) {
            stopForeground(Service.STOP_FOREGROUND_REMOVE);
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        //stopLocationUpdates();
        //stopSelf();
    }

    public class LocalBinder extends Binder {
        GeoService getService() {
            return GeoService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }


    public void onConnected() {
        if (mCurrentLocation == null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    mCurrentLocation = location;
                }
            });
            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        }

        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) {
                return;
            }
            for (Location location : locationResult.getLocations()) {
                if (location != null) {

                    mCurrentLocation = location;
                    mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                    lastTime = System.currentTimeMillis();
                    Log.d(TAG, "" + mCurrentLocation.getLatitude());
                    getMyPref().setData(MyPref.Keys.LAT, mCurrentLocation.getLatitude() + "");
                    getMyPref().setData(MyPref.Keys.LAG, mCurrentLocation.getLongitude() + "");

                    if (lastLocation == null || lastLocation.distanceTo(location) > MIN_DISTANCE) {

                        LatLng original;
                        original = new LatLng(location.getLatitude(), location.getLongitude());
                        Location finalLocation = new Location("");
                        finalLocation.setLatitude(original.latitude);
                        finalLocation.setLongitude(original.longitude);
                        Intent intent = new Intent(ACTION_LOCATION);
                        intent.putExtra(EXTRA_LOCATION, finalLocation);
                        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                        lastLocation = location;

                        if (new MyPref(getApplicationContext()).getData(MyPref.Keys.START_SOCKET_BACKGROUND,false)){

                            ((AppClass) getApplication()).createSocketConnection();
                            ((AppClass) getApplication()).setAppListerner(new MyPref(getApplicationContext()).getData(MyPref.Keys.PARENT_ID));

                            JSONObject object = new JSONObject();
                            try {
                                object.put("referral_type", myPref.getUserData().getRoles().get(myPref.getUserData().getRoles().size() - 1).getName());
                                object.put("latitude", location.getLatitude());
                                object.put("longitude", location.getLongitude());
                                object.put("first_name", myPref.getUserData().getFirst_name());
                                object.put("last_name", myPref.getUserData().getLast_name());
                                object.put("status", "accept");
                                object.put("id", new MyPref(getApplicationContext()).getData(MyPref.Keys.ID_SOCKET));
                                object.put("parent_id", new MyPref(getApplicationContext()).getData(MyPref.Keys.PARENT_ID));
                                object.put("type_id", new MyPref(getApplicationContext()).getData(MyPref.Keys.TYPE_ID_SOCKET));
                                object.put("user_id", myPref.getUserData().getId());
                                object.put("color", myPref.getUserData().getColor());
                                object.put("icon", myPref.getUserData().getIcon());
                                Logger.e("SEND From GEO-->" + object.toString());
                                ((AppClass) getApplication()).setEmitData(SocketEmitType.send_location, object.toString(), new SocketIOConnectionHelper.OnSocketAckListerner() {
                                    @Override
                                    public void onSocketAck(final SocketEmitType type, Object object) {

                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }


                    }


                }
            }
        }
    };


}