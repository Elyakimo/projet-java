package com.onocia.gestionstage.controller;

import com.onocia.gestionstage.model.Stage;
import com.onocia.gestionstage.service.StageService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/stages")
public class StageController {

    private final StageService stageService;

    public StageController(StageService stageService){
        this.stageService = stageService;
    }

    @GetMapping
    public List<Stage> getAllStages() {
        return stageService.getAll();
    }

    @GetMapping("/{id}")
    public Stage getStageById(@PathVariable UUID id){
        return stageService.getById(id);
    }

    @PostMapping 
    public Stage createStage(@RequestBody Stage stage){
        return stageService.create(stage);
    }

    @PutMapping("/{id}")
    public Stage updateStage(@PathVariable UUID id, @RequestBody Stage stage){
        return stageService.update(id, stage);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStage(@PathVariable UUID id) {
        stageService.delete(id);
        return ResponseEntity.ok("Stage supprimée avec succès.");
    }
}
