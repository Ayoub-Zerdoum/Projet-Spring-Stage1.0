package com.springers.ENTITIES;

import java.io.Serializable;
import java.time.LocalDate;

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
@Table(name = "response")
public class Response {
	@EmbeddedId
    private ResponseId id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "comment")
    private String comment;
    
    @OneToOne
    @JoinColumn(name = "request_stage_id", insertable = false, updatable = false)
    private RequestStage requestStage;

    @ManyToOne
    @JoinColumn(name = "admin_id", insertable = false, updatable = false)
    private Admin admin;

    // Constructors, getters, and setters

    @Embeddable
    public static class ResponseId implements Serializable {
        private static final long serialVersionUID = 1L;

        @Column(name = "request_stage_id")
        private Long requestStageId;

        @Column(name = "admin_id")
        private Long adminId;

        // Constructors, getters, setters, and other methods
    }
}
