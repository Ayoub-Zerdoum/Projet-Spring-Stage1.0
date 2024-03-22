package com.springers.SERVICES;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
