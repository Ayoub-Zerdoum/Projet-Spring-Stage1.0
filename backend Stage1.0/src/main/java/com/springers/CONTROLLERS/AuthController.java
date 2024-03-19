package com.springers.CONTROLLERS;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springers.ENTITIES.User;
import com.springers.SERVICES.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
    	String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        User user = authService.authenticate(username, password);
        if (user != null) {
            // Return user information in the response
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
