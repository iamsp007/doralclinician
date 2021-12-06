package com.android.doral.retrofit.model;

import android.graphics.Bitmap;

import com.android.doral.Utils.StringUtils;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class UserModel extends BaseModel implements Serializable {
    private String id, first_name, user_id, clincial_id, last_name, email, type_id, parent_id, password, gender, dob, phone, type, access_token, token_type, latitude, longitude, start_latitude, end_longitude, referral_type, avatar_image, is_available, gender_name, icon, color,designation_id,preparation_time,accepted_time,travel_time,passcode;
    private UserModel data, user, detail;
    private String device_token, device_type,service_id;
    private boolean isEmailVerified, isMobileVerified, isProfileVerified, isApplicant, isEducation, isProfessional, isBackground, isDeposit, isDocuments;

    private String duration, kilometer;
    private String patient_request_id;


    private List<UserRoles> roles;
    private Polyline lastPolyline;
    private Marker marker;
    private double lastLat = 0, LastLng = 0;
    private Bitmap iconBitmap;
    private UserModel demographic;
    private AddressModel address;
    private String insurance,diagnosis;

    public String getIsApplicantStatus() {
        return isApplicantStatus;
    }

    public void setIsApplicantStatus(String isApplicantStatus) {
        this.isApplicantStatus = isApplicantStatus;
    }

    private String isApplicantStatus;


    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
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

    public UserModel getData() {
        return data;
    }

    public void setData(UserModel data) {
        this.data = data;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getFullName() {
        if (!StringUtils.isNotEmpty(last_name)) {
            return first_name;
        }
        return first_name + " " + last_name;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        isEmailVerified = emailVerified;
    }

    public boolean isMobileVerified() {
        return isMobileVerified;
    }

    public void setMobileVerified(boolean mobileVerified) {
        isMobileVerified = mobileVerified;
    }

    public boolean isProfileVerified() {
        return isProfileVerified;
    }

    public void setProfileVerified(boolean profileVerified) {
        isProfileVerified = profileVerified;
    }

    public String getAvatar_image() {
        return avatar_image;
    }

    public void setAvatar_image(String avatar_image) {
        this.avatar_image = avatar_image;
    }

    public String getIs_available() {
        return is_available;
    }

    public void setIs_available(String is_available) {
        this.is_available = is_available;
    }

    public String getGender_name() {
        return gender_name;
    }

    public void setGender_name(String gender_name) {
        this.gender_name = gender_name;
    }

    public boolean isApplicant() {
        return isApplicant;
    }

    public void setApplicant(boolean applicant) {
        isApplicant = applicant;
    }

    public boolean isEducation() {
        return isEducation;
    }

    public void setEducation(boolean education) {
        isEducation = education;
    }

    public boolean isProfessional() {
        return isProfessional;
    }

    public void setProfessional(boolean professional) {
        isProfessional = professional;
    }

    public boolean isBackground() {
        return isBackground;
    }

    public void setBackground(boolean background) {
        isBackground = background;
    }

    public boolean isDeposit() {
        return isDeposit;
    }

    public void setDeposit(boolean deposit) {
        isDeposit = deposit;
    }

    public boolean isDocuments() {
        return isDocuments;
    }

    public void setDocuments(boolean documents) {
        isDocuments = documents;
    }

    public UserModel getDetail() {
        return detail;
    }

    public void setDetail(UserModel detail) {
        this.detail = detail;
    }

    public List<UserRoles> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRoles> roles) {
        this.roles = roles;
    }

    public String getStart_latitude() {
        return start_latitude;
    }

    public void setStart_latitude(String start_latitude) {
        this.start_latitude = start_latitude;
    }

    public String getEnd_longitude() {
        return end_longitude;
    }

    public void setEnd_longitude(String end_longitude) {
        this.end_longitude = end_longitude;
    }

    public String getReferral_type() {
        return referral_type;
    }

    public void setReferral_type(String referral_type) {
        this.referral_type = referral_type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public Polyline getLastPolyline() {
        return lastPolyline;
    }

    public void setLastPolyline(Polyline lastPolyline) {
        this.lastPolyline = lastPolyline;
    }

    public double getLastLat() {
        return lastLat;
    }

    public void setLastLat(double lastLat) {
        this.lastLat = lastLat;
    }

    public double getLastLng() {
        return LastLng;
    }

    public void setLastLng(double lastLng) {
        LastLng = lastLng;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getKilometer() {
        return kilometer;
    }

    public void setKilometer(String kilometer) {
        this.kilometer = kilometer;
    }

    public String getClincial_id() {
        return clincial_id;
    }

    public void setClincial_id(String clincial_id) {
        this.clincial_id = clincial_id;
    }

    public Bitmap getIconBitmap() {
        return iconBitmap;
    }

    public void setIconBitmap(Bitmap iconBitmap) {
        this.iconBitmap = iconBitmap;
    }

    public String getDesignation_id() {
        return designation_id;
    }

    public void setDesignation_id(String designation_id) {
        this.designation_id = designation_id;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public UserModel getDemographic() {
        return demographic;
    }

    public void setDemographic(UserModel demographic) {
        this.demographic = demographic;
    }

    public AddressModel getAddress() {
        return address;
    }


    public void setAddress(AddressModel address) {
        this.address = address;
    }

    public String getPatient_request_id() {
        return patient_request_id;
    }

    public void setPatient_request_id(String patient_request_id) {
        this.patient_request_id = patient_request_id;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getPreparation_time() {
        return preparation_time;
    }

    public void setPreparation_time(String preparation_time) {
        this.preparation_time = preparation_time;
    }

    public String getAccepted_time() {
        return accepted_time;
    }

    public void setAccepted_time(String accepted_time) {
        this.accepted_time = accepted_time;
    }

    public String getTravel_time() {
        return travel_time;
    }

    public void setTravel_time(String travel_time) {
        this.travel_time = travel_time;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }
}
