package com.springers.SERVICES;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import com.springers.ENTITIES.Poster;
import com.springers.REPOSITORIES.PosterRepo;
import com.springers.REPOSITORIES.PosterRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
class ServicePosterTest {

	@Mock
    private PosterRepo posterRepo;

    @InjectMocks
    private ServicePoster servicePoster;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPoster() {
        Poster poster = new Poster();
        servicePoster.addPoster(poster);
        verify(posterRepo, times(1)).save(poster);
    }

    @Test
    void testGetPosters() {
        List<Poster> expectedPosters = new ArrayList<>();
        when(posterRepo.findAll()).thenReturn(expectedPosters);

        List<Poster> actualPosters = servicePoster.getPosters();

        assertEquals(expectedPosters, actualPosters);
    }

    @Test
    void testDeletePoster() {
        Long posterId = 1L;
        servicePoster.deletPoster(posterId);
        verify(posterRepo, times(1)).deleteById(posterId);
    }

    @Test
    void testUpdatePoster() {
        Poster poster = new Poster();
        servicePoster.updatePoster(poster);
        verify(posterRepo, times(1)).save(poster);
    }

    @Test
    void testFindBySessionId() {
        Long sessionId = 1L;
        List<Poster> expectedPosters = new ArrayList<>();
        when(posterRepo.findBySessionPosterId(sessionId)).thenReturn(expectedPosters);

        List<Poster> actualPosters = servicePoster.findBySessionId(sessionId);

        assertEquals(expectedPosters, actualPosters);
    }

}
