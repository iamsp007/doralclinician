package com.android.doral.retrofit.model;

import java.io.Serializable;

public class ParentIdModel extends BaseModel implements Serializable {
    private String parent_id;
    private ParentIdModel data;

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public ParentIdModel getData() {
        return data;
    }

    public void setData(ParentIdModel data) {
        this.data = data;
    }
}
