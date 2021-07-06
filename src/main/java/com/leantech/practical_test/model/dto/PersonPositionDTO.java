package com.leantech.practical_test.model.dto;

import java.io.Serializable;

import com.leantech.practical_test.model.PersonPosition;

/**
 * DTO representing the <code>PersonPosition</code> entity
 * 
 * @author abaquero
 * */
public class PersonPositionDTO implements Serializable {
	private static final long serialVersionUID = 5298897739156166981L;

	/** Position's ID */
	private Integer id;
	
	/** Position's name */
	private String name;
	
	/** Fills the DTO from a <code>PersonPosition</code> entity object */
	public void fromEntity(PersonPosition pp) {
		this.setId(pp.getId());
		this.setName(pp.getName());
	}

	/** Returns a <code>PersonPosition</code> entity version of the DTO */
	public PersonPosition toEntity() {
		PersonPosition pp = new PersonPosition();
		if(this.getId() != null) {
			pp.setId(this.getId());
		}
		pp.setName(this.getName());		
		return pp;
	}
	
	/** Overridden version of toString() method */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Position [id=");
		sb.append(this.getId());
		sb.append(", name=");
		sb.append(this.getName());
		sb.append("]");
		
		return sb.toString();
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
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
