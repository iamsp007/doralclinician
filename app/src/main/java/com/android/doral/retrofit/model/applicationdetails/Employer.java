
package com.android.doral.retrofit.model.applicationdetails;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Employer implements Serializable
{

    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phoneNo")
    @Expose
    private String phoneNo;
    @SerializedName("supervisor")
    @Expose
    private String supervisor;
    @SerializedName("companyName")
    @Expose
    private String companyName;
    private final static long serialVersionUID = 3658073910293994331L;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
