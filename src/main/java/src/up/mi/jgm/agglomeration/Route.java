package src.up.mi.jgm.agglomeration;

/**
 * Classe représentant une route dans une communauté d'agglomération.
 * Chaque route relie deux villes.
 */

public class Route {
    private final Ville villeA;
    private final Ville villeB;

    /**
     * Constructeur parametré pour créer une nouvelle route.
     *
     * @param villeA La première ville reliée par la route.
     * @param villeB La deuxième ville reliée par la route.
     */
    public Route(Ville villeA, Ville villeB) {
        this.villeA = villeA;
        this.villeB = villeB;
    }

    /**
     * Obtient la première ville reliée par la route.
     *
     * @return La première ville (villeA).
     */
    public Ville getVilleA() {
        return villeA;
    }

    /**
     * Obtient la deuxième ville reliée par la route.
     *
     * @return La deuxième ville (villeB).
     */
    public Ville getVilleB() {
        return villeB;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Route other = (Route) obj;
        return (villeA.equals(other.villeA) && villeB.equals(other.villeB)) ||
               (villeA.equals(other.villeB) && villeB.equals(other.villeA));
    }
}