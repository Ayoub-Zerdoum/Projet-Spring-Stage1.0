package com.springers.CONTROLLERS;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import com.springers.ENTITIES.Societe;
import com.springers.SERVICES.I_Service_Societe;

@RestController
@CrossOrigin
@RequestMapping("/api/societes")

public class SocieteController {
    @Autowired
    private I_Service_Societe societeService;

    @Secured({"ROLE_ADMIN","ROLE_STUDENT"})
    @PostMapping("/add")
    public ResponseEntity<String> addSociete(@RequestBody Societe societe) {
        societeService.ajouter_Societe(societe);
        return ResponseEntity.ok("Société ajoutée avec succès");
    }

    @Secured({"ROLE_ADMIN","ROLE_STUDENT"})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteSociete(@PathVariable Long id) {
        societeService.supprimer_Societe(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Société supprimée avec succès");
        return ResponseEntity.ok(response);
    }

    @Secured({"ROLE_ADMIN","ROLE_STUDENT"})
    @GetMapping("/{id}")
    public ResponseEntity<Societe> getSocieteById(@PathVariable("id") Long id) {
        Societe societe = societeService.getSocieteById(id);
        if (societe != null) {
            return ResponseEntity.ok(societe);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Secured({"ROLE_ADMIN","ROLE_STUDENT"})
    @GetMapping("/all")
    public ResponseEntity<List<Societe>> getAllSocietes() {
        List<Societe> societes = societeService.afficher_Societes();
        return ResponseEntity.ok(societes);
    }

}