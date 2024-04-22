package com.springers.SERVICES;

import java.util.List;
import java.util.Map;

import com.springers.ENTITIES.Professor;

public interface I_Service_Professor {
	public void ajouter_Prof(Professor ad);
	public void supprimer_Prof(Long id);
	public List<Professor> afficher_Profs();
	public List<Professor> searchProfessorsByUsername(String usernameQuery);
	public List<Professor> searchProfessorsByEmail(String emailQuery);
	public List<Professor> searchProfessorsByTelephone(String telephoneQuery);
	public List<Professor> filterProfessors(String department, String accountStatus);
	public Professor getProfessorById(Long id);
	public void suspendAccount(Long Id);
	public void activateAccount(Long Id);
	public void editProfessor(Long professorId, Map<String, Object> professorData);
}
