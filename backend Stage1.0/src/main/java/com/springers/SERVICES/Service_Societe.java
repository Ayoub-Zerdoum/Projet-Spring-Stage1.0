package com.springers.SERVICES;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springers.ENTITIES.Societe;
import com.springers.REPOSITORIES.SocieteRepo;

@Service
public class Service_Societe implements I_Service_Societe{


    @Autowired
    private SocieteRepo societeRepo;

    @Override
    public void ajouter_Societe(Societe societe) {
        societeRepo.save(societe);
    }

    @Override
    public void supprimer_Societe(Long id) {
        societeRepo.deleteById(id);
    }

    @Override
    public List<Societe> afficher_Societes() {
        return (List<Societe>) societeRepo.findAll();
    }

    @Override
    public Societe getSocieteById(Long id) {
        return societeRepo.findById(id).orElse(null);
    }

    @Override
    public List<Societe> rechercherParNom(String nom) {
        return societeRepo.findByNameContainingIgnoreCase(nom);
    }

    @Override
    public List<Societe> rechercherParAdresse(String adresse) {
        return societeRepo.findByAddressContainingIgnoreCase(adresse);
    }
}
