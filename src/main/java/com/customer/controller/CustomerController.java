package com.customer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer.request.CustomerRequest;


@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

	
	@RequestMapping("/")
	public String addCustomer(CustomerRequest customerRequest) {
		//TODO
		return null;
	}
}
