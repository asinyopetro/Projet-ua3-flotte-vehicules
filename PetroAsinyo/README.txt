Travail de Komla Petro Asinyo
=============================

## Fichiers développés
- src/modele/Vehicule.java — classe abstraite
- src/modele/Voiture.java — tarif basé sur le nombre de places
- src/modele/Camion.java — tarif basé sur la charge utile
- src/modele/Moto.java — tarif basé sur la cylindrée
- src/interfaces/Louable.java
- src/interfaces/Entretenable.java
- src/interfaces/Rapportable.java
- src/exceptions/DonneeInvalideException.java
- src/exceptions/VehiculeIndisponibleException.java
- src/exceptions/KilometrageInvalideException.java
- src/Main.java — démo location, retour, entretien

## Principes appliqués
- Héritage et classe abstraite pour factoriser le code
- Interfaces pour séparer les responsabilités
- Redéfinition de méthodes pour les tarifs (OCP)
