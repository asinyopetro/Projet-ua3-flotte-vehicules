package modele;

/** Voiture : le tarif dépend du nombre de places. */
public class Voiture extends Vehicule {

    private final int nombrePlaces;

    public Voiture(String id, String marque, String modele, int annee,
                   int kilometrage, boolean disponible, int joursLoues,
                   double revenuCumule, int seuilEntretienKm, int nombrePlaces) {
        super(id, marque, modele, annee, kilometrage, disponible, joursLoues,
                revenuCumule, seuilEntretienKm);
        this.nombrePlaces = nombrePlaces;
    }

    @Override
    public String getType() {
        return "VOITURE";
    }

    @Override
    public double calculerTarif(int jours) {
        double tarifBase = 45.0;
        double supplementPlaces = nombrePlaces > 5 ? 10.0 : 0.0;
        return (tarifBase + supplementPlaces) * jours;
    }

    public int getNombrePlaces() {
        return nombrePlaces;
    }
}
