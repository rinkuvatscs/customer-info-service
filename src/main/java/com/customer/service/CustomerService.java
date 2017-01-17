package com.customer.service;

import com.customer.request.CustomerRequest;

public interface CustomerService {
	public String addCustomer(CustomerRequest customerRequest);
	public String deleteCustomer(CustomerRequest customerRequest);
	
}
