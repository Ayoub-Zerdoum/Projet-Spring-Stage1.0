package com.springers.SERVICES;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;

import com.springers.REPOSITORIES.ProfessorRepo;
import com.springers.REPOSITORIES.SessionPosterRepo;
import com.springers.SERVICES.ServiceSessionPoster;
import com.springers.ENTITIES.SessionPoster;
import com.springers.REPOSITORIES.ProfessorRepo;
import com.springers.REPOSITORIES.SessionPosterRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


class ServiceSessionPosterTest {

	@Mock
    private SessionPosterRepo sessionPosterRepo;

    @Mock
    private ProfessorRepo professorRepo;

    @InjectMocks
    private ServiceSessionPoster serviceSessionPoster;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPosterSession() {
        SessionPoster sessionPoster = new SessionPoster();
        serviceSessionPoster.addPosterSession(sessionPoster);
        verify(sessionPosterRepo, times(1)).save(sessionPoster);
    }

    @Test
    void testGetPosterSessions() {
        List<SessionPoster> expectedSessions = new ArrayList<>();
        when(sessionPosterRepo.findAll()).thenReturn(expectedSessions);

        List<SessionPoster> actualSessions = serviceSessionPoster.getPosterSessions();

        assertEquals(expectedSessions, actualSessions);
    }

    @Test
    void testDeletePosterSession() {
        Long sessionId = 1L;
        serviceSessionPoster.deletPosterSession(sessionId);
        verify(sessionPosterRepo, times(1)).deleteById(sessionId);
    }

    @Test
    void testGetPosterSessionById() {
        Long sessionId = 1L;
        SessionPoster expectedSession = new SessionPoster();
        when(sessionPosterRepo.findById(sessionId)).thenReturn(Optional.of(expectedSession));

        SessionPoster actualSession = serviceSessionPoster.getPosterSessionById(sessionId);

        assertEquals(expectedSession, actualSession);
    }

}
