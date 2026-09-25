package com.onocia.gestionstage.controller;

import com.onocia.gestionstage.model.Organisation;
import com.onocia.gestionstage.service.OrganisationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/organisations")
public class OrganisationController {

    private final OrganisationService organisationService;

    public OrganisationController(OrganisationService organisationService) {
        this.organisationService = organisationService;
    }

    @GetMapping
    public List<Organisation> getAllOrganisations() {
        return organisationService.getAll();
    }

    @GetMapping("/{id}")
    public Organisation getOrganisationById(@PathVariable UUID id) {
        return organisationService.getById(id);
    }

    @PostMapping
    public Organisation createOrganisation(@RequestBody Organisation organisation) {
        return organisationService.create(organisation);
    }

    @PutMapping("/{id}")
    public Organisation updateOrganisation(@PathVariable UUID id, @RequestBody Organisation organisation) {
        return organisationService.update(id, organisation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrganisation(@PathVariable UUID id) {
        organisationService.delete(id);
        return ResponseEntity.ok("Organisation supprimée avec succès.");
    }
}