package com.springers.CONTROLLERS;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springers.ENTITIES.Student;
import com.springers.SERVICES.Service_Student;

@RestController
@CrossOrigin
@RequestMapping("/api/students")
public class StudentController {
	@Autowired
    private Service_Student studentService;

    @PostMapping("/add")
    public ResponseEntity<String> addAdmin(@RequestBody Student std) {
    	studentService.ajouter_Student(std);
        return ResponseEntity.ok("Student added successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
    	studentService.supprimer_Student(id);
        return ResponseEntity.ok("Student deleted successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.afficher_Students();
        return ResponseEntity.ok(students);
    }
    
    @GetMapping("/search-username")
    public ResponseEntity<List<Student>> searchStudentsByUsername(@RequestParam("username") String usernameQuery) {
        List<Student> students = studentService.searchStudentsByUsername(usernameQuery);
        return ResponseEntity.ok(students);
    }
    
    @GetMapping("/search-email")
    public ResponseEntity<List<Student>> searchStudentsByEmail(@RequestParam("email") String emailQuery) {
        List<Student> students = studentService.searchStudentsByEmail(emailQuery);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/search-telephone")
    public ResponseEntity<List<Student>> searchStudentsByTelephone(@RequestParam("telephone") String telephoneQuery) {
        List<Student> students = studentService.searchStudentsByTelephone(telephoneQuery);
        return ResponseEntity.ok(students);
    }
}
