package com.android.doral.retrofit.model;

import android.text.TextUtils;

import com.android.doral.Utils.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AddressModel implements Serializable {
    private String city, state, primary, address1, address2, zip_code, apt_building;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getApt_building() {
        return apt_building;
    }


    public void setApt_building(String apt_building) {
        this.apt_building = apt_building;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {

        this.address2 = address2;
    }

    public String getFullAddress() {
        List<String> list = new ArrayList<>();
        if (StringUtils.isNotEmpty(address1)) {
            list.add(address1);
        }
        if (StringUtils.isNotEmpty(address2)) {
            list.add(address2);
        }
        if (StringUtils.isNotEmpty(apt_building)) {
            list.add(apt_building);
        }
        if (StringUtils.isNotEmpty(city)) {
            list.add(city);
        }
        if (StringUtils.isNotEmpty(state)) {
            list.add(state);
        }
        if (StringUtils.isNotEmpty(zip_code)) {
            list.add(zip_code);
        }
        return TextUtils.join(",", list);
    }
}
