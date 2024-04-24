package com.springers.REPOSITORIES;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.springers.ENTITIES.Professor;
import com.springers.ENTITIES.SessionPoster;

import jakarta.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class SessionPosterRepoTest {

	@Autowired
    private SessionPosterRepo sessionPosterRepo;


    @Test
    public void testFindSessionPostersByClassroom() {
        // Create some test data
        SessionPoster poster1 = new SessionPoster();
        poster1.setClassroom(101);

        SessionPoster poster2 = new SessionPoster();
        poster2.setClassroom(102);

        // Save the test data
        sessionPosterRepo.save(poster1);
        sessionPosterRepo.save(poster2);

        // Perform the repository call
        List<SessionPoster> postersInClassroom101 = sessionPosterRepo.findSessionPostersByClassroom(101);

        // Assert
        assertThat(postersInClassroom101.get(0).getClassroom()).isEqualTo(101);
    }

    @Test
    public void testFindSessionPostersByProfessors() {
        // Create some test data
        Professor professor1 = new Professor();
        professor1.setUsername("John Doe");
        professor1.setEmail("john@gmail.com");
        professor1.setPassword("12345678");
        professor1.setTelephone("+21622366000");

        Professor professor2 = new Professor();
        professor2.setUsername("Jane Smith");
        professor2.setEmail("jane@gmail.com");
        professor2.setPassword("12345678");
        professor2.setTelephone("+21622366000");

        SessionPoster poster1 = new SessionPoster();
        poster1.setProfessors(Set.of(professor1));

        SessionPoster poster2 = new SessionPoster();
        poster2.setProfessors(Set.of(professor2));

        // Save the test data
        sessionPosterRepo.save(poster1);
        sessionPosterRepo.save(poster2);

        // Perform the repository call
        List<SessionPoster> postersByProfessor1 = sessionPosterRepo.findSessionPostersByProfessors(Set.of(professor1));

        // Assert
        assertThat(postersByProfessor1).hasSize(1);
        assertThat(postersByProfessor1.get(0).getProfessors()).contains(professor1);
    }

}

