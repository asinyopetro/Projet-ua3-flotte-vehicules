package exceptions;

/**
 * Levée lorsque le kilométrage au retour est incohérent.
 */
public class KilometrageInvalideException extends Exception {

    public KilometrageInvalideException(String message) {
        super(message);
    }
}
