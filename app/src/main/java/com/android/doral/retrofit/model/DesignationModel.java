package com.android.doral.retrofit.model;

import java.io.Serializable;
import java.util.List;

public class DesignationModel extends BaseModel implements Serializable {
    private String id, name, role_id;
    private DesignationModel data;
    private List<DesignationModel> designation;

    public DesignationModel(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public DesignationModel getData() {
        return data;
    }

    public void setData(DesignationModel data) {
        this.data = data;
    }

    public List<DesignationModel> getDesignation() {
        return designation;
    }

    public void setDesignation(List<DesignationModel> designation) {
        this.designation = designation;
    }

    @Override
    public String toString() {
        return name;
    }
}
