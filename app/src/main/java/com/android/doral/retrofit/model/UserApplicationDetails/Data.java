
package com.android.doral.retrofit.model.UserApplicationDetails;
import java.util.ArrayList;
import java.util.List;

import com.android.doral.retrofit.model.CompanyModel;
import com.android.doral.retrofit.model.EmergencyDataModel;
import com.android.doral.retrofit.model.WorkHistroyModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("family_detail")
    @Expose
    private Object familyDetail;
    @SerializedName("military_detail")
    @Expose
    private Object militaryDetail;
    @SerializedName("security_detail")
    @Expose
    private Object securityDetail;
    @SerializedName("address_detail")
    @Expose
    private AddressDetail addressDetail;
    @SerializedName("prior_detail")
    @Expose
    private Object priorDetail;
    @SerializedName("reference_detail")
    @Expose
    private List<ReferenceDetail> referenceDetail = null;
    @SerializedName("employer_detail")
    @Expose
    private EmployerDetail employerDetail;
    @SerializedName("education_detail")
    @Expose
    private EducationDetail educationDetail;
    @SerializedName("language_detail")
    @Expose
    private Object languageDetail;
    @SerializedName("skill_detail")
    @Expose
    private Object skillDetail;
    @SerializedName("emergency_detail")
    @Expose
    private Object emergencyDetail;
    @SerializedName("payroll_details")
    @Expose
    private PayrollDetails payrollDetails;
    @SerializedName("applicant_name")
    @Expose
    private Object applicantName;
    @SerializedName("other_name")
    @Expose
    private Object otherName;
    @SerializedName("ssn")
    @Expose
    private String ssn;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("home_phone")
    @Expose
    private String homePhone;
    @SerializedName("date")
    @Expose
    private Object date;
    @SerializedName("us_citizen")
    @Expose
    private int usCitizen;
    @SerializedName("immigration_id")
    @Expose
    private Object immigrationId;
    @SerializedName("address_line_1")
    @Expose
    private Object addressLine1;
    @SerializedName("address_line_2")
    @Expose
    private Object addressLine2;
    @SerializedName("city")
    @Expose
    private Object city;
    @SerializedName("state")
    @Expose
    private Object state;
    @SerializedName("zip")
    @Expose
    private Object zip;
    @SerializedName("address_life")
    @Expose
    private Object addressLife;
    @SerializedName("bonded")
    @Expose
    private int bonded;
    @SerializedName("refused_bond")
    @Expose
    private int refusedBond;
    @SerializedName("convicted_crime")
    @Expose
    private int convictedCrime;
    @SerializedName("emergency_name")
    @Expose
    private Object emergencyName;
    @SerializedName("emergency_address")
    @Expose
    private Object emergencyAddress;
    @SerializedName("emergency_phone")
    @Expose
    private String emergencyPhone;
    @SerializedName("emergency_relationship")
    @Expose
    private Object emergencyRelationship;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("signature")
    @Expose
    private Object signature;
    @SerializedName("workHistory_detail")
    @Expose
    private WorkHistoryDetail workHistoryDetail;
   // private List<WorkHistoryDetail> workHistoryDetail =null;
    @SerializedName("professional_detail")
    @Expose
    private ProfessionalDetail professionalDetail;
    @SerializedName("signature_url")
    @Expose
    private Object signatureUrl;
    @SerializedName("is_signature_added")
    @Expose
    private boolean isSignatureAdded;
    @SerializedName("documents")
    @Expose
    private List<Document> documents = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Object getFamilyDetail() {
        return familyDetail;
    }

    public void setFamilyDetail(Object familyDetail) {
        this.familyDetail = familyDetail;
    }

    public Object getMilitaryDetail() {
        return militaryDetail;
    }

    public void setMilitaryDetail(Object militaryDetail) {
        this.militaryDetail = militaryDetail;
    }

    public Object getSecurityDetail() {
        return securityDetail;
    }

    public void setSecurityDetail(Object securityDetail) {
        this.securityDetail = securityDetail;
    }

    public AddressDetail getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(AddressDetail addressDetail) {
        this.addressDetail = addressDetail;
    }

    public Object getPriorDetail() {
        return priorDetail;
    }

    public void setPriorDetail(Object priorDetail) {
        this.priorDetail = priorDetail;
    }

    public List<ReferenceDetail> getReferenceDetail() {
        return referenceDetail;
    }

    public void setReferenceDetail(List<ReferenceDetail> referenceDetail) {
        this.referenceDetail = referenceDetail;
    }

    public EmployerDetail getEmployerDetail() {
        return employerDetail;
    }

    public void setEmployerDetail(EmployerDetail employerDetail) {
        this.employerDetail = employerDetail;
    }

    public EducationDetail getEducationDetail() {
        return educationDetail;
    }

    public void setEducationDetail(EducationDetail educationDetail) {
        this.educationDetail = educationDetail;
    }

    public Object getLanguageDetail() {
        return languageDetail;
    }

    public void setLanguageDetail(Object languageDetail) {
        this.languageDetail = languageDetail;
    }

    public Object getSkillDetail() {
        return skillDetail;
    }

    public void setSkillDetail(Object skillDetail) {
        this.skillDetail = skillDetail;
    }

    public Object getEmergencyDetail() {
        return emergencyDetail;
    }

    public void setEmergencyDetail(Object emergencyDetail) {
        this.emergencyDetail = emergencyDetail;
    }

    public PayrollDetails getPayrollDetails() {
        return payrollDetails;
    }

    public void setPayrollDetails(PayrollDetails payrollDetails) {
        this.payrollDetails = payrollDetails;
    }

    public Object getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(Object applicantName) {
        this.applicantName = applicantName;
    }

    public Object getOtherName() {
        return otherName;
    }

    public void setOtherName(Object otherName) {
        this.otherName = otherName;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }

    public int getUsCitizen() {
        return usCitizen;
    }

    public void setUsCitizen(int usCitizen) {
        this.usCitizen = usCitizen;
    }

    public Object getImmigrationId() {
        return immigrationId;
    }

    public void setImmigrationId(Object immigrationId) {
        this.immigrationId = immigrationId;
    }

    public Object getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(Object addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public Object getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(Object addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Object getZip() {
        return zip;
    }

    public void setZip(Object zip) {
        this.zip = zip;
    }

    public Object getAddressLife() {
        return addressLife;
    }

    public void setAddressLife(Object addressLife) {
        this.addressLife = addressLife;
    }

    public int getBonded() {
        return bonded;
    }

    public void setBonded(int bonded) {
        this.bonded = bonded;
    }

    public int getRefusedBond() {
        return refusedBond;
    }

    public void setRefusedBond(int refusedBond) {
        this.refusedBond = refusedBond;
    }

    public int getConvictedCrime() {
        return convictedCrime;
    }

    public void setConvictedCrime(int convictedCrime) {
        this.convictedCrime = convictedCrime;
    }

    public Object getEmergencyName() {
        return emergencyName;
    }

    public void setEmergencyName(Object emergencyName) {
        this.emergencyName = emergencyName;
    }

    public Object getEmergencyAddress() {
        return emergencyAddress;
    }

    public void setEmergencyAddress(Object emergencyAddress) {
        this.emergencyAddress = emergencyAddress;
    }

    public String getEmergencyPhone() {
        return emergencyPhone;
    }

    public void setEmergencyPhone(String emergencyPhone) {
        this.emergencyPhone = emergencyPhone;
    }

    public Object getEmergencyRelationship() {
        return emergencyRelationship;
    }

    public void setEmergencyRelationship(Object emergencyRelationship) {
        this.emergencyRelationship = emergencyRelationship;
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

    public Object getSignature() {
        return signature;
    }

    public void setSignature(Object signature) {
        this.signature = signature;
    }

    public WorkHistoryDetail getWorkHistoryDetail() {
        return workHistoryDetail;
    }

    public void setWorkHistoryDetail( WorkHistoryDetail workHistoryDetail) {
        this.workHistoryDetail = workHistoryDetail;
    }

    public ProfessionalDetail getProfessionalDetail() {
        return professionalDetail;
    }

    public void setProfessionalDetail(ProfessionalDetail professionalDetail) {
        this.professionalDetail = professionalDetail;
    }

    public Object getSignatureUrl() {
        return signatureUrl;
    }

    public void setSignatureUrl(Object signatureUrl) {
        this.signatureUrl = signatureUrl;
    }

    public boolean isIsSignatureAdded() {
        return isSignatureAdded;
    }

    public void setIsSignatureAdded(boolean isSignatureAdded) {
        this.isSignatureAdded = isSignatureAdded;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

}
