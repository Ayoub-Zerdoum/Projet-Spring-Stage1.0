package com.springers.ENTITIES;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
    private Date dateOfBirth;
    
    @OneToMany(mappedBy = "studentReq")
    private List<RequestStage> requestStages;
    
    @OneToMany(mappedBy = "studentOffer")
    private List<OfferApplication> offerApplications;
}
