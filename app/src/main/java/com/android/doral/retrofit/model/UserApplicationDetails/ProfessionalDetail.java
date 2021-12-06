
package com.android.doral.retrofit.model.UserApplicationDetails;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfessionalDetail {

    @SerializedName("age_0_9")
    @Expose
    private String age09;
    @SerializedName("age_10_13")
    @Expose
    private String age1013;
    @SerializedName("age_14_21")
    @Expose
    private String age1421;
    @SerializedName("age_22_40")
    @Expose
    private String age2240;
    @SerializedName("age_41_65")
    @Expose
    private String age4165;
    @SerializedName("age_65Plus")
    @Expose
    private String age65Plus;
    @SerializedName("stateLicense")
    @Expose
    private List<StateLicense> stateLicense = null;
    @SerializedName("federal_DEA_id")
    @Expose
    private String federalDEAId;
    @SerializedName("boardCertificate")
    @Expose
    private List<BoardCertificate> boardCertificate = null;
    @SerializedName("medicaidEnrolled")
    @Expose
    private String medicaidEnrolled;
    @SerializedName("medicareEnrolled")
    @Expose
    private String medicareEnrolled;
    @SerializedName("medicaidEnrolled_Number")
    @Expose
    private String medicaidEnrolledNumber;
    @SerializedName("medicareEnrolled_Number")
    @Expose
    private String medicareEnrolledNumber;
    @SerializedName("medicaidEnrolled_StateId")
    @Expose
    private String medicaidEnrolledStateId;
    @SerializedName("medicareEnrolled_StateId")
    @Expose
    private String medicareEnrolledStateId;

    public String getAge09() {
        return age09;
    }

    public void setAge09(String age09) {
        this.age09 = age09;
    }

    public String getAge1013() {
        return age1013;
    }

    public void setAge1013(String age1013) {
        this.age1013 = age1013;
    }

    public String getAge1421() {
        return age1421;
    }

    public void setAge1421(String age1421) {
        this.age1421 = age1421;
    }

    public String getAge2240() {
        return age2240;
    }

    public void setAge2240(String age2240) {
        this.age2240 = age2240;
    }

    public String getAge4165() {
        return age4165;
    }

    public void setAge4165(String age4165) {
        this.age4165 = age4165;
    }

    public String getAge65Plus() {
        return age65Plus;
    }

    public void setAge65Plus(String age65Plus) {
        this.age65Plus = age65Plus;
    }

    public List<StateLicense> getStateLicense() {
        return stateLicense;
    }

    public void setStateLicense(List<StateLicense> stateLicense) {
        this.stateLicense = stateLicense;
    }

    public String getFederalDEAId() {
        return federalDEAId;
    }

    public void setFederalDEAId(String federalDEAId) {
        this.federalDEAId = federalDEAId;
    }

    public List<BoardCertificate> getBoardCertificate() {
        return boardCertificate;
    }

    public void setBoardCertificate(List<BoardCertificate> boardCertificate) {
        this.boardCertificate = boardCertificate;
    }

    public String getMedicaidEnrolled() {
        return medicaidEnrolled;
    }

    public void setMedicaidEnrolled(String medicaidEnrolled) {
        this.medicaidEnrolled = medicaidEnrolled;
    }

    public String getMedicareEnrolled() {
        return medicareEnrolled;
    }

    public void setMedicareEnrolled(String medicareEnrolled) {
        this.medicareEnrolled = medicareEnrolled;
    }

    public String getMedicaidEnrolledNumber() {
        return medicaidEnrolledNumber;
    }

    public void setMedicaidEnrolledNumber(String medicaidEnrolledNumber) {
        this.medicaidEnrolledNumber = medicaidEnrolledNumber;
    }

    public String getMedicareEnrolledNumber() {
        return medicareEnrolledNumber;
    }

    public void setMedicareEnrolledNumber(String medicareEnrolledNumber) {
        this.medicareEnrolledNumber = medicareEnrolledNumber;
    }

    public String getMedicaidEnrolledStateId() {
        return medicaidEnrolledStateId;
    }

    public void setMedicaidEnrolledStateId(String medicaidEnrolledStateId) {
        this.medicaidEnrolledStateId = medicaidEnrolledStateId;
    }

    public String getMedicareEnrolledStateId() {
        return medicareEnrolledStateId;
    }

    public void setMedicareEnrolledStateId(String medicareEnrolledStateId) {
        this.medicareEnrolledStateId = medicareEnrolledStateId;
    }

}
