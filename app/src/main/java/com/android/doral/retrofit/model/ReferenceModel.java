package com.android.doral.retrofit.model;

public class ReferenceModel {
    private String reference_name,reference_address,reference_phone,reference_relationship;

    public ReferenceModel(String referance_name, String reference_address, String reference_phone, String reference_relationship) {
        this.reference_name = referance_name;
        this.reference_address = reference_address;
        this.reference_phone = reference_phone;
        this.reference_relationship = reference_relationship;
    }

    public String getReferance_name() {
        return reference_name;
    }

    public void setReferance_name(String referance_name) {
        this.reference_name = referance_name;
    }

    public String getReference_address() {
        return reference_address;
    }

    public void setReference_address(String reference_address) {
        this.reference_address = reference_address;
    }

    public String getReference_phone() {
        return reference_phone;
    }

    public void setReference_phone(String reference_phone) {
        this.reference_phone = reference_phone;
    }

    public String getReference_relationship() {
        return reference_relationship;
    }

    public void setReference_relationship(String reference_relationship) {
        this.reference_relationship = reference_relationship;
    }
}
