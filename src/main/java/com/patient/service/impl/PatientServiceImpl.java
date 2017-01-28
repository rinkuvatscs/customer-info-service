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
    public String addCustomer(PatientRequest customerRequest) {
        // TODO Auto-generated method stub
        return patientDao.addCustomer(customerRequest);
    }

    @Override
    public String deleteCustomer(PatientRequest customerRequest) {
        // TODO Auto-generated method stub
        return patientDao.deleteCustomer(customerRequest);
    }

    @Override
    public Patient getCustomerByAdharNumber(String adharNumber) {
        // TODO Auto-generated method stub
        Patient customer = patientDao.getCustomerByAdharNumber(adharNumber);
        return customer;
    }

    @Override
    public String updateCustomer(PatientRequest customerRequest) {
        // TODO Auto-generated method stub
        return patientDao.updateCustomer(customerRequest);
    }

    @Override
    public Patient getCustomerByMobile(String mobile) {
        return patientDao.getCustomerByMobileNumber(mobile);
    }

    @Override
    public Patient getCustomerByCustomerId(Integer custId) {
        return patientDao.getCustomerById(custId);
    }

    @Override
    public Patient getCustomerByEmail(String email) {
        return patientDao.getCustomerByEmail(email);
    }

    @Override
    public List<Patient> getCustomerByName(String name) {
        // TODO Auto-generated method stub
        return patientDao.getCustomerByName(name);
    }

}
