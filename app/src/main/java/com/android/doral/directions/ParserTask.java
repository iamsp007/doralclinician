package com.android.doral.directions;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
    private OnGoogleMapEventListener onGoogleMapEventListener;
    private JSONObject timeObj, distObj;
    JSONArray getRootsData;
    private int shortPath = 0;

    public ParserTask(OnGoogleMapEventListener onGoogleMapEventListener) {
        this.onGoogleMapEventListener = onGoogleMapEventListener;
    }

    @Override
    protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
        JSONObject jObject;
        List<List<HashMap<String, String>>> routes = null;
        try {
            jObject = new JSONObject(jsonData[0]);
            DirectionsJSONParser parser = new DirectionsJSONParser();
            routes = parser.parse(jObject);
            timeObj = parser.getTimeObj();
            distObj = parser.getDistObj();
            getRootsData = parser.getRoots();
            shortPath = parser.getShortPath();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return routes;
    }

    @Override
    protected void onPostExecute(List<List<HashMap<String, String>>> result) {
        ArrayList points = null;
        PolylineOptions lineOptions = null;
        MarkerOptions markerOptions = new MarkerOptions();

        if (result != null && result.size() > 0) {
            for (int i = 0; i < result.size(); i++) {
                if (shortPath == i) {
                    points = new ArrayList();
                    lineOptions = new PolylineOptions();
                    List<HashMap<String, String>> path = result.get(i);
                    for (int j = 0; j < path.size(); j++) {
                        HashMap<String, String> point = path.get(j);
                        double lat = Double.parseDouble(point.get("lat"));
                        double lng = Double.parseDouble(point.get("lng"));
                        LatLng position = new LatLng(lat, lng);
                        points.add(position);
                    }
                    if (points != null)
                        lineOptions.addAll(points);

                    lineOptions.width(14);
                    lineOptions.color(Color.parseColor("#00666f"));
                    lineOptions.geodesic(true);

                    break;
                }
            }
        } else {
           /* points = new ArrayList();
            lineOptions = new PolylineOptions();*/
        }
//        if (lineOptions != null && points != null && points.size() > 0) {
//            if (lastLatLng == null) {
//                lastPolyLine = mMap.addPolyline(lineOptions);
//            }
//            lastLatLng = lineOptions.getPoints();
//            lastPolyLine.setPoints(points);
//        } else {
//            if (lastLatLng == null)
//                Log.e("TAG", "No Route Found");
//        }
        if (onGoogleMapEventListener != null && lineOptions != null && points != null) {
            onGoogleMapEventListener.latlngList(lineOptions, points);
        }
        if (onGoogleMapEventListener != null) {
            if (timeObj!=null && distObj!=null){
                try {
                    onGoogleMapEventListener.getDuration(timeObj, distObj,getRootsData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {

                try {
                    onGoogleMapEventListener.getDuration(null, null,null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }


    }
}