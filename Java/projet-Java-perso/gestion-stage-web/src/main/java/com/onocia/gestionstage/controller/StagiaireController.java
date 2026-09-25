package com.onocia.gestionstage.controller;

import com.onocia.gestionstage.model.Organisation;
import com.onocia.gestionstage.model.Stagiaire;
import com.onocia.gestionstage.service.StagiaireService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;



@RestController 
@RequestMapping ("/api/stagiaires")
public class StagiaireController {
    private final StagiaireService stagiaireService;

    public StagiaireController(StagiaireService stagiaireService){
        this.stagiaireService = stagiaireService;
    }

    @GetMapping
    public List<Stagiaire> getAllStagiaires(){
        return stagiaireService.getAll();
    }
    
    @GetMapping("/{id}")
    public Stagiaire getStagiaireById(@PathVariable UUID id){
        return stagiaireService.getById(id);
    }

    @PostMapping 
    public Stagiaire createStagiaire(@RequestBody Stagiaire stagiaire){
        return stagiaireService.create(stagiaire);
    }

    @PutMapping("/{id}")
    public Stagiaire updateStagiaire(@PathVariable UUID id, @RequestBody Stagiaire stagiaire) {
        return stagiaireService.update(id, stagiaire);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStagiaire(@PathVariable UUID id) {
        stagiaireService.delete(id);
        return ResponseEntity.ok("stagiaire supprimée avec succès.");
    }
}
