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

import com.springers.ENTITIES.Professor;
import com.springers.SERVICES.Service_Professor;

@RestController
@CrossOrigin
@RequestMapping("/api/professors")
public class ProfessorController {
	@Autowired
    private Service_Professor professorService;

    @PostMapping("/add")
    public ResponseEntity<String> addProf(@RequestBody Professor prof) {
    	professorService.ajouter_Prof(prof);
        return ResponseEntity.ok("Professor added successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProf(@PathVariable Long id) {
    	professorService.supprimer_Prof(id);
        return ResponseEntity.ok("Professor deleted successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Professor>> getAllProfs() {
        List<Professor> profs = professorService.afficher_Profs();
        return ResponseEntity.ok(profs);
    }
    
    @GetMapping("/search-username")
    public ResponseEntity<List<Professor>> searchProfessorsByUsername(@RequestParam("username") String usernameQuery) {
        List<Professor> professors = professorService.searchProfessorsByUsername(usernameQuery);
        return ResponseEntity.ok(professors);
    }

    @GetMapping("/search-email")
    public ResponseEntity<List<Professor>> searchProfessorsByEmail(@RequestParam("email") String emailQuery) {
        List<Professor> professors = professorService.searchProfessorsByEmail(emailQuery);
        return ResponseEntity.ok(professors);
    }
    
    @GetMapping("/search-telephone")
    public ResponseEntity<List<Professor>> searchProfessorsByTelephone(@RequestParam("telephone") String telephoneQuery) {
        List<Professor> professors = professorService.searchProfessorsByTelephone(telephoneQuery);
        return ResponseEntity.ok(professors);
    }
    
    @GetMapping("/filter")
    public ResponseEntity<List<Professor>> filterProfessors(@RequestParam(required = false) String department,
                                                            @RequestParam(required = false) String accountStatus) {
        List<Professor> filteredProfessors = professorService.filterProfessors(department, accountStatus);
        return ResponseEntity.ok(filteredProfessors);
    }
}
