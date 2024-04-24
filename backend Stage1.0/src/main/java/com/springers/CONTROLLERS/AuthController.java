package com.springers.CONTROLLERS;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springers.ENTITIES.Admin;
import com.springers.ENTITIES.LoginRequest;
import com.springers.ENTITIES.User;
import com.springers.REPOSITORIES.UserRepo;
import com.springers.SERVICES.AuthService;
import com.springers.SERVICES.Service_EmailSender;
import com.springers.UTILITIS.JwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	/*
	@Autowired
    private AuthService authService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private UserRepo userRepo;*/
	
	@Autowired
	private Service_EmailSender ServiceEmail;
	
    private final AuthService authService;
	private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    
    public AuthController(AuthService authService,UserRepo userRepo,PasswordEncoder passwordEncoder,JwtService jwtService,AuthenticationManager authManager) {
    	this.authService = authService;
    	this.userRepo = userRepo;
    	this.passwordEncoder = passwordEncoder;
    	this.jwtService = jwtService;
    	this.authManager = authManager;
    }
	
	
	@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    	String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        //System.out.println(username + "  "+ password);
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );
        System.out.println(username + "  "+ password);
        User user = userRepo.findByUsername(username)
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        System.out.println(jwtToken);
     // Create a Map to hold user and token information
        Map<String, Object> response = new HashMap<>();
        response.put("user", user);
        response.put("token", jwtToken);

        // Return the response map
        return ResponseEntity.ok(response);
	}
	
	
	@PostMapping("/user/sendPasswordVerificationEmail")
    public ResponseEntity<Map<String, String>> sendVerificationEmail(@RequestBody String email,String code) {
        Map<String, String> response = new HashMap<>();
        User user = userRepo.findByEmail(email)
        		.orElseThrow(() -> new NoSuchElementException("User not found"));
        if(user!=null) {
        	ServiceEmail.sendemail(email, "Reset mot de pass Stage1.0",
            		"vous avez demander de reset le mot de pass de  votre compte à Stage1.0\n "
            		+ "Votre nom d'utilisateur est :" + user.getUsername() + "\nle code de verification est :" + code);
            	System.out.print("email sent succesfully to :" + user.getUsername());
            response.put("message","code envoyé à l'email");
            response.put("status","success");
        }else {
        	 response.put("message","utilisateur non existant");
             response.put("status","fail");
        }
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/user/{userId}/type")
    public ResponseEntity<Map<String, String>> getUserTypeById(@PathVariable Long userId) {
        String userType = authService.getUserTypeById(userId);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("userType", userType);
        return ResponseEntity.ok(responseBody);
    }
}
