package com.leantech.practical_test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.leantech.practical_test.dto.DataOperationResultDTO;
import com.leantech.practical_test.dto.PositionListDTO;
import com.leantech.practical_test.model.Employee;
import com.leantech.practical_test.model.Person;
import com.leantech.practical_test.model.PersonPosition;
import com.leantech.practical_test.model.dto.EmployeeDTO;
import com.leantech.practical_test.model.dto.PersonDTO;
import com.leantech.practical_test.model.dto.PersonPositionDTO;
import com.leantech.practical_test.repository.EmployeeRepository;
import com.leantech.practical_test.repository.PersonRepository;
import com.leantech.practical_test.repository.PostitionRepository;
import com.leantech.practical_test.service.impl.PracticalTestServiceImpl;
import com.leantech.practical_test.util.Constants;

/**
 * Unit test class for <code>PracticalTestService</code>
 * 
 * @author abaquero
 */
@SpringBootTest
public class PracticalTestServiceTest {
	private static final String MOCK_EXCEPTION_MESSAGE = "Mock Exception";

	@Mock
	PersonRepository perRepo;

	@Mock
	PostitionRepository posRepo;

	@Mock
	EmployeeRepository empRepo;

	@InjectMocks
	PracticalTestService practicalTestService = new PracticalTestServiceImpl();

	@BeforeEach
	void setMockOutput() {
		EmployeeDTO mockEmp = createMockEmployee(true);

		Optional<Person> optPer = Optional.of(mockEmp.getPerson().toEntity());
		Optional<PersonPosition> optPos = Optional.of(mockEmp.getPosition().toEntity());
		Optional<Employee> optEmp = Optional.of(mockEmp.toEntity());

		when(perRepo.findById(anyString())).thenReturn(optPer);
		when(perRepo.save(any())).thenReturn(mockEmp.getPerson().toEntity());

		when(posRepo.findByName(anyString())).thenReturn(optPos);
		when(posRepo.save(any())).thenReturn(mockEmp.getPosition().toEntity());

		when(empRepo.findByPersonId(anyString())).thenReturn(optEmp);
		when(empRepo.save(any())).thenReturn(mockEmp.toEntity());
		
		List<Employee> mockEmpCollection = new ArrayList<>();
		mockEmpCollection.add(mockEmp.toEntity());
		mockEmpCollection.add(mockEmp.toEntity());
		when(empRepo.listByCriteria(any(), any())).thenReturn(mockEmpCollection);
	}

	private EmployeeDTO createMockEmployee(Boolean ok) {
		PersonDTO mockPerson = new PersonDTO();

		mockPerson.setId(ok ? "123456" : "");
		mockPerson.setName("mock name");
		mockPerson.setLastName("mock last name");
		mockPerson.setAddress("mock address");
		mockPerson.setCellPhone("mock cellphone");
		mockPerson.setCityName("mock city name");

		PersonPositionDTO mockPositionDTO = new PersonPositionDTO();
		mockPositionDTO.setId(1);
		mockPositionDTO.setName("mock position");

		EmployeeDTO mockEmp = new EmployeeDTO();

		mockEmp.setId(1);
		mockEmp.setSalary(1);
		mockEmp.setPerson(mockPerson);
		mockEmp.setPosition(ok ? mockPositionDTO : null);

		return mockEmp;
	}

	/**
	 * Test for createEmployee success
	 */
	@Test
	void createEmployeeSuccessTest() throws Exception {
		when(perRepo.findById(anyString())).thenReturn(Optional.empty());

		DataOperationResultDTO<EmployeeDTO> result = practicalTestService.createEmployee(createMockEmployee(true));

		assertEquals(Constants.SUCCESS, result.getResult());
	}

	/**
	 * Test for createEmployee person exists fail
	 */
	@Test
	void createEmployeePersonExistsFailTest() throws Exception {
		DataOperationResultDTO<EmployeeDTO> result = practicalTestService.createEmployee(createMockEmployee(true));

		assertEquals(Constants.FAILURE, result.getResult());
		assertEquals("The candidate person is already recorded in the data base", result.getMotive());
	}

	/**
	 * Test for createEmployee exception thrown fail
	 */
	@Test
	void createEmployeeExceptionFailTest() throws Exception {
		when(perRepo.findById(anyString())).thenThrow(new IllegalArgumentException(MOCK_EXCEPTION_MESSAGE));

		DataOperationResultDTO<EmployeeDTO> result = practicalTestService.createEmployee(createMockEmployee(true));

		assertEquals(Constants.FAILURE, result.getResult());
		assertEquals(MOCK_EXCEPTION_MESSAGE, result.getMotive());
	}

	/**
	 * Test for updateEmployee success
	 */
	@Test
	void updateEmployeeSuccessTest() throws Exception {
		DataOperationResultDTO<EmployeeDTO> result = practicalTestService.updateEmployee(createMockEmployee(true));

		assertEquals(Constants.SUCCESS, result.getResult());
	}

	/**
	 * Test for updateEmployee person ID was not included
	 */
	@Test
	void updateEmployeeNoPersonIdFailTest() throws Exception {
		EmployeeDTO mockEmp = createMockEmployee(true);
		mockEmp.getPerson().setId(null);

		DataOperationResultDTO<EmployeeDTO> result = practicalTestService.updateEmployee(mockEmp);

		assertEquals(Constants.FAILURE, result.getResult());
		assertEquals("The candidate ID was not included", result.getMotive());
	}

	/**
	 * Test for updateEmployee person doesn't exists fail
	 */
	@Test
	void updateEmployeePersonNotExistsFailTest() throws Exception {
		when(empRepo.findByPersonId(anyString())).thenReturn(Optional.empty());

		DataOperationResultDTO<EmployeeDTO> result = practicalTestService.updateEmployee(createMockEmployee(true));

		assertEquals(Constants.FAILURE, result.getResult());
		assertEquals("The employee was not found", result.getMotive());
	}

	/**
	 * Test for removeEmployee success
	 */
	@Test
	void removeEmployeeSuccessTest() throws Exception {
		DataOperationResultDTO<String> result = practicalTestService
		        .removeEmployee(createMockEmployee(true).getPerson().getId());

		assertEquals(Constants.SUCCESS, result.getResult());
	}

	/**
	 * Test for removeEmployee person ID was not included
	 */
	@Test
	void removeEmployeeNoPersonIdFailTest() throws Exception {
		DataOperationResultDTO<String> result = practicalTestService.removeEmployee(null);

		assertEquals(Constants.FAILURE, result.getResult());
		assertEquals("The candidate ID is not present", result.getMotive());
	}

	/**
	 * Test for removeEmployee person doesn't exists fail
	 */
	@Test
	void removeEmployeePersonNotExistsFailTest() throws Exception {
		when(empRepo.findByPersonId(anyString())).thenReturn(Optional.empty());

		DataOperationResultDTO<String> result = practicalTestService
		        .removeEmployee(createMockEmployee(true).getPerson().getId());

		assertEquals(Constants.FAILURE, result.getResult());
		assertEquals("The employee was not found", result.getMotive());
	}

	/**
	 * Test for removeEmployee exception thrown fail
	 */
	@Test
	void removeEmployeeExceptionFailTest() throws Exception {
		doThrow(new IllegalArgumentException(MOCK_EXCEPTION_MESSAGE)).when(perRepo).delete(any());

		DataOperationResultDTO<String> result = practicalTestService
		        .removeEmployee(createMockEmployee(true).getPerson().getId());

		assertEquals(Constants.FAILURE, result.getResult());
		assertEquals(MOCK_EXCEPTION_MESSAGE, result.getMotive());
	}
	
	/**
	 * Test for listEmployees success with matches
	 * */
	@Test
	void listEmployeesSuccessMatchTest() throws Exception {
		DataOperationResultDTO<List<EmployeeDTO>> result = practicalTestService.listEmployees(Collections.emptyMap());

		assertEquals(Constants.SUCCESS, result.getResult());
		assertTrue(result.getResponseObject().size() > 0);
	}
	
	/**
	 * Test for listEmployees success without matches
	 * */
	@Test
	void listEmployeesSuccessNoMatchTest() throws Exception {
		when(empRepo.listByCriteria(any(), any())).thenReturn(Collections.emptyList());
		
		DataOperationResultDTO<List<EmployeeDTO>> result = practicalTestService.listEmployees(Collections.emptyMap());

		assertEquals(Constants.SUCCESS, result.getResult());
		assertEquals(0, result.getResponseObject().size());
	}
	
	/**
	 * Test for listPositions success with matches
	 * */
	@Test
	void listPositionsSuccessMatchTest() throws Exception {
		DataOperationResultDTO<List<PositionListDTO>> result = practicalTestService.listPositions();

		assertEquals(Constants.SUCCESS, result.getResult());
		assertTrue(result.getResponseObject().size() > 0);
	}
	
	/**
	 * Test for listPositions success without matches
	 * */
	@Test
	void listPositionsSuccessNoMatchTest() throws Exception {
		when(empRepo.listByCriteria(any(), any())).thenReturn(Collections.emptyList());
		
		DataOperationResultDTO<List<PositionListDTO>> result = practicalTestService.listPositions();

		assertEquals(Constants.SUCCESS, result.getResult());
		assertEquals(0, result.getResponseObject().size());
	}
}
