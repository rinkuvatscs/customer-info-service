package com.customer.service;

import com.customer.entity.Customer;
import com.customer.request.CustomerRequest;

public interface CustomerService {
    public String addCustomer(CustomerRequest customerRequest);

    public String deleteCustomer(CustomerRequest customerRequest);

    public Customer getCustomerByAdharNumber(String adharNumber);
    
	public String updateCustomer(CustomerRequest customerRequest);

}
