package com.leantech.practical_test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.BDDMockito.given;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leantech.practical_test.dto.DataOperationResultDTO;
import com.leantech.practical_test.dto.PositionListDTO;
import com.leantech.practical_test.model.dto.EmployeeDTO;
import com.leantech.practical_test.model.dto.PersonDTO;
import com.leantech.practical_test.model.dto.PersonPositionDTO;
import com.leantech.practical_test.service.impl.PracticalTestServiceImpl;
import com.leantech.practical_test.util.Constants;

/**
 * Unit test class for <code>PracticalTestController</code>
 * 
 * @author abaquero
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PracticalTestControllerTest {
	// bind the above RANDOM_PORT
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	ObjectMapper mapper = new ObjectMapper();

	HttpHeaders headers;

	@MockBean
	PracticalTestServiceImpl practicalTestServiceImpl;

	@BeforeEach
	public void setup() {
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
	}

	// constants
	static final String CREATE_OR_UPDATE_EMPLOYEE_MOCK_REQUEST_BODY = "{\"person\":{\"id\":\"mock person id\",\"name\":\"mock person name\","
	        + "\"lastName\":\"mock person last name\",\"adress\":\"mock address\",\"cellPhone\":\"mock cell phone\","
	        + "\"cityName\":\"mock city name\"},\"Position\":{\"id\":1,\"name\":\"mock position name\"},\"salary\":1}";

	static final String MOCK_PERSON_ID = "mock person id";
	static final String JSON_MAPPING_FAILURE = "The Json request doesn't comply with the required structure";

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

	private DataOperationResultDTO<?> getMockResult(String operation, boolean ok) {

		DataOperationResultDTO<?> result = new DataOperationResultDTO<>(operation,
		        ok ? Constants.SUCCESS : Constants.FAILURE, ok ? null : "mock cause", setMockResultData(operation, ok));
		return result;
	}

	@SuppressWarnings("unchecked")
	private Object setMockResultData(String operation, boolean ok) {
		Object result = null;

		if (ok) {
			switch (operation) {
			case Constants.OPERATION_CREATE_EMPLOYEE:
			case Constants.OPERATION_UPDATE_EMPLOYEE:
				result = createMockEmployee(ok);
				break;
			case Constants.OPERATION_DELETE_EMPLOYEE:
				result = "The employee with person ID " + MOCK_PERSON_ID + " was deleted";
				break;
			case Constants.OPERATION_LIST_EMPLOYEES:
				result = new ArrayList<EmployeeDTO>();
				((ArrayList<EmployeeDTO>) result).add(createMockEmployee(ok));
				break;
			case Constants.OPERATION_LIST_POSITIONS:
				EmployeeDTO mockEmp = createMockEmployee(ok);
				PositionListDTO mockPos = new PositionListDTO(mockEmp.getPosition().getId(),
				        mockEmp.getPosition().getName());
				mockPos.addEmployee(mockEmp);

				result = new ArrayList<PositionListDTO>();
				((ArrayList<PositionListDTO>) result).add(mockPos);
				break;
			default:
				break;
			}
		}

		return result;
	}

	/**
	 * Test for the health check
	 */
	@Test
	void testHealthCheckTest() throws Exception {
		ResponseEntity<String> response = restTemplate.exchange(
		        new URL("http://localhost:" + port + "/api/v1/practical-test/health").toString(), HttpMethod.GET,
		        new HttpEntity<Object>(headers), String.class);

		JsonNode resJson = mapper.readTree(response.getBody());
		String opResult = resJson.has(Constants.RESULT) ? resJson.get(Constants.RESULT).asText() : "";

		assertEquals(Constants.SUCCESS, opResult);
	}

	/**
	 * Test for createEmployee success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	void createEmployeeSuccessTest() throws Exception {
		given(practicalTestServiceImpl.createEmployee(any(EmployeeDTO.class))).willReturn(
		        (DataOperationResultDTO<EmployeeDTO>) getMockResult(Constants.OPERATION_CREATE_EMPLOYEE, true));

		HttpEntity<String> request = new HttpEntity<String>(CREATE_OR_UPDATE_EMPLOYEE_MOCK_REQUEST_BODY, headers);

		ResponseEntity<String> response = restTemplate.exchange(
		        new URL("http://localhost:" + port + "/api/v1/practical-test/employee").toString(), HttpMethod.PUT,
		        request, String.class);

		JsonNode resJson = mapper.readTree(response.getBody());
		String opResult = resJson.has(Constants.RESULT) ? resJson.get(Constants.RESULT).asText() : "";

		assertEquals(Constants.SUCCESS, opResult);
	}

	/**
	 * Test for createEmployee failure on creation service
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	void createEmployeeServiceFailTest() throws Exception {
		given(practicalTestServiceImpl.createEmployee(any(EmployeeDTO.class))).willReturn(
		        (DataOperationResultDTO<EmployeeDTO>) getMockResult(Constants.OPERATION_CREATE_EMPLOYEE, false));

		HttpEntity<String> request = new HttpEntity<String>(CREATE_OR_UPDATE_EMPLOYEE_MOCK_REQUEST_BODY, headers);

		ResponseEntity<String> response = restTemplate.exchange(
		        new URL("http://localhost:" + port + "/api/v1/practical-test/employee").toString(), HttpMethod.PUT,
		        request, String.class);

		JsonNode resJson = mapper.readTree(response.getBody());
		String opResult = resJson.has(Constants.RESULT) ? resJson.get(Constants.RESULT).asText() : "";

		assertEquals(Constants.FAILURE, opResult);
	}

	/**
	 * Test for createEmployee failure on JSON mapper
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	void createEmployeeJSONMapperFailTest() throws Exception {
		given(practicalTestServiceImpl.createEmployee(any(EmployeeDTO.class))).willReturn(
		        (DataOperationResultDTO<EmployeeDTO>) getMockResult(Constants.OPERATION_CREATE_EMPLOYEE, false));

		HttpEntity<String> request = new HttpEntity<String>(mapper.writeValueAsString(createMockEmployee(false)),
		        headers);

		ResponseEntity<String> response = restTemplate.exchange(
		        new URL("http://localhost:" + port + "/api/v1/practical-test/employee").toString(), HttpMethod.PUT,
		        request, String.class);

		JsonNode resJson = mapper.readTree(response.getBody());
		String opResult = resJson.has(Constants.RESULT) ? resJson.get(Constants.RESULT).asText() : "";
		String opMessage = resJson.has(Constants.CAUSE) ? resJson.get(Constants.CAUSE).asText() : "";

		assertEquals(Constants.FAILURE, opResult);
		assertEquals(JSON_MAPPING_FAILURE, opMessage);
	}

	/**
	 * Test for updateEmployee success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	void updateEmployeeSuccessTest() throws Exception {
		given(practicalTestServiceImpl.updateEmployee(any(EmployeeDTO.class))).willReturn(
		        (DataOperationResultDTO<EmployeeDTO>) getMockResult(Constants.OPERATION_UPDATE_EMPLOYEE, true));

		HttpEntity<String> request = new HttpEntity<String>(CREATE_OR_UPDATE_EMPLOYEE_MOCK_REQUEST_BODY, headers);

		ResponseEntity<String> response = restTemplate.exchange(
		        new URL("http://localhost:" + port + "/api/v1/practical-test/employee").toString(), HttpMethod.POST,
		        request, String.class);

		JsonNode resJson = mapper.readTree(response.getBody());
		String opResult = resJson.has(Constants.RESULT) ? resJson.get(Constants.RESULT).asText() : "";

		assertEquals(Constants.SUCCESS, opResult);
	}

	/**
	 * Test for updateEmployee failure on update service
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	void updateEmployeeServiceFailTest() throws Exception {
		given(practicalTestServiceImpl.updateEmployee(any(EmployeeDTO.class))).willReturn(
		        (DataOperationResultDTO<EmployeeDTO>) getMockResult(Constants.OPERATION_UPDATE_EMPLOYEE, false));

		HttpEntity<String> request = new HttpEntity<String>(CREATE_OR_UPDATE_EMPLOYEE_MOCK_REQUEST_BODY, headers);

		ResponseEntity<String> response = restTemplate.exchange(
		        new URL("http://localhost:" + port + "/api/v1/practical-test/employee").toString(), HttpMethod.POST,
		        request, String.class);

		JsonNode resJson = mapper.readTree(response.getBody());
		String opResult = resJson.has(Constants.RESULT) ? resJson.get(Constants.RESULT).asText() : "";

		assertEquals(Constants.FAILURE, opResult);
	}

	/**
	 * Test for updateEmployee failure on JSON mapper
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	void updateEmployeeJSONMapperFailTest() throws Exception {
		given(practicalTestServiceImpl.updateEmployee(any(EmployeeDTO.class))).willReturn(
		        (DataOperationResultDTO<EmployeeDTO>) getMockResult(Constants.OPERATION_UPDATE_EMPLOYEE, false));

		HttpEntity<String> request = new HttpEntity<String>(mapper.writeValueAsString(createMockEmployee(false)),
		        headers);

		ResponseEntity<String> response = restTemplate.exchange(
		        new URL("http://localhost:" + port + "/api/v1/practical-test/employee").toString(), HttpMethod.POST,
		        request, String.class);

		JsonNode resJson = mapper.readTree(response.getBody());
		String opResult = resJson.has(Constants.RESULT) ? resJson.get(Constants.RESULT).asText() : "";
		String opMessage = resJson.has(Constants.CAUSE) ? resJson.get(Constants.CAUSE).asText() : "";

		assertEquals(Constants.FAILURE, opResult);
		assertEquals(JSON_MAPPING_FAILURE, opMessage);
	}

	/**
	 * Test for deleteEmployee success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	void deleteEmployeeSuccessTest() throws Exception {
		given(practicalTestServiceImpl.removeEmployee(anyString()))
		        .willReturn((DataOperationResultDTO<String>) getMockResult(Constants.OPERATION_DELETE_EMPLOYEE, true));

		HttpEntity<String> request = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
		        new URL("http://localhost:" + port + "/api/v1/practical-test/employee/" + MOCK_PERSON_ID).toString(),
		        HttpMethod.DELETE, request, String.class);

		JsonNode resJson = mapper.readTree(response.getBody());
		String opResult = resJson.has(Constants.RESULT) ? resJson.get(Constants.RESULT).asText() : "";

		assertEquals(Constants.SUCCESS, opResult);
	}

	/**
	 * Test for deleteEmployee failure on remove service
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	void deleteEmployeeServiceFailTest() throws Exception {
		given(practicalTestServiceImpl.removeEmployee(anyString()))
		        .willReturn((DataOperationResultDTO<String>) getMockResult(Constants.OPERATION_DELETE_EMPLOYEE, false));

		HttpEntity<String> request = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
		        new URL("http://localhost:" + port + "/api/v1/practical-test/employee/" + MOCK_PERSON_ID).toString(),
		        HttpMethod.DELETE, request, String.class);

		JsonNode resJson = mapper.readTree(response.getBody());
		String opResult = resJson.has(Constants.RESULT) ? resJson.get(Constants.RESULT).asText() : "";

		assertEquals(Constants.FAILURE, opResult);
	}

	/**
	 * Test for listEmployees success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	void listEmployeesSuccessTest() throws Exception {
		given(practicalTestServiceImpl.listEmployees(anyMap())).willReturn(
		        (DataOperationResultDTO<List<EmployeeDTO>>) getMockResult(Constants.OPERATION_LIST_EMPLOYEES, true));

		ResponseEntity<String> response = restTemplate.exchange(
		        new URL("http://localhost:" + port + "/api/v1/practical-test/employee/list").toString(), HttpMethod.GET,
		        new HttpEntity<Object>(headers), String.class);

		JsonNode resJson = mapper.readTree(response.getBody());
		String opResult = resJson.has(Constants.RESULT) ? resJson.get(Constants.RESULT).asText() : "";

		assertEquals(Constants.SUCCESS, opResult);
	}

	/**
	 * Test for listPositions success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	void listPositionsSuccessTest() throws Exception {
		given(practicalTestServiceImpl.listPositions()).willReturn(
		        (DataOperationResultDTO<List<PositionListDTO>>) getMockResult(Constants.OPERATION_LIST_POSITIONS,
		                true));

		ResponseEntity<String> response = restTemplate.exchange(
		        new URL("http://localhost:" + port + "/api/v1/practical-test/position/list").toString(), HttpMethod.GET,
		        new HttpEntity<Object>(headers), String.class);

		JsonNode resJson = mapper.readTree(response.getBody());
		String opResult = resJson.has(Constants.RESULT) ? resJson.get(Constants.RESULT).asText() : "";

		assertEquals(Constants.SUCCESS, opResult);
	}
}
