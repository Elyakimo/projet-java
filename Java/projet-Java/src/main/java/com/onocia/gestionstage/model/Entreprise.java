package model;


public class Entreprise {
    private String nomEntreprise;
    private String nomTuteur;
    private String prenomTuteur;
    private String mailEntreprise;
    private int telephoneEntreprise;
    private String adresseEntreprise;  
    public Entreprise(String nomEntreprise, String nomTuteur, String prenomTuteur, String mailEntreprise, int telephoneEntreprise, String adresseEntreprise){
        this.nomEntreprise = nomEntreprise;
        this.nomTuteur = nomTuteur;
        this.prenomTuteur = prenomTuteur;
        this.mailEntreprise = mailEntreprise;
        this.telephoneEntreprise = telephoneEntreprise;
        this.adresseEntreprise = adresseEntreprise;
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
    public int getTelephoneEntreprise(){
        return telephoneEntreprise;
    }
    public String getAdresseEntreprise(){
        return adresseEntreprise;
    }
    @Override
    public String toString() {
        return  nomEntreprise +
                "  \n tuteur = " + prenomTuteur + " " + nomTuteur + 
                "  \n mail entreprise = " + mailEntreprise +
                "  \n téléphone entreprise = " + telephoneEntreprise +
                "  \n adresse entreprise = " + adresseEntreprise + '}';
    }

}
