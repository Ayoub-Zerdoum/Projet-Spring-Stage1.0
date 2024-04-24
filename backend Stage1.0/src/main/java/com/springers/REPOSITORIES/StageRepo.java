package com.springers.REPOSITORIES;

import com.springers.ENTITIES.Stage;

import com.springers.ENTITIES.Encadreur;
import com.springers.ENTITIES.Societe;
import com.springers.ENTITIES.Stage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StageRepo extends JpaRepository<Stage, Long> {
	List<Stage> findByDateDebut(LocalDate startDate);

    List<Stage> findByDateFin(LocalDate endDate);

    List<Stage> findByEncadreur(Encadreur supervisor);

    List<Stage> findBySociete(Societe company);

    List<Stage> findByDateDebutBetween(LocalDate startDate, LocalDate endDate);

    List<Stage> findByDateDebutBefore(LocalDate date);

    List<Stage> findByDateDebutAfter(LocalDate date);

    List<Stage> findByDateFinBefore(LocalDate date);

    List<Stage> findByDateFinAfter(LocalDate date);

	List<Stage> findByEncadreurAndSociete(Encadreur encadreur, Societe societe);
}