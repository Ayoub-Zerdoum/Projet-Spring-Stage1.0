package com.springers.SERVICES;

import com.springers.ENTITIES.Encadreur;
import com.springers.ENTITIES.Societe;
import com.springers.ENTITIES.Stage;
import com.springers.REPOSITORIES.EncadreurRepo;
import com.springers.REPOSITORIES.SocieteRepo;
import com.springers.REPOSITORIES.StageRepo;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ServiceStage implements I_Service_Stage{

    @Autowired
    private StageRepo stageRepo;
    
    @Autowired
    private EncadreurRepo encadreurRepo;

    @Autowired
    private SocieteRepo societeRepo;

    @Override
    public void addStage(Stage stage) {
        stageRepo.save(stage);
    }

    @Override
    public void deleteStage(Long id) {
        stageRepo.deleteById(id);
    }

    @Override
    public void updateStage(Stage stage) {
        stageRepo.save(stage);
    }

    @Override
    public Stage getStageById(Long id) {
        return stageRepo.findById(id).orElse(null);
    }

    @Override
    public List<Stage> getAllStages() {
        return stageRepo.findAll();
    }
    
    @Override
    public List<Stage> searchStagesByStartDate(LocalDate startDate) {
        return stageRepo.findByDateDebut(startDate);
    }

    @Override
    public List<Stage> searchStagesBySupervisor(Long supervisorId) {
        Encadreur supervisor = encadreurRepo.findById(supervisorId)
                .orElseThrow(() -> new EntityNotFoundException("Supervisor not found with ID: " + supervisorId));
        return stageRepo.findByEncadreur(supervisor);
    }

    @Override
    public List<Stage> searchStagesByCompany(Long companyId) {
        Societe company = societeRepo.findById(companyId)
                .orElseThrow(() -> new EntityNotFoundException("Company not found with ID: " + companyId));
        return stageRepo.findBySociete(company);
    }

    @Override
    public List<Stage> searchStagesByPeriod(LocalDate startDate, LocalDate endDate) {
        return stageRepo.findByDateDebutBetween(startDate, endDate);
    }

    @Override
    public List<Stage> searchStagesByStartDateBefore(LocalDate date) {
        return stageRepo.findByDateDebutBefore(date);
    }

    @Override
    public List<Stage> searchStagesByStartDateAfter(LocalDate date) {
        return stageRepo.findByDateDebutAfter(date);
    }

    @Override
    public List<Stage> searchStagesByEndDateBefore(LocalDate date) {
        return stageRepo.findByDateFinBefore(date);
    }

    @Override
    public List<Stage> searchStagesByEndDateAfter(LocalDate date) {
        return stageRepo.findByDateFinAfter(date);
    }

    @Override
    public void updateStage(Long id, Map<String, Object> updatedStage) {
        Optional<Stage> optionalStage = stageRepo.findById(id);
        if (optionalStage.isPresent()) {
            Stage existingStage = optionalStage.get();

            // Vérifiez chaque clé de la map et mettez à jour les champs correspondants
            if (updatedStage.containsKey("dateDebut") && updatedStage.get("dateDebut") instanceof LocalDate) {
                existingStage.setDateDebut((LocalDate) updatedStage.get("dateDebut"));
            }
            if (updatedStage.containsKey("dateFin") && updatedStage.get("dateFin") instanceof LocalDate) {
                existingStage.setDateFin((LocalDate) updatedStage.get("dateFin"));
            }
            if (updatedStage.containsKey("lettreAffectationUrl")
                    && updatedStage.get("lettreAffectationUrl") instanceof String) {
                existingStage.setLettreAffectationUrl((String) updatedStage.get("lettreAffectationUrl"));
            }
            if (updatedStage.containsKey("encadreurId") && updatedStage.get("encadreurId") instanceof Long) {
                Long encadreurId = (Long) updatedStage.get("encadreurId");
                Encadreur encadreur = encadreurRepo.findById(encadreurId)
                        .orElseThrow(() -> new EntityNotFoundException("Encadreur not found with ID: " + encadreurId));
                existingStage.setEncadreur(encadreur);
            }
            if (updatedStage.containsKey("societeId") && updatedStage.get("societeId") instanceof Long) {
                Long societeId = (Long) updatedStage.get("societeId");
                Societe societe = societeRepo.findById(societeId)
                        .orElseThrow(() -> new EntityNotFoundException("Societe not found with ID: " + societeId));
                existingStage.setSociete(societe);
            }

            // Enregistrez les modifications
            stageRepo.save(existingStage);
        } else {
            throw new IllegalArgumentException("Stage with ID " + id + " not found");
        }
    }

  


    @Override
    public List<Stage> searchStagesByDate(String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        return stageRepo.findByDateDebut(parsedDate);
    }

    @Override
    public List<Stage> searchStagesByEncadreur(Long encadreurId) {
        Encadreur encadreur = encadreurRepo.findById(encadreurId)
                .orElseThrow(() -> new EntityNotFoundException("Encadreur not found with ID: " + encadreurId));
        return stageRepo.findByEncadreur(encadreur);
    }

    @Override
    public List<Stage> searchStagesBySociete(Long societeId) {
        Societe societe = societeRepo.findById(societeId)
                .orElseThrow(() -> new EntityNotFoundException("Societe not found with ID: " + societeId));
        return stageRepo.findBySociete(societe);
    }

    @Override
    public List<Stage> searchStagesByEndDate(LocalDate endDate) {

        return stageRepo.findByDateFin(endDate);
    }

    @Override
    public List<Stage> filterStages(String dateDebut, String dateFin, Long encadreurId, Long societeId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'filterStages'");
    }




}