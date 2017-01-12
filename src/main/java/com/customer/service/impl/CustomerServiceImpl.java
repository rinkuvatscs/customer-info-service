package com.customer.service.impl;

import org.springframework.stereotype.Service;

import com.customer.dao.CustomerDao;
import com.customer.request.CustomerRequest;
import com.customer.service.CustomerService;
@Service
public class CustomerServiceImpl implements CustomerService {

	CustomerDao customerDao;
	
	@Override
	public String addCustomer(CustomerRequest customerRequest) {
		// TODO Auto-generated method stub
		return customerDao.addCustomer(customerRequest);
	}

}
