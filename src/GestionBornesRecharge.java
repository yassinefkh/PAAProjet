import java.util.ArrayList;
import java.util.List;

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
        // indique si la ville est reliée à une autre ville avec une borne de recharge
        boolean villeRelieeAvecBorne = false;
        // indique si la ville est reliée à une autre ville
        boolean villeEstReliee = false;

        // parcourt toutes les routes de la communauté d'agglomération
        for (Route route : communaute.getRoutes()) {
            if (route.getVilleA().equals(ville) || route.getVilleB().equals(ville)) {
                villeEstReliee = true;
                Ville autreVille = (route.getVilleA().equals(ville)) ? route.getVilleB() : route.getVilleA();
                // si l'autre ville possède une borne de recharge, la ville courante est
                // considérée comme reliée à une ville avec borne
                if (autreVille.possedeBorneRecharge()) {
                    villeRelieeAvecBorne = true;
                }
            }
        }
        // si la ville n'est reliée à aucune autre ville on ne peut pas retirer sa borne
        // (ville isolée)
        if (!villeEstReliee) {
            return false;
        }
        // retourne vrai si la ville est reliée à une autre ville ayant une borne de
        // recharge
        return villeRelieeAvecBorne;
    }

}