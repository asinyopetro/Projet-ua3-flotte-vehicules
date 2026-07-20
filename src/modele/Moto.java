package modele;

/**
 * Deux-roues motorisé — tarif réduit avec léger ajustement selon la cylindrée.
 */
public class Moto extends Vehicule {

    private final int cylindree;

    public Moto(String id, String marque, String modele, int annee,
                int kilometrage, boolean disponible, int joursLoues,
                double revenuCumule, int seuilEntretienKm, int cylindree) {
        super(id, marque, modele, annee, kilometrage, disponible, joursLoues,
                revenuCumule, seuilEntretienKm);
        this.cylindree = cylindree;
    }

    @Override
    public String getType() {
        return "MOTO";
    }

    @Override
    public double calculerTarif(int jours) {
        double tarifBase = 25.0;
        double supplement = cylindree > 600 ? 8.0 : 0.0;
        return (tarifBase + supplement) * jours;
    }

    public int getCylindree() {
        return cylindree;
    }
}
