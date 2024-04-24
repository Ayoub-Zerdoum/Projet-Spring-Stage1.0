package com.springers.SERVICES;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.springers.ENTITIES.Stage;

public interface I_Service_Stage {
void addStage(Stage stage);
    
    void deleteStage(Long id);

    List<Stage> filterStages(String dateDebut, String dateFin, Long encadreurId, Long societeId);

    
    List<Stage> getAllStages();
    
    List<Stage> searchStagesByStartDate(LocalDate startDate);
    
    List<Stage> searchStagesByEndDate(LocalDate endDate);
    
    List<Stage> searchStagesBySupervisor(Long supervisorId);
    
    List<Stage> searchStagesByCompany(Long companyId);
    
    List<Stage> searchStagesByPeriod(LocalDate startDate, LocalDate endDate);
    
    List<Stage> searchStagesByStartDateBefore(LocalDate date);
    
    List<Stage> searchStagesByStartDateAfter(LocalDate date);
    
    List<Stage> searchStagesByEndDateBefore(LocalDate date);
    
    List<Stage> searchStagesByEndDateAfter(LocalDate date);
    Stage getStageById(Long id);

    List<Stage> searchStagesByDate(String date);

    List<Stage> searchStagesByEncadreur(Long encadreurId);

    List<Stage> searchStagesBySociete(Long societeId);
    void updateStage(Long id, Map<String, Object> updatedStage);

	void updateStage(Stage stage);
}
