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
@Table(name = "request_stage")
public class RequestStage {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_stage_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RequestStatus status;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student studentReq;

    @OneToOne
    @JoinColumn(name = "stage_id")
    private Stage stage;
    
    @OneToOne(mappedBy = "requestStage")
    private Response response;
}
