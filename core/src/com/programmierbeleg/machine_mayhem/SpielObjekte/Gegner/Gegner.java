package com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner;

import com.programmierbeleg.machine_mayhem.Daten.GegnerTyp;
import com.programmierbeleg.machine_mayhem.Spiel;
import com.programmierbeleg.machine_mayhem.SpielObjekte.SpielObjekt;

import java.util.Random;

public abstract class Gegner extends SpielObjekt {
    protected int leben;
    protected int maxLeben;
    protected int laufGeschwindigkeit;

    private float sekundenBisNächsterAngriff;

    public Gegner (float x, float y) {
        super (x, y,
                Spiel.instanz.atlas.findRegion("robot",1).getRegionWidth(),
                Spiel.instanz.atlas.findRegion("robot",1).getRegionHeight(),
                true,"Gegner");
    }

    protected abstract boolean angriff();
    protected abstract void denke();
}
