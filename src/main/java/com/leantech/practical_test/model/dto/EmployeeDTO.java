package com.leantech.practical_test.model.dto;

import java.io.Serializable;

import com.leantech.practical_test.model.Employee;

/**
 * DTO representing the <code>Employee</code> entity
 * 
 * @author abaquero
 */
public class EmployeeDTO implements Serializable {
	private static final long serialVersionUID = 8694334670448695438L;

	/** Employee's ID */
	private Integer id;

	/** Employee's person */
	private PersonDTO person;

	/** Employee's position */
	private PersonPositionDTO position;

	/** Employee's salary */
	private int salary;
	
	/** Default constructor */
	public EmployeeDTO() {}
	
	/** Constructor from entity */
	public EmployeeDTO(Employee emp) {
		fromEntity(emp);
	}
	
	/** Fills the DTO from a <code>Employee</code> entity object */
	public void fromEntity(Employee e) {
		this.setId(e.getId());
		PersonDTO per = new PersonDTO();
		per.fromEntity(e.getPerson());
		this.setPerson(per);
		PersonPositionDTO pp = new PersonPositionDTO();
		pp.fromEntity(e.getPosition());
		this.setPosition(pp);
		this.setSalary(e.getSalary());
	}

	/** Returns a <code>Employee</code> entity version of the DTO */
	public Employee toEntity() {
		Employee e = new Employee();		
		e.setId(this.getId());	
		e.setPerson(this.getPerson().toEntity());
		e.setPosition(this.getPosition().toEntity());
		e.setSalary(this.getSalary());
		return e;
	}
	
	/** Overridden version of toString() method */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Employee [id=");
		sb.append(this.getId());
		sb.append(", person=");
		sb.append(this.getPerson().toString());
		sb.append(", position=");
		sb.append(this.getPosition().toString());
		sb.append(", salary=");
		sb.append(this.getSalary());
		sb.append("]");
		
		return sb.toString();
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the person
	 */
	public PersonDTO getPerson() {
		return person;
	}

	/**
	 * @param person the person to set
	 */
	public void setPerson(PersonDTO person) {
		this.person = person;
	}

	/**
	 * @return the position
	 */
	public PersonPositionDTO getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(PersonPositionDTO position) {
		this.position = position;
	}

	/**
	 * @return the salary
	 */
	public Integer getSalary() {
		return salary;
	}

	/**
	 * @param salary the salary to set
	 */
	public void setSalary(Integer salary) {
		this.salary = salary;
	}
}
