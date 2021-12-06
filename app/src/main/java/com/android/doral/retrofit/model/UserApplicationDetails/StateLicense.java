
package com.android.doral.retrofit.model.UserApplicationDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class StateLicense {

    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("Number")
    @Expose
    private String number;
    @SerializedName("StateID")
    @Expose
    private String stateID;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStateID() {
        return stateID;
    }

    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

}
