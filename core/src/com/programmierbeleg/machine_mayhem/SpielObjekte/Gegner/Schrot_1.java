package com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.Interfaces.EinmalProFrame;
import com.programmierbeleg.machine_mayhem.Sonstiges.Animation;
import com.programmierbeleg.machine_mayhem.Spiel;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Projektil;
import com.programmierbeleg.machine_mayhem.Welt.Raum;

import java.util.Random;

public class Schrot_1 extends Gegner implements EinmalProFrame {

    Animation animationRecharge;
    private float timer;
    private Random rnd;
    private Vector2 bewegungsVektor;
    private float speed = 30.0f;

    private float schussSpeed = 50.0f;

    public Schrot_1(float x, float y, Raum raum){
        super(x,y, raum);
        textur =Spiel.instanz.atlas.findRegion("schrot_idle");

        leben=40;
        maxLeben=40;
        laufGeschwindigkeit=100;

        animationRecharge =new Animation(this,new TextureRegion[]{
                Spiel.instanz.atlas.findRegion("schrot_recharge",4),
                Spiel.instanz.atlas.findRegion("schrot_recharge",3),
                Spiel.instanz.atlas.findRegion("schrot_recharge",2),
                Spiel.instanz.atlas.findRegion("schrot_recharge",1),
                Spiel.instanz.atlas.findRegion("schrot_idle"),
        },0.5f,false);

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
        Random rnd = new Random();
        for(int i=0; i<5; i++){
            float tmpWinkel = winkelZu(SpielAnzeige.spieler1) + (float)(rnd.nextInt(50)-25);
            SpielAnzeige.projektile.add(new Projektil(x,y,tmpWinkel,Spiel.instanz.atlas.findRegion("laser_rot",1),5, new Vector2(
                    (float) (-Math.sin( (tmpWinkel/180) * Math.PI)) * schussSpeed,
                    (float) (Math.cos( (tmpWinkel/180) * Math.PI)) * schussSpeed),
                    raum, true));
        }
        animationRecharge.starteVonVorn();
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
