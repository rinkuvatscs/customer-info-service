package com.patient.request;


public class PatientRequest {

    private Integer custId;
    private String custName;
    private String custEmail;
    private String custAadhaar;
    private String custMobile;
    private String custHomeAddress;

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public String getCustAadhaar() {
        return custAadhaar;
    }

    public void setCustAadhaar(String custAadhaar) {
        this.custAadhaar = custAadhaar;
    }

    public String getCustMobile() {
        return custMobile;
    }

    public void setCustMobile(String custMobile) {
        this.custMobile = custMobile;
    }

    public String getCustHomeAddress() {
        return custHomeAddress;
    }

    public void setCustHomeAddress(String custHomeAddress) {
        this.custHomeAddress = custHomeAddress;
    }

    @Override
    public String toString() {
        return "CustomerRequest [custName=" + custName + ", custEmail="
                + custEmail + ", custAadhaar=" + custAadhaar + ", custMobile="
                + custMobile + ", custHomeAddress=" + custHomeAddress + "]";
    }
}
