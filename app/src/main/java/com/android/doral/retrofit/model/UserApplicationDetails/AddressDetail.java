
package com.android.doral.retrofit.model.UserApplicationDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AddressDetail {

    @SerializedName("info")
    @Expose
    private Info info;
    @SerializedName("prior")
    @Expose
    private Prior prior;
    @SerializedName("address")
    @Expose
    private Address address;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

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

}
