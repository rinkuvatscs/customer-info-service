package com.customer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customer.dao.CustomerDao;
import com.customer.entity.Customer;
import com.customer.request.CustomerRequest;
import com.customer.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	CustomerDao customerDao;

	@Override
	public String addCustomer(CustomerRequest customerRequest) {
		// TODO Auto-generated method stub
		return customerDao.addCustomer(customerRequest);
	}

	@Override
	public String deleteCustomer(CustomerRequest customerRequest) {
		// TODO Auto-generated method stub
		return customerDao.deleteCustomer(customerRequest);
	}

	@Override
	public Customer getCustomerByAdharNumber(String adharNumber) {
		// TODO Auto-generated method stub
		Customer customer = customerDao.getCustomerByAdharNumber(adharNumber);
		return customer;
	}

	@Override
	public String updateCustomer(CustomerRequest customerRequest) {
		// TODO Auto-generated method stub
		return customerDao.updateCustomer(customerRequest);
	}

	@Override
	public Customer getCustomerByMobile(String mobile) {
		return customerDao.getCustomerByMobileNumber(mobile);
	}

	@Override
	public Customer getCustomerByCustomerId(Integer custId) {
		return customerDao.getCustomerById(custId);
	}

	@Override
	public List<Customer> getCustomerByEmail(String email) {
		return customerDao.getCustomerByEmail(email);
	}

	@Override
	public List<Customer> getCustomerByName(String name) {
		// TODO Auto-generated method stub
		return customerDao.getCustomerByName(name);
	}

}
