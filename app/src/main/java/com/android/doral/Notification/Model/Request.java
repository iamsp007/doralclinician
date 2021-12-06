package com.android.doral.Notification.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Request {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("clincial_id")
    @Expose
    private Integer clincialId;
    @SerializedName("test_name")
    @Expose
    private String testName;
    @SerializedName("type_id")
    @Expose
    private Integer typeId;
    @SerializedName("parent_id")
    @Expose
    private Integer parentId;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("is_active")
    @Expose
    private String isActive;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("dieses")
    @Expose
    private String dieses;
    @SerializedName("symptoms")
    @Expose
    private String symptoms;
    @SerializedName("is_parking")
    @Expose
    private Object isParking;
    @SerializedName("prepare_time")
    @Expose
    private Object prepareTime;
    @SerializedName("otp")
    @Expose
    private Object otp;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("preparation_time")
    @Expose
    private Object preparationTime;
    @SerializedName("preparasion_date")
    @Expose
    private Object preparasionDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getClincialId() {
        return clincialId;
    }

    public void setClincialId(Integer clincialId) {
        this.clincialId = clincialId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
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

    public Object getIsParking() {
        return isParking;
    }

    public void setIsParking(Object isParking) {
        this.isParking = isParking;
    }

    public Object getPrepareTime() {
        return prepareTime;
    }

    public void setPrepareTime(Object prepareTime) {
        this.prepareTime = prepareTime;
    }

    public Object getOtp() {
        return otp;
    }

    public void setOtp(Object otp) {
        this.otp = otp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Object preparationTime) {
        this.preparationTime = preparationTime;
    }

    public Object getPreparasionDate() {
        return preparasionDate;
    }

    public void setPreparasionDate(Object preparasionDate) {
        this.preparasionDate = preparasionDate;
    }

}
