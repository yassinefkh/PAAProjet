package src.up.mi.yfh.exception;

/**
 * Cette classe représente une exception personnalisée pour les villes de
 * recharge inexistantes.
 */
public class VilleRechargeInexistante extends RuntimeException {

    /**
     * Constructeur de l'exception VilleRechargeInexistante.
     *
     * @param message Le message d'erreur associé à cette exception.
     */
    public VilleRechargeInexistante(String message) {
        super(message);
    }
}
