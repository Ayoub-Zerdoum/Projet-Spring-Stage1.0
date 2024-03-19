package com.springers.SERVICES;

import com.springers.ENTITIES.User;

public interface Interface_authService {
	User authenticate(String username, String password);
}
