package com.springers.CONTROLLERS;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springers.ENTITIES.LoginRequest;
import com.springers.ENTITIES.User;
import com.springers.REPOSITORIES.UserRepo;
import com.springers.SERVICES.AuthService;
import com.springers.UTILITIS.JwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
    private AuthService authService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private UserRepo userRepo;
	
	@GetMapping("/user")
    public String helloUser() {
        return "Hello User";
    }

	 
	/*
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
    	String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        User user = authService.authenticate(username, password);
        if (user != null) {
              //Return user information in the response
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }*/
	
	@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    	String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        System.out.println(username + "  "+ password);
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );
        User user = userRepo.findByUsername(username)
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return ResponseEntity.ok(jwtToken);
	}
	
	
	
    
    @GetMapping("/user/{userId}/type")
    public ResponseEntity<Map<String, String>> getUserTypeById(@PathVariable Long userId) {
        String userType = authService.getUserTypeById(userId);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("userType", userType);
        return ResponseEntity.ok(responseBody);
    }
}
