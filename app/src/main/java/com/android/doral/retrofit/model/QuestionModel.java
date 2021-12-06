package com.android.doral.retrofit.model;

import java.io.Serializable;

public class QuestionModel implements Serializable {
    private String question, ans, date,vaccine ;

    public QuestionModel(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReceived_vaccine() {
        return vaccine ;
    }

    public void setReceived_vaccine(String received_vaccine) {
        this.vaccine  = received_vaccine;
    }
}
