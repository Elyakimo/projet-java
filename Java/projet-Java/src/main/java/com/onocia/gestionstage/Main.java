import model.Etudiant;
import model.Entreprise;
import model.Stage;
import model.StatutStage;
import service.GestionnaireStage;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        GestionnaireStage gestionnaire = new GestionnaireStage();
        int choix = -1;
        while (choix != 0) {
            System.out.println("=====Gestionnaire de stages=====");
            System.out.println("1. Ajouter un stage");
            System.out.println("2. Lister les stages");
            System.out.println("3. Rechercher les stages par statut");
            System.out.println("4. Rechercher les stages par étudiant");
            System.out.println("5. Rechercher les stages par entreprise");
            System.out.println("0. Quitter");
            System.out.print("Choix : ");
            choix = scanner.nextInt();
            switch (choix) {
                case 1 -> {
                    scanner.nextLine();
                    System.out.println("Veuillez saisir les informations du stage: ");
                    System.out.print("Nom de l'étudiant : ");
                    String nomEtudiant = scanner.nextLine();
                    System.out.print("Prénom de l'étudiant : ");
                    String prenomEtudiant = scanner.nextLine();
                    System.out.print("Numéro de l'étudiant : ");
                    int numeroEtudiant = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Mail de l'étudiant : ");
                    String mailEtudiant = scanner.nextLine();
                    System.out.println("Adresse de l'étudiant : ");
                    String adresseEtudiant = scanner.nextLine();
                    System.out.println("Classe de l'étudiant : ");
                    String classe = scanner.nextLine();
                    System.out.print("Nom de l'entreprise : ");
                    String nomEntreprise = scanner.nextLine();
                    System.out.print("Nom du tuteur : ");
                    String nomTuteur = scanner.nextLine();
                    System.out.print("Prénom du tuteur : ");
                    String prenomTuteur = scanner.nextLine();
                    System.out.print("Mail de l'entreprise : ");
                    String mailEntreprise = scanner.nextLine();
                    System.out.print("Téléphone de l'entreprise : ");
                    int telephoneEntreprise = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Adresse de l'entreprise : ");
                    String adresseEntreprise = scanner.nextLine();
                    System.out.print("Statut du stage (CANDIDATURE, EN_COURS, TERMINER, REFUSE) : ");
                    String statutStr = scanner.nextLine();
                    StatutStage statut = StatutStage.valueOf(statutStr);
                    System.out.print("Date de début du stage (YYYY-MM-DD) : ");
                    String dateDebutStr = scanner.nextLine();
                    System.out.print("Date de fin du stage (YYYY-MM-DD) : ");
                    String dateFinStr = scanner.nextLine();
                    Etudiant etudiant = new Etudiant(nomEtudiant, prenomEtudiant, numeroEtudiant, mailEtudiant, adresseEtudiant, classe);
                    Entreprise entreprise = new Entreprise(nomEntreprise, nomTuteur, prenomTuteur, mailEntreprise, telephoneEntreprise, adresseEntreprise);
                    Stage stage = new Stage(etudiant, entreprise, statut, java.time.LocalDate.parse(dateDebutStr), java.time.LocalDate.parse(dateFinStr));
                    gestionnaire.ajouterStage(stage);
                    System.out.println("Stage ajouté avec succès ! Voici les nouvelles informations : " + stage);
                }
                 
                case 2 -> { 
                    System.out.println("Liste des stages :");
                    for (Stage stage : gestionnaire.listerStages()) { 
                        System.out.println(stage); 
                    } 
                }
                case 3 -> { 
                    System.out.print("Entrez le statut (CANDIDATURE, EN_COURS, TERMINER, REFUSE) : "); 
                    String statutStr = scanner.next(); 
                    StatutStage statut = StatutStage.valueOf(statutStr); 
                    System.out.println("Stages avec le statut" + statut + " :"); 
                    for (Stage stage : gestionnaire.rechercherParStatut(statut)) { 
                        System.out.println(stage);
                    } 
                }
                case 4 -> {
                    System.out.println("Entrez le nom de l'étudiant : ");
                    String nomEtudiant = scanner.next();
                    System.out.println("Stages pour l'étudiant " + nomEtudiant + " : "); 
                    for (Stage stage : gestionnaire.rechercherParNomEtudiant(nomEtudiant)){
                        System.out.println(stage);
                    }
                }
                case 5 -> {
                    System.out.println("Entrez le nom de l'entreprise : "); 
                    String nomEntreprise = scanner.next(); 
                    System.out.println("Stages pour l'entreprise " + nomEntreprise + " : "); 
                    for (Stage stage : gestionnaire.rechercherParNomEntreprise(nomEntreprise)){
                        System.out.println(stage);
                    }
                }
                case 0 -> {
                    System.out.println("Au revoir !");
                }
                default -> {
                    System.out.println("Choix invalide. Veuillez réessayer.");
                }
               
            } 
            
        }scanner.close();
    }
}
