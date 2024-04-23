package com.springers.CONTROLLERS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springers.ENTITIES.Offer;
import com.springers.SERVICES.Service_Offer;
import com.springers.SERVICES.Service_Student;

@RestController
@CrossOrigin
@RequestMapping("/api/offers")
public class OfferController {
	@Autowired
	Service_Offer offerService;
	
	@Autowired
	Service_Student studentService;
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/all")
    public ResponseEntity<List<Offer>> getAllOffers(@RequestParam(defaultValue = "0") int page,
												    @RequestParam(defaultValue = "5") int size) {
		List<Offer> offers = offerService.afficher_Offers(page,size);
	    return ResponseEntity.ok(offers);
    }
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/all/taille")
    public ResponseEntity<Integer> getAllOffersSize(@RequestParam(defaultValue = "0") int page,
												    @RequestParam(defaultValue = "5") int size) {
		Integer taille = offerService.afficher_Offers().size();
	    return ResponseEntity.ok(taille);
    }
	
	
	@Secured({"ROLE_ADMIN","ROLE_STUDENT"})
	@GetMapping("/specialization/all/{studentId}")
    public ResponseEntity<List<Map<String, Object>>> getAllOffersBySpecialisation(@PathVariable Long studentId,
				    												@RequestParam(defaultValue = "0") int page,
																    @RequestParam(defaultValue = "5") int size) {
		List<Offer> offers = offerService.afficher_Offers(studentId,page,size);
		List<Map<String, Object>> offersWithRanks = new ArrayList<>();
	    
	    for (Offer offer : offers) {
	        Integer rank = studentService.calculateRankInOffer(studentId, offer.getId());
	        Map<String, Object> offerMap = new HashMap<>();
	        
	        // Add offer properties to the map
	        offerMap.put("id", offer.getId());
	        offerMap.put("specialization", offer.getSpecialization());
	        offerMap.put("title", offer.getTitle());
	        offerMap.put("description", offer.getDescription());
	        offerMap.put("deadline", offer.getDeadline());
	        offerMap.put("nomSociete", offer.getNomSociete());
	        offerMap.put("isActive", offer.getIsActive());
	        offerMap.put("mailRH", offer.getMailRH());
	        offerMap.put("nbPlaces", offer.getNbPlaces());
	        offerMap.put("localisation", offer.getLocalisation());
	        offerMap.put("offerApplications", offer.getOfferApplications());
	        offerMap.put("rank", rank);
	        offersWithRanks.add(offerMap);
	    }
	    return ResponseEntity.ok(offersWithRanks);
    }
	
	@Secured({"ROLE_ADMIN","ROLE_STUDENT"})
	@GetMapping("/specialization/all/taille/{studentId}")
	public ResponseEntity<Integer> getAllOffersBySpecialisationSize(@PathVariable Long studentId){
		Integer taille = offerService.afficher_Offers(studentId).size();

        // Return the response map as JSON
        return ResponseEntity.ok(taille);
	}
}
