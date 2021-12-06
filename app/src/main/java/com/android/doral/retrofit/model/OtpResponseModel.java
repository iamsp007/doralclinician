package com.android.doral.retrofit.model;

import java.io.Serializable;

public class OtpResponseModel extends BaseModel implements Serializable {
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
