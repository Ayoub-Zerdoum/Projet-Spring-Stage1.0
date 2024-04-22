package com.springers.REPOSITORIES;

import com.springers.ENTITIES.AccountStatus;
import com.springers.ENTITIES.Specialization;
import com.springers.ENTITIES.Student;
import com.springers.ENTITIES.StudentStatus;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long>{
	List<Student> findByUsernameContainingIgnoreCase(String usernameQuery);
	List<Student> findByEmailContainingIgnoreCase(String email);
    List<Student> findByTelephoneContainingIgnoreCase(String telephone);
	List<Student> findByDateOfBirth(Date dateOfBirth);
	
	@Query("SELECT s FROM Student s WHERE (:status IS NULL OR s.studentStatus = :status) " +
	           "AND (:specialization IS NULL OR s.specialization = :specialization) " +
	           "AND (:accountStatus IS NULL OR s.accountStatus = :accountStatus) " +
	           "AND (:dobMin IS NULL OR s.dateOfBirth >= :dobMin) " +
	           "AND (:dobMax IS NULL OR s.dateOfBirth <= :dobMax)")
	    List<Student> filterStudents(@Param("status") StudentStatus status,
	                                 @Param("specialization") Specialization specialization,
	                                 @Param("accountStatus") AccountStatus accountStatus,
	                                 @Param("dobMin") LocalDate dobMin,
	                                 @Param("dobMax") LocalDate dobMax);
	
}
