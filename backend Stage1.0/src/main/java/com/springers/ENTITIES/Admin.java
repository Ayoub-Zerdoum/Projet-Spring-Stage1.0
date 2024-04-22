package com.springers.ENTITIES;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
@DiscriminatorValue("ADMIN")
public class Admin extends User {
	private static final long serialVersionUID = 1L;
	@Enumerated(EnumType.STRING)
    @Column(name = "privilege" ,length = 50)
    private Privilege privilege;
	
	@OneToMany(mappedBy = "adminoffer",fetch=FetchType.EAGER)
	@JsonIgnoreProperties("adminoffer")
    private List<Offer> offers=new ArrayList<Offer>();
	
	@OneToMany(mappedBy = "admin")
    private List<Response> responses;
}
