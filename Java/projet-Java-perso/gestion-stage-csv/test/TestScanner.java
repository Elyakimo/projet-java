import java.util.Scanner;

public class TestScanner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez votre age : ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Entrez votre nom : ");
        String nom = scanner.nextLine();
        System.out.println("Vous avez " + age + " ans et votre nom est " + nom);
        scanner.close();
    }
}
