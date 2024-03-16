package com.springers.ENTITIES;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "session_poster")
public class SessionPoster {
	@Id
	@Column(name = "session_poster_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "salle")
    Salle salle;

    @Column(name = "date")
    LocalDate date;

    @Column(name = "heure")
    LocalTime heure;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "professor_session_poster",
            joinColumns = @JoinColumn(name = "professor_id"),
            inverseJoinColumns = @JoinColumn(name = "session_poster_id")
        )
    Set<Professor> professors;
}
