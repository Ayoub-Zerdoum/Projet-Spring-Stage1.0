package com.springers.CONTROLLERS;

import com.springers.ENTITIES.Stage;
import com.springers.SERVICES.ServiceStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/stages")
public class StageController {

    @Autowired
    private ServiceStage serviceStage;

    // Add a new stage
    @Secured({"ROLE_ADMIN","ROLE_STUDENT"})
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> addStage(@RequestBody  Stage stage) {
        System.out.println("Stage: " + stage);
        serviceStage.addStage(stage);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Stage added successfully");
        return ResponseEntity.ok(response);
    }

    // Delete a stage
    @Secured({"ROLE_ADMIN","ROLE_STUDENT"})
    @PostMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteStage(@PathVariable Long id) {
        serviceStage.deleteStage(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Stage deleted successfully");
        return ResponseEntity.ok(response);
    }

    // Update a stage
    @Secured({"ROLE_ADMIN","ROLE_STUDENT"})
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, String>> updateStage(@PathVariable Long id, @RequestBody Map<String, Object> stageData) {
        serviceStage.updateStage(id, stageData);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Stage updated successfully");
        return ResponseEntity.ok(response);
    }

    // Get a stage by id
    @Secured({"ROLE_ADMIN","ROLE_STUDENT"})
    @GetMapping("/{id}")
    public ResponseEntity<Stage> getStageById(@PathVariable  Long id) {
    	Stage stage = serviceStage.getStageById(id);
        if (stage != null) {
            return ResponseEntity.ok(stage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get all stages
    @Secured("ROLE_ADMIN")
    @GetMapping("/all")
    public List<Stage> getStages() {
        return serviceStage.getAllStages();
    }
    
    @Secured("ROLE_ADMIN")
    @GetMapping("/search-date")
    public ResponseEntity<List<Stage>> searchStagesByDate(@RequestParam("date") String date) {
        List<Stage> stages = serviceStage.searchStagesByDate(date);
        return ResponseEntity.ok(stages);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/search-encadreur")
    public ResponseEntity<List<Stage>> searchStagesByEncadreur(@RequestParam("encadreurId") Long encadreurId) {
        List<Stage> stages = serviceStage.searchStagesByEncadreur(encadreurId);
        return ResponseEntity.ok(stages);
    }
    
    @Secured("ROLE_ADMIN")
    @GetMapping("/search-societe")
    public ResponseEntity<List<Stage>> searchStagesBySociete(@RequestParam("societeId") Long societeId) {
        List<Stage> stages = serviceStage.searchStagesBySociete(societeId);
        return ResponseEntity.ok(stages);
    }
    
    @Secured("ROLE_ADMIN")
    @GetMapping("/filter")
    public ResponseEntity<List<Stage>> filterStages(@RequestParam(required = false) String dateDebut,
                                                    @RequestParam(required = false) String dateFin,
                                                    @RequestParam(required = false) Long encadreurId,
                                                    @RequestParam(required = false) Long societeId) {
        List<Stage> filteredStages = serviceStage.filterStages(dateDebut, dateFin, encadreurId, societeId);
        return ResponseEntity.ok(filteredStages);
    }
}