import model.Etudiant;
import model.Entreprise;
import model.Stage;
import model.StatutStage;
import service.GestionnaireStage;

public class Main {
    public static void main(String[] args) {
        Etudiant e1 = new Etudiant("test", "test", 1, "test@test.com", "123 Main St", "Classe A");
        Entreprise entreprise1 = new Entreprise("Onocia", "Doe", "John", "john.doe@onocia.com", 123456789, "456 Oak Ave");
        Stage stage1 = new Stage(e1, entreprise1, StatutStage.EN_COURS, java.time.LocalDate.of(2023, 6, 1), java.time.LocalDate.of(2023, 8, 31));
        System.out.println(stage1);
    }
}
