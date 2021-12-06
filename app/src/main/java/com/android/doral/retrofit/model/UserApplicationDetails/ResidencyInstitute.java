
package com.android.doral.retrofit.model.UserApplicationDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResidencyInstitute {

    @SerializedName("medical_cityId")
    @Expose
    private String medicalCityId;
    @SerializedName("medical_stateId")
    @Expose
    private String medicalStateId;
    @SerializedName("medical_zipcode")
    @Expose
    private String medicalZipcode;
    @SerializedName("medical_address1")
    @Expose
    private String medicalAddress1;
    @SerializedName("medical_address2")
    @Expose
    private String medicalAddress2;
    @SerializedName("medical_building")
    @Expose
    private String medicalBuilding;
    @SerializedName("medical_yearEnded")
    @Expose
    private String medicalYearEnded;
    @SerializedName("medical_yearStarted")
    @Expose
    private String medicalYearStarted;
    @SerializedName("medical_instituteName")
    @Expose
    private String medicalInstituteName;

    public String getMedicalCityId() {
        return medicalCityId;
    }

    public void setMedicalCityId(String medicalCityId) {
        this.medicalCityId = medicalCityId;
    }

    public String getMedicalStateId() {
        return medicalStateId;
    }

    public void setMedicalStateId(String medicalStateId) {
        this.medicalStateId = medicalStateId;
    }

    public String getMedicalZipcode() {
        return medicalZipcode;
    }

    public void setMedicalZipcode(String medicalZipcode) {
        this.medicalZipcode = medicalZipcode;
    }

    public String getMedicalAddress1() {
        return medicalAddress1;
    }

    public void setMedicalAddress1(String medicalAddress1) {
        this.medicalAddress1 = medicalAddress1;
    }

    public String getMedicalAddress2() {
        return medicalAddress2;
    }

    public void setMedicalAddress2(String medicalAddress2) {
        this.medicalAddress2 = medicalAddress2;
    }

    public String getMedicalBuilding() {
        return medicalBuilding;
    }

    public void setMedicalBuilding(String medicalBuilding) {
        this.medicalBuilding = medicalBuilding;
    }

    public String getMedicalYearEnded() {
        return medicalYearEnded;
    }

    public void setMedicalYearEnded(String medicalYearEnded) {
        this.medicalYearEnded = medicalYearEnded;
    }

    public String getMedicalYearStarted() {
        return medicalYearStarted;
    }

    public void setMedicalYearStarted(String medicalYearStarted) {
        this.medicalYearStarted = medicalYearStarted;
    }

    public String getMedicalInstituteName() {
        return medicalInstituteName;
    }

    public void setMedicalInstituteName(String medicalInstituteName) {
        this.medicalInstituteName = medicalInstituteName;
    }

}
