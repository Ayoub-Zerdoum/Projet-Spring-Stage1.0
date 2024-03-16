package com.springers.ENTITIES;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@FieldDefaults(level=AccessLevel.PRIVATE)
@Builder
@Table(name = "societe")
public class Societe {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "societe_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;
}
