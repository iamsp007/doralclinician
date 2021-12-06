
package com.android.doral.retrofit.model.UserApplicationDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Position {

    @SerializedName("date")
    @Expose
    private Object date;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("isCurrentEmployee")
    @Expose
    private boolean isCurrentEmployee;
    @SerializedName("isAllowContactToEmployer")
    @Expose
    private boolean isAllowContactToEmployer;

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isIsCurrentEmployee() {
        return isCurrentEmployee;
    }

    public void setIsCurrentEmployee(boolean isCurrentEmployee) {
        this.isCurrentEmployee = isCurrentEmployee;
    }

    public boolean isIsAllowContactToEmployer() {
        return isAllowContactToEmployer;
    }

    public void setIsAllowContactToEmployer(boolean isAllowContactToEmployer) {
        this.isAllowContactToEmployer = isAllowContactToEmployer;
    }

}
