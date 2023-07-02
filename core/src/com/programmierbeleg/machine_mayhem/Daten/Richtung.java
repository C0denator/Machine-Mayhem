package com.programmierbeleg.machine_mayhem.Daten;

/**
 * Alle Himmelsrichtungen. Wichtig für die Weltgenerierung.
 */
public enum Richtung {
    Nord,
    Süd,
    Ost,
    West;

    public static Richtung GegenRichtung(Richtung richtung){
        switch (richtung){
            case Nord:
                return Süd;
            case Süd:
                return Nord;
            case Ost:
                return West;
            case West:
                return Ost;
        }
        throw new RuntimeException("Rip");
    }
}
