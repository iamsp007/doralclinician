
package com.android.doral.retrofit.model.UserApplicationDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EducationDetail {

    @SerializedName("medicalInstitute")
    @Expose
    private MedicalInstitute medicalInstitute;
    @SerializedName("residencyInstitute")
    @Expose
    private ResidencyInstitute residencyInstitute;
    @SerializedName("fellowshipInstitute")
    @Expose
    private FellowshipInstitute fellowshipInstitute;

    public MedicalInstitute getMedicalInstitute() {
        return medicalInstitute;
    }

    public void setMedicalInstitute(MedicalInstitute medicalInstitute) {
        this.medicalInstitute = medicalInstitute;
    }

    public ResidencyInstitute getResidencyInstitute() {
        return residencyInstitute;
    }

    public void setResidencyInstitute(ResidencyInstitute residencyInstitute) {
        this.residencyInstitute = residencyInstitute;
    }

    public FellowshipInstitute getFellowshipInstitute() {
        return fellowshipInstitute;
    }

    public void setFellowshipInstitute(FellowshipInstitute fellowshipInstitute) {
        this.fellowshipInstitute = fellowshipInstitute;
    }

}
