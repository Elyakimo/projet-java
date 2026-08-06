package service;

import java.util.List;

public class GestionnaireStage {
    public void listerStages() {
        // Implémentation de la méthode pour lister les stages
        ListeStages listeStages = new ListeStages();
        for (Stage stage : ListeStages.getStages()) {
            System.out.println(stage);
        }
    }
    public void ajouterStage(Stage stage) {
        // Implémentation de la méthode pour ajouter un stage
        ListeStages.ajouterStage(stage);
    }
    public void supprimerStage(Stage stage) {
        // Implémentation de la méthode pour supprimer un stage
        ListeStages.supprimerStage(stage);
    }
    public void modifierStatutStage(Stage stage, StatutStage nouveauStatut) {
        // Implémentation de la méthode pour modifier le statut d'un stage
        stage.setStatut(nouveauStatut);
    }
    public List<Stage> rechercherParStatut(StatutStage statut) {
        // Implémentation de la méthode pour rechercher des stages par statut
        return ListeStages.rechercherParStatut(statut);
    }
    public List<Stage> rechercherParEtudiant(Etudiant etudiant) {
        // Implémentation de la méthode pour rechercher des stages par étudiant
        return ListeStages.rechercherParEtudiant(etudiant);
    }
    public List<Stage> rechercherParEntreprise(Entreprise entreprise) {
        // Implémentation de la méthode pour rechercher des stages par entreprise
        return ListeStages.rechercherParEntreprise(entreprise);
    }
}
