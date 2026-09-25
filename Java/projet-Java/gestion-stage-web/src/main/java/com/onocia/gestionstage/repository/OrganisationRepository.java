package com.onocia.gestionstage.repository;

import com.onocia.gestionstage.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface OrganisationRepository extends JpaRepository<Organisation, UUID> {
    
}
