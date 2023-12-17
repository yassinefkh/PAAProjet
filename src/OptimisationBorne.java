

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.List;


public class OptimisationBorne {
    
    /**
     * Améliore la communauté d'agglomération en ajustant les bornes de recharge pour les véhicules électriques.
     *
     * Explication algo : Approche aléatoier, il y a une tentative d'optimisation (pas totalement naif donc) mais
     * peut ne pas etre très efficace ou rapide pour de grands graphes notamment avec bcp de cliques.
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




    /**
     * Cet algorithme utilise une approche heuristique pour selectionner les villes
     * Il selectionne les villes en fonction du nombre de leur couvertures maximal qu'elles
     * peuvent fournir, c-a-d on tente de minimiser le nombre de bornes de rehcagres necessaires 
     * en se concentrant sur les villes les plus stratégiquement importantes.
     * Methode efficace pour trouevr d'assez bonnes solutions dans des graphes ou il a une forte présence 
     * de clique.
     */
    public static void alomVertexCover(CommunauteAgglomeration communaute) {
        initialiserCommunauteSansBornes(communaute); 
        Set<Ville> villesNonCouvertes = new HashSet<>(communaute.getVilles());

        while (!villesNonCouvertes.isEmpty()) {
            Ville selectedVertex = selectBestVertexToCover(communaute, villesNonCouvertes);
            if (selectedVertex != null) {
                selectedVertex.ajouterBorneRecharge();
                updateCoveredStatus(selectedVertex, villesNonCouvertes, communaute);
            }
        }
    }


    public static void initialiserCommunauteSansBornes(CommunauteAgglomeration communaute) {
        for (Ville ville : communaute.getVilles()) {
            ville.retirerBorneRecharge(); // Retire toutes les bornes de recharge
        }
    }
    

    private static Ville selectBestVertexToCover(CommunauteAgglomeration communaute, Set<Ville> villesNonCouvertes) {
        Ville bestVille = null;
        int maxCover = -1;

        for (Ville ville : villesNonCouvertes) {
            int coverCount = countPotentialCoverage(ville, communaute);
            if (coverCount > maxCover) {
                bestVille = ville;
                maxCover = coverCount;
            }
        }

        return bestVille;
    }


    private static int countPotentialCoverage(Ville ville, CommunauteAgglomeration communaute) {
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


    private static void updateCoveredStatus(Ville ville, Set<Ville> villesNonCouvertes, CommunauteAgglomeration communaute) {
        villesNonCouvertes.remove(ville);
        for (Route route : communaute.getRoutes()) {
            if (route.getVilleA().equals(ville)) {
                villesNonCouvertes.remove(route.getVilleB());
            } else if (route.getVilleB().equals(ville)) {
                villesNonCouvertes.remove(route.getVilleA());
            }
        }
    }




}
