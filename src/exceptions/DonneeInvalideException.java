package exceptions;

/** Donnee CSV ou champ invalide. */
public class DonneeInvalideException extends Exception {

    public DonneeInvalideException(String message) {
        super(message);
    }

    public DonneeInvalideException(String message, Throwable cause) {
        super(message, cause);
    }
}
