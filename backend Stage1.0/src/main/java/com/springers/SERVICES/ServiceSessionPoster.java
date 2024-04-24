package com.springers.SERVICES;

import com.springers.ENTITIES.Professor;
import com.springers.ENTITIES.SessionPoster;
import com.springers.REPOSITORIES.ProfessorRepo;
import com.springers.REPOSITORIES.SessionPosterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceSessionPoster implements IServiceSessionPoster {

    @Autowired
    public SessionPosterRepo sessionPosterRepo;

    @Autowired
    public ProfessorRepo professorRepo;

    @Override
    public void addPosterSession(SessionPoster sessionPoster) {
        sessionPosterRepo.save(sessionPoster);
    }

    @Override
    public List<SessionPoster> getPosterSessions() {
        return sessionPosterRepo.findAll();
    }

    @Override
    public void deletPosterSession(Long id) {
        sessionPosterRepo.deleteById(id);
    }

    @Override
    public SessionPoster getPosterSessionById(Long id) {
        Optional<SessionPoster> sessionPoster = sessionPosterRepo.findById(id);
        return sessionPoster.orElse(null);
    }

    @Override
    public void updatePosterSession(SessionPoster sessionPoster) {
        sessionPosterRepo.save(sessionPoster);
    }

    @Override
    public List<SessionPoster> searchPosterSessionsByClassroom(int classroom) {
        return sessionPosterRepo.findSessionPostersByClassroom(classroom);
    }

    @Override
    public List<SessionPoster> searchPosterSessionsByProfName(String username) {
        List<Professor> professors = professorRepo.findByUsernameContainingIgnoreCase(username);
        if (professors.isEmpty()) return List.of();

        return sessionPosterRepo.findSessionPostersByProfessors(new HashSet<>(professors));}
    }