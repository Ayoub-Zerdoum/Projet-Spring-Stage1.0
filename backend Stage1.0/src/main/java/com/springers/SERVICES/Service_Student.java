package com.springers.SERVICES;

import org.springframework.transaction.annotation.Transactional;
import com.springers.ENTITIES.AccountStatus;
import com.springers.ENTITIES.Specialization;
import com.springers.ENTITIES.Student;
import com.springers.ENTITIES.StudentStatus;
import com.springers.REPOSITORIES.*;

import jakarta.persistence.EntityNotFoundException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Service_Student implements I_Service_Student{
	@Autowired
	StudentRepo StdRepo;

	@Override
	public void ajouter_Student(Student std){
		Student std2 = StdRepo.save(std);
	}

	@Override
	public void supprimer_Student(Long id){
		StdRepo.deleteById(id);
	}

	@Override
	public List<Student> afficher_Students(){
		List<Student> students = (List<Student>) StdRepo.findAll();
		return students;
	}
	
	@Override
	public Student getStudentById(Long id) {
        return StdRepo.findById(id).orElse(null);
    }
	
	@Override
	public List<Student> searchStudentsByUsername(String usernameQuery) {
		List<Student> students = (List<Student>) StdRepo.findByUsernameContainingIgnoreCase(usernameQuery);
		return students;
    }

	@Override
	public List<Student> searchStudentsByEmail(String emailQuery) {
        return StdRepo.findByEmailContainingIgnoreCase(emailQuery);
    }

	@Override
    public List<Student> searchStudentsByTelephone(String telephoneQuery) {
        return StdRepo.findByTelephoneContainingIgnoreCase(telephoneQuery);
    }
	
	@Override
	public List<Student> searchStudentsByDateOfBirth(String dateOfBirthQuery) {
		Date dateOfBirth = Date.valueOf(dateOfBirthQuery);
        return StdRepo.findByDateOfBirth(dateOfBirth);
    }
	
	@Override
    public List<Student> filterStudents(String status, String specialization, String accountStatus, LocalDate dobMin, LocalDate dobMax) {
		// Convert string status to enum
	    StudentStatus studentStatusEnum = null;
	    if (status != null && !status.isEmpty()) {
	        studentStatusEnum = StudentStatus.valueOf(status.toUpperCase());
	    }
	    
	 // Convert string specialization to enum
	    Specialization specializationEnum = null;
	    if (specialization != null && !specialization.isEmpty()) {
	        specializationEnum = Specialization.valueOf(specialization.toUpperCase());
	    }

	    // Convert string accountStatus to enum
	    AccountStatus accountStatusEnum = null;
	    if (accountStatus != null && !accountStatus.isEmpty()) {
	        accountStatusEnum = AccountStatus.valueOf(accountStatus.toUpperCase());
	    }

	    // Call repository method with enum values
	    return StdRepo.filterStudents(studentStatusEnum, specializationEnum, accountStatusEnum, dobMin, dobMax);
	}
    
	@Transactional
    public void suspendAccount(Long studentId) {
        // Retrieve the student entity from the database
		Student student = StdRepo.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentId));


        // Set the account status to SUSPENDED
        student.setAccountStatus(AccountStatus.SUSPENDED);

        // Save the updated student entity
        StdRepo.save(student);
    }
}

