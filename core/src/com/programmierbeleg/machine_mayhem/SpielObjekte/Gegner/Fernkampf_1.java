package com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.programmierbeleg.machine_mayhem.Spiel;

public class Fernkampf_1 extends Gegner{

    public Fernkampf_1(float x, float y){
        super(x,y);
        textur =Spiel.instanz.atlas.findRegion("robot",1);

        leben=50;
        maxLeben=50;
        laufGeschwindigkeit=100;
    }

    @Override
    protected boolean angriff() {
        return false;
    }

    @Override
    protected void denke() {

    }
}
