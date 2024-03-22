package com.springers.REPOSITORIES;

import com.springers.ENTITIES.Admin;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepo extends CrudRepository<Admin, Long>{

	public List<Admin> findByUsernameContainingIgnoreCase(String usernameQuery);
	public List<Admin> findByEmailContainingIgnoreCase(String emailQuery);
	public List<Admin> findByTelephoneContainingIgnoreCase(String telephoneQuery);

}
