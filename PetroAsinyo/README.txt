Travail de Komla Petro Asinyo
=============================

Fichiers :
- src/modele/Vehicule.java (classe abstraite)
- src/modele/Voiture.java
- src/modele/Camion.java
- src/modele/Moto.java
- src/interfaces/Louable.java
- src/interfaces/Entretenable.java
- src/interfaces/Rapportable.java
- src/exceptions/DonneeInvalideException.java
- src/exceptions/VehiculeIndisponibleException.java
- src/exceptions/KilometrageInvalideException.java
- partie demo dans Main.java (parc, location, retour, entretien)

Notes :
- Vehicule regroupe ce qui est commun
- chaque type calcule son tarif a sa facon (override de calculerTarif)
- les interfaces separent location / entretien / rapport
