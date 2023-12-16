import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GestionBornesRecharge {

    /**
     * Renvoie la liste des noms des villes avec des bornes de recharge.
     *
     * @param communaute La communauté d'agglomération à analyser.
     * @return Une chaîne de caractères contenant les noms des villes avec des bornes de recharge, séparés par des espaces.
     */
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

    /**
     * Vérifie si une borne de recharge peut être retirée d'une ville sans isoler d'autres villes avec bornes de recharge.
     *
     * @param ville      La ville à vérifier.
     * @param communaute La communauté d'agglomération à laquelle appartient la ville.
     * @return true si la borne peut être retirée sans isoler d'autres villes, sinon false.
     */
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

    /**
     * Vérifie si une ville donnée est reliée à une autre ville avec une borne de recharge, à l'exception d'une ville spécifiée.
     *
     * @param ville           La ville à vérifier.
     * @param villeException  La ville à exclure de la vérification.
     * @param communaute      La communauté d'agglomération à laquelle appartiennent les villes.
     * @return true si la ville est reliée à une autre ville avec une borne de recharge (à l'exception de villeException), sinon false.
     */
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