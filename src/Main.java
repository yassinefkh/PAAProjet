import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CommunauteAgglomeration communaute = new CommunauteAgglomeration();

        System.out.print("Entrez le nombre de villes : ");
        int nombreVilles = scanner.nextInt();
        scanner.nextLine(); 

        for (int i = 0; i < nombreVilles; i++) {
            System.out.print("Nom de la ville " + (i + 1) + ": ");
            String nomVille = scanner.nextLine();
            communaute.ajouterVille(new Ville(nomVille));
        }

        // menu pour ajouter/retirer des zones de recharge
        while (true) {
            System.out.println("Villes avec zone de recharge : ");
            communaute.afficherVillesAvecZoneDeRecharge();
            System.out.println("Menu :");
            System.out.println("1) Ajouter une zone de recharge");
            System.out.println("2) Retirer une zone de recharge");
            System.out.println("3) Quitter");
            int choix = scanner.nextInt();
            scanner.nextLine(); 

            if (choix == 1) {
              // ajouter une zone de recharge
            } else if (choix == 2) {
                // retirer une zone de recharge
            } else if (choix == 3) {
                break;
            } else {
                System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }

        scanner.close();
    }

}