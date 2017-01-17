package com.customer.dao.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.customer.dao.CustomerDao;
import com.customer.db.config.QueryConstants;
import com.customer.entity.Customer;
import com.customer.exceptionhandler.BadRequestException;
import com.customer.extractor.CustomerExtractor;
import com.customer.request.CustomerRequest;

@Repository
public class CustomerDaoImpl implements CustomerDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public String addCustomer(CustomerRequest customer) {
		String response = null;
		//(cust_name,cust_mobile_number,cust_home_address,cust_adhaar_number,"
		//+ "cust_mail,date_of_registered)
		if (!isCustomerExists(customer)) {

			List<Object> args = new ArrayList<>();
			args.add(customer.getCustName());
			args.add(customer.getCustMobile());
			args.add(customer.getCustHomeAddress());
			args.add(customer.getCustAadhaar());
			args.add(customer.getCustEmail());
			LocalDate localDate = LocalDate.now();
	        System.out.println("curr date="+DateTimeFormatter.ofPattern("yyyy/MM/dd").format(localDate));
			args.add(DateTimeFormatter.ofPattern("yyyy/MM/dd").format(localDate));

			int row = jdbcTemplate.update(QueryConstants.ADD_CUSTOMER,
					args.toArray());
			if (row == 1) {
				response = customer.getCustName() + " registered successfully";
			} else {
				response = "Sorry, " + customer.getCustName()
						+ " not registered . Please try again";
			}
		} else {
			response = "Sorry, " + customer.getCustName()
					+ " already registered";
		}

		return response;
	}

	private boolean isCustomerExists(CustomerRequest customer) {

		boolean isExist = false;
		List<String> args = new ArrayList<>();
		args.add(customer.getCustMobile());
		args.add(customer.getCustAadhaar());
		List<Customer> response = jdbcTemplate.query(
				QueryConstants.IS_CUSTOMER_EXIST, new CustomerExtractor(),
				args.toArray());
		if (!StringUtils.isEmpty(response) && response.size() > 0) {
			isExist = true;
		}
		return isExist;
	}

	@Override
	public String deleteCustomer(CustomerRequest customerRequest) {

		String response = null;
		int delete;
		if (!StringUtils.isEmpty(customerRequest)) {
			if (!StringUtils.isEmpty(customerRequest.getCustId())
					&& getCustomerById(customerRequest.getCustId()) != null) {
				Object args[] = { customerRequest.getCustId() };
				delete = jdbcTemplate.update(
						"DELETE FROM customer WHERE cust_id = ? ", args);
				if (delete > 0) {
					response = "Customer with Customer ID " + customerRequest.getCustId()
							+ " successfully Deleted";
				} else {
					response = "Please try again later";
				}
			} else if (!StringUtils.isEmpty(customerRequest.getCustAadhaar())
					&& getCustomerByAdharNumber(customerRequest.getCustAadhaar()) != null) {
				Object args[] = { customerRequest.getCustAadhaar() };
				delete = jdbcTemplate
						.update("DELETE FROM customer WHERE cust_adhaar_number = ? ",
								args);
				if (delete > 0) {
					response = "Customer with Aadhar Number "
							+ customerRequest.getCustAadhaar()
							+ " successfully Deleted";
				} else {
					response = "Please try again later";
				}
			} else if (!StringUtils.isEmpty(customerRequest.getCustMobile())
					&& getCustomerByMobileNumber(customerRequest.getCustMobile()) != null) {
				Object args[] = { customerRequest.getCustMobile() };
				delete = jdbcTemplate.update(
						"DELETE FROM customer WHERE cust_mobile_number = ? ",
						args);
				if (delete > 0) {
					response = "Customer with Mobile Number "
							+ customerRequest.getCustMobile()
							+ " successfully Deleted";
				} else {
					response = "Please try again later";
				}
			} else {
				throw new BadRequestException(
						"Please provide valid Customer Id or Customer Adhar Number or Customer Mobile Number.");
			}
		} else {
			throw new BadRequestException(
					"Customer can not be deleted without details");

		}
		return response;
	}

	
	@Override
	public Customer getCustomerById(Integer id) {

		if (!StringUtils.isEmpty(id)) {
			Object args[] = { id };
			List<Customer> response = jdbcTemplate.query(
					QueryConstants.GET_CUSTOMER_BY_ID, args,
					new CustomerExtractor());
			if (!StringUtils.isEmpty(response) && response.size() > 0) {
				return response.get(0);
			}
		}
		return new Customer();
	}

	@Override
	public Customer getCustomerByAdharNumber(String adharNumber) {

		if (!StringUtils.isEmpty(adharNumber)) {
			Object args[] = { adharNumber };
			List<Customer> response = jdbcTemplate.query(
					QueryConstants.GET_CUSTOMER_BY_ADHAR_NUMBER,
					new CustomerExtractor(), args);
			if (!StringUtils.isEmpty(response) && response.size() > 0) {
				return response.get(0);
			}
		}
		return new Customer();
	}

	@Override
	public Customer getCustomerByMobileNumber(String mobileNumber) {

		if (!StringUtils.isEmpty(mobileNumber)) {
			Object args[] = { mobileNumber };
			List<Customer> response = jdbcTemplate.query(
					QueryConstants.GET_CUSTOMER_BY_MOBILE_NUMBER,
					new CustomerExtractor(), args);
			if (!StringUtils.isEmpty(response) && response.size() > 0) {
				return response.get(0);
			}
		}
		return new Customer();
	}


}
