
package com.android.doral.retrofit.model.applicationdetails;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Position implements Serializable
{

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("position")
    @Expose
    private String position;

    @SerializedName("isAllowContactToEmployer")
    @Expose
    private Boolean isAllowContactToEmployer;


    public Boolean getAllowContactToEmployer() {
        return isAllowContactToEmployer;
    }

    public void setAllowContactToEmployer(Boolean allowContactToEmployer) {
        isAllowContactToEmployer = allowContactToEmployer;
    }

    public Boolean getCurrentEmployee() {
        return isCurrentEmployee;
    }

    public void setCurrentEmployee(Boolean currentEmployee) {
        isCurrentEmployee = currentEmployee;
    }

    @SerializedName("isCurrentEmployee")
    @Expose
    private Boolean isCurrentEmployee;
    private final static long serialVersionUID = -2843077874863880113L;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }



}
