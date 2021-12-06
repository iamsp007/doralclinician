
package com.android.doral.retrofit.model.UserApplicationDetails;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployerDetail {

    @SerializedName("employer")
    @Expose
    private List<Object> employer = null;
    @SerializedName("position")
    @Expose
    private Position position;

    public List<Object> getEmployer() {
        return employer;
    }

    public void setEmployer(List<Object> employer) {
        this.employer = employer;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

}
