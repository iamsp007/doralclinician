package com.android.doral.socket;


public enum SocketEmitType {
    send_location("send-location");

    public String NAME;

    SocketEmitType(String name) {
        this.NAME = name;
    }
}
 