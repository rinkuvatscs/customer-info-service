package com.customer.controller;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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

import com.customer.entity.Customer;
import com.customer.exceptionhandler.BadRequestException;
import com.customer.request.CustomerRequest;
import com.customer.response.CustomerResponse;
import com.customer.service.CustomerService;

@RestController
@CrossOrigin
@RequestMapping("/customer")
@Api(basePath = "/customer", value = "customermanagement", description = "Operations with Landlords", produces = "application/json")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "add new customer", notes = "add new customer")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Fields are with validation errors"),
			@ApiResponse(code = 201, message = "") })
	public CustomerResponse addCustomer(
			@RequestBody CustomerRequest customerRequest) {

		if (!StringUtils.isEmpty(customerRequest)
				&& !StringUtils.isEmpty(customerRequest.getCustAadhaar())
				&& !StringUtils.isEmpty(customerRequest.getCustMobile())) {
			return new CustomerResponse(
					customerService.addCustomer(customerRequest));
		} else {
			throw new BadRequestException(
					"Customer Aaddhar Number and Mobile Number should not be blank");
		}

	}

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, value = "/getCustomerByAadhar/{adharNumber}")
	@ResponseBody
	public Customer getCustomerByAdharNumber(@PathVariable String adharNumber) {
		if (!StringUtils.isEmpty(adharNumber)) {
			return customerService.getCustomerByAdharNumber(adharNumber);
		} else {
			throw new BadRequestException(
					"Customer Aadhar Number should not be blank");
		}

	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value = "/getCustomerByMobile/{mobile}")
	@ResponseBody
	public Customer getCustomerByMobile(@PathVariable String mobile) {
		if (!StringUtils.isEmpty(mobile)) {
			return customerService.getCustomerByMobile(mobile);
		} else {
			throw new BadRequestException(
					"Customer Mobile Number should not be blank");
		}

	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value = "/getCustomerById/{custId}")
	@ResponseBody
	public Customer getCustomerByCustomerId(@PathVariable String custId) {
		if (!StringUtils.isEmpty(custId)) {
			Integer customerId = 0;
			try {
				customerId = Integer.parseInt(custId);
			} catch (NumberFormatException numberFormatException) {
				throw new BadRequestException(
						"Customer ID should not be Alpanumeric. It Should be Number only");
			}
			return customerService.getCustomerByCustomerId(customerId);
		} else {
			throw new BadRequestException("Customer ID should not be blank");
		}

	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value = "/getCustomerByEmail/{email}")
	@ResponseBody
	public List<Customer> getCustomerByEmail(@PathVariable String email) {
		if (!StringUtils.isEmpty(email)) {
			return customerService.getCustomerByEmail(email);
		} else {
			throw new BadRequestException("Customer Email should not be blank");
		}

	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value = "/getCustomerByName/{name}")
	@ResponseBody
	public List<Customer> getCustomerByName(@PathVariable String name) {
		if (!StringUtils.isEmpty(name)) {
			return customerService.getCustomerByName(name);
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
	public CustomerResponse deleteCustomerById(@PathVariable Integer custId) {
		if (!StringUtils.isEmpty(custId)) {
			CustomerRequest customerRequest = new CustomerRequest();
			customerRequest.setCustId(Integer.valueOf(custId));
			return new CustomerResponse(
					customerService.deleteCustomer(customerRequest));
		} else
			throw new BadRequestException(
					"customerRequest Id should not be blank");
	}

	@RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/deleteCustomerByAadhar/{custAdharNumber}")
	@ResponseBody
	public CustomerResponse deleteCustomerByAdharNumber(
			@PathVariable String custAdharNumber) {
		if (!StringUtils.isEmpty(custAdharNumber)) {
			CustomerRequest customerRequest = new CustomerRequest();
			customerRequest.setCustAadhaar(custAdharNumber);
			return new CustomerResponse(
					customerService.deleteCustomer(customerRequest));
		} else
			throw new BadRequestException(
					"Customer Aaddhar Number should not be blank");
	}

	@RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/deleteCustomerByMobile/{custMobileNumber}")
	@ResponseBody
	public CustomerResponse deleteCustomerByMobileNumber(
			@PathVariable String custMobileNumber) {
		if (!StringUtils.isEmpty(custMobileNumber)) {
			CustomerRequest customerRequest = new CustomerRequest();
			customerRequest.setCustMobile(custMobileNumber);
			return new CustomerResponse(
					customerService.deleteCustomer(customerRequest));
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
	public CustomerResponse updateCustomer(
			@RequestBody CustomerRequest customerRequest) {

		return new CustomerResponse(
				customerService.updateCustomer(customerRequest));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String welcome() {
		return "Welcome to Customer Management Tool";
	}

}
