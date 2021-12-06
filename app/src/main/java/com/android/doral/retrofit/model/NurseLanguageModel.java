package com.android.doral.retrofit.model;

public class NurseLanguageModel {
    String name;
    boolean minimal;
    boolean fluent;
    boolean read;
    boolean write;

    public NurseLanguageModel(String name, boolean minimal, boolean fluent, boolean read, boolean write) {
        this.name = name;
        this.minimal = minimal;
        this.fluent = fluent;
        this.read = read;
        this.write = write;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMinimal() {
        return minimal;
    }

    public void setMinimal(boolean minimal) {
        this.minimal = minimal;
    }

    public boolean isFluent() {
        return fluent;
    }

    public void setFluent(boolean fluent) {
        this.fluent = fluent;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isWrite() {
        return write;
    }

    public void setWrite(boolean write) {
        this.write = write;
    }
}
