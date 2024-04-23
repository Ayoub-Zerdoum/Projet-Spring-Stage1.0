package com.springers.REPOSITORIES;

import com.springers.ENTITIES.AccountStatus;
import com.springers.ENTITIES.Admin;
import com.springers.ENTITIES.Privilege;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Long>{

	public List<Admin> findByUsernameContainingIgnoreCase(String usernameQuery);
	public List<Admin> findByEmailContainingIgnoreCase(String emailQuery);
	public List<Admin> findByTelephoneContainingIgnoreCase(String telephoneQuery);
	public List<Admin> findByPrivilegeAndAccountStatus(Privilege privilege, AccountStatus accountStatus);
	public List<Admin> findByPrivilege(Privilege privilege);
	public List<Admin> findByAccountStatus(AccountStatus accountStatus);
}
