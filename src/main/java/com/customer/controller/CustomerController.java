package com.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer.exceptionhandler.BadRequestException;
import com.customer.request.CustomerRequest;
import com.customer.response.CustomerResponse;
import com.customer.service.CustomerService;


@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	
	@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)

	public CustomerResponse addCustomer(CustomerRequest customerRequest) {
		//TODOcreate database customer_info_db;

//DROP TABLE IF EXISTS customer; CREATE TABLE customer  ( cust_id  int(11) NOT NULL auto_increment, cust_name varchar(45) default NULL, cust_mobile_number varchar(10) default NULL, cust_home_address varchar(255) default NULL, cust_adhaar_number varchar(20) default NULL, cust_mail varchar(4) default NULL, date_of_registered varchar(20) default NULL, status varchar(30) default 'Activate', PRIMARY KEY (cust_id) ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
		if (!StringUtils.isEmpty(customerRequest)
				&& !StringUtils.isEmpty(customerRequest.getCustAadhaar())
				&& !StringUtils.isEmpty(customerRequest.getCustMobile())) {
			return new CustomerResponse(customerService.addCustomer(customerRequest));
		} else {
			throw new BadRequestException(
					"Doctor Aaddhar Number and Mobile Number should not be blank");
		}

	}
}
