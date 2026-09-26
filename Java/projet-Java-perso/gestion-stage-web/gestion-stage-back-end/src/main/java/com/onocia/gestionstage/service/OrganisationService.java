package com.onocia.gestionstage.service;

import com.onocia.gestionstage.model.Organisation;
import com.onocia.gestionstage.repository.OrganisationRepository;
import com.onocia.gestionstage.exception.RessourceNonTrouveeException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrganisationService {
    private final OrganisationRepository organisationRepository;

    public OrganisationService(OrganisationRepository organisationRepository){
        this.organisationRepository = organisationRepository;
    }

    public List<Organisation> getAll(){
        return organisationRepository.findAll();
    }

    public Organisation getById(UUID id) {
        return organisationRepository.findById(id)
            .orElseThrow(() -> new RessourceNonTrouveeException("Organisation introuvable : "+ id));
    }
    
    public Organisation create(Organisation organisation){
        return organisationRepository.save(organisation);
    }

    public Organisation update(UUID id, Organisation nouvelleDonnees) {
        Organisation existante = getById(id);
        existante.setNomEntreprise(nouvelleDonnees.getNomEntreprise());
        existante.setMailEntreprise(nouvelleDonnees.getMailEntreprise());
        existante.setAdresseEntreprise(nouvelleDonnees.getAdresseEntreprise());
        existante.setTelephoneEntreprise(nouvelleDonnees.getTelephoneEntreprise());
        return organisationRepository.save(existante);
    }

    public void delete(UUID id){
        if (!organisationRepository.existsById(id)) {
            throw new RessourceNonTrouveeException("Organisation introuvable : " + id);
        }
        organisationRepository.deleteById(id);
    }
}
