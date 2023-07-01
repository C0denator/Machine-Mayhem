package com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.Interfaces.EinmalProFrame;
import com.programmierbeleg.machine_mayhem.Sonstiges.Animation;
import com.programmierbeleg.machine_mayhem.Spiel;
import com.programmierbeleg.machine_mayhem.Welt.Raum;

import java.util.Random;

public class Fernkampf_1 extends Gegner implements EinmalProFrame {

    Animation angriffAnimation;
    private float timer;
    Random rnd;

    public Fernkampf_1(float x, float y, Raum raum){
        super(x,y, raum);
        textur =Spiel.instanz.atlas.findRegion("robot",1);

        leben=50;
        maxLeben=50;
        laufGeschwindigkeit=100;

        angriffAnimation=new Animation(this,new TextureRegion[]{
                Spiel.instanz.atlas.findRegion("robot",1),
                Spiel.instanz.atlas.findRegion("robot",2),
                Spiel.instanz.atlas.findRegion("robot",3),
                Spiel.instanz.atlas.findRegion("robot",2)
        },0.25f,true);
        angriffAnimation.starteVonVorn();

        if(SpielAnzeige.physikObjekte==null){
            System.err.println("Fehler: SpielAnzeige.physikObjekte ist null");
        }else{
            SpielAnzeige.physikObjekte.add(this);
        }

        angriffAktiv=false;
        angriffCooldown=4.0f;
        angriffTimer=angriffCooldown;
        rnd = new Random();
    }

    @Override
    public void einmalProFrame(float delta) {
        if(angriffAktiv){
            angriff(delta);
        }else{
            denke(delta);
        }
    }
    @Override
    protected void angriff(float delta) {
        angriffAktiv=false;
    }
    @Override
    public void denke(float delta) {
        if(angriffTimer>0){
            angriffTimer-=delta;
            int tmpX = rnd.nextInt(200)-100;
            int tmpY = rnd.nextInt(200)-100;
            if(!prüfeKollision(raum,new Vector2((float)tmpX,(float)tmpY),delta)){
                bewegen(tmpX,tmpY,delta);
            }
        }else{
            angriffTimer=angriffCooldown;
            angriffAktiv=true;
        }
    }
}
