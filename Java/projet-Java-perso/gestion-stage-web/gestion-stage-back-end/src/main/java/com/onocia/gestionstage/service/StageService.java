package com.onocia.gestionstage.service;

import com.onocia.gestionstage.model.Utilisateur;
import com.onocia.gestionstage.repository.UtilisateurRepository;
import com.onocia.gestionstage.model.Stage;
import com.onocia.gestionstage.model.Organisation;
import com.onocia.gestionstage.model.Tuteur;
import com.onocia.gestionstage.model.Stagiaire;
import com.onocia.gestionstage.repository.StageRepository;
import com.onocia.gestionstage.repository.TuteurRepository;
import com.onocia.gestionstage.repository.OrganisationRepository;
import com.onocia.gestionstage.repository.StagiaireRepository;
import com.onocia.gestionstage.exception.RessourceNonTrouveeException;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StageService {
    
    private final StageRepository stageRepository;
    private final TuteurRepository tuteurRepository;
    private final OrganisationRepository organisationRepository;
    private final StagiaireRepository stagiaireRepository;
    private final UtilisateurRepository utilisateurRepository;

    public StageService(StageRepository stageRepository, TuteurRepository tuteurRepository, OrganisationRepository organisationRepository, StagiaireRepository stagiaireRepository, UtilisateurRepository utilisateurRepository){
        this.stageRepository = stageRepository;
        this.tuteurRepository = tuteurRepository;
        this.organisationRepository = organisationRepository;     
        this.stagiaireRepository = stagiaireRepository;
        this.utilisateurRepository = utilisateurRepository;
    }
    
    public List<Stage> getAll(){
        return stageRepository.findAll();
    }

    public Stage getById(UUID id) {
        return stageRepository.findById(id)
            .orElseThrow(() -> new RessourceNonTrouveeException("Stage introuvable " + id));
    }

    public Stage create(Stage stage){
        UUID tuteurId = stage.getTuteur().getId();
        UUID organisationId = stage.getOrganisation().getId();
        UUID stagiaireId = stage.getStagiaire().getId();
        Organisation organisation = organisationRepository.findById(organisationId)
            .orElseThrow(() -> new RessourceNonTrouveeException("Organisation introuvable " + organisationId));
        Tuteur tuteur = tuteurRepository.findById(tuteurId)
            .orElseThrow(() -> new RessourceNonTrouveeException("Tuteur introuvable " + tuteurId));
        Stagiaire stagiaire = stagiaireRepository.findById(stagiaireId)
            .orElseThrow(() -> new RessourceNonTrouveeException("Stagiaire introuvable " + stagiaireId));
        stage.setOrganisation(organisation);
        stage.setTuteur(tuteur);
        stage.setStagiaire(stagiaire);
        return stageRepository.save(stage);
    }

    public Stage update(UUID id, Stage nouvelleDonnees){
        Stage existant = getById(id);
        existant.setDateDebut(nouvelleDonnees.getDateDebut());
        existant.setDateFin(nouvelleDonnees.getDateFin());
        existant.setStatut(nouvelleDonnees.getStatut());
        Organisation newOrganisation = organisationRepository.findById(nouvelleDonnees.getOrganisation().getId())
            .orElseThrow(() -> new RessourceNonTrouveeException("Organisation non trouvé " + id));
        existant.setOrganisation(newOrganisation);
        Tuteur newTuteur = tuteurRepository.findById(nouvelleDonnees.getTuteur().getId())
            .orElseThrow(() -> new RessourceNonTrouveeException("Tuteur non trouvé " + id));
        existant.setTuteur(newTuteur);
        Stagiaire newStagiaire = stagiaireRepository.findById(nouvelleDonnees.getStagiaire().getId())
            .orElseThrow(() -> new RessourceNonTrouveeException("Stagiaire non trouvé " + id));
        existant.setStagiaire(newStagiaire);
        return stageRepository.save(existant);
    }
    
    public List<Stage> getAllPourUtilisateur(String email) {
    Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
        .orElseThrow(() -> new RessourceNonTrouveeException("Utilisateur introuvable : " + email));

    if ("PROFESSEUR".equals(utilisateur.getRole())) {
        List<Stagiaire> etudiantsSuivis = utilisateur.getEtudiantsSuivis();
        return stageRepository.findAll().stream()
            .filter(stage -> etudiantsSuivis.contains(stage.getStagiaire()))
            .toList();
    }

    if ("ETUDIANT".equals(utilisateur.getRole())) {
        Stagiaire sonStagiaire = utilisateur.getStagiaire();
        if (sonStagiaire == null) {
            throw new RessourceNonTrouveeException("Ce compte n'est associé à aucune fiche stagiaire.");
        }
        return stageRepository.findAll().stream()
            .filter(stage -> stage.getStagiaire().equals(sonStagiaire))
            .toList();
    }

    return stageRepository.findAll();
} 

    public void delete(UUID id){
        if (!stageRepository.existsById(id)){
            throw new RessourceNonTrouveeException("Stage introuvable :" + id);
        }
        stageRepository.deleteById(id);
    }

}
