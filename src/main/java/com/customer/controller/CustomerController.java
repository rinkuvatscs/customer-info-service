package com.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.customer.exceptionhandler.BadRequestException;
import com.customer.request.CustomerRequest;
import com.customer.response.CustomerResponse;
import com.customer.service.CustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping(value = "/customer")
@Api(basePath = "/customer", value = "customermanagement", description = "Operations with Landlords", produces = "application/json")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/addcustomer", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "add new customer", notes = "add new customer")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Fields are with validation errors"),
			@ApiResponse(code = 201, message = "") })
	
	public CustomerResponse addCustomer(@RequestBody CustomerRequest customerRequest) {

		if (!StringUtils.isEmpty(customerRequest)
				&& !StringUtils.isEmpty(customerRequest.getCustAadhaar())
				&& !StringUtils.isEmpty(customerRequest.getCustMobile())) {
			return new CustomerResponse(customerService.addCustomer(customerRequest));
		} else {
			throw new BadRequestException(
					"Customer Aaddhar Number and Mobile Number should not be blank");
		}

	}
}
