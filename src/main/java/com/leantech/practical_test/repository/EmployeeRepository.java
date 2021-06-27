package com.leantech.practical_test.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.leantech.practical_test.model.Employee;

/**
 * Interface for Database CRUD operations for the Employee table
 * 
 * @author abaquero
 */
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
	/**
	 * Finds and employee entry by it's person's ID
	 * 
	 * @param id Person ID
	 * @return Optional for the found employee record
	 */
	@Query(value = "select * from employee e inner join candidate c on e.person = c.id where c.id = :id", nativeQuery = true)
	public Optional<Employee> findByPersonId(@Param("id") String id);

	/**
	 * Finds employees by person name and/or position name (no criteria list all the
	 * employees)
	 * 
	 * @param personName   Optional search criteria for the name of the employee
	 * @param positionName Optional search criteria for the position name
	 */
	@Query(value = "select * from employee e inner join candidate c on e.person = c.id "
	        + "inner join position p on e.position = p.id "
	        + "where (:personName is null or lower(c.name) like lower(concat('%',:personName,'%')) or lower(c.lastname) like lower(concat('%',:personName,'%'))) "
	        + "and (:positionName is null or lower(p.name) like lower(concat('%',:positionName,'%')))", nativeQuery = true)
	public Collection<Employee> listByCriteria(@Param("personName") String personName,
	        @Param("positionName") String positionName);
}
