package com.android.doral.retrofit.model;

import java.util.List;

public class ReasonModel extends BaseModel {
    private String id,reason;
    private boolean isSelect;

    private ReasonModel data;
    private List<ReasonModel> reasons;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ReasonModel getData() {
        return data;
    }

    public void setData(ReasonModel data) {
        this.data = data;
    }

    public List<ReasonModel> getReasons() {
        return reasons;
    }

    public void setReasons(List<ReasonModel> reasons) {
        this.reasons = reasons;
    }
}
