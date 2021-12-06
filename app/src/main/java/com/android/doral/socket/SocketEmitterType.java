package com.android.doral.socket;


public enum SocketEmitterType {
    receive_location("receive-location-");
    public String NAME;

    SocketEmitterType(String name) {
        this.NAME = name;
    }
}
