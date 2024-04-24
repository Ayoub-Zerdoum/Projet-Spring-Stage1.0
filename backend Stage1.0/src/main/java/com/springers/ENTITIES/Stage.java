package com.springers.ENTITIES;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Cascade;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level=AccessLevel.PRIVATE)
@Builder
@Table(name = "stage")
public class Stage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stage_id")
    private Long id;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Column(name = "lettre_affectation_url")
    private String lettreAffectationUrl;

    @ManyToOne
    @JoinColumn(name = "encadreur_id")
    private Encadreur encadreur;

    @ManyToOne
    @JoinColumn(name = "societe_id")
    private Societe societe;

    @OneToOne(mappedBy = "stage")
    @JsonIgnore
    private Poster poster;

    @OneToOne(mappedBy = "stage", cascade = CascadeType.ALL)
    private RequestStage requestStage;
}