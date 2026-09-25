package com.onocia.gestionstage.repository;

import com.onocia.gestionstage.model.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface StageRepository extends JpaRepository<Stage, UUID> {
    
}
