package com.android.doral.retrofit.model;

public class CompanyModel {

    private String company_name,state,city,start_date,end_date,position,reason,zipcode,address_line_1,address_line_2,cityId,stateId,building;

    public CompanyModel(String company_name, String state, String city, String start_date, String end_date, String position, String reason, String zipcode, String address_line_1,String address_line_2,String cityId,String stateId,String building) {
        this.company_name = company_name;
        this.state = state;
        this.city = city;
        this.start_date = start_date;
        this.end_date = end_date;
        this.position = position;
        this.reason = reason;
        this.zipcode = zipcode;
        this.address_line_1 = address_line_1;
        this.address_line_2 = address_line_2;
        this.cityId = cityId;
        this.stateId = stateId;
        this.building = building;

    }

    public CompanyModel() {

    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress_line_1() {
        return address_line_1;
    }

    public void setAddress_line_1(String address_line_1) {
        this.address_line_1 = address_line_1;
    }

    public String getAddress_line_2() {
        return address_line_2;
    }

    public void setAddress_line_2(String address_line_2) {
        this.address_line_2 = address_line_2;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getState_id() {
        return state;
    }

    public void setState_id(String state_id) {
        this.state = state_id;
    }

    public String getCity_id() {
        return city;
    }

    public void setCity_id(String city_id) {
        this.city = city_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }


    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
