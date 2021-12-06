package com.android.doral.retrofit.model;

import java.io.Serializable;
import java.util.List;

public class CovidFormModel implements Serializable {


    private String id;
    private String recipient_name, preferred_name, dob, marital_staus, gender_id, ethnicity, sex_at_birth;
    private String preferred_language, address, state, city, zip;
    private String email, phone, parent_name, race;

    private String primary_insurance_name, primary_insurance_id, primary_subscriber_dob, primary_relation_patient, primary_insurance_address, primary_insurance_group, primary_insurance_phone;
    private String secondary_insurance_name, secondary_insurance_id, secondary_subscriber_dob, secondary_relation_patient, secondary_insurance_address, secondary_insurance_group, secondary_insurance_phone;

    private String clinic_site, primary_care;
    private List<QuestionModel> questionnaire;

    private boolean is_use_authorization;
    private boolean is_emergency_content;

    private String recipient_signature_datetime, recipient_relation, print_name, telephonic_interpreter_id, telephonic_interpreter_datetime;
    private String interpreter_signature_datetime, interpreter_relation;

    private String vaccine_name, vaccine_type, vaccine_date, fact_dose_date, manufacture_lot_number;


    private String administration_site, dosage;
    private boolean is_accept_term;

    public String getRecipient_name() {
        return recipient_name;
    }

    public void setRecipient_name(String recipient_name) {
        this.recipient_name = recipient_name;
    }

    public String getPreferred_name() {
        return preferred_name;
    }

    public void setPreferred_name(String preferred_name) {
        this.preferred_name = preferred_name;
    }

    public String getDOB() {
        return dob;
    }

    public void setDOB(String DOB) {
        this.dob = DOB;
    }

    public String getMarital_staus() {
        return marital_staus;
    }

    public void setMarital_staus(String marital_staus) {
        this.marital_staus = marital_staus;
    }

    public String getGender_id() {
        return gender_id;
    }

    public void setGender_id(String gender_id) {
        this.gender_id = gender_id;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getPreferred_language() {
        return preferred_language;
    }

    public void setPreferred_language(String preferred_language) {
        this.preferred_language = preferred_language;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getPrimary_insurance_name() {
        return primary_insurance_name;
    }

    public void setPrimary_insurance_name(String primary_insurance_name) {
        this.primary_insurance_name = primary_insurance_name;
    }

    public String getPrimary_insurance_id() {
        return primary_insurance_id;
    }

    public void setPrimary_insurance_id(String primary_insurance_id) {
        this.primary_insurance_id = primary_insurance_id;
    }

    public String getPrimary_subscriber_dob() {
        return primary_subscriber_dob;
    }

    public void setPrimary_subscriber_dob(String primary_subscriber_dob) {
        this.primary_subscriber_dob = primary_subscriber_dob;
    }

    public String getPrimary_relation_patient() {
        return primary_relation_patient;
    }

    public void setPrimary_relation_patient(String primary_relation_patient) {
        this.primary_relation_patient = primary_relation_patient;
    }

    public String getPrimary_insurance_address() {
        return primary_insurance_address;
    }

    public void setPrimary_insurance_address(String primary_insurance_address) {
        this.primary_insurance_address = primary_insurance_address;
    }

    public String getPrimary_insurance_group() {
        return primary_insurance_group;
    }

    public void setPrimary_insurance_group(String primary_insurance_group) {
        this.primary_insurance_group = primary_insurance_group;
    }

    public String getPrimary_insurance_phone() {
        return primary_insurance_phone;
    }

    public void setPrimary_insurance_phone(String primary_insurance_phone) {
        this.primary_insurance_phone = primary_insurance_phone;
    }

    public String getSecondary_insurance_name() {
        return secondary_insurance_name;
    }

    public void setSecondary_insurance_name(String secondary_insurance_name) {
        this.secondary_insurance_name = secondary_insurance_name;
    }

    public String getSecondary_insurance_id() {
        return secondary_insurance_id;
    }

    public void setSecondary_insurance_id(String secondary_insurance_id) {
        this.secondary_insurance_id = secondary_insurance_id;
    }

    public String getSecondary_subscriber_dob() {
        return secondary_subscriber_dob;
    }

    public void setSecondary_subscriber_dob(String secondary_subscriber_dob) {
        this.secondary_subscriber_dob = secondary_subscriber_dob;
    }

    public String getSecondary_relation_patient() {
        return secondary_relation_patient;
    }

    public void setSecondary_relation_patient(String secondary_relation_patient) {
        this.secondary_relation_patient = secondary_relation_patient;
    }

    public String getSecondary_insurance_address() {
        return secondary_insurance_address;
    }

    public void setSecondary_insurance_address(String secondary_insurance_address) {
        this.secondary_insurance_address = secondary_insurance_address;
    }

    public String getSecondary_insurance_group() {
        return secondary_insurance_group;
    }

    public void setSecondary_insurance_group(String secondary_insurance_group) {
        this.secondary_insurance_group = secondary_insurance_group;
    }

    public String getSecondary_insurance_phone() {
        return secondary_insurance_phone;
    }

    public void setSecondary_insurance_phone(String secondary_insurance_phone) {
        this.secondary_insurance_phone = secondary_insurance_phone;
    }

    public String getClinicOffice_site() {
        return clinic_site;
    }

    public void setClinicOffice_site(String clinicOffice_site) {
        this.clinic_site = clinicOffice_site;
    }

    public String getPrimary_care() {
        return primary_care;
    }

    public void setPrimary_care(String primary_care) {
        this.primary_care = primary_care;
    }

    public List<QuestionModel> getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(List<QuestionModel> questionnaire) {
        this.questionnaire = questionnaire;
    }

    public boolean isIs_use_authorization() {
        return is_use_authorization;
    }

    public void setIs_use_authorization(boolean is_use_authorization) {
        this.is_use_authorization = is_use_authorization;
    }

    public boolean isIs_emergency_content() {
        return is_emergency_content;
    }

    public void setIs_emergency_content(boolean is_emergency_content) {
        this.is_emergency_content = is_emergency_content;
    }

    public String getRecipient_signature_datetime() {
        return recipient_signature_datetime;
    }

    public void setRecipient_signature_datetime(String recipient_signature_datetime) {
        this.recipient_signature_datetime = recipient_signature_datetime;
    }

    public String getRecipient_relation() {
        return recipient_relation;
    }

    public void setRecipient_relation(String recipient_relation) {
        this.recipient_relation = recipient_relation;
    }

    public String getPrint_name() {
        return print_name;
    }

    public void setPrint_name(String print_name) {
        this.print_name = print_name;
    }

    public String getTelephonic_interpreter_id() {
        return telephonic_interpreter_id;
    }

    public void setTelephonic_interpreter_id(String telephonic_interpreter_id) {
        this.telephonic_interpreter_id = telephonic_interpreter_id;
    }

    public String getTelephonic_interpreter_datetime() {
        return telephonic_interpreter_datetime;
    }

    public void setTelephonic_interpreter_datetime(String telephonic_interpreter_datetime) {
        this.telephonic_interpreter_datetime = telephonic_interpreter_datetime;
    }

    public String getInterpreter_signature_datetime() {
        return interpreter_signature_datetime;
    }

    public void setInterpreter_signature_datetime(String interpreter_signature_datetime) {
        this.interpreter_signature_datetime = interpreter_signature_datetime;
    }

    public String getInterpreter_relation() {
        return interpreter_relation;
    }

    public void setInterpreter_relation(String interpreter_relation) {
        this.interpreter_relation = interpreter_relation;
    }

    public String getVaccine_name() {
        return vaccine_name;
    }

    public void setVaccine_name(String vaccine_name) {
        this.vaccine_name = vaccine_name;
    }

    public String getVaccine_type() {
        return vaccine_type;
    }

    public void setVaccine_type(String vaccine_type) {
        this.vaccine_type = vaccine_type;
    }

    public String getVaccine_date() {
        return vaccine_date;
    }

    public void setVaccine_date(String vaccine_date) {
        this.vaccine_date = vaccine_date;
    }

    public String getFact_dose_date() {
        return fact_dose_date;
    }

    public void setFact_dose_date(String fact_dose_date) {
        this.fact_dose_date = fact_dose_date;
    }

    public String getManufacture_lot_number() {
        return manufacture_lot_number;
    }

    public void setManufacture_lot_number(String manufacture_lot_number) {
        this.manufacture_lot_number = manufacture_lot_number;
    }

    public String getAdministration_site() {
        return administration_site;
    }

    public void setAdministration_site(String administration_site) {
        this.administration_site = administration_site;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public boolean isIs_accept_term() {
        return is_accept_term;
    }

    public void setIs_accept_term(boolean is_accept_term) {
        this.is_accept_term = is_accept_term;
    }

    public String getSex_at_birth() {
        return sex_at_birth;
    }

    public void setSex_at_birth(String sex_at_birth) {
        this.sex_at_birth = sex_at_birth;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
