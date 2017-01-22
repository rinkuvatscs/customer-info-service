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
		// (cust_name,cust_mobile_number,cust_home_address,cust_adhaar_number,"
		// + "cust_mail,date_of_registered)
		if (!isCustomerExists(customer)) {

			List<Object> args = new ArrayList<>();
			args.add(customer.getCustName());
			args.add(customer.getCustMobile());
			args.add(customer.getCustHomeAddress());
			args.add(customer.getCustAadhaar());
			args.add(customer.getCustEmail());
			// TODO We can use database date also that will be easy for use
			// using now() function in MYSQL
			LocalDate localDate = LocalDate.now();
			System.out.println("curr date="
					+ DateTimeFormatter.ofPattern("yyyy/MM/dd").format(
							localDate));
			args.add(DateTimeFormatter.ofPattern("yyyy/MM/dd")
					.format(localDate));

			int row = jdbcTemplate.update(QueryConstants.ADD_CUSTOMER,
					args.toArray());
			if (row == 1) {
				response = customer.getCustName() + " registered successfully";
			} else {
				response = "Sorry, " + customer.getCustName()
						+ " not registered . Please try again";
			}
		} else {
			// TODO need to explain here that our aadhar or mobile number
			// already registered please login
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
					&& getCustomerById(customerRequest.getCustId()).getCustId() != null) {
				Object args[] = { customerRequest.getCustId() };
				delete = jdbcTemplate.update(
						"DELETE FROM customer WHERE cust_id = ? ", args);
				if (delete > 0) {
					response = "Customer with Customer ID "
							+ customerRequest.getCustId()
							+ " successfully Deleted";
				} else {
					response = "Please try again later";
				}
			} else if (!StringUtils.isEmpty(customerRequest.getCustAadhaar())
					&& getCustomerByAdharNumber(
							customerRequest.getCustAadhaar()).getCustAadhaar() != null) {
				Object args[] = { customerRequest.getCustAadhaar() };
				delete = jdbcTemplate.update(
						"DELETE FROM customer WHERE cust_adhaar_number = ? ",
						args);
				if (delete > 0) {
					response = "Customer with Aadhar Number "
							+ customerRequest.getCustAadhaar()
							+ " successfully Deleted";
				} else {
					response = "Please try again later";
				}
			} else if (!StringUtils.isEmpty(customerRequest.getCustMobile())
					&& getCustomerByMobileNumber(
							customerRequest.getCustMobile()).getCustMobile() != null) {
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
					"Customer can not be deleted without details {"
							+ customerRequest + "}");

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

	@Override
	public String updateCustomer(CustomerRequest customerRequest) {

		String response = null;
		List<Object> args = new ArrayList<>();
		StringBuilder query = new StringBuilder(" UPDATE customer SET ");
		if (!StringUtils.isEmpty(customerRequest)) {
			if (isCustomerExists(customerRequest)) {
				boolean isCustomerName = false, isHomeAddress = false, isCustEmail = false;
				if (null != customerRequest.getCustName()) {
					query.append(" cust_name = ? ");
					args.add(customerRequest.getCustName());
					isCustomerName = true;
				}
				if (null != customerRequest.getCustHomeAddress()) {
					if (isCustomerName) {
						query.append(" , cust_home_address = ? ");
						args.add(customerRequest.getCustHomeAddress());
					} else {
						query.append(" cust_home_add" + "ress = ? ");
						args.add(customerRequest.getCustHomeAddress());
					}
					isHomeAddress = true;
				}
				if (null != customerRequest.getCustEmail()) {
					if (isHomeAddress || isCustomerName) {
						query.append(" , cust_mail = ? ");
						args.add(customerRequest.getCustEmail());
					} else {
						query.append(" cust_mail = ?");
						args.add(customerRequest.getCustEmail());
					}
					isCustEmail = true;
				}

				if (isCustomerName || isHomeAddress || isCustEmail) {
					if (null != customerRequest.getCustMobile()) {
						query.append(" WHERE cust_mobile_number = ? ");
						args.add(customerRequest.getCustMobile());
					} else if (null != customerRequest.getCustAadhaar()) {
						query.append(" WHERE cust_adhaar_number = ? ");
						args.add(customerRequest.getCustAadhaar());
					} else if (null != customerRequest.getCustId()) {
						query.append(" WHERE cust_id = ? ");
						args.add(customerRequest.getCustId());
					} else {
						response = "Please Enter data to Update....!!!";
					}
				}
				if (StringUtils.isEmpty(response)) {
					int update = jdbcTemplate.update(query.toString(),
							args.toArray());
					if (update > 0) {
						response = "Customer successfully Updated...!!!";
					} else {
						response = "There is some problem, please try again later...!!!";
					}
				}
			} else {
				response = "Customer does not exist...!!!";
			}
		} else {
			response = "Customer details are Empty, provide some details to update....!!!";
		}

		return response;
	}

	@Override
	public List<Customer> getCustomerByEmail(String email) {
		if (!StringUtils.isEmpty(email)) {
			Object args[] = { email };
			List<Customer> response = jdbcTemplate.query(
					QueryConstants.GET_CUSTOMER_BY_EMAIL,
					new CustomerExtractor(), args);
			if (!StringUtils.isEmpty(response) && response.size() > 0) {
				return response;
			}
		}
		return new ArrayList<Customer>();
	}

	@Override
	public List<Customer> getCustomerByName(String name) {
		if (!StringUtils.isEmpty(name)) {
			Object args[] = { name };
			List<Customer> response = jdbcTemplate.query(
					QueryConstants.GET_CUSTOMER_BY_NAME,
					new CustomerExtractor(), args);
			if (!StringUtils.isEmpty(response) && response.size() > 0) {
				return response;
			}
		}
		return new ArrayList<Customer>();
	}
}
