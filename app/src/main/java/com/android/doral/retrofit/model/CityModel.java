package com.android.doral.retrofit.model;

import java.io.Serializable;

public class CityModel extends BaseModel implements Serializable {
    private String id = "", city = "", state_code = "",city_code="";

    public CityModel(String state) {
        this.city = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getState_code() {
        return state_code;
    }

    public void setState_code(String state_code) {
        this.state_code = state_code;
    }

    @Override
    public String toString() {
        return city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }
}
