package com.programmierbeleg.machine_mayhem.SpielObjekte;

public class Gegner extends SpielObjekt{
    public Gegner(float x, float y, float breite, float höhe) {
        super(x, y, breite, höhe, true,"Gegner");
        this.setKlassenName("Gegner");
    }
}
