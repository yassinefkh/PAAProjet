package test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Communaute {
    private final List<Villes> villes; //liste des villes dans la communauté
    private final List<Route> routes;  //liste des routes entre les villes
    private final Map<String, Villes> nomVilleAssociation;  //association entre les noms de ville et les instances de villes
    
    public Communaute() {
        villes = new ArrayList<>();  // Initialisation de la liste des villes
        routes = new ArrayList<>();  // Initialisation de la liste des routes
        nomVilleAssociation = new HashMap<>();   // Initialisation de la map d'association entre les noms de ville et les instances de Villes
    }
    
    /**
     * Ajoute une nouvelle ville à la communauté.
     *
     * @param ville La nouvelle ville à ajouter à la communauté.
     *              Cette méthode met à jour la liste des villes et l'association entre les noms de ville et les instances de Villes.
     */
    public void ajouterVille(Villes ville) {
        villes.add(ville);  // Ajout de la ville à la liste des villes
        nomVilleAssociation.put(ville.getNom(), ville);  // Association du nom de la ville à son instance dans la map
    }

    /**
     * Ajoute une nouvelle route à la communauté.
     *
     * @param route La nouvelle route à ajouter à la communauté.
     *              Cette méthode met à jour la liste des routes.
     */
    public void ajouterRoute(Route route) {
        routes.add(route); // Ajout de la route à la liste des routes
    }
    
    /**
     * Obtient la liste des villes dans la communauté.
     *
     * @return Une liste contenant les instances de Villes dans la communauté.
     */
    public List<Villes> getVilles() {
        return villes;
    }
    /**
     * Obtient la liste des routes dans la communauté.
     *
     * @return Une liste contenant les instances de Route représentant les connexions entre les villes.
     */
    public List<Route> getRoutes() {
        return routes;
    }
    
    /**
     * Obtient une ville spécifique dans la communauté en utilisant son nom.
     *
     * @param nom Le nom de la ville que l'on souhaite obtenir.
     * @return L'instance de Villes correspondante au nom donné, ou null si aucune ville n'est trouvée.
     */
    public Villes getVilleParNom(String nom) {
        return nomVilleAssociation.get(nom);
    }
    
    /**
     * Associe un nom de ville à une instance de Villes dans la communauté.
     *
     * @param nom   Le nom de la ville à associer.
     * @param ville L'instance de Villes correspondante.
     *              Cette méthode met à jour l'association entre les noms de ville et les instances de Villes.
     */
    public void ajouterVilleParNom(String nom, Villes ville) {
        nomVilleAssociation.put(nom, ville); // Association du nom de la ville à son instance dans la map
    }
    
    /**
     * Affiche les villes de la communauté qui possèdent une zone de recharge.
     * Imprime la liste des noms des villes avec une zone de recharge.
     */
    public void afficherVillesAvecZoneDeRecharge() {
        System.out.print("Villes avec zone de recharge : ");
        for (Villes ville : villes) {
            if (ville.getZoneRecharge()) {
                System.out.print(ville.getNom() + " ");
            }
        }
        System.out.println();
    }

}
