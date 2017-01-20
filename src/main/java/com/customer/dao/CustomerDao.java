package com.customer.dao;

import com.customer.entity.Customer;
import com.customer.request.CustomerRequest;

public interface CustomerDao {
    public String addCustomer(CustomerRequest customer);

    public String deleteCustomer(CustomerRequest customerRequest);

    public Customer getCustomerByMobileNumber(String mobileNumber);

    public Customer getCustomerByAdharNumber(String adharNumber);

    public Customer getCustomerById(Integer id);

    public String updateCustomer(CustomerRequest customerRequest);

}
