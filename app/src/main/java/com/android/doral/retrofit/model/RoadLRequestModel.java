package com.android.doral.retrofit.model;

import java.io.Serializable;

public class RoadLRequestModel implements Serializable {

    private  int icon;
    private String title;
    private boolean isVisible=false,isChecked=false;

    public RoadLRequestModel(int icon, String title, boolean isVisible, boolean isChecked) {
        this.icon = icon;
        this.title = title;
        this.isVisible = isVisible;
        this.isChecked = isChecked;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
