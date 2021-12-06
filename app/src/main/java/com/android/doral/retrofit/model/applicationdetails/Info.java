package com.android.doral.retrofit.model.applicationdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {
    @SerializedName("ssn")
    @Expose
    private String ssn;

    @SerializedName("emergency_phone")
    @Expose
    private String emergency_phone;

    @SerializedName("immigration_card")
    @Expose
    private String immigration_card;

    @SerializedName("date_available")
    @Expose
    private String date_available;

    @SerializedName("us_citizen")
    @Expose
    private String us_citizen;

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getEmergency_phone() {
        return emergency_phone;
    }

    public void setEmergency_phone(String emergency_phone) {
        this.emergency_phone = emergency_phone;
    }

    public String getDate_available() {
        return date_available;
    }

    public void setDate_available(String date_available) {
        this.date_available = date_available;
    }

    public String getImmigration_card() {
        return immigration_card;
    }

    public void setImmigration_card(String immigration_card) {
        this.immigration_card = immigration_card;
    }

    public String getUs_citizen() {
        return us_citizen;
    }

    public void setUs_citizen(String us_citizen) {
        this.us_citizen = us_citizen;
    }
}
