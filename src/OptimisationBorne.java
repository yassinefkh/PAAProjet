import java.util.Random;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class OptimisationBorne {
    
    /**
     * Améliore la communauté d'agglomération en ajustant les bornes de recharge pour les véhicules électriques.
     *
     * @param CA La communauté d'agglomération à améliorer.
     * @param k  Le nombre d'itérations pour l'amélioration.
     */
    public static void algorithmeOpti(CommunauteAgglomeration CA, int k) {
        Random random = new Random();
        int i = 0;
        int scoreCourant = score(CA);

        while (i < k) {
            Ville villeChoisie = CA.getVilles().get(random.nextInt(CA.getVilles().size()));
            boolean avaitBorne = villeChoisie.possedeBorneRecharge();

            if (avaitBorne && GestionBornesRecharge.peutRetirerBorneRecharge(villeChoisie, CA)) {
                villeChoisie.retirerBorneRecharge();
            } else if (!avaitBorne) {
                villeChoisie.ajouterBorneRecharge();
            }

            if (CA.estSolutionValide()) {
                int nouveauScore = score(CA);
                if (nouveauScore < scoreCourant) {
                    scoreCourant = nouveauScore;
                    i = 0;
                } else {
                    i++;
                    // annule la modification si elle n'a pas amélioré le score
                    if (avaitBorne) {
                        villeChoisie.ajouterBorneRecharge();
                    } else {
                        villeChoisie.retirerBorneRecharge();
                    }
                }
            } else {
                // annule la modification si elle viole l'accessibilité
                i++;
                if (avaitBorne) {
                    villeChoisie.ajouterBorneRecharge();
                } else {
                    villeChoisie.retirerBorneRecharge();
                }
            }
        }
    }

    
     /**
     * Calcule le score de la communauté d'agglomération, c'est-à-dire le nombre de villes avec une borne de recharge.
     *
     * @param CA La communauté d'agglomération pour laquelle calculer le score.
     * @return Le score de la communauté d'agglomération.
     */
    private static int score(CommunauteAgglomeration CA) {
        int compteur = 0;
        for (Ville ville : CA.getVilles()) {
            if (ville.possedeBorneRecharge()) {
                compteur++;
            }
        }
        return compteur;
    }

   


}

