package com.patient.entity;


public class Patient {

    private Integer patientId;
    private String patientName;
    private String patientEmail;
    private String patientAadhaar;
    private String patientMobile;
    private String patientHomeAddress;
	public Integer getPatientId() {
		return patientId;
	}
	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getPatientEmail() {
		return patientEmail;
	}
	public void setPatientEmail(String patientEmail) {
		this.patientEmail = patientEmail;
	}
	public String getPatientAadhaar() {
		return patientAadhaar;
	}
	public void setPatientAadhaar(String patientAadhaar) {
		this.patientAadhaar = patientAadhaar;
	}
	public String getPatientMobile() {
		return patientMobile;
	}
	public void setPatientMobile(String patientMobile) {
		this.patientMobile = patientMobile;
	}
	public String getPatientHomeAddress() {
		return patientHomeAddress;
	}
	public void setPatientHomeAddress(String patientHomeAddress) {
		this.patientHomeAddress = patientHomeAddress;
	}

   
    @Override
    public String toString() {
        return "PatientRequest [patientName=" + patientName + ", patientEmail="
                + patientEmail + ", patientAadhaar=" + patientAadhaar + ", patientMobile="
                + patientMobile + ", patientHomeAddress=" + patientHomeAddress + "]";
    }
}
