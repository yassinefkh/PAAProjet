package test;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
    	// Création d'une instance de la communauté
        Communaute communaute = new Communaute();
        
        // Affichage du titre du gestionnaire de bornes de recharge
        System.out.println(" \n /// Gestionnaire de bornes de recharge /// \n");
        // Configuration des villes par l'utilisateur
        configurerVilles(communaute);
        // Configuration des routes par l'utilisateur
        configurerRoutes(communaute);
        // Gestion des bornes de recharge par l'utilisateur
        gererBornesDeRecharge(communaute);
        
        // Affichage de la liste des villes avec des bornes de recharge
        System.out.println("\n ------->  Liste des villes avec des bornes de recharge : "
                + GestionBornesRecharge.listeVillesAvecBornesRecharge(communaute));
     
        // Fermeture du scanner
        scanner.close();
    }
    
    /**
     * Configure les villes dans la communauté en fonction des entrées de l'utilisateur.
     * L'utilisateur est invité à spécifier le nombre de villes à configurer,
     * puis à fournir les noms de chaque ville.
     *
     * @param communaute L'instance de la communauté d'agglomération à configurer.
     */
    private static void configurerVilles(Communaute communaute) {
        System.out.print("Combien de villes souhaitez-vous configurer ? ");
        int nombreVilles = scanner.nextInt();
        scanner.nextLine(); // Pour consommer la nouvelle ligne après la saisie du nombre

        // Boucle pour configurer chaque ville
        for (int i = 0; i < nombreVilles; i++) {
        	 // Invite l'utilisateur à saisir le nom de la ville
            System.out.print("Nom de la ville " + (i + 1) + ": ");
            String nomVille = scanner.nextLine();
            
            //ajout de la nouvelle ville à la communauté
            communaute.ajouterVille(new Villes(nomVille));
        }
    }
    
    /**
     * Configure les routes dans la communauté en fonction des choix de l'utilisateur.
     * L'utilisateur peut choisir d'ajouter une nouvelle route ou de terminer la configuration.
     *
     * @param communaute L'instance de la communauté d'agglomération à configurer.
     */
    private static void configurerRoutes(Communaute communaute) {
        boolean configTerminee = false;
        
        // Continue la configuration jusqu'à ce que l'utilisateur décide de terminer
        while (!configTerminee) {
            System.out.println("\n Menu de configuration des routes : ");
            System.out.println("1) Ajouter une route");
            System.out.println("2) Terminer la configuration des routes");
            System.out.print("Choix : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); // Pour consommer la nouvelle ligne après la saisie du choix

            // Traite le choix de l'utilisateur
            if (choix == 1) {
                ajouterRoute(communaute); // Appelle la méthode pour ajouter une route
            } else if (choix == 2) {
                configTerminee = true;  // Termine la configuration des routes
            } else {
            	// Affiche un message d'erreur en cas de choix invalide
                System.out.println("Choix invalide, veuillez choisir une option valide.");
            }
        }
    }
    
    /**
     * Ajoute une nouvelle route à la communauté en fonction des villes spécifiées par l'utilisateur.
     * L'utilisateur est invité à fournir le nom de la ville de départ et le nom de la ville d'arrivée.
     * Si les deux villes existent, une nouvelle route est créée et ajoutée à la communauté.
     *
     * @param communaute L'instance de la communauté d'agglomération à laquelle la route sera ajoutée.
     */
    private static void ajouterRoute(Communaute communaute) {
    	// Demande à l'utilisateur le nom de la ville de départ
        System.out.print("Ville de départ : ");
        String nomVilleA = scanner.nextLine();
        
        // Demande à l'utilisateur le nom de la ville d'arrivée
        System.out.print("Ville d'arrivée : ");
        String nomVilleB = scanner.nextLine();
        
        // Obtient les instances de Villes correspondant aux noms spécifiés
        Villes villeA = communaute.getVilleParNom(nomVilleA);
        Villes villeB = communaute.getVilleParNom(nomVilleB);
        
        // Vérifie si les deux villes existent
        if (villeA != null && villeB != null) {
        	if (!existeDejaRoute(communaute, villeA, villeB)) {
        		// Crée une nouvelle route avec les villes de départ et d'arrivée
                Route route = new Route(villeA, villeB);
                
                communaute.ajouterRoute(route); // Ajoute la route à la communauté
                
                // Affiche un message de confirmation
                System.out.println("Route ajoutée entre " + nomVilleA + " et " + nomVilleB + ".");
        	} else {
        		// Affiche un message d'erreur si la route existe déjà
                System.out.println("La route entre " + nomVilleA + " et " + nomVilleB + " existe déjà.");
        	}
        }else {
        	// Affiche un message d'erreur si l'une des villes n'est pas trouvée
            System.out.println("Ville non trouvée, vérifiez les noms.");
        }
    }
    
    /**
     * Gère les bornes de recharge dans la communauté en fonction des choix de l'utilisateur.
     * L'utilisateur peut choisir d'ajouter une nouvelle zone de recharge, de retirer une zone de recharge
     * ou de terminer la gestion.
     *
     * @param communaute L'instance de la communauté d'agglomération à gérer.
     */
    private static void gererBornesDeRecharge(Communaute communaute) {
        boolean gestionTerminee = false;
        
        // Continue la gestion des bornes de recharge jusqu'à ce que l'utilisateur décide de terminer
        while (!gestionTerminee) {
        	// Affiche le menu de gestion
            System.out.println("\n -- Menu de gestion des bornes de recharge : ");
            System.out.println("1) Ajouter une zone de recharge");
            System.out.println("2) Retirer une zone de recharge");
            System.out.println("3) Terminer la gestion");
            System.out.println("Liste des villes avec des bornes de recharge : "
                    + GestionBornesRecharge.listeVillesAvecBornesRecharge(communaute));
            System.out.print("Choix : ");
            int choixGestion = scanner.nextInt();
            scanner.nextLine(); // Pour consommer la nouvelle ligne après la saisie du choix
            
         // Traite le choix de l'utilisateur en utilisant une instruction switch
            switch (choixGestion) {
                case 1:
                    ajouterBorneRecharge(communaute);  // Appelle la méthode pour ajouter une zone de recharge
                    break;
                case 2:
                    retirerBorneRecharge(communaute);   // Appelle la méthode pour retirer une zone de recharge
                    break;
                case 3:
                    gestionTerminee = true; // Termine la gestion des bornes de recharge
                    break;
                default:
                	 // Affiche un message d'erreur en cas de choix invalide
                    System.out.println("Choix invalide, veuillez choisir une option valide.");
            }
        }
    }

    private static void ajouterBorneRecharge(Communaute communaute) {
        System.out.print("Nom de la ville où ajouter une zone de recharge : ");
        String nomVilleAjout = scanner.nextLine();
        Villes villeAjout = communaute.getVilleParNom(nomVilleAjout);

        if (villeAjout != null) {
            if (!villeAjout.getZoneRecharge()) {
                villeAjout.setZoneRecharge(true);
                System.out.println("Zone de recharge ajoutée à la ville " + nomVilleAjout + ".");
            } else {
                System.out.println("Cette ville a déjà une zone de recharge.");
            }
        } else {
            System.out.println("Ville non trouvée. Vérifiez le nom.");
        }
    }
    
    /**
     * Gère le retrait d'une zone de recharge d'une ville dans la communauté.
     * L'utilisateur saisit le nom de la ville où retirer la zone de recharge, et
     * le programme effectue les vérifications nécessaires avant de retirer la zone.
     *
     * @param communaute L'instance de la communauté d'agglomération à gérer.
     */
    private static void retirerBorneRecharge(Communaute communaute) {
        System.out.print("Nom de la ville où retirer une zone de recharge : ");
        String nomVilleRetrait = scanner.nextLine();
        Villes villeRetrait = communaute.getVilleParNom(nomVilleRetrait);
        
        // Vérifie si la ville existe dans la communauté
        if (villeRetrait != null) {
        	 // Vérifie si la borne de recharge peut être retirée sans affecter d'autres villes
            if (GestionBornesRecharge.peutRetirerBorneRecharge(villeRetrait, communaute)) {
                villeRetrait.setZoneRecharge(false); // Retire la zone de recharge
                System.out.println("Zone de recharge retirée de la ville " + nomVilleRetrait + ".");
            } else {
                System.out.println("Impossible de retirer la borne de recharge. D'autres villes en dépendent.");
            }
        } else {
            System.out.println("Ville non trouvée. Vérifiez le nom.");
        }
    }
    
    /**
     * Vérifie si une route entre deux villes existe déjà dans la communauté.
     *
     * @param communaute L'instance de la communauté d'agglomération à vérifier.
     * @param villeA     La ville de départ.
     * @param villeB     La ville d'arrivée.
     * @return true si la route existe déjà, false sinon.
     */
    private static boolean existeDejaRoute(Communaute communaute, Villes villeA, Villes villeB) {
        for (Route route : communaute.getRoutes()) {
            if ((route.getVilleDepart().equals(villeA) && route.getVilleFin().equals(villeB)) ||
                (route.getVilleDepart().equals(villeB) && route.getVilleFin().equals(villeA))) {
                return true; // La route existe déjà
            }
        }
        return false; // La route n'existe pas encore
    }

}


