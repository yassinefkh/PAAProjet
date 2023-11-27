import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Classe de gestion des bornes de recharge au sein d'une communauté
 * d'agglomération.
 */
public class GestionBornesRecharge {

    /**
     * Génère les villes possédant des bornes de recharge.
     *
     * @param communaute L'instance de la communauté d'agglomération dont on veut
     *                   lister les villes avec bornes.
     * @return String contenant les noms des villes avec bornes de recharge.
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
     * Vérifie si une borne de recharge peut être retirée d'une ville en respectant
     * la règle d'accessibilité
     * 
     * @param ville      La ville pour laquelle la borne de recharge est envisagée à
     *                   être retirée
     * @param communaute L'instance de la communauté d'agglomération
     * @return true si la borne de recharge peut être retirée, false sinon
     */
    public static boolean peutRetirerBorneRecharge(Ville ville, CommunauteAgglomeration communaute) {
        if (!ville.possedeBorneRecharge()) {
            return false;
        }

        // vérifie si la ville est la seule source de recharge pour une autre ville
        for (Route route : communaute.getRoutes()) {
            Ville villeA = route.getVilleA();
            Ville villeB = route.getVilleB();

            if ((ville.equals(villeA) && !villeB.possedeBorneRecharge()
                    && !estRelieeAVilleAvecBorne(villeB, ville, communaute)) ||
                    (ville.equals(villeB) && !villeA.possedeBorneRecharge()
                            && !estRelieeAVilleAvecBorne(villeA, ville, communaute))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Vérifie si une ville est reliée directement à une autre ville possédant une
     * borne de recharge, à l'exception d'une ville spécifique
     * 
     * @param ville          La ville à vérifier
     * @param villeException La ville à exclure de la vérification
     * @param communaute     L'instance de la communauté d'agglomération
     * @return true si la ville est reliée à une autre ville avec borne, false
     *         sinon
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