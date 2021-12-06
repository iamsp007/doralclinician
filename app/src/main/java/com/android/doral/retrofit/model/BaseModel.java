package com.android.doral.retrofit.model;

import java.io.Serializable;


public class BaseModel implements Serializable {
    private String success;
    private String message;
    private  String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String isStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
