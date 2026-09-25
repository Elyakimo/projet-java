package com.onocia.gestionstage.controller;

import com.onocia.gestionstage.model.Stage;
import com.onocia.gestionstage.model.Tuteur;
import com.onocia.gestionstage.model.Stagiaire;
import com.onocia.gestionstage.model.Organisation;
import com.onocia.gestionstage.repository.StageRepository;
import com.onocia.gestionstage.repository.StagiaireRepository;
import com.onocia.gestionstage.repository.TuteurRepository;
import com.onocia.gestionstage.repository.OrganisationRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/stages")
public class StageController {
    private final StageRepository stageRepository;
    private final OrganisationRepository organisationRepository;
    private final TuteurRepository tuteurRepository;
    private final StagiaireRepository stagiaireRepository;

    public StageController(StageRepository stageRepository, OrganisationRepository organisationRepository,
            TuteurRepository tuteurRepository, StagiaireRepository stagiaireRepository) {
        this.stageRepository = stageRepository;
        this.organisationRepository = organisationRepository;
        this.tuteurRepository = tuteurRepository;
        this.stagiaireRepository = stagiaireRepository;
    }

    @GetMapping
    public List<Stage> getAllStages() {
        return stageRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stage> getStageById(@PathVariable UUID id) {
        return stageRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping 
    public ResponseEntity<Stage> createStage(@RequestBody Stage stage){
        Organisation organisation = organisationRepository.findById(stage.getOrganisation().getId())
            .orElse(null);
        Tuteur tuteur = tuteurRepository.findById(stage.getTuteur().getId())
            .orElse(null);
        Stagiaire stagiaire = stagiaireRepository.findById(stage.getStagiaire().getId())
            .orElse(null);
        if (organisation == null || tuteur == null || stagiaire == null){
            return ResponseEntity.badRequest().build();
        }
        stage.setOrganisation(organisation);
        stage.setTuteur(tuteur);
        stage.setStagiaire(stagiaire);

        Stage saved = stageRepository.save(stage);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stage> updateStage(@PathVariable UUID id, @RequestBody Stage stage){
        return stageRepository.findById(id)
            .map(existingStage -> {
                existingStage.setStatut(stage.getStatut());
                existingStage.setDateDebut(stage.getDateDebut());
                existingStage.setDateFin(stage.getDateFin());
                Organisation newOrganisation = organisationRepository.findById(stage.getOrganisation().getId())
                        .orElseThrow(() -> new RuntimeException("Aucune organisation."));
                existingStage.setOrganisation(newOrganisation);
                Tuteur newTuteur = tuteurRepository.findById(stage.getTuteur().getId())
                        .orElseThrow(()-> new RuntimeException("Auncun tuteur"));
                existingStage.setTuteur(newTuteur);
                Stagiaire newStagiaire = stagiaireRepository.findById(stage.getStagiaire().getId())
                        .orElseThrow(()-> new RuntimeException("Auncun stagiaire"));
                existingStage.setStagiaire(newStagiaire);
                Stage updated = stageRepository.save(existingStage);
                return ResponseEntity.ok(updated);
            }).orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delStage(@PathVariable UUID id) {
        if (!stageRepository.existsById(id)) {
            return ResponseEntity.status(404).body("le stage n'existe pas");
        }
        stageRepository.deleteById(id);
        return ResponseEntity.ok("Stage supprimé avec succès");
    }

}
