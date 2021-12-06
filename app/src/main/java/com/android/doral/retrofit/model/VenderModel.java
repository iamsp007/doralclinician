package com.android.doral.retrofit.model;

import java.io.Serializable;
import java.util.List;

public class VenderModel extends BaseModel implements Serializable {
    private List<VenderModel> data;
    private String id,guard_name,role_id,name,icon,color,test_name;
    private boolean isVisible,isChecked=false;

    private  String user_id,clincial_id,type_id,parent_id;
    private VenderModel check;
    public List<VenderModel> getData() {
        return data;
    }

    public void setData(List<VenderModel> data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGuard_name() {
        return guard_name;
    }

    public void setGuard_name(String guard_name) {
        this.guard_name = guard_name;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isChecked() {


        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getTest_name() {
        return test_name;
    }

    public void setTest_name(String test_name) {
        this.test_name = test_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getClincial_id() {
        return clincial_id;
    }

    public void setClincial_id(String clincial_id) {
        this.clincial_id = clincial_id;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public VenderModel getCheck() {
        return check;
    }

    public void setCheck(VenderModel check) {
        this.check = check;
    }
}
