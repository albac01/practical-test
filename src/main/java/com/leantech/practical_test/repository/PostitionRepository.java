package com.leantech.practical_test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.leantech.practical_test.model.PersonPosition;

/**
 * Interface for Database CRUD operations for the PersonPosition table
 * 
 * @author abaquero
 * */
@Repository
public interface PostitionRepository extends CrudRepository<PersonPosition, Integer> {
	/**
	 * Find a position by position name
	 * @param name Position name
	 * @return Optional for the position found
	 * */
	@Query(value = "select * from Position where name=:name limit 1", nativeQuery = true)
	public Optional<PersonPosition> findByName(@Param("name") String name);
}
