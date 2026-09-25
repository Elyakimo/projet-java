package model;

public class Etudiant {
    private String nom;
    private String prenom;
    private int numeroEtudiant;  
    private String mailEtudiant;
    private String adresseEtudiant;
    private String classe;
    public Etudiant(String nom, String prenom, int numeroEtudiant, String mailEtudiant, String adresseEtudiant, String classe){
        this.nom = nom;
        this.prenom = prenom;
        this.numeroEtudiant = numeroEtudiant;
        this.mailEtudiant = mailEtudiant;
        this.adresseEtudiant = adresseEtudiant;
        this.classe = classe;
    }

    public String getNom(){
        return nom;
    }
    public String getPrenom(){
        return prenom;
    }
    public int getNumeroEtudiant(){
        return numeroEtudiant;
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

    @Override
    public String toString() {
        return  prenom + " " + nom +
                "  \n mail étudiant = " + mailEtudiant +
                "  \n numéro étudiant = " + numeroEtudiant +
                "  \n adresse étudiant = " + adresseEtudiant + 
                "  \n classe = " + classe;
    }
}
