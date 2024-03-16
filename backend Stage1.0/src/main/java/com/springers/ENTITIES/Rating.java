package com.springers.ENTITIES;

import java.io.Serializable;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "rating")
public class Rating {
	@EmbeddedId
    private RatingId id;

    @Column(name = "rating")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 10, message = "Rating cannot be greater than 5")
    private int rating; // Assuming the rating is an integer value

    // Other attributes specific to Rating
    @ManyToOne
    @JoinColumn(name = "session_poster_id", insertable = false, updatable = false)
    private SessionPoster sessionPoster;

    @ManyToOne
    @JoinColumn(name = "professor_id", insertable = false, updatable = false)
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "poster_id", insertable = false, updatable = false)
    private Poster poster;
    
    @Embeddable
    public static class RatingId implements Serializable {
        private static final long serialVersionUID = 1L;

        @Column(name = "session_poster_id")
        private Long sessionPosterId;

        @Column(name = "professor_id")
        private Long professorId;

        @Column(name = "poster_id")
        private Long posterId;
    }
}
