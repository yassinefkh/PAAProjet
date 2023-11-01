package test;
import java.util.Scanner ;
import java.util.List ;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in) ;
		System.out.println("Entrez le nombre villes : ") ;
		int nombreVille = scanner.nextInt() ;
		  
		//Saisir le nom de chaque ville
		Villes[] villes = new Villes[nombreVille] ;// tableau pour stocker les viles
		
		for(int i = 0; i<nombreVille; i++) {
			System.out.println("Entrez le nom de la villle "+ (i+1) +": ") ;
			String nomVille = scanner.next() ;
			villes[i] = new Villes(nomVille, false) ; //on crée un objet ville pour ensuite l'ajouter au tableau
		}
		
		 // Créez la communauté d'agglomération
        Communaute communaute = new Communaute(villes);

        int choice;

        do {
            System.out.println("\nMenu :");
            System.out.println("1) Ajouter une route");
            System.out.println("2) Fin");
            System.out.print("Choix : ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Ville de départ : ");
                    String villeA = scanner.next();
                    System.out.print("Ville d'arrivée : ");
                    String villeB = scanner.next();

                    Villes villeDepart = communaute.TrouverVilleParNom(villeA);
                    Villes villeFin = communaute.TrouverVilleParNom(villeB);

                    if (villeDepart != null && villeFin != null) {
                        communaute.ajouterRoute(villeDepart, villeFin);
                        System.out.println("Route ajoutée !");
                    } else {
                        System.out.println("Villes non trouvées, Veuillez entrez des noms valide.");
                    }
                    break;
                case 2:
                    System.out.println("Vous pouvez maintenant gérer les zones de recharge.");
                    break;
                default:
                    System.out.println("Choix non valide. Veuillez réessayer.");
            }
        } while (choice != 2);

        int rechargeChoice;

        do {
            System.out.println("\nMenu de gestion des zones de recharge :");
            System.out.println("1) Ajouter une zone de recharge");
            System.out.println("2) Retirer une zone de recharge");
            System.out.println("3) Fin");
            System.out.print("Choix : ");
            rechargeChoice = scanner.nextInt();

            switch (rechargeChoice) {
                case 1:
                    System.out.print("Dans quelle ville voulez-vous ajouter une zone de recharge : ");
                    String villeRecharge = scanner.next();
                    Villes villeRechargeObj = communaute.TrouverVilleParNom(villeRecharge);

                    if (villeRechargeObj != null) {
                        communaute.ajouterZoneRecharge(villeRechargeObj);
                        System.out.println("Zone de recharge ajoutée!");
                    } else {
                        System.out.println("Veuillez vérifier le nom de la ville.");
                    }
                    break;
                case 2:
                    System.out.print("Dans quelle ville voulez-vous  retirer une zone de recharge : ");
                    String villeRetrait = scanner.next();
                    Villes villeRetraitObj = communaute.TrouverVilleParNom(villeRetrait);

                    if (villeRetraitObj != null) {
                        if (communaute.supprimerZoneRecharge(villeRetraitObj)) {
                            System.out.println("Zone de recharge retirée!");
                        } else {
                            System.out.println("Impossible de retirer la zone de recharge. Choisissez une ville ayant une borne de recharge");
                        }
                    } else {
                        System.out.println("Ville non trouvée. Veuillez vérifier le nom de la ville.");
                    }
                    break;
                case 3:
                    System.out.println("Gestion des zones de recharge terminée.");
                    break;
                default:
                    System.out.println("Choix non valide. Veuillez réessayer.");
            }
        } while (rechargeChoice != 3);
        
        System.out.print("Villes avec des zones de recharge : ");
        
        //afficher les villes avec des zones de recharge
        List<String> villesAvecZonesRecharge = communaute.getVillesAvecZonesRecharge();
        for (String nomVille : villesAvecZonesRecharge) {
            System.out.print(nomVille + " ");
        }
        System.out.println();
	}
}


