

import java.io.IOException;
import java.util.Scanner;
import java.util.Set;



import java.util.List;
import java.util.ArrayList;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static CommunauteAgglomeration communaute = new CommunauteAgglomeration();


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
                   // sauvegarderSolution();
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


    private static void resoudreAutomatiquement() {
        appliquerSolutionNaive(communaute);
        OptimisationBorne.algorithmeOpti(communaute, communaute.getVilles().size() * 200);
        CommunauteAgglomeration.afficherInformationsCommunaute(communaute);
    }

    
    private static void retirerToutesLesBornes(CommunauteAgglomeration communaute) {
        for (Ville ville : communaute.getVilles()) {
            ville.retirerBorneRecharge();
        }
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
            CommunauteAgglomeration.afficherInformationsCommunaute(communaute);
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

    /**
     * Applique une solution naïve en ajoutant une borne de recharge à chaque ville de la communauté
     * d'agglomération qui n'en possède pas déjà.
     *
     * Cette méthode parcourt toutes les villes de la communauté d'agglomération et ajoute une borne
     * de recharge à chaque ville qui n'en possède pas.
     *
     * @param communaute La communauté d'agglomération à laquelle appliquer la solution naïve.
     */
    private static void appliquerSolutionNaive(CommunauteAgglomeration communaute) {
        for (Ville ville : communaute.getVilles()) {
            if (!ville.possedeBorneRecharge()) {
                ville.ajouterBorneRecharge();
            }
        }
    }
    
    /**
     * Vérifie si la solution actuelle de la communauté d'agglomération est valide en respectant les contraintes d'accessibilité.
     *
     * Cette méthode parcourt toutes les villes de la communauté d'agglomération et vérifie si elles respectent la contrainte
     * d'accessibilité. Une ville est considérée valide si elle possède une borne de recharge ou si elle est reliée à une ville
     * qui en possède une.
     *
     * @param communaute La communauté d'agglomération à vérifier.
     * @return True si la solution est valide, False sinon.
     */
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
    
    /**
     * Vérifie si une ville donnée est reliée à une autre ville qui possède une borne de recharge.
     *
     * Cette méthode parcourt les routes de la communauté d'agglomération pour vérifier si la ville spécifiée
     * est reliée à une autre ville qui possède une borne de recharge. Elle permet de vérifier si une ville peut
     * être considérée comme valide en respectant la contrainte d'accessibilité.
     *
     * @param ville      La ville à vérifier.
     * @param communaute La communauté d'agglomération contenant les routes et les autres villes.
     * @return True si la ville est reliée à une autre ville avec une borne de recharge, False sinon.
     */
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