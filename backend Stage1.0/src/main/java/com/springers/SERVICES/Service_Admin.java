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
	
	public void ajouter_Admin(Admin admin) {
		Admin admin2 = AdRepo.save(admin);
	};
	public void supprimer_Admin(Long id) {
		AdRepo.deleteById(id);
	};
	public List<Admin> afficher_Admins(){
		List<Admin> admins = (List<Admin>) AdRepo.findAll();
		return admins;
	};
}