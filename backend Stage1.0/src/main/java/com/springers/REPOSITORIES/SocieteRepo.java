package com.springers.REPOSITORIES;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springers.ENTITIES.Societe;

public interface SocieteRepo extends JpaRepository<Societe, Long> {

    public List<Societe> findByNameContainingIgnoreCase(String nameQuery);
    public List<Societe> findByAddressContainingIgnoreCase(String addressQuery);

}
