package com.android.doral.retrofit.model;

import java.io.Serializable;
import java.util.List;

public class FormModel extends BaseModel implements Serializable {
    private String id,user_id,dose,patient_name,phone,vaccine_name,vaccine_date,form_filling_date,recipient_signature,interpreter_signature,vaccination_signature;
    private List<FormModel> data;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<FormModel> getData() {
        return data;
    }

    public void setData(List<FormModel> data) {
        this.data = data;
    }

    public String getTime_date() {
        return form_filling_date;
    }

    public void setTime_date(String time_date) {
        this.form_filling_date = time_date;
    }

    public String getRecipient_signature() {
        return recipient_signature;
    }

    public void setRecipient_signature(String recipient_signature) {
        this.recipient_signature = recipient_signature;
    }

    public String getInterpreter_signature() {
        return interpreter_signature;
    }

    public void setInterpreter_signature(String interpreter_signature) {
        this.interpreter_signature = interpreter_signature;
    }

    public String getVaccination_signature() {
        return vaccination_signature;
    }

    public void setVaccination_signature(String vaccination_signature) {
        this.vaccination_signature = vaccination_signature;
    }

    public String getVaccine_name() {
        return vaccine_name;
    }

    public void setVaccine_name(String vaccine_name) {
        this.vaccine_name = vaccine_name;
    }

    public String getVaccine_date() {
        return vaccine_date;
    }

    public void setVaccine_date(String vaccine_date) {
        this.vaccine_date = vaccine_date;
    }
}
