package com.leantech.practical_test.util;

/**
 * Final class containing naming purposed constants
 * 
 * @author abaquero
 * */
public final class Constants {
	private Constants() {}
	
	// data operation DTO constants
	public static final String OPERATION = "operation";
	public static final String RESULT = "result";
	public static final String CAUSE = "cause";
	public static final String DATA = "data";
	
	/* Operations */
	public static final String HEALTH_CHECK = "Health check";
	public static final String OPERATION_CREATE_EMPLOYEE = "Employee creation";
	public static final String OPERATION_UPDATE_EMPLOYEE = "Employee update";
	public static final String OPERATION_DELETE_EMPLOYEE = "Employee deletion";
	public static final String OPERATION_LIST_EMPLOYEES = "Employee listing";
	public static final String OPERATION_LIST_POSITIONS = "Position listing";
	
	/* Operation Results */
	public static final String SUCCESS = "SUCCESS";
	public static final String FAILURE = "FAIL";
	
	// input field names
	public static final String PERSON = "person";
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String LAST_NAME = "lastName";
	public static final String ADDRESS = "adress";
	public static final String CELLPHONE = "cellPhone";
	public static final String CITY_NAME = "cityName";
	public static final String POSITION = "Position";
	public static final String SALARY = "salary";
	
	// search criteria fields
	public static final String PERSON_NAME_CRITERIA = "personName";
	public static final String POSITION_NAME_CRITERIA = "positionName";
	
}
