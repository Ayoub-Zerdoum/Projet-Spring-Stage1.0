package com.springers.CONTROLLERS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springers.ENTITIES.AccountStatus;
import com.springers.ENTITIES.Departement;
import com.springers.ENTITIES.Professor;
import com.springers.ENTITIES.Role;
import com.springers.SERVICES.Service_EmailSender;
import com.springers.SERVICES.Service_Professor;

@RestController
@CrossOrigin
@RequestMapping("/api/professors")
public class ProfessorController {
	@Autowired
    private Service_Professor professorService;
	
	@Autowired
	private Service_EmailSender ServiceEmail;

	@Secured("ROLE_ADMIN")
    @PostMapping("/add")
    public ResponseEntity<String> addProf(@RequestBody Professor prof) {
    	professorService.ajouter_Prof(prof);
        return ResponseEntity.ok("Professor added successfully");
    }

	@Secured("ROLE_ADMIN")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteProfessor(@PathVariable Long id) {
    	professorService.supprimer_Prof(id);
    	Map<String, String> response = new HashMap<>();
        response.put("message", "Professor deleted successfully");
        
        return ResponseEntity.ok(response);
    }

	@Secured("ROLE_ADMIN")
    @GetMapping("/all")
    public ResponseEntity<List<Professor>> getAllProfs() {
        List<Professor> profs = professorService.afficher_Profs();
        return ResponseEntity.ok(profs);
    }
    
	@Secured({"ROLE_ADMIN","ROLE_PROFESSOR"})
    @GetMapping("/{id}")
    public ResponseEntity<Professor> getProfessorById(@PathVariable("id") Long id) {
    	Professor prof = professorService.getProfessorById(id);
        if (prof != null) {
            return ResponseEntity.ok(prof);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    
	@Secured("ROLE_ADMIN")
    @GetMapping("/search-username")
    public ResponseEntity<List<Professor>> searchProfessorsByUsername(@RequestParam("username") String usernameQuery) {
        List<Professor> professors = professorService.searchProfessorsByUsername(usernameQuery);
        return ResponseEntity.ok(professors);
    }

	@Secured("ROLE_ADMIN")
    @GetMapping("/search-email")
    public ResponseEntity<List<Professor>> searchProfessorsByEmail(@RequestParam("email") String emailQuery) {
        List<Professor> professors = professorService.searchProfessorsByEmail(emailQuery);
        return ResponseEntity.ok(professors);
    }
    
	@Secured("ROLE_ADMIN")
    @GetMapping("/search-telephone")
    public ResponseEntity<List<Professor>> searchProfessorsByTelephone(@RequestParam("telephone") String telephoneQuery) {
        List<Professor> professors = professorService.searchProfessorsByTelephone(telephoneQuery);
        return ResponseEntity.ok(professors);
    }
    
	@Secured("ROLE_ADMIN")
    @GetMapping("/filter")
    public ResponseEntity<List<Professor>> filterProfessors(@RequestParam(required = false) String department,
                                                            @RequestParam(required = false) String accountStatus) {
        List<Professor> filteredProfessors = professorService.filterProfessors(department, accountStatus);
        return ResponseEntity.ok(filteredProfessors);
    }
    
	@Secured("ROLE_ADMIN")
    @PostMapping("/addv2")
    public ResponseEntity<?> addProfessor(@RequestBody Map<String, Object> professorData) {
        // Extract data from the map
        String username = (String) professorData.get("username");
        String password = (String) professorData.get("password");
        String email = (String) professorData.get("email");
        String telephone = (String) professorData.get("telephone");
        Boolean sendVerificationEmail = (Boolean) professorData.get("sendVerificationEmail");

        // Convert the department string to enum
        String departmentStr = (String) professorData.get("department");
        Departement department = Departement.valueOf(departmentStr);

        // Determine the account status
        AccountStatus accountStatus = AccountStatus.ACTIVE;

        BCryptPasswordEncoder Bcrypt = new BCryptPasswordEncoder();
        // Create the Professor object using builder pattern
        Professor professor = Professor.builder()
                .username(username)
                .password(Bcrypt.encode(password))
                .role(Role.PROFESSOR)
                .email(email)
                .telephone(telephone)
                .department(department)
                .accountStatus(accountStatus)
                .build();

        // Call the service method to add the professor
        professorService.ajouter_Prof(professor);
        
        if(sendVerificationEmail == true) {
        	ServiceEmail.sendemail(email, "Invitation a rejoindre l'application Stage1.0",
        		"L'administration de l'Enicar vous a inviter pour rejoindre l'application Stage1.0\n "
        		+ "Votre nom d'utilisateur est :" + professor.getUsername() + "\nVotre Mot de passe  est :" + password);
        	System.out.print("email sent succesfully to :" + professor.getUsername());
        }

        // Create a response map with a success message
        Map<String, String> response = new HashMap<>();
        response.put("message", "Professor added successfully");

        // Return the response map as JSON
        return ResponseEntity.ok(response);
    }
    
	@Secured("ROLE_ADMIN")
    @PutMapping("/suspend/{id}")
    public ResponseEntity<Map<String,String>> suspendAccount(@PathVariable Long id) {
        professorService.suspendAccount(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Professor account suspended successfully");

        // Return the response map as JSON
        return ResponseEntity.ok(response);
    }
    
	@Secured("ROLE_ADMIN")
    @PutMapping("/activate/{id}")
    public ResponseEntity<Map<String, String>> activateAccount(@PathVariable Long id) {
        professorService.activateAccount(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Student account activated successfully");
        return ResponseEntity.ok(response);
    }
    
	@Secured("ROLE_ADMIN")
    @PutMapping("/edit/{id}")
    public ResponseEntity<Map<String,String>> editStudent(@PathVariable Long id, @RequestBody Map<String, Object> profData) {
        professorService.editProfessor(id, profData);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Professor edited successfully");

        // Return the response map as JSON
        return ResponseEntity.ok(response);
    }
    
}
