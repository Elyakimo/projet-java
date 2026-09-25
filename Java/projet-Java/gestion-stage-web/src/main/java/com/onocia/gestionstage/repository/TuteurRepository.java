package com.onocia.gestionstage.repository;

import com.onocia.gestionstage.model.Tuteur;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TuteurRepository extends JpaRepository<Tuteur, UUID> {
    
}
