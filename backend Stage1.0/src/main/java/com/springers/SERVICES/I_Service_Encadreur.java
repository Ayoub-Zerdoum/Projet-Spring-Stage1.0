package com.springers.SERVICES;

import java.util.List;
import java.util.Map;

import com.springers.ENTITIES.Encadreur;

public interface I_Service_Encadreur {
	public void ajouter_Encadreur(Encadreur encadreur);
    public void supprimer_Encadreur(Long id);
    public List<Encadreur> afficher_Encadreurs();
    public Encadreur getEncadreurById(Long id);
    public List<Encadreur> rechercherParNom(String nom);
    public List<Encadreur> rechercherParTelephone(String telephone);
    public List<Encadreur> rechercherParEmail(String email);
    void updateEncadreur(Long id, Map<String, Object> updatedEncadreurData);
}
