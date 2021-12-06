package com.android.doral.retrofit.model;

public class NurseEducationHistoryModel {
    String name;
    String year;
    String isGraduate;
    String Degree;
    String address_line_2;
    String state_id;
    String city_id;
    String zipcode;
    String building;

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    String website;


    public NurseEducationHistoryModel(String name, String year, String isGraduate ,String degree ,String address_line_2, String state_id, String city_id, String zipcode, String building,String website) {
        this.name = name;
        this.year = year;
        this.isGraduate = isGraduate;
        Degree = degree;
        this.address_line_2 = address_line_2;
        this.state_id = state_id;
        this.city_id = city_id;
        this.zipcode = zipcode;
        this.building = building;
        this.website = website;
    }

    public NurseEducationHistoryModel(String toString, String s, String address, String year, String degree) {

        this.name = address;
        this.year = year;
        Degree = degree;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getIsGraduate() {
        return isGraduate;
    }

    public void setIsGraduate(String isGraduate) {
        this.isGraduate = isGraduate;
    }

    public String getDegree() {
        return Degree;
    }

    public void setDegree(String degree) {
        Degree = degree;
    }

    public String getAddress_line_2() {
        return address_line_2;
    }

    public void setAddress_line_2(String address_line_2) {
        this.address_line_2 = address_line_2;
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
}
