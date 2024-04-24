package com.springers.CONTROLLERS;

import java.util.List;

import com.springers.CONTROLLERS.dto.PosterDTO;
import com.springers.ENTITIES.*;
import com.springers.SERVICES.ServicePoster;
import com.springers.SERVICES.ServiceSessionPoster;
import com.springers.SERVICES.ServiceStage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin
@RequestMapping("/api/posters")
public class PosterController {

    private static final Logger logger = Logger.getLogger(PosterController.class);

    @Autowired
    private ServicePoster servicePoster;

    @Autowired
    private ServiceStage serviceStage;

    @Autowired
    ServiceSessionPoster serviceSessionPoster;

    @PostMapping("/add")
    public ResponseEntity<String> addPoster(@RequestBody PosterDTO posterDTO) {
        logger.info("Adding new poster...");
        if (posterDTO == null) {
            logger.error("Invalid poster data");
            return ResponseEntity.badRequest().body("Invalid poster data");
        }
        if (posterDTO.getStageId() == null) {
            logger.error("Stage ID is required");
            return ResponseEntity.badRequest().body("Stage ID is required");
        }

        logger.info("Retrieving stage by ID: " + posterDTO.getStageId());
        Stage stage = serviceStage.getStageById(posterDTO.getStageId());
        if (stage == null) {
            logger.error("Stage not found for ID: " + posterDTO.getStageId());
            return ResponseEntity.badRequest().body("Stage not found");
        }

        logger.info("Retrieving session poster by ID: " + posterDTO.getSessionPosterId());
        SessionPoster sessionPoster = serviceSessionPoster.getPosterSessionById(posterDTO.getSessionPosterId());
        if (sessionPoster == null) {
            logger.error("Session poster not found for ID: " + posterDTO.getSessionPosterId());
            return ResponseEntity.badRequest().body("Session poster not found");
        }

        logger.info("Creating new poster...");
        Poster poster = new Poster();
        poster.setStage(stage);
        poster.setSessionPoster(sessionPoster);
        poster.setFinalRating(posterDTO.getFinalRating());
        poster.setPdfPath(posterDTO.getPdfPath());
        poster.setTitle(posterDTO.getTitle());

        logger.info("Adding poster to database...");
        servicePoster.addPoster(poster);

        logger.info("Poster added successfully");
        return ResponseEntity.ok("Poster added successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Poster>> findAllPosters() {
        logger.info("Finding all posters...");
        List<Poster> posters = servicePoster.getPosters();
        logger.info("Found " + posters.size() + " posters");
        return ResponseEntity.ok(posters);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Poster>> searchPosterBySessionId(Long sessionId) {
        logger.info("Searching posters by session ID: " + sessionId);
        List<Poster> posters = servicePoster.findBySessionId(sessionId);
        logger.info("Found " + posters.size() + " posters for session ID: " + sessionId);
        return ResponseEntity.ok(posters);
    }
}