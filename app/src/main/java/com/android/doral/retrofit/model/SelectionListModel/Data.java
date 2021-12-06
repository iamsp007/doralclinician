
package com.android.doral.retrofit.model.SelectionListModel;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("certifying_board")
    @Expose
    private List<CertifyingBoard> certifyingBoard = null;
    @SerializedName("application_status")
    @Expose
    private List<ApplicationStatus> applicationStatus = null;
    @SerializedName("relationships")
    @Expose
    private List<Relationship> relationships = null;
    @SerializedName("address_type")
    @Expose
    private List<AddressType> addressType = null;
    @SerializedName("legal_entity")
    @Expose
    private List<LegalEntity> legalEntity = null;
    @SerializedName("designation")
    @Expose
    private List<Designation> designation = null;
    @SerializedName("reason_for_leaving")
    @Expose
    private List<ReasonForLeaving> reasonForLeaving = null;

    public List<CertifyingBoard> getCertifyingBoard() {
        return certifyingBoard;
    }

    public void setCertifyingBoard(List<CertifyingBoard> certifyingBoard) {
        this.certifyingBoard = certifyingBoard;
    }

    public List<ApplicationStatus> getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(List<ApplicationStatus> applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public List<Relationship> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<Relationship> relationships) {
        this.relationships = relationships;
    }

    public List<AddressType> getAddressType() {
        return addressType;
    }

    public void setAddressType(List<AddressType> addressType) {
        this.addressType = addressType;
    }

    public List<LegalEntity> getLegalEntity() {
        return legalEntity;
    }

    public void setLegalEntity(List<LegalEntity> legalEntity) {
        this.legalEntity = legalEntity;
    }

    public List<Designation> getDesignation() {
        return designation;
    }

    public void setDesignation(List<Designation> designation) {
        this.designation = designation;
    }

    public List<ReasonForLeaving> getReasonForLeaving() {
        return reasonForLeaving;
    }

    public void setReasonForLeaving(List<ReasonForLeaving> reasonForLeaving) {
        this.reasonForLeaving = reasonForLeaving;
    }

}
