package com.android.doral.retrofit.model;

public class CompanyHistoryModel {
    String companyName;
    String address;
    String phoneNo;

    public String getAddress_line_2() {
        return address_line_2;
    }

    public void setAddress_line_2(String address_line_2) {
        this.address_line_2 = address_line_2;
    }

    String address_line_2;
    String state_id;
    String city_id;
    String zipcode;
    String building;

    public CompanyHistoryModel(String companyName, String address, String phoneNo,String address_line_2,String state_id,String city_id,String zipcode,String building) {
        this.companyName = companyName;
        this.address = address;
        this.phoneNo = phoneNo;
        this.address_line_2 = address_line_2;
        this.state_id = state_id;
        this.city_id = city_id;
        this.zipcode = zipcode;
        this.building = building;

    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

//    public String getSupervisor() {
//        return supervisor;
//    }
//
//    public void setSupervisor(String supervisor) {
//        this.supervisor = supervisor;
//    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }
}
