package com.springers.REPOSITORIES;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.springers.ENTITIES.Offer;
import com.springers.ENTITIES.Specialization;

public interface OfferRepo extends JpaRepository<Offer, Long>{
	public List<Offer> findByNomSocieteContainingIgnoreCase(String nom);
	public List<Offer> findByTitleContainingIgnoreCase(String title);
	public List<Offer> findBySpecialization(Specialization specialization,Pageable page);
	public List<Offer> findBySpecialization(Specialization specialization);
}
