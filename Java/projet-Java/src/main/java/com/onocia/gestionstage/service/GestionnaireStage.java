package service;

import java.util.List;
import java.util.ArrayList;
import model.Etudiant;
import model.Entreprise;
import model.Stage;
import model.StatutStage;


public class GestionnaireStage {
    private List<Stage> stages;

    public GestionnaireStage() {
        this.stages = new ArrayList<>();
    }
    public void ajouterStage(Stage stage) {
        stages.add(stage);
    }
    public void supprimerStage(Stage stage) {
        stages.remove(stage);
    }
    public List<Stage> listerStages() {
        return new ArrayList<>(stages);
    }
    public void modifierStatutStage(Stage stage, StatutStage nouveauStatut) {
        stage.setStatut(nouveauStatut);
   
    }
    public List<Stage> rechercherParStatut(StatutStage statut) {
        List<Stage> stagesTrouves = new ArrayList<>();
        for (Stage stage : stages) {
            if (stage.getStatut() == statut) {
                stagesTrouves.add(stage);
            }
        }
        return stagesTrouves;
    }
    public List<Stage> rechercherParEtudiant(Etudiant etudiant) {
        List<Stage> stagesTrouves = new ArrayList<>();
        for (Stage stage : stages) {
            if (stage.getEtudiant().equals(etudiant)) {
                stagesTrouves.add(stage);
            }
        }
        return stagesTrouves;
    }
    public List<Stage> rechercherParEntreprise(Entreprise entreprise) {
        List<Stage> stagesTrouves = new ArrayList<>();
        for (Stage stage : stages) {
            if (stage.getEntreprise().equals(entreprise)) {
                stagesTrouves.add(stage);
            }
        }
        return stagesTrouves;
    }
    public List<Stage> rechercherParNomEtudiant(String nom) {
        List<Stage> stagesTrouves = new ArrayList<>();
        for (Stage stage : stages) {
            if (stage.getEtudiant().getNom().equalsIgnoreCase(nom)) {
                stagesTrouves.add(stage);
            }
        }
        return stagesTrouves;
    }
    public List<Stage> rechercherParNomEntreprise(String nomEntreprise) {
        List<Stage> stagesTrouves = new ArrayList<>();
        for (Stage stage : stages) {
            if (stage.getEntreprise().getNomEntreprise().equalsIgnoreCase(nomEntreprise)){
                stagesTrouves.add(stage);
            }
        }
        return stagesTrouves;
    }
}
