package com.onocia.gestionstage.controller;

import com.onocia.gestionstage.model.Stagiaire;
import com.onocia.gestionstage.model.Utilisateur;
import com.onocia.gestionstage.repository.StagiaireRepository;
import com.onocia.gestionstage.repository.UtilisateurRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/professeur")
public class ProfesseurController {

    private final UtilisateurRepository utilisateurRepository;
    private final StagiaireRepository stagiaireRepository;

    public ProfesseurController(UtilisateurRepository utilisateurRepository, StagiaireRepository stagiaireRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.stagiaireRepository = stagiaireRepository;
    }

    @GetMapping("/etudiants-suivis")
    public List<Stagiaire> getEtudiantsSuivis(Authentication authentication) {
        Utilisateur professeur = utilisateurRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new RuntimeException("Professeur introuvable"));
        return professeur.getEtudiantsSuivis();
    }

    @PostMapping("/etudiants-suivis/{idStagiaire}")
    public ResponseEntity<String> ajouterEtudiantSuivi(@PathVariable UUID idStagiaire, Authentication authentication) {
        Utilisateur professeur = utilisateurRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new RuntimeException("Professeur introuvable"));
        Stagiaire stagiaire = stagiaireRepository.findById(idStagiaire)
            .orElseThrow(() -> new RuntimeException("Stagiaire introuvable"));

        if (!professeur.getEtudiantsSuivis().contains(stagiaire)) {
            professeur.getEtudiantsSuivis().add(stagiaire);
            utilisateurRepository.save(professeur);
        }
        return ResponseEntity.ok("Étudiant ajouté au suivi.");
    }

    @DeleteMapping("/etudiants-suivis/{idStagiaire}")
    public ResponseEntity<String> retirerEtudiantSuivi(@PathVariable UUID idStagiaire, Authentication authentication) {
        Utilisateur professeur = utilisateurRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new RuntimeException("Professeur introuvable"));
        Stagiaire stagiaire = stagiaireRepository.findById(idStagiaire)
            .orElseThrow(() -> new RuntimeException("Stagiaire introuvable"));

        professeur.getEtudiantsSuivis().remove(stagiaire);
        utilisateurRepository.save(professeur);
        return ResponseEntity.ok("Étudiant retiré du suivi.");
    }
}