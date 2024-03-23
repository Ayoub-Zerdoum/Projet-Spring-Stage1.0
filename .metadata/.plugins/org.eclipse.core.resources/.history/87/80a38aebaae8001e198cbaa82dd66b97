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
import org.springframework.web.bind.annotation.RequestParam;
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
    
    @GetMapping("/search-username")
    public ResponseEntity<List<Admin>> searchAdminsByUsername(@RequestParam("username") String usernameQuery) {
        List<Admin> admins = adminService.searchAdminsByUsername(usernameQuery);
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/search-email")
    public ResponseEntity<List<Admin>> searchAdminsByEmail(@RequestParam("email") String emailQuery) {
        List<Admin> admins = adminService.searchAdminsByEmail(emailQuery);
        return ResponseEntity.ok(admins);
    }
    
    @GetMapping("/search-telephone")
    public ResponseEntity<List<Admin>> searchAdminsByTelephone(@RequestParam("telephone") String telephoneQuery) {
        List<Admin> admins = adminService.searchAdminsByTelephone(telephoneQuery);
        return ResponseEntity.ok(admins);
    }
}
