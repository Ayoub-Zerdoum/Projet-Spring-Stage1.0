package com.springers.ENTITIES;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PosterTest {

    @Test
    void testPosterConstructor() {
        // Create a new Poster instance using the constructor
        Poster poster = new Poster();
        poster.setId(1L);
        poster.setTitle("Sample Title");
        poster.setPdfPath("/path/to/pdf");
        poster.setFinalRating(4.5);

        // Check if the attributes are set correctly
        assertEquals(1L, poster.getId(), "ID should be 1");
        assertEquals("Sample Title", poster.getTitle(), "Title should be 'Sample Title'");
        assertEquals("/path/to/pdf", poster.getPdfPath(), "PDF path should be '/path/to/pdf'");
        assertEquals(4.5, poster.getFinalRating(), 0.01, "Final rating should be 4.5");
    }
}