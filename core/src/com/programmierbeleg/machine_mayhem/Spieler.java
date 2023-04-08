package com.programmierbeleg.machine_mayhem;

public class Spieler extends SpielObjekt implements GrafikAusgabe {

    private int leben;
    private int maxLeben;

    public Spieler(float x, float y, float breite, float höhe){
        super(x,y,breite,höhe,true);
        leben=100;
        maxLeben=100;

    }

    @Override
    public void render(float delta) {

    }
    public void update(float delta) {

    }
}
