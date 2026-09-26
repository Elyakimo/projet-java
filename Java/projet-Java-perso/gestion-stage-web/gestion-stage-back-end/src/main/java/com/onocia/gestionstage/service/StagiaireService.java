package com.onocia.gestionstage.service;

import com.onocia.gestionstage.model.Stagiaire;
import com.onocia.gestionstage.repository.StagiaireRepository;
import com.onocia.gestionstage.exception.RessourceNonTrouveeException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service 
public class StagiaireService {
    private final StagiaireRepository stagiaireRepository;
    
    public StagiaireService(StagiaireRepository stagiaireRepository){
        this.stagiaireRepository = stagiaireRepository;
    }

    public List<Stagiaire> getAll(){
        return stagiaireRepository.findAll();
    }

    public Stagiaire getById(UUID id){
        return stagiaireRepository.findById(id)
            .orElseThrow(() -> new RessourceNonTrouveeException("Stagiaire introuvable : " + id));
    }
    
    public Stagiaire create(Stagiaire stagiaire){
        return stagiaireRepository.save(stagiaire);
    }

    public Stagiaire update(UUID id, Stagiaire nouvelleDonnees) {
        Stagiaire existant = getById(id);
        existant.setNom(nouvelleDonnees.getNom());
        existant.setPrenom(nouvelleDonnees.getPrenom());
        existant.setAdresseEtudiant(nouvelleDonnees.getAdresseEtudiant());
        existant.setMailEtudiant(nouvelleDonnees.getMailEtudiant());
        existant.setTelephoneEtudiant(nouvelleDonnees.getTelephoneEtudiant());
        existant.setClasse(nouvelleDonnees.getClasse());
        return stagiaireRepository.save(existant);
    }

    public void delete(UUID id){
        if (!stagiaireRepository.existsById(id)) {
            throw new RessourceNonTrouveeException("Stagiaire introuvable : " + id);
        }
        stagiaireRepository.deleteById(id);
    }
}
