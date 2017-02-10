package com.patient.request;


public class PatientRequest {

    private Integer patientId;
    private String patientName;
    private String patientEmail;
    private String patientAadhaar;
    private String patientMobile;
    private String patientHomeAddress;
    private Integer age;
    private String gender;
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
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setPatientHomeAddress(String patientHomeAddress) {
		this.patientHomeAddress = patientHomeAddress;
	}
	@Override
	public String toString() {
		return "PatientRequest [patientId=" + patientId + ", patientName=" + patientName + ", patientEmail="
				+ patientEmail + ", patientAadhaar=" + patientAadhaar + ", patientMobile=" + patientMobile
				+ ", patientHomeAddress=" + patientHomeAddress + ", age=" + age + ", gender=" + gender + "]";
	}

   
   
}
