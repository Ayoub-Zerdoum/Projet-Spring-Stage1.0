package com.springers.SERVICES;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springers.ENTITIES.Admin;
import com.springers.REPOSITORIES.AdminRepo;

@Service
public class Service_Admin implements I_Service_Admin{
	@Autowired
	AdminRepo AdRepo;
	
	@Override
	public void ajouter_Admin(Admin admin) {
		Admin admin2 = AdRepo.save(admin);
	};
	
	@Override
	public void supprimer_Admin(Long id) {
		AdRepo.deleteById(id);
	};
	
	@Override
	public List<Admin> afficher_Admins(){
		List<Admin> admins = (List<Admin>) AdRepo.findAll();
		return admins;
	};
	
	@Override
	public List<Admin> searchAdminsByUsername(String usernameQuery) {
	    return AdRepo.findByUsernameContainingIgnoreCase(usernameQuery);
	}

	@Override
	public List<Admin> searchAdminsByEmail(String emailQuery) {
	    return AdRepo.findByEmailContainingIgnoreCase(emailQuery);
	}

	@Override
	public List<Admin> searchAdminsByTelephone(String telephoneQuery) {
	    return AdRepo.findByTelephoneContainingIgnoreCase(telephoneQuery);
	}
}
