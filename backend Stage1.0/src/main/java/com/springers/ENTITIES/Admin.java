package com.springers.ENTITIES;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@ToString
@SuperBuilder
@DiscriminatorValue("ADMIN")
public class Admin extends User {
	private static final long serialVersionUID = 1L;
	@Enumerated(EnumType.STRING)
    @Column(name = "privilege" ,length = 50)
    private Privilege privilege;
	
	@OneToMany(mappedBy = "admin")
    private List<Response> responses;
}
