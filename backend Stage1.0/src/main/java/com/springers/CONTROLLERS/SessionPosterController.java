package com.springers.CONTROLLERS;
import com.springers.CONTROLLERS.dto.PosterDTO;
import com.springers.CONTROLLERS.dto.SessionPosterDTO;
import com.springers.ENTITIES.Professor;
import com.springers.ENTITIES.SessionPoster;
import com.springers.SERVICES.ServiceSessionPoster;
import com.springers.SERVICES.Service_Professor;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/api/session-posters")
@Slf4j
public class SessionPosterController {

    @Autowired
    private ServiceSessionPoster  serviceSessionPoster;

    @Autowired
    private Service_Professor serviceProfessor;



    // Add a new session poster
    @Secured("ROLE_ADMIN")
    @PostMapping("/add")
    public ResponseEntity<String> addSessionPoster(@RequestBody SessionPosterDTO sessionPosterDTO) {
        log.info("Session Poster: " + sessionPosterDTO);
        SessionPoster sessionPoster = new SessionPoster();
        sessionPoster.setClassroom(sessionPosterDTO.getClassroom());
        sessionPoster.setDateTime(sessionPosterDTO.getDate());
        List<String> professorNames = sessionPosterDTO.getProfNames();
        Set<Professor> professorSet = new HashSet<>();
        for( String profName : professorNames) {
            List<Professor> professors = serviceProfessor.searchProfessorsByUsername(profName);
            if(professors.isEmpty()) {
                return ResponseEntity.badRequest().body("Professor with username " + profName + " not found");
            }
            professorSet.addAll(professors);
        }
        sessionPoster.setProfessors(professorSet);
        serviceSessionPoster.addPosterSession(sessionPoster);
        return ResponseEntity.ok("Session poster added successfully");
    }


    //search by professor name
    @Secured("ROLE_ADMIN")
    @GetMapping("/search-professor")
    public ResponseEntity<List<SessionPoster>> searchSessionPostersByProfessor(@RequestParam String username) {
        return ResponseEntity.ok(serviceSessionPoster.searchPosterSessionsByProfName(username));
    }

    //search by classroom
    @Secured("ROLE_ADMIN")
    @GetMapping("/search-classroom")
    public ResponseEntity<List<SessionPoster>> searchSessionPostersByClassroom(@RequestParam int classroom) {
        return ResponseEntity.ok(serviceSessionPoster.searchPosterSessionsByClassroom(classroom));
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/all")
    public ResponseEntity<List<SessionPoster>> searchSessionPosters(@RequestParam(required = false) Integer classroom,
                                                                    @RequestParam(required = false) String professorName) {
        if(classroom != null) {
            return ResponseEntity.ok(serviceSessionPoster.searchPosterSessionsByClassroom(classroom));
        }
        if(professorName != null) {
            return ResponseEntity.ok(serviceSessionPoster.searchPosterSessionsByProfName(professorName));
        }
        return ResponseEntity.ok(serviceSessionPoster.getPosterSessions());
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/update")
    public ResponseEntity<String> updateSessionPoster(
            @RequestBody SessionPosterDTO sessionPosterDTO) {
        SessionPoster sessionPoster = new SessionPoster();
        sessionPoster.setId(sessionPosterDTO.getId());
        sessionPoster.setClassroom(sessionPosterDTO.getClassroom());
        sessionPoster.setDateTime(sessionPosterDTO.getDate());
        List<String> professorNames = sessionPosterDTO.getProfNames();
        Set<Professor> professorSet = new HashSet<>();
        for( String profName : professorNames) {
            List<Professor> professors = serviceProfessor.searchProfessorsByUsername(profName);
            if(professors.isEmpty()) {
                return ResponseEntity.badRequest().body("Professor with username " + profName + " not found");
            }
            professorSet.addAll(professors);
        }
        sessionPoster.setProfessors(professorSet);
        serviceSessionPoster.updatePosterSession(sessionPoster);
        return ResponseEntity.ok("Session poster updated successfully");
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSessionPoster(@NotNull @PathVariable Long id) {
        serviceSessionPoster.deletPosterSession(id);
        return ResponseEntity.ok("Session poster deleted successfully");
    }


}