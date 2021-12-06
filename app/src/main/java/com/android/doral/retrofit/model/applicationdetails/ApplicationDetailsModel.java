
package com.android.doral.retrofit.model.applicationdetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.android.doral.retrofit.model.BaseModel;
import com.android.doral.retrofit.model.EducationModel;
import com.android.doral.retrofit.model.EmergencyDataModel;
import com.android.doral.retrofit.model.NurseEducationHistoryModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ApplicationDetailsModel extends BaseModel implements Serializable {


    @SerializedName("data")
    @Expose
    private ApplicationDetailsModel data;
    @SerializedName("home_phone")
    @Expose
    private String home_phone;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("family_detail")
    @Expose
    private FamilyDetail familyDetail;
    @SerializedName("payroll_details")
    @Expose
    private PayrollDetail payrollDetail;
    @SerializedName("military_detail")
    @Expose
    private MilitaryDetail militaryDetail;
    @SerializedName("security_detail")
    @Expose
    private SecurityDetail securityDetail;
    @SerializedName("address_detail")
    @Expose
    private AddressDetail addressDetail;
    @SerializedName("reference_detail")
    @Expose
    private List<EmergencyDataModel> referenceDetail = new ArrayList<EmergencyDataModel>();
    @SerializedName("employer_detail")
    @Expose
    private EmployerDetail employerDetail;
    @SerializedName("education_detail")
    @Expose
    private List<NurseEducationHistoryModel> educationDetail = new ArrayList<NurseEducationHistoryModel>();
    @SerializedName("language_detail")
    @Expose
    private LanguageDetail languageDetail;

    public Boolean getIs_signature_added() {
        return is_signature_added;
    }

    public void setIs_signature_added(Boolean is_signature_added) {
        this.is_signature_added = is_signature_added;
    }

    @SerializedName("is_signature_added")
    @Expose
    private Boolean is_signature_added=false;
    @SerializedName("emergency_detail")
    @Expose
    private List<EmergencyDataModel> emergencyDetail = new ArrayList<EmergencyDataModel>();
    @SerializedName("documents")
    @Expose
    private List<DocumentModel> documents = new ArrayList<DocumentModel>();
    private final static long serialVersionUID = 4976699615989566708L;

    public FamilyDetail getFamilyDetail() {
        return familyDetail;
    }

    public void setFamilyDetail(FamilyDetail familyDetail) {
        this.familyDetail = familyDetail;
    }

    public MilitaryDetail getMilitaryDetail() {
        return militaryDetail;
    }

    public void setMilitaryDetail(MilitaryDetail militaryDetail) {
        this.militaryDetail = militaryDetail;
    }

    public SecurityDetail getSecurityDetail() {
        return securityDetail;
    }

    public void setSecurityDetail(SecurityDetail securityDetail) {
        this.securityDetail = securityDetail;
    }

    public AddressDetail getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(AddressDetail addressDetail) {
        this.addressDetail = addressDetail;
    }

    public List<EmergencyDataModel> getReferenceDetail() {
        return referenceDetail;
    }

    public void setReferenceDetail(List<EmergencyDataModel> referenceDetail) {
        this.referenceDetail = referenceDetail;
    }

    public EmployerDetail getEmployerDetail() {
        return employerDetail;
    }

    public void setEmployerDetail(EmployerDetail employerDetail) {
        this.employerDetail = employerDetail;
    }

    public List<NurseEducationHistoryModel> getEducationDetail() {
        return educationDetail;
    }

    public void setEducationDetail(List<NurseEducationHistoryModel> educationDetail) {
        this.educationDetail = educationDetail;
    }

    public LanguageDetail getLanguageDetail() {
        return languageDetail;
    }

    public void setLanguageDetail(LanguageDetail languageDetail) {
        this.languageDetail = languageDetail;
    }

    public List<EmergencyDataModel> getEmergencyDetail() {
        return emergencyDetail;
    }

    public void setEmergencyDetail(List<EmergencyDataModel> emergencyDetail) {
        this.emergencyDetail = emergencyDetail;
    }

    public ApplicationDetailsModel getData() {
        return data;
    }

    public void setData(ApplicationDetailsModel data) {
        this.data = data;
    }

    public List<DocumentModel> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentModel> documents) {
        this.documents = documents;
    }

    public PayrollDetail getPayrollDetail() {
        return payrollDetail;
    }

    public void setPayrollDetail(PayrollDetail payrollDetail) {
        this.payrollDetail = payrollDetail;
    }

    public String getHome_phone() {
        return home_phone;
    }

    public void setHome_phone(String home_phone) {
        this.home_phone = home_phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
