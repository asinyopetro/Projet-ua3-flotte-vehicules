package services;

import exceptions.DonneeInvalideException;
import modele.Camion;
import modele.Moto;
import modele.Vehicule;
import modele.Voiture;

/** Crée le bon type de véhicule selon le champ "type" du CSV. */
public class VehiculeFactory {

    public Vehicule creer(String type, String id, String marque, String modele,
                          int annee, int kilometrage, boolean disponible,
                          int joursLoues, double revenuCumule,
                          int seuilEntretienKm, double attribut)
            throws DonneeInvalideException {

        String typeNormalise = type.trim().toUpperCase();

        return switch (typeNormalise) {
            case "VOITURE" -> new Voiture(id, marque, modele, annee, kilometrage,
                    disponible, joursLoues, revenuCumule, seuilEntretienKm, (int) attribut);
            case "CAMION" -> new Camion(id, marque, modele, annee, kilometrage,
                    disponible, joursLoues, revenuCumule, seuilEntretienKm, attribut);
            case "MOTO" -> new Moto(id, marque, modele, annee, kilometrage,
                    disponible, joursLoues, revenuCumule, seuilEntretienKm, (int) attribut);
            default -> throw new DonneeInvalideException(
                    "Type de véhicule inconnu: " + type);
        };
    }
}
