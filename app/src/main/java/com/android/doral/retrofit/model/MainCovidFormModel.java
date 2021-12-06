package com.android.doral.retrofit.model;

public class MainCovidFormModel extends BaseModel {

    private String user_id,dose,patient_name,phone,form_filling_date,vaccine_name,vaccine_date;
    private CovidFormModel data;

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

    public CovidFormModel getData() {
        return data;
    }

    public void setData(CovidFormModel data) {
        this.data = data;
    }

    public String getTime() {
        return form_filling_date;
    }

    public void setTime(String time) {
        this.form_filling_date = time;
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
