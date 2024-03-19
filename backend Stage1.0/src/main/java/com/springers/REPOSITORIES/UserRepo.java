package com.springers.REPOSITORIES;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springers.ENTITIES.User;

@Repository
public interface UserRepo extends CrudRepository<User, Long>{
	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
}