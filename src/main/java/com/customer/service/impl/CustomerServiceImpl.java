package com.customer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customer.dao.CustomerDao;
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
		return null;
	}

}
