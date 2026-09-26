package com.onocia.gestionstage.controller;

import com.onocia.gestionstage.model.Tuteur;
import com.onocia.gestionstage.service.TuteurService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tuteurs")
public class TuteurController {

    private final TuteurService tuteurService;

    public TuteurController(TuteurService tuteurService){
        this.tuteurService = tuteurService;
    }

    @GetMapping
    public List<Tuteur> getAllTuteurs() {
        return tuteurService.getAll();
    }

    @GetMapping("/{id}")
    public Tuteur getTuteurById(@PathVariable UUID id){
        return tuteurService.getById(id);
    }

    @PostMapping
    public Tuteur createTuteur(@RequestBody Tuteur tuteur){
        return tuteurService.create(tuteur);
    }

    @PutMapping("/{id}")
    public Tuteur updateTuteur(@PathVariable UUID id, @RequestBody Tuteur tuteur){
        return tuteurService.update(id, tuteur);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTuteur(@PathVariable UUID id) {
        tuteurService.delete(id);
        return ResponseEntity.ok("Tuteur supprimée avec succès.");
    }
}