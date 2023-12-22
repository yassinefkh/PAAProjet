package src.up.mi.yfh.main;

import java.util.Scanner;
import java.io.IOException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import src.up.mi.yfh.agglomeration.*;
import src.up.mi.yfh.exception.*;
import src.up.mi.yfh.gestion.*;
import src.up.mi.yfh.optimisation.*;
import src.up.mi.yfh.loader.*;
import java.util.InputMismatchException;
import java.io.File;

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
        } catch ( VilleDupliqueeException | RouteDupliqueeException | VilleInexistanteException |  VilleRechargeInexistante  e) {
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
                scanner.nextLine(); 
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
        boolean gestionTerminee = false;
    
        while (!gestionTerminee) {
            System.out.println("\n*************************************************");
            System.out.println("     Menu de Résolution Automatique");
            System.out.println("*************************************************");
            System.out.println("Choisissez un algorithme :");
            System.out.println("1) Algorithme d'approximation");
            System.out.println("2) Algorithme Personnalisé");
            System.out.println("3) Retourner au menu principal");
            System.out.print("Votre choix : ");
    
            try {
                int choix = scanner.nextInt();
                scanner.nextLine();
                switch (choix) {
                    case 1:
                        System.out.println("\n* * * * * EXÉCUTION DE L'ALGORITHME D'APPROXIMATION * * * * *\n");
                        OptimisationBorne.algorithmeApproximation(communaute, communaute.getVilles().size() * 2);
                        CommunauteAgglomeration.afficherInformationsCommunaute(communaute);
                        System.out.println("* * * * * FIN DE L'ALGORITHME D'APPROXIMATION * * * * *\n");
                        break;
                    case 2:
                        System.out.println("\n* * * * * EXÉCUTION DE L'ALGORITHME PERSONNALISÉ * * * * *\n");
                        OptimisationBorne.algorithmeCouverture(communaute);
                        CommunauteAgglomeration.afficherInformationsCommunaute(communaute);
                        System.out.println("* * * * * FIN DE L'ALGORITHME PERSONNALISÉ * * * * *\n");
                        break;
                    case 3:
                        gestionTerminee = true;
                        break;
                    default:
                        System.out.println("Choix invalide, veuillez choisir une option valide.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrée invalide. Veuillez entrer un nombre.");
                scanner.nextLine();
            }
        }
    
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
            System.out.println("\n*************************************************");
            System.out.println("     Menu de Gestion Manuelle des Bornes de Recharge");
            System.out.println("*************************************************");
            CommunauteAgglomeration.afficherInformationsCommunaute(communaute);
            System.out.println("1) Ajouter une zone de recharge");
            System.out.println("2) Retirer une zone de recharge");
            System.out.println("3) Retourner au menu principal");
            System.out.print("Votre choix : ");

            try {
                int choix = scanner.nextInt();
                scanner.nextLine(); 

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
                scanner.nextLine(); 
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
            if (GestionBorneRecharge.peutRetirerBorneRecharge(villeRetrait, communaute)) {
                villeRetrait.retirerBorneRecharge();
                System.out.println("Zone de recharge retirée de la ville " + nomVilleRetrait + ".");
            } else {
                System.out.println("\n\033[31m[Erreur] Retrait de la zone de recharge refusé : Violation des règles d'accessibilité.\033[0m");
            }
        } else {
            System.out.println("Ville non trouvée, vérifiez le nom.");
        }
    }
    
    private static void sauvegarderSolution() {
        System.out.print("Veuillez entrer le chemin du fichier de sauvegarde : ");
        scanner.nextLine(); 
        String cheminSauvegarde = scanner.nextLine();

        // Vérification du chemin et des permissions
        File fichierSauvegarde = new File(cheminSauvegarde);
        if (!fichierSauvegarde.exists() || !fichierSauvegarde.canWrite()) {
            System.out.println("Vous n'avez pas la permission d'écrire dans ce fichier ou le chemin est invalide.");
            return; 
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cheminSauvegarde))) {
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

            System.out.println("Sauvegarde réussie dans le fichier : " + cheminSauvegarde);
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture dans le fichier : " + e.getMessage());
        }
    }


}   