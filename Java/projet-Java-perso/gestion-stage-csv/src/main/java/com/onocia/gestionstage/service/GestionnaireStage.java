package service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.io.BufferedReader;
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
    public void sauvegarder(String cheminFichier){
        try (FileWriter writer = new FileWriter(cheminFichier)){
            for (Stage stage : stages){
                Etudiant e = stage.getEtudiant();
                Entreprise ent = stage.getEntreprise();
                String ligne = e.getNom() + ";" + e.getPrenom() + ";" + e.getNumeroEtudiant() + ";" + e.getMailEtudiant() + ";" + e.getAdresseEtudiant() + ";" + e.getClasse() + ";"
                        + ent.getNomEntreprise() + ";" + ent.getNomTuteur() + ";" + ent.getPrenomTuteur() + ";"
                        + ent.getMailEntreprise() + ";" + ent.getTelephoneEntreprise() + ";" + ent.getAdresseEntreprise()
                        + ";" + stage.getStatut() + ";" + stage.getDateDebut() + ";" + stage.getDateFin();
                writer.write(ligne + "\n");
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }
    public void charger(String cheminFichier) {
        try (FileReader reader = new FileReader(cheminFichier)) {
            BufferedReader bufferedReader = new BufferedReader(reader);
            String ligne;
            while ((ligne = bufferedReader.readLine()) != null){
                String[] champs = ligne.split(";");
                String nomEtudiant = champs[0];
                String prenomEtudiant = champs[1];
                int numeroEtudiant = Integer.parseInt(champs[2]);
                String mailEtudiant = champs[3];
                String adresseEtudiant = champs[4];
                String classe = champs[5];
                String nomEntreprise = champs[6];
                String nomTuteur = champs[7];
                String prenomTuteur = champs[8];
                String mailEntreprise = champs[9];
                int telephoneEntreprise = Integer.parseInt(champs[10]);
                String adresseEntreprise = champs[11];
                StatutStage statut = StatutStage.valueOf(champs[12]);
                LocalDate dateDebut = LocalDate.parse(champs[13]);
                LocalDate dateFin = LocalDate.parse(champs[14]);
                Etudiant etudiant = new Etudiant(nomEtudiant, prenomEtudiant, numeroEtudiant, mailEtudiant, adresseEtudiant, classe);
                Entreprise entreprise = new Entreprise(nomEntreprise, nomTuteur, prenomTuteur, mailEntreprise, telephoneEntreprise, adresseEntreprise);
                Stage stage = new Stage(etudiant, entreprise, statut, dateDebut, dateFin);
                stages.add(stage);
            }
        
        }
        catch(IOException e){
            System.out.println("Erreur lors du chargement : " + e.getMessage());
        }
    }
}
