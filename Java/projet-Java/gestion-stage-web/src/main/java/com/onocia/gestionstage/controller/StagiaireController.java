package com.onocia.gestionstage.controller;

import com.onocia.gestionstage.model.Stagiaire;
import com.onocia.gestionstage.repository.StageRepository;
import com.onocia.gestionstage.repository.StagiaireRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;



@RestController 
@RequestMapping ("/api/stagiaires")
public class StagiaireController {
    private final StagiaireRepository stagiaireRepository;

    public StagiaireController(StagiaireRepository stagiaireRepository, StageRepository stageRepository){
        this.stagiaireRepository = stagiaireRepository;
    }

    @GetMapping
    public List<Stagiaire> getAllStagiaires(){
        return stagiaireRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Stagiaire> getStagiaireById(@PathVariable UUID id){
        return stagiaireRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping 
    public Stagiaire createStagiaire(@RequestBody Stagiaire stagiaire){
        return stagiaireRepository.save(stagiaire);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stagiaire> updateStagiaire(@PathVariable UUID id, @RequestBody Stagiaire stagiaire) {
        //TODO: process PUT request        
        return stagiaireRepository.findById(id)
            .map(existingStagiaire -> {
                existingStagiaire.setNom(stagiaire.getNom());
                existingStagiaire.setPrenom(stagiaire.getPrenom());
                existingStagiaire.setTelephoneEtudiant(stagiaire.getTelephoneEtudiant());
                existingStagiaire.setMailEtudiant(stagiaire.getMailEtudiant());
                existingStagiaire.setAdresseEtudiant(stagiaire.getAdresseEtudiant());
                existingStagiaire.setClasse(stagiaire.getClasse());
                Stagiaire updated = stagiaireRepository.save(existingStagiaire);
                return ResponseEntity.ok(updated);
            })
            .orElse(ResponseEntity.notFound().build());

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delStagiaire(@PathVariable UUID id){
        if (!stagiaireRepository.existsById(id)){
            return ResponseEntity.status(404).body("Le stagiaire n'existe pas");
        }
        stagiaireRepository.deleteById(id);
        return ResponseEntity.ok("Stagiaire supprimé avec succès");
    }
}
