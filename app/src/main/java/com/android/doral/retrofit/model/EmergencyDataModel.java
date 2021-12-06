package com.android.doral.retrofit.model;

public class EmergencyDataModel {


    public EmergencyDataModel() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address_line_1;
    }

    public void setAddress(String address) {
        this.address_line_1 = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getPersonRelation() {
        return personRelation;
    }

    public void setPersonRelation(String personRelation) {
        this.personRelation = personRelation;
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

    String name;
    String address_line_1;
    String address_line_2;
    String state_id;
    String city_id;
    String state;
    String city;
    String zipcode;
    String building;
    String phoneNo;

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

    String relation;
    String personRelation;

    public EmergencyDataModel(String name, String phoneNo, String building, String address, String address_line_2, String state_id, String city_id, String zipcode, String relation) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.building = building;
        this.address_line_1 = address;
        this.address_line_2 = address_line_2;
        this.state_id = state_id;
        this.city_id = city_id;
        this.state = state;
        this.city = city;
        this.zipcode = zipcode;
        this.relation = relation;
    }

//    public EmergencyDataModel(String name, String phoneNo, String building, String address, String address_line_2, String state_id, String city_id, String zipcode, String relation, String personRelation, String s) {
//        this.name = name;
//        this.phoneNo = phoneNo;
//        this.building = building;
//        this.address_line_1 = address;
//        this.address_line_2 = address_line_2;
//        this.state_id = state_id;
//        this.city_id = city_id;
//        this.zipcode = zipcode;
//        this.relation = relation;
//        this.personRelation = personRelation;
//    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", address_line_1='" + address_line_1 + '\'' +
                ", address_line_2='" + address_line_2 + '\'' +
                ", state_id='" + state_id + '\'' +
                ", city_id='" + city_id + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", building='" + building + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", relation='" + relation + '\'' +
                ", personRelation='" + personRelation + '\'' +
                '}';
    }
}
