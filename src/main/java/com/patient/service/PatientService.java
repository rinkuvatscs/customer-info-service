package com.patient.service;

import java.util.List;

import com.patient.entity.Patient;
import com.patient.request.PatientRequest;

public interface PatientService {
    public String addCustomer(PatientRequest customerRequest);

    public String deleteCustomer(PatientRequest customerRequest);

    public Patient getCustomerByAdharNumber(String adharNumber);

    public Patient getCustomerByMobile(String mobile);

    public Patient getCustomerByEmail(String email);

    public List<Patient> getCustomerByName(String name);

    public Patient getCustomerByCustomerId(Integer custId);

    public String updateCustomer(PatientRequest customerRequest);

}
