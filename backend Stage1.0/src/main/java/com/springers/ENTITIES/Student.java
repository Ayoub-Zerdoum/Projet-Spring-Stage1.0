package com.springers.ENTITIES;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@DiscriminatorValue("STUDENT")
public class Student extends User {
	private static final long serialVersionUID = 1L;
	@Enumerated(EnumType.STRING)
    private StudentStatus studentStatus;
	
    @Enumerated(EnumType.STRING)
    private Specialization specialization;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfBirth;
    
    @OneToMany(mappedBy = "studentReq")
    private List<RequestStage> requestStages;
    
    @OneToMany(mappedBy = "studentOffer")
    @JsonIgnoreProperties("studentOffer")
    private List<OfferApplication> std_offerApplications;

}
