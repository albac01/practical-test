package com.leantech.practical_test.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.leantech.practical_test.dto.DataOperationResultDTO;
import com.leantech.practical_test.model.dto.EmployeeDTO;
import com.leantech.practical_test.model.dto.PersonDTO;
import com.leantech.practical_test.model.dto.PersonPositionDTO;
import com.leantech.practical_test.service.impl.PracticalTestServiceImpl;
import com.leantech.practical_test.util.Constants;

/**
 * Class that contains the end points for the practical test
 * 
 * @author abaquero
 */
@RestController
@CrossOrigin(origins="http://localhost:4300")
@RequestMapping(path = "api/v1/practical-test", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
public class PracticalTestController {
	/** Console logger */
	private static final Logger logger = LoggerFactory.getLogger(PracticalTestController.class);

	// Class constants
	private static final String JSON_MAPPING_FAILURE = "The Json request doesn't comply with the required structure";

	/** Mapper for responses */
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private PracticalTestServiceImpl service;

	/**
	 * Health check
	 */
	@GetMapping(path = "/health", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ObjectNode> healthCheck() {
		logger.info("Health check stating");

		ObjectNode response = toObjectNode(
		        new DataOperationResultDTO<>(Constants.HEALTH_CHECK, Constants.SUCCESS, "Ok", null));

		logger.info("Health check finished");

		return ResponseEntity.ok().body(response);
	}

	/**
	 * Employee addition
	 * 
	 * @param requestData JSON representation of the new employee data
	 * @return JSON response with the status of the operation
	 */
	@PutMapping(path = "/employee", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ObjectNode> createEmployee(@RequestBody String requestData, HttpServletRequest request) {
		logger.info("Initiating employee creation operation");

		ObjectNode response = null;
		DataOperationResultDTO<EmployeeDTO> operationResult = null;
		JsonNode jsonNode = null;

		try {
			jsonNode = mapper.readTree(requestData);

			EmployeeDTO emp = mapEmployee(jsonNode, false);

			operationResult = service.createEmployee(emp);

			if (operationResult.getResult().equals(Constants.FAILURE)) {
				logger.warn("The employee was not recorded");
			}

			response = toObjectNode(operationResult);
		} catch (IOException e) {
			logger.error(JSON_MAPPING_FAILURE);

			response = toObjectNode(new DataOperationResultDTO<>(Constants.OPERATION_CREATE_EMPLOYEE, Constants.FAILURE,
			        JSON_MAPPING_FAILURE, null));

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		logger.info("Employee creation operation completed");
		return ResponseEntity.ok().body(response);
	}

	/**
	 * Employee modification
	 * 
	 * @param requestData JSON representation of the employee data to be updated
	 * @return JSON response with the status of the operation
	 */
	@PostMapping(path = "/employee", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ObjectNode> updateEmployee(@RequestBody String requestData, HttpServletRequest request) {
		logger.info("Initiating employee update operation");

		ObjectNode response = null;
		DataOperationResultDTO<EmployeeDTO> operationResult = null;
		JsonNode jsonNode = null;

		try {
			jsonNode = mapper.readTree(requestData);

			EmployeeDTO emp = mapEmployee(jsonNode, true);

			operationResult = service.updateEmployee(emp);

			if (operationResult.getResult().equals(Constants.FAILURE)) {
				logger.warn("The employee update was not performed");
			}

			response = toObjectNode(operationResult);
		} catch (IOException e) {
			logger.error(JSON_MAPPING_FAILURE);

			response = toObjectNode(new DataOperationResultDTO<>(Constants.OPERATION_CREATE_EMPLOYEE, Constants.FAILURE,
			        JSON_MAPPING_FAILURE, null));

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		logger.info("Employee update operation completed");
		return ResponseEntity.ok().body(response);
	}

	/**
	 * Delete Employee
	 * 
	 * @return JSON response with the status of the operation
	 */
	@DeleteMapping(path = "/employee/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ObjectNode> deleteEmployee(@PathVariable String id) {
		logger.info("Initiating delete employee operation");

		ObjectNode response = null;
		DataOperationResultDTO<?> operationResult = service.removeEmployee(id);

		if (operationResult.getResult().equals(Constants.FAILURE)) {
			logger.warn("The employee deletion was not performed");
		}

		response = toObjectNode(operationResult);

		logger.info("Delete employee operation completed");
		return ResponseEntity.ok().body(response);
	}

	/**
	 * List employees
	 * 
	 * @param personName   Optional search criteria for the name of the employee
	 * @param positionName Optional search criteria for the position name
	 * @return JSON response with the status of the operation and list of the
	 *         matching employees
	 */
	@GetMapping(path = "/employee/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ObjectNode> listEmployees(@RequestParam(name = "person", required = false) String personName,
	        @RequestParam(name = "position", required = false) String positionName) {
		logger.info("Initiating employee listing operation");

		ObjectNode response = null;

		Map<String, String> criteria = new HashMap<>();
		if (personName != null && !personName.isEmpty()) {
			criteria.put(Constants.PERSON_NAME_CRITERIA, personName);
		}

		if (positionName != null && !positionName.isEmpty()) {
			criteria.put(Constants.POSITION_NAME_CRITERIA, positionName);
		}

		DataOperationResultDTO<?> operationResult = service.listEmployees(criteria);

		response = toObjectNode(operationResult);

		logger.info("List employees operation completed");
		return ResponseEntity.ok().body(response);
	}

	/**
	 * List positions
	 * 
	 * @return JSON response with the status of the operation and a list of
	 *         employees sorted by position
	 */
	@GetMapping(path = "/position/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ObjectNode> listPositions() {
		logger.info("Initiating position listing operation");

		ObjectNode response = null;

		DataOperationResultDTO<?> operationResult = service.listPositions();

		response = toObjectNode(operationResult);

		logger.info("List positions operation completed");
		return ResponseEntity.ok().body(response);
	}

	/**
	 * Method used to translate the <code>DataOperationResultDTO</code> into an
	 * <code>ObjectNode</code>
	 */
	private ObjectNode toObjectNode(DataOperationResultDTO<?> operationResult) {
		logger.debug("Converting result data from JSON to DTO");

		ObjectNode toReturn = mapper.createObjectNode();

		if (operationResult == null) {
			toReturn.put("Alert", "No operation result was given!");
			return toReturn;
		}

		if (operationResult.getOperation() != null && !operationResult.getOperation().isEmpty()) {
			toReturn.put(Constants.OPERATION, operationResult.getOperation());
		}

		if (operationResult.getResult() != null && !operationResult.getResult().isEmpty()) {
			toReturn.put(Constants.RESULT, operationResult.getResult());
		}

		if (operationResult.getMotive() != null && !operationResult.getMotive().isEmpty()) {
			toReturn.put(Constants.CAUSE, operationResult.getMotive());
		}

		if (operationResult.getResponseObject() != null) {
			try {
				JsonNode data = mapper.readTree(mapper.writeValueAsString(operationResult.getResponseObject()));
				toReturn.set(Constants.DATA, data);
			} catch (IOException e) {
				logger.warn("Result data object could not be parsed to JSON");
				toReturn.put(Constants.DATA, "Result data object could not be parsed to JSON");
			}
		}

		return toReturn;
	}

	/**
	 * Method used to convert the JSON representation of the employee data to the
	 * corresponding DTO objects
	 * 
	 * @param jsonNode JSON representation of the employee data
	 * @param isUpdate Indicates the request is meant to be and update
	 * @return DTO representation of the employee data
	 * @throws IOException on missing of failed structure of the JSON data
	 */
	private EmployeeDTO mapEmployee(JsonNode jsonNode, boolean isUpdate) throws IOException {
		logger.debug("Converting employee JSON representation to DTO representation");
		// checks the presence of person ID
		if (!jsonNode.has(Constants.PERSON) || !jsonNode.get(Constants.PERSON).has(Constants.ID)
		        || jsonNode.get(Constants.PERSON).get(Constants.ID) == null
		        || jsonNode.get(Constants.PERSON).get(Constants.ID).asText().isEmpty()) {
			throw new IOException();
		}

		PersonDTO per = new PersonDTO();
		per.setId(jsonNode.get(Constants.PERSON).get(Constants.ID).asText());
		per.setName(jsonNode.get(Constants.PERSON).has(Constants.NAME)
		        ? jsonNode.get(Constants.PERSON).get(Constants.NAME).asText()
		        : null);
		per.setLastName(jsonNode.get(Constants.PERSON).has(Constants.LAST_NAME)
		        ? jsonNode.get(Constants.PERSON).get(Constants.LAST_NAME).asText()
		        : null);
		per.setAddress(jsonNode.get(Constants.PERSON).has(Constants.ADDRESS)
		        ? jsonNode.get(Constants.PERSON).get(Constants.ADDRESS).asText()
		        : null);
		per.setCellPhone(jsonNode.get(Constants.PERSON).has(Constants.CELLPHONE)
		        ? jsonNode.get(Constants.PERSON).get(Constants.CELLPHONE).asText()
		        : null);
		per.setCityName(jsonNode.get(Constants.PERSON).has(Constants.CITY_NAME)
		        ? jsonNode.get(Constants.PERSON).get(Constants.CITY_NAME).asText()
		        : null);

		PersonPositionDTO pos = new PersonPositionDTO();

		// checks the presence of position data for a new employee
		if (!jsonNode.has(Constants.POSITION) && !isUpdate) {
			logger.error("The request data does not include position data for the new employee");
			throw new IOException();
		} else if (jsonNode.has(Constants.POSITION)) {
			pos.setId(jsonNode.get(Constants.POSITION).has(Constants.ID)
			        ? jsonNode.get(Constants.POSITION).get(Constants.ID).asInt()
			        : null);
			pos.setName(jsonNode.get(Constants.POSITION).has(Constants.NAME)
			        ? jsonNode.get(Constants.POSITION).get(Constants.NAME).asText()
			        : null);
		}

		// checks the salary data
		if (!jsonNode.has(Constants.SALARY)) {
			logger.error("The request data does not include salary");
			throw new IOException();
		}

		EmployeeDTO emp = new EmployeeDTO();
		emp.setSalary(jsonNode.has(Constants.SALARY) ? jsonNode.get(Constants.SALARY).asInt() : null);
		emp.setPerson(per);
		emp.setPosition(pos);

		return emp;
	}
}
