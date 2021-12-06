
package com.android.doral.retrofit.model.applicationdetails;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Language implements Serializable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("read")
    @Expose
    private Boolean read;
    @SerializedName("write")
    @Expose
    private Boolean write;
    @SerializedName("fluent")
    @Expose
    private Boolean fluent;
    @SerializedName("minimal")
    @Expose
    private Boolean minimal;
    private final static long serialVersionUID = -5192280918707153362L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Boolean getWrite() {
        return write;
    }

    public void setWrite(Boolean write) {
        this.write = write;
    }

    public Boolean getFluent() {
        return fluent;
    }

    public void setFluent(Boolean fluent) {
        this.fluent = fluent;
    }

    public Boolean getMinimal() {
        return minimal;
    }

    public void setMinimal(Boolean minimal) {
        this.minimal = minimal;
    }

}
