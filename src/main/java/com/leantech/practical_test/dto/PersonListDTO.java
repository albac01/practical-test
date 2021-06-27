package com.leantech.practical_test.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Person DTO used to aid the representation of an employee list by position
 * 
 * @author abaquero
 */
public class PersonListDTO implements Serializable {
	private static final long serialVersionUID = -4278115223236024355L;

	@JsonProperty(value = "name")
	private String name;
	
	@JsonProperty(value = "lastNam2")
	private String lastNam2;
	
	@JsonProperty(value = "address")
	private String address;
	
	@JsonProperty(value = "cellphone")
	private String cellphone;
	
	@JsonProperty(value = "cityName")
	private String cityName;

	/**
	 * @param name
	 * @param lastNam2
	 * @param address
	 * @param cellphone
	 * @param cityName
	 */
	public PersonListDTO(String name, String lastNam2, String address, String cellphone, String cityName) {
		this.name = name;
		this.lastNam2 = lastNam2;
		this.address = address;
		this.cellphone = cellphone;
		this.cityName = cityName;
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
	 * @return the lastNam2
	 */
	public String getLastNam2() {
		return lastNam2;
	}

	/**
	 * @param lastNam2 the lastNam2 to set
	 */
	public void setLastNam2(String lastNam2) {
		this.lastNam2 = lastNam2;
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
	 * @return the cellphone
	 */
	public String getCellphone() {
		return cellphone;
	}

	/**
	 * @param cellphone the cellphone to set
	 */
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
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
