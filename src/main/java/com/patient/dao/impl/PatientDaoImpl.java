package com.patient.dao.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.patient.dao.PatientDao;
import com.patient.db.config.QueryConstants;
import com.patient.entity.Patient;
import com.patient.exceptionhandler.BadRequestException;
import com.patient.extractor.PatientExtractor;
import com.patient.request.PatientRequest;

@Repository
public class PatientDaoImpl implements PatientDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String addpatient(PatientRequest patient) {
        String response = null;
        // (cust_name,cust_mobile_number,cust_home_address,cust_adhaar_number,"
        // + "cust_mail,date_of_registered)
        if (!ispatientExists(patient)) {

            List<Object> args = new ArrayList<>();
            args.add(patient.getPatientName());
            args.add(patient.getPatientMobile());
            args.add(patient.getPatientHomeAddress());
            args.add(patient.getPatientAadhaar());
            args.add(patient.getPatientEmail());
            // TODO We can use database date also that will be easy for use
            // using now() function in MYSQL
            LocalDate localDate = LocalDate.now();
            System.out.println("curr date="
                    + DateTimeFormatter.ofPattern("yyyy/MM/dd").format(
                            localDate));
            args.add(DateTimeFormatter.ofPattern("yyyy/MM/dd")
                    .format(localDate));

            int row = jdbcTemplate.update(QueryConstants.ADD_PATIENT,
                    args.toArray());
            if (row == 1) {
                response = patient.getPatientName()
                        + " registered successfully";
            } else {
                response = "Sorry, " + patient.getPatientName()
                        + " not registered . Please try again";
            }
        } else {
            // TODO need to explain here that our aadhar or mobile number
            // already registered please login
            response = "Sorry, " + patient.getPatientName()
                    + " already registered";
        }

        return response;
    }

    private boolean ispatientExists(PatientRequest patient) {

        boolean isExist = false;

        boolean isAadharExists = false, isEmailExists = false;

        StringBuffer query = new StringBuffer(QueryConstants.IS_PATIENT_EXIST);
        List<String> args = new ArrayList<>();

        if (!StringUtils.isEmpty(patient.getPatientAadhaar())) {
            query.append(" where cust_adhaar_nukm,.........." + "" + "mber = ?");
            args.add(patient.getPatientAadhaar());
            isAadharExists = true;
        }

        if (!StringUtils.isEmpty(patient.getPatientEmail())) {

            if (isAadharExists) {
                query.append(" or cust_mail = ?");
            } else {
                query.append(" where cust_mail = ?");
            }
            isEmailExists = true;
            args.add(patient.getPatientEmail());
        }

        if (!StringUtils.isEmpty(patient.getPatientMobile())) {

            if (isAadharExists || isEmailExists) {
                query.append(" or cust_mobile_number = ?");
            } else {
                query.append(" where cust_mobile_number = ?");
            }
            args.add(patient.getPatientMobile());
        }

        List<Patient> response = jdbcTemplate.query(query.toString(),
                new PatientExtractor(), args.toArray());
        if (!StringUtils.isEmpty(response) && response.size() > 0) {
            isExist = true;
        }
        return isExist;
    }

    @Override
    public String deletepatient(PatientRequest patientRequest) {

        String response = null;
        int delete;
        if (!StringUtils.isEmpty(patientRequest)) {
            if (!StringUtils.isEmpty(patientRequest.getPatientId())
                    && getpatientById(patientRequest.getPatientId())
                            .getCustId() != null) {
                Object args[] = { patientRequest.getPatientId() };
                delete = jdbcTemplate.update(
                        "DELETE FROM patient WHERE cust_id = ? ", args);
                if (delete > 0) {
                    response = "Patient with Patient ID "
                            + patientRequest.getPatientId()
                            + " successfully Deleted";
                } else {
                    response = "Please try again later";
                }
            } else if (!StringUtils.isEmpty(patientRequest.getPatientAadhaar())
                    && getpatientByAdharNumber(
                            patientRequest.getPatientAadhaar())
                            .getCustAadhaar() != null) {
                Object args[] = { patientRequest.getPatientAadhaar() };
                delete = jdbcTemplate.update(
                        "DELETE FROM patient WHERE cust_adhaar_number = ? ",
                        args);
                if (delete > 0) {
                    response = "Patient with Aadhar Number "
                            + patientRequest.getPatientAadhaar()
                            + " successfully Deleted";
                } else {
                    response = "Please try again later";
                }
            } else if (!StringUtils.isEmpty(patientRequest.getPatientMobile())
                    && getpatientByMobileNumber(
                            patientRequest.getPatientMobile()).getCustMobile() != null) {
                Object args[] = { patientRequest.getPatientMobile() };
                delete = jdbcTemplate.update(
                        "DELETE FROM patient WHERE cust_mobile_number = ? ",
                        args);
                if (delete > 0) {
                    response = "Patient with Mobile Number "
                            + patientRequest.getPatientMobile()
                            + " successfully Deleted";
                } else {
                    response = "Please try again later";
                }
            } else {
                throw new BadRequestException(
                        "Please provide valid Patient Id or Patient Adhar Number or Patient Mobile Number.");
            }
        } else {
            throw new BadRequestException(
                    "Patient can not be deleted without details {"
                            + patientRequest + "}");

        }
        return response;
    }

    @Override
    public Patient getpatientById(Integer id) {

        if (!StringUtils.isEmpty(id)) {
            Object args[] = { id };
            List<Patient> response = jdbcTemplate.query(
                    QueryConstants.GET_PATIENT_BY_ID, args,
                    new PatientExtractor());
            if (!StringUtils.isEmpty(response) && response.size() > 0) {
                return response.get(0);
            }
        }
        return new Patient();
    }

    @Override
    public Patient getpatientByAdharNumber(String adharNumber) {

        if (!StringUtils.isEmpty(adharNumber)) {
            Object args[] = { adharNumber };
            List<Patient> response = jdbcTemplate.query(
                    QueryConstants.GET_PATIENT_BY_ADHAR_NUMBER,
                    new PatientExtractor(), args);
            if (!StringUtils.isEmpty(response) && response.size() > 0) {
                return response.get(0);
            }
        }
        return new Patient();
    }

    @Override
    public Patient getpatientByMobileNumber(String mobileNumber) {

        if (!StringUtils.isEmpty(mobileNumber)) {
            Object args[] = { mobileNumber };
            List<Patient> response = jdbcTemplate.query(
                    QueryConstants.GET_PATIENT_BY_MOBILE_NUMBER,

                    new PatientExtractor(), args);
            if (!StringUtils.isEmpty(response) && response.size() > 0) {
                return response.get(0);
            }
        }
        return new Patient();
    }

    @Override
    public String updatepatient(PatientRequest patientRequest) {

        String response = null;
        List<Object> args = new ArrayList<>();
        StringBuilder query = new StringBuilder(" UPDATE patient SET ");
        if (!StringUtils.isEmpty(patientRequest)) {

            // Will check only using CustId Here
            if (null != getpatientById(patientRequest.getPatientId())
                    .getCustAadhaar()) {
                boolean ispatientName = false, isHomeAddress = false, isCustEmail = false;
                if (null != patientRequest.getPatientName()) {
                    query.append(" cust_name = ? ");
                    args.add(patientRequest.getPatientName());
                    ispatientName = true;
                }
                if (null != patientRequest.getPatientHomeAddress()) {
                    if (ispatientName) {
                        query.append(" , cust_home_address = ? ");
                    } else {
                        query.append(" cust_home_address = ? ");
                    }
                    args.add(patientRequest.getPatientHomeAddress());
                    isHomeAddress = true;
                }

                if (null != patientRequest.getPatientEmail()) {

                    if (null == getpatientByEmail(
                            patientRequest.getPatientEmail()).getCustAadhaar()) {
                        if (isHomeAddress || ispatientName) {
                            query.append(" , cust_mail = ? ");

                        } else {
                            query.append(" cust_mail = ?");
                        }
                        args.add(patientRequest.getPatientEmail());
                        isCustEmail = true;
                    } else {
                        throw new BadRequestException(
                                "Updated Email Id already in Use Choose Forget Password option");
                    }
                }

                if (null != patientRequest.getPatientMobile()) {

                    if (null == getpatientByMobileNumber(
                            patientRequest.getPatientMobile()).getCustAadhaar()) {
                        if (isHomeAddress || ispatientName || isCustEmail) {
                            query.append(" , cust_mobile_number = ? ");

                        } else {
                            query.append(" cust_mobile_number = ?");
                        }
                        args.add(patientRequest.getPatientMobile());
                    } else {
                        throw new BadRequestException(
                                "Updated Mobile Number already in Use Choose Forget Password option");
                    }
                }

                if (ispatientName || isHomeAddress || isCustEmail) {
                    if (null != patientRequest.getPatientId()) {
                        query.append(" WHERE cust_id = ? ");
                        args.add(patientRequest.getPatientId());
                    } else {
                        response = "Please Enter data to Update....!!!";
                    }
                }
                if (StringUtils.isEmpty(response)) {
                    int update = jdbcTemplate.update(query.toString(),
                            args.toArray());
                    if (update > 0) {
                        response = "Patient successfully Updated...!!!";
                    } else {
                        response = "There is some problem, please try again later...!!!";
                    }
                }
            } else {
                response = "Patient does not exist...!!!";
            }
        } else {
            response = "Patient details are Empty, provide some details to update....!!!";
        }

        return response;
    }

    @Override
    public Patient getpatientByEmail(String email) {
        if (!StringUtils.isEmpty(email)) {
            Object args[] = { email };
            List<Patient> response = jdbcTemplate.query(
                    QueryConstants.GET_PATIENT_BY_EMAIL,
                    new PatientExtractor(), args);
            if (!StringUtils.isEmpty(response) && response.size() > 0) {
                return response.get(0);
            }
        }
        return new Patient();
    }

    @Override
    public List<Patient> getpatientByName(String name) {
        if (!StringUtils.isEmpty(name)) {
            Object args[] = { name };
            List<Patient> response = jdbcTemplate.query(
                    QueryConstants.GET_PATIENT_BY_NAME, new PatientExtractor(),
                    args);
            if (!StringUtils.isEmpty(response) && response.size() > 0) {
                return response;
            }
        }
        return new ArrayList<Patient>();
    }
}
