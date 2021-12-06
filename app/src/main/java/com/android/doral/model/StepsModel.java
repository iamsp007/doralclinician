package com.android.doral.model;

public class StepsModel {

    String html_instructions="",km="",time="";

    public StepsModel(String html_instructions, String km, String time) {
        this.html_instructions = html_instructions;
        this.km = km;
        this.time = time;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public StepsModel(String html_instructions) {
        this.html_instructions = html_instructions;
    }

    public String getHtml_instructions() {
        return html_instructions;
    }

    public void setHtml_instructions(String html_instructions) {
        this.html_instructions = html_instructions;
    }
}
