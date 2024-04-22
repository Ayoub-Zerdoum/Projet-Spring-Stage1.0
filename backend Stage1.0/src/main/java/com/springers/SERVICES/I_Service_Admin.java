package com.springers.SERVICES;

import java.util.List;
import java.util.Map;

import com.springers.ENTITIES.Admin;

public interface I_Service_Admin {
	public void ajouter_Admin(Admin ad);
	public void supprimer_Admin(Long id);
	public List<Admin> afficher_Admins();
	public List<Admin> searchAdminsByUsername(String usernameQuery);
	public List<Admin> searchAdminsByEmail(String emailQuery);
	public List<Admin> searchAdminsByTelephone(String telephoneQuery);
	public List<Admin> filterAdmins(String privilege, String accountStatus);
	public Admin getAdminById(Long id);
	public void suspendAccount(Long Id);
	public void activateAccount(Long Id);
	public void editAdmin(Long adminId, Map<String, Object> adminData);
	void ajouter_Offer(Long adminId, Map<String, Object> offerData);
}
