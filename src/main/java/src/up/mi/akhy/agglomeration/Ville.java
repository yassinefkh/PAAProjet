package src.up.mi.akhy.agglomeration;


/**
 * Classe représentant une ville dans une communauté d'agglomération.
 * Chaque ville peut posséder ou non une borne de recharge.
 */

public class Ville {
    private final String nom;
    private boolean possedeBorneRecharge;

    /**
     * Constructeur parametré pour créer une nouvelle ville
     *
     * @param nom Le nom de la ville.
     */
    public Ville(String nom) {
        this.nom = nom;
        this.possedeBorneRecharge = true; // une ville ne possède pas de borne de recharge par défaut
    }

    // getter
    public String getNom() {
        return nom;
    }

    public boolean possedeBorneRecharge() {
        return possedeBorneRecharge;
    }

    public void ajouterBorneRecharge() {
        possedeBorneRecharge = true;
    }

    public void retirerBorneRecharge() {
        possedeBorneRecharge = false;
    }
}