package com.android.doral.directions;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public interface OnGoogleMapEventListener {
    void latlngList(PolylineOptions lineOptions, List<LatLng> latLngList);

    void getDuration(JSONObject durations, JSONObject distance, JSONArray stepsData) throws JSONException;

    //void getDuration(JSONObject durations, JSONObject distance);


}
