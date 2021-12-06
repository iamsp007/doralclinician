
package com.android.doral.retrofit.model.UserApplicationDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PayrollDetails {

    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("building")
    @Expose
    private String building;
    @SerializedName("filesTax")
    @Expose
    private int filesTax;
    @SerializedName("state_id")
    @Expose
    private String stateId;
    @SerializedName("zip_code")
    @Expose
    private String zipCode;
    @SerializedName("dependents")
    @Expose
    private String dependents;
    @SerializedName("nameOfBank")
    @Expose
    private String nameOfBank;
    @SerializedName("filesyourtax")
    @Expose
    private String filesyourtax;
    @SerializedName("legal_entity")
    @Expose
    private String legalEntity;
    @SerializedName("send_tax_doc")
    @Expose
    private int sendTaxDoc;
    @SerializedName("accountNumber")
    @Expose
    private String accountNumber;
    @SerializedName("nameOfAccount")
    @Expose
    private String nameOfAccount;
    @SerializedName("routingNumber")
    @Expose
    private String routingNumber;
    @SerializedName("typeOfAccount")
    @Expose
    private String typeOfAccount;
    @SerializedName("address_line_1")
    @Expose
    private String addressLine1;
    @SerializedName("address_line_2")
    @Expose
    private String addressLine2;
    @SerializedName("otherdependents")
    @Expose
    private String otherdependents;
    @SerializedName("childrendependents")
    @Expose
    private String childrendependents;
    @SerializedName("taxpayer_id_number")
    @Expose
    private String taxpayerIdNumber;
    @SerializedName("confirmAcountNumber")
    @Expose
    private String confirmAcountNumber;

    @SerializedName("totaldependents")
    private String totaldependents;
    @SerializedName("sendtaxdocument")
    private String sendtaxdocument;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public int getFilesTax() {
        return filesTax;
    }

    public void setFilesTax(int filesTax) {
        this.filesTax = filesTax;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getDependents() {
        return dependents;
    }

    public void setDependents(String dependents) {
        this.dependents = dependents;
    }

    public String getNameOfBank() {
        return nameOfBank;
    }

    public void setNameOfBank(String nameOfBank) {
        this.nameOfBank = nameOfBank;
    }

    public String getFilesyourtax() {
        return filesyourtax;
    }

    public void setFilesyourtax(String filesyourtax) {
        this.filesyourtax = filesyourtax;
    }

    public String getLegalEntity() {
        return legalEntity;
    }

    public void setLegalEntity(String legalEntity) {
        this.legalEntity = legalEntity;
    }

    public int getSendTaxDoc() {
        return sendTaxDoc;
    }

    public void setSendTaxDoc(int sendTaxDoc) {
        this.sendTaxDoc = sendTaxDoc;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getNameOfAccount() {
        return nameOfAccount;
    }

    public void setNameOfAccount(String nameOfAccount) {
        this.nameOfAccount = nameOfAccount;
    }

    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    public String getTypeOfAccount() {
        return typeOfAccount;
    }

    public void setTypeOfAccount(String typeOfAccount) {
        this.typeOfAccount = typeOfAccount;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getOtherdependents() {
        return otherdependents;
    }

    public void setOtherdependents(String otherdependents) {
        this.otherdependents = otherdependents;
    }

    public String getChildrendependents() {
        return childrendependents;
    }

    public void setChildrendependents(String childrendependents) {
        this.childrendependents = childrendependents;
    }

    public String getTaxpayerIdNumber() {
        return taxpayerIdNumber;
    }

    public void setTaxpayerIdNumber(String taxpayerIdNumber) {
        this.taxpayerIdNumber = taxpayerIdNumber;
    }

    public String getConfirmAcountNumber() {
        return confirmAcountNumber;
    }

    public void setConfirmAcountNumber(String confirmAcountNumber) {
        this.confirmAcountNumber = confirmAcountNumber;
    }

    public String getTotaldependents() {
        return totaldependents;
    }

    public void setTotaldependents(String totaldependents) {
        this.totaldependents = totaldependents;
    }

    public String getSendtaxdocument() {
        return sendtaxdocument;
    }

    public void setSendtaxdocument(String sendtaxdocument) {
        this.sendtaxdocument = sendtaxdocument;
    }
}
