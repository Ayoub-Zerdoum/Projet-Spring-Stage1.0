package com.springers.SERVICES;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springers.ENTITIES.AccountStatus;
import com.springers.ENTITIES.Departement;
import com.springers.ENTITIES.Professor;
import com.springers.ENTITIES.Specialization;
import com.springers.ENTITIES.Student;
import com.springers.ENTITIES.StudentStatus;
import com.springers.REPOSITORIES.ProfessorRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class Service_Professor implements I_Service_Professor{
	@Autowired
	ProfessorRepo ProfRepo;
	
	@Autowired
	Service_EmailSender ServiceEmail;

	@Override
	public void ajouter_Prof(Professor prof){
		Professor prof2 = ProfRepo.save(prof);
	}

	@Override
	public void supprimer_Prof(Long id){
		ProfRepo.deleteById(id);
	}

	@Override
	public List<Professor> afficher_Profs(){
		List<Professor> students = (List<Professor>) ProfRepo.findAll();
		return students;
	}
	
	@Override
	public Professor getProfessorById(Long id) {
        return ProfRepo.findById(id).orElse(null);
    }
	
	@Override
	public List<Professor> searchProfessorsByUsername(String usernameQuery) {
	    return ProfRepo.findByUsernameContainingIgnoreCase(usernameQuery);
	}

	@Override
	public List<Professor> searchProfessorsByEmail(String emailQuery) {
	    return ProfRepo.findByEmailContainingIgnoreCase(emailQuery);
	}

	@Override
	public List<Professor> searchProfessorsByTelephone(String telephoneQuery) {
	    return ProfRepo.findByTelephoneContainingIgnoreCase(telephoneQuery);
	}
	
	@Override
	public List<Professor> filterProfessors(String department, String accountStatus) {
        Departement departmentEnum = department != null ? Departement.valueOf(department) : null;
        AccountStatus accountStatusEnum = accountStatus != null ? AccountStatus.valueOf(accountStatus) : null;
        
        if (departmentEnum != null && accountStatusEnum != null) {
            return ProfRepo.findByDepartmentAndAccountStatus(departmentEnum, accountStatusEnum);
        } else if (departmentEnum != null) {
            return ProfRepo.findByDepartment(departmentEnum);
        } else if (accountStatusEnum != null) {
            return ProfRepo.findByAccountStatus(accountStatusEnum);
        } else {
            return (List<Professor>) ProfRepo.findAll(); // No filters, return all professors
        }
    }
	
	@Transactional
    public void suspendAccount(Long Id) {
        // Retrieve the student entity from the database
		Professor prof = ProfRepo.findById(Id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + Id));


        // Set the account status to SUSPENDED
        prof.setAccountStatus(AccountStatus.SUSPENDED);
    }
	
	@Override
	@Transactional
	public void activateAccount(Long Id) {
	    Professor prof = ProfRepo.findById(Id)
	            .orElseThrow(() -> new EntityNotFoundException("prof not found with id: " + Id));
	    prof.setAccountStatus(AccountStatus.ACTIVE);
	}
	
	@Override
	@Transactional
    public void editProfessor(Long profId, Map<String, Object> profData) {
        // Retrieve the prof entity from the database
        Professor prof = ProfRepo.findById(profId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + profId));

        // Update the prof entity with the new data
        if (profData.containsKey("username")) {
        	prof.setUsername((String) profData.get("username"));
        }
        if (profData.containsKey("email")) {
        	prof.setEmail((String) profData.get("email"));
        }
        if (profData.containsKey("telephone")) {
        	prof.setTelephone((String) profData.get("telephone"));
        }
        if (profData.containsKey("password") && !profData.get("password").equals("")) {
        	BCryptPasswordEncoder Bcrypt = new BCryptPasswordEncoder();
        	String password = (String) profData.get("password");
        	prof.setPassword(Bcrypt.encode(password));
        	ServiceEmail.sendemail(prof.getEmail(), "Modification de mot de passe de l'application Stage1.0 ",
            		"votre mot de pass a été modifé par un administrateur\n "
            		+ "Votre nom d'utilisateur est :" + prof.getUsername() + "\nVotre Mot de passe  est :" + password);
            	System.out.print("email sent succesfully to :" + prof.getUsername());
        }
        if (profData.containsKey("department")) {
            // Convert String to StudentStatus enum
        	Departement dep = Departement.valueOf((String) profData.get("department"));
            prof.setDepartment(dep);
        }
        ProfRepo.save(prof);
        
    }
}
