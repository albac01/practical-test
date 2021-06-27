package com.leantech.practical_test.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.leantech.practical_test.model.dto.EmployeeDTO;

/**
 * Position DTO used to aid the representation of an employee list by position
 * 
 * @author abaquero
 */
public class PositionListDTO implements Serializable {
	private static final long serialVersionUID = -511912908753208987L;

	@JsonProperty(value = "id")
	private int positionId;

	@JsonProperty(value = "name")
	private String name;

	@JsonProperty(value = "employees")
	private List<EmployeeListDTO> employees;

	/**
	 * @param positionId
	 * @param name
	 */
	public PositionListDTO(int positionId, String name) {
		this.positionId = positionId;
		this.name = name;
		this.employees = new ArrayList<>();
	}

	/**
	 * Adds an employee based on it's entity DTO
	 */
	public void addEmployee(EmployeeDTO empDto) {
		this.employees.add(new EmployeeListDTO(empDto.getId(), empDto.getSalary(),
		        new PersonListDTO(empDto.getPerson().getName(), empDto.getPerson().getLastName(),
		                empDto.getPerson().getAddress(), empDto.getPerson().getCellPhone(),
		                empDto.getPerson().getCityName())));
	}

	/**
	 * @return the positionId
	 */
	public int getPositionId() {
		return positionId;
	}

	/**
	 * @param positionId the positionId to set
	 */
	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the employees
	 */
	public List<EmployeeListDTO> getEmployees() {
		return employees;
	}

	/**
	 * @param employees the employees to set
	 */
	public void setEmployees(List<EmployeeListDTO> employees) {
		this.employees = employees;
	}

}
