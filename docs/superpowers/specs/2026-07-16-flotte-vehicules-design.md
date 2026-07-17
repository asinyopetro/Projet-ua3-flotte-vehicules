# Design — Projet 3 : Gestion d'une flotte de véhicules

**Cours :** Programmation Avancée · UA3 · Printemps 2026  
**Équipe :** Komla Petro Asinyo · Kassoum Dene  
**Dossiers GitHub :** `PetroAsinyo/` · `KassoumDene/`  
**Niveau :** bonifié modéré (plusieurs interfaces + démo console claire)  
**Date :** 2026-07-16

---

## 1. Objectif

Application console Java pour gérer une flotte de location : catalogue de véhicules (héritage), location/retour, entretien, statistiques, rapport CSV/TXT. Données initiales dans un CSV créé par l'équipe (≥15 véhicules, ≥1 ligne invalide).

## 2. Structure du dépôt (ce que le prof voit sur GitHub)

```
Projet-ua3-flotte-vehicules/
├── README.md                 # Nom du projet, membres, fonctionnalités, comment lancer
├── contributions.txt         # Qui a fait quoi
├── data/
│   └── vehicules.csv         # Données initiales + 1 ligne invalide
├── reports/                  # Rapport généré à l'exécution
├── src/
│   ├── Main.java
│   ├── modele/
│   ├── interfaces/
│   ├── exceptions/
│   ├── services/
│   └── util/
├── PetroAsinyo/
│   └── README.txt            # Travail de Petro
└── KassoumDene/
    └── README.txt            # Travail de Kassoum
```

**GitHub :** un seul repository collaboratif. Chaque étudiant remet le même lien dans le Pigeonnier. Commits réguliers depuis IntelliJ (pas d'upload navigateur).

## 3. Architecture

### 3.1 Hiérarchie (classe abstraite + redéfinition)

- `Vehicule` (abstract) : id, marque, modele, annee, kilometrage, disponible, joursLoues, revenuCumule, seuilEntretienKm
- Méthode abstraite : `double calculerTarif(int jours)`
- Sous-classes : `Voiture`, `Camion`, `Moto` (tarifs distincts)

### 3.2 Interfaces

- `Louable` : louer, retourner
- `Entretenable` : signalerEntretien, besoinEntretien, terminerEntretien
- `Rapportable` : ligne résumé pour le rapport (bonus / OCP)

### 3.3 Exceptions personnalisées

- `DonneeInvalideException` — CSV mal formé, type inconnu, champs manquants
- `VehiculeIndisponibleException` — location ou entretien impossible
- `KilometrageInvalideException` — km retour < km actuel ou négatif

### 3.4 Services (SRP)

| Classe | Responsabilité |
|--------|----------------|
| `VehiculeFactory` | Instancier le bon type (OCP) |
| `CsvVehiculeLoader` | Lire/valider le CSV, ignorer lignes invalides avec message |
| `GestionnaireFlotte` | Location, retour, entretien, liste ArrayList |
| `CalculateurStatistiques` | Revenu, km moyen, taux d'utilisation, top véhicules, entretien |
| `GenerateurRapport` | Écrire `reports/rapport_flotte.txt` (et optionnellement CSV) |

### 3.5 SOLID démontrables

- **SRP** : une classe = une responsabilité (loader ≠ stats ≠ flotte)
- **OCP** : ajouter `Utilitaire extends Vehicule` + case dans la factory, sans modifier `GestionnaireFlotte` / stats

## 4. Règles métier

1. **Location** : véhicule disponible et pas en entretien ; limite globale de locations simultanées (ex. 5) pour la démo.
2. **Retour** : km retour ≥ km actuel ; tarif = `calculerTarif(jours)` ; mise à jour km, revenu, disponibilité, joursLoues.
3. **Entretien** : si `kilometrage >= seuilEntretienKm` ou signalement manuel → indisponible jusqu'à `terminerEntretien()`.

## 5. CSV

Séparateur `;`. Colonnes :

`id;type;marque;modele;annee;kilometrage;disponible;joursLoues;revenuCumule;seuilEntretienKm`

- `type` ∈ {VOITURE, CAMION, MOTO}
- ≥15 lignes valides + ≥1 ligne invalide (ex. type `BUS` ou kilometrage `-100`)

## 6. Statistiques et rapport

- Revenu total généré
- Kilométrage moyen
- Taux d'utilisation par type
- Véhicules les plus utilisés (joursLoues)
- Véhicules nécessitant un entretien
- Affichage console + fichier `reports/rapport_flotte.txt`

## 7. Démo (`Main`)

1. Charger le CSV (afficher les erreurs de lignes invalides)
2. Afficher le parc
3. Simuler 1–2 locations / retours / un entretien
4. Afficher les statistiques
5. Générer le rapport

Menu minimal optionnel pour la présentation orale.

## 8. Répartition des tâches

| PetroAsinyo | KassoumDene |
|-------------|-------------|
| `modele/` (Vehicule, Voiture, Camion, Moto) | `CsvVehiculeLoader`, `VehiculeFactory` |
| `interfaces/` (Louable, Entretenable, Rapportable) | `GestionnaireFlotte` |
| `exceptions/` | `CalculateurStatistiques`, `GenerateurRapport` |
| README personnel | README personnel |
| Partie de `Main` (affichage parc / démo location) | Partie de `Main` (stats / rapport) + `contributions.txt` draft |

Travail collaboratif visible via commits et dossiers personnels.

## 9. Hors scope

- Interface graphique
- Base de données
- Chargement dynamique de classes (plugins)
- Application web

## 10. Critères de succès

- Toutes les exigences du sujet Projet 3 couvertes
- Au moins 2 principes SOLID visibles dans le code
- CSV + rapport + exceptions + ArrayList
- Repo GitHub propre : README, contributions, dossiers personnels, historique de commits
- Présentable le 4 août 2026
