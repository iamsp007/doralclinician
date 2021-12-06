package com.android.doral.Utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.doral.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Utility {

    public static String recipient_sign = "";
    public static String interpreter_sign = "";
    public static String vaccination_sign = "";
    public static String CATEGORIES_ID = "";
    public static String SUBCATEGORIES_ID = "";
    public static String CATEGORIES_NAME = "";
    public static String SUBCATEGORIES_NAME = "";
    public static boolean IS_SUBCATEGORIES_Available = false;
    public static String Driving = "driving";
    public static String Walking  = "walking";
    public static String Bicycling  = "bicycling";
    public static String Transit = "transit";
    public static String Bus = "bus";
    public static String Train = "train";
    public static String Subway = "subway";
    /* public static String Motorcycle = "Two-wheeler";
     public static String Ride = "Ride";
     public static String Bicycle = "Cycling";
     public static String Flight = "Flight";*/
    public static String TRAVEL_MODES = "";
    public static String TRANSIT_MODES = "";

    public static boolean isValidEmail(String target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    /*public static void replaceFragement(AppCompatActivity activity,Fragment newFragment,  boolean isAdd) {
        if (newFragment.isAdded()) {
            return;
        }



        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        *//*transaction.setCustomAnimations(
                R.anim.slide_left_in,  // enter
                R.anim.slide_left_out,  // exit
                R.anim.slide_right_in,   // popEnter
                R.anim.slide_right_out  // popExit
        );*//*
// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
        if (isAdd) {
            transaction.add(R.id.fragment_container, newFragment);
            transaction.commit();
        } else {

            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            Fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
            ft.remove(fragment);
            ft.commit();

        }

// Commit the transaction

    }*/


    public static String getCity(Context context, LatLng latLng) {
        String city = "";
        Geocoder geocoder = new Geocoder(context);

        try {
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addressList != null && addressList.size() > 0) {
                String locality = addressList.get(0).getAddressLine(0);
                String country = addressList.get(0).getCountryName();
                if (!locality.isEmpty() && !country.isEmpty())

                    if (StringUtils.isNotEmpty(addressList.get(0).getLocality())) {
                        city = addressList.get(0).getLocality();


                    } else if (StringUtils.isNotEmpty(addressList.get(0).getSubLocality())) {
                        city = addressList.get(0).getSubLocality();


                    } else if (StringUtils.isNotEmpty(addressList.get(0).getSubAdminArea())) {
                        city = addressList.get(0).getSubAdminArea();


                    }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return city;
    }

    public static String getState(Context context, LatLng latLng) {
        String city = "";
        Geocoder geocoder = new Geocoder(context);

        try {
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addressList != null && addressList.size() > 0) {
                String locality = addressList.get(0).getAddressLine(0);
                String country = addressList.get(0).getCountryName();
                if (!locality.isEmpty() && !country.isEmpty())

                    if (StringUtils.isNotEmpty(addressList.get(0).getAdminArea())) {
                        city = addressList.get(0).getAdminArea();

                    }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return city;
    }

    public static String getPostalCode(Context context, LatLng latLng) {
        String city = "";
        Geocoder geocoder = new Geocoder(context);

        try {
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addressList != null && addressList.size() > 0) {
                String locality = addressList.get(0).getAddressLine(0);
                String country = addressList.get(0).getCountryName();
                if (!locality.isEmpty() && !country.isEmpty())

                    if (StringUtils.isNotEmpty(addressList.get(0).getPostalCode())) {
                        city = addressList.get(0).getPostalCode();

                    }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return city;
    }
}
