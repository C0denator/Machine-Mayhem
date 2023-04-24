package com.programmierbeleg.machine_mayhem.SpielObjekte;

public class Spieler extends SpielObjekt {

    private int leben;
    private int maxLeben;

    public Spieler(float x, float y, float breite, float höhe){
        super(x,y,breite,höhe,true, "Spieler");
        leben=100;
        maxLeben=100;

    }

}
