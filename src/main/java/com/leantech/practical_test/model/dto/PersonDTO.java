package com.leantech.practical_test.model.dto;

import java.io.Serializable;

import com.leantech.practical_test.model.Person;

/**
 * DTO representing the <code>Person</code> entity
 * 
 * @author abaquero
 * */
public class PersonDTO implements Serializable {
	private static final long serialVersionUID = -170205385192282180L;

	/** Person's ID */
	private String id;

	/** Person's name */
	private String name;

	/** Person's last name */
	private String lastName;

	/** Person's address */
	private String address;

	/** Person's cell phone number */
	private String cellPhone;

	/** Person's residence city name */
	private String cityName;

	/** Fills the DTO from a <code>Person</code> entity object */
	public void fromEntity(Person p) {
		this.setId(p.getId());
		this.setName(p.getName());
		this.setLastName(p.getLastName());
		this.setAddress(p.getAddress());
		this.setCellPhone(p.getCellPhone());
		this.setCityName(p.getCityName());
	}

	/** Returns a <code>Person</code> entity version of the DTO */
	public Person toEntity() {
		Person p = new Person();

		p.setId(this.getId());
		p.setName(this.getName());
		p.setLastName(this.getLastName());
		p.setAddress(this.getAddress());
		p.setCellPhone(this.getCellPhone());
		p.setCityName(this.getCityName());

		return p;
	}
	
	/** Overridden version of toString() method */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Person [id=");
		sb.append(this.getId());
		sb.append(", name=");
		sb.append(this.getName());
		sb.append(", lastName=");
		sb.append(this.getLastName());
		sb.append(", cellPhone=");
		sb.append(this.getCellPhone());
		sb.append(", cityName");
		sb.append(this.getCityName());
		sb.append("]");
		
		return sb.toString();
	}

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
