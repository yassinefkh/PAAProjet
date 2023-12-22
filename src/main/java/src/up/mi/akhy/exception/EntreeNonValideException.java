package src.up.mi.akhy.exception;

/**
 * Cette classe représente une exception pour les entrées non valides.
 */
public class EntreeNonValideException extends Exception {

    /**
     * Constructeur de l'exception EntreeNonValideException.
     *
     * @param message Le message d'erreur associé à cette exception.
     */
    public EntreeNonValideException(String message) {
        super(message);
    }
}
