package com.springers.REPOSITORIES;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springers.ENTITIES.Encadreur;

@Repository
public interface EncadreurRepo extends JpaRepository<Encadreur, Long> {
    public List<Encadreur> findByNameContainingIgnoreCase(String nameQuery);
    public List<Encadreur> findByTelephoneContainingIgnoreCase(String telephoneQuery);
    public List<Encadreur> findByEmailContainingIgnoreCase(String emailQuery);
}