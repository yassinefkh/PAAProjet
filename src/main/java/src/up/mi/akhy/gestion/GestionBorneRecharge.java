package src.up.mi.akhy.gestion;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import src.up.mi.akhy.agglomeration.*;

public class GestionBorneRecharge {

    /**
     * Renvoie la liste des noms des villes avec des bornes de recharge.
     *
     * @param communaute La communauté d'agglomération à analyser.
     * @return Une chaîne de caractères contenant les noms des villes avec des
     *         bornes de recharge, séparés par des espaces.
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
     * Vérifie si une borne de recharge peut être retirée d'une ville sans isoler
     * d'autres villes qui en dépendent.
     * Cette méthode effectue deux tests principaux:
     * 1. Vérifier si en retirant une borne de la ville en question, d'autres villes
     * reliées par des routes
     * et qui ne possèdent pas de borne restent accessibles à une borne dans une
     * autre ville.
     * 2. Vérifier si toutes les villes avec bornes restent interconnectées après le
     * retrait de la borne.
     *
     * @param ville      La ville à vérifier.
     * @param communaute La communauté d'agglomération à laquelle appartient la
     *                   ville.
     * @return true si la borne peut être retirée sans isoler d'autres villes, sinon
     *         false.
     */

    public static boolean peutRetirerBorneRecharge(Ville ville, CommunauteAgglomeration communaute) {
    	
        if (!ville.possedeBorneRecharge()) {
            return false;
        }
        // Premier test : vérification de la connectivité des villes sans borne
        List<Ville> villesConnectees = new ArrayList<>();
        for (Route route : communaute.getRoutes()) {
            if (route.getVilleA().equals(ville)) {
                villesConnectees.add(route.getVilleB());
            } else if (route.getVilleB().equals(ville)) {
                villesConnectees.add(route.getVilleA());
            }
        }

        // Pour chaque ville sans borne, vérifie si elle est connectée à une ville avec une borne
        for (Ville villeConnectee : villesConnectees) {
            if (!villeConnectee.possedeBorneRecharge()
                    && !estRelieeAVilleAvecBorne(villeConnectee, ville, communaute)) {
                return false;
            }
        }

        // Deuxième test : vérification de la connectivité de toutes les villes avec borne
        Set<Ville> villesConnecte = new HashSet<>();

        villesConnecte.add(ville);

        List<Ville> villesAExplorer = new ArrayList<>(villesConnecte);

        // Utilisation d'une recherche en largeur pour trouver toutes les villes connectées
        while (!villesAExplorer.isEmpty()) {
            Ville villeCourante = villesAExplorer.remove(0);

            for (Route route : communaute.getRoutes()) {
                Ville villeA = route.getVilleA();
                Ville villeB = route.getVilleB();

                // Si une route relie la ville courante à une autre ville avec une borne, l'ajoute à la liste des villes connectées
                if (villeA.equals(villeCourante) && !villesConnecte.contains(villeB) && villeB.possedeBorneRecharge()) {
                    villesConnecte.add(villeB);
                    villesAExplorer.add(villeB);
                }
                if (villeB.equals(villeCourante) && !villesConnecte.contains(villeA) && villeA.possedeBorneRecharge()) {
                    villesConnecte.add(villeA);
                    villesAExplorer.add(villeA);
                }
            }
        }

        // Vérifie si la ville actuelle est connectée à au moins une autre ville avec borne
        for (Route route : communaute.getRoutes()) {
            Ville villeA = route.getVilleA();
            Ville villeB = route.getVilleB();

            if ((ville.equals(villeA) && villesConnecte.contains(villeB)) ||
                    (ville.equals(villeB) && villesConnecte.contains(villeA))) {
                return true;
            }
        }
        // Si aucune des conditions ci-dessus n'est remplie, on ne peut pas retirer la borne
        return false;
    }

    /**
     * Vérifie si une ville donnée est reliée à une autre ville avec une borne de
     * recharge, à l'exception d'une ville spécifiée.
     *
     * @param ville          La ville à vérifier.
     * @param villeException La ville à exclure de la vérification.
     * @param communaute     La communauté d'agglomération à laquelle appartiennent
     *                       les villes.
     * @return true si la ville est reliée à une autre ville avec une borne de
     *         recharge (à l'exception de villeException), sinon false.
     */
    private static boolean estRelieeAVilleAvecBorne(Ville ville, Ville villeException,
            CommunauteAgglomeration communaute) {
    	 // Parcourt toutes les routes de la communauté d'agglomération
        for (Route route : communaute.getRoutes()) {
        	  // Vérifie si la route relie la ville à une autre ville avec une borne, en évitant la ville d'exception
            if ((route.getVilleA().equals(ville) && !route.getVilleB().equals(villeException)
                    && route.getVilleB().possedeBorneRecharge()) ||
                    (route.getVilleB().equals(ville) && !route.getVilleA().equals(villeException)
                            && route.getVilleA().possedeBorneRecharge())) {
                return true; // La ville est reliée à une ville avec une borne
            }
            // Vérifie si la route relie la ville à une autre ville avec une borne, sans éviter la ville d'exception
            if (route.getVilleB().equals(ville) && !route.getVilleA().equals(villeException)
                    && route.getVilleA().possedeBorneRecharge()) {
                return true; // La ville est reliée à une ville avec une borne
            }
        }
        return false; // Si aucune route ne relie la ville à une autre ville avec une borne, retourne false
    }
}