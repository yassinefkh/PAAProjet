import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static CommunauteAgglomeration communaute = new CommunauteAgglomeration();

  /*   public static void main(String[] args) {
        System.out.println("\n=== Gestionnaire de Bornes de Recharge ===");
        configurerVilles();
        configurerRoutes();
        gererBornesRecharge();
        scanner.close();
    } */

  
       /*  public static void main(String[] args) throws IOException {
            if (args.length < 1) {
                System.out.println("Usage: java Main <chemin_du_fichier>");
                return;
            }
    
            String cheminFichier = args[0];
            ChargerCommunauteDepuisFichier chargeur = new ChargerCommunauteDepuisFichier(cheminFichier);
            CommunauteAgglomeration communaute = chargeur.getCommunaute();
            
            // Affichage des informations sur la communauté
            afficherInformationsCommunaute(communaute);
        } */


    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java Main <chemin_du_fichier>");
            return;
        }
        String cheminFichier = args[0];
        try {
            ChargerCommunauteDepuisFichier chargeur = new ChargerCommunauteDepuisFichier(cheminFichier);
            communaute = chargeur.getCommunaute(); 
            
        
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement du fichier de communauté.");
            e.printStackTrace();
            return;
        }

        int choix;
        do {
            System.out.println("\nMenu:");
            System.out.println("1) Résoudre manuellement");
            System.out.println("2) Résoudre automatiquement");
            System.out.println("3) Sauvegarder");
            System.out.println("4) Fin");
            System.out.print("Entrez votre choix: ");
            choix = scanner.nextInt();

            switch (choix) {
                case 1:
                    resoudreManuellement();
                    break;
                case 2:
                    resoudreAutomatiquement();
                    break;
                case 3:
                    sauvegarderSolution();
                    break;
                case 4:
                    System.out.println("Fin du programme.");
                    break;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        } while (choix != 4);
        
        scanner.close();
    }

  /*   // ???
    private static void resoudreManuellement() {
        System.out.println("\n=== Gestionnaire de Bornes de Recharge ===");
        configurerVilles();
        configurerRoutes();
        gererBornesRecharge();
        scanner.close();
    } */

    private static void resoudreAutomatiquement() {
        // Appliquer la solution naïve pour commencer
        appliquerSolutionNaive(communaute);
    
        // Appliquer l'algorithme d'approximation pour optimiser les bornes de recharge
        AlgorithmeApproximation.ameliorerCommunaute(communaute, communaute.getVilles().size() * 2);
    
    
        // Afficher l'état final de la communauté
        afficherInformationsCommunaute(communaute);
    }
    
    

    private static void resoudreManuellement() {
        // Vérifier si la solution actuelle est valide ou non
        if (!estSolutionValide(communaute)) {
            // Si la solution n'est pas valide, appliquer la solution naïve
            appliquerSolutionNaive(communaute);
        }
    
        boolean gestionTerminee = false;
        while (!gestionTerminee) {
            System.out.println("\n--- Menu de Gestion Manuelle des Bornes de Recharge ---");
            afficherInformationsCommunaute(communaute);
            System.out.println("1) Ajouter une zone de recharge");
            System.out.println("2) Retirer une zone de recharge");
            System.out.println("3) Retourner au menu principal");
            System.out.print("Votre choix : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); // Nettoyer le buffer
    
            switch (choix) {
                case 1:
                    ajouterZoneDeRecharge();
                    break;
                case 2:
                    retirerZoneDeRecharge();
                    break;
                case 3:
                    gestionTerminee = true;
                    break;
                default:
                    System.out.println("Choix invalide, veuillez choisir une option valide.");
            }
        }
    }

  

    private static void appliquerSolutionNaive(CommunauteAgglomeration communaute) {
        for (Ville ville : communaute.getVilles()) {
            if (!ville.possedeBorneRecharge()) {
                ville.ajouterBorneRecharge();
            }
        }
    }
    
    private static boolean estSolutionValide(CommunauteAgglomeration communaute) {
        for (Ville ville : communaute.getVilles()) {
            // Vérifier si la ville possède une borne de recharge
            if (!ville.possedeBorneRecharge()) {
                // Si la ville n'a pas de borne de recharge, vérifier si elle est reliée à une ville qui en a une
                if (!estRelieeAVilleAvecBorne(ville, communaute)) {
                    return false; // Retourner false si une ville ne respecte pas la contrainte d'accessibilité
                }
            }
        }
        return true; // Toutes les villes respectent la contrainte
    }
    
    private static boolean estRelieeAVilleAvecBorne(Ville ville, CommunauteAgglomeration communaute) {
        for (Route route : communaute.getRoutes()) {
            Ville villeA = route.getVilleA();
            Ville villeB = route.getVilleB();
    
            // Vérifier si l'une des villes reliées possède une borne de recharge
            if ((ville.equals(villeA) && villeB.possedeBorneRecharge()) || 
                (ville.equals(villeB) && villeA.possedeBorneRecharge())) {
                return true;
            }
        }
        return false;
    }


    private static void sauvegarderSolution() {
        System.out.print("Entrez le chemin de sauvegarde pour la solution: ");
        scanner.nextLine(); 
        String cheminSauvegarde = scanner.nextLine();
        // TODO
    }
        
        private static void afficherInformationsCommunaute(CommunauteAgglomeration communaute) {
            System.out.println("Informations sur la communauté d'agglomération:");
    
            System.out.println("\nListe des villes:");
            for (Ville ville : communaute.getVilles()) {
                System.out.println("- " + ville.getNom() + (ville.possedeBorneRecharge() ? " (avec borne de recharge)" : ""));
            }
    
            System.out.println("\nListe des routes:");
            for (Route route : communaute.getRoutes()) {
                System.out.println("- Route entre " + route.getVilleA().getNom() + " et " + route.getVilleB().getNom());
            }
    
            //System.out.println("\nVilles avec zone de recharge:");
            communaute.afficherVillesAvecZoneDeRecharge();
        }
    
    
    
    private static void configurerVilles() {
        System.out.print("Combien de villes souhaitez-vous configurer ? ");
        int nombreVilles = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < nombreVilles; i++) {
            System.out.print("Nom de la ville " + (i + 1) + ": ");
            String nomVille = scanner.nextLine();
            communaute.ajouterVille(new Ville(nomVille));
        }
    }

    private static void configurerRoutes() {
        boolean configTerminee = false;

        while (!configTerminee) {
            System.out.println("\n================ Menu de Configuration =================");
            System.out.println("1) Ajouter une route");
            System.out.println("2) Terminer la configuration");
            System.out.print("Choix : ");
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    ajouterRoute();
                    break;
                case 2:
                    configTerminee = true;
                    break;
                default:
                    System.out.println("Choix invalide, veuillez choisir une option valide.");
            }
        }
    }

    private static void ajouterRoute() {
        System.out.print("Ville de départ : ");
        String nomVilleA = scanner.nextLine();
        System.out.print("Ville d'arrivée : ");
        String nomVilleB = scanner.nextLine();

        Ville villeA = communaute.getVilleParNom(nomVilleA);
        Ville villeB = communaute.getVilleParNom(nomVilleB);

        if (villeA != null && villeB != null) {
            Route route = new Route(villeA, villeB);
            boolean routeAjoutee = communaute.ajouterRoute(route);
            if (routeAjoutee) {
                System.out.println("Route ajoutée entre " + nomVilleA + " et " + nomVilleB + ".");
            } else {
                System.out.println("Cette route existe déjà entre " + nomVilleA + " et " + nomVilleB + ".");
            }
        } else {
            System.out.println("Ville non trouvée, vérifiez les noms.");
        }
    }

    private static void gererBornesRecharge() {
        boolean solutionTrouvee = false;

        while (!solutionTrouvee) {
            System.out.println("\n================ Menu de Gestion des Bornes de Recharge ================");
            communaute.afficherRoutes();
            System.out.println("1) Ajouter une zone de recharge");
            System.out.println("2) Retirer une zone de recharge");
            System.out.println("3) Terminer la gestion");
            System.out.println("---- Liste des villes avec des bornes de recharge ----");
            System.out.println(GestionBornesRecharge.listeVillesAvecBornesRecharge(communaute));
            System.out.println("-----------------------------------------------------");
            System.out.print("Votre choix : ");
            int choixGestion = scanner.nextInt();
            scanner.nextLine();

            switch (choixGestion) {
                case 1:
                    ajouterZoneDeRecharge();
                    break;
                case 2:
                    retirerZoneDeRecharge();
                    break;
                case 3:
                    solutionTrouvee = true;
                    break;
                default:
                    System.out.println("Choix invalide, veuillez choisir une option valide.");
            }
        }
    }

    private static void ajouterZoneDeRecharge() {
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

    private static void retirerZoneDeRecharge() {
        System.out.print("Nom de la ville où retirer une zone de recharge : ");
        String nomVilleRetrait = scanner.nextLine();
        Ville villeRetrait = communaute.getVilleParNom(nomVilleRetrait);

        if (villeRetrait != null) {
            if (GestionBornesRecharge.peutRetirerBorneRecharge(villeRetrait, communaute)) {
                villeRetrait.retirerBorneRecharge();
                System.out.println("Zone de recharge retirée de la ville " + nomVilleRetrait + ".");
            } else {
                System.out.println(
                    "\n[Erreur] Retrait de la zone de recharge refusé : Violation des règles d'accessibilité.");
            }
        } else {
            System.out.println("Ville non trouvée, vérifiez le nom.");
        }
    }
}