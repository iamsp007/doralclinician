
package com.android.doral.retrofit.model.applicationdetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.android.doral.retrofit.model.CompanyHistoryModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class EmployerDetail implements Serializable
{

    @SerializedName("employer")
    @Expose
    private List<CompanyHistoryModel> employer = new ArrayList<CompanyHistoryModel>();
    @SerializedName("position")
    @Expose
    private Position position;
    private final static long serialVersionUID = 1612272523953398912L;

    public List<CompanyHistoryModel> getEmployer() {
        return employer;
    }

    public void setEmployer(List<CompanyHistoryModel> employer) {
        this.employer = employer;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

}
