package com.springers.REPOSITORIES;

import com.springers.ENTITIES.AccountStatus;
import com.springers.ENTITIES.Departement;
import com.springers.ENTITIES.Professor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepo extends JpaRepository<Professor, Long>{
	public List<Professor> findByUsernameContainingIgnoreCase(String usernameQuery);
	public List<Professor> findByEmailContainingIgnoreCase(String emailQuery);
	public List<Professor> findByTelephoneContainingIgnoreCase(String telephoneQuery);
	public List<Professor> findByDepartment(Departement department);
	public List<Professor> findByAccountStatus(AccountStatus accountStatus);
	public List<Professor> findByDepartmentAndAccountStatus(Departement department, AccountStatus accountStatus);
	

}
