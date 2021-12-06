
package com.android.doral.retrofit.model.applicationdetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.android.doral.retrofit.model.NurseLanguageModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LanguageDetail implements Serializable
{

    @SerializedName("skill")
    @Expose
    private String skill;
    @SerializedName("language")
    @Expose
    private List<NurseLanguageModel> language = new ArrayList<NurseLanguageModel>();
    private final static long serialVersionUID = 5752740233520902165L;

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public List<NurseLanguageModel> getLanguage() {
        return language;
    }

    public void setLanguage(List<NurseLanguageModel> language) {
        this.language = language;
    }

}
