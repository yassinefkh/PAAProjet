package main.java.src.up.mi.yfh.main;

import java.util.Scanner;
import java.io.IOException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import main.java.src.up.mi.yfh.agglomeration.*;
import main.java.src.up.mi.yfh.exceptions.*;
import main.java.src.up.mi.yfh.gestion.*;
import main.java.src.up.mi.yfh.optimisation.*;
import main.java.src.up.mi.yfh.loader.*;
import java.util.InputMismatchException;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static CommunauteAgglomeration communaute = new CommunauteAgglomeration();
    private static String cheminFichier;
    
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java Main <chemin_du_fichier>");
            return;
        }
        cheminFichier = args[0];
        try {
            ChargerCommunauteDepuisFichier chargeur = new ChargerCommunauteDepuisFichier(cheminFichier);
            communaute = chargeur.getCommunaute();
        } catch (VilleDupliqueeExceptions | RouteDupliqueeExceptions | VilleInexisistanteExceptions | VIlleRechargeInexistante e) {
            System.out.println(e.getMessage());
            return; 
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement du fichier de communauté.");
            e.printStackTrace();
            return;
        }

        int choix = -1;
        do {
            afficherMenuPrincipal();
            try {
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
                    	// apres l'appel de sauvegarderSolution, affichez l'état de chaque ville
                    	System.out.println("État actuel des villes après la sauvegarde :");
                    	for (Ville ville : communaute.getVilles()) {
                    	    System.out.println(ville.getNom() + ": " + (ville.possedeBorneRecharge() ? "avec borne" : "sans borne"));
                    	}
                        break;
                    case 4:
                        System.out.println("Fin du programme.");
                        break;
                    default:
                        System.out.println("Choix invalide, veuillez réessayer.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Veuillez entrer un nombre valide.");
                scanner.nextLine(); // Important pour nettoyer le buffer du scanner
            }
        } while (choix != 4);
        scanner.close();
    }  
    
    private static void afficherMenuPrincipal() {
        System.out.println("======================================");
        System.out.println("           Menu Principal");
        System.out.println("======================================");
        System.out.println("1) Résoudre manuellement");
        System.out.println("2) Résoudre automatiquement");
        System.out.println("3) Sauvegarder");
        System.out.println("4) Fin");
        System.out.print("Entrez votre choix : ");
    }

    private static void resoudreAutomatiquement() {
        appliquerSolutionNaive(communaute);
        OptimisationBorne.algorithmeApproximation(communaute, communaute.getVilles().size() * 2);
        //OptimisationBorne.alomVertexCover(communaute);
        CommunauteAgglomeration.afficherInformationsCommunaute(communaute);
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

            try {
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
            } catch (InputMismatchException e) {
                System.out.println("Entrée invalide. Veuillez entrer un nombre.");
                scanner.nextLine(); // Nettoyer le buffer pour se débarrasser de l'entrée invalide
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
    
    private static void sauvegarderSolution() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cheminFichier))) {
            // Écrire les informations sur les villes
            for (Ville ville : communaute.getVilles()) {
                writer.write("ville(" + ville.getNom() + ").");
                writer.newLine();
            }

            // Écrire les informations sur les routes
            for (Route route : communaute.getRoutes()) {
                writer.write("route(" + route.getVilleA().getNom() + "," + route.getVilleB().getNom() + ").");
                writer.newLine();
            }

            // Écrire les informations sur les bornes de recharge
            for (Ville ville : communaute.getVilles()) {
                if (ville.possedeBorneRecharge()) {
                    writer.write("recharge(" + ville.getNom() + ").");
                    writer.newLine();
                }
            }

            System.out.println("Sauvegarde réussie dans le fichier : " + cheminFichier);
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture dans le fichier : " + e.getMessage());
        }
    }

}   