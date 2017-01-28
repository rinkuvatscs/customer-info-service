package com.customer.service;

import java.util.List;

import com.customer.entity.Customer;
import com.customer.request.CustomerRequest;

public interface CustomerService {
    public String addCustomer(CustomerRequest customerRequest);

    public String deleteCustomer(CustomerRequest customerRequest);

    public Customer getCustomerByAdharNumber(String adharNumber);

    public Customer getCustomerByMobile(String mobile);

    public Customer getCustomerByEmail(String email);

    public List<Customer> getCustomerByName(String name);

    public Customer getCustomerByCustomerId(Integer custId);

    public String updateCustomer(CustomerRequest customerRequest);

}
