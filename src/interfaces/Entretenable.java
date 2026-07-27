package interfaces;

import exceptions.VehiculeIndisponibleException;

/** Entretien / reparation d'un vehicule. */
public interface Entretenable {

    void signalerEntretien() throws VehiculeIndisponibleException;

    boolean besoinEntretien();

    void terminerEntretien();
}
