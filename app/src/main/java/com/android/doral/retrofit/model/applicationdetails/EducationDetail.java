
package com.android.doral.retrofit.model.applicationdetails;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class EducationDetail implements Serializable
{

    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("Degree")
    @Expose
    private String degree;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("isGraduate")
    @Expose
    private String isGraduate;
    private final static long serialVersionUID = -5163889471039158765L;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIsGraduate() {
        return isGraduate;
    }

    public void setIsGraduate(String isGraduate) {
        this.isGraduate = isGraduate;
    }

}
