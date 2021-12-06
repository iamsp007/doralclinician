package com.android.doral.retrofit.model;

import java.io.Serializable;
import java.util.List;

public class RoadRequestModel extends BaseModel implements Serializable {


    private RoadRequestModel data;
    private List<UserModel> clinicians;
    private UserModel patient;

    public RoadRequestModel getData() {
        return data;
    }

    public void setData(RoadRequestModel data) {
        this.data = data;
    }

    public List<UserModel> getClinicians() {
        return clinicians;
    }

    public void setClinicians(List<UserModel> clinicians) {
        this.clinicians = clinicians;
    }

    public UserModel getPatient() {
        return patient;
    }

    public void setPatient(UserModel patient) {
        this.patient = patient;
    }
}
