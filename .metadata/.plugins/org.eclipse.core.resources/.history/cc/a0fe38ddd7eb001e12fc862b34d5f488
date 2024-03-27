package com.springers.SERVICES;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springers.ENTITIES.AccountStatus;
import com.springers.ENTITIES.Admin;
import com.springers.ENTITIES.Privilege;
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
	
	@Override
	public List<Admin> filterAdmins(String privilege, String accountStatus) {
        Privilege privilegeEnum = privilege != null ? Privilege.valueOf(privilege) : null;
        AccountStatus accountStatusEnum = accountStatus != null ? AccountStatus.valueOf(accountStatus) : null;
        
        if (privilegeEnum != null && accountStatusEnum != null) {
            return AdRepo.findByPrivilegeAndAccountStatus(privilegeEnum, accountStatusEnum);
        } else if (privilegeEnum != null) {
            return AdRepo.findByPrivilege(privilegeEnum);
        } else if (accountStatusEnum != null) {
            return AdRepo.findByAccountStatus(accountStatusEnum);
        } else {
            return (List<Admin>) AdRepo.findAll(); // No filters, return all admins
        }
    }
}
