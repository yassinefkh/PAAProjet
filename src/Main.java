import java.util.Scanner;
import java.io.IOException;


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
            ChargerCommunaute chargeur = new ChargerCommunaute(cheminFichier);
            communaute = chargeur.getCommunaute();
        } catch (VilleDupliqueeException | RouteDupliqueeException | VilleInexistanteException | VilleRechargeInexistanteException e) {
            System.out.println(e.getMessage());
            return; 
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement du fichier de communauté.");
            e.printStackTrace();
            return;
        }

        int choix;
        do {
            afficherMenuPrincipal();
            choix = scanner.nextInt();

            switch (choix) {
                case 1:
                    resoudreManuellement();
                    break;
                case 2:
                    resoudreAutomatiquement();
                    break;
                case 3:
                    //sauvegarderSolution();
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
        //OptimisationBorne.algorithmeApproximation(communaute, communaute.getVilles().size());
        OptimisationBorne.algorithmeCouverture(communaute);
        CommunauteAgglomeration.afficherInformationsCommunaute(communaute);
    }
    

    private static void resoudreManuellement() {
        if (!estSolutionValide(communaute)) {
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
            if (!ville.possedeBorneRecharge()) {
                if (!estRelieeAVilleAvecBorne(ville, communaute)) {
                    return false; 
                }
            }
        }
        return true; 
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