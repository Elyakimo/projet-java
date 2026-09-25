package com.onocia.gestionstage.controller;

import com.onocia.gestionstage.model.Tuteur;
import com.onocia.gestionstage.model.Organisation;
import com.onocia.gestionstage.repository.TuteurRepository;
import com.onocia.gestionstage.repository.OrganisationRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tuteurs")
public class TuteurController {
    private final TuteurRepository tuteurRepository;
    private final OrganisationRepository organisationRepository;

    public TuteurController(TuteurRepository tuteurRepository, OrganisationRepository organisationRepository) {
        this.tuteurRepository = tuteurRepository;
        this.organisationRepository = organisationRepository;
    }

    @GetMapping
    public List<Tuteur> getAllTuteurs() {
        return tuteurRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tuteur> getTuteurBysId(@PathVariable UUID id) {
        return tuteurRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tuteur> createTuteur(@RequestBody Tuteur tuteur) {
        UUID organisationId = tuteur.getOrganisation().getId();
        return organisationRepository.findById(organisationId)
                .map(organisation -> {
                    tuteur.setOrganisation(organisation);
                    Tuteur saved = tuteurRepository.save(tuteur);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tuteur> updateTuteur(@PathVariable UUID id, @RequestBody Tuteur tuteur) {
        return tuteurRepository.findById(id)
                .map(existingTuteur -> {
                    existingTuteur.setNomTuteur(tuteur.getNomTuteur());
                    existingTuteur.setPrenomTuteur(tuteur.getPrenomTuteur());
                    existingTuteur.setNumeroTuteur(tuteur.getNumeroTuteur());
                    Organisation newOrganisation = organisationRepository.findById(tuteur.getOrganisation().getId())
                            .orElseThrow(() -> new RuntimeException("Organisation non trouvé"));
                    existingTuteur.setOrganisation(newOrganisation);
                    Tuteur updated = tuteurRepository.save(existingTuteur);
                    return ResponseEntity.ok(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delTuteur(@PathVariable UUID id) {
        if (!tuteurRepository.existsById(id)) {
            return ResponseEntity.status(404).body("Le tuteur n'existe pas.");
        }
        tuteurRepository.deleteById(id);
        return ResponseEntity.ok("Tuteur supprimé avec succès.");
    }
}
