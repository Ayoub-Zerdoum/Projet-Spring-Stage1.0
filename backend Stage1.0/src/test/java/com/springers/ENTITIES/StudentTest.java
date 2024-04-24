package com.springers.ENTITIES;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

class StudentTest {
	
	@Test
    public void testStudentConstructor() {
		// Create a new student instance using the constructor
		Student student = Student.builder()
			    .userId(1L)
			    .password("password123")
			    .email("student@example.com")
			    .username("John Doe")
			    .telephone("1234567890")
			    .accountStatus(AccountStatus.ACTIVE)
			    .studentStatus(StudentStatus.ENROLLED)
			    .specialization(Specialization.INFORMATIQUE)
			    .build();

		// Check if the attributes are set correctly
        assertEquals(1L, student.getUserId(), "User ID should be 1");
        assertEquals("password123", student.getPassword(), "Password should be password123");
        assertEquals("student@example.com", student.getEmail(), "Email should be student@example.com");
        assertEquals("John Doe", student.getUsername(), "Name should be John Doe");
        assertEquals("1234567890", student.getTelephone(), "Telephone should be 1234567890");
        assertEquals(AccountStatus.ACTIVE, student.getAccountStatus(), "Account status should be ACTIVE");
        assertEquals(StudentStatus.ENROLLED, student.getStudentStatus(), "Student status should be NIVEAU_1");
        assertEquals(Specialization.INFORMATIQUE, student.getSpecialization(), "Specialization should be INFORMATIQUE");
        
     
    }

}
