package com.leantech.practical_test.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Employee DTO used to aid the representation of an employee list by position
 * 
 * @author abaquero
 */
public class EmployeeListDTO implements Serializable{
	private static final long serialVersionUID = -387434176765233053L;

	@JsonProperty(value = "id")
	private int employeeId;
	
	@JsonProperty(value = "salary")
	private int salary;
	
	@JsonProperty(value = "person")
	private PersonListDTO person;

	/**
	 * @param employeeId
	 * @param salary
	 * @param person
	 */
	public EmployeeListDTO(int employeeId, int salary, PersonListDTO person) {
		this.employeeId = employeeId;
		this.salary = salary;
		this.person = person;
	}

	/**
	 * @return the employeeId
	 */
	public int getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * @return the salary
	 */
	public int getSalary() {
		return salary;
	}

	/**
	 * @param salary the salary to set
	 */
	public void setSalary(int salary) {
		this.salary = salary;
	}

	/**
	 * @return the person
	 */
	public PersonListDTO getPerson() {
		return person;
	}

	/**
	 * @param person the person to set
	 */
	public void setPerson(PersonListDTO person) {
		this.person = person;
	}
}
