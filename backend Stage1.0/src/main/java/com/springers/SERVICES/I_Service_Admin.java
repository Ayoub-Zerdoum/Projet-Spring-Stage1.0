package com.springers.SERVICES;

import java.util.List;

import com.springers.ENTITIES.Admin;

public interface I_Service_Admin {
	public void ajouter_Admin(Admin ad);
	public void supprimer_Admin(Long id);
	public List<Admin> afficher_Admins();
}