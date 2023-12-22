package src.up.mi.yfh.agglomeration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe qui représente une communauté d'agglomeration.
 */
public class CommunauteAgglomeration {
    private final List<Ville> villes; // liste des villes dans la communauté d'agglomération
    private final List<Route> routes; // liste des routes entre les villes
    private final Map<String, Ville> nomVilleAssociation; // association entre les noms de ville et les instances

    /**
     * Constructeur par défaut de la classe CommunauteAgglomeration.
     * Initialise les listes de villes et de routes, ainsi que la map d'association
     * des noms de villes.
     */
    public CommunauteAgglomeration() {
        villes = new ArrayList<>();
        routes = new ArrayList<>();
        nomVilleAssociation = new HashMap<>();
    }

    /**
     * Ajoute une ville à la communauté d'agglomération.
     *
     * @param ville La ville à ajouter.
     */
    public void ajouterVille(Ville ville) {
        villes.add(ville);
        nomVilleAssociation.put(ville.getNom(), ville);
    }

    /**
     * Ajoute une route à la communauté d'agglomération si elle n'existe pas déjà.
     *
     * @param route La route à ajouter.
     * @return true si la route a été ajoutée avec succès, false si la route existe
     *         déjà.
     */
    public boolean ajouterRoute(Route route) {
        if (!routes.contains(route)) {
            routes.add(route);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Obtient la liste des villes dans la communauté
     * 
     * @return une liste contenant les instanecs de villes dans la communauté
     */
    public List<Ville> getVilles() {
        return villes;
    }

    /**
     * Obtient la liste des routes dans la communauté
     * 
     * @return Une liste contenant les instances de route représentant les
     *         conneixons entr eles villes
     */
    public List<Route> getRoutes() {
        return routes;
    }

    /**
     * Récupère une instance de la classe Ville associée à un nom donné.
     *
     * @param nom Le nom de la ville que vous souhaitez récupérer.
     * @return Une instance de la classe Ville correspondant au nom spécifié, ou
     *         null si aucune ville n'est associée à ce nom.
     */
    public Ville getVilleParNom(String nom) {
        return nomVilleAssociation.get(nom);
    }

    /**
     * Ajoute une association entre un nom de ville et une instance de la classe
     * Ville.
     *
     * @param nom   Le nom de la ville à associer.
     * @param ville L'instance de la classe Ville à associer au nom spécifié.
     */
    public void ajouterVilleParNom(String nom, Ville ville) {
        nomVilleAssociation.put(nom, ville);
    }

    /**
     * Affiche les villes de la communauté qui possèdent une zone de recharge
     */
    public void afficherVillesAvecZoneDeRecharge() {
        int nombreDeVilles = villes.size();
        int nombreDeBornes = 0;

        System.out.print("\n ** Villes avec zone de recharge : ");
        for (Ville ville : villes) {
            if (ville.possedeBorneRecharge()) {
                nombreDeBornes++;
                System.out.print(ville.getNom() + " ");
            }
        }

        System.out.println();
        System.out.println(" ** Economie - Nombre de bornes : " + nombreDeBornes + "/" + nombreDeVilles);
    }

    /**
     * Methode d'affichage des routes
     */
    public void afficherRoutes() {
        if (routes.isEmpty()) {
            System.out.println("Aucune route configurée actuellement.");
        } else {
            System.out.println("\n------ Routes actuelles dans la communauté d'agglomération ------");
            for (Route route : routes) {
                System.out.println(route.getVilleA().getNom() + " --- " + route.getVilleB().getNom());
            }
            System.out.println("-------------------------------------------------\n");
        }
    }

    /**
     * Vérifie si la solution actuelle est valide.
     *
     * @return true si la solution est valide, sinon false.
     */
    public boolean estSolutionValide() {
        for (Ville ville : this.getVilles()) {
            if (!ville.possedeBorneRecharge() && !estRelieeAVilleAvecBorne(ville)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Vérifie si une ville donnée est reliée à une autre ville avec une borne de
     * recharge.
     *
     * @param ville La ville à vérifier.
     * @return true si la ville est reliée à une autre ville avec une borne de
     *         recharge, sinon false.
     */
    public boolean estRelieeAVilleAvecBorne(Ville ville) {
        for (Route route : this.getRoutes()) {
            Ville villeA = route.getVilleA();
            Ville villeB = route.getVilleB();
            if ((ville.equals(villeA) && villeB.possedeBorneRecharge()) ||
                    (ville.equals(villeB) && villeA.possedeBorneRecharge())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Affiche les informations détaillées sur la communauté d'agglomération.
     */
    public static void afficherInformationsCommunaute(CommunauteAgglomeration communaute) {
        System.out.println("\n*************************************************");
        System.out.println("     Informations sur la communauté d'agglomération");
        System.out.println("*************************************************");

        System.out.println("\nListe des villes:");
        for (Ville ville : communaute.getVilles()) {
            String borneRecharge = ville.possedeBorneRecharge() ? " (avec borne de recharge)" : "";
            System.out.println("- " + ville.getNom() + borneRecharge);
        }

        System.out.println("\nListe des routes:");
        for (Route route : communaute.getRoutes()) {
            System.out.println("- Route entre " + route.getVilleA().getNom() + " et " + route.getVilleB().getNom());
        }

        System.out.println("\nListe des villes avec borne de recharge:");
        communaute.afficherVillesAvecZoneDeRecharge();

        System.out.println("\n*************************************************\n");
    }

    /**
     * Retire une borne de recharge de la ville spécifiée si elle appartient à la
     * communauté d'agglomération.
     *
     * @param ville La ville de laquelle retirer la borne de recharge.
     */
    public void retirerBorneRecharge(Ville ville) {
        if (villes.contains(ville)) {
            ville.retirerBorneRecharge();
        }
    }
}