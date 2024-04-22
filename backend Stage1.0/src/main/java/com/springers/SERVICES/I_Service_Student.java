package com.springers.SERVICES;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.springers.ENTITIES.Student;

public interface I_Service_Student {
	public void ajouter_Student(Student std);
	public void supprimer_Student(Long id);
	public List<Student> afficher_Students();
	public List<Student> searchStudentsByUsername(String usernameQuery);
	public List<Student> searchStudentsByEmail(String emailQuery);
	public List<Student> searchStudentsByTelephone(String telephoneQuery);
	public List<Student> searchStudentsByDateOfBirth(String dateOfBirthQuery);
	
	public List<Student> filterStudents(String status, String specialization, String accountStatus, LocalDate dobMin, LocalDate dobMax);
	public Student getStudentById(Long id);
	public void suspendAccount(Long Id);
	public void activateAccount(Long Id);
	public void editStudent(Long studentId, Map<String, Object> studentData);
	void ReserveOffer(Long offerId, Long studentId);

}
