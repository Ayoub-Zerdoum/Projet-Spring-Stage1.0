package com.springers.REPOSITORIES;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.springers.ENTITIES.OfferApplication;
import com.springers.ENTITIES.OfferApplication.OfferApplicationId;

public interface OfferApplicationRepo extends JpaRepository<OfferApplication, OfferApplicationId>{
	public List<OfferApplication> findByStudentOfferUserId(long id,Pageable pageable);
	public List<OfferApplication> findByStudentOfferUserId(long id);
	public List<OfferApplication> findByOfferApplicationId(long id);
	public Optional<OfferApplication> findById(OfferApplicationId id);
}
