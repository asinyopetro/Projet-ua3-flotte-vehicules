package modele;

import exceptions.KilometrageInvalideException;
import exceptions.VehiculeIndisponibleException;
import interfaces.Entretenable;
import interfaces.Louable;
import interfaces.Rapportable;

/** Classe de base pour tous les véhicules de la flotte. */
public abstract class Vehicule implements Louable, Entretenable, Rapportable {

    private final String id;
    private final String marque;
    private final String modele;
    private final int annee;
    private int kilometrage;
    private boolean disponible;
    private int joursLoues;
    private double revenuCumule;
    private final int seuilEntretienKm;
    private boolean enEntretien;

    protected Vehicule(String id, String marque, String modele, int annee,
                       int kilometrage, boolean disponible, int joursLoues,
                       double revenuCumule, int seuilEntretienKm) {
        this.id = id;
        this.marque = marque;
        this.modele = modele;
        this.annee = annee;
        this.kilometrage = kilometrage;
        this.disponible = disponible;
        this.joursLoues = joursLoues;
        this.revenuCumule = revenuCumule;
        this.seuilEntretienKm = seuilEntretienKm;
        this.enEntretien = false;
    }

    public abstract String getType();

    public abstract double calculerTarif(int jours);

    @Override
    public void louer() throws VehiculeIndisponibleException {
        if (!disponible || enEntretien) {
            throw new VehiculeIndisponibleException(
                    "Le véhicule " + id + " n'est pas disponible à la location.");
        }
        disponible = false;
    }

    @Override
    public double retourner(int nouveauKilometrage, int joursLocation)
            throws KilometrageInvalideException {
        if (nouveauKilometrage < kilometrage) {
            throw new KilometrageInvalideException(
                    "Kilométrage invalide pour " + id + ": " + nouveauKilometrage
                            + " km (actuel: " + kilometrage + " km).");
        }
        if (joursLocation <= 0) {
            throw new KilometrageInvalideException(
                    "Nombre de jours de location invalide pour " + id + ".");
        }

        double tarif = calculerTarif(joursLocation);
        kilometrage = nouveauKilometrage;
        joursLoues += joursLocation;
        revenuCumule += tarif;
        disponible = true;
        return tarif;
    }

    @Override
    public void signalerEntretien() throws VehiculeIndisponibleException {
        if (enEntretien) {
            throw new VehiculeIndisponibleException(
                    "Le véhicule " + id + " est déjà en entretien.");
        }
        enEntretien = true;
        disponible = false;
    }

    @Override
    public boolean besoinEntretien() {
        return kilometrage >= seuilEntretienKm || enEntretien;
    }

    @Override
    public void terminerEntretien() {
        enEntretien = false;
        disponible = true;
    }

    @Override
    public String genererLigneRapport() {
        return String.format("%s;%s;%s %s;%d km;%s;%d j;%s;%.2f $",
                id,
                getType(),
                marque,
                modele,
                kilometrage,
                disponible ? "DISPONIBLE" : (enEntretien ? "ENTRETIEN" : "LOUE"),
                joursLoues,
                besoinEntretien() ? "OUI" : "NON",
                revenuCumule);
    }

    public String getId() {
        return id;
    }

    public String getMarque() {
        return marque;
    }

    public String getModele() {
        return modele;
    }

    public int getAnnee() {
        return annee;
    }

    public int getKilometrage() {
        return kilometrage;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public int getJoursLoues() {
        return joursLoues;
    }

    public double getRevenuCumule() {
        return revenuCumule;
    }

    public int getSeuilEntretienKm() {
        return seuilEntretienKm;
    }

    public boolean isEnEntretien() {
        return enEntretien;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s %s (%d) - %d km - %s - Revenu: %.2f $",
                id,
                marque,
                modele,
                annee,
                kilometrage,
                disponible ? "Disponible" : (enEntretien ? "En entretien" : "Loué"),
                revenuCumule);
    }
}
