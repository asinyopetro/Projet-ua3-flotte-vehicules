package modele;

/**
 * Véhicule utilitaire lourd — tarif adapté à la charge utile.
 */
public class Camion extends Vehicule {

    private final double chargeUtileKg;

    public Camion(String id, String marque, String modele, int annee,
                  int kilometrage, boolean disponible, int joursLoues,
                  double revenuCumule, int seuilEntretienKm, double chargeUtileKg) {
        super(id, marque, modele, annee, kilometrage, disponible, joursLoues,
                revenuCumule, seuilEntretienKm);
        this.chargeUtileKg = chargeUtileKg;
    }

    @Override
    public String getType() {
        return "CAMION";
    }

    @Override
    public double calculerTarif(int jours) {
        double tarifBase = 80.0;
        double facteurCharge = 1.0 + (chargeUtileKg / 5000.0) * 0.5;
        return tarifBase * facteurCharge * jours;
    }

    public double getChargeUtileKg() {
        return chargeUtileKg;
    }
}
