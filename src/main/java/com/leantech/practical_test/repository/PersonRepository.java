package com.leantech.practical_test.repository;

import org.springframework.stereotype.Repository;

import com.leantech.practical_test.model.Person;

/**
 * Interface for Database CRUD operations for the Person table
 * 
 * @author abaquero
 * */
@Repository
public interface PersonRepository extends org.springframework.data.repository.CrudRepository<Person, String> {

}
