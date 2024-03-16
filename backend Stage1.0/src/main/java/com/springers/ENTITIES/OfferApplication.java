package com.springers.ENTITIES;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

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
@Table(name = "offer_application")
public class OfferApplication {
	@EmbeddedId
    private OfferApplicationId id;

	@ManyToOne
    @JoinColumn(name = "offer_id", insertable = false, updatable = false)
    private Offer offerApplication;

    @ManyToOne
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    private Student studentOffer;

    @Column(name = "application_date")
    private LocalDate applicationDate;

    @Column(name = "application_time")
    private LocalTime applicationTime;
    
    @Embeddable
    public static class OfferApplicationId implements Serializable {
		private static final long serialVersionUID = 1L;

		@Column(name = "offer_id")
        private Long offerId;

        @Column(name = "student_id")
        private Long studentId;
    }
}
