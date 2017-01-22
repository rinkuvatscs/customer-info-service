package com.customer.dao;

import java.util.List;

import com.customer.entity.Customer;
import com.customer.request.CustomerRequest;

public interface CustomerDao {
    public String addCustomer(CustomerRequest customer);

    public String deleteCustomer(CustomerRequest customerRequest);

    public Customer getCustomerByMobileNumber(String mobileNumber);

    public Customer getCustomerByAdharNumber(String adharNumber);
    
    public List<Customer> getCustomerByEmail(String email);
    
    public List<Customer> getCustomerByName(String name);

    public Customer getCustomerById(Integer id);

    public String updateCustomer(CustomerRequest customerRequest);

}
