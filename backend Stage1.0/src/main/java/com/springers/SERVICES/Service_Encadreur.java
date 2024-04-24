package com.springers.SERVICES;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springers.ENTITIES.Encadreur;
import com.springers.REPOSITORIES.EncadreurRepo;

@Service
public class Service_Encadreur implements I_Service_Encadreur {
    @Autowired
    private EncadreurRepo encadreurRepo;

    @Override
    public void ajouter_Encadreur(Encadreur encadreur) {
        encadreurRepo.save(encadreur);
    }

    @Override
    public void supprimer_Encadreur(Long id) {
        encadreurRepo.deleteById(id);
    }

    @Override
    public List<Encadreur> afficher_Encadreurs() {
        return (List<Encadreur>) encadreurRepo.findAll();
    }

    @Override
    public Encadreur getEncadreurById(Long id) {
        return encadreurRepo.findById(id).orElse(null);
    }

    @Override
    public List<Encadreur> rechercherParNom(String nom) {
        return encadreurRepo.findByNameContainingIgnoreCase(nom);
    }

    @Override
    public List<Encadreur> rechercherParTelephone(String telephone) {
        return encadreurRepo.findByTelephoneContainingIgnoreCase(telephone);
    }

    @Override
    public List<Encadreur> rechercherParEmail(String email) {
        return encadreurRepo.findByEmailContainingIgnoreCase(email);
    }
    // Vous pouvez ajouter d'autres méthodes de recherche et de filtrage en fonction de vos besoins

    @Override
public void updateEncadreur(Long id, Map<String, Object> updatedEncadreurData) {
    encadreurRepo.findById(id).ifPresent(encadreur -> {
        if (updatedEncadreurData.containsKey("name") && updatedEncadreurData.get("name") instanceof String) {
            encadreur.setName((String) updatedEncadreurData.get("name"));
        }
        if (updatedEncadreurData.containsKey("telephone") && updatedEncadreurData.get("telephone") instanceof String) {
            encadreur.setTelephone((String) updatedEncadreurData.get("telephone"));
        }
        if (updatedEncadreurData.containsKey("email") && updatedEncadreurData.get("email") instanceof String) {
            encadreur.setEmail((String) updatedEncadreurData.get("email"));
        }
        encadreurRepo.save(encadreur);
    });
}

    };

