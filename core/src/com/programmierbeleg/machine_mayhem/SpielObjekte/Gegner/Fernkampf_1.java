package com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.Sonstiges.Animation;
import com.programmierbeleg.machine_mayhem.Spiel;

public class Fernkampf_1 extends Gegner{

    Animation angriffAnimation;

    public Fernkampf_1(float x, float y){
        super(x,y);
        textur =Spiel.instanz.atlas.findRegion("robot",1);

        leben=50;
        maxLeben=50;
        laufGeschwindigkeit=100;

        angriffAnimation=new Animation(this,new TextureRegion[]{
                Spiel.instanz.atlas.findRegion("robot",1),
                Spiel.instanz.atlas.findRegion("robot",2),
                Spiel.instanz.atlas.findRegion("robot",3)
        },0.25f,true);
        angriffAnimation.starteVonVorn();
        SpielAnzeige.physikObjekte.add(angriffAnimation);
    }

    @Override
    protected boolean angriff() {
        return false;
    }

    @Override
    protected void denke() {

    }
}
