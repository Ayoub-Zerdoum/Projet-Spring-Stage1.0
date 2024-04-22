package com.springers.SERVICES;

import java.util.List;
import java.util.Map;

import com.springers.ENTITIES.Offer;
import com.springers.ENTITIES.Specialization;

public interface I_Service_Offer {

	void ajouter_Offer(Offer offre);
	Offer getOfferById(Long id);
	List<Offer> searchOffersByNomSociete(String Query);
	List<Offer> searchOffersByDomaine(String Query);
	void editOffer(Long offerId, Map<String, Object> offerData);
	List<Offer> searchOffersBySpecialization(Specialization Query);

}
