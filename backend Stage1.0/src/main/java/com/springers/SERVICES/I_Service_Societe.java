package com.springers.SERVICES;

import java.util.List;

import com.springers.ENTITIES.Societe;

public interface I_Service_Societe {
	public void ajouter_Societe(Societe societe);
    public void supprimer_Societe(Long id);
    public List<Societe> afficher_Societes();
    public Societe getSocieteById(Long id);
    public List<Societe> rechercherParNom(String nom);
    public List<Societe> rechercherParAdresse(String adresse);
}
