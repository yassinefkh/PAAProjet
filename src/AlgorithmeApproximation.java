import java.util.Random;

public class AlgorithmeApproximation {
    public static void ameliorerCommunaute(CommunauteAgglomeration CA, int k) {
        Random random = new Random();
        int i = 0;
        int scoreCourant = score(CA);

        while (i < k) {
            Ville villeChoisie = CA.getVilles().get(random.nextInt(CA.getVilles().size()));
            boolean avaitBorne = villeChoisie.possedeBorneRecharge();

            if (avaitBorne) {
                villeChoisie.retirerBorneRecharge();
            } else {
                villeChoisie.ajouterBorneRecharge();
            }

            int nouveauScore = score(CA);
            if (nouveauScore < scoreCourant) {
                i = 0;
                scoreCourant = nouveauScore;
            } else {
                if (avaitBorne) {
                    villeChoisie.ajouterBorneRecharge();
                } else {
                    villeChoisie.retirerBorneRecharge();
                }
                i++;
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
