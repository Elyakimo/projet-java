package com.onocia.gestionstage.repository;

import com.onocia.gestionstage.model.Stagiaire;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface  StagiaireRepository extends JpaRepository<Stagiaire, UUID>{
    
}
