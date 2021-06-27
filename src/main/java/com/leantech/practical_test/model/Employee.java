package com.leantech.practical_test.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Entity class representing the table <code>EMPLOYEE</code>
 * 
 * @author abaquero
 * */
@Entity
@Table(name = "Employee")
public class Employee implements Serializable{
	private static final long serialVersionUID = 1341702189909364806L;

	/** Employee's ID */
	@Id
	@GeneratedValue
	private int id;

	/** Employee's person */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "person", referencedColumnName = "id")
	private Person person;

	/** Employee's position */
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "position", referencedColumnName = "id")
	private PersonPosition position;

	/** Employee's salary */
	@Column(name = "salary")
	private Integer salary;

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
	public Person getPerson() {
		return person;
	}

	/**
	 * @param person the person to set
	 */
	public void setPerson(Person p) {
		this.person = p;
	}

	/**
	 * @return the position
	 */
	public PersonPosition getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(PersonPosition pp) {
		this.position = pp;
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
