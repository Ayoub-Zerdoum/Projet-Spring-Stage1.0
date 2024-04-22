package com.springers.REPOSITORIES;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springers.ENTITIES.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
	
	@Query(value = "SELECT user_type FROM user WHERE user_id = :userId", nativeQuery = true)
    String findUserTypeById(@Param("userId") Long userId);
}
