package interfaces;

import exceptions.KilometrageInvalideException;
import exceptions.VehiculeIndisponibleException;

/** Location et retour d'un vehicule. */
public interface Louable {

    void louer() throws VehiculeIndisponibleException;

    double retourner(int nouveauKilometrage, int joursLoues) throws KilometrageInvalideException;
}
