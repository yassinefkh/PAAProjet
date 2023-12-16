

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class GestionBornesRecharge {

 
    public static String listeVillesAvecBornesRecharge(CommunauteAgglomeration communaute) {
        List<Ville> villesAvecBornes = new ArrayList<>();
        for (Ville ville : communaute.getVilles()) {
            if (ville.possedeBorneRecharge()) {
                villesAvecBornes.add(ville);
            }
        }
        StringBuilder builder = new StringBuilder();
        for (Ville ville : villesAvecBornes) {
            builder.append(ville.getNom()).append(" ");
        }
        return builder.toString();
    }

 
    public static boolean peutRetirerBorneRecharge(Ville ville, CommunauteAgglomeration communaute) {
        if (!ville.possedeBorneRecharge()) {
            return false;
        }
    
        // Vérifie si la ville est isolée (non reliée à aucune autre ville)
        boolean estIsolée = true;
        for (Route route : communaute.getRoutes()) {
            Ville villeA = route.getVilleA();
            Ville villeB = route.getVilleB();
    
            if ((ville.equals(villeA) || ville.equals(villeB)) && (villeA.possedeBorneRecharge() || villeB.possedeBorneRecharge())) {
                estIsolée = false;
                break;
            }
        }

        if (estIsolée) {
            return false;
        }
    
        for (Route route : communaute.getRoutes()) {
            Ville villeA = route.getVilleA();
            Ville villeB = route.getVilleB();
    
            if ((ville.equals(villeA) && !villeB.possedeBorneRecharge()
                    && !estRelieeAVilleAvecBorne(villeB, ville, communaute)) ||
                    (ville.equals(villeB) && !villeA.possedeBorneRecharge()
                            && !estRelieeAVilleAvecBorne(villeA, ville, communaute))) {
                return false; // La ville n'est pas la seule source de recharge pour une autre ville
            }
        }
    
        return true;
    }
    

      private static boolean estRelieeAVilleAvecBorne(Ville ville, Ville villeException, CommunauteAgglomeration communaute) {
        for (Route route : communaute.getRoutes()) {
            if (route.getVilleA().equals(ville) && !route.getVilleB().equals(villeException)
                    && route.getVilleB().possedeBorneRecharge()) {
                return true;
            }
            if (route.getVilleB().equals(ville) && !route.getVilleA().equals(villeException)
                    && route.getVilleA().possedeBorneRecharge()) {
                return true;
            }
        }
        return false;
    }

    
}