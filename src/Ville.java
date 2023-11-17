/**
 * Classe représentant une ville dans une communauté d'agglomération.
 * Chaque ville peut posséder ou non une borne de recharge.
 */

class Ville {
    private final String nom;
    private boolean possedeBorneRecharge;

    /**
     * Constructeur parametré pour créer une nouvelle ville
     *
     * @param nom Le nom de la ville.
     */
    public Ville(String nom) {
        this.nom = nom;
        this.possedeBorneRecharge = true; // une ville possède une borne de recharge par défaut
    }

    /**
     * Obtient le nom de la ville.
     *
     * @return Le nom de la ville.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Vérifie si la ville possède une borne de recharge.
     *
     * @return true si la ville possède une borne de recharge, false sinon.
     */
    public boolean possedeBorneRecharge() {
        return possedeBorneRecharge;
    }

    /**
     * Ajoute une borne de recharge à la ville.
     */
    public void ajouterBorneRecharge() {
        possedeBorneRecharge = true;
    }

    /**
     * Retire la borne de recharge de la ville.
     */
    public void retirerBorneRecharge() {
        possedeBorneRecharge = false;
    }
}
