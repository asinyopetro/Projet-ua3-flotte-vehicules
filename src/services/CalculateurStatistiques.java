package services;

import modele.Vehicule;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/** Stats sur la flotte (revenu, km, utilisation, etc.). */
public class CalculateurStatistiques {

    private final List<Vehicule> vehicules;

    public CalculateurStatistiques(List<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }

    public double revenuTotal() {
        return vehicules.stream()
                .mapToDouble(Vehicule::getRevenuCumule)
                .sum();
    }

    public double kilometrageMoyen() {
        return vehicules.stream()
                .mapToInt(Vehicule::getKilometrage)
                .average()
                .orElse(0.0);
    }

    public Map<String, Double> tauxUtilisationParType() {
        Map<String, List<Vehicule>> parType = vehicules.stream()
                .collect(Collectors.groupingBy(Vehicule::getType));

        Map<String, Double> resultat = new LinkedHashMap<>();
        for (Map.Entry<String, List<Vehicule>> entry : parType.entrySet()) {
            double moyenneJours = entry.getValue().stream()
                    .mapToInt(Vehicule::getJoursLoues)
                    .average()
                    .orElse(0.0);
            resultat.put(entry.getKey(), moyenneJours);
        }
        return resultat;
    }

    public List<Vehicule> vehiculesLesPlusUtilises(int limite) {
        return vehicules.stream()
                .sorted(Comparator.comparingInt(Vehicule::getJoursLoues).reversed())
                .limit(limite)
                .toList();
    }

    public List<Vehicule> vehiculesJamaisLoues() {
        return vehicules.stream()
                .filter(v -> v.getJoursLoues() == 0)
                .sorted(Comparator.comparing(Vehicule::getId))
                .toList();
    }

    public Map<String, Long> nombreParType() {
        return vehicules.stream()
                .collect(Collectors.groupingBy(Vehicule::getType, Collectors.counting()));
    }

    public int nombreEnEntretien() {
        return (int) vehicules.stream()
                .filter(Vehicule::isEnEntretien)
                .count();
    }
}
