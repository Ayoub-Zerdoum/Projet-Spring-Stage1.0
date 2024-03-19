package com.springers.SERVICES;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springers.ENTITIES.User;
import com.springers.REPOSITORIES.UserRepo;

@Service
public class AuthService implements Interface_authService{
	@Autowired
    private UserRepo userRepository;

    @Override
    public User authenticate(String username, String password) {
        // Find user by username
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Validate password
            if (user.getPassword().equals(password)) {
                return user; // Authentication successful
            }
        }
        return null; // Authentication failed
    }
}
