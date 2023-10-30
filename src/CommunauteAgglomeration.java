import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CommunauteAgglomeration {
    private final List<Ville> villes;
    private final List<Route> routes;
    private final Map<String, Ville> nomVilleAssociation;

    public CommunauteAgglomeration() {
        villes = new ArrayList<>();
        routes = new ArrayList<>();
        nomVilleAssociation = new HashMap<>();
    }

    public void ajouterVille(Ville ville) {
        villes.add(ville);
        nomVilleAssociation.put(ville.getNom(), ville);
    }

    public void ajouterRoute(Route route) {
        routes.add(route);
    }

    public List<Ville> getVilles() {
        return villes;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public Ville getVilleParNom(String nom) {
        return nomVilleAssociation.get(nom);
    }

    public void ajouterVilleParNom(String nom, Ville ville) {
        nomVilleAssociation.put(nom, ville);
    }
    
    public void afficherVillesAvecZoneDeRecharge() {
        System.out.print("Villes avec zone de recharge : ");
        for (Ville ville : villes) {
            if (ville.possedeBorneRecharge()) {
                System.out.print(ville.getNom() + " ");
            }
        }
        System.out.println();
    }

}