package com.leantech.practical_test.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leantech.practical_test.dto.DataOperationResultDTO;
import com.leantech.practical_test.model.Employee;
import com.leantech.practical_test.model.Person;
import com.leantech.practical_test.model.PersonPosition;
import com.leantech.practical_test.model.dto.EmployeeDTO;
import com.leantech.practical_test.model.dto.PersonDTO;
import com.leantech.practical_test.model.dto.PersonPositionDTO;
import com.leantech.practical_test.repository.EmployeeRepository;
import com.leantech.practical_test.repository.PersonRepository;
import com.leantech.practical_test.repository.PostitionRepository;
import com.leantech.practical_test.service.PracticalTestService;
import com.leantech.practical_test.util.Constants;

/**
 * Service class for the employee management
 * 
 * @author abaquero
 */
@Service
public class PracticalTestServiceImpl implements PracticalTestService {
	/** Console logger */
	private static final Logger logger = LoggerFactory.getLogger(PracticalTestServiceImpl.class);

	@Autowired
	private PersonRepository perRepo;

	@Autowired
	private PostitionRepository posRepo;

	@Autowired
	private EmployeeRepository empRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.leantech.practical_test.service.PracticalTestService#createEmployee(com.
	 * leantech.practical_test.model.dto.EmployeeDTO)
	 */
	public DataOperationResultDTO<EmployeeDTO> createEmployee(EmployeeDTO empDTO) {
		logger.debug("Initiating employee creation");

		DataOperationResultDTO<EmployeeDTO> result = new DataOperationResultDTO<>(Constants.OPERATION_CREATE_EMPLOYEE,
		        Constants.SUCCESS, null, null);

		Person per = null;
		PersonPosition pos = null;

		Employee emp = new Employee();
		emp.setSalary(empDTO.getSalary());

		try {
			emp = empRepo.save(emp);

			Optional<Person> optPer = perRepo.findById(empDTO.getPerson().getId());
			if (optPer.isPresent()) {
				logger.debug("The candidate already exists");
				result.setResult(Constants.FAILURE);
				result.setMotive("The candidate person is already recorded in the data base");
				return result;
			} else {
				per = perRepo.save(empDTO.getPerson().toEntity());
			}

			Optional<PersonPosition> optPos = posRepo.findByName(empDTO.getPosition().getName());
			if (optPos.isPresent()) {
				pos = optPos.get();
			} else {
				pos = posRepo.save(empDTO.getPosition().toEntity());
			}

			emp.setPerson(per);
			emp.setPosition(pos);

			empRepo.save(emp);

			result.setResponseObject(new EmployeeDTO(emp));
		} catch (Exception e) {
			logger.error("Employee creation failed");
			result.setResult(Constants.FAILURE);
			result.setMotive(e.getLocalizedMessage());
		}

		logger.debug("Finishing employee creation");
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.leantech.practical_test.service.PracticalTestService#updateEmployee(com.
	 * leantech.practical_test.model.dto.EmployeeDTO)
	 */
	public DataOperationResultDTO<EmployeeDTO> updateEmployee(EmployeeDTO empDTO) {
		logger.debug("Initiating employee update");

		DataOperationResultDTO<EmployeeDTO> result = new DataOperationResultDTO<>(Constants.OPERATION_UPDATE_EMPLOYEE,
		        Constants.SUCCESS, null, null);

		Employee emp = null;

		try {
			// the employee is found by it's person ID
			if (empDTO.getPerson() == null || empDTO.getPerson().getId() == null
			        || empDTO.getPerson().getId().isEmpty()) {
				logger.debug("The candidate ID was not included");
				result.setResult(Constants.FAILURE);
				result.setMotive("The candidate ID was not included");
				return result;
			}

			Optional<Employee> optEmp = empRepo.findByPersonId(empDTO.getPerson().getId());
			if (!optEmp.isPresent()) {
				logger.debug("The employee was not found");
				result.setResult(Constants.FAILURE);
				result.setMotive("The employee was not found");
				return result;
			}

			emp = optEmp.get();

			updateData(emp, empDTO);

			result.setResponseObject(new EmployeeDTO(emp));
		} catch (Exception e) {
			logger.error("Employee update failed");
			result.setResult(Constants.FAILURE);
			result.setMotive(e.getLocalizedMessage());
		}

		logger.debug("Finishing employee update");
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.leantech.practical_test.service.PracticalTestService#removeEmployee(java.
	 * lang.String)
	 */
	public DataOperationResultDTO<?> removeEmployee(String perId) {
		logger.debug("Initiating employee deletion");

		DataOperationResultDTO<String> result = new DataOperationResultDTO<>(Constants.OPERATION_DELETE_EMPLOYEE,
		        Constants.SUCCESS, null, null);

		try {
			// the employee is found by it's person ID
			if (perId == null || perId.isEmpty()) {
				logger.debug("The candidate ID is not present");
				result.setResult(Constants.FAILURE);
				result.setMotive("The candidate ID is not present");
				return result;
			}

			Optional<Employee> optEmp = empRepo.findByPersonId(perId);
			if (!optEmp.isPresent()) {
				logger.debug("The employee was not found");
				result.setResult(Constants.FAILURE);
				result.setMotive("The employee was not found");
				return result;
			}

			Employee emp = optEmp.get();
			perRepo.delete(emp.getPerson());
			empRepo.delete(emp);

			result.setResponseObject("The employee with person ID " + perId + " was deleted");
		} catch (Exception e) {
			logger.error("Employee deletion failed");
			result.setResult(Constants.FAILURE);
			result.setMotive(e.getLocalizedMessage());
		}

		logger.debug("Finishing employee deletion");
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.leantech.practical_test.service.PracticalTestService#listEmployees(java.
	 * util.Map)
	 */
	public DataOperationResultDTO<List<EmployeeDTO>> listEmployees(Map<String, String> criteria) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.leantech.practical_test.service.PracticalTestService#listPositions()
	 */
	public DataOperationResultDTO<?> listPositions() {
		return null;
	}

	/**
	 * Sets the new data into the current data. Person ID is not modified
	 * 
	 * @param emp    Entity version of the employee obtained from the database
	 * @param empDTO DTO version of the employee received in the request with the
	 *               new data for the employee
	 */
	private void updateData(Employee emp, EmployeeDTO empDTO) {
		logger.debug("Initiating employee data update");

		PersonDTO per = empDTO.getPerson();

		if (per.getName() != null && !per.getName().isEmpty()) {
			emp.getPerson().setName(per.getName());
		}

		if (per.getLastName() != null && !per.getLastName().isEmpty()) {
			emp.getPerson().setLastName(per.getLastName());
		}

		if (per.getAddress() != null && !per.getAddress().isEmpty()) {
			emp.getPerson().setAddress(per.getAddress());
		}

		if (per.getCellPhone() != null && !per.getCellPhone().isEmpty()) {
			emp.getPerson().setCellPhone(per.getCellPhone());
		}

		if (per.getCityName() != null && !per.getCityName().isEmpty()) {
			emp.getPerson().setCityName(per.getCityName());
		}

		PersonPositionDTO pos = empDTO.getPosition();

		if (pos != null && !pos.getName().equalsIgnoreCase(emp.getPosition().getName())) {
			Optional<PersonPosition> optPos = posRepo.findByName(pos.getName());
			if (optPos.isPresent()) {
				emp.setPosition(optPos.get());
			} else {
				emp.setPosition(posRepo.save(pos.toEntity()));
			}
		}

		if (empDTO.getSalary() != null && empDTO.getSalary().compareTo(emp.getSalary()) != 0) {
			emp.setSalary(empDTO.getSalary());
		}

		empRepo.save(emp);

		logger.debug("Finishing employee data update");
	}

}
