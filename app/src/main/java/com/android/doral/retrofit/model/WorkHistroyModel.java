package com.android.doral.retrofit.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WorkHistroyModel implements Serializable {
    List<CompanyModel> work_history=new ArrayList<>();

    public List<CompanyModel> getWork_history() {
        return work_history;
    }

    public void setWork_history(List<CompanyModel> work_history) {
        this.work_history = work_history;
    }
}
