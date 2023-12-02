import java.util.Random;

public class AlgorithmeApproximation {
    
    public static void ameliorerCommunaute(CommunauteAgglomeration CA, int k) {
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
