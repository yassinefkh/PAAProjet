import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        CommunauteAgglomeration communaute = new CommunauteAgglomeration();

        System.out.println(" \n /// Gestionnaire de bornes de recharge /// \n");
        configurerVilles(communaute);
        configurerRoutes(communaute);
        gererBornesDeRecharge(communaute);

        System.out.println("\n ------->  Liste des villes avec des bornes de recharge : "
                + GestionBornesRecharge.listeVillesAvecBornesRecharge(communaute));

        scanner.close();
    }

    private static void configurerVilles(CommunauteAgglomeration communaute) {
        System.out.print("Combien de villes souhaitez-vous configurer ? ");
        int nombreVilles = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < nombreVilles; i++) {
            System.out.print("Nom de la ville " + (i + 1) + ": ");
            String nomVille = scanner.nextLine();
            communaute.ajouterVille(new Ville(nomVille));
        }
    }

    private static void configurerRoutes(CommunauteAgglomeration communaute) {

        boolean configTerminee = false;

        while (!configTerminee) {
            System.out.println("\n Menu de configuration des routes : ");
            System.out.println("1) Ajouter une route");
            System.out.println("2) Terminer la configuration des routes");
            System.out.print("Choix : ");
            int choix = scanner.nextInt();
            scanner.nextLine();

            if (choix == 1) {
                ajouterRoute(communaute);
            } else if (choix == 2) {
                configTerminee = true;
            } else {
                System.out.println("Choix invalide, veuillez choisir une option valide.");
            }
        }
    }

    private static void ajouterRoute(CommunauteAgglomeration communaute) {
        System.out.print("Ville de départ : ");
        String nomVilleA = scanner.nextLine();
        System.out.print("Ville d'arrivée : ");
        String nomVilleB = scanner.nextLine();

        Ville villeA = communaute.getVilleParNom(nomVilleA);
        Ville villeB = communaute.getVilleParNom(nomVilleB);

        if (villeA != null && villeB != null) {
            Route route = new Route(villeA, villeB);
            communaute.ajouterRoute(route);
            System.out.println("Route ajoutée entre " + nomVilleA + " et " + nomVilleB + ".");
        } else {
            System.out.println("Ville non trouvée, vérifiez les noms.");
        }
    }

    private static void gererBornesDeRecharge(CommunauteAgglomeration communaute) {
        boolean gestionTerminee = false;

        while (!gestionTerminee) {
            System.out.println("\n -- Menu de gestion des bornes de recharge : ");
            System.out.println("1) Ajouter une zone de recharge");
            System.out.println("2) Retirer une zone de recharge");
            System.out.println("3) Terminer la gestion");
            System.out.println("Liste des villes avec des bornes de recharge : "
                    + GestionBornesRecharge.listeVillesAvecBornesRecharge(communaute));
            System.out.print("Choix : ");
            int choixGestion = scanner.nextInt();
            scanner.nextLine();

            switch (choixGestion) {
                case 1:
                    ajouterBorneRecharge(communaute);
                    break;
                case 2:
                    retirerBorneRecharge(communaute);
                    break;
                case 3:
                    gestionTerminee = true;
                    break;
                default:
                    System.out.println("Choix invalide, veuillez choisir une option valide.");
            }
        }
    }

    private static void ajouterBorneRecharge(CommunauteAgglomeration communaute) {
        System.out.print("Nom de la ville où ajouter une zone de recharge : ");
        String nomVilleAjout = scanner.nextLine();
        Ville villeAjout = communaute.getVilleParNom(nomVilleAjout);

        if (villeAjout != null) {
            if (!villeAjout.possedeBorneRecharge()) {
                villeAjout.ajouterBorneRecharge();
                System.out.println("Zone de recharge ajoutée à la ville " + nomVilleAjout + ".");
            } else {
                System.out.println("Cette ville a déjà une zone de recharge.");
            }
        } else {
            System.out.println("Ville non trouvée. Vérifiez le nom.");
        }
    }

    private static void retirerBorneRecharge(CommunauteAgglomeration communaute) {
        System.out.print("Nom de la ville où retirer une zone de recharge : ");
        String nomVilleRetrait = scanner.nextLine();
        Ville villeRetrait = communaute.getVilleParNom(nomVilleRetrait);

        if (villeRetrait != null) {
            if (GestionBornesRecharge.peutRetirerBorneRecharge(villeRetrait, communaute)) {
                villeRetrait.retirerBorneRecharge();
                System.out.println("Zone de recharge retirée de la ville " + nomVilleRetrait + ".");
            } else {
                System.out.println("Impossible de retirer la borne de recharge. D'autres villes en dépendent.");
            }
        } else {
            System.out.println("Ville non trouvée. Vérifiez le nom.");
        }
    }

}
