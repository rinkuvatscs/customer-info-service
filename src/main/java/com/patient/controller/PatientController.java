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
@Api(basePath = "/patient", value = "patientmanagement", description = "Operations with Landlords", produces = "application/json")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "add new patient", notes = "add new patient")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Fields are with validation errors"),
            @ApiResponse(code = 201, message = "") })
    public PatientResponse addpatient(@RequestBody PatientRequest patientRequest) {

        if (!StringUtils.isEmpty(patientRequest)
                && !StringUtils.isEmpty(patientRequest.getPatientAadhaar())
                && !StringUtils.isEmpty(patientRequest.getPatientMobile())
                && !StringUtils.isEmpty(patientRequest.getPatientEmail())) {
            return new PatientResponse(
                    patientService.addpatient(patientRequest));
        } else {
            throw new BadRequestException(
                    "patient Aadhar Number,Mobile Number and Email Id should not be blank");
        }

    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, value = "/getpatientByAadhar/{adharNumber}")
    @ResponseBody
    public Patient getpatientByAdharNumber(@PathVariable String adharNumber) {
        if (!StringUtils.isEmpty(adharNumber)) {
            return patientService.getpatientByAdharNumber(adharNumber);
        } else {
            throw new BadRequestException(
                    "patient Aadhar Number should not be blank");
        }

    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value = "/getpatientByMobile/{mobile}")
    @ResponseBody
    public Patient getpatientByMobile(@PathVariable String mobile) {
        if (!StringUtils.isEmpty(mobile)) {
            return patientService.getpatientByMobile(mobile);
        } else {
            throw new BadRequestException(
                    "patient Mobile Number should not be blank");
        }

    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value = "/getpatientById/{custId}")
    @ResponseBody
    public Patient getpatientBypatientId(@PathVariable String custId) {
        if (!StringUtils.isEmpty(custId)) {
            Integer patientId = 0;
            try {
                patientId = Integer.parseInt(custId);
            } catch (NumberFormatException numberFormatException) {
                throw new BadRequestException(
                        "patient ID should not be Alpanumeric. It Should be Number only");
            }
            return patientService.getpatientBypatientId(patientId);
        } else {
            throw new BadRequestException("patient ID should not be blank");
        }

    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value = "/getpatientByEmail/{email}")
    @ResponseBody
    public Patient getpatientByEmail(@PathVariable String email) {
        if (!StringUtils.isEmpty(email)) {
            return patientService.getpatientByEmail(email);
        } else {
            throw new BadRequestException("patient Email should not be blank");
        }

    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value = "/getpatientByName/{name}")
    @ResponseBody
    public List<Patient> getpatientByName(@PathVariable String name) {
        if (!StringUtils.isEmpty(name)) {
            return patientService.getpatientByName(name);
        } else {
            throw new BadRequestException("patient Name should not be blank");
        }

    }

    @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/{custId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "delete patient", notes = "delete patient")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Fields are with validation errors"),
            @ApiResponse(code = 201, message = "") })
    public PatientResponse deletepatientById(@PathVariable Integer custId) {
        if (!StringUtils.isEmpty(custId)) {
            PatientRequest patientRequest = new PatientRequest();
            patientRequest.setPatientId(Integer.valueOf(custId));
            return new PatientResponse(
                    patientService.deletepatient(patientRequest));
        } else
            throw new BadRequestException(
                    "patientRequest Id should not be blank");
    }

    @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/deletepatientByAadhar/{custAdharNumber}")
    @ResponseBody
    public PatientResponse deletepatientByAdharNumber(
            @PathVariable String custAdharNumber) {
        if (!StringUtils.isEmpty(custAdharNumber)) {
            PatientRequest patientRequest = new PatientRequest();
            patientRequest.setPatientAadhaar(custAdharNumber);
            return new PatientResponse(
                    patientService.deletepatient(patientRequest));
        } else
            throw new BadRequestException(
                    "patient Aaddhar Number should not be blank");
    }

    @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/deletepatientByMobile/{custMobileNumber}")
    @ResponseBody
    public PatientResponse deletepatientByMobileNumber(
            @PathVariable String custMobileNumber) {
        if (!StringUtils.isEmpty(custMobileNumber)) {
            PatientRequest patientRequest = new PatientRequest();
            patientRequest.setPatientMobile(custMobileNumber);
            return new PatientResponse(
                    patientService.deletepatient(patientRequest));
        } else
            throw new BadRequestException(
                    "patient Mobile Number should not be blank");
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "update patient", notes = "update patient")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Fields are with validation errors"),
            @ApiResponse(code = 201, message = "") })
    public PatientResponse updatepatient(
            @RequestBody PatientRequest patientRequest) {

        return new PatientResponse(patientService.updatepatient(patientRequest));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String welcome() {
        return "Welcome to patient Management Tool";
    }

}
