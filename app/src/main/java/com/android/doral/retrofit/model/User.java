package com.android.doral.retrofit.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("company_id")
    @Expose
    private Object companyId;
    @SerializedName("employeeID")
    @Expose
    private Object employeeID;
    @SerializedName("dlNumber")
    @Expose
    private Object dlNumber;
    @SerializedName("avatar")
    @Expose
    private Object avatar;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("phone_verified_at")
    @Expose
    private Object phoneVerifiedAt;
    @SerializedName("tele_phone")
    @Expose
    private Object telePhone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("email_verified_at")
    @Expose
    private String emailVerifiedAt;
    @SerializedName("profile_verified_at")
    @Expose
    private Object profileVerifiedAt;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("designation_id")
    @Expose
    private Object designationId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("api_token")
    @Expose
    private Object apiToken;
    @SerializedName("device_token")
    @Expose
    private String deviceToken;
    @SerializedName("device_type")
    @Expose
    private Integer deviceType;
    @SerializedName("latitude")
    @Expose
    private Object latitude;
    @SerializedName("longitude")
    @Expose
    private Object longitude;
    @SerializedName("is_available")
    @Expose
    private String isAvailable;
    @SerializedName("welcome_message")
    @Expose
    private Object welcomeMessage;
    @SerializedName("week_off")
    @Expose
    private Object weekOff;
    @SerializedName("work_start_time")
    @Expose
    private Object workStartTime;
    @SerializedName("work_end_time")
    @Expose
    private Object workEndTime;
    @SerializedName("web_token")
    @Expose
    private Object webToken;
    @SerializedName("gender_name")
    @Expose
    private String genderName;
    @SerializedName("avatar_image")
    @Expose
    private String avatarImage;
    @SerializedName("phone_format")
    @Expose
    private String phoneFormat;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Object companyId) {
        this.companyId = companyId;
    }

    public Object getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Object employeeID) {
        this.employeeID = employeeID;
    }

    public Object getDlNumber() {
        return dlNumber;
    }

    public void setDlNumber(Object dlNumber) {
        this.dlNumber = dlNumber;
    }

    public Object getAvatar() {
        return avatar;
    }

    public void setAvatar(Object avatar) {
        this.avatar = avatar;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getPhoneVerifiedAt() {
        return phoneVerifiedAt;
    }

    public void setPhoneVerifiedAt(Object phoneVerifiedAt) {
        this.phoneVerifiedAt = phoneVerifiedAt;
    }

    public Object getTelePhone() {
        return telePhone;
    }

    public void setTelePhone(Object telePhone) {
        this.telePhone = telePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(String emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }

    public Object getProfileVerifiedAt() {
        return profileVerifiedAt;
    }

    public void setProfileVerifiedAt(Object profileVerifiedAt) {
        this.profileVerifiedAt = profileVerifiedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getDesignationId() {
        return designationId;
    }

    public void setDesignationId(Object designationId) {
        this.designationId = designationId;
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

    public Object getApiToken() {
        return apiToken;
    }

    public void setApiToken(Object apiToken) {
        this.apiToken = apiToken;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public Object getLatitude() {
        return latitude;
    }

    public void setLatitude(Object latitude) {
        this.latitude = latitude;
    }

    public Object getLongitude() {
        return longitude;
    }

    public void setLongitude(Object longitude) {
        this.longitude = longitude;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Object getWelcomeMessage() {
        return welcomeMessage;
    }

    public void setWelcomeMessage(Object welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    public Object getWeekOff() {
        return weekOff;
    }

    public void setWeekOff(Object weekOff) {
        this.weekOff = weekOff;
    }

    public Object getWorkStartTime() {
        return workStartTime;
    }

    public void setWorkStartTime(Object workStartTime) {
        this.workStartTime = workStartTime;
    }

    public Object getWorkEndTime() {
        return workEndTime;
    }

    public void setWorkEndTime(Object workEndTime) {
        this.workEndTime = workEndTime;
    }

    public Object getWebToken() {
        return webToken;
    }

    public void setWebToken(Object webToken) {
        this.webToken = webToken;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public String getAvatarImage() {
        return avatarImage;
    }

    public void setAvatarImage(String avatarImage) {
        this.avatarImage = avatarImage;
    }

    public String getPhoneFormat() {
        return phoneFormat;
    }

    public void setPhoneFormat(String phoneFormat) {
        this.phoneFormat = phoneFormat;
    }

}
