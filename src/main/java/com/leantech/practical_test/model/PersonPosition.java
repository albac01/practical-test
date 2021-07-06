package com.leantech.practical_test.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity class representing the table <code>POSITION</code>
 * 
 * @author abaquero
 * */
@Entity
@Table(name = "Position")
public class PersonPosition implements Serializable{
	private static final long serialVersionUID = -7669197455323770454L;

	/** Position's ID */
	@Id
	@GeneratedValue
	private int id;
	
	/** Position's name */
	@Column(name = "name")
	private String name;

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
}
