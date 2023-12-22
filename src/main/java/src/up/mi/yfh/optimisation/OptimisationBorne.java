package main.java.src.up.mi.yfh.optimisation;

import java.util.Random;
import java.util.Set;
import java.util.HashSet;
import main.java.src.up.mi.yfh.agglomeration.*;
import main.java.src.up.mi.yfh.gestion.*;

public class OptimisationBorne {

    /**
     * Améliore la communauté d'agglomération en ajustant les bornes de recharge
     * pour les véhicules électriques.
     *
     * Explication algo : Approche aléatoier, il y a une tentative d'optimisation
     * (pas totalement naif donc) mais
     * peut ne pas etre très efficace ou rapide pour de grands graphes notamment
     * avec bcp de cliques.
     * Cet algo ne va jamais donner toujours la même solution, qui ne sera pas
     * toujours optimale.
     * 
     * @param CA La communauté d'agglomération à améliorer.
     * @param k  Le nombre d'itérations pour l'amélioration.
     */
    public static void algorithmeApproximation(CommunauteAgglomeration CA, int k) {
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
     * Calcule le score de la communauté d'agglomération, c'est-à-dire le nombre de
     * villes avec une borne de recharge.
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

    /**
     * Cet algorithme utilise une approche heuristique pour selectionner les villes
     * Il selectionne les villes en fonction du nombre de leur couvertures maximal
     * qu'elles
     * peuvent fournir, c-a-d on tente de minimiser le nombre de bornes de recharges
     * necessaires
     * en se concentrant sur les villes les plus stratégiquement importantes.
     * Methode efficace pour trouver d'assez bonnes solutions dans des graphes
     *  ou il y a une forte présence de clique.
     * Trouve toujours la solution presque optimale pour un même graphe.
     * 
     * @param communaute La communauté d'agglomération à traiter.
     */
    public static void algorithmeCouverture(CommunauteAgglomeration communaute) {
        initialiserCommunauteSansBornes(communaute);
        Set<Ville> villesNonCouvertes = new HashSet<>(communaute.getVilles());

        while (!villesNonCouvertes.isEmpty()) {
            Ville selectedVertex = selectBestVertexToCover(communaute, villesNonCouvertes);
            if (selectedVertex != null) {
                selectedVertex.ajouterBorneRecharge();
                updateStatutVillesNonCouvertes(selectedVertex, villesNonCouvertes, communaute);
            }
        }
    }

    /**
     * Initialise la communauté en retirant toutes les bornes de recharge.
     * 
     * @param communaute La communauté d'agglomération à initialiser.
     */
    public static void initialiserCommunauteSansBornes(CommunauteAgglomeration communaute) {
        for (Ville ville : communaute.getVilles()) {
            ville.retirerBorneRecharge(); // Retire toutes les bornes de recharge
        }
    }

    /**
     * Selectionne la meilleure ville à couvrir en fonction des villes non couvertes
     * restantes.
     * 
     * @param communaute         La communauté d'agglomération.
     * @param villesNonCouvertes L'ensemble des villes non couvertes.
     * @return La meilleure ville à couvrir, ou null si aucune ville appropriée
     *         trouvée.
     */
    private static Ville selectBestVertexToCover(CommunauteAgglomeration communaute, Set<Ville> villesNonCouvertes) {
        Ville bestVille = null;
        int maxCover = -1;

        /*
         * on parcourt toutes les villes non couvertes pour trouver celle avec le plus
         * grand
         * potentiel de couverture
         */
        for (Ville ville : villesNonCouvertes) {
            int coverCount = compterCouverturePotentielle(ville, communaute);
            if (coverCount > maxCover) {
                bestVille = ville;
                maxCover = coverCount;
            }
        }

        return bestVille;
    }

    /**
     * Compte le nombre de routes potentielles qu'une ville peut couvrir dans la
     * communauté
     * 
     * @param ville      La ville à évaluer.
     * @param communaute La communauté d'agglomération.
     * @return Le nombre de routes potentielles que la ville peut couvrir.
     */
    private static int compterCouverturePotentielle(Ville ville, CommunauteAgglomeration communaute) {
        int count = 0;
        for (Route route : communaute.getRoutes()) {
            Ville villeA = route.getVilleA();
            Ville villeB = route.getVilleB();
            if (ville.equals(villeA) && !villeB.possedeBorneRecharge()) {
                count++;
            } else if (ville.equals(villeB) && !villeA.possedeBorneRecharge()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Met à jour le statut des villes non couvertes en fonction de la ville
     * sélectionnée.
     * 
     * @param ville              La ville qui vient d'être couverte.
     * @param villesNonCouvertes L'ensemble des villes non couvertes.
     * @param communaute         La communauté d'agglomération.
     */
    private static void updateStatutVillesNonCouvertes(Ville ville, Set<Ville> villesNonCouvertes,
            CommunauteAgglomeration communaute) {
        villesNonCouvertes.remove(ville);

        /**
         * on parcourt les routes pour mettre à jour les villes non couvertes restantes
         */
        for (Route route : communaute.getRoutes()) {
            if (route.getVilleA().equals(ville)) {
                villesNonCouvertes.remove(route.getVilleB());
            } else if (route.getVilleB().equals(ville)) {
                villesNonCouvertes.remove(route.getVilleA());
            }
        }
    }

}