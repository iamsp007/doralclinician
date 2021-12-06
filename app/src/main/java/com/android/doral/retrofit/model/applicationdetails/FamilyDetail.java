
package com.android.doral.retrofit.model.applicationdetails;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class FamilyDetail implements Serializable
{

    @SerializedName("father_name")
    @Expose
    private String fatherName;
    @SerializedName("mother_name")
    @Expose
    private String motherName;
    @SerializedName("spouse_name")
    @Expose
    private String spouseName;
    @SerializedName("parents_name")
    @Expose
    private String parentsName;
    @SerializedName("children_name")
    @Expose
    private String childrenName;
    @SerializedName("siblings_name")
    @Expose
    private String siblingsName;
    @SerializedName("father_in_low_name")
    @Expose
    private String fatherInLowName;
    @SerializedName("mother_in_low_name")
    @Expose
    private String motherInLowName;
    private final static long serialVersionUID = 1985294049686524671L;

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public String getParentsName() {
        return parentsName;
    }

    public void setParentsName(String parentsName) {
        this.parentsName = parentsName;
    }

    public String getChildrenName() {
        return childrenName;
    }

    public void setChildrenName(String childrenName) {
        this.childrenName = childrenName;
    }

    public String getSiblingsName() {
        return siblingsName;
    }

    public void setSiblingsName(String siblingsName) {
        this.siblingsName = siblingsName;
    }

    public String getFatherInLowName() {
        return fatherInLowName;
    }

    public void setFatherInLowName(String fatherInLowName) {
        this.fatherInLowName = fatherInLowName;
    }

    public String getMotherInLowName() {
        return motherInLowName;
    }

    public void setMotherInLowName(String motherInLowName) {
        this.motherInLowName = motherInLowName;
    }

}
