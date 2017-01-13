package com.customer.db.config;

public interface QueryConstants {
//cust_name varchar(45) default NULL, cust_mobile_number  cust_home_address , cust_adhaar_number cust_mail  date_of_registered status varchar(30) default 'Activate'
	String ADD_CUSTOMER = "insert into  customer(cust_name,cust_mobile_number,cust_home_address,cust_adhaar_number,"
			+ "cust_mail,date_of_registered)"
			+ " values"
			+ "(?,?,?,?,?,?)";

	String IS_CUSTOMER_EXIST = "SELECT * FROM customer WHERE cust_mobile_number = ? OR cust_adhaar_number = ? ";

	String DELETE_DOCTOR = "delete from doctor_detail WHERE doctor_id = ?";

	String GET_DOCTOR_BY_ID = " SELECT * FROM doctor_detail WHERE doctor_id = ? ";

	String GET_DOCTOR_BY_ADHAR_NUMBER = " SELECT * FROM doctor_detail WHERE doctor_adhaar_number = ? ";

	String GET_DOCTOR_BY_MOBILE_NUMBER = " SELECT * FROM doctor_detail WHERE doctor_number = ? ";

	String GET_DOCTOR_BY_NAME = " SELECT * FROM doctor_detail WHERE doctor_name LIKE ? ";

	String GET_DOCTOR_BY_EXPERTISTED = " SELECT * FROM doctor_detail WHERE doctor_expertized = ? ";

	String GET_DOCTOR_BY_CONSULTING_FEE = " SELECT * FROM doctor_detail WHERE onetime_consulting_fee = ? ";

	// -----------------------------------------------------------------------------------------------------------

	
}
