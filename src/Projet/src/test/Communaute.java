package test;
import java.util.ArrayList ;
import java.util.List ;

public class Communaute {
	private Villes[] villes ;
	private ArrayList<Route> routes ;
	
	public Communaute(Villes[] villes) {
		this.villes = villes ;
		this.routes = new ArrayList<>() ;
	}
	
	public void ajouterRoute(Villes villeDepart, Villes villeFin) {
		Route nouvelleRoute = new Route(villeDepart, villeFin) ;//création d'une nouvelle route
		routes.add(nouvelleRoute) ; //on ajoute la route à la liste
	}
	
	public void ajouterZoneRecharge(Villes ville) {
		for(Villes v : villes) {
			if(v.equals(ville)) {
				v.setZoneRecharge(true) ;
				break ; 
			}
		}
	}
	
	public boolean supprimerZoneRecharge(Villes ville) {
	    for (Villes v : villes) {
	        if (v.equals(ville)) {
	            if (v.estZoneRecharge()) {
	                v.setZoneRecharge(false);
	                return true; // suppression de la zone de recharge 
	            } else {
	                return false; 
	            }
	        }
	    }
	    return false; 
	}

	
	// Méthode pour trouver une ville par son nom
    public Villes TrouverVilleParNom(String cityName) {
        for (Villes ville : villes) {
            if (ville.getNom().equalsIgnoreCase(cityName)) {
                return ville;
            }
        }
        return null; //Pas de ville
    }
    
    public List<String> getVillesAvecZonesRecharge() {
        List<String> villesAvecZonesRecharge = new ArrayList<String>();
        for (Villes ville : villes) {
            if (ville.estZoneRecharge()) {
                villesAvecZonesRecharge.add(ville.getNom());
            }
        }
        return villesAvecZonesRecharge;
    }

}