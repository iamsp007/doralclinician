package com.android.doral.directions;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DownloadTask extends AsyncTask<String, Void, String> implements OnGoogleMapEventListener {
    private OnGoogleMapEventListener onGoogleMapEventListener;

    public DownloadTask(OnGoogleMapEventListener onGoogleMapEventListener) {
        this.onGoogleMapEventListener = onGoogleMapEventListener;
    }

    @Override
    protected String doInBackground(String... url) {
        String data = "";
        try {
            data = downloadUrl(url[0]);
        } catch (Exception e) {
            Log.d("Background Task", e.toString());
        }
        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        ParserTask parserTask = new ParserTask(this);
        parserTask.execute(result);
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    @Override
    public void latlngList(PolylineOptions lineOptions, List<LatLng> latLngList) {
        if (onGoogleMapEventListener != null) {
            onGoogleMapEventListener.latlngList(lineOptions, latLngList);
        }
    }

    @Override
    public void getDuration(JSONObject durations, JSONObject distance, JSONArray stepsData) throws JSONException {
        if (onGoogleMapEventListener != null) {
            onGoogleMapEventListener.getDuration(durations,distance,stepsData);
        }
    }
}