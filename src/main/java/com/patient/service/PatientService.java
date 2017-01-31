package com.patient.service;

import java.util.List;

import com.patient.entity.Patient;
import com.patient.request.PatientRequest;

public interface PatientService {
    public String addpatient(PatientRequest patientRequest);

    public String deletepatient(PatientRequest patientRequest);

    public Patient getpatientByAdharNumber(String adharNumber);

    public Patient getpatientByMobile(String mobile);

    public Patient getpatientByEmail(String email);

    public List<Patient> getpatientByName(String name);

    public Patient getpatientBypatientId(Integer custId);

    public String updatepatient(PatientRequest patientRequest);

}
