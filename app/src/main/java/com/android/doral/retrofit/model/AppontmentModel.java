package com.android.doral.retrofit.model;

import java.io.Serializable;
import java.util.List;

public class AppontmentModel  implements Serializable {
    private String id,title,book_datetime,start_datetime,end_datetime,booked_user_id,patient_id,provider1,provider2,service_id,Note,appointment_url,reason_id,reason_notes,cancel_user,status,created_at,updated_at;
    private UserModel booked_details,patients;
    private ServiceModel service;
    private MeetingModel meeting;
    private String parent_id;

    private List<AppontmentModel> data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBook_datetime() {
        return book_datetime;
    }

    public void setBook_datetime(String book_datetime) {
        this.book_datetime = book_datetime;
    }

    public String getStart_datetime() {
        return start_datetime;
    }

    public void setStart_datetime(String start_datetime) {
        this.start_datetime = start_datetime;
    }

    public String getEnd_datetime() {
        return end_datetime;
    }

    public void setEnd_datetime(String end_datetime) {
        this.end_datetime = end_datetime;
    }

    public String getBooked_user_id() {
        return booked_user_id;
    }

    public void setBooked_user_id(String booked_user_id) {
        this.booked_user_id = booked_user_id;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getProvider1() {
        return provider1;
    }

    public void setProvider1(String provider1) {
        this.provider1 = provider1;
    }

    public String getProvider2() {
        return provider2;
    }

    public void setProvider2(String provider2) {
        this.provider2 = provider2;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getAppointment_url() {
        return appointment_url;
    }

    public void setAppointment_url(String appointment_url) {
        this.appointment_url = appointment_url;
    }

    public String getReason_id() {
        return reason_id;
    }

    public void setReason_id(String reason_id) {
        this.reason_id = reason_id;
    }

    public String getReason_notes() {
        return reason_notes;
    }

    public void setReason_notes(String reason_notes) {
        this.reason_notes = reason_notes;
    }

    public String getCancel_user() {
        return cancel_user;
    }

    public void setCancel_user(String cancel_user) {
        this.cancel_user = cancel_user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public UserModel getBooked_details() {
        return booked_details;
    }

    public void setBooked_details(UserModel booked_details) {
        this.booked_details = booked_details;
    }

    public UserModel getPatients() {
        return patients;
    }

    public void setPatients(UserModel patients) {
        this.patients = patients;
    }

    public ServiceModel getService() {
        return service;
    }

    public void setService(ServiceModel service) {
        this.service = service;
    }

    public MeetingModel getMeeting() {
        return meeting;
    }

    public void setMeeting(MeetingModel meeting) {
        this.meeting = meeting;
    }

    public List<AppontmentModel> getData() {
        return data;
    }

    public void setData(List<AppontmentModel> data) {
        this.data = data;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }
}
