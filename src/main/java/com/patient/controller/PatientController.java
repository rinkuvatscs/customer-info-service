package com.patient.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.patient.entity.Patient;
import com.patient.exceptionhandler.BadRequestException;
import com.patient.request.PatientRequest;
import com.patient.response.PatientResponse;
import com.patient.service.PatientService;

@RestController
@CrossOrigin
@RequestMapping("/patient")
@Api(basePath = "/patient", value = "customermanagement", description = "Operations with Landlords", produces = "application/json")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "add new customer", notes = "add new customer")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Fields are with validation errors"),
            @ApiResponse(code = 201, message = "") })
    public PatientResponse addCustomer(
            @RequestBody PatientRequest customerRequest) {

        if (!StringUtils.isEmpty(customerRequest)
                && !StringUtils.isEmpty(customerRequest.getPatientAadhaar())
                && !StringUtils.isEmpty(customerRequest.getPatientMobile())
                && !StringUtils.isEmpty(customerRequest.getPatientEmail())) {
            return new PatientResponse(
                    patientService.addCustomer(customerRequest));
        } else {
            throw new BadRequestException(
                    "Customer Aadhar Number,Mobile Number and Email Id should not be blank");
        }

    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, value = "/getCustomerByAadhar/{adharNumber}")
    @ResponseBody
    public Patient getCustomerByAdharNumber(@PathVariable String adharNumber) {
        if (!StringUtils.isEmpty(adharNumber)) {
            return patientService.getCustomerByAdharNumber(adharNumber);
        } else {
            throw new BadRequestException(
                    "Customer Aadhar Number should not be blank");
        }

    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value = "/getCustomerByMobile/{mobile}")
    @ResponseBody
    public Patient getCustomerByMobile(@PathVariable String mobile) {
        if (!StringUtils.isEmpty(mobile)) {
            return patientService.getCustomerByMobile(mobile);
        } else {
            throw new BadRequestException(
                    "Customer Mobile Number should not be blank");
        }

    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value = "/getCustomerById/{custId}")
    @ResponseBody
    public Patient getCustomerByCustomerId(@PathVariable String custId) {
        if (!StringUtils.isEmpty(custId)) {
            Integer customerId = 0;
            try {
                customerId = Integer.parseInt(custId);
            } catch (NumberFormatException numberFormatException) {
                throw new BadRequestException(
                        "Customer ID should not be Alpanumeric. It Should be Number only");
            }
            return patientService.getCustomerByCustomerId(customerId);
        } else {
            throw new BadRequestException("Customer ID should not be blank");
        }

    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value = "/getCustomerByEmail/{email}")
    @ResponseBody
    public Patient getCustomerByEmail(@PathVariable String email) {
        if (!StringUtils.isEmpty(email)) {
            return patientService.getCustomerByEmail(email);
        } else {
            throw new BadRequestException("Customer Email should not be blank");
        }

    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value = "/getCustomerByName/{name}")
    @ResponseBody
    public List<Patient> getCustomerByName(@PathVariable String name) {
        if (!StringUtils.isEmpty(name)) {
            return patientService.getCustomerByName(name);
        } else {
            throw new BadRequestException("Customer Name should not be blank");
        }

    }

    @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/{custId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "delete customer", notes = "delete customer")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Fields are with validation errors"),
            @ApiResponse(code = 201, message = "") })
    public PatientResponse deleteCustomerById(@PathVariable Integer custId) {
        if (!StringUtils.isEmpty(custId)) {
            PatientRequest customerRequest = new PatientRequest();
            customerRequest.setPatientId(Integer.valueOf(custId));
            return new PatientResponse(
                    patientService.deleteCustomer(customerRequest));
        } else
            throw new BadRequestException(
                    "customerRequest Id should not be blank");
    }

    @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/deleteCustomerByAadhar/{custAdharNumber}")
    @ResponseBody
    public PatientResponse deleteCustomerByAdharNumber(
            @PathVariable String custAdharNumber) {
        if (!StringUtils.isEmpty(custAdharNumber)) {
            PatientRequest customerRequest = new PatientRequest();
            customerRequest.setPatientAadhaar(custAdharNumber);
            return new PatientResponse(
                    patientService.deleteCustomer(customerRequest));
        } else
            throw new BadRequestException(
                    "Customer Aaddhar Number should not be blank");
    }

    @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/deleteCustomerByMobile/{custMobileNumber}")
    @ResponseBody
    public PatientResponse deleteCustomerByMobileNumber(
            @PathVariable String custMobileNumber) {
        if (!StringUtils.isEmpty(custMobileNumber)) {
            PatientRequest customerRequest = new PatientRequest();
            customerRequest.setPatientMobile(custMobileNumber);
            return new PatientResponse(
                    patientService.deleteCustomer(customerRequest));
        } else
            throw new BadRequestException(
                    "Customer Mobile Number should not be blank");
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "update customer", notes = "update customer")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Fields are with validation errors"),
            @ApiResponse(code = 201, message = "") })
    public PatientResponse updateCustomer(
            @RequestBody PatientRequest customerRequest) {

        return new PatientResponse(
                patientService.updateCustomer(customerRequest));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String welcome() {
        return "Welcome to Customer Management Tool";
    }

}
