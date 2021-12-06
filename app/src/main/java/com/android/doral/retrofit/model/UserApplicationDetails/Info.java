
package com.android.doral.retrofit.model.UserApplicationDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

    @SerializedName("ssn")
    @Expose
    private String ssn;
    @SerializedName("us_citizen")
    @Expose
    private String usCitizen;
    @SerializedName("date_available")
    @Expose
    private String dateAvailable;
    @SerializedName("emergency_phone")
    @Expose
    private String emergencyPhone;
    @SerializedName("immigration_card")
    @Expose
    private String immigrationCard;

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getUsCitizen() {
        return usCitizen;
    }

    public void setUsCitizen(String usCitizen) {
        this.usCitizen = usCitizen;
    }

    public String getDateAvailable() {
        return dateAvailable;
    }

    public void setDateAvailable(String dateAvailable) {
        this.dateAvailable = dateAvailable;
    }

    public String getEmergencyPhone() {
        return emergencyPhone;
    }

    public void setEmergencyPhone(String emergencyPhone) {
        this.emergencyPhone = emergencyPhone;
    }

    public String getImmigrationCard() {
        return immigrationCard;
    }

    public void setImmigrationCard(String immigrationCard) {
        this.immigrationCard = immigrationCard;
    }

}
