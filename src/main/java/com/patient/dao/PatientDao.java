package com.patient.dao;

import java.util.List;

import com.patient.entity.Patient;
import com.patient.request.PatientRequest;

public interface PatientDao {
    public String addpatient(PatientRequest patient);

    public String deletepatient(PatientRequest patientRequest);

    public Patient getpatientByMobileNumber(String mobileNumber);

    public Patient getpatientByAdharNumber(String adharNumber);

    public Patient getpatientByEmail(String email);

    public List<Patient> getpatientByName(String name);

    public Patient getpatientById(Integer id);

    public String updatepatient(PatientRequest patientRequest);

}
