package com.leantech.practical_test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leantech.practical_test.model.dto.EmployeeDTO;
import com.leantech.practical_test.model.dto.PersonDTO;
import com.leantech.practical_test.model.dto.PersonPositionDTO;

public class AppTest {

	public static void main(String[] args) {
		PersonDTO per = new PersonDTO();
		per.setId("16079491");
		per.setName("Alfonso");
		per.setLastName("Baquero");
		per.setAddress("Cll 49 #39G-07");
		per.setCellPhone("3006158501");
		per.setCityName("Manizales");
		
		PersonPositionDTO pos = new PersonPositionDTO();
		pos.setId(1);
		pos.setName("Senior Developer");
		
		EmployeeDTO emp = new EmployeeDTO();
		emp.setId(1);
		emp.setSalary(2000);
		emp.setPerson(per);
		emp.setPosition(pos);
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			String json = mapper.writeValueAsString(emp);
			
			System.out.println(json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
