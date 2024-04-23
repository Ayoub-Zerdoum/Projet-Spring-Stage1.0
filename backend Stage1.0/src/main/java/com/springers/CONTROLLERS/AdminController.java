package com.springers.CONTROLLERS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.springers.ENTITIES.AccountStatus;
import com.springers.ENTITIES.Admin;
import com.springers.ENTITIES.Offer;
import com.springers.ENTITIES.Privilege;
import com.springers.ENTITIES.Role;
import com.springers.ENTITIES.Student;
import com.springers.SERVICES.Service_Admin;
import com.springers.SERVICES.Service_EmailSender;
import com.springers.SERVICES.Service_Offer;

@RestController
@CrossOrigin
@RequestMapping("/api/admins")
public class AdminController {
	@Autowired
    private Service_Admin adminService;
	
	@Autowired
	private Service_Offer offreService;
	
	@Autowired
	private Service_EmailSender ServiceEmail;

	@Secured("ROLE_ADMIN")
    @PostMapping("/add")
    public ResponseEntity<String> addAdmin(@RequestBody Admin admin) {
        adminService.ajouter_Admin(admin);
        return ResponseEntity.ok("Admin added successfully");
    }
    
    @Secured("ROLE_ADMIN")
    @PostMapping("/addv2")
    public ResponseEntity<?> addAdmin(@RequestBody Map<String, Object> adminData) {
        // Extract data from the map
        String username = (String) adminData.get("username");
        String password = (String) adminData.get("password");
        String email = (String) adminData.get("email");
        String telephone = (String) adminData.get("telephone");
        Boolean sendVerificationEmail = (Boolean) adminData.get("sendVerificationEmail");


        String privilegeStr = (String) adminData.get("privilege");
        Privilege privilege = Privilege.valueOf(privilegeStr);

        // Determine the account status
        AccountStatus accountStatus = AccountStatus.ACTIVE;

        BCryptPasswordEncoder Bcrypt = new BCryptPasswordEncoder();
        Admin admin = Admin.builder()
                .username(username)
                .password(Bcrypt.encode(password))
                .email(email)
                .telephone(telephone)
                .role(Role.ADMIN)
                .privilege(privilege)
                .accountStatus(accountStatus)
                .build();

        // Call the service method to add the admin
        adminService.ajouter_Admin(admin);
        
        if(sendVerificationEmail == true) {
        	ServiceEmail.sendemail(email, "Invitation a rejoindre l'application Stage1.0",
        		"L'administration de l'Enicar vous a inviter pour rejoindre l'application Stage1.0\n "
        		+ "Votre nom d'utilisateur est :" + admin.getUsername() + "\nVotre Mot de passe  est :" + password);
        	System.out.print("email sent succesfully to :" + admin.getUsername());
        }
        

        // Create a response map with a success message
        Map<String, String> response = new HashMap<>();
        response.put("message", "Admin added successfully");

        // Return the response map as JSON
        return ResponseEntity.ok(response);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String,String>> deleteAdmin(@PathVariable Long id) {
        adminService.supprimer_Admin(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Professor deleted successfully");
        
        return ResponseEntity.ok(response);
    }
    
    @Secured("ROLE_ADMIN")
    @GetMapping("/all")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminService.afficher_Admins();
        return ResponseEntity.ok(admins);
    }
    
    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable("id") Long id) {
        Admin admin = adminService.getAdminById(id);
        if (admin != null) {
            return ResponseEntity.ok(admin);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @Secured("ROLE_ADMIN")
    @GetMapping("/search-username")
    public ResponseEntity<List<Admin>> searchAdminsByUsername(@RequestParam("username") String usernameQuery) {
        List<Admin> admins = adminService.searchAdminsByUsername(usernameQuery);
        return ResponseEntity.ok(admins);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/search-email")
    public ResponseEntity<List<Admin>> searchAdminsByEmail(@RequestParam("email") String emailQuery) {
        List<Admin> admins = adminService.searchAdminsByEmail(emailQuery);
        return ResponseEntity.ok(admins);
    }
    
    @Secured("ROLE_ADMIN")
    @GetMapping("/search-telephone")
    public ResponseEntity<List<Admin>> searchAdminsByTelephone(@RequestParam("telephone") String telephoneQuery) {
        List<Admin> admins = adminService.searchAdminsByTelephone(telephoneQuery);
        return ResponseEntity.ok(admins);
    }
    
    @Secured("ROLE_ADMIN")
    @GetMapping("/filter")
    public ResponseEntity<List<Admin>> filterAdmins(@RequestParam(required = false) String privilege,
                                                    @RequestParam(required = false) String accountStatus) {
        List<Admin> filteredAdmins = adminService.filterAdmins(privilege, accountStatus);
        return ResponseEntity.ok(filteredAdmins);
    }
    
    @Secured("ROLE_ADMIN")
    @PutMapping("/suspend/{id}")
    public ResponseEntity<Map<String,String>> suspendAccount(@PathVariable Long id) {
        adminService.suspendAccount(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Admin account suspended successfully");

        // Return the response map as JSON
        return ResponseEntity.ok(response);
    }
    
    @Secured("ROLE_ADMIN")
    @PutMapping("/activate/{id}")
    public ResponseEntity<Map<String, String>> activateAccount(@PathVariable Long id) {
        adminService.activateAccount(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Student account activated successfully");
        return ResponseEntity.ok(response);
    }
    
    @Secured("ROLE_ADMIN")
    @PutMapping("/edit/{id}")
    public ResponseEntity<Map<String, String>> editAdmin(@PathVariable Long id, @RequestBody Map<String, Object> adminData) {
        adminService.editAdmin(id, adminData);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Admin edited successfully");
        return ResponseEntity.ok(response);
    }
    
    @Secured("ROLE_ADMIN")
    @PutMapping("/offre/edit/{offreId}")
    public ResponseEntity<Map<String, String>> editOffre(@PathVariable Long offreId, @RequestBody Map<String, Object> offreData) {
        offreService.editOffer(offreId, offreData);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Offre edited successfully");
        return ResponseEntity.ok(response);
    }
    
    @Secured("ROLE_ADMIN")
    @PostMapping("/offre/add")
    public ResponseEntity<Map<String, String>> ajouterOffre(@PathVariable Long adminId,@RequestBody Map<String, Object> offreData){
    	adminService.ajouter_Offer(adminId, offreData);
    	
    	Map<String, String> response = new HashMap<>();
        response.put("message", "Offre ajouter successfully");
        return ResponseEntity.ok(response);
    }
    
    @Secured("ROLE_ADMIN")
    @GetMapping("/offre/all")
    public ResponseEntity<List<Offer>> afficherOffres(@RequestParam(defaultValue = "0") int page,
												              @RequestParam(defaultValue = "5") int size){
    	List<Offer> offers = offreService.afficher_Offers(page, size);
        return ResponseEntity.ok(offers);
    }
    
    @Secured({"ROLE_STUDENT","ROLE_ADMIN"})
    @GetMapping("/user")
    public String helloUser() {
		System.out.println("hello user");
        return "Hello User";
    }

    
}
