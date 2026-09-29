package com.onocia.gestionstage.repository;

import com.onocia.gestionstage.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, UUID> {
    
}
