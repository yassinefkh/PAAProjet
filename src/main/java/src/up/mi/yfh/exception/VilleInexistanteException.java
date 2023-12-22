package src.up.mi.yfh.exception;

/**
 * Cette classe représente une exception personnalisée pour les villes inexistantes.
 */
public class VilleInexistanteException extends RuntimeException {

    /**
     * Constructeur de l'exception VilleInexistanteException.
     *
     * @param message Le message d'erreur associé à cette exception.
     */
    public VilleInexistanteException(String message) {
        super(message);
    }
}
