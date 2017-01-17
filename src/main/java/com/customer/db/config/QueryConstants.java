package com.customer.db.config;

public interface QueryConstants {
//cust_name varchar(45) default NULL, cust_mobile_number  cust_home_address , cust_adhaar_number cust_mail  date_of_registered status varchar(30) default 'Activate'
	String ADD_CUSTOMER = "insert into  customer(cust_name,cust_mobile_number,cust_home_address,cust_adhaar_number,"
			+ "cust_mail,date_of_registered)"
			+ " values"
			+ "(?,?,?,?,?,?)";

	String IS_CUSTOMER_EXIST = "SELECT * FROM customer WHERE cust_mobile_number = ? OR cust_adhaar_number = ? ";

	String DELETE_CUSTOMER = "delete from customer WHERE cust_id = ?";

	String GET_CUSTOMER_BY_ID = " SELECT * FROM customer WHERE cust_id = ? ";

	String GET_CUSTOMER_BY_ADHAR_NUMBER = " SELECT * FROM customer WHERE cust_adhaar_number = ? ";

	String GET_CUSTOMER_BY_MOBILE_NUMBER = " SELECT * FROM customer WHERE cust_mobile_number = ? ";

	String GET_CUSTOMER_BY_NAME = " SELECT * FROM customer WHERE cust_name LIKE ? ";

	
	// -----------------------------------------------------------------------------------------------------------

	
}
