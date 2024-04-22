package com.springers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;



import com.springers.ENTITIES.Admin;
import com.springers.ENTITIES.Offer;
import com.springers.ENTITIES.OfferApplication;
import com.springers.ENTITIES.OfferApplication.OfferApplicationId;
import com.springers.ENTITIES.Specialization;
import com.springers.ENTITIES.Student;
import com.springers.REPOSITORIES.AdminRepo;
import com.springers.REPOSITORIES.OfferApplicationRepo;
import com.springers.REPOSITORIES.OfferRepo;
import com.springers.SERVICES.I_Service_Admin;
import com.springers.SERVICES.I_Service_Offer;
import com.springers.SERVICES.I_Service_Student;

import jakarta.persistence.EntityNotFoundException;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:4200"); // Replace this with the origin of your Angular app
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
	
	
	//@Bean
	CommandLineRunner start (I_Service_Offer S_Offer,AdminRepo AdRepo,I_Service_Admin S_Admin,OfferApplicationRepo offerAppRepo,I_Service_Student S_Student) {
		return args ->{
			/*
			Offer offre = new Offer();
	        offre.setSpecialization(Specialization.INFORMATIQUE);
	        offre.setTitle("Title");
	        offre.setDescription("Description");
	        offre.setDeadline(LocalDate.now());
	        offre.setNomSociete("Company Name");
	        offre.setNbPlaces(5);
	        offre.setIsActive(true);
	        */
			
			/*Map<String,Object> offerMap = new HashMap<String,Object>();
			offerMap.put("specialization", "INFORMATIQUE");
	        offerMap.put("Title", "Web");
	        offerMap.put("Description", "New offre 3");
	        offerMap.put("deadline", "2024-06-25");
	        offerMap.put("NomSociete", "ACTIA");
	        offerMap.put("NbPlaces", 4);
	        offerMap.put("localisation", "Localisation3");
	        offerMap.put("MailRH", "exampleOffreMail@gmail.com");
	        offerMap.put("isActive", true);
	        Long adminId = 6L;
	        // Set properties of adminOffer as needed for the test
	        Admin admin = AdRepo.findById(adminId)
	                .orElseThrow(() -> new EntityNotFoundException("Admin not found with id: " + adminId));
	        
	        
	        //offre.setAdminoffer(admin);

	        Student std = new Student();
	        std.setUsername("Dhia");
	        std.setPassword("super1");
	        std.setEmail("email@gmail.com");
	        std.setTelephone("50505050");*/
	        //S_Student.ajouter_Student(std);
	        
	        
	        //S_Offer.ajouter_Offer(offre);
	        /*
	        List<Offer> offers = admin.getOffers();
	        offers.forEach(p -> {System.out.println(p.getDescription());});
	        System.out.println("x");
	        
	        offers = S_Offer.searchOffersByNomSociete("x");
	        offers.forEach(p -> {System.out.println(p.getId());});
	        
	        offers = S_Offer.searchOffersByDomaine("T");
	        offers.forEach(p -> {System.out.println(p.getId());});*/
	        //System.out.print(offerMap);
	        //S_Admin.ajouter_Offer(adminId, offerMap);
	        
	        //admin.getOffers().forEach(p -> {System.out.println(p.getId());});
	        /*
	        OfferApplication offreApp = new OfferApplication();
	        OfferApplicationId appId = new OfferApplicationId();
	        appId.setOfferId(4L);
	        appId.setStudentId(8L);
	       
	        offreApp.setId(appId);
	        offerAppRepo.save(offreApp);
	        */
	        /*
	        List<OfferApplication> stdoffre1 = (List<OfferApplication>) offerAppRepo.findByStudentOfferUserId(7L);
	        stdoffre1.forEach(p -> {System.out.println(p.getId());});
	        
	        List<OfferApplication> stdoffre2 = (List<OfferApplication>) offerAppRepo.findByOfferApplicationId(4L);
	        stdoffre2.forEach(p -> {System.out.println(p.getId());});
	        */
	        //S_Student.ReserveOffer(4L,18L);
	        	        
		};
		
		
		
	}
}
