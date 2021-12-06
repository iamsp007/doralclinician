package com.android.doral.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.android.doral.retrofit.model.UserModel;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

public class MyPref {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public MyPref(Context context) {
        preferences = context.getSharedPreferences(context.getPackageName(), MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setUserData(UserModel userData) {
        editor.putString(Keys.UserData.name(), new Gson().toJson(userData));
        editor.commit();
    }

    public UserModel getUserData() {
        String userData = preferences.getString(Keys.UserData.name(), "");
        if (TextUtils.isEmpty(userData))
            return new UserModel();
        UserModel user = new Gson().fromJson(userData, UserModel.class);
        if (user == null) return new UserModel();
        return user;
    }


    public void setData(Keys keys, boolean isData) {
        editor.putBoolean(keys.name(), isData);
        editor.commit();
    }

    public void setData(Keys keys, String isData) {
        editor.putString(keys.name(), isData);
        editor.commit();
    }

    public String getData(Keys keys) {
        return preferences.getString(keys.name(), "");
    }

    public boolean getData(Keys keys, boolean defaults) {
        return preferences.getBoolean(keys.name(), defaults);
    }

    public void clearPrefs() {
        editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    public enum Keys {
        UserData, VehicleData, token, LAT, LAG, IsLogin, IsProfile, IsDriverStatus, CANCELREASON, NOTIFICATION_STATUS, LIFT_STATUS, OUTSIDE_DRIVER_STATUS,
        RENTAL_RIDE_STATUS, RATING_DATA, RIDE_TIME, RIDE_DISTANCE, RIDE_LAST_LAT_LOCATION, RIDE_LAST_LANG_LOCATION, VEH_IMAGES, DRIVER_TMP_STATUS,USER_NAME,PASSWORD,COVID_TIME,FORM_ID,
        ACCEPT_SOCKET,ID_SOCKET,PARENT_ID_SOCKET,TYPE_ID_SOCKET,PARENT_ID,START_SOCKET_BACKGROUND;
    }
}
