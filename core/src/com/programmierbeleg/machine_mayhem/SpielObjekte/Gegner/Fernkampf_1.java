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
    private Random rnd;
    private Vector2 bewegungsVektor;
    private float speed = 30.0f;

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
        bewegungsVektor=new Vector2();
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
        //dieser Gegner hat eine zufällige Bewegung
        if(angriffTimer>0){
            angriffTimer-=delta;

            if(bewegungsVektor.len2()<20) zufälligeBewegung();

            if(!kollidiertInZukunft(raum,bewegungsVektor,delta)){
                bewegen(bewegungsVektor,delta);
            }else{
                zufälligeBewegung();
            }

        }else{
            angriffTimer=angriffCooldown;
            angriffAktiv=true;
        }
    }

    private void zufälligeBewegung(){
        //erstellt einen zufälligen, normalisierten Vektor
        bewegungsVektor.x=rnd.nextFloat()*2.0f-1.0f;
        bewegungsVektor.y=rnd.nextFloat()*2.0f-1.0f;
        bewegungsVektor.x/=bewegungsVektor.len2();
        bewegungsVektor.y/=bewegungsVektor.len2();
        bewegungsVektor.x*=speed;
        bewegungsVektor.y*=speed;
    }
}
