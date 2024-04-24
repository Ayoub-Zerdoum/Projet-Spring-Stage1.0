package com.springers.ENTITIES;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level=AccessLevel.PRIVATE)
@Table(name = "poster")
public class Poster {
	 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poster_id")
    private Long id;
	 
	@Column(name = "title")
	private String title;

    @Column(name = "pdf_path")
    private String pdfPath;

    @Column(name = "final_rating")
    private Double finalRating;
    
    @ManyToOne
    @JoinColumn(name = "session_poster_id")
    private SessionPoster sessionPoster;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "stage_id")
    private Stage stage;
}
