
package com.android.doral.retrofit.model.applicationdetails;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SecurityDetail implements Serializable
{

    @SerializedName("bond")
    @Expose
    private Boolean bond;

    public String getBond_explain() {
        return bond_explain;
    }

    public void setBond_explain(String bond_explain) {
        this.bond_explain = bond_explain;
    }

    @SerializedName("bond_explain")
    @Expose
    private String bond_explain;


    @SerializedName("convict")
    @Expose
    private Boolean convict;
    @SerializedName("convict_explain")
    @Expose
    private String convictExplain;
    private final static long serialVersionUID = 6878930003281847049L;

    public Boolean getBond() {
        return bond;
    }

    public void setBond(Boolean bond) {
        this.bond = bond;
    }

    public Boolean getConvict() {
        return convict;
    }

    public void setConvict(Boolean convict) {
        this.convict = convict;
    }

    public String getConvictExplain() {
        return convictExplain;
    }

    public void setConvictExplain(String convictExplain) {
        this.convictExplain = convictExplain;
    }

}
