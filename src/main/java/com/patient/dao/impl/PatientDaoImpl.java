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
    public String addCustomer(PatientRequest customer) {
        String response = null;
        // (cust_name,cust_mobile_number,cust_home_address,cust_adhaar_number,"
        // + "cust_mail,date_of_registered)
        if (!isCustomerExists(customer)) {

            List<Object> args = new ArrayList<>();
            args.add(customer.getCustName());
            args.add(customer.getCustMobile());
            args.add(customer.getCustHomeAddress());
            args.add(customer.getCustAadhaar());
            args.add(customer.getCustEmail());
            // TODO We can use database date also that will be easy for use
            // using now() function in MYSQL
            LocalDate localDate = LocalDate.now();
            System.out.println("curr date="
                    + DateTimeFormatter.ofPattern("yyyy/MM/dd").format(
                            localDate));
            args.add(DateTimeFormatter.ofPattern("yyyy/MM/dd")
                    .format(localDate));

            int row = jdbcTemplate.update(QueryConstants.ADD_CUSTOMER,
                    args.toArray());
            if (row == 1) {
                response = customer.getCustName() + " registered successfully";
            } else {
                response = "Sorry, " + customer.getCustName()
                        + " not registered . Please try again";
            }
        } else {
            // TODO need to explain here that our aadhar or mobile number
            // already registered please login
            response = "Sorry, " + customer.getCustName()
                    + " already registered";
        }

        return response;
    }

    private boolean isCustomerExists(PatientRequest customer) {

        boolean isExist = false;

        boolean isAadharExists = false, isEmailExists = false;

        StringBuffer query = new StringBuffer(QueryConstants.IS_CUSTOMER_EXIST);
        List<String> args = new ArrayList<>();

        if (!StringUtils.isEmpty(customer.getCustAadhaar())) {
            query.append(" where cust_adhaar_number = ?");
            args.add(customer.getCustAadhaar());
            isAadharExists = true;
        }

        if (!StringUtils.isEmpty(customer.getCustEmail())) {

            if (isAadharExists) {
                query.append(" or cust_mail = ?");
            } else {
                query.append(" where cust_mail = ?");
            }
            isEmailExists = true;
            args.add(customer.getCustEmail());
        }

        if (!StringUtils.isEmpty(customer.getCustMobile())) {

            if (isAadharExists || isEmailExists) {
                query.append(" or cust_mobile_number = ?");
            } else {
                query.append(" where cust_mobile_number = ?");
            }
            args.add(customer.getCustMobile());
        }

        List<Patient> response = jdbcTemplate.query(query.toString(),
                new PatientExtractor(), args.toArray());
        if (!StringUtils.isEmpty(response) && response.size() > 0) {
            isExist = true;
        }
        return isExist;
    }

    @Override
    public String deleteCustomer(PatientRequest customerRequest) {

        String response = null;
        int delete;
        if (!StringUtils.isEmpty(customerRequest)) {
            if (!StringUtils.isEmpty(customerRequest.getCustId())
                    && getCustomerById(customerRequest.getCustId()).getCustId() != null) {
                Object args[] = { customerRequest.getCustId() };
                delete = jdbcTemplate.update(
                        "DELETE FROM customer WHERE cust_id = ? ", args);
                if (delete > 0) {
                    response = "Customer with Customer ID "
                            + customerRequest.getCustId()
                            + " successfully Deleted";
                } else {
                    response = "Please try again later";
                }
            } else if (!StringUtils.isEmpty(customerRequest.getCustAadhaar())
                    && getCustomerByAdharNumber(
                            customerRequest.getCustAadhaar()).getCustAadhaar() != null) {
                Object args[] = { customerRequest.getCustAadhaar() };
                delete = jdbcTemplate.update(
                        "DELETE FROM customer WHERE cust_adhaar_number = ? ",
                        args);
                if (delete > 0) {
                    response = "Customer with Aadhar Number "
                            + customerRequest.getCustAadhaar()
                            + " successfully Deleted";
                } else {
                    response = "Please try again later";
                }
            } else if (!StringUtils.isEmpty(customerRequest.getCustMobile())
                    && getCustomerByMobileNumber(
                            customerRequest.getCustMobile()).getCustMobile() != null) {
                Object args[] = { customerRequest.getCustMobile() };
                delete = jdbcTemplate.update(
                        "DELETE FROM customer WHERE cust_mobile_number = ? ",
                        args);
                if (delete > 0) {
                    response = "Customer with Mobile Number "
                            + customerRequest.getCustMobile()
                            + " successfully Deleted";
                } else {
                    response = "Please try again later";
                }
            } else {
                throw new BadRequestException(
                        "Please provide valid Customer Id or Customer Adhar Number or Customer Mobile Number.");
            }
        } else {
            throw new BadRequestException(
                    "Customer can not be deleted without details {"
                            + customerRequest + "}");

        }
        return response;
    }

    @Override
    public Patient getCustomerById(Integer id) {

        if (!StringUtils.isEmpty(id)) {
            Object args[] = { id };
            List<Patient> response = jdbcTemplate.query(
                    QueryConstants.GET_CUSTOMER_BY_ID, args,
                    new PatientExtractor());
            if (!StringUtils.isEmpty(response) && response.size() > 0) {
                return response.get(0);
            }
        }
        return new Patient();
    }

    @Override
    public Patient getCustomerByAdharNumber(String adharNumber) {

        if (!StringUtils.isEmpty(adharNumber)) {
            Object args[] = { adharNumber };
            List<Patient> response = jdbcTemplate.query(
                    QueryConstants.GET_CUSTOMER_BY_ADHAR_NUMBER,
                    new PatientExtractor(), args);
            if (!StringUtils.isEmpty(response) && response.size() > 0) {
                return response.get(0);
            }
        }
        return new Patient();
    }

    @Override
    public Patient getCustomerByMobileNumber(String mobileNumber) {

        if (!StringUtils.isEmpty(mobileNumber)) {
            Object args[] = { mobileNumber };
            List<Patient> response = jdbcTemplate.query(
                    QueryConstants.GET_CUSTOMER_BY_MOBILE_NUMBER,

                    new PatientExtractor(), args);
            if (!StringUtils.isEmpty(response) && response.size() > 0) {
                return response.get(0);
            }
        }
        return new Patient();
    }

    @Override
    public String updateCustomer(PatientRequest customerRequest) {

        String response = null;
        List<Object> args = new ArrayList<>();
        StringBuilder query = new StringBuilder(" UPDATE customer SET ");
        if (!StringUtils.isEmpty(customerRequest)) {

            // Will check only using CustId Here
            if (null != getCustomerById(customerRequest.getCustId())
                    .getCustAadhaar()) {
                boolean isCustomerName = false, isHomeAddress = false, isCustEmail = false;
                if (null != customerRequest.getCustName()) {
                    query.append(" cust_name = ? ");
                    args.add(customerRequest.getCustName());
                    isCustomerName = true;
                }
                if (null != customerRequest.getCustHomeAddress()) {
                    if (isCustomerName) {
                        query.append(" , cust_home_address = ? ");
                    } else {
                        query.append(" cust_home_address = ? ");
                    }
                    args.add(customerRequest.getCustHomeAddress());
                    isHomeAddress = true;
                }

                if (null != customerRequest.getCustEmail()) {

                    if (null == getCustomerByEmail(
                            customerRequest.getCustEmail()).getCustAadhaar()) {
                        if (isHomeAddress || isCustomerName) {
                            query.append(" , cust_mail = ? ");

                        } else {
                            query.append(" cust_mail = ?");
                        }
                        args.add(customerRequest.getCustEmail());
                        isCustEmail = true;
                    } else {
                        throw new BadRequestException(
                                "Updated Email Id already in Use Choose Forget Password option");
                    }
                }

                if (null != customerRequest.getCustMobile()) {

                    if (null == getCustomerByMobileNumber(
                            customerRequest.getCustMobile()).getCustAadhaar()) {
                        if (isHomeAddress || isCustomerName || isCustEmail) {
                            query.append(" , cust_mobile_number = ? ");

                        } else {
                            query.append(" cust_mobile_number = ?");
                        }
                        args.add(customerRequest.getCustMobile());
                    } else {
                        throw new BadRequestException(
                                "Updated Mobile Number already in Use Choose Forget Password option");
                    }
                }

                if (isCustomerName || isHomeAddress || isCustEmail) {
                    if (null != customerRequest.getCustId()) {
                        query.append(" WHERE cust_id = ? ");
                        args.add(customerRequest.getCustId());
                    } else {
                        response = "Please Enter data to Update....!!!";
                    }
                }
                if (StringUtils.isEmpty(response)) {
                    int update = jdbcTemplate.update(query.toString(),
                            args.toArray());
                    if (update > 0) {
                        response = "Customer successfully Updated...!!!";
                    } else {
                        response = "There is some problem, please try again later...!!!";
                    }
                }
            } else {
                response = "Customer does not exist...!!!";
            }
        } else {
            response = "Customer details are Empty, provide some details to update....!!!";
        }

        return response;
    }

    @Override
    public Patient getCustomerByEmail(String email) {
        if (!StringUtils.isEmpty(email)) {
            Object args[] = { email };
            List<Patient> response = jdbcTemplate.query(
                    QueryConstants.GET_CUSTOMER_BY_EMAIL,
                    new PatientExtractor(), args);
            if (!StringUtils.isEmpty(response) && response.size() > 0) {
                return response.get(0);
            }
        }
        return new Patient();
    }

    @Override
    public List<Patient> getCustomerByName(String name) {
        if (!StringUtils.isEmpty(name)) {
            Object args[] = { name };
            List<Patient> response = jdbcTemplate.query(
                    QueryConstants.GET_CUSTOMER_BY_NAME,
                    new PatientExtractor(), args);
            if (!StringUtils.isEmpty(response) && response.size() > 0) {
                return response;
            }
        }
        return new ArrayList<Patient>();
    }
}
