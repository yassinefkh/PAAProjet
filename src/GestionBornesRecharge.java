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
     * Détermine si une borne de recharge peut être retirée d'une ville, en
     * respectant la contrainte
     * d'accessibilité.
     *
     * @param ville      La ville sur laquelle on souhaite vérifier la possibilité
     *                   de retrait.
     * @param communaute L'instance de la communauté d'agglomération où se situe la
     *                   ville.
     * @return true si la borne peut être retirée, false sinon.
     */
    public static boolean peutRetirerBorneRecharge(Ville ville, CommunauteAgglomeration communaute) {

        // Si la ville est isolée (sans routes la connectant aux autres), ne pas retirer
        // la borne
        if (estIsolee(ville, communaute)) {
            return false;
        }

        // Si la ville n'a pas de borne de recharge, on ne peut pas la retirer
        if (!ville.possedeBorneRecharge()) {
            return false;
        }

        // on retire temporairement la borne de recharge pour test
        ville.retirerBorneRecharge();

        // verificatoin de l'accessibilité des autres villes à une ville avec borne
        boolean accesPossible = true;
        for (Ville autreVille : communaute.getVilles()) {
            if (autreVille != ville && !estAccessibleAvecBorne(autreVille, communaute)) {
                accesPossible = false;
                break;
            }
        }

        // on remet la borne de recharge
        ville.ajouterBorneRecharge();

        return accesPossible;
    }

    private static boolean estAccessibleAvecBorne(Ville ville, CommunauteAgglomeration communaute) {
        Map<Ville, Boolean> visitees = new HashMap<>();
        return dfsBorne(ville, communaute, visitees);
    }

    private static boolean dfsBorne(Ville ville, CommunauteAgglomeration communaute, Map<Ville, Boolean> visitees) {
        visitees.put(ville, true);

        if (ville.possedeBorneRecharge()) {
            return true;
        }

        for (Route route : communaute.getRoutes()) {
            Ville villeA = route.getVilleA();
            Ville villeB = route.getVilleB();

            if ((ville.equals(villeA) || ville.equals(villeB)) && !visitees.getOrDefault(villeB, false)) {
                Ville villeSuivante = ville.equals(villeA) ? villeB : villeA;
                if (dfsBorne(villeSuivante, communaute, visitees)) {
                    return true;
                }
            }
        }

        return false;
    }

    // pour gérer le cas d'une ville isolée
    private static boolean estIsolee(Ville ville, CommunauteAgglomeration communaute) {
        for (Route route : communaute.getRoutes()) {
            if (route.getVilleA().equals(ville) || route.getVilleB().equals(ville)) {
                return false;
            }
        }
        return true;
    }

}
