import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class TestFichier {
    public static void main(String[] args) {
        // ÉCRITURE
        try (FileWriter writer = new FileWriter("test.csv")) {
            writer.write("Kanaky;Dawany;985757\n");
            writer.write("Toto;Martin;123456\n");
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture : " + e.getMessage());
        }

        System.out.println("Fichier écrit !");

        // LECTURE
        try (BufferedReader reader = new BufferedReader(new FileReader("test.csv"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                System.out.println("Ligne lue : " + ligne);
                String[] champs = ligne.split(";");
                System.out.println("  Nom : " + champs[0] + ", Prénom : " + champs[1] + ", Numéro : " + champs[2]);
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture : " + e.getMessage());
        }
    }
}