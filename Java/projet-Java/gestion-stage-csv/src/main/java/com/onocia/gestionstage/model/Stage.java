package model;
import java.time.LocalDate;

public class Stage {
    private Etudiant etudiant;
    private Entreprise entreprise;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private StatutStage statut;
    
    public Stage(Etudiant etudiant, Entreprise entreprise, StatutStage statut, LocalDate dateDebut, LocalDate dateFin) {
        this.etudiant = etudiant;
        this.entreprise = entreprise;
        this.statut = statut;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }
    public Etudiant getEtudiant(){
        return etudiant;
    }
    public Entreprise getEntreprise(){
        return entreprise;
    }
    public StatutStage getStatut(){
        return statut;
    }
    public void setStatut(StatutStage statut){
        this.statut = statut;
    }
    public LocalDate getDateDebut(){
        return dateDebut;
    }
    public LocalDate getDateFin(){
        return dateFin;
    }
    @Override
    public String toString(){
        return "Stage{" + 
                "  \n étudiant = " + etudiant + 
                "  \n entreprise = " + entreprise + 
                "  \n date de Debut = " + dateDebut + 
                "  \n date de fin = " + dateFin +
                "  \n statut = " + statut + '}';

    }
}

