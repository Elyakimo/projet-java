package com.onocia.gestionstage.model;

import java.util.UUID;

import jakarta.persistence.*;

@Entity 
@Table(name = "tuteur")
public class Tuteur {
    //permet de laisser POSTGRE générer les id   
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(name = "nom_tuteur")
    private String nomTuteur;
    @Column(name = "prenom_tuteur")
    private String prenomTuteur;
    @Column(name = "numero_tuteur")
    private Integer numeroTuteur;
    
    //permet de récupérer les données grâce à la clée étrangère
    @ManyToOne 
    @JoinColumn(name = "id_entreprise")
    private Organisation organisation;

    public Tuteur(){
        //constructeur vide requis par JPA/Hibernate
    }
    public Tuteur(String nomTuteur, String prenomTuteur, Integer numeroTuteur, Organisation organisation){
        this.nomTuteur = nomTuteur;
        this.prenomTuteur = prenomTuteur;
        this.numeroTuteur = numeroTuteur;
        this.organisation = organisation;
    }
    public UUID getId(){
        return id;
    }
    public String getNomTuteur(){
        return nomTuteur;
    }
    public String getPrenomTuteur(){
        return prenomTuteur;
    }
    public Integer getNumeroTuteur(){
        return numeroTuteur;
    }
    public Organisation getOrganisation(){
        return organisation;
    }
    public void setNomTuteur(String nomTuteur){
        this.nomTuteur = nomTuteur;
    }
    public void setPrenomTuteur(String prenomTuteur){
        this.prenomTuteur = prenomTuteur;
    }
    public void setNumeroTuteur(Integer numeroTuteur){
        this.numeroTuteur = numeroTuteur;
    }
    public void setOrganisation(Organisation organisation){
        this.organisation = organisation;
    }
    @Override
    public String toString() {
        return  nomTuteur + " " + prenomTuteur +
                "  \n téléphone tuteur = " + numeroTuteur +
                "  \n entreprise = " + (organisation != null ? organisation.getNomEntreprise() : "aucune") + '}';
    }
}
