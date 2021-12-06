package com.android.doral.retrofit.model;

import java.io.Serializable;
import java.util.List;

public class RequestModel extends BaseModel implements Serializable {
    private String id, user_id, clincial_id, type_id, parent_id, latitude, longitude, reason, is_active, created_at, updated_at, dieses, symptoms, is_parking, test_name = "",sub_test_name="", preparation_time, preparasion_date;
    private UserModel patient_detail;
    private String distance, travel_time;

    private UserModel detail;
    private List<RequestModel> data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getClincial_id() {
        return clincial_id;
    }

    public void setClincial_id(String clincial_id) {
        this.clincial_id = clincial_id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDieses() {
        return dieses;
    }

    public void setDieses(String dieses) {
        this.dieses = dieses;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getIs_parking() {
        return is_parking;
    }

    public void setIs_parking(String is_parking) {
        this.is_parking = is_parking;
    }

    public UserModel getDetail() {
        return patient_detail;
    }

    public void setDetail(UserModel detail) {
        this.patient_detail = detail;
    }

    public List<RequestModel> getData() {
        return data;
    }

    public void setData(List<RequestModel> data) {
        this.data = data;
    }

    public String getTest_name() {
        return test_name;
    }

    public void setTest_name(String test_name) {
        this.test_name = test_name;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public UserModel getPatient_detail() {
        return patient_detail;
    }

    public void setPatient_detail(UserModel patient_detail) {
        this.patient_detail = patient_detail;
    }

    public String getPreparation_time() {
        return preparation_time;
    }

    public void setPreparation_time(String preparation_time) {
        this.preparation_time = preparation_time;
    }

    public String getPreparasion_date() {
        return preparasion_date;
    }

    public void setPreparasion_date(String preparasion_date) {
        this.preparasion_date = preparasion_date;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTravel_time() {
        return travel_time;
    }

    public void setTravel_time(String travel_time) {
        this.travel_time = travel_time;
    }

    public String getSub_test_name() {
        return sub_test_name;
    }

    public void setSub_test_name(String sub_test_name) {
        this.sub_test_name = sub_test_name;
    }
}
