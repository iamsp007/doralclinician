package com.android.doral.retrofit.model.applicationdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PayrollDetail implements Serializable {


    @SerializedName("routingNumber")
    @Expose
    private String routingNumber;
    @SerializedName("nameOfAccount")
    @Expose
    private String nameOfAccount;
    @SerializedName("dependents")
    private String dependents;
    @Expose
    @SerializedName("typeOfAccount")
    private String typeOfAccount;
    @SerializedName("accountNumber")
    private String accountNumber;

    @SerializedName("city_id")
    @Expose
    private String city_id;
    @SerializedName("state_id")
    @Expose
    private String state_id;
    @SerializedName("zip_code")
    private String zip_code;

    @SerializedName("otherdependents")
    private String otherdependents;

    @SerializedName("childrendependents")
    private String childrendependents;

    @SerializedName("totaldependents")
    private String totaldependents;

    @SerializedName("filesyourtax")
    private String filesyourtax;

    @SerializedName("address_line_1")
    private String address_line_1;
    @SerializedName("address_line_2")
    private String address_line_2;
    @SerializedName("Apt")
    private String Apt;

    @SerializedName("nameofaccount")
    private String nameofaccount;


    public String getConfirmAcountNumber() {
        return confirmAcountNumber;
    }

    public void setConfirmAcountNumber(String confirmAcountNumber) {
        this.confirmAcountNumber = confirmAcountNumber;
    }

    private  String confirmAcountNumber;




    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    public String getNameOfAccount() {
        return nameOfAccount;
    }

    public void setNameOfAccount(String nameOfAccount) {
        this.nameOfAccount = nameOfAccount;
    }

    public String getDependents() {
        return dependents;
    }

    public void setDependents(String dependents) {
        this.dependents = dependents;
    }


    private Boolean istypeOfAccount;
    public String getTypeOfAccount() {
        return typeOfAccount;
    }

    public void setTypeOfAccount(String typeOfAccount) {
        this.typeOfAccount = typeOfAccount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
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

    public String getFilesyourtax() {
        return filesyourtax;
    }

    public void setFilesyourtax(String filesyourtax) {
        this.filesyourtax = filesyourtax;
    }

    public String getAddress_line_1() {
        return address_line_1;
    }

    public void setAddress_line_1(String address_line_1) {
        this.address_line_1 = address_line_1;
    }

    public String getAddress_line_2() {
        return address_line_2;
    }

    public void setAddress_line_2(String address_line_2) {
        this.address_line_2 = address_line_2;
    }

    public String getApt() {
        return Apt;
    }

    public void setApt(String apt) {
        Apt = apt;
    }

    public String getNameofaccount() {
        return nameofaccount;
    }

    public void setNameofaccount(String nameofaccount) {
        this.nameofaccount = nameofaccount;
    }

    public String getTotaldependents() {
        return totaldependents;
    }

    public void setTotaldependents(String totaldependents) {
        this.totaldependents = totaldependents;
    }
}
