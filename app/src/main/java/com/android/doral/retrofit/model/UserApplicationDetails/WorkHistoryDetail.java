
package com.android.doral.retrofit.model.UserApplicationDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkHistoryDetail {

    @SerializedName("list")
    @Expose
    private java.util.List<List> list = null;
    @SerializedName("gapReason")
    @Expose
    private String gapReason;

    public java.util.List<List> getList() {
        return list;
    }

    public void setList(java.util.List<List> list) {
        this.list = list;
    }

    public String getGapReason() {
        return gapReason;
    }

    public void setGapReason(String gapReason) {
        this.gapReason = gapReason;
    }

}
