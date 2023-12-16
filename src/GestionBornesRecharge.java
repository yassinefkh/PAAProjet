
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
    
        Set<Ville> villesConnectees = new HashSet<>();
        
        villesConnectees.add(ville);
        
        List<Ville> villesAExplorer = new ArrayList<>(villesConnectees);
        
        while (!villesAExplorer.isEmpty()) {
            Ville villeCourante = villesAExplorer.remove(0);
            
            for (Route route : communaute.getRoutes()) {
                Ville villeA = route.getVilleA();
                Ville villeB = route.getVilleB();
    
                if (villeA.equals(villeCourante) && !villesConnectees.contains(villeB) && villeB.possedeBorneRecharge()) {
                    villesConnectees.add(villeB);
                    villesAExplorer.add(villeB);
                }
                if (villeB.equals(villeCourante) && !villesConnectees.contains(villeA) && villeA.possedeBorneRecharge()) {
                    villesConnectees.add(villeA);
                    villesAExplorer.add(villeA);
                }
            }
        }

        for (Route route : communaute.getRoutes()) {
            Ville villeA = route.getVilleA();
            Ville villeB = route.getVilleB();
    
            if ((ville.equals(villeA) && villesConnectees.contains(villeB)) ||
                (ville.equals(villeB) && villesConnectees.contains(villeA))) {
                return true; 
            }
        }
    
        return false; 
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