package com.patient.db.config;

public interface QueryConstants {
//cust_name varchar(45) default NULL, cust_mobile_number  cust_home_address , cust_adhaar_number cust_mail  date_of_registered status varchar(30) default 'Activate'
	String ADD_PATIENT = "insert into  patient(patient_name,patient_mobile_number,patient_home_address,patient_adhaar_number,"
			+ "patient_mail,date_of_registered,dob,gender)"
			+ " values"
			+ "(?,?,?,?,?,?,?,?)";

	String IS_PATIENT_EXIST = "SELECT * FROM patient";
	
	//WHERE cust_mobile_number = ? OR cust_adhaar_number = ? ";

	String DELETE_PATIENT = "delete from patient WHERE patient_id = ?";

	String GET_PATIENT_BY_ID = " SELECT * FROM patient WHERE patient_id = ? ";

	String GET_PATIENT_BY_ADHAR_NUMBER = " SELECT * FROM patient WHERE patient_adhaar_number = ? ";
	
	String GET_PATIENT_BY_EMAIL = " SELECT * FROM patient WHERE patient_mail = ? ";	

	String GET_PATIENT_BY_MOBILE_NUMBER = " SELECT * FROM patient WHERE patient_mobile_number = ? ";

	String GET_PATIENT_BY_NAME = " SELECT * FROM patient WHERE patient_name LIKE ? ";

	
	// -----------------------------------------------------------------------------------------------------------

	
}
