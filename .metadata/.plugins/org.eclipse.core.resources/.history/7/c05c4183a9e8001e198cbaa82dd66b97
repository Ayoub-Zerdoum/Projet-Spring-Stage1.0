package com.springers.REPOSITORIES;

import com.springers.ENTITIES.Professor;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepo extends CrudRepository<Professor, Long>{
	public List<Professor> findByUsernameContainingIgnoreCase(String usernameQuery);
	public List<Professor> findByEmailContainingIgnoreCase(String emailQuery);
	public List<Professor> findByTelephoneContainingIgnoreCase(String telephoneQuery);

}
