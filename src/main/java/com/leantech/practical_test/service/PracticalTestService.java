package com.leantech.practical_test.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.leantech.practical_test.dto.DataOperationResultDTO;
import com.leantech.practical_test.model.dto.EmployeeDTO;

/**
 * Service interface for the employee management
 * 
 * @author abaquero
 * */
@Service
public interface PracticalTestService {
	/**
	 * Service used to create an employee
	 * */
	public DataOperationResultDTO<EmployeeDTO> createEmployee(EmployeeDTO empDTO);

	/**
	 * Service used to update an employee
	 * */
	public DataOperationResultDTO<EmployeeDTO> updateEmployee(EmployeeDTO empDTO);

	/**
	 * Service used to remove an employee
	 * */
	public DataOperationResultDTO<?> removeEmployee(String perId);

	/**
	 * Service used to list employees
	 * */
	public DataOperationResultDTO<List<EmployeeDTO>> listEmployees(Map<String, String> criteria);

	/**
	 * Service used to list positions
	 * */
	public DataOperationResultDTO<?> listPositions();
}
