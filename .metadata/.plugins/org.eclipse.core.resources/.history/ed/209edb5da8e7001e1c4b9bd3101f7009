package com.springers.CONTROLLERS;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;


import com.springers.ENTITIES.Admin;
import com.springers.SERVICES.Service_Admin;

@RestController
@CrossOrigin
@RequestMapping("/api/admins")
public class AdminController {
	@Autowired
    private Service_Admin adminService;

    @PostMapping("/add")
    public ResponseEntity<String> addAdmin(@RequestBody Admin admin) {
        adminService.ajouter_Admin(admin);
        return ResponseEntity.ok("Admin added successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Long id) {
        adminService.supprimer_Admin(id);
        return ResponseEntity.ok("Admin deleted successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminService.afficher_Admins();
        return ResponseEntity.ok(admins);
    }
}
