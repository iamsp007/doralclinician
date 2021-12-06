package com.android.doral.retrofit.model;

public class StateLicense {

    String StateID,State,Number;

    public StateLicense() {
    }

    public StateLicense(String stateID, String State, String Number) {
        this.StateID = stateID;
        this.State = State;
        this.Number = Number;
    }

    public String getStateID() {
        return StateID;
    }

    public void setStateID(String stateID) {
        StateID = stateID;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }
}
