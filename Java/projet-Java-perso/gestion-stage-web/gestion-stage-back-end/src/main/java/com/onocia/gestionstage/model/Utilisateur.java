package com.onocia.gestionstage.model;

import jakarta.persistence.*;

import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import ch.qos.logback.classic.pattern.Util;


@Entity 
@Table(name = "utilisateur")
public class Utilisateur {
    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(name = "email")
    private String email;

    @Column(name = "role")
    private String role;

    @Column(name = "mot_de_passe")
    private String motDePasse;

    @ManyToOne 
    @JoinColumn(name = "id_stagiaire")
    private Stagiaire stagiaire;

    public Utilisateur(){

    }

    public Utilisateur(String email, String role, String motDePasse, Stagiaire stagiaire){
        this.email = email;
        this.motDePasse = motDePasse;
        this.role = role;
        this.stagiaire = stagiaire;
    }
    public UUID getId(){
        return id;
    }
    public String getEmail(){
        return email;
    }
    public String getRole(){
        return role;
    }
    public String getMotDePasse(){
        return motDePasse;
    }
    public Stagiaire getStagiaire(){
        return stagiaire;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setRole(String role){
        this.role = role;
    }
    public void setMotDePasse(String motDePasse){
        this.motDePasse = motDePasse;
    }
    public void setStagiaire(Stagiaire stagiaire){
        this.stagiaire = stagiaire;
    }
    @Override 
    public String toString(){
        return email + " " + role + " " + stagiaire;
    }
}

