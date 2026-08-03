package main.java.com.onocia.gestionstage.model;
import java.time.LocalDate;

public class Stage {
    private String nom;
    private String prenom;
    private String numeroEtudiant;  
    private String mailEtudiant;
    private String adresseEtudiant;
    private String nomEntreprise;
    private String nomTuteur;
    private String prenomTuteur;
    private String mailEntreprise;
    private String telephoneEntreprise;
    private String adresseEntreprise;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private StatutStage statut;
    
    public Stage(String nom, String prenom, String numeroEtudiant, String mailEtudiant, String adresseEtudiant, String nomEntreprise, String nomTuteur, String prenomTuteur, String mailEntreprise, String telephoneEntreprise, String adresseEntreprise,StatutStage statut, LocalDate dateDebut, LocalDate dateFin ){
        this.nom = nom;
        this.prenom = prenom;
        this.numeroEtudiant = numeroEtudiant;
        this.mailEtudiant = mailEtudiant;
        this.adresseEtudiant = adresseEtudiant;
        this.nomEntreprise = nomEntreprise;
        this.nomTuteur = nomTuteur;
        this.prenomTuteur = prenomTuteur;
        this.mailEntreprise = mailEntreprise;
        this.telephoneEntreprise = telephoneEntreprise;
        this.adresseEntreprise = adresseEntreprise;
        this.statut = statut;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public String getNom(){
        return nom;
    }
    public String getPrenom(){
        return prenom;
    }
    public String getNumeroEtudiant(){
        return numeroEtudiant;
    }
    public String getMailEtudiant(){
        return mailEtudiant;
    }
    public String getAdresseEtudiant(){
        return adresseEtudiant;
    }
    public String getNomEntreprise(){
        return nomEntreprise;
    }
    public String getNomTuteur(){
        return nomTuteur;
    }
    public String getPrenomTuteur(){
        return prenomTuteur;
    }
    public String getMailEntreprise(){
        return mailEntreprise;
    }
    public String getTelephoneEntreprise(){
        return telephoneEntreprise;
    }
    public String getAdresseEntreprise(){
        return adresseEntreprise;
    }
    public String getStatut(){
        return statut;
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
                " étudiant= " + prenom + " " + nom + 
                " ,mail étudiant= " + mailEtudiant + 
                " ,numéro étudiant= " + numeroEtudiant +  
                " ,entreprise= " + nomEntreprise + '\'' +
                " , tuteur= " + prenomTuteur + " " + nomTuteur + '\'' + 
                " , date de Debut= " + dateDebut + 
                " , date de fin= " + dateFin +
                " , statut= " + statut + '}';

    }
}

