package com.onocia.gestionstage.model;

import jakarta.persistence.Table;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity 
@Table(name= "organisation")
public class Organisation {
    //permet de laisser POSTGRE générer les id
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;
    @Column(name = "nom_entreprise") private String nomEntreprise;
    @Column(name = "mail_entreprise") private String mailEntreprise;
    @Column(name = "telephone_entreprise") private Integer telephoneEntreprise;
    @Column(name = "adresse_entreprise") private String adresseEntreprise;
    
    public Organisation(){
        // constructeur vide requis par JPA/Hibernate
    }
    public Organisation(String nomEntreprise, String mailEntreprise, Integer telephoneEntreprise, String adresseEntreprise){
        this.nomEntreprise = nomEntreprise;
        this.mailEntreprise = mailEntreprise;
        this.telephoneEntreprise = telephoneEntreprise;
        this.adresseEntreprise = adresseEntreprise;
    }
    public UUID getId(){
        return id;
    }
    public String getMailEntreprise(){
        return mailEntreprise;
    }
    public Integer getTelephoneEntreprise(){
        return telephoneEntreprise;
    }
    public String getAdresseEntreprise(){
        return adresseEntreprise;
    }
    public String getNomEntreprise(){
        return nomEntreprise;
    }
    public void setNomEntreprise(String nomEntreprise){
        this.nomEntreprise = nomEntreprise;
    }
    public void setMailEntreprise(String mailEntreprise){
        this.mailEntreprise = mailEntreprise;
    }
    public void setAdresseEntreprise(String adresseEntreprise){
        this.adresseEntreprise = mailEntreprise;
    }
    public void setTelephoneEntreprise(Integer telephoneEntreprise){
        this.telephoneEntreprise = telephoneEntreprise;
    }
    @Override
    public String toString() {
        return  nomEntreprise +
                "  \n mail entreprise = " + mailEntreprise +
                "  \n téléphone entreprise = " + telephoneEntreprise +
                "  \n adresse entreprise = " + adresseEntreprise + '}';
    }

}
