
package com.android.doral.retrofit.model.applicationdetails;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ReferenceDetail implements Serializable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phoneNo")
    @Expose
    private String phoneNo;
    @SerializedName("relation")
    @Expose
    private String relation;
    @SerializedName("personRelation")
    @Expose
    private String personRelation;
    private final static long serialVersionUID = -159791719809911410L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getPersonRelation() {
        return personRelation;
    }

    public void setPersonRelation(String personRelation) {
        this.personRelation = personRelation;
    }

}
