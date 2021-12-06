
package com.android.doral.retrofit.model.applicationdetails;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AddressDetail implements Serializable
{

    @SerializedName("prior")
    @Expose
    private Prior prior;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("info")
    @Expose
    private Info info;
    private final static long serialVersionUID = -136780651004302539L;

    public Prior getPrior() {
        return prior;
    }

    public void setPrior(Prior prior) {
        this.prior = prior;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
