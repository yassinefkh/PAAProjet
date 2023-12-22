package main.java.src.up.mi.yfh.agglomeration;
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


    public CommunauteAgglomeration() {
        villes = new ArrayList<>();
        routes = new ArrayList<>();
        nomVilleAssociation = new HashMap<>();
    }

    public void ajouterVille(Ville ville) {
        villes.add(ville);
        nomVilleAssociation.put(ville.getNom(), ville);
    }

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

    public Ville getVilleParNom(String nom) {
        return nomVilleAssociation.get(nom);
    }


    public void ajouterVilleParNom(String nom, Ville ville) {
        nomVilleAssociation.put(nom, ville);
    }

    /**
     * Affiche les villes de la communauté qui possèdent une zone de recharge
     */
    public void afficherVillesAvecZoneDeRecharge() {
        int nombreDeVilles = villes.size();
        int nombreDeBornes = 0;
    
        System.out.print("\nVilles avec zone de recharge : ");
        for (Ville ville : villes) {
            if (ville.possedeBorneRecharge()) {
                nombreDeBornes++;
                System.out.print(ville.getNom() + " ");
            }
        }
    
        System.out.println();
        System.out.println("Economie - Nombre de bornes : " + nombreDeBornes + "/" + nombreDeVilles);
    }
    
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
    * Vérifie si une ville donnée est reliée à une autre ville avec une borne de recharge.
    *
    * @param ville La ville à vérifier.
    * @return true si la ville est reliée à une autre ville avec une borne de recharge, sinon false.
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
        System.out.println("Informations sur la communauté d'agglomération:\n");
    
        System.out.println("Liste des villes:");
        for (Ville ville : communaute.getVilles()) {
            System.out.println("- " + ville.getNom() + (ville.possedeBorneRecharge() ? " (avec borne de recharge)" : ""));
        }
    
        System.out.println("\nListe des routes:");
        for (Route route : communaute.getRoutes()) {
            System.out.println("- Route entre " + route.getVilleA().getNom() + " et " + route.getVilleB().getNom());
        }
    
   
        communaute.afficherVillesAvecZoneDeRecharge();
    
        System.out.println("\n----------------------------------------------\n");
    }
    
    public void retirerBorneRecharge(Ville ville) {
        if (villes.contains(ville)) {
            ville.retirerBorneRecharge();
        }
    }
}