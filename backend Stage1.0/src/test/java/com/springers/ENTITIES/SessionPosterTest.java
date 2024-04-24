package com.springers.ENTITIES;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

class SessionPosterTest {

	@Test
    void testSessionPosterCreation() {
        // Create professors
        Professor professor1 = new Professor();
        professor1.setUserId(1L);
        professor1.setUsername("Professor 1");

        Professor professor2 = new Professor();
        professor2.setUserId(2L);
        professor2.setUsername("Professor 2");

        // Create SessionPoster
        SessionPoster sessionPoster = new SessionPoster();
        sessionPoster.setId(1L);
        sessionPoster.setClassroom(101);
        sessionPoster.setDateTime(LocalDateTime.now());

        // Add professors to SessionPoster
        Set<Professor> professors = new HashSet<>();
        professors.add(professor1);
        professors.add(professor2);
        sessionPoster.setProfessors(professors);

        // Test SessionPoster attributes
        assertEquals(1L, sessionPoster.getId());
        assertEquals(101, sessionPoster.getClassroom());
        assertEquals(LocalDateTime.now().getDayOfYear(), sessionPoster.getDateTime().getDayOfYear());

        // Test professors set
        assertEquals(2, sessionPoster.getProfessors().size());
        assertEquals("Professor 1", sessionPoster.getProfessors().iterator().next().getUsername());
    }

}
