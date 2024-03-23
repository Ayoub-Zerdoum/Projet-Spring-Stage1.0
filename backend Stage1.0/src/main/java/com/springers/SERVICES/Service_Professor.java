package com.springers.SERVICES;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springers.ENTITIES.AccountStatus;
import com.springers.ENTITIES.Departement;
import com.springers.ENTITIES.Professor;
import com.springers.REPOSITORIES.ProfessorRepo;

@Service
public class Service_Professor implements I_Service_Professor{
	@Autowired
	ProfessorRepo ProfRepo;

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
}
