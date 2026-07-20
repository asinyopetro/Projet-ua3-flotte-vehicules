package exceptions;

/**
 * Levée lorsqu'un véhicule ne peut pas être loué (indisponible ou en entretien).
 */
public class VehiculeIndisponibleException extends Exception {

    public VehiculeIndisponibleException(String message) {
        super(message);
    }
}
