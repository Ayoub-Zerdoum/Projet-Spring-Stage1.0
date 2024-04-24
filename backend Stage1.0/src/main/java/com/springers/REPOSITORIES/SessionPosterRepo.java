package com.springers.REPOSITORIES;

import com.springers.ENTITIES.Professor;
import com.springers.ENTITIES.SessionPoster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface SessionPosterRepo extends JpaRepository<SessionPoster, Long> {
    List<SessionPoster> findSessionPostersByClassroom(int classroom);
    List<SessionPoster> findSessionPostersByProfessors( Set<Professor> professors);
}