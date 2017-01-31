package com.patient.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patient.dao.PatientDao;
import com.patient.entity.Patient;
import com.patient.request.PatientRequest;
import com.patient.service.PatientService;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    PatientDao patientDao;

    @Override
    public String addpatient(PatientRequest patientRequest) {
        // TODO Auto-generated method stub
        return patientDao.addpatient(patientRequest);
    }

    @Override
    public String deletepatient(PatientRequest patientRequest) {
        // TODO Auto-generated method stub
        return patientDao.deletepatient(patientRequest);
    }

    @Override
    public Patient getpatientByAdharNumber(String adharNumber) {
        // TODO Auto-generated method stub
        Patient patient = patientDao.getpatientByAdharNumber(adharNumber);
        return patient;
    }

    @Override
    public String updatepatient(PatientRequest patientRequest) {
        // TODO Auto-generated method stub
        return patientDao.updatepatient(patientRequest);
    }

    @Override
    public Patient getpatientByMobile(String mobile) {
        return patientDao.getpatientByMobileNumber(mobile);
    }

    @Override
    public Patient getpatientBypatientId(Integer custId) {
        return patientDao.getpatientById(custId);
    }

    @Override
    public Patient getpatientByEmail(String email) {
        return patientDao.getpatientByEmail(email);
    }

    @Override
    public List<Patient> getpatientByName(String name) {
        // TODO Auto-generated method stub
        return patientDao.getpatientByName(name);
    }

}
