package com.springers.CONTROLLERS;

import com.springers.CONTROLLERS.dto.PosterDTO;
import com.springers.ENTITIES.Poster;
import com.springers.ENTITIES.SessionPoster;
import com.springers.ENTITIES.Stage;
import com.springers.SERVICES.ServicePoster;
import com.springers.SERVICES.ServicePoster;
import com.springers.SERVICES.ServiceSessionPoster;
import com.springers.SERVICES.ServiceStage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PosterControllerTest {

    @Mock
    private ServicePoster servicePoster;

    @Mock
    private ServiceStage serviceStage;

    @Mock
    private ServiceSessionPoster serviceSessionPoster;

    @InjectMocks
    private PosterController posterController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPoster() {
        PosterDTO posterDTO = new PosterDTO();
        posterDTO.setStageId(1L);
        posterDTO.setSessionPosterId(2L);
        // Set other properties of posterDTO

        Stage stage = new Stage();
        // Set properties of stage

        SessionPoster sessionPoster = new SessionPoster();
        // Set properties of sessionPoster

        // Mock service method calls
        when(serviceStage.getStageById(posterDTO.getStageId())).thenReturn(stage);
        when(serviceSessionPoster.getPosterSessionById(posterDTO.getSessionPosterId())).thenReturn(sessionPoster);

        ResponseEntity<String> responseEntity = posterController.addPoster(posterDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Poster added successfully", responseEntity.getBody());
    }

    @Test
    void testAddPoster_InvalidData() {
        ResponseEntity<String> responseEntity = posterController.addPoster(null);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid poster data", responseEntity.getBody());
    }

    // Add more test methods for other scenarios
}