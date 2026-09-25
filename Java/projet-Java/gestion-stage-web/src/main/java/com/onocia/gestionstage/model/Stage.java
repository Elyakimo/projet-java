package com.onocia.gestionstage.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


@Entity 
@Table(name = "stage")
public class Stage {
    //permet de laisser POSTGRE générer les id
    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "statut_stage")
    private StatutStage statut;

    @Column (name = "date_debut")
    private LocalDateTime dateDebut;

    @Column(name = "date_fin")
    private LocalDateTime dateFin;
    
    //permet de récupérer les données grâce à la clée étrangère
    @ManyToOne
    @JoinColumn(name = "id_entreprise")
    private Organisation organisation;
    
    @ManyToOne 
    @JoinColumn(name = "id_tuteur")
    private Tuteur tuteur;

    @ManyToOne
    @JoinColumn(name = "id_stagiaire")
    private Stagiaire stagiaire;

    public Stage(){
        //constructeur vide requis par JPA/HIBERNATE
    }
    public Stage(StatutStage statut, LocalDateTime dateDebut, LocalDateTime dateFin, Organisation organisation, Tuteur tuteur, Stagiaire stagiaire){
        this.statut = statut;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.organisation = organisation;
        this.tuteur = tuteur;
        this.stagiaire = stagiaire;
    }
    public UUID getId(){
        return id;
    }
    public StatutStage getStatut(){
        return statut;
    }
    public LocalDateTime getDateDebut(){
        return dateDebut;
    }
    public LocalDateTime getDateFin(){
        return dateFin;
    }
    public Organisation getOrganisation(){
        return organisation;
    }
    public Tuteur getTuteur(){
        return tuteur;
    }
    public Stagiaire getStagiaire(){
        return stagiaire;
    }
    public void setStatut(StatutStage statut){
        this.statut = statut;
    }
    public void setDateDebut(LocalDateTime dateDebut){
        this.dateDebut = dateDebut;
    }
    public void setDateFin(LocalDateTime dateFin){
        this.dateFin = dateFin;
    }
    public void setOrganisation(Organisation organisation){
        this.organisation = organisation;
    }
    public void setTuteur(Tuteur tuteur){
        this.tuteur =  tuteur;
    }
    public void setStagiaire(Stagiaire stagiaire){
        this.stagiaire = stagiaire;
    }
    @Override 
    public String toString(){
        return statut + " " + dateDebut + " " + dateFin + " " + organisation + " " + tuteur + " " + stagiaire;
    }

}
