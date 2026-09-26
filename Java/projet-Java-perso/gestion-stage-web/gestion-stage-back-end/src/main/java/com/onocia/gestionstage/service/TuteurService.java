package com.onocia.gestionstage.service;

import com.onocia.gestionstage.model.Organisation;
import com.onocia.gestionstage.model.Tuteur;
import com.onocia.gestionstage.repository.TuteurRepository;
import com.onocia.gestionstage.repository.OrganisationRepository;
import com.onocia.gestionstage.exception.RessourceNonTrouveeException;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TuteurService {
    private final TuteurRepository tuteurRepository;
    private final OrganisationRepository organisationRepository;

    public TuteurService(TuteurRepository tuteurRepository, OrganisationRepository organisationRepository) {
        this.tuteurRepository = tuteurRepository;
        this.organisationRepository = organisationRepository;
    }

    public List<Tuteur> getAll() {
        return tuteurRepository.findAll();
    }

    public Tuteur getById(UUID id) {
        return tuteurRepository.findById(id)
                .orElseThrow(() -> new RessourceNonTrouveeException("Tuteur introuvable " + id));
    }

    public Tuteur create(Tuteur tuteur) {
        UUID organisationId = tuteur.getOrganisation().getId();
        Organisation organisation = organisationRepository.findById(organisationId)
                .orElseThrow(() -> new RessourceNonTrouveeException("Organisation introuvable" + organisationId));
        tuteur.setOrganisation(organisation);
        return tuteurRepository.save(tuteur);

    }

    public Tuteur update(UUID id, Tuteur nouvelleDonnees) {
        Tuteur existant = getById(id);
        existant.setNomTuteur(nouvelleDonnees.getNomTuteur());
        existant.setPrenomTuteur(nouvelleDonnees.getPrenomTuteur());
        existant.setNumeroTuteur(nouvelleDonnees.getNumeroTuteur());
        Organisation newOrganisation = organisationRepository.findById(nouvelleDonnees.getOrganisation().getId())
                .orElseThrow(() -> new RessourceNonTrouveeException("Organisation non trouvé " + id));
        existant.setOrganisation(newOrganisation);
        return tuteurRepository.save(existant);
    }

    public void delete(UUID id) {
        if (!tuteurRepository.existsById(id)) {
            throw new RessourceNonTrouveeException("Tuteur introuvable : " + id);
        }
        tuteurRepository.deleteById(id);
    }
}
