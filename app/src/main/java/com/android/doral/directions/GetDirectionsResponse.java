package com.android.doral.directions;

import android.content.Context;


import com.android.doral.R;
import com.android.doral.Utils.Logger;
import com.google.android.gms.maps.model.LatLng;

import static com.android.doral.Utils.Utility.TRANSIT_MODES;
import static com.android.doral.Utils.Utility.TRAVEL_MODES;
import static com.android.doral.Utils.Utility.Transit;

public class GetDirectionsResponse {

    public GetDirectionsResponse(Context context, LatLng origin, LatLng dest, OnGoogleMapEventListener onGoogleMapEventListener) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
      /*  String str_origin = "origin="+"23.0459295,72.6697394";
        String str_dest = "destination=" + "22.9960424,72.4996271";*/
        /* String str_origin = "origin="+"40.670402,-73.9090724";
        String str_dest = "destination=" + "40.7680866,-73.5291616";*/
        String sensor = "sensor=false";
        String mode = "mode="+TRAVEL_MODES;
       // String transitMode = "transit_mode="+TRANSIT_MODES;
        //String mode = "mode=driving";
        String alternatives = "alternatives=true";
        String key = "key=" + context.getString(R.string.map_key);

        if (TRAVEL_MODES.equals(Transit)){

            String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode+"&" + key + "&" + alternatives;
            String output = "json";
            String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

            Logger.e("url-->" + url);
            DownloadTask downloadTask = new DownloadTask(onGoogleMapEventListener);
            downloadTask.execute(url);

        }else {

            String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode + "&" + key + "&" + alternatives;
            String output = "json";
            String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

            Logger.e("url-->" + url);
            DownloadTask downloadTask = new DownloadTask(onGoogleMapEventListener);
            downloadTask.execute(url);

        }

    }
}
