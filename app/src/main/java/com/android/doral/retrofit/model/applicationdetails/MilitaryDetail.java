
package com.android.doral.retrofit.model.applicationdetails;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MilitaryDetail implements Serializable
{

    @SerializedName("isVietnam")
    @Expose
    private Boolean isVietnam;
    @SerializedName("isCommited")
    @Expose
    private Boolean isCommited;
    @SerializedName("isMilitary")
    @Expose
    private Boolean isMilitary;
    @SerializedName("isDisableVetran")
    @Expose
    private Boolean isDisableVetran;
    @SerializedName("isCommited_explain")
    @Expose
    private String isCommitedExplain;
    @SerializedName("isSpecialDisableVereran")
    @Expose
    private Boolean isSpecialDisableVereran;

    public Boolean getVietnam() {
        return isVietnam;
    }

    public void setVietnam(Boolean vietnam) {
        isVietnam = vietnam;
    }

    public Boolean getCommited() {
        return isCommited;
    }

    public void setCommited(Boolean commited) {
        isCommited = commited;
    }

    public Boolean getMilitary() {
        return isMilitary;
    }

    public void setMilitary(Boolean military) {
        isMilitary = military;
    }

    public Boolean getDisableVetran() {
        return isDisableVetran;
    }

    public void setDisableVetran(Boolean disableVetran) {
        isDisableVetran = disableVetran;
    }

    public Boolean getSpecialDisableVereran() {
        return isSpecialDisableVereran;
    }

    public void setSpecialDisableVereran(Boolean specialDisableVereran) {
        isSpecialDisableVereran = specialDisableVereran;
    }

    public String getServe_end_date() {
        return serve_end_date;
    }

    public void setServe_end_date(String serve_end_date) {
        this.serve_end_date = serve_end_date;
    }

    public String getServe_start_date() {
        return serve_start_date;
    }

    public void setServe_start_date(String serve_start_date) {
        this.serve_start_date = serve_start_date;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    @SerializedName("serve_end_date")
    @Expose
    private String serve_end_date;

    @SerializedName("serve_start_date")
    @Expose
    private String serve_start_date;


    @SerializedName("branch")
    @Expose
    private String branch;

    private final static long serialVersionUID = -2855968354019411381L;

    public Boolean getIsVietnam() {
        return isVietnam;
    }

    public void setIsVietnam(Boolean isVietnam) {
        this.isVietnam = isVietnam;
    }

    public Boolean getIsCommited() {
        return isCommited;
    }

    public void setIsCommited(Boolean isCommited) {
        this.isCommited = isCommited;
    }

    public Boolean getIsMilitary() {
        return isMilitary;
    }

    public void setIsMilitary(Boolean isMilitary) {
        this.isMilitary = isMilitary;
    }

    public Boolean getIsDisableVetran() {
        return isDisableVetran;
    }

    public void setIsDisableVetran(Boolean isDisableVetran) {
        this.isDisableVetran = isDisableVetran;
    }

    public String getIsCommitedExplain() {
        return isCommitedExplain;
    }

    public void setIsCommitedExplain(String isCommitedExplain) {
        this.isCommitedExplain = isCommitedExplain;
    }

    public Boolean getIsSpecialDisableVereran() {
        return isSpecialDisableVereran;
    }

    public void setIsSpecialDisableVereran(Boolean isSpecialDisableVereran) {
        this.isSpecialDisableVereran = isSpecialDisableVereran;
    }

}
