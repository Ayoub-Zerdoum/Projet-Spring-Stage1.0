package com.springers.CONTROLLERS;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import com.springers.ENTITIES.Encadreur;
import com.springers.SERVICES.I_Service_Encadreur;

@RestController
@CrossOrigin
@RequestMapping("/api/encadreurs")
public class EncadreurController {
    @Autowired
    private I_Service_Encadreur encadreurService;

    @Secured({"ROLE_ADMIN","ROLE_STUDENT"})
    @PostMapping("/add")
    public ResponseEntity<String> addEncadreur(@RequestBody Encadreur encadreur) {
        encadreurService.ajouter_Encadreur(encadreur);
        return ResponseEntity.ok("Encadreur ajouté avec succès");
    }

    @Secured({"ROLE_ADMIN","ROLE_STUDENT"})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteEncadreur(@PathVariable Long id) {
        encadreurService.supprimer_Encadreur(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Encadreur supprimé avec succès");
        return ResponseEntity.ok(response);
    }

    @Secured({"ROLE_ADMIN","ROLE_STUDENT"})
    @GetMapping("/{id}")
    public ResponseEntity<Encadreur> getEncadreurById(@PathVariable("id") Long id) {
        Encadreur encadreur = encadreurService.getEncadreurById(id);
        if (encadreur != null) {
            return ResponseEntity.ok(encadreur);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/all")
    public ResponseEntity<List<Encadreur>> getAllEncadreurs() {
        List<Encadreur> encadreurs = encadreurService.afficher_Encadreurs();
        return ResponseEntity.ok(encadreurs);
    }
   
    @Secured({"ROLE_ADMIN","ROLE_STUDENT"})
    @PutMapping("/update/{id}")
public ResponseEntity<Map<String, String>> updateEncadreur(@PathVariable Long id, @RequestBody Map<String, Object> updatedEncadreurData) {
    encadreurService.updateEncadreur(id, updatedEncadreurData);
    Map<String, String> response = new HashMap<>();
    response.put("message", "Encadreur updated successfully");
    return ResponseEntity.ok(response);
}

   
}
