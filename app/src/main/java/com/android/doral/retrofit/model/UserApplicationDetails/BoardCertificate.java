
package com.android.doral.retrofit.model.UserApplicationDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BoardCertificate {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("certificate")
    @Expose
    private String certificate;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

}
