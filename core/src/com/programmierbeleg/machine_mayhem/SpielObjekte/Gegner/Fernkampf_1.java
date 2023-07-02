package com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.Interfaces.EinmalProFrame;
import com.programmierbeleg.machine_mayhem.Sonstiges.Animation;
import com.programmierbeleg.machine_mayhem.Spiel;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Projektile.Projektil;
import com.programmierbeleg.machine_mayhem.Welt.Raum;

import java.util.Random;

public class Fernkampf_1 extends Gegner implements EinmalProFrame {

    Animation AnimationAngriffStart;
    Animation AnimationAngriffEnde;
    private float timer;
    private Random rnd;
    private Vector2 bewegungsVektor;
    private float speed = 30.0f;

    private float schussSpeed = 300.0f;

    public Fernkampf_1(float x, float y, Raum raum){
        super(x,y, raum);
        textur =Spiel.instanz.atlas.findRegion("robot",1);

        leben=30;
        maxLeben=30;
        laufGeschwindigkeit=100;

        AnimationAngriffStart=new Animation(this,new TextureRegion[]{
                Spiel.instanz.atlas.findRegion("robot",1),
                Spiel.instanz.atlas.findRegion("robot",2),
                Spiel.instanz.atlas.findRegion("robot",3),
        },0.2f,false);
        AnimationAngriffEnde =new Animation(this,new TextureRegion[]{
                Spiel.instanz.atlas.findRegion("robot",3),
                Spiel.instanz.atlas.findRegion("robot",2),
                Spiel.instanz.atlas.findRegion("robot",1),
        },0.2f,false);

        if(SpielAnzeige.physikObjekte==null){
            System.err.println("Fehler: SpielAnzeige.physikObjekte ist null");
        }else{
            SpielAnzeige.physikObjekte.add(this);
        }

        angriffAktiv=false;
        angriffCooldown=2.5f;
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
        if(AnimationAngriffStart.isPausiert() && AnimationAngriffEnde.isPausiert()){
            if(!AnimationAngriffStart.isFertig() && !AnimationAngriffEnde.isFertig()){
                AnimationAngriffStart.starteVonVorn();
            }else if(AnimationAngriffStart.isFertig() && !AnimationAngriffEnde.isFertig()){
                //Angriff!
                float tmpWinkel = winkelZu(SpielAnzeige.spieler1);
                SpielAnzeige.projektile.add(new Projektil(x,y,tmpWinkel,Spiel.instanz.atlas.findRegion("laser_rot",1),10, new Vector2(
                        (float) (-Math.sin( (tmpWinkel/180) * Math.PI)) * schussSpeed,
                        (float) (Math.cos( (tmpWinkel/180) * Math.PI)) * schussSpeed),
                        raum, true));
                AnimationAngriffEnde.starteVonVorn();
            }else if(AnimationAngriffStart.isFertig() && AnimationAngriffEnde.isFertig()){
                angriffAktiv=false;
                AnimationAngriffStart.stop();
                AnimationAngriffEnde.stop();
            }
        }
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
