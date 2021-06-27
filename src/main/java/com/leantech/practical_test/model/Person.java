package com.leantech.practical_test.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity class representing the table <code>CANDIDATE</code>
 * 
 * @author abaquero
 * */
@Entity
@Table(name = "Candidate")
public class Person implements Serializable{
	private static final long serialVersionUID = -7504152638113706075L;
	
	/** Person's ID */
	@Id
	private String id;
	
	/** Person's name */
	@Column(name = "name")
	private String name;
	
	/** Person's last name */
	@Column(name = "lastname")
	private String lastName;
	
	/** Person's address */
	@Column(name = "address")
	private String address;
	
	/** Person's cell phone number */
	@Column(name = "cellphone")
	private String cellPhone;
	
	/** Person's residence city name */
	@Column(name = "cityname")
	private String cityName;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the cellPhone
	 */
	public String getCellPhone() {
		return cellPhone;
	}

	/**
	 * @param cellPhone the cellPhone to set
	 */
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}
