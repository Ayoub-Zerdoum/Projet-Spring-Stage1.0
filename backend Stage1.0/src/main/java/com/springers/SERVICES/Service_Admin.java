package com.springers.SERVICES;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springers.ENTITIES.AccountStatus;
import com.springers.ENTITIES.Admin;
import com.springers.ENTITIES.Departement;
import com.springers.ENTITIES.Offer;
import com.springers.ENTITIES.Privilege;
import com.springers.ENTITIES.Professor;
import com.springers.ENTITIES.Specialization;
import com.springers.ENTITIES.Student;
import com.springers.REPOSITORIES.AdminRepo;
import com.springers.REPOSITORIES.OfferRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class Service_Admin implements I_Service_Admin{
	@Autowired
	AdminRepo AdRepo;
	
	@Autowired
	Service_Offer S_Offer;
	
	@Autowired
	Service_EmailSender ServiceEmail;
	
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
	public Admin getAdminById(Long id) {
        return AdRepo.findById(id).orElse(null);
    }
	
	
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
	
	@Override
	@Transactional
    public void suspendAccount(Long Id) {
        // Retrieve the student entity from the database
		Admin admin = AdRepo.findById(Id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + Id));


        // Set the account status to SUSPENDED
        admin.setAccountStatus(AccountStatus.SUSPENDED);
    }
	
	@Override
	@Transactional
	public void activateAccount(Long Id) {
		Admin admin = AdRepo.findById(Id)
	            .orElseThrow(() -> new EntityNotFoundException("professor not found with id: " + Id));
	    admin.setAccountStatus(AccountStatus.ACTIVE);
	}
	
	@Override
	@Transactional
    public void editAdmin(Long adminId, Map<String, Object> adminData) {
        // Retrieve the Admin entity from the database
        Admin admin = AdRepo.findById(adminId)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found with id: " + adminId));

        // Update the admin entity with the new data
        if (adminData.containsKey("username")) {
        	admin.setUsername((String) adminData.get("username"));
        }
        if (adminData.containsKey("email")) {
        	admin.setEmail((String) adminData.get("email"));
        }
        if (adminData.containsKey("telephone")) {
        	admin.setTelephone((String) adminData.get("telephone"));
        }
        if (adminData.containsKey("password") && !adminData.get("password").equals("")) {
        	BCryptPasswordEncoder Bcrypt = new BCryptPasswordEncoder();
        	String password = (String) adminData.get("password");
        	admin.setPassword(Bcrypt.encode(password));
        	ServiceEmail.sendemail(admin.getEmail(), "Modification de mot de passe de l'application Stage1.0\n ",
        			"votre mot de pass a été modifé par un administrateur\\n "
            		+ "Votre nom d'utilisateur est :" + admin.getUsername() + "\nVotre nouveau Mot de passe  est :" + password);
            	System.out.print("email sent succesfully to :" + admin.getUsername());
        }
        if (adminData.containsKey("privilege")) {
            // Convert String to StudentStatus enum
        	Privilege privilege = Privilege.valueOf((String) adminData.get("privilege"));
        	admin.setPrivilege(privilege);
        }
        AdRepo.save(admin);
        
    }
	
	@Override
	public void ajouter_Offer(Long adminId,Map<String, Object> offerData) {		
        // Set properties of adminOffer as needed for the test
        Admin admin = AdRepo.findById(adminId)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found with id: " + adminId));
        
        Offer offre = new Offer();
        System.out.println(offerData.get("specialization"));
        if(offerData.containsKey("specialization")) {
        	Specialization specialization = Specialization.valueOf((String) offerData.get("specialization"));
        	offre.setSpecialization(specialization);
        }
        
        if(offerData.containsKey("Title")) {
        	offre.setTitle((String) offerData.get("Title"));
        }
        
        if(offerData.containsKey("Description"))
        offre.setDescription((String) offerData.get("Description"));
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(offerData.containsKey("deadline")) {
	        LocalDate deadline = LocalDate.parse((String) offerData.get("deadline"), formatter);
	        offre.setDeadline(deadline);
        }
        if(offerData.containsKey("NomSociete")) {
        offre.setNomSociete((String) offerData.get("NomSociete"));}
        if(offerData.containsKey("NbPlaces")) {
        offre.setNbPlaces((Integer) offerData.get("NbPlaces"));}
        if(offerData.containsKey("localisation")) {
        offre.setLocalisation((String) offerData.get("localisation"));}
        if(offerData.containsKey("MailRH")) {
        offre.setMailRH((String) offerData.get("MailRH"));}
        offre.setAdminoffer(admin);
        offre.setIsActive(true);
        
        S_Offer.ajouter_Offer(offre);
	}
}
