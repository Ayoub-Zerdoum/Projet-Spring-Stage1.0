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
}
