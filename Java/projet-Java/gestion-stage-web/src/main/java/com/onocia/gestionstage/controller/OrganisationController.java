package com.onocia.gestionstage.controller;

import com.onocia.gestionstage.model.Organisation;
import com.onocia.gestionstage.repository.OrganisationRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/organisations")
public class OrganisationController {
    // Le constructeur reçoit OrganisationRepository en paramètre — c'est de
    // l'injection de dépendances
    private final OrganisationRepository organisationRepository;

    public OrganisationController(OrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;
    }

    @GetMapping
    public List<Organisation> getAllOrganisations() {
        return organisationRepository.findAll();
    }

    @GetMapping("/{id}")
    //renvoie un code 404 afin que ça affiche bien erreur si on ne touve pas l'id
    public ResponseEntity<Organisation> getOrganisationById(@PathVariable UUID id) {
        return organisationRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Organisation createOrganisation(@RequestBody Organisation organisation) {
        return organisationRepository.save(organisation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Organisation> updateOrganisation(@PathVariable UUID id,
            @RequestBody Organisation organisation) {
        // recherche l'organisation si elle existe ou renvoie une erreur
        return organisationRepository.findById(id)
                .map(existingOrganisation -> {
                    existingOrganisation.setNomEntreprise(organisation.getNomEntreprise());
                    existingOrganisation.setMailEntreprise(organisation.getMailEntreprise());
                    existingOrganisation.setTelephoneEntreprise(organisation.getTelephoneEntreprise());
                    existingOrganisation.setAdresseEntreprise(organisation.getAdresseEntreprise());
                    Organisation updated = organisationRepository.save(existingOrganisation);
                    return ResponseEntity.ok(updated);

                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delOrganisation(@PathVariable UUID id) {
        if (!organisationRepository.existsById(id)) {
            return ResponseEntity.status(404).body("L'organisation n'exite pas.");
        }
        organisationRepository.deleteById(id);
        return ResponseEntity.ok("Organisation supprimé avec succès.");
    }

}
