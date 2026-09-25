package com.onocia.gestionstage.model;

import jakarta.persistence.Table;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity 
@Table(name = "stagiaire")
public class Stagiaire {
    //permet de laisser POSTGRE générer les id
    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;
    @Column(name = "nom") private String nom;
    @Column(name = "prenom") private String prenom;
    @Column(name = "telephone_etudiant") private Integer telephoneEtudiant;
    @Column(name = "mail_etudiant") private String mailEtudiant;
    @Column(name = "adresse_etudiant") private String adresseEtudiant;
    @Column(name = "classe") private String classe;

    public Stagiaire(){
        // constructeur vide requis par JPA/HIBERNATE
    }
    public Stagiaire(String nom, String prenom, Integer telephoneEtudiant, String mailEtudiant, String adresseEtudiant, String classe){
        this.nom = nom;
        this.prenom = prenom;
        this.telephoneEtudiant = telephoneEtudiant;
        this.mailEtudiant = mailEtudiant;
        this.adresseEtudiant = adresseEtudiant;
        this.classe = classe;
    }
    public UUID getId(){
        return id;
    }
    public String getNom(){
        return nom;
    }
    public String getPrenom(){
        return prenom;
    }
    public Integer getTelephoneEtudiant(){
        return telephoneEtudiant;
    }
    public String getMailEtudiant(){
        return mailEtudiant;
    }
    public String getAdresseEtudiant(){
        return adresseEtudiant;
    }
    public String getClasse(){
        return classe;
    }
    public void setNom(String nom){
        this.nom = nom;
    }
    public void setPrenom(String prenom){
        this.prenom = prenom;
    }
    public void setTelephoneEtudiant(Integer telephoneEtudiant){
        this.telephoneEtudiant = telephoneEtudiant;
    }
    public void setMailEtudiant(String mailEtudiant){
        this.mailEtudiant = mailEtudiant;
    }
    public void setAdresseEtudiant(String adresseEtudiant){
        this.adresseEtudiant = adresseEtudiant;
    }
    public void setClasse(String classe){
        this.classe = classe;
    }
    @Override
    public String toString() {
        return  nom + " " + prenom + 
                "  \n mail étudiant = " + mailEtudiant +
                "  \n téléphone étudiant = " + telephoneEtudiant +
                "  \n adresse étudiant = " + adresseEtudiant + " " + 
                "\n classe étudiant =" + classe + '}';
    }
    
}
