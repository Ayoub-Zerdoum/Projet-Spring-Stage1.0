package com.springers.ENTITIES;


import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@DiscriminatorValue("PROFESSOR")
public class Professor extends User{
	private static final long serialVersionUID = 1L;
	@Enumerated(EnumType.STRING)
    private Departement department;
	
	@ManyToMany(cascade = CascadeType.ALL,mappedBy = "professors")
	@JsonIgnoreProperties("professors")
    private Set<SessionPoster> sessionPosters;
}
