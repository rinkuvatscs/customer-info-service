/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.patient.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.patient.entity.Patient;

/**
 *
 * @author Preeti
 */
public class PatientExtractor implements ResultSetExtractor<List<Patient>> {

    List<Patient> patientList = new ArrayList<>();

    @Override
    public List<Patient> extractData(ResultSet rs) throws SQLException,
            DataAccessException {
        Patient patient;
        // cust_id int(11) NOT NULL auto_increment, cust_name varchar(45)
        // default NULL, cust_mobile_number varchar(10) default NULL,
        // cust_home_address varchar(255) default NULL, cust_adhaar_number
        // varchar(20) default NULL, cust_mail varchar(4) default NULL,
        // date_of_registered varchar(20) default NULL, status varchar(30)
        // default 'Activate', PRIMARY KEY (cust_id) ) ENGINE=InnoDB DEFAULT
        // CHARSET=latin1;
        while (rs.next()) {
            patient = new Patient();
            patient.setCustId(rs.getInt("patient_id"));
            patient.setCustAadhaar(rs.getString("patient_adhaar_number"));
            patient.setCustHomeAddress(rs.getString("patient_home_address"));
            patient.setCustName(rs.getString("patient_name"));
            patient.setCustMobile(rs.getString("patient_mobile_number"));
            patient.setCustEmail(rs.getString("patient_mail"));
            patientList.add(patient);
        }
        return patientList;
    }

}
